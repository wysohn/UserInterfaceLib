package org.userinterfacelib.command.user;

import java.util.ArrayList;
import java.util.List;

import org.userinterfacelib.command.SubCommand;
import org.userinterfacelib.main.CommandExecutor;

public abstract class SubCommandUser extends SubCommand {
	
	protected SubCommandUser(String name) {
		super(name);
	}

}
