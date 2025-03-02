package xiaojing.galactic_dogfight.core.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import xiaojing.galactic_dogfight.api.IDisposeable;

public abstract class Entity implements IDisposeable {
    abstract public LivingEntity asLivingEntity();
    abstract public String getDisplayName();

    abstract public Texture getTexture();

    public void render(Batch batch, LivingEntity entity, Vector2 pos) {
        batch.draw(getTexture(), pos.x, pos.y);
    }
}
