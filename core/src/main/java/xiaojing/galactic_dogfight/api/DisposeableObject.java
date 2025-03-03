package xiaojing.galactic_dogfight.api;

import com.badlogic.gdx.utils.Disposable;

import java.util.function.Supplier;

/**
 * @see Disposable
 * @param <T> The something should dispose, and it's disposeable.
 */
public abstract class DisposeableObject<T> implements Disposable, Supplier<T> {
    private final T obj;

    public DisposeableObject(T obj) {
        this.obj = obj;
    }

    public T get() {
        return this.obj;
    }

    abstract protected void dispose(T obj);

    @Override
    public void dispose() {
        this.dispose(obj);
    }
}
