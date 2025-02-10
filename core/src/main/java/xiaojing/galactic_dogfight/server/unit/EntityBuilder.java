package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import xiaojing.galactic_dogfight.server.NewId;

import static xiaojing.galactic_dogfight.Main.emptyTexture;
import static xiaojing.galactic_dogfight.server.NewId.newEntityIdName;
import static xiaojing.galactic_dogfight.server.unit.EntityType.EMPTY;

/**
 * @apiNote {@link Entity} 构建器
 * @author 尽
 */
public class EntityBuilder {
    protected String entityIdName = NewId.newEntityIdName();
    protected Texture texture = emptyTexture;
    protected EntityType entityType = EMPTY;
    protected float speed = 1f;
    protected float x = 0;
    protected float y = 0;
    protected float retreatSpeed = 0.5f;
    protected float rotationalSpeed = 0.3f;
    protected float sideSpeed = 0f;
    protected float width = 32f;
    protected float height = 32f;
    protected float rectangleWidth;
    protected float rectangleHeight;
    protected BodyDef bodyDef;

    protected boolean isSetRectangleWidth;
    protected boolean isSetRectangleHeight;

    /** 设置单位命名标识符 */
    public EntityBuilder entityIdName(String entityIdName) {
        this.entityIdName = NewId.newEntityIdName(entityIdName);
        return this;
    }


    /** 设置单位命名名称ID */
    public EntityBuilder entityIdName(String entityId, String entityName) {
        this.entityIdName = newEntityIdName(entityId, entityName);
        return this;
    }

    /** 设置单位纹理 */
    public EntityBuilder texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    /** 设置单位类型 */
    public EntityBuilder entityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /** 设置单位速度 */
    public EntityBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    /** 设置单位位置 */
    public EntityBuilder position(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /** 设置单位后退速度 */
    public EntityBuilder retreatSpeed(float retreatSpeed) {
        this.retreatSpeed = retreatSpeed;
        return this;
    }

    /** 设置单位旋转速度 */
    public EntityBuilder rotationalSpeed(float rotationalSpeed) {
        this.rotationalSpeed = rotationalSpeed;
        return this;
    }

    /** 设置单位侧向速度 */
    public EntityBuilder sideSpeed(float sideSpeed) {
        this.sideSpeed = sideSpeed;
        return this;
    }

    /** 设置单位宽度 */
    public EntityBuilder width(float width) {
        this.width = width;
        return this;
    }

    /** 设置单位高度 */
    public EntityBuilder height(float height) {
        this.height = height;
        return this;
    }

    /** 设置单位碰撞箱宽度 */
    public EntityBuilder rectangleWidth(float rectangleWidth) {
        this.rectangleWidth = rectangleWidth;
        isSetRectangleWidth = true;
        return this;
    }

    /** 设置单位碰撞箱高度 */
    public EntityBuilder rectangleHeight(float rectangleHeight) {
        this.rectangleHeight = rectangleHeight;
        isSetRectangleHeight = true;
        return this;
    }

    /** 构建单位 */
    public EntityBuilder build() {
        if (!isSetRectangleHeight) {
            rectangleHeight = height;
        }
        if (!isSetRectangleWidth) {
            rectangleWidth = width;
        }
        return this;
    }
}
