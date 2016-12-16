package cn.com.kingteller.ktcs.ximageview;

import android.graphics.Bitmap;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.WindowManager;

import com.lzy.okgo.OkGo;

import cn.com.kingteller.ktcs.ximageview.utils.BitmapDialogCallback;
import cn.com.kingteller.ktcs.ximageview.view.DragImageView;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 使用XImageView可以旋转 缩放 自动恢复
 * 使用DragImage只可以缩放  是能自动恢复
 */
public class MainActivity extends AppCompatActivity {

    private int window_width, window_height;// 控件宽度
    private DragImageView xImageView;// 自定义控件
    private int state_height;// 状态栏的高度

    private ViewTreeObserver viewTreeObserver;


    private String imageUrl = "http://n.sinaimg.cn/news/transform/20161216/dXCF-fxytqav9552909.jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        xImageView = (DragImageView) findViewById(R.id.ximage);

        /** 获取可見区域高度 **/
        WindowManager manager = getWindowManager();
        window_width = manager.getDefaultDisplay().getWidth();
        window_height = manager.getDefaultDisplay().getHeight();

        OkGo.getInstance().cancelTag(this);
        OkGo.get(imageUrl)//
                .tag(this)//
              /*  .headers("header1", "headerValue1")//
                .params("param1", "paramValue1")//*/

                .execute(new BitmapDialogCallback(this) {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        /*Glide.with(MainActivity.this).load(imageUrl)//
                                .override(500,300)
                                .placeholder(R.drawable.ic_default_color)// 这行貌似是glide的bug,在部分机型上会导致第一次图片不在中间
                                .error(R.drawable.ic_default_color)//
                                .diskCacheStrategy(DiskCacheStrategy.ALL)//
                                .into(xImageView);*/
                        xImageView.setImageBitmap(bitmap);

                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                    }
                });

        /** 测量状态栏高度 **/
        viewTreeObserver = xImageView.getViewTreeObserver();
        viewTreeObserver
                .addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {

                    @Override
                    public void onGlobalLayout() {
                        if (state_height == 0) {
                            // 获取状况栏高度
                            Rect frame = new Rect();
                            getWindow().getDecorView()
                                    .getWindowVisibleDisplayFrame(frame);
                            state_height = frame.top;
                            xImageView.setScreen_H(window_height-state_height);
                            xImageView.setScreen_W(window_width);
                        }

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
