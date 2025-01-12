package xiaojing.galactic_dogfight.server;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import static xiaojing.galactic_dogfight.Main.EMPTY_TEXTURE;

/**
 * @Author: 尽
 * @Description:
 * @name: Galactic Dogfight
 * @Date: 2025/1/13
 */
public class unit extends Sprite {
    Rectangle unitRectangle;
    UnitType type;

    public unit() {
        setUnitTexture(EMPTY_TEXTURE);
    }

    public void setUnitTexture(Texture texture) {
        setTexture(texture);
    }

    public void setUnitTexture(String textureRoute) {
        setTexture(new Texture(textureRoute));
    }

    public void setUnitTexture(Skin skin, String textureName){
        setTexture(skin.get(textureName, Texture.class));
    }

    public void setUnitSize(int width, int height) {
        setSize(width, height);
    }

    public void setUnitRectangle(Rectangle rectangle) {
        unitRectangle = rectangle;
    }

    public void setUnitRectangle(float width, float height) {
        unitRectangle.set(getX(), getY(), width, height);
    }

    public void setUnitRectangle(float x, float y, float width, float height) {
        unitRectangle.set(x, y, width, height);
    }

    public void setUnitRelativeRectangle(float x, float y, float width, float height) {
        unitRectangle.set(getX()+x, getY()+y, width, height);
    }

    /** 类型枚举 */
    public enum UnitType {
        /** 空 */
        EMPTY,
        /** 物体 */
        OBJECT,
        /** 实体 */
        ENTITY,
        /** 机关 */
        ORGAN,
        /** 发射误 */
        PROJECTILE
    }
}
