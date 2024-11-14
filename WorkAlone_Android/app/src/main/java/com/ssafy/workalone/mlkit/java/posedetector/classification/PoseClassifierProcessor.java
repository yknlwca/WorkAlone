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

package com.ssafy.workalone.mlkit.java.posedetector.classification;
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


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.util.Log;

import androidx.annotation.WorkerThread;

import com.google.common.base.Preconditions;
import com.google.mlkit.vision.pose.Pose;
import com.ssafy.workalone.presentation.viewmodels.ExerciseMLKitViewModel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Accepts a stream of {@link Pose} for classification and Rep counting.
 */
public class PoseClassifierProcessor {
  private static final String TAG = "PoseClassifierProcessor";
  private static  String POSE_SAMPLES_FILE = "";
  public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

  private static final String PUSHUPS_CLASS = "pushups_down";
  private static final String SQUATS_CLASS = "squats_down";
  private static final String JUMPING_JACKS_CLASS = "jumping_jacks_down";
  private static final String PULLUPS_CLASS = "pullups_down";
  private static final String SITUP_CLASS = "situp_down";
  private static final String PLANK_CLASS = "plank";
  private boolean plankFlag = false;
  private static final float MINIMUM_RMS_THRESHOLD = 3.5f; // 감도 기준 설정 (조정 가능)
  private boolean isEnvironmentLoud = false;


  // 음석인식 부분
  private SpeechRecognizer speechRecognizer;
  private boolean isTracking = true; // "시작" 명령이 들어오면 true
  private boolean isPaused = false;




  private TextToSpeech textToSpeech;
  private Context context;

  private static final String[] POSE_CLASSES = {
          PUSHUPS_CLASS, SQUATS_CLASS, JUMPING_JACKS_CLASS, PULLUPS_CLASS, SITUP_CLASS, PLANK_CLASS
  };

  private final boolean isStreamMode;
  private EMASmoothing emaSmoothing;
  private List<RepetitionCounter> repCounters;
  private PoseClassifier poseClassifier;
  private String lastRepResult;
  private Handler mainHandler = new Handler(Looper.getMainLooper());
  private ExerciseMLKitViewModel viewModel;

  @WorkerThread
  public PoseClassifierProcessor(Context context, boolean isStreamMode,String ExerciseType, ExerciseMLKitViewModel viewModel) {
    Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());
    this.isStreamMode = isStreamMode;
    this.context = context;
    this.viewModel = viewModel;



    setPoseSamplesFile(ExerciseType);
    Log.d("exer: ",ExerciseType);
    Log.d("exer:  ",POSE_SAMPLES_FILE);
    initializeTextToSpeech();

    if (isStreamMode) {
      emaSmoothing = new EMASmoothing();
      repCounters = new ArrayList<>();
      lastRepResult = "";
    }
    mainHandler.post(this::initializeSpeechRecognition);

    Log.d("exer","poseClassifierProcessor");
    loadPoseSamples(context);
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



  // 음성 인식 부분

