package com.jhon.camara;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;
import java.io.IOException;

public class ImagenCapturada extends AppCompatActivity {

    private ImageView imagenTomada;
    private Button bGrabar, bDetener, bReproducir;
    private MediaRecorder mediaRecorder;
    private MediaPlayer mediaPlayer;
    private static final String LOG_TAG = "Descripcion";
    private static String fichero = Environment.
            getExternalStorageDirectory().getAbsolutePath() + "/DCIM/ejemplo/"+ MainActivity.nombre+".3gp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        imagenTomada = (ImageView)findViewById(R.id.imagenTomada);
        imagenTomada.setImageBitmap(Constantes.imagen);
        bGrabar = (Button) findViewById(R.id.bGrabar);
        bDetener = (Button) findViewById(R.id.bDetener);
        bReproducir = (Button) findViewById(R.id.bReproducir);
        bDetener.setEnabled(false);
        bReproducir.setEnabled(false);
    }

    private void createInstagramIntent(String type){

        // Create the new Intent using the 'Send' action.
        Intent share = new Intent(Intent.ACTION_SEND);

        // Set the MIME type
        share.setType(type);

        // Create the URI from the media
        File miImagen = new File("/sdcard/"+MainActivity.nombre+".PNG");
        Uri uri = Uri.fromFile(miImagen);

        // Add the URI to the Intent.
        share.putExtra(Intent.EXTRA_STREAM, uri);

        // Broadcast the Intent.
        startActivity(Intent.createChooser(share, "Share to"));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_imagen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void Compartir(View view)
    {
        //Toast.makeText(ImagenCapturada.this,String.valueOf(MainActivity.nombre),Toast.LENGTH_LONG).show();

        File miImagen = new File("/sdcard/"+MainActivity.nombre+".PNG");
        Uri uri = Uri.fromFile(miImagen);

        TweetComposer.Builder builder = new TweetComposer.Builder(ImagenCapturada.this).text("Mi imagen").image(uri);
        builder.show();

    }

    public void CompartirInstagram(View view)
    {
        //Instagram
        String type = "image/*";
        createInstagramIntent(type);
    }

    public void grabar(View view)
    {
        bGrabar.setEnabled(false);
        bDetener.setEnabled(true);
        bReproducir.setEnabled(false);
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setOutputFile(fichero);
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try
        {
            mediaRecorder.prepare();
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Fallo en grabación");
        }
        mediaRecorder.start();
    }

    public void detenerGrabacion(View view)
    {
        mediaRecorder.stop();
        mediaRecorder.release();
        bGrabar.setEnabled(true);
        bDetener.setEnabled(false);
        bReproducir.setEnabled(true);
    }

    public void reproducir(View view)
    {
        mediaPlayer = new MediaPlayer();
        try
        {
            mediaPlayer.setDataSource(fichero);
            mediaPlayer.prepare();
            mediaPlayer.start();
        }
        catch (IOException e)
        {
            Log.e(LOG_TAG, "Fallo en reproducción");
        }
    }

    public void galeria(View view) {
        Intent intent = new Intent(ImagenCapturada.this, Gallery.class);
        startActivity(intent);
    }
}
