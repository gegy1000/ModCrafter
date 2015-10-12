package net.gegy1000.modcrafter.common.modrun;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.def.hat.ScriptDefOnBlockAdded;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.world.World;

public class BlockCustom extends Block
{
    private Component component;

    public BlockCustom(Component component)
    {
        super(Material.rock);
        this.setHardness(1.0F);
        this.setResistance(1.0F);
        this.setCreativeTab(((EnumCreativeTab) component.getProperty("Creative Tab")).getTab());
        this.setBlockName(component.getName().toLowerCase().replaceAll(" ", "_"));
        this.component = component;
    }

    public Component getComponent()
    {
        return component;
    }

    /**
     * Called whenever the block is added into the world. Args: world, x, y, z
     */
    public void onBlockAdded(World world, int x, int y, int z)
    {
        for (Script script : component.getHatScripts())
        {
            if (script.getScriptDef() instanceof ScriptDefOnBlockAdded)
            {
                script.execute(new Object[]{world});
            }
        }
    }
}
