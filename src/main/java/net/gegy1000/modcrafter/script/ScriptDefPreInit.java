package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefMod;

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
    public boolean isAllowedFor(Component component)
    {
        return component.getComponentDef() instanceof ComponentDefMod;
    }
}
