/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */
package ru.newmcpe.vkintegrationforintelij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

public class ToggleAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent a) {
        Project project = a.getProject();
        VKIntelij.isEnabledIntegration(project, result -> {
            if (result) {
                VKIntelij.setIntegrationEnabled(project, false);
                VKIntelij.stopVK();
            } else {
                VKIntelij.setIntegrationEnabled(project, true);
                VKIntelij.enableVK(a.getProject());
            }
        });
    }
}
