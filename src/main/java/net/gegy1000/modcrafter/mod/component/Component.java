package net.gegy1000.modcrafter.mod.component;

import com.google.common.collect.Lists;
import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.json.JsonComponent;
import net.gegy1000.modcrafter.json.JsonScript;
import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefHat;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Component
{
    private String name;
    private final Mod mod;

    private List<Script> scripts = Lists.newArrayList();

    private ComponentDef def;

    private Map<String, Object> properties = new HashMap<String, Object>();

    public Component(ComponentDef def, Mod mod, String name)
    {
        this.mod = mod;
        this.name = name;
        this.def = def;

        applyDefaultProperties();
    }

    public Component(Mod mod, JsonComponent jsonComponent)
    {
        this.mod = mod;

        this.def = ModCrafterAPI.getComponentById(jsonComponent.type);
        this.name = jsonComponent.name;

        if (jsonComponent.properties != null)
        {
            for (JsonComponent.JsonProperty property : jsonComponent.properties)
            {
                setProperty(property.key, property.getObject());
            }
        }

        for (JsonScript script : jsonComponent.scripts)
        {
            Script newScript = script.toScript(this, null);

            addScript(newScript);
        }

        applyDefaultProperties();
    }

    private void applyDefaultProperties()
    {
        Map<String, Object> defaultProperties = new HashMap<String, Object>();
        def.applyDefaultProperties(defaultProperties);

        for (Map.Entry<String, Object> entry : defaultProperties.entrySet())
        {
            if (!properties.containsKey(entry.getKey()))
            {
                properties.put(entry.getKey(), entry.getValue());
            }
        }
    }

    public ComponentDef getComponentDef()
    {
        return def;
    }

    public List<Script> getScripts()
    {
        return scripts;
    }

    public List<Script> getHatScripts()
    {
        List<Script> hatScripts = new ArrayList<Script>();

        for (Script script : scripts)
        {
            if (script.getScriptDef() instanceof ScriptDefHat)
            {
                hatScripts.add(script);
            }
        }

        return hatScripts;
    }

    public void addScript(Script script)
    {
        if (script != null && script.getParent() == null)
        {
            this.scripts.add(script);
        }
    }

    public void removeScript(Script script)
    {
        if (script != null)
        {
            int scriptId = getScriptId(script);

            if (scriptId >= 0)
            {
                scripts.remove(scriptId);

                if (script.getChild() != null)
                {
                    removeScript(script.getChild());
                }
            }
        }
    }

    public Script getScript(int id)
    {
        return id >= 0 ? scripts.get(id) : null;
    }

    public int getScriptId(Script script)
    {
        return scripts.indexOf(script);
    }

    public Mod getMod()
    {
        return mod;
    }

    public String getName()
    {
        if (name == null)
        {
            name = RandomStringUtils.randomAscii(10);
        }

        return name;
    }

    public void rename(String name)
    {
        this.name = name;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof Component)
        {
            return ((Component) obj).getName().equals(getName());
        }

        return false;
    }

    public List<Script> getScriptsAndChildren()
    {
        List<Script> scripts = new ArrayList<Script>();

        for (Script script : this.scripts)
        {
            getScriptAndChild(script, scripts);
        }

        return scripts;
    }

    private void getScriptAndChild(Script script, List<Script> scripts)
    {
        scripts.add(script);

        if (script.getChild() != null)
        {
            getScriptAndChild(script.getChild(), scripts);
        }
    }

    public Object getProperty(String name)
    {
        return properties.get(name);
    }

    public Map<String, Object> getProperties()
    {
        return properties;
    }

    public void setProperty(String key, Object value)
    {
        properties.put(key, value);
    }
}
