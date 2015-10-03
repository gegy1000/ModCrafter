package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.sprite.Sprite;

public class ScriptDefHatTest extends ScriptDefHat
{
    @Override
    public String getId()
    {
        return "hat_test";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"Hat Test"};
    }

    @Override
    public int getColor()
    {
        return 0xFFFFFF;
    }

    @Override
    public boolean isAllowedFor(Sprite sprite)
    {
        return true;
    }
}
