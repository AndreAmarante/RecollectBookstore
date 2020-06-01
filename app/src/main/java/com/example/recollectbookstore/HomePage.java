package com.example.recollectbookstore;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recollectbookstore.entity.Item;
import com.google.android.material.navigation.NavigationView;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomePage extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private RecyclerView mSearchResultsRecycler;
    //private DiscoverResultsAdapter resultsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        mSearchResultsRecycler = findViewById(R.id.recycler_search_discover);

        //initRecyclerView();

        apiTest();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home_page, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void initRecyclerView() {


        //resultsAdapter = new DiscoverResultsAdapter(searchResults, this);
        mSearchResultsRecycler.setLayoutManager(new LinearLayoutManager(this));
        //mSearchResultsRecycler.setAdapter(resultsAdapter);

    }

    private void apiTest(){
        String url = "http://192.168.160.59:8080/api/items";

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

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();


                Log.e("items: ", myResponse);
                System.out.println(myResponse);

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
                        String str[] = json.get("images").toString().split(",");
                        images = (ArrayList<String>) Arrays.asList(str);

                        Date creationDate = new SimpleDateFormat("dd/MM/yyyy").parse(json.get("creationDate").toString());

                        String category = json.get("category").toString();
                        Item item = new Item(id,name,quantity,price,description,images,creationDate, category);
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }


            }
        });


    }

    }
