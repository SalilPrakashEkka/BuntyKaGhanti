package backcodes.com.buntykaghanti;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements RecognitionListener {

    private static final int REQUEST_RECORD_PERMISSION = 100;
    private TextView returnedText;
    private ToggleButton toggleButton;
    private ProgressBar progressBar;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;
    private String LOG_TAG = "VoiceRecognitionActivity";
    RecyclerView rcv;
    rcvadapter rcvadapter;
    MediaPlayer mediaPlayer;
    int i = 0;
    int s[]={R.raw.ayemc,R.raw.buntytochutiahai,R.raw.chatri,R.raw.dekhlegamc,R.raw.mtlbmeral,R.raw.taboot,R.raw.avibaakarrahahainamai};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        returnedText = (TextView) findViewById(R.id.textView1);
        progressBar = (ProgressBar) findViewById(R.id.progressBar1);
        toggleButton = (ToggleButton) findViewById(R.id.toggleButton1);
        progressBar.setVisibility(View.INVISIBLE);
        rcv=(RecyclerView)findViewById(R.id.rcv) ;
        rcvadapter=new rcvadapter(MainActivity.this);
        rcv.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        rcv.setAdapter(rcvadapter);
        speech = SpeechRecognizer.createSpeechRecognizer(this);
        Log.i(LOG_TAG, "isRecognitionAvailable: " + SpeechRecognizer.isRecognitionAvailable(this));
        speech.setRecognitionListener(MainActivity.this);
        recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE,
                "en");
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 3);

        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                if (isChecked) {
                    progressBar.setVisibility(View.VISIBLE);
                    progressBar.setIndeterminate(true);
                    ActivityCompat.requestPermissions
                            (MainActivity.this,
                                    new String[]{Manifest.permission.RECORD_AUDIO},
                                    REQUEST_RECORD_PERMISSION);
                } else {
                    progressBar.setIndeterminate(false);
                    progressBar.setVisibility(View.INVISIBLE);
                    speech.stopListening();
                }
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    speech.startListening(recognizerIntent);
                } else {
                    Toast.makeText(MainActivity.this, "Permission Denied!", Toast
                            .LENGTH_SHORT).show();
                }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (speech != null) {
            speech.destroy();
            Log.i(LOG_TAG, "destroy");
        }
    }


    @Override
    public void onBeginningOfSpeech() {
        Log.i(LOG_TAG, "onBeginningOfSpeech");
        progressBar.setIndeterminate(false);
        progressBar.setMax(10);
    }

    @Override
    public void onBufferReceived(byte[] buffer) {
        Log.i(LOG_TAG, "onBufferReceived: " + buffer);
    }

    @Override
    public void onEndOfSpeech() {
        Log.i(LOG_TAG, "onEndOfSpeech");
        progressBar.setIndeterminate(true);
        toggleButton.setChecked(false);
    }

    @Override
    public void onError(int errorCode) {
        String errorMessage = getErrorText(errorCode);
        Log.d(LOG_TAG, "FAILED " + errorMessage);
        returnedText.setText(errorMessage);
        toggleButton.setChecked(false);
    }

    @Override
    public void onEvent(int arg0, Bundle arg1) {
        Log.i(LOG_TAG, "onEvent");
    }

    @Override
    public void onPartialResults(Bundle arg0) {
        Log.i(LOG_TAG, "onPartialResults");
    }

    @Override
    public void onReadyForSpeech(Bundle arg0) {
        Log.i(LOG_TAG, "onReadyForSpeech");
    }

    @Override
    public void onResults(Bundle results) {
        Log.i(LOG_TAG, "onResults");
        ArrayList<String> matches = results
                .getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
        String text = "";
        text =matches.get(0);
        if(text.equalsIgnoreCase("bunty bhai")||text.equalsIgnoreCase("banty bhai")||text.equalsIgnoreCase("bunty bai"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[1]);
        }
        else if(text.equalsIgnoreCase("hi")||text.equalsIgnoreCase("hi bunty bhai")||text.equalsIgnoreCase("hello bunty bhai")||text.equalsIgnoreCase("Hi bunty")||text.equalsIgnoreCase("hello bunty")||text.equalsIgnoreCase("Hi banti")||text.equalsIgnoreCase("Hello banti"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[0]);
        }
        else if(text.equalsIgnoreCase("kya dalenge")||text.equalsIgnoreCase("bunty kya dalna hai")||text.equalsIgnoreCase("Chatri")||text.equalsIgnoreCase("kya dalega")||text.equalsIgnoreCase("kya darling")||text.equalsIgnoreCase("kya daalenge"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[2]);
        }
        else if(text.equalsIgnoreCase("Bunty to chutiya hai")||text.equalsIgnoreCase("lauda bunty")||text.equalsIgnoreCase("lada bunty")||text.equalsIgnoreCase("land bunty")||text.equalsIgnoreCase("bunty madarchod")||text.equalsIgnoreCase("bunty madarchodh")||text.equalsIgnoreCase("Bunty tu chutiya hai"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[3]);
        }
        else if(text.equalsIgnoreCase("matlab kya")||text.equalsIgnoreCase("bunty matlab kya")||text.equalsIgnoreCase("matlab"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[4]);
        }
        else if (text.equalsIgnoreCase("taboot kya hai")||text.equalsIgnoreCase("taboot kya hota hai")||text.equalsIgnoreCase("taboot")||text.equalsIgnoreCase("saboot"))
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[5]);
        }
        else
        {
            if(i>0)
                mediaPlayer.stop();
            play(s[6]);
        }
        returnedText.setText(text);
    }

    @Override
    public void onRmsChanged(float rmsdB) {
        Log.i(LOG_TAG, "onRmsChanged: " + rmsdB);
        progressBar.setProgress((int) rmsdB);
    }

    public static String getErrorText(int errorCode) {
        String message;
        switch (errorCode) {
            case SpeechRecognizer.ERROR_AUDIO:
                message = "Audio recording error";
                break;
            case SpeechRecognizer.ERROR_CLIENT:
                message = "Client side error";
                break;
            case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                message = "Insufficient permissions";
                break;
            case SpeechRecognizer.ERROR_NETWORK:
                message = "Network error";
                break;
            case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                message = "Network timeout";
                break;
            case SpeechRecognizer.ERROR_NO_MATCH:
                message = "No match";
                break;
            case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                message = "RecognitionService busy";
                break;
            case SpeechRecognizer.ERROR_SERVER:
                message = "error from server";
                break;
            case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                message = "No speech input";
                break;
            default:
                message = "Didn't understand, please try again.";
                break;
        }
        return message;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    public void play(int n)
    {
        i++;
        mediaPlayer=MediaPlayer.create(MainActivity.this,n);
        mediaPlayer.start();
    }
}
