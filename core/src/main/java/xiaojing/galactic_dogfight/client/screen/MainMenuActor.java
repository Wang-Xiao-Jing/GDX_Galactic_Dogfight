package xiaojing.galactic_dogfight.client.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomTableButton;

import static xiaojing.galactic_dogfight.Main.assetManager;

/**
 * @author 尽
 * @apiNote 主菜单界面
 */
public class MainMenuActor extends CustomizeGroup {
    private Table menu;                 // 菜单
    private Image titleImage;           // 标题图片
    CustomTableButton startButton;      // 开始游戏按钮
    CustomTableButton configButton;     // 设置按钮
    CustomTableButton aboutButton;      // 关于按钮
    CustomTableButton exitButton;       // 退出按钮
    private String startTxt, configTxt, aboutTxt, exitTxt;

    // 初始化
    public MainMenuActor(){
        initialTexts();
        float optionRatio = 1;          // 菜单按钮缩放比
        button(optionRatio);            // 菜单按钮
        menu();                         // 菜单
        title();                        // 标题
        addActor(menu);                 // 添加菜单
        addActor(titleImage);           // 添加标题
    }

    @Override
    public void adjustSize(float width, float height){
        setSize(width, height);
        titleImage.setY(getHeight() - titleImage.getHeight() * titleImage.getScaleY());
    }

    /** 标题 */
    private void title() {
        float titleImageScale = 3;
        titleImage = new Image(assetManager.get("texture/gui/homepage/title.png", Texture.class));
        titleImage.setScale(titleImageScale);
    }

    /** 交互 */
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
    private void initialTexts(){
        startTxt = "开始游戏";
        configTxt = "设置";
        aboutTxt = "关于";
        exitTxt = "退出游戏";
    }

    /** 监听器&触发器 */
    @Override
    public void listener(MainMenuScreen screen) {
        startButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (!screen.loading){
                    screen.loading = true;
                    screen.game.loading = true;
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
        configButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                if (!screen.loading) {
                    screen.GUI_CONTAINER.setActor(screen.configActor);
                    screen.switchPages();
                }
            }
        });
    }
}
