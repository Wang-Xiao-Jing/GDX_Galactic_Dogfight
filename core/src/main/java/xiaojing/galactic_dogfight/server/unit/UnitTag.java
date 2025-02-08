package xiaojing.galactic_dogfight.server.unit;

/**
 * 单位TAG
 */
//TODO
public enum UnitTag {
    /** 队伍 */
    TEAM            ("team"),
    /** 玩家 */
    PLAYER          ("player"),
    /** 玩家单位 */
    PLAYER_UNITS    ("player_units"),
    /** 中立单位 */
    NEUTRALITY_UNITS("neutrality_units"),
    /** 敌人单位 */
    ENEMY_UNITS     ("enemy_units"),
    /** 建筑物 */
    BUILDING        ("building");

    final String unitTag;

    UnitTag(String unitTag) {
       this.unitTag = unitTag;
    }

    public String get() {
        return unitTag;
    }
}
