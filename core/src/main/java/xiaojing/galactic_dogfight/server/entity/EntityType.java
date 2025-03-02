package xiaojing.galactic_dogfight.server.entity;

/**
 * 实体类型
 */
//TODO
public enum EntityType {
    /**
     * 空
     */
    EMPTY("empty"),
    /**
     * 物体
     */
    OBJECT("object"),
    /**
     * 实体
     */
    ENTITY("entity"),
    /**
     * 机关
     */
    ORGAN("organ"),
    /**
     * 发射物
     */
    PROJECTILE("projectile");

    final String typeName;

    EntityType(String typeName) {
        this.typeName = typeName;
    }

    public String get() {
        return typeName;
    }
}
