package net.gegy1000.modcrafter.script.def.hat;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefBlock;
import net.minecraft.world.World;

public class ScriptDefOnBlockAdded extends ScriptDefHat
{
    @Override
    public String getId()
    {
        return "block_added";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"When Block Placed"};
    }

    @Override
    public int getColor()
    {
        return 0x492984;
    }

    @Override
    public boolean isAllowedFor(Component component)
    {
        return component.getComponentDef() instanceof ComponentDefBlock;
    }

    @Override
    public Class[] getContextVariables()
    {
        return new Class[]{World.class};
    }
}
