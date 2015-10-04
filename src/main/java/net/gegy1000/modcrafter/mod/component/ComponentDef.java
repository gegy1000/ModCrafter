package net.gegy1000.modcrafter.mod.component;

import java.util.Map;

public abstract class ComponentDef
{
    public abstract String getId();

    public abstract String getDisplayName();

    public abstract Map<String, Class> getProperties();

    public boolean canCreateAndDestroy()
    {
        return true;
    }
}
