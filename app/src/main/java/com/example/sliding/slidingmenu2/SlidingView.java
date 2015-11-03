package com.example.sliding.slidingmenu2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
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
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        int action = ev.getAction();
        switch (action){
            case MotionEvent.ACTION_UP:
                int xScroll = getScrollX();
                //右拉，meun为close时，触发
                if (xScroll > menuWidth/2 *1.5 && !menuState){
                    menuClose();
                }
                //左拉,menu为open时，触发
                else if (xScroll <= menuWidth/2 *1.5  && !menuState){
                    menuOpen();
                }
                else if (xScroll <= menuWidth/4  && menuState){
                    menuOpen();
                }
                else if (xScroll > menuWidth/4  && menuState){
                    menuClose();
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
        super.onScrollChanged(l, t, oldl, oldt);
        float scale = l * 1.0f / menuWidth;
        leftItemView.setTranslationX(menuWidth * scale);
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
