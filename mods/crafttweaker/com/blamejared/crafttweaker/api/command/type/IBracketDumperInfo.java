package com.blamejared.crafttweaker.api.command.type;

import com.mojang.brigadier.Command;
import java.util.stream.Stream;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.network.chat.MutableComponent;

public interface IBracketDumperInfo extends Command<CommandSourceStack> {
   String subCommandName();

   MutableComponent description();

   String dumpedFileName();

   Stream<String> values();
}
