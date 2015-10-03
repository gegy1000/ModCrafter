package net.gegy1000.modcrafter.mod.component;

public abstract class ComponentDef
{
    public abstract String getId();

    public abstract String getDisplayName();

    public boolean canCreateAndDestroy()
    {
        return true;
    }
}
