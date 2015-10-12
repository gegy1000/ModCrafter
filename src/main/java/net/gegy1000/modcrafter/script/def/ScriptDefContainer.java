package net.gegy1000.modcrafter.script.def;

import net.gegy1000.modcrafter.script.Script;

public abstract class ScriptDefContainer extends ScriptDef
{
    @Override
    public int getHeight(Script script)
    {
        int gap = 9;

        if (script != null)
        {
            Script contained = script.getContained();

            if (contained != null)
            {
                gap = 0;
            }

            while (contained != null)
            {
                gap += contained.getScriptDef().getHeight(contained) - 2;

                contained = contained.getChild();
            }
        }

        return gap + 22;
    }
}
