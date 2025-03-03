package xiaojing.galactic_dogfight.core.math;

import com.badlogic.gdx.math.Vector2;
import xiaojing.galactic_dogfight.core.entity.LivingEntity;

/**
 * AABB（Axis-Aligned Bounding Box，体积箱）用于作为扫描范围或物件碰撞箱。
 */
public class AABB {
    private final Vector2 startPoint;
    private final Vector2 endPoint;

    public AABB() {
        this.startPoint = Vector2.Zero;
        this.endPoint = Vector2.Zero;
    }

    public AABB(Vector2 start, Vector2 end) {
        this.startPoint = start;
        this.endPoint = end;
    }

    public AABB(float startX, float startB, float endA, float endB) {
        this.startPoint = new Vector2(startX, startB);
        this.endPoint = new Vector2(endA, endB);
    }

    public static AABB of(Vector2 start, float range) {
        final AABB aabb = new AABB(start.cpy(), start.cpy());
        aabb.startPoint.add(-range, range);
        aabb.endPoint.add(range, -range);
        return aabb;
    }

    public static AABB byEntity(LivingEntity entity, float range) {
        final Vector2 pos = entity.getPos().cpy();
        final AABB aabb = new AABB(pos, pos);
        aabb.startPoint.add(-range, range);
        aabb.endPoint.add(range, -range);
        return aabb;
    }

    public boolean match(Vector2 pos) {
        final boolean flagX = pos.x > this.startPoint.x && pos.x < this.endPoint.x;
        final boolean flagY = pos.y < this.startPoint.y && pos.y > this.endPoint.y;
        return flagX && flagY;
    }
}
