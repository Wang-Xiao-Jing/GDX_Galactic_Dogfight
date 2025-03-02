package xiaojing.galactic_dogfight.server.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;

import static com.badlogic.gdx.physics.box2d.BodyDef.BodyType.DynamicBody;
import static xiaojing.galactic_dogfight.Main.emptyTexture;

/**
 * @author 尽
 * @apiNote 实体实体
 */
public class Entity extends Actor {
    protected final Array<Entity> childEntity;  // 子实体
    protected final EntityType entityType;      // 实体类型
    protected final Array<EntityTag> entityTag; // 实体标签
    protected final BodyDef bodyDef;            // 实体物理属性
    private final Sprite sprite;                // 实体纹理
    protected float currentSpeed;               // 实体当前速度
    protected float speedMoving;                // 实体移动速度
    protected float sideSpeed;                  // 实体侧向移动速度
    protected float retreatSpeed;               // 实体后退移动倍率
    protected float rotationalSpeed;            // 实体旋转速度
    protected boolean isMove;                     // 是否在移动
    protected boolean isPressTheMobileButton;     // 是否按移动按键
    protected EventListener listener;           // 事件监听器
    private final float collisionBoxWidth;            // 实体碰撞盒宽度
    private final float collisionBoxHeight;           // 实体碰撞盒高度
    private Body body;
    private final float reductionRatio;        // 停止减速比
    // region 构造函数
    private boolean isTimeElapsed;          // 是否延迟
    private Vector2 previousPosition;       // 上次位置
    // endregion

    // region 方法
    private int frameCount;                 // 帧计数
    private float accumulatedSpeedChange;   // 累积的速度变化

    /**
     * 实体构造函数
     *
     * @param b 实体构造器
     */
    public Entity(EntityBuilder b) {
        this(b.entityIdName, b.texture, b.entityType, b.x, b.y,
                b.speed, b.retreatSpeed, b.rotationalSpeed, b.sideSpeed,
                b.width, b.height,
                b.collisionBoxWidth, b.collisionBoxHeight, b.reductionRatio);
    }

    /**
     * 实体构造函数
     *
     * @param entityIdName       实体ID标识符
     * @param texture            实体纹理
     * @param entityType         实体类型 {@link EntityType}
     * @param x                  X轴实体坐标
     * @param y                  Y轴实体坐标
     * @param speedMoving              实体移动速度
     * @param retreatSpeed       实体后退移动倍率
     * @param rotationalSpeed    实体旋转速度
     * @param sideSpeed          实体侧向移动速度
     * @param width              实体宽度
     * @param height             实体高度
     * @param collisionBoxWidth  实体碰撞盒宽度
     * @param collisionBoxHeight 实体碰撞盒高度
     */
    private Entity(String entityIdName, Texture texture, EntityType entityType,
                  float x, float y,
                  float speedMoving, float retreatSpeed, float rotationalSpeed, float sideSpeed,
                  float width, float height,
                  float collisionBoxWidth, float collisionBoxHeight, float reductionRatio) {
        sprite = new Sprite(texture);
        bodyDef = new BodyDef();
        bodyDef.gravityScale = 0;
        bodyDef.type = DynamicBody;
        childEntity = new Array<>();
        entityTag = new Array<>();
        setName(entityIdName);
        this.entityType = entityType;
        this.speedMoving = speedMoving;
        this.retreatSpeed = retreatSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.sideSpeed = sideSpeed;
        this.collisionBoxWidth = collisionBoxWidth;
        this.collisionBoxHeight = collisionBoxHeight;
        setEntitySize(width, height);
        setRotation(90); // 朝上，原先朝向是在左侧
        setEntityPosition(x, y);
        listener = new ClickListener();
        previousPosition = new Vector2();
        this.reductionRatio = reductionRatio;
    }

    /**
     * 添加到世界（默认方法）
     * <br/>
     * 方形碰撞盒
     *
     * @param world 世界
     */
    public void addToWorldDefault(World world) {
        Body ballBody = world.createBody(bodyDef);
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(collisionBoxWidth / 2, collisionBoxHeight / 2);
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        ballBody.setUserData(this);
        ballBody.createFixture(fixtureDef);
        shape.dispose(); // 释放形状
        body = ballBody;
    }

