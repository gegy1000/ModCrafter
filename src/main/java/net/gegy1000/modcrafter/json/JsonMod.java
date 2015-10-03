package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.sprite.Sprite;

import java.util.ArrayList;
import java.util.List;

public class JsonMod
{
    public String name;

    public List<JsonSprite> sprites;

    public long lastModified;

    public JsonMod(Mod mod)
    {
        this.name = mod.getName();

        this.lastModified = mod.getLastModified();
        this.sprites = new ArrayList<JsonSprite>();

        for (Sprite sprite : mod.getSprites())
        {
            sprites.add(new JsonSprite(sprite));
        }
    }

    public Mod toMod(String fileName)
    {
        return new Mod(this, fileName);
    }
}
