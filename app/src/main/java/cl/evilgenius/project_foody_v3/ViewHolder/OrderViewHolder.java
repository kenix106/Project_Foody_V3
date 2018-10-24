package cl.evilgenius.project_foody_v3.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import cl.evilgenius.project_foody_v3.Interface.ItemClickListener;
import cl.evilgenius.project_foody_v3.R;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtOrderId, txtOrderStatus, txtOrderFONO, txtOrderAdddress;

    private ItemClickListener itemClickListener;




    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtOrderAdddress=(TextView)itemView.findViewById(R.id.order_addresss);
        txtOrderId = (TextView) itemView.findViewById(R.id.order_id);
        txtOrderStatus = (TextView) itemView.findViewById(R.id.order_status);
        txtOrderFONO = (TextView) itemView.findViewById(R.id.order_phone);

        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }


    @Override
    public void onClick(View v) {

        itemClickListener.onClick(v,getAdapterPosition(),false);


    }
}
