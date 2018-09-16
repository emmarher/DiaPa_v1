package com.example.emmarher.diapa_v1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AcercaDe  extends AppCompatActivity{
    private EditText et1, et2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        // campos de layout
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);

        //Dar de alta en la aplicacion


        // Hacemos la consulta por DNI

// ***************BOTON PARA REGRESAR AL ACTIVIYTY 1 *******************************
        Button btn_Main =  findViewById(R.id.btn_Main);
        btn_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (v.getContext(), MainActivity.class);
                startActivityForResult(intent2, 0);
            }
        });
    }
 // ******************  A L T A   E N   B A S E    DE   D A T O S *****************
    public void alta(View view){
        MyDB admin = new MyDB(this, "administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et1.getText().toString(); // OBTENEMOS EL VALOR EN EL EDITEXT et1 y se guarda en la variable nombre como string
        String nombre = et2.getText().toString(); // OBTENEMOS EL VALOR EN EL EDITEXT et1 y se guarda en la variable nombre como string


        ContentValues registro = new ContentValues(); // hacemos que en el objeto registro se guarden los valores con pt
        registro.put("SujetoName",nombre);
        registro.put("SujetoID",dni);

        // los inserto en la tabla, que antes guardamos en el objeto registro, y lo pasamos con ibsert a la bd
        bd.insert("Sujeto",null,registro);
        bd.close();

    }

    // <------------- Consulta de base de datos --------------------->

}
