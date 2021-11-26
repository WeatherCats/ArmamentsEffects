package org.cubeville.effects.commands;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cubeville.commons.commands.CommandParameterBoolean;
import org.cubeville.commons.commands.CommandParameterDouble;
import org.cubeville.commons.commands.CommandParameterList;
import org.cubeville.commons.commands.CommandParameterListDouble;
import org.cubeville.commons.commands.CommandParameterType;
import org.cubeville.effects.managers.sources.value.ConstantValueSource;
import org.cubeville.effects.managers.sources.value.LinearValueSource;
import org.cubeville.effects.managers.sources.value.ListValueSource;
import org.cubeville.effects.managers.sources.value.RandomValueSource;
import org.cubeville.effects.managers.sources.value.SinewaveValueSource;
import org.cubeville.effects.managers.sources.value.ValueSource;

public class CommandParameterValueSource implements CommandParameterType
{
    // constant(3.0)
    // linear(1.0,0.01)
    // sinewave(0,36,0.0,1.0,false)
    // random(0.0,10.0)
    // list(1,2,3,4,5)
    
    Map<String, CommandParameterList> parameterLists;

    public CommandParameterValueSource() {
        parameterLists = new HashMap<>();

        List<CommandParameterType> constList = new ArrayList<>();;
        constList.add(new CommandParameterDouble());
        parameterLists.put("constant", new CommandParameterList(constList));

        List<CommandParameterType> linearList = new ArrayList<>();;
        linearList.add(new CommandParameterDouble());
        linearList.add(new CommandParameterDouble());
        parameterLists.put("linear", new CommandParameterList(linearList));

        List<CommandParameterType> sinewaveList = new ArrayList<>();
        sinewaveList.add(new CommandParameterDouble());
        sinewaveList.add(new CommandParameterDouble());
        sinewaveList.add(new CommandParameterDouble());
        sinewaveList.add(new CommandParameterDouble());
        sinewaveList.add(new CommandParameterBoolean());
        parameterLists.put("sinewave", new CommandParameterList(sinewaveList));

        List<CommandParameterType> listList = new ArrayList<>();
        listList.add(new CommandParameterListDouble(","));
        parameterLists.put("list", new CommandParameterList(listList));

	List<CommandParameterType> randomList = new ArrayList<>();
	randomList.add(new CommandParameterDouble());
	randomList.add(new CommandParameterDouble());
	parameterLists.put("random", new CommandParameterList(randomList));
    }
    
    public boolean isValid(String value) {
        if(!value.endsWith(")")) return false;
        if(value.indexOf("(") == -1) return false;
        String type = value.substring(0, value.indexOf("("));
        String val = value.substring(value.indexOf("(") + 1, value.length() - 1);
        for(String key: parameterLists.keySet()) {
            if(type.equals(key)) {
                return parameterLists.get(key).isValid(val);
            }
        }
        return false;
    }

    public String getInvalidMessage(String value) {
        return "Invalid list parameter.";
    }

    public ValueSource getValue(String value) {
        String type = value.substring(0, value.indexOf("("));
        String val = value.substring(value.indexOf("(") + 1, value.length() - 1);
        if(type.equals("constant")) {
            List<Object> parameters = (List<Object>) parameterLists.get("constant").getValue(val);
            return new ConstantValueSource((double) parameters.get(0));
        }
        else if(type.equals("linear")) {
            List<Object> parameters = (List<Object>) parameterLists.get("linear").getValue(val);
            return new LinearValueSource((double) parameters.get(0),
                                         (double) parameters.get(1));
        }
        else if(type.equals("sinewave")) {
            List<Object> parameters = (List<Object>) parameterLists.get("sinewave").getValue(val);
            return new SinewaveValueSource((double) parameters.get(0),
                                           (double) parameters.get(1),
                                           (double) parameters.get(2),
                                           (double) parameters.get(3),
                                           (boolean) parameters.get(4));
        }
        else if(type.equals("list")) {
            List<Object> parameters = (List<Object>) parameterLists.get("list").getValue(val);
            return new ListValueSource((List<Double>) parameters.get(0));
        }
	else if(type.equals("random")) {
	    List<Object> parameters = (List<Object>) parameterLists.get("random").getValue(val);
	    return new RandomValueSource((double) parameters.get(0), (double) parameters.get(1));
	}
        return null;
    }
}
