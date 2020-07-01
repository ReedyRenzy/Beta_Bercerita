package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ActivityOptions;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import faisyal.bahasaisyarat.betabercerita.Activities.models.Kategori;
import faisyal.bahasaisyarat.betabercerita.Activities.adapters.KategoriAdapter;
import faisyal.bahasaisyarat.betabercerita.Activities.models.Slide;
import faisyal.bahasaisyarat.betabercerita.Activities.adapters.SliderPagerAdapter;
import faisyal.bahasaisyarat.betabercerita.Activities.adapters.VideoItemClickListener;
import faisyal.bahasaisyarat.betabercerita.R;

public class HomeActivity extends AppCompatActivity implements VideoItemClickListener {

    private List<Slide> stSlides ;
    private ViewPager sliderpager;
    private TabLayout indicator;
    private RecyclerView KategoriBI, VideoNampilHome;
    private DatabaseReference VideoRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        VideoRef = FirebaseDatabase.getInstance().getReference().child("videos");

        BottomNavigationView bottomNavigationView = findViewById(R.id.Bottom_Nav);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.home:
                        return true;
                    case R.id.upload_bottom:
                        startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.settings_bottom:
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
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

        sliderpager = findViewById(R.id.slider_pager);
        indicator = findViewById(R.id.indicator);
        KategoriBI = findViewById(R.id.BS_Kategori);
        VideoNampilHome = findViewById(R.id.VideoNampilHome);

        // prepare a list of slides....

        stSlides = new ArrayList<>();
        stSlides.add(new Slide(R.drawable.slide1, "nanti ini akan dipasang banner"));
        stSlides.add(new Slide(R.drawable.slide2, "nanti ini akan dipasang banner"));
        stSlides.add(new Slide(R.drawable.slide3, "nanti ini akan dipasang banner"));
        stSlides.add(new Slide(R.drawable.slide4, "nanti ini akan dipasang banner"));
        SliderPagerAdapter adapter = new SliderPagerAdapter(this,stSlides);
        sliderpager.setAdapter(adapter);

        // setup timer

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager(sliderpager,true);

        // Recyclerview setup


        // ini data

        List<Kategori> stKategori = new ArrayList<>();
        stKategori.add(new Kategori("Dasar Kosa Kata", R.drawable.kategori1,R.drawable.detail1));
        stKategori.add(new Kategori("Lagu", R.drawable.kategori2,R.drawable.detail2));
        stKategori.add(new Kategori("Podcast", R.drawable.kategori3,R.drawable.detail3));


        KategoriAdapter kategoriAdapter = new KategoriAdapter(this,stKategori,this);
        KategoriBI.setAdapter(kategoriAdapter);
        KategoriBI.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false));

    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder{
        View mview;
        public VideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mview = itemView;
        }
        public void setVideoJudul(String videoJudul){
           TextView judulvideo = (TextView) mview.findViewById(R.id.judul_tampil);
           judulvideo.setText(videoJudul);
         }
        public void setVideoUri(String videoUri){
            VideoView videotampill = (VideoView) mview.findViewById(R.id.video_tampil);
            videotampill.setVideoURI(Uri.parse(videoUri));
            videotampill.start();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<tampilvideo> options = new FirebaseRecyclerOptions.Builder<tampilvideo>().setQuery(VideoRef, tampilvideo.class).build();

        FirebaseRecyclerAdapter<tampilvideo, VideoViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<tampilvideo, VideoViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final VideoViewHolder holder, int position, @NonNull final tampilvideo model) {
                holder.setVideoJudul(model.getVideoJudul());
                holder.setVideoUri(model.getVideoUri());
            }

            @NonNull
            @Override
            public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilvideolayout, parent, false);
                VideoViewHolder viewHolder = new VideoViewHolder(view);
                return viewHolder;
            }
        };
        VideoNampilHome.setAdapter(firebaseRecyclerAdapter);
        VideoNampilHome.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        firebaseRecyclerAdapter.startListening();
    }


    @Override
    public void onVideoClick(Kategori video, ImageView VideoImageView) {

        // here we send movie information to detail activity
        // also we ll create the transition animation between the two activity

        Intent intent = new Intent(this,VideoDetailActivity.class);

        // send movie information to the deatilActivity

        intent.putExtra("title",video.getTitle());
        intent.putExtra("imgURL",video.getThumbnail());
        intent.putExtra("imgCover",video.getCoverPhoto());
        /*intent.putExtra("desc",video.getDescription());*/

        // lets create the animation
        ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(HomeActivity.this,
                                                    VideoImageView,"sharedName");

        startActivity(intent,options.toBundle());

        // i ll make a simple test to see if the click works

        Toast.makeText(this,"item clicked : " + video.getTitle(),Toast.LENGTH_LONG).show();
        // it works great

    }

    class SliderTimer extends TimerTask {

        @Override
        public void run() {

            HomeActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (sliderpager.getCurrentItem()<stSlides.size()-1) {
                        sliderpager.setCurrentItem(sliderpager.getCurrentItem()+1);

                    }
                    else
                        sliderpager.setCurrentItem(0);
                }
            });
        }
    }
}
