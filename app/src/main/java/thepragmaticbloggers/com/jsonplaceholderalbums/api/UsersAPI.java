package thepragmaticbloggers.com.jsonplaceholderalbums.api;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.User;

/**
 * Created by kdeloria on 2/5/2016.
 */
public interface UsersAPI {

    @GET("/users/{id}")
    public void getUser(@Path("id") Long id, Callback<User> callback);


    @GET("/users")
    public void getUsers(Callback<List<User>> callback);

}
