package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.CustomScreenAbstract;

import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 默认场景
 */
public abstract class DefaultScene extends CustomScreenAbstract {
    protected final Main GAME;                            // 游戏实例
    protected final Stage GUI_MAIN_STAGE;                 // GUI总舞台
    protected final Container<Actor> GUI_CONTAINER;       // GUI容器
    protected boolean isCenterPoint;                      // 是否显示屏幕中心点
    protected TiledMap map;                               // 地图
    protected final OrthogonalTiledMapRenderer renderer;  // 正交相机渲染
    protected final OrthographicCamera camera;            // 正交相机
    private final float maxiMapX, maxiMapY;               // 地图最大坐标
    private float moveSpeed = 1;                          // 相机移动的速度
    protected final Vector2 resistance;                   // 空气阻力
    public float CenterPointSize = 5;                     // 显示中心点大小

    public DefaultScene(Main game) {
        GAME = game;
        map = gameAssetManager.get("demo/map/map.tmx");
        maxiMapX = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        maxiMapY = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
        renderer = new OrthogonalTiledMapRenderer(map, 1);
        camera = (OrthographicCamera) gameViewport.getCamera();
        gameSpriteBatch.setProjectionMatrix(camera.combined);
        GUI_MAIN_STAGE = new Stage(guiViewport);
        GUI_CONTAINER = new Container<>();
        GUI_CONTAINER.setSize(
            guiViewport.getWorldWidth() - guiLeftMargin - guiRightMargin,
            guiViewport.getWorldHeight() - guiTopMargin - guiBottomMargin
        );
        GUI_CONTAINER.setFillParent(true);
        isCenterPoint = true;
        resistance = Zero;
    }

    /** 获取相机位置X轴 */
    public float getCameraPositionX(){
        return gameViewport.getCamera().position.x;
    }

    /** 设置相机位置X轴 */
    public void setCameraPositionX(float x){
        gameViewport.getCamera().position.x = x;
    }

    /** 获取相机位置Y轴 */
    public float getCameraPositionY(){
        return gameViewport.getCamera().position.y;
    }

    /** 设置相机位置Y轴 */
    public void setCameraPositionY(float y){
        gameViewport.getCamera().position.y = y;
    }

    /** 获取相机位置 */
    public Vector3 getCameraPosition(){
        return gameViewport.getCamera().position;
    }

    /** 设置相机位置 */
    public void setCameraPosition(float x, float y){
        gameViewport.getCamera().position.x = x;
        gameViewport.getCamera().position.y = y;
    }

    /** 设置相机位置 */
    public void setCameraPosition(Vector3 position){
        gameViewport.getCamera().position.x = position.x;
        gameViewport.getCamera().position.z = position.z;
        gameViewport.getCamera().position.y = position.y;
    }

    /** 设置相机位置 */
    public void setCameraPosition(Vector2 position){
        gameViewport.getCamera().position.x = position.x;
        gameViewport.getCamera().position.y = position.y;
    }

    /** 相机缩放 */
    public void scaleCamera(){
        boolean add = Gdx.input.isKeyPressed(Input.Keys.EQUALS);
        boolean minus = Gdx.input.isKeyPressed(Input.Keys.MINUS);
        boolean reset = Gdx.input.isKeyPressed(Input.Keys.DEL);
        float ratio = 0.01f;

        if (add){
            cameraZoomRatio += ratio;
        }

        if (minus){
            cameraZoomRatio -= ratio;
        }

        if (cameraZoomRatio >= 2){
            cameraZoomRatio = 2;
        }
        if (cameraZoomRatio <= 0.1){
            cameraZoomRatio = 0.1f;
        }

        gameViewport.setWorldSize(
            gameViewportWidth * globalScaleFactor * cameraZoomRatio,
            gameViewportHeight * globalScaleFactor * cameraZoomRatio
        );

        if (reset){
            cameraZoomRatio = 1;
            gameViewport.setWorldSize(
                gameViewportWidth * globalScaleFactor,
                gameViewportHeight * globalScaleFactor
            );
        }

        camera.zoom = cameraZoomRatio;
    }

