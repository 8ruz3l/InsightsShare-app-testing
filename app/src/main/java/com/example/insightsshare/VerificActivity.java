package com.example.insightsshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class VerificActivity extends AppCompatActivity {

    //Variablen müssten in registryactivity eigentlich gleich bei der DB angelegt werden, wegen programmabstürzen provisorisch hier
    public static String username;
    public static String email;
    public static String password;

    Button changeDataButton, verifyButton;
    ProgressBar progressBar;

    FirebaseAuth fAuth;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verific);

        progressBar = findViewById(R.id.progressBar);
        fAuth = FirebaseAuth.getInstance();

        verifyButton = (Button) findViewById(R.id.verifyBtn);
        verifyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                    FirebaseAuth.getInstance().getCurrentUser().reload();
                    if (FirebaseAuth.getInstance().getCurrentUser().isEmailVerified()) {
                        Toast.makeText(VerificActivity.this, "Email Verified", Toast.LENGTH_SHORT).show();

                        Toast.makeText(VerificActivity.this, "User Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), NavigationActivity.class));

                        rootNode = FirebaseDatabase.getInstance("https://insightsshare-1e407-default-rtdb.europe-west1.firebasedatabase.app/");
                        reference = rootNode.getReference("User");

                        UserClass userInfo = new UserClass(username, email);

                        reference.child(fAuth.getCurrentUser().getUid()).setValue(userInfo);
                    } else {
                        Toast.makeText(VerificActivity.this, "Email not verified yet!", Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(VerificActivity.this, "Too fast!", Toast.LENGTH_SHORT).show();
                }

            }
        });

        changeDataButton = (Button) findViewById(R.id.verificationButton);
        changeDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user.delete();
                fAuth.signOut();
                Intent i = new Intent(VerificActivity.this, RegistrActivity.class);
                startActivity(i);
            }
        });
    }
}