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
import com.badlogic.gdx.utils.Array;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.CustomScreenAbstract;
import xiaojing.galactic_dogfight.server.unit.EntityUnit;
import xiaojing.galactic_dogfight.server.unit.EntityUnitBuilder;
import xiaojing.galactic_dogfight.server.unit.PlayerUnit;

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
    protected final OrthographicCamera CAMERA;            // 正交相机
    private final float MAXI_MAP_X, MAXI_MAP_Y;           // 地图最大坐标
    private float moveSpeed = 1;                          // 相机移动的速度
    protected float resistance;                           // 空气阻力
    public float centerPointSize = 5;                     // 显示中心点大小
    protected Array<EntityUnit> entityUnits;              // 单位

//    public float cameraZoomRatio = 1;

    public DefaultScene(Main game) {
        GAME = game;
        map = gameAssetManager.get("demo/map/map.tmx");
        MAXI_MAP_X = map.getProperties().get("width", Integer.class) * map.getProperties().get("tilewidth", Integer.class);
        MAXI_MAP_Y = map.getProperties().get("height", Integer.class) * map.getProperties().get("tileheight", Integer.class);
        RENDERER = new OrthogonalTiledMapRenderer(map, 1);
        CAMERA = (OrthographicCamera) gameViewport.getCamera();
        gameSpriteBatch.setProjectionMatrix(CAMERA.combined);
        GUI_MAIN_STAGE = new Stage(guiViewport);
        STAGE = new Stage(gameViewport, gameSpriteBatch);
        GUI_CONTAINER = new Container<>();
        GUI_CONTAINER.setSize(
            guiViewport.getWorldWidth() - guiLeftMargin - guiRightMargin,
            guiViewport.getWorldHeight() - guiTopMargin - guiBottomMargin
        );
        GUI_CONTAINER.setFillParent(true);
        isCenterPoint = false;
        entityUnits = new Array<>();
//        entityUnits.add(new PlayerUnit());
//        entityUnits.add(new EntityUnit(new EntityUnit.EntityUnitBuilder().unitIdName("a").width(16).height(16).build()));
//        for (int i = 0; i < entityUnits.size; i++){
//            entityUnits.get(i).debug();
//        }
        STAGE.addActor(new PlayerUnit());
        STAGE.addActor(new EntityUnit(new EntityUnitBuilder().unitIdName("a").width(16).height(16).position(50,50).build()));
    }

    // region 相机操作方法
    /** 获取相机位置X轴 */
    public float getCameraPositionX(){
        return gameViewport.getCamera().position.x;
    }

    /** 设置相机位置X轴 */
    public void setCameraPositionX(float x){
        gameViewport.getCamera().position.x = x;
    }

    /** 移动相机位置X轴 */
    public void translateCameraPositionX(float x){
        gameViewport.getCamera().position.x += x;
    }

    /** 获取相机位置Y轴 */
    public float getCameraPositionY(){
        return gameViewport.getCamera().position.y;
    }

    /** 设置相机位置Y轴 */
    public void setCameraPositionY(float y){
        gameViewport.getCamera().position.y = y;
    }

    /** 移动相机位置Y轴 */
    public void translateCameraPositionY(float y){
        gameViewport.getCamera().position.y += y;
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

    /** 移动相机位置 */
    public void translateCameraPositionY(float x, float y){
        gameViewport.getCamera().position.y += y;
        gameViewport.getCamera().position.x += x;
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
        boolean amplify = Gdx.input.isKeyPressed(Input.Keys.EQUALS);
        boolean reduce = Gdx.input.isKeyPressed(Input.Keys.MINUS);
        boolean reset = Gdx.input.isKeyPressed(Input.Keys.DEL);
        float ratio = 0.01f;

        if (reduce){
            amplifyCamera(ratio);
        }
        if (amplify){
            reduceCamera(ratio);
        }
        if (reset){
            resetCamera();
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

        CAMERA.zoom = cameraZoomRatio;
    }

    /** 重置相机 */
    public void resetCamera() {
        cameraZoomRatio = 1;
    }

    /** 缩小相机 */
    public void reduceCamera(float ratio) {
        cameraZoomRatio -= ratio;
    }

    /** 放大相机 */
    public void amplifyCamera(float ratio) {
        cameraZoomRatio += ratio;
    }
    // endregion

    // region 玩家移动
    /** 玩家上移 */
    public void playerMoveUp(float distance){
        translateCameraPositionY(distance);
        if(getPlayerUnit()!=null){
            getPlayerUnit().translateUnitY(distance);
        }
    }

    /** 玩家下移 */
    public void playerMoveDown(float distance){
        translateCameraPositionY(-distance);
        if(getPlayerUnit()!=null) {
            getPlayerUnit().translateUnitY(-distance);
        }
    }

    /** 玩家左移 */
    public void playerMoveLeft(float distance){
        translateCameraPositionX(-distance);
        if(getPlayerUnit()!=null) {
            getPlayerUnit().translateUnitX(-distance);
        }
    }

    /** 玩家右移 */
    public void playerMoveRight(float distance){
        translateCameraPositionX(distance);
        if(getPlayerUnit()!=null) {
            getPlayerUnit().translateUnitX(distance);
        }
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

        if (getCameraPositionX()>= MAXI_MAP_X){
            setCameraPositionX(MAXI_MAP_X);
        }
        if (getCameraPositionY()>= MAXI_MAP_Y){
            setCameraPositionY(MAXI_MAP_Y);
        }
        if (getCameraPositionX()<=0){
            setCameraPositionX(0);
        }
        if (getCameraPositionY()<=0){
            setCameraPositionY(0);
        }
    }
    // endregion

    /** 获取玩家 */
    public PlayerUnit getPlayerUnit() {
        for(int i = 0; i < entityUnits.size; i++){
            if(entityUnits.get(i).getUnitName().equals("galactic_dogfight:player")){
                return (PlayerUnit)entityUnits.get(i);
            }
        }
        // 等待处理
        return null;
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
        CAMERA.update();
//        gameViewport.apply();
        guiViewport.apply();
//        gameViewport.apply();
        RENDERER.setView(CAMERA);
        RENDERER.render();
        gameSpriteBatch.setProjectionMatrix(CAMERA.combined); // 设置投影矩阵
        STAGE.draw();
        STAGE.act(delta);
        gameSpriteBatch.begin();
        gameSpriteBatchBegin();
        gameSpriteBatch.end();
        guiSpriteBatch.begin();
        guiSpriteBatchBegin();
        guiSpriteBatch.end();
    }

    /** 游戏渲染 */
    public void gameSpriteBatchBegin(){
//        unitRender();
    }

//    /** 单位渲染 */
//    public void unitRender(){
////        for(int i = 0; i < entityUnits.size; i++){
////            entityUnits.get(i).draw(gameSpriteBatch);
////        }
//    }

    /** GUI 渲染 */
    public void guiSpriteBatchBegin(){
        if (isCenterPoint) {
            guiSpriteBatch.draw(
                pixelTexture, guiViewport.getWorldWidth()/2 - centerPointSize /2,
                guiViewport.getWorldHeight()/2 - centerPointSize /2,
                centerPointSize,
                centerPointSize
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
        RENDERER.dispose();
        GUI_MAIN_STAGE.dispose();
        STAGE.dispose();
    }
}
