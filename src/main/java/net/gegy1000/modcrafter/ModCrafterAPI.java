package net.gegy1000.modcrafter;

import net.gegy1000.modcrafter.mod.sprite.SpriteDef;
import net.gegy1000.modcrafter.script.ScriptDef;

import java.util.HashMap;
import java.util.Map;

public class ModCrafterAPI
{
    private static Map<String, ScriptDef> registeredScriptTypes = new HashMap<String, ScriptDef>();
    private static Map<String, SpriteDef> registeredSpriteTypes = new HashMap<String, SpriteDef>();

    public static void registerScriptDef(ScriptDef def)
    {
        registeredScriptTypes.put(def.getId(), def);
    }

    public static ScriptDef getScriptById(String id)
    {
        return registeredScriptTypes.get(id);
    }

    public static void registerSpriteDef(SpriteDef def)
    {
        registeredSpriteTypes.put(def.getId(), def);
    }

    public static SpriteDef getSpriteById(String id)
    {
        return registeredSpriteTypes.get(id);
    }

    public static Map<String, ScriptDef> getScriptDefs()
    {
        return registeredScriptTypes;
    }
}
