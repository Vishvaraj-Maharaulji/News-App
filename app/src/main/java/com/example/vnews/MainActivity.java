package com.example.vnews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GridLayout grid = findViewById(R.id.grid_layout);

        for (int i= 0; i < grid.getChildCount(); i++){
            CardView container = (CardView) grid.getChildAt(i);
            ViewGroup viewGroup = ((ViewGroup)container.getChildAt(0));
            String getName = ((TextView)viewGroup.getChildAt(1)).getText().toString();
            container.setOnClickListener(new View.OnClickListener(){
                public void onClick(View view){
                    Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                    intent.putExtra("category",getName);
                    startActivity(intent);
                }
            });
        }

    }

}