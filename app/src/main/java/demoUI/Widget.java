package demoUI;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

import java.util.ArrayList;

/**
 * Created by mark on 11/4/16.
 */

public class Widget {
    private static Paint Paint                  = new Paint();
    private static ArrayList<Widget> Widgets    = new ArrayList<Widget>();

    private Rect bounds                 = null;
    private ArrayList<Widget> children  = new ArrayList<Widget>();
    private int lineColor               = Color.WHITE;
    private int fillColor               = Color.BLACK;
    private float vAnchor               = 0.5f;
    private float hAnchor               = 0.5f;
    private int lineWidth               = 1;

    // Interface ///////////////////////////////////////////////////////////////////////////////////
    public static void Add(Widget newWidget) {
        Widgets.add(newWidget);
    }

    public static void Remove(Widget oldWidget) {
        Widgets.remove(oldWidget);
    }

    public static void Draw(Canvas canvas) {
        for (int i=0; i<Widgets.size(); ++i) {
            Widgets.get(i).drawSelf(canvas, 0, 0);
        }
    }

    public static Widget WidgetAt(int x, int y) {
        Widget foundWidget = null;

        for (int i=0; i<Widgets.size(); ++i) {
            foundWidget = Widgets.get(i).getContainer(x, y);
            if (foundWidget != null) {
                break;
            }
        }

        return foundWidget;
    }

    public Widget(int x, int y, int width, int height, float hAnchor, float vAnchor) {
        this.vAnchor = vAnchor;
        this.hAnchor = hAnchor;

        int top = Math.round(y - vAnchor * height);
        int left = Math.round(x - hAnchor * width);
        int bottom = top + height;
        int right = left + width;

        bounds = new Rect(top, left, bottom, right);
    }

    public void setLineWidth(int newWidth) {
        lineWidth = Math.max(newWidth, 0);
    }

    public Rect getBounds() {
        return bounds;
    }

    public void touchStart() {
        // Override to provide custom functionality.
    }

    public void touchEnd() {
        // Override to provide custom functionality.
    }

    public int getTop() {
        return bounds.top;
    }

    public int getLeft() {
        return bounds.left;
    }

    public void setColor(int newLineColor, int newFillColor) {
        lineColor = newLineColor;
        fillColor = newFillColor;
    }

    public void drawSelf(Canvas canvas, int originX, int originY) {
        draw(canvas);

        bounds.left += originX;
        bounds.top += originY;

        drawChildren(canvas);

        bounds.left -= originX;
        bounds.top -= originY;

    }

    public void drawChildren(Canvas canvas) {
        for (int i=0; i<children.size(); ++i) {
            children.get(i).drawSelf(canvas, getLeft(), getTop());
        }
    }

    // Implementation //////////////////////////////////////////////////////////////////////////////
    protected void draw(Canvas canvas) {

        if (lineWidth > 0) {
            Paint.setColor(lineColor);
            canvas.drawRect(bounds.left - lineWidth,
                            bounds.top - lineWidth,
                            bounds.right + lineWidth,
                            bounds.bottom + lineWidth,
                            Paint);
        }

        Paint.setColor(fillColor);
        canvas.drawRect(bounds.left, bounds.top, bounds.right, bounds.bottom, Paint);
    }

    protected Widget getContainer(int x, int y) {
        Widget container = null;

        if (contains(x, y)) {
            container = this;

            for (int i=0; i<children.size(); ++i) {
                if (children.get(i).contains(x - bounds.left, y - bounds.top)) {
                    container = children.get(i).getContainer(x - bounds.left, y - bounds.top);
                    break;
                }
            }
        }

        return container;
    }

    private boolean contains(int x, int y) {
        return x >= bounds.left &&
                y >= bounds.top &&
                x <= bounds.right &&
                y <= bounds.bottom;
    }
}
