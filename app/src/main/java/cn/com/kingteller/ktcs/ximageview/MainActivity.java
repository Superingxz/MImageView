package cn.com.kingteller.ktcs.ximageview;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.lzy.okgo.OkGo;

import cn.com.kingteller.ktcs.ximageview.utils.BitmapDialogCallback;
import cn.com.kingteller.ktcs.ximageview.view.DragImageView;
import cn.com.kingteller.ktcs.ximageview.view.XImageView;
import okhttp3.Call;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private DragImageView xImageView;
    private String imageUrl = "https://github.com/jeasonlzy/Screenshots/blob/master/okgo/logo4.jpg?raw=true";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xImageView = (DragImageView) findViewById(R.id.ximage);
        OkGo.getInstance().cancelTag(this);
        OkGo.get(imageUrl)//
                .tag(this)//
              /*  .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//*/

                .execute(new BitmapDialogCallback(this) {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        Glide.with(MainActivity.this).load(imageUrl)//
                                .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                                .error(R.drawable.ic_default_color)//
                                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                                .into(xImageView);
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });
    }

    public void rotateClick(View view) {
        switch (view.getId()) {
            case R.id.ssz:
              //  xImageView.doRotate(0.5f);
                break;
            case R.id.nsz:
             //   xImageView.doRotate(-0.25f);
                break;
            default:
                break;
        }


    }
}
