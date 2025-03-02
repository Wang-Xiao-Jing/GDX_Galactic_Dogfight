package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import space.earlygrey.shapedrawer.ShapeDrawer;
import xiaojing.galactic_dogfight.runtimeException.CustomRuntimeException;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;


import static com.badlogic.gdx.math.Vector2.Zero;
import static space.earlygrey.shapedrawer.JoinType.POINTY;
import static xiaojing.galactic_dogfight.Main.*;
import static xiaojing.galactic_dogfight.server.NewId.*;
import static xiaojing.galactic_dogfight.server.NewId.newUnitIdName;
import static xiaojing.galactic_dogfight.server.unit.UnitType.EMPTY;

/**
 * @author 尽
 * @apiNote 实体单位
 */
// TODO - Entity and Actor.
public class EntityUnit extends Sprite {
    private final String unitIdName;                // 单位标识符
    protected final Array<EntityUnit> sonUnit;      // 子单位
    private final Rectangle unitCollisionBox;       // 单位碰撞箱
    protected final UnitType unitType;              // 单位类型
    protected final Array<UnitTag> unitTag;         // 单位标签
    private final Vector2 position;                 // 单位位置
    protected final Vector2 currentSpeed;           // 单位当前速度
    protected float speed;                          // 单位移动速度
    protected float sideSpeed;                      // 单位侧向 （左右）移动速度
    protected float retreatSpeed;                   // 单位后退移动速度 这个是倍率
    protected float rotationalSpeed;                // 单位旋转移动速度

    protected transient ShapeDrawer shapeDrawer;         // 绘制器
    protected transient Color BoundsColor = Color.WHITE; // 矩形框颜色
    protected transient boolean bugBox;                  // 是否显示矩形框

    // region 构造函数

    public EntityUnit(EntityUnit entityunit) {
        this(entityunit.getUnitName(),
            entityunit.getTexture(),
            entityunit.getUnitType(),
            entityunit.getSpeed(),
            entityunit.getUnitPosition(),
            entityunit.getRetreatSpeed(),
            entityunit.getRotationalSpeed(),
            entityunit.getSideSpeed(),
            entityunit.getWidth(),
            entityunit.getHeight(),
            entityunit.getUnitCollisionBox().width,
            entityunit.getUnitCollisionBox().height
        );
    }

    /**
     * 构造函数
     */
    public EntityUnit(String unitIdName, Texture texture, UnitType unitType,
                      float speed, Vector2 position,
                      float retreatSpeed, float rotationalSpeed, float sideSpeed,
                      float width, float height, float rectangleWidth, float rectangleHeight) {
        super(texture);
        this.sonUnit = new Array<>();
        this.unitCollisionBox = new Rectangle();
        this.unitTag = new Array<>();
        this.position = new Vector2();
        this.currentSpeed = new Vector2();
        this.unitIdName = unitIdName;
        this.unitType = unitType;
        this.speed = speed;
        setUnitPosition(position);
        this.retreatSpeed = retreatSpeed;
        this.rotationalSpeed = rotationalSpeed;
        this.sideSpeed = sideSpeed;
        setUnitSize(width, height);
        shapeDrawer = new ShapeDrawer(gameSpriteBatch, new TextureRegion(pixelTexture));
        setUnitRectangle(rectangleWidth, rectangleHeight);
        setOriginCenter();
    }
    // endregion

    // region 方法
    public void overlaps(Rectangle r){
        if (r == null) return;
        if (!unitCollisionBox.overlaps(r)) return;
        float centerX = r.x + r.width / 2;
        float centerY = r.y + r.height / 2;
        if(centerX < unitCollisionBox.x){
            translateUnitX(unitCollisionBox.x - centerX);
        }

        if(centerX > unitCollisionBox.x+unitCollisionBox.width){
            translateUnitX(unitCollisionBox.x + unitCollisionBox.width - centerX);
        }

        if(centerY < unitCollisionBox.y){
            translateUnitY(unitCollisionBox.y - centerY);
        }

        if(centerY > unitCollisionBox.y+unitCollisionBox.height){
            translateUnitY(unitCollisionBox.y + unitCollisionBox.height - centerY);
        }
    }
    /** 绘制调试盒 */
    public void drawDebug() {
        drawDebugBounds(BoundsColor);
    }

