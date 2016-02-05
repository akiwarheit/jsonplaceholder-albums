package thepragmaticbloggers.com.jsonplaceholderalbums.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Photo;

/**
 * Created by kdeloria on 2/5/2016.
 */
public interface AlbumsAPI {

    @GET("/albums")
    public void getAlbums(Callback<List<Album>> callback);

    @GET("/albums/{id}/photos")
    public void getAlbumPhotos(@Path("id") Long id, Callback<List<Photo>> callback);

}
