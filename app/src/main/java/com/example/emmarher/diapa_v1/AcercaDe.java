package com.example.emmarher.diapa_v1;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AcercaDe  extends AppCompatActivity{
    private EditText et_Dni, et_Name, et_Mail;
    private Spinner spinner_gen, spinner_edad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acercade);

        //SPINNER  <----------------------->
         spinner_gen = findViewById(R.id.spinner_genero);
        String[] s_genres = {"Hombre", "Mujer","Otro"};
        spinner_gen.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, s_genres));
        spinner_edad = findViewById(R.id.spinner_edad);
        String [] s_edad = {"20","21","21","22","23","24","25","26","26","27","28","29","30","31","32","33","34","35","36","37","38","39","40",
                "41","42","43","44","45","46","47","48","49","50","51","52","53","54","55","56","57","58","59","60"
                ,"61","62","63","64","65","66","67","68","69","70","71","72","73","74","75","76","77","78","79","80"
                ,"81","82","83","84","85","86","87","88","89","90","91","92","93","94","95","96","97","98","99","100"};
        spinner_edad.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, s_edad));

        // campos de layout
        et_Dni = findViewById(R.id.et_dni);
        et_Name = findViewById(R.id.et_name);
        et_Mail = findViewById(R.id.et_email);

        //Dar de alta en la aplicacion


        // Hacemos la consulta por DNI

// ***************BOTON PARA REGRESAR AL ACTIVIYTY 1 *******************************
        Button btn_Main =  findViewById(R.id.btn_Main);
        btn_Main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dni = et_Dni.getText().toString();
                String nombre = et_Name.getText().toString();
                String mail = et_Mail.getText().toString();
                String genero  = spinner_gen.getSelectedItem().toString();
                String edad =  spinner_edad.getSelectedItem().toString();


                Intent intent2 = new Intent (v.getContext(), MainActivity.class);
                //startActivityForResult(intent2, 0);
                intent2.putExtra("usuario", nombre);
                intent2.putExtra("dni", dni);
                intent2.putExtra("email", mail);
                intent2.putExtra("genero",genero);
                intent2.putExtra("edad",edad);
                startActivity(intent2);
            }
        });

        // ***************BOTON PARA MOSTRAR DATOS *******************************
        Button btn_mostrar_datos = findViewById(R.id.btn_mostrar_datos);
        btn_mostrar_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String dni = et_Dni.getText().toString();
                Intent intent = new Intent(view.getContext(), MostrarDatos.class);
                intent.putExtra("dni",dni);
                startActivity(intent);
            }
        });
    }
 // ******************  A L T A   E N   B A S E    DE   D A T O S *****************
    public void alta(View view){
        /*MyDB admin = new MyDB(this, "administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et_Dni.getText().toString(); // OBTENEMOS EL VALOR EN EL EDITEXT et1 y se guarda en la variable Dni como string
        String nombre = et_Name.getText().toString(); // OBTENEMOS EL VALOR EN EL EDITEXT et1 y se guarda en la variable nombre como string
        String mail = et_Mail.getText().toString();
        String genero  = spinner_gen.getSelectedItem().toString();
        int edad = Integer.valueOf((Integer) spinner_edad.getSelectedItem());


        ContentValues registro = new ContentValues(); // hacemos que en el objeto registro se guarden los valores con pt
        registro.put("SujetoName",nombre);
        registro.put("SujetoID",dni);
        registro.put("SujetoMail",mail);
        registro.put("SujetoEdad",edad);

        // los inserto en la tabla, que antes guardamos en el objeto registro, y lo pasamos con ibsert a la bd
        bd.insert("Sujeto",null,registro);
        bd.close();
        //ponemos los campos en vacío
        et_Name.setText(""); et_Dni.setText(""); et_Mail.setText("");
        Toast.makeText(this, "Datos del usuario cargados", Toast.LENGTH_SHORT).show();
*/
    }

    // <------------- Consulta de base de datos --------------------->
    public void consulta(View v) {
        MyDB admin = new MyDB(this, "administracion", null,1);
        SQLiteDatabase bd = admin.getWritableDatabase();
        String dni = et_Dni.getText().toString();
        Cursor fila = bd.rawQuery("select SujetoName, SujetoMail,SujetoEdad from Sujeto where SujetoID="+dni,null);

        if(fila.moveToFirst()){
            et_Name.setText(fila.getString(0));
            et_Mail.setText(fila.getString(1));
           // spinner_edad.setSelected(fila.getDouble(2));
        }else
            Toast.makeText(this, "No existe ningún usuario con ese dni",

                    Toast.LENGTH_SHORT).show();

        bd.close();

    }
}
