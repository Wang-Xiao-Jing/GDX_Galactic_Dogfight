package xiaojing.galactic_dogfight.gui.mainMenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.FPSLogger;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import xiaojing.galactic_dogfight.gui.QuickMethod;
import xiaojing.galactic_dogfight.gui.widget.XjkzTableButton;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/2
 */
public class MainMenuActor extends Group {
    private MainMenuScreen SCREEN;
    private Container<Actor> POSITION;
    private Table menu;
    private Image titleImage;
    XjkzTableButton startButton;
    XjkzTableButton configButton;
    XjkzTableButton aboutButton;
    XjkzTableButton exitButton;
    private String startTxt, configTxt, aboutTxt, exitTxt;
    QuickMethod quickMethod;

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
//        extracted();         // 测试
    }

    private void title() {
        float titleImageScale = 3;
        titleImage = new Image(new Texture("ui/title.png"));
        titleImage.setScale(titleImageScale);
        titleImage.setY(getHeight()-titleImage.getHeight()*titleImage.getScaleY());

    }

    private void extracted() {
        System.out.println(getWidth());
        System.out.println(getHeight());
        System.out.println(menu.getWidth());
        System.out.println(menu.getHeight());
        System.out.println(menu.getX());
        System.out.println(menu.getY());
    }
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

    private void button(float optionRatio) {
        startButton = new XjkzTableButton(startTxt, optionRatio);
        configButton = new XjkzTableButton(configTxt, optionRatio);
        aboutButton = new XjkzTableButton(aboutTxt, optionRatio);
        exitButton = new XjkzTableButton(exitTxt, optionRatio);
        startButton.left();
        configButton.left();
        aboutButton.left();
        exitButton.left();
        startButton.addLabelListener();
        configButton.addLabelListener();
        aboutButton.addLabelListener();
        exitButton.addLabelListener();
    }

    // 文字
    private void initialTexts(){
        startTxt = "开始游戏";
        configTxt = "设置";
        aboutTxt = "关于";
        exitTxt = "退出游戏";
    }

    // 监听器
    public void listener(final MainMenuScreen SCREEN, final Container<Actor> POSITION) {
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
            }
        });
    }
}
