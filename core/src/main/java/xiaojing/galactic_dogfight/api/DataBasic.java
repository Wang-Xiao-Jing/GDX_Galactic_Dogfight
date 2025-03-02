package xiaojing.galactic_dogfight.api;

import com.badlogic.gdx.utils.ObjectSet;
import com.github.czyzby.kiwi.util.gdx.collection.GdxSets;

public class DataBasic {
    private final ObjectSet<IDisposeable> disposeables = GdxSets.newSet();

    public void addTo(IDisposeable disposeable) {
        this.disposeables.add(disposeable);
    }

    public void disposeAll() {
        this.disposeables.forEach(IDisposeable::dispose);
    }
}
