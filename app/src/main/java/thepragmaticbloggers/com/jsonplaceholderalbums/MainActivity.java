package thepragmaticbloggers.com.jsonplaceholderalbums;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;
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
        ProgressDialogFragment.showLoadingProgress(getSupportFragmentManager());
        AlbumsAPI albumsAPI = APIClient.getInstance(this).getAPI(AlbumsAPI.class);
        albumsAPI.getAlbums(new Callback<List<Album>>() {
            @Override
            public void success(List<Album> alba, Response response) {
                albumViewModels.clear();
                for (Album album : alba) {
                    albumViewModels.add(new AlbumViewModel(album));
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
            }
        });
    }
}
