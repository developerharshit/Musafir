package com.nitjsr.musafir;

public class Mask {
    private boolean faceDetected,faceMaskDetected;
    private String image_to_show;

    public boolean isFaceDetected() {
        return faceDetected;
    }

    public void setFaceDetected(boolean faceDetected) {
        this.faceDetected = faceDetected;
    }

    public boolean isFaceMaskDetected() {
        return faceMaskDetected;
    }

    public void setFaceMaskDetected(boolean faceMaskDetected) {
        this.faceMaskDetected = faceMaskDetected;
    }

    public String getImage_to_show() {
        return image_to_show;
    }

    public void setImage_to_show(String image_to_show) {
        this.image_to_show = image_to_show;
    }
}
