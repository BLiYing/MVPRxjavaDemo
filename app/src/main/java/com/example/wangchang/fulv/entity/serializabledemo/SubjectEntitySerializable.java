package com.example.wangchang.fulv.entity.serializabledemo;

import android.os.Parcel;

import java.io.Serializable;
import java.util.List;

/**
 * Created by liying on 17/2/27.
 * Subject是继承Parcelable，而这里是集成Serializable
 */
public class SubjectEntitySerializable implements Serializable{

    private String id;
    private String alt;
    private String year;
    private String title;
    private String original_title;
    private List<String> genres;
    private List<Cast> casts;
    private List<Cast> directors;
    private Avatars images;

    @Override
    public String toString() {
        return "SubjectEntityParcelable.id=" + id
                + " SubjectEntityParcelable.title=" + title
                + " SubjectEntityParcelable.year=" + year
                + " SubjectEntityParcelable.originalTitle=" + original_title + casts.toString() + directors.toString() + " | ";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<Cast> getCasts() {
        return casts;
    }

    public void setCasts(List<Cast> casts) {
        this.casts = casts;
    }

    public List<Cast> getDirectors() {
        return directors;
    }

    public void setDirectors(List<Cast> directors) {
        this.directors = directors;
    }

    public Avatars getImages() {
        return images;
    }

    public void setImages(Avatars images) {
        this.images = images;
    }

    protected SubjectEntitySerializable(Parcel in){

    }
    private static class Cast{
        private String id;
        private String name;
        private String alt;
        private Avatars avatars;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public Avatars getAvatars() {
            return avatars;
        }

        public void setAvatars(Avatars avatars) {
            this.avatars = avatars;
        }

        @Override
        public String toString() {
            return "cast.id=" + id + " cast.name=" + name + " | ";
        }
    }

    private static class Avatars{
        private String small;
        private String medium;
        private String large;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }
    }


}