    /** 玩家上移 */
    public void playerMoveUp(float distance){
        gameViewport.getCamera().position.y += distance;
    }

    /** 玩家下移 */
    public void playerMoveDown(float distance){
        gameViewport.getCamera().position.y -= distance;
    }

    /** 玩家左移 */
    public void playerMoveLeft(float distance){
        gameViewport.getCamera().position.x -= distance;
    }

    /** 玩家右移 */
    public void playerMoveRight(float distance){
        gameViewport.getCamera().position.x += distance;
    }

    /** 移动 */
    private void playerMove() {
        boolean RIGHT = Gdx.input.isKeyPressed(Input.Keys.D);
        boolean LEFT = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean UP = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean DOWN = Gdx.input.isKeyPressed(Input.Keys.S);
//        float diagonalMoveSpeed = moveSpeed / (float)Math.sqrt(2); // 对角线移动速度
//
//        if (RIGHT && UP||LEFT && UP||DOWN && LEFT||DOWN && RIGHT){
//            if (RIGHT && UP) {
//                bucketSprite.translateX(diagonalMoveSpeed);
//                bucketSprite.translateY(diagonalMoveSpeed);
//            }
//            if (LEFT && UP) {
//                bucketSprite.translateX(-diagonalMoveSpeed);
//                bucketSprite.translateY(diagonalMoveSpeed);
//            }
//            if (DOWN && LEFT) {
//                bucketSprite.translateX(-diagonalMoveSpeed);
//                bucketSprite.translateY(-diagonalMoveSpeed);
//            }
//            if (DOWN && RIGHT) {
//                bucketSprite.translateX(diagonalMoveSpeed);
//                bucketSprite.translateY(-diagonalMoveSpeed);
//            }
//        }
        if (RIGHT) {
            playerMoveRight(moveSpeed);
        }
        if (LEFT) {
            playerMoveLeft(moveSpeed);
        }
        if (UP) {
            playerMoveUp(moveSpeed);
        }
        if (DOWN) {
            playerMoveDown(moveSpeed);
        }

        if (getCameraPositionX()>=maxiMapX){
            setCameraPositionX(maxiMapX);
        }
        if (getCameraPositionY()>=maxiMapY){
            setCameraPositionY(maxiMapY);
        }
        if (getCameraPositionX()<=0){
            setCameraPositionX(0);
        }
        if (getCameraPositionY()<=0){
            setCameraPositionY(0);
        }
    }

    @Override
    public void show() {

    }

    private float time; // 时间

    @Override
    public void render(float delta) {
        scaleCamera();
        time += delta;
        playerMove();
//        gameViewport.getCamera().update();
        camera.update();
//        gameViewport.apply();
        guiViewport.apply();
        renderer.setView(camera);
        renderer.render();
        gameSpriteBatch.setProjectionMatrix(camera.combined); // 设置投影矩阵
        gameSpriteBatch.begin();
        gameSpriteBatchBegin();
        gameSpriteBatch.end();
        guiSpriteBatch.begin();
        guiSpriteBatchBegin();
        guiSpriteBatch.end();
    }

    /** 游戏渲染 */
    public void gameSpriteBatchBegin(){

    }

    /** GUI 渲染 */
    public void guiSpriteBatchBegin(){
        if (isCenterPoint) {
            guiSpriteBatch.draw(
                pixelTexture, guiViewport.getWorldWidth()/2 - CenterPointSize/2,
                guiViewport.getWorldHeight()/2 - CenterPointSize/2,
                CenterPointSize,
                CenterPointSize
            );
        }
    }

    @Override
    public void resize(int width, int height) {

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
        renderer.dispose();
        GUI_MAIN_STAGE.dispose();
    }
}
