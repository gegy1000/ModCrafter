package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.script.parameter.InputParameter;

public class ScriptDefPrintConsole extends ScriptDef
{
    @Override
    public void execute(Script script)
    {
        System.out.println(script.getParameter(0));
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
    public boolean isAllowedFor(Sprite sprite)
    {
        return true;
    }

    @Override
    public int getHeight(Script script)
    {
        return 11;
    }
}
