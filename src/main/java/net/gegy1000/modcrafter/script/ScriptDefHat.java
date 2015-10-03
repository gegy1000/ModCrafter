package net.gegy1000.modcrafter.script;

public abstract class ScriptDefHat extends ScriptDef
{
    @Override
    public void execute(Script script)
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
}
