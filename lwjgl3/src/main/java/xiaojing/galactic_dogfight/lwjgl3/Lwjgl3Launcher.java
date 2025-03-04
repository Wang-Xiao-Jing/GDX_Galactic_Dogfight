package xiaojing.galactic_dogfight.lwjgl3;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import xiaojing.galactic_dogfight.Main;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/**
 * Launches the desktop (LWJGL3) application.
 */
public class Lwjgl3Launcher {
    public static void main(String[] args) {
        // 设置控制台输出编码为 UTF-8
        System.setOut((new PrintStream(System.out, true, StandardCharsets.UTF_8)));
        if (StartupHelper.startNewJvmIfRequired()) return; // 这将处理macOS支持并在Windows上提供帮助。
        createApplication();
    }

    // 创建应用程序
    private static Lwjgl3Application createApplication() {
        return new Lwjgl3Application(new Main(), getDefaultConfiguration());
    }

    // 获取默认配置
    private static Lwjgl3ApplicationConfiguration getDefaultConfiguration() {
        Lwjgl3ApplicationConfiguration configuration = new Lwjgl3ApplicationConfiguration();
        // 设置标题
        configuration.setTitle("Galactic Dogfight");
        // 设置帧率 (`Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1` 为垂直同步)
        configuration.setForegroundFPS(Lwjgl3ApplicationConfiguration.getDisplayMode().refreshRate + 1);
        // 设置窗口大小
        configuration.setWindowedMode(960, 540);
        // 设置窗口大小限制
        configuration.setWindowSizeLimits(960, 540, -1, -1);
        // 设置窗口图标
        configuration.setWindowIcon("libgdx128.png", "libgdx64.png", "libgdx32.png", "libgdx16.png");
        return configuration;
    }
}
