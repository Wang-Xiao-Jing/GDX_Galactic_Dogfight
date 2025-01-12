package xiaojing.galactic_dogfight.client.screen.mainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.QuickMethod;


/**
 * @Author: 尽
 * @Description: 主菜单屏幕
 */
public class MainMenuScreen implements Screen {
    AssetManager manager;                           // 资源管理器
    final Main GAME;                                // 游戏实例
    FitViewport viewport;                           // 视口实例
    final SpriteBatch BATCH;                        // 用于绘制的SpriteBatch实例
    final Stage MAIN_STAGE;                         // 总舞台
    final Container<Actor> BACKGROUND_CONTAINER;    // 背景容器
    final Container<Actor> GUI_CONTAINER;           // GUI容器
    ConfigActor configActor;                        // 配置界面
    MainMenuActor mainMenuActor;                    // 主菜单界面

    /**
     * 构造方法
     */
    public MainMenuScreen(final Main GAME) {
        this.GAME = GAME;
        this.viewport = this.GAME.viewport;
        this.BATCH = this.GAME.batch;
        this.manager = this.GAME.manager;
        this.MAIN_STAGE = new Stage(viewport);
        Gdx.input.setInputProcessor(MAIN_STAGE);
        BACKGROUND_CONTAINER = new Container<>(new Image(manager.get("texture/gui/homepage/background.jpg", Texture.class)));
        GUI_CONTAINER = new Container<>();
        // 初始基本部分
        BACKGROUND_CONTAINER.setFillParent(true);
        GUI_CONTAINER.setSize(
            viewport.getWorldWidth() - this.GAME.guiMarginsLeft - this.GAME.guiMarginsRight,
            viewport.getWorldHeight() - this.GAME.guiMarginsTop - this.GAME.guiMarginsBottom
        );
        GUI_CONTAINER.setFillParent(true);
        // 显示调试信息
//        MAIN_STAGE.setDebugAll(true);
        MAIN_STAGE.addActor(BACKGROUND_CONTAINER);
        MAIN_STAGE.addActor(GUI_CONTAINER);
        mainMenuActor = new MainMenuActor(this, GUI_CONTAINER);
        configActor = new ConfigActor(this, GUI_CONTAINER);
        GUI_CONTAINER.setActor(mainMenuActor);
    }

    /**
     * 当屏幕应该自行渲染时调用。
     */
    @Override
    public void render(float delta) {
        BATCH.setProjectionMatrix(viewport.getCamera().combined); // 设置投影矩阵
        viewport.apply();   // 应用视口
        MAIN_STAGE.draw();
        MAIN_STAGE.act(Gdx.graphics.getDeltaTime());
        BATCH.begin();      // 开始绘制
        BATCH.end();// 结束绘制
    }

    /** 交互 */
    public void interactive() {
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuActor actor){
            actor.interactive();
        }
        if (GUI_CONTAINER.getChild(0) instanceof ConfigActor actor) {
            actor.interactive();
        }
    }

    /**
     * 当此屏幕成为游戏的当前屏幕时调用。
     */
    @Override
    public void show() {

    }

    /**
     * 当应用程序暂停时调用，通常是当它不活动或在屏幕上不可见时。应用程序在销毁之前也会暂停。
     */
    @Override
    public void pause() {

    }

    /**
     * 当应用程序从暂停状态恢复时调用，通常是当它重新获得焦点时。
     */
    @Override
    public void resume() {

    }

    /**
     * 当此屏幕不再是游戏的当前屏幕时调用。
     */
    @Override
    public void hide() {

    }

    /**
     * 调整应用程序大小时调用。这可以在非暂停状态下的任何时候发生，但在调用create（）之前永远不会发生。
     */
    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    /**
     * 监听器
     */
    public void listener() {
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuActor actor){
            actor.listener(this, GUI_CONTAINER);
        }
        if (GUI_CONTAINER.getChild(0) instanceof ConfigActor actor) {
            actor.listener(this, GUI_CONTAINER);
        }
    }

    /**
     * 当此屏幕应释放所有资源时调用。
     */
    @Override
    public void dispose() {
        MAIN_STAGE.dispose();
    }

    public void switchPages(){
        listener();
    }
}
