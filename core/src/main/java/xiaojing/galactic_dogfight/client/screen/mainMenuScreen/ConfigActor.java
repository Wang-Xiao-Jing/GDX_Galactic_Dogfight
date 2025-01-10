package xiaojing.galactic_dogfight.client.screen.mainMenuScreen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomImageButton;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;

import static xiaojing.galactic_dogfight.client.gui.customControl.CustomStateButton.State.*;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/2
 */
public class ConfigActor extends Group {
    final MainMenuScreen SCREEN;         // 父屏幕
    final Container<Actor> POSITION;     // 父容器
    final AssetManager MANAGER;          // 资源管理器
    CustomLabel title;                   // 标题
    Container<Actor> titleBox;           // 标题容器
    CustomImageButton closeButton;       // 关闭按钮
    Container<Actor> closeButtonBox;     // 关闭按钮容器
    Skin test;                           // 皮肤
    Image background;                    // 背景
    Drawable ninePatch;
    Drawable ninePatchHang;                    // 背景
    float BORDER_OFFSET_X;               // 边框偏移量
    float BORDER_OFFSET_Y;               // 边框偏移量

    public ConfigActor(final MainMenuScreen SCREEN, final Container<Actor> POSITION) {
        BORDER_OFFSET_X = 30f;
        BORDER_OFFSET_Y = 10f;
        this.SCREEN = SCREEN;
        this.POSITION = POSITION;
        MANAGER = SCREEN.GAME.manager;
        setSize(this.POSITION.getWidth(), this.POSITION.getHeight());
        test = MANAGER.get("texture/gui/test/test.json", Skin.class);
        {
            NinePatch ninePatch = test.get("background", NinePatch.class);
            background = new Image(ninePatch);
        }
        {
            ninePatch = new NinePatchDrawable(test.get("no", NinePatch.class));
            ninePatchHang = new NinePatchDrawable(test.get("no-hang", NinePatch.class));
        }
        title = new CustomLabel("设置", test, 1f);
        titleBox = new Container<>(title);
        closeButton = new CustomImageButton(ninePatch);
        closeButtonBox = new Container<>(closeButton);
        addActor(background);
        addActor(titleBox);
        addActor(closeButtonBox);
        background();
        titleBox();
        closeButtonBox();
        debugAll();
    }

    private void closeButtonBox() {
        closeButtonBox.setSize(closeButton.getWidth(),closeButton.getHeight());
        closeButtonBox.setX(getWidth() - closeButton.getWidth() - BORDER_OFFSET_X/2 - 2);
        closeButtonBox.setY(getHeight() - closeButton.getHeight() - BORDER_OFFSET_Y/2 - 2);
        closeButton.setDrawable(OVER, ninePatchHang);
        closeButton.setDrawable(PRESSED, ninePatchHang);
    }

    public void interactive() {
        closeButton.replaceStyle();
    }

    void listener(final MainMenuScreen SCREEN, final Container<Actor> POSITION) {
        closeButton.addListener(new ClickListener() {
            @Override
            public void clicked (InputEvent event, float x, float y) {
                SCREEN.GUI_CONTAINER.setActor(SCREEN.mainMenuActor);
                SCREEN.switchPages();
            }
        });
    }

    /** 背景 */
    private void background() {
        background.setSize(getWidth() - BORDER_OFFSET_X, getHeight() - BORDER_OFFSET_Y);
        background.setOrigin(background.getWidth()/2, background.getHeight()/2);
        background.setPosition(BORDER_OFFSET_X / 2f, BORDER_OFFSET_Y / 2f );
    }

    /** 表格容器 */
    private void titleBox() {
        titleBox.left();
        titleBox.setTransform(true);
        titleBox.setSize(title.getWidth(), title.getHeight());
        titleBox.setY(background.getHeight() + BORDER_OFFSET_Y/2 - title.getHeight() - 2);
        titleBox.setX(BORDER_OFFSET_X/2 + 2);
    }
}
