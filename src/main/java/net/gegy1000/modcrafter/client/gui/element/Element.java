package net.gegy1000.modcrafter.client.gui.element;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.color.ColorHelper;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public abstract class Element
{
    public Minecraft mc = Minecraft.getMinecraft();

    public GuiModCrafterProject parent;
    public int xPosition;
    public int yPosition;
    public int width;
    public int height;

    public Element(GuiModCrafterProject gui, int x, int y, int width, int height)
    {
        this.parent = gui;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    public void mouseMovedOrUp(int mouseX, int mouseY, int event)
    {

    }

    public void mouseClicked(int mouseX, int mouseY, int button)
    {

    }

    public void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick)
    {

    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        ColorHelper.setColorFromInt(parent.elementColor, 1.0F);
        parent.drawTexturedModalRect(xPosition, yPosition, 0, 0, width, height);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    public void drawRect(int x, int y, int sizeX, int sizeY, float r, float g, float b, float a)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(r, g, b, a);
        parent.drawTexturedModalRect(x, y, 0, 0, sizeX, sizeY);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(1, 1, 1, 1);
    }

    public void drawScaledString(Minecraft mc, String text, float x, float y, int color, float scale)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x / scale, y / scale, 0);
        parent.drawString(mc.fontRenderer, text, 0, 0, color);
        GL11.glPopMatrix();
    }
}