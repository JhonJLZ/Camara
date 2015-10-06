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

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Random;

public class CamaraFrontal extends Activity implements SurfaceHolder.Callback{

    private static final String TAG = "jhon.hardware";
    public static Bitmap imagen;
    private LayoutInflater mInflater = null;
    Camera camera;
    public static byte[] tempdata;
    boolean mPreviewRunning = false;
    private SurfaceHolder mSurfaceHolder;
    private SurfaceView mSurfaceView;
    Button takePicture;
    Button changeCamera;

    //Ruta y Nombre de la foto
    public static String url;

    //Nombres Ramdom
    Random random;
    public static int nombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.camara_frontal);

        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceFrontal);
        mSurfaceHolder = mSurfaceView.getHolder();
        mSurfaceHolder.addCallback(this);
        mSurfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        mInflater = LayoutInflater.from(this);
        View overView = mInflater.inflate(R.layout.segunda_capa, null);
        this.addContentView(overView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));

        changeCamera = (Button)findViewById(R.id.botonCambiar);
        changeCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CamaraFrontal.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

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
            imagen = filtro.executeFilter(CamaraFrontal.this);

            outStream = new FileOutputStream(file);
            imagen.compress(Bitmap.CompressFormat.PNG, 100, outStream);
            outStream.flush();
            outStream.close();
            //Abrir Activity luego de guardar la foto
            Intent intent = new Intent(CamaraFrontal.this, ImagenCapturada.class);
            startActivity(intent);

        } catch(Exception e) {
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.e(TAG,"Surface Created");
        int camaraid = getFrontCameraId();
        camera = Camera.open(camaraid);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.e(TAG, "SurfaceChanged");
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
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.e(TAG, "Surface Destroyed");
        camera.stopPreview();
        mPreviewRunning = false;
        camera.release();
        camera = null;
    }

    private int getFrontCameraId(){
        int camId = -1;
        int numberOfCameras = Camera.getNumberOfCameras();
        Camera.CameraInfo ci = new Camera.CameraInfo();

        for(int i = 0;i < numberOfCameras;i++){
            Camera.getCameraInfo(i,ci);
            if(ci.facing == Camera.CameraInfo.CAMERA_FACING_FRONT){
                camId = i;
            }
        }
        return camId;
    }
}
