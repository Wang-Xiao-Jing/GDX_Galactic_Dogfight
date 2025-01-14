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
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import xiaojing.galactic_dogfight.client.screen.StartLoadingScreen;
import xiaojing.galactic_dogfight.client.screen.mainMenuScreen.MainMenuScreen;
import xiaojing.galactic_dogfight.interfaces.xjkzInputProcessor;

/**
 * @author 尽
 * @apiNote 主类
 */
public class Main extends Game {
    public static Texture EMPTY_TEXTURE;

    private boolean resourcesLoaded = false;            // 资源是否已加载完成
    private float loadingDelta = 0;                     // 加载的时间计数
    private boolean initializeLoadingScreen = false;    // 初始化加载界面
    private boolean loadingScreen = false;              // 是否渲染加载页面 & 菜单交互

    public SpriteBatch batch;                           // 渲染器
    public FitViewport viewport;                        // 窗口适配器
    public static BitmapFont bitmapFont;                // 默认字体
    public static BitmapFont freeTypeFont;              // 自定义字体
    xjkzInputProcessor inputProcessor;                  // 输入处理器
    public float scale = 4f;                            // 缩放比例
    public AssetManager manager;                        // 资源管理器

    private MainMenuScreen mainMenuScreen;              // 菜单界面
    private StartLoadingScreen startLoadingScreen;      // 加载界面
    public Texture PIXEL_PNG;                           // 通用像素染色白图

    // 边距
    public float guiMarginsTop = 20f;
    public float guiMarginsBottom = 20f;
    public float guiMarginsLeft = 20f;
    public float guiMarginsRight = 20f;

    /**
     * 初始化
     */
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(1920f / scale, 1080f / scale);
        inputProcessor = new xjkzInputProcessor();
        manager();
        Gdx.input.setInputProcessor(inputProcessor);
        VisUI.load();
//        manager.update(60);
    }

    /** 资源载入 */
    private void manager() {
        EMPTY_TEXTURE = new Texture("texture/empty_texture.png");
        manager = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/silver/silver.ttf";
        mySmallFont.fontParameters.size = 19;

        manager.load("texture/gui/loading/loading.fnt", BitmapFont.class);      // 加载界面字体
        manager.load("texture/gui/homepage/background.jpg", Texture.class);     // 主页背景
        manager.load("fonts/silver/silver.fnt", BitmapFont.class);
        manager.load("fonts/silver/silver.ttf", BitmapFont.class, mySmallFont);
        manager.load("texture/gui/pixel.png", Texture.class);                   // 通用像素染色白图
        manager.load("texture/gui/homepage/title.png", Texture.class);          // 主页标题
        manager.load("texture/gui/test/test.json", Skin.class);                 // 测试gui皮肤
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    /**
     * 渲染游戏
     */
    @Override
    public void render() {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        ScreenUtils.clear(Color.BLACK); // 清屏
        loading();
        super.render();
        loadingScreen(); // 加载界面
    }

    private void loadingScreen() {
        // 渲染加载界面
        if(manager.isLoaded("texture/gui/loading/loading.fnt") && !loadingScreen) {
            // 初始化加载界面
            if (!initializeLoadingScreen){
                startLoadingScreen = new StartLoadingScreen(this);
                initializeLoadingScreen = true;
            }
            startLoadingScreen.render(Gdx.graphics.getDeltaTime());
        }

        // 当加载页面完成过度时，才允许交互
        if (loadingScreen){
            if (getScreen() instanceof MainMenuScreen menuScreen) {
                menuScreen.interactive();
            }
        }
    }

    /** 加载 */
    public void loading() {
        // 加载完成
        if(manager.update()) {
            // 初始化
            if (loadingDelta >= 1f){
                startLoadingScreen.dispose(); // 释放加载界面
                loadingDelta = 0;             // 重置时间
                loadingScreen = true;         // 允许交互
                mainMenuScreen.listener();    // 监听器
            }
            // 初始化资源
            if(!resourcesLoaded) {
                setFont();  // 设置字体
                PIXEL_PNG = manager.get("texture/gui/pixel.png", Texture.class); // 赋值通用像素染色白图
                mainMenuScreen = new MainMenuScreen(this);  // 创建菜单
                this.setScreen(mainMenuScreen);                    // 设置菜单为当前屏幕
                resourcesLoaded = true;                            // 资源加载完成
            }
        }

        // 切换时间递增
        if (manager.isLoaded("texture/gui/loading/loading.fnt") && !loadingScreen){
            loadingDelta += Gdx.graphics.getDeltaTime();
        }
    }

    /**
     * 设置字体
     */
    public void setFont() {
        // 使用 BitmapFont
        bitmapFont = manager.get("fonts/silver/silver.fnt", BitmapFont.class);
        bitmapFont.setUseIntegerPositions(true);
        bitmapFont.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
        // 使用 FreeType
        freeTypeFont = manager.get("fonts/silver/silver.ttf", BitmapFont.class);
        freeTypeFont.setUseIntegerPositions(true);
        freeTypeFont.getData().setScale(viewport.getWorldHeight() / Gdx.graphics.getHeight());
    }

    /**
     * 释放资源
     */
    @Override
    public void dispose() {
        VisUI.dispose();
        manager.dispose();
        screen.dispose();
        batch.dispose();
    }
}
