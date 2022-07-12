package com.example.vnews;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements NewsItemClicked {

    private SwipeRefreshLayout swipeContainer;

    private String category;

    private NewsListAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {

            category = extras.getString("category");

            setTitle(toUpperCase(category));

            swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
            swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    fetchData();
                }
            });

            RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));

            fetchData();
            mAdapter = new NewsListAdapter(this);

            recyclerView.setAdapter(mAdapter);

        }

    }

    private void fetchData() {
        String url = "https://saurav.tech/NewsAPI/top-headlines/category/" + category + "/in.json";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {

                            JSONArray newsJsonArray = response.getJSONArray("articles");
                            ArrayList<NewsData> newsArray = new ArrayList<>();

                            for (int i = 0; i < newsJsonArray.length(); i++) {
                                JSONObject currentArticle = newsJsonArray.getJSONObject(i);

                                String author;

                                if(currentArticle.isNull("author")) {
                                    author = "No Author";
                                } else {
                                    author = currentArticle.getString("author");
                                }

                                NewsData newsData = new NewsData(currentArticle.getString("title"),
                                        author,
                                        currentArticle.getJSONObject("source").getString("name"),
                                        currentArticle.getString("description"),
                                        currentArticle.getString("url"),
                                        currentArticle.getString("urlToImage"),
                                        currentArticle.getString("publishedAt"));

                                newsArray.add(newsData);
                            }

                            mAdapter.updateNews(newsArray);

                            swipeContainer.setRefreshing(false);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

        // Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    public void onItemClicked(NewsData item) {

        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(this, Uri.parse(item.getUrl()));

    }

    private String toUpperCase(String s) {
       return s = s.toUpperCase();
    }

}