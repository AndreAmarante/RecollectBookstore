package com.example.recollectbookstore.ui;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.recollectbookstore.R;
import com.example.recollectbookstore.entity.Comment;
import com.example.recollectbookstore.entity.Item;
import com.example.recollectbookstore.entity.User;
import com.google.gson.JsonObject;

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

public class UserDetailActivity extends AppCompatActivity {

    public static final String OWNERID = "ownerID";
    private TextView user_name;
    private TextView user_email;
    private TextView user_phone;
    private TextView user_municipality;
    private TextView user_district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_detail);

        Bundle extras = getIntent().getExtras();
        long ownerID = -1;

        if (extras != null) {
            ownerID = extras.getLong(OWNERID);
        }

        user_name = findViewById(R.id.user_name);
        user_email = findViewById(R.id.user_email);
        user_phone = findViewById(R.id.user_phone);
        user_municipality = findViewById(R.id.user_municipality);
        user_district = findViewById(R.id.user_district);

        //API Call to show User details
        getUserFromAPI(ownerID);
    }

    /**
     *
     * @param ownerID - the book's owner's id
     * API call to get owner's info and show it to user
     */
    private void getUserFromAPI(long ownerID) {
        String url = "http://192.168.160.59:8080/api/user/" + ownerID;

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

                    String ownerName = json.get("name").toString();
                    String ownerEmail = json.get("email").toString();
                    String ownerPhone = json.get("phone").toString();
                    JSONObject location = json.getJSONObject("location");
                    String district = location.getString("district");
                    String county = location.getString("county");

                    final User owner = new User(id, ownerName, ownerEmail, ownerPhone, county, district);

                    //Update UI
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            user_name.setText(owner.getName());
                            user_email.setText(owner.getEmail());
                            user_phone.setText(owner.getPhone());
                            user_district.setText(owner.getDistrict());
                            user_municipality.setText(owner.getMunicipality());
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
     * Go to the previous activity (ItemDetailActivity)
     */
    public void onBackArrowClicked(View view) {
        onBackPressed();
    }
}
