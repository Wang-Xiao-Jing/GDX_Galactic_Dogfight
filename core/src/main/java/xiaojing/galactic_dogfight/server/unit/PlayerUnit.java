package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import static xiaojing.galactic_dogfight.Main.gameAssetManager;

/**
 * @author 尽
 * @apiNote 玩家单位
 */
public class PlayerUnit extends EntityUnit {
    public final String playerName;

    public PlayerUnit() {
        super(new EntityUnitBuilder()
            .unitIdName("player")
            .texture(gameAssetManager.get("texture/sprite/player/template_player.png"))
            .height(32)
            .width(32)
            .build());
        playerName = "player";
//        debug();
        setBoundsColor(Color.GREEN);
    }
}
