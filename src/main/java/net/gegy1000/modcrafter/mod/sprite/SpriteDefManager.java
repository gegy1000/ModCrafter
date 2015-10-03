package net.gegy1000.modcrafter.mod.sprite;

import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.script.ScriptDef;
import net.gegy1000.modcrafter.script.ScriptDefPreInit;
import net.gegy1000.modcrafter.script.ScriptDefPrintConsole;

public class SpriteDefManager
{
    public static final SpriteDef mod = new SpriteDefMod();
    public static final SpriteDef item = new SpriteDefItem();
//    public static final ScriptDef containerTest = new ScriptDefContainerTest();

    public static void init()
    {
        ModCrafterAPI.registerSpriteDef(item);
        ModCrafterAPI.registerSpriteDef(mod);
    }
}
