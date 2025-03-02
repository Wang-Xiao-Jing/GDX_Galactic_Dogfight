package xiaojing.galactic_dogfight.server;

import java.util.regex.Pattern;

/**
 * @author 尽
 * @apiNote  创建单位ID类
 */
public class NewId {
    public static final String ID = "galactic_dogfight";
    private static final String ALLOW_CHARACTERS = "[^(a-z0-9_\\-.)]";
    public static final String UNIT = "unit";

    /** 判断字符串 */
    private static boolean stringRules(String string) {
        return Pattern.matches(ALLOW_CHARACTERS, string);
    }

    /** 默认单位ID */
    public static String newUnitIdName(){
        return ID + ":" + UNIT;
    /** 默认实体ID */
    public static String newEntityIdName(){
        return ID + ":" + ENTITY;
    }

    /**
     * 创建entityId
     * <br/>
     * 判断名称是否符合规则
     */
    public static String newUnitIdName(String unitName){
        return stringRules(unitName) ? ID + ":" + unitName : newUnitIdName();
    public static String newEntityIdName(String entityName){
        if (stringRules(entityName)){
            return ID + ":" + entityName;
        }
        return newEntityIdName();
    }

    /** 判断字符串 */
    private static boolean stringRules(String string) {
        return Pattern.matches(ALLOW_CHARACTERS, string);
    }

    /**
     * 创建unitId
     * <br/>
     * 判断名称和命名名称ID是否符合规则
     */
    // TODO
    public static String newUnitIdName(String unitNamingID, String unitName){
        if (!stringRules(unitNamingID)) {
            unitNamingID = "mod";
            System.out.println("所属Id错误");
        }
        if (!stringRules(unitName)) {
            unitName = UNIT + "Name";
            System.out.println("单位名称格式错误");
        }
        return unitNamingID + ":" + unitName;
    }
}
