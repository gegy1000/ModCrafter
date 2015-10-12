package net.gegy1000.modcrafter.script.parameter;

public class InputParameter implements IParameter
{
    private Object data;
    private DataType dataType;

    public InputParameter(Object data)
    {
        this.setData(data);
    }

    public void setData(Object data)
    {
        this.data = data;

        if (data instanceof Boolean)
        {
            dataType = DataType.BOOLEAN;
        }
        else if (data instanceof Float || data instanceof Double || data instanceof Integer || data instanceof Long)
        {
            dataType = DataType.NUMBER;
        }
        else if (data instanceof String)
        {
            dataType = DataType.TEXT;
        }
    }

    public Object getData()
    {
        return data;
    }

    @Override
    public DataType getDataType()
    {
        return dataType;
    }
}
