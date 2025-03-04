package jinGUI.gui.controls;

import jinGUI.Render;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * 控件基类
 *
 * @apiNote
 */
public abstract class Control implements Render {
    private String name;
    private Control parentControl; // 父控件
    private final ArrayList<Control> childControl; // 子控件
    private float worldX, worldY;     // 绝对坐标
    private float parentX, parentY;   // 相对坐标
    private float scalingX, scalingY; // 缩放
    private float angle;              // 旋转角度

    /**
     * 构造函数
     */
    public Control() {
        childControl = new ArrayList<>();
        name = "BasicControl";
        adjustment();
    }

    /**
     * 获取控件名称
     */
    public String getName() {
        return name;
    }

    public float getX() {
        if (parentControl == null) return getWorldX();
        return parentX;
    }

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

    public float getScalingX() {
        return scalingX;
    }

    public float getScalingY() {
        return scalingY;
    }

    public void setScaling(float Scaling) {
        scalingX = Scaling;
        scalingY = Scaling;
    }

    public void setScalingX(float x) {
        scalingX = x;
    }

    public void setScalingY(float y) {
        scalingY = y;
    }

    /**
     * 设置控件坐标
     */
    public void setPosition(float x, float y) {
        if (parentControl == null) {
            worldX = x;
            worldY = y;
        } else {
            parentX = x;
            parentY = y;
        }
        adjustment();
    }

    public void setX(float x) {
        if (parentControl == null) {
            worldX = x;
        } else {
            parentX = x;
        }
        adjustment();
    }

    public void setY(float y) {
        if (parentControl == null) {
            worldY = y;
        } else {
            parentY = y;
        }
        adjustment();
    }

    public void addPosition(float x, float y) {
        if (parentControl == null) {
            worldX += x;
            worldY += y;
        } else {
            parentX += x;
            parentY += y;
        }
        adjustment();
    }

    /**
     * 设置控件名称
     */
    public void setName(String name) {
        this.name = name;
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
    public void setParent(Control control) {
        parentControl = control;
        adjustment();
    }

    /**
     * 移除父控件
     */
    public void removeParent() {
        parentControl.removeChild(this);
        parentControl = null;
        parentX = 0;
        parentY = 0;
        adjustment();
    }

    /**
     * 添加子控件
     */
    public void addChild(Control... children) {
        for (Control child : children) {
            if (child == null) {
                System.out.println("addChild: child is null");
                return;
            }
            child.getParent().removeChild(child);
            child.setParent(child);
        }
        childControl.addAll(Arrays.asList(children));
        adjustment();
    }

    /**
     * 移除子控件
     */
    public void removeChild(Control child) {
        if (child == null) {
            System.out.println("removeChild: child is null");
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
        } else {
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

    /**
     * 判断是否是同一个控件
     */
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
}
