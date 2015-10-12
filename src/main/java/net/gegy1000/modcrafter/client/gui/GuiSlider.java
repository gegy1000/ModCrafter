package net.gegy1000.modcrafter.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import org.lwjgl.opengl.GL11;

public class GuiSlider extends Gui
{
    /**
     * Width in pixels
     */
    public int width;
    /**
     * Height in pixels
     */
    public int height;

    /**
     * The x position of this control.
     */
    public int xPosition;

    /**
     * The y position of this control.
     */
    public int yPosition;

    public int min, max;

    public int id;

    public boolean hovering;

    public int slide;

    public int colour;

    public boolean dragging;

    public GuiSlider(int id, int x, int y, int width, int height, int min, int max, int defaultPos, int colour)
    {
        this.id = id;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.min = min;
        this.max = max;
        this.slide = defaultPos;
        this.colour = colour;
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        dragging = mouseY > yPosition && mouseY < yPosition + height && mouseX > xPosition && !dragging;
    }

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY)
    {
        if (mouseY > yPosition - 4 && mouseY < yPosition + height + 4 && mouseX > xPosition && dragging)
        {
            this.slide = Math.min(Math.max(min, (mouseX - xPosition) - 65), max);
        }
        else
        {
            dragging = false;
        }
    }

    public void drawSlider(Minecraft mc, int mouseX, int mouseY)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.hovering = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.mouseDragged(mc, mouseX, mouseY);

        int drawX = this.xPosition + this.width / 2;
        int drawY = this.yPosition + (this.height - 8) / 2 + 1;

        this.drawScaledRect(drawX, drawY + (height / 2), width, 1, 1.0F, colour);
//        this.drawCenteredString(fontrenderer, this.dropdownItems.get(selected), drawX, drawY, l);

        this.drawBoxWithOutline(drawX + slide, drawY, 5, height, 1, 1.0F, 0x404040, 0x202020);
    }

    /**
     * Draws a textured rectangle at the stored z-value. Args: x, y, u, v, width, height
     */
    public void drawScaledRect(int x, int y, int width, int height, float scale, int colour)
    {
        GL11.glPushMatrix();

        x /= scale;
        y /= scale;

        GL11.glScalef(scale, scale, scale);

        GL11.glDisable(GL11.GL_TEXTURE_2D);

        float red = (float) (colour >> 16 & 255) / 255.0F;
        float green = (float) (colour >> 8 & 255) / 255.0F;
        float blue = (float) (colour & 255) / 255.0F;

        GL11.glColor3f(red, green, blue);

        float f = 1.0F / (float) width;
        float f1 = 1.0F / (float) height;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.addVertexWithUV((double) (x), (double) (y + height), (double) 1, (double) (0), (double) ((float) (height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y + height), (double) 0, (double) ((float) (width) * f), (double) ((float) (height) * f1));
        tessellator.addVertexWithUV((double) (x + width), (double) (y), (double) 0, (double) ((float) (width) * f), (double) ((float) 0));
        tessellator.addVertexWithUV((double) (x), (double) (y), (double) 0, (double) ((float) 0), (double) ((float) 0));
        tessellator.draw();

        GL11.glColor3f(1.0F, 1.0F, 1.0F);

        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glPopMatrix();
    }

    public void drawBoxOutline(int x, int y, int sizeX, int sizeY, int borderSize, float scale, int colour)
    {
        GL11.glPushMatrix();

        drawScaledRect(x, y, sizeX, borderSize, scale, colour);
        drawScaledRect(x + sizeX, y, borderSize, sizeY + borderSize, scale, colour);
        drawScaledRect(x, y, borderSize, sizeY + borderSize, scale, colour);
        drawScaledRect(x, y + sizeY, sizeX, borderSize, scale, colour);

        GL11.glPopMatrix();
    }

    public void drawBoxWithOutline(int x, int y, int sizeX, int sizeY, int borderSize, float scale, int colourBorder, int colourFill)
    {
        drawBoxOutline(x, y, sizeX, sizeY, borderSize, scale, colourBorder);
        drawScaledRect(x + 1, y + 1, sizeX - 1, sizeY - 1, scale, colourFill);
    }
}
