package thepragmaticbloggers.com.jsonplaceholderalbums.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.keeboi.theplan.base.ViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import thepragmaticbloggers.com.jsonplaceholderalbums.R;
import thepragmaticbloggers.com.jsonplaceholderalbums.api.UsersAPI;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.User;
import thepragmaticbloggers.com.jsonplaceholderalbums.web.APIClient;

/**
 * Created by kdeloria on 2/5/2016.
 */
public class AlbumViewModel extends ViewModel<Album> {

    @Bind(R.id.title)
    TextView titleTextView;

    @Bind(R.id.user)
    TextView userTextView;

    String username = "Retrieving username...";

    public AlbumViewModel(Album model) {
        super(model);
    }

    @Override
    public View getView(int position, LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view = null; // The view of each row

        // We check the convertView if it's null, this is basically a view already inflated and is just being recycled
        if (convertView == null) {
            view = inflater.inflate(R.layout.layout_album_item, null, false);
        } else {
            view = convertView;
        }

        ButterKnife.bind(this, view);

        if (titleTextView != null) {
            titleTextView.setText(getObject().getTitle());
        }

        userTextView.setText(username);
        UsersAPI usersAPI = APIClient.getInstance(inflater.getContext()).getAPI(UsersAPI.class);
        usersAPI.getUser(getObject().getUserId(), new Callback<User>() {
            @Override
            public void success(User user, Response response) {
                username = user.getName();
                if (userTextView != null) {
                    userTextView.setText(username);
                }
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

        return view;
    }
}
