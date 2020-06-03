package com.example.recollectbookstore.ui;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recollectbookstore.R;
import com.example.recollectbookstore.adapter.CommentAdapter;
import com.example.recollectbookstore.entity.Comment;
import com.example.recollectbookstore.entity.Item;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ItemDetailActivity extends AppCompatActivity {

    public static final String ITEMID = "itemID";
    private TextView item_name_View;
    private TextView item_category;
    private ImageView item_image_View;
    private TextView item_date;
    private TextView item_price;
    private TextView item_quantity;
    private CommentAdapter commentAdapter;
    private ArrayList<Comment> mComments;
    private RecyclerView commentsRecycler;
    private ViewGroup emptyView;

    private long globalOwnerID = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_detail);

        Bundle extras = getIntent().getExtras();
        String itemID = "";

        if (extras != null) {
            itemID = extras.getString(ITEMID);
        }

        item_name_View = findViewById(R.id.item_name);
        item_image_View = findViewById(R.id.item_image);
        item_category = findViewById(R.id.item_category);
        item_date = findViewById(R.id.item_date);
        item_price = findViewById(R.id.item_price);
        item_quantity = findViewById(R.id.item_quantity);
        commentsRecycler = findViewById(R.id.recycler_comments);
        emptyView = findViewById(R.id.view_empty_comments);

        commentAdapter = new CommentAdapter(mComments);
        commentsRecycler.setLayoutManager(new LinearLayoutManager(this));
        commentsRecycler.setAdapter(commentAdapter);
        commentAdapter.onChanged(commentsRecycler, emptyView);

        //API call
        getItemDetails(itemID);
    }

    /**
     *
     * @param id - Book's id
     *
     * API Call and data processing to show the book's details to the user
     */
    private void getItemDetails(String id){
        final long idLong = Long.parseLong(id);

        String url = "http://192.168.160.59:8080/api/item/" + id;

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("Fail", "API call failed");
                Log.e("Fail", e.getMessage());
                call.cancel();
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d("Success", "Success calling the API");
                final String myResponse = response.body().string();

                try {
                    JSONObject json = new JSONObject(myResponse);

                    long id = Long.parseLong(json.get("id").toString());

                    final String name = json.get("name").toString();
                    int quantity = Integer.parseInt(json.get("quantity").toString());
                    double price = Double.parseDouble(json.get("price").toString());
                    String description = json.get("description").toString();

                    ArrayList<String> images = new ArrayList<>();
                    JSONArray jsonArrayImages = (JSONArray) json.get("images");
                    for(int i=0; i<jsonArrayImages.length(); i++){
                       images.add(jsonArrayImages.get(i).toString());
                    }

                    String date = json.get("creationDate").toString().split("T")[0];
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate creationDate = LocalDate.parse(date, formatter);

                    String category = json.get("category").toString();
                    long ownerID = -1;

                    try{
                        JSONObject jsonOwner = json.getJSONObject("owner");
                        ownerID = Long.parseLong(jsonOwner.get("id").toString());

                    }catch(Exception e){
                        //Enters here if owner is just the ID and not a JSONObject
                        ownerID = Long.parseLong(json.get("owner").toString());
                    }
                    globalOwnerID = ownerID;

                    ArrayList<Comment> comments = new ArrayList<>();
                    JSONArray commentsArray = json.getJSONArray("comment");

                    for(int i=0; i<commentsArray.length(); i++){
                        JSONObject comment = commentsArray.getJSONObject(i);

                        long commentID = Long.parseLong(comment.get("id").toString());
                        String commentText = comment.get("text").toString();

                        String dateComment = comment.get("timestamp").toString().split("T")[0];
                        DateTimeFormatter formatterComment = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate timestamp = LocalDate.parse(date, formatter);

                        Comment actual_comment = new Comment( commentID,  commentText,  timestamp);
                        comments.add(actual_comment);
                    }

                     final Item item = new Item(id,name,quantity,price,description,images,creationDate, category);
                     item.setComments(comments);

                     //Update UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            item_name_View.setText(name);
                            // Load image
                            Glide.with(item_image_View.getContext())
                                    .load(item.getFirstImage())
                                    .into(item_image_View);
                            item_category.setText(item.getCategory());
                            item_date.setText(item.getCreationDate().toString());
                            item_price.setText(item.getPrice() + " €");
                            item_quantity.setText("Available units: " + item.getQuantity());

                            mComments = item.getComments();
                            commentAdapter.updateResults(mComments);
                            commentAdapter.onChanged(commentsRecycler, emptyView);

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
     * @param view
     * Method called from the interface User Button to go to the UserDetailActivity
     * and call the API from there
     */
    public void seeUser(View view) {

        if(globalOwnerID!=-1){
            Intent intent = new Intent(this, UserDetailActivity.class);
            intent.putExtra(UserDetailActivity.OWNERID, globalOwnerID);
            startActivity(intent);
        }else{
            Toast.makeText(this, R.string.no_user, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     *
     * @param view
     * Go to the previous activity (HomePage)
     */
    public void onBackArrowClicked(View view) {
        onBackPressed();
    }




}
