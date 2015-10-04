package net.gegy1000.modcrafter.mod.component;

import java.util.HashMap;
import java.util.Map;

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

    @Override
    public Map<String, Class> getProperties()
    {
        Map<String, Class> properties = new HashMap<String, Class>();

        return properties;
    }

    @Override
    public void applyDefaultProperties(Map<String, Object> properties)
    {

    }
}
