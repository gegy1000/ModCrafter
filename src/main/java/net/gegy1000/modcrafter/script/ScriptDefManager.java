package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.ModCrafterAPI;

public class ScriptDefManager
{
    public static final ScriptDef printConsole = new ScriptDefPrintConsole();
    public static final ScriptDef preInit = new ScriptDefPreInit();
    public static final ScriptDef init = new ScriptDefInit();
    public static final ScriptDef postInit = new ScriptDefPostInit();
    public static final ScriptDef onItemRightClick = new ScriptDefOnItemRightClick();
    public static final ScriptDef onBlockAdded = new ScriptDefOnBlockAdded();
//    public static final ScriptDef containerTest = new ScriptDefContainerTest();

    public static void init()
    {
        // Hats
        ModCrafterAPI.registerScriptDef(preInit);
        ModCrafterAPI.registerScriptDef(init);
        ModCrafterAPI.registerScriptDef(postInit);
        ModCrafterAPI.registerScriptDef(onItemRightClick);
        ModCrafterAPI.registerScriptDef(onBlockAdded);

        // Blocks
        ModCrafterAPI.registerScriptDef(printConsole);

        // Containers
//        ModCrafterAPI.registerScriptDef(containerTest);
    }
}
