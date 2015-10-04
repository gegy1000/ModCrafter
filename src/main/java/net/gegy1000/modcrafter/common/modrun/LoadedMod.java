package net.gegy1000.modcrafter.common.modrun;

import cpw.mods.fml.common.registry.GameRegistry;
import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefBlock;
import net.gegy1000.modcrafter.mod.component.ComponentDefItem;
import net.gegy1000.modcrafter.mod.component.ComponentDefMod;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.ScriptDefInit;
import net.gegy1000.modcrafter.script.ScriptDefPostInit;
import net.gegy1000.modcrafter.script.ScriptDefPreInit;

import java.util.ArrayList;
import java.util.List;

public class LoadedMod
{
    private List<Component> components;

    private List<BlockCustom> blocks;
    private List<ItemCustom> items;

    public LoadedMod(List<Component> components)
    {
        this.components = components;

        this.blocks = new ArrayList<BlockCustom>();
        this.items = new ArrayList<ItemCustom>();

        for (Component component : components)
        {
            if (component.getComponentDef() instanceof ComponentDefItem)
            {
                ItemCustom item = new ItemCustom(component);

                this.items.add(item);
            }
            else if (component.getComponentDef() instanceof ComponentDefBlock)
            {
                BlockCustom block = new BlockCustom(component);

                this.blocks.add(block);
            }
        }
    }

    public void preInit()
    {
        for (Component component : components)
        {
            if (component.getComponentDef() instanceof ComponentDefMod)
            {
                for (Script script : component.getHatScripts())
                {
                    if (script.getScriptDef() instanceof ScriptDefPreInit)
                    {
                        script.execute();
                    }
                }
            }
        }

        for (ItemCustom item : items)
        {
            GameRegistry.registerItem(item, item.getComponent().getName().toLowerCase().replaceAll(" ", "_"));
        }

        for (BlockCustom block : blocks)
        {
            GameRegistry.registerBlock(block, block.getComponent().getName().toLowerCase().replaceAll(" ", "_"));
        }
    }

    public void init()
    {
        for (Component component : components)
        {
            if (component.getComponentDef() instanceof ComponentDefMod)
            {
                for (Script script : component.getHatScripts())
                {
                    if (script.getScriptDef() instanceof ScriptDefInit)
                    {
                        script.execute();
                    }
                }
            }
        }
    }

    public void postInit()
    {
        for (Component component : components)
        {
            if (component.getComponentDef() instanceof ComponentDefMod)
            {
                for (Script script : component.getHatScripts())
                {
                    if (script.getScriptDef() instanceof ScriptDefPostInit)
                    {
                        script.execute();
                    }
                }
            }
        }
    }
}
