package com.example.bakayarou.youtubeapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static String API_KEY = "AIzaSyDVvZufMvOG_D2r9w8pBZRctObEcydLUGo";
    String IDList ="PLF4R2hLHA2dc88vE-5HRwwNP1FNSwkxlz";

    String urlJson ="https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId="+IDList+"&key="+API_KEY+"&maxResults=50";
    ListView listView;
    ArrayList<Video> videoArrayList;
    VideoAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView) findViewById(R.id.listview);
        videoArrayList = new ArrayList<>();
        adapter =  new VideoAdapter(this,R.layout.row_video,videoArrayList);
        listView.setAdapter(adapter);

        GetJsonYouTube(urlJson);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PlayVideo.class);
                intent.putExtra("idvideo",videoArrayList.get(position).getIdVideo());
                startActivity(intent);
            }
        });
    }
    private void GetJsonYouTube(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray jsonItems =response.getJSONArray("items");

                            String title ="";
                            String url = "";
                            String idvideo="";
                            for(int i =0; i< jsonItems.length();i++){
                                JSONObject jsonItem = jsonItems.getJSONObject(i);
                                JSONObject jsonSnippet = jsonItem.getJSONObject("snippet");

                                 title= jsonSnippet.getString("title");

                                 JSONObject jsonThumbnail = jsonSnippet.getJSONObject("thumbnails");

                                 JSONObject jsonMedium = jsonThumbnail.getJSONObject("medium");
                                 url = jsonMedium.getString("url");

                                 JSONObject jsonResourcID = jsonSnippet.getJSONObject("resourceId");
                                 idvideo = jsonResourcID.getString("videoId");

                                 videoArrayList.add(new Video(title,url,idvideo));


                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        requestQueue.add(jsonObjectRequest);
    }
}
