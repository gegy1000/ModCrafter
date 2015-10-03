package net.gegy1000.modcrafter.client.gui.element;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;

public class ElementTopBar extends Element
{
    public ElementTopBar(GuiModCrafterProject gui, int x, int y, int width, int height)
    {
        super(gui, x, y, width, height);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        width = parent.width - parent.elementScriptSidebar.width;
        xPosition = parent.elementScriptSidebar.width;

        super.drawScreen(mouseX, mouseY, partialTicks);

        if (parent.selectedComponent != null)
        {
            String componentType = parent.selectedComponent.getComponentDef().getDisplayName();
            String title = parent.selectedComponent == null ? "ModCrafter" : parent.selectedComponent.getName() + " - " + parent.loadedMod.getName();
            drawScaledString(mc, componentType, parent.width - parent.getScaledStringWidth(componentType, 0.75F) - 1, 2, 0xFFFFFF, 0.75F);

            drawScaledString(mc, title, parent.elementScriptSidebar.width + 3, 2, 0xFFFFFF, 0.75F);

            drawRect(xPosition, height - 1, width, 1, 1.0F, 1.0F, 1.0F, 0.2F);
        }
    }
}