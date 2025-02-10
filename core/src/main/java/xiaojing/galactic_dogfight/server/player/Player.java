package xiaojing.galactic_dogfight.server.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import xiaojing.galactic_dogfight.server.DefaultCamera;
import xiaojing.galactic_dogfight.server.inputProcessor.KeyProcessor;
import xiaojing.galactic_dogfight.server.unit.Entity;
import xiaojing.galactic_dogfight.server.unit.EntityBuilder;

import static xiaojing.galactic_dogfight.Main.gameAssetManager;
import static xiaojing.galactic_dogfight.server.InputConfiguration.*;

/**
 * @author 尽
 * @apiNote 玩家单位
 */
public class Player extends Entity implements KeyProcessor {
    public final String playerName;
    public boolean isAim; // 是否瞄准

    public Player() {
        super(new EntityBuilder()
            .entityIdName("player")
            .texture(gameAssetManager.get("texture/sprite/player/template_player.png"))
            .height(32)
            .width(32)
            .speed(500)
            .rotationalSpeed(100)
            .retreatSpeed(0.5F)
            .build());
        playerName = "player";
        addInputProcessor();
        bodyDef.allowSleep = false;
        bodyDef.bullet = false;
        bodyDef.type = BodyDef.BodyType.KinematicBody;
    }

    /**
     * 绘制
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    /**
     * 添加输入处理器
     */
    @Override
    public void keyDownOverride(int keycode) {
        if (keycode == playerAimSwitchKey){
            isAim = !isAim;
        }
    }

    /** 玩家上移 */
    public void playerMoveUp(float distance){
        translateEntityY(distance);
    }

    /** 玩家下移 */
    public void playerMoveDown(float distance){
        translateEntityY(-distance);
    }

    /** 玩家左移 */
    public void playerMoveLeft(float distance){
        translateEntityX(-distance);
    }

    /** 玩家右移 */
    public void playerMoveRight(float distance){
        translateEntityX(distance);
    }

    /** 移动 */
    public void playerMove(float delta , DefaultCamera camera) {
        playerMove(delta);
//        camera.moveTarget(delta, this);
    }

    public boolean isRight;
    public boolean isLeft;
    public boolean isUp;
    public boolean isDown;

    /** 移动 */
    public void playerMove(float delta) {
        float speed = delta * getSpeed();
        if (isUp) {
            translateForward(speed);
        }
        if (isDown) {
            translateForward(-speed * retreatSpeed);
        }
        if (isMove()){
            if (isRight) {
                rotateRight(rotationalSpeed * delta);
            }
            if (isLeft) {
                rotateLeft(rotationalSpeed * delta);
            }
        }

    }

    /**
     * 更新按键状态
     */
    @Override
    public void updateKeyState(int keycode, boolean state) {
        if (keycode == playerClockwiseRotationKey) {
            isRight = state;
        }
        if (keycode == playerAnticlockwiseRotationKey) {
            isLeft = state;
        }
        if (keycode == playerUpMovementKey) {
            isUp = state;
        }
        if (keycode == playerDownMovementKey) {
            isDown = state;
        }
    }
}
