package com.example.filterimages;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.zomato.photofilters.FilterPack;
import com.zomato.photofilters.imageprocessors.Filter;
import com.zomato.photofilters.imageprocessors.subfilters.BrightnessSubFilter;
import com.zomato.photofilters.imageprocessors.subfilters.ContrastSubFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class filterImage extends AppCompatActivity implements View.OnClickListener {
    static
    {
        System.loadLibrary("NativeImageProcessor");
    }
   private ImageView imgfilter,btnBack;
    private List<ImageView> listimg;
    private ImageView img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15;
    private  Bitmap orgin;
    private List<Filter> listfilter;
    private Filter myfilter;
     private Button btnResult;
    private  Bitmap outputImage;
 private  Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_image);
        intent =new Intent(filterImage.this,Edit_Main_Activity.class);
        connectIdFromActivity();
        initlistImg();
        initlistFilter();

        if(getIntent().hasExtra("imagesFilter")){
            orgin = BitmapFactory.decodeByteArray(getIntent().getByteArrayExtra("imagesFilter"),0,getIntent().getByteArrayExtra("imagesFilter").length);
             imgfilter.setImageBitmap(orgin);
        }
        createImgFilterDeFault();
        final BitmapDrawable bitmapDrawable = (BitmapDrawable) imgfilter.getDrawable();
        orgin =bitmapDrawable.getBitmap();
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                      finish();
            }
        });
        btnResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ByteArrayOutputStream byteArrayOutputStream =new ByteArrayOutputStream();
                outputImage.compress(Bitmap.CompressFormat.JPEG,50,byteArrayOutputStream);
                intent =new Intent(filterImage.this,Edit_Main_Activity.class);
                intent.putExtra("filterImg",byteArrayOutputStream.toByteArray());
                startActivity(intent);
            }
        });
    }

     private  void createImgFilterDeFault(){
         for(int i=0;i<listimg.size();i++){
              listimg.get(i).setImageBitmap(orgin);
               listimg.get(i).setOnClickListener(this);
              getDefautFilter(listfilter.get(i),listimg.get(i));
         }
     }

    private void connectIdFromActivity(){
        imgfilter =findViewById(R.id.imgfilter);
        btnBack =findViewById(R.id.btnback);
        btnResult =findViewById(R.id.btnResult);
         img1 =findViewById(R.id.image1);
        img2 =findViewById(R.id.image2);
        img3 =findViewById(R.id.image3);
        img4 =findViewById(R.id.image4);
        img5 =findViewById(R.id.image5);
        img6 =findViewById(R.id.image6);
        img7 =findViewById(R.id.image7);
        img8 =findViewById(R.id.image8);
        img9 =findViewById(R.id.image9);
        img10 =findViewById(R.id.image10);
        img11 =findViewById(R.id.image11);
        img12 =findViewById(R.id.image12);
        img13 =findViewById(R.id.image13);
        img14 =findViewById(R.id.image14);
        img15 = findViewById(R.id.image15);
    }

    private  void initlistImg()
    {
        listimg = new ArrayList<ImageView>();
        ImageView[] arrimageview ={img1,img2,img3,img4,img5,img6,img7,img8,img9,img10,img11,img12,img13,img14,img15};
        for(int i=0;i<arrimageview.length;i++)
        {
            listimg.add(arrimageview[i]);
        }
    }

    private  void initlistFilter()
    {
        listfilter =new ArrayList<Filter>();
        Filter filter1 = new Filter();
        filter1.addSubFilter(new BrightnessSubFilter(30));
        filter1.addSubFilter(new ContrastSubFilter(1.1f));
        Filter filter2= FilterPack.getAweStruckVibeFilter(this);
        Filter filter3= FilterPack.getClarendon(this);
        Filter filter4= FilterPack.getOldManFilter(this);
        Filter filter5= FilterPack.getMarsFilter(this);
        Filter filter6= FilterPack.getRiseFilter(this);
        Filter filter7= FilterPack.getAprilFilter(this);
        Filter filter8= FilterPack.getAmazonFilter(this);
        Filter filter9= FilterPack.getStarLitFilter(this);
        Filter filter10= FilterPack.getNightWhisperFilter(this);
        Filter filter11= FilterPack.getLimeStutterFilter(this);
        Filter filter12= FilterPack.getHaanFilter(this);
        Filter filter13= FilterPack.getAdeleFilter(this);
        Filter filter14= FilterPack.getMetropolis(this);
        Filter filter15= FilterPack.getAudreyFilter(this);
        Filter[] arrfilter ={filter1,filter2,filter3,filter4,filter5,filter6,filter7,filter8,filter9,filter10,filter11,filter12
        ,filter13,filter14,filter15};
        for(int i=0;i<arrfilter.length;i++)
        listfilter.add(arrfilter[i]);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.image1:
                 myfilter = listfilter.get(0);
                getFilter(myfilter);
                break;

            case R.id.image2:
                myfilter = listfilter.get(1);
                getFilter(myfilter);
                break;

            case R.id.image3:
                myfilter = listfilter.get(2);
                getFilter(myfilter);
                break;

            case R.id.image4:
                myfilter = listfilter.get(3);
                getFilter(myfilter);
                break;

            case R.id.image5:
                myfilter = listfilter.get(4);
                getFilter(myfilter);
                break;

            case R.id.image6:
                myfilter = listfilter.get(5);
                getFilter(myfilter);
                break;

            case R.id.image7:
                myfilter = listfilter.get(6);
                getFilter(myfilter);
                break;

            case R.id.image8:
                myfilter = listfilter.get(7);
                getFilter(myfilter);
                break;

            case R.id.image9:
                myfilter = listfilter.get(8);
                getFilter(myfilter);
                break;
            case R.id.image10:
                myfilter = listfilter.get(9);
                getFilter(myfilter);
                break;
            case R.id.image11:
                myfilter = listfilter.get(10);
                getFilter(myfilter);
                break;
            case R.id.image12:

                myfilter = listfilter.get(11);
                getFilter(myfilter);
                break;
            case R.id.image13:
                myfilter = listfilter.get(12);
                getFilter(myfilter);
                break;
            case R.id.image14:
                myfilter = listfilter.get(13);
                getFilter(myfilter);
                break;
            case R.id.image15:
                myfilter = listfilter.get(14);
                getFilter(myfilter);
                break;
        }
        
    }

    private void getFilter(Filter myfilter){
        Bitmap imgbitmap1 = orgin.copy(Bitmap.Config.ARGB_8888,true);
         outputImage = myfilter.processFilter(imgbitmap1);
        imgfilter.setImageBitmap(outputImage);
    }

    private void getDefautFilter(Filter myfilter,ImageView imgview){
        BitmapDrawable drawablebitmap = (BitmapDrawable) imgview.getDrawable();
        Bitmap imgbitmap = drawablebitmap.getBitmap();
        Bitmap imgbitmapconfig = imgbitmap.copy(Bitmap.Config.ARGB_8888,true);
        Bitmap outputImage = myfilter.processFilter(imgbitmapconfig);
        imgview.setImageBitmap(outputImage);
    }
}