package com.example.sliding.slidingmenu2;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.ListView;

/**
 * Created by bingoo on 2015/11/3.
 * 为了解决scrollview里嵌套listview时，listview偏离X轴滑动效果受影响
 */
public class ListViewForScrollView extends ListView {

    private float x_1;
    private float x_2;
    private float y_1;
    private float y_2;
    private boolean state = true;

    public ListViewForScrollView(Context context) {
        super(context);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ListViewForScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        float yBegin = ev.getY();
        float xBegin = ev.getX();
        System.out.println("xy =="+xBegin);

        switch (ev.getAction()) {
            // 当手指触摸listview时，让父控件交出ontouch权限,不能滚动
            case MotionEvent.ACTION_DOWN:
                x_1 = xBegin;
                y_1 = yBegin;
                setParentScrollAble(false);
                state = true;
            case MotionEvent.ACTION_MOVE:
//                Log.i("yScroll", String.valueOf(yScroll));
                x_2 = ev.getX();
                y_2 = ev.getY();
                System.out.println("xy =="+x_1);
//                Log.i("scroll", "Math.abs(y_2 - y_1) == "+String.valueOf(Math.abs(y_2 - y_1))+
//                        "           Math.abs(x_2 - x_1) == "+Math.abs(x_2 - x_1));
                if (Math.abs(y_2 - y_1) < 30 && Math.abs(x_2 - x_1) > 30 && state){
                    setParentScrollAble(true);
                }else if (Math.abs(y_2 - y_1) >= 30){
                    setParentScrollAble(false);
                    state = false;
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                // 当手指松开时，让父控件重新获取onTouch权限
                setParentScrollAble(true);
                state = true;
                break;

        }
        return super.onTouchEvent(ev);
    }

    // 设置父控件是否可以获取到触摸处理权限
    private void setParentScrollAble(boolean flag) {
        getParent().requestDisallowInterceptTouchEvent(!flag);
    }
}
