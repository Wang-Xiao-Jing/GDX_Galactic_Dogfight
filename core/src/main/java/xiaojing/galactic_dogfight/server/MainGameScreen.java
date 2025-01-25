package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.CustomizeScreenAbstract;
import xiaojing.galactic_dogfight.server.scene.DefaultScene;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 主游戏画面（场景）
 */
public class MainGameScreen extends DefaultScene {

    public MainGameScreen(final Main GAME) {
        super(GAME);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
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
