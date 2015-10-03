package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefMod;

public class ScriptDefInit extends ScriptDefHat
{
    @Override
    public String getId()
    {
        return "init";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"On Game Initialization"};
    }

    @Override
    public int getColor()
    {
        return 0x871212;
    }

    @Override
    public boolean isAllowedFor(Component component)
    {
        return component.getComponentDef() instanceof ComponentDefMod;
    }
}
