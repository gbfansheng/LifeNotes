package com.threef.lifenotes;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by shenglinfan on 16/3/6.
 */
public class TScoreView  extends View {

    /*
     * 自定义控件一般写两个构造方法 CoordinatesView(Context context)用于java硬编码创建控件
     * 如果想要让自己的控件能够通过xml来产生就必须有第2个构造方法 CoordinatesView(Context context,
     * AttributeSet attrs) 因为框架会自动调用具有AttributeSet参数的这个构造方法来创建继承自View的控件
     */
    public TScoreView(Context context) {
        super(context, null);
    }

    public TScoreView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /*
     * 标准分数据
     */
    Float pScore;
    Float eScore;
    Float nScore;
    Float lScore;

    public void  setTScore(Float pScore,Float eScore,Float nScore,Float lScore) {
        this.pScore = pScore;
        this.eScore = eScore;
        this.nScore = nScore;
        this.lScore = lScore;
    }

    float width;
    float height;
    float sWidth;
    float sHeight;
    float sOffsetX;
    float sOffsetY;
    float wDivide;
    float hDivide;

    @Override
    public void onSizeChanged(int w, int h, int oldw, int oldh) {
        float fw = (float)w;
        float fh = (float)h;
        width = fw;
        height = fh;
        sWidth = fw / 7 * 5;
        sHeight = sWidth;
        wDivide = sWidth / 5;
        hDivide = sHeight / 12;
        sOffsetX = (fw - sWidth) / 2;
        sOffsetY = (fh - sHeight) - 1;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    /*
     * 自定义控件一般都会重载onDraw(Canvas canvas)方法，来绘制自己想要的图形
     */
    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(30);
        paint.setStrokeWidth(2);

        if (canvas != null) {
            //线框
            canvas.drawLine(sOffsetX,sOffsetY,sOffsetX + sWidth,sOffsetY,paint);
            canvas.drawLine(sOffsetX,sOffsetY,sOffsetX,sOffsetY + sHeight,paint);
            canvas.drawLine(sOffsetX + sWidth,sOffsetY,sOffsetX + sWidth,sOffsetY + sHeight,paint);
            canvas.drawLine(sOffsetX,sOffsetY + sHeight,sOffsetX + sWidth,sOffsetY + sHeight,paint);
            //标尺
            for (int i = 0 ; i < 4 ; i++) {
                canvas.drawLine(sOffsetX + (i + 1) * wDivide, sOffsetY, sOffsetX + (i + 1) * wDivide, sOffsetY + sHeight , paint);
            }

            for (int i = 0 ; i < 5 ; i++) {
                canvas.drawText(""+(60 - i * 5),sOffsetX - 60 ,sOffsetY + (i + 4) * hDivide + 5,paint);
                canvas.drawText(""+(60 - i * 5),sOffsetX + sWidth + 10 ,sOffsetY + (i + 4) * hDivide + 5,paint);
                canvas.drawLine(sOffsetX - 10,sOffsetY + (i + 4) * hDivide,sOffsetX + 10 ,sOffsetY + (i + 4) * hDivide , paint);
                canvas.drawLine(sOffsetX + sWidth - 10 , sOffsetY + (i + 4) * hDivide, sOffsetX + sWidth + 10 ,sOffsetY + (i + 4) * hDivide, paint);

            }
            canvas.drawLine(sOffsetX, sOffsetY + sHeight / 2, sOffsetX + sWidth, sOffsetY + sHeight / 2, paint);

            //字
            canvas.drawText("P", sOffsetX +     wDivide - 15, sOffsetY - 10 , paint);
            canvas.drawText("E", sOffsetX + 2 * wDivide - 15, sOffsetY - 10 , paint);
            canvas.drawText("N", sOffsetX + 3 * wDivide - 15, sOffsetY - 10, paint);
            canvas.drawText("L", sOffsetX + 4 * wDivide - 15, sOffsetY - 10, paint);

            canvas.drawText("T分", sOffsetX - 60, sOffsetY + 5, paint);

            //点
            canvas.drawCircle(sOffsetX +     wDivide, sOffsetY + (80 - pScore) / 5 * hDivide, 10, paint);
            canvas.drawCircle(sOffsetX + 2 * wDivide, sOffsetY + (80 - eScore) / 5 * hDivide, 10, paint);
            canvas.drawCircle(sOffsetX + 3 * wDivide, sOffsetY + (80 - nScore) / 5 * hDivide, 10, paint);
            canvas.drawCircle(sOffsetX + 4 * wDivide, sOffsetY + (80 - lScore) / 5 * hDivide, 10, paint);
            canvas.drawLine(sOffsetX +     wDivide, sOffsetY + (80 - pScore) / 5 * hDivide,sOffsetX + 2 * wDivide, sOffsetY + (80 - eScore) / 5 * hDivide,paint);
            canvas.drawLine(sOffsetX + 2 * wDivide, sOffsetY + (80 - eScore) / 5 * hDivide,sOffsetX + 3 * wDivide, sOffsetY + (80 - nScore) / 5 * hDivide,paint);
            canvas.drawLine(sOffsetX + 3 * wDivide, sOffsetY + (80 - nScore) / 5 * hDivide,sOffsetX + 4 * wDivide, sOffsetY + (80 - lScore) / 5 * hDivide,paint);

        }

    }
}
