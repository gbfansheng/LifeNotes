package com.threef.lifenotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @ClassName : CoordinatesView
 * @author : ZGX zhangguoxiao_happy@163.com
 *
 */
public class CoordinatesView extends View {

    /*
     * 自定义控件一般写两个构造方法 CoordinatesView(Context context)用于java硬编码创建控件
     * 如果想要让自己的控件能够通过xml来产生就必须有第2个构造方法 CoordinatesView(Context context,
     * AttributeSet attrs) 因为框架会自动调用具有AttributeSet参数的这个构造方法来创建继承自View的控件
     */
    public CoordinatesView(Context context) {
        super(context, null);
    }

    public CoordinatesView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 标准分数据
     */
    Float nTScore;
    Float eTScore;
    Point resultPoint = new Point();
    Float ratio;
    public void setResult(float eTScore, float nTScore) {
        this.eTScore = eTScore;
        this.nTScore = nTScore;
    }

    /*
     * 圆心（坐标值是相对与控件的左上角的）
     */
    Point po = new Point();
    /*
     * 控件的中心点
     */
    int centerX, centerY;
    int divide;
    /*
     * 控件创建完成之后，在显示之前都会调用这个方法，此时可以获取控件的大小 并得到中心坐标和坐标轴圆心所在的点。
     */
    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        centerX = w / 2;
        centerY = h / 2;
        divide = w / 6;
        ratio = ((float) w) / 60;
        po.set(centerX, centerY);
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*
     * 自定义控件一般都会重载onDraw(Canvas canvas)方法，来绘制自己想要的图形
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("EPQ","onDraw");
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setStrokeWidth(2);

        // 画坐标轴
        if (canvas != null) {
            canvas.drawColor(Color.WHITE);
            // 画直线
            canvas.drawLine(0, centerY, 2 * centerX, centerY, paint);
            canvas.drawLine(centerX, 0, centerX, 2 * centerY, paint);
            //画标尺
            for (int i = 0 ; i < 6 ; i ++) {
                canvas.drawLine(centerX + 1, (i + 1) * divide, centerX + 16, (i + 1) * divide, paint);
                canvas.drawLine(i * divide, centerY - 1, i * divide, centerY - 16, paint);
                if (i != 2) {
                    canvas.drawText(""+(70 - i * 10 ),centerX - 42,(i + 1) * divide, paint);
                }
                if (i != 3) {
                    canvas.drawText(""+(i * 10 + 20),i * divide,centerY + 23,paint);
                }
            }

            // 画X轴箭头
            int x = 2 * centerX, y = centerY;
            drawTriangle(canvas, new Point(x, y), new Point(x - 10, y - 5),
                    new Point(x - 10, y + 5));
            canvas.drawText("E维度", x - 80, y - 20, paint);
            // 画Y轴箭头
            x = centerX;
            y = 0;
            drawTriangle(canvas, new Point(x, y), new Point(x - 5, y + 10),
                    new Point(x + 5, y + 10));
            canvas.drawText("N纬度", x + 12, y + 25, paint);

            canvas.drawText("内向、不稳定", 10,30, paint);
            canvas.drawText("抑郁质",10,60, paint);
            canvas.drawText("外向、不稳定", 2 * centerX - 200, 30, paint);
            canvas.drawText("胆汁质", 2 * centerX - 200, 60, paint);
            canvas.drawText("内向、稳定", 10, 2 * centerY - 50, paint);
            canvas.drawText("粘液质", 10, 2 * centerY - 20, paint);
            canvas.drawText("外向、稳定", 2 * centerX - 200, 2 * centerY - 50, paint);
            canvas.drawText("多血质", 2 * centerX - 200, 2 * centerY - 20, paint);
            // 画中心点坐标
            // 先计算出来当前中心点坐标的值
//            String centerString = "(" + (centerX - po.x) / 2 + ","
//                    + (po.y - centerY) / 2 + ")";
//            // 然后显示坐标
//            canvas.drawText(centerString, centerX - 25, centerY + 15, paint);

        }

        if (canvas != null) {
            /*
             * TODO 画数据 所有外部需要在坐标轴上画的数据，都在这里进行绘制
             */
//            canvas.drawColor(Color.RED);
            Log.d("EPQ","ratio:"+ratio);
            resultPoint.set((int) ((eTScore - 50) * ratio), (int) ((50 - nTScore) * ratio));
            Log.d("EPQ","x:"+resultPoint.x+"y:"+resultPoint.y);
            canvas.drawCircle(po.x + resultPoint.x, po.y + resultPoint.y, 10, paint);
//            canvas.drawCircle(po.x + 2 * pb.x, po.y - 2 * pb.y, 2, paint);
//            canvas.drawLine(po.x + 2 * pa.x, po.y - 2 * pa.y, po.x + 2 * pb.x,
//                    po.y - 2 * pb.y, paint);
            // canvas.drawPoint(pa.x+po.x, po.y-pa.y, paint);
        }

    }

    /**
     * 画三角形 用于画坐标轴的箭头
     */
    private void drawTriangle(Canvas canvas, Point p1, Point p2, Point p3) {
        Path path = new Path();
        path.moveTo(p1.x, p1.y);
        path.lineTo(p2.x, p2.y);
        path.lineTo(p3.x, p3.y);
        path.close();

        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        // 绘制这个多边形
        canvas.drawPath(path, paint);
    }
    /*
     * 用于保存拖动时的上一个点的位置
     */
//    int x0, y0;

    /*
     * 拖动事件监听
     */
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        int action = event.getAction();
//        /*
//         * (x,y)点为发生事件时的点，它的坐标值为相对于该控件左上角的距离
//         */
//        int x = (int) event.getX();
//        int y = (int) event.getY();
//        switch (action) {
//
//            case MotionEvent.ACTION_DOWN: // 按下
//                x0 = x;
//                y0 = y;
//                Log.i("down", "(" + x0 + "," + y0 + ")");
//                break;
//            case MotionEvent.ACTION_MOVE: // 拖动
//            /*
//             * 拖动时圆心坐标相对运动 (x0,y0)保存先前一次事件发生的坐标
//             * 拖动的时候只要计算两个坐标的delta值，然后加到圆心中，即移动了圆心。
//             * 然后调用invalidate()让系统调用onDraw()刷新以下屏幕，即实现了坐标移动。
//             */
//                po.x += x - x0;
//                po.y += y - y0;
//                x0 = x;
//                y0 = y;
//                Log.i("move", "(" + x0 + "," + y0 + ")");
//                invalidate();
//                break;
//            case MotionEvent.ACTION_UP: // 弹起
//                break;
//        }
//
//        /*
//         * 注意：这里一定要返回true
//         * 返回false和super.onTouchEvent(event)都会本监听只能检测到按下消息
//         * 这是因为false和super.onTouchEvent(event)的处理都是告诉系统该控件不能处理这样的消息，
//         * 最终系统会将这些事件交给它的父容器处理。
//         */
//        return true;
//
//    }
}
