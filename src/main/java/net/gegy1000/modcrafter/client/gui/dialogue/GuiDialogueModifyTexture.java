package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiDropdown;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.client.gui.GuiSlider;
import net.ilexiconn.llibrary.common.color.ColorHelper;
import net.minecraft.client.gui.GuiButton;

public class GuiDialogueModifyTexture extends GuiDialogueBox
{
    protected int[][] image;

    protected GuiSlider redSlider;
    protected GuiSlider greenSlider;
    protected GuiSlider blueSlider;

    public GuiDialogueModifyTexture(GuiModCrafterProject project)
    {
        super(project);

        redSlider = new GuiSlider(0, width / 2 - 150, height / 2 - 90, 127, 10, 0, 127, 127, 0xFF0000);
        greenSlider = new GuiSlider(0, width / 2 - 150, height / 2 - 75, 127, 10, 0, 127, 127, 0x00FF00);
        blueSlider = new GuiSlider(0, width / 2 - 150, height / 2 - 60, 127, 10, 0, 127, 127, 0x0000BB);
    }

    @Override
    public void init()
    {
        this.buttonList.add(new GuiModCrafterButton(0, width / 2 - 90, height / 2 + 85, 50, 20, "Okay"));
        this.buttonList.add(new GuiModCrafterButton(1, width / 2 - 35, height / 2 + 85, 50, 20, "Cancel"));
        this.buttonList.add(new GuiModCrafterButton(2, width / 2 + 20, height / 2 + 85, 50, 20, "Reset"));

        redSlider.xPosition = width / 2 - 150; redSlider.yPosition = height / 2 - 90;
        greenSlider.xPosition = width / 2 - 150; greenSlider.yPosition = height / 2 - 75;
        blueSlider.xPosition = width / 2 - 150; blueSlider.yPosition = height / 2 - 60;

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

            closeDialogue();
        }
        else if(button.id == 1)
        {
            closeDialogue();
        }
        else if(button.id == 2)
        {
            for (int x = 0; x < 16; x++)
            {
                for (int y = 0; y < 16; y++)
                {
                    image[x][y] = 0xFFFFFF;
                }
            }
        }
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

        redSlider.mouseClicked(mouseX, mouseY, mouseButton);
        greenSlider.mouseClicked(mouseX, mouseY, mouseButton);
        blueSlider.mouseClicked(mouseX, mouseY, mouseButton);

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                int drawX = x * 6 + (width / 2) - 10;
                int drawY = y * 6 + (height / 2) - 35;

                if(mouseX > drawX && mouseX < drawX + 6 && mouseY > drawY && mouseY < drawY + 6)
                {
                    int red = redSlider.slide * 2;
                    int green = greenSlider.slide * 2;
                    int blue = blueSlider.slide * 2;

                    image[x][y] = 256 * 256 * red + 256 * green + blue;
                }
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        project.drawCenteredString(mc.fontRenderer, "Texture Editor", width / 2, height / 2 - ((dialogueHeight / 2) - 5), 0xFFFFFF);

        redSlider.drawSlider(mc, mouseX, mouseY);
        greenSlider.drawSlider(mc, mouseX, mouseY);
        blueSlider.drawSlider(mc, mouseX, mouseY);

        drawScaledRect(width / 2 + 60, height / 2 - 79, 20, 20, 1.0F, redSlider.slide * 2, greenSlider.slide * 2, blueSlider.slide * 2);

        for (int x = 0; x < 16; x++)
        {
            for (int y = 0; y < 16; y++)
            {
                int colour = image[x][y];

                int drawX = x * 6 + (width / 2) - 10;
                int drawY = y * 6 + (height / 2) - 35;

                drawScaledRect(drawX, drawY, 6, 6, 1.0F, colour);

                drawBoxOutline(drawX, drawY, 6, 6, 1, 1.0F, 0x606060);
            }
        }
    }
}
