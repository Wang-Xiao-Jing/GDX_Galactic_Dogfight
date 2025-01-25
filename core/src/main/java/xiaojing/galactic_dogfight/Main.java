package xiaojing.galactic_dogfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.kotcrab.vis.ui.VisUI;
import xiaojing.galactic_dogfight.client.screen.CustomizeLoadingScreen;
import xiaojing.galactic_dogfight.client.screen.StartLoadingScreen;
import xiaojing.galactic_dogfight.client.screen.MainMenuScreen;

/**
 * @author 尽
 * @apiNote 游戏主类，负责游戏的初始化、资源加载、渲染和资源释放
 */
public class Main extends Game {
    // 公共静态资源
    public static Texture emptyTexture;                        // 空纹理
    public static SpriteBatch guIspriteBatch;                     // 渲染器
    public static SpriteBatch gameSpriteBatch;                 // 渲染器
    public static ScreenViewport uiViewport;                   // UI窗口适配器
    public static ExtendViewport gameViewport;                 // 游戏场景窗口适配器
    public static BitmapFont defaultFont;                      // 默认字体
    public static BitmapFont customFont;                       // 自定义字体
    public static Texture pixelTexture;                        // 通用像素染色白图
    public static float scaleFactor = 0.3f;                    // 缩放比例
    public static AssetManager assetManager;                   // 资源管理器
    public static AssetManager gameAssetManager;               // 资源管理器
    public static float delta = 0;

    // 边距
    public static float guiTopMargin = 20f;
    public static float guiBottomMargin = 20f;
    public static float guiLeftMargin = 20f;
    public static float guiRightMargin = 20f;

    // 内部状态变量
    private boolean areResourcesLoaded = false;                 // 资源是否已加载完成
    private float loadingTime = 0;                              // 加载的时间计数
    private boolean isInitializationLoadingScreenDone = false;  // 初始化加载界面
    public boolean loading;                                     // 是否加载
    private MainMenuScreen mainMenuScreen;                      // 菜单界面
    private CustomizeLoadingScreen loadingScreen;               // 加载界面

    /**
     * 初始化
     */
    public void create() {
        guIspriteBatch = new SpriteBatch();
        gameSpriteBatch = new SpriteBatch();
        uiViewport = new ScreenViewport();
        uiViewport.setUnitsPerPixel(scaleFactor);
        gameViewport = new ExtendViewport(1920f * scaleFactor, 1080f * scaleFactor);
        initializeAssetManager();
        VisUI.load();
        loading = true;
    }

    /** 资源载入 */
    private void initializeAssetManager() {
        emptyTexture = new Texture("texture/empty_texture.png");
        assetManager = new AssetManager();
        gameAssetManager = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/silver/silver.ttf";
        mySmallFont.fontParameters.size = 19;
        assetManager.load("texture/gui/loading/loading.fnt", BitmapFont.class);      // 加载界面字体
        assetManager.load("texture/gui/homepage/background.jpg", Texture.class);     // 主页背景
        assetManager.load("fonts/silver/silver.fnt", BitmapFont.class);
        assetManager.load("fonts/silver/silver.ttf", BitmapFont.class, mySmallFont);
        assetManager.load("texture/gui/pixel.png", Texture.class);                   // 通用像素染色白图
        assetManager.load("texture/gui/homepage/title.png", Texture.class);          // 主页标题
        assetManager.load("texture/gui/test/test.json", Skin.class);                 // 测试gui皮肤
        assetManager.load("texture/gui/loading/loading.json", Skin.class);           //
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        uiViewport.update(width, height, true);
        if (getScreen() != null) getScreen().resize(width, height);
    }

    /**
     * 渲染游戏
     */
    @Override
    public void render() {
        delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        ScreenUtils.clear(Color.BLACK); // 清屏
        // 加载完成
        if(assetManager.update()) {
            // 初始化
            if (loadingTime >= 1f){
                disposeLoadingScreen();             // 释放加载界面
                loadingTime = 0;                    // 重置时间
                loading = false;                     // 允许交互
                mainMenuScreen.listener();          // 监听器
            }
            // 初始化资源
            if(!areResourcesLoaded) {
                // 使用 BitmapFont
                defaultFont = assetManager.get("fonts/silver/silver.fnt", BitmapFont.class);
                defaultFont.setUseIntegerPositions(true);
                defaultFont.getData().setScale(uiViewport.getWorldHeight() / Gdx.graphics.getHeight());
                // 使用 FreeType
                customFont = assetManager.get("fonts/silver/silver.ttf", BitmapFont.class);
                customFont.setUseIntegerPositions(true);
                customFont.getData().setScale(uiViewport.getWorldHeight() / Gdx.graphics.getHeight());
                pixelTexture = assetManager.get("texture/gui/pixel.png", Texture.class); // 赋值通用像素染色白图

                mainMenuScreen = new MainMenuScreen(this);  // 创建菜单
                this.setScreen(mainMenuScreen);                   // 设置菜单为当前屏幕
                areResourcesLoaded = true;                        // 资源加载完成
            }
            if (loading && loadingScreen != null  && ! loadingScreen.isExit) {
                loadingScreen.render(delta);
            }
        }

        // 切换时间递增
        if (assetManager.isLoaded("texture/gui/loading/loading.fnt") && loading){
            loadingTime += delta;
        }

        super.render();

        // 渲染加载界面
        if(assetManager.isLoaded("texture/gui/loading/loading.fnt") && loading) {
            // 初始化加载界面
            if (!isInitializationLoadingScreenDone){
                setLoadingScreen(new StartLoadingScreen(this));
                isInitializationLoadingScreenDone = true;
            }
            loadingScreen.render(delta);
        }

        // 当加载页面完成过度时，才允许交互
        if (!loading){
            if (getScreen() instanceof MainMenuScreen menuScreen) {
                menuScreen.interactive();
            }
        }
    }

    /** 设置加载界面 */
    public void setLoadingScreen(CustomizeLoadingScreen loadingScreen){
        this.loadingScreen = loadingScreen;
    }

    /** 获取加载界面 */
    public CustomizeLoadingScreen getLoadingScreen(){
        return loadingScreen;
    }

    /** 释放加载界面 */
    public void disposeLoadingScreen() {
        loadingScreen.dispose();
        loadingScreen = null;
    }

    /**
     * 释放资源
     */
    @Override
    public void dispose() {
        VisUI.dispose();
        assetManager.dispose();
        gameAssetManager.dispose();
        screen.dispose();
        guIspriteBatch.dispose();
        gameSpriteBatch.dispose();
        if (loadingScreen!=null) disposeLoadingScreen();
    }
}
