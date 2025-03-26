package xiaojing.galactic_dogfight;

import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/** 静态资源 */
public class StaticClass {
    // 空纹理
    public final static Texture EMPTY_TEXTURE = new Texture("texture/empty_texture.png");
    // 通用像素染色白图
    public final static Texture PIXEL_TEXTURE = new Texture("texture/gui/pixel.png"); // 赋值通用像素染色白图

    public static SpriteBatch guiSpriteBatch;                  // 渲染器
    public static SpriteBatch gameSpriteBatch;                 // 渲染器
    public static ScreenViewport guiViewport;                  // UI窗口适配器
    public static ExtendViewport gameViewport;                 // 游戏场景窗口适配器
    public static BitmapFont defaultFont;                      // 默认字体

    public static float globalScaleFactor = 0.3f;              // 缩放比例
    public static AssetManager assetManager;                   // 资源管理器
    public static AssetManager gameAssetManager;               // 资源管理器
    public static float delta = 0;
    public static final float BASICS_CAMERA_ZOOM_RATIO = 1f;   // 相机缩放倍率
    public static float cameraZoomRatio = 0;                   // 相机缩放增幅器
    public static final float GAME_VIEWPORT_WIDTH = 1920f;     // 相机缩放倍率
    public static final float GAME_VIEWPORT_HEIGHT = 1080f;    // 相机缩放倍率

    // 边距
    public static float guiTopMargin = 20f;
    public static float guiBottomMargin = 20f;
    public static float guiLeftMargin = 20f;
    public static float guiRightMargin = 20f;

    public static InputMultiplexer multiplexer;                 // 输入处理器

    public static void dispose() {
        defaultFont.dispose();
        assetManager.dispose();
        gameAssetManager.dispose();
        guiSpriteBatch.dispose();
        gameSpriteBatch.dispose();
        EMPTY_TEXTURE.dispose();
        PIXEL_TEXTURE.dispose();
        System.out.println("[StaticClass] complete");
    }
}
