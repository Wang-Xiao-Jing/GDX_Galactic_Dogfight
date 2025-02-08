package xiaojing.galactic_dogfight.server.player;

import xiaojing.galactic_dogfight.server.unit.Entity;
import xiaojing.galactic_dogfight.server.unit.EntityUnitBuilder;

import static xiaojing.galactic_dogfight.Main.gameAssetManager;

/**
 * @author 尽
 * @apiNote 玩家单位
 */
public class Player extends Entity {
    public final String playerName;

    public Player() {
        super(new EntityUnitBuilder()
            .unitIdName("player")
            .texture(gameAssetManager.get("texture/sprite/player/template_player.png"))
            .height(32)
            .width(32)

 .speed(300)
            .build());
        playerName = "player";
//        debug();
//        setBoundsColor(Color.GREEN);
    }
}
