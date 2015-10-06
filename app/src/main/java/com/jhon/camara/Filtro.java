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

    private double borderSize;

    public Filtro(Bitmap bitmapIn) {
        this.bitmapIn1 = bitmapIn;
        this.borderSize = borderSize;
    }

    @Override
    Bitmap executeFilter(Context con)
    {
        //long time = System.currentTimeMillis();
        final int width = this.getBitmapIn().getWidth();
        final int height = this.getBitmapIn().getHeight();
        //int x, y;
        //final int border = (int) (((width * this.getBorderSize()) + (height * this.getBorderSize())) / 2);

        Bitmap iconoMarco = BitmapFactory.decodeResource(con.getResources(), R.drawable.marcodos);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(this.getBitmapIn(), iconoMarco.getWidth(), iconoMarco.getHeight(), false);

        final Bitmap bitmapOut = Bitmap.createBitmap(width, height, resizedBitmap.getConfig());

        final Canvas canvas = new Canvas();
        canvas.setBitmap(bitmapOut);

        canvas.drawBitmap(resizedBitmap, 0, 0, null);
        canvas.drawBitmap(iconoMarco, 0, 0, null);

        /*final Paint paint = new Paint();
        for (x = 0; x < width; x++) {
            for (y = 0; y < height; y++) {
                if (((x < border) || (x > width - border))
                        || ((y < border) || (y > height - border))) {
                    paint.setARGB(0, 0, 255, 0);
                    canvas.drawPoint(x, y, paint);
                }
                if (((x == border) || (x == width - border))
                        || ((y == border) || (y == height - border))) {
                    paint.setARGB(127, 255, 255, 255);
                    canvas.drawPoint(x, y, paint);
                }
            }
        }
        //time = System.currentTimeMillis() - time;
        //System.out.println("Finished @ " + time + "ms");*/

        return bitmapOut;
    }

    public Bitmap getBitmapIn() {
        return bitmapIn1;
    }

    public void setBitmapIn(Bitmap bitmapIn) {
        this.bitmapIn1 = bitmapIn;
    }

    public double getBorderSize() {
        return borderSize;
    }

    public void setBorderSize(double borderSize) {
        this.borderSize = borderSize;
    }
}
