package faisyal.bahasaisyarat.betabercerita.Activities.ui;

public class tampilvideo {
    public String videoJudul, videoUri;

    tampilvideo(){

    }

    public tampilvideo(String videoJudul, String videoUri) {
        this.videoJudul = videoJudul;
        this.videoUri = videoUri;
    }

    public String getVideoJudul() {
        return videoJudul;
    }

    public void setVideoJudul(String videoJudul) {
        this.videoJudul = videoJudul;
    }

    public String getVideoUri() {
        return videoUri;
    }

    public void setVideoUri(String videoUri) {
        this.videoUri = videoUri;
    }
}
