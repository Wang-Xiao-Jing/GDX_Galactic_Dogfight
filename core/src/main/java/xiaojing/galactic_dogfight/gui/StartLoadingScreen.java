package xiaojing.galactic_dogfight.gui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import space.earlygrey.shapedrawer.ShapeDrawer;
import xiaojing.galactic_dogfight.Main;

import javax.sound.sampled.Line;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/5
 */
public class StartLoadingScreen implements Screen {
    private final Main GAME;   // 游戏实例
    FitViewport viewport;      // 视口实例
    final SpriteBatch BATCH;   // 用于绘制的SpriteBatch实例
    final Stage MAIN_STAGE;    // 总舞台
    AssetManager MANAGER;      // 资源管理器
    Label LOADING_LABEL;
    Stack STACK;
    Image PROGRESS_BAR;
    Image PROGRESS_BOX;
    ShapeDrawer drawer;
    float progressValue;
    float lineSize;
    Vector2 start;
    Vector2 end;
    public StartLoadingScreen(final Main GAME){
        MANAGER = GAME.manager;
        this.GAME = GAME;
        this.viewport = this.GAME.viewport;
        this.BATCH = this.GAME.batch;
        this.MAIN_STAGE = new Stage(viewport);
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        TextureRegion region = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();

        lineSize = 10 * GAME.scale;
        start = new Vector2(0, GAME.viewport.getScreenHeight()/2f-lineSize/2f);
        end = new Vector2(0, GAME.viewport.getScreenHeight()/2f-lineSize/2f);
        drawer = new ShapeDrawer(BATCH, region);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        ScreenUtils.clear(Color.BLACK); // 清屏
        BATCH.begin();
        progress();
        BATCH.end();
    }

    private void progress() {
        progressValue = GAME.manager.getProgress();
        start.x = 0;
        end.x = start.x + GAME.viewport.getScreenWidth() * progressValue;
        drawer.line(start, end, lineSize);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
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
        MAIN_STAGE.dispose();
    }
}
