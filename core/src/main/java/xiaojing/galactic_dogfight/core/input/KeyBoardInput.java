package xiaojing.galactic_dogfight.core.input;

import com.badlogic.gdx.Input;
import com.google.common.collect.Sets;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.LinkedHashSet;

public final class KeyBoardInput {
    private KeyBoardInput() {}

    private static LinkedHashSet<Input.Keys> INPUT_KEYS = Sets.newLinkedHashSet();

    /**
     * 检查按键是否被按下。
     * @param key 目标按键。
     * @return 如果被按下则返回 true。
     */
    public static boolean isKeyDown(Input.Keys key) {
        return INPUT_KEYS.contains(key);
    }

    /**
     * 对于一套具有冲突性的按键可以提取最后被按下的成员。
     * @param keys 一组冲突的按键组合，如左（A）和右（D）。
     * @return 返回组合中最后被按下的按键。
     */
    @Nullable
    public static Input.Keys findFirstInputAt(Input.Keys[] keys) {
        for (Input.Keys inputKey : INPUT_KEYS) {
            if (Arrays.stream(keys).anyMatch(it -> it == inputKey)) {
                return inputKey;
            }
        }

        return null;
    }

    public static Input.Keys keyDown(Input.Keys key) {
        INPUT_KEYS.add(key);
        return key;
    }

    public static Input.Keys keyUp(Input.Keys key) {
        INPUT_KEYS.remove(key);
        return key;
    }

    public static Input.Keys[] getKeys() {
        return INPUT_KEYS.toArray(new Input.Keys[0]);
    }
}
