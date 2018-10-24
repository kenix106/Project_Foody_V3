package cl.evilgenius.project_foody_v3;

import android.content.Intent;
import android.icu.util.ULocale;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import java.util.Locale;

import cl.evilgenius.project_foody_v3.Common.Common;
import cl.evilgenius.project_foody_v3.Interface.ItemClickListener;
import cl.evilgenius.project_foody_v3.Model.Categoria;
import cl.evilgenius.project_foody_v3.ViewHolder.MenuViewHolder;

public class Home extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    FirebaseDatabase database;
    DatabaseReference Categoria;

    TextView txtFullName;

    RecyclerView recycler_menu;
    RecyclerView.LayoutManager layoutManager;

    FirebaseRecyclerAdapter<Categoria, MenuViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Menu");
        setSupportActionBar(toolbar);

        //Iniciamos Firebase
        database = FirebaseDatabase.getInstance();
        Categoria = database.getReference("Categoria");
        // FirebaseRecyclerAdapter adapter =  new FirebaseRecyclerAdapter;


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();

                Intent carroIntent = new Intent(Home.this, Carro.class);
                startActivity(carroIntent);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //Nombre del Usuario//Seteamos el nombre del usuario logeado
        View headerView = navigationView.getHeaderView(0);
        txtFullName = (TextView) headerView.findViewById(R.id.txtFullName);
        txtFullName.setText(Common.currentUser.getName());

        //Cargamos el Menu de (usando Firebase UI )
        recycler_menu = (RecyclerView) findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(layoutManager);

        loadMenu();

    }


    private void loadMenu() {
        Log.e("TAG", "loadMenu_WORKING");
        Query query = FirebaseDatabase.getInstance().getReference().child("Categoria");
        FirebaseRecyclerOptions<Categoria> options = new FirebaseRecyclerOptions.Builder<Categoria>().setQuery(query, Categoria.class).build();
        //  FirebaseRecyclerOptions<Categoria>options= new FirebaseRecyclerOptions.Builder<Categoria>().setQuery(categoria,Categoria.class).build();
        adapter = new FirebaseRecyclerAdapter<Categoria, MenuViewHolder>(options) {
            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.e("TAG", "onCreateViewHolder(_WORKING");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.menu_item, viewGroup, false);
                return new MenuViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MenuViewHolder holder, int position, @NonNull Categoria model) {
                Log.e("TAG", "onBindViewHolder_WORKING");
                holder.txtMenuName.setText(model.getNombre());
                Picasso.get().load(model.getPic()).into(holder.imageView);
                final Categoria clickItem = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {

                        //aqui tomamos el ID seleccionado y lo mostramos en un toast
                        //  Toast.makeText(Home.this, ""+clickItem.getNombre(), Toast.LENGTH_SHORT).show();
                        //Pero ahora ttomamos ese ID y lo enviamos con un intent a otra actividad

                        Intent foodList = new Intent(Home.this, FoodList.class);
                        foodList.putExtra("CategoriaId", adapter.getRef(position).getKey());
                        startActivity(foodList);

                    }
                });
            }
        };
        //adapter.startListening();
        recycler_menu.setAdapter(adapter);
        recycler_menu.setHasFixedSize(true);
        recycler_menu.setLayoutManager(layoutManager);

    }//fin loadMenu()

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.e("TAG", "OnStart_WORKING");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.e("TAG", "OnStop_WORKING");
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_menu) {
            // menu de la pagina principal
        } else if (id == R.id.nav_cart) {
            Intent CartIntent = new Intent(Home.this, Carro.class);
            startActivity(CartIntent);

        } else if (id == R.id.nav_order) {
            Intent OrderStatusIntent = new Intent(Home.this, OrderStatus.class);
            startActivity(OrderStatusIntent);

        } else if (id == R.id.nav_Log_out) {
            //Logout, aqui terminamos session
            Intent logout = new Intent(Home.this, MainActivity.class);
            logout.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(logout);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
