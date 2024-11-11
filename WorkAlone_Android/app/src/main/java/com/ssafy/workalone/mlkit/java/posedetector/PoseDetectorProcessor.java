/*
 * Copyright 2020 Google LLC. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ssafy.workalone.mlkit.java.posedetector;

import static android.webkit.ConsoleMessage.MessageLevel.LOG;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.tasks.Task;
import com.google.android.odml.image.MlImage;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.pose.Pose;
import com.google.mlkit.vision.pose.PoseDetection;
import com.google.mlkit.vision.pose.PoseDetector;
import com.google.mlkit.vision.pose.PoseDetectorOptionsBase;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import com.google.mlkit.vision.pose.PoseLandmark;
import com.google.mlkit.vision.pose.Pose;
import com.ssafy.workalone.mlkit.GraphicOverlay;
import com.ssafy.workalone.mlkit.java.VisionProcessorBase;
import com.ssafy.workalone.mlkit.java.posedetector.classification.PoseClassifierProcessor;
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel;

/** A processor to run pose detector. */
public class PoseDetectorProcessor
        extends VisionProcessorBase<PoseDetectorProcessor.PoseWithClassification> {
  private static final String TAG = "PoseDetectorProcessor";

  private final PoseDetector detector;

  private final boolean showInFrameLikelihood;
  private final boolean visualizeZ;
  private final boolean rescaleZForVisualization;
  private final boolean runClassification;
  private final boolean isStreamMode;
  private final Context context;
  private final Executor classificationExecutor;
  private final String ExerciseType;
  private TextToSpeech textToSpeech;  // TTS instance
  private final ExerciseMLKitViewModel viewModel;
  private static final long TTS_COOLDOWN_MS = 10000; // 메시지 호출 간 최소 간격 (10초)
  private long lastTtsTimeOutOfFrame = 0; // "화면 안으로 들어와 주세요" 메시지의 마지막 호출 시간
  private long lastTtsTimeInFrame = 0; // "안으로 들어왔습니다" 메시지의 마지막 호출 시간

  private long lastTtsTime = 0; // 마지막 TTS 호출 시간
  private PoseClassifierProcessor poseClassifierProcessor;

  private boolean wasOutOfFrame = false;


  /** Internal class to hold Pose and classification results. */
  protected static class PoseWithClassification {
    private final Pose pose;
    private final List<String> classificationResult;

    public PoseWithClassification(Pose pose, List<String> classificationResult) {
      this.pose = pose;
      this.classificationResult = classificationResult;
    }

    public Pose getPose() {
      return pose;
    }

    public List<String> getClassificationResult() {
      return classificationResult;
    }
  }

  public PoseDetectorProcessor(
          Context context,
          PoseDetectorOptionsBase options,
          boolean showInFrameLikelihood,
          boolean visualizeZ,
          boolean rescaleZForVisualization,
          boolean runClassification,
          boolean isStreamMode,
          String ExerciseType,
           ExerciseMLKitViewModel viewModel1) {
    super(context);

    this.showInFrameLikelihood = showInFrameLikelihood;
    this.visualizeZ = visualizeZ;
    this.rescaleZForVisualization = rescaleZForVisualization;
    detector = PoseDetection.getClient(options);
    this.runClassification = runClassification;
    this.isStreamMode = isStreamMode;
    this.context = context;
    this.ExerciseType=ExerciseType;
    this.viewModel = viewModel1;
    classificationExecutor = Executors.newSingleThreadExecutor();
    Log.d("exer","PoseDetectorProcessor");

  initializeTextToSpeech();


  }

  @Override
  public void stop() {
    super.stop();
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }

    detector.close();
  }

  @Override
  protected Task<PoseWithClassification> detectInImage(InputImage image) {
    return detector
            .process(image)
            .continueWith(
                    classificationExecutor,
                    task -> {
                      Pose pose = task.getResult();


                      List<String> classificationResult = new ArrayList<>();
                      if (runClassification) {
                        if (poseClassifierProcessor == null) {
                          Log.d("exer","dectectInImage");
                          poseClassifierProcessor = new PoseClassifierProcessor(context, isStreamMode,ExerciseType);
                        }
                        classificationResult = poseClassifierProcessor.getPoseResult(pose,viewModel);
                      }
                      return new PoseWithClassification(pose, classificationResult);
                    });
  }


  // 화면 전부 들어오게 하는부분
  @Override
  protected Task<PoseWithClassification> detectInImage(MlImage image) {
    return detector
            .process(image)
            .continueWith(
                    classificationExecutor,
                    task -> {
                      Pose pose = task.getResult();


                      // 이게 false면 화면안에 없는거임

//                      boolean isIn = true;
//
//                      if (!isFullBodyVisible(pose)) {
//                        Log.d(TAG, "Full body is not visible. Skipping frame.");
//
//                        isIn= false;
//                        long currentTime = System.currentTimeMillis();
//                        // 마지막 TTS 호출 시간에서 10초가 경과했는지 확인
//                        if (currentTime - lastTtsTime >= TTS_COOLDOWN_MS) {
//                          textToSpeech.speak("화면 안으로 모두 들어와주세요", TextToSpeech.QUEUE_FLUSH, null, null);
//                          lastTtsTime = currentTime; // 마지막 TTS 호출 시간 업데이트
//                        }
//
//                        return null;
//                      }
                      boolean isInFrame = isFullBodyVisible(pose);

                      long currentTime = System.currentTimeMillis();

                      // 화면 안에 들어온 경우
                      if (isInFrame) {
                        if (wasOutOfFrame) {
                          // Cooldown 시간 이후에만 "안으로 들어왔습니다" 메시지 출력
                          if (currentTime - lastTtsTimeInFrame >= TTS_COOLDOWN_MS) {
                            textToSpeech.speak("안으로 들어왔습니다", TextToSpeech.QUEUE_FLUSH, null, null);
                            lastTtsTimeInFrame = currentTime;
                          }
                          wasOutOfFrame = false; // 상태를 화면 안에 있음으로 변경
                        }
                      } else { // 화면 밖에 있는 경우
                        if (!wasOutOfFrame) {
                          wasOutOfFrame = true; // 상태를 화면 밖으로 설정
                        }
                        // Cooldown 시간 이후에만 "화면 안으로 들어와 주세요" 메시지 출력
                        if (currentTime - lastTtsTimeOutOfFrame >= TTS_COOLDOWN_MS) {
                          textToSpeech.speak("화면 안으로 모두 들어와주세요", TextToSpeech.QUEUE_FLUSH, null, null);
                          lastTtsTimeOutOfFrame = currentTime;
                        }
                        return null; // 화면 밖에 있으면 포즈 인식을 진행하지 않음
                      }
                      //Log.d("exer","dectectInImage");
                      List<String> classificationResult = new ArrayList<>();
                     // Log.d("exer",String.valueOf(runClassification));
                      if (runClassification) {
                        if (poseClassifierProcessor == null) {
                          poseClassifierProcessor = new PoseClassifierProcessor(context, isStreamMode,ExerciseType);
                        }
                        classificationResult = poseClassifierProcessor.getPoseResult(pose,viewModel);
                      }
                      return new PoseWithClassification(pose, classificationResult);
                    });
  }

  @Override
  protected void onSuccess(
          @NonNull PoseWithClassification poseWithClassification,
          @NonNull GraphicOverlay graphicOverlay) {
//    graphicOverlay.add(
//        new PoseGraphic(
//            graphicOverlay,
//            poseWithClassification.pose,
//            showInFrameLikelihood,
//            visualizeZ,
//            rescaleZForVisualization,
//            poseWithClassification.classificationResult));

    if (poseWithClassification != null) { // null 체크 추가
      graphicOverlay.add(
              new PoseGraphic(
                      graphicOverlay,
                      poseWithClassification.pose,
                      showInFrameLikelihood,
                      visualizeZ,
                      rescaleZForVisualization,
                      poseWithClassification.classificationResult));
    }
  }

  @Override
  protected void onFailure(@NonNull Exception e) {
    Log.e(TAG, "Pose detection failed!", e);
  }

  @Override
  protected boolean isMlImageEnabled(Context context) {
    // Use MlImage in Pose Detection by default, change it to OFF to switch to InputImage.
    return true;
  }





  // 주요 랜드마크가 모두 감지되었는지 확인하는 메서드 추가
  private static final float LANDMARK_CONFIDENCE_THRESHOLD = 0.5f; // 신뢰도 임계값 설정

  private boolean isFullBodyVisible(Pose pose) {
    // 전체 신체를 인식하는 데 필요한 주요 랜드마크 (어깨, 손목, 엉덩이, 발목 등)
    int[] requiredLandmarks = {

            PoseLandmark.LEFT_SHOULDER,
//            PoseLandmark.RIGHT_SHOULDER,
//            PoseLandmark.LEFT_HIP,
//            PoseLandmark.RIGHT_HIP,
//            PoseLandmark.LEFT_WRIST,
//            PoseLandmark.RIGHT_WRIST,
//            PoseLandmark.LEFT_ANKLE,
//            PoseLandmark.RIGHT_ANKLE
    };

    for (int landmarkType : requiredLandmarks) {
      PoseLandmark landmark = pose.getPoseLandmark(landmarkType);
      if (landmark == null || landmark.getInFrameLikelihood() < LANDMARK_CONFIDENCE_THRESHOLD) {
        // 주요 랜드마크가 하나라도 감지되지 않거나 신뢰도가 낮으면 전체 신체가 보이지 않음
        return false;
      }
    }
    return true; // 주요 랜드마크가 모두 감지되고 신뢰도 기준을 충족한 경우
  }
  private void initializeTextToSpeech() {
    textToSpeech = new TextToSpeech(context, status -> {
      if (status == TextToSpeech.SUCCESS) {
        textToSpeech.setLanguage(Locale.KOREAN);
      } else {
        // Log.e(TAG, "TextToSpeech initialization failed");
      }
    });
  }



}