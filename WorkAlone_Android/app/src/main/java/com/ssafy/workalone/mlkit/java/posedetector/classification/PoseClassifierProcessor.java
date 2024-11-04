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

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import androidx.annotation.WorkerThread;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.common.base.Preconditions;
import com.google.mlkit.vision.pose.Pose;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import android.speech.tts.TextToSpeech;

/**
 * Accepts a stream of {@link Pose} for classification and Rep counting.
 */
public class PoseClassifierProcessor {
  private static final String TAG = "PoseClassifierProcessor";
  private static final String POSE_SAMPLES_FILE = "pose/pose_data.csv";
  public static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

  private static final String PUSHUPS_CLASS = "pushups_down";
  private static final String SQUATS_CLASS = "squats_down";
  private static final String JUMPING_JACKS_CLASS = "jumping_jacks_down";
  private static final String PULLUPS_CLASS = "pullups_down";
  private static final String SITUP_CLASS = "situp_down";
  private static final String PLANK_CLASS = "plank";
  private static long plankStartTime = 0;


  // 음석인식 부분
  private SpeechRecognizer speechRecognizer;
  private boolean isTracking = false; // "시작" 명령이 들어오면 true
  private boolean isPaused = false;   //




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

  private void requestAudioPermission(Activity activity) {
    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    }
  }

  @WorkerThread
  public PoseClassifierProcessor(Context context, boolean isStreamMode) {
    Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());
    this.isStreamMode = isStreamMode;
    this.context = context;


    initializeTextToSpeech();
    if (isStreamMode) {
      emaSmoothing = new EMASmoothing();
      repCounters = new ArrayList<>();
      lastRepResult = "";
    }
    mainHandler.post(this::initializeSpeechRecognition);

    loadPoseSamples(context);
  }

  private void initializeTextToSpeech() {
    textToSpeech = new TextToSpeech(context, status -> {
      if (status == TextToSpeech.SUCCESS) {
        textToSpeech.setLanguage(Locale.KOREAN);
      } else {
        Log.e(TAG, "TextToSpeech initialization failed");
      }
    });
  }

  public void requestAudioPermissionIfNeeded (Activity activity) {
    if (ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO)
            != PackageManager.PERMISSION_GRANTED) {
      ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, REQUEST_RECORD_AUDIO_PERMISSION);
    } else {
      Log.d(TAG, "Audio permission already granted.");
    }
  }

  private void initializeSpeechRecognition() {
    speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
    speechRecognizer.setRecognitionListener(new RecognitionListener() {

      @Override
      public void onReadyForSpeech(Bundle bundle) {
        Log.d(TAG, "음성 인식 준비됨");
        System.out.println("음석 인식 준비 됨");
      }

      @Override
      public void onBeginningOfSpeech() {
        Log.d(TAG, "음성 인식 시작됨");
        System.out.println("음성 인식 시작");

      }

      @Override
      public void onRmsChanged(float v) {

      }

      @Override
      public void onBufferReceived(byte[] bytes) {

      }

      @Override
      public void onEndOfSpeech() {
        Log.d(TAG, "음성 인식 종료됨");
      }

      @Override
      public void onError(int error) {
        Log.e(TAG, "음성 인식 오류 발생: " + error);
        switch (error) {
          case SpeechRecognizer.ERROR_NETWORK:
            Log.e(TAG, "네트워크 오류");
            break;
          case SpeechRecognizer.ERROR_AUDIO:
            Log.e(TAG, "오디오 입력 오류");
            break;
          case SpeechRecognizer.ERROR_NO_MATCH:
            Log.e(TAG, "일치하는 결과 없음");
            break;
          case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
            Log.e(TAG, "음성 입력 시간 초과");
            break;
          // 추가 오류 코드 확인 가능
        }
        startListening(); // 오류 발생 시 다시 듣기 시작
      }

      @Override
      public void onResults(Bundle results) {
        List<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        if (matches != null) {
          for (String command : matches) {
            Log.d(TAG, "음성 인식 결과: " + command); // 여기서 실시간으로 인식된 음성을 로그로 출력
            // 기존 코드 유지
          }
        }
        if (matches != null) {
          for (String command : matches) {
            if (command.equalsIgnoreCase("시작")) {
              isTracking = true;
              isPaused = false;
              Log.d(TAG, "운동 추적 시작됨");
            } else if (command.equalsIgnoreCase("중지")) {
              isPaused = true;
              Log.d(TAG, "운동 추적 일시 중지됨");
            } else if (command.equalsIgnoreCase("종료")) {
              isTracking = false;
              shutdown();
              Log.d(TAG, "운동 추적 종료됨");
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

      // RecognitionListener의 다른 메서드도 여기에 구현합니다.
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
      Log.e(TAG, "Error when loading pose samples.\n" + e);
    }
    poseClassifier = new PoseClassifier(poseSamples);
    if (isStreamMode) {
      for (String className : POSE_CLASSES) {
        repCounters.add(new RepetitionCounter(className));
      }
    }
  }



  @WorkerThread
  public List<String> getPoseResult(Pose pose) {
    Preconditions.checkState(Looper.myLooper() != Looper.getMainLooper());
    List<String> result = new ArrayList<>();


    ClassificationResult classification = poseClassifier.classify(pose);

    if (!isTracking || isPaused) {
      result.add("추적이 현재 일시 중지 또는 종료 상태입니다.");
      result.add("isTracking: "+isTracking);
      result.add("isPaused: "+isPaused);

      return result;
    }

    if (isStreamMode) {
      classification = emaSmoothing.getSmoothedResult(classification);

      if (pose.getAllPoseLandmarks().isEmpty()) {
        result.add(lastRepResult);
        return result;
      }

      for (RepetitionCounter repCounter : repCounters) {
        if (repCounter.getClassName().equals(PLANK_CLASS)) {
          if (classification.getMaxConfidenceClass().equals(PLANK_CLASS)) {
            if (plankStartTime == 0) {
              plankStartTime = System.currentTimeMillis();
            }

            long elapsedTime = (System.currentTimeMillis() - plankStartTime) / 1000;
            lastRepResult = String.format(Locale.KOREAN, "%s : %d 초", PLANK_CLASS, elapsedTime);

            if (elapsedTime % 3 == 0) {
              speakResult(lastRepResult);
            }
          } else {
            plankStartTime = 0;
          }
        } else {
          int repsBefore = repCounter.getNumRepeats();
          int repsAfter = repCounter.addClassificationResult(classification);
          if (repsAfter > repsBefore) {
         //   ToneGenerator tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);
           // tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            lastRepResult = String.format(Locale.KOREAN, "%s : %d", repCounter.getClassName(), repsAfter);
            speakResult(lastRepResult);
            break;
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
}
