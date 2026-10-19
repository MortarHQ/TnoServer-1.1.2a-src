package de.ellpeck.naturesaura.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public final class CommandAura {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("naaura")
                     .requires(s -> s.hasPermission(2)))
                  .then(Commands.literal("add").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes(context -> {
                     int amount = IntegerArgumentType.getInteger(context, "amount");
                     CommandSourceStack source = (CommandSourceStack)context.getSource();
                     BlockPos pos = BlockPos.containing(source.getPosition());

                     while (amount > 0) {
                        BlockPos spot = IAuraChunk.getLowestSpot(source.getLevel(), pos, 35, pos);
                        amount -= IAuraChunk.getAuraChunk(source.getLevel(), spot).storeAura(spot, amount);
                     }

                     source.sendSuccess(() -> Component.literal("Added aura to area"), true);
                     return 0;
                  }))))
               .then(Commands.literal("remove").then(Commands.argument("amount", IntegerArgumentType.integer(1)).executes(context -> {
                  int amount = IntegerArgumentType.getInteger(context, "amount");
                  CommandSourceStack source = (CommandSourceStack)context.getSource();
                  BlockPos pos = BlockPos.containing(source.getPosition());

                  while (amount > 0) {
                     BlockPos spot = IAuraChunk.getHighestSpot(source.getLevel(), pos, 35, pos);
                     amount -= IAuraChunk.getAuraChunk(source.getLevel(), spot).drainAura(spot, amount);
                  }

                  source.sendSuccess(() -> Component.literal("Removed aura from area"), true);
                  return 0;
               }))))
            .then(Commands.literal("reset").then(Commands.argument("range", IntegerArgumentType.integer(10, 1000)).executes(context -> {
               int range = IntegerArgumentType.getInteger(context, "range");
               CommandSourceStack source = (CommandSourceStack)context.getSource();
               BlockPos pos = BlockPos.containing(source.getPosition());
               IAuraChunk.getSpotsInArea(source.getLevel(), pos, range, (spot, amount) -> {
                  IAuraChunk chunk = IAuraChunk.getAuraChunk(source.getLevel(), spot);
                  if (amount > 0) {
                     chunk.drainAura(spot, amount);
                  } else {
                     chunk.storeAura(spot, -amount);
                  }
               });
               source.sendSuccess(() -> Component.literal("Reset aura in area"), true);
               return 0;
            })))
      );
   }
}
