/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */

package ru.newmcpe.vkintegrationforintelij;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import org.jetbrains.annotations.NotNull;

public class ProjectChanged implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        FileChanged hook = new FileChanged();
        String version = ApplicationInfo.getInstance().getVersionName() + "+" + ApplicationInfo.getInstance().getFullVersion();
        version = version.replace(" ", "+");
        project.getMessageBus().connect().subscribe(FileEditorManagerListener.FILE_EDITOR_MANAGER, hook);

        StringBuilder status = new StringBuilder("Пишет код в Intelij IDEA. ");
        status
                .append("Работает над " + project.getName() + ". ")
                .append("Версия: " + version);
        VKIntelij.setStatus(status.toString(), project);
    }
}
