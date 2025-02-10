package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.gui.customControl.CustomLabel;
import xiaojing.galactic_dogfight.client.screen.CustomScreenAbstract;
import xiaojing.galactic_dogfight.server.DefaultCamera;
import xiaojing.galactic_dogfight.server.unit.Entity;
import xiaojing.galactic_dogfight.server.unit.EntityBuilder;
import xiaojing.galactic_dogfight.server.player.Player;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 默认场景
 */
public abstract class DefaultScene extends CustomScreenAbstract {
    protected final Main GAME;                            // 游戏实例
    protected final Stage GUI_MAIN_STAGE;                 // GUI总舞台
    protected final Stage STAGE;                          // 总舞台
    protected final Container<Actor> GUI_CONTAINER;       // GUI容器
    protected boolean isCenterPoint;                      // 是否显示屏幕中心点
    protected TiledMap map;                               // 地图
    protected final OrthogonalTiledMapRenderer RENDERER;  // 正交相机渲染
    protected final DefaultCamera CAMERA;                 // 正交相机
    private final float MAXI_MAP_X, MAXI_MAP_Y;           // 地图最大坐标
    protected float resistance;                           // 空气阻力
    public float centerPointSize = 5;                     // 显示中心点大小
    World world;

    Player player;                                        // 玩家

    CustomLabel label;
    CustomLabel label1;
    CustomLabel label2;
    CustomLabel label3;

//    public float cameraZoomRatio = 1;

    public DefaultScene(Main game) {
        GAME = game;
        map = gameAssetManager.get("demo/map/map.tmx");
        MAXI_MAP_X = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        MAXI_MAP_Y = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
        RENDERER = new OrthogonalTiledMapRenderer(map, 1);
        CAMERA = new DefaultCamera(gameViewport.getCamera());
        gameSpriteBatch.setProjectionMatrix(CAMERA.camera.combined);
        GUI_MAIN_STAGE = new Stage(guiViewport);
        STAGE = new Stage(gameViewport, gameSpriteBatch);
        GUI_CONTAINER = new Container<>();
        player = new Player();
        world = new World(Zero, true);

        GUI_CONTAINER.setSize(
            guiViewport.getWorldWidth() - guiLeftMargin - guiRightMargin,
            guiViewport.getWorldHeight() - guiTopMargin - guiBottomMargin
        );
        GUI_CONTAINER.setFillParent(true);
        label = new CustomLabel(defaultFont);
        label1 = new CustomLabel(defaultFont);
        label2 = new CustomLabel(defaultFont);
        label3 = new CustomLabel(defaultFont);
        label.setFontScale(1);
        label1.setFontScale(1);
        label2.setFontScale(1);
        label3.setFontScale(1);
        GUI_MAIN_STAGE.addActor(label);
        GUI_MAIN_STAGE.addActor(label1);
        GUI_MAIN_STAGE.addActor(label2);
        GUI_MAIN_STAGE.addActor(label3);
        isCenterPoint = false;
        CAMERA.camera.position.set(player.getOriginX(),player.getOriginY(),0);
        STAGE.addActor(player);
        STAGE.addActor(new Entity(new EntityBuilder().entityIdName("a").width(16).height(16).position(50,50).build()));

    }

    /** 获取玩家 */
    public Player getPlayer() {
        return player;
    }

    @Override
    public void show() {

    }

    private float time; // 时间

    @Override
    public void render(float delta) {
        time += delta;
        CAMERA.scale();
        getPlayer().playerMove(delta, CAMERA);
        CAMERA.update(delta, player);
        guiViewport.apply();
        RENDERER.setView(CAMERA.camera);
        RENDERER.render();
//        limitation();
        gameSpriteBatch.setProjectionMatrix(CAMERA.camera.combined); // 设置投影矩阵
        STAGE.draw();
        STAGE.act(delta);
        gameSpriteBatch.begin();
        gameSpriteBatchBegin(delta);
        gameSpriteBatch.end();

        guiSpriteBatch.begin();
        guiSpriteBatchBegin(delta);
        guiSpriteBatch.end();

    }

    /** 限制 */
    private void limitation() {
        if (CAMERA.getX()>= MAXI_MAP_X){
            CAMERA.setX(MAXI_MAP_X);
        }
        if (CAMERA.getY()>= MAXI_MAP_Y){
            CAMERA.setY(MAXI_MAP_Y);
        }
        if (CAMERA.getX()<=0){
            CAMERA.setX(0);
        }
        if (CAMERA.getY()<=0){
            CAMERA.setY(0);
        }
    }

    /** 游戏渲染 */
    public void gameSpriteBatchBegin(float delta){
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
        label.setText("玩家速度：" + (String.format("%.2f", player.getCurrentSpeed())));
        label1.setText("相机速度：" + (String.format("%.2f", CAMERA.getCurrentSpeed())));
        label2.setText("瞄准模式：" + player.isAim);
        label3.setText("是否居中：" + CAMERA.isCenter);
    }

    @Override
    public void resize(int width, int height) {
        label.setY(guiViewport.getWorldHeight() - label.getHeight()*2);
        label1.setY(label.getY() - (label.getHeight())*2 - 4);
        label2.setY(label1.getY() - (label.getHeight())*2 - 4);
        label3.setY(label2.getY() - (label.getHeight())*2 - 4);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        RENDERER.dispose();
        GUI_MAIN_STAGE.dispose();
        STAGE.dispose();
    }
}
