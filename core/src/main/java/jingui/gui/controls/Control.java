package jingui.gui.controls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;

import java.util.ArrayList;
import java.util.Arrays;

import static java.lang.String.format;

/**
 * 基控件类
 *
 * @apiNote
 */
public abstract class Control implements Render {
    /** 控件名称 */
    private String name;
    /** 控件类型 */
    private final String type;
    /** 父控件 */
    private Control parentControl;
    /** 子控件 */
    private final ArrayList<Control> childControl;
    /** 绝对坐标 */
    private float worldX, worldY;
    /** 相对坐标 */
    private float parentX, parentY;
    /** 缩放 */
    private float scalingX = 1, scalingY = 1;
    /** 旋转角度 */
    private float angle;
    /** 控件尺寸 */
    private float width, height;
    private boolean isFollowParent = true;
    private final Color color = new Color(1, 1, 1, 1);

    /**
     * 构造函数
     */
    public Control(String name, String type) {
        this.type = type;
        childControl = new ArrayList<>();
        this.name = name;
        adjustment();
    }

    public Control(String name){
        this(name, "Control");
    }

    public Control(){
        this("BasicControl");
    }

    /**
     * 获取控件旋转角度
     */
    public float getAngle() {
        return angle;
    }

    /** 获取控件名称 */
    public String getName() {
        return name;
    }

    /** 获取控件X轴坐标 */
    public float getX() {
        if (parentControl == null) return getWorldX();
        return parentX;
    }

    /** 获取控件Y轴坐标 */
    public float getY() {
        if (parentControl == null) return getWorldY();
        return parentY;
    }

    /**
     * 获取控件世界（绝对）坐标
     */
    public float getWorldX() {
        return worldX;
    }

    /**
     * 获取控件世界（绝对）坐标
     */
    public float getWorldY() {
        return worldY;
    }

    /** 获取控件X轴缩放 */
    public float getScalingX() {
        return scalingX;
    }

    /** 获取控件Y轴缩放 */
    public float getScalingY() {
        return scalingY;
    }

    /** 获取控件尺寸X */
    public float getWidth() {
        return width;
    }

    /** 获取控件尺寸Y */
    public float getHeight() {
        return height;
    }

    /** 获取控件类型 */
    public String getType() {
        return type;
    }

    /** 获取控件颜色 */
    public Color getColor() {
        return color;
    }

    /** 设置控件旋转角度 */
    public Control setAngle(float angle) {
        if (this.angle + angle > 360) {
            this.angle = this.angle + angle - 360;
            return this;
        }
        this.angle = angle;
        return this;
    }

    /** 设置控件名称 */
    public Control setName(String name) {
        this.name = name;
        return this;
    }

    /** 设置控件坐标 */
    public Control setPosition(float x, float y) {
        if (parentControl == null) {
            worldX = x;
            worldY = y;
        } else {
            parentX = x;
            parentY = y;
        }
        adjustment();
        return this;
    }

    /** 设置控件X轴坐标 */
    public Control setX(float x) {
        if (parentControl == null) {
            worldX = x;
        } else {
            parentX = x;
        }
        adjustment();
        return this;
    }

    /** 设置控件Y轴坐标 */
    public Control setY(float y) {
        if (parentControl == null) {
            worldY = y;
        } else {
            parentY = y;
        }
        adjustment();
        return this;
    }

    /** 增加/减少控件坐标 */
    public Control addPosition(float x, float y) {
        if (parentControl == null) {
            worldX += x;
            worldY += y;
        } else {
            parentX += x;
            parentY += y;
        }
        adjustment();
        return this;
    }

    /** 增加/减少控件X轴坐标 */
    public Control addX(float x) {
        if (parentControl == null) {
            worldX += x;
        } else {
            parentX += x;
        }
        adjustment();
        return this;
    }

