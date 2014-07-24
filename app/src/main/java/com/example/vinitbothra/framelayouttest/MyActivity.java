package com.example.vinitbothra.framelayouttest;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    private final long durationMilliseconds = 300;
    TextView textView;
    ListView listView;
    GestureDetector mGestureDetector;
    ViewGroup _root;
    //    private int _xDelta;
    private int yDistanceFromTop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        textView = (TextView) findViewById(R.id.textView1);
        _root = (ViewGroup) findViewById(R.id.relative);

        listView = (ListView) findViewById(R.id.list);
        final List<String> list = new ArrayList<String>();
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        list.add("test1");
        list.add("test2");
        list.add("test3");
        listView.setAdapter(new ArrayAdapter<String>(this, R.layout.list_text, R.id.textView, list));

        mGestureDetector = new GestureDetector(this,
                new GestureDetector.SimpleOnGestureListener() {
                    @Override
                    public boolean onFling(MotionEvent e1, MotionEvent e2,
                                           float velocityX, float velocityY) {
                        Log.i("onFling Velocity X :", String.valueOf(velocityX));
                        Log.i("onFling Velocity Y :", String.valueOf(velocityY));
                        if (velocityX < -10.0f) {
                            Log.i("****** : ", "got the fling with");
                        }
                        if (velocityY > 1000f) {
//                            listView.setTranslationY(100f);
//                            listView.setAnimation(new SlideRightAnimation());
//                            listView.startAnimation(slideRightAnimation);
                        }
                        return true;
                    }

                    @Override
                    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                        Log.i("onScroll Velocity X :", String.valueOf(listView.getY()));
                        Log.i("onScroll Velocity Y :", String.valueOf(distanceY));

                        if (distanceY > 5 || distanceY < -5) {
                            float newY = listView.getY();
                            // moving up - distanceY  will be positive
                            if (distanceY > 0 && listView.getY() >= 0) {
                                if (listView.getY() - distanceY >= 0)
                                    newY = (listView.getY() - distanceY);
                                else
                                    newY = 0;
                            } else if (distanceY < 0 && listView.getY() <= 400) {
                                // moving down - distanceY will be postive
                                if (listView.getY() - distanceY <= 400)
                                    newY = (listView.getY() - distanceY);
                                else
                                    newY = (400);

                            }
                            listView.setY(newY);
                            listView.invalidate();
                        }
                        return true;
                    }
                }
        );

        // http://stackoverflow.com/questions/9398057/android-move-a-view-on-touch-move-action-move
        listView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(final View view, MotionEvent event) {

                listView.getChildAt(0).getY();
                listView.getY();
                if(listView.getChildAt(0).getY() != 0 )
                    return false;

                final int yCurrentTouchedLocation = (int) event.getRawY();

                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        RelativeLayout.LayoutParams lParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        yDistanceFromTop = yCurrentTouchedLocation - lParams.topMargin;
                        break;

                    case MotionEvent.ACTION_UP:
                        final RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        int temp = layoutParam.topMargin;
                        layoutParam.topMargin = yCurrentTouchedLocation - yDistanceFromTop;
                        int a = textView.getHeight() + layoutParam.topMargin;

                        if (textView.getHeight() / 2 > a) {
                            layoutParam.topMargin = -textView.getHeight();
                            listView.startAnimation(new SlideRightAnimation(temp - layoutParam.topMargin, 0));
                        } else {
                            layoutParam.topMargin = 0;
                            listView.startAnimation(new SlideRightAnimation(temp, layoutParam.topMargin));
                        }
                        view.setLayoutParams(layoutParam);

                        break;

                    case MotionEvent.ACTION_POINTER_DOWN:
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        break;

                    case MotionEvent.ACTION_MOVE:
                        RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
                        layoutParams.topMargin = yCurrentTouchedLocation - yDistanceFromTop;
                        if (layoutParams.topMargin < -textView.getHeight()) {
                            layoutParams.topMargin = -textView.getHeight();
                            view.setLayoutParams(layoutParams);
                            return false;
                        } else if (layoutParams.topMargin > 0) {
                            layoutParams.topMargin = 0;
                        }

                        view.setLayoutParams(layoutParams);
                        break;
                }
                // not necessary to call invalidate. though just keep it.
//                _root.invalidate();
                return true;
            }
        });

        final boolean[] first = {true};
        textView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
//                Log.i("Log onGlobalLayout changed : ", String.valueOf(textView.getHeight()));
                if (first[0]) {
//                    listView.setPadding(0, textView.getHeight() + 10, 0, 0);
                    first[0] = false;
                }


            }
        });
        Log.i("Log oncreate changed : ", String.valueOf(textView.getHeight()));


    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//        return mGestureDetector.onTouchEvent(event);
//    }

    private class SlideRightAnimation extends TranslateAnimation {

        public SlideRightAnimation(int y, int displacementPixel) {
            super(0, 0, y, displacementPixel);
            setDuration(durationMilliseconds);
            setFillAfter(true);
        }

    }


}
