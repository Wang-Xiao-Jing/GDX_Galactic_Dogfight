package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 进入游戏加载界面
 */
public class EnterLoadingScreen extends CustomizeLoadingScreen {
    float duration = 1f;            // 过度时间
    float progressValue;            // 进度条值
    CustomLabel loadingLabel;       // 加载提示标签
    Image progressBarBackground;    // 进度条
    Image progressBar;              // 进度条
    Image background;               // 背景
    BitmapFont bitmapFont;          // 默认字体
    Skin style;                     // 皮肤

    public EnterLoadingScreen(){
        main_stage = new Stage(uiViewport);
        style = manager.get("texture/gui/loading/loading.json", Skin.class);
        progressBarBackground = new Image(style.getPatch("progressBar-background-horizontal"));
        progressBar = new Image(style.getPatch("progressBar-default-horizontal"));
        background = new Image(Main.PIXEL_PNG);
        action = new AlphaAction();
//        main_stage.setDebugAll(true);
//        loadingLabel = new CustomLabel("加载中...", bitmapFont, 0.8f);
//        this.bitmapFont = manager.get("fonts/silver/silver.fnt");
//        action = new AlphaAction();
//        action.setAlpha(0);
//        action.setDuration(duration)
        background.setFillParent(true);
        background.setColor(Color.BLACK);
        progressBarBackground.setHeight(5);
        main_stage.addActor(background);
        main_stage.addActor(progressBarBackground);
        main_stage.addActor(progressBar);
        main_stage.addAction(action);
        progressBar.setHeight(5);
        adjustSize();
        gameManager.load("texture/bullet/template_bullet.png", Texture.class);
        gameManager.load("texture/enemy/template_enemy.png", Texture.class);
        ((AlphaAction)action).setAlpha(0);
        ((AlphaAction)action).setDuration(duration);
        ((AlphaAction)action).setReverse(true);
    }

    @Override
    public void render(float delta) {
//        batch.setProjectionMatrix(uiViewport.getCamera().combined); // 设置投影矩阵
        uiViewport.apply();   // 应用视口
        main_stage.draw();
        main_stage.act(delta);
        progressBar.setWidth(progressBar.getPrefWidth() +
            (progressBarBackground.getWidth() - progressBar.getPrefWidth()) * gameManager.getProgress());
        action.act(delta);
        if(((AlphaAction)action).getColor().a >= 1){
            if(gameManager.update()){
                action.restart();
                ((AlphaAction)action).setReverse(false);
                if (((AlphaAction)action).getColor().a <= 0){
                    dispose();
                }
            }
        }
    }

    /** 调整大小 */
    @Override
    public void adjustSize() {
        progressBarBackground.setWidth(uiViewport.getWorldWidth()*0.5f);
        if(progressBarBackground.getWidth() >= 300) progressBarBackground.setWidth(300);
        progressBarBackground.setPosition(
            uiViewport.getWorldWidth()/2 - progressBarBackground.getWidth()/2,
            uiViewport.getWorldHeight() * 0.2f - progressBarBackground.getHeight()/2
        );
        progressBar.setPosition(progressBarBackground.getX(), progressBarBackground.getY());
    }

    @Override
    public void dispose() {
        main_stage.dispose();
    }
}
