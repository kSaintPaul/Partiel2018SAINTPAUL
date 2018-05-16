package smartbookmarks.kevinsaintpaul.diiage.org.partiel2018saintpaul;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import Database.ReleaseHelper;
import Model.Release;

public class MainActivity extends AppCompatActivity {

    ListView listRelease;

    ArrayList<Release> releases;

    ReleaseHelper releaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String baseUrlApi = getResources().getString(R.string.base_url_api);


        URL baseUrl = null;

        try{
            baseUrl = new URL(baseUrlApi);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        asyncTask.execute(baseUrlApi);
    }

    @SuppressLint("StaticFieldLeak")
    AsyncTask<String, Void, JSONArray> asyncTask = new AsyncTask<String, Void, JSONArray>() {
        @Override
        protected JSONArray doInBackground(String... strings) {

            URL baseUrl = null;
            try {
                baseUrl = new URL(strings[0]);

                InputStream inputStream = null;

                inputStream = baseUrl.openStream();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                StringBuilder stringBuilder = new StringBuilder();

                String lineBuffer = null;
                while ((lineBuffer = bufferedReader.readLine()) != null){
                    stringBuilder.append(lineBuffer);
                }

                String data = stringBuilder.toString();

                return new JSONArray(data);

            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }


        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            super.onPostExecute(jsonArray);

            releases = new ArrayList<>();

            try {
                /*Cursor cursor = db.query(DbHelper.Table_Release,
                        new String[]{"id", "title"},
                        null,
                        null,
                        null,null, "title DESC");


                while(cursor.moveToNext()){
                    Release release = new Release();
                    releases.add(release);
                }*/

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = null;

                    jsonObject = jsonArray.getJSONObject(i);


                    Release release = new Release();

                    release.setId(jsonObject.getInt("id"));
                    release.setStatus(jsonObject.getString("status"));
                    release.setThumb(jsonObject.getString("thumb"));
                    release.setFormat(jsonObject.getString("format"));
                    release.setTitle(jsonObject.getString("title"));
                    release.setCatno(jsonObject.getString("catno"));
                    release.setYear(jsonObject.getInt("year"));
                    release.setResourceUrl(jsonObject.getString("resource_url"));
                    release.setArtist(jsonObject.getString("artist"));

                    releases.add(release);


                }

            }catch (Exception e){

            }


            listRelease = findViewById(R.id.ListRelease);

            ReleaseAdapter releaseAdapter = new ReleaseAdapter(MainActivity.this, releases);

            listRelease.setAdapter(releaseAdapter);
        }
    };


    public static class ReleaseAdapter extends BaseAdapter {

        Activity context;
        ArrayList<Release> releases;
        ReleaseViewHolder releaseViewHolder;

        public ReleaseAdapter(Activity context, ArrayList<Release> releases) {
            this.context = context;
            this.releases = releases;
        }

        @Override
        public int getCount() {
            return releases.size();
        }

        @Override
        public Object getItem(int position) {
            return releases.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View view, ViewGroup viewGroup) {
            View v;

            Release release = releases.get(position);

            if (view != null) {
                v = view;
                releaseViewHolder = (ReleaseViewHolder) view.getTag();
            } else {
                LayoutInflater layoutInflater = this.context.getLayoutInflater();

                v = layoutInflater.inflate(R.layout.row_release, viewGroup, false);

                TextView lblTitle = v.findViewById(R.id.lblTitle);
                TextView lblArtist = v.findViewById(R.id.lblArtist);
                TextView lblYear = v.findViewById(R.id.lblYear);
                TextView lblCatNo = v.findViewById(R.id.lblCatNo);

                releaseViewHolder = new ReleaseViewHolder(lblTitle, lblArtist, lblYear, lblCatNo);

                v.setTag(releaseViewHolder);
            }

            releaseViewHolder.Artist.setText(release.getArtist());
            releaseViewHolder.Title.setText(release.getTitle());
            releaseViewHolder.CatNo.setText(release.getCatno());
            releaseViewHolder.Year.setText(String.valueOf(release.getYear()));

            return v;

        }

        public static class ReleaseViewHolder{

            public TextView Title;
            public TextView Artist;
            public TextView Year;
            public TextView CatNo;

            public ReleaseViewHolder(TextView title, TextView artist, TextView year, TextView catNo) {
                Title = title;
                Artist = artist;
                Year = year;
                CatNo = catNo;
            }
        }


    }



}
