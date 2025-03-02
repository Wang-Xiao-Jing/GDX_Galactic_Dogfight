package xiaojing.galactic_dogfight.server.entity.player;

import com.badlogic.gdx.graphics.g2d.Batch;
import xiaojing.galactic_dogfight.server.DefaultCamera;
import xiaojing.galactic_dogfight.server.entity.Entity;
import xiaojing.galactic_dogfight.server.entity.EntityBuilder;
import xiaojing.galactic_dogfight.server.inputProcessor.KeyProcessor;

import static xiaojing.galactic_dogfight.Main.gameAssetManager;
import static xiaojing.galactic_dogfight.server.InputConfiguration.*;

/**
 * 玩家载具实体
 *
 * @author 尽
 * @apiNote 玩家载具实实体的移动和其他功能
 */
public class PlayerVehicle extends Entity implements KeyProcessor {

    public final Player player; // 玩家
    public boolean isAim; // 是否瞄准
    public boolean isRight;
    public boolean isLeft;
    public boolean isUp;
    public boolean isDown;

    public PlayerVehicle() {
        this(
            new Player(new Player.PlayerBuilder("player")),
            new EntityBuilder("player")
                .texture(gameAssetManager.get("texture/sprite/player/template_player.png"))
                .height(32)
                .width(32)
                .speedMoving(1)
                .rotationalSpeed(100)
                .retreatSpeed(0.5F)
                .reductionRatio(0.1F)
                .build());
        addInputProcessor();
    }

    public PlayerVehicle(Player player, EntityBuilder builder) {
        super(builder);
        this.player = player;
        addInputProcessor();
        bodyDef.allowSleep = false;
        bodyDef.bullet = false;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        player.render(delta);
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
        if (keycode == playerAimSwitchKey) {
            isAim = !isAim;
        }
    }

    @Override
    public void keyUpOverride(int keycode) {
        if (keycode == playerUpMovementKey) {
            isPressTheMobileButton = false;
        }
    }

    /**
     * 载具上移
     */
    public void playerMoveUp(float distance) {
        translateEntityY(distance);
    }

    /**
     * 载具下移
     */
    public void playerMoveDown(float distance) {
        translateEntityY(-distance);
    }

    /**
     * 载具左移
     */
    public void playerMoveLeft(float distance) {
        translateEntityX(-distance);
    }

    /**
     * 载具右移
     */
    public void playerMoveRight(float distance) {
        translateEntityX(distance);
    }

    /**
     * 载具移动
     */
    public void playerMove(float delta, DefaultCamera camera) {
        playerMove(delta);
        synchro();
//        camera.moveTarget(delta, this);
    }

    /**
     * 载具移动
     */
    public void playerMove(float delta) {
        float speed = delta * speedMoving;
        float speedRotational = rotationalSpeed * delta;

        if (isDown) {
//            applyForce(retreatSpeed); // TODO: 请添加减速功能
        }
        if (isUp) {
            translateForward(speed * (isDown ? retreatSpeed : 1));
            if (isRight) {
                rotateEntity(-speedRotational);
            }
            if (isLeft) {
                rotateEntity(speedRotational);
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
        if (!isUp && !isDown) {
            isPressTheMobileButton = state;
        }
    }
}
