package net.gegy1000.modcrafter.mod.sprite;

public class SpriteDefMod extends SpriteDef
{
    @Override
    public String getId()
    {
        return "sprite_mod";
    }

    @Override
    public String getDisplayName()
    {
        return "Mod";
    }

    @Override
    public boolean canCreateAndDestroy()
    {
        return false;
    }
}
