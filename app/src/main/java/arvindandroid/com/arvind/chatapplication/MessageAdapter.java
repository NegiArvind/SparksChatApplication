package arvindandroid.com.arvind.chatapplication;

import android.app.Activity;
import android.content.Context;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.shimmer.ShimmerFrameLayout;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter {

    private ArrayList<Message> messageArrayList;
    private Context context;
    private RecyclerViewClickListener recyclerViewClickListener;

    public MessageAdapter(ArrayList<Message> messageArrayList, Context context) {
        this.messageArrayList=messageArrayList;
        this.context=context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view;

        switch (i){
            case Common.NOTIFICATION_TEXT_ID:
                view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.notification_message_raw_layout,viewGroup,false);
                return new NotificationMessageHolder(view);

            case Common.MY_TEXT_ID:
                view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.my_message_raw_layout,viewGroup,false);
                return new MyMessageHolder(view);

            case Common.OTHERS_TEXT_ID:
                view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.others_message_raw_layout,viewGroup,false);
                return new OthersMessageHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder viewHolder, int i) {
        Message message=messageArrayList.get(i);
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NetworkCheck.isNetworkAvailable(context)) {
                    messageArrayList.add(new Message("Dummy data added", -1, Common.MY_TEXT_ID, true));
                    notifyDataSetChanged();
                    new CountDownTimer(500, 1000) {

                        @Override
                        public void onTick(long millisUntilFinished) {

                        }

                        @Override
                        public void onFinish() {
                            messageArrayList.get(messageArrayList.size() - 1).setShimmer(false);
                            notifyDataSetChanged();
                        }
                    }.start();
                }else{
                    ((AppCompatActivity)context).
                            getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout,NoInternetFragment.newInstance()).commit();
                }
            }
        });
        viewHolder.setIsRecyclable(false);
        if(viewHolder.getItemViewType()==Common.NOTIFICATION_TEXT_ID){
            NotificationMessageHolder notificationMessageHolder= (NotificationMessageHolder) viewHolder;
            if(message.isShimmer){
                notificationMessageHolder.notificationShimmerFrameLayout.startShimmerAnimation();
            }else {
                notificationMessageHolder.notificationShimmerFrameLayout.stopShimmerAnimation();
                notificationMessageHolder.notificationTextMessage.setText(message.getTextMessage());
            }
        }else if(viewHolder.getItemViewType()==Common.MY_TEXT_ID){
            MyMessageHolder myMessageHolder=(MyMessageHolder)viewHolder;
                myMessageHolder.myShimmerFrameLayout.stopShimmerAnimation();
                if (messageArrayList.get(i).getData() == -1) {
                    myMessageHolder.myImageView.setVisibility(View.GONE);
                    if(message.isShimmer){
                        myMessageHolder.myShimmerFrameLayout.startShimmerAnimation();
                    }else {
                        myMessageHolder.myShimmerFrameLayout.stopShimmerAnimation();
                        myMessageHolder.myTextMessage.setText(message.getTextMessage());
                    }
                } else {
                    myMessageHolder.myTextMessage.setVisibility(View.GONE);
                    if(message.isShimmer) {
                        myMessageHolder.myShimmerFrameLayout.startShimmerAnimation();
                    }else {
                        myMessageHolder.myShimmerFrameLayout.stopShimmerAnimation();
                        myMessageHolder.myImageView.setImageResource(message.getData());
                    }
                }
            }
        else {
            OthersMessageHolder othersMessageHolder = (OthersMessageHolder) viewHolder;
                othersMessageHolder.othersShimmerFrameLayout.stopShimmerAnimation();
                if (messageArrayList.get(i).getData() == -1) {
                    othersMessageHolder.othersImageView.setVisibility(View.GONE);
                    if (message.isShimmer) {
                    othersMessageHolder.othersShimmerFrameLayout.startShimmerAnimation();
                    }else {
                        othersMessageHolder.othersShimmerFrameLayout.stopShimmerAnimation();
                        othersMessageHolder.othersTextMessage.setText(message.getTextMessage());
                    }
                } else {
                    othersMessageHolder.othersTextMessage.setVisibility(View.GONE);
                    if (message.isShimmer) {
                        othersMessageHolder.othersShimmerFrameLayout.startShimmerAnimation();
                    }else {
                        othersMessageHolder.othersShimmerFrameLayout.stopShimmerAnimation();
                        othersMessageHolder.othersImageView.setImageResource(message.getData());
                    }
                }
        }
    }

    @Override
    public int getItemCount() {
        return this.messageArrayList.size();
    }


    @Override
    public int getItemViewType(int position) {
        Log.i("position",String.valueOf(position));
        switch (messageArrayList.get(position).getViewType()){
            case Common.NOTIFICATION_TEXT_ID:
                return Common.NOTIFICATION_TEXT_ID;
            case Common.MY_TEXT_ID:
                return Common.MY_TEXT_ID;
            case Common.OTHERS_TEXT_ID:
                return Common.OTHERS_TEXT_ID;
        }
        return -1;
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    public static class MyMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView myImageView;
        private TextView myTextMessage;
        private ShimmerFrameLayout myShimmerFrameLayout;
        private RecyclerViewClickListener recyclerViewClickListener;

        MyMessageHolder(@NonNull View itemView) {
            super(itemView);
            myShimmerFrameLayout=itemView.findViewById(R.id.myShimmerFrameLayout);
            myImageView=itemView.findViewById(R.id.my_imageview);
            myTextMessage=itemView.findViewById(R.id.my_text_message);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            recyclerViewClickListener.onClick(v,getAdapterPosition());
        }
    }

    public static class OthersMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView othersImageView;
        private TextView othersTextMessage;
        private ShimmerFrameLayout othersShimmerFrameLayout;

        OthersMessageHolder(@NonNull View itemView) {
            super(itemView);
            othersShimmerFrameLayout=itemView.findViewById(R.id.otherShimmerFrameLayout);
            othersImageView=itemView.findViewById(R.id.others_imageview);
            othersTextMessage=itemView.findViewById(R.id.others_message);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }

    public static class NotificationMessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView notificationTextMessage;
        private ShimmerFrameLayout notificationShimmerFrameLayout;
        NotificationMessageHolder(@NonNull View itemView) {
            super(itemView);
            notificationShimmerFrameLayout=itemView.findViewById(R.id.notificationShimmerFrameLayout);
            notificationTextMessage=itemView.findViewById(R.id.notification_message);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
