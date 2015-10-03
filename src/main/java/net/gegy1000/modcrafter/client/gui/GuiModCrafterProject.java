package net.gegy1000.modcrafter.client.gui;

import com.google.common.collect.Lists;
import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.client.gui.dialogue.GuiDialogueBox;
import net.gegy1000.modcrafter.client.gui.element.Element;
import net.gegy1000.modcrafter.client.gui.element.ElementComponents;
import net.gegy1000.modcrafter.client.gui.element.ElementSidebar;
import net.gegy1000.modcrafter.client.gui.element.ElementTopBar;
import net.gegy1000.modcrafter.color.ColorHelper;
import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.ModSaveManager;
import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.ScriptDef;
import net.gegy1000.modcrafter.script.ScriptDefContainer;
import net.gegy1000.modcrafter.script.ScriptDefHat;
import net.gegy1000.modcrafter.script.parameter.DataType;
import net.gegy1000.modcrafter.script.parameter.InputParameter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map.Entry;

public class GuiModCrafterProject extends GuiScreen
{
    public GuiModCrafter modCrafterGui;
    public ArrayList<Element> elements = Lists.newArrayList();
    public int backgroundColor = 0x212121;
    public int elementColor = 0x141414;

    public static final ResourceLocation scriptTextures = new ResourceLocation("modcrafter:textures/gui/script/scripts.png");
    public static final ResourceLocation widgets = new ResourceLocation("modcrafter:textures/gui/widgets.png");

    public Mod loadedMod;

    public final int defualtScriptHeight = 11;
    public final int componentWidth = 21;

    public Script holdingScript;

    public int heldOffsetX, heldOffsetY;

    public Script snapping;

    public Component selectedComponent;

    public ElementSidebar elementScriptSidebar;
    public ElementComponents elementComponents;
    public ElementTopBar elementTopBar;

    public TextBox textBox;
    private boolean container;
    public GuiDialogueBox openDialogue;

    public GuiModCrafterProject(GuiModCrafter modCrafterGui, Mod loadedMod)
    {
        this.modCrafterGui = modCrafterGui;
        this.loadedMod = loadedMod;
    }

    @Override
    public void setWorldAndResolution(Minecraft mc, int width, int height)
    {
        super.setWorldAndResolution(mc, width, height);

        if (openDialogue != null)
        {
            openDialogue.setWorldAndResolution(mc, width, height);
        }
    }

    public void openDialogue(GuiDialogueBox dialogue)
    {
        this.openDialogue = dialogue;

        ScaledResolution resolution = new ScaledResolution(mc, mc.displayWidth, mc.displayHeight);

        openDialogue.setWorldAndResolution(mc, resolution.getScaledWidth(), resolution.getScaledHeight());
    }

    public void initGui()
    {
        int i = this.height / 4 + 48;

        selectedComponent = loadedMod.getComponent(0);

        heldOffsetX = 0;
        heldOffsetY = 0;

        this.buttonList.add(new GuiModCrafterButton(0, this.width - 80, this.height - 10 - 20, 72, 20, I18n.format("gui.done", new Object[0])));

        this.elements.clear();
        this.elements.add(this.elementScriptSidebar = new ElementSidebar(this, 0, 0, elementScriptSidebar == null ? 85 : elementScriptSidebar.width, height));
        this.elements.add(this.elementComponents = new ElementComponents(this, 0, elementComponents == null ? height - 66 : elementComponents.yPosition, elementScriptSidebar.width, elementComponents == null ? height - (height - 66) : elementComponents.height));
        this.elements.add(this.elementTopBar = new ElementTopBar(this, elementScriptSidebar.width, 0, width - elementScriptSidebar.width, 10));
    }

