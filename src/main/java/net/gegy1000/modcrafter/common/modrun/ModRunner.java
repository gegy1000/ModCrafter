package net.gegy1000.modcrafter.common.modrun;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.ModSaveManager;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.mod.sprite.SpriteDefMod;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.ScriptDefPreInit;

import java.util.List;

public class ModRunner
{
    private List<Mod> mods;

    private static ModRunner instance;

    public void preInit()
    {
        this.mods = ModSaveManager.discoverMods();

        for (Mod mod : mods)
        {
            for (Sprite sprite : mod.getSprites())
            {
                if(sprite.getSpriteDef() instanceof SpriteDefMod)
                {
                    for (Script script : sprite.getHatScripts())
                    {
                        if(script.getScriptDef() instanceof ScriptDefPreInit)
                        {
                            script.execute();
                        }
                    }
                }
            }
        }
    }

    public static ModRunner instance()
    {
        return instance != null ? instance : (instance = new ModRunner());
    }
}