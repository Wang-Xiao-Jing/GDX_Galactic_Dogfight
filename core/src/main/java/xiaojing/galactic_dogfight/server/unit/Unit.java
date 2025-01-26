package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import xiaojing.galactic_dogfight.runtimeException.CustomRuntimeException;


import java.util.logging.Logger;

import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.emptyTexture;
import static xiaojing.galactic_dogfight.server.NewId.*;
import static xiaojing.galactic_dogfight.server.unit.UnitType.EMPTY;

/**
 * @author 尽
 * @apiNote 单位类
 */
public class Unit extends Sprite {
    private String unitIdName;                // 单位ID名
    protected Array<Unit> sonUnit;            // 子单位
    protected Rectangle unitCollisionBox;     // 单位碰撞箱
    protected final UnitType unitType;        // 单位类型
    protected final Array<UnitTag> unitTag;   // 单位标签
    protected boolean bugBox;                 // 是否显示矩形框
    protected final Vector2 position;         // 单位位置
    protected final Vector2 speed;            // 单位速度
    protected float sideSpeed;                // 单位侧向 （左右）移动速度
    protected float retreatSpeed;             // 单位后退移动速度 这个是倍率
    protected float rotationalSpeed;          // 单位旋转移动速度

    // region 构造函数
    /** 默认构造函数 */
    public Unit() {
        this(UNIT, EMPTY, emptyTexture);
    }

    public Unit(UnitType unitType){
        this(UNIT, unitType, emptyTexture);
    }

    /** 构造单位函数 */
    public Unit(String unitIdName, UnitType unitType, Texture texture) {
        this(ID, unitIdName, unitType, texture);
    }

    /** 构造单位函数 */
    public Unit(String unitIdName) {
        this(ID, unitIdName, EMPTY, emptyTexture);
    }

    /** 构造单位函数 */
    public Unit(String unitNamingID, String unitName, UnitType unitType, Texture texture) {
        this.unitIdName = newUnitIdName(unitNamingID, unitName);
        this.unitType = unitType;
        setTexture(texture);
        setSize(texture.getTextureData().getWidth(), texture.getTextureData().getHeight());
        position = Zero;
        speed = Zero;
        sonUnit = new Array<>();
        unitCollisionBox = new Rectangle();
        unitTag = new Array<>();
        setPosition(position.x, position.y);
    }
    // endregion

    // region 方法
    /** 绘制调试盒 */
    public void drawDebug (ShapeRenderer shapes) {
        drawDebugBounds(shapes);
    }

    /** 绘制调试盒 */
    protected void drawDebugBounds (ShapeRenderer shapes) {
        if (!bugBox) return;
        shapes.set(ShapeRenderer.ShapeType.Line);
        shapes.rect(getX(), getY(), getOriginX(), getOriginY(), getWidth(), getHeight(), getScaleX(), getScaleY(), getRotation());
    }

    /** 添加单位标签 */
    public Unit abbUnitTag(UnitTag unitTag){
        this.unitTag.add(unitTag);
        return this;
    }

    public Unit abbUnitTag(UnitTag... unitTag){
        for (UnitTag tag : unitTag) {
            this.unitTag.add(tag);
        }
        return this;
    }

    /** 设置单位位置 */
    public void setUnitPosition(Vector2 position){
        this.position.set(position);
        setPosition(position.x, position.y);
    }

    /** 设置单位位置 */
    public void setUnitPosition(float x, float y){
        this.position.set(x, y);
        setPosition(x, y);
    }

    /** 设置单位X轴 */
    public void setUnitX(float x){
        this.position.x = x;
        setX(x);
    }

    /** 设置单位Y轴 */
    public void setUnitY(float y){
        this.position.y = y;
        setY(y);
    }
    // endregion

    // region 设置
    /** 设置deBug状态 */
    public void setDeBug(boolean enabled){
        bugBox = enabled;
    }

    /** 设置deBug状态 */
    public void setAllDeBug(boolean enabled){
        for (int i = 0;i < sonUnit.size; i++){
            sonUnit.get(i).setDeBug(enabled);
        }
        bugBox = enabled;
    }

    /** 设置deBug状态 */
    public Unit debug () {
        setDeBug(true);
        return this;
    }

    /** 设置子单位 */
    public void setSonUnit(Array<Unit> sonUnit) {
        this.sonUnit = sonUnit;
    }

    /** 设置子单位 */
    public void setSonUnit(Unit unit, int index){
        sonUnit.set(index, unit);
    }

    /** 设置单位纹理 */
    public void setUnitTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        setTexture(texture);
    }

    /** 设置单位纹理 */
    public void setUnitTexture(String textureRoute) {
        if (textureRoute != null) {
            setTexture(new Texture(textureRoute));
            return;
        }
        setTexture(emptyTexture);
    }

    /** 设置单位纹理 */
    public void setUnitTexture(Skin skin, String textureName){
        try {
            setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            setTexture(emptyTexture);
        }
    }

    /** 设置单位大小 */
    public void setUnitSize(float width, float height) {
        setSize(width, height);
    }

    /**
     * 设置单位大小和碰撞箱
     *  @param unitWidth 单位宽度
     *  @param unitHeight 单位高度
     *  @param unitCollisionBoxWidth 单位碰撞箱宽度
     *  @param unitCollisionBoxHeight 单位碰撞箱高度
     */
    public void setUnitSize(float unitWidth, float unitHeight, float unitCollisionBoxWidth, float unitCollisionBoxHeight) {
        setSize(unitWidth, unitHeight);
        setUnitRectangle(unitCollisionBoxWidth, unitCollisionBoxHeight);
    }

    /** 设置单位碰撞箱 */
    public void setUnitCollisionBox(Rectangle rectangle) {
        unitCollisionBox = rectangle;
    }

    /** 设置单位碰撞箱大小 */
    public void setUnitRectangle(float width, float height) {
        unitCollisionBox.set(getX(), getY(), width, height);
    }

    /** 设置单位碰撞箱大小和位置 */
    public void setUnitRectangle(float x, float y, float width, float height) {
        unitCollisionBox.set(x, y, width, height);
    }

    /** 设置单位碰撞箱大小和相对位置 */
    public void setUnitRelativeRectangle(float x, float y, float width, float height) {
        unitCollisionBox.set(getX()+x, getY()+y, width, height);
    }

    /** 设置后退速度 */
    public void setRetreatSpeed(float speed){
        try {
            if (speed < 0 || speed > 1){
                throw new CustomRuntimeException("范围过大或者过小（范围：0~1）");
            }
        } catch (CustomRuntimeException e) {
            System.out.println("范围过大或者过小（范围：0~1）");
            return;
        }
        retreatSpeed = speed;
    }

    /** 设置侧向速度 */
    public void setSideSpeed(float speed){
        sideSpeed = speed;
    }
    // endregion

    // region 获取
    /** 获取单位类型 */
    public UnitType getUnitType() {
        return unitType;
    }

    /** 获取deBug状态 */
    public boolean getBugBox() {
        return bugBox;
    }

    /** 获取子单位 */
    public Unit getSonUnit(int index){
        return sonUnit.get(index);
    }

    /** 获取子单位 */
    public Array<Unit> getSonUnit() {
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
        return position;
    }

    /** 获取单位X轴 */
    public float getUnitX(){
        return position.x;
    }

    /** 获取单位Y轴 */
    public float getUnitY(){
        return position.y;
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
    // endregion
}
