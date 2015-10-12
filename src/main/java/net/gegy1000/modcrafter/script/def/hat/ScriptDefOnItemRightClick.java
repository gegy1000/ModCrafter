package net.gegy1000.modcrafter.script.def.hat;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

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

    @Override
    public Class[] getContextVariables()
    {
        return new Class[]{World.class, EntityPlayer.class};
    }
}
