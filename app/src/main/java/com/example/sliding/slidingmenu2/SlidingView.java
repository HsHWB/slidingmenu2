package com.example.sliding.slidingmenu2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * Created by bingoo on 2015/11/3.
 */
public class SlidingView extends HorizontalScrollView {

    private ViewGroup leftItemView;
    private ViewGroup contentView;
    private LinearLayout mainLinear;
    private float screenWidth;
    private float screenHeight;
    private int menuWidth;
    private boolean menuState = false;

    public SlidingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.screenHeight = WindowsUtlls.getWindowHeight(context);
        this.screenWidth = WindowsUtlls.getWindowWidth(context);
    }

    /**
     * 定义view的大小
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        /**
         * 获取view对象
         */
        mainLinear = (LinearLayout)this.getChildAt(0);
        leftItemView = (ViewGroup) mainLinear.getChildAt(0);
        contentView = (ViewGroup) mainLinear.getChildAt(1);
        menuWidth = leftItemView.getWidth();
        /**
         * contentView宽度设置为屏幕宽度
         */
        LinearLayout.LayoutParams contentViewll = new LinearLayout.LayoutParams(
                (int) this.screenWidth,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        contentView.setLayoutParams(contentViewll);

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 定义在父布局中的位置
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        menuClose();
        super.onLayout(changed, l, t, r, b);
    }

    /**
     * action_up:
     * scrollX > menuWidth/2 && !menuState :当meun为close时，触发
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int scrollX = getScrollX();
                if (scrollX > menuWidth/2 && !menuState){
                    super.smoothScrollTo(0, 0);
                    return true;
                }
                return true;
        }

        return super.onTouchEvent(ev);
    }

    /**
     * 滑动过程中的处理
     * @param l
     * @param t
     * @param oldl
     * @param oldt
     */
    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt){

    }

    /**
     * menu的状态和动作
     */
    private void menuOpen(){
        super.smoothScrollTo(0,0);
        menuState = true;
    }

    private void menuClose(){
        super.smoothScrollTo(menuWidth,0);
        menuState = false;
    }
}
