package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefItem;

public class ScriptDefOnItemRightClick extends ScriptDefHat
{
    @Override
    public String getId()
    {
        return "item_right_click";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"When Right Clicked"};
    }

    @Override
    public int getColor()
    {
        return 0x492984;
    }

    @Override
    public boolean isAllowedFor(Component component)
    {
        return component.getComponentDef() instanceof ComponentDefItem;
    }
}
