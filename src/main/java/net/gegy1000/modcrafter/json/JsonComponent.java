package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class JsonComponent
{
    public String name;

    public List<JsonScript> scripts;
    public Map<String, Object> properties;

    public String type;

    public JsonComponent(Component component)
    {
        this.name = component.getName();

        this.scripts = new ArrayList<JsonScript>();
        this.properties = component.getProperties();

        this.type = component.getComponentDef().getId();

        for (Script script : component.getScripts())
        {
            scripts.add(new JsonScript(script));
        }
    }

    public Component toComponent(Mod mod)
    {
        return new Component(mod, this);
    }
}
