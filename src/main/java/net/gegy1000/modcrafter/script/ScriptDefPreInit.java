package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.mod.sprite.SpriteDefMod;

public class ScriptDefPreInit extends ScriptDefHat
{
    @Override
    public String getId()
    {
        return "pre_init";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"On Pre-Game Initialization"};
    }

    @Override
    public int getColor()
    {
        return 0xFFFFFF;
    }

    @Override
    public boolean isAllowedFor(Sprite sprite)
    {
        return sprite.getSpriteDef() instanceof SpriteDefMod;
    }
}
