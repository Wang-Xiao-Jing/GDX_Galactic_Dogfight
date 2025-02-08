package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import xiaojing.galactic_dogfight.runtimeException.CustomRuntimeException;


import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.server.NewId.newUnitIdName;

/**
 * @author 尽
 * @apiNote 实体单位
 */
public class Entity extends Actor {
    protected final Array<Entity> sonUnit;  // 子单位
    protected final UnitType unitType;      // 单位类型
    protected final Array<UnitTag> unitTag; // 单位标签
    protected final Vector2 currentSpeed;   // 单位当前速度
    protected float speed;                  // 单位移动速度
    protected float sideSpeed;              // 单位侧向 （左右）移动速度
    protected float retreatSpeed;           // 单位后退移动速度 这个是倍率
    protected float rotationalSpeed;        // 单位旋转移动速度
    private final Sprite SPRITE;            // 单位纹理

    // region 构造函数

    public Entity(EntityUnitBuilder builder) {
        this(builder.unitIdName,
            builder.texture,
            builder.unitType,
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
    public Entity(String unitIdName, Texture texture, UnitType unitType,
                  float speed, float x, float y,
                  float retreatSpeed, float rotationalSpeed, float sideSpeed,
                  float width, float height) {
        this.SPRITE = new Sprite(texture);
        this.sonUnit = new Array<>();
        this.unitTag = new Array<>();
        this.currentSpeed = new Vector2();
        setName(unitIdName);
        this.unitType = unitType;
        this.speed = speed;
        this.retreatSpeed = retreatSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.sideSpeed = sideSpeed;
        setUnitPosition(x, y);
        setUnitSize(width, height);
    }
    // endregion

    // region 方法
    /** 绘制 */
    @Override
    public void draw(Batch batch, float parentAlpha) {
        SPRITE.draw(batch, parentAlpha);
        super.draw(batch, parentAlpha);
    }
    /** 添加单位标签 */
    public Entity abbUnitTag(UnitTag unitTag){
        this.unitTag.add(unitTag);
        return this;
    }

    /** 添加单位标签 */
    public Entity abbUnitTag(UnitTag... unitTag){
        for (UnitTag tag : unitTag) {
            this.unitTag.add(tag);
        }
        return this;
    }

    /** 设置单位位置 */
    public Entity setUnitPosition(Vector2 position){
        return setUnitPosition(position.x, position.y);
    }

    /** 设置单位位置 */
    public Entity setUnitPosition(float x, float y){
        setPosition(x, y);
        this.SPRITE.setPosition(x, y);
        return this;
    }

    /** 移动单位位置 */
    public Entity translateUnitPosition(float x, float y){
        moveBy(x, y);
        setPosition(x, y);
        this.SPRITE.translate(x, y);
        return this;
    }

    /** 设置单位X轴 */
    public Entity setUnitX(float x){
        setX(x);
        this.SPRITE.setX(x);
        return this;
    }

    /** 移动单位X轴 */
    public Entity translateUnitX(float x){
        moveBy(x, 0);
        this.SPRITE.translateX(x);
        return this;
    }

    /** 设置单位Y轴 */
    public Entity setUnitY(float y){
        setY(y);
        this.SPRITE.setY(y);
        return this;
    }

    /** 移动单位Y轴 */
    public Entity translateUnitY(float y){
        moveBy(0, y);
        this.SPRITE.translateY(y);
        return this;
    }
    // endregion

    // region 设置
    /** 设置子单位 */
    public Entity setSonUnit(Array<Entity> sonUnit) {
        this.sonUnit.items = sonUnit.items;
        return this;
    }

    /** 设置子单位 */
    public Entity setSonUnit(Entity Entity, int index){
        this.sonUnit.set(index, Entity);
        return this;
    }

    // region 设置纹理
    /** 设置单位纹理 */
    public Entity setUnitTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        this.SPRITE.setTexture(texture);
        return this;
    }

    /** 设置单位纹理 */
    public Entity setUnitTexture(String textureRoute) {
        if (textureRoute != null) {
            this.SPRITE.setTexture(new Texture(textureRoute));
            return this;
        }
        this.SPRITE.setTexture(emptyTexture);
        return this;
    }

    /** 设置单位纹理 */
    public Entity setUnitTexture(Skin skin, String textureName){
        try {
            this.SPRITE.setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            this.SPRITE.setTexture(emptyTexture);
        }
        return this;
    }
    // endregion

    /** 设置单位大小 */
    public Entity setUnitSize(float width, float height) {
        setSize(width, height);
        this.SPRITE.setSize(width, height);
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
    /** 获取单位类型 */
    public UnitType getUnitType() {
        return unitType;
    }

    /** 获取子单位 */
    public Entity getSonUnit(int index){
        return sonUnit.get(index);
    }

    /** 获取子单位 */
    public Array<Entity> getSonUnit() {
        return sonUnit;
    }

    /** 获取单位命名名称ID */
    public String getUnitNamingID(){
        return getName().substring(0, getName().indexOf(":"));
    }

    /** 获取单位标签 */
    public Array<UnitTag> getUnitTag() {
        return unitTag;
    }

    /** 获取单位标签数组 */
    public UnitTag[] getUnitTagList() {
        return unitTag.toArray(UnitTag.class);
    }

    /** 获取单位位置向量 */
    public Vector2 getUnitPosition(){
        return new Vector2(getX(), getY());
    }

    /** 获取单位原点位置向量 */
    public Vector2 getUnitOriginPosition(){
        return new Vector2(getX()+getWidth()/2, getY()+getHeight()/2);
    }

    /** 获取单位X轴 */
    public float getUnitX(){
        return getX();
    }

    /** 获取单位Y轴 */
    public float getUnitY(){
        return getY();
    }

    /** 获取单位后退速度 */
    public float getRetreatSpeed(){
        return retreatSpeed;
    }

    /** 获取单位旋转速度 */
    public float getRotationalSpeed () {
        return rotationalSpeed;
    }

    /** 获取单位侧向速度 */
    public float getSideSpeed(){
        return sideSpeed;
    }

    /** 获取单位移动速度 */
    public float getSpeed(){
        return speed;
    }

    // endregion
}
