package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.script.Script;

import java.util.ArrayList;
import java.util.List;

public class JsonSprite
{
    public String name;

    public List<JsonScript> scripts;

    public String type;

    public JsonSprite(Sprite sprite)
    {
        this.name = sprite.getName();

        this.scripts = new ArrayList<JsonScript>();

        this.type = sprite.getSpriteDef().getId();

        for (Script script : sprite.getScripts())
        {
            scripts.add(new JsonScript(script));
        }
    }

    public Sprite toSprite(Mod mod)
    {
        return new Sprite(mod, this);
    }
}
