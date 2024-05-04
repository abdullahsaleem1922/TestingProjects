package com.asmaulhusna.a99namesofallah;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;



public  class PP extends DialogFragment {
    Button Yes;
    CheckBox agreeCheckBox;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, getActivity().getApplicationInfo().theme);

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            getActivity().setRequestedOrientation(
                    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else {

            getActivity().setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
    }

    public static PP newInstance(String title) {
        PP frag = new PP();
        Bundle args = new Bundle();
        args.putString("title", title);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setIcon( R.drawable.icon)
                // Set Dialog Title
                .setTitle(getResources().getString( R.string.app_name));
        builder.setMessage(getResources().getString(R.string.optin_message));
        builder.setPositiveButton(getActivity().getResources().getString(R.string.optin_yesbtn),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });


        LayoutInflater i = getActivity().getLayoutInflater();
        View v = i.inflate(R.layout.frg_pp, null);

        TextView clickLink = (TextView) v.findViewById(R.id.link_tv);
        clickLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent( Intent.ACTION_VIEW);
                i.setData( Uri.parse("https://sites.google.com/view/radiantislamicapps/home"));
                startActivity(i);
            }
        });

        builder.setView(v);
        return builder.create();
    }
    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        getActivity().setRequestedOrientation( ActivityInfo.SCREEN_ORIENTATION_USER);
    }
    @Override
    public void onStart() {
        super.onStart();
        Yes = ((AlertDialog) getDialog())
                .getButton( AlertDialog.BUTTON_POSITIVE);
        Yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDialog().dismiss();

            }
        });

    }
}