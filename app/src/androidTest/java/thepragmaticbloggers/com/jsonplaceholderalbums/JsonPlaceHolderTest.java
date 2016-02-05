package thepragmaticbloggers.com.jsonplaceholderalbums;

import android.test.AndroidTestCase;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.util.List;

import thepragmaticbloggers.com.jsonplaceholderalbums.model.Album;
import thepragmaticbloggers.com.jsonplaceholderalbums.model.Photo;

/**
 * Created by kdeloria on 2/5/2016.
 */
public class JsonPlaceHolderTest extends AndroidTestCase {

    /**
     * Check if album binding is correct
     *
     * @throws IOException
     */
    public void assertAlbumCorrect() throws IOException {
        Type type = new TypeToken<List<Album>>() {
        }.getType();
        Gson gson = new Gson();
        InputStream inputStream = getContext().getAssets().open("albums.json");
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter, "UTF-8");

        List<Album> alba = gson.fromJson(stringWriter.toString(), type);

        assertNotNull(alba);
    }

    /**
     * Check if model binding is correct
     *
     * @throws IOException
     */
    public void assertPhotoCorrect() throws IOException {
        Type type = new TypeToken<List<Photo>>() {
        }.getType();
        Gson gson = new Gson();
        InputStream inputStream = getContext().getAssets().open("albums.json");
        StringWriter stringWriter = new StringWriter();
        IOUtils.copy(inputStream, stringWriter, "UTF-8");

        List<Photo> photos = gson.fromJson(stringWriter.toString(), type);
        assertNotNull(photos);
    }
}
