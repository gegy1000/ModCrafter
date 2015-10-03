package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.mod.sprite.SpriteDefManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.lang3.RandomStringUtils;

public class GuiDialogueCreateSprite extends GuiDialogueBox
{
    public GuiDialogueCreateSprite(GuiModCrafterProject project)
    {
        super(project);

        this.buttonList.add(new GuiModCrafterButton(0, 150, 150, 50, 20, "Add"));
        this.buttonList.add(new GuiModCrafterButton(1, 210, 150, 50, 20, "Cancel"));
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            project.loadedMod.addSprite(new Sprite(SpriteDefManager.item, project.loadedMod, "Item" + RandomStringUtils.randomAscii(5)));
        }

        closeDialogue();
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        project.drawCenteredString(mc.fontRenderer, "Create Sprite", resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2 - 45, 0xFFFFFF);
    }
}
