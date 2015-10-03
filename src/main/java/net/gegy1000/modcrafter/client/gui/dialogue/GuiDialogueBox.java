package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public abstract class GuiDialogueBox
{
    private GuiButton selectedButton;

    protected List<GuiModCrafterButton> buttonList = new ArrayList<GuiModCrafterButton>();

    protected Minecraft mc = Minecraft.getMinecraft();

    protected GuiModCrafterProject project;

    public GuiDialogueBox(GuiModCrafterProject project)
    {
        this.project = project;
        this.project.openDialogue = this;
    }

    /**
     * Called when the mouse is clicked.
     */
    public void mouseClicked(int x, int y, int mouseButton)
    {
        if (mouseButton == 0)
        {
            for (int l = 0; l < this.buttonList.size(); ++l)
            {
                GuiButton button = this.buttonList.get(l);

                if (button.mousePressed(this.mc, x, y))
                {
                    this.selectedButton = button;
                    button.func_146113_a(this.mc.getSoundHandler());
                    this.actionPerformed(button);
                }
            }
        }
    }

    public void closeDialogue()
    {
        project.openDialogue = null;
    }

    public abstract void actionPerformed(GuiButton button);

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

    public void renderBase(int mouseX, int mouseY)
    {
        int k;

        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        drawBoxWithOutline((resolution.getScaledWidth() / 2) - 100, (resolution.getScaledHeight() / 2) - 50, 200, 100, 1, 1.0F, 0x404040, 0x303030);

        for (k = 0; k < this.buttonList.size(); ++k)
        {
            this.buttonList.get(k).drawButton(this.mc, mouseX, mouseY);
        }

        render(mouseX, mouseY);
    }

    public abstract void render(int mouseX, int mouseY);
}