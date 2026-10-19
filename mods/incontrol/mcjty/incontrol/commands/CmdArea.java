package mcjty.incontrol.commands;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import mcjty.incontrol.areas.AreaSystem;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;

public class CmdArea {
   public static ArgumentBuilder<CommandSourceStack, ?> register(CommandDispatcher<CommandSourceStack> dispatcher) {
      return ((LiteralArgumentBuilder)Commands.literal("area").requires(cs -> cs.hasPermission(2))).executes(CmdArea::showArea);
   }

   private static int showArea(CommandContext<CommandSourceStack> context) {
      BlockPos pos = ((CommandSourceStack)context.getSource()).getPlayer().blockPosition();
      String area = AreaSystem.isInArea(((CommandSourceStack)context.getSource()).getPlayer().level(), pos.getX(), pos.getY(), pos.getZ());
      if (area == null) {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Not in any area"), false);
      } else {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Current area: " + area), false);
      }

      return 0;
   }
}
