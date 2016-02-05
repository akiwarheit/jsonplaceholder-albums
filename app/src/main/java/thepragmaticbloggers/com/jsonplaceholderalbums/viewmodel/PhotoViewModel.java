package thepragmaticbloggers.com.jsonplaceholderalbums.viewmodel;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.keeboi.theplan.base.ViewModel;

import butterknife.Bind;
import butterknife.ButterKnife;
import thepragmaticbloggers.com.jsonplaceholderalbums.R;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Photo;

/**
 * Created by kdeloria on 2/5/2016.
 */
public class PhotoViewModel extends ViewModel<Photo> {

    @Bind(R.id.image)
    ImageView thumbnail;

    @Bind(R.id.title)
    TextView title;

    public PhotoViewModel(Photo model) {
        super(model);
    }

    @Override
    public View getView(int position, LayoutInflater inflater, View convertView, ViewGroup parent) {
        View view = null; // The view of each row

        // We check the convertView if it's null, this is basically a view already inflated and is just being recycled
        if (convertView == null) {
            view = inflater.inflate(R.layout.layout_photo_item, null, false);
        } else {
            view = convertView;
        }

        ButterKnife.bind(this, view);
        Glide.with(inflater.getContext()).load(getObject().getThumbnailUrl()).into(thumbnail);
        title.setText(getObject().getTitle());

        return view;
    }
}
