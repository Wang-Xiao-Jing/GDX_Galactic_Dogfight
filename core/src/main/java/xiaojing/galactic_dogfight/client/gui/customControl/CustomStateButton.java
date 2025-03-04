package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

/**
 * 自定义抽象按钮类
 * @apiNote
 */
public abstract class CustomStateButton extends Button {
    public final CustomStatusDrawable drawable = new CustomStatusDrawable();
    boolean defaultState = true; // 默认状态
    boolean overState;           // 触摸状态
    boolean disabledState;       // 禁用状态
    boolean pressedState;        // 按下状态

    public CustomStateButton() {
        super();
        setSkin(drawable);
    }

    public CustomStateButton(CustomStatusDrawable drawable) {
//        super(skin);
    }

    public void setSkin(CustomStatusDrawable drawable) {
        setStyle(drawable.newButtonStyle(drawable));
    }

    // region 状态切换

    /**
     * 切换到默认状态
     */
    public void defaultState() {

    }

    /**
     * 切换到悬停状态
     */
    public void overState() {

    }

    /**
     * 切换到禁用状态
     */
    public void disabledState() {

    }

    /**
     * 切换到按下状态
     */
    public void pressedState() {

    }
    // endregion

    /**
     * 样式状态切换
     */
    public void replaceStyle() {
        replaceState();
        if (disabledState) {
            disabledState();
            return;
        } else {
            if (overState) {
                if (pressedState) {
                    pressedState();
                } else {
                    overState();
                }
                return;
            }
        }
        defaultState();
    }

    /**
     * 获取按钮子控件。
     *
     * @return 按钮子控件。
     */
    public Actor getChild() {
        return getChildren().first();
    }

    ;

    /**
     * 设置指定状态的样式。
     *
     * @param state    样式状态
     * @param settings 样式设置
     */
    public void setFontStyle(State state, ButtonStyle settings) {
    }

    /**
     * 按钮状态切换
     */
    public void replaceState() {
        if (isDisabled()) {
            disabledState = true;
            defaultState = false;
        } else {
            if (isOver()) {
                overState = true;
                pressedState = isPressed();
                defaultState = false;
            } else {
                overState = false;
                pressedState = false;
                disabledState = false;
                defaultState = true;
            }
        }
    }

    /**
     * 按钮状态枚举。
     */
    public enum State {
        /**
         * 默认状态
         */
        DEFAULT,
        /**
         * 鼠标悬停状态
         */
        OVER,
        /**
         * 禁用状态
         */
        DISABLED,
        /**
         * 按下状态
         */
        PRESSED
    }

}
