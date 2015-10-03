package net.gegy1000.modcrafter.common.proxy;

import cpw.mods.fml.common.FMLCommonHandler;

import java.io.File;

public class CommonProxy
{
    public void preInit()
    {

    }

    public void init()
    {

    }

    public void postInit()
    {

    }

    public File getModsFile()
    {
        return new File(FMLCommonHandler.instance().getSavesDirectory().getParentFile(), "mods");
    }
}
