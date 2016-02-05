package thepragmaticbloggers.com.jsonplaceholderalbums;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
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
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Photo;
import thepragmaticbloggers.com.jsonplaceholderalbums.viewmodel.PhotoViewModel;
import thepragmaticbloggers.com.jsonplaceholderalbums.web.APIClient;
import thepragmaticbloggers.com.jsonplaceholderalbums.widget.ProgressDialogFragment;

/**
 * Created by kdeloria on 2/5/2016.
 */
public class AlbumActivity extends AppCompatActivity {

    @Bind(R.id.album_grid)
    GridView grid;

    ViewableListAdapter viewableListAdapter;

    List<PhotoViewModel> photoViewModels = new ArrayList<PhotoViewModel>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        ButterKnife.bind(this);

        Album album = (Album) getIntent().getSerializableExtra("album");
        viewableListAdapter = new ViewableListAdapter(this, photoViewModels);
        grid.setAdapter(viewableListAdapter);

        AlbumsAPI albumsAPI = APIClient.getInstance(this).getAPI(AlbumsAPI.class);
        ProgressDialogFragment.showLoadingProgress(getSupportFragmentManager());
        albumsAPI.getAlbumPhotos(album.getId(), new Callback<List<Photo>>() {
            @Override
            public void success(List<Photo> photos, Response response) {
                photoViewModels.clear();

                for (Photo photo : photos) {
                    photoViewModels.add(new PhotoViewModel(photo));
                }
                viewableListAdapter.notifyDataSetChanged();

                try {
                    ProgressDialogFragment.dismissLoadingProgress(getSupportFragmentManager());
                } catch (Exception ex) {
                    Toast.makeText(AlbumActivity.this, "Something went wrong", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                error.printStackTrace();
                Toast.makeText(AlbumActivity.this, error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}
