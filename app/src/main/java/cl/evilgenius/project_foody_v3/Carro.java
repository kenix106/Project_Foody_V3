package cl.evilgenius.project_foody_v3;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.evilgenius.project_foody_v3.Common.Common;
import cl.evilgenius.project_foody_v3.Database.Database;
import cl.evilgenius.project_foody_v3.Model.Order;
import cl.evilgenius.project_foody_v3.Model.Pedido;
import cl.evilgenius.project_foody_v3.ViewHolder.CarroAdapter;
import info.hoang8f.widget.FButton;

public class Carro extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference request;

    TextView txtTotalPrecio;
    FButton btnPlace;

    List<Order> cart = new ArrayList<>();
    CarroAdapter adapter;

    String precio, cantidad;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        Log.e("TAG", "onCreate_Carro, working");

        //iniciamos Firebase 03oct
        database = FirebaseDatabase.getInstance();
        request = database.getReference("Pedido");

        recyclerView = (RecyclerView) findViewById(R.id.listCarro);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrecio = (TextView) findViewById(R.id.total);
        btnPlace = (FButton) findViewById(R.id.btnPlaceOrden);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "Click en pedir orden");
                showAlertDialog();
                Toast.makeText(Carro.this, "Pedir orden", Toast.LENGTH_SHORT).show();

            }
        });

        loadListFood();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void showAlertDialog() {
        Log.e("TAG", "comenzando SHOWALERTDIALOG STARTED");

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(Carro.this);

        Log.e("TAG", "Se creo el dialogo");

        alertDialog.setTitle("Un ultimo paso").setMessage("Cual es su direccion");
        //alertDialog.setMessage("Cual es su Direccion: ");

        final EditText edtAdress = new EditText(Carro.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT

        );

        edtAdress.setLayoutParams(lp);
        alertDialog.setView(edtAdress);
        alertDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Log.e("TAG", "Se toma el pedido");
                String status = "0";

                Pedido pedido = new Pedido(Common.currentUser.getPhone(), Common.currentUser.getName(), edtAdress.getText().toString(), txtTotalPrecio.getText().toString(),status, cart);

                //ahora lo registramos en Firebase
                request.child(String.valueOf(edtAdress.getText().toString())).setValue(pedido);

                //se borra toda la data in el cart
                new Database(getBaseContext()).CleanTheCarro();
                Toast.makeText(Carro.this, "Gracias por su preferencia, su pedido ha sido entregado.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDialog.show();
    }

    private void loadListFood() {
        Log.e("TAG", "Carro, methodo: LoadListFood, working y INICIANDO");
        cart = new Database(this).getCarts();
        adapter = new CarroAdapter(cart, this);
        recyclerView.setAdapter(adapter);
        //Order ca = null;

        //hacemos el calculo del total;
        double total = 0;
        for (Order order : cart) {
            // total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
            Log.e("TAG", "precio: " + order.getPrice() + "cantidad: " + order.getQuantity());
            double price1 = Double.parseDouble(order.getPrice());
            double quantity1 = Double.parseDouble(order.getQuantity());
            total += price1 * quantity1;
            Log.e("TAG", "Carro.class working LoadListFood Working and now getting data: price: " + total);


        }
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);

        txtTotalPrecio.setText(fmt.format(total));
    }

}
