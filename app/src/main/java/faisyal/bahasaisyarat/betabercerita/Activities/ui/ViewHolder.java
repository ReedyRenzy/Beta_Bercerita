package faisyal.bahasaisyarat.betabercerita.Activities.ui;

import android.app.Application;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.squareup.picasso.Picasso;

import faisyal.bahasaisyarat.betabercerita.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    SimpleExoPlayer exoPlayer;
    ImageView imageView;

    public ViewHolder(@NonNull View itemView) {

        super(itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mClickListener.onItemClick(view,getAdapterPosition());
            }
        });
        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {

                mClickListener.onItemClick(view,getAdapterPosition());
                return false;
            }
        });



    }

    public void  setExoplayer(Application application ,String JudulVideo, String VideoUrl, String thumbnail) {

        TextView textView = itemView.findViewById(R.id.tv_judulvideo);
        imageView = itemView.findViewById(R.id.thumbnail);

        textView.setText(JudulVideo);

        try {
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter.Builder(application).build();
            TrackSelector trackSelector = new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = (SimpleExoPlayer) ExoPlayerFactory.newSimpleInstance(application);
            Uri video = Uri.parse(VideoUrl);
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory("video");
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(video,dataSourceFactory,extractorsFactory,null,null);
            Picasso.get()
                    .load(thumbnail)
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .into(imageView);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(false);

        }catch (Exception e) {
                Log.e("ViewHolder","exoplayer error"+e.toString());
        }


    }

        private ViewHolder.Clicklistener mClickListener;
    public  interface Clicklistener{
        void onItemClick(View view,int position);
        void onItemLongClick(View view , int position);
    }

    public void setOnClickListener(ViewHolder.Clicklistener clicklistener){
        mClickListener = clicklistener;
    }







}