    public void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            try
            {
                ModSaveManager.saveMod(loadedMod);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            // TODO ask whether to save
            this.mc.displayGuiScreen(modCrafterGui);
        }
    }

    public void updateScreen()
    {
        super.updateScreen();

        if (textBox != null)
        {
            textBox.updateScreen();
        }

        if (openDialogue != null)
        {
            openDialogue.updateScreen();
        }
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        ColorHelper.setColorFromInt(backgroundColor, 1.0F);
        drawTexturedModalRect(0, 0, 0, 0, width, height);
        GL11.glEnable(GL11.GL_TEXTURE_2D);

        GL11.glEnable(GL11.GL_BLEND);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        for (Element element : elements)
        {
            element.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (selectedComponent != null)
        {
            for (Script script : selectedComponent.getScripts())
            {
                drawScriptAndChild(script);
            }
        }

        if (textBox != null)
        {
            textBox.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (openDialogue != null)
        {
            openDialogue.renderBase(mouseX, mouseY);
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawScriptAndChild(Script script)
    {
        drawScript(script);

        if (script.getChild() != null)
        {
            drawScriptAndChild(script.getChild());
        }
    }

    public void drawScript(Script script)
    {
        int x = script.getX();

        float alpha = 1.0F;

        if ((script.equals(holdingScript) && snapping != null) || x < 0)
        {
            alpha = 0.8F;
        }

        drawScript(script.getScriptDef(), x + elementScriptSidebar.width, script.getY() + elementTopBar.height, script.getName(), script.getDisplayName(), alpha, width, script);
    }

    public void drawScript(ScriptDef def, int xPosition, int yPosition, Object[] name, String displayName, float alpha, int maxWidth, Script script)
    {
        int width = getScriptDrawWidth(displayName);

        int colour = def.getColor();

        float r = (colour & 0xFF0000) >> 16;
        float g = (colour & 0xFF00) >> 8;
        float b = (colour & 0xFF);

        GL11.glColor4f(r / 255.0F, g / 255.0F, b / 255.0F, alpha);

        mc.renderEngine.bindTexture(scriptTextures);

        if (def instanceof ScriptDefHat)
        {
            drawLimitedTexturedModalRect(xPosition, yPosition, 12, 0, 7, 12, maxWidth);

            for (int i = 0; i < 5; i++)
            {
                drawLimitedTexturedModalRect(xPosition + 7 + i, yPosition, 19, 0, 1, 12, maxWidth);
            }

            drawLimitedTexturedModalRect(xPosition + 12, yPosition, 20, 0, 1, 12, maxWidth);

            for (int i = 5; i < width; i++)
            {
                drawLimitedTexturedModalRect(xPosition + 8 + (i), yPosition, 21, 0, 1, 12, maxWidth);
            }

            drawLimitedTexturedModalRect(xPosition + 8 + width, yPosition, 22, 0, 1, 12, maxWidth);

            yPosition++;
        }
        else if (def instanceof ScriptDefContainer)
        {
            int gap = def.getHeight(script) - 22;

            drawLimitedTexturedModalRect(xPosition, yPosition, 24, 0, 12, 11, maxWidth);
            drawLimitedTexturedModalRect(xPosition, yPosition + gap + defualtScriptHeight, 24, 12, 12, 10, maxWidth);

            for (int i = 0; i < gap; i++)
            {
                drawLimitedTexturedModalRect(xPosition, yPosition + i + defualtScriptHeight, 24, 11, 6, 1, maxWidth);
            }

            for (int i = 0; i < width; i++)
            {
                drawLimitedTexturedModalRect(xPosition + 12 + (i), yPosition, 36, 0, 1, 10, maxWidth);
                drawLimitedTexturedModalRect(xPosition + 12 + (i), yPosition + gap + defualtScriptHeight, 36, 12, 1, 10, maxWidth);
            }

            drawLimitedTexturedModalRect(xPosition + 12 + width, yPosition, 37, 0, 1, 10, maxWidth);
            drawLimitedTexturedModalRect(xPosition + 12 + width, yPosition + gap + defualtScriptHeight, 37, 12, 1, 10, maxWidth);
        }
        else
        {
            drawLimitedTexturedModalRect(xPosition, yPosition, 0, 0, 7, 12, maxWidth);

            for (int i = 0; i < width; i++)
            {
                drawLimitedTexturedModalRect(xPosition + 7 + (i), yPosition, 7, 0, 1, 12, maxWidth);
            }

            drawLimitedTexturedModalRect(xPosition + 7 + width, yPosition, 9, 0, 1, 12, maxWidth);
        }

        int x = xPosition + 2;

        int parameter = 0;

        for (Object object : name)
        {
            if (object instanceof InputParameter)
            {
                InputParameter inputParameter = (InputParameter) object;

                String string = inputParameter.getData().toString();

                int textWidth = getScaledStringWidth(string + " ", 0.5F);

                if (inputParameter.getDataType() == DataType.TEXT)
                {
                    drawLimitedRect(x - 1, yPosition + 2, textWidth + 1, 6, 1F, 1F, 1F, 0.7F * alpha, maxWidth);
                }

                if (!drawScaledLimitedString(mc, string, x, yPosition + 3, 0xCCCCCC, 0.5F, maxWidth))
                {
                    break;
                }

                x += textWidth;

                parameter++;
            }
            else
            {
                if (drawScaledLimitedString(mc, object.toString(), x, yPosition + 3, 0xFFFFFF, 0.5F, maxWidth))
                    x += getScaledStringWidth(object.toString() + " ", 0.5F);
                else
                    break;
            }
        }
    }

    private void drawLimitedTexturedModalRect(int x, int y, int u, int v, int width, int height, int maxWidth)
    {
        maxWidth--;

        if (x + width > maxWidth)
        {
            for (int i = 0; i < width; i++)
            {
                if (x + i < maxWidth)
                    drawTexturedModalRect(x + i, y, u + i, v, 1, height);
                else
                    break;
            }
        }
        else
            drawTexturedModalRect(x, y, u, v, width, height);
    }

    private void drawLimitedRect(int x, int y, int sizeX, int sizeY, float r, float g, float b, float a, int maxWidth)
    {
        maxWidth--;

        if (x + sizeX > maxWidth)
        {
            for (int i = 0; i < sizeX; i++)
            {
                if (x + i < maxWidth)
                    drawRect(x + i, y, 1, sizeY, r, g, b, a);
                else
                    break;
            }
        }
        else
            drawRect(x, y, sizeX, sizeY, r, g, b, a);
    }

    private boolean drawScaledLimitedString(Minecraft mc, String text, int x, int y, int color, float scale, int maxWidth)
    {
        maxWidth -= 2;

        int width = getScaledStringWidth(text, scale);

        if (x + width > maxWidth)
        {
            while (x + width >= maxWidth && text.length() > 0)
            {
                text = text.substring(0, text.length() - 1);

                width = getScaledStringWidth(text + "..", scale);
            }

            // for (int i = 0; i < text.length(); i++)
            // {
            // char charAt = text.charAt(i);
            // int charWidth = (int) ((float) fontRendererObj.getCharWidth(charAt) * 0.5F);
            //
            // if(x + charWidth < maxWidth)
            // {
            // drawScaledString(mc, String.valueOf(charAt), x, y, color, scale);
            //
            // x += charWidth;
            // }
            // else
            // {
            // drawScaledString(mc, "..", x, y, color, scale);
            // return false;
            // }
            // }

            if (!text.isEmpty())
            {
                drawScaledString(mc, text + "..", x, y, color, scale);
            }
        }
        else
        {
            drawScaledString(mc, text, x, y, color, scale);
        }

        return true;
    }

    private int getScriptDrawWidth(String displayName)
    {
        return (int) ((float) fontRendererObj.getStringWidth(displayName) * 0.5F) - 6;
    }

    private int getScriptWidth(String displayName)
    {
        return (int) ((float) fontRendererObj.getStringWidth(displayName) * 0.5F);
    }

    public int getScaledStringWidth(String displayName, float scale)
    {
        return (int) ((float) fontRendererObj.getStringWidth(displayName) * (float) scale);
    }

    private void drawScaledString(Minecraft mc, String text, float x, float y, int color, float scale)
    {
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        GL11.glTranslatef(x / scale, y / scale, 0);
        drawString(mc.fontRenderer, text, 0, 0, color);
        GL11.glPopMatrix();
    }

    protected void mouseClickMove(int mouseX, int mouseY, int lastButtonClicked, long timeSinceMouseClick)
    {
        for (Element element : elements)
        {
            element.mouseClickMove(mouseX, mouseY, lastButtonClicked, timeSinceMouseClick);
        }

        if (holdingScript != null && textBox == null)
        {
            dragScripts(mouseX, mouseY);
        }
    }

    private void dragScripts(int mouseX, int mouseY)
    {
        int x = mouseX + heldOffsetX;
        int y = mouseY + heldOffsetY;

        snapping = null;

        for (Script script : selectedComponent.getScriptsAndChildren())
        {
            if (script != holdingScript && holdingScript.getScriptDef().canAttachTo(script) && (script.getChild() == null || script.getChild() == holdingScript))
            {
                container = false;

                if (script.getScriptDef() instanceof ScriptDefContainer)
                {
                    int yDiff = Math.abs(y - (script.getY() + defualtScriptHeight));

                    if (yDiff <= 4)
                    {
                        int sWidth = getScriptWidth(script.getDisplayName());

                        if (x > script.getX() + 2 && x + sWidth < script.getX() + sWidth - 2 + 6)
                        {
                            x = script.getX() + 5;
                            y = script.getY() + defualtScriptHeight - 1;

                            snapping = script;
                            container = true;

                            break;
                        }
                    }
                }

                if (snapping == null)
                {
                    int yDiff = Math.abs(y - (script.getY() + script.getScriptDef().getHeight(script)));

                    if (yDiff <= 4)
                    {
                        int sWidth = getScriptWidth(script.getDisplayName());

                        if (x > script.getX() - 4 && x + sWidth < script.getX() + sWidth + 4)
                        {
                            x = script.getX();
                            y = script.getY() + script.getScriptDef().getHeight(script) - 1;

                            snapping = script;

                            break;
                        }
                    }
                }
            }
        }

        if (y < 0)
            y = 0;

        moveChild(holdingScript, x, y);

        holdingScript.setPosition(x, y);
    }

    private void moveChild(Script script, int x, int y)
    {
        if (script.getChild() != null)
        {
            y += script.getScriptDef().getHeight(script) - 1;

            script.getChild().setPosition(x, y);

            moveChild(script.getChild(), x, y);

            // Script contained = script.getContained();
            //
            // if(contained != null)
            // {
            // x += 5;
            // y -= (script.getScriptDef().getHeight(script)) - defualtScriptHeight;
            //
            // contained.setPosition(x, y);
            // moveChild(contained, x, y);
            // }
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int button)
    {
        super.mouseClicked(mouseX, mouseY, button);

        for (Element element : elements)
        {
            element.mouseClicked(mouseX, mouseY, button);
        }

        if (openDialogue != null)
        {
            openDialogue.mouseClicked(mouseX, mouseY, button);
        }

        if (textBox != null)
        {
            textBox.mouseClicked(mouseX, mouseY, button);
        }

        if (holdingScript == null)
        {
            if (selectedComponent != null)
            {
                for (Script script : selectedComponent.getScriptsAndChildren())
                {
                    int width = getScriptWidth(script.getDisplayName());

                    int x = script.getX() + elementScriptSidebar.width;
                    int y = script.getY() + elementTopBar.height;

                    if (mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + script.getScriptDef().getHeight(script))
                    {
                        holdingScript = script;

                        heldOffsetX = x - elementScriptSidebar.width - mouseX;
                        heldOffsetY = y - elementTopBar.height - mouseY;

                        if (snapping != null)
                        {
                            if (holdingScript.equals(snapping.getContained()))
                            {
                                snapping.setContained(null);
                            }
                        }

                        snapping = null;

                        break;
                    }
                }

                if (holdingScript == null)
                {
                    int y = 12;

                    for (Entry<String, ScriptDef> entry : ModCrafterAPI.getScriptDefs().entrySet())
                    {
                        ScriptDef def = entry.getValue();

                        if (def.isAllowedFor(selectedComponent))
                        {
                            int width = getScriptWidth(def.getDefualtDisplayName());

                            if (mouseX >= 2 && mouseX <= width && mouseY >= y && mouseY <= y + def.getHeight(null))
                            {
                                holdingScript = new Script(selectedComponent, def, null);

                                heldOffsetX = 2 - mouseX - elementScriptSidebar.width;
                                heldOffsetY = y - mouseY - elementTopBar.height;

                                holdingScript.setPosition(mouseX + heldOffsetX, mouseY + heldOffsetY);

                                snapping = null;

                                break;
                            }

                            y += def.getHeight(null) + 2;
                        }
                    }
                }

                if (holdingScript != null)
                {
                    dragScripts(mouseX, mouseY);
                    Script script = holdingScript;
                    int xPos = script.getX() + elementScriptSidebar.width;
                    int yPos = script.getY() + elementTopBar.height;
                    int x = xPos + 2;

                    int par = 0;

                    for (Object object : script.getName())
                    {
                        if (object instanceof InputParameter)
                        {
                            InputParameter inputParameter = (InputParameter) script.getParameter(par);
                            String string = inputParameter.getData().toString();
                            int textWidth = getScaledStringWidth(string + " ", 0.5F);

                            if (inputParameter.getDataType() == DataType.TEXT)
                            {
                                if (xPos >= elementScriptSidebar.width && mouseX > x - 1 && mouseX <= x - 1 + textWidth + 1)
                                {
                                    if (mouseY > yPos && mouseY <= yPos + script.getScriptDef().getHeight(script) && textBox == null)
                                    {
                                        textBox = new TextBox(this, x - 2, yPos - 17, textWidth * 2 + 4, 12, inputParameter);
                                        textBox.text = string;
                                    }
                                }
                            }

                            par++;

                            x += textWidth;
                        }
                        else
                        {
                            x += getScaledStringWidth(object.toString() + " ", 0.5F);
                        }
                    }
                }
            }
        }
    }

    protected void keyTyped(char c, int key)
    {
        super.keyTyped(c, key);

        if (textBox != null)
        {
            textBox.keyTyped(c, key);
        }

        if (openDialogue != null)
        {
            openDialogue.keyTyped(c, key);
        }
    }

    private void drawRect(int x, int y, int sizeX, int sizeY, float r, float g, float b, float a)
    {
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glColor4f(r, g, b, a);
        drawTexturedModalRect(x, y, 0, 0, sizeX, sizeY);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
    }

    protected void mouseMovedOrUp(int mouseX, int mouseY, int event)
    {
        super.mouseMovedOrUp(mouseX, mouseY, event);

        for (Element element : elements)
        {
            element.mouseMovedOrUp(mouseX, mouseY, event);
        }

        if (holdingScript != null)
        {
            if (container)
            {
                if (snapping != null)
                {
                    snapping.setContained(holdingScript);
                }

                container = false;
            }
            else
            {
                holdingScript.setParent(snapping);
            }

            if (holdingScript.getX() < 0)
            {
                selectedComponent.removeScript(holdingScript);
            }
        }

        holdingScript = null;
        heldOffsetX = 0;
        heldOffsetY = 0;
    }
}
