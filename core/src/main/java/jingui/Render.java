package jingui;

import com.badlogic.gdx.utils.Disposable;

public interface Render extends Disposable {
    /** 渲染 */
    void render ();
    /** 释放 */
    void dispose ();
}
