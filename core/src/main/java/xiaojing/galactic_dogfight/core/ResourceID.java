package xiaojing.galactic_dogfight.core;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @author 尽
 * @apiNote  创建单位ID类
 */
public class ResourceID {
    public static final String ID = "galactic_dogfight";

    private static final String ALLOW_CHARACTERS = "[^(a-z0-9_\\-.)]";

    public final String id;
    public final String name;

    private ResourceID(final String ID, final String name) {
        if (!checkIllegal(ID) || !checkIllegal(name)) {
            throw new IllegalArgumentException("ResourceID's argument can only in [a-z0-9-.]!");
        }

        this.id = ID;
        this.name = name;
    }

    public static ResourceID of(final String name) {
        if (!name.contains(":")) {
            return new ResourceID(ID, name);
        }

        final String[] dats = name.split(":");
        return new ResourceID(dats[0], dats[1]);
    }

    public static ResourceID of(final String id, final String name) {
        return new ResourceID(id, name);
    }

    private static boolean checkIllegal(String string) {
        return Pattern.matches(ALLOW_CHARACTERS, string);
    }

    @Override
    public String toString() {
        return String.format("%s:%s", this.id, this.name);
    }

    @Override
    public int hashCode() {
        return this.toString().hashCode();
    }

    /**
     * Just to ask for string is equals.
     * @param obj The target, maybe another ResourceID, maybe a String.
     * @return If the data are equals each.
     */
    @Override
    public boolean equals(Object obj) {
        return Objects.equals(this.toString(), obj.toString());
    }
}
