package cn.iichen.diverseweather.utils.rotate;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;


public class Rotate3DLayout extends FrameLayout {
    private DefaultLayout mDefaultLayout;
    private ReverseLayout mReverseLayout;
    private Rotate3DAnimation startRotate3DAnimation;//旋转动画（转到反面）
    private Rotate3DAnimation endRotate3DAnimation;//反转动画（转回正面）
    public Rotate3DLayout(@NonNull Context context) {
        this(context,null);
    }

    public Rotate3DLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public Rotate3DLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        initAnim();
    }
    private void initAnim() {
        startRotate3DAnimation = new Rotate3DAnimation(0, 180);
        startRotate3DAnimation.setDuration(500);
        startRotate3DAnimation.setInterpolator(new LinearInterpolator());
        //保持执行动画后的样子
        startRotate3DAnimation.setFillAfter(true);
        startRotate3DAnimation.setUpdateListener((float progress, float value)-> {
            //当旋转角度为90度的时候隐藏正面
            if(mDefaultLayout !=null && mReverseLayout != null && progress >= 0.5 && mDefaultLayout.getVisibility() == VISIBLE){
                mDefaultLayout.setVisibility(View.GONE);
                mReverseLayout.setVisibility(View.VISIBLE);
            }
        });

        endRotate3DAnimation = new Rotate3DAnimation(180, 0);
        endRotate3DAnimation.setDuration(500);
        endRotate3DAnimation.setInterpolator(new LinearInterpolator());
        //保持执行动画后的样子
        endRotate3DAnimation.setFillAfter(true);
        endRotate3DAnimation.setUpdateListener((float progress, float value)-> {
            //当旋转角度为90度的时候隐藏反面
            if(mDefaultLayout !=null && mReverseLayout != null && progress >= 0.5 && mReverseLayout.getVisibility() == VISIBLE){
                mDefaultLayout.setVisibility(View.VISIBLE);
                mReverseLayout.setVisibility(View.GONE);
            }
        });
    }
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            if(child instanceof DefaultLayout){
                mDefaultLayout = (DefaultLayout) child;
            }else if(child instanceof ReverseLayout){
                mReverseLayout = (ReverseLayout) child;
            }
            if(mDefaultLayout != null && mReverseLayout != null){
                break;
            }
        }
    }

    /**
     * 进行3D旋转
     */
    public void rotate3D(){
        if(mDefaultLayout != null && mReverseLayout != null){
            if(mDefaultLayout.getVisibility() == View.VISIBLE){
                startAnimation(startRotate3DAnimation);
            }else {
                startAnimation(endRotate3DAnimation);
            }
        }
    }
}


/**
 <com.example.rotate3dlayout.widget.Rotate3DLayout
     android:id="@+id/rl_rotate"
     android:background="@drawable/shape_bg"
     android:padding="10dp"
     android:layout_gravity="center_horizontal"
     android:layout_marginTop="20dp"
     android:layout_width="300dp"
     android:layout_height="400dp">
     <com.example.rotate3dlayout.widget.DefaultLayout
     android:layout_width="match_parent"
     android:layout_height="wrap_content">
         <LinearLayout
             android:orientation="vertical"
             android:layout_gravity="center"
             android:layout_width="match_parent"
             android:layout_height="wrap_content">
             <TextView
             android:text="我是正面"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"/>
             <Button
             android:text="我是正面"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"/>
             <EditText
             android:hint="我是正面"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"/>
         </LinearLayout>
     </com.example.rotate3dlayout.widget.DefaultLayout>
     <com.example.rotate3dlayout.widget.ReverseLayout
         android:layout_width="match_parent"
         android:layout_height="wrap_content">
         <LinearLayout
             android:orientation="vertical"
             android:layout_gravity="center"
             android:layout_width="match_parent"
             android:layout_height="wrap_content">
             <TextView
             android:text="我是反面"
             android:layout_width="wrap_content"
             android:layout_height="wrap_content"/>
             <Button
             android:text="我是反面"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"/>
             <EditText
             android:hint="我是反面"
             android:layout_width="match_parent"
             android:layout_height="wrap_content"/>
         </LinearLayout>
     </com.example.rotate3dlayout.widget.ReverseLayout>
 </com.example.rotate3dlayout.widget.Rotate3DLayout>
 **/