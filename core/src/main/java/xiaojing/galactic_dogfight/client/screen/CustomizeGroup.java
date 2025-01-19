package xiaojing.galactic_dogfight.client.screen;

/**
 * @author  尽
 * @apiNote 自定义Actor接口
 */
public interface CustomizeGroup {
    void adjustSize();

    void adjustSize(float width, float height);

    /** 监听器&触发器 */
    void listener();

    /** 持续交互 */
    void interactive();

    /** 监听器&触发器 */
    void listener(final MainMenuScreen SCREEN);
}
