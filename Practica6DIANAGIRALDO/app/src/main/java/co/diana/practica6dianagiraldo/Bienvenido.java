package co.diana.practica6dianagiraldo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class Bienvenido extends AppCompatActivity {

    private TextView textViewBienvenido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenido);

        textViewBienvenido = findViewById(R.id.textViewBienvenido);
    }
}