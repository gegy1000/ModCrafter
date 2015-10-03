package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.mod.sprite.SpriteDefManager;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;

public class GuiDialogueCreateSprite extends GuiDialogueBox
{
    private GuiTextField name;

    public GuiDialogueCreateSprite(GuiModCrafterProject project)
    {
        super(project);
    }

    @Override
    public void init()
    {
        this.buttonList.add(new GuiModCrafterButton(0, width / 2 - 90, height / 2 + 25, 50, 20, "Add"));
        this.buttonList.add(new GuiModCrafterButton(1, width / 2 - 35, height / 2 + 25, 50, 20, "Cancel"));

        this.name = new GuiTextField(mc.fontRenderer, width / 2 - 90, height / 2 - 20, 105, 20);
        this.name.setFocused(true);
        this.name.setText("New Sprite");
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        this.name.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen()
    {
        this.name.updateCursorCounter();

        this.buttonList.get(0).enabled = this.name.getText().length() > 0 && !project.loadedMod.hasSpriteWithName(this.name.getText());
    }

    @Override
    public void keyTyped(char character, int key)
    {
        if (this.name.isFocused())
        {
            this.name.textboxKeyTyped(character, key);
        }

        if (key == 28 || key == 156)
        {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if(button.id == 0)
        {
            project.loadedMod.addSprite(new Sprite(SpriteDefManager.item, project.loadedMod, name.getText()));
        }

        closeDialogue();
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        project.drawCenteredString(mc.fontRenderer, "Create Sprite", resolution.getScaledWidth() / 2, resolution.getScaledHeight() / 2 - 45, 0xFFFFFF);

        name.drawTextBox();
    }
}
