package com.numustec.voluntario.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.DateTime;
import com.numustec.voluntario.R;
import com.numustec.voluntario.entity.Post;
import com.numustec.voluntario.fragment.YouPostFragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PostNewEditActivity extends AppCompatActivity {
    CardView loading;
    EditText title,descript,address,date,time;
    FirebaseFirestore db;
    FirebaseAuth mauth;
    LinearLayout post;
    Button button;
    Timestamp timestamp;
    Post item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_post_new_edit);
        Intent intent = getIntent();
        loading = (CardView)findViewById(R.id.inLoading);
        post = (LinearLayout)findViewById(R.id.layoutPost);
        loading.setVisibility(View.GONE);
        title = (EditText)findViewById(R.id.etTitle);
        descript = (EditText)findViewById(R.id.etDescript);
        address = (EditText)findViewById(R.id.etAddress);
        date = (EditText)findViewById(R.id.etDate);
        time = (EditText)findViewById(R.id.etTime);
        button = (Button)findViewById(R.id.btn_save);
        item = new Post();
        if(intent.hasExtra("title")){
            item.title = intent.getStringExtra("title");
            item.descript = intent.getStringExtra("descript");
            item.address = intent.getStringExtra("address");
            item.author = intent.getStringExtra("author");
            item.id = intent.getStringExtra("id");
            item.datetime = intent.getLongExtra("date",0);
            title.setText(intent.getStringExtra("title"));
            descript.setText(intent.getStringExtra("descript"));
            address.setText(intent.getStringExtra("address"));
            String date1 = intent.getStringExtra("datetime");
            Log.e("PostNew","Date => "+date1);
            if(date1 != null ){
                date.setText(date1.split(" ")[0]);
                time.setText(date1.split(" ")[1]);
            }
            button.setOnClickListener(v -> {
                edit(v);
            });
        }
        else{
            button.setOnClickListener(v -> {
                save(v);
            });
        }
        db = FirebaseFirestore.getInstance();
        mauth = FirebaseAuth.getInstance();
        //init();
    }

    public void init(){
        title.setText("Monitor de Atividades em Abrigo de Animais (Manhã)");
        descript.setText("Monitor de Atividades em Abrigo de Animais (Manhã)\n" +
                "\n" +
                "Organização: Pata Amiga\n" +
                "\n" +
                "Descrição:\n" +
                "\n" +
                "Auxiliar na monitoração das atividades dos animais no abrigo, como brincadeiras, passeios e alimentação.\n" +
                "Interagir com os animais, proporcionando carinho e atenção.\n" +
                "Auxiliar na limpeza e organização do abrigo.\n" +
                "Diferencial: Oportunidade de participar de workshops sobre comportamento animal e bem-estar animal.\n" +
                "Requisitos:\n" +
                "\n" +
                "Amor por animais.\n" +
                "Responsabilidade e compromisso.\n" +
                "Boa comunicação e capacidade de trabalhar em equipe.\n" +
                "Disponibilidade de tempo no turno da manhã.\n" +
                "Inscrição: Enviar currículo e carta de apresentação para pataamiga@email.com");
            address.setText("Abrigo Pata Amiga, Rua das Flores, 123 - Centro");
    }
    public boolean valid_erro(){
        if(title.getText().length() > 6 && descript.getText().length() > 20 ){
            return true;
        }
        if(title.getText().length() < 6){
            title.setError(getString(R.string.erro_title));
        }
        if(descript.getText().length() < 20){
            descript.setError(getString(R.string.erro_descript));
        }
        return false;
    }
    public void setLoading(boolean x){
        if(x == true){
            loading.setVisibility(View.VISIBLE);
            post.setVisibility(View.GONE);
        }
        else{
            loading.setVisibility(View.GONE);
            post.setVisibility(View.VISIBLE);
        }
    }

    public void save(View view){
        if(valid_erro()){
            setLoading(true);
            Map<String, Object> post = process_date();
            db.collection("post")
                    .add(post)
                    .addOnFailureListener(command -> {
                        Log.e("Save Post","Erro => "+command.getLocalizedMessage());
                    })
                    .addOnSuccessListener(command -> {
                        Toast.makeText(getApplication(),getString(R.string.save_ok),Toast.LENGTH_LONG).show();
                    })
                    .addOnCompleteListener(command -> {
                        setLoading(false);
                        Activity next = YouPostFragment.newInstance().getActivity();
                        Intent intent = new Intent(this, next.getClass());
                        startActivity(intent);
                    });
        }
    }
    public void edit(View view){
        if(valid_erro()){
            setLoading(true);
            Map<String, Object> post = process_date();
            if(post != null){
                db.collection("post")
                        .document(item.id)
                        .update(post)
                        .addOnFailureListener(command -> {
                            Log.e("Save Post","Erro => "+command.getLocalizedMessage());
                        })
                        .addOnSuccessListener(command -> {
                            Toast.makeText(getApplication(),getString(R.string.save_ok),Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(this, YouPostFragment.class);
                            startActivity(intent);
                        })
                        .addOnCompleteListener(command -> {
                            setLoading(false);
                        });
            }
        }
    }
    public Map<String, Object> process_date(){
        try{
                String title    = this.title.getText().toString();
                String descript = this.descript.getText().toString();
                String address  = this.address.getText().toString();
                String date     = this.date.getText().toString()+" "+this.time.getText();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                Date parsedDate  = dateFormat.parse(date);
                Timestamp timestamp = new Timestamp(parsedDate);
                FirebaseUser user = mauth.getCurrentUser();
                Map<String, Object> post = new HashMap<>();
                post.put("title", title);
                post.put("descript",descript);
                post.put("local",address);
                post.put("uuid_login", user.getUid());
                post.put("datetime",timestamp);
            return post;
        } catch (ParseException e) {
            return null;
        }
    }

    public void onDate(View v){
        Calendar c = Calendar.getInstance();
        c.setTime(item.getDateI());
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                String formattedDate = String.format("%02d/%02d/%d", day,month + 1, year);
                date.setText(formattedDate);
            }
        }, year, month, day);
        datePickerDialog.show();
    }

    public void onTime(View v){
        Calendar c = Calendar.getInstance();
        c.setTime(item.getDateI());
        int hour = c.get(Calendar.HOUR);
        int minute = c.get(Calendar.MINUTE);
        TimePickerDialog timerPickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String formattedDate = String.format("%02d:%02d",hourOfDay,minute);
                String text = time.getText().toString();
                time.setText(text+" "+formattedDate);
            }
        },hour,minute,true);
        timerPickerDialog.show();
    }

}