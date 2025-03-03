package xiaojing.galactic_dogfight.api;

import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;

import java.util.function.Consumer;

public class DataBasic {
    private final ObjectSet<Disposable> disposables = GdxSets.newSet();

    public <T extends Disposable> T addTo(T disposeable) {
        this.disposables.add(disposeable);
        return disposeable;
    }

    public <T> T addTo(T obj, Consumer<T> func) {
        this.disposables.add(new DisposeableObject<>(obj) {
            @Override
            protected void dispose(T obj) {
                func.accept(obj);
            }
        });
        return obj;
    }

    public void disposeAll() {
        this.disposables.forEach(Disposable::dispose);
    }
}
