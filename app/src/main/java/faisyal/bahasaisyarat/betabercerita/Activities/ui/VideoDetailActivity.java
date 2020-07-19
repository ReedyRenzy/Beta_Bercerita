package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import faisyal.bahasaisyarat.betabercerita.R;

public class VideoDetailActivity extends AppCompatActivity {

    private ImageView VideoThumbnailImg,VideoCoverImg;
    private TextView tv_title,tv_description;
    private FloatingActionButton play_fab;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_detail);
        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Nav);
        bottomNavigationView.setSelectedItemId(R.id.settings_bottom);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.upload_bottom:
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings_bottom:
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.realtimechat_buttom:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });


        // ini views
        /*videoView = findViewById(R.id.videonampil);
        videoView.setVideoURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/bercerita-58273.appspot.com/o/Video-Output-27396Bff-1C43-46D0-8C64-7C2ac168cd7d-1.mkv?alt=media&token=2f2de606-da3c-40e8-b78e-d27bc4e7ca6f"));
        videoView.start();*/

        iniViews();
    }

    void iniViews()  {
        /*play_fab = findViewById(R.id.play_fab);*/
        String videoTitle = getIntent().getExtras().getString("title");
        String videoDesc = getIntent().getExtras().getString("Desc");
        int imageResourceId = getIntent().getExtras().getInt("imgURL");
        int imagecover = getIntent().getExtras().getInt("imgCover");
        VideoThumbnailImg = findViewById(R.id.detail_video_img);
        Glide.with(this). load(imageResourceId).into(VideoThumbnailImg);
        VideoThumbnailImg.setImageResource(imageResourceId);
        /*VideoCoverImg = findViewById(R.id.detail_video_cover);*/
        /*Glide.with(this).load(imagecover).into(VideoCoverImg);*/
        tv_title = findViewById(R.id.detail_video_title);
        tv_title.setText(videoTitle);
        getSupportActionBar().setTitle(videoTitle);
        tv_description = findViewById(R.id.detail_video_desc);
        // setup animation
       /* VideoCoverImg.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));
        play_fab.setAnimation(AnimationUtils.loadAnimation(this,R.anim.scale_animation));videoView.start();*/
    }

}