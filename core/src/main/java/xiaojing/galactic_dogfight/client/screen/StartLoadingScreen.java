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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 加载画面界面
 */
public class StartLoadingScreen extends ScreenAdapter {
    private final float DURATION = 0.8f;          // 过度时间
    private final Stage MAIN_STAGE;               // 总舞台
    private final CustomLabel loadingLabel;       // 加载提示标签
    private final Image progressBar;              // 进度条
    private final Image background;               // 背景
    private float progressValue;                  // 进度条值
    private final float lineSize;                 // 进度条宽度
    private final Vector2 start;                  // 移动向量
    private final Vector2 end;                    // 移动向量
    private final BitmapFont bitmapFont;          // 默认字体
    private final AlphaAction action;             // 透明度动作

    public StartLoadingScreen(){
        this.MAIN_STAGE = new Stage(uiViewport);
        this.bitmapFont = manager.get("texture/gui/loading/loading.fnt");
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        TextureRegion region = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();
        lineSize = 10 * scale;
        start = new Vector2(0, uiViewport.getScreenHeight() / 2f - lineSize / 2f);
        end = new Vector2(0, uiViewport.getScreenHeight() / 2f - lineSize / 2f);

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
        progressValue = manager.getProgress();
        if (manager.update()){
            action.act(delta);
        }
        progressBar();
        label();
    }

    /** 标签 */
    private void label(){
        loadingLabel.setText(String.format("%.2f", progressValue * 100)+"%");
        loadingLabel.setPosition(
                uiViewport.getWorldWidth() / 2 - loadingLabel.getPrefWidth() / 2,
                uiViewport.getWorldHeight() / 2 - loadingLabel.getPrefHeight() / 2
        );
    }

    /** 进度条 */
    private void progressBar() {
        start.x = 0;
        end.x = start.x + uiViewport.getWorldWidth() * progressValue;
        progressBar.setWidth(end.x);
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void dispose() {
        MAIN_STAGE.dispose();
    }
}
