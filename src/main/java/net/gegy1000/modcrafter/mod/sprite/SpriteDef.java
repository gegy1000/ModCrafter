package net.gegy1000.modcrafter.mod.sprite;

public abstract class SpriteDef
{
    public abstract String getId();

    public abstract String getDisplayName();

    public boolean canCreateAndDestroy()
    {
        return true;
    }
}
