package xiaojing.galactic_dogfight.core.register;

import com.badlogic.gdx.utils.Disposable;
import com.google.common.base.Suppliers;
import com.google.common.collect.ImmutableSet;
import org.jetbrains.annotations.Nullable;
import xiaojing.galactic_dogfight.core.ResourceID;

import javax.annotation.CheckForNull;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Supplier;

public final class RegistryTable<T extends Disposable> {
    public final ResourceID tableName;
    private ImmutableSet<RegistryObject<T>> table;

    @CheckForNull
    private ImmutableSet.Builder<RegistryObject<T>> registerList = new ImmutableSet.Builder<>();

    // TODO
    public RegistryTable(String name) {
        this.tableName = ResourceID.of(name);
    }

    public RegistryTable(ResourceID name) {
        this.tableName = name;
    }

    @Nullable
    public RegistryObject<T> find(ResourceID id) {
        return Objects.isNull(registerList)
            ? table.stream().filter(it -> it.getRegisterName().equals(id)).findFirst().get()
            : null;
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
        Optional.ofNullable(registerList).ifPresent( it -> {
            this.table = it.build();
            this.registerList = null;
        });
    }

    void disposeAll() {
        Optional.ofNullable(table).ifPresent(it -> it.forEach(RegistryObject::dispose));
    }
}
