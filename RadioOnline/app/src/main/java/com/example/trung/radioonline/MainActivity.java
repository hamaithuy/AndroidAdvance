package com.example.trung.radioonline;

import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mdrawerlayout;
    private ActionBarDrawerToggle mtoggle;
    private NavigationView mnaviationview;
    private final static String stream = "http://bbcmedia.ic.llnwd.net/stream/bbcmedia_radio2_mf_p";
    private String streamurl="";
    ImageButton play, next, previous;
    MediaPlayer mediaPlayer;
    boolean started = false;
    boolean prepared = false;
    ArrayList<String> url = new ArrayList<String>();
    TextView textfm;
    ImageView imagefm;
    String[] url2 = new String[]{
            "http://118.69.80.90:8000/live/",
            "http://54.196.17.162/townsquare-wwylfmaac-ibc3",
            "http://23.111.161.50:8006/;",
            "https://5a6872aace0ce.streamlock.net/vovgt+hn/vovgt+hn.stream_aac/playlist.m3u8"



    };
    //  "https://5a6872aace0ce.streamlock.net/vovgt+hn/vovgt+hn.stream_aac/playlist.m3u8",
    // "http://wms-13.streamsrus.com:10010/;"
    //"http://sc6.vie.llnw.net/stream/bbcwssc_mp1_ws-ename",
    //http://stream2.mobiradio.vn/vovradio/vov2.stream/playlist.m3u8
    String[] fmname = new String[]{
            "Xone FM",
            "Southern Hits Stasion - Wild 104",
            "Dance N Pop FM Top 40",
            "VOV giao thông",


    };
    // "VOV giao thông","BBC World Service",
    //"Addicted to Radio - Dance Hits",
    int[] image = new int[]{
            R.drawable.xone,
            R.drawable.wwyl,
            R.drawable.dancenpop,
            R.drawable.vov

    };
    int pos=0;
    //url.add(1,"http://54.196.17.162/townsquare-wwylfmaac-ibc3");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
/*
        url.add(1,"http://118.69.80.90:8000/live/");
        url.add(2,"http://54.196.17.162/townsquare-wwylfmaac-ibc3");
        url.add(3,"http://23.111.161.50:8006/;");

*/
        mdrawerlayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mtoggle = new ActionBarDrawerToggle(this, mdrawerlayout,R.string.open,R.string.close);

        mdrawerlayout.addDrawerListener(mtoggle);
        mtoggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mnaviationview =(NavigationView) findViewById(R.id.nv1);

        textfm = (TextView) findViewById(R.id.namefm);
        imagefm= (ImageView) findViewById(R.id.img);
        play= (ImageButton) findViewById(R.id.b_play);
        play.setImageResource(R.mipmap.play);
        next = (ImageButton) findViewById(R.id.b_next);
        previous = (ImageButton) findViewById(R.id.b_previous);
       // streamurl=url2[pos];
        //play.setEnabled(false);
       // play.setText("Loading..");

        loaddt();
        try {
            taoplayer();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //mediaPlayer = new MediaPlayer();
       // mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        mnaviationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                ngunplayer();
                switch (menuItem.getItemId()){
                    case(R.id.nav_xonefm):{

                        pos=0;

                        break;
                    }
                    case(R.id.nav_wwyl): {

                        pos=1;

                        break;
                    }
                    case(R.id.nav_dancepop)    : {

                        pos=2;

                        break;
                    }
                    case(R.id.nav_vov):{
                        pos=3;
                        break;
                    }


                }
                loaddt();
                try {
                    taoplayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chayplayer();
                play.setImageResource(R.mipmap.pause);

                return true;
            }
        });


        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying())
                {
                    mediaPlayer.stop();
                    play.setImageResource(R.mipmap.play);
                }
                else{
                    try {
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();
                    play.setImageResource(R.mipmap.pause);
                }
                /*
                if (started) {
                    mediaPlayer.pause();
                    started = false;
                    play.setText("Play");
                } else {
                    mediaPlayer.start();
                    started = true;
                    play.setText("Pause");
                } */
            }
        });


        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngunplayer();
                pos++;
                //mediaPlayer.setDataSource(url.get(pos));
                streamurl=url2[pos];
                loaddt();
                try {
                    taoplayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chayplayer();
                play.setImageResource(R.mipmap.pause);

            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ngunplayer();
                pos--;
                //mediaPlayer.setDataSource(url.get(pos));
                streamurl=url2[pos];
                loaddt();
                try {
                    taoplayer();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    mediaPlayer.prepare();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chayplayer();
                play.setImageResource(R.mipmap.pause);
            }

        });
        //new PlayTask().execute(streamurl);
    }
    /*
    private class PlayTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected Boolean doInBackground(String... strings) {
            try {
                mediaPlayer.setDataSource(strings[0]);
                mediaPlayer.prepare();
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return prepared;
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            play.setEnabled(true);
            play.setText("Play");
        }

    }
     case(R.id.nav_vov):{
                        pos=3;
                        break;
                    }
     case(R.id.nav_addicted):{
                        pos=4;
                        break;
                    }
                    case(R.id.nav_bbc):{
                        pos=5;
                        break;
                    }

<item
        android:id="@+id/nav_vov"
        android:title="VOV giao thông"/>
    <item
        android:id="@+id/nav_addicted"
        android:title="Addicted to Radio"/>
    <item
        android:id="@+id/nav_bbc"
        android:title="BBC World Service"/>
*/

    private void loaddt(){
        textfm.setText(fmname[pos]);
        imagefm.setImageResource(image[pos]);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(mtoggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    private void taoplayer() throws IOException {
        mediaPlayer=new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setDataSource(url2[pos]);

    }
    private void ngunplayer(){
        mediaPlayer.stop();
        mediaPlayer.release();
    }
    private void chayplayer(){
        mediaPlayer.start();
    }
    @Override
    protected void onPause() {
        super.onPause();
        if(started)
            mediaPlayer.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(started)
            mediaPlayer.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mediaPlayer.release();
    }


}
