package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @author 尽
 * @apiNote 自定义标签
 */
public class CustomLabel extends Label {

    private static final LabelStyle DEFAULT_STYLE = new LabelStyle(new BitmapFont(), Color.WHITE);

    public CustomLabel() {
        super("", DEFAULT_STYLE);
    }

    public CustomLabel(CharSequence text, BitmapFont font) {
        this(text, new LabelStyle(font, Color.WHITE));
    }

    public CustomLabel(CharSequence text, BitmapFont font, float scale) {
        this(text, new LabelStyle(font, Color.WHITE), scale);
    }

    public CustomLabel(BitmapFont font) {
        this("", new LabelStyle(font, Color.WHITE));
    }

    public CustomLabel(CharSequence text) {
        this(text, DEFAULT_STYLE);
    }

    public CustomLabel(LabelStyle style) {
        this("", style);
    }

    public CustomLabel(LabelStyle style, Color color) {
        this("", style, color);
    }

    public CustomLabel(CharSequence text, Skin skin) {
        super(text, skin);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public CustomLabel(CharSequence text, float scale) {
        this(text, DEFAULT_STYLE, scale);
    }

    public CustomLabel(CharSequence text, Skin skin, float scale) {
        this(text, skin);
        setFontScale(scale);
    }

    public CustomLabel(CharSequence text, LabelStyle style, float scale) {
        this(text, style);
        setFontScale(scale);
    }

    public CustomLabel(CharSequence text, LabelStyle style, Color color) {
        this(text, style, color, 1.0f);
    }

    public CustomLabel(CharSequence text, LabelStyle style, Color color, float scale) {
        super(text, style);
        style.fontColor = color;
        setFontScale(scale);
        setSize(getPrefWidth(), getPrefHeight());
    }

    public CustomLabel(CharSequence text, Color color, float scale) {
        this(text, new LabelStyle(xiaojing.galactic_dogfight.Main.defaultFont, color), scale);
    }

    private CustomLabel(CharSequence text, LabelStyle style) {
        super(text, style);
        setSize(getPrefWidth(), getPrefHeight());
    }


    /**
     * 修改文本缩放比例
     *
     * @param scale 缩放比例
     */
    public void setTextScale(float scale) {
        setFontScale(scale);
    }

    /**
     * 修改文本样式
     *
     * @param text  文本
     * @param scale 缩放比例
     */
    public void setTextStyle(CharSequence text, float scale) {
        setText(text);
        setTextScale(scale);
    }

}
