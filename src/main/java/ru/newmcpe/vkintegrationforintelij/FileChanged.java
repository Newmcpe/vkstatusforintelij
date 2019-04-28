/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */

package ru.newmcpe.vkintegrationforintelij;

import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.fileEditor.FileEditorManagerEvent;
import com.intellij.openapi.fileEditor.FileEditorManagerListener;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

public class FileChanged implements FileEditorManagerListener {
    @Override
    public void selectionChanged(@NotNull FileEditorManagerEvent e) {
        if (e.getNewFile() != null) {
            VirtualFile file = e.getNewFile();
            String version = ApplicationInfo.getInstance().getVersionName() + " " + ApplicationInfo.getInstance().getFullVersion();
            version = version.replace(" ", "+");
            StringBuilder status = new StringBuilder("Пишет код в Intelij IDEA. ");
            status
                    .append("Работает над " + e.getManager().getProject().getName() + ". ")
                    .append("Редактирует " + file.getName() + ". ")
                    .append("Версия: " + version);
            VKIntelij.setStatus(status.toString(), e.getManager().getProject());
        } else {
            new ProjectChanged().runActivity(e.getManager().getProject());
        }
    }
}
