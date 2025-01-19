package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.scenes.scene2d.Group;

/**
 * @author 尽
 * @apiNote 自定义Group
 */
public abstract class CustomizeGuiGroup extends Group implements CustomizeGroup {
    @Override
    public void adjustSize() {

    }

    /** 监听器&触发器 */
    @Override
    public void listener() {

    }

    @Override
    public void interactive() {

    }

    /** 监听器&触发器 */
    @Override
    public void listener(MainMenuScreen SCREEN) {

    }

    @Override
    public void adjustSize(float width, float height) {
        setSize(width, height);
    }
}
