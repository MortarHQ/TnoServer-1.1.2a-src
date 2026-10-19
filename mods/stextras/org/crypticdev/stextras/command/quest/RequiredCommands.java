package org.crypticdev.stextras.command.quest;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.quest.assignment.RequiredQuestSelector;
import org.crypticdev.stextras.quest.registry.QuestRegistry;

public final class RequiredCommands {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("required")
               .requires(src -> src.hasPermission(2)))
            .then(Commands.literal("preview").then(Commands.argument("prestige", IntegerArgumentType.integer(1)).executes(RequiredCommands::handlePreview))))
         .then(
            Commands.literal("apply")
               .then(
                  Commands.argument("player", EntityArgument.player())
                     .then(Commands.argument("prestige", IntegerArgumentType.integer(1)).executes(RequiredCommands::handleApply))
               )
         );
   }

   private static int handlePreview(CommandContext<CommandSourceStack> ctx) {
      int prestige = IntegerArgumentType.getInteger(ctx, "prestige");
      List<ResourceLocation> picks = RequiredQuestSelector.selectRequiredForPrestige(prestige, ((CommandSourceStack)ctx.getSource()).getLevel().getRandom());
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("prestige", CommandText.number(prestige)));
      picks.stream()
         .map(QuestRegistry.INSTANCE::byId)
         .filter(Objects::nonNull)
         .map(
            quest -> Component.empty()
               .append(CommandText.namedResource(quest.id))
               .append(Component.literal(" "))
               .append(CommandText.tag(quest.rarity.name().toLowerCase(), ChatFormatting.LIGHT_PURPLE))
         )
         .forEach(lines::add);
      if (lines.size() == 1) {
         lines.add(CommandText.label("no required quests selected"));
      }

      CommandSupport.panel(ctx, "required preview", lines);
      return 1;
   }

   private static int handleApply(CommandContext<CommandSourceStack> ctx) {
      ServerPlayer target = CommandSupport.target(ctx, "player");
      int prestige = IntegerArgumentType.getInteger(ctx, "prestige");
      RequiredQuestSelector.applyRequiredOnCharacterReset(target, prestige);
      CommandSupport.success(
         ctx,
         Component.empty()
            .append(CommandText.label("required quests applied"))
            .append(Component.literal(" "))
            .append(CommandText.player(target))
            .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
            .append(CommandText.number(prestige)),
         true
      );
      return 1;
   }

   private RequiredCommands() {
   }
}
