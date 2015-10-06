package com.jhon.camara;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by Admin_Sena on 05/10/2015.
 */
public class Filtro extends Filter
{
    private Bitmap bitmapIn1;

    public Filtro(Bitmap bitmapIn) {
        this.bitmapIn1 = bitmapIn;
    }

    @Override
    Bitmap executeFilter(Context con)
    {
        //long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        //int x, y;
        //final int border = (int) (((width * this.getBorderSize()) + (height * this.getBorderSize())) / 2);

        Bitmap marco = BitmapFactory.decodeResource(con.getResources(), R.drawable.marcodos);
        Bitmap ajustarMarco = Bitmap.createScaledBitmap(marco, this.getBitmapIn().getWidth(), this.getBitmapIn().getHeight(), false);

        //Logos
        Bitmap logoUno = BitmapFactory.decodeResource(con.getResources(), R.mipmap.ic_launcher);

        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, this.getBitmapIn().getConfig());

        final Canvas canvas = new Canvas();
        canvas.setBitmap(bitmapOut);

        canvas.drawBitmap(this.getBitmapIn(), 0, 0, null);
        canvas.drawBitmap(ajustarMarco, 0, 0, null);
        canvas.drawBitmap(logoUno, 200, 0, null);


        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn1;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn1 = bitmapIn;
    }
}
