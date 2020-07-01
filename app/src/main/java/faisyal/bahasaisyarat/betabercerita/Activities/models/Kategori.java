package faisyal.bahasaisyarat.betabercerita.Activities.models;

public class Kategori {

        private String title;
        private String description;
        private int thumbnail;
        private String studio;
        private String rating;
        private String streaminglink;
        private int coverPhoto;

    public Kategori(String title, int thumbnail, int coverPhoto) {
        this.title = title;
        this.thumbnail = thumbnail;
        this.coverPhoto = coverPhoto;
    }

    public Kategori(String title, int thumbnail) {
        this.title = title;
        this.thumbnail = thumbnail;
    }

    public Kategori(String title, String description, int thumbnail, String studio, String rating, String streaminglink) {
        this.title = title;
        this.description = description;
        this.thumbnail = thumbnail;
        this.studio = studio;
        this.rating = rating;
        this.streaminglink = streaminglink;
    }


    public int getCoverPhoto() {
        return coverPhoto;
    }

    public void setCoverPhoto(int coverPhoto) {
        this.coverPhoto = coverPhoto;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public String getStudio() {
        return studio;
    }

    public String getRating() {
        return rating;
    }

    public String getStreaminglink() {
        return streaminglink;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

    public void setStudio(String studio) {
        this.studio = studio;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setStreaminglink(String streaminglink) {
        this.streaminglink = streaminglink;
    }
}
