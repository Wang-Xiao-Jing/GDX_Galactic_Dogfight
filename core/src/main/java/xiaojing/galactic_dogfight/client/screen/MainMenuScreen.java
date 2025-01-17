package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;

import static xiaojing.galactic_dogfight.Main.*;


/**
 * @author 尽
 * @apiNote 主菜单屏幕
 */
public class MainMenuScreen extends ScreenAdapter {
    final Stage MAIN_STAGE;                         // 总舞台
    final Container<Actor> BACKGROUND_CONTAINER;    // 背景容器
    final Container<Actor> GUI_CONTAINER;           // GUI容器
    MainMenuConfigActor configActor;                        // 配置界面
    MainMenuActor mainMenuActor;                    // 主菜单界面

    /**
     * 构造函数
     */
    public MainMenuScreen() {
        this.MAIN_STAGE = new Stage(uiViewport);
        Gdx.input.setInputProcessor(MAIN_STAGE);
        BACKGROUND_CONTAINER = new Container<>(new Image(manager.get("texture/gui/homepage/background.jpg", Texture.class)));
        GUI_CONTAINER = new Container<>();
        // 初始基本部分
        BACKGROUND_CONTAINER.setFillParent(true);
        GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - guiMarginsLeft - guiMarginsRight,
            uiViewport.getWorldHeight() - guiMarginsTop - guiMarginsBottom
        );
        GUI_CONTAINER.setFillParent(true);
        // 显示调试信息
//        MAIN_STAGE.setDebugAll(true);
        MAIN_STAGE.addActor(BACKGROUND_CONTAINER);
        MAIN_STAGE.addActor(GUI_CONTAINER);
        mainMenuActor = new MainMenuActor(this, GUI_CONTAINER);
        configActor = new MainMenuConfigActor(this, GUI_CONTAINER);
        GUI_CONTAINER.setActor(mainMenuActor);
    }

    /**
     * 当屏幕应该自行渲染时调用。
     */
    @Override
    public void render(float delta) {
        batch.setProjectionMatrix(uiViewport.getCamera().combined); // 设置投影矩阵
        uiViewport.apply();   // 应用视口
        MAIN_STAGE.draw();
        MAIN_STAGE.act(Gdx.graphics.getDeltaTime());
        batch.begin();      // 开始绘制
        batch.end();// 结束绘制
    }

    /** 交互 */
    public void interactive() {
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuActor actor){
            actor.interactive();
        }
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuConfigActor actor) {
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
     * 调整应用程序大小时调用。这可以在非暂停状态下的任何时候发生，但在调用create()之前永远不会发生。
     */
    @Override
    public void resize(int width, int height) {
        uiViewport.update(width, height);
    }

    /**
     * 监听器
     */
    public void listener() {
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuActor){
            mainMenuActor.listener(this, GUI_CONTAINER);
        }
        if (GUI_CONTAINER.getChild(0) instanceof MainMenuConfigActor) {
            configActor.listener(this, GUI_CONTAINER);
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
