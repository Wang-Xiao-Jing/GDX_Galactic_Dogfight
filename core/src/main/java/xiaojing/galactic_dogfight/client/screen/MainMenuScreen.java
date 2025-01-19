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
public class MainMenuScreen extends ScreenAdapter{
    final Stage MAIN_STAGE;                         // 总舞台
    final Container<Actor> BACKGROUND_CONTAINER;    // 背景容器
    final Container<Actor> GUI_CONTAINER;           // GUI容器
    MainMenuConfigActor configActor;                // 配置界面
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
        GUI_CONTAINER.setFillParent(true);
        // 显示调试信息
        MAIN_STAGE.setDebugAll(true);
        MAIN_STAGE.addActor(BACKGROUND_CONTAINER);
        MAIN_STAGE.addActor(GUI_CONTAINER);
        mainMenuActor = new MainMenuActor();
        configActor = new MainMenuConfigActor();
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
        ((CustomizeGuiGroup) GUI_CONTAINER.getChild(0)).interactive();
    }

    /**
     * 调整应用程序大小时调用。这可以在非暂停状态下的任何时候发生，但在调用create()之前永远不会发生。
     */
    @Override
    public void resize(int width, int height) {
        GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - guiMarginsLeft - guiMarginsRight,
            uiViewport.getWorldHeight() - guiMarginsTop - guiMarginsBottom
        );
        guiChildSize();
    }

    /** 调整子元素大小 */
    private void guiChildSize() {
        ((CustomizeGuiGroup) GUI_CONTAINER.getChild(0)).adjustSize(
            GUI_CONTAINER.getWidth(),
            GUI_CONTAINER.getHeight()
        );
    }

    /**
     * 监听器
     */
    public void listener() {
        ((CustomizeGuiGroup) GUI_CONTAINER.getChild(0)).listener(this);
    }

    /**
     * 当此屏幕应释放所有资源时调用。
     */
    @Override
    public void dispose() {
        MAIN_STAGE.dispose();
    }

    /** 切换页面 */
    public void switchPages(){
        listener();
        GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - guiMarginsLeft - guiMarginsRight,
            uiViewport.getWorldHeight() - guiMarginsTop - guiMarginsBottom
        );
        guiChildSize();
    }
}