  private void initializeSpeechRecognition() {
    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
    speechRecognizer.setRecognitionListener(new RecognitionListener() {

      @Override
      public void onReadyForSpeech(Bundle bundle)
      {
        Log.d("exer", "음성 인식 준비됨");
        //System.out.println("음석 인식 준비 됨");
      }

      @Override
      public void onBeginningOfSpeech()
      {Log.i("exer", "음성 인식 시작됨");
       // ystem.out.println("음성 인식 시작");

      }

      @Override
      public void onRmsChanged(float rms) {
        if (rms < MINIMUM_RMS_THRESHOLD) {
          isEnvironmentLoud = false;
        } else {
          isEnvironmentLoud = true;
        }
      }

      @Override
      public void onBufferReceived(byte[] bytes) {

      }

      @Override
      public void onEndOfSpeech() {
     //   Log.d(TAG, "음성 인식 종료됨");
      }

      @Override
      public void onError(int error) {
        Log.e("exer", "음성 인식 오류 발생: " + error);
        switch (error) {
          case SpeechRecognizer.ERROR_NETWORK:
            //Log.e("exer", "네트워크 오류");
            break;
          case SpeechRecognizer.ERROR_AUDIO:
           //Log.e("exer", "오디오 입력 오류");
            break;
          case SpeechRecognizer.ERROR_NO_MATCH:
           // Log.e("exer", "일치하는 결과 없음");
            break;
          case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
            //Log.e("exer", "음성 입력 시간 초과");
            break;

        }


        startListening(); // 오류 발생 시 다시 듣기 시작
      }

      @Override
      public void onResults(Bundle results) {
        List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

        if (matches != null) {
          for (String command : matches) {
            if (command.equalsIgnoreCase("시작")) {
              isTracking = true;
              isPaused = false;
              viewModel.startExercise();
              Log.d("exer", "운동 추적 시작됨");
            } else if (command.equalsIgnoreCase("정지")) {
              isPaused = true;
              viewModel.stopExercise();
             Log.d("exer", "운동 추적 일시 중지됨");
            } else if (command.equalsIgnoreCase("종료")) {
              isTracking = false;
              viewModel.clickExit();

            }
          }
        }
        // 명령을 지속적으로 듣기 위해 다시 시작
        startListening();
      }

      @Override
      public void onPartialResults(Bundle bundle) {

      }

      @Override
      public void onEvent(int i, Bundle bundle) {

      }


    });
    startListening();
  }
  private void startListening() {


    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
    intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.KOREAN);
    speechRecognizer.startListening(intent);
  }

  private void loadPoseSamples(Context context) {
    List<PoseSample> poseSamples = new ArrayList<>();
    try {
      BufferedReader reader = new BufferedReader(
              new InputStreamReader(context.getAssets().open(POSE_SAMPLES_FILE)));
      String csvLine = reader.readLine();
      while (csvLine != null) {
        PoseSample poseSample = PoseSample.getPoseSample(csvLine, ",");
        if (poseSample != null) {
          poseSamples.add(poseSample);
        }
        csvLine = reader.readLine();
      }
    } catch (IOException e) {
    }
    poseClassifier = new PoseClassifier(poseSamples);
    if (isStreamMode) {
      for (String className : POSE_CLASSES) {
        repCounters.add(new RepetitionCounter(className));
      }
    }
  }



  @WorkerThread
  public List<String> getPoseResult(Pose pose, ExerciseMLKitViewModel viewModel) {
    Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());
    List<String> result = new ArrayList<>();




    ClassificationResult classification = poseClassifier.classify(pose);

    if (viewModel.isResting().getValue()) {result.add("추적이 종료되었습니다.");
      result.add("isTracking: " + isTracking);
      result.add("isPaused: " + isPaused);
      return result;
    }

    if (!viewModel.isExercising().getValue()) {
      result.add("추적이 현재 일시 중지 상태입니다.");
      result.add("isTracking: " + isTracking);
      result.add("isPaused: " + isPaused);
      return result;
    }

    if (isStreamMode) {
      classification = emaSmoothing.getSmoothedResult(classification);

      if (pose.getAllPoseLandmarks().isEmpty()) {
        result.add(lastRepResult);
        return result;
      }


      for (RepetitionCounter repCounter : repCounters) {


        if (POSE_SAMPLES_FILE.equals("pose/plank.csv")) {
          if (classification.getMaxConfidenceClass().equals(PLANK_CLASS))
          {
//            // 플래그를 true로 설정하여 플랭크 자세 인식
//            plankFlag = true;
//            lastRepResult = String.format(Locale.KOREAN, "%s : 유지 중", PLANK_CLASS);
//            Log.d("exer","플랭크 자세 유지중 "+plankFlag);
          }
          else {
//            // 플랭크 자세를 벗어나면 플래그를 false로 설정
//            plankFlag = false;
//
//            lastRepResult = String.format(Locale.KOREAN, "%s : 중단됨", PLANK_CLASS);
//            Log.d("exer","플랭크 자세 유지 X "+plankFlag);
          }
//          if(plankFlag){
//            viewModel.startExercise();
//          }else{
//            viewModel.stopExercise();
//          }
        }
        else {
          int repsBefore = repCounter.getNumRepeats();
          int repsAfter = repCounter.addClassificationResult(classification);
          if (repsAfter > repsBefore) {


            lastRepResult = String.format(Locale.KOREAN, "%s : %d", repCounter.getClassName(), repsAfter," iaTracking: "+isTracking+"  isPaues: "+isPaused);
            viewModel.addRep("스쿼트, 푸쉬업, 윗몸일으키기");
            Log.d("exer","count: "+String.valueOf(repsAfter));
            speakResult(String.valueOf(viewModel.getNowRep().getValue()));
            break;
          }
          //횟수 다채우면 다음세트
          if(viewModel.getNowRep().getValue() == viewModel.getTotalRep().getValue()){
            viewModel.addSet();
             repCounter.setNumRepeats();

            //세트 다 채우면 다음 운동 or 운동 완료
            if(viewModel.getNowSet().getValue() == viewModel.getTotalSet().getValue()+1){
              viewModel.exerciseFinish();
            }
          }
        }
      }
      result.add(lastRepResult);
    }

    if (!pose.getAllPoseLandmarks().isEmpty()) {
      String maxConfidenceClass = classification.getMaxConfidenceClass();
      String maxConfidenceClassResult = String.format(
              Locale.KOREAN,
              "%s : %.2f confidence",
              maxConfidenceClass,
              classification.getClassConfidence(maxConfidenceClass)
                      / poseClassifier.confidenceRange());
      result.add(maxConfidenceClassResult);
    }

    return result;
  }

  private void speakResult(String result) {
    if (textToSpeech != null && !textToSpeech.isSpeaking()) {
      textToSpeech.speak(result, TextToSpeech.QUEUE_FLUSH, null, null);
    }
  }

  public void shutdown() {
    if (textToSpeech != null) {
      textToSpeech.stop();
      textToSpeech.shutdown();
    }
    if (speechRecognizer != null) {
      speechRecognizer.stopListening();
      speechRecognizer.cancel();
      speechRecognizer.destroy();
    }
  }

  private void setPoseSamplesFile(String exerciseType) {
    switch (exerciseType.toLowerCase()) {
      case "스쿼트":
        POSE_SAMPLES_FILE = "pose/squat.csv";
        break;
      case "푸쉬업":
        POSE_SAMPLES_FILE = "pose/pushup.csv";
        break;
      case "플랭크":
        POSE_SAMPLES_FILE = "pose/plank.csv";
        break;
      case "윗몸 일으키기":
        POSE_SAMPLES_FILE = "pose/situp.csv";
        break;
      default:
        break;
    }

  }

}
