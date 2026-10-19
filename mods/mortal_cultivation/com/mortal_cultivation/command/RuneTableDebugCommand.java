package com.mortal_cultivation.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mortal_cultivation.block.entity.RuneTableBlockEntity;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public final class RuneTableDebugCommand {
   private static final double TARGET_RANGE = 8.0;

   private RuneTableDebugCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(createRoot("mortalcultivation"));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> createRoot(String name) {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal(name)
         .then(
            Commands.literal("debug")
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("rune_table").requires(source -> source.hasPermission(2)))
                     .then(
                        Commands.literal("add")
                           .then(Commands.argument("amount", IntegerArgumentType.integer(1, 500)).executes(RuneTableDebugCommand::addRunes))
                     )
               )
         );
   }

   private static int addRunes(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
      CommandSourceStack source = (CommandSourceStack)context.getSource();
      ServerPlayer player = source.getPlayerOrException();
      Vec3 start = player.getEyePosition();
      Vec3 end = start.add(player.getViewVector(1.0F).scale(8.0));
      BlockHitResult hit = player.level().clip(new ClipContext(start, end, Block.OUTLINE, Fluid.NONE, player));
      if (hit.getType() == Type.BLOCK && player.level().getBlockEntity(hit.getBlockPos()) instanceof RuneTableBlockEntity runeTable) {
         int var11 = runeTable.getStoredRuneCount();
         int requested = IntegerArgumentType.getInteger(context, "amount");
         runeTable.setStoredRuneCount(var11 + requested);
         int after = runeTable.getStoredRuneCount();
         int added = after - var11;
         if (added <= 0) {
            source.sendFailure(Component.translatable("mortal_cultivation.command.debug.rune_table.full", new Object[]{500}));
            return 0;
         } else {
            source.sendSuccess(() -> Component.translatable("mortal_cultivation.command.debug.rune_table.success", new Object[]{added, after, 500}), true);
            return added;
         }
      } else {
         source.sendFailure(Component.translatable("mortal_cultivation.command.debug.rune_table.not_found", new Object[]{8}));
         return 0;
      }
   }
}
