package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister(loaders = {"crafttweaker", "tags"})
@ZenCodeType.Name("crafttweaker.api.bracket.CommandStringDisplayable")
@Document("vanilla/api/bracket/CommandStringDisplayable")
public interface CommandStringDisplayable {
   @ZenCodeType.Method
   @ZenCodeType.Getter("commandString")
   String getCommandString();
}
