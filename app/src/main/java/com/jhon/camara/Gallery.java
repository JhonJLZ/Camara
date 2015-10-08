package com.jhon.camara;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.io.File;
import java.util.ArrayList;

public class Gallery extends AppCompatActivity {

    private GridView imageGrid;
    public static ArrayList<File> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        imageGrid = (GridView)findViewById(R.id.gridview);
        File f = new File(Environment.getExternalStorageDirectory() + "/DCIM/ejemplo/");
        list = imageReader(f);

        imageGrid.setAdapter(new NewGridAdapter(this));
    }

    class NewGridAdapter extends BaseAdapter {
        private Context mContext;
        //private ArrayList<File> fileList = Gallery.list;

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        public NewGridAdapter(Context c) {
            mContext = c;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imageView;
            File picture = new File(getItem(position).toString());
            if (convertView == null){
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }
            else{
                imageView = (ImageView) convertView;
            }
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 2;
            Bitmap myBitmap = BitmapFactory.decodeFile(picture.getAbsolutePath(), options);
            imageView.setImageBitmap(myBitmap);
            //imageView.setImageResource(list.get(position));
            return imageView;
        }
    }

    ArrayList<File> imageReader (File root)
    {
        ArrayList<File> a = new ArrayList<>();
        File [] files = root.listFiles();
        for (int i = 0; i < files.length; i++)
        {
            if (files[i].isDirectory())
            {
                a.addAll(imageReader(files[i]));
            }else {
                if (files[i].getName().endsWith(".jpg")){
                    a.add(files[i]);
                }
            }
        }
        return a;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_gallery, menu);
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
}
