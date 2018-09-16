package com.example.emmarher.diapa_v1;

import android.app.Person;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.microsoft.band.BandClient;
import com.microsoft.band.BandClientManager;
import com.microsoft.band.ConnectionState;
import com.microsoft.band.BandInfo;
import com.microsoft.band.BandException;
import com.microsoft.band.BandPendingResult;
import com.microsoft.band.InvalidBandVersionException;
import com.microsoft.band.UserConsent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandAccelerometerEvent;
import com.microsoft.band.sensors.BandHeartRateEvent;
import com.microsoft.band.sensors.BandAccelerometerEventListener;
import com.microsoft.band.sensors.BandHeartRateEventListener;
import com.microsoft.band.sensors.BandRRIntervalEvent;
import com.microsoft.band.sensors.BandRRIntervalEventListener;
import com.microsoft.band.sensors.BandSkinTemperatureEvent;
import com.microsoft.band.sensors.BandSkinTemperatureEventListener;
import com.microsoft.band.sensors.BandGyroscopeEvent;
import com.microsoft.band.sensors.BandGyroscopeEventListener;
import com.microsoft.band.sensors.BandUVEvent;
import com.microsoft.band.sensors.BandUVEventListener;
import com.microsoft.band.sensors.HeartRateConsentListener;
import com.microsoft.band.sensors.SampleRate;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

import java.io.PrintStream;
import java.net.Socket;

public class MainActivity extends AppCompatActivity {

    BandInfo[] pairedBands;
    BandClient bandClient;
    TextView BandVersion;
    TextView BandHR;
    TextView BandTemp;
    TextView BandFVersion;
    TextView BandAccelX, BandAccelY, BandAccelZ;
    TextView BandGyroX, BandGyroY, BandGyroZ;
    TextView BandUV, BandRRInterval;
    Button mSubmitButton, btnAcercaDe;
    Spinner spinner;

    // Uso para el ejemlo de mostrar datos de Firebase DB
    TextView lblCielo, lblTemperatura, lblHumedad;
    Button btnEliminarListner;
    EditText txtNombre;


    // [START declare_database_ref]
    //Here we create object type DatabaseReference, an this save the reference to database
    private DatabaseReference dbCielo, dbPrediccion, mDatabase;
    private ValueEventListener eventListener;

    private static final String TAGLOG = "firebase-db";
// [END declare_database_ref]

    //DataSnapshot dataSnapshot = new DataSnapshot();
    // Write a message to the database
    //FirebaseDatabase database = FirebaseDatabase.getInstance();
    //DatabaseReference myRef = database.getReference("message");
   // DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
   // DatabaseReference mensajeRef = ref.child("Mensajito");

// S O C K E T .... como cree la clase en un archivo aparte no son necesarios aqui
    private Socket socket;
    private PrintStream out;
    private Boolean connected;



    //myRef.setValue("Hello, World!");
    //mensajeRef.setValue("cambio");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.diagpark);// ScreenSplash
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pairedBands = BandClientManager.getInstance().getPairedBands();
        bandClient = BandClientManager.getInstance().create(getApplicationContext(), pairedBands[0]);
// <------------------ S E N S O R E S  /   X M L ---------------------->
        BandVersion = findViewById(R.id.BandVer);
        BandHR = findViewById(R.id.BandHR);
        BandTemp = findViewById(R.id.BandTemp);
        BandFVersion = findViewById(R.id.BandFVer);
        BandAccelX = findViewById(R.id.BandAccelX);
        BandAccelY = findViewById(R.id.BandAccelY);
        BandAccelZ = findViewById(R.id.BandAccelZ);
        BandGyroX = findViewById(R.id.BandGyroX);
        BandGyroY = findViewById(R.id.BandGyroY);
        BandGyroZ = findViewById(R.id.BandGyroZ);
        BandUV = findViewById(R.id.BandUV);
        BandRRInterval = findViewById(R.id.BandRRInterval);
// <-------------------- F I R E B A S E  /  X M L ------------------>
        mSubmitButton = findViewById(R.id.modif);
        lblCielo = findViewById(R.id.lblCielo);
        lblHumedad = findViewById(R.id.lblHumedad);
        lblTemperatura = findViewById(R.id.lblHumedad);
        btnEliminarListner = findViewById(R.id.btnEliminarListener);
        txtNombre = findViewById(R.id.txtNombre);

        // ************* Button acerca de ******************
        btnAcercaDe =  findViewById(R.id.acercade);

// <------------------- S O C K E T ----------------------->

SocketThread st = new SocketThread();

