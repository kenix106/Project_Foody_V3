package cl.evilgenius.project_foody_v3;

import android.app.ProgressDialog;
import android.provider.ContactsContract;
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

import cl.evilgenius.project_foody_v3.Model.User;

public class SignUPActivity extends AppCompatActivity {

    EditText phone, pass, name;
    Button regist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        phone = (MaterialEditText) findViewById(R.id.EditTextPhone_UP);
        pass = (MaterialEditText) findViewById(R.id.EditTextPassword_UP);
        name = (MaterialEditText) findViewById(R.id.EditTextName_UP);

        regist = (Button) findViewById(R.id.BTN_SignInRegistrar_signUPActivity);

        //iniciamos FIREBASE
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");

        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (phone.getText().toString().equals("")||
        pass.getText().toString().equals("")||
        name.getText().toString().equals("")){
    Toast.makeText(SignUPActivity.this, "Campos vacios", Toast.LENGTH_SHORT).show();
}else{                //todo:hay que hacer condicionales para los campos vacios
    //ya arreglamos el condicional para los campos vacios del registro

                final ProgressDialog mDialog = new ProgressDialog(SignUPActivity.this);
                mDialog.setMessage("Cargando...");
                mDialog.show();


                table_user.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        //Verifiquemos que el numero de celu exista
                        if (dataSnapshot.child(phone.getText().toString()).exists()) {
                            mDialog.dismiss();

                            Toast.makeText(SignUPActivity.this, "Numero ya existe, Cambiar numero.", Toast.LENGTH_LONG).show();
                        } else {
                            mDialog.dismiss();

                            User user = new User(name.getText().toString(), pass.getText().toString()); //tomamos los valores de los campos para darle valor a la PK (abajo)
                            table_user.child(phone.getText().toString()).setValue(user);//creamos al usuario por su PK dandole el valor de lo de arriba
                            Toast.makeText(SignUPActivity.this, "Resgistro Exitoso!", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }//fin condicionarl de campos vacios
            }// fin onm click listener
        });

    }
}