    /**
     * 同步
     */
    public Entity synchro() {
        setEntityPosition(body.getPosition().add(-getWidth() / 2, -getHeight() / 2));
//        setEntitySize(body.getPosition());
        return this;
    }

    /**
     * 绘制
     */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // 检查是否是时间流逝的开始，用于初始化速度计算
        updateSpeed();
        sprite.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
        pressTheMobileButton();
    }

    /**
     * 按移动按键
     */
    protected void pressTheMobileButton() {
        Vector2 velocity = body.getLinearVelocity();
        Vector2 dragForce = velocity.cpy().scl(-reductionRatio * speedMoving);
        Vector2 dragForce2 = velocity.cpy().scl(-1 * speedMoving);
        body.applyForce(isPressTheMobileButton ? dragForce2 : dragForce, body.getWorldCenter(), false);
    }

    /**
     * 检查是否是时间流逝的开始，用于初始化速度计算
     */
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
            isMove = false;
            currentSpeed = 0;
        } else {
            isMove = true;
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

    /**
     * 添加实体标签
     */
    public Entity abbEntityTag(EntityTag entityTag) {
        this.entityTag.add(entityTag);
        return this;
    }

    /**
     * 添加实体标签
     */
    public Entity abbEntityTag(EntityTag... entityTag) {
        for (EntityTag tag : entityTag) {
            this.entityTag.add(tag);
        }
        return this;
    }

    /**
     * 设置实体位置
     */
    public Entity setEntityPosition(Vector2 position) {
        return setEntityPosition(position.x, position.y);
    }

    /**
     * 设置实体位置
     */
    public Entity setEntityPosition(float x, float y) {
        setPosition(x, y);
        this.sprite.setPosition(x, y);
        this.bodyDef.position.set(getWidth() / 2 + x, getHeight() / 2 + y);
        return this;
    }

    /**
     * 移动实体位置
     */
    public Entity translateEntityPosition(float x, float y) {
//        moveBy(x, y);
//        this.sprite.translate(x, y);
//        this.bodyDef.position.add(x, y);
        body.setLinearVelocity(x, y);
        return this;
    }

    /**
     * 移动实体位置
     */
    public Entity translateEntityPosition(Vector2 vector2) {
        return translateEntityPosition(vector2.x, vector2.y);
    }

    /**
     * 移动实体X轴
     */
    public Entity translateEntityX(float x) {
        moveBy(x, 0);
        this.sprite.translateX(x);
        this.bodyDef.position.x += x;
        return this;
    }

    /**
     * 移动实体Y轴
     */
    public Entity translateEntityY(float y) {
        moveBy(0, y);
        this.sprite.translateY(y);
        this.bodyDef.position.y += y;
        return this;
    }

    /**
     * 顺时针旋转实体
     */
    public Entity rotateRight(float amountInDegrees) {
        return rotateEntity(-amountInDegrees);
    }

    /**
     * 逆时针旋转实体
     */
    public Entity rotateLeft(float amountInDegrees) {

        return rotateEntity(amountInDegrees);
    }

    /**
     * 设置实体角度
     */
    public Entity setRotate(float amountInDegrees) {
        setRotation(amountInDegrees);
        sprite.setRotation(amountInDegrees);
        bodyDef.angle = amountInDegrees * MathUtils.degreesToRadians;
        return this;
    }

    /**
     * 旋转实体
     */
    public Entity rotateEntity(float amountInDegrees) {
        rotateBy(amountInDegrees);
        sprite.setRotation((sprite.getRotation() + amountInDegrees) % 360);
        bodyDef.angle += amountInDegrees * MathUtils.degreesToRadians;
        return this;
    }

    /**
     * 移动实体 方向
     */
    public Entity translateForward(float distance) {
        return translateEntityPosition(getMoveVector(distance));
    }

    /**
     * 清除实体
     */
    @Override
    public void clear() {
        super.clear();
    }

    /**
     * 设置子实体
     */
    public Entity setChildEntity(Entity Entity, int index) {
        this.childEntity.set(index, Entity);
        return this;
    }

    /**
     * 设置实体纹理
     */
    public Entity setEntityTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        this.sprite.setTexture(texture);
        return this;
    }

    // endregion

    // region 设置

    /**
     * 设置实体纹理
     */
    public Entity setEntityTexture(String textureRoute) {
        if (textureRoute != null) {
            this.sprite.setTexture(new Texture(textureRoute));
            return this;
        }
        this.sprite.setTexture(emptyTexture);
        return this;
    }

    /**
     * 设置实体纹理
     */
    public Entity setEntityTexture(Skin skin, String textureName) {
        try {
            this.sprite.setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            this.sprite.setTexture(emptyTexture);
        }
        return this;
    }

    // region 设置纹理

    /**
     * 设置实体大小
     */
    public Entity setEntitySize(float width, float height) {
        setSize(width, height);
        this.sprite.setSize(width, height);
        setOrigin(width / 2, height / 2);
        return this;
    }

    /**
     * 获取实体类型
     */
    public EntityType getEntityType() {
        return entityType;
    }

    /**
     * 获取子实体
     */
    public Entity getSonEntity(int index) {
        return childEntity.get(index);
    }
    // endregion

    /**
     * 获取子实体
     */
    public Array<Entity> getChildEntity() {
        return childEntity;
    }

    // region 设置速度

    /**
     * 设置子实体
     */
    public Entity setChildEntity(Array<Entity> sonEntity) {
        this.childEntity.items = sonEntity.items;
        return this;
    }

    /**
     * 获取实体命名名称ID
     */
    public String getEntityNamingID() {
        return getName().substring(0, getName().indexOf(":"));
    }
    // endregion

    // endregion

    // region 获取

    /**
     * 获取实体标签
     */
    public Array<EntityTag> getEntityTag() {
        return entityTag;
    }

    /**
     * 获取实体标签数组
     */
    public EntityTag[] getEntityTagList() {
        return entityTag.toArray(EntityTag.class);
    }

    /**
     * 获取实体位置向量
     */
    public Vector2 getPosition() {
        return new Vector2(getX(), getY());
    }

    /**
     * 获取实体原点位置向量
     */
    public Vector2 getOriginPosition() {
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    /**
     * 获取实体X轴
     */
    public float getEntityX() {
        return getX();
    }

    /**
     * 设置实体X轴
     */
    public Entity setEntityX(float x) {
        setX(x);
        this.sprite.setX(x);
        this.bodyDef.position.x = getWidth() / 2 + x;
        return this;
    }

    /**
     * 获取实体Y轴
     */
    public float getEntityY() {
        return getY();
    }

    /**
     * 设置实体Y轴
     */
    public Entity setEntityY(float y) {
        setY(y);
        this.sprite.setY(y);
        this.bodyDef.position.y = getHeight() / 2 + y;
        return this;
    }

    /**
     * 获取实体后退速度
     */
    public float getRetreatSpeed() {
        return retreatSpeed;
    }

    /**
     * 设置后退速度
     */
    public Entity setRetreatSpeed(float speed) {
        if (speed < 0 || speed > 1) {
            System.out.println("范围过大或者过小（范围：0~1）");
            retreatSpeed = 1;
            return this;
        }
        retreatSpeed = speed;
        return this;
    }

    /**
     * 获取实体旋转速度
     */
    public float getRotationalSpeed() {
        return rotationalSpeed;
    }

    /**
     * 获取实体侧向速度
     */
    public float getSideSpeed() {
        return sideSpeed;
    }

    /**
     * 设置侧向速度
     */
    public Entity setSideSpeed(float speed) {
        sideSpeed = speed;
        return this;
    }

    /**
     * 获取实体移动速度
     */
    public float getSpeedMoving() {
        return speedMoving;
    }

    /**
     * 获取实体当前移动速度
     */
    public float getCurrentSpeed() {
        return currentSpeed;
    }

    /**
     * 获取实体是否移动
     */
    public boolean isMove() {
        return isMove;
    }

    /**
     * 获取实体碰撞盒
     */
    public BodyDef getBodyDef() {
        return this.bodyDef;
    }

    /**
     * 获取移动向量
     */
    private Vector2 getMoveVector(float distance) {
        // 计算方向向量（基于当前旋转角度）
        Vector2 direction = new Vector2(1, 0); // 初始方向向量 (1, 0) 表示向右
        direction.setAngleRad(getRotation() * MathUtils.degreesToRadians); // 根据旋转角度设置方向向量
        // 计算移动向量
        // 方向向量乘以距离
        return new Vector2(direction).scl(distance);
    }

    /**
     * 获取实体是否按下移动按钮
     */
    public boolean isPressTheMobileButton() {
        return isPressTheMobileButton;
    }

    // endregion
}
