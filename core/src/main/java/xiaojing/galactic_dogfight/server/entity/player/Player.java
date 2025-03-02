package xiaojing.galactic_dogfight.server.entity.player;

import com.badlogic.gdx.utils.Array;
import xiaojing.galactic_dogfight.server.entity.disease.DiseaseTemplate;

/**
 * 玩家类
 *
 * @author xiaojing
 */
public class Player {
    private final String playerName;     // 玩家名
    private final Array<DiseaseTemplate> disease; // 疾病
    private float vitality = 100;       // 生命值
    private float maxVitality = 100;    // 最大生命值
    private final float health = 1F;          // 健康百分百比
    private float energy = 100;         // 饥饿值
    private float maxEnergy = 100;      // 最大饥饿值
    private float oxygen = 100;         // 氧气值
    private float maxOxygen = 100;      // 最大氧气值

    public Player(String playerName) {
        this.playerName = playerName;
        this.disease = new Array<>();
    }

    public Player(PlayerBuilder builder) {
        this(builder.playerName);
        this.vitality = builder.vitality;
        this.maxVitality = builder.vitality;
        this.energy = builder.hunger;
        this.maxEnergy = builder.hunger;
        this.oxygen = builder.oxygen;
        this.maxOxygen = builder.oxygen;
    }

    public Player addDisease(DiseaseTemplate disease) {
        for (int i = 0; i < this.disease.size; ++i) {
            if (this.disease.get(i).equals(disease)) {
                this.disease.get(i).setDuration(disease.getDuration());
                this.disease.get(i).setLevel(disease.getLevel());
                return this;
            }
        }
        this.disease.add(disease);
        return this;
    }

    public void removeDisease(DiseaseTemplate disease) {
        if (this.disease.removeValue(disease, false)) {
            System.out.println("疾病" + disease.getName() + "已消失");
        }
    }


    public void render(float delta) {
        for (int i = 0; i < disease.size; ++i) {
            disease.get(i).render(delta);
            if (disease.get(i).getDuration() <= 0) {
                removeDisease(disease.get(i));
                i--;
            }
        }
    }

    public static class PlayerBuilder {
        protected final String playerName;
        protected float vitality = 100F;
        protected float hunger = 1;
        protected float oxygen = 100;

        public PlayerBuilder(String playerName) {
            this.playerName = playerName;
        }

        /**
         * 生命值
         */
        public PlayerBuilder vitality(float vitality) {
            this.vitality = vitality;
            return this;
        }

        /**
         * 饥饿值
         */
        public PlayerBuilder hunger(float hunger) {
            this.hunger = hunger;
            return this;
        }

        /**
         * 氧气值
         */
        public PlayerBuilder oxygen(float oxygen) {
            this.oxygen = oxygen;
            return this;
        }

        public PlayerBuilder build() {
            return this;
        }
    }
}
