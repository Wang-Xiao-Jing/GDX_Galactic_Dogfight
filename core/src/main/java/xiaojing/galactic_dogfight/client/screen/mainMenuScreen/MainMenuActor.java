package xiaojing.galactic_dogfight.client.screen.mainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xiaojing.galactic_dogfight.client.screen.QuickMethod;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomTableButton;

/**
 * @author 尽
 * @apiNote 主菜单界面
 */
public class MainMenuActor extends Group {
    private MainMenuScreen SCREEN;
    private Container<Actor> POSITION;
    private Table menu;
    private Image titleImage;
    CustomTableButton startButton;
    CustomTableButton configButton;
    CustomTableButton aboutButton;
    CustomTableButton exitButton;
    private String startTxt, configTxt, aboutTxt, exitTxt;
    QuickMethod quickMethod;

    // 初始化
    public MainMenuActor(final MainMenuScreen SCREEN, final Container<Actor> POSITION){
        initialTexts();
        float optionRatio = 1;
        this.SCREEN = SCREEN;
        this.POSITION = POSITION;
        quickMethod = new QuickMethod(SCREEN.viewport);
        setSize(this.POSITION.getWidth(), this.POSITION.getHeight());
        button(optionRatio); // 菜单按钮
        menu();              // 菜单
        title();             // 标题
        addActor(menu);
        addActor(titleImage);
    }

    // 标题
    private void title() {
        float titleImageScale = 3;
        titleImage = new Image(SCREEN.GAME.manager.get("texture/gui/homepage/title.png", Texture.class));
        titleImage.setScale(titleImageScale);
        titleImage.setY(getHeight()-titleImage.getHeight()*titleImage.getScaleY());
    }

    /** 交互 */
    public void interactive() {
        startButton.replaceStyle();
        configButton.replaceStyle();
        aboutButton.replaceStyle();
        exitButton.replaceStyle();
    }

    // 测试
    private void extracted() {
        System.out.println("----------------------");
        System.out.println(startButton.isDisabled());
        System.out.println("按下：" + startButton.isPressed());
        System.out.println("触摸：" + startButton.isOver());
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

    // 监听器
    void listener(final MainMenuScreen SCREEN, final Container<Actor> POSITION) {
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                Gdx.app.exit(); // 退出应用程序
            }
        });
        configButton.addListener(new ClickListener(){
            @Override
            public void clicked (InputEvent event, float x, float y) {
                SCREEN.GUI_CONTAINER.setActor(SCREEN.configActor);
                SCREEN.switchPages();
            }
        });
    }
}
