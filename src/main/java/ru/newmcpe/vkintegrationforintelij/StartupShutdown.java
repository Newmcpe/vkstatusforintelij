package ru.newmcpe.vkintegrationforintelij;
/*
 * Created by Newmcpe
 * VK: vk.com/newmcpead
 */

import com.intellij.openapi.components.ApplicationComponent;

public class StartupShutdown implements ApplicationComponent {
    @Override
    public void initComponent() {
        VKIntelij.enableVK();
    }

    @Override
    public void disposeComponent() {
        VKIntelij.stopVK();
    }
}
