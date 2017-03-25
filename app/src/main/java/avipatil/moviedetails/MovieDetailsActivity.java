package avipatil.moviedetails;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class MovieDetailsActivity extends AppCompatActivity {
    TextView title,date,overView,vote;
    //ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title= (TextView) findViewById(R.id.title);
        date= (TextView) findViewById(R.id.date);
        overView= (TextView) findViewById(R.id.overView);
        overView.setMovementMethod(new ScrollingMovementMethod());
        vote= (TextView) findViewById(R.id.vote);
        //MovieDetails movieDetails= (MovieDetails) getIntent().getExtras().getSerializable("MOVIE_DETAILS");

        Intent i=getIntent();
          String name=i.getStringExtra("name");
          String overView1=i.getStringExtra("over");
          String date1=i.getStringExtra("date");
          //double rate=i.getDoubleExtra("rate",0.00);
        Bundle b = getIntent().getExtras();
        double rate = b.getDouble("rate");
        String rate1= Double.toString(rate);
        String img=i.getStringExtra("poster");
       ImageView imageView1= (ImageView) findViewById(R.id.poster);
        MovieDetails movieDetails=new MovieDetails(img);
         //Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+img.into(imageView));
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/"+movieDetails.getPoster_path()).into(imageView1);
            title.setText(name);
            date.setText(date1);
            overView.setText(overView1);
            vote.setText(rate1);

    }
}
