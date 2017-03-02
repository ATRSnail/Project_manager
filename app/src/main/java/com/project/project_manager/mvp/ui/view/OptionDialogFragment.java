package com.project.project_manager.mvp.ui.view;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.project_manager.R;
import com.project.project_manager.mvp.ui.adapter.OptionDialogAdapter;

import butterknife.BindView;

/**
 * @author xch
 * @version 1.0
 * @create_date 17/2/6
 */

public class OptionDialogFragment extends DialogFragment{

    @BindView(R.id.radio_rv)
    RecyclerView radioRv;

    private OptionDialogAdapter optionDialogAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dialog, container);
    }


}
