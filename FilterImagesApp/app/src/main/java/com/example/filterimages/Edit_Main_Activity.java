package com.example.filterimages;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.icu.text.CaseMap;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

public class Edit_Main_Activity extends AppCompatActivity {

    ImageView imgedit;
    Button btndone,btncancle;
    ImageButton btnCrop;
    ImageButton btnFilter;
    ImageButton btn_brightness;
    Uri uri;
    Bitmap bitmap;
    BitmapDrawable bitmapDrawable;
     Intent intent;
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__main_);
     connectIdFromActivity();


        if(getIntent().hasExtra("filterImg"))
        {
             bitmap = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("filterImg"),0,getIntent().getByteArrayExtra("filterImg").length);
            uri = getImageUri(getApplicationContext(),bitmap);
         //  imgedit.setImageBitmap(bitmap);
            imgedit.setImageURI(uri);


        }else if(getIntent().hasExtra("brightnessImages")){
                bitmap =BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("brightnessImages"),0,getIntent().getByteArrayExtra("brightnessImages").length);
            uri = getImageUri(getApplicationContext(),bitmap);
            imgedit.setImageURI(uri);
        }
        else  {
            Bundle bundleEdit= getIntent().getExtras();
            uri = (Uri) bundleEdit.get("uriImages");
            imgedit.setImageURI(uri);
            bitmapDrawable = (BitmapDrawable) imgedit.getDrawable();
            bitmap = bitmapDrawable.getBitmap();
        }
        btncancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 intent =new Intent(Edit_Main_Activity.this,MainActivity.class);
                startActivity(intent);
                deleteAllImageTitle();
            }
        });

        btndone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   uri = saveImageUri(getApplicationContext(),bitmap);
                 intent =new Intent(Edit_Main_Activity.this,MainActivity.class);
                      intent.putExtra("uriFromEdit",uri);
                       startActivity(intent);
                deleteAllImageTitle();
                Toast.makeText(Edit_Main_Activity.this,"SuccessFully",Toast.LENGTH_LONG).show();

            }
        });
        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imgedit !=null) {
                    startCrop(uri);
                }
            }
        });
        btnFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 intent =new Intent(Edit_Main_Activity.this,filterImage.class);
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                bitmapDrawable = (BitmapDrawable) imgedit.getDrawable();
                bitmap =bitmapDrawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                intent.putExtra("imagesFilter",byteArrayOutputStream.toByteArray());
                startActivity(intent);
            }
        });
        btn_brightness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(Edit_Main_Activity.this,brightness.class);
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                bitmapDrawable = (BitmapDrawable) imgedit.getDrawable();
                bitmap =bitmapDrawable.getBitmap();
                bitmap.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                intent.putExtra("imageBright",byteArrayOutputStream.toByteArray());
                startActivity(intent);
            }
        });
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

         if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE&&data !=null){
             CropImage.ActivityResult rs   = CropImage.getActivityResult((data));
             if(resultCode ==RESULT_OK)
             {
                 imgedit.setImageURI(rs.getUri());
                 uri =rs.getUri();
             }
         }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void startCrop(Uri uriimages) {
        CropImage.activity(uriimages).setGuidelines(CropImageView.Guidelines.ON)
        .setMultiTouchEnabled(true)
        .start(this);
    }
  private  void deleteAllImageTitle(){
      File dir = new File("/storage/emulated/0/Pictures/");
      if (dir.isDirectory())
      {
          String[] children = dir.list();
          for (int i = 0; i < children.length; i++)
          {
              if(children[i].contains("Title"))
                  new File(dir, children[i]).delete();
          }
      }
  }
    public void connectIdFromActivity(){
       imgedit = findViewById(R.id.imgEdit);
       btndone = findViewById(R.id.btnDone);
       btnCrop = findViewById(R.id.btnCrop);
       btnFilter =findViewById(R.id.btnFilter);
       btn_brightness =findViewById(R.id.btnBrightness);
       btncancle =findViewById(R.id.btncancle);

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public Uri saveImageUri(Context inContext, Bitmap inImage) {
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "result", null);
        return Uri.parse(path);
    }
}