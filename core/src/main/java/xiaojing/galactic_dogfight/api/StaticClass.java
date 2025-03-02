package xiaojing.galactic_dogfight.api;

import com.badlogic.gdx.graphics.Texture;

public class StaticClass {
    // 空纹理
    public final static Texture emptyTexture = new Texture("texture/empty_texture.png");
    // 通用像素染色白图
    public final static Texture pixelTexture = new Texture("texture/gui/pixel.png"); // 赋值通用像素染色白图

    public static void dispose() {
        emptyTexture.dispose();
        pixelTexture.dispose();
    }
}
