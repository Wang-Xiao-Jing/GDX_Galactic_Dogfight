package xiaojing.galactic_dogfight.server.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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

    public Player() {
        super(new EntityBuilder()
            .unitIdName("player")
            .texture(gameAssetManager.get("texture/sprite/player/template_player.png"))
            .height(32)
            .width(32)
            .speed(1000)
            .rotationalSpeed(100)
            .retreatSpeed(0.5F)
            .build());
        playerName = "player";
        addInputProcessor();
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
//            playerMoveUp(speed);
        }
        if (isDown) {
            translateForward(-speed*retreatSpeed);
//            playerMoveDown(speed);
        }
        if (isMove()){
            if (isRight) {
                rotateRight(rotationalSpeed * delta);
    //            playerMoveRight(speed);
            }
            if (isLeft) {
                rotateLeft(rotationalSpeed * delta);
    //            playerMoveLeft(speed);
            }
        }

    }

    /**
     * 更新按键状态
     */
    @Override
    public void updateKeyState(int keycode, boolean state) {
        if (keycode == playerRightMovementKey) {
            isRight = state;
        }
        if (keycode == playerLeftMovementKey) {
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
