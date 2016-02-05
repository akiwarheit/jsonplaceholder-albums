package thepragmaticbloggers.com.jsonplaceholderalbums.model;

import java.io.Serializable;

/**
 * Created by kdeloria on 2/5/2016.
 */
public class Photo implements Serializable {

    private Long albumId;

    private Long id;

    private String title;

    private String url;

    private String thumbnailUrl;

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }
}
