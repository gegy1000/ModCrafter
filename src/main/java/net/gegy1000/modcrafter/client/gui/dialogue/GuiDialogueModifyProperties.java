package net.gegy1000.modcrafter.client.gui.dialogue;

import net.gegy1000.modcrafter.client.gui.GuiDropdown;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterButton;
import net.gegy1000.modcrafter.client.gui.GuiModCrafterProject;
import net.gegy1000.modcrafter.common.modrun.EnumCreativeTab;
import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.mod.component.ComponentDefTexture;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GuiDialogueModifyProperties extends GuiDialogueBox
{
    private List<GuiTextField> textBoxes = new ArrayList<GuiTextField>();
    private List<GuiDropdown> dropdowns = new ArrayList<GuiDropdown>();

    private List<String> names = new ArrayList<String>();

    public GuiDialogueModifyProperties(GuiModCrafterProject project)
    {
        super(project);
    }

    @Override
    public void init()
    {
        this.buttonList.add(new GuiModCrafterButton(0, width / 2 - 90, height / 2 + 85, 50, 20, "Okay"));
        this.buttonList.add(new GuiModCrafterButton(1, width / 2 - 35, height / 2 + 85, 50, 20, "Cancel"));

        this.dropdowns.clear();
        this.names.clear();
        this.textBoxes.clear();

        Map<String, Class> propertyTypes = project.selectedComponent.getComponentDef().getProperties();

        for (Map.Entry<String, Class> propertyType : propertyTypes.entrySet())
        {
            if (propertyType.getValue().isEnum())
            {
                if (propertyType.getValue() == EnumCreativeTab.class) //Don't have this hardcoded in future
                {
                    List<String> selections = new ArrayList<String>();

                    for (EnumCreativeTab tab : EnumCreativeTab.values())
                    {
                        selections.add(tab.getTabName());
                    }

                    GuiDropdown dropdown = new GuiDropdown(dropdowns.size(), width / 2 - 10, height / 2 - 92 + (names.size() * 18), 80, 15, selections);

                    Object property = project.selectedComponent.getProperty(propertyType.getKey());

                    if (property instanceof EnumCreativeTab)
                    {
                        dropdown.setSelected(((EnumCreativeTab) property).ordinal());
                    }

                    dropdowns.add(dropdown);

                    names.add(propertyType.getKey());
                }
            }
            else if (propertyType.getKey().equalsIgnoreCase("Texture") && propertyType.getValue() == String.class)
            {
                List<String> selections = new ArrayList<String>();

                Object property = project.selectedComponent.getProperty(propertyType.getKey());

                int selected = 0;

                int i = 0;

                for (Component component : project.loadedMod.getComponents())
                {
                    if (component.getComponentDef() instanceof ComponentDefTexture)
                    {
                        String name = component.getName();

                        if (property instanceof String && ((String) property).equalsIgnoreCase(name))
                        {
                            selected = i;
                        }

                        selections.add(name);

                        i++;
                    }
                }

                GuiDropdown dropdown = new GuiDropdown(dropdowns.size(), width / 2 - 10, height / 2 - 92 + (names.size() * 18), 80, 15, selections);

                dropdown.setSelected(selected);

                dropdowns.add(dropdown);

                names.add(propertyType.getKey());
            }
        }

//        this.dropdown = new GuiDropdown(0, width / 2 + 30, height / 2 - 18, 60, 15, selections);
//        this.dropdownList.add(dropdown);
//
//        this.name = new GuiTextField(mc.fontRenderer, width / 2 - 90, height / 2 - 20, 105, 20);
//        this.name.setFocused(true);
//        this.name.setText("New Component");

        this.dropdownList.addAll(dropdowns);

        this.dialogueHeight = 225;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        for (GuiTextField textField : textBoxes)
        {
            textField.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void updateScreen()
    {
        for (GuiTextField textField : textBoxes)
        {
            textField.updateCursorCounter();

//            this.buttonList.get(0).enabled = textField.getText().length() > 0;
        }
    }

    @Override
    public void keyTyped(char character, int key)
    {
        for (GuiTextField textField : textBoxes)
        {
            if (textField.isFocused())
            {
                textField.textboxKeyTyped(character, key);
            }
        }

//        if (key == 28 || key == 156)
//        {
//            this.actionPerformed(this.buttonList.get(0));
//        }
    }

    @Override
    public void actionPerformed(GuiButton button)
    {
        if (button.id == 0)
        {
            Map<String, Class> propertyTypes = project.selectedComponent.getComponentDef().getProperties();

            int dropdownIndex = 0;

            for (Map.Entry<String, Class> propertyType : propertyTypes.entrySet())
            {
                if (propertyType.getValue().isEnum())
                {
                    if (propertyType.getValue() == EnumCreativeTab.class) //TODO Don't have this hardcoded in future
                    {
                        project.selectedComponent.setProperty(propertyType.getKey(), EnumCreativeTab.values()[dropdowns.get(dropdownIndex).getSelectedIndex()]);

                        dropdownIndex++;
                    }
                }
            }
        }

        closeDialogue();
    }

    @Override
    public void actionPerformed(GuiDropdown dropdown)
    {

    }

    @Override
    public void render(int mouseX, int mouseY)
    {
        project.drawCenteredString(mc.fontRenderer, "Change Properties for: " + project.selectedComponent.getName(), width / 2, height / 2 - ((dialogueHeight / 2) - 5), 0xFFFFFF);

        for (GuiTextField textField : textBoxes)
        {
            textField.drawTextBox();
        }

        int i = 0;

        for (String name : names)
        {
            project.drawString(mc.fontRenderer, name + ":", width / 2 + (dialogueWidth / 2) - 190, height / 2 - (dialogueHeight / 2) + i * 18 + 25, 0x00FF55);

            i++;
        }
    }
}
