package net.gegy1000.modcrafter.common.modrun;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.ModSaveManager;

import java.util.ArrayList;
import java.util.List;

public class ModRunner
{
    private List<LoadedMod> loadedMods = new ArrayList<LoadedMod>();

    private static ModRunner instance;

    public void preInit()
    {
        List<Mod> mods = ModSaveManager.discoverMods();

        for (Mod mod : mods)
        {
            LoadedMod loadedMod = new LoadedMod(mod.getComponents());

            loadedMods.add(loadedMod);
        }

        for (LoadedMod loadedMod : loadedMods)
        {
            loadedMod.preInit();
        }
    }

    public void init()
    {
        for (LoadedMod loadedMod : loadedMods)
        {
            loadedMod.init();
        }
    }

    public void postInit()
    {
        for (LoadedMod loadedMod : loadedMods)
        {
            loadedMod.postInit();
        }
    }

    public static ModRunner instance()
    {
        return instance != null ? instance : (instance = new ModRunner());
    }
}
