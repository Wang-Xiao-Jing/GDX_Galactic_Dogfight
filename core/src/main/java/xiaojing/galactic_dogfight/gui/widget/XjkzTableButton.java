package xiaojing.galactic_dogfight.gui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;

import java.util.Optional;

/**
 * 自定义文本按钮类
 */
public class XjkzTableButton extends XjkzButton {

    // 默认字体
    private final BitmapFont bitmapFont = xiaojing.galactic_dogfight.Main.bitmapFont;

    private final LabelStyle labelStyleDefault = new LabelStyle(bitmapFont, Color.WHITE);
    private final LabelStyle labelStyleOver = new LabelStyle(bitmapFont, Color.GREEN);
    private final LabelStyle labelStyleDisabled = new LabelStyle(bitmapFont, Color.RED);
    private final LabelStyle labelStylePressed = new LabelStyle(bitmapFont, Color.ROYAL);

    // region 构造方法
    /**
     * 构造带有文本的按钮
     */
    public XjkzTableButton() {
        add(new Label("", labelStyleDefault));
    }

    /**
     * 构造带有文本的按钮
     *
     * @param text 按钮显示的文本
     */
    public XjkzTableButton(CharSequence text) {
        this();
        setText(text);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param scale 字体缩放比例
     */
    public XjkzTableButton(float scale) {
        this();
        setTextScale(scale);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param label 标签对象
     * @param scale 字体缩放比例
     */
    public XjkzTableButton(Label label, float scale) {
        add(label);
        label.setScale(scale);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param skin  皮肤对象
     */
    public XjkzTableButton(Skin skin) {
        this("", skin);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param text  按钮显示的文本
     * @param skin  皮肤对象
     */
    public XjkzTableButton(CharSequence text, Skin skin) {
        this(text, skin, 1);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param text  按钮显示的文本
     * @param skin  皮肤对象
     * @param scale 字体缩放比例
     */
    public XjkzTableButton(CharSequence text, Skin skin, float scale) {
        this(new Label(text, skin), scale);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param text  按钮显示的文本
     * @param scale 字体缩放比例
     */
    public XjkzTableButton(CharSequence text, float scale) {
        this(text);
        setTextScale(scale);
    }

    /**
     * 构造带有文本的按钮
     *
     * @param bitmapFont 按钮使用的字体
     */
    public XjkzTableButton(BitmapFont bitmapFont) {
        getSon().getStyle().font = bitmapFont;
    }
    // endregion

    // region 修改样式
    /**
     * 修改文本
     *
     * @param text 文本
     */
    public void setText(CharSequence text){
        getSon().setText(text);
    }

    /**
     * 修改文本缩放比例
     *
     * @param scale 缩放比例
     */
    public void setTextScale(float scale){
        getSon().setFontScale(scale);
    }

    /**
     * 修改文本样式
     *
     * @param text 文本
     * @param scale 缩放比例
     */
    public void setTextStyle(CharSequence text, float scale){
        setText(text);
        setTextScale(scale);
    }

    /**
     * 修改指定状态的字体
     *
     * @param state      状态
     * @param bitmapFont 字体
     */
    public void setLabelTextFont(State state, BitmapFont bitmapFont) {
        getStyle(state).ifPresent(style -> {
            style.font = bitmapFont;
        });
    }

    /**
     * 修改指定状态的样式
     *
     * @param state     状态
     * @param settings  样式
     */
    public void setFontStyle(State state, LabelStyle settings) {
        getStyle(state).ifPresent(style -> {
            style.fontColor = settings.fontColor;
            style.font = settings.font;
            style.background = settings.background;
        });
    }

    /**
     * 修改所有状态的样式
     *
     * @param settings  样式
     */
    public void setAllFontStyle(LabelStyle settings) {
        labelStyleDefault.font = bitmapFont;
        labelStyleOver.font = bitmapFont;
        labelStyleDisabled.font = bitmapFont;
        labelStylePressed.font = bitmapFont;
    }

    /**
     * 修改所有状态的样式
     *
     * @param bitmapFont 样式
     */
    public void setAllLabelTextFont(BitmapFont bitmapFont) {
        setLabelStyle(labelStyleDefault, bitmapFont);
        setLabelStyle(labelStyleOver, bitmapFont);
        setLabelStyle(labelStyleDisabled, bitmapFont);
        setLabelStyle(labelStylePressed, bitmapFont);
    }

    /**
     * 修改所有状态的字体颜色
     *
     * @param fontColor 字体颜色
     */
    public void setAllFontColor(Color fontColor) {
        setLabelStyle(labelStyleDefault, fontColor);
        setLabelStyle(labelStyleOver, fontColor);
        setLabelStyle(labelStyleDisabled, fontColor);
        setLabelStyle(labelStylePressed, fontColor);
    }

    /**
     * 修改所有状态的字体
     *
     * @param font 字体
     */
    public void setAllFont(BitmapFont font) {
        setLabelStyle(labelStyleDefault, font);
        setLabelStyle(labelStyleOver, font);
        setLabelStyle(labelStyleDisabled, font);
        setLabelStyle(labelStylePressed, font);
    }

    /**
     * 修改所有状态的背景
     *
     * @param background 背景
     */
    public void setAllBackground(Drawable background) {
        setLabelStyle(labelStyleDefault, background);
        setLabelStyle(labelStyleOver, background);
        setLabelStyle(labelStyleDisabled, background);
        setLabelStyle(labelStylePressed, background);
    }
    // endregion

    // region 状态切换
    /**
     * 切换到默认状态
     */
    public void defaultState() {
        getSon().setStyle(labelStyleDefault);
    }

    /**
     * 切换到悬停状态
     */
    public void overState() {
        getSon().setStyle(labelStyleOver);
    }

    /**
     * 切换到禁用状态
     */
    public void disabledState() {
        getSon().setStyle(labelStyleDisabled);
    }

    /**
     * 切换到按下状态
     */
    public void pressedState() {
        getSon().setStyle(labelStylePressed);
    }
    // endregion

    // region 获取方法
    /**
     * 获取子控件（Label）
     *
     * @return 子控件（Label）
     */
    public Label getSon() {
        return (Label) getChildren().first();
    }

    /**
     * 获取指定状态的字体样式
     *
     * @param state 状态名
     * @return 字体样式
     */
    public Optional<LabelStyle> getStyle(State state) {
        return switch (state) {
            case DEFAULT -> Optional.ofNullable(labelStyleDefault);
            case OVER -> Optional.ofNullable(labelStyleOver);
            case DISABLED -> Optional.ofNullable(labelStyleDisabled);
            case PRESSED -> Optional.ofNullable(labelStylePressed);
        };
    }
    // endregion

    // region 私有方法
    /**
     * 更新所有标签样式的字体
     */
    private void updateLabelStyles() {
        labelStyleDefault.font = bitmapFont;
        labelStyleOver.font = bitmapFont;
        labelStyleDisabled.font = bitmapFont;
        labelStylePressed.font = bitmapFont;
    }

    private void setLabelStyle(LabelStyle style0, LabelStyle style1) {
        style0.fontColor = style1.fontColor;
        style0.font = style1.font;
        style0.background = style1.background;
    }

    private void setLabelStyle(LabelStyle style, Color fontColor) {
        style.fontColor = fontColor;
    }

    private void setLabelStyle(LabelStyle style, BitmapFont font) {
        style.font = font;
    }

    private void setLabelStyle(LabelStyle style, Drawable background) {
        style.background = background;
    }
    // endregion

    /**
     * 添加监听器
     */
    public void addLabelListener() {
        addButtonListener();
        addListener(new InputListener() {
            @Override
            public boolean handle(Event e) {
                if (isDisabled()) {
                    disabledState();
                }
                else if (isPressed()&&isOver()) {
                    pressedState();
                }
                else if (!isPressed()&&isOver()) {
                    overState();
                }
                else {
                    defaultState();
                }
                return true;
            }
        });
    }

}