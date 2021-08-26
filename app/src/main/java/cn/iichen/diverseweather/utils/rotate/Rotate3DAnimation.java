package cn.iichen.diverseweather.utils.rotate;

import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.Transformation;

//google提供的apidemo工程中 修改
public class Rotate3DAnimation extends Animation {
    private int mCenterX,mCenterY;
    private Camera mCamera;
    private float mFromDegrees,mToDegrees;
    private AnimationUpdateListener updateListener;
    private int mWidth;

    public AnimationUpdateListener getUpdateListener() {
        return updateListener;
    }

    public void setUpdateListener(AnimationUpdateListener updateListener) {
        this.updateListener = updateListener;
    }

    public Rotate3DAnimation(float mFromDegrees, float mToDegrees) {
        this.mFromDegrees = mFromDegrees;
        this.mToDegrees = mToDegrees;
    }

    @Override
    public void initialize(int width, int height, int parentWidth, int parentHeight) {
        super.initialize(width, height, parentWidth, parentHeight);
        mWidth = width;
        mCenterX = width / 2;
        mCenterY = height / 2;
        mCamera = new Camera();
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        float degress = mFromDegrees + (interpolatedTime *  (mToDegrees - mFromDegrees));

        if(updateListener != null){
            updateListener.onProgressUpdate(interpolatedTime,degress);
        }
        Matrix matrix = t.getMatrix();
        // 通过改变Camera的“眼睛”来显示不同的屏幕显示效果
        mCamera.save();
        //让旋转90度的时候不显的太大
        if(interpolatedTime >= 0.5){
            mCamera.translate(0,0,(Math.abs(interpolatedTime - 1) / 0.5f) * mWidth / 2);
        }else {
            mCamera.translate(0,0,(interpolatedTime / 0.5f) * mWidth / 2);
        }
        //图形绕Y轴旋转  可以理解为源 控件不动咱们绕着 控件旋转去 看。MAME看到的就不一样了
        mCamera.rotateY(degress);
        // 没有Canvas需要借助Matrix未知信息
        mCamera.getMatrix(matrix);
        //将原点移动到中心处
        // 因为上面的效果作用于(0,0)坐标。所以需要先将其移动到中心，所以先pre.即吧组件的中西移到(0,0)
        matrix.preTranslate(-mCenterX,-mCenterY);
        // 上一步作用于效果且中心到(0,0) 需要将其在吧中心回到 画面中心。所以再 post
        matrix.postTranslate(mCenterX,mCenterY);
//        ==> pre作用上一步效果前先 xxx
//        ==> post作用于上一个效果后 xxx
        mCamera.restore();

        super.applyTransformation(interpolatedTime, t);
    }

    /**
     * 动画更新的回调
     */
    public interface AnimationUpdateListener{
        /**
         * 进度回调
         */
        void onProgressUpdate(float progress,float value);
    }
}
