package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.Actor;

import java.util.Optional;

/**
 * @Author: 尽
 * @Description: 自定义按钮
 */
public class CustomButton extends CustomStateButton{
    private final ButtonStyle styleDefault = new ButtonStyle(); // 默认样式
    private final ButtonStyle styleOver = new ButtonStyle();    // 触摸样式
    private final ButtonStyle styleDisabled = new ButtonStyle();  // 禁用样式
    private final ButtonStyle stylePressed = new ButtonStyle(); // 按下样式\

    public CustomButton(){
        setStyle(styleDefault);
    }

    // region 状态切换

    /**
     * 切换到默认状态
     */
    @Override
    public void defaultState() {
        setStyle(styleDefault);
    }

    /**
     * 切换到悬停状态
     */
    @Override
    public void overState() {
        setStyle(styleDefault);
    }

    /**
     * 切换到禁用状态
     */
    @Override
    public void disabledState() {
        setStyle(styleDefault);
    }

    /**
     * 切换到按下状态
     */
    @Override
    public void pressedState() {
        setStyle(styleDefault);
    }
    // endregion

    /** 样式状态切换 */
    @Override
    public void replaceStyle(){
        replaceState();
        if (disabledState){
            disabledState();
            return;
        }else {
            if(overState){
                if (pressedState){
                    pressedState();
                }
                else{
                    overState();
                }
                return;
            }
        }
        defaultState();
    };

    /**
     * 获取按钮子控件。
     * @return 按钮子控件。
     */
    @Override
    public Actor getSon() {
        return getChildren().first();
    }

    /**
     * 设置指定状态的样式。
     *
     * @param state       样式状态
     * @param settings    样式设置
     */
    @Override
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
    @Override
    protected Optional<ButtonStyle> getStyle(State state) {
        return switch (state) {
            case DEFAULT -> Optional.ofNullable(styleDefault);
            case OVER -> Optional.ofNullable(styleOver);
            case DISABLED -> Optional.ofNullable(styleDisabled);
            case PRESSED -> Optional.ofNullable(stylePressed);
        };
    }
}
