package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Stage;

/**
 * @author 尽
 * @apiNote 自定义加载画面
 */
public abstract class CustomizeLoadingScreen extends CustomizeScreenAbstract{
    protected float enterDuration;      // 进入过度时间
    protected float exitDuration;       // 退出过度时间
    protected Stage main_stage;         // 主舞台
    protected Action enterAction;       // 进入动作
    protected boolean isEnter;          // 进入动作是否完成
    protected Action exitAction;        // 退出动作
    protected boolean isExit;           // 退出动作是否完成
    protected Action action;            // 动作

    public CustomizeLoadingScreen(){
        this(0, 0);
    }

    public CustomizeLoadingScreen(float enterDuration, float exitDuration){
        this.enterDuration = enterDuration;
        this.exitDuration = exitDuration;
        if (enterDuration <= 0) isEnter = true;
        if (exitDuration <= 0) isExit = true;
    }
}
