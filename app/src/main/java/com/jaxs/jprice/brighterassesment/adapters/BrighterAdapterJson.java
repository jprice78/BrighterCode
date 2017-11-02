package com.jaxs.jprice.brighterassesment.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaxs.jprice.brighterassesment.DetailActivity;
import com.jaxs.jprice.brighterassesment.R;
import com.jaxs.jprice.brighterassesment.model.Product;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by E21873 on 10/31/2017.
 */

public class BrighterAdapterJson extends RecyclerView.Adapter<BrighterAdapterJson.ViewBrighter> {


    private List<Product> ProductList;
    private Context mContext;
    Product brightProduct;


    public BrighterAdapterJson( Context mContext,List<Product> productList) {
        ProductList = productList;
        this.mContext = mContext;
    }

    @Override
    public ViewBrighter onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.album_card, parent, false);

        return new ViewBrighter(view);
    }

    @Override
    public void onBindViewHolder(ViewBrighter holder, int position) {

       brightProduct = ProductList.get(position);

        holder.title.setText(brightProduct.getmName());

        Picasso.with(mContext).load(brightProduct.getmImage()).into(holder.thumbnail);
    }

    @Override
    public int getItemCount() {
        return ProductList.size();
    }


    public class ViewBrighter extends RecyclerView.ViewHolder {

        public TextView title, count;
        public ImageView thumbnail, overflow;


         public ViewBrighter(View itemView) {
             super(itemView);

             title = (TextView) itemView.findViewById(R.id.title);
             count = (TextView) itemView.findViewById(R.id.count);
             thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);





             itemView.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     int pos = getAdapterPosition();
                     // if (pos != 0) {
                     //Album clickedDataItem = albumList.get(pos);
                     Intent intent = new Intent(mContext, DetailActivity.class);
                     intent.putExtra("name", ProductList.get(pos).getmName());
                     intent.putExtra("Desc", ProductList.get(pos).getDesc());
                     intent.putExtra("thumbnail", ProductList.get(pos).getmImage());

                     intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                     mContext.startActivity(intent);


                 }
             });



         }
     }

}
