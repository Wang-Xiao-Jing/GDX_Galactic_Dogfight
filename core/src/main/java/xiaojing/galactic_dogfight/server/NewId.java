package xiaojing.galactic_dogfight.server;

import java.util.regex.Pattern;

/**
 * 创建标识符ID名称
 *
 * @author 尽
 * @apiNote
 */
public class NewId {
    public static final String ID = "galactic_dogfight";
    public static final String ENTITY = "entity";
    public static final String DISEASE = "disease";
    private static final String ALLOW_CHARACTERS = "^[a-z0-9][a-z0-9_-]*[a-z0-9]$";

    /**
     * 判断字符串
     */
    private static boolean stringRules(String string) {
        return Pattern.matches(ALLOW_CHARACTERS, string);
    }

    /**
     * 创建entityId
     * <br/>
     * 判断名称是否符合规则
     */
    public static String newIdName(String typeName, String name) {
        if (stringRules(name) && stringRules(typeName)) {
            return ID + ":" + typeName + "." + name;
        }
        System.out.println("请输入正确的类型或名称（^[a-z0-9][a-z0-9_-]*[a-z0-9]$");
        return ID + ":" + typeName + "." + name;
//        throw new NumberFormatException("请输入正确的类型或名称（可用字符正则^[a-z0-9][a-z0-9_\\-.]*[a-z0-9]$）");
    }

}
