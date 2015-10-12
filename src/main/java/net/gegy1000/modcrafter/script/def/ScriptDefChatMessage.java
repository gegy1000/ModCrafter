package net.gegy1000.modcrafter.script.def;

import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.parameter.InputParameter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;

public class ScriptDefChatMessage extends ScriptDef
{
    @Override
    public void execute(Script script, Object[] contextVariables)
    {
        EntityPlayer player = (EntityPlayer) contextVariables[0];

        if (player.worldObj.isRemote)
        {
            player.addChatComponentMessage(new ChatComponentText((String) script.getParameter(0).getData()));
        }
    }

    @Override
    public String getId()
    {
        return "chat_message";
    }

    @Override
    public Object[] getName()
    {
        return new Object[]{"Send", new InputParameter("Hello World"), "to player"};
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
        return new Class[]{EntityPlayer.class};
    }
}
