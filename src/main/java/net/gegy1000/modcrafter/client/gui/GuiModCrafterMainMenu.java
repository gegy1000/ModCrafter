package net.gegy1000.modcrafter.client.gui;

import net.ilexiconn.llibrary.client.gui.GuiOverride;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMainMenu;

public class GuiModCrafterMainMenu extends GuiOverride
{
    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        int i = this.height / 4 + 48;
        int j = 24;

        this.buttonList.add(new GuiButton(10, this.width / 2 + 104, i + j * 2, 98, 20, "ModCrafter"));
    }

    public void actionPerformed(GuiButton button)
    {
        if (button.id == 10)
        {
            this.mc.displayGuiScreen(new GuiModCrafter((GuiMainMenu) this.overriddenScreen));
        }
    }
}
