package com.kson.circle;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    ImageView iv1,iv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv1 = (ImageView) findViewById(R.id.iv1);
        Glide.with(this).load("https://ss0.baidu.com/6ONWsjip0QIZ8tyhnq/it/u=2861297535,1561072810&fm=173&s=6E0009C3C82320963C212035030050D3&w=218&h=146&img.JPEG").into(iv1);
        loadImage();
    }

    private void loadImage(){
        BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds=true;
        BitmapFactory.decodeResource(getResources(),R.drawable.c,options);
        //xml 里面设定image width height ==100dp。这里将dp 转化为px。这里不xml 代码了
        int size= (int) (100*getResources().getDisplayMetrics().density);
        System.out.println("size: "+size);
        int widthSampleSize=options.outWidth/size;
        int heiSampleSize=options.outHeight/size;
        options.inSampleSize=widthSampleSize>heiSampleSize?heiSampleSize:widthSampleSize;
        options.inSampleSize=options.inSampleSize<1?1:options.inSampleSize;
        options.inJustDecodeBounds=false;
        Bitmap bitmap=BitmapFactory.decodeResource(getResources(),R.drawable.c,options);
        Bitmap target=Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),bitmap.getConfig());
        BitmapShader shader=new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Paint paint=new Paint();
        paint.setShader(shader);
        //draw into target bitmap
        Canvas canvas=new Canvas(target);
        float r=target.getWidth()>target.getHeight()?target.getHeight():target.getWidth();
        r/=2;
        //绘制一个圆形图片。经典的使用场景是头像
        canvas.drawCircle(r,r,r,paint);
        //display the resule
        iv1.setImageBitmap(target);
        bitmap.recycle();
//        target.recycle();
        target=Bitmap.createBitmap(target.getWidth(),target.getHeight(),target.getConfig());
        canvas.setBitmap(target);
        RectF rect=new RectF(0,0,target.getWidth(),target.getHeight());
        //绘制一个圆角矩形
        canvas.drawRoundRect(rect,400,400,paint);

    }

}
