package faisyal.bahasaisyarat.betabercerita.Activities.ui;



public class Member {

    private String JudulVideo;
    private String VideoUri;
    private String thumbnail;
    private String search;

    public Member () {}


    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getJudulVideo() {
        return JudulVideo;
    }

    public void setJudulVideo(String judulVideo) {
        JudulVideo = judulVideo;
    }

    public String getVideoUrl() {
        return VideoUri;
    }

    public void setVideoUrl(String videoUrl) {
        VideoUri = videoUrl;
    }

    public String getSearch() {
        return search;
    }

    public void setSearch(String search) {
        this.search = search;
    }
}
