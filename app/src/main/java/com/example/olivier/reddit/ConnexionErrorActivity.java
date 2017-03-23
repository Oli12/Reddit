package com.example.olivier.reddit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class ConnexionErrorActivity extends AppCompatActivity implements View.OnClickListener {
    Button connexionButton;
    String sectionsel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion_error);

        sectionsel = getIntent().getStringExtra("SECTION");
        connexionButton = (Button)findViewById(R.id.connexion_error_button);
        connexionButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.putExtra("SECTION", sectionsel);

        startActivity(intent);

    }
}
