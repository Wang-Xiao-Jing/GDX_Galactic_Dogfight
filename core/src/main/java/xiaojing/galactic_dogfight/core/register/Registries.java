package xiaojing.galactic_dogfight.core.register;

import com.google.common.collect.ImmutableSet;
import xiaojing.galactic_dogfight.core.entity.Entity;
import xiaojing.galactic_dogfight.core.item.Item;

public final class Registries {
    private Registries() {
    }



    private static final ImmutableSet<RegistryTable<?>> map;

    public static final RegistryTable<Item> ITEMS;
    public static final RegistryTable<Entity> ENTITIES;

    static {
        ImmutableSet.Builder<RegistryTable<?>> builder = new ImmutableSet.Builder<>();

        ITEMS = new RegistryTable<>("items");
        ENTITIES = new RegistryTable<>("entities");

        map = builder.build();
    }

    /* Damage Zoom */

    public static void init() {
        EntityRegister.init();
    }

    public static void frozen() {
        map.forEach(RegistryTable::frozen);
    }

    public static void dispose() {
        map.forEach(RegistryTable::disposeAll);
    }
}
