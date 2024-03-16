package com.example.group5bookhub;

public class ImageAndText {
    private String txt;
    private int imageID;

    private String status;

    public ImageAndText(String txt, int imageID) {
        this.txt = txt;
        this.imageID = imageID;
    }

    public ImageAndText(String txt, int imageID, String status) {
        this.txt = txt;
        this.imageID = imageID;
        this.status = status;
    }

    public String getTxt() {

        return txt;
    }

    public int getImageID() {

        return imageID;
    }

    public String getStatus() {
        return status;
    }
}
