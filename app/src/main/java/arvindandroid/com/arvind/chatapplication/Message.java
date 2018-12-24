package arvindandroid.com.arvind.chatapplication;

public class Message {

    public String textMessage;
    public int data; //store respective data like drawable id
    public int viewType;
    public boolean isShimmer;

    public Message(String textMessage, int data, int viewType, boolean isShimmer) {
        this.textMessage = textMessage;
        this.data = data;
        this.viewType = viewType;
        this.isShimmer = isShimmer;
    }

    public Message() {
    }

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public boolean isShimmer() {
        return isShimmer;
    }

    public void setShimmer(boolean shimmer) {
        isShimmer = shimmer;
    }
}
