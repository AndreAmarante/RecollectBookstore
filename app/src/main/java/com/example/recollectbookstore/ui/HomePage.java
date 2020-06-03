package com.example.recollectbookstore.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recollectbookstore.R;
import com.example.recollectbookstore.adapter.DiscoverResultsAdapter;
import com.example.recollectbookstore.entity.Item;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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

        //Initialize recycler and adapter to receive books from API
        itemRecycler=findViewById(R.id.recycler_itens);
        mEmptyView =findViewById(R.id.view_empty);
        initRecyclerView();

        //API call in this methos
        getBooksForSale();
    }

    /**
     * Initialize adapter + link it to recycler view
     */
    private void initRecyclerView() {
        resultsAdapter = new DiscoverResultsAdapter(mItens, this);
        itemRecycler.setLayoutManager(new LinearLayoutManager(this));
        itemRecycler.setAdapter(resultsAdapter);
    }

    /**
     * API call itself, processing the received data and passing it to the RecyclerView
     */
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
                Log.e("Fail", "API call failed");
                Log.e("Fail: ", e.getMessage());
                call.cancel();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Success", "Success calling the API");
                final String myResponse = response.body().string();

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
                            images.add(jsonArrayImages.get(i).toString());
                        }

                        String date = json.get("creationDate").toString().split("T")[0];
                        //DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        //LocalDate creationDate = LocalDate.parse(date, formatter);

                        String category = json.get("category").toString();
                        Item item = new Item(id,name,quantity,price,description,images,date, category);
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


    /**
     *
     * @param itemDocID - the clicked book's id
     *
     * Go to ItemDetailActivity, passing the book's id for the next API call
     */
    @Override
    public void onItemSelected(String itemDocID) {
        Intent intent = new Intent(this, ItemDetailActivity.class);
        intent.putExtra(ItemDetailActivity.ITEMID, itemDocID);
        startActivity(intent);
    }

    /**
     *
     * @param view
     *
     * Go back to MainActivity (initial home page)
     */
    public void onBackArrowClicked(View view) {
        onBackPressed();
    }
}
