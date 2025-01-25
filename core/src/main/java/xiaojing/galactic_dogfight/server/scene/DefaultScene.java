package xiaojing.galactic_dogfight.server.scene;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import xiaojing.galactic_dogfight.Main;
import xiaojing.galactic_dogfight.client.screen.CustomizeScreenAbstract;

import static xiaojing.galactic_dogfight.Main.*;

/**
 * @author 尽
 * @apiNote 默认场景
 */
public abstract class DefaultScene extends CustomizeScreenAbstract {
    protected final Main GAME;                      // 游戏实例
    protected final Stage GUI_MAIN_STAGE;           // GUI总舞台
    protected final Container<Actor> GUI_CONTAINER; // GUI容器
    protected boolean deBugGrid;                    // 是否显示网格
    protected TiledMap map;                         // 地图
    protected OrthogonalTiledMapRenderer renderer;  // 正交相机渲染
    private final float maxiMapX, maxiMapY;
    private final float moveSpeed = 1;              // 相机移动的速度

    public DefaultScene(Main game) {
        GAME = game;
        gameSpriteBatch.setProjectionMatrix(gameViewport.getCamera().combined);
        map = new TmxMapLoader().load("demo/map/map.tmx");
        maxiMapX = map.getProperties().get("width", Integer.class)*map.getProperties().get("tilewidth", Integer.class);
        maxiMapY = map.getProperties().get("height", Integer.class)*map.getProperties().get("tileheight", Integer.class);
        renderer = new OrthogonalTiledMapRenderer(map, 1f);
        GUI_MAIN_STAGE = new Stage(uiViewport);
        GUI_CONTAINER = new Container<>();
        GUI_CONTAINER.setSize(
            uiViewport.getWorldWidth() - guiLeftMargin - guiRightMargin,
            uiViewport.getWorldHeight() - guiTopMargin - guiBottomMargin
        );
        GUI_CONTAINER.setFillParent(true);
        deBugGrid = true;
    }

    private void move() {
//        if (Gdx.input.isKeyPressed(Input.Keys.LEFT) || Gdx.input.isKeyPressed(Input.Keys.A)) {
//            if (gameViewport.getCamera().position.x < 0) {
//                gameViewport.getCamera().position.x = 0;
//            } else
//                gameViewport.getCamera().position.x -= moveSpeed;
//        }
//
//        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT) || Gdx.input.isKeyPressed(Input.Keys.D)) {
//            if (gameViewport.getCamera().position.x >= maxiMapX) {
//                gameViewport.getCamera().position.x = maxiMapX;
//            } else
//                gameViewport.getCamera().position.x += moveSpeed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.UP) || Gdx.input.isKeyPressed(Input.Keys.W)) {
//            if (gameViewport.getCamera().position.y >= maxiMapY) {
//                gameViewport.getCamera().position.y = maxiMapY;
//            } else
//                gameViewport.getCamera().position.y += moveSpeed;
//        }
//        if (Gdx.input.isKeyPressed(Input.Keys.DOWN) || Gdx.input.isKeyPressed(Input.Keys.S)) {
//            if (gameViewport.getCamera().position.y < 0) {
//                gameViewport.getCamera().position.y = 0;
//            } else
//                gameViewport.getCamera().position.y -= moveSpeed;
//        }

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
            moveRight(moveSpeed);
        }
        if (LEFT) {
            moveLeft(moveSpeed);
        }
        if (UP) {
            moveUp(moveSpeed);
        }
        if (DOWN) {
            moveDown(moveSpeed);
        }
    }

    public void moveUp(float distance){
        gameViewport.getCamera().position.y += distance;
    }

    public void moveDown(float distance){
        gameViewport.getCamera().position.y -= distance;
    }

    public void moveLeft(float distance){
        gameViewport.getCamera().position.x -= distance;
    }

    public void moveRight(float distance){
        gameViewport.getCamera().position.x += distance;
    }

    @Override
    public void show() {

    }

    private float time = 0;

    @Override
    public void render(float delta) {
        time += delta;
        move();
        gameViewport.apply();
        uiViewport.apply();
        renderer.setView((OrthographicCamera) gameViewport.getCamera());
        renderer.render();
        gameSpriteBatch.begin();
        if (deBugGrid){
            gameSpriteBatch.draw(pixelTexture, 0, 0, 10, 10);
        }
        gameSpriteBatch.end();
        gameViewport.getCamera().update();
    }

    @Override
    public void resize(int width, int height) {
//        gameSpriteBatch.setProjectionMatrix(gameViewport.getCamera().combined); // 设置投影矩阵
        gameViewport.update(width, height);
        uiViewport.update(width, height, true);
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

    }
}
