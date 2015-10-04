package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.common.modrun.EnumCreativeTab;
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
    public List<JsonProperty> properties;

    public String type;

    public JsonComponent(Component component)
    {
        this.name = component.getName();

        this.scripts = new ArrayList<JsonScript>();
        this.properties = new ArrayList<JsonProperty>();

        for (Map.Entry<String, Object> entry : component.getProperties().entrySet())
        {
            properties.add(new JsonProperty(entry.getKey(), entry.getValue()));
        }

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

    public class JsonProperty
    {
        public String key;
        public String value;

        public JsonProperty(String key, Object object)
        {
            this.key = key;

            if (object instanceof String)
            {
                this.value = "string:" + object;
            }
            else if (object instanceof EnumCreativeTab)
            {
                this.value = "tab:" + ((EnumCreativeTab) object).ordinal();
            }
        }

        public Object getObject()
        {
            Object object = null;

            if (value.startsWith("string:"))
            {
                object = value.split("string:")[1];
            }
            else if (value.startsWith("tab:"))
            {
                object = EnumCreativeTab.values()[Integer.parseInt(value.split("tab:")[1])];
            }

            return object;
        }
    }
}
