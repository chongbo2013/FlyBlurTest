package xu.ferris.flyblurtest;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView iv_bg;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        iv_bg=(ImageView)findViewById(R.id.iv_bg);
        button=(Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap mBitmap= BitmapFactory.decodeResource(getResources(),R.drawable.blur01);
                iv_bg.setImageBitmap(blurLayout(mBitmap));
            }
        });

    }
    Bitmap mBlurBitmap=null;
    Canvas mCanvas=null;
    Paint drawPaint=null;
    float scaleFactor = 12;
    public Bitmap blurLayout(Bitmap nBitmap){
        long startTime = System.nanoTime();  //开始时间
        if(mBlurBitmap==null) {
            mBlurBitmap = Bitmap.createBitmap((int)(nBitmap.getWidth()/scaleFactor), (int)(nBitmap.getHeight()/scaleFactor), Bitmap.Config.ARGB_4444);
        }
        mBlurBitmap.eraseColor(Color.TRANSPARENT);
        if(mCanvas==null){
            mCanvas=new Canvas(mBlurBitmap);
        }
        if(drawPaint==null){
            drawPaint=new Paint();
            drawPaint.setFlags(Paint.FILTER_BITMAP_FLAG);
        }
        mCanvas.save();
        //将图片缩小到原来的1/8
        mCanvas.scale(1 / scaleFactor, 1 / scaleFactor);
        //画壁纸
        mCanvas.drawBitmap(nBitmap,0,0,drawPaint);
        mCanvas.restore();

        Bitmap tempBitmap= FlyBlurUtil.boxBlurFilter(mBlurBitmap);
        long consumingTime = System.nanoTime() -startTime; //消耗时间

        Log.d("blurLayout", "blur_bg:" + consumingTime / 1000000);
        return tempBitmap;
    }
}
