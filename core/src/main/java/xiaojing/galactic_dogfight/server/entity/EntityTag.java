package xiaojing.galactic_dogfight.server.entity;

/**
 * 实体TAG
 */
//TODO
public enum EntityTag {
    /**
     * 队伍
     */
    TEAM("team"),
    /**
     * 玩家
     */
    PLAYER("player"),
    /**
     * 玩家实体
     */
    PLAYER_UNITS("player_units"),
    /**
     * 中立实体
     */
    NEUTRALITY_UNITS("neutrality_units"),
    /**
     * 敌人实体
     */
    ENEMY_UNITS("enemy_units"),
    /**
     * 建筑物
     */
    BUILDING("building");

    final String unitTag;

    EntityTag(String unitTag) {
        this.unitTag = unitTag;
    }

    public String get() {
        return unitTag;
    }
}
