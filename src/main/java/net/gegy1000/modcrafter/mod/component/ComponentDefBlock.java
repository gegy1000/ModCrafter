package net.gegy1000.modcrafter.mod.component;

import net.gegy1000.modcrafter.common.modrun.EnumCreativeTab;

import java.util.HashMap;
import java.util.Map;

public class ComponentDefBlock extends ComponentDef
{
    @Override
    public String getId()
    {
        return "component_block";
    }

    @Override
    public String getDisplayName()
    {
        return "Block";
    }

    @Override
    public Map<String, Class> getProperties()
    {
        Map<String, Class> properties = new HashMap<String, Class>();

        properties.put("Creative Tab", EnumCreativeTab.class);
        properties.put("Texture", String.class);

        return properties;
    }

    @Override
    public void applyDefaultProperties(Map<String, Object> properties)
    {
        properties.put("Creative Tab", EnumCreativeTab.BLOCKS);
        properties.put("Texture", "missingno");
    }
}
