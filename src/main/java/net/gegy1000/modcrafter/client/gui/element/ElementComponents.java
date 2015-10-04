package net.gegy1000.modcrafter.client.gui.element;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.client.gui.dialogue.GuiDialogueCreateComponent;
import net.gegy1000.modcrafter.client.gui.dialogue.GuiDialogueModifyProperties;
import net.gegy1000.modcrafter.mod.component.Component;
import org.lwjgl.opengl.GL11;

public class ElementComponents extends Element
{
    public boolean dragging;
    public int draggingStartX;
    public int draggingStartY;

    public ElementComponents(GuiModCrafterProject gui, int x, int y, int width, int height)
    {
        super(gui, x, y, width, height);
    }

    public void mouseMovedOrUp(int mouseX, int mouseY, int event)
    {
        super.mouseMovedOrUp(mouseX, mouseY, event);

        dragging = false;
        draggingStartX = -1;
        draggingStartY = -1;
    }

    public void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);

        draggingStartX = mouseX;
        draggingStartY = mouseY;

        int i = 3;

        if (!dragging && mouseY > yPosition - i && mouseY <= yPosition + i && mouseX <= width + i)
        {
            dragging = true;
        }

        int x = 0;
        int y = 0;
        int componentWidth = parent.componentWidth;

        double scale = ((double) (width) / (double) (componentWidth * 4 + 1));

        componentWidth = (int) (scale * componentWidth - 1);

        if (mouseX > 1 && mouseX < mc.fontRenderer.getCharWidth('+') * scale && mouseY > yPosition + 2 && mouseY < yPosition + (8 * scale))
        {
            parent.openDialogue(new GuiDialogueCreateComponent(parent));
        }

        for (Component component : parent.loadedMod.getComponents())
        {
            int drawY = yPosition + 10 + y;

            if (mouseX < x + componentWidth - scale && mouseX > x)
            {
                if (mouseY > drawY && mouseY < drawY + componentWidth - scale)
                {
                    if (mouseY > drawY + (2 * scale) && mouseY < drawY + (7 * scale) && mouseX > x + (13 * scale)) //TODO doesnt work at all scales
                    {
                        parent.openDialogue(new GuiDialogueModifyProperties(parent));
                    }

                    parent.selectedComponent = component;

                    break;
                }
            }

            x += componentWidth;

            if (x > componentWidth * 3)
            {
                x = 0;
                y += componentWidth;
            }
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick)
    {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);

        if (dragging)
        {
            yPosition = mouseY;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        width = parent.elementScriptSidebar.width;
        height = parent.height - yPosition;

        super.drawScreen(mouseX, mouseY, partialTicks);

        drawRect(0, yPosition, width, 1, 1.0F, 1.0F, 1.0F, 0.2F);
        drawRect(width - 1, yPosition + 1, 1, height, 1.0F, 1.0F, 1.0F, 0.2F);

        int x = 0;
        int y = 0;
        int componentWidth = parent.componentWidth;
        double scale = ((double) (width) / (double) (componentWidth * 4 + 1));

        GL11.glPushMatrix();
        GL11.glTranslated(0, (yPosition + y + 2), 0);
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(0, -(yPosition + y + 2), 0);

        parent.drawString(mc.fontRenderer, "+", 1, yPosition + 2, 0x009922);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        for (Component component : parent.loadedMod.getComponents())
        {
            mc.getTextureManager().bindTexture(parent.widgets);

            int drawY = yPosition + y + 10;

            parent.drawTexturedModalRect(x + 13, drawY + 2, 20, 0, 5, 5);

            if (component == parent.selectedComponent)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
            }

            parent.drawTexturedModalRect(x, drawY, 0, 0, componentWidth - 1, componentWidth - 1);

            String name = component.getName();

            if (name.length() > 15)
            {
                name = name.substring(0, 12) + "...";
            }

            drawScaledString(mc, name, x + 1, drawY + 17, 0xFFFFFF, 0.25F);

            x += componentWidth;

            if (x > componentWidth * 3)
            {
                x = 0;
                y += componentWidth;
            }
        }

        GL11.glPopMatrix();
    }
}