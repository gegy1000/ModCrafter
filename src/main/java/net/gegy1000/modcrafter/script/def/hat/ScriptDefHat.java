package net.gegy1000.modcrafter.script.def.hat;

import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.def.ScriptDef;

public abstract class ScriptDefHat extends ScriptDef
{
    @Override
    public void execute(Script script, Object[] contextVariables)
    {
    }

    @Override
    public boolean canAttachTo(Script script)
    {
        return false;
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

    public abstract Class[] getContextVariables();
}
