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

    /** 默认单位ID */
    public static String newUnitIdName(){
        return ID + ":" + UNIT;
    }

    /**
     * 创建unitId
     * <br/>
     * 判断名称是否符合规则
     */
    public static String newUnitIdName(String unitName){
        if (stringRules(unitName)){
            return ID + ":" + unitName;
        }
        return newUnitIdName();
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
