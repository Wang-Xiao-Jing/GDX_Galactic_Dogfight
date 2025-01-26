package xiaojing.galactic_dogfight.server.unit;

import com.badlogic.gdx.graphics.Texture;

import static xiaojing.galactic_dogfight.Main.emptyTexture;
import static xiaojing.galactic_dogfight.server.NewId.ID;
import static xiaojing.galactic_dogfight.server.NewId.UNIT;
import static xiaojing.galactic_dogfight.server.unit.UnitType.ENTITY;

/**
 * @author 尽
 * @apiNote
 */
public class EntityUnit extends Unit{

    public EntityUnit() {
        this(UNIT, emptyTexture);
    }

    public EntityUnit(String unitIdName, Texture texture){
        this(ID, ENTITY + "." + unitIdName, texture);
    }

    public EntityUnit(String unitName){
        this(unitName, ENTITY, emptyTexture);
    }

    public EntityUnit(String unitIdName, UnitType unitType, Texture texture){
        this(ID, unitType + "." + unitIdName, texture);
    }

    public EntityUnit(String unitNamingID, String unitName, Texture texture){
        super(unitNamingID, unitName, ENTITY, texture);
    }
}
