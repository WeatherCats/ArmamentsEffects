package kipperorigin.armamentseffects.commands;

import org.cubeville.commons.commands.CommandParameterType;

import kipperorigin.armamentseffects.registry.Registry;

public class CommandParameterEventClass implements CommandParameterType
{
    public boolean isValid(String value) {
        return Registry.getInstance().getEventClasses().contains(value);
    }

    public String getInvalidMessage(String value) {
        return "Event class " + value + " does not exist!";
    }

    public Object getValue(String value) {
        return value;
    }
}
