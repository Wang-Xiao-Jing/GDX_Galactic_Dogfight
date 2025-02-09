package xiaojing.galactic_dogfight.server.inputProcessor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;

import static xiaojing.galactic_dogfight.Main.multiplexer;

/**
 * @author xiaojing
 * @apiNote 键盘控制接口
 */
@FunctionalInterface
public interface KeyProcessor {

    /**  添加键盘控制 */
    default void addInputProcessor(){
        multiplexer.addProcessor(new InputAdapter() {
            @Override
            public boolean keyDown (int keycode) {
                updateKeyState(keycode, true);
                return false;
            }

            @Override
            public boolean keyUp (int keycode) {
                updateKeyState(keycode, false);
                return false;
            }
        });
    }

    /** 更新按键状态 */
    void updateKeyState(int keycode, boolean state);
}
