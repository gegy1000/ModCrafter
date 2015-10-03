package net.gegy1000.modcrafter.json;

import net.gegy1000.modcrafter.mod.sprite.Sprite;
import net.gegy1000.modcrafter.script.Script;
import net.gegy1000.modcrafter.script.parameter.IParameter;

import java.util.ArrayList;
import java.util.List;

public class JsonScript
{
    public int sprite;

    public String defId;

    public JsonScript child;

    public int x;
    public int y;

    public List<Object> parameters = new ArrayList<Object>();

    public JsonScript contained;

    public JsonScript(Script script)
    {
        Sprite sprite = script.getSprite();

        this.defId = script.getScriptDef().getId();

        if (script.getChild() != null)
            this.child = new JsonScript(script.getChild());

        if (script.getContained() != null)
            this.contained = new JsonScript(script.getContained());

        this.x = script.getX();
        this.y = script.getY();

        int parIndex = 0;

        for (Object part : script.getName())
        {
            if (part instanceof IParameter)
            {
                this.parameters.add(((IParameter) part).getData());
            }
        }
    }

    public Script toScript(Sprite sprite, Script parent)
    {
        return new Script(sprite, sprite.getMod(), parent, this);
    }
}
