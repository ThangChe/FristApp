package com.thangtien.firstapp.model;

public class Video {
    private String path;
    private String thumb;

    public Video(String path, String thumb) {
        this.path = path;
        this.thumb = thumb;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }
}
