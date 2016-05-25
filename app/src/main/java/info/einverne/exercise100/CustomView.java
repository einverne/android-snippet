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


    private String hintString;
    private int exampleColor = Color.RED;
    private float exampleDimension = 0;
    private Drawable exampleDrawable;

    private TextPaint textPaint;
    private float textWidth;
    private float textHeight;

    // circle related attr
    private Paint paint;
    private PointF circlePosition = new PointF(40, 40);             // Circle position
    private float radius = 30;                                     // Circle radisu
    private int circleColor = Color.WHITE;     // Circle color

    private String operation;                   // operation way to move Circle

    private Context context;
    private SensorManager sm;

    private int contentWidth;                   // content Width of View
    private int contentHeight;                  // content Height of View
    private int paddingLeft;
    private int paddingTop;
    public final static String TAG = "EV_VIEW_TAG";

    public CustomView(Context context) {
        super(context);
        this.context = context;
        init(null, 0);
    }

    public CustomView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        init(attrs, 0);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        Log.d(TAG, "init");
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.CustomView, defStyle, 0);

        hintString = a.getString(
                R.styleable.CustomView_exampleString);
        exampleColor = a.getColor(
                R.styleable.CustomView_exampleColor,
                exampleColor);

        circleColor = a.getColor(
                R.styleable.CustomView_CircleColor,
                circleColor
        );

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        exampleDimension = a.getDimension(
                R.styleable.CustomView_exampleDimension,
                exampleDimension);

        if (a.hasValue(R.styleable.CustomView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                    R.styleable.CustomView_exampleDrawable);
            if (exampleDrawable != null) {
                exampleDrawable.setCallback(this);
            }
        }

        a.recycle();


        // Set up a default TextPaint object
        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

        // Set up Paint()

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(circleColor);


        // read from preferences settings
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
        String color_of_circle = settings.getString(KEY_COLOR_OF_CIRCLE, "1");
        int size_of_circle = settings.getInt(KEY_SIZE_OF_CIRCLE, 30);
        operation = settings.getString(KEY_OPERATION, "1");

        switch (color_of_circle) {
            case "1":
                circleColor = Color.WHITE;
                break;
            case "2":
                circleColor = (Color.RED);
                break;
            case "3":
                circleColor = (Color.BLUE);
                break;
            case "4":
                circleColor = (Color.YELLOW);
                break;
            default:
                circleColor = (Color.WHITE);
        }
        radius = size_of_circle;

        SharedPreferences pos_pref = context.getSharedPreferences("position", Context.MODE_PRIVATE);

        circlePosition.x = pos_pref.getFloat("x", 40);
        circlePosition.y = pos_pref.getFloat("y", 40);

        sm = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
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
        pos_sharedPreferences = context.getSharedPreferences("position", Context.MODE_PRIVATE);
        editor = pos_sharedPreferences.edit();


        editor.putFloat("x", circlePosition.x);
        editor.putFloat("y", circlePosition.y);
        editor.apply();

    }

    private void invalidateTextPaintAndMeasurements() {
        textPaint.setTextSize(exampleDimension);
        textPaint.setColor(exampleColor);
        textWidth = textPaint.measureText(hintString);

        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Draw the text.
        canvas.drawText(hintString,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + textHeight * 4,
                textPaint);

        float x = circlePosition.x;
        float y = circlePosition.y;
        if (x - radius <= 0) {
            circlePosition.x = radius;
        } else if (x + radius > contentWidth) {
            circlePosition.x = contentWidth - radius;
        }
        if (y - radius <= 0) {
            circlePosition.y = radius;
        } else if (y + radius > contentHeight) {
            circlePosition.y = contentHeight - radius;
        }

        paint.setColor(circleColor);
        canvas.drawCircle(circlePosition.x, circlePosition.y, radius, paint);

        // Draw the example drawable on top of the text.
        if (exampleDrawable != null) {
            exampleDrawable.setBounds(paddingLeft, paddingTop,
                    paddingLeft + contentWidth, paddingTop + contentHeight);
            exampleDrawable.draw(canvas);
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
                    circlePosition.set(x, y);
                    invalidate();
                    break;
                case MotionEvent.ACTION_MOVE:
                    circlePosition.set(x, y);
                    invalidate();

                    break;
                case MotionEvent.ACTION_UP:
                    circlePosition.set(x, y);
                    invalidate();

                    break;
            }

            // Circle 不出界
            if (x - radius <= 0) {
                Log.d(TAG, "onTouchEvent x - <=0");
                circlePosition.x = radius;
            } else if (x + radius > contentWidth) {
                circlePosition.x = contentWidth - radius;
                Log.d(TAG, "onTouchEvent x + > width");
            }
            if (y - radius <= 0) {
                circlePosition.y = radius;
                Log.d(TAG, "onTouchEvent y < 0");
            } else if (y + radius > contentHeight) {
                circlePosition.y = contentHeight - radius;
                Log.d(TAG, "onTouchEvent y > height");
            }
            Log.d(TAG, "x,y" + circlePosition.x + circlePosition.y);
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

            PointF p = circlePosition;
            p.x = p.x - ax * 3;
            p.y = p.y + ay * 3;

            setCirclePosition(p);

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
        return hintString;
    }

    /**
     * Sets the view's example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleString(String exampleString) {
        hintString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    @SuppressWarnings("unused")
    public int getExampleColor() {
        return exampleColor;
    }

    /**
     * Sets the view's example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleColor(int exampleColor) {
        this.exampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    @SuppressWarnings("unused")
    public float getExampleDimension() {
        return exampleDimension;
    }

    /**
     * Sets the view's example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleDimension(float exampleDimension) {
        this.exampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    @SuppressWarnings("unused")
    public Drawable getExampleDrawable() {
        return exampleDrawable;
    }

    /**
     * Sets the view's example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    @SuppressWarnings("unused")
    public void setExampleDrawable(Drawable exampleDrawable) {
        this.exampleDrawable = exampleDrawable;
    }

    /**
     * 获取圆的原色
     *
     * @return 颜色
     */
    @SuppressWarnings("unused")
    public int getCircleColor() {
        return circleColor;
    }

    /**
     * 设置圆的颜色
     *
     * @param circleColor 颜色
     */
    @SuppressWarnings("unused")
    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
        invalidate();
    }

    /**
     * 获取圆的位置
     *
     * @return 位置
     */
    @SuppressWarnings("unused")
    public PointF getCirclePosition() {
        return circlePosition;
    }

    /**
     * 设置圆的位置
     *
     * @param circlePosition 位置
     */
    @SuppressWarnings("unused")
    public void setCirclePosition(PointF circlePosition) {

        float x = circlePosition.x;
        float y = circlePosition.y;
        if (x - radius <= 0) {
            circlePosition.x = radius;
        } else if (x + radius > contentWidth) {
            circlePosition.x = contentWidth - radius;
        }
        if (y - radius <= 0) {
            circlePosition.y = radius;
        } else if (y + radius > contentHeight) {
            circlePosition.y = contentHeight - radius;
        }
        if (x > radius && x < contentWidth - radius &&
                y > radius && y < contentHeight - radius) {
            this.circlePosition = circlePosition;
        }
        invalidate();
    }

    /**
     * 获取圆的半径
     *
     * @return 半径
     */
    @SuppressWarnings("unused")
    public float getRadius() {
        return radius;
    }

    /**
     * 设置圆的半径
     *
     * @param radius 半径
     */
    @SuppressWarnings("unused")
    public void setRadius(float radius) {
        this.radius = radius;
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
