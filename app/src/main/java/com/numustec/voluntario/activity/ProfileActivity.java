package com.numustec.voluntario.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.numustec.voluntario.R;
import com.numustec.voluntario.auth.AuthEmailPassword;

public class ProfileActivity extends AppCompatActivity {

    EditText name,email;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    LinearLayout texts;
    CardView loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_profile);
        name = (EditText)findViewById(R.id.etName);
        email = (EditText)findViewById(R.id.etEmail);
        texts = (LinearLayout)findViewById(R.id.cardContent);
        loading = (CardView)findViewById(R.id.inLoading);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        getUser();
    }
    public void setLoading(boolean x){
        if(x == true){
            loading.setVisibility(View.VISIBLE);
            texts.setVisibility(View.GONE);
        }
        else{
            loading.setVisibility(View.GONE);
            texts.setVisibility(View.VISIBLE);
        }
    }
    public void getUser(){
        setLoading(true);
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
                    Log.e("Ler Perfil","Uid => "+currentUser.getUid());
                    db.collection("usuarios")
                            .whereEqualTo("uuid_login",currentUser.getUid())
                            .get()
                            .addOnSuccessListener(command -> {
                                DocumentSnapshot user = command.getDocuments()
                                        .get(0);
                                name.setText(user.get("name").toString());
                                email.setText(currentUser.getEmail());
                            })
                            .addOnFailureListener(command -> {
                                Log.e("ler perfil",command.getLocalizedMessage());
                            })
                            .addOnCompleteListener(command -> {
                                setLoading(false);
                            });
        }
        else{
            Intent intent = new Intent(this, AuthEmailPassword.class);
            startActivity(intent);
        }
    }
}