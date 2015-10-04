package net.gegy1000.modcrafter.common.modrun;

import net.minecraft.creativetab.CreativeTabs;

public enum EnumCreativeTab
{
    BLOCKS("Blocks", CreativeTabs.tabBlock), MISC("Miscellaneous", CreativeTabs.tabMisc), MATERIALS("Materials", CreativeTabs.tabMaterials), BREWING("Brewing", CreativeTabs.tabBrewing), COMBAT("Combat", CreativeTabs.tabCombat), TOOLS("Tools", CreativeTabs.tabTools), FOOD("Food", CreativeTabs.tabFood), REDSTONE("Redstone", CreativeTabs.tabRedstone), DECORATION("Decoration", CreativeTabs.tabDecorations), TRANSPORT("Transport", CreativeTabs.tabTransport);

    String name;
    CreativeTabs tab;

    EnumCreativeTab(String name, CreativeTabs tab)
    {
        this.name = name;
        this.tab = tab;
    }

    public String getTabName()
    {
        return name;
    }

    public CreativeTabs getTab()
    {
        return tab;
    }

    public static EnumCreativeTab byTab(CreativeTabs property)
    {
        for (EnumCreativeTab tab : values())
        {
            if (tab.getTab().equals(property))
            {
                return tab;
            }
        }

        return null;
    }
}
