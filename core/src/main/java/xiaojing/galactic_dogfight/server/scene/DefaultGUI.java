package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomScreenAbstract;

import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.Main.guiBottomMargin;

/**
 * 默认的GUI
 * @author xiaojing
 */
public class DefaultGUI extends CustomScreenAbstract {
    protected final Stage GUI_MAIN_STAGE;                 // GUI总舞台
    protected final Container<Actor> GUI_CONTAINER;       // GUI容器
    protected float centerPointSize = 5;                     // 显示中心点大小
    protected boolean isCenterPoint;                    // 是否显示屏幕中心点

    private CustomLabel label;
    private CustomLabel label1;
    private CustomLabel label2;
    private CustomLabel label3;

    private final DefaultScene scene;

    public DefaultGUI(DefaultScene scene) {
        this.scene = scene;
        GUI_CONTAINER = new Container<>();
        GUI_MAIN_STAGE = new Stage(guiViewport);
        GUI_CONTAINER.setSize(
            guiViewport.getWorldWidth() - guiLeftMargin - guiRightMargin,
            guiViewport.getWorldHeight() - guiTopMargin - guiBottomMargin
        );
        GUI_CONTAINER.setFillParent(true);



        label = new CustomLabel(defaultFont);
        label1 = new CustomLabel(defaultFont);
        label2 = new CustomLabel(defaultFont);
        label3 = new CustomLabel(defaultFont);

        GUI_MAIN_STAGE.addActor(label);
        GUI_MAIN_STAGE.addActor(label1);
        GUI_MAIN_STAGE.addActor(label2);
        GUI_MAIN_STAGE.addActor(label3);

        label.setFontScale(1);
        label1.setFontScale(1);
        label2.setFontScale(1);
        label3.setFontScale(1);
    }

    @Override
    public void render(float delta){
        // GUI渲染
        guiViewport.apply();
        GUI_MAIN_STAGE.draw();
        GUI_MAIN_STAGE.act(delta);
        guiSpriteBatch.begin();
        guiSpriteBatchBegin(delta);
        guiSpriteBatch.end();

    }

    /** GUI 渲染 */
    public void guiSpriteBatchBegin(float delta){
        if (isCenterPoint) {
            guiSpriteBatch.draw(
                pixelTexture, guiViewport.getWorldWidth()/2 - centerPointSize /2,
                guiViewport.getWorldHeight()/2 - centerPointSize /2,
                centerPointSize,
                centerPointSize
            );
        }
        GUI_MAIN_STAGE.draw();
        label.setText("玩家速度：" + (String.format("%.2f", scene.getPlayer().getCurrentSpeed())));
        label1.setText("相机速度：" + (String.format("%.2f", scene.getCamera().getCurrentSpeed())));
        label2.setText("瞄准模式：" + scene.getPlayer().isAim);
        label3.setText("是否居中：" + scene.getCamera().isCenter);
    }

    @Override
    protected void guiChildSize() {

    }

    @Override
    public void resize(int width, int height) {
        label.setY(guiViewport.getWorldHeight() - label.getHeight()*2);
        label1.setY(label.getY() - (label.getHeight())*2 - 4);
        label2.setY(label1.getY() - (label.getHeight())*2 - 4);
        label3.setY(label2.getY() - (label.getHeight())*2 - 4);
    }

    @Override
    public void dispose() {
        GUI_MAIN_STAGE.dispose();
    }
}
