package com.jacobsbmi.quizforkids;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class fragmentLandScape extends Fragment {

    // onCreateView() is called when the fragment’s layout is needed
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.landscape_fragment, container, false);
        //inflate layout into the fragment -> fragment uses portrait_fragment
    }
}

