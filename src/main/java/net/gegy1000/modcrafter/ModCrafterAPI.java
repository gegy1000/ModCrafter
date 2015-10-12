package net.gegy1000.modcrafter;

import net.gegy1000.modcrafter.mod.component.ComponentDef;
import net.gegy1000.modcrafter.script.def.ScriptDef;

import java.util.HashMap;
import java.util.Map;

public class ModCrafterAPI
{
    private static Map<String, ScriptDef> registeredScriptTypes = new HashMap<String, ScriptDef>();
    private static Map<String, ComponentDef> registeredComponentTypes = new HashMap<String, ComponentDef>();

    public static void registerScriptDef(ScriptDef def)
    {
        registeredScriptTypes.put(def.getId(), def);
    }

    public static ScriptDef getScriptById(String id)
    {
        return registeredScriptTypes.get(id);
    }

    public static void registerComponentDef(ComponentDef def)
    {
        registeredComponentTypes.put(def.getId(), def);
    }

    public static ComponentDef getComponentById(String id)
    {
        return registeredComponentTypes.get(id);
    }

    public static Map<String, ScriptDef> getScriptDefs()
    {
        return registeredScriptTypes;
    }

    public static Map<String, ComponentDef> getComponentDefs()
    {
        return registeredComponentTypes;
    }

    public static ComponentDef getComponentByDisplayName(String name)
    {
        for (Map.Entry<String, ComponentDef> entry : registeredComponentTypes.entrySet())
        {
            if (entry.getValue().getDisplayName().equalsIgnoreCase(name))
            {
                return entry.getValue();
            }
        }

        return null;
    }
}
