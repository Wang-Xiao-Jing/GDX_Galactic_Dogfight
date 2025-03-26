package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import xiaojing.galactic_dogfight.StaticClass;

import static xiaojing.galactic_dogfight.api.QuickMethod.textureConvertToDrawable;
import static xiaojing.galactic_dogfight.StaticClass.EMPTY_TEXTURE;
import static xiaojing.galactic_dogfight.StaticClass.PIXEL_TEXTURE;

/**
 * 多状态聚合Drawable贴图
 * @apiNote
 */
public class CustomStatusDrawable {
    // 按钮有开和关的状态 我把认为默认的为关闭
    private Drawable up;             // 关-默认状态     对应Style的up
    private Drawable over;           // 关-鼠标悬停状态  对应Style的over
    private Drawable down;           // 关-按下状态     对应Style的down
    private Drawable focused;        // 关-键盘选择状态  对应Style的focused

    private Drawable checked;        // 开-默认状态     对应Style的checked
    private Drawable checkedOver;    // 开-鼠标悬停状态  对应Style的checkedOver
    private Drawable checkedDown;    // 开-按下状态     对应Style的checkedDown
    private Drawable checkedFocused; // 开-键盘选择状态  对应Style的checkedFocused

    private Drawable disabled;       // 禁用状态        对应Style的disabled

    /** 默认构造函数
     * <p>
     * 默认为黑紫贴图 有空白需求请用 {@linkplain StaticClass#PIXEL_TEXTURE} */
    public CustomStatusDrawable() {
        this(textureConvertToDrawable(PIXEL_TEXTURE));
    }

    public CustomStatusDrawable(Drawable texture) {
        if (texture == null) texture = textureConvertToDrawable(EMPTY_TEXTURE);
        this.up = texture;
        this.over = texture;
        this.down = texture;
        this.focused = texture;
        this.checked = texture;
        this.checkedOver = texture;
        this.checkedDown = texture;
        this.checkedFocused = texture;
        this.disabled = texture;
    }

    public CustomStatusDrawable(Skin skin, String name) {
        this(new NinePatchDrawable(skin.getPatch(name)));
    }

    private CustomStatusDrawable(Builder builder) {
        this(builder.defaultTexture, builder.overTexture, builder.pressedTexture, builder.keyboardSelectionTexture,
            builder.defaultTextureOn, builder.overTextureOn, builder.pressedTextureOn, builder.keyboardSelectionTextureOn, builder.disabledTexture);
    }

    public CustomStatusDrawable(CustomStatusDrawable drawable) {
        this(drawable.up, drawable.over, drawable.down, drawable.focused,
            drawable.checked, drawable.checkedOver, drawable.checkedDown, drawable.checkedFocused, drawable.disabled);
    }

    public CustomStatusDrawable(Drawable up, Drawable over, Drawable down, Drawable focused,
                                Drawable checked, Drawable checkedOver, Drawable checkedDown, Drawable checkedFocused, Drawable disabled) {
        this.disabled = disabled;

        this.up = up;
        this.over = over;
        this.down = down;
        this.focused = focused;

        this.checked = checked;
        this.checkedOver = checkedOver;
        this.checkedDown = checkedDown;
        this.checkedFocused = checkedFocused;
    }

    public Drawable getDefault() {
        return this.up;
    }

    public void setDefault(Drawable defaultTexture) {
        this.up = defaultTexture;
    }

    public Drawable getOver() {
        return this.over;
    }

    public void setOver(Drawable overTexture) {
        this.over = overTexture;
    }

    public Drawable getDisabled() {
        return this.disabled;
    }

    public void setDisabled(Drawable disabledTexture) {
        this.disabled = disabledTexture;
    }

    public Drawable getPressed() {
        return this.down;
    }

    public void setPressed(Drawable pressedTexture) {
        this.down = pressedTexture;
    }

    public Drawable getDefaultOn() {
        return this.checked;
    }

    public void setDefaultOn(Drawable defaultTextureOn) {
        this.checked = defaultTextureOn;
    }

    public Drawable getOverOn() {
        return this.checkedOver;
    }

    public void setOverOn(Drawable overTextureOn) {
        this.checkedOver = overTextureOn;
    }

    public Drawable getPressedOn() {
        return this.checkedDown;
    }

    public void setPressedOn(Drawable pressedTextureOn) {
        this.checkedDown = pressedTextureOn;
    }

    public Drawable getKeyboardSelection() {
        return this.focused;
    }

    public void setKeyboardSelection(Drawable keyboardSelectionTexture) {
        this.focused = keyboardSelectionTexture;
    }

