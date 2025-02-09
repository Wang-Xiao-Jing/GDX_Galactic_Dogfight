package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.Input;

/**
 * @author 尽
 * @apiNote 输入配置类
 */
public class InputConfiguration {
    // 玩家操作
    public static int playerLeftMovementKey = Input.Keys.A; // 左移动按键
    public static int playerRightMovementKey = Input.Keys.D; // 右移动按键
    public static int playerUpMovementKey = Input.Keys.W; // 上移动按键
    public static int playerDownMovementKey = Input.Keys.S; // 下移动按键

    // 相机操作
    public static int zoomInKey = Input.Keys.EQUALS; // 放大按键
    public static int zoomOutKey = Input.Keys.MINUS; // 缩小按键
    public static int resetZoomKey = Input.Keys.DEL; // 重置缩放按键
}
