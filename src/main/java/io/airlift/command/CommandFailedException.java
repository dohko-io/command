/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.airlift.command;

import static java.lang.String.format;

public class CommandFailedException extends Exception
{
	/**
	 * Serial code version <code>serialVersionUID</code> for serialization 
	 */
	private static final long serialVersionUID = -8959248506256356537L;

    private final Integer pid;
	private final Integer exitCode;
    private final String output;
    private final Command command;

    public CommandFailedException(Command command, String message, Throwable cause)
    {
        super(exceptionMessage(command, message, cause), cause);
        this.command = command;
        exitCode = null;
        output = null;
        this.pid = null;
    }

    public CommandFailedException(Command command, Integer exitCode, Integer pid, String output)
    {
        super(format("%s exited with %s%n%s", command.getCommand(), exitCode, output));
        this.command = command;
        this.exitCode = exitCode;
        this.output = output;
        this.pid = pid;
    }
    
    public CommandFailedException(Command command, Integer exitCode, Integer pid, String output, Throwable cause)
    {
    	super(format("%s exited with %s%n%s", command.getCommand(), exitCode, output));
    	this.command = command;
    	this.exitCode = exitCode;
    	this.output = output;
    	this.pid = pid;
    }

    public CommandFailedException(Command command, String message, Integer pid, Throwable cause) 
    {
    	this(command, null, pid, message, cause);
	}

	public Command getCommand()
    {
        return command;
    }

    public boolean exited()
    {
        return exitCode != null;
    }

    public Integer getExitCode()
    {
        return exitCode;
    }

    public String getOutput()
    {
        return output;
    }
    
    

    /**
	 * @return the pid
	 */
	public Integer getPid() {
		return pid;
	}

	private static String exceptionMessage(Command command, String message, Throwable cause)
    {
        String s = (cause == null) ? "" : (": " + cause.getMessage());
        return format("%s %s%s", command.getCommand(), message, s);
    }
}
