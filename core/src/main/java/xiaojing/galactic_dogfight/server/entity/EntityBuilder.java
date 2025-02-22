package xiaojing.galactic_dogfight.server.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import xiaojing.galactic_dogfight.server.NewId;

import static xiaojing.galactic_dogfight.Main.emptyTexture;
import static xiaojing.galactic_dogfight.server.NewId.newEntityIdName;
import static xiaojing.galactic_dogfight.server.entity.EntityType.EMPTY;

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
    protected float collisionBoxWidth;
    protected float collisionBoxHeight;

    protected boolean isCollisionBoxWidth;
    protected boolean isCollisionBoxHeight;

    protected float reductionRatio = 0.1F;

    /** 设置实体命名标识符 */
    public EntityBuilder entityIdName(String entityIdName) {
        this.entityIdName = NewId.newEntityIdName(entityIdName);
        return this;
    }

    /** 设置减速比 */
    public EntityBuilder reductionRatio(float ratio) {
        this.reductionRatio = ratio;
        return this;
    }

    /** 设置实体命名名称ID */
    public EntityBuilder entityIdName(String entityId, String entityName) {
        this.entityIdName = newEntityIdName(entityId, entityName);
        return this;
    }

    /** 设置实体纹理 */
    public EntityBuilder texture(Texture texture) {
        this.texture = texture;
        return this;
    }

    /** 设置实体类型 {@link EntityType} */
    public EntityBuilder entityType(EntityType entityType) {
        this.entityType = entityType;
        return this;
    }

    /** 设置实体速度 */
    public EntityBuilder speed(float speed) {
        this.speed = speed;
        return this;
    }

    /** 设置实体位置 */
    public EntityBuilder position(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    /** 设置实体后退速度 */
    public EntityBuilder retreatSpeed(float retreatSpeed) {
        this.retreatSpeed = retreatSpeed;
        return this;
    }

    /** 设置实体旋转速度 */
    public EntityBuilder rotationalSpeed(float rotationalSpeed) {
        this.rotationalSpeed = rotationalSpeed;
        return this;
    }

    /** 设置实体侧向速度 */
    public EntityBuilder sideSpeed(float sideSpeed) {
        this.sideSpeed = sideSpeed;
        return this;
    }

    /** 设置实体宽度 */
    public EntityBuilder width(float width) {
        this.width = width;
        return this;
    }

    /** 设置实体高度 */
    public EntityBuilder height(float height) {
        this.height = height;
        return this;
    }

    /** 设置实体碰撞箱宽度 */
    public EntityBuilder rectangleWidth(float rectangleWidth) {
        this.collisionBoxWidth = rectangleWidth;
        isCollisionBoxWidth = true;
        return this;
    }

    /** 设置实体碰撞箱高度 */
    public EntityBuilder rectangleHeight(float rectangleHeight) {
        this.collisionBoxHeight = rectangleHeight;
        isCollisionBoxHeight = true;
        return this;
    }

    /** 构建实体 */
    public EntityBuilder build() {
        if (!isCollisionBoxHeight) {
            collisionBoxHeight = height;
        }
        if (!isCollisionBoxWidth) {
            collisionBoxWidth = width;
        }
        return this;
    }
}
