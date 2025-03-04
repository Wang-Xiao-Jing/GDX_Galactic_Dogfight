package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;
import xiaojing.galactic_dogfight.server.inputProcessor.KeyProcessor;

import static xiaojing.galactic_dogfight.StaticClass.BASICS_CAMERA_ZOOM_RATIO;
import static xiaojing.galactic_dogfight.StaticClass.cameraZoomRatio;
import static xiaojing.galactic_dogfight.server.InputConfiguration.*;

/**
 * @apiNote 默认相机
 */
public class DefaultCamera implements KeyProcessor {
    private float currentSpeed;       // 当前速度

    public final OrthographicCamera camera; // 相机

    private boolean zoomIn;
    private boolean zoomOut;
    private boolean resetZoom;
    public boolean isCenter; // 是否居中

    public DefaultCamera(final Camera camera) {
        this.camera = (OrthographicCamera) camera;
        initialize();
    }

    public DefaultCamera() {
        this.camera = new OrthographicCamera();
        initialize();
    }

    public DefaultCamera(Viewport viewport) {
        this.camera = new OrthographicCamera(viewport.getWorldWidth(), viewport.getWorldHeight());
        initialize();
    }

    /**
     * 初始化
     */
    public void initialize() {
//        previousPosition = new Vector2();
        addInputProcessor();
    }

    @Override
    public void keyDownOverride(int keycode) {
        if (keycode == centerZoomKey) {
            isCenter = !isCenter;
        }
    }

    /**
     * 更新按键状态
     */
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

    /**
     * 获取相机位置X轴
     */
    public float getX() {
        return camera.position.x;
    }

    /**
     * 设置相机位置X轴
     */
    public void setX(float x) {
        camera.position.x = x;
    }

    /**
     * 移动相机位置X轴
     */
    public void translateX(float x) {
        camera.translate(x, 0);
    }

    /**
     * 获取相机位置Y轴
     */
    public float getY() {
        return camera.position.y;
    }

    /**
     * 设置相机位置Y轴
     */
    public void setY(float y) {
        camera.position.y = y;
    }

    /**
     * 移动相机位置Y轴
     */
    public void translateY(float y) {
        camera.translate(0, y);
    }

    /**
     * 获取相机位置
     */
    public Vector2 getPosition() {
        final Vector2 position = new Vector2();
        position.x = camera.position.x;
        position.y = camera.position.y;
        return position;
    }

    /**
     * 设置相机位置
     */
    public void setPosition(Vector2 position) {
        camera.position.x = position.x;
        camera.position.y = position.y;
    }

    /**
     * 设置相机位置
     */
    public void setPosition(float x, float y) {
        camera.position.x = x;
        camera.position.y = y;
    }

    /**
     * 移动相机位置
     */
    public void translate(float x, float y) {
        camera.translate(x, y);
    }

    /**
     * 移动相机位置
     */
    public void translate(Vector2 vector2) {
        camera.translate(vector2);
    }

    /**
     * 相机缩放
     */
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

        if (cameraZoomRatio >= 1) {
            cameraZoomRatio = 1;
        }
        if (cameraZoomRatio <= 0.5F) {
            cameraZoomRatio = 0.5F;
        }

        camera.zoom = cameraZoomRatio + BASICS_CAMERA_ZOOM_RATIO;
    }

    /**
     * 重置相机缩放
     */
    public void resetZoomLevel() {
        cameraZoomRatio = 0;
    }

    /**
     * 缩小相机
     */
    public void reduceZoom(float ratio) {
        cameraZoomRatio -= ratio;
    }

    /**
     * 放大相机
     */
    public void amplifyZoom(float ratio) {
        cameraZoomRatio += ratio;
    }

