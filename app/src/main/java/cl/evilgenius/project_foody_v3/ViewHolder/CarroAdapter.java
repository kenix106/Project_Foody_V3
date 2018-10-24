package cl.evilgenius.project_foody_v3.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import cl.evilgenius.project_foody_v3.Interface.ItemClickListener;
import cl.evilgenius.project_foody_v3.Model.Order;
import cl.evilgenius.project_foody_v3.R;

class CarroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

   public TextView txt_carro_nombre, txt_precio;
   public ImageView img_carro_cantidad;
 //  public TextView cantidadTXT;

   private ItemClickListener itemClickListener;

    public void setTxt_carro_nombre(TextView txt_carro_nombre) {
        this.txt_carro_nombre = txt_carro_nombre;
    }

    public CarroViewHolder(@NonNull View itemView) {
        super(itemView);

        txt_carro_nombre = (TextView)itemView.findViewById(R.id.carro_item_name);
        txt_precio = (TextView) itemView.findViewById(R.id.carro_item_precio);
        img_carro_cantidad=(ImageView) itemView.findViewById(R.id.carro_item_cantidad);
       // cantidadTXT= (TextView) itemView.findViewById(R.id.carro_item_cantidad2);

    }

    @Override
    public void onClick(View v) {

    }
}

public class CarroAdapter extends RecyclerView.Adapter<CarroViewHolder> {

    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CarroAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CarroViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.carro_layout,viewGroup,false);
        return new CarroViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CarroViewHolder carroViewHolder, int i) {
        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(i).getQuantity(), Color.GREEN);//numero de cantidad
        carroViewHolder.img_carro_cantidad.setImageDrawable(drawable);
       // carroViewHolder.cantidadTXT.setText(listData.get(i).getQuantity());

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        Log.e("TAG", "NumberFormat carro adapter, working |precio:"+listData.get(i).getPrice()+"|  cantidad:"+ listData.get(i).getQuantity() );
        int price = (Integer.parseInt(listData.get(i).getPrice())) * (Integer.parseInt(listData.get(i).getQuantity()));
        carroViewHolder.txt_precio.setText(fmt.format(price)) ;
        carroViewHolder.txt_carro_nombre.setText(listData.get(i).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
