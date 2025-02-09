package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import xiaojing.galactic_dogfight.runtimeException.CustomRuntimeException;


import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.server.NewId.newUnitIdName;

/**
 * @author 尽
 * @apiNote 实体实体
 */
public class Entity extends Actor {
    protected final Array<Entity> sonEntity;  // 子实体
    protected final EntityType entityType;      // 实体类型
    protected final Array<EntityTag> entityTag; // 实体标签
    protected float currentSpeed;           // 实体当前速度
    protected float speed;                  // 实体移动速度
    protected float sideSpeed;              // 实体侧向 （左右）移动速度
    protected float retreatSpeed;           // 实体后退移动速度 这个是倍率
    protected float rotationalSpeed;        // 实体旋转速度
    private final Sprite sprite;            // 实体纹理
    private boolean isMove;                 // 是否移动

    // region 构造函数

    public Entity(EntityBuilder builder) {
        this(builder.entityIdName,
            builder.texture,
            builder.entityType,
            builder.speed,
            builder.x,
            builder.y,
            builder.retreatSpeed,
            builder.rotationalSpeed,
            builder.sideSpeed,
            builder.width,
            builder.height
        );
    }

    /**
     * 构造函数
     */
    public Entity(String entityIdName, Texture texture, EntityType entityType,
                  float speed, float x, float y,
                  float retreatSpeed, float rotationalSpeed, float sideSpeed,
                  float width, float height) {
        this.sprite = new Sprite(texture);
        this.sonEntity = new Array<>();
        this.entityTag = new Array<>();
        setName(entityIdName);
        this.entityType = entityType;
        this.speed = speed;
        this.retreatSpeed = retreatSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.sideSpeed = sideSpeed;
        setEntityPosition(x, y);
        setEntitySize(width, height);
        previousPosition = new Vector2();
        setRotation(90);
    }
    // endregion

    // region 方法

