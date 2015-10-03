package net.gegy1000.modcrafter.mod;

import com.google.common.collect.Lists;
import net.gegy1000.modcrafter.json.JsonMod;
import net.gegy1000.modcrafter.json.JsonSprite;
import net.gegy1000.modcrafter.mod.sprite.Sprite;

import java.util.List;

public class Mod implements Comparable<Mod>
{
    private String name;
    private List<Sprite> sprites = Lists.newArrayList();
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

        for (JsonSprite sprite : jsonMod.sprites)
        {
            addSprite(sprite.toSprite(this));
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

    public List<Sprite> getSprites()
    {
        return sprites;
    }

    public boolean addSprite(Sprite sprite)
    {
        if (!sprites.contains(sprite))
        {
            sprites.add(sprite);

            return true;
        }

        return false;
    }

    public Sprite getSprite(int sprite)
    {
        return sprites.size() > sprite && sprite >= 0 ? sprites.get(sprite) : null;
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

    public int getSpriteId(Sprite sprite)
    {
        return sprites.indexOf(sprite);
    }

    public boolean hasSpriteWithName(String name)
    {
        for (Sprite sprite : sprites)
        {
            if(sprite.getName().equalsIgnoreCase(name))
            {
                return true;
            }
        }

        return false;
    }
}
