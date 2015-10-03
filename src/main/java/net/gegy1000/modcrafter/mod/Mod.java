package net.gegy1000.modcrafter.mod;

import com.google.common.collect.Lists;
import net.gegy1000.modcrafter.json.JsonComponent;
import net.gegy1000.modcrafter.json.JsonMod;
import net.gegy1000.modcrafter.mod.component.Component;

import java.util.List;

public class Mod implements Comparable<Mod>
{
    private String name;
    private List<Component> components = Lists.newArrayList();
    private String fileName;
    private long lastModified;

    public Mod(String name)
    {
        this.name = name;
        this.fileName = name;
        this.lastModified = System.currentTimeMillis();
    }

    public Mod(JsonMod jsonMod, String fileName)
    {
        this.name = jsonMod.name;
        this.fileName = fileName;
        this.lastModified = jsonMod.lastModified;

        for (JsonComponent component : jsonMod.components)
        {
            addComponent(component.toComponent(this));
        }
    }

    public String getName()
    {
        return name;
    }

    public void rename(String name)
    {
        this.name = name;
    }

    public List<Component> getComponents()
    {
        return components;
    }

    public boolean addComponent(Component component)
    {
        if (!components.contains(component))
        {
            components.add(component);

            return true;
        }

        return false;
    }

    public Component getComponent(int component)
    {
        return components.size() > component && component >= 0 ? components.get(component) : null;
    }

    public JsonMod toJson()
    {
        return new JsonMod(this);
    }

    public String getFileName()
    {
        return fileName;
    }

    public long getLastModified()
    {
        return lastModified;
    }

    @Override
    public int compareTo(Mod mod)
    {
        return this.lastModified < mod.lastModified ? 1 : (this.lastModified > mod.lastModified ? -1 : this.fileName.compareTo(mod.fileName));
    }

    public int getComponentId(Component component)
    {
        return components.indexOf(component);
    }

    public boolean hasComponentWithName(String name)
    {
        for (Component component : components)
        {
            if (component.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }

        return false;
    }
}
