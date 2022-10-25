package com.example.geofencing;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.ArrayList;
import java.util.List;


/*class TTS extends Activity implements TextToSpeech.OnInitListener, TextToSpeech.OnUtteranceCompletedListener {
    public static TextToSpeech mTts;
    private String spokenText;
    private static final int TTS_CHECK_CODE = 101;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent checkIntent = new Intent();
        checkIntent.setAction(TextToSpeech.Engine.ACTION_CHECK_TTS_DATA);
        startActivityForResult(checkIntent, TTS_CHECK_CODE);
        // This is a good place to set spokenText
        mTts = new TextToSpeech(this, this);


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == TTS_CHECK_CODE) {
            if (resultCode == TextToSpeech.Engine.CHECK_VOICE_DATA_PASS) {
                // success, create the TTS instance
                mTts = new TextToSpeech(this, this);
            } else {
                // missing data, install it
                Intent installIntent = new Intent();
                installIntent.setAction(TextToSpeech.Engine.ACTION_INSTALL_TTS_DATA);
                startActivity(installIntent);
            }
        }


    }




    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            int result = mTts.setLanguage(Locale.US);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.v("OnInit", "Language is not available.");
            } else {
                Log.v("OnInit","Language Set");
            }
        } else {
            Log.v("onInit", "Could not initialize TextToSpeech.");
        }
        }


    @Override
    public void onUtteranceCompleted(String s) {

    }
}*/

   /* public class SpeechTT extends Service implements TextToSpeech.OnInitListener{

        @Override
        public IBinder onBind(Intent arg0) {
            return null;
        }

        @Override
        public void onInit(int status) {
            if (status == TextToSpeech.SUCCESS) {
                int result = mTts.setLanguage(Locale.US);
                if (result != TextToSpeech.LANG_MISSING_DATA && result != TextToSpeech.LANG_NOT_SUPPORTED) {
                    mTts.speak(spokenText, TextToSpeech.QUEUE_FLUSH, null);
                }
            }
        }
    }
}*/


public class GeofenceBroadcastReceiver extends BroadcastReceiver  {

    private static final String TAG = "GeofenceBroadcastReceiv";




    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
//        Toast.makeText(context, "Geofence triggered...", Toast.LENGTH_SHORT).show();

        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Error receiving geofence event...");
            return;
        }

        List<String> geofenceList = new ArrayList<String>();
        for (Geofence geofence: geofencingEvent.getTriggeringGeofences()) {
            geofenceList.add(geofence.getRequestId());
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
            int transitionType = geofencingEvent.getGeofenceTransition();

            switch (transitionType) {
                case Geofence.GEOFENCE_TRANSITION_ENTER:
                /*if (ACTION_SPEAKER.equals(intent.getAction())) {
                    Intent speechIntent = new Intent(context, SpeechService.class);
                    speechIntent.putExtra(SpeechService.EXTRA_WORD, intent.getStringExtra(SpeechService.EXTRA_WORD));
                    speechIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK |  Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                    context.startService(speechIntent);

                } else {
                    Log.d(TAG,"NOPE");
                }*/
                    Intent speechIntent = new Intent();
                    speechIntent.putExtra("MESSAGE", "");
                    SpeechService.enqueueWork(context, speechIntent);
                    Toast.makeText(context, "Nous approchons de l'arret Ecole de Police ", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_ENTER", "", MapsActivity.class);

                    //TTS.mTts.speak("Nous approchons de l'arret Ecole de Police", TextToSpeech.QUEUE_FLUSH,null);




                    break;
                case Geofence.GEOFENCE_TRANSITION_DWELL:
                    Toast.makeText(context, "Vous etes arrivés l'arret Ecole de Police", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL", "", MapsActivity.class);
                    break;
                case Geofence.GEOFENCE_TRANSITION_EXIT:
                    Toast.makeText(context, "Prochain arret Carrefour CHU", Toast.LENGTH_SHORT).show();
                    notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_EXIT", "", MapsActivity.class);
                    break;
            }
        }
//        Location location = geofencingEvent.getTriggeringLocation();


    }


    public static final String ACTION_SPEAKER = "com.appsrox.dailyvocab.action.SPEAKER";
}