    /** 增加/减少控件Y轴坐标 */
    public Control addY(float y) {
        if (parentControl == null) {
            worldY += y;
        } else {
            parentY += y;
        }
        adjustment();
        return this;
    }

    /** 设置控件缩放 */
    public Control setScaling(float Scaling) {
        scalingX = Scaling;
        scalingY = Scaling;
        adjustment();
        return this;
    }

    /** 设置控件X轴缩放 */
    public Control setScalingX(float x) {
        scalingX = x;
        adjustment();
        return this;
    }

    /** 设置控件Y轴缩放 */
    public Control setScalingY(float y) {
        scalingY = y;
        adjustment();
        return this;
    }

    /** 设置控件尺寸 */
    public Control setSize(float sizeX, float sizeY) {
        this.width = sizeX;
        this.height = sizeY;
        adjustment();
        return this;
    }

    /** 设置控件尺寸X */
    public Control setWidth(float width) {
        this.width = width;
        adjustment();
        return this;
    }

    /** 设置控件尺寸Y */
    public Control setHeight(float height) {
        this.height = height;
        adjustment();
        return this;
    }

    /** 设置控件颜色 */
    public void setColor(Color color) {
        this.color.set(color);
    }

    /** 设置控件颜色 */
    public void setColor(float r, float g, float b, float a) {
        color.set(r, g, b, a);
    }

    /**
     * 获取父控件
     */
    public Control getParent() {
        if (parentControl == null) {
            System.out.println("getParent: control is null");
            return null;
        }
        return parentControl;
    }

    /**
     * 设置父控件
     */
    public Control setParent(Control control) {
        parentControl = control;
        adjustment();
        return this;
    }

    /**
     * 移除父控件
     */
    public Control removeParent() {
        parentControl.removeChild(this);
        parentControl = null;
        parentX = 0;
        parentY = 0;
        adjustment();
        return this;
    }

    /**
     * 添加子控件
     */
    public Control addChild(Control... children) {
        for (Control child : children) {
            if (child == null) {
                System.err.println("addChild: child is null");
                return this;
            }
            child.getParent().removeChild(child);
            child.setParent(child);
        }
        childControl.addAll(Arrays.asList(children));
        adjustment();
        return this;
    }

    /**
     * 移除子控件
     */
    public void removeChild(Control child) {
        if (child == null) {
            System.err.println("removeChild: child is null");
            return;
        }
        childControl.remove(child);
        adjustment();
    }

    /**
     * 获取子控件
     */
    public ArrayList<Control> getChildControl() {
        return childControl;
    }

    /**
     * 调整
     */
    public void adjustment() {
        if (parentControl == null) {
            parentX = 0;
            parentY = 0;
        } else if (isFollowParent){
            worldX = parentControl.getWorldX() + parentX;
            worldY = parentControl.getWorldY() + parentY;
        }
        for (Control control : childControl) {
            control.adjustment();
        }
    }


    @Override
    public void render() {
        for (Control child : childControl) {
            child.render();
        }
    }

    @Override
    public void dispose() {
        for (Control child : childControl) {
            child.dispose();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        for (Control child : childControl) {
            child.draw(batch, parentAlpha);
        }
    }

    /** 判断是否是同一个控件 */
    @Override
    public boolean equals(Object control) {
        return control instanceof Control;
    }

    /**
     * 判断两个控件的名称是否相等
     */
    public boolean equalsName(Control control) {
        return name.equals(control.getName());
    }

    @Override
    public String toString(){
        return format(
            "[%s: [position: [X: %.2f%n], [Y: %.2f%n]], [worldPosition: [X: %.2f%n], [Y: %.2f%n]], [scaling: [X: %.2f%n], [Y: %.2f%n]]]",
            getName(), getX(), getY(), getWorldX(), getWorldY(), getScalingX(), getScalingY());
    }

    public boolean isFollowParent() {
        return isFollowParent;
    }

    public void isFollowParent(boolean followParent) {
        isFollowParent = followParent;
    }
}
