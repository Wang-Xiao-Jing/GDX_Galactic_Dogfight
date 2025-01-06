package xiaojing.galactic_dogfight.gui.widget;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import java.util.Optional;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2024/12/31
 */
public class XjkzButton extends Button {
    private ButtonStyle styleDefault = new ButtonStyle(); // 默认样式
    private ButtonStyle styleOver = new ButtonStyle();    // 触摸样式
    private ButtonStyle styleDisabled = new ButtonStyle();  // 禁用样式
    private ButtonStyle stylePressed = new ButtonStyle(); // 按下样式

    public XjkzButton(){
        setStyle(styleDefault);
    }

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
    public Actor getSon() {
        return getChildren().first();
    }

    /**
     * 设置指定状态的样式。
     *
     * @param state       样式状态
     * @param settings    样式设置
     */
    public void setFontStyle(State state, ButtonStyle settings) {
        getStyle(state).ifPresent(style -> {
            style.up = settings.up;
            style.down = settings.down;
            style.over = settings.over;
            style.focused = settings.focused;
            style.disabled = settings.disabled;

            style.checked = settings.checked;
            style.checkedOver = settings.checkedOver;
            style.checkedDown = settings.checkedDown;
            style.checkedFocused = settings.checkedFocused;

            style.pressedOffsetX = settings.pressedOffsetX;
            style.pressedOffsetY = settings.pressedOffsetY;
            style.unpressedOffsetX = settings.unpressedOffsetX;
            style.unpressedOffsetY = settings.unpressedOffsetY;
            style.checkedOffsetX = settings.checkedOffsetX;
            style.checkedOffsetY = settings.checkedOffsetY;
        });
    }

    /** 获取指定状态的样式。 */
    private Optional<ButtonStyle> getStyle(State state) {
        return switch (state) {
            case DEFAULT -> Optional.ofNullable(styleDefault);
            case OVER -> Optional.ofNullable(styleOver);
            case DISABLED -> Optional.ofNullable(styleDisabled);
            case PRESSED -> Optional.ofNullable(stylePressed);
        };
    }

    /**
     * 添加监听器
     */
    public void addButtonListener() {
        addListener(new InputListener() {
            @Override
            public boolean handle(Event e){
                if(isDisabled()) setStyle(styleDisabled);
                else if(isOver()&&isPressed()) setStyle(stylePressed);
                else if (isOver()) setStyle(styleOver);
                else setStyle(styleDefault);
                return true;
            }
        });
    }
}
