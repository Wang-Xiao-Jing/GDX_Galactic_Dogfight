package xiaojing.galactic_dogfight.core.register;

import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Nullable;
import xiaojing.galactic_dogfight.server.ResourceID;

import java.util.Objects;
import java.util.function.Supplier;

public class RegistryTable<T> {
    public final ResourceID tableName;
    public ImmutableSet<RegistryObject<T>> table;

    @Nullable
    private ImmutableSet.Builder<RegistryObject<T>> registerList = new ImmutableSet.Builder<>();

    // TODO
    public RegistryTable(String name) {
        this.tableName = ResourceID.of(name);
    }

    public RegistryTable(ResourceID name) {
        this.tableName = name;
    }

    public RegistryObject<T> register(RegistryObject<T> obj) {
        Objects.requireNonNull(this.registerList, "Register table is frozen!")
            .add(obj);
        return obj;
    }

    public RegistryObject<T> register(String registerName, Supplier<T> holder) {
        final RegistryObject<T> object = new RegistryObject<>(ResourceID.of(registerName), holder);
        return this.register(object);
    }

    public RegistryObject<T> register(ResourceID registerName, T holder) {
        final RegistryObject<T> object = new RegistryObject<>(registerName, Suppliers.ofInstance(holder));
        return this.register(object);
    }

    public RegistryObject<T> register(String registerName, T holder) {
        final RegistryObject<T> object =
            new RegistryObject<>(ResourceID.of(registerName), Suppliers.ofInstance(holder));
        return this.register(object);
    }

    void frozen() {
        this.table = registerList.build();
        this.registerList = null;
    }

}
