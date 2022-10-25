package com.example.geofencing;

import android.app.Activity;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.speech.tts.TextToSpeech;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.JobIntentService;

import java.util.Locale;

public class SpeechService extends JobIntentService  {

    public static final String EXTRA_WORD = "Hello word";
    public static final String EXTRA_MEANING = "meaning";
    private TextToSpeech mySpeakTextToSpeech ;
    private boolean isSafeToDestroy = false;


    private String word, meaning;
    private boolean isInit;
    private Handler handler;
    private String msg="";
    public static void enqueueWork(Context context, Intent intent) {
        enqueueWork(context, SpeechService.class, 1, intent);
    }



    /*@Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handler.removeCallbacksAndMessages(null);

        word = intent.getStringExtra(SpeechService.EXTRA_WORD);
        meaning = intent.getStringExtra(SpeechService.EXTRA_MEANING);

        if (isInit) {
            speak();
        }

        handler.postDelayed(new Runnable() {

            @Override
            public void run() {
                stopSelf();
            }
        }, 15*1000);

        return SpeechService.START_NOT_STICKY;
    }*/

    @Override
    public void onDestroy() {
        if (isSafeToDestroy) {
            if (mySpeakTextToSpeech != null) {
                mySpeakTextToSpeech.shutdown();
            }
            super.onDestroy();
        }
    }


    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String message = intent.getStringExtra("MESSAGE");
        mySpeakTextToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {

        /*if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.US);
            if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                speak();
                isInit = true;
            }


        }*/
                mySpeakTextToSpeech.speak("hello word", TextToSpeech.QUEUE_ADD, null, null);
                while (mySpeakTextToSpeech.isSpeaking()) {

                }
                isSafeToDestroy = true;
            }
        });
        }

    /*private void speak() {
        if (tts != null) {
            tts.speak(msg, TextToSpeech.QUEUE_FLUSH, null);

        }
    }*/

   /* @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }*/

}
