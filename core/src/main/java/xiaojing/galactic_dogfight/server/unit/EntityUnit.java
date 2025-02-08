package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import space.earlygrey.shapedrawer.ShapeDrawer;
import xiaojing.galactic_dogfight.runtimeException.CustomRuntimeException;


import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.server.NewId.*;
import static xiaojing.galactic_dogfight.server.NewId.newUnitIdName;
import static xiaojing.galactic_dogfight.server.unit.UnitType.EMPTY;

/**
 * @author 尽
 * @apiNote 实体单位
 */
public class EntityUnit extends Actor {
    private final String unitIdName;                // 单位标识符
    protected final Array<EntityUnit> sonUnit;      // 子单位
//    private final Body unitCollisionBox;            // 单位碰撞箱
    protected final UnitType unitType;              // 单位类型
    protected final Array<UnitTag> unitTag;         // 单位标签
//    private final Vector2 position;                 // 单位位置
    protected final Vector2 currentSpeed;           // 单位当前速度
    protected float speed;                          // 单位移动速度
    protected float sideSpeed;                      // 单位侧向 （左右）移动速度
    protected float retreatSpeed;                   // 单位后退移动速度 这个是倍率
    protected float rotationalSpeed;                // 单位旋转移动速度
    private final Sprite SPRITE;                          // 单位纹理

//    protected transient ShapeDrawer shapeDrawer;         // 绘制器
//    protected transient Color BoundsColor = Color.WHITE; // 矩形框颜色
//    protected transient boolean bugBox;                  // 是否显示矩形框

    // region 构造函数

    public EntityUnit(EntityUnitBuilder builder) {
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
//            builder.getUnitCollisionBox().width,
//            builder.getUnitCollisionBox().height
        );
    }

    /**
     * 构造函数
     */
    public EntityUnit(String unitIdName, Texture texture, UnitType unitType,
                      float speed, float x, float y,
                      float retreatSpeed, float rotationalSpeed, float sideSpeed,
                      float width, float height/*, float rectangleWidth, float rectangleHeight*/) {
        this.SPRITE = new Sprite(texture);
        this.sonUnit = new Array<>();
//        this.unitCollisionBox = new Body();
        this.unitTag = new Array<>();
//        this.position = new Vector2();
        this.currentSpeed = new Vector2();
//        shapeDrawer = new ShapeDrawer(gameSpriteBatch, new TextureRegion(pixelTexture));
        this.unitIdName = unitIdName;
        this.unitType = unitType;
        this.speed = speed;
        this.retreatSpeed = retreatSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.sideSpeed = sideSpeed;
        setUnitPosition(x, y);
        setUnitSize(width, height);
//        setUnitRectangle(rectangleWidth, rectangleHeight);
//        this.SPRITE.setOriginCenter();
    }
    // endregion

    // region 方法

//    /** 碰撞检测 */
//    public void overlaps(Rectangle r){
//        if (r == null) return;
//        if (!unitCollisionBox.overlaps(r)) return;
//        float centerX = r.x + r.width / 2;
//        float centerY = r.y + r.height / 2;
//        if(centerX < unitCollisionBox.x){
//            translateUnitX(unitCollisionBox.x - centerX);
//        }
//
//        if(centerX > unitCollisionBox.x+unitCollisionBox.width){
//            translateUnitX(unitCollisionBox.x + unitCollisionBox.width - centerX);
//        }
//
//        if(centerY < unitCollisionBox.y){
//            translateUnitY(unitCollisionBox.y - centerY);
//        }
//
//        if(centerY > unitCollisionBox.y+unitCollisionBox.height){
//            translateUnitY(unitCollisionBox.y + unitCollisionBox.height - centerY);
//        }
//    }
//
//    /** 绘制调试盒 */
//    public void drawDebug() {
//        drawDebugBounds(BoundsColor);
//    }
//
//    /** 绘制调试盒 */
//    protected void drawDebugBounds(Color color) {
//        if (!bugBox) return;
//        shapeDrawer.rectangle(unitCollisionBox.x - 0.25f, unitCollisionBox.y - 0.25f, unitCollisionBox.getWidth() + 0.25f, unitCollisionBox.getHeight() + 0.25f, color, 0.5f);
//    }

//    /** 绘制 */
//    @Override
//    public void draw(Batch batch, float parentAlpha) {
////        drawDebug();
//        super.draw(batch, parentAlpha);
//    }

    /** 添加单位标签 */
    public EntityUnit abbUnitTag(UnitTag unitTag){
        this.unitTag.add(unitTag);
        return this;
    }

    /** 添加单位标签 */
    public EntityUnit abbUnitTag(UnitTag... unitTag){
        for (UnitTag tag : unitTag) {
            this.unitTag.add(tag);
        }
        return this;
    }

    /** 设置单位位置 */
    public EntityUnit setUnitPosition(Vector2 position){
        return setUnitPosition(position.x, position.y);
    }

    /** 设置单位位置 */
    public EntityUnit setUnitPosition(float x, float y){
//        this.position.set(position);
        setPosition(x, y);
        this.SPRITE.setPosition(x, y);
//        this.unitCollisionBox.setPosition(x, y);
        return this;
    }

    /** 移动单位位置 */
    public EntityUnit translateUnitPosition(float x, float y){
//        this.position.add(x, y);
        moveBy(x, y);
        setPosition(x, y);
        this.SPRITE.translate(x, y);
//        this.unitCollisionBox.setPosition(unitCollisionBox.x + x, unitCollisionBox.y + y);
        return this;
    }

    /** 设置单位X轴 */
    public EntityUnit setUnitX(float x){
//        this.position.x = x;
        setX(x);
        this.SPRITE.setX(x);
//        this.unitCollisionBox.setX(x);
        return this;
    }

    /** 移动单位X轴 */
    public EntityUnit translateUnitX(float x){
        moveBy(x, 0);
        this.SPRITE.translateX(x);
//        this.unitCollisionBox.setX(unitCollisionBox.x+x);
        return this;
    }

    /** 设置单位Y轴 */
    public EntityUnit setUnitY(float y){
        setY(y);
        this.SPRITE.setY(y);
//        this.unitCollisionBox.setY(y);
        return this;
    }

    /** 移动单位Y轴 */
    public EntityUnit translateUnitY(float y){
        moveBy(0, y);
        this.SPRITE.translateY(y);
//        this.unitCollisionBox.setY(unitCollisionBox.y+y);
        return this;
    }
    // endregion

    // region 设置
