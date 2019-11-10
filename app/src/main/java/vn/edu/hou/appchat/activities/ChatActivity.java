package vn.edu.hou.appchat.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yarolegovich.lovelydialog.LovelyInfoDialog;
import com.yarolegovich.lovelydialog.LovelyProgressDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import de.hdodenhof.circleimageview.CircleImageView;
import vn.edu.hou.appchat.R;
import vn.edu.hou.appchat.db.SharedPreferenceHelper;
import vn.edu.hou.appchat.models.Config;
import vn.edu.hou.appchat.models.Conversation;
import vn.edu.hou.appchat.models.Message;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerChat;
    public static final int VIEW_TYPE_USER_MESSAGE = 0;
    public static final int VIEW_TYPE_FRIEND_MESSAGE = 1;
    private ListMessageAdapter adapter;
    private String roomId;
    private ArrayList<CharSequence> idFriend;
    private Conversation conversation;
    private ImageButton btnSend;
    private EditText editWriteMessage;
    private LinearLayoutManager linearLayoutManager;
    public static HashMap<String, Bitmap> bitmapAvatarFriend;
    public Bitmap bitmapAvatarUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intentData = getIntent();
        idFriend = intentData.getCharSequenceArrayListExtra(Config.INTENT_KEY_CHAT_ID);
        roomId = intentData.getStringExtra(Config.INTENT_KEY_CHAT_ROOM_ID);
        String nameFriend = intentData.getStringExtra(Config.INTENT_KEY_CHAT_FRIEND);

        conversation = new Conversation();
        btnSend = (ImageButton) findViewById(R.id.btnSend);
        btnSend.setOnClickListener(this);

        String base64AvatarUser = SharedPreferenceHelper.getInstance(this).getUserInfo().avatar;
        if (!base64AvatarUser.equals(Config.STR_DEFAULT_BASE64)) {
            byte[] decodedString = Base64.decode(base64AvatarUser, Base64.DEFAULT);
            bitmapAvatarUser = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        } else {
            bitmapAvatarUser = null;
        }

        editWriteMessage = (EditText) findViewById(R.id.editWriteMessage);
        if (idFriend != null && nameFriend != null) {
            getSupportActionBar().setTitle(nameFriend);
            linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
            recyclerChat = (RecyclerView) findViewById(R.id.recyclerChat);
            recyclerChat.setLayoutManager(linearLayoutManager);
            conversation.setRoomID(roomId);
            adapter = new ListMessageAdapter(this, conversation, bitmapAvatarFriend, bitmapAvatarUser);
            FirebaseDatabase.getInstance().getReference().child("message/" + roomId).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                    if (dataSnapshot.getValue() != null) {
                        HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                        Message newMessage = new Message();
                        newMessage.key = (String) dataSnapshot.getKey();
                        newMessage.idSender = (String) mapMessage.get("idSender");
                        newMessage.idReceiver = (String) mapMessage.get("idReceiver");
                        newMessage.text = (String) mapMessage.get("text");
                        newMessage.timestamp = (long) mapMessage.get("timestamp");
                        conversation.getListMessageData().add(newMessage);
                        adapter.notifyDataSetChanged();
                        linearLayoutManager.scrollToPosition(conversation.getListMessageData().size() - 1);
                    }
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
            recyclerChat.setAdapter(adapter);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent result = new Intent();
            result.putExtra("idFriend", idFriend.get(0));
            setResult(RESULT_OK, result);
            this.finish();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        Intent result = new Intent();
        result.putExtra("idFriend", idFriend.get(0));
        setResult(RESULT_OK, result);
        this.finish();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnSend) {
            String content = editWriteMessage.getText().toString().trim();
            if (content.length() > 0) {
                editWriteMessage.setText("");
                Message newMessage = new Message();
                newMessage.text = content;
                newMessage.idSender = Config.UID;
                newMessage.idReceiver = roomId;
                newMessage.timestamp = System.currentTimeMillis();
                FirebaseDatabase.getInstance().getReference().child("message/" + roomId).push().setValue(newMessage);
            }
        }
    }

    public void removeMessage(Message m) {
        conversation.getListMessageData().remove(m);
        adapter.notifyDataSetChanged();
    }
}

class ListMessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private Conversation conversation;
    private HashMap<String, Bitmap> bitmapAvatar;
    private HashMap<String, DatabaseReference> bitmapAvatarDB;
    private Bitmap bitmapAvatarUser;
    private int viewType;
    LovelyProgressDialog dialogWaitDeleting;


    public ListMessageAdapter(Context context, Conversation conversation, HashMap<String, Bitmap> bitmapAvatar, Bitmap bitmapAvatarUser) {
        this.context = context;
        this.conversation = conversation;
        this.bitmapAvatar = bitmapAvatar;
        this.bitmapAvatarUser = bitmapAvatarUser;
        bitmapAvatarDB = new HashMap<>();
        dialogWaitDeleting = new LovelyProgressDialog(context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.viewType = viewType;
        if (viewType == ChatActivity.VIEW_TYPE_FRIEND_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_friend, parent, false);
            return new ItemMessageFriendHolder(view);
        } else if (viewType == ChatActivity.VIEW_TYPE_USER_MESSAGE) {
            View view = LayoutInflater.from(context).inflate(R.layout.rc_item_message_user, parent, false);
            return new ItemMessageUserHolder(view);
        }
        return null;
    }

    //    friends time message: comment: 212
//    users time message: comment: 245
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof ItemMessageFriendHolder) {
            ((ItemMessageFriendHolder) holder).txtContent.setText(conversation.getListMessageData().get(position).text);
            ((ItemMessageFriendHolder) holder).txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(conversation.getListMessageData().get(position).timestamp)));
            ((ItemMessageFriendHolder) holder).txtTime.setVisibility(View.GONE);
            Bitmap currentAvatar = bitmapAvatar.get(conversation.getListMessageData().get(position).idSender);
            if (currentAvatar != null) {
                ((ItemMessageFriendHolder) holder).avatar.setImageBitmap(currentAvatar);
            } else {
                final String id = conversation.getListMessageData().get(position).idSender;
                if (bitmapAvatarDB.get(id) == null) {
                    bitmapAvatarDB.put(id, FirebaseDatabase.getInstance().getReference().child("user/" + id + "/avatar"));
                    bitmapAvatarDB.get(id).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.getValue() != null) {
                                String avataStr = (String) dataSnapshot.getValue();
                                if (!avataStr.equals(Config.STR_DEFAULT_BASE64)) {
                                    byte[] decodedString = Base64.decode(avataStr, Base64.DEFAULT);
                                    ChatActivity.bitmapAvatarFriend.put(id, BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length));
                                } else {
                                    ChatActivity.bitmapAvatarFriend.put(id, BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_default_avatar));
                                }
                                notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        } else if (holder instanceof ItemMessageUserHolder) {
            ((ItemMessageUserHolder) holder).txtContent.setText(conversation.getListMessageData().get(position).text);
            ((ItemMessageUserHolder) holder).txtTime.setText(new SimpleDateFormat("HH:mm").format(new Date(conversation.getListMessageData().get(position).timestamp)));
            ((ItemMessageUserHolder) holder).txtTime.setVisibility(View.GONE);
            if (bitmapAvatarUser != null) {
                ((ItemMessageUserHolder) holder).avatar.setImageBitmap(bitmapAvatarUser);
            }
        }

