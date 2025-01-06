package xiaojing.galactic_dogfight.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import xiaojing.galactic_dogfight.Main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Launches the desktop (LWJGL3) application. */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // 设置控制台输出编码为 UTF-8
        System.setOut((new PrintStream(System.out, true, StandardCharsets.UTF_8)));
        if (StartupHelper.startNewJvmIfRequired()) return; // 这将处理macOS支持并在Windows上提供帮助。
        createApplication();
    }

    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        configuration.setTitle("Galactic Dogfight");
        //// Vsync将每秒帧数限制在硬件可以显示的范围内，并有助于消除
        //// 屏幕撕裂。此设置在Linux上并不总是有效，因此后面的行是一种保护措施。
//        configuration.useVsync(true);
        //// 将FPS限制为当前活动监视器的刷新率，加1以尝试匹配分数
        /// 刷新率。上述Vsync设置应限制实际FPS以匹配监视器。
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        //// 如果你删除上面的行并将Vsync设置为false，你可以获得无限的FPS，这可以是
        //// 对于测试性能很有用，但对某些硬件来说也可能非常有压力。
        //// 您可能还需要配置GPU驱动程序以完全禁用Vsync；这可能会导致屏幕撕裂。
        configuration.setWindowedMode(1920, 1080);
        //// 您可以更改这些文件；它们位于lwjgl3/src/main/resources/中。
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
