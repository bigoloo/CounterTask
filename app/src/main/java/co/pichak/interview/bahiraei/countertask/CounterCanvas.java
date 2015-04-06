package co.pichak.interview.bahiraei.countertask;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;

import co.pichak.interview.bahiraei.countertask.common.CounterTaskPreference;
import co.pichak.interview.bahiraei.countertask.common.NotificationUtils;

/**
 * Created by bigoloo on 4/6/15.
 */
public class CounterCanvas extends View {


    private int counter = 0;
    Paint paint = new Paint();
    Paint circlePaint = new Paint();
    Point centerPoint = new Point();
    float mCircleRadius = 0f;
NotificationUtils notificationUtils=new NotificationUtils();
    public CounterCanvas(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public CounterCanvas(Context context) {
        super(context);

    }

    public int getCurrent() {
        return counter;
    }

    public void increaseCounter() {
        counter++;
        invalidate();

    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (!this.isEnabled()) return false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                if (inCircle(event.getX(), event.getY(), centerPoint.x, centerPoint.y, mCircleRadius)) {

                    doActions();

                }
                return false;
            }

        }

        return false;


    }

    private void doActions() {

        increaseCounter();
        playSoundEffect(SoundEffectConstants.CLICK);
        notificationUtils.showNotification(getContext(),MainActivity.class,getCurrent());
        CounterTaskPreference.getCurrentCountPref(getContext()).saveCurrent(getCurrent());

    }

    private boolean inCircle(float x, float y, float circleCenterX, float circleCenterY, float circleRadius) {
        double dx = Math.pow(x - circleCenterX, 2);
        double dy = Math.pow(y - circleCenterY, 2);

        if ((dx + dy) < Math.pow(circleRadius, 2)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.rgb(255,69,0));

        circlePaint.setStrokeWidth(8);

        paint.setTextSize(getResources().getDimension(R.dimen.font_size));

        float mt = paint.measureText(counter + "");
        centerPoint.x = getWidth() / 2;
        centerPoint.y = getHeight() / 2;
        mCircleRadius = (getWidth()) / 3;

        //getWidth()/2-mt/2 ,getHeight()/2 is center of canvas 
        canvas.drawText(counter + "", centerPoint.x - mt / 2, centerPoint.y, paint);


        canvas.drawCircle(centerPoint.x, centerPoint.y, mCircleRadius, circlePaint);
    }

    public void setCurrent(int current) {
        this.counter = current;
        invalidate();
    }
}
