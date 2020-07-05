package faisyal.bahasaisyarat.betabercerita.Activities.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

import faisyal.bahasaisyarat.betabercerita.Activities.models.Kategori;
import faisyal.bahasaisyarat.betabercerita.Activities.models.Slide;
import faisyal.bahasaisyarat.betabercerita.Activities.ui.HomeActivity;
import faisyal.bahasaisyarat.betabercerita.Activities.ui.tampilvideo;
import faisyal.bahasaisyarat.betabercerita.R;

public class CustomViewAdapter extends FirebaseRecyclerAdapter<tampilvideo, RecyclerView.ViewHolder> {
    private static final int BANNER_VIEW = 1;
    private static final int POSTER_VIEW = 2;

    private Context mContext;
    private VideoItemClickListener mVideoItemClickListener;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CustomViewAdapter(@NonNull FirebaseRecyclerOptions<tampilvideo> options, Context context) {
        super(options);
        mContext = context;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) return BANNER_VIEW;
        if (position == 1) return POSTER_VIEW;
        return 0;
    }

    @Override
    protected void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position, @NonNull tampilvideo model) {
        try {
            if (holder instanceof VideoViewHolder) {
                VideoViewHolder videoViewHolder = (VideoViewHolder) holder;
                videoViewHolder.setVideoJudul(model.getVideoJudul());
                videoViewHolder.setVideoUri(model.getVideoUri());
            } else if (holder instanceof BannerViewHolder) {
                BannerViewHolder bannerViewHolder = (BannerViewHolder) holder;
            } else if (holder instanceof PosterViewHolder) {
                PosterViewHolder posterViewHolder = (PosterViewHolder) holder;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == BANNER_VIEW) {
            View banner = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_banner, parent, false);
            return new BannerViewHolder(banner);
        }

        if (viewType == POSTER_VIEW) {
            View poster = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_poster, parent, false);
            return new PosterViewHolder(poster);
        }

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tampilvideolayout, parent, false);
        return new VideoViewHolder(view);
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

    public class BannerViewHolder extends RecyclerView.ViewHolder{
        public BannerViewHolder(@NonNull View itemView) {
            super(itemView);
            ViewPager sliderpager = itemView.findViewById(R.id.slider_pager);
            TabLayout indicator = itemView.findViewById(R.id.indicator);
            List<Slide>  stSlides = new ArrayList<>();
            stSlides.add(new Slide(R.drawable.slide1, "nanti ini akan dipasang banner"));
            stSlides.add(new Slide(R.drawable.slide2, "nanti ini akan dipasang banner"));
            stSlides.add(new Slide(R.drawable.slide3, "nanti ini akan dipasang banner"));
            stSlides.add(new Slide(R.drawable.slide4, "nanti ini akan dipasang banner"));
            SliderPagerAdapter adapter = new SliderPagerAdapter(mContext ,stSlides);
            sliderpager.setAdapter(adapter);

//            Timer timer = new Timer();
//            timer.scheduleAtFixedRate(new HomeActivity.SliderTimer(), 4000, 6000);
//            indicator.setupWithViewPager(sliderpager,true);
        }
    }

    public class PosterViewHolder extends RecyclerView.ViewHolder{
        public PosterViewHolder(@NonNull View itemView) {
            super(itemView);
            RecyclerView kategoriBI = itemView.findViewById(R.id.BS_Kategori);

            List<Kategori> stKategori = new ArrayList<>();
            stKategori.add(new Kategori("Dasar Kosa Kata", R.drawable.kategori1,R.drawable.detail1));
            stKategori.add(new Kategori("Lagu", R.drawable.kategori2,R.drawable.detail2));
            stKategori.add(new Kategori("Podcast", R.drawable.kategori3,R.drawable.detail3));


            KategoriAdapter kategoriAdapter = new KategoriAdapter(mContext, stKategori, mVideoItemClickListener);
            kategoriBI.setAdapter(kategoriAdapter);
            kategoriBI.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false));
        }
    }

    public void setVideoClickListener(VideoItemClickListener videoClickListener) {
        this.mVideoItemClickListener = videoClickListener;
    }
}
