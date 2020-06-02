package com.example.recollectbookstore.adapter;

import android.content.res.Resources;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recollectbookstore.R;
import com.example.recollectbookstore.entity.Item;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.Locale;

import com.bumptech.glide.Glide;

public class DiscoverResultsAdapter extends RecyclerView.Adapter<DiscoverResultsAdapter.DiscoverViewHolder> {

    public interface OnItemSelectedListener {

        void onItemSelected(String itemDocID);

    }

    private OnItemSelectedListener mListener;
    private LinkedHashMap<String, Item> mItens;

    public DiscoverResultsAdapter(LinkedHashMap<String, Item> itens, OnItemSelectedListener listener) {
        mItens = itens;
        mListener = listener;
    }

    public void updateResults(LinkedHashMap<String, Item> itens){
        mItens = itens;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DiscoverViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new DiscoverViewHolder(inflater.inflate(R.layout.item_card_item, parent, false));
    }


    @Override
    public void onBindViewHolder(@NonNull DiscoverViewHolder holder, int position) {
        holder.bind(mItens, mListener, position);
    }

    @Override
    public int getItemCount() {
        return mItens != null ? mItens.size() : 0;
    }

    public static class DiscoverViewHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        TextView nameView;
        TextView priceView;
        TextView quantityView;
        TextView categoryView;
        TextView dateView;
        TextView descView;

        public DiscoverViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_item_image);
            nameView = itemView.findViewById(R.id.item_item_name);
            priceView= itemView.findViewById(R.id.item_item_price);
            categoryView = itemView.findViewById(R.id.item_item_category);
            quantityView = itemView.findViewById(R.id.item_item_quantity);

            dateView = itemView.findViewById(R.id.item_item_date);
            descView = itemView.findViewById(R.id.item_item_descricao);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        public void bind(final  LinkedHashMap<String, Item> mItens,
                         final OnItemSelectedListener listener,
                         int postion) {

            final String recDocID = (new ArrayList<>(mItens.keySet())).get(postion);
            Item item = (new ArrayList<>(mItens.values())).get(postion);

            Resources resources = itemView.getResources();

            // Load image
            Glide.with(imageView.getContext())
                    .load(item.getFirstImage())
                    .into(imageView);

            nameView.setText(item.getName());
            priceView.setText(String.valueOf(item.getPrice()) + "€");
            quantityView.setText(resources.getString(R.string.fmt_num_ratings,
                    item.getQuantity()));
            categoryView.setText(item.getCategory());

            Log.e("data", item.getCreationDate().toString());
            dateView.setText(item.getCreationDate().toString());

            descView.setText(item.getDescription());

            // Click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        listener.onItemSelected(recDocID);
                    }
                }
            });
        }

    }


}
