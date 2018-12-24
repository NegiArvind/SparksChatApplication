package arvindandroid.com.arvind.chatapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class NoInternetFragment extends Fragment {

    private Button retryButton;
    private Context context;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.internet_alert_dialog_fragment,container,false);
        retryButton=view.findViewById(R.id.retryButton);
        context=getContext();
        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(NetworkCheck.isNetworkAvailable(context)){
                    getFragmentManager().beginTransaction().replace(R.id.frameLayout,ChatFragment.newInstance()).commit();
                }
            }
        });
        return view;
    }

    public static NoInternetFragment newInstance() {
        
        Bundle args = new Bundle();
        
        NoInternetFragment fragment = new NoInternetFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
