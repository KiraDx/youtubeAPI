package com.example.bakayarou.youtubeapi;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

public class PlayVideo extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener{

    YouTubePlayerView youTubePlayerView;
    String id ="";
    int REQUEST_VIDEO =123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.youtube);

        Intent intent = getIntent();
        id = intent.getStringExtra("idvideo");
        youTubePlayerView.initialize(MainActivity.API_KEY,this);



    }

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        youTubePlayer.loadVideo(id);
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        if(youTubeInitializationResult.isUserRecoverableError()){
            youTubeInitializationResult.getErrorDialog(PlayVideo.this, REQUEST_VIDEO);
        } else {
            Toast.makeText(this, "Lỗi ", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==REQUEST_VIDEO){
            youTubePlayerView.initialize(MainActivity.API_KEY, PlayVideo.this);
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
