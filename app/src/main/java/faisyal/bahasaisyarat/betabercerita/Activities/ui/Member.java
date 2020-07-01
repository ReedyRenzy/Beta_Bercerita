package faisyal.bahasaisyarat.betabercerita.Activities.ui;



public class Member {

    private String VideoJudul;
    private  String VideoUri;

    private Member () {}

    public  Member (String name,String videoUri){

        if (name.trim().equals("")){
            name = "not available";
        }
        VideoJudul = name;
        VideoUri = videoUri;
    }


    public String getVideoJudul() {
        return VideoJudul;
    }

    public void setVideoJudul(String videoJudul) {
        VideoJudul = videoJudul;
    }

    public String getVideoUri() {
        return VideoUri;
    }

    public void setVideoUri(String videoUri) {
        VideoUri = videoUri;
    }
}
