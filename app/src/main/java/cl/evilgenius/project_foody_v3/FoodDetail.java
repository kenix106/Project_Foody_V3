package cl.evilgenius.project_foody_v3;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import cl.evilgenius.project_foody_v3.Database.Database;
import cl.evilgenius.project_foody_v3.Model.Food;
import cl.evilgenius.project_foody_v3.Model.Order;


public class FoodDetail extends AppCompatActivity {

TextView food_name, food_price, food_description;
ImageView food_image;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton btnCart;
ElegantNumberButton numberButton;

String foodId="";

FirebaseDatabase database;
DatabaseReference foods;

    Food currentPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        int a = 10;

        //iniciamos FIREBASE
        database = FirebaseDatabase.getInstance();
        foods=database.getReference("Food");

        //iniciamos la vista
        numberButton = (ElegantNumberButton)findViewById(R.id.number_btn);
        btnCart =(FloatingActionButton)findViewById(R.id.btnCarro);

        //boton de carro (3 oct)
        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCarro(new Order(
                        foodId,
                currentPlato.getName(),
                numberButton.getNumber(),
                currentPlato.getPrice(),
                currentPlato.getDiscount(),
                      foodId
                        //hay que agregar un contador para saber el numero de la mesa

                ));
                String a,b,c,d,e;
                a= currentPlato.getName();
                b=numberButton.getNumber();
                c=currentPlato.getPrice();
                d=currentPlato.getDiscount();
                e=foodId;
                String texto= "nombre : "+a+"| cantidad: "+b+"| | precio: "+c+"| descuento: "+d+"| ID: "+e;
                Toast.makeText(FoodDetail.this, "Producto Agregado al Carro   "+texto, Toast.LENGTH_SHORT).show();
            }
        });//fin boton de carro (3 oct)


        food_description = (TextView) findViewById(R.id.food_descripcion);
        food_image = (ImageView) findViewById(R.id.img_food);
        food_name = (TextView) findViewById(R.id.food_name_detail);
        food_price =(TextView) findViewById(R.id.food_price_detail);

        collapsingToolbarLayout = (CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppBar);

        //Ahora traemos el ID del objeto seleccionado por el Intent

        if (getIntent() != null){
            foodId= getIntent().getStringExtra("FoodId");

        }
        if(!foodId.isEmpty()){
            getDetailFood(foodId);
        }

    }



    private void getDetailFood(String foodId){
        foods.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                currentPlato = dataSnapshot.getValue(Food.class);

                Picasso.get().load(currentPlato.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(currentPlato.getName());
                food_price.setText(currentPlato.getPrice());
                food_name.setText(currentPlato.getName());
                food_description.setText(currentPlato.getDescription());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
