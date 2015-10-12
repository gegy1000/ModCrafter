package net.gegy1000.modcrafter.script.def;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.parameter.IParameter;

public abstract class ScriptDef
{
    public abstract void execute(Script script, Object[] contextVariables);

    public abstract String getId();

    public abstract Object[] getName();

    public abstract int getColor();

    public abstract boolean isAllowedFor(Component component);

    public abstract int getHeight(Script script);

    public abstract Class[] getRequiredContextVariables();

    public boolean canAttachTo(Script script)
    {
        return true;
    }

    public String getDefualtDisplayName()
    {
        String displayName = "";

        int parIndex = 0;

        for (Object namePart : getName())
        {
            if (namePart instanceof IParameter)
            {
                displayName += ((IParameter) namePart).getData();
            }
            else
            {
                displayName += namePart;
            }

            displayName += " ";
        }

        return displayName;
    }
}
