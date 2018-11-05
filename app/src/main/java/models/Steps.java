package models;

public class Steps {
    String shortDescription,description,videoUrl,Thumbnail;

    public Steps(String shortDescription, String description, String videoUrl, String thumbnail) {
        this.shortDescription = shortDescription;
        this.description = description;
        this.videoUrl = videoUrl;
        Thumbnail = thumbnail;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setVideoUrl(String videoUrl) {
        this.videoUrl = videoUrl;
    }

    public void setThumbnail(String thumbnail) {
        Thumbnail = thumbnail;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public String getDescription() {
        return description;
    }

    public String getVideoUrl() {
        return videoUrl;
    }

    public String getThumbnail() {
        return Thumbnail;
    }
}
