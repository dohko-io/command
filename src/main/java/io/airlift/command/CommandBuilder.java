package io.airlift.command;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import io.airlift.units.Duration;

public final class CommandBuilder 
{
	private String id;
	private List<String> command = new ArrayList<>();
    private Set<Integer> successfulExitCodes = new HashSet<>();
    private File directory;
    private Map<String, String> environment = new HashMap<>();
    private Duration timeLimit;
    private List<Object> listeners = new ArrayList<>();
    
    private CommandBuilder(){}
    
    public static CommandBuilder newCommandBuilder()
    {
    	return new CommandBuilder();
    }
    
//    public static CommandBuilder newNullCommandBuilder()
//    {
//    	return new CommandBuilder();
//    }
    
    public CommandBuilder setId(UUID id)
    {
    	if (id != null )
    	{
    		this.id = id.toString();
    	}
    	
    	return this;
    }
    
    public CommandBuilder setId(String id)
    {
    	this.id = id;
    	return this;
    }
    
    public CommandBuilder setDirectory(File directory)
    {
    	this.directory = directory;
    	return this;
    }
    
    public CommandBuilder setCommands(String ... commands)
    {
    	this.command = Arrays.asList(commands);
    	return this;
    }
    
    public CommandBuilder setCommands(List<String> commands)
    {
    	if (commands != null)
    	{
        	this.command.addAll(commands);
    	}
    	
    	return this;
    }
    
    public CommandBuilder setTimeout(Duration duration)
    {
    	this.timeLimit = duration;
    	return this;
    }
    
    public CommandBuilder setTimeout(double value, TimeUnit timeUnit)
    {
		return setTimeout(new Duration(value, timeUnit));
    }
    
    public CommandBuilder setSuccessfulExitCodes(Integer ... successfulExitCodes)
    {
    	if (successfulExitCodes != null)
    	{
    		for (Integer code: successfulExitCodes)
        	{
        		this.successfulExitCodes.add(code);
        	}
    	}
    	return this;
    }
    
    public CommandBuilder addEnviromentVariable(String name, String value)
    {
    	Preconditions.checkArgument(Strings.isNullOrEmpty(name), "Variable name is null");
    	
    	environment.put(name, value);
    	return this;
    }
    
    public  CommandBuilder registerListeners(Object ... listeners)
    {
    	if (listeners != null)
    	{
    		for (Object listener: listeners)
    		{
    			this.listeners.add(listener);
    		}
    		
    	}
    	
    	return this;
    }
    
    
    public Command build()
    {
		return new Command(this.id, command, successfulExitCodes, directory, environment, timeLimit, listeners);
    }
}