        //SPINNER  <----------------------->
        Spinner spinner = findViewById(R.id.spinner);
        String[] genres = {"Hombre", "Mujer","Indefinido","LGTB"};
        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, genres));



        // <------------------- F I R E B A S E  -  I N I C I A L I Z A R---------------------------->
        mDatabase = FirebaseDatabase.getInstance().getReference().child("prediccion-hoy");
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //Opción 1
                lblCielo.setText(dataSnapshot.child("cielo").getValue().toString());
                lblTemperatura.setText(dataSnapshot.child("temperatura").getValue().toString());
                lblHumedad.setText(dataSnapshot.child("humedad").getValue().toString());

                //Opcion 2
               /* Prediccion pred = dataSnapshot.getValue(Prediccion.class);
                lblCielo.setText(pred.getCielo());
                lblTemperatura.setText(pred.getTemperatura() + "ºC");
                lblHumedad.setText(pred.getHumedad() + "%");*/

                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error", databaseError.toException());

            }
        };

        mDatabase.addValueEventListener(eventListener);
        // ********** BTN ACERCA DE *************************
        btnAcercaDe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),AcercaDe.class);
            }
        });
//<----------------- B T N  -  E L I M I N A R  -  L I S T E N E R ----------------
        btnEliminarListner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDatabase.removeEventListener(eventListener);
            }
        });

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //String name =
                txtNombre.getText().toString();
                //Person person = new Person();
                //person.setName(txtNombre);
            }
        });
        // [START initialize_database_ref]
        //mDatabase = FirebaseDatabase.getInstance().getReference();
// [END initialize_database_ref]

        //NOTE: The BandClient.Connect method must be called from background thread. An exception
        //will be thrown if called from the UI thread

        bandConsent(); //new SDK requires consent to read from HR sensor... hace esto para que en cuanto arranca la actividad, de permisos en automatico... supongo

        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //submitPost();
            }
        });

        //<------------------- Socket --------->
        new Thread(new SocketThread()).start(); //Inicializan el hilo de socket.


    }
// <--------------------- CONSENTIMIENTO -----> PARA EL PULSO, en automatico aceptar
    private HeartRateConsentListener mHeartRateConsentListener = new HeartRateConsentListener() {
        @Override
        public void userAccepted(boolean b) {
            //handle user´s heart rate consent decision
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        helloMSBand();
                    }catch (BandException e){
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    };
// <-------------------- UNA VEZ ACEPTADO EL CONSENTIMIENTO, EJECUTA LOS SENSORES ---------------->
    public boolean bandConsent(){
        if(bandClient.getSensorManager().getCurrentHeartRateConsent() != UserConsent.GRANTED){
            bandClient.getSensorManager().requestHeartRateConsent(this,mHeartRateConsentListener);
            return false;
        }else {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try{
                        helloMSBand();
                        // prueba socket
                      // if(connected == true){
                        //   String s = String.format("{\"x\": %f, \"y\": %f, \"z\": %f}",
                       //            event.values[0], event.values[1], event.values[2]);]

                    }catch (BandException e){
                        e.printStackTrace();
                    }
                }
            }).start();
            return true;
        }
    }
