package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.component.Component;

public class ScriptDefContainerTest extends ScriptDefContainer
{
    @Override
    public String getId()
    {
        return "block_test";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"Container Test"};
    }

    @Override
    public int getColor()
    {
        return 0xFFFFFF;
    }

    @Override
    public boolean isAllowedFor(Component component)
    {
        return true;
    }

    @Override
    public void execute(Script script)
    {
    }
}
