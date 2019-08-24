package br.com.cybersociety.testedesenvolvedormobile.model.entities;

import java.util.Date;

public class Movie {

    private Long id;
    private Boolean adult;
    private String originalLanguage;
    private String originalTitle;
    private String title;
    private String overview;
    private Double popularity;
    private Double rating;
    private Date releasedDate;

    public Movie() {
    }

    public Movie(Long id, Boolean adult, String originalLanguage, String originalTitle, String title, String overview, Double popularity, Double rating, Date releasedDate) {
        this.id = id;
        this.adult = adult;
        this.originalLanguage = originalLanguage;
        this.originalTitle = originalTitle;
        this.title = title;
        this.overview = overview;
        this.popularity = popularity;
        this.rating = rating;
        this.releasedDate = releasedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public Double getPopularity() {
        return popularity;
    }

    public void setPopularity(Double popularity) {
        this.popularity = popularity;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public Date getReleasedDate() {
        return releasedDate;
    }

    public void setReleasedDate(Date releasedDate) {
        this.releasedDate = releasedDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