//    /** 设置调试盒颜色 */
//    protected void setBoundsColor(Color color){
//        BoundsColor = color;
//    }
//
//    /** 设置deBug状态 */
//    public EntityUnit setDeBug(boolean enabled){
//        bugBox = enabled;
//        return this;
//    }
//
//    /** 设置deBug状态 */
//    public EntityUnit setAllDeBug(boolean enabled){
//        for (int i = 0;i < sonUnit.size; i++){
//            sonUnit.get(i).setDeBug(enabled);
//        }
//        bugBox = enabled;
//        return this;
//    }

//    /** 设置deBug状态 */
//    public EntityUnit debug() {
//        setDeBug(true);
//        return this;
//    }

    /** 设置子单位 */
    public EntityUnit setSonUnit(Array<EntityUnit> sonUnit) {
        this.sonUnit.items = sonUnit.items;
        return this;
    }

    /** 设置子单位 */
    public EntityUnit setSonUnit(EntityUnit EntityUnit, int index){
        this.sonUnit.set(index, EntityUnit);
        return this;
    }

    // region 设置纹理
    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        this.SPRITE.setTexture(texture);
        return this;
    }

    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(String textureRoute) {
        if (textureRoute != null) {
            this.SPRITE.setTexture(new Texture(textureRoute));
            return this;
        }
        this.SPRITE.setTexture(emptyTexture);
        return this;
    }

    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(Skin skin, String textureName){
        try {
            this.SPRITE.setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            this.SPRITE.setTexture(emptyTexture);
        }
        return this;
    }
    // endregion

    /** 设置单位大小 */
    public EntityUnit setUnitSize(float width, float height) {
        setSize(width, height);
        this.SPRITE.setSize(width, height);
        return this;
    }

//    // region 设置单位大小和碰撞箱
//    /**
//     * 设置单位大小和碰撞箱
//     *  @param unitWidth 单位宽度
//     *  @param unitHeight 单位高度
//     *  @param unitCollisionBoxWidth 单位碰撞箱宽度
//     *  @param unitCollisionBoxHeight 单位碰撞箱高度
//     */
//    public EntityUnit setUnitSize(float unitWidth, float unitHeight, float unitCollisionBoxWidth, float unitCollisionBoxHeight) {
//        setSize(unitWidth, unitHeight);
//        setUnitRectangle(unitCollisionBoxWidth, unitCollisionBoxHeight);
//        return this;
//    }
//
//    /** 设置单位碰撞箱 */
//    public EntityUnit setUnitCollisionBox(Rectangle rectangle) {
//        unitCollisionBox.set(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
//        return this;
//    }
//
//    /** 设置单位碰撞箱大小 */
//    public EntityUnit setUnitRectangle(float width, float height) {
//        unitCollisionBox.set(getX(), getY(), width, height);
//        return this;
//    }
//
//    /** 设置单位碰撞箱大小和位置 */
//    public EntityUnit setUnitRectangle(float x, float y, float width, float height) {
//        unitCollisionBox.set(x, y, width, height);
//        return this;
//    }
//
//    /** 设置单位碰撞箱大小和相对位置 */
//    public EntityUnit setUnitRelativeRectangle(float x, float y, float width, float height) {
//        unitCollisionBox.set(getX()+x, getY()+y, width, height);
//        return this;
//    }
//    // endregion

    // region 设置速度
    /** 设置后退速度 */
    public EntityUnit setRetreatSpeed(float speed){
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
    public EntityUnit setSideSpeed(float speed){
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

//    /** 获取deBug状态 */
//    public boolean getBugBox() {
//        return bugBox;
//    }

    /** 获取子单位 */
    public EntityUnit getSonUnit(int index){
        return sonUnit.get(index);
    }

    /** 获取子单位 */
    public Array<EntityUnit> getSonUnit() {
        return sonUnit;
    }

    /** 获取单位命名名称 */
    public String getUnitName(){
        return unitIdName;
    }

    /** 获取单位命名名称ID */
    public String getUnitNamingID(){
        return unitIdName.substring(0, unitIdName.indexOf(":"));
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

//    /** 获取单位碰撞箱 */
//    public Rectangle getUnitCollisionBox(){
//        return unitCollisionBox;
//    }

    // endregion
}
