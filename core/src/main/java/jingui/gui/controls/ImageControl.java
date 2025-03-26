package jingui.gui.controls;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import xiaojing.galactic_dogfight.StaticClass;

public class ImageControl extends Control{
    private Texture texture;
    private float textureX, textureY;

    /**构造函数*/
    public ImageControl(String name, String type) {
        super(name, type);
        texture = StaticClass.EMPTY_TEXTURE;
        setSize(texture.getWidth(), texture.getHeight());
    }
    public ImageControl(String name){
        this(name, "ImageControl");
    }

    public ImageControl(){
        this("ImageControl");
    }

    public Texture getTexture() {
        return texture;
    }

    public ImageControl setTexture(Texture texture) {
        this.texture = texture == null ? StaticClass.EMPTY_TEXTURE : texture;
        return this;
    }

    /** 获取图片的X轴坐标*/
    public float getTextureX() {
        return textureX;
    }

    /** 设置图片的X轴坐标*/
    public ImageControl setTextureX(float textureX) {
        this.textureX = textureX;
        return this;
    }

    /** 设置图片的X轴坐标*/
    public float getTextureY() {
        return textureY;
    }

    /** 设置图片的Y轴坐标*/
    public ImageControl setTextureY(float textureY) {
        this.textureY = textureY;
        return this;
    }

    public ImageControl setTexture(Texture texture, float textureX, float textureY) {
        this.texture = texture == null ? StaticClass.EMPTY_TEXTURE : texture;
        this.textureX = textureX;
        this.textureY = textureY;
        return this;
    }

    public ImageControl setTexture(float textureX, float textureY) {
        this.textureX = textureX;
        this.textureY = textureY;
        return this;
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        Color color = getColor();
        batch.setColor(color.r, color.g, color.b, color.a * parentAlpha);
        batch.draw(texture,
            getWorldX() + textureX + getWidth() / 2 * getScalingX(),
            getWorldY() + textureY + getHeight() / 2 * getScalingY());
        super.draw(batch, parentAlpha);
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        texture.dispose();
        super.dispose();
    }
}
