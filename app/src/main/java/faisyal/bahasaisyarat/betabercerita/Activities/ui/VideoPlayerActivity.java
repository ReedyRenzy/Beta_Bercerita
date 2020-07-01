package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import faisyal.bahasaisyarat.betabercerita.R;

public class VideoPlayerActivity extends AppCompatActivity {


    private PlayerView playerView;
    private SimpleExoPlayer simpleExoPlayer;
    public static final String VIDEO_TEST_URL="https://firebasestorage.googleapis.com/v0/b/bercerita-58273.appspot.com/o/video1.mp4?alt=media&token=10640805-497c-4038-8e3a-566025cf45dd" ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setFullScreen();
        setContentView(R.layout.activity_video_player);
        hideActionbar();

        iniExoPlayer();
    }

    private void hideActionbar() {
        getSupportActionBar().hide();
    }

    private void setFullScreen() {

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    private void iniExoPlayer(){


        playerView = findViewById(R.id.movie_exo_player);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);
        playerView.setPlayer(simpleExoPlayer);
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this,"appname"));
        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(VIDEO_TEST_URL));
        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        simpleExoPlayer.release();
    }
}