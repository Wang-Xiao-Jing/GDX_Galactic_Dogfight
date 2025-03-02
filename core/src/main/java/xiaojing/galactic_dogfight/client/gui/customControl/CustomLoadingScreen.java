package xiaojing.galactic_dogfight.client.gui.customControl;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;
import xiaojing.galactic_dogfight.Main;

/**
 * 自定义加载画面
 *
 * @author 尽
 * @apiNote
 */
public abstract class CustomLoadingScreen extends CustomScreenAbstract {
    public float enterDuration;              // 进入过度时间
    public float exitDuration;               // 退出过度时间
    public Stage main_stage;                 // 主舞台
    public TemporalAction enterAction;       // 进入动作
    public boolean isEnter;                  // 进入动作是否完成
    public TemporalAction exitAction;        // 退出动作
    public boolean isExit;                   // 退出动作是否完成
    public TemporalAction action;            // 动作
    Main game;                               // 游戏实例

    public CustomLoadingScreen() {
        this(0, 0);
    }

    public CustomLoadingScreen(float enterDuration, float exitDuration) {
        this.enterDuration = enterDuration;
        this.exitDuration = exitDuration;
    }

    /**
     * 渲染时调用
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        enter(delta);
        exit(delta);
    }

    /**
     * 播放进入动作
     */
    public void enter(float delta) {
        if (enterDuration <= 0 || isEnter) return;
        if (enterAction.getTime() >= enterDuration) {
            isEnter = true;
            return;
        }
        enterAction.act(delta);
    }

    /**
     * 播放退出动作
     */
    public void exit(float delta) {
        if (exitDuration <= 0 || isExit) return;
        if (exitAction.getTime() >= exitDuration) {
            isExit = true;
            complete();
            return;
        }
        exitAction.act(delta);
    }

    /**
     * 完成之后调用
     */
    public void complete() {
    }
}
