package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import space.earlygrey.shapedrawer.ShapeDrawer;
import xiaojing.galactic_dogfight.server.inputProcessor.KeyProcessor;
import xiaojing.galactic_dogfight.server.player.Player;
import xiaojing.galactic_dogfight.server.unit.Entity;

import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.server.InputConfiguration.*;

/**
 * @author 尽
 * @apiNote 默认相机
 */
public class DefaultCamera implements KeyProcessor {
    private float moveSpeed = 100;          // 相机移动的速度
    private float acceleration = 1;         // 加速度
    private float currentSpeed = 100;       // 当前速度

    public final OrthographicCamera camera; // 相机
    private float speed;                    // 速度

    private boolean zoomIn;
    private boolean zoomOut;
    private boolean resetZoom;

    public DefaultCamera(final Camera camera){
        this.camera = (OrthographicCamera) camera;
        initialize();
    }

    public DefaultCamera(){
        this.camera = new OrthographicCamera();
        initialize();
    }

    public DefaultCamera(Viewport viewport){
        this.camera = new OrthographicCamera(viewport.getWorldWidth(), viewport.getWorldHeight());
        initialize();
    }

    /** 初始化 */
    public void initialize(){
        shapeDrawer = new ShapeDrawer(gameSpriteBatch, new TextureRegion(pixelTexture));
        previousPosition = new Vector2();
        addInputProcessor();
    }

    /** 更新按键状态 */
    @Override
    public void updateKeyState(int keycode, boolean state) {
        if (keycode == zoomInKey) {
            zoomIn = state;
        }
        if (keycode == zoomOutKey) {
            zoomOut = state;
        }
        if (keycode == resetZoomKey) {
            resetZoom = state;
        }
    }

    /** 获取相机位置X轴 */
    public float getX(){
        return camera.position.x;
    }

    /** 设置相机位置X轴 */
    public void setX(float x){
        camera.position.x = x;
    }

    /** 移动相机位置X轴 */
    public void translateX(float x){
        camera.translate(x, 0);
    }

    /** 获取相机位置Y轴 */
    public float getY(){
        return camera.position.y;
    }

    /** 设置相机位置Y轴 */
    public void setY(float y){
        camera.position.y = y;
    }

    /** 移动相机位置Y轴 */
    public void translateY(float y) {
        camera.translate(0, y);
    }

    /** 获取相机位置 */
    public Vector2 getPosition(){
        final Vector2 position = new Vector2();
        position.x = camera.position.x;
        position.y = camera.position.y;
        return position;
    }

    /** 设置相机位置 */
    public void setPosition(float x, float y){
        camera.position.x = x;
        camera.position.y = y;
    }

    /** 移动相机位置 */
    public void translate(float x, float y){
        camera.translate(x, y);
    }

    /** 移动相机位置 */
    public void translate(Vector2 vector2){
        camera.translate(vector2);
    }

    /** 设置相机位置 */
    public void setPosition(Vector2 position){
        camera.position.x = position.x;
        camera.position.y = position.y;
    }

    /** 相机缩放 */
    public void scale() {
        float zoomRatio = 0.01f;
        if (zoomOut) {
            reduceZoom(zoomRatio);
        }
        if (zoomIn) {
            amplifyZoom(zoomRatio);
        }
        if (resetZoom) {
            resetZoomLevel();
        }

        if (cameraZoomRatio >= 2){
            cameraZoomRatio = 2;
        }
        if (cameraZoomRatio <= 0.1){
            cameraZoomRatio = 0.1f;
        }

        camera.zoom = cameraZoomRatio;
    }

    /** 重置相机缩放 */
    public void resetZoomLevel() {
        cameraZoomRatio = 1;
    }

    /** 缩小相机 */
    public void reduceZoom(float ratio) {
        cameraZoomRatio -= ratio;
    }

    /** 放大相机 */
    public void amplifyZoom(float ratio) {
        cameraZoomRatio += ratio;
    }

    protected transient ShapeDrawer shapeDrawer;         // 绘制器
    protected transient Color BoundsColor = Color.WHITE; // 矩形框颜色
    protected transient boolean bugBox = true;                  // 是否显示矩形框

    protected void drawDebugBounds(float extremeDistance, Vector2 targetPosition) {
        if (!bugBox) return;
        shapeDrawer.circle(getX(), getY(), extremeDistance);
        shapeDrawer.circle(targetPosition.x, targetPosition.y, 10);
        shapeDrawer.line(new Vector2(getX(), getY()), targetPosition);
    }

    private float temporarySpeed;           // 临时速度存储
    private boolean isTimeElapsed;          // 是否延迟
    private Vector2 previousPosition;       // 上次位置
    private Vector2 currentPosition;        // 当前位置
    private int frameCount;                 // 帧计数
    private float accumulatedSpeedChange;   // 累积的速度变化

    /** 移动到目标 */
    public void moveTarget(float delta, Entity entity) {
        updateSpeed();
        // 获取目标实体的位置
        Vector2 targetPosition = entity.getOriginPosition();
        // 计算从当前位置到目标位置的方向向量
        Vector2 direction = new Vector2(targetPosition.x - getX(), targetPosition.y - getY());
        // 计算当前位置与目标位置之间的距离
        float distance = direction.len();
        direction.nor().scl(distance * entity.getSpeed()*0.01F * delta);
        if (entity instanceof Player player && player.isMove()){
            translate(getMoveVector((player.isDown ? player.getSpeed()*0.1F : -player.getSpeed() * player.getRetreatSpeed() * 0.1F)* delta, player));
        }
        {
            translate(direction);
        }

    }

    private Vector2 getMoveVector(float distance, Entity entity) {
        // 计算方向向量（基于当前旋转角度）
        Vector2 direction = new Vector2(1, 0); // 初始方向向量 (1, 0) 表示向右
        direction.setAngleRad(entity.getRotation() * MathUtils.degreesToRadians); // 根据旋转角度设置方向向量
        // 计算移动向量
        // 方向向量乘以距离
        return new Vector2(direction).scl(distance);
    }

    /** 检查是否是时间流逝的开始，用于初始化速度计算 */
    private void updateSpeed() {
        Vector2 currentPosition = getPosition();
        float speedChange = currentPosition.dst(previousPosition);

        if (!isTimeElapsed) {
            isTimeElapsed = true;
            previousPosition = currentPosition;
            return;
        }

        // 如果速度变化为0，则重置速度
        if (speedChange == 0) {
            currentSpeed = 0;
        } else {
            accumulatedSpeedChange += speedChange;
            frameCount++;
        }

        // 根据速度变化调整速度
        if (frameCount == 3) {
            currentSpeed = accumulatedSpeedChange / 3;
            accumulatedSpeedChange = 0;
            frameCount = 0;
        }

        previousPosition = currentPosition;
    }

    /** 获取速度 */
    public float getSpeed() {
        return speed;
    }

    /** 获取当前移动速度 */
    public float getCurrentSpeed(){
        return currentSpeed;
    }

    public void update(float delta, Entity entity) {
        moveTarget(delta, entity);
        scale();
        camera.update();
    }

}
