package net.gegy1000.modcrafter.common.modrun;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefOnItemRightClick;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemCustom extends Item
{
    private Component component;

    public ItemCustom(Component component)
    {
        super();
        this.setUnlocalizedName(component.getName().toLowerCase().replaceAll(" ", "_"));
        this.setCreativeTab(((EnumCreativeTab) component.getProperty("Creative Tab")).getTab());
        this.component = component;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
    {
        for (Script script : component.getHatScripts())
        {
            if (script.getScriptDef() instanceof ScriptDefOnItemRightClick)
            {
            }
        }

        return stack;
    }

    public Component getComponent()
    {
        return component;
    }
}
