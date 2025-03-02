package xiaojing.galactic_dogfight.server;

import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.server.scene.DefaultScene;

/**
 * 主游戏画面（场景）
 * @author 尽
 * @apiNote
 */
public class MainGameScreen extends DefaultScene {
    public static boolean isGameScreenPause;

    public MainGameScreen(final Main GAME) {
        super(GAME);
    }


    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
    }

    @Override
    public void pause() {
        super.pause();
        isGameScreenPause = !isGameScreenPause;
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void hide() {
        super.hide();
    }

    @Override
    public void dispose() {
        super.dispose();
    }
}
