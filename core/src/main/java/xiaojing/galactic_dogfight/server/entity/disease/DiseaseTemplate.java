package xiaojing.galactic_dogfight.server.entity.disease;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import xiaojing.galactic_dogfight.core.ResourceID;

/**
 * 疾病模板
 * @apiNote 等级、命名ID、持续时间
 */
public abstract class DiseaseTemplate {
    private final String name; // 疾病命名标识ID
    private final Image image;       // 疾病图标
    private int level;         // 疾病等级
    private float duration;    // 剩余时间
    private boolean isInitialize = false;

    public DiseaseTemplate(String diseaseName, Image image) {
        this.name = ResourceID.of(diseaseName).toString();
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }

    public void render(float delta) {
        execution(delta);
        duration -= delta;
    }

    /**
     * 疾病执行
     */
    public abstract void execution(float delta);

    /**
     * 刚获得时执行
     */
    public void initialize() {
        isInitialize = true;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof DiseaseTemplate disease) {
            return disease.getName().equals(this.getName());
        } else {
            System.out.println("你要不看看你给的是啥x");
        }
        return false;
    }

    public boolean isInitialize() {
        return isInitialize;
    }
}
