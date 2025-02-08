package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import static com.badlogic.gdx.math.Vector2.Zero;
import static xiaojing.galactic_dogfight.Main.emptyTexture;
import static xiaojing.galactic_dogfight.server.NewId.newUnitIdName;
import static xiaojing.galactic_dogfight.server.unit.UnitType.EMPTY;

/**
 * @apiNote {@link EntityUnit} 构建器
 * @author 尽
 */
public class EntityUnitBuilder {
    protected String unitIdName = newUnitIdName();
    protected Texture texture = emptyTexture;
    protected UnitType unitType = EMPTY;
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

    protected boolean isSetRectangleWidth;
    protected boolean isSetRectangleHeight;

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

    /** 设置单位位置 */
    public EntityUnitBuilder position(float x, float y) {
        this.x = x;
        this.y = y;
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
    public EntityUnitBuilder build() {
        if (!isSetRectangleHeight) {
            rectangleHeight = height;
        }
        if (!isSetRectangleWidth) {
            rectangleWidth = width;
        }
        return this;
    }
}
