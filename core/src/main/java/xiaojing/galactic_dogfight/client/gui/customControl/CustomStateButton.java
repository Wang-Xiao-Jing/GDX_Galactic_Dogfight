package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Optional;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2024/12/31
 */
public abstract class CustomStateButton extends Button {
    private final ButtonStyle styleDefault = new ButtonStyle(); // 默认样式
    private final ButtonStyle styleOver = new ButtonStyle();    // 触摸样式
    private final ButtonStyle styleDisabled = new ButtonStyle();  // 禁用样式
    private final ButtonStyle stylePressed = new ButtonStyle(); // 按下样式
    boolean defaultState = true; // 默认状态
    boolean overState;           // 触摸状态
    boolean disabledState;       // 禁用状态
    boolean pressedState;        // 按下状态

    public CustomStateButton(){
        setStyle(styleDefault);
    }

    // region 状态切换
    /**
     * 切换到默认状态
     */
    public abstract void defaultState();

    /**
     * 切换到悬停状态
     */
    public abstract void overState();

    /**
     * 切换到禁用状态
     */
    public abstract void disabledState();

    /**
     * 切换到按下状态
     */
    public abstract void pressedState();
    // endregion

    /** 样式状态切换 */
    public abstract void replaceStyle();

    /** 按钮状态枚举。 */
    public enum State {
        /** 默认状态 */
        DEFAULT,
        /** 鼠标悬停状态 */
        OVER,
        /** 禁用状态 */
        DISABLED,
        /** 按下状态 */
        PRESSED
    }

    /**
     * 获取按钮子控件。
     * @return 按钮子控件。
     */
    public abstract Actor getSon();

    /**
     * 设置指定状态的样式。
     *
     * @param state       样式状态
     * @param settings    样式设置
     */
    public abstract void setFontStyle(State state, ButtonStyle settings);

    /** 获取指定状态的样式。 */
    protected abstract Optional<ButtonStyle> getStyle(State state);

    /** 按钮状态切换 */
    public void replaceState(){
        if (isDisabled()){
            disabledState = true;
            defaultState = false;
        }else {
            if(isOver()){
                overState = true;
                if (isPressed()){
                    pressedState = true;
                }
                else {
                    pressedState = false;
                }
                defaultState = false;
            }
            else{
                overState = false;
                pressedState = false;
                disabledState = false;
                defaultState = true;
            }
        }
    }

}
