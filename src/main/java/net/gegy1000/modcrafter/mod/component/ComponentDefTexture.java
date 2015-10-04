package net.gegy1000.modcrafter.mod.component;

import java.util.HashMap;
import java.util.Map;

public class ComponentDefTexture extends ComponentDef
{
    @Override
    public String getId()
    {
        return "component_texture";
    }

    @Override
    public String getDisplayName()
    {
        return "Texture";
    }

    @Override
    public Map<String, Class> getProperties()
    {
        Map<String, Class> properties = new HashMap<String, Class>();

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                properties.put("X;" + x + "Y;" + y, Integer.class);
            }
        }

        return properties;
    }

    @Override
    public void applyDefaultProperties(Map<String, Object> properties)
    {
        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                properties.put("X;" + x + "Y;" + y, 0xFFFFFF);
            }
        }
    }
}
