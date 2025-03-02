package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;

import static xiaojing.galactic_dogfight.api.QuickMethod.converted;
import static xiaojing.galactic_dogfight.api.StaticClass.emptyTexture;

public class CustomStatusDrawable {
    private Drawable defaultTexture; // 默认状态
    private Drawable overTexture;    // 鼠标悬停状态
    private Drawable disabledTexture;// 禁用状态
    private Drawable pressedTexture; // 按下状态

    public CustomStatusDrawable() {
        this(converted(emptyTexture));
    }

    public CustomStatusDrawable(Drawable texture) {
        if (texture == null) {
            texture = converted(emptyTexture);
        }
        this.defaultTexture = texture;
        this.overTexture = texture;
        this.disabledTexture = texture;
        this.pressedTexture = texture;
    }

    public CustomStatusDrawable(Skin skin, String name) {
        this(new NinePatchDrawable(skin.getPatch(name)));
    }

    private CustomStatusDrawable(Builder builder) {
        this(builder.defaultTexture, builder.overTexture, builder.disabledTexture, builder.pressedTexture);
    }

    public CustomStatusDrawable(CustomStatusDrawable drawable) {
        this(drawable.defaultTexture, drawable.overTexture, drawable.disabledTexture, drawable.pressedTexture);
    }

    public CustomStatusDrawable(Drawable defaultTexture, Drawable overTexture, Drawable disabledTexture, Drawable pressedTexture) {
        this.defaultTexture = defaultTexture;
        this.overTexture = overTexture;
        this.disabledTexture = disabledTexture;
        this.pressedTexture = pressedTexture;
    }

    public Drawable getDefault(){
        return this.defaultTexture;
    }

    public Drawable getOver(){
        return this.overTexture;
    }

    public Drawable getDisabled(){
        return this.disabledTexture;
    }

    public Drawable getPressed(){
        return this.pressedTexture;
    }

    public void setDefault(Drawable defaultTexture){
        this.defaultTexture = defaultTexture;
    }

    public void setOver(Drawable overTexture){
        this.overTexture = overTexture;
    }

    public void setDisabled(Drawable disabledTexture){
        this.disabledTexture = disabledTexture;
    }

    public void setPressed(Drawable pressedTexture){
        this.pressedTexture = pressedTexture;
    }

    public static class Builder {
        Drawable defaultTexture = converted(emptyTexture);
        Drawable overTexture = converted(emptyTexture);
        Drawable disabledTexture = converted(emptyTexture);
        Drawable pressedTexture = converted(emptyTexture);

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

        public CustomStatusDrawable build() {
            if (defaultTexture == null) defaultTexture = converted(emptyTexture);
            if (overTexture == null) overTexture = converted(emptyTexture);
            if (disabledTexture == null) disabledTexture = converted(emptyTexture);
            if (pressedTexture == null) pressedTexture = converted(emptyTexture);
            return new CustomStatusDrawable(this);
        }
    }
}
