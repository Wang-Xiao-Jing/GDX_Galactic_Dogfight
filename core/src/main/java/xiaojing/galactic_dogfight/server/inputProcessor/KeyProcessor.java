package xiaojing.galactic_dogfight.server.inputProcessor;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputProcessor;

import static xiaojing.galactic_dogfight.StaticClass.multiplexer;

/**
 * 键盘控制接口
 * @apiNote
 */
public interface KeyProcessor extends InputProcessor {

    /**
     * 添加键盘控制
     */
    default void addInputProcessor() {
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                updateKeyState(keycode, true);
                keyDownOverride(keycode);
                return false;
            }

            @Override
            public boolean keyUp(int keycode) {
                updateKeyState(keycode, false);
                keyUpOverride(keycode);
                return false;
            }
        });
    }

    default void keyDownOverride(int keycode) {
    }

    default void keyUpOverride(int keycode) {
    }

    /**
     * 更新按键状态
     */
    default void updateKeyState(int keycode, boolean state) {
    }

    @Override
    default boolean keyUp(int keycode) {
        return false;
    }

    @Override
    default boolean keyDown(int keycode) {
        return false;
    }

    @Override
    default boolean keyTyped(char character) {
        return false;
    }

    @Override
    default boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    default boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    default boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    default boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    default boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    default boolean scrolled(float amountX, float amountY) {
        return false;
    }
}
