package xiaojing.galactic_dogfight.core.effect;

import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;

import java.util.Objects;

public final class InstanceEffect implements Disposable, Disableable {
    private final EffectBuff effectIn;
    private int time;
    private int amplifier;

    private boolean canEffect = true;

    public InstanceEffect(EffectBuff effectIn, int time, int amplifier) {
        this.effectIn = effectIn;
        this.time = time;
        this.amplifier = amplifier;
    }

    public InstanceEffect(EffectBuff effectIn, int time) {
        this(effectIn, time, 0);
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public boolean isCanEffect() {
        return canEffect;
    }

    public void setCanEffect(boolean canEffect) {
        this.canEffect = canEffect;
    }

    public int getAmplifier() {
        return amplifier;
    }

    public void setAmplifier(int amplifier) {
        this.amplifier = amplifier;
    }

    public EffectBuff getEffectIn() {
        return effectIn;
    }

    @Override
    public void setDisabled(boolean b) {
        this.canEffect = b;
    }

    @Override
    public boolean isDisabled() {
        return effectIn.canEffect() && this.canEffect;
    }

    @Override
    public void dispose() {
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        InstanceEffect that = (InstanceEffect) o;
        return time == that.time && amplifier == that.amplifier && canEffect == that.canEffect && Objects.equals(effectIn, that.effectIn);
    }

    @Override
    public int hashCode() {
        return Objects.hash(effectIn, time, amplifier, canEffect);
    }

    @Override
    public String toString() {
        return "InstanceEffect{" +
            "effectIn=" + effectIn +
            ", time=" + time +
            ", amplifier=" + amplifier +
            ", canEffect=" + canEffect +
            '}';
    }
}
