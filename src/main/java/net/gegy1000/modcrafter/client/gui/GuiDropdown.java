package net.gegy1000.modcrafter.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.gegy1000.modcrafter.common.modrun.EnumCreativeTab;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GuiDropdown extends Gui
{
    protected static final ResourceLocation textures = new ResourceLocation("textures/gui/widgets.png");
    /**
     * Button width in pixels
     */
    public int width;
    /**
     * Button height in pixels
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

    private int selected;

    private List<String> dropdownItems = new ArrayList<String>();

    public int id;
    /**
     * True if this control is enabled, false to disable.
     */
    public boolean enabled;
    /**
     * Hides the button completely if false.
     */
    public boolean visible;
    protected boolean field_146123_n;
    public int packedFGColour;

    public boolean open;

    public GuiDropdown(int id, int x, int y, List<String> dropdownItems)
    {
        this(id, x, y, 200, 20, dropdownItems);
    }

    public GuiDropdown(int id, int x, int y, int width, int height, List<String> dropdownItems)
    {
        this.width = 200;
        this.height = 20;
        this.enabled = true;
        this.visible = true;
        this.id = id;
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
        this.dropdownItems = dropdownItems;
    }

    /**
     * Returns 0 if the button is disabled, 1 if the mouse is NOT hovering over this button and 2 if it IS hovering over
     * this button.
     */
    public int getHoverState(boolean p_146114_1_)
    {
        byte b0 = 1;

        if (!this.enabled)
        {
            b0 = 0;
        }
        else if (p_146114_1_)
        {
            b0 = 2;
        }

        return b0;
    }

    /**
     * Draws this button to the screen.
     */
    public void drawDropdown(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(textures);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.field_146123_n = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;
            int k = this.getHoverState(this.field_146123_n);
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            drawBoxWithOutline(this.xPosition, this.yPosition, this.width, this.height, 1, 1.0F, 0x404040, 0x202020);
            this.mouseDragged(mc, mouseX, mouseY);
            int l = 14737632;

            if (packedFGColour != 0)
            {
                l = packedFGColour;
            }
            else if (!this.enabled)
            {
                l = 10526880;
            }
            else if (this.field_146123_n)
            {
                l = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.dropdownItems.get(selected), this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2 + 1, l);

            if (open)
            {
                int y = height;

                for (String item : dropdownItems)
                {
                    int renderX = this.xPosition;
                    int renderY = this.yPosition + y;

                    if (mouseX >= renderX && mouseY >= renderY && mouseX < renderX + this.width && mouseY < renderY + this.height)
                    {
                        drawBoxWithOutline(renderX, renderY, this.width, this.height, 1, 1.0F, 0x505050, 0x303030);
                    }
                    else
                    {
                        drawBoxWithOutline(renderX, renderY, this.width, this.height, 1, 1.0F, 0x606060, 0x404040);
                    }

                    drawString(fontrenderer, item, renderX + 3, renderY + height / 2 - 3, 0xFFFFFF);

                    y += height;
                }
            }
        }
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

    /**
     * Fired when the mouse button is dragged. Equivalent of MouseListener.mouseDragged(MouseEvent e).
     */
    protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}

    /**
     * Fired when the mouse button is released. Equivalent of MouseListener.mouseReleased(MouseEvent e).
     */
    public void mouseReleased(int mouseX, int mouseY) {}

    /**
     * Returns true if the mouse has been pressed on this control. Equivalent of MouseListener.mousePressed(MouseEvent
     * e).
     */
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY)
    {
        if (this.enabled && this.visible)
        {
            boolean clickDisplayBox = mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height;

            if (!clickDisplayBox)
            {
                if(open)
                {
                    for (int i = 0; i < dropdownItems.size(); i++)
                    {
                        int renderX = this.xPosition;
                        int renderY = this.yPosition + (this.height - 8) / 2 + (i * this.height) + height;

                        if (mouseX >= renderX && mouseY >= renderY && mouseX < renderX + this.width && mouseY < renderY + this.height)
                        {
                            this.selected = i;
                            this.open = false;

                            return true;
                        }
                    }
                }

                this.open = false;
            }
            else
            {
                this.open = !open;

                return true;
            }
        }

        return false;
    }

    public boolean func_146115_a()
    {
        return this.field_146123_n;
    }

    public void func_146111_b(int p_146111_1_, int p_146111_2_) {}

    public void func_146113_a(SoundHandler sound)
    {
        sound.playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0F));
    }

    public int getButtonWidth()
    {
        return this.width;
    }

    public int func_154310_c()
    {
        return this.height;
    }

    public String getSelected()
    {
        return dropdownItems.get(selected);
    }

    public int getSelectedIndex()
    {
        return selected;
    }

    public void setSelected(int selected)
    {
        this.selected = selected;
    }
}