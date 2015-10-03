package net.gegy1000.modcrafter.mod.component;

public class ComponentDefMod extends ComponentDef
{
    @Override
    public String getId()
    {
        return "component_mod";
    }

    @Override
    public String getDisplayName()
    {
        return "Mod";
    }

    @Override
    public boolean canCreateAndDestroy()
    {
        return false;
    }
}
