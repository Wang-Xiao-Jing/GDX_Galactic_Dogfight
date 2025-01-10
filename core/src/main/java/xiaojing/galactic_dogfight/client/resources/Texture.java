package xiaojing.galactic_dogfight.client.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

/**
 * @Author: 尽
 * @Description: 纹理类
 * @name: Galactic Dogfight
 * @Date: 2024/12/29
 */
public class Texture {
    // 模板
    public Skin templateRootUI= new Skin(Gdx.files.internal("ui/uiskin.json"));// 模板根UI
    public String titleTexture = "ui/title.png";
    public String backgroundTexture = "texture/screen/background.jpg";
}
