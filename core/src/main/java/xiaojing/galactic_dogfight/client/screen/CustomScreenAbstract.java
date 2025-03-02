package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.ScreenAdapter;

/**
 * @author 尽
 * @apiNote 抽象类，所有自定义的Screen类都需要继承此类
 */
public abstract class CustomScreenAbstract extends ScreenAdapter {
    /**
     * 渲染
     */
    @Override
    public void render(float delta) {

    }

    /**
     * 改变大小窗口大小时执行
     */
    @Override
    public void resize(int width, int height) {

    }

    /**
     * 当此屏幕成为游戏的当前屏幕时调用
     */
    @Override
    public void show() {

    }

    /**
     * 当此屏幕不再成为游戏的当前屏幕时调用
     */
    @Override
    public void hide() {
    }


    /**
     * 当游戏暂停时调用
     */
    @Override
    public void pause() {

    }

    /**
     * 当游戏恢复时调用
     */
    @Override
    public void resume() {

    }

    /**
     * 释放资源
     */
    @Override
    public void dispose() {

    }


    /**
     * 调整大小时执行
     */
    public void adjustSize() {

    }

    /**
     * 切换时执行
     */
    public void switchPages() {
        listener();
    }

    /**
     * 持续执行方法
     */
    public void interactive() {

    }

    /**
     * 初始化监听器
     */
    public void listener() {

    }

    /**
     * 调整子控件大小
     */
    protected void guiChildSize() {

    }
}
