package info.einverne.exercise100;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * view to draw circle
 * <p/>
 * Views are all drawn on the same GUI thread which is also used for all user interaction.
 * <p/>
 * So if you need to update GUI rapidly or if the rendering takes too much time and affects user experience then use SurfaceView.
 * <p/>
 * SurfaceView can be drawn on by background theads but Views can't
 */
public class CustomView extends View implements SensorEventListener {

    // List Preference key
    public final static String KEY_SIZE_OF_CIRCLE = "sizeOfCircle";
    public final static String KEY_COLOR_OF_CIRCLE = "colorOfCircle";
    public final static String KEY_OPERATION = "operation";


    private String mHintString;
    private int mExampleColor = Color.RED;
    private float mExampleDimension = 0;
    private Drawable mExampleDrawable;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    // circle related attr
    private Paint mPaint;
    private PointF mCirclePosition = new PointF(40, 40);             // Circle position
    private float mRadius = 30;                                     // Circle radisu
    private int mCircleColor = Color.WHITE;     // Circle color

    private String operation;                   // operation way to move Circle

    private Context context_;
    private SensorManager sm;

    private int contentWidth;                   // content Width of View
    private int contentHeight;                  // content Height of View
    private int paddingLeft;
    private int paddingTop;
    public final static String TAG = "EV_VIEW_TAG";

    public CustomView(Context context) {
        super(context);
        context_ = context;
        init(null, 0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        context_ = context;
        init(attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        context_ = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Log.d(TAG, "init");
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomView, defStyle, 0);

        mHintString = a.getString(
                R.styleable.CustomView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.CustomView_exampleColor,
                mExampleColor);

        mCircleColor = a.getColor(
                R.styleable.CustomView_CircleColor,
                mCircleColor
        );

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.CustomView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.CustomView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.CustomView_exampleDrawable);
            if (mExampleDrawable != null) {
                mExampleDrawable.setCallback(this);
            }
        }

        a.recycle();


        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Set up Paint()

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(mCircleColor);


