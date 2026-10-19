package org.crypticdev.stextras.command.quest;

import com.mojang.brigadier.builder.ArgumentBuilder;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.quest.assignment.RepeatableQuestService;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.reward.QuestRewardService;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class QuestCommands {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("quests")
                  .then(buildCatalogCommands()))
               .then(buildSummaryCommands()))
            .then(buildListCommands()))
         .then(buildMutationCommands());
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildCatalogCommands() {
      var list = (LiteralArgumentBuilder<CommandSourceStack> & ArgumentBuilder)Commands.literal("list").executes(ctx -> handleCatalogList(ctx, null));
      addCategoryLiterals(list, category -> handleCatalogList(category.ctx, category.category));
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("catalog").then(list);
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildSummaryCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("summary")
            .executes(ctx -> handleSummary(ctx, CommandSupport.self(ctx))))
         .then(
            ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player()).requires(src -> src.hasPermission(2)))
               .executes(ctx -> handleSummary(ctx, EntityArgument.getPlayer(ctx, "player")))
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildListCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("list")
               .then(buildListModeBranch("all", QuestCommands.QuestListMode.ALL)))
            .then(buildListModeBranch("active", QuestCommands.QuestListMode.ACTIVE)))
         .then(buildListModeBranch("completed", QuestCommands.QuestListMode.COMPLETED));
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildMutationCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                        "manage"
                     )
                     .requires(src -> src.hasPermission(2)))
                  .then(buildAddCommand()))
               .then(buildRemoveCommand()))
            .then(buildCompleteCommand()))
         .then(buildRepeatableCooldownCommands());
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildAddCommand() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("add")
            .then(
               Commands.argument("id", ResourceLocationArgument.id())
                  .suggests(QuestCommands::suggestAllQuestIds)
                  .executes(ctx -> handleQuestAdd(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id")))
            ))
         .then(
            Commands.literal("player")
               .then(
                  Commands.argument("player", EntityArgument.player())
                     .then(
                        Commands.argument("id", ResourceLocationArgument.id())
                           .suggests(QuestCommands::suggestAllQuestIds)
                           .executes(ctx -> handleQuestAdd(ctx, CommandSupport.target(ctx, "player"), ResourceLocationArgument.getId(ctx, "id")))
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildRemoveCommand() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("remove")
            .then(
               Commands.argument("id", ResourceLocationArgument.id())
                  .suggests(QuestCommands::suggestCurrentQuestIdsForSelf)
                  .executes(ctx -> handleQuestRemove(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id")))
            ))
         .then(
            Commands.literal("player")
               .then(
                  Commands.argument("player", EntityArgument.player())
                     .then(
                        Commands.argument("id", ResourceLocationArgument.id())
                           .suggests(QuestCommands::suggestCurrentQuestIdsForTarget)
                           .executes(ctx -> handleQuestRemove(ctx, CommandSupport.target(ctx, "player"), ResourceLocationArgument.getId(ctx, "id")))
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildCompleteCommand() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("complete")
               .then(buildCompleteAllSelfBranch()))
            .then(
               Commands.argument("id", ResourceLocationArgument.id())
                  .suggests(QuestCommands::suggestCurrentQuestIdsForSelf)
                  .executes(ctx -> handleQuestComplete(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id")))
            ))
         .then(
            Commands.literal("player")
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player()).then(buildCompleteAllTargetBranch()))
                     .then(
                        Commands.argument("id", ResourceLocationArgument.id())
                           .suggests(QuestCommands::suggestCurrentQuestIdsForTarget)
                           .executes(ctx -> handleQuestComplete(ctx, CommandSupport.target(ctx, "player"), ResourceLocationArgument.getId(ctx, "id")))
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildRepeatableCooldownCommands() {
      LiteralArgumentBuilder<CommandSourceStack> reset = (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)Commands.literal("reset")
            .then(Commands.literal("all").executes(ctx -> handleRepeatableCooldownResetAll(ctx, CommandSupport.self(ctx)))))
         .then(
            Commands.argument("id", ResourceLocationArgument.id())
               .suggests(QuestCommands::suggestRepeatableQuestIds)
               .executes(ctx -> handleRepeatableCooldownReset(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id")))
         );
      reset.then(
         Commands.literal("player")
            .then(
               ((RequiredArgumentBuilder)Commands.argument("player", EntityArgument.player())
                     .then(Commands.literal("all").executes(ctx -> handleRepeatableCooldownResetAll(ctx, CommandSupport.target(ctx, "player")))))
                  .then(
                     Commands.argument("id", ResourceLocationArgument.id())
                        .suggests(QuestCommands::suggestRepeatableQuestIds)
                        .executes(ctx -> handleRepeatableCooldownReset(ctx, CommandSupport.target(ctx, "player"), ResourceLocationArgument.getId(ctx, "id")))
                  )
            )
      );
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("cooldown").then(reset);
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildCompleteAllSelfBranch() {
      var root = (LiteralArgumentBuilder<CommandSourceStack> & ArgumentBuilder)Commands.literal("all")
         .executes(ctx -> handleQuestCompleteAll(ctx, CommandSupport.self(ctx), null));
      addManageCategoryLiterals(root, category -> handleQuestCompleteAll(category.ctx, CommandSupport.self(category.ctx), category.category));
      return root;
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildCompleteAllTargetBranch() {
      var root = (LiteralArgumentBuilder<CommandSourceStack> & ArgumentBuilder)Commands.literal("all")
         .executes(ctx -> handleQuestCompleteAll(ctx, CommandSupport.target(ctx, "player"), null));
      addManageCategoryLiterals(root, category -> handleQuestCompleteAll(category.ctx, CommandSupport.target(category.ctx, "player"), category.category));
      return root;
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildListModeBranch(String name, QuestCommands.QuestListMode mode) {
      var root = (LiteralArgumentBuilder<CommandSourceStack> & ArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(name)
            .executes(ctx -> handlePlayerQuestList(ctx, CommandSupport.self(ctx), mode, null)))
         .then(buildTargetListBranch(mode));
      addCategoryLiterals(root, category -> handlePlayerQuestList(category.ctx, CommandSupport.self(category.ctx), mode, category.category));
      return root;
   }

   private static RequiredArgumentBuilder<CommandSourceStack, ?> buildTargetListBranch(QuestCommands.QuestListMode mode) {
      var playerArg = (RequiredArgumentBuilder<CommandSourceStack, EntitySelector> & ArgumentBuilder)((RequiredArgumentBuilder)Commands.argument(
               "player", EntityArgument.player()
            )
            .requires(src -> src.hasPermission(2)))
         .executes(ctx -> handlePlayerQuestList(ctx, CommandSupport.target(ctx, "player"), mode, null));
      addCategoryLiterals(playerArg, category -> handlePlayerQuestList(category.ctx, CommandSupport.target(category.ctx, "player"), mode, category.category));
      return playerArg;
   }

   private static int handleCatalogList(CommandContext<CommandSourceStack> ctx, QuestCategory category) {
      List<QuestDefinition> list = category == null
         ? new ArrayList<>(QuestRegistry.INSTANCE.all())
         : new ArrayList<>(QuestRegistry.INSTANCE.byCategory(category));
      list.sort(Comparator.comparing(quest -> quest.id.toString()));
      sendQuestList(ctx, null, "quest catalog" + formatCategorySuffix(category), list);
      return list.size();
   }

   private static int handleSummary(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      CommandSupport.panel(
         ctx,
         "quest summary",
         List.of(
            CommandText.keyValue("player", CommandText.player(player)),
            CommandText.keyValue("active", CommandText.number(store.activeAll().size())),
            CommandText.keyValue("completed", CommandText.number(store.completedAll().size())),
            CommandText.keyValue("completed races", CommandText.number(store.getCompletedRaces().size())),
            CommandText.keyValue("daily", CommandText.value(store.active(QuestCategory.DAILY).size() + " / " + store.completed(QuestCategory.DAILY).size())),
            CommandText.keyValue("weekly", CommandText.value(store.active(QuestCategory.WEEKLY).size() + " / " + store.completed(QuestCategory.WEEKLY).size())),
            CommandText.keyValue(
               "required", CommandText.value(store.active(QuestCategory.REQUIRED).size() + " / " + store.completed(QuestCategory.REQUIRED).size())
            ),
            CommandText.keyValue(
               "repeatable", CommandText.value(store.active(QuestCategory.REPEATABLE).size() + " / " + store.completed(QuestCategory.REPEATABLE).size())
            ),
            CommandText.keyValue(
               "race prestige",
               CommandText.value(store.active(QuestCategory.RACE_PRESTIGE).size() + " / " + store.completed(QuestCategory.RACE_PRESTIGE).size())
            )
         )
      );
      return 1;
   }

   private static int handlePlayerQuestList(
      CommandContext<CommandSourceStack> ctx, ServerPlayer player, QuestCommands.QuestListMode mode, QuestCategory category
   ) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();

      Set<ResourceLocation> ids = switch (mode) {
         case ALL -> {
            Set<ResourceLocation> all = new HashSet<>(store.activeAll());
            all.addAll(store.completedAll());
            yield all;
         }
         case ACTIVE -> new HashSet(category == null ? store.activeAll() : store.active(category));
         case COMPLETED -> new HashSet(category == null ? store.completedAll() : store.completed(category));
      };
      List<QuestDefinition> list = ids.stream()
         .map(QuestRegistry.INSTANCE::byId)
         .filter(Objects::nonNull)
         .filter(quest -> category == null || quest.category == category)
         .sorted(Comparator.comparing(quest -> quest.id.toString()))
         .toList();
      sendQuestList(
         ctx, store, "player quests for " + CommandSupport.nameOf(player) + " [" + mode.name().toLowerCase() + "]" + formatCategorySuffix(category), list
      );
      return list.size();
   }

   private static void sendQuestList(CommandContext<CommandSourceStack> ctx, STExtrasQuestStorage store, String title, List<QuestDefinition> list) {
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("count", CommandText.number(list.size())));
      if (list.isEmpty()) {
         lines.add(CommandText.label("no quests found"));
         CommandSupport.panel(ctx, title, lines);
      } else {
         Set<ResourceLocation> activeAll = store == null ? Set.of() : new HashSet<>(store.activeAll());
         Set<ResourceLocation> completedAll = store == null ? Set.of() : new HashSet<>(store.completedAll());

         for (QuestDefinition quest : list) {
            lines.add(formatQuestLine(store, activeAll, completedAll, quest));
         }

         CommandSupport.panel(ctx, title, lines);
      }
   }

   private static Component formatQuestLine(
      STExtrasQuestStorage store, Set<ResourceLocation> activeAll, Set<ResourceLocation> completedAll, QuestDefinition quest
   ) {
      boolean completed = completedAll.contains(quest.id);
      boolean active = activeAll.contains(quest.id);
      ChatFormatting stateColor = completed ? ChatFormatting.GREEN : (active ? ChatFormatting.YELLOW : ChatFormatting.GRAY);
      String stateText = completed ? "completed" : (active ? "active" : "idle");
      int progress = store == null ? 0 : store.getProgress(quest.id);
      int required = Math.max(1, quest.amount);
      String questName = quest.name != null && !quest.name.isBlank() ? quest.name : quest.id.toString();
      MutableComponent line = Component.empty()
         .append(CommandText.value(questName))
         .append(Component.literal(" "))
         .append(CommandText.tag(stateText, stateColor))
         .append(Component.literal(" "))
         .append(CommandText.label(quest.creator.name().toLowerCase()))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.value(targetSummary(quest)));
      if (!quest.noAmountRequired()) {
         ChatFormatting progressColor = progress >= required ? ChatFormatting.GREEN : ChatFormatting.YELLOW;
         line.append(Component.literal(" ")).append(CommandText.tag(progress + "/" + required, progressColor));
      }

      if (quest.category == QuestCategory.REQUIRED) {
         line.append(Component.literal(" ")).append(CommandText.tag("p" + quest.prestige, ChatFormatting.LIGHT_PURPLE));
      }

      line.append(Component.literal(" ").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal("(").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.resource(quest.id))
         .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
      return line;
   }

   private static String targetSummary(QuestDefinition quest) {
      if (!quest.types.isEmpty()) {
         return quest.types.size() == 1 ? quest.types.get(0).toString() : quest.types.toString();
      } else {
         return quest.mobCategory != null ? quest.mobCategory.name() : "Any";
      }
   }

   private static int handleQuestAdd(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation id) {
      QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
      if (quest == null) {
         CommandSupport.failure(ctx, questActionLine("unknown quest", player, id));
         return 0;
      } else {
         STExtarsStorage.Quests.addActive(player, id);
         CommandSupport.success(ctx, questActionLine("quest added", player, id), true);
         return 1;
      }
   }

   private static int handleQuestRemove(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation id) {
      STExtarsStorage.Quests.removeActive(player, id);
      CommandSupport.success(ctx, questActionLine("quest removed", player, id), true);
      return 1;
   }

   private static int handleQuestComplete(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation id) {
      QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
      if (quest == null) {
         CommandSupport.failure(ctx, questActionLine("unknown quest", player, id));
         return 0;
      } else {
         STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
         store.markCompleted(id);
         STPlayerEvents.fireQuestCompleted(player, id);
         QuestRewardService.grant(player, quest);
         CommandSupport.success(ctx, questActionLine("quest completed", player, id), true);
         return 1;
      }
   }

   private static int handleQuestCompleteAll(CommandContext<CommandSourceStack> ctx, ServerPlayer player, QuestCategory category) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      Collection<ResourceLocation> activeIds = category == null ? store.activeAll() : store.active(category);
      List<ResourceLocation> ids = CommandSupport.sortIds(activeIds);
      int completed = 0;
      int skipped = 0;

      for (ResourceLocation id : ids) {
         QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
         if (quest != null && (category == null || quest.category == category)) {
            store.markCompleted(id);
            STPlayerEvents.fireQuestCompleted(player, id);
            QuestRewardService.grant(player, quest);
            completed++;
         } else {
            skipped++;
         }
      }

      if (store.isDirty()) {
         store.syncAllTo(player);
         store.clearDirty();
      }

      MutableComponent message = Component.empty()
         .append(CommandText.label("completed active quests"))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.number(completed));
      if (category != null) {
         message.append(Component.literal(" ")).append(CommandText.tag(categoryLabel(category), ChatFormatting.AQUA));
      }

      if (skipped > 0) {
         message.append(Component.literal(" ")).append(CommandText.tag("skipped " + skipped, ChatFormatting.DARK_GRAY));
      }

      CommandSupport.success(ctx, message, true);
      return completed;
   }

   private static int handleRepeatableCooldownReset(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation id) {
      QuestDefinition quest = QuestRegistry.INSTANCE.byId(id);
      if (quest == null) {
         CommandSupport.failure(ctx, questActionLine("unknown quest", player, id));
         return 0;
      }

      if (quest.category != QuestCategory.REPEATABLE) {
         CommandSupport.failure(ctx, questActionLine("not a repeatable quest", player, id));
         return 0;
      }

      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      long remaining = store.cooldownRemaining(id);
      store.clearCooldown(id);
      RepeatableQuestService.tryStartRepeatable(player, id);
      if (store.isDirty()) {
         store.syncAllTo(player);
         store.clearDirty();
      }

      MutableComponent message = Component.empty()
         .append(CommandText.label("repeatable cooldown reset"))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.namedResource(id));
      if (remaining > 0L) {
         message.append(Component.literal(" ")).append(CommandText.tag(CommandSupport.formatDuration(remaining), ChatFormatting.AQUA));
      }

      CommandSupport.success(ctx, message, true);
      return 1;
   }

   private static int handleRepeatableCooldownResetAll(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      int reset = 0;

      for (QuestDefinition quest : QuestRegistry.INSTANCE.byCategory(QuestCategory.REPEATABLE)) {
         if (store.cooldownRemaining(quest.id) > 0L) {
            reset++;
         }

         store.clearCooldown(quest.id);
      }

      RepeatableQuestService.ensureRepeatablesApplied(player);
      if (store.isDirty()) {
         store.syncAllTo(player);
         store.clearDirty();
      }

      MutableComponent message = Component.empty()
         .append(CommandText.label("reset repeatable cooldowns"))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.number(reset));
      CommandSupport.success(ctx, message, true);
      return reset;
   }

   private static Component questActionLine(String label, ServerPlayer player, ResourceLocation id) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.namedResource(id));
   }

   private static CompletableFuture<Suggestions> suggestAllQuestIds(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
      return CommandSupport.suggestQuestIds(builder, CommandSupport::allQuestIds);
   }

   private static CompletableFuture<Suggestions> suggestCurrentQuestIdsForSelf(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
      return suggestCurrentQuestIds(CommandSupport.self(ctx), builder);
   }

   private static CompletableFuture<Suggestions> suggestCurrentQuestIdsForTarget(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
      return suggestCurrentQuestIds(CommandSupport.target(ctx, "player"), builder);
   }

   private static CompletableFuture<Suggestions> suggestRepeatableQuestIds(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
      return CommandSupport.suggestQuestIds(builder, QuestCommands::repeatableQuestIds);
   }

   private static CompletableFuture<Suggestions> suggestCurrentQuestIds(ServerPlayer player, SuggestionsBuilder builder) {
      return CommandSupport.suggestQuestIds(builder, () -> CommandSupport.sortIds(STExtarsStorage.Quests.active(player)));
   }

   private static List<ResourceLocation> repeatableQuestIds() {
      return QuestRegistry.INSTANCE
         .byCategory(QuestCategory.REPEATABLE)
         .stream()
         .map(quest -> quest.id)
         .sorted(Comparator.comparing(ResourceLocation::getNamespace).thenComparing(ResourceLocation::getPath))
         .toList();
   }

   private static String formatCategorySuffix(QuestCategory category) {
      return category == null ? "" : " [" + category.name().toLowerCase() + "]";
   }

   private static String categoryLabel(QuestCategory category) {
      return switch (category) {
         case DAILY -> "daily";
         case WEEKLY -> "weekly";
         case REQUIRED -> "required";
         case REPEATABLE -> "repeatable";
         case RACE_PRESTIGE -> "race";
      };
   }

   private static <T extends ArgumentBuilder<CommandSourceStack, T>> T addCategoryLiterals(T builder, QuestCommands.CategoryExecutor executor) {
      builder.then(Commands.literal("daily").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.DAILY))));
      builder.then(Commands.literal("weekly").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.WEEKLY))));
      builder.then(Commands.literal("required").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.REQUIRED))));
      builder.then(Commands.literal("repeatable").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.REPEATABLE))));
      builder.then(Commands.literal("race-prestige").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.RACE_PRESTIGE))));
      return builder;
   }

   private static <T extends ArgumentBuilder<CommandSourceStack, T>> T addManageCategoryLiterals(T builder, QuestCommands.CategoryExecutor executor) {
      addCategoryLiterals(builder, executor);
      builder.then(Commands.literal("race").executes(ctx -> executor.execute(new QuestCommands.CategoryExecution(ctx, QuestCategory.RACE_PRESTIGE))));
      return builder;
   }

   private QuestCommands() {
   }

   private record CategoryExecution(CommandContext<CommandSourceStack> ctx, QuestCategory category) {
   }

   @FunctionalInterface
   private interface CategoryExecutor {
      int execute(QuestCommands.CategoryExecution var1);
   }

   private enum QuestListMode {
      ALL,
      ACTIVE,
      COMPLETED;
   }
}
