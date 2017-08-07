package com.example.pd905.chatbot3.activities;

import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.pd905.chatbot3.R;
import com.example.pd905.chatbot3.adapter.ChatMessageAdapter;
import com.example.pd905.chatbot3.helper.ChatMessage;

import ai.api.AIServiceException;
import ai.api.RequestExtras;
import ai.api.android.AIConfiguration;
import ai.api.android.AIDataService;
import ai.api.android.AIService;
import ai.api.model.AIContext;
import ai.api.model.AIError;
import ai.api.model.AIEvent;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;
import ai.api.model.Result;
import ai.api.ui.AIButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ChatbotActivity extends AppCompatActivity implements AIButton.AIButtonListener {

    private static final String TAG = "Android: ";
    final static String CLIENT_ACCESS_TOKEN = "4290a50af03843b58fd102d1c985fdb1";

    private ChatMessageAdapter mAdapter;
    private EditText mInputMessage;
    private ListView mMessageList;
    private AIButton mAIButton;
    private AIService mAIService;
    private  AIDataService mAIDataService;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatbot);

        Log.wtf(TAG, "onCreate called");
        // request permission if not granted
        requestAudioPermission();



        // set ui components
        mAIButton = (AIButton) findViewById(R.id.ai_button);

        //init AI Service, must after mAIButton being initialized
        initService();



        mInputMessage = (EditText) findViewById(R.id.input_message);
        mInputMessage.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                boolean handled = false;
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendOnClick(v);
                    handled = true;
                }
                return handled;
            }
        });

        mAdapter = new ChatMessageAdapter(this, new ArrayList<ChatMessage>());
        mMessageList = (ListView) findViewById(R.id.messageList);
        mMessageList.setAdapter(mAdapter);
    }

    private void sendMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, true, false);
        mAdapter.add(chatMessage);

        //mimicOtherMessage(message);
    }

    private void mimicOtherMessage(String message) {
        ChatMessage chatMessage = new ChatMessage(message, false, false);
        mAdapter.add(chatMessage);
    }

    private void sendMessage() {
        ChatMessage chatMessage = new ChatMessage(null, true, true);
        mAdapter.add(chatMessage);

        mimicOtherMessage();
    }

    private void mimicOtherMessage() {
        ChatMessage chatMessage = new ChatMessage(null, false, true);
        mAdapter.add(chatMessage);
    }



    //actions when send button clicked
    public void sendOnClick(View view) {
        String message = mInputMessage.getText().toString();

        Log.wtf(TAG, "sendOnClick called");
        if (!TextUtils.isEmpty(message)) {
            sendMessage(message);

            Log.wtf(TAG, "calling sendRequest");
            sendRequest(message);
            mInputMessage.setText("");


        }


    }



    private void sendRequest( String message ){

        final AsyncTask<String, Void, AIResponse> task = new AsyncTask<String, Void, AIResponse>() {

            private AIError aiError;

            @Override
            protected AIResponse doInBackground(final String... params) {
                final AIRequest request = new AIRequest();
                String query = params[0];
                String event = params[1];

                if (!TextUtils.isEmpty(query))
                    request.setQuery(query);
                if (!TextUtils.isEmpty(event))
                    request.setEvent(new AIEvent(event));
                final String contextString = params[2];
                RequestExtras requestExtras = null;
                if (!TextUtils.isEmpty(contextString)) {
                    final List<AIContext> contexts = Collections.singletonList(new AIContext(contextString));
                    requestExtras = new RequestExtras(contexts, null);
                }

                try {
                    return mAIDataService.request(request, requestExtras);
                } catch (final AIServiceException e) {
                    aiError = new AIError(e);
                    return null;
                }
            }

            @Override
            protected void onPostExecute(final AIResponse response) {
                if (response != null) {
                    onResultText(response);
                } else {
                    onError(aiError);
                }

                //scroll the chatmessage list to bottom
                scrollMyListViewToBottom();
            }
        };

        task.execute(message, null, null);




    }


    public void onResultText(AIResponse response) {
        final Result result = response.getResult();
        final String aiSay = result.getFulfillment().getSpeech();
        mimicOtherMessage(aiSay);
    }



    //Implements AIButtonListener
    @Override
    public void onResult(final AIResponse response) {
        final Result result = response.getResult();
        final String userSay = result.getResolvedQuery();
        final String aiSay = result.getFulfillment().getSpeech();
        sendMessage(userSay);

        // AI reply after .5 second
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // reply after .5 seconds
                onResultText(response);
                //scroll the chatmessage list to bottom
                scrollMyListViewToBottom();
            }
        }, 500);


    }

    @Override
    public void onError(final AIError error) {

    }

    @Override
    public void onCancelled() {

    }

    //init AIDataService
    private void initService() {
        final AIConfiguration config = new AIConfiguration(CLIENT_ACCESS_TOKEN,
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        mAIService = AIService.getService(this, config);
        mAIDataService = new AIDataService(this, config);

        config.setRecognizerStartSound(getResources().openRawResourceFd(R.raw.test_start));
        config.setRecognizerStopSound(getResources().openRawResourceFd(R.raw.test_stop));
        config.setRecognizerCancelSound(getResources().openRawResourceFd(R.raw.test_cancel));
        mAIButton.initialize(config);
        mAIButton.setResultsListener(this);

    }



    //scroll the chatmessage list to bottom
    private void scrollMyListViewToBottom() {
        mMessageList.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                mMessageList.setSelection(mAdapter.getCount() -1);
            }
        });
    }



    private void requestAudioPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.RECORD_AUDIO}, 0);
        }
    }
}
