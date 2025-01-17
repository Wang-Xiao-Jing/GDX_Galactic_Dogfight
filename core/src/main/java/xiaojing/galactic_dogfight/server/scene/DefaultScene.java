package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import xiaojing.galactic_dogfight.Main;

/**
 * @author 尽
 * @apiNote 默认场景
 */
public class DefaultScene implements Screen {
    private AssetManager manager;                           // 资源管理器
    private final Main GAME;                                // 游戏实例
    private ExtendViewport gameViewport;                    // 游戏场景视口实例
    private ScreenViewport uiViewport;                      // UI视口实例
    private final SpriteBatch BATCH;                        // 用于绘制的SpriteBatch实例
    private final Stage MAIN_STAGE;                         // 总舞台
    private final Container<Actor> GUI_CONTAINER;           // GUI容器

    public DefaultScene(final Main GAME) {
        this.GAME = GAME;
        this.gameViewport = this.GAME.gameViewport;
        this.uiViewport = this.GAME.uiViewport;
        this.BATCH = this.GAME.batch;
        this.manager = this.GAME.manager;
        this.MAIN_STAGE = new Stage(uiViewport);
        GUI_CONTAINER = new Container<>();
        GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - this.GAME.guiMarginsLeft - this.GAME.guiMarginsRight,
            uiViewport.getWorldHeight() - this.GAME.guiMarginsTop - this.GAME.guiMarginsBottom
        );
        GUI_CONTAINER.setFillParent(true);
    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        gameViewport.apply();
        uiViewport.apply();
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width, height);
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
