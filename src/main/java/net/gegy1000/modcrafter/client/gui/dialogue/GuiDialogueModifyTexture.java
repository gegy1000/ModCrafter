package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiDropdown;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.opengl.GL11;

public class GuiDialogueModifyTexture extends GuiDialogueBox
{
    protected int[][] image;

    public GuiDialogueModifyTexture(GuiModCrafterProject project)
    {
        super(project);
    }

    @Override
    public void init()
    {
        this.buttonList.add(new GuiModCrafterButton(0, width / 2 - 90, height / 2 + 85, 50, 20, "Okay"));
        this.buttonList.add(new GuiModCrafterButton(1, width / 2 - 35, height / 2 + 85, 50, 20, "Cancel"));

        this.dialogueHeight = 225;

        if (image == null)
        {
            loadImage();
        }
    }

    private void loadImage()
    {
        image = new int[16][16];

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                Object property = project.selectedComponent.getProperty("X;" + x + "Y;" + y);

                if(property == null)
                {
                    property = 0xFFFFFF;
                }

                image[x][y] = (int) property;
            }
        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            for (int x = 0; x < 16; x++)
            {
                for (int y = 0; y < 16; y++)
                {
                    project.selectedComponent.setProperty("X;" + x + "Y;" + y, image[x][y]);
                }
            }
        }

        closeDialogue();
    }

    @Override
    public void actionPerformed(GuiDropdown dropdown)
    {

    }

    /**
     * Called when the mouse is clicked.
     */
    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                int drawX = x * 6 + (width / 2) - 10;
                int drawY = y * 6 + (height / 2) - 50;

                if(mouseX > drawX && mouseX < drawX + 6 && mouseY > drawY && mouseY < drawY + 6)
                {
                    image[x][y] = 0x000000;
                }
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        project.drawCenteredString(mc.fontRenderer, "Texture Editor", width / 2, height / 2 - ((dialogueHeight / 2) - 5), 0xFFFFFF);

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                int colour = image[x][y];

                int drawX = x * 6 + (width / 2) - 10;
                int drawY = y * 6 + (height / 2) - 50;

                drawScaledRect(drawX, drawY, 6, 6, 1.0F, colour);

                drawBoxOutline(drawX, drawY, 6, 6, 1, 1.0F, 0x606060);
            }
        }
    }
}