// <--------------- METODO EN EL CUAL SE CONECTA A LOS SENSORES A LA INTERFAZ ---------------->
    public void helloMSBand() throws BandException {
        BandPendingResult<ConnectionState> pendingResult = bandClient.connect();

        try{
            ConnectionState result = pendingResult.await();
            if(result == ConnectionState.CONNECTED){
                try{
                    BandPendingResult<String> pendingVersion = bandClient.getFirmwareVersion();
                    final String fwVersion = pendingVersion.await();
                    pendingVersion = bandClient.getHardwareVersion();
                    final String hwVersion = pendingVersion.await();
                    BandVersion.post(new Runnable() {
                        @Override
                        public void run() {
                            BandVersion.setText(hwVersion);
                        }
                    });
                    BandVersion.post(new Runnable() {
                        @Override
                        public void run() {
                            BandFVersion.setText(fwVersion);
                        }
                    });
                }catch(InterruptedException ex){
                    //catch
                }catch (BandException ex){
                    //catch
                }

                BandHeartRateEventListener heartRateListener = new BandHeartRateEventListener() {
                    @Override
                    public void onBandHeartRateChanged(BandHeartRateEvent bandHeartRateEvent) {
                        final String HR = String.valueOf(bandHeartRateEvent.getHeartRate());
                        BandHR.post(new Runnable() {
                            @Override
                            public void run() {
                                BandHR.setText(HR);
                            }
                        });
                    }
                };

                BandSkinTemperatureEventListener skinTemperatureEventListener = new BandSkinTemperatureEventListener() {
                    @Override
                    public void onBandSkinTemperatureChanged(BandSkinTemperatureEvent bandSkinTemperatureEvent) {
                        final String TempF = String.valueOf(bandSkinTemperatureEvent.getTemperature());
                        BandTemp.post(new Runnable() {
                            @Override
                            public void run() {
                                BandTemp.setText(TempF);
                            }
                        });
                    }
                };

                BandAccelerometerEventListener accelerometerEventListener = new BandAccelerometerEventListener() {
                    @Override
                    public void onBandAccelerometerChanged(BandAccelerometerEvent bandAccelerometerEvent) {
                        final String AccX = String.format("%.2f",bandAccelerometerEvent.getAccelerationX());
                        final String AccY = String.format("%.2f",bandAccelerometerEvent.getAccelerationY());
                        final String AccZ = String.format("%.2f", bandAccelerometerEvent.getAccelerationZ());
                        //socket
                        if(connected == true){
                              String s = String.format("{\"x\": %.2f, \"y\": %.2f, \"z\": %.2f}",
                                        AccX, AccY, AccZ);}
                        BandAccelX.post(new Runnable() {
                            @Override
                            public void run() {
                                BandAccelX.setText("X: " + AccX);
                            }
                        });
                        BandAccelY.post(new Runnable() {
                            @Override
                            public void run() {
                                BandAccelY.setText("Y: " + AccY );
                            }
                        });
                        BandAccelZ.post(new Runnable() {
                            @Override
                            public void run() {
                                BandAccelZ.setText("Z: " + AccZ);
                            }
                        });
                    }
                };

                BandGyroscopeEventListener gyroscopeEventListener = new BandGyroscopeEventListener() {
                    @Override
                    public void onBandGyroscopeChanged(BandGyroscopeEvent bandGyroscopeEvent) {
                        final String GyrX = String.format("%.2f", bandGyroscopeEvent.getAngularVelocityX());
                        final String GyrY = String.format("%.2f", bandGyroscopeEvent.getAngularVelocityY());
                        final String GyrZ = String.format("%.2f", bandGyroscopeEvent.getAngularVelocityZ());
                        BandGyroX.post(new Runnable() {
                            @Override
                            public void run() {
                                BandGyroX.setText("X: " + GyrX );
                            }
                        });
                        BandGyroY.post(new Runnable() {
                            @Override
                            public void run() {
                                BandGyroY.setText("Y: " + GyrY );
                            }
                        });
                        BandGyroZ.post(new Runnable() {
                            @Override
                            public void run() {
                                BandGyroZ.setText("Z: " + GyrZ );
                            }
                        });
                    }
                };

                BandUVEventListener uvEventListener = new BandUVEventListener() {
                    @Override
                    public void onBandUVChanged(BandUVEvent bandUVEvent) {
                        final String UVRead = String.valueOf(bandUVEvent.getUVIndexLevel());
                        BandUV.post(new Runnable() {
                            @Override
                            public void run() {
                                BandUV.setText(UVRead);
                            }
                        });
                    }
                };

                BandRRIntervalEventListener rrIntervalEventListener = new BandRRIntervalEventListener() {
                    @Override
                    public void onBandRRIntervalChanged(BandRRIntervalEvent bandRRIntervalEvent) {
                        final String RRInterval = String.valueOf(bandRRIntervalEvent.getInterval());
                        BandRRInterval.post(new Runnable() {
                            @Override
                            public void run() {
                                BandRRInterval.setText(RRInterval);
                            }
                        });

                    }
                };

                try{
                    bandClient.getSensorManager().registerUVEventListener(uvEventListener);
                }catch(BandException ex){//Catch
                }

                try{
                    bandClient.getSensorManager().registerAccelerometerEventListener(accelerometerEventListener, SampleRate.MS128);
                }catch(BandException ex) {
                    //Catch
                }

                try{
                    bandClient.getSensorManager().registerGyroscopeEventListener(gyroscopeEventListener, SampleRate.MS128);
                }catch(BandException ex){
                    //Catch
                }

                try{
                    bandClient.getSensorManager().registerHeartRateEventListener(heartRateListener);
                }catch (BandException ex){
                    //Catch
                }

                try{
                    bandClient.getSensorManager().registerSkinTemperatureEventListener(skinTemperatureEventListener);
                }catch (BandException ex){
                    //Catch
                }

                try{
                    bandClient.getSensorManager().registerRRIntervalEventListener(rrIntervalEventListener);
                }catch (BandException ex){
                    //catch
                } catch (InvalidBandVersionException e) {
                    e.printStackTrace();
                }
            }else{
                BandVersion.setText(":( La Conexión Fallo");
            }
        }
        catch(InterruptedException ex){
            //Catch
        }
        catch (BandException ex){
            //Catch
        }

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        //Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        //Handle action bar item clicks here. The action bar will
        //automatically handle clicks on the Home/Up button, so long
        //as you specify a parent activity in AndroidManifest.xml
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.actions_settings){
        // return true;
        // }

        return super.onOptionsItemSelected(item);
    }

    // Read from the database
//myRef.addValueEventListener(new ValueEventListener() {
     //   @Override
     //   public void onDataChange(DataSnapshot dataSnapshot) {
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
      //      String value = dataSnapshot.getValue(String.class);
     //       Log.d(TAG, "Value is: " + value);
       // }

     //   @Override
      //  public void onCancelled(DatabaseError error) {
            // Failed to read value
       //     Log.w(TAG, "Failed to read value.", error.toException());
      //  }
   // });
/*    @Override
    protected void onStart(){
        super.onStart();
        mensajeRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String Value = dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void submitPost(){

    }*/
private void ejecutaCliente(){
    try{
        socket = new Socket ("mi direccion de ip", 5001); // Inicializa el constructor del socket
        out = new PrintStream(socket.getOutputStream(),true);
        connected = true;
    } catch(java.io.IOException e){
        e.printStackTrace();
    }
}
}
