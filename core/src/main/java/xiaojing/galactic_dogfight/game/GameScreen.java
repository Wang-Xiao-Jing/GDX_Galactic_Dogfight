package xiaojing.galactic_dogfight.game;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.viewport.FitViewport;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.mainMenuScreen.ConfigActor;
import xiaojing.galactic_dogfight.client.screen.mainMenuScreen.MainMenuActor;
import xiaojing.galactic_dogfight.server.player.Player;

/**
 * @author 尽
 * @apiNote 游戏界面
 */
public class GameScreen implements Screen {
    AssetManager manager; // 资源管理器
    final Main GAME;                                // 游戏实例
    FitViewport viewport;                           // 视口实例
    final SpriteBatch BATCH;                        // 用于绘制的SpriteBatch实例
    SpriteBatch spriteBatch;
    Player player;
    Stage mainStage;                                // 总舞台
    Container<Actor> guiContainer;                  // GUI容器

    /** 构造函数 */
    public GameScreen(final Main GAME){
        this.GAME = GAME;
        this.viewport = this.GAME.viewport;
        this.BATCH = this.GAME.batch;
        this.manager = this.GAME.manager;
        this.mainStage = new Stage(viewport);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
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
