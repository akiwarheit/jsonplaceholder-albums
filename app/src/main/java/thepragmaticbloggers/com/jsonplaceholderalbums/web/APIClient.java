package thepragmaticbloggers.com.jsonplaceholderalbums.web;

import android.content.Context;
import android.util.Log;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;

import retrofit.RestAdapter;
import retrofit.client.ApacheClient;
import retrofit.client.Client;

/**
 * Default APIClient, we will use this for authentication. Extend this later on to add an interceptor for headers
 * <p/>
 * Created by kevin on 10/31/15.
 */
public class APIClient {

    protected RestAdapter retrofit;

    protected Context context;

    protected APIClient(Context context) {
        this.context = context;
    }

    public <K> K getAPI(Class<K> clazz) {
        // Build retrofit if it's empty
        if (retrofit == null) {
            setup();
        }
        return retrofit.create(clazz);
    }

    /**
     * Do initializations and stuff
     */
    protected void setup() {
        retrofit = setupRestAdapter(new RestAdapter.Builder()).build();
    }

    protected RestAdapter.Builder setupRestAdapter(RestAdapter.Builder builder) {
        return builder.setLog(new RestAdapter.Log() {
            @Override
            public void log(String message) {
                Log.i("RETROFIT", message);
            }
        })
                .setClient(getClient())
                .setEndpoint("http://jsonplaceholder.typicode.com/").setLogLevel(RestAdapter.LogLevel.FULL);
    }

    /**
     * Create an http client
     *
     * @return httpclient
     */
    protected Client getClient() {
        BasicHttpParams params = new BasicHttpParams();
        SchemeRegistry schemeRegistry = new SchemeRegistry();
        schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
        final SSLSocketFactory sslSocketFactory = SSLSocketFactory.getSocketFactory();
        schemeRegistry.register(new Scheme("https", sslSocketFactory, 443));
        ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);
        DefaultHttpClient httpclient = new DefaultHttpClient(cm, params);
        return new ApacheClient(httpclient);
    }


    // Used for non-rg classes
    private static APIClient apiClient;

    /**
     * Utility method to get use APIClient for non-rg classes
     *
     * @param context the context this API client will be used on
     * @return the API client object
     */
    public static APIClient getInstance(Context context) {
        if (apiClient == null) {
            apiClient = new APIClient(context);
        }

        return apiClient;
    }
}
