package faisyal.bahasaisyarat.betabercerita.Activities.adapters;

import android.widget.ImageView;

import faisyal.bahasaisyarat.betabercerita.Activities.models.Kategori;

public interface VideoItemClickListener {

    void  onVideoClick(Kategori video, ImageView VideoImageView) ; // we will need the imageview to make the shared animation between the two activity


}
