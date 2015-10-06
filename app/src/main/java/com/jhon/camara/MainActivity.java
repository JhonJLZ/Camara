package com.jhon.camara;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.provider.MediaStore.Images;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

import io.fabric.sdk.android.Fabric;



public class MainActivity extends Activity implements SurfaceHolder.Callback {

    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "gvRHFEoxnEeL9T06dxEw7ONKR";
    private static final String TWITTER_SECRET = "IXva31F3iNaoY4iSH74mASA5Ei4rEHQdgKIiF1zXnvz4T6ibDK";


    private static final String TAG = "jhon.hardware";
    public static Bitmap imagen;
    private LayoutInflater mInflater = null;
    Camera camera;
    public static byte[] tempdata;
    boolean mPreviewRunning = false;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    Button takePicture;

    //Ruta y Nombre de la foto
    public static String url;

    //Nombres Ramdom
    Random random;
    public static int nombre;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        mSurfaceView = (SurfaceView) findViewById(R.id.surface);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mInflater = LayoutInflater.from(this);
        View overView = mInflater.inflate(R.layout.segunda_capa,null);
        this.addContentView(overView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));

        takePicture = (Button) findViewById(R.id.botonFoto);
        takePicture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                camera.takePicture(mShutterCallback, mPictureCallback, mjpeg);
            }
        });

        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        random = new Random();
    }

    ShutterCallback mShutterCallback = new ShutterCallback() {

        @Override
        public void onShutter() {
        }
    };

    PictureCallback mPictureCallback = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub

        }
    };

    PictureCallback mjpeg = new PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // TODO Auto-generated method stub
            if(data != null){
                tempdata = data;
                done();
            }
        }
    };

    void done(){
        nombre = random.nextInt(100000);

        OutputStream outStream = null;
        File file = new File("/sdcard/DCIM/",String.valueOf(nombre)+ ".PNG");
        try {
            imagen=BitmapFactory.decodeByteArray(tempdata, 0, tempdata.length);

            Filtro filtro = new Filtro(imagen);
            imagen = filtro.executeFilter(MainActivity.this);

            outStream = new FileOutputStream(file);
            imagen.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            //Abrir Activity luego de guardar la foto
            Intent intent = new Intent(MainActivity.this, ImagenCapturada.class);
            startActivity(intent);

        } catch(Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width,
                               int height) {
        // TODO Auto-generated method stub
        Log.e(TAG,"SurfaceChanged");
        try{
            if(mPreviewRunning){
                camera.stopPreview();
                mPreviewRunning = false;
            }

            Camera.Parameters p = camera.getParameters();
            p.setPreviewSize(width,height);

            camera.setParameters(p);
            camera.setPreviewDisplay(holder);
            camera.startPreview();
            mPreviewRunning = true;
        }catch(Exception e){
            Log.d("",e.toString());
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.e(TAG,"Surface Created");
        camera = Camera.open();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        // TODO Auto-generated method stub
        Log.e(TAG, "Surface Destroyed");
        camera.stopPreview();
        mPreviewRunning = false;
        camera.release();
        camera = null;
    }
}