package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import xiaojing.galactic_dogfight.server.unit.Entity;

import static xiaojing.galactic_dogfight.Main.cameraZoomRatio;

/**
 * @author 尽
 * @apiNote 默认相机
 */
public class DefaultCamera{
    private float moveSpeed = 150;          // 相机移动的速度
    private float acceleration = 1;         // 加速度
    private float currentMoveSpeed = 150;   // 当前速度
    private float temporarySpeed;           // 临时速度存储
    private boolean isTimeElapsed;          // 是否延迟
    private Vector2 previousPosition;       // 上次位置
    private Vector2 currentPosition;        // 当前位置
    private int frameCount;                 // 帧数

    public final OrthographicCamera camera; // 相机
    public float speed;                     // 速度


    public DefaultCamera(final Camera camera){
        this.camera = (OrthographicCamera) camera;
    }

    public DefaultCamera(){
        this.camera = new OrthographicCamera();
    }

    public DefaultCamera(Viewport viewport){
        this.camera = new OrthographicCamera(viewport.getWorldWidth(), viewport.getWorldHeight());
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

    /** 设置相机位置 */
    public void setPosition(Vector2 position){
        camera.position.x = position.x;
        camera.position.y = position.y;
    }

    /** 相机缩放 */
    public void scale() {
        boolean zoomIn = Gdx.input.isKeyPressed(Input.Keys.EQUALS);
        boolean zoomOut = Gdx.input.isKeyPressed(Input.Keys.MINUS);
        boolean resetZoom = Gdx.input.isKeyPressed(Input.Keys.DEL);
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

    /** 移动到目标 */
    public void moveTarget(float delta, Entity targetEntity) {
        // 获取目标实体的位置
        Vector2 targetPosition = targetEntity.getUnitOriginPosition();
        // 计算从当前位置到目标位置的方向向量
        Vector2 direction = new Vector2(targetPosition.x - getX(), targetPosition.y - getY());
        // 计算当前位置与目标位置之间的距离
        float distance = direction.len();
        float speedChange;

        // 检查是否是时间流逝的开始，用于初始化速度计算
        if (!isTimeElapsed) {
            isTimeElapsed = true;
            previousPosition = getPosition();
        } else {
            isTimeElapsed = false;
            currentPosition = getPosition();
            // 计算当前帧的速度变化
            speedChange = currentPosition.dst(previousPosition);
            // 如果速度变化为0，则重置速度
            if (speedChange == 0) {
                speed = 0;
            }
            // 根据速度变化调整速度
            if (frameCount == 3) {
                speed = temporarySpeed / 3;
                temporarySpeed = 0;
                frameCount = 0;
            }
            temporarySpeed += speedChange;
            frameCount++;
        }

        // 根据距离和当前移动速度更新位置
        if (distance >= currentMoveSpeed * delta) {
            // 方向向量标准化
            direction.nor();
            // 根据方向和速度移动
            translate(direction.x * currentMoveSpeed * delta, direction.y * currentMoveSpeed * delta);
            // 根据摄像机视口尺寸计算极限距离
            // TODO 还需要优化极限速度调节添加回弹缓冲
            float extremeDistance = (camera.viewportWidth > camera.viewportHeight) ? camera.viewportHeight / 3 : camera.viewportWidth / 3;
            // 如果目标实体与当前实体之间的距离超过极限距离，则加速移动
            if (targetEntity.getUnitPosition().dst(getPosition()) >= extremeDistance) {
                currentMoveSpeed += currentMoveSpeed * acceleration * delta;
                return;
            }
            // 如果当前移动速度小于目标实体的速度，则加速到目标速度
            if (currentMoveSpeed < targetEntity.getSpeed()) {
                currentMoveSpeed += currentMoveSpeed * acceleration * delta;
                return;
            }
            // 如果当前移动速度达到或超过目标实体的速度，则保持目标速度
            if (currentMoveSpeed >= targetEntity.getSpeed()) {
                currentMoveSpeed = targetEntity.getSpeed();
            }
        } else {
            // 如果目标很近，直接移动到目标位置并重置速度
            setPosition(targetPosition.x, targetPosition.y);
            currentMoveSpeed = moveSpeed;
        }
    }
}
