package net.gegy1000.modcrafter.client.proxy;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterMainMenu;
import net.gegy1000.modcrafter.common.proxy.CommonProxy;
import net.ilexiconn.llibrary.client.gui.GuiHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

import java.io.File;

public class ClientProxy extends CommonProxy
{
    @Override
    public void preInit()
    {
        super.preInit();

        new File(getModsFile(), "modcrafter").mkdir();
    }

    @Override
    public void init()
    {
        super.init();
    }

    @Override
    public void postInit()
    {
        super.postInit();
        GuiHelper.addOverride(GuiMainMenu.class, new GuiModCrafterMainMenu());
    }

    @Override
    public File getModsFile()
    {
        return new File(Minecraft.getMinecraft().mcDataDir, "mods");
    }
}
