package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Group;
import xiaojing.galactic_dogfight.client.screen.MainMenuScreen;

/**
 * 自定义Group
 * @apiNote
 */
public abstract class CustomGroup extends Group {
    /**
     * 交互
     */
    public void interactive() {

    }

    /**
     * 监听器&触发器
     */
    public void listener() {

    }

    /**
     * 监听器&触发器
     */
    public void listener(MainMenuScreen SCREEN) {

    }

    public void listener(Screen SCREEN) {

    }

    /**
     * 调整大小
     */
    public void adjustSize() {

    }

    public void adjustSize(float width, float height) {
        setSize(width, height);
    }
}
