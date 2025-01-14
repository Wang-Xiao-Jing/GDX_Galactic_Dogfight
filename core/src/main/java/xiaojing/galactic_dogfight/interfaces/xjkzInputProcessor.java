package xiaojing.galactic_dogfight.interfaces;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

/**
 * @author 尽
 * @apiNote 输入处理
 */
public class xjkzInputProcessor extends InputAdapter implements InputProcessor {

    /**
     * 处理键盘按下事件
     *
     * @param keycode 按下的键盘键值
     */
    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    /**
     * 处理键盘抬起事件
     *
     * @param keycode 抬起的键盘键值
     */
    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    /**
     * 处理键盘字符输入事件
     *
     * @param character 输入的字符
     */
    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    /**
     * 处理触摸屏按下事件
     *
     * @param screenX 屏幕X坐标
     * @param screenY 屏幕Y坐标
     * @param pointer 触摸的指针ID
     * @param button 触摸的按钮ID
     */
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * 处理触摸屏抬起事件
     *
     * @param screenX 屏幕X坐标
     * @param screenY 屏幕Y坐标
     * @param pointer 触摸的指针ID
     * @param button 触摸的按钮ID
     */
    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * 处理触摸屏触摸取消事件
     *
     * @param screenX 屏幕X坐标
     * @param screenY 屏幕Y坐标
     * @param pointer 触摸的指针ID
     * @param button 触摸的按钮ID
     */
    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    /**
     * 处理触摸屏拖动事件
     *
     * @param screenX 屏幕X坐标
     * @param screenY 屏幕Y坐标
     * @param pointer 触摸的指针ID
     */
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    /**
     * 处理鼠标移动事件
     *
     * @param screenX 屏幕X坐标
     * @param screenY 屏幕Y坐标
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    /**
     * 处理滚动事件
     *
     * @param amountX 滚动的水平分量
     * @param amountY 滚动的垂直分量
     */
    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
