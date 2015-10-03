package net.gegy1000.modcrafter.client.gui;

import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.ModSaveManager;
import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.mod.sprite.SpriteDefMod;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class GuiCreateMod extends GuiScreen
{
    private GuiModCrafter modCraft;

    public static final ResourceLocation background = new ResourceLocation("modcrafter:textures/gui/background.png");

    private GuiTextField name;

    public GuiCreateMod(GuiModCrafter modCrafter)
    {
        this.modCraft = modCrafter;
    }

    public void initGui()
    {
        int i = this.height / 4 + 48;

        this.buttonList.add(new GuiModCrafterButton(0, 10, this.height - 10 - 20, 72, 20, I18n.format("gui.cancel", new Object[0])));
        this.buttonList.add(new GuiModCrafterButton(1, 84, this.height - 10 - 20, 72, 20, "Create"));

        this.name = new GuiTextField(this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.name.setFocused(true);
        this.name.setText("New Mod");
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char p_73869_1_, int key)
    {
        if (this.name.isFocused())
        {
            this.name.textboxKeyTyped(p_73869_1_, key);
        }

        if (key == 28 || key == 156)
        {
            this.actionPerformed((GuiButton) this.buttonList.get(1));
        }

        ((GuiButton) this.buttonList.get(1)).enabled = this.name.getText().length() > 0;
    }

    /**
     * Called when the mouse is clicked.
     */
    protected void mouseClicked(int mouseX, int mouseY, int p_73864_3_)
    {
        super.mouseClicked(mouseX, mouseY, p_73864_3_);

        this.name.mouseClicked(mouseX, mouseY, p_73864_3_);
    }

    public void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            this.mc.displayGuiScreen(modCraft);
        }
        else if (button.id == 1)
        {
            Mod mod = new Mod(name.getText());
            mod.addSprite(new Sprite(new SpriteDefMod(), mod, "Mod"));

            try
            {
                ModSaveManager.saveMod(mod);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            this.mc.displayGuiScreen(modCraft);
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int p_73863_1_, int p_73863_2_, float p_73863_3_)
    {
        this.drawDefaultBackground();
        name.drawTextBox();
        this.drawCenteredString(this.fontRendererObj, "ModCrafter", this.width / 2, 5, 0xFFFFFFFF);
        super.drawScreen(p_73863_1_, p_73863_2_, p_73863_3_);
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.name.updateCursorCounter();
    }

    /**
     * Draws the background (i is always 0 as of 1.2.2)
     */
    public void drawBackground(int p_146278_1_)
    {
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        Tessellator tessellator = Tessellator.instance;
        this.mc.getTextureManager().bindTexture(background);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        float f = 32.0F;
        tessellator.startDrawingQuads();
        // tessellator.setColorOpaque_I(4210752);
        tessellator.addVertexWithUV(0.0D, (double) this.height, 0.0D, 0.0D, (double) ((float) this.height / f + (float) p_146278_1_));
        tessellator.addVertexWithUV((double) this.width, (double) this.height, 0.0D, (double) ((float) this.width / f), (double) ((float) this.height / f + (float) p_146278_1_));
        tessellator.addVertexWithUV((double) this.width, 0.0D, 0.0D, (double) ((float) this.width / f), (double) p_146278_1_);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, 0.0D, (double) p_146278_1_);
        tessellator.draw();
    }
}
