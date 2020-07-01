package faisyal.bahasaisyarat.betabercerita.Activities.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import faisyal.bahasaisyarat.betabercerita.Activities.models.Kategori;
import faisyal.bahasaisyarat.betabercerita.R;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.MyViewHolder> {

    Context context ;
    List<Kategori> mData;
    VideoItemClickListener videoItemClickListener;


    public KategoriAdapter(Context context, List<Kategori> mData, VideoItemClickListener listener) {
        this.context = context;
        this.mData = mData;
        videoItemClickListener = listener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_movie,viewGroup, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {


        myViewHolder.TvTitle.setText(mData.get(i).getTitle());
        myViewHolder.ImgKategori.setImageResource(mData.get(i).getThumbnail());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class  MyViewHolder extends  RecyclerView.ViewHolder {

        private TextView TvTitle;
        private ImageView ImgKategori;
       /* private TextView TvDescription;*/


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            TvTitle = itemView.findViewById(R.id.item_kategori_title);
            ImgKategori = itemView.findViewById(R.id.item_kategori_img);
            /*TvDescription = itemView.findViewById(R.id.detail_video_desc);*/

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    videoItemClickListener.onVideoClick(mData.get(getAdapterPosition()),ImgKategori);
                }
            });
        }
    }
}
