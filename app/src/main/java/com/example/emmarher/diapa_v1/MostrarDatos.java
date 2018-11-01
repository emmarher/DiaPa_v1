package com.example.emmarher.diapa_v1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MostrarDatos extends AppCompatActivity {
    private TextView et_Ax_x, et_Ax_y, et_Ax_z, et_Pulso, et_Nombre, et_Mail, et_Edad, et_Genero, et_Temp, et_Gax_x, et_Gax_y, et_Gax_z;
    private String dni;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_data);

        et_Nombre = findViewById(R.id.et_nombre);
        et_Mail = findViewById(R.id.et_mail);
        et_Edad = findViewById(R.id.et_edad);
        et_Genero=findViewById(R.id.et_gen);
        et_Ax_x = findViewById(R.id.m_ax_x);
        et_Ax_y = findViewById(R.id.m_ax_y);
        et_Ax_z = findViewById(R.id.m_ax_z);
        et_Gax_x = findViewById(R.id.gy_ax_x);
        et_Gax_y = findViewById(R.id.gy_ax_y);
        et_Gax_z = findViewById(R.id.gy_ax_z);
        et_Pulso = findViewById(R.id.et_Pulso);
        et_Temp = findViewById(R.id.et_Temp);

        dni = getIntent().getExtras().getString("dni");
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "De vuelta al menu", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent intent = new Intent(MostrarDatos.this,AcercaDe.class);
                //startActivityForResult(intent, 0);
                startActivity(intent);

            }
        });

         MyDB admin = new MyDB(this, "administracion", null,1);

        SQLiteDatabase bd = admin.getWritableDatabase();

        Cursor fila = bd.rawQuery(

                "select nombre, mail,edad, genero, acelx, acely, acelz, gyro_x, gyro_y, gyro_z,pulso, tempe from usuario where dni=" + dni, null);

        if (fila.moveToFirst()) {

            et_Nombre.setText("Nombre: "+fila.getString(0));
            et_Mail.setText("E-mail: "+fila.getString(1));
            et_Edad.setText("Edad: "+fila.getString(2));
            et_Genero.setText("Genero: "+fila.getString(3));
            et_Ax_x.setText("   X\n"+fila.getString(4));
            et_Ax_y.setText("   Y\n"+fila.getString(5));
            et_Ax_z.setText("   Z\n"+fila.getString(6));
            et_Gax_x.setText("   X\n"+fila.getString(7));
            et_Gax_y.setText("   Y\n"+fila.getString(8));
            et_Gax_z.setText("   Z\n"+fila.getString(9));
            et_Pulso.setText(fila.getString(10));
            et_Temp.setText(fila.getString(11));
            //monitor.setText(array);

        } else

            Toast.makeText(this, "No existe ningún usuario con ese dni",

                    Toast.LENGTH_SHORT).show();

        bd.close();

        // ***************BOTON PARA REGRESAR AL menu *******************************
        Button btn_main =  findViewById(R.id.btn_main);
        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), AcercaDe.class);
                //startActivityForResult(intent2, 0);
                startActivity(intent2);
            }
        });




    }

}
