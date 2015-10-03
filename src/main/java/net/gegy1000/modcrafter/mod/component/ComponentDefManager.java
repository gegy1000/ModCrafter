package net.gegy1000.modcrafter.mod.component;

import net.gegy1000.modcrafter.ModCrafterAPI;

public class ComponentDefManager
{
    public static final ComponentDef mod = new ComponentDefMod();
    public static final ComponentDef item = new ComponentDefItem();
    public static final ComponentDef block = new ComponentDefBlock();

    public static void init()
    {
        ModCrafterAPI.registerComponentDef(item);
        ModCrafterAPI.registerComponentDef(mod);
        ModCrafterAPI.registerComponentDef(block);
    }
}
