package xiaojing.galactic_dogfight.core.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

/**
 * Entity 是生物实体的记录类，通常具备唯一性。Entity 的数据不应该提前释放，除非生物列表中已经不存在任何相关实体。
 * Entity 是可释放的，通常仅在游戏结束时被释放。
 * TODO - 方法需补入 LivingEntity
 *
 * @see LivingEntity
 */
public abstract class Entity implements Disposable {
    abstract public LivingEntity asLivingEntity();

    abstract public String getDisplayName(LivingEntity entity);
    abstract public Texture getTexture(LivingEntity entity);

    // TODO
    public void render(Batch batch, LivingEntity entity, Vector2 pos) {
        batch.draw(getTexture(entity), pos.x, pos.y);
    }
}
