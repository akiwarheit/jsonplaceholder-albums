package thepragmaticbloggers.com.jsonplaceholderalbums;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.keeboi.theplan.adapter.ViewableListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import thepragmaticbloggers.com.jsonplaceholderalbums.api.AlbumsAPI;
import thepragmaticbloggers.com.jsonplaceholderalbums.api.UsersAPI;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.User;
import thepragmaticbloggers.com.jsonplaceholderalbums.viewmodel.AlbumViewModel;
import thepragmaticbloggers.com.jsonplaceholderalbums.web.APIClient;
import thepragmaticbloggers.com.jsonplaceholderalbums.widget.ProgressDialogFragment;

public class MainActivity extends AppCompatActivity {

    // the list view for this particular album
    @Bind(R.id.album_list)
    ListView albumListView;

    ViewableListAdapter viewableListAdapter;

    List<AlbumViewModel> albumViewModels = new ArrayList<AlbumViewModel>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewableListAdapter = new ViewableListAdapter(this, albumViewModels);

        setContentView(R.layout.activity_main);
        // view binding
        ButterKnife.bind(this);
        albumListView.setAdapter(viewableListAdapter);

        albumListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AlbumActivity.class);
                intent.putExtra("album", albumViewModels.get(position).getObject());
                startActivity(intent);
            }
        });

        ProgressDialogFragment.showLoadingProgress(getSupportFragmentManager());
        final AlbumsAPI albumsAPI = APIClient.getInstance(this).getAPI(AlbumsAPI.class);


        UsersAPI usersAPI = APIClient.getInstance(this).getAPI(UsersAPI.class);
        usersAPI.getUsers(new Callback<List<User>>() {
            @Override
            public void success(List<User> users, Response response) {
                AlbumViewModel.users.addAll(users);

                albumsAPI.getAlbums(new Callback<List<Album>>() {
                    @Override
                    public void success(List<Album> alba, Response response) {
                        albumViewModels.clear();
                        for (Album album : alba) {
                            AlbumViewModel albumViewModel = new AlbumViewModel(album);
                            albumViewModels.add(albumViewModel);
                        }
                        viewableListAdapter.notifyDataSetChanged();
                        try {
                            ProgressDialogFragment.dismissLoadingProgress(getSupportFragmentManager());
                        } catch (Exception ex) {
                            Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        error.printStackTrace();
                        Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                        ProgressDialogFragment.dismissLoadingProgress(getSupportFragmentManager());
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
                ProgressDialogFragment.dismissLoadingProgress(getSupportFragmentManager());
            }
        });


    }
}
