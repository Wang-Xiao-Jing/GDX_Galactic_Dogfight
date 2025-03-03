package xiaojing.galactic_dogfight.core.effect;

import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import xiaojing.galactic_dogfight.core.ResourceID;

import java.util.Objects;

public abstract class EffectBuff implements Disposable {
    abstract public ResourceID getName();
    abstract public boolean isTickEffect();
    abstract public boolean canEffect();

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        return ((EffectBuff) obj).getName().equals(this.getName());
    }
}
