package avipatil.moviedetails;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener
{

   ListView listView;
    MovieDetails movieDetails=new MovieDetails();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //linking  layout;
        //comment
        setContentView(R.layout.activitymain);
        listView= (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(this);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MainActivity.this,"longClick",Toast.LENGTH_SHORT).show();
                //view.toString().

                return false;
            }
        });

        //onCreateOptionsMenu()

        new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=4ca49f119a3255cba9e5546f0a8011ed");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.papular:
                new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/popular?api_key=4ca49f119a3255cba9e5546f0a8011ed");
                //Toast.makeText(getApplicationContext(),"Item 1 Selected",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.now_plaing:
                new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/now_playing?api_key=4ca49f119a3255cba9e5546f0a8011ed");
                //Toast.makeText(getApplicationContext(),"Item 2 Selected",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.up_coming:
                new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/upcoming?api_key=4ca49f119a3255cba9e5546f0a8011ed");
                //Toast.makeText(getApplicationContext(),"Item 3 Selected",Toast.LENGTH_LONG).show();
                return  true;
            case R.id.top_rated:
                new CheckConnectionStatus().execute("https://api.themoviedb.org/3/movie/top_rated?api_key=4ca49f119a3255cba9e5546f0a8011ed");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {

       // Toast.makeText(MainActivity.this,"Click",Toast.LENGTH_SHORT).show();
        MovieDetails movieDetails= (MovieDetails) parent.getItemAtPosition(position);
        String name= movieDetails.getOriginal_title();
        String date=movieDetails.getRelease_date();
        double rate=movieDetails.getVote_average();
        String imag=movieDetails.getPoster_path();
        String overView=movieDetails.getOverview();
        Intent it=new Intent(this,MovieDetailsActivity.class);
        it.putExtra("name",name);
        it.putExtra("over",overView);
        it.putExtra("date",date);
        Bundle b = new Bundle();
        b.putDouble("rate",rate);
        it.putExtras(b);
        it.putExtra("poster",imag);
        startActivity(it);


    }


    class CheckConnectionStatus extends AsyncTask<String,Void,String>
    {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //tv.setText(s);
            JSONObject jsonObject;
            try {
                 jsonObject=new JSONObject(s);
                // Array of parent Json Object
                ArrayList<MovieDetails> movieList=new ArrayList<>();
                JSONArray jsonArray=jsonObject.getJSONArray("results");
                Log.d("JsonArray",jsonArray.toString());
                System.out.println("JsonArray+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++"+jsonArray);
                for(int i=0;i<jsonArray.length();i++)
                {
                    JSONObject object=jsonArray.getJSONObject(i);
                    MovieDetails movieDetails=new MovieDetails();
                    movieDetails.setOriginal_title(object.getString("original_title"));
                    movieDetails.setOverview(object.getString("overview"));
                    movieDetails.setRelease_date(object.getString("release_date"));
                    movieDetails.setVote_average(object.getDouble("vote_average"));
                    movieDetails.setPoster_path(object.getString("poster_path"));
                    movieList.add(movieDetails);
                }
                MovieArrayAdapter movieArrayAdapter=new MovieArrayAdapter(MainActivity.this,R.layout.movie_list,movieList);
                listView.setAdapter(movieArrayAdapter);
            }catch (JSONException e){
                e.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {
            URL url =null;
            try{
                url=new URL(strings[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {
                HttpURLConnection urlConnection= (HttpURLConnection) url.openConnection();
                //urlConnection.setDoOutput(true);
                // geting input from connection
                InputStream inputStream=urlConnection.getInputStream();
                //Reading The Response

                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
                String s=bufferedReader.readLine();
                Log.d("ss",s);
                bufferedReader.close();
                //Return the Response Message to on post Excute Method
                return  s;
            } catch (IOException e) {
                Log.e("error",e.getMessage());
                e.printStackTrace();
            }

            return null;
        }
    }
}
