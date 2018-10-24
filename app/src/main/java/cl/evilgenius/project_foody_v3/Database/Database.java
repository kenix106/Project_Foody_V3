package cl.evilgenius.project_foody_v3.Database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

import cl.evilgenius.project_foody_v3.Model.Order;

public class Database extends SQLiteAssetHelper {

    private static final String DB_NAME = "FOODY_DB.db";
    private static final int DB_VER = 1;


    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);

    }

    public List<Order> getCarts() {
        Log.e("TAG", "llamando a BD");
        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"ProductName", "ProductID", "Quantity", "Price", "Discount", "NumTable"};
        String sqlTable = "OrderDetail";

        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null, null, null,null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()) {
            do {

                result.add(new Order(
                        c.getString(c.getColumnIndex("ProductID")),
                        c.getString(c.getColumnIndex("ProductName")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price")),
                        c.getString(c.getColumnIndex("Discount")),
                        c.getString(c.getColumnIndex("NumTable"))));

            } while (c.moveToNext());
        }
        //  Log.e("TAG","Termino de llamada de DB: mostrando result: "+result);
        return result;
    }

    public void addToCarro(Order order)
    {
        Log.e("TAG", "orden guardada en BD iniciando...");
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format(
                "INSERT INTO OrderDetail (ProductID,ProductName,Quantity,Price,Discount,NumTable) VALUES ('%s','%s','%s','%s','%s','%s');",
                order.getProductID(),
                order.getProductName(),
                order.getQuantity(),
                order.getPrice(),
                order.getDiscount(),
                order.getNumTable());
        db.execSQL(query);
        Log.e("TAG", "orden guardada en BD e INSERTADA");
        Log.e("TAG", "query: "+query);
        Log.e("TAG","ID: "+order.getProductID()+"| nombre: "+order.getProductName()+"| cantidad: "+order.getQuantity()+"| precio: "+order.getPrice()+"| descuento: "+order.getDiscount()+"| mesa: "+order.getNumTable());
    }

    public void CleanTheCarro() {
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM  OrderDetail");
        db.execSQL(query);
    }

}
