package cl.evilgenius.project_foody_v3;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rengwuxian.materialedittext.MaterialEditText;

import cl.evilgenius.project_foody_v3.Common.Common;
import cl.evilgenius.project_foody_v3.Model.User;

public class SignInActivity extends AppCompatActivity {
    EditText fono, pass;
    Button Enter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        fono = (MaterialEditText) findViewById(R.id.EditTextPhone);
        pass = (MaterialEditText) findViewById(R.id.EditTextPassword);

        Enter = (Button) findViewById(R.id.BTN_SignInRegistrar_signInActivity);

        //Iniciamos FIREBASE
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        Enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final ProgressDialog mDialog = new ProgressDialog(SignInActivity.this);
                mDialog.setMessage("Cargando...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {


                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {



                            //Verificamos si el usuario Existe en la base de datos
                            if (dataSnapshot.child(fono.getText().toString()).exists()) { //Primer IF

                                //Buscamos informacion del usuario
                                mDialog.dismiss();
                                User user = dataSnapshot.child(fono.getText().toString()).getValue(User.class);

                                user.setPhone(fono.getText().toString()); //setamos el telefono '3oct'
//30:59
                                if (user.getPass() != null && user.getPass().equals(pass.getText().toString())) {//Segundo IF
                                    mDialog.dismiss();
                                    Toast.makeText(SignInActivity.this, "Logeado Correctamente", Toast.LENGTH_SHORT).show();

                                    Intent Homeintent = new Intent(SignInActivity.this, Home.class);
                                    Common.currentUser = user;
                                    startActivity(Homeintent);
                                    finish();


                                } else {
                                    String passTemp = user.getPass();
                                    mDialog.dismiss();

                                    Toast.makeText(SignInActivity.this, "Campos Incorrectos ", Toast.LENGTH_SHORT).show();
                                }//fin Segundo IF-ELSE


                            }//fin Primer IF

                            else {
                                mDialog.dismiss();
                                Toast.makeText(SignInActivity.this, "Usuario no Existe en la Base de Datos", Toast.LENGTH_SHORT).show();
                            }



                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });

    }
}