//        remove friends message: uncomment: 257 - 279, comment: 280
//        remove user message: uncomment: 287 - 310, comment: 311
        if (holder instanceof ItemMessageFriendHolder) {
            ((View) ((ItemMessageFriendHolder) holder).txtContent.getParent()).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    new AlertDialog.Builder(context)
//                            .setTitle("Delete message")
//                            .setMessage("Are you sure want to delete ?")
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    final String roomId = conversation.getRoomID();
//                                    final String key = conversation.getListMessageData().get(position).key;
//                                    dialogWaitDeleting.setTitle("Deleting...")
//                                            .setCancelable(false)
//                                            .setTopColorRes(R.color.colorAccent)
//                                            .show();
//                                    deleteMessage(roomId, key);
//                                }
//                            })
//                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            }).show();
//                    return true;
                    return false;
                }
            });
        } else if (holder instanceof ItemMessageUserHolder) {
            ((View) ((ItemMessageUserHolder) holder).txtContent.getParent()).setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
//                    new AlertDialog.Builder(context)
//                            .setTitle("Delete message")
//                            .setMessage("Are you sure want to delete ?")
//                            .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                    final String roomId = conversation.getRoomID();
//                                    final String key = conversation.getListMessageData().get(position).key;
//                                    dialogWaitDeleting.setTitle("Deleting...")
//                                            .setCancelable(false)
//                                            .setTopColorRes(R.color.colorAccent)
//                                            .show();
//                                    deleteMessage(roomId, key);
//                                }
//                            })
//                            .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialogInterface, int i) {
//                                    dialogInterface.dismiss();
//                                }
//                            }).show();
//
//                    return true;
                    return false;
                }
            });
        }
    }

    private void deleteMessage(final String roomId, final String idRemoval) {
        if (roomId != null && idRemoval != null) {
            FirebaseDatabase.getInstance().getReference().child("message").child(roomId)
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.getValue() == null) {
                                //email not found
                                dialogWaitDeleting.dismiss();
                                new LovelyInfoDialog(context)
                                        .setTopColorRes(R.color.colorAccent)
                                        .setTitle("Error")
                                        .setMessage("Error occurred during deleting message")
                                        .show();
                            } else {
//                        String idRemoval = ((HashMap) dataSnapshot.getValue()).keySet().iterator().next().toString();
                                FirebaseDatabase.getInstance().getReference().child("message")
                                        .child(roomId).child(idRemoval).removeValue()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                dialogWaitDeleting.dismiss();

                                                new LovelyInfoDialog(context)
                                                        .setTopColorRes(R.color.colorAccent)
                                                        .setTitle("Success")
                                                        .setMessage("Message deleting successfully")
                                                        .show();

                                                for (Message m : conversation.getListMessageData()) {
                                                    if (m.key.equals(idRemoval)) {
                                                        ((ChatActivity) context).removeMessage(m);
                                                    }
                                                }
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                dialogWaitDeleting.dismiss();
                                                new LovelyInfoDialog(context)
                                                        .setTopColorRes(R.color.colorAccent)
                                                        .setTitle("Error")
                                                        .setMessage("Error occurred during deleting friend")
                                                        .show();
                                            }
                                        });
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
        } else {
            dialogWaitDeleting.dismiss();
            new LovelyInfoDialog(context)
                    .setTopColorRes(R.color.colorPrimary)
                    .setTitle("Error")
                    .setMessage("Error occurred during deleting friend")
                    .show();
        }
    }

    @Override
    public int getItemViewType(int position) {
        return conversation.getListMessageData().get(position).idSender.equals(Config.UID) ? ChatActivity.VIEW_TYPE_USER_MESSAGE : ChatActivity.VIEW_TYPE_FRIEND_MESSAGE;
    }

    @Override
    public int getItemCount() {
        return conversation.getListMessageData().size();
    }
}

class ItemMessageUserHolder extends RecyclerView.ViewHolder {
    public TextView txtContent, txTtime, txtTime;
    public CircleImageView avatar;

    public ItemMessageUserHolder(View itemView) {
        super(itemView);
        txtContent = (TextView) itemView.findViewById(R.id.textContentUser);
        avatar = (CircleImageView) itemView.findViewById(R.id.imageView2);
        txtTime = (TextView) itemView.findViewById(R.id.time_message);
    }
}

class ItemMessageFriendHolder extends RecyclerView.ViewHolder {
    public TextView txtContent, txtTime;
    public CircleImageView avatar;

    public ItemMessageFriendHolder(View itemView) {
        super(itemView);
        txtContent = itemView.findViewById(R.id.textContentFriend);
        avatar = itemView.findViewById(R.id.imageView3);
        txtTime = (TextView) itemView.findViewById(R.id.time_message);
    }
}
