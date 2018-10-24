package cl.evilgenius.project_foody_v3;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mancj.materialsearchbar.MaterialSearchBar;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import cl.evilgenius.project_foody_v3.Interface.ItemClickListener;
import cl.evilgenius.project_foody_v3.Model.Food;
import cl.evilgenius.project_foody_v3.ViewHolder.FoodViewHolder;

public class FoodList extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoriaID="";

    FirebaseRecyclerAdapter<Food,FoodViewHolder> adapter;

    //Funcionalidad del buscador
    FirebaseRecyclerAdapter<Food,FoodViewHolder> searchAdapter;
    List<String> suggestList = new ArrayList<>();
    MaterialSearchBar materialSearchBar;
    Food currentPlato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase iniciamos
        database =FirebaseDatabase.getInstance();
        foodList = database.getReference("Food");

        recyclerView = (RecyclerView) findViewById(R.id.recycler_food);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        categoriaID=getIntent().getStringExtra("CategoriaId");
        //Aqui recibimos el ID de Home (id de lo seleccionado)
        if (getIntent()!= null)
            categoriaID=getIntent().getStringExtra("CategoriaId");
        if (!categoriaID.isEmpty())
        {
            Log.e("TAG", "LoadList from ID working, ID from category is= "+categoriaID);
            loadListFood(categoriaID);// carga la seleccion dependiendo del ID clikeado anterior mente"categogiaID"
        }

        //BUSCADOOOOOOR
        materialSearchBar = (MaterialSearchBar)findViewById(R.id.searchBar);
        materialSearchBar.setHint("Buscador");
        //materialSearchBar.setSpeechMode(false);

        loadSuggest();//metodo de carga de lo buscado en firebase

        materialSearchBar.setLastSuggestions(suggestList);
        materialSearchBar.setCardViewElevation(10);
        materialSearchBar.addTextChangeListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  //cuando el usuario introduzca el texto esto cambiara la list de suggest(sugerido, escrito al ingles por comodidad)
                Log.e("TAG", "on text change working_ reciving data from search bar");
                    List<String> suggest= new ArrayList<String>();
                    for (String  search:suggestList){ //
                        if (search.toLowerCase().contains(materialSearchBar.getText().toLowerCase()))suggest.add(search);
                    }
                Log.e("TAG", "onTextChange_ "+materialSearchBar.getText());
                    materialSearchBar.setLastSuggestions(suggest);
                Log.e("TAG", "onTextChange_ las suggestion:  --->"+suggest);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        materialSearchBar.setOnSearchActionListener(new MaterialSearchBar.OnSearchActionListener() {
            @Override
            public void onSearchStateChanged(boolean enabled) {
                //cuando se la barra de buscador es cerrada
                //restauramos el adaptador original de sugerido (suggest)
                Log.e("TAG", "HAS CLIKEADO UN ELEMENTO ---->(onSearchStateChanged)");
                if (!enabled) {
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onSearchConfirmed(CharSequence text) {
                Log.e("TAG", "HAS CLIKEADO UN ELEMENTO ---->(onSearchConfirmed)------------>"+text);
                //caundo la buscqueda termina
                //mostramos el resultado de la searchAdapter

                startSearch(text);
             //  startSearch2(text);
            }

            @Override
            public void onButtonClicked(int buttonCode) {
            Log.e("TAG", "HAS CLIKEADO UN ELEMENTO---->"+ materialSearchBar.getText()+" |||plus buttonCode= "+buttonCode);
                
            }
        });

    }
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
  /*  private void startSearch2(CharSequence text) {
        Query query = FirebaseDatabase.getInstance().getReference("Food").orderByChild("Name").equalTo(text.toString());
        FirebaseRecyclerOptions <Food> options = new FirebaseRecyclerOptions.Builder<Food>().setQuery(query,Food.class).build();
        //searchAdapter2 = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoriaID))
        //        adapter= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class,R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoriaID))
        searchAdapter2 = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options) {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.e("TAG","oncreatedFoodlist loadListFood, working");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item,viewGroup,false);

                return new FoodViewHolder(view);

            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                Log.e("TAG","onBindViewFoodlist StratSearCH2, working");
                holder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.food_image);
                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();

                        //aqui enviamos el detalle de la eleccion de la subcategoria al layout de food_detail
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());//Enviamos a food_detail la key el objeto seleccionado
                        startActivity(foodDetail);
            }
        };
    }*/
//------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

    //09:00
    private void startSearch(CharSequence text) {
    Log.e("TAG", "StarSearCH is working...! con la palabra: " + text);

        Query query = FirebaseDatabase.getInstance().getReference("Food")
                .orderByChild("Name").equalTo(text.toString());

        FirebaseRecyclerOptions<Food>options=new FirebaseRecyclerOptions
                .Builder<Food>().setQuery(query,Food.class).build();

        searchAdapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options)
      //  Log.e("TAG", "ID de '"+text+"' : "+ searchAdapter.getRef();

        {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.e("TAG","onCreated startSearch, working");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item,viewGroup,false);

                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                Log.e("TAG","onBindView startSearch, working");
                holder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.food_image);
                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();

                        //aqui enviamos el detalle de la eleccion de la subcategoria al layout de food_detail
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",searchAdapter.getRef(position).getKey());//Enviamos a food_detail la key el objeto seleccionado
                        startActivity(foodDetail);

                    }
                });
            }
        };
        recyclerView.setAdapter(searchAdapter);//seteamos el adaptador para SEARCH
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }



    private void loadSuggest() {
        Log.e("TAG", "LoadSuggest working");
        foodList.orderByChild("MenuId").equalTo(categoriaID)
                .addValueEventListener(new ValueEventListener() {//busca segun categorizacion de ID
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for (DataSnapshot postSnapshot:dataSnapshot.getChildren())
                {
                    Food item = postSnapshot.getValue(Food.class);
                    suggestList.add(item.getName());//Agregamos el nombre d ela comida sugerida
                    Log.e("TAG", "LoadSuggest working and the item suggest for suggestList is: ------->"+item);
                    Log.e("TAG", "LoadSuggest working gettin ID suggestList name: ------->"+ item.getName());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void loadListFood (String categoriaID){
        Log.e("TAG", "LoadlistFood working");
  /*       adapter= new FirebaseRecyclerAdapter<Food, FoodViewHolder>(Food.class, R.layout.food_item,FoodViewHolder.class,foodList.orderByChild("MenuId").equalTo(categoriaID))*/
        Query query = FirebaseDatabase.getInstance().getReference("Food")
                .orderByChild("MenuId").equalTo(categoriaID);

        FirebaseRecyclerOptions<Food>options=new FirebaseRecyclerOptions
                .Builder<Food>().setQuery(query,Food.class).build();

        adapter = new FirebaseRecyclerAdapter<Food, FoodViewHolder>(options)

        {
            @NonNull
            @Override
            public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.e("TAG","oncreatedFoodlist loadListFood, working");
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.food_item,viewGroup,false);

                return new FoodViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FoodViewHolder holder, int position, @NonNull Food model) {
                Log.e("TAG","onBindViewFoodlist loadListFood, working");
                holder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(holder.food_image);
                final Food local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                       // Toast.makeText(FoodList.this, ""+local.getName(), Toast.LENGTH_SHORT).show();

                        //aqui enviamos el detalle de la eleccion de la subcategoria al layout de food_detail
                        Intent foodDetail = new Intent(FoodList.this,FoodDetail.class);
                        foodDetail.putExtra("FoodId",adapter.getRef(position).getKey());//Enviamos a food_detail la key el objeto seleccionado
                        startActivity(foodDetail);

                    }
                });
            }


        };

        Log.e("TAG", "ADAPTER - "+adapter.getItemCount());
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);
    }
    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
        Log.e("TAG", "OnStart_WORKING2");
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
        Log.e("TAG", "OnStop_WORKING2");
    }
}
