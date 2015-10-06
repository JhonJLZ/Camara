package com.jhon.camara;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.twitter.sdk.android.tweetcomposer.TweetComposer;

import java.io.File;

public class ImagenCapturada extends AppCompatActivity {

    private ImageView imagenTomada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imagen);

        imagenTomada = (ImageView)findViewById(R.id.imagenTomada);
        imagenTomada.setImageBitmap(Constantes.imagen);
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
}