        // read from preferences settings
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context_);
        String color_of_circle = settings.getString(KEY_COLOR_OF_CIRCLE, "1");
        int size_of_circle = settings.getInt(KEY_SIZE_OF_CIRCLE, 30);
        operation = settings.getString(KEY_OPERATION, "1");

        switch (color_of_circle) {
            case "1":
                mCircleColor = Color.WHITE;
                break;
            case "2":
                mCircleColor = (Color.RED);
                break;
            case "3":
                mCircleColor = (Color.BLUE);
                break;
            case "4":
                mCircleColor = (Color.YELLOW);
                break;
            default:
                mCircleColor = (Color.WHITE);
        }
        mRadius = size_of_circle;

        SharedPreferences pos_pref = context_.getSharedPreferences("position", Context.MODE_PRIVATE);

        mCirclePosition.x = pos_pref.getFloat("x", 40);
        mCirclePosition.y = pos_pref.getFloat("y", 40);

        sm = (SensorManager) context_.getSystemService(Context.SENSOR_SERVICE);
        Sensor gravitySensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sm.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_NORMAL);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    /**
     * View默认为可见的，不是默认值时先调用onVisibilityChanged()，但是此时该View的尺寸、位置等信息都不知道。
     * 这个函数在构造函数之前被调用
     * View 生命周期：http://www.jianshu.com/p/08e6dab7886e
     *
     * @param changedView view
     * @param visibility  visibility
     */
    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        Log.d(TAG, "onVisibilityChanged");
    }

    /**
     * 从XMl文件中inflate完成
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate");
    }

    /**
     * 附着窗口时触发
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        Log.d(TAG, "onAttachedToWindow");
    }

    /**
     * 确定所有子元素的大小
     *
     * @param widthMeasureSpec  width
     * @param heightMeasureSpec height
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure");
    }

    /**
     * 当View分配所有的子元素的大小和位置时触发
     *
     * @param changed boolean
     * @param left    int
     * @param top     int
     * @param right   int
     * @param bottom  int
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout");
        paddingLeft = getPaddingLeft();
        paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();
        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;
//        Log.d(TAG, "contentWidth: "+contentWidth);
//        Log.d(TAG, "contentHeight: "+contentHeight);

    }

    /**
     * 当view离开附着的窗口时触发
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        sm.unregisterListener(this);

        SharedPreferences pos_sharedPreferences;
        SharedPreferences.Editor editor;
        pos_sharedPreferences = context_.getSharedPreferences("position", Context.MODE_PRIVATE);
        editor = pos_sharedPreferences.edit();


        editor.putFloat("x", mCirclePosition.x);
        editor.putFloat("y", mCirclePosition.y);
        editor.apply();

    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mHintString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the text.
        canvas.drawText(mHintString,
                paddingLeft + (contentWidth - mTextWidth) / 2,
                paddingTop + mTextHeight * 4,
                mTextPaint);

        float x = mCirclePosition.x;
        float y = mCirclePosition.y;
        if (x - mRadius <= 0) {
            mCirclePosition.x = mRadius;
        } else if (x + mRadius > contentWidth) {
            mCirclePosition.x = contentWidth - mRadius;
        }
        if (y - mRadius <= 0) {
            mCirclePosition.y = mRadius;
        } else if (y + mRadius > contentHeight) {
            mCirclePosition.y = contentHeight - mRadius;
        }

        mPaint.setColor(mCircleColor);
        canvas.drawCircle(mCirclePosition.x, mCirclePosition.y, mRadius, mPaint);

        // Draw the example drawable on top of the text.
        if (mExampleDrawable != null) {
            mExampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            mExampleDrawable.draw(canvas);
        }
    }

    /**
     * 触屏事件
     *
     * @param event 事件
     * @return true or false
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (operation.equals("1")) {           // 如果重力不处理

            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mCirclePosition.set(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    mCirclePosition.set(x, y);
                    invalidate();

                    break;
                case MotionEvent.ACTION_UP:
                    mCirclePosition.set(x, y);
                    invalidate();

                    break;
            }

            // Circle 不出界
            if (x - mRadius <= 0) {
                Log.d(TAG, "onTouchEvent x - <=0");
                mCirclePosition.x = mRadius;
            } else if (x + mRadius > contentWidth) {
                mCirclePosition.x = contentWidth - mRadius;
                Log.d(TAG, "onTouchEvent x + > width");
            }
            if (y - mRadius <= 0) {
                mCirclePosition.y = mRadius;
                Log.d(TAG, "onTouchEvent y < 0");
            } else if (y + mRadius > contentHeight) {
                mCirclePosition.y = contentHeight - mRadius;
                Log.d(TAG, "onTouchEvent y > height");
            }
            Log.d(TAG, "x,y" + mCirclePosition.x + mCirclePosition.y);
        }
        return true;
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (operation.equals("2")) {
            // 如果手势不处理

            Log.d(TAG, "onSensorChanged");
            if (Sensor.TYPE_ACCELEROMETER != event.sensor.getType()) {
                return;
            }
            float[] values = event.values;
            float ax = values[0];
            float ay = values[1];

            PointF p = mCirclePosition;
            p.x = p.x - ax * 3;
            p.y = p.y + ay * 3;

            setmCirclePosition(p);

        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        Log.d(TAG, "onAccuracyChanged");

    }


    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    @SuppressWarnings("unused")
    public String getExampleString() {
        return mHintString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleString(String exampleString) {
        mHintString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    @SuppressWarnings("unused")
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    @SuppressWarnings("unused")
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    @SuppressWarnings("unused")
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }

    /**
     * 获取圆的原色
     *
     * @return 颜色
     */
    @SuppressWarnings("unused")
    public int getmCircleColor() {
        return mCircleColor;
    }

    /**
     * 设置圆的颜色
     *
     * @param mCircleColor 颜色
     */
    @SuppressWarnings("unused")
    public void setmCircleColor(int mCircleColor) {
        this.mCircleColor = mCircleColor;
        invalidate();
    }

    /**
     * 获取圆的位置
     *
     * @return 位置
     */
    @SuppressWarnings("unused")
    public PointF getmCirclePosition() {
        return mCirclePosition;
    }

    /**
     * 设置圆的位置
     *
     * @param mCirclePosition 位置
     */
    @SuppressWarnings("unused")
    public void setmCirclePosition(PointF mCirclePosition) {

        float x = mCirclePosition.x;
        float y = mCirclePosition.y;
        if (x - mRadius <= 0) {
            Log.d(TAG, "setmCirclePosition x - <=0");
            mCirclePosition.x = mRadius;
        } else if (x + mRadius > contentWidth) {
            mCirclePosition.x = contentWidth - mRadius;
            Log.d(TAG, "setmCirclePosition x + > width");
        }
        if (y - mRadius <= 0) {
            mCirclePosition.y = mRadius;
            Log.d(TAG, "setmCirclePosition y < 0");
        } else if (y + mRadius > contentHeight) {
            mCirclePosition.y = contentHeight - mRadius;
            Log.d(TAG, "setmCirclePosition y > height");
        }
        if (x > mRadius && x < contentWidth - mRadius &&
                y > mRadius && y < contentHeight - mRadius) {
            this.mCirclePosition = mCirclePosition;
        }
        invalidate();
    }

    /**
     * 获取圆的半径
     *
     * @return 半径
     */
    @SuppressWarnings("unused")
    public float getmRadius() {
        return mRadius;
    }

    /**
     * 设置圆的半径
     *
     * @param mRadius 半径
     */
    @SuppressWarnings("unused")
    public void setmRadius(float mRadius) {
        this.mRadius = mRadius;
        invalidate();
    }

    /**
     * 获取移动圆的方法，手势或者重力
     *
     * @return 操作方式
     */
    @SuppressWarnings("unused")
    public String getOperation() {
        return operation;
    }

    /**
     * 设置移动圆的方法，手势或者重力
     *
     * @param operation 操作方式
     */
    @SuppressWarnings("unused")
    public void setOperation(String operation) {
        this.operation = operation;
    }
}
