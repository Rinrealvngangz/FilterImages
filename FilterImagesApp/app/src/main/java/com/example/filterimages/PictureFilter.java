package com.example.filterimages;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Handler;
import android.widget.ImageView;

import java.util.logging.LogRecord;

public class PictureFilter extends Thread {
   private ImageView imageView;
    private Bitmap bitmap;
    private  Bitmap bitmapTemp;
    private Canvas canvas;
    private Paint paint;
    private ColorMatrix colorMatrix;
    private  ColorMatrixColorFilter colorMatrixColorFilter;
    private Handler handler;
    public   boolean running =false;
    public  PictureFilter(ImageView imageView,Bitmap bitmap){
        this.imageView =imageView;
        this.bitmap =bitmap;
        bitmapTemp = bitmapTemp.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        canvas =new Canvas(bitmapTemp);
         handler =new Handler();
         paint =new Paint();

    }
    public void GetAcjustBrightness(int bright){

        colorMatrix =new ColorMatrix(new float[]{
                1,0,0,0,bright,
                0,1f,0,0,bright,
                0,0,1f,0,bright,
                0,0,0,1f,0,
        }
        );
        colorMatrixColorFilter =new ColorMatrixColorFilter(colorMatrix);
        paint.setColorFilter(colorMatrixColorFilter);
        running =true;
    }

    ColorMatrixColorFilter setSaturation(float saturation) {

        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(saturation);

        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        return filter;
    }
    ColorMatrixColorFilter setContrast(float contrast) {
        float scale = contrast + 1.f;
        float translate = (-.5f * scale + .5f) * 255.f;
        float[] array = new float[] {
                scale, 0, 0, 0, translate,
                0, scale, 0, 0, translate,
                0, 0, scale, 0, translate,
                0, 0, 0, 1, 0};
        ColorMatrix matrix = new ColorMatrix(array);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        return filter;
    }
    public void GetAcjustContrast(float contrast){

        paint.setColorFilter(setContrast(contrast));
        running =true;
    }
    public void GetAcjustSaturation(float saturation){

        paint.setColorFilter(setSaturation(saturation));
        running =true;
    }

    public  Bitmap bitmapAfterFilter(){
        return  bitmapTemp;
    }

    @Override
    public void run() {
         while (true){

             if(running){
                 canvas.drawBitmap(bitmap,0,0,paint);
                 handler.post(new Runnable() {
                     @Override
                     public void run() {
                          imageView.setImageBitmap(bitmapTemp);
                          running =false;
                     }
                 });
             }

         }
    }

}
