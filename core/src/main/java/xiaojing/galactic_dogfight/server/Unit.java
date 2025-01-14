package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;


import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.EMPTY_TEXTURE;
import static xiaojing.galactic_dogfight.server.NewId.*;

/**
 * @author 尽
 * @apiNote 单位类
 */
public class Unit extends Sprite {
    private final String unitIdName;        // 单位ID名
    private Array<Unit> sonUnit;            // 子单位
    private Rectangle unitCollisionBox;     // 单位碰撞箱
    private final UnitType unitType;        // 单位类型
    private final Array<UnitTag> unitTag;   // 单位标签
    private boolean bugBox;                 // 是否显示矩形框
    private final Vector2 position;         // 单位位置
    final Vector2 speed;                    // 单位速度

    // region 构造函数
    /** 默认构造函数 */
    public Unit() {
        this(UNIT, UnitType.EMPTY, EMPTY_TEXTURE);
    }

    /** 构造单位函数 */
    public Unit(String unitIdName, UnitType unitType, Texture texture) {
        this(ID, unitIdName, unitType, texture);
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
        if (texture == null) texture = EMPTY_TEXTURE;
        setTexture(texture);
    }

    /** 设置单位纹理 */
    public void setUnitTexture(String textureRoute) {
        if (textureRoute != null) {
            setTexture(new Texture(textureRoute));
            return;
        }
        setTexture(EMPTY_TEXTURE);
    }

    /** 设置单位纹理 */
    public void setUnitTexture(Skin skin, String textureName){
        try {
            setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            setTexture(EMPTY_TEXTURE);
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
    // endregion
}
