package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.ModCrafterAPI;

public class ScriptDefManager
{
    public static final ScriptDef printConsole = new ScriptDefPrintConsole();
    public static final ScriptDef hatTest = new ScriptDefPreInit();
    public static final ScriptDef containerTest = new ScriptDefContainerTest();

    public static void init()
    {
        // Hats
        ModCrafterAPI.registerScriptDef(hatTest);

        // Blocks
        ModCrafterAPI.registerScriptDef(printConsole);

        // Containers
        ModCrafterAPI.registerScriptDef(containerTest);
    }
}