    public Drawable getKeyboardSelectionOn() {
        return this.checkedFocused;
    }

    public void setKeyboardSelectionOn(Drawable keyboardSelectionTextureOn) {
        this.checkedFocused = keyboardSelectionTextureOn;
    }

    /**
     * 创建Style
     */
    public Button.ButtonStyle newButtonStyle(CustomStatusDrawable drawable) {
        Button.ButtonStyle style = new Button.ButtonStyle();
        style.up = drawable.getDefault();
        style.down = drawable.getPressed();
        style.over = drawable.getOver();
        style.disabled = drawable.getDisabled();
        style.checked = drawable.getDefaultOn();
        style.checkedOver = drawable.getOverOn();
        style.checkedDown = drawable.getPressedOn();
        style.checkedFocused = drawable.getKeyboardSelection();
        return style;
    }

    public static class Builder {
        private Drawable defaultTexture = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable overTexture = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable pressedTexture = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable keyboardSelectionTexture = textureConvertToDrawable(EMPTY_TEXTURE);

        private Drawable defaultTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable overTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable pressedTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
        private Drawable keyboardSelectionTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);

        private Drawable disabledTexture = textureConvertToDrawable(EMPTY_TEXTURE);

        public Builder allTexture(Drawable texture) {
            this.defaultTexture = texture;
            this.overTexture = texture;
            this.pressedTexture = texture;
            this.keyboardSelectionTexture = texture;
            this.defaultTextureOn = texture;
            this.overTextureOn = texture;
            this.pressedTextureOn = texture;
            this.keyboardSelectionTextureOn = texture;
            this.disabledTexture = texture;
            return this;
        }

        public Builder allTextureOff(Drawable texture) {
            this.defaultTexture = texture;
            this.overTexture = texture;
            this.pressedTexture = texture;
            this.keyboardSelectionTexture = texture;
            return this;
        }

        public Builder allTextureOn(Drawable texture) {
            this.defaultTextureOn = texture;
            this.overTextureOn = texture;
            this.pressedTextureOn = texture;
            this.keyboardSelectionTextureOn = texture;
            return this;
        }

        public Builder defaultTexture(Drawable defaultTexture) {
            this.defaultTexture = defaultTexture;
            return this;
        }

        public Builder overTexture(Drawable overTexture) {
            this.overTexture = overTexture;
            return this;
        }

        public Builder disabledTexture(Drawable disabledTexture) {
            this.disabledTexture = disabledTexture;
            return this;
        }

        public Builder pressedTexture(Drawable pressedTexture) {
            this.pressedTexture = pressedTexture;
            return this;
        }

        public Builder keyboardSelectionTexture(Drawable keyboardSelectionTexture) {
            this.keyboardSelectionTexture = keyboardSelectionTexture;
            return this;
        }

        public Builder defaultTextureOn(Drawable defaultTextureOn) {
            this.defaultTextureOn = defaultTextureOn;
            return this;
        }

        public Builder overTextureOn(Drawable overTextureOn) {
            this.overTextureOn = overTextureOn;
            return this;
        }

        public Builder pressedTextureOn(Drawable pressedTextureOn) {
            this.pressedTextureOn = pressedTextureOn;
            return this;
        }

        public Builder keyboardSelectionTextureOn(Drawable keyboardSelectionTextureOn) {
            this.keyboardSelectionTextureOn = keyboardSelectionTextureOn;
            return this;
        }

        public Builder disabledTextureOn(Drawable disabledTextureOn) {
            this.disabledTexture = disabledTextureOn;
            return this;
        }

        public CustomStatusDrawable build() {
            if (defaultTexture == null) defaultTexture = textureConvertToDrawable(EMPTY_TEXTURE);
            if (overTexture == null) overTexture = textureConvertToDrawable(EMPTY_TEXTURE);
            if (disabledTexture == null) disabledTexture = textureConvertToDrawable(EMPTY_TEXTURE);
            if (pressedTexture == null) pressedTexture = textureConvertToDrawable(EMPTY_TEXTURE);
            if (keyboardSelectionTexture == null) keyboardSelectionTexture = textureConvertToDrawable(EMPTY_TEXTURE);
            if (defaultTextureOn == null) defaultTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
            if (overTextureOn == null) overTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
            if (pressedTextureOn == null) pressedTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
            if (keyboardSelectionTextureOn == null) keyboardSelectionTextureOn = textureConvertToDrawable(EMPTY_TEXTURE);
            return new CustomStatusDrawable(this);
        }
    }
}
