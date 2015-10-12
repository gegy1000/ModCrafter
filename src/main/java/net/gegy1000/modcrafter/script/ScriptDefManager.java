package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.script.def.ScriptDef;
import net.gegy1000.modcrafter.script.def.ScriptDefChatMessage;
import net.gegy1000.modcrafter.script.def.ScriptDefPrintConsole;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefInit;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefOnBlockAdded;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefOnItemRightClick;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefPostInit;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefPreInit;

public class ScriptDefManager
{
    public static final ScriptDef printConsole = new ScriptDefPrintConsole();
    public static final ScriptDef preInit = new ScriptDefPreInit();
    public static final ScriptDef init = new ScriptDefInit();
    public static final ScriptDef postInit = new ScriptDefPostInit();
    public static final ScriptDef onItemRightClick = new ScriptDefOnItemRightClick();
    public static final ScriptDef onBlockAdded = new ScriptDefOnBlockAdded();
    public static final ScriptDef addChatMessage = new ScriptDefChatMessage();
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
        ModCrafterAPI.registerScriptDef(addChatMessage);

        // Containers
//        ModCrafterAPI.registerScriptDef(containerTest);
    }
}
