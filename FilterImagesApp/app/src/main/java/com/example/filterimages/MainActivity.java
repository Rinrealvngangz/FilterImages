package com.example.filterimages;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Instrumentation;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.filterimages.R.id.btnOpen;


public class MainActivity extends AppCompatActivity {
    ImageButton btnOpen;
    ImageButton btnClear;
    ImageView imgDefault;
    Intent intent ;
     Button btnEdit;
     Uri uri;

    private static final int REQUEST_CODE_IMAGES =123;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        connectIdFromActivity();
        btnOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent =new Intent();
                intent.setType("image/*");
                intent.setAction(intent.ACTION_GET_CONTENT);
                startActivityForResult(intent.createChooser(intent,"Pick Images"),REQUEST_CODE_IMAGES);
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  Intent  intent =new Intent(MainActivity.this,Edit_Main_Activity.class);
                  if(uri!=null){
                      intent.putExtra("uriImages",uri);
                      startActivity(intent);
                  }else
                      Toast.makeText(MainActivity.this,"You don't choose Images",Toast.LENGTH_LONG).show();


            }
        });
        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Drawable drawable =getResources().getDrawable(R.drawable.my_default_background);
                   imgDefault.setImageDrawable(drawable);
                   uri =null;
            }
        });

        if(getIntent().hasExtra("uriFromEdit")) {
            Bundle bundle = getIntent().getExtras();
            uri = (Uri) bundle.get("uriFromEdit");
            imgDefault.setImageURI(uri);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
         if(requestCode ==REQUEST_CODE_IMAGES && resultCode == Activity.RESULT_OK&&data !=null){
              uri =  data.getData();
             imgDefault.setImageURI(uri);

         }

    }
    public void connectIdFromActivity()
    {
          btnOpen = findViewById(R.id.btnOpen);
          btnClear =findViewById(R.id.btnClear);
          imgDefault =findViewById(R.id.imgdisplay);
          btnEdit =findViewById(R.id.btnEdit);


    }
}