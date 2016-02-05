package thepragmaticbloggers.com.jsonplaceholderalbums.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;

/**
 * Created by kdeloria on 2/5/2016.
 */
public interface AlbumsAPI {

    @GET("/albums")
    public void getAlbums(Callback<List<Album>> callback);

}
