package com.application.cmapp.activities.fragments;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.application.cmapp.R;

public class AppFragment extends Fragment {

    public AppFragment(){
        //No-args constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstance){

        return inflater.inflate(R.layout.activity_app_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance)
    {
        TextView textView = view.findViewById(R.id.titleView);
        TextView textView2 = view.findViewById(R.id.description);

        switch(getArguments().getInt("Page")){
            case 1:
                textView.setText("Temperature");
                textView2.setText("Press the button to get the current temperature.");
                break;
            case 2:
                textView.setText("Humidity");
                textView2.setText("Press the button to get the current humidity.");
                break;
            case 3:
                textView.setText("Carbon Dioxide");
                textView2.setText("Press the button to get the current carbon dioxide levels.");
                break;
            case 4:
                textView.setText("Sound");
                textView2.setText("Press the button to get the current sound level.");
                break;
            default:
                break;
        }
    }
}
