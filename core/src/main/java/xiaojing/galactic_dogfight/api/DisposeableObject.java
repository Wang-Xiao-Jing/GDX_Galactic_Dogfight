package xiaojing.galactic_dogfight.api;

public abstract class DisposeableObject<T> implements IDisposeable {
    private final T obj;

    public DisposeableObject(T obj) {
        this.obj = obj;
    }

    abstract protected void dispose(T obj);

    @Override
    public void dispose() {
        this.dispose(obj);
    }
}
