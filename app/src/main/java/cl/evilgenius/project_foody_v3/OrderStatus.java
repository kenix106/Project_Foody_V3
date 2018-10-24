package cl.evilgenius.project_foody_v3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import cl.evilgenius.project_foody_v3.Common.Common;
import cl.evilgenius.project_foody_v3.Database.Database;
import cl.evilgenius.project_foody_v3.Model.Order;
import cl.evilgenius.project_foody_v3.Model.Pedido;
import cl.evilgenius.project_foody_v3.ViewHolder.OrderViewHolder;

public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter <Pedido, OrderViewHolder> adapter;

    FirebaseDatabase database;
    DatabaseReference requests;

    String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

      //invocamos a firebaseLORD!!!! xD
        database = FirebaseDatabase.getInstance();
        requests= database.getReference("Pedido");

        recyclerView = (RecyclerView) findViewById(R.id.listOrders);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //loadOrders(Common.currentUser.getPhone());
        String phone = Common.currentUser.getPhone();
        if (phone.isEmpty()){
            Intent intent = new Intent(OrderStatus.this, Home.class);
            Toast.makeText(this, "Usuario no reconosido", Toast.LENGTH_SHORT).show();
            startActivity(intent);
        }else{
            loadOrders(phone);
            Log.e("TAG", "Startin method out side LOAD_ORDERS as "+ Common.currentUser.getPhone());//telefono de la persona logeada.
        }




    }


   private void loadOrders (String phone){
        Log.e("TAG", "Starting loadOrders from inside with phone: "+phone);
      // Query query=FirebaseDatabase.getInstance().getReference("Pedido")
       //        .orderByChild("phone").equalTo(phone);
       Query query = requests.orderByChild("phone").equalTo(phone);
       Log.e("TAG", "Query writen");

       FirebaseRecyclerOptions<Pedido> options = new FirebaseRecyclerOptions
               .Builder<Pedido>().setQuery(query,Pedido.class).build();
       Log.e("TAG", "options builded");


       Log.e("TAG", "Starting Adapter");
       adapter = new FirebaseRecyclerAdapter<Pedido, OrderViewHolder>(options) {


           @NonNull
           @Override //identificamos el problema q cuando parte onCreateViewHolder se cae el sistema
           public OrderViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
               Log.e("TAG", "onCreateViewHolder, in ORDERStatus Starting AKI SE CAE EL SISTEMA!");

               View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.order_layout, viewGroup,false);
               return new OrderViewHolder(view);
           }



           @Override
           protected void onBindViewHolder(@NonNull OrderViewHolder holder, int position, @NonNull Pedido model) {

               Log.e("TAG", "onBINViewHolder, in ORDERStatus Starting");
               Toast.makeText(OrderStatus.this, "Comenzando bindView: " + model.getNombre(), Toast.LENGTH_SHORT).show();

               holder.txtOrderId.setText(adapter.getRef(position).getKey());
                     Log.e("TAG","getID: "+adapter.getRef(position).getKey());
               holder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                      Log.e("TAG","getStatus: "+model.getStatus());
               holder.txtOrderAdddress.setText(model.getAddress());
                       Log.e("TAG","getADRESS: "+model.getAddress());
               holder.txtOrderFONO.setText(model.getPhone());
                       Log.e("TAG","getPhone: "+model.getPhone());
           }




       };

       adapter.startListening();
       Log.e("TAG", "ADAPTER - :" + adapter.getItemCount());
       recyclerView.setAdapter(adapter);
       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(layoutManager);
   }



    private String convertCodeToStatus(String status ) {


        if ("0".equals(status))
            return "Pedido Recibido";
        else if ("1".equals(status))
            return "En proceso";
        else
            return "Enviado";

    }

  /*  @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.e("TAG", "OrderStatus ON_STARTING");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.e("TAG", "OrderStatus ON_STOPING");
    }*/
}
