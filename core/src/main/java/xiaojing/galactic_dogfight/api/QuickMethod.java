package xiaojing.galactic_dogfight.api;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.jetbrains.annotations.NotNull;
import xiaojing.galactic_dogfight.StaticClass;

/**
 * 便捷函数类
 * @apiNote 为各种Actor的位置处理、及转换
 */
public class QuickMethod {
    private final Viewport guiViewport = StaticClass.guiViewport;

    /**
     * Texture转换为Drawable
     */
    public static Drawable textureConvertToDrawable(Texture texture) {
        return new TextureRegionDrawable(new TextureRegion(texture));
    }

    /**
     * TextureRegionDrawable转换为Texture
     */
    public static Texture textureRegionDrawableConvertToTexture(TextureRegionDrawable drawable) {
        return drawable.getRegion().getTexture();
    }

    /**
     * 居中
     */
    public void center(@NotNull Actor actor) {
        actor.setPosition(guiViewport.getWorldWidth() / 2 - actor.getWidth() / 2, guiViewport.getWorldHeight() / 2 - actor.getHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Table actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public <T extends Actor> void originCenter(Container<T> actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Stack actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(ScrollPane actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(SplitPane actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public <N extends Tree.Node<N, V, A>, V, A extends Actor> void originCenter(Tree<N, V> actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(VerticalGroup actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(HorizontalGroup actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Label actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Image actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Button actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(TextButton actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(ImageButton actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(CheckBox actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    // ButtonGroup没有原点！（干脆到时候我写一个 :! ）

    /**
     * 原点居中
     */
    public void originCenter(TextField actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(TextArea actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public <T> void originCenter(List<T> actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public <T> void originCenter(SelectBox<T> actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(ProgressBar actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Slider actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Window actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Touchpad actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }

    /**
     * 原点居中
     */
    public void originCenter(Dialog actor) {
        actor.setOrigin(actor.getPrefWidth() / 2, actor.getPrefHeight() / 2);
    }
}
