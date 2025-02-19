package utils.config;

import org.yaml.snakeyaml.Yaml;

import java.io.InputStream;

public class ConfigReader {

    private static final String DEFAULT_CONFIG = "/project-config.yml";

    private static ProjectConfig projectConfig;

    /**
     * Загрузка настроек из project-config.yml в один раз при первом вызове
     */
    public static ProjectConfig getConfig() {
        if (projectConfig == null) {
            projectConfig = loadConfig();
        }
        return projectConfig;
    }

    private static ProjectConfig loadConfig() {
        Yaml yaml = new Yaml();
        // Ищем файл в ресурсах
        try (InputStream inputStream = ConfigReader.class.getResourceAsStream(DEFAULT_CONFIG)) {
            if (inputStream == null) {
                throw new RuntimeException("Не найден файл " + DEFAULT_CONFIG);
            }
            // Загружаем YAML в Java-объект ProjectConfig
            return yaml.loadAs(inputStream, ProjectConfig.class);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка загрузки YAML-конфига: " + e.getMessage(), e);
        }
    }
}