    /** 绘制调试盒 */
    protected void drawDebugBounds(Color color) {
        if (!bugBox) return;
        shapeDrawer.rectangle(unitCollisionBox.x - 0.25f, unitCollisionBox.y - 0.25f, unitCollisionBox.getWidth() + 0.25f, unitCollisionBox.getHeight() + 0.25f, color, 0.5f);
    }

    /** 绘制 */
    @Override
    public void draw(Batch batch) {
        drawDebug();
        super.draw(batch);
    }

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
        this.position.set(position);
        setPosition(position.x, position.y);
        this.unitCollisionBox.setPosition(position.x, position.y);
        return this;
    }

    /** 移动单位位置 */
    public EntityUnit translateUnitPosition(float x, float y){
        this.position.add(x, y);
        translate(x, y);
        this.unitCollisionBox.setPosition(unitCollisionBox.x + x, unitCollisionBox.y + y);
        return this;
    }

    /** 设置单位X轴 */
    public EntityUnit setUnitX(float x){
        this.position.x = x;
        setX(x);
        this.unitCollisionBox.setX(x);
        return this;
    }

    /** 移动单位X轴 */
    public EntityUnit translateUnitX(float x){
        this.position.x += x;
        translateX(x);
        this.unitCollisionBox.setX(unitCollisionBox.x+x);
        return this;
    }

    /** 设置单位Y轴 */
    public EntityUnit setUnitY(float y){
        this.position.y = y;
        setY(y);
        this.unitCollisionBox.setY(y);
        return this;
    }

    /** 移动单位Y轴 */
    public EntityUnit translateUnitY(float y){
        this.position.y += y;
        translateY(y);
        this.unitCollisionBox.setY(unitCollisionBox.y+y);
        return this;
    }
    // endregion

    // region 设置
    /** 设置调试盒颜色 */
    protected void setBoundsColor(Color color){
        BoundsColor = color;
    }

    /** 设置deBug状态 */
    public EntityUnit setDeBug(boolean enabled){
        bugBox = enabled;
        return this;
    }

    /** 设置deBug状态 */
    public EntityUnit setAllDeBug(boolean enabled){
        for (int i = 0;i < sonUnit.size; i++){
            sonUnit.get(i).setDeBug(enabled);
        }
        bugBox = enabled;
        return this;
    }

    /** 设置deBug状态 */
    public EntityUnit debug() {
        setDeBug(true);
        return this;
    }

    /** 设置子单位 */
    public EntityUnit setSonUnit(Array<EntityUnit> sonUnit) {
        this.sonUnit.items = sonUnit.items;
        return this;
    }

    /** 设置子单位 */
    public EntityUnit setSonUnit(EntityUnit EntityUnit, int index){
        sonUnit.set(index, EntityUnit);
        return this;
    }

    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(Texture texture) {
        if (texture == null) texture = emptyTexture;
        setTexture(texture);
        return this;
    }

    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(String textureRoute) {
        if (textureRoute != null) {
            setTexture(new Texture(textureRoute));
            return this;
        }
        setTexture(emptyTexture);
        return this;
    }

    /** 设置单位纹理 */
    public EntityUnit setUnitTexture(Skin skin, String textureName){
        try {
            setTexture(skin.get(textureName, Texture.class));
        } catch (Exception _) {
            setTexture(emptyTexture);
        }
        return this;
    }

    /** 设置单位大小 */
    public EntityUnit setUnitSize(float width, float height) {
        setSize(width, height);
        setUnitRectangle(width, height);
        return this;
    }

    /**
     * 设置单位大小和碰撞箱
     *  @param unitWidth 单位宽度
     *  @param unitHeight 单位高度
     *  @param unitCollisionBoxWidth 单位碰撞箱宽度
     *  @param unitCollisionBoxHeight 单位碰撞箱高度
     */
    public EntityUnit setUnitSize(float unitWidth, float unitHeight, float unitCollisionBoxWidth, float unitCollisionBoxHeight) {
        setSize(unitWidth, unitHeight);
        setUnitRectangle(unitCollisionBoxWidth, unitCollisionBoxHeight);
        return this;
    }

    /** 设置单位碰撞箱 */
    public EntityUnit setUnitCollisionBox(Rectangle rectangle) {
        unitCollisionBox.set(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        return this;
    }

    /** 设置单位碰撞箱大小 */
    public EntityUnit setUnitRectangle(float width, float height) {
        unitCollisionBox.set(getX(), getY(), width, height);
        return this;
    }

    /** 设置单位碰撞箱大小和位置 */
    public EntityUnit setUnitRectangle(float x, float y, float width, float height) {
        unitCollisionBox.set(x, y, width, height);
        return this;
    }

    /** 设置单位碰撞箱大小和相对位置 */
    public EntityUnit setUnitRelativeRectangle(float x, float y, float width, float height) {
        unitCollisionBox.set(getX()+x, getY()+y, width, height);
        return this;
    }

    /** 设置后退速度 */
    public EntityUnit setRetreatSpeed(float speed){
        try {
            if (speed < 0 || speed > 1){
                // TODO
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

    /** 获取单位移动速度 */
    public float getSpeed(){
        return speed;
    }

    /** 获取单位碰撞箱 */
    public Rectangle getUnitCollisionBox(){
        return unitCollisionBox;
    }

    // endregion


    /**
     * @apiNote {@link EntityUnit} 构建器
     * @author 尽
     */
    public static class EntityUnitBuilder {
        private String unitIdName = newUnitIdName();
        private Texture texture = emptyTexture;
        private UnitType unitType = EMPTY;
        private float speed = 1f;
        private Vector2 position = Zero;
        private float retreatSpeed = 0.5f;
        private float rotationalSpeed = 0.3f;
        private float sideSpeed = 0f;
        private float width = 32f;
        private float height = 32f;
        private float rectangleWidth;
        private float rectangleHeight;

        private boolean isSetRectangleWidth;
        private boolean isSetRectangleHeight;

        /** 设置单位命名标识符 */
        public EntityUnitBuilder unitIdName(String unitIdName) {
            this.unitIdName = newUnitIdName(unitIdName);
            return this;
        }


        /** 设置单位命名名称ID */
        public EntityUnitBuilder unitIdName(String unitId, String unitName) {
            this.unitIdName = newUnitIdName(unitId, unitName);
            return this;
        }

        /** 设置单位纹理 */
        public EntityUnitBuilder texture(Texture texture) {
            this.texture = texture;
            return this;
        }

        /** 设置单位类型 */
        public EntityUnitBuilder unitType(UnitType unitType) {
            this.unitType = unitType;
            return this;
        }

        /** 设置单位速度 */
        public EntityUnitBuilder speed(float speed) {
            this.speed = speed;
            return this;
        }

        /** 设置单位位置向量 */
        public EntityUnitBuilder position(Vector2 position) {
            this.position = position;
            return this;
        }

        /** 设置单位后退速度 */
        public EntityUnitBuilder retreatSpeed(float retreatSpeed) {
            this.retreatSpeed = retreatSpeed;
            return this;
        }

        /** 设置单位旋转速度 */
        public EntityUnitBuilder rotationalSpeed(float rotationalSpeed) {
            this.rotationalSpeed = rotationalSpeed;
            return this;
        }

        /** 设置单位侧向速度 */
        public EntityUnitBuilder sideSpeed(float sideSpeed) {
            this.sideSpeed = sideSpeed;
            return this;
        }

        /** 设置单位宽度 */
        public EntityUnitBuilder width(float width) {
            this.width = width;
            return this;
        }

        /** 设置单位高度 */
        public EntityUnitBuilder height(float height) {
            this.height = height;
            return this;
        }

        /** 设置单位碰撞箱宽度 */
        public EntityUnitBuilder rectangleWidth(float rectangleWidth) {
            this.rectangleWidth = rectangleWidth;
            isSetRectangleWidth = true;
            return this;
        }

        /** 设置单位碰撞箱高度 */
        public EntityUnitBuilder rectangleHeight(float rectangleHeight) {
            this.rectangleHeight = rectangleHeight;
            isSetRectangleHeight = true;
            return this;
        }

        /** 构建单位 */
        public EntityUnit build() {
            if (!isSetRectangleHeight) {
                rectangleHeight = height;
            }
            if (!isSetRectangleWidth) {
                rectangleWidth = width;
            }
            return new EntityUnit(unitIdName, texture, unitType, speed, position, retreatSpeed, rotationalSpeed, sideSpeed, width, height, rectangleWidth, rectangleHeight);
        }
    }
}
