package com.asmaulhusna.a99namesofallah;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ActivityListData extends AppCompatActivity {
    int pos;
    TextView image;
    TextView name, meaning, description;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_data);



        image = findViewById(R.id.imageShow);
        name = findViewById(R.id.textName);
        meaning = findViewById(R.id.textMeaning);
        description = findViewById(R.id.textDescription);


        pos = getIntent().getIntExtra("Position", -1);
        Log.e("position", String.valueOf(pos));

        image.setText(MainActivity.imagesSmall[pos]);
        name.setText(MainActivity.meaning[pos]);
        description.setText(MainActivity.description[pos]);


    }


//    @Override
//    protected int getLayoutResourceId() {
//        return R.layout.activity_list_data;
//    }
}
