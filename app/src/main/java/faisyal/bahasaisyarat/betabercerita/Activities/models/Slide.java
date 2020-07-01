package faisyal.bahasaisyarat.betabercerita.Activities.models;

public class Slide {

    private  int image ;
    private  String Title ;
    // add more field depandon what you want ...

    public Slide(int image, String title) {
        this.image = image;
        Title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return Title;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public void setTitle(String title) {
        Title = title;
    }
}

