package jingui.gui.controls;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.utils.Disposable;

public interface Render extends Disposable {
    /** 渲染 */
    void render ();
    /** 释放 */
    @Override
    void dispose ();
    /** 绘制 */
    void draw(Batch batch, float parentAlpha);
}
