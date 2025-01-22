package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

/**
 * @author 尽
 * @apiNote 自定义加载画面
 */
public abstract class CustomizeLoadingScreen extends CustomizeScreenAbstract{
    Game game;
    protected float enterDuration;      // 进入过度时间
    protected float exitDuration;       // 退出过度时间
    protected Stage main_stage;         // 主舞台
    protected TemporalAction enterAction;       // 进入动作
    protected boolean isEnter;          // 进入动作是否完成
    protected TemporalAction exitAction;        // 退出动作
    protected boolean isExit;           // 退出动作是否完成
    protected TemporalAction action;            // 动作

    public CustomizeLoadingScreen(){
        this(0, 0);
    }

    public CustomizeLoadingScreen(float enterDuration, float exitDuration){
        this.enterDuration = enterDuration;
        this.exitDuration = exitDuration;
    }

    /**
     * 渲染
     *
     * @param delta
     */
    @Override
    public void render(float delta) {
        super.render(delta);
        enter(delta);
        exit(delta);
    }

    /** 播放进入动作 */
    public void enter(float delta){
        if (enterDuration <= 0 || isEnter) return;
        if (enterAction.getTime()>= enterDuration) {
            isEnter = true;
            return;
        }
        enterAction.act(delta);
    }

    /** 播放退出动作 */
    public void exit(float delta){
        if (exitDuration <= 0 || isExit) return;
        if (exitAction.getTime()>= exitDuration) {
            isExit = true;
            complete();
            return;
        }
        exitAction.act(delta);
    }

    /** 完成之后执行 */
    public void complete(){
    }
}