    /** 绘制 */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        // 检查是否是时间流逝的开始，用于初始化速度计算
        updateSpeed();
        sprite.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }

    private boolean isTimeElapsed;          // 是否延迟
    private Vector2 previousPosition;       // 上次位置
    private int frameCount;                 // 帧计数
    private float accumulatedSpeedChange;   // 累积的速度变化

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


    /** 添加实体标签 */
    public Entity abbEntityTag(EntityTag entityTag){
        this.entityTag.add(entityTag);
        return this;
    }

    /** 添加实体标签 */
    public Entity abbEntityTag(EntityTag... entityTag){
        for (EntityTag tag : entityTag) {
            this.entityTag.add(tag);
        }
        return this;
    }

    /** 设置实体位置 */
    public Entity setEntityPosition(Vector2 position){
        return setEntityPosition(position.x, position.y);
    }

    /** 设置实体位置 */
    public Entity setEntityPosition(float x, float y){
        setPosition(x, y);
        this.sprite.setPosition(x, y);
        return this;
    }

    /** 移动实体位置 */
    public Entity translateEntityPosition(float x, float y){
        moveBy(x, y);
        this.sprite.translate(x, y);
        return this;
    }

    /** 移动实体位置 */
    public Entity translateEntityPosition(Vector2 vector2){
        return translateEntityPosition(vector2.x, vector2.y);
    }

    /** 设置实体X轴 */
    public Entity setEntityX(float x){
        setX(x);
        this.sprite.setX(x);
        return this;
    }

    /** 移动实体X轴 */
    public Entity translateEntityX(float x){
        moveBy(x, 0);
        this.sprite.translateX(x);
        return this;
    }

    /** 设置实体Y轴 */
    public Entity setEntityY(float y){
        setY(y);
        this.sprite.setY(y);
        return this;
    }

    /** 移动实体Y轴 */
    public Entity translateEntityY(float y){
        moveBy(0, y);
        this.sprite.translateY(y);
        return this;
    }

    /** 顺时针旋转实体 */
    public Entity rotateRight(float amountInDegrees){
        rotateBy(-amountInDegrees);
        sprite.rotate( - amountInDegrees);
        return this;
    }

    /** 逆时针旋转实体 */
    public Entity rotateLeft(float amountInDegrees){
        rotateBy(amountInDegrees);
        sprite.setRotation((sprite.getRotation() + amountInDegrees) % 360);
        rotationChanged();
        return this;
    }

    /** 向前移动实体 */
    public Entity translateForward(float distance){

        // 更新实体的位置
        translateEntityPosition(getMoveVector(distance));
        return this;
    }

    /** 获取移动向量 */
    private Vector2 getMoveVector(float distance) {
        // 计算方向向量（基于当前旋转角度）
        Vector2 direction = new Vector2(1, 0); // 初始方向向量 (1, 0) 表示向右
        direction.setAngleRad(getRotation() * MathUtils.degreesToRadians); // 根据旋转角度设置方向向量
        // 计算移动向量
        // 方向向量乘以距离
        return new Vector2(direction).scl(distance);
    }
    // endregion

    // region 设置
    /** 设置子实体 */
    public Entity setSonEntity(Array<Entity> sonEntity) {
        this.sonEntity.items = sonEntity.items;
        return this;
    }

    /** 设置子实体 */
    public Entity setSonEntity(Entity Entity, int index){
        this.sonEntity.set(index, Entity);
        return this;
    }

    // region 设置纹理
    /** 设置实体纹理 */
    public Entity setEntityTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        this.sprite.setTexture(texture);
        return this;
    }

    /** 设置实体纹理 */
    public Entity setEntityTexture(String textureRoute) {
        if (textureRoute != null) {
            this.sprite.setTexture(new Texture(textureRoute));
            return this;
        }
        this.sprite.setTexture(emptyTexture);
        return this;
    }

    /** 设置实体纹理 */
    public Entity setEntityTexture(Skin skin, String textureName){
        try {
            this.sprite.setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            this.sprite.setTexture(emptyTexture);
        }
        return this;
    }
    // endregion

    /** 设置实体大小 */
    public Entity setEntitySize(float width, float height) {
        setSize(width, height);
        this.sprite.setSize(width, height);
        setOrigin(width / 2, height / 2);
        return this;
    }

    // region 设置速度
    /** 设置后退速度 */
    public Entity setRetreatSpeed(float speed){
        try {
            if (speed < 0 || speed > 1){
                throw new CustomRuntimeException("范围过大或者过小（范围：0~1）");
            }
        } catch (CustomRuntimeException e) {
            System.out.println("范围过大或者过小（范围：0~1）");
            return this;
        }
        retreatSpeed = speed;
        return this;
    }

    /** 设置侧向速度 */
    public Entity setSideSpeed(float speed){
        sideSpeed = speed;
        return this;
    }
    // endregion

    // endregion

    // region 获取
    /** 获取实体类型 */
    public EntityType getEntityType() {
        return entityType;
    }

    /** 获取子实体 */
    public Entity getSonEntity(int index){
        return sonEntity.get(index);
    }

    /** 获取子实体 */
    public Array<Entity> getSonEntity() {
        return sonEntity;
    }

    /** 获取实体命名名称ID */
    public String getEntityNamingID(){
        return getName().substring(0, getName().indexOf(":"));
    }

    /** 获取实体标签 */
    public Array<EntityTag> getEntityTag() {
        return entityTag;
    }

    /** 获取实体标签数组 */
    public EntityTag[] getEntityTagList() {
        return entityTag.toArray(EntityTag.class);
    }

    /** 获取实体位置向量 */
    public Vector2 getPosition(){
        return new Vector2(getX(), getY());
    }

    /** 获取实体原点位置向量 */
    public Vector2 getOriginPosition(){
        return new Vector2(getX() + getWidth() / 2, getY() + getHeight() / 2);
    }

    /** 获取实体X轴 */
    public float getEntityX(){
        return getX();
    }

    /** 获取实体Y轴 */
    public float getEntityY(){
        return getY();
    }

    /** 获取实体后退速度 */
    public float getRetreatSpeed(){
        return retreatSpeed;
    }

    /** 获取实体旋转速度 */
    public float getRotationalSpeed () {
        return rotationalSpeed;
    }

    /** 获取实体侧向速度 */
    public float getSideSpeed(){
        return sideSpeed;
    }

    /** 获取实体移动速度 */
    public float getSpeed(){
        return speed;
    }

    /** 获取实体当前移动速度 */
    public float getCurrentSpeed(){
        return currentSpeed;
    }

    /** 获取实体是否移动 */
    public boolean isMove() {
        return isMove;
    }

    // endregion
}
