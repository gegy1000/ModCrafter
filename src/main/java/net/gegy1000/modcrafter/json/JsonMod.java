package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.component.Component;

import java.util.ArrayList;
import java.util.List;

public class JsonMod
{
    public String name;

    public List<JsonComponent> components;

    public long lastModified;

    public JsonMod(Mod mod)
    {
        this.name = mod.getName();

        this.lastModified = mod.getLastModified();
        this.components = new ArrayList<JsonComponent>();

        for (Component component : mod.getComponents())
        {
            components.add(new JsonComponent(component));
        }
    }

    public Mod toMod(String fileName)
    {
        return new Mod(this, fileName);
    }
}
