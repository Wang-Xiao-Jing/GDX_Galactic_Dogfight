package xiaojing.galactic_dogfight.core.register;

import com.badlogic.gdx.utils.Disposable;
import xiaojing.galactic_dogfight.core.ResourceID;

import java.util.Objects;
import java.util.function.Supplier;

public record RegistryObject<T extends Disposable>(ResourceID registerName, Supplier<T> holder) implements Disposable, Supplier<T> {
    public T get() {
        return this.holder.get();
    }

    public Supplier<T> getHolder() {
        return this.holder;
    }

    public ResourceID getRegisterName() {
        return this.registerName;
    }

    @Override
    public String toString() {
        return this.registerName.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RegistryObject<?> that = (RegistryObject<?>) o;
        return Objects.equals(holder, that.holder) && Objects.equals(registerName, that.registerName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(registerName, holder);
    }

    @Override
    public void dispose() {
        this.holder.get().dispose();
    }
}
