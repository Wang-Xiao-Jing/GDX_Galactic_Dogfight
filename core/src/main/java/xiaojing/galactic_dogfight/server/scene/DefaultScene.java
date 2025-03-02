package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.Stage;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.CustomScreenAbstract;
import xiaojing.galactic_dogfight.server.DefaultCamera;
import xiaojing.galactic_dogfight.core.entity.Entity;
import xiaojing.galactic_dogfight.server.entity.EntityBuilder;
import xiaojing.galactic_dogfight.server.entity.player.PlayerVehicle;

import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 默认场景
 */
public abstract class DefaultScene extends CustomScreenAbstract {
    protected final Main GAME;                            // 游戏实例
    protected final Stage STAGE;                          // 总舞台
    protected final OrthogonalTiledMapRenderer RENDERER;  // 正交相机渲染
    protected final DefaultCamera CAMERA;                 // 正交相机
    private final float MAXI_MAP_X, MAXI_MAP_Y;           // 地图最大坐标
    private final World world;                            // 世界
    private final Box2DDebugRenderer debugRenderer;       // Box2D调试渲染器
    private final PlayerVehicle playerVehicle;            // 玩家
    protected TiledMap map;                               // 地图
    protected DefaultGUI GUI;                           // GUI
    protected float resistance;                           // 空气阻力

//    public float cameraZoomRatio = 1;
    private float time; // 时间

    public DefaultScene(Main game) {
        GAME = game;
        map = gameAssetManager.get("demo/map/map.tmx");
        MAXI_MAP_X = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        MAXI_MAP_Y = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
        RENDERER = new OrthogonalTiledMapRenderer(map, 1);
        CAMERA = new DefaultCamera(gameViewport.getCamera());
        gameSpriteBatch.setProjectionMatrix(CAMERA.camera.combined);
        STAGE = new Stage(gameViewport, gameSpriteBatch);
        playerVehicle = new PlayerVehicle();
        world = new World(Zero, true);
//        Body world = new Body();
        debugRenderer = new Box2DDebugRenderer();

        CAMERA.camera.position.set(playerVehicle.getOriginX(), playerVehicle.getOriginY(), 0);
        STAGE.addActor(playerVehicle);
        STAGE.addActor(new Entity(new EntityBuilder("a").width(16).height(16).position(50, 50).build()));

        // 添加实体到世界
        for (int i = 0; i < STAGE.getActors().size; i++) {
            if (STAGE.getActors().get(i) instanceof Entity entity) {
                entity.addToWorldDefault(world);
//                world.createBody(entity.getBodyDef());
            }
        }

        GUI = new DefaultGUI(this);
    }

    /**
     * 获取玩家
     */
    public PlayerVehicle getPlayerVehicle() {
        return playerVehicle;
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        time += delta;

        // 渲染相机/地图
        CAMERA.scale();
        CAMERA.update(delta, playerVehicle);
        RENDERER.setView(CAMERA.camera);
        RENDERER.render();
        gameSpriteBatch.setProjectionMatrix(CAMERA.camera.combined); // 设置投影矩阵

        // 除GUI以外的渲染
        STAGE.draw();
        STAGE.act(delta);
        // 模拟世界
        world.step(1.0f / 60.0f, 6, 2);
        getPlayerVehicle().playerMove(delta, CAMERA);
        for (int i = 0; i < STAGE.getActors().size; i++) {
            if (STAGE.getActors().get(i) instanceof Entity entity) {
                entity.synchro();
            }
        }
        gameSpriteBatch.begin();
        gameSpriteBatchBegin(delta);
        debugRenderer.render(world, CAMERA.camera.combined);
        gameSpriteBatch.end();
        GUI.render(delta);
    }

    public DefaultCamera getCamera() {
        return CAMERA;
    }

    /**
     * 限制
     */
    private void limitation() {
        if (CAMERA.getX() >= MAXI_MAP_X) {
            CAMERA.setX(MAXI_MAP_X);
        }
        if (CAMERA.getY() >= MAXI_MAP_Y) {
            CAMERA.setY(MAXI_MAP_Y);
        }
        if (CAMERA.getX() <= 0) {
            CAMERA.setX(0);
        }
        if (CAMERA.getY() <= 0) {
            CAMERA.setY(0);
        }
    }

    /**
     * 游戏渲染
     */
    public void gameSpriteBatchBegin(float delta) {}

    @Override
    public void resize(int width, int height) {
        GUI.resize(width, height);
    }

    @Override
    public void dispose() {
        GUI.dispose();
        world.dispose();
        debugRenderer.dispose();
        RENDERER.dispose();
        STAGE.dispose();
    }
}
