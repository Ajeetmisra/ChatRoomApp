package com.ajeetMishra.flashchatnewfirebase;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.ajeetMishra.flashchatnewfirebase.RegisterActivity.CHAT_PREFS;
import static com.ajeetMishra.flashchatnewfirebase.RegisterActivity.DISPLAY_NAME_KEY;


public class MainChatActivity extends AppCompatActivity {

    // TODO: Add member variables here:
    private String mDisplayName;
    private ListView mChatListView;
    private EditText mInputText;
    private ImageButton mSendButton;
    private DatabaseReference mdatabasereference;
    private ChatListAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_chat);

        // TODO: Set up the display name and get the Firebase reference
            setdisplayname();
     mdatabasereference = FirebaseDatabase.getInstance().getReference();

        // Link the Views in the layout to the Java code
        mInputText = (EditText) findViewById(R.id.messageInput);
        mSendButton = (ImageButton) findViewById(R.id.sendButton);
        mChatListView = (ListView) findViewById(R.id.chat_list_view);

        // TODO: Send the message when the "enter" button is pressed
           mInputText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
               @Override
               public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                   sendMessage();
                   return false;
               }
           });

        // TODO: Add an OnClickListener to the sendButton to send a message
            mSendButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sendMessage();
                }
            });
    }

    // TODO: Retrieve the display name from the Shared Preferences
  private void setdisplayname(){
      SharedPreferences prfs = getSharedPreferences(CHAT_PREFS,MODE_PRIVATE);
     mDisplayName = prfs.getString(DISPLAY_NAME_KEY,null);
     if (mDisplayName == null) mDisplayName = "UNKNOWN";

  }

    private void sendMessage() {

        // TODO: Grab the text the user typed in and push the message to Firebase
         String input = mInputText.getText().toString();
         if(!input.equals(""))
         {
             Instantmesseges chat = new Instantmesseges(input,mDisplayName);
              mdatabasereference.child("messeges").push().setValue(chat);
              mInputText.setText("");
         }
    }

    // TODO: Override the onStart() lifecycle method. Setup the adapter here.
    @Override
    public void onStart() {
        super.onStart();
        mAdapter = new ChatListAdapter(this,mdatabasereference,mDisplayName);
        mChatListView.setAdapter(mAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();

        // TODO: Remove the Firebase event listener on the adapter.
       mAdapter.cleanup();
    }

}
