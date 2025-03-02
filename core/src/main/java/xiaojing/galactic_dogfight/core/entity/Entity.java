package xiaojing.galactic_dogfight.core.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import xiaojing.galactic_dogfight.api.IDisposeable;

/**
 * Entity 是生物实体的记录类，通常具备唯一性。Entity 的数据不应该提前释放，除非生物列表中已经不存在任何相关实体。
 *
 * @see LivingEntity
 */
public abstract class Entity implements IDisposeable {
    abstract public LivingEntity asLivingEntity();
    abstract public String getDisplayName();

    abstract public Texture getTexture();

    // TODO
    public void render(Batch batch, LivingEntity entity, Vector2 pos) {
        batch.draw(getTexture(), pos.x, pos.y);
    }
}
