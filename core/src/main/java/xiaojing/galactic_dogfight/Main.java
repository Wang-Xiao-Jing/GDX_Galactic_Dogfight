package xiaojing.galactic_dogfight;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.kotcrab.vis.ui.VisUI;
import xiaojing.galactic_dogfight.client.screen.StartLoadingScreen;
import xiaojing.galactic_dogfight.client.screen.mainMenuScreen.MainMenuActor;
import xiaojing.galactic_dogfight.client.screen.mainMenuScreen.MainMenuScreen;
import xiaojing.galactic_dogfight.interfaces.xjkzInputProcessor;

/**
 * 主类
 */
public class Main extends Game {
    public SpriteBatch batch;                   // 渲染器
    public FitViewport viewport;                // 窗口适配器
    public static BitmapFont bitmapFont;        // 默认字体
    public static BitmapFont freeTypeFont;      // 自定义字体
    xjkzInputProcessor inputProcessor;          // 输入处理器
    public float scale = 4f;                    // 缩放比例
    public AssetManager manager;                // 资源管理器
    private boolean resourcesLoaded = false;    // 资源是否已加载完成
    MainMenuScreen mainMenuScreen;
    StartLoadingScreen startLoadingScreen;      // 加载界面
    public Texture PIXEL_PNG;
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
        manager = new AssetManager();
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));
        FreetypeFontLoader.FreeTypeFontLoaderParameter mySmallFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        mySmallFont.fontFileName = "fonts/silver/silver.ttf";
        mySmallFont.fontParameters.size = 19;
        manager.load("texture/gui/loading/loading.fnt", BitmapFont.class);
        manager.load("texture/gui/background.jpg", Texture.class);
        manager.load("fonts/silver/silver.fnt", BitmapFont.class);
        manager.load("fonts/silver/silver.ttf", BitmapFont.class, mySmallFont);
        manager.load("texture/gui/pixel.png", Texture.class);
        manager.load("ui/title.png", Texture.class);
        manager.load("texture/gui/test/test.json", Skin.class);
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

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, false);
    }

    boolean pixelLoaded = false;
    boolean loadingScreen = false;
    /**
     * 渲染游戏
     */
    @Override
    public void render() {
        Gdx.gl.glActiveTexture(GL20.GL_TEXTURE0);
        ScreenUtils.clear(Color.BLACK); // 清屏
        loading();
        super.render();
        // 初始化加载界面
        if(manager.isLoaded("texture/gui/loading/loading.fnt") && !loadingScreen) {
            if (!pixelLoaded){
                startLoadingScreen = new StartLoadingScreen(this);
                pixelLoaded = true;
            }
            startLoadingScreen.render(Gdx.graphics.getDeltaTime());
        }

        if (loadingScreen){
            if (getScreen() instanceof MainMenuScreen menuScreen) {
                menuScreen.interactive();
            }
        }
    }
    float delta;
    // 加载
    public void loading() {
        if(manager.update()) {
            // 初始化
            if (delta >= 1f){
                startLoadingScreen.dispose(); // 释放加载界面
                delta = 0;
                loadingScreen = true;
                mainMenuScreen.listener(); // 监听器
            }
            if(!resourcesLoaded) {
                // 初始化资源
                setFont();
                PIXEL_PNG = manager.get("texture/gui/pixel.png", Texture.class);
                mainMenuScreen = new MainMenuScreen(this);

                this.setScreen(mainMenuScreen);
                resourcesLoaded = true;
            }
        }
        if (manager.isLoaded("texture/gui/loading/loading.fnt") && !loadingScreen){
            delta += Gdx.graphics.getDeltaTime();
        }
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
