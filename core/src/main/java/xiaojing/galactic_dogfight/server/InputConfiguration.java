package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.Input;

/**
 * @author 尽
 * @apiNote 输入配置类
 */
public class InputConfiguration {
    // 玩家操作
    public static int playerAnticlockwiseRotationKey = Input.Keys.A; // 逆时针旋转按键
    public static int playerClockwiseRotationKey = Input.Keys.D; // 顺时针旋转按键
    public static int playerUpMovementKey = Input.Keys.W; // 向前移动按键
    public static int playerDownMovementKey = Input.Keys.S; // 向后移动按键
    public static int playerAimSwitchKey = Input.Keys.Q; // 向后移动按键

    // 相机操作
    public static int zoomInKey = Input.Keys.MINUS; // 放大按键
    public static int zoomOutKey = Input.Keys.EQUALS; // 缩小按键
    public static int resetZoomKey = Input.Keys.DEL; // 重置缩放按键
    public static int centerZoomKey = Input.Keys.E; // 重置缩放按键
}
