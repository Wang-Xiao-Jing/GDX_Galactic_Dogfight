package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Optional;

/**
 * @Author: 尽
 * @Description: 自定义图片按钮
 */
public class CustomImageButton extends CustomButton {
    Image image;

    private Drawable ImageStyleDefault; // 默认样式
    private Drawable ImageStyleOver;    // 触摸样式
    private Drawable ImageStyleDisabled;  // 禁用样式
    private Drawable ImageStylePressed; // 按下样式

    public CustomImageButton(){
        this(null, null, null, null,1);
    }

    public CustomImageButton(float scale){
        this(null, null, null, null, scale);
    }

    public CustomImageButton(Drawable defaultStyle){
        this(defaultStyle, defaultStyle, defaultStyle, defaultStyle, 1);
    }

    public CustomImageButton(Drawable defaultStyle, float scale){
        this(defaultStyle, defaultStyle, defaultStyle, defaultStyle, scale);
    }

    public CustomImageButton(Drawable defaultStyle, Drawable overStyle, Drawable disabledStyle, Drawable pressedStyle){
        this(defaultStyle, overStyle, disabledStyle, pressedStyle, 1);
    }

    public CustomImageButton(Drawable defaultStyle, Drawable overStyle, Drawable disabledStyle, Drawable pressedStyle, float scale){
        ImageStyleDefault = defaultStyle;
        ImageStyleOver = overStyle;
        ImageStyleDisabled = disabledStyle;
        ImageStylePressed = pressedStyle;
        add(image = new Image(ImageStyleDefault));
        image.setFillParent(true);
        setSize(image.getWidth(), image.getHeight());
        setScale(scale);
    }

    // region 状态切换
    /**
     * 切换到默认状态
     */
    @Override
    public void defaultState() {
        image.setDrawable(ImageStyleDefault);
    }

    /**
     * 切换到悬停状态
     */
    @Override
    public void overState() {
        image.setDrawable(ImageStyleOver);
    }

    /**
     * 切换到禁用状态
     */
    @Override
    public void disabledState() {
        image.setDrawable(ImageStyleDisabled);
    }

    /**
     * 切换到按下状态
     */
    @Override
    public void pressedState() {
        image.setDrawable(ImageStylePressed);
    }
    // endregion

    public void setAllDrawable(Drawable settings){
        ImageStyleDefault = settings;
        ImageStyleOver = settings;
        ImageStyleDisabled = settings;
        ImageStylePressed = settings;
    }

    /** 获取指定状态的图片。 */
    public Optional<Drawable> getDrawable(State state) {
        return switch (state) {
            case DEFAULT -> Optional.ofNullable(ImageStyleDefault);
            case OVER -> Optional.ofNullable(ImageStyleOver);
            case DISABLED -> Optional.ofNullable(ImageStyleDisabled);
            case PRESSED -> Optional.ofNullable(ImageStylePressed);
        };
    }

    /**
     * 设置指定状态的图片。
     *
     * @param state       样式状态
     * @param settings    样式设置
     */
    public void setDrawable(State state, Drawable settings) {
        switch (state) {
            case DEFAULT -> ImageStyleDefault = settings;
            case OVER -> ImageStyleOver = settings;
            case DISABLED -> ImageStyleDisabled = settings;
            case PRESSED -> ImageStylePressed = settings;
        }
    }

    public void setDrawable(State state, Image image) {
        switch (state) {
            case DEFAULT -> ImageStyleDefault = image.getDrawable();
            case OVER -> ImageStyleOver = image.getDrawable();
            case DISABLED -> ImageStyleDisabled = image.getDrawable();
            case PRESSED -> ImageStylePressed = image.getDrawable();
        }
    }
}
