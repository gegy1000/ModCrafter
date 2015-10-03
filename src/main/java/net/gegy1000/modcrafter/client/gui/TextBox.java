package net.gegy1000.modcrafter.client.gui;

import net.gegy1000.modcrafter.client.gui.element.Element;
import net.gegy1000.modcrafter.script.parameter.DataType;
import net.gegy1000.modcrafter.script.parameter.InputParameter;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ChatAllowedCharacters;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class TextBox extends Element
{
    public String text = "";
    public InputParameter inputParameter;
    public int ticksSinceClicked;
    public boolean selectAll = false;

    public TextBox(GuiModCrafterProject gui, int x, int y, int width, int height, InputParameter parameter)
    {
        super(gui, x, y, width, height);
        this.inputParameter = parameter;
    }

    public void keyTyped(char c, int key)
    {
        Keyboard.enableRepeatEvents(true);

        if (key == 14 && text.length() > 0)
        {
            if (selectAll)
            {
                text = "";
                selectAll = false;
            }
            else
            {
                text = text.substring(0, text.length() - 1);
            }
        }
        else if (isAllowedCharacter(c, inputParameter.getDataType()))
        {
            if (selectAll)
            {
                text = c + "";
                selectAll = false;
            }
            else
            {
                text += c;
            }
        }
        else if (key == Keyboard.KEY_RETURN)
        {
            parent.textBox = null;
            inputParameter.setData(text);
        }
        else if (parent.isCtrlKeyDown())
        {
            if (Keyboard.isKeyDown(Keyboard.KEY_A))
            {
                selectAll = true;
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_C))
            {
                if (selectAll)
                {
                    GuiScreen.setClipboardString(text);
                }
            }
            else if (Keyboard.isKeyDown(Keyboard.KEY_V))
            {
                if (selectAll)
                {
                    text = GuiScreen.getClipboardString();
                    selectAll = false;
                }
                else
                {
                    text += GuiScreen.getClipboardString();
                }
            }
        }
    }

    public boolean isAllowedCharacter(char c, DataType type)
    {
        if (type == DataType.TEXT)
        {
            return ChatAllowedCharacters.isAllowedCharacter(c);
        }
        else if (type == DataType.NUMBER)
        {
            return c == 46 || c >= 48 && c <= 57;
        }

        return false;
    }

    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        if (!(mouseX > xPosition && mouseX <= xPosition + width && mouseY > yPosition && mouseY <= yPosition + height))
        {
            parent.textBox = null;
            inputParameter.setData(text);
        }
        else
        {
            selectAll = ticksSinceClicked <= 5;
            ticksSinceClicked = 0;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        width = mc.fontRenderer.getStringWidth(text + "_") + 4;

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawRect(xPosition, yPosition, width, 1, 1.0F, 1.0F, 1.0F, 0.2F);
        drawRect(xPosition, yPosition + 1, 1, height - 2, 1.0F, 1.0F, 1.0F, 0.2F);
        drawRect(xPosition, yPosition + height - 1, width, 1, 1.0F, 1.0F, 1.0F, 0.2F);
        drawRect(xPosition + width - 1, yPosition + 1, 1, height - 2, 1.0F, 1.0F, 1.0F, 0.2F);

        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor3f(0.05F, 0.05F, 0.05F);
        parent.drawTexturedModalRect(xPosition + 2, yPosition + height, 0, 0, 2, 4);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        String s = text + (System.currentTimeMillis() % 1200 >= 600 ? "_" : "");
        drawScaledString(mc, s, xPosition + 2, yPosition + 2, 0xFFFFFF, 1.0F);

        if (selectAll)
        {
            drawRect(xPosition + 2, yPosition + 2, width - 4, height - 4, 0.5F, 0.7F, 1.0F, 0.5F);
        }
    }

    public void updateScreen()
    {
        ++ticksSinceClicked;
    }
}