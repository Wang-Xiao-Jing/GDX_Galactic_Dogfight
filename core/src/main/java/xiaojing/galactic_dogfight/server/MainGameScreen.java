package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.client.screen.CustomizeScreenAbstract;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 主游戏画面（场景）
 */
public class MainGameScreen extends CustomizeScreenAbstract {
    final SpriteBatch BATCH;                        // 用于绘制的SpriteBatch实例
    final Stage MAIN_STAGE;                         // 总舞台
    final Container<Actor> GUI_CONTAINER;           // GUI容器

    public MainGameScreen() {
        gameViewport.getCamera().position.set(gameViewport.getWorldWidth() / 2, gameViewport.getWorldHeight() / 2, 0);
        this.BATCH = batch;
        this.MAIN_STAGE = new Stage(uiViewport);
        this.GUI_CONTAINER = new Container<>();
        this.GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - guiMarginsLeft - guiMarginsRight,
            uiViewport.getWorldHeight() - guiMarginsTop - guiMarginsBottom
        );
        this.GUI_CONTAINER.setFillParent(true);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
