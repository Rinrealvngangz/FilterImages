package com.example.filterimages;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

public class brightness extends AppCompatActivity {

    private Button btnReject,btnResult;
   private ImageView imageView ;
   private SeekBar seekBarBirght;
    private SeekBar seekBarContrast;
    private SeekBar seekBarSaturation;
    private int seekBarOrigin =255;
    private int seekBarConAndSa =256;
   private Bitmap bitmap;
   private  PictureFilter pictureFilterContrast,pictureFilterBright,pictureFilterSaturation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_brightness);
        connectIdFromActivity();
        if(getIntent().hasExtra("imageBright")){
            bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("imageBright"),0,getIntent().getByteArrayExtra("imageBright").length);

            imageView.setImageBitmap(bitmap);
        }
        pictureFilterBright =null;
        pictureFilterContrast =null;
        pictureFilterSaturation =null;

         btnReject.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                setProgOrigin();
             }
         });

         btnResult.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Intent intent =new Intent(brightness.this,Edit_Main_Activity.class);
                 ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                 bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                 intent.putExtra("brightnessImages",byteArrayOutputStream.toByteArray());
                 startActivity(intent);
             }
         });
        seekBarBirght.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                pictureFilterBright.GetAcjustBrightness(seekBar.getProgress()-255);

            }
            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                pictureFilterBright = new PictureFilter(imageView, bitmap);
                pictureFilterBright.start();
            }
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                 bitmap = pictureFilterBright.bitmapAfterFilter();

            }
        });
        seekBarContrast.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                pictureFilterContrast.GetAcjustContrast(seekBar.getProgress() / 256);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                pictureFilterContrast =new PictureFilter(imageView,bitmap);

                pictureFilterContrast.start();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                bitmap =pictureFilterContrast.bitmapAfterFilter();
            }
        });
        seekBarSaturation.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                           pictureFilterSaturation.GetAcjustSaturation(seekBar.getProgress()/256);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

               pictureFilterSaturation =new PictureFilter(imageView,bitmap);

                pictureFilterSaturation.start();

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
               bitmap =pictureFilterSaturation.bitmapAfterFilter();

            }
        });
    }

    private  void setProgOrigin(){

       seekBarBirght.setProgress(seekBarOrigin);
       seekBarSaturation.setProgress(seekBarConAndSa);
         seekBarContrast.setProgress(seekBarConAndSa);

    }
    public void connectIdFromActivity()
    {
       imageView =findViewById(R.id.imgBright);
        seekBarBirght =findViewById(R.id.seekbarbrightness);
        seekBarContrast =findViewById(R.id.seekbarcontrast);
        seekBarSaturation =findViewById(R.id.seekbarsaturation);
        btnReject =findViewById(R.id.btnReject);
        btnResult = findViewById(R.id.btnResult);
    }
}