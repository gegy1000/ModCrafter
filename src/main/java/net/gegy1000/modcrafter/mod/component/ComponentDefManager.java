package net.gegy1000.modcrafter.mod.component;

import net.gegy1000.modcrafter.ModCrafterAPI;

public class ComponentDefManager
{
    public static final ComponentDef mod = new ComponentDefMod();
    public static final ComponentDef item = new ComponentDefItem();
//    public static final ScriptDef containerTest = new ScriptDefContainerTest();

    public static void init()
    {
        ModCrafterAPI.registerComponentDef(item);
        ModCrafterAPI.registerComponentDef(mod);
    }
}
