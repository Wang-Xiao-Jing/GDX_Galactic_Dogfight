package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.AlphaAction;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;
import xiaojing.galactic_dogfight.server.MainGameScreen;

import java.util.Random;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 进入游戏加载界面
 */
public class EnterLoadingScreen extends CustomLoadingScreen {
    float duration = 1f;            // 过度时间
    CustomLabel describeLabel;      // 加载提示标签
    Table labelContainer;           // 提示标签容器
    Image progressBarBackground;    // 进度条
    Image progressBar;              // 进度条
    Image background;               // 背景
    Skin style;                     // 皮肤

    public EnterLoadingScreen(Main game){
        this.game = game;
        main_stage = new Stage(guiViewport);
        style = assetManager.get("texture/gui/loading/loading.json", Skin.class);
        progressBarBackground = new Image(style.getPatch("progressBar-background-horizontal"));
        progressBar = new Image(style.getPatch("progressBar-default-horizontal"));
        background = new Image(Main.pixelTexture);
        action = new AlphaAction();
        labelContainer = new Table();
        describeLabel = new CustomLabel(PlayPrompt(), defaultFont, 1f);
        background.setFillParent(true);
        background.setColor(Color.BLACK);
        progressBarBackground.setHeight(5);
        main_stage.addActor(background);
        main_stage.addActor(progressBarBackground);
        main_stage.addActor(progressBar);
        main_stage.addActor(labelContainer);
        main_stage.addAction(action);
        progressBar.setHeight(5);
        labelContainer.moveBy(5,5);
        labelContainer.defaults().height(describeLabel.getPrefHeight());
        labelContainer.add(describeLabel).width(52).left();
        labelContainer.setHeight(labelContainer.defaults().getPrefHeight());
        labelContainer.top().left();
        adjustSize();
        gameAssetManager.load("texture/bullet/template_bullet.png", Texture.class);
        gameAssetManager.load("texture/enemy/template_enemy.png", Texture.class);
        gameAssetManager.load("texture/outerSpace/outerSpace.png", Texture.class);
        gameAssetManager.load("demo/map/map.tmx", TiledMap.class);
        ((AlphaAction)action).setAlpha(0);
        action.setDuration(duration);
        action.setReverse(true);
//        main_stage.setDebugAll(true);
    }

    float time = 0;
    @Override
    public void render(float delta) {
        guiViewport.apply();   // 应用视口
        main_stage.draw();
        main_stage.act(delta);
        progressBar.setWidth(progressBar.getPrefWidth() +
            (progressBarBackground.getWidth() - progressBar.getPrefWidth()) * gameAssetManager.getProgress());
        action.act(delta);
        time +=delta;
        if (time >= 3) {
            describeLabel.setText(PlayPrompt());
            describeLabel.setFontScale(1);
            describeLabel.setWidth(describeLabel.getPrefWidth());
            time = 0;
        }
        if(((AlphaAction)action).getColor().a >= 1){
            if(gameAssetManager.update()){
                action.restart();
                action.setReverse(false);
                game.setScreen(new MainGameScreen(game));
            }
        }else {
            if (((AlphaAction)action).getColor().a <= 0) isExit = true;
            complete();
        }
    }

    @Override
    public void adjustSize() {
        labelContainer.setWidth(
            guiViewport.getWorldWidth() / 3
        );
        if (labelContainer.getWidth() >= 200) labelContainer.setWidth(200);
        progressBarBackground.setWidth(guiViewport.getWorldWidth() * 0.5f);
        if(progressBarBackground.getWidth() >= 300) progressBarBackground.setWidth(300);
        progressBarBackground.setPosition(
            guiViewport.getWorldWidth() / 2 - progressBarBackground.getWidth() / 2,
            guiViewport.getWorldHeight() * 0.2f - progressBarBackground.getHeight() / 2
        );
        progressBar.setPosition(progressBarBackground.getX(), progressBarBackground.getY());
    }


    @Override
    public void complete(){
        if (!isExit) return;
        game.loading = false;
        game.disposeLoadingScreen();
    }

    @Override
    public void dispose() {
        main_stage.dispose();
    }

    public String PlayPrompt() {
        Random random = new Random();
        String[] txt ={
            "你知道吗: 人的脸皮在生理角度来讲，也是会随着年龄的增长而加厚。",
            "你知道吗: 人类对ccb的开发不足1%。",
            "你知道吗: 肾上腺素风暴（Adrenergic storm）是儿茶酚胺肾上腺素和去甲肾上腺素的血清水平突然而剧烈增加。",
            "你知道吗: 南极3%的冰是企鹅的尿液形成的。",
            "你知道吗: 男性断掉的丁丁，保存得当的话，再移植手术的成功率在90%以上。",
            "你知道吗: 将牛粪放在高温沸水中蒸煮一个小时左右，牛粪就会释放出香兰素，也就是可以散发出香草味的物质。",
            "你知道吗: 金属制品的温度在零下是尝起来是甜的。",
            "你知道吗: 实际上这个作品是因为作者无聊搞的。",
            "（此时一位充满好奇心的南方人舔了一下电线杆）",
            "你知道吗: 电线杆一般是水泥的。",
            "你知道吗: 血清素症候群的癫痫发作时给予不足量的苯二氮卓镇静反而可能诱发全面性发作。",
            "你知道吗: 你最好不要信这些。",
            "黑藓Black_Moss：我每天起早贪黑，把所有空余时间都搭进去，就为了从国外搬运MC资源到国内论坛。只要一有空，我就泡在论坛上忙这事儿。可到最后，怎么所有错都成我的了？行吧，你们厉害，都是大佬，是我不配了？"
        };
        return txt[random.nextInt(txt.length)];
    }
}
