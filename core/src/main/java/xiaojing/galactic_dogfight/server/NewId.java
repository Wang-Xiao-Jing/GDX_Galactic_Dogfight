package xiaojing.galactic_dogfight.server;

import java.util.regex.Pattern;

/**
 * @author 尽
 * @apiNote  创建单位ID类
 */
public class NewId {
    public static final String ID = "galactic_dogfight";
    private static final String ALLOW_CHARACTERS = "[^(a-z0-9_\\-.)]";
    public static final String ENTITY = "entity";

    /** 默认单位ID */
    public static String newEntityIdName(){
        return ID + ":" + ENTITY;
    }

    /**
     * 创建entityId
     * <br/>
     * 判断名称是否符合规则
     */
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
     * 创建entityId
     * <br/>
     * 判断名称和命名名称ID是否符合规则
     */
    // TODO
    public static String newEntityIdName(String entityNamingID, String entityName){
        if (!stringRules(entityNamingID)) {
            entityNamingID = "mod";
            System.out.println("所属Id错误");
        }
        if (!stringRules(entityName)) {
            entityName = ENTITY + "Name";
            System.out.println("单位名称格式错误");
        }
        return entityNamingID + ":" + entityName;
    }
}
