package com.example.vnews;

public class NewsData {

    private String title;
    private String author;
    private String sourceName;
    private String description;
    private String url;
    private String urlToImage;
    private String publishedAt;

    public NewsData(String tit, String aut, String source, String desc, String u, String uToImage, String date) {
        title = tit;
        author = aut;
        sourceName = source;
        description = desc;
        url = u;
        urlToImage = uToImage;
        publishedAt = date;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getSourceName() {
        return sourceName;
    }

    public String getDescription() {
        return description;
    }

    public String getUrl() {
        return url;
    }

    public String getUrlToImage() {
        return urlToImage;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

}