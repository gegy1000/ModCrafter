package net.gegy1000.modcrafter.script.def;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.parameter.InputParameter;

public class ScriptDefPrintConsole extends ScriptDef
{
    @Override
    public void execute(Script script, Object[] contextVariables)
    {
        System.out.println(script.getParameter(0).getData());
    }

    @Override
    public String getId()
    {
        return "print_console";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"print", new InputParameter("Hello World"), "to console"};
    }

    @Override
    public int getColor()
    {
        return 0xFF6A00;
    }

    @Override
    public boolean isAllowedFor(Component component)
    {
        return true;
    }

    @Override
    public int getHeight(Script script)
    {
        return 11;
    }

    @Override
    public Class[] getRequiredContextVariables()
    {
        return new Class[0];
    }
}
