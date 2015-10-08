package com.jhon.camara;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Jhon on 07/10/2015.
 */
public class GridAdapter extends BaseAdapter {

    private ArrayList<File> fileList = Gallery.list;

    @Override
    public int getCount() {
        return this.fileList.size();
    }

    @Override
    public Object getItem(int position) {
        return fileList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        /*if (convertView==null)
        {
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.my_grid, null);
        }

        ImageView imageView = (ImageView)convertView.findViewById(R.id.imageViewGrid);
        imageView.setImageURI(Uri.parse(getItem(position).toString()));*/

        return null;
    }
}
