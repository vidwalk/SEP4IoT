package com.application.cmapp.activities;



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
        View view = inflater.inflate(R.layout.activity_app_fragment, container, false);
        TextView textView = view.findViewById(R.id.fragText);
        textView.setText(getArguments().getString("Message"));
        return view;
    }
}
