/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */

package ru.newmcpe.vkintegrationforintelij;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.ui.Messages;

public class TokenEnterAction extends AnAction {
    @Override
    public void actionPerformed(AnActionEvent e) {
        String token = Messages.showInputDialog(e.getProject(), "Enter VK Token", "Enter VK Token to integration work", Messages.getQuestionIcon());
        VKIntelij.setToken(token);
    }
}
