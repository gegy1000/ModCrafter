package net.gegy1000.modcrafter.script;

import net.gegy1000.modcrafter.ModCrafterAPI;
import net.gegy1000.modcrafter.json.JsonScript;
import net.gegy1000.modcrafter.mod.Mod;
import net.gegy1000.modcrafter.mod.component.Component;
import net.gegy1000.modcrafter.script.parameter.IParameter;
import net.gegy1000.modcrafter.script.parameter.InputParameter;

public class Script
{
    private ScriptDef def;

    private Script parent;
    private Script child;
    private Script contained;

    private Object[] name;

    private Component component;

    private int x;
    private int y;

    private Mod mod;

    public Script(Component component, ScriptDef def, Script parent, boolean addToComponent)
    {
        this.component = component;
        this.def = def;
        this.parent = parent;
        this.mod = component.getMod();
        this.name = def.getName();

        if (addToComponent)
        {
            this.component.addScript(this); // TODO add child sort of thing, but called contained, not list as that contained stores it's children in scriptder, boolean isContafiner
        }
    }

    public Script(Component component, ScriptDef def, Script parent)
    {
        this(component, def, parent, true);
    }

    public void execute()
    {
        def.execute(this);

        // TODO execute contained
        Script child = getChild();

        if (child != null)
        {
            child.execute();
        }
    }

    public Script(Component component, Mod mod, Script parent, JsonScript jsonScript)
    {
        this.def = ModCrafterAPI.getScriptById(jsonScript.defId);
        this.parent = parent;

        if (jsonScript.child != null)
            this.child = new Script(component, mod, this, jsonScript.child);

        if (jsonScript.contained != null)
            this.contained = new Script(component, mod, this, jsonScript.contained);

        this.component = component;
        this.name = def.getName();

        if (jsonScript.parameters != null)
        {
            int parameterId = 0;

            for (int i = 0; i < name.length; i++)
            {
                if (name[i] instanceof IParameter)
                {
                    name[i] = new InputParameter(jsonScript.parameters.get(parameterId)); // TODO save type of par? or override with hat script?

                    parameterId++;
                }
            }
        }

        this.setPosition(jsonScript.x, jsonScript.y);
        this.mod = mod;
    }

    public void setPosition(int x, int y)
    {
        this.x = x;
        this.y = y;
    }

    public int getX()
    {
        return x;
    }

    public int getY()
    {
        return y;
    }

    public void setChild(Script child)
    {
        Script oldChild = this.child;

        this.child = child;

        if (oldChild != null)
        {
            if (oldChild.getParent() != null && !areEqual(oldChild, child))
            {
                oldChild.setParent(null);
            }
        }

        if (child != null)
        {
            if (!areEqual(this, child.parent))
            {
                child.setParent(this);
            }
        }
    }

    public static boolean areEqual(Script script1, Script script2)
    {
        if (script1 != null && script2 != null)
        {
            return script1.component.getScriptId(script1) == script2.component.getScriptId(script2);
        }

        return false;
    }

    public static Script copy(Script script)
    {
        Script newScript = null;

        if (script != null)
        {
            newScript = new Script(script.component, script.def, script.parent, false);
            newScript.x = script.x;
            newScript.y = script.y;
            newScript.contained = script.contained;
            newScript.child = script.child;
            newScript.parent = script.parent;
            newScript.name = script.name;
        }

        return newScript;
    }

    public void setContained(Script contained)
    {
        Script oldContained = copy(this.contained);

        this.contained = contained;

        if (oldContained != null)
        {
            if (oldContained.getParent() != null && !areEqual(oldContained, contained))
            {
                oldContained.setParent(null);
            }
        }

        if (contained != null)
        {
            if (!areEqual(this, contained.parent))
            {
                contained.setParent(this);
            }
        }
    }

    public final Script getChild()
    {
        return child;
    }

    public Script getParent()
    {
        return parent;
    }

    public void setParent(Script parent)
    {
        Script oldParent = this.parent;

        this.parent = parent;

        if (oldParent != null)
        {
            if (oldParent.getChild() != null && !areEqual(oldParent, parent))
            {
                oldParent.setChild(null);
            }
        }

        if (parent != null)
        {
            if (!areEqual(this, parent.child))
            {
                parent.setChild(this);
            }
        }

        if (oldParent == null && this.parent != null)
        {
            component.removeScript(this);
        }
        else if (oldParent != null && this.parent == null)
        {
            component.addScript(this);
        }
    }

    public IParameter getParameter(int index)
    {
        int parIndex = 0;

        for (Object namePart : name)
        {
            if (namePart instanceof IParameter)
            {
                if (parIndex == index)
                {
                    return (IParameter) namePart;
                }

                parIndex++;
            }
        }

        return null;
    }

    public String getDisplayName()
    {
        String displayName = "";

        for (Object namePart : name)
        {
            if (namePart instanceof IParameter)
                displayName += ((IParameter) namePart).getData();
            else
                displayName += namePart;

            displayName += " ";
        }

        return displayName;
    }

    public ScriptDef getScriptDef()
    {
        return def;
    }

    public Component getComponent()
    {
        return component;
    }

    public Object[] getName()
    {
        return name;
    }

    public Script getContained()
    {
        return contained;
    }
}
