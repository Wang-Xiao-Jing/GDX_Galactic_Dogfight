package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * @author 尽
 * @apiNote 便捷函数类
 */
public class QuickMethod {
    private final Viewport viewport;

    public QuickMethod(Viewport viewport) {
        this.viewport = viewport;
    }

    public String getPixel() {
        return "texture/screen/pixel.png";
    }

    /**
     * 居中
     */
    public void center(Actor actor) {
        actor.setPosition(viewport.getWorldWidth() / 2 - actor.getWidth() / 2, viewport.getWorldHeight() / 2 - actor.getHeight() / 2);
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
