package bigchadguys.sellingbin.command;

import bigchadguys.sellingbin.init.ModConfigs;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.network.chat.Component;

public class ReloadCommand extends Command {
   @Override
   public void register(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext access, CommandSelection environment) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("sellingbin").requires(source -> source.hasPermission(4)))
            .then(Commands.literal("reload").executes(this::onReload))
      );
   }

   private int onReload(CommandContext<CommandSourceStack> context) {
      try {
         ModConfigs.register(false);
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Reloaded configs successfully.").withStyle(ChatFormatting.GRAY), true);
         return 0;
      } catch (Exception e) {
         ((CommandSourceStack)context.getSource()).sendSuccess(() -> Component.literal("Failed to reload configs.").withStyle(ChatFormatting.GRAY), true);
         throw e;
      }
   }
}
