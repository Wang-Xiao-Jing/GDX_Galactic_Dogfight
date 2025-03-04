package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomGroup;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomTableButton;

import static xiaojing.galactic_dogfight.StaticClass.assetManager;

/**
 * @apiNote 主菜单界面
 */
public class MainMenuActor extends CustomGroup {
    private CustomTableButton startButton;      // 开始游戏按钮
    private CustomTableButton configButton;     // 设置按钮
    private CustomTableButton aboutButton;      // 关于按钮
    private CustomTableButton exitButton;       // 退出按钮
    private Button button;
    private Table menu;                 // 菜单
    private Image titleImage;           // 标题图片
    private String startTxt, configTxt, aboutTxt, exitTxt;

    // 初始化
    public MainMenuActor() {
        initialTexts();
        float optionRatio = 1;          // 菜单按钮缩放比
        button(optionRatio);            // 菜单按钮
        menu();                         // 菜单
        title();                        // 标题
        addActor(menu);                 // 添加菜单
        addActor(titleImage);           // 添加标题
        addActor(button);
    }

    @Override
    public void adjustSize(float width, float height) {
        setSize(width, height);
        titleImage.setY(getHeight() - titleImage.getHeight() * titleImage.getScaleY());
    }

    /**
     * 标题
     */
    private void title() {
        float titleImageScale = 3;
        titleImage = new Image(assetManager.get("texture/gui/homepage/title.png", Texture.class));
        titleImage.setScale(titleImageScale);
    }

    /**
     * 交互
     */
    public void interactive() {
        startButton.replaceStyle();
        configButton.replaceStyle();
        aboutButton.replaceStyle();
        exitButton.replaceStyle();
    }

    // 按钮
    private void button(float optionRatio) {
        startButton = new CustomTableButton(startTxt, optionRatio);
        configButton = new CustomTableButton(configTxt, optionRatio);
        aboutButton = new CustomTableButton(aboutTxt, optionRatio);
        exitButton = new CustomTableButton(exitTxt, optionRatio);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Skin skin = new Skin(Gdx.files.internal("texture/cs.json"));
        buttonStyle = skin.get("default", Button.ButtonStyle.class);
//        buttonStyle.up = skin
//        buttonStyle.down = skin
//        buttonStyle.over = skin
//        buttonStyle.disabled = skin
        button = new Button(buttonStyle);
        startButton.left();
        configButton.left();
        aboutButton.left();
        exitButton.left();
    }

    // 菜单
    private void menu() {
        float menuCellHeight = 20;
        float menuCellWidth = 150;
        menu = new Table();
        menu.setTransform(true);
        menu.defaults().height(menuCellHeight).width(menuCellWidth).fill();
        menu.add(startButton).uniform().row();
        menu.add(configButton).uniform().row();
        menu.add(aboutButton).uniform().row();
        menu.add(exitButton).uniform().row();
        menu.setWidth(150);
        menu.setHeight(menu.getCells().size * menuCellHeight);
    }

    // 文字
    private void initialTexts() {
        startTxt = "开始游戏";
        configTxt = "设置";
        aboutTxt = "关于";
        exitTxt = "退出游戏";
    }

    /**
     * 监听器&触发器
     */
    @Override
    public void listener(MainMenuScreen screen) {
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!screen.loading) {
                    screen.loading = true;
                    screen.game.setIsLoading(true);
                    screen.game.setLoadingScreen(new EnterLoadingScreen(screen.game));
                }
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!screen.loading) Gdx.app.exit(); // 退出应用程序
            }
        });
        configButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!screen.loading) {
                    screen.GUI_CONTAINER.setActor(screen.configActor);
                    screen.switchPages();
                }
            }
        });
    }
}
