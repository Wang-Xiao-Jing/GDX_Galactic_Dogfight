package xiaojing.galactic_dogfight.core.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import xiaojing.galactic_dogfight.api.IDisposeable;

public class LivingEntity implements IDisposeable {
    public final Entity entity;
    public final Vector2 position;
    public String displayName = "";

    public LivingEntity(Entity entity) {
        this.entity = entity;
        this.position = Vector2.Zero;
    }

    public String displayName() {
        return this.displayName.isEmpty() ? this.entity.getDisplayName() : displayName;
    }

    public Vector2 getPos() {
        return this.position;
    }

    public void setPos(Vector2 pos) {
        this.position.set(pos);
    }

    public void setPos(float x, float y) {
        this.position.set(x, y);
    }

    public Texture getTexture() {
        return entity.getTexture();
    }

    public void render(Batch batch) {
        this.entity.render(batch, this, this.getPos());
    }

    @Override
    public void dispose() {
        this.entity.dispose();
    }
}
