package xiaojing.galactic_dogfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import xiaojing.galactic_dogfight.client.screen.CustomLoadingScreen;
import xiaojing.galactic_dogfight.client.screen.MainMenuScreen;
import xiaojing.galactic_dogfight.client.screen.StartLoadingScreen;
import xiaojing.galactic_dogfight.core.register.Registries;
import xiaojing.galactic_dogfight.i18n.I18N;

/**
 * @author 尽
 * @apiNote 游戏主类，负责游戏的初始化、资源加载、渲染和资源释放
 */
public class Main extends Game {
    // TODO - 部分数据应该移动到 Setting.
    // 公共静态资源
    public static Texture emptyTexture;                        // 空纹理
    public static SpriteBatch gameSpriteBatch;                 // 渲染器
    public static ScreenViewport guiViewport;                  // UI窗口适配器
    public static ExtendViewport gameViewport;                 // 游戏场景窗口适配器
    public static BitmapFont defaultFont;                      // 默认字体

    public static Texture pixelTexture;                        // 通用像素染色白图
    public static float globalScaleFactor = 0.3f;              // 缩放比例
    public static AssetManager assetManager;                   // 资源管理器
    public static AssetManager gameAssetManager;               // 资源管理器
    public static float delta = 0;
    public static float cameraZoomRatio = 1f;                  // 相机缩放倍率
    public static float gameViewportWidth = 1920f;             // 相机缩放倍率
    public static float gameViewportHeight = 1080f;            // 相机缩放倍率

    // 边距
    public static float guiTopMargin = 20f;
    public static float guiBottomMargin = 20f;
    public static float guiLeftMargin = 20f;
    public static float guiRightMargin = 20f;

    public static InputMultiplexer multiplexer;                 // 输入处理器
    public static Main INSTANCES;
    public I18N language;

    private boolean isLoading;                                  // 是否加载
    private boolean isResourcesLoaded = false;                  // 资源是否已加载完成
    private float loadingTime = 0;                              // 加载的时间计数
    private boolean isInitializationLoadingScreenDone = false;  // 初始化加载界面
    private MainMenuScreen mainMenuScreen;                      // 菜单界面
    private CustomLoadingScreen loadingScreen;

    public boolean getLoading(){
        return isLoading;
    }

    public void setIsLoading(boolean isLoading){
        this.isLoading = isLoading;
    }

    /**
     * 初始化
     */
    public void create() {
        INSTANCES = this;

        this.language = new I18N("en_us");
        Registries.init();

        gameSpriteBatch = new SpriteBatch();
        guiViewport = new ScreenViewport();
        guiViewport.setUnitsPerPixel(globalScaleFactor);
        gameViewport = new ExtendViewport(gameViewportWidth * globalScaleFactor * cameraZoomRatio, gameViewportHeight * globalScaleFactor * cameraZoomRatio);
        initializeAssetManager();
        isLoading = true;
        multiplexer = new InputMultiplexer();
        Gdx.input.setInputProcessor(multiplexer);

        Registries.frozen();
    }

    /**
     * 资源载入
     */
    private void initializeAssetManager() {
        assetManager = new AssetManager();
        gameAssetManager = new AssetManager();
        // 添加新的加载器
        gameAssetManager.setLoader(TiledMap.class, new TmxMapLoader(new InternalFileHandleResolver()));
        FileHandleResolver resolver = new InternalFileHandleResolver();
        assetManager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        assetManager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
//        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
//        mySmallFont.fontFileName = "fonts/silver/silver.ttf";
//        mySmallFont.fontParameters.size = 19;
        assetManager.load("fonts/loading/loading.fnt", BitmapFont.class);            // 加载界面字体
        assetManager.load("texture/gui/homepage/background.jpg", Texture.class);     // 主页背景
        assetManager.load("fonts/silver/silver.fnt", BitmapFont.class);              // 默认字体
//        assetManager.load("fonts/silver/silver.ttf", BitmapFont.class, mySmallFont);
        assetManager.load("texture/gui/homepage/title.png", Texture.class);          // 主页标题
        assetManager.load("texture/gui/test/test.json", Skin.class);                 // 测试gui皮肤
        assetManager.load("texture/gui/loading/loading.json", Skin.class);           // 加载界面皮肤
    }

    @Override
    public void resize(int width, int height) {
        gameViewport.update(width, height);
        guiViewport.update(width, height, true);
        guiSpriteBatch.setProjectionMatrix(guiViewport.getCamera().combined); // 设置投影矩阵
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
        if (assetManager.update()) {
            // 初始化
            if (loadingTime >= 1f) {
                disposeLoadingScreen();
                loadingTime = 0;
                isLoading = false;
                mainMenuScreen.listener();
            }
            // 初始化资源
            if (!isResourcesLoaded) {
                resourcesLoaded();
            }
            if (isLoading && loadingScreen != null && !loadingScreen.isExit) {
                loadingScreen.render(delta);
            }
        }

        // 切换时间递增
        if (assetManager.isLoaded("fonts/loading/loading.fnt") && isLoading) {
            loadingTime += delta;
        }

        super.render();

        // 渲染加载界面
        if (assetManager.isLoaded("fonts/loading/loading.fnt") && isLoading) {
            // 初始化加载界面
            if (!isInitializationLoadingScreenDone) {
                setLoadingScreen(new StartLoadingScreen(this));
                isInitializationLoadingScreenDone = true;
            }
            loadingScreen.render(delta);
        }

        // 当加载页面完成过度时，才允许交互
        if (!isLoading) {
            if (getScreen() instanceof MainMenuScreen menuScreen) {
                menuScreen.interactive();
            }
        }
    }

    private void resourcesLoaded() {
        defaultFont = assetManager.get("fonts/silver/silver.fnt", BitmapFont.class);
        defaultFont.setUseIntegerPositions(true);
        defaultFont.getData().setScale(guiViewport.getWorldHeight() / Gdx.graphics.getHeight());
//                customFont = assetManager.get("fonts/silver/silver.ttf", BitmapFont.class);
//                customFont.setUseIntegerPositions(true);
//                customFont.getData().setScale(guiViewport.getWorldHeight() / Gdx.graphics.getHeight());
        mainMenuScreen = new MainMenuScreen(this); // 创建菜单
        this.setScreen(mainMenuScreen); // 设置菜单为当前屏幕
        isResourcesLoaded = true; // 资源加载完成
    }

    /**
     * 获取加载界面
     */
    public CustomLoadingScreen getLoadingScreen() {
        return loadingScreen;
    }

    /**
     * 设置加载界面
     */
    public void setLoadingScreen(CustomLoadingScreen loadingScreen) {
        this.loadingScreen = loadingScreen;
    }

    /**
     * 释放加载界面
     */
    public void disposeLoadingScreen() {
        loadingScreen.dispose();
        loadingScreen = null;
    }

    /**
     * 释放资源
     */
    @Override
    public void dispose() {
        StaticClass.dispose();
        defaultFont.dispose();
        assetManager.dispose();
        gameAssetManager.dispose();
        screen.dispose();
        guiSpriteBatch.dispose();
        gameSpriteBatch.dispose();
        if (loadingScreen != null) disposeLoadingScreen();
    }
}
