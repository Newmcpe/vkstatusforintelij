/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */

package ru.newmcpe.vkintegrationforintelij;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.project.Project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class VKIntelij {
    private static final String ENABLED_FILE = "enabled.txt";
    private static final String TOKEN_PATH = System.getProperty("user.home") + "/token.txt";
    private static final String DEFAULT_MESSAGE_PATH = System.getProperty("user.home") + "/default_message.txt";
    private static ExecutorService threadPool
            = Executors.newFixedThreadPool(10);

    public static void enableVK() {
        String version = ApplicationInfo.getInstance().getVersionName() + "+" + ApplicationInfo.getInstance().getFullVersion();
        version = version.replace(" ", "+");
        StringBuilder status = new StringBuilder("Пишет код в IntelliJ IDEA. ");
        status
                .append("Загрузка... Плагин создан https://vk.com/newmcpead ")
                .append("Версия: " + version);
        setStatus(status.toString());
    }

    public static void enableVK(Project p) {
        enableVK();
        new ProjectChanged().runActivity(p);
    }

    public static void stopVK() {
        setStatus("Loading");
    }

    //Для чекания на включенную интеграцию
    public static void setStatus(String text, Project project) {
        System.out.println("intgr called");
        try {
            VKIntelij.isEnabledIntegration(project, result -> {
                if (result) {
                    VKIntelij.getToken(token -> Utils.get("https://api.vk.com/method/status.set?text=" + text.replace(" ", "+") + "&access_token=" + token + "&v=5.67"));
                } else {
                }
            });
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    public static void setStatus(String text) {
        VKIntelij.getToken(token -> {
            Utils.get("https://api.vk.com/method/status.set?text=" + text.replace(" ", "+") + "&access_token=" + token + "&v=5.67");
        });
    }

    public static void setIntegrationEnabled(Project project, boolean enabled) {
        threadPool.submit(() -> {
            try {
                File file = new File(project.getBasePath() + "/.idea/" + ENABLED_FILE);
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        VKIntelij.setIntegrationEnabled(project, true);
                    }
                } else {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    writer.write(String.valueOf(enabled));
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void isEnabledIntegration(Project project, Consumer<Boolean> enabled) {
        threadPool.submit(() -> {
            try {
                File file = new File(project.getBasePath() + "/.idea/" + ENABLED_FILE);
                if (!file.exists()) {
                    if (file.createNewFile()) {
                        VKIntelij.setIntegrationEnabled(project, true);
                        enabled.accept(true);
                    }
                } else {
                    enabled.accept(Boolean.valueOf(Files.lines(file.toPath()).findFirst().orElse(null)));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void getToken(Consumer<String> token) {
        threadPool.submit(() -> {
            try {
                File file = new File(TOKEN_PATH);
                if (!file.exists()) {
                    file.createNewFile();
                    token.accept(null);
                } else {
                    String value = Files.lines(file.toPath()).findFirst().get();
                    System.out.println(value);
                    token.accept(value);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void setToken(String token) {
        threadPool.submit(() -> {
            try {
                File file = new File(TOKEN_PATH);
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                writer.write(token);
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
