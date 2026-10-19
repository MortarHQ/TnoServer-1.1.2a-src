package org.crypticdev.stextras.command.support;

import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.quest.registry.QuestRegistry;

public final class CommandSupport {
   private static final Comparator<ResourceLocation> RESOURCE_ID_ORDER = Comparator.comparing(ResourceLocation::getNamespace)
      .thenComparing(ResourceLocation::getPath);

   public static ServerPlayer self(CommandContext<CommandSourceStack> ctx) {
      try {
         return ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      } catch (CommandSyntaxException e) {
         throw new RuntimeException(e);
      }
   }

   public static ServerPlayer target(CommandContext<CommandSourceStack> ctx, String name) {
      try {
         return EntityArgument.getPlayer(ctx, name);
      } catch (CommandSyntaxException e) {
         throw new RuntimeException(e);
      }
   }

   public static void success(CommandContext<CommandSourceStack> ctx, Component message, boolean broadcastToOps) {
      success((CommandSourceStack)ctx.getSource(), message, broadcastToOps);
   }

   public static void success(CommandContext<CommandSourceStack> ctx, String message, boolean broadcastToOps) {
      success(ctx, Component.literal(message), broadcastToOps);
   }

   public static void success(CommandSourceStack source, Component message, boolean broadcastToOps) {
      source.sendSuccess(() -> CommandText.success(message), broadcastToOps);
   }

   public static void success(CommandSourceStack source, String message, boolean broadcastToOps) {
      success(source, Component.literal(message), broadcastToOps);
   }

   public static void info(CommandContext<CommandSourceStack> ctx, Component message, boolean broadcastToOps) {
      info((CommandSourceStack)ctx.getSource(), message, broadcastToOps);
   }

   public static void info(CommandSourceStack source, Component message, boolean broadcastToOps) {
      source.sendSuccess(() -> CommandText.info(message), broadcastToOps);
   }

   public static void failure(CommandContext<CommandSourceStack> ctx, Component message) {
      failure((CommandSourceStack)ctx.getSource(), message);
   }

   public static void failure(CommandContext<CommandSourceStack> ctx, String message) {
      failure(ctx, Component.literal(message));
   }

   public static void failure(CommandSourceStack source, Component message) {
      source.sendFailure(CommandText.failure(message));
   }

   public static void failure(CommandSourceStack source, String message) {
      failure(source, Component.literal(message));
   }

   public static void panel(CommandContext<CommandSourceStack> ctx, String title, List<? extends Component> lines) {
      panel((CommandSourceStack)ctx.getSource(), title, lines);
   }

   public static void panel(CommandSourceStack source, String title, List<? extends Component> lines) {
      source.sendSuccess(() -> CommandText.panel(title, lines), false);
   }

   public static String nameOf(ServerPlayer player) {
      return player.getGameProfile().getName();
   }

   public static String formatDuration(long seconds) {
      long hours = seconds / 3600L;
      long minutes = seconds % 3600L / 60L;
      long secs = seconds % 60L;
      return String.format("%02dh %02dm %02ds", hours, minutes, secs);
   }

   public static CompletableFuture<Suggestions> suggestQuestIds(SuggestionsBuilder builder, Supplier<? extends Collection<ResourceLocation>> supplier) {
      return suggestResources(builder, supplier);
   }

   public static CompletableFuture<Suggestions> suggestSkillIds(SuggestionsBuilder builder) {
      return suggestResources(builder, CommandSupport::allSkillIds);
   }

   public static CompletableFuture<Suggestions> suggestResources(SuggestionsBuilder builder, Supplier<? extends Collection<ResourceLocation>> supplier) {
      return SharedSuggestionProvider.suggestResource(supplier.get(), builder);
   }

   public static List<ResourceLocation> allQuestIds() {
      return QuestRegistry.INSTANCE.all().stream().map(quest -> quest.id).sorted(RESOURCE_ID_ORDER).toList();
   }

   public static List<ResourceLocation> allSkillIds() {
      return SkillAPI.getSkillRegistry().getIds().stream().sorted(RESOURCE_ID_ORDER).toList();
   }

   public static List<ResourceLocation> sortIds(Collection<ResourceLocation> ids) {
      return ids.stream().sorted(RESOURCE_ID_ORDER).toList();
   }

   private CommandSupport() {
   }
}
