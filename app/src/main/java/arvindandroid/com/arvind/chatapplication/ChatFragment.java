package arvindandroid.com.arvind.chatapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class ChatFragment extends Fragment {


    private RecyclerView messageRecyclerView;
    private LinearLayoutManager layoutManager;
    private ArrayList<Message> messageArrayList;
    private String messageArray[];
    private LinearLayout linearLayout;
    private MessageAdapter messageAdapter;
    private Context context;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chat_fragment_layout,container,false);
        context=getContext();

        messageRecyclerView=view.findViewById(R.id.messageRecyclerView);
        linearLayout=view.findViewById(R.id.linearLayout);

        layoutManager=new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        layoutManager.setStackFromEnd(true);
//        layoutManager.setReverseLayout(true);
        messageRecyclerView.setLayoutManager(layoutManager);
        messageArray=getResources().getStringArray(R.array.messages);
        Log.i("array length",String.valueOf(messageArray.length));


        setDumyList(true);

        messageRecyclerView.setHasFixedSize(true);
        messageAdapter=new MessageAdapter(messageArrayList,context);
//        messageAdapter.setHasStableIds(true);
        messageRecyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);
        messageAdapter.notifyDataSetChanged();
        messageRecyclerView.setAdapter(messageAdapter);


        setPostDelayed(-1);

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked","true");
                messageArrayList.add(new Message("Dummy Data is added",-1,Common.MY_TEXT_ID,false));
//                Log.i("Clicked","true")
                messageAdapter.notifyDataSetChanged();
                setPostDelayed(messageArrayList.size()-1);
            }
        });
        return view;
    }

    private void setPostDelayed(final int index) {
        messageRecyclerView.postDelayed(new Runnable() {
            @Override
            public void run() {
//                setDumyList(false);
                if(index==-1) {
                    for (Message message : messageArrayList) {
                        message.setShimmer(false);
                    }
                }else{
                    Message message=messageArrayList.get(index);
                    message.setShimmer(true);
                }
                messageAdapter.notifyDataSetChanged();
            }
        },500);
    }

    private void setDumyList(boolean isShimmer) {
        messageArrayList=new ArrayList<>();
        for(int i=0;i<messageArray.length;i++) {

            if(i==7||i==19||i==33||i==43){
                messageArrayList.add(new Message(messageArray[i],-1,Common.NOTIFICATION_TEXT_ID,isShimmer));
            }
            else if(i%2==0){
                if(i==5||i==10){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.emma_watson,Common.MY_TEXT_ID,isShimmer));
                }else if(i==35||i==15||i==20){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.nature_image,Common.MY_TEXT_ID,isShimmer));

                }else if(i==30||i==24||i==40||i==45){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.nature_image2,Common.MY_TEXT_ID,isShimmer));
                }
                else{
                    Log.i("message", i +" "+messageArray[i]);
                    messageArrayList.add(new Message(messageArray[i],-1,Common.MY_TEXT_ID,isShimmer));
                    Log.i("list message",messageArrayList.get(i).getTextMessage());
                }
            }
            else{
                if(i==5||i==10){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.emma_watson,Common.OTHERS_TEXT_ID,isShimmer));
                }else if(i==35||i==15||i==20){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.nature_image,Common.OTHERS_TEXT_ID,isShimmer));

                }else if(i==30||i==24||i==40||i==45){
                    messageArrayList.add(new Message(messageArray[i],R.drawable.nature_image2,Common.OTHERS_TEXT_ID,isShimmer));
                }
                else{
                    Log.i("message", i +" "+messageArray[i]);
                    messageArrayList.add(new Message(messageArray[i],-1,Common.OTHERS_TEXT_ID,isShimmer));
                    Log.i("list message",messageArrayList.get(i).getTextMessage());
                }
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(!NetworkCheck.isNetworkAvailable(context)){
            addDifferentFragment(NoInternetFragment.newInstance());
        }
    }

    private void addDifferentFragment(Fragment fragment){
        getFragmentManager().beginTransaction().replace(R.id.frameLayout,fragment).commit();
    }

    public static ChatFragment newInstance() {

        Bundle args = new Bundle();

        ChatFragment fragment = new ChatFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
