package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 加载画面界面
 */
public class StartLoadingScreen extends CustomizeLoadingScreen {
    final float DURATION = 0.8f;          // 过度时间
    final Stage MAIN_STAGE;               // 总舞台
    final CustomLabel loadingLabel;       // 加载提示标签
    final Image progressBar;              // 进度条
    final Image background;               // 背景
    float progressValue;                  // 进度条值
    final float lineSize;                 // 进度条宽度
    float start;                          // 移动向量
    float end;                            // 移动向量
    final BitmapFont bitmapFont;          // 默认字体

    public StartLoadingScreen(Main game){
        this.game = game;
        this.MAIN_STAGE = new Stage(uiViewport);
        this.bitmapFont = assetManager.get("texture/gui/loading/loading.fnt");
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.drawPixel(0, 0);
        TextureRegion region = new TextureRegion(new Texture(pixmap), 0, 0, 1, 1);
        pixmap.dispose();
        lineSize = 10 * scaleFactor;

        progressBar = new Image(region);
        background = new Image(region);
        background.setColor(Color.BLACK);
        background.setFillParent(true);
        loadingLabel = new CustomLabel(bitmapFont);

        exitAction = new AlphaAction();
        ((AlphaAction)exitAction).setAlpha(0);
        exitAction.setDuration(DURATION);

        MAIN_STAGE.addActor(background);
        MAIN_STAGE.addActor(progressBar);
        MAIN_STAGE.addActor(loadingLabel);
        MAIN_STAGE.addAction(exitAction);
    }

    @Override
    public void render(float delta) {
        MAIN_STAGE.draw();
        progressValue = assetManager.getProgress();
        if (assetManager.update()){
            exitAction.act(delta);
        }
        start = 0;
        end = start + uiViewport.getWorldWidth() * progressValue;
        progressBar.setWidth(end);
        loadingLabel.setText(String.format("%.2f", progressValue * 100)+"%");
        loadingLabel.setPosition(
            uiViewport.getWorldWidth() / 2 - loadingLabel.getPrefWidth() / 2,
            uiViewport.getWorldHeight() / 2 - loadingLabel.getPrefHeight() / 2
        );
    }

    @Override
    public void dispose() {
        MAIN_STAGE.dispose();
    }
}
