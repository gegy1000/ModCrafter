package net.gegy1000.modcrafter.mod.component;

import net.gegy1000.modcrafter.common.modrun.EnumCreativeTab;

import java.util.HashMap;
import java.util.Map;

public class ComponentDefItem extends ComponentDef
{
    @Override
    public String getId()
    {
        return "component_item";
    }

    @Override
    public String getDisplayName()
    {
        return "Item";
    }

    @Override
    public Map<String, Class> getProperties()
    {
        Map<String, Class> properties = new HashMap<String, Class>();

        properties.put("Creative Tab", EnumCreativeTab.class);

        return properties;
    }

    @Override
    public void applyDefaultProperties(Map<String, Object> properties)
    {
        properties.put("Creative Tab", EnumCreativeTab.MISC);
    }
}
