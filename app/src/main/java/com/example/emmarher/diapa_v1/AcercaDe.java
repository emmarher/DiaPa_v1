package com.example.emmarher.diapa_v1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AcercaDe  extends AppCompatActivity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        Button btn_Main =  findViewById(R.id.btn_Main);
        btn_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent2, 0);
            }
        });
    }
}
