package com.example.recollectbookstore;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recollectbookstore.adapter.DiscoverResultsAdapter;
import com.example.recollectbookstore.entity.Item;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity implements DiscoverResultsAdapter.OnItemSelectedListener{
    private DiscoverResultsAdapter resultsAdapter;
    private LinkedHashMap<String, Item> mItens;
    private RecyclerView itemRecycler;
    private ViewGroup mEmptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);

        itemRecycler=findViewById(R.id.recycler_itens);
        mEmptyView =findViewById(R.id.view_empty);
        initRecyclerView();

        getBooksForSale();

    }

    private void initRecyclerView() {


        resultsAdapter = new DiscoverResultsAdapter(mItens, this);

        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setAdapter(resultsAdapter);

    }

    private void getBooksForSale(){
        mItens = new LinkedHashMap<>();
        String url = "http://192.168.160.59:8080/api/items?category=BOOKS&sold=false";

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("items: ", "failure");
                Log.e("items: ", e.getMessage());
                call.cancel();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Success", "");
                final String myResponse = response.body().string();

                //Log.e("items: ", myResponse);
                //System.out.println(myResponse);

                try {
                    JSONArray jsonArr = new JSONArray(myResponse);
                    for(int x=0; x<jsonArr.length(); x++){
                        JSONObject json = jsonArr.getJSONObject(x);

                        long id = Long.parseLong(json.get("id").toString());
                        String name = json.get("name").toString();
                        int quantity = Integer.parseInt(json.get("quantity").toString());
                        double price = Double.parseDouble(json.get("price").toString());
                        String description = json.get("description").toString();

                        ArrayList<String> images = new ArrayList<>();
                        JSONArray jsonArrayImages = (JSONArray) json.get("images");

                        for(int i=0; i<jsonArrayImages.length(); i++){
                            //Log.e(i + "",jsonArrayImages.get(i).toString());
                            images.add(jsonArrayImages.get(i).toString());
                        }

                        String date = json.get("creationDate").toString().split("T")[0];
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate creationDate = LocalDate.parse(date, formatter);


                        String category = json.get("category").toString();
                        Item item = new Item(id,name,quantity,price,description,images,creationDate, category);
                        mItens.put(item.getId().toString(),item);
                    }


                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            resultsAdapter.updateResults(mItens);
                        }
                    });
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });


    }


    @Override
    public void onItemSelected(String itemDocID) {

        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ITEMID, itemDocID);
        startActivity(intent);
    }








}
