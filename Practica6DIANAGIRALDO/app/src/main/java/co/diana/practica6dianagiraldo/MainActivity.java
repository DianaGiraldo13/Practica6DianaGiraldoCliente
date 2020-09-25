package co.diana.practica6dianagiraldo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class MainActivity extends AppCompatActivity {

    private EditText editTextUsuario;
    private EditText editTextContraseña;
    private Button buttonIngresar;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public void pass(){
        Intent i = new Intent(this,Bienvenido.class);
        startActivity(i);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextUsuario = findViewById(R.id.editTextUsuario);
        editTextContraseña = findViewById(R.id.editTextContraseña);
        buttonIngresar = findViewById(R.id.buttonIngresar);

        buttonIngresar.setOnClickListener(
                (v)->{
                    initClient();

                }
        );

    }

    public void initClient() {

        new Thread(
                ()->{

                    try {

                        //2.Enviar solicitud de conexion
                        socket = new Socket("192.168.1.15",5000);
                        //3.Cliente y Server Conectados
                        System.out.println("Conectados");

                        InputStream is = socket.getInputStream();
                        InputStreamReader isr = new InputStreamReader(is);
                        reader = new BufferedReader(isr);

                        OutputStream os = socket.getOutputStream();
                        OutputStreamWriter osw = new OutputStreamWriter(os);
                        writer = new BufferedWriter(osw);
                        while(true){
                            System.out.println("Esperando...");
                            String usuario=editTextUsuario.getText().toString();
                            String contra=editTextContraseña.getText().toString();
                            Usuario u = new Usuario(usuario,contra);
                            Gson gson = new Gson();
                            String json = gson.toJson(u);
                            sendMessage(json);
                            String respuesta = reader.readLine();
                            if(respuesta.equals("Password correcta")){
                                pass();
                                break;
                            }
                            else{
                                Toast.makeText(this,"Contraseña incorrecta",Toast.LENGTH_LONG).show();
                            }
                        }

                    } catch (UnknownHostException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

        ).start();

    }

    public void sendMessage(String msg) {

        new Thread(
                ()->{

                    try {

                        writer.write(msg+"\n");
                        writer.flush();

                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }


        ).start();

    }
}