//    private float temporarySpeed;           // 临时速度存储
//    private boolean isTimeElapsed;          // 是否延迟
//    private Vector2 previousPosition;       // 上次位置
//    private Vector2 currentPosition;        // 当前位置
//    private int frameCount;                 // 帧计数
//    private float accumulatedSpeedChange;   // 累积的速度变化

    /**
     * 移动摄像机到目标实体的位置。
     * <p>
     * 该方法根据目标实体的位置，计算摄像机移动的方向和距离，并根据实体的速度调整移动速度。
     * 如果目标实体是玩家，则根据玩家是否在移动、是否瞄准等状态进一步调整摄像机的移动。
     *
     * @param delta 时间间隔（秒），用于平滑处理移动
     * @param entity 目标实体，摄像机将移动到该实体的位置
     */
    /*public void moveTarget(float delta, Entity entity) {
        float entitySpeed = entity.getSpeed()*500;
        // 更新摄像机的速度
        updateSpeed();

        // 获取目标实体的当前位置
        Vector2 targetPosition = entity.getOriginPosition();

        // 计算从当前摄像机位置到目标实体位置的方向向量
        Vector2 direction = new Vector2(targetPosition.x - getX(), targetPosition.y - getY());

        // 计算方向向量的长度（即距离）
        float distance = direction.len();

        // 将方向向量归一化并缩放，使其长度等于目标实体的速度乘以时间间隔
        direction.nor().scl(distance * entitySpeed * 0.01F * delta);

        float cameraZoomMultiplier = camera.zoom;
        if (cameraZoomMultiplier >= 1.6){
            cameraZoomMultiplier = 1.6f;
        }

        // 如果目标实体是玩家，则根据玩家的状态进一步调整摄像机的移动
        if (entity instanceof Player player && !isCenter) {
            // 如果玩家正在移动
            if (player.isMove()) {
               if (!player.isAim && !player.isDown){
                   direction.scl(1 / cameraZoomMultiplier * 0.5F);
               }else{
                   direction.scl(1 / cameraZoomMultiplier * 2F);
               }
                // 根据玩家的移动状态和瞄准状态，计算额外的移动向量
                float moveSpeed = ((player.isAim && !player.isDown) ? -50 : 0.1F) *
                    (player.isDown ? entitySpeed * 0.2F : - entitySpeed * player.getRetreatSpeed() * 0.1F) * delta;
                translate(getMoveVector(moveSpeed * cameraZoomMultiplier, player));
                // 按照计算的方向向量移动摄像机
                translate(direction);
            } else {
                // 如果玩家没有移动，摄像机缓慢地向目标位置移动
                translate(direction.scl(0.5F));

                // 如果玩家正在瞄准，添加额外的移动向量
                if (player.isAim) {
                    translate(getMoveVector(1.5F * cameraZoomMultiplier, player));
                }
            }
            return;
        }
        if (isCenter){
            translate(direction.scl(5));
//           setPosition(targetPosition.x, targetPosition.y);
            return;
        }
        // 如果目标实体不是玩家，摄像机缓慢地向目标位置移动
        translate(direction.scl(0.5F));

    }*/

    /**
     * 获取移动向量。
     * <p>
     * 该方法根据实体的旋转角度和给定的速度，计算出一个二维向量，表示摄像机应朝哪个方向移动以及移动的距离。
     *
     * @param speed 速度，决定了移动向量的长度
     * @param entity 实体，用于获取其旋转角度
     * @return 返回一个二维向量，表示摄像机的移动方向和距离
     */
    /*private Vector2 getMoveVector(float speed, Entity entity) {
        // 创建一个初始方向向量，默认指向右侧 (1, 0)
        Vector2 direction = new Vector2(1, 0);

        // 根据实体的旋转角度设置方向向量的角度
        direction.setAngleRad(entity.getRotation() * MathUtils.degreesToRadians);

        // 将方向向量缩放至指定的速度
        return new Vector2(direction).scl(speed);
    }*/

    /** 检查是否是时间流逝的开始，用于初始化速度计算 */
    /*private void updateSpeed() {
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
    }*/

    /** 获取当前移动速度 */
    public float getCurrentSpeed(){
        return currentSpeed;
    }

    public void update(float delta) {
//        moveTarget(delta, entity);
        scale();
        camera.update();
    }

}
