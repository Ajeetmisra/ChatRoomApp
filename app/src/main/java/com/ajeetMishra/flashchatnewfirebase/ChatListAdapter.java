package com.ajeetMishra.flashchatnewfirebase;

import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import javax.sql.StatementEvent;

public class ChatListAdapter extends BaseAdapter {

    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mSnapshotList;
    private ChildEventListener mlistener = new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            mSnapshotList.add(dataSnapshot);
            notifyDataSetChanged();


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };


    public ChatListAdapter(Activity activity, DatabaseReference ref, String name) {
        mActivity = activity;
        mDisplayName = name;
        mDatabaseReference = ref.child("messeges");
        mSnapshotList = new ArrayList<>();
        mDatabaseReference.addChildEventListener(mlistener);

    }

    static class ViewHolder {
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams params;

    }

    @Override
    public int getCount() {
        return mSnapshotList.size();
    }

    @Override
    public Instantmesseges getItem(int position) {
        DataSnapshot snapshot = mSnapshotList.get(position);
        return snapshot.getValue(Instantmesseges.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            convertView = inflater.inflate(R.layout.chat_msg_row, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.authorName = (TextView) convertView.findViewById(R.id.author);
            holder.body = (TextView) convertView.findViewById(R.id.message);
            holder.params = (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);
        }
        final Instantmesseges message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        String author = message.getAuthor();
        holder.authorName.setText(author);
        String msg = message.getMessege();
        holder.body.setText(msg);
        return convertView;
    }
    void cleanup()
    {
        mDatabaseReference.removeEventListener(mlistener);
    }


}

