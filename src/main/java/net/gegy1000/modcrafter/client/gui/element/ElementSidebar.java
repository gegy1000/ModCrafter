package net.gegy1000.modcrafter.client.gui.element;

import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.script.ScriptDef;
import org.lwjgl.opengl.GL11;

import java.util.Map.Entry;

public class ElementSidebar extends Element
{
    public boolean dragging;
    public int draggingStartX;
    public int draggingStartY;

    public ElementSidebar(GuiModCrafterProject gui, int x, int y, int width, int height)
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

        if (!dragging && mouseX > width - i && mouseX <= width + i)
        {
            dragging = true;
        }
    }

    public void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick)
    {
        super.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);

        if (dragging)
        {
            width = mouseX;
        }

        if (width < parent.componentWidth)
        {
            width = parent.componentWidth;
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        super.drawScreen(mouseX, mouseY, partialTicks);

        drawRect(width - 1, yPosition, 1, height, 1.0F, 1.0F, 1.0F, 0.2F);
        drawRect(0, 9, width - 1, 1, 1.0F, 1.0F, 1.0F, 0.2F);

        if (parent.selectedComponent != null)
        {
            String s = "Script Selection";
            int i = (int) (parent.getScaledStringWidth(s, 0.75F));

            if (i + 3 < width)
            {
                drawScaledString(mc, s, 2, 2, 0xFFFFFF, 0.75F);
            }
            else
            {
                while (i + 3 >= width && s.length() > 0)
                {
                    s = s.substring(0, s.length() - 1);
                    i = (int) (mc.fontRenderer.getStringWidth(s + "..") * 0.75F);
                }

                if (!s.isEmpty())
                {
                    drawScaledString(mc, s + "..", 2, 2, 0xFFFFFF, 0.75F);
                }
            }

            int y = 12;
            int scriptHeight = parent.defualtScriptHeight;
            double scale = (double) width / (double) (scriptHeight * 4 - 1);

            GL11.glPushMatrix();
            // GL11.glTranslated(0, (yPosition + y + 2), 0);
            // GL11.glScaled(scale, scale, scale);
            // GL11.glTranslated(0, -(yPosition + y + 2), 0);

            for (Entry<String, ScriptDef> entry : ModCrafterAPI.getScriptDefs().entrySet())
            {
                ScriptDef def = entry.getValue();

                if(def.isAllowedFor(parent.selectedComponent))
                {
                    parent.drawScript(def, 2, y, def.getName(), def.getDefualtDisplayName(), 1.0F, width, null);

                    y += entry.getValue().getHeight(null) + 2;
                }
            }

            GL11.glPopMatrix();
        }
    }
}