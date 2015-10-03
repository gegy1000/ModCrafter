package net.gegy1000.modcrafter.client.gui.element;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import org.lwjgl.opengl.GL11;

public class ElementSprites extends Element
{
    public boolean dragging;
    public int draggingStartX;
    public int draggingStartY;

    public ElementSprites(GuiModCrafterProject gui, int x, int y, int width, int height)
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
        int spriteWidth = parent.spriteWidth;
        double scale = 0.0D;

        while ((spriteWidth * 4 - 1) * scale < width)
        {
            scale += 0.001D;
        }

        spriteWidth = (int) (scale * spriteWidth - 1);

        for (Sprite sprite : parent.loadedMod.getSprites())
        {
            int drawY = yPosition + 10 + y;

            if (mouseX < x + spriteWidth - scale && mouseX > x)
            {
                if (mouseY > drawY && mouseY < drawY + spriteWidth - scale)
                {
                    parent.selectedSprite = sprite;

                    break;
                }
            }

            x += spriteWidth;

            if (x > spriteWidth * 3)
            {
                x = 0;
                y += spriteWidth;
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
        int spriteWidth = parent.spriteWidth;
        double scale = ((double) (width) / (double) (spriteWidth * 4 + 1));

        GL11.glPushMatrix();
        GL11.glTranslated(0, (yPosition + y + 2), 0);
        GL11.glScaled(scale, scale, scale);
        GL11.glTranslated(0, -(yPosition + y + 2), 0);

        for (Sprite sprite : parent.loadedMod.getSprites())
        {
            mc.getTextureManager().bindTexture(parent.widgets);

            if (sprite == parent.selectedSprite)
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            }
            else
            {
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
            }

            int drawY = yPosition + y + 10;
            parent.drawTexturedModalRect(x, drawY, 0, 0, spriteWidth - 1, spriteWidth - 1);

            String name = sprite.getName();

            if (name.length() > 15)
            {
                name = name.substring(0, 12) + "...";
            }

            drawScaledString(mc, name, x + 1, drawY + 17, 0xFFFFFF, 0.25F);

            x += spriteWidth;

            if (x > spriteWidth * 3)
            {
                x = 0;
                y += spriteWidth;
            }
        }

        GL11.glPopMatrix();
    }
}