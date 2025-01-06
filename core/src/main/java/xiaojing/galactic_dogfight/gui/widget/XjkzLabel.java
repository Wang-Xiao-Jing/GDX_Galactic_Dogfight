package xiaojing.galactic_dogfight.gui.widget;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.Optional;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/5
 */
public class XjkzLabel extends Label {

    private static final LabelStyle DEFAULT_STYLE = new LabelStyle(xiaojing.galactic_dogfight.Main.bitmapFont, Color.WHITE);

    public XjkzLabel() {
        super("", DEFAULT_STYLE);
    }

    public XjkzLabel(CharSequence text) {
        this(text, DEFAULT_STYLE);
    }

    public XjkzLabel(LabelStyle style) {
        this("", style);
    }

    public XjkzLabel(LabelStyle style, Color color) {
        this("", style, color);
    }

    public XjkzLabel(CharSequence text, Skin skin) {
        super(text, skin);
        Optional.of(skin).orElseThrow(() -> new IllegalArgumentException("Skin cannot be null"));
    }

    public XjkzLabel(CharSequence text, Skin skin, float scale) {
        this(text, skin);
        setFontScale(scale);
    }

    public XjkzLabel(CharSequence text, LabelStyle style, float scale) {
        this(text, style);
        setFontScale(scale);
    }

    public XjkzLabel(CharSequence text, LabelStyle style, Color color) {
        this(text, style, color, 1.0f); // 默认缩放比例为1.0
    }

    public XjkzLabel(CharSequence text, LabelStyle style, Color color, float scale) {
        super(text, style);
        Optional.ofNullable(style).orElseThrow(() -> new IllegalArgumentException("LabelStyle cannot be null"));
        style.fontColor = color;
        setFontScale(scale);
    }

    public XjkzLabel(CharSequence text, Color color, float scale) {
        this(text, new LabelStyle(xiaojing.galactic_dogfight.Main.bitmapFont, color), scale);
    }

    private XjkzLabel(CharSequence text, LabelStyle style) {
        super(text, style);
        Optional.ofNullable(xiaojing.galactic_dogfight.Main.bitmapFont).orElseThrow(() -> new IllegalStateException("BitmapFont cannot be null"));
    }


    /**
     * 修改文本缩放比例
     *
     * @param scale 缩放比例
     */
    public void setTextScale(float scale){
        setFontScale(scale);
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

}
