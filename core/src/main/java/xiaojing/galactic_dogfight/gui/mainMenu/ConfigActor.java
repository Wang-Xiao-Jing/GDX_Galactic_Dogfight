package xiaojing.galactic_dogfight.gui.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import xiaojing.galactic_dogfight.gui.QuickMethod;
import xiaojing.galactic_dogfight.gui.widget.XjkzLabel;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/2
 */
public class ConfigActor extends Group {
    final QuickMethod quickMethod;
//    private final ConfigActor.Window window;
    private final MainMenuScreen SCREEN;
    final Container<Actor> POSITION;
    private Table table;
    Container<Actor> container;
    Skin test;
    Image background;
    public ConfigActor(final MainMenuScreen SCREEN, final Container<Actor> POSITION) {
        quickMethod = new QuickMethod(SCREEN.viewport);
        float optionRatio = 2;
//        setLayoutEnabled(false);
        this.SCREEN = SCREEN;
        this.POSITION = POSITION;
        setSize(this.POSITION.getWidth(), this.POSITION.getHeight());
        test = new Skin(Gdx.files.internal("texture/gui/test/test.json"));

        NinePatch backgroundPng = test.get("background", NinePatch.class);
        background = new Image(backgroundPng);
        Table();
        container();
        background.setSize(getWidth()-30, getHeight()-30);
        background.setOrigin(
            background.getWidth()/2,
            background.getHeight()/2
        );
        container.setSize(getWidth()-30, getHeight()-30);
//        window = new Window(getWidth()-30, getHeight()-30);
//        window.setPosition(getWidth()/2,getHeight()/2);
//        window.moveBy(window.getWidth()/2,window.getHeight()/2);
        background.setPosition(30f/2f,30f/2f);
        addActor(background);
        addActor(container);
    }

    private void Table() {
        table = new Table();
//        table.setSize(getWidth(), getHeight());
        table.setTransform(true);
        table.add(new XjkzLabel("设置", test, 2f));
//        table.setDebug(true);
    }

    private void container() {
        container = new Container<>();
        container.setTransform(true);
//        container.setPosition(-getWidth()/2, -getHeight()/2);
        quickMethod.center(container);
        container.setActor(table);
//        container.setDebug(true);
    }
}
