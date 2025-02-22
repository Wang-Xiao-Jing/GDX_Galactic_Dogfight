package xiaojing.galactic_dogfight.server.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import xiaojing.galactic_dogfight.server.DefaultCamera;
import xiaojing.galactic_dogfight.server.inputProcessor.KeyProcessor;
import xiaojing.galactic_dogfight.server.entity.Entity;
import xiaojing.galactic_dogfight.server.entity.EntityBuilder;

import static xiaojing.galactic_dogfight.Main.gameAssetManager;
import static xiaojing.galactic_dogfight.server.InputConfiguration.*;

/**
 * @author 尽
 * @apiNote 玩家实体
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
            .speed(5)
            .rotationalSpeed(100)
            .retreatSpeed(0.5F)
            .reductionRatio(0.5F)
            .build());
        playerName = "player";
        addInputProcessor();
//        multiplexer.addProcessor(new InputAdapter(){
//
//        });
        bodyDef.allowSleep = false;
        bodyDef.bullet = false;
    }

    /**
     * 绘制
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
    }

    @Override
    public void keyDownOverride(int keycode) {
        if (keycode == playerAimSwitchKey){
            isAim = !isAim;
        }
    }

    @Override
    public void keyUpOverride(int keycode) {
        if (keycode == playerUpMovementKey || keycode == playerDownMovementKey) {
            isPressTheMobileButton = false;
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
        synchro();
//        camera.moveTarget(delta, this);
    }

    public boolean isRight;
    public boolean isLeft;
    public boolean isUp;
    public boolean isDown;

    /** 移动 */
    public void playerMove(float delta) {
        float speed = delta * getSpeed()*10000;
        if (isUp) {
            translateForward(speed);
            if (isRight) {
                rotateRight(rotationalSpeed * delta);
            }
            if (isLeft) {
                rotateLeft(rotationalSpeed * delta);
            }
        }
        if (isDown) {
            translateForward(-speed * retreatSpeed);
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
        if (!isUp && !isDown) {
            isPressTheMobileButton = state;
        }
    }
}
