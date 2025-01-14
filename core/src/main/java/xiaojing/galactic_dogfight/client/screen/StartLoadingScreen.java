package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.viewport.FitViewport;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;

/**
 * @author 尽
 * @apiNote 加载画面界面
 */
public class StartLoadingScreen extends ScreenAdapter {
    private float DURATION = 0.8f; // 过度时间
    private final Main GAME;                // 游戏实例
    private final FitViewport viewport;     // 视口实例
    private final Stage MAIN_STAGE;         // 总舞台
    private final AssetManager MANAGER;     // 资源管理器
    private CustomLabel loadingLabel;       // 加载提示标签
    private Image progressBar;              // 进度条
    private Image background;               // 背景
    private float progressValue;            // 进度条值
    private float lineSize;                 // 进度条宽度
    private Vector2 start;                  // 移动向量
    private Vector2 end;                    // 移动向量
    private BitmapFont bitmapFont;          // 默认字体

    private AlphaAction action;             // 透明度动作

    public StartLoadingScreen(final Main GAME){
        this.GAME = GAME;
        this.MANAGER = this.GAME.manager;
        this.viewport = this.GAME.viewport;
        this.MAIN_STAGE = new Stage(viewport);
        this.bitmapFont = MANAGER.get("texture/gui/loading/loading.fnt");
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        TextureRegion region = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();
        lineSize = 10 * GAME.scale;
        start = new Vector2(0, GAME.viewport.getScreenHeight() / 2f - lineSize / 2f);
        end = new Vector2(0, GAME.viewport.getScreenHeight() / 2f - lineSize / 2f);

        progressBar = new Image(region);
        background = new Image(region);
        background.setColor(Color.BLACK);
        background.setFillParent(true);
        loadingLabel = new CustomLabel(bitmapFont);

        action = new AlphaAction();
        action.setAlpha(0);
        action.setDuration(DURATION);

        MAIN_STAGE.addActor(background);
        MAIN_STAGE.addActor(progressBar);
        MAIN_STAGE.addActor(loadingLabel);
        MAIN_STAGE.addAction(action);
    }

    @Override
    public void render(float delta) {
        MAIN_STAGE.draw();
        progressValue = GAME.manager.getProgress();
        if (GAME.manager.update()){
            action.act(delta);
        }
        progressBar();
        label();
    }

    /** 标签 */
    private void label(){
        loadingLabel.setText(String.format("%.2f", progressValue * 100)+"%");
        loadingLabel.setPosition(
                viewport.getWorldWidth() / 2 - loadingLabel.getPrefWidth() / 2,
                viewport.getWorldHeight() / 2 - loadingLabel.getPrefHeight() / 2
        );
    }

    /** 进度条 */
    private void progressBar() {
        start.x = 0;
        end.x = start.x + GAME.viewport.getWorldWidth() * progressValue;
        progressBar.setWidth(end.x);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    @Override
    public void dispose() {
        MAIN_STAGE.dispose();
    }
}
