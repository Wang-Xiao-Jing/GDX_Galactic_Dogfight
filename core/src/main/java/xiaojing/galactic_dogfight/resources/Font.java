package xiaojing.galactic_dogfight.resources;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont.BitmapFontData;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

/**
 * @Author: 尽
 * @Description: 字体类
 * @name: Galactic Dogfight
 * @Date: 2024/12/29
 */
public class Font {
    public String customCharacters = Gdx.files.internal("fonts/customCharacters.txt").readString();// 字库
    // silver 字体
    private final Array<TextureRegion> silver = new Array<>();
    public Array<TextureRegion> initialSilver(){
        for (int i = 1;i<=4;i++){
            silver.add(new TextureRegion(new Texture("fonts/silver/silver"+i+".png")));
        }
        return silver;
    }
    public BitmapFontData silverFnt = new BitmapFontData(Gdx.files.internal("fonts/silver/silver.fnt"),false);
    public FileHandle silverTtf = new FileHandle("fonts/silver/silver.ttf");

}
