package org.crypticdev.stextras.command.quest;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import dev.architectury.registry.registries.Registrar;
import io.github.manasmods.manascore.race.api.ManasRace;
import io.github.manasmods.manascore.race.api.ManasRaceInstance;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.event.STPlayerEvents;
import org.crypticdev.stextras.net.STExtrasQuestSync;
import org.crypticdev.stextras.quest.definition.QuestCategory;
import org.crypticdev.stextras.quest.definition.QuestDefinition;
import org.crypticdev.stextras.quest.registry.QuestRegistry;
import org.crypticdev.stextras.quest.registry.RacePrestigeMetadataRegistry;
import org.crypticdev.stextras.storage.quest.ISTExtrasQuests;
import org.crypticdev.stextras.storage.quest.STExtrasQuestStorage;

public final class RacePrestigeCommands {
   private static final SuggestionProvider<CommandSourceStack> RACE_SUGGESTIONS = (ctx, builder) -> CommandSupport.suggestResources(
      builder, RacePrestigeCommands::knownRaceIds
   );

   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("race")
         .then(
            ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("prestige")
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("races").executes(ctx -> listRaces(ctx, CommandSupport.self(ctx))))
                              .then(
                                 Commands.argument("player", EntityArgument.player()).executes(ctx -> listRaces(ctx, EntityArgument.getPlayer(ctx, "player")))
                              )
                        ))
                     .then(
                        Commands.literal("quests")
                           .then(
                              ((RequiredArgumentBuilder)Commands.argument("race", ResourceLocationArgument.id())
                                    .suggests(RACE_SUGGESTIONS)
                                    .executes(ctx -> showRaceQuests(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "race"))))
                                 .then(
                                    Commands.argument("player", EntityArgument.player())
                                       .executes(
                                          ctx -> showRaceQuests(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "race"))
                                       )
                                 )
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("mark-complete").requires(src -> src.hasPermission(2)))
                        .then(
                           ((RequiredArgumentBuilder)Commands.argument("race", ResourceLocationArgument.id())
                                 .suggests(RACE_SUGGESTIONS)
                                 .executes(ctx -> markComplete(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "race"))))
                              .then(
                                 Commands.argument("player", EntityArgument.player())
                                    .executes(ctx -> markComplete(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "race")))
                              )
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("unmark-complete").requires(src -> src.hasPermission(2)))
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("race", ResourceLocationArgument.id())
                              .suggests(RACE_SUGGESTIONS)
                              .executes(ctx -> unmarkComplete(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "race"))))
                           .then(
                              Commands.argument("player", EntityArgument.player())
                                 .executes(ctx -> unmarkComplete(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "race")))
                           )
                     )
               )
         );
   }

   private static List<ResourceLocation> knownRaceIds() {
      Set<ResourceLocation> ids = new LinkedHashSet<>(RacePrestigeMetadataRegistry.INSTANCE.getAllRaceIds());
      if (ids.isEmpty()) {
         ids.addAll(
            QuestRegistry.INSTANCE
               .byCategory(QuestCategory.RACE_PRESTIGE)
               .stream()
               .map(quest -> quest.race)
               .filter(Objects::nonNull)
               .collect(Collectors.toList())
         );
      }

      return CommandSupport.sortIds(ids);
   }

   private static int listRaces(CommandContext<CommandSourceStack> ctx, ServerPlayer player) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      Set<ResourceLocation> completed = store.getCompletedRaces();
      List<ResourceLocation> all = knownRaceIds();
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("player", CommandText.player(player)));
      currentRaceId(player).ifPresent(race -> lines.add(CommandText.keyValue("current race", CommandText.namedResource(race))));
      if (all.isEmpty()) {
         lines.add(CommandText.label("no race prestige data found"));
         CommandSupport.panel(ctx, "race prestige", lines);
         return 1;
      }

      for (ResourceLocation id : all) {
         boolean done = completed.contains(id);
         boolean isCurrent = currentRaceId(player).<Boolean>map(id::equals).orElse(false);
         MutableComponent line = Component.empty()
            .append(CommandText.namedResource(id))
            .append(Component.literal(" "))
            .append(CommandText.tag(done ? "complete" : "pending", done ? ChatFormatting.GREEN : ChatFormatting.RED));
         if (isCurrent) {
            line.append(Component.literal(" ")).append(CommandText.tag("current", ChatFormatting.YELLOW));
         }

         lines.add(line);
      }

      CommandSupport.panel(ctx, "race prestige", lines);
      return all.size();
   }

   private static int showRaceQuests(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation raceId) {
      List<QuestDefinition> quests = QuestRegistry.INSTANCE
         .byCategory(QuestCategory.RACE_PRESTIGE)
         .stream()
         .filter(questx -> raceId.equals(questx.race))
         .sorted(Comparator.comparing(questx -> questx.id.toString()))
         .toList();
      if (quests.isEmpty()) {
         CommandSupport.failure(
            ctx,
            Component.empty().append(CommandText.label("no race prestige quests for")).append(Component.literal(" ")).append(CommandText.namedResource(raceId))
         );
         return 0;
      }

      boolean isCurrent = currentRaceId(player).<Boolean>map(raceId::equals).orElse(false);
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      Set<ResourceLocation> active = isCurrent ? new HashSet<>(store.active(QuestCategory.RACE_PRESTIGE)) : Set.of();
      Set<ResourceLocation> completed = isCurrent ? new HashSet<>(store.completed(QuestCategory.RACE_PRESTIGE)) : Set.of();
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("player", CommandText.player(player)));
      lines.add(CommandText.keyValue("race", CommandText.namedResource(raceId)));
      if (!isCurrent) {
         lines.add(CommandText.label("not the current race; progress shown as idle"));
      }

      for (QuestDefinition quest : quests) {
         lines.add(formatRaceQuestLine(store, active, completed, isCurrent, quest));
      }

      CommandSupport.panel(ctx, "race prestige quests", lines);
      return quests.size();
   }

   private static Component formatRaceQuestLine(
      STExtrasQuestStorage store, Set<ResourceLocation> active, Set<ResourceLocation> completed, boolean isCurrent, QuestDefinition quest
   ) {
      int required = quest.noAmountRequired() ? 1 : Math.max(1, quest.amount);
      String state;
      ChatFormatting color;
      if (isCurrent && completed.contains(quest.id)) {
         state = "completed";
         color = ChatFormatting.GREEN;
      } else if (isCurrent && active.contains(quest.id)) {
         state = Math.min(store.getProgress(quest.id), required) + "/" + required;
         color = ChatFormatting.YELLOW;
      } else {
         state = "idle";
         color = ChatFormatting.GRAY;
      }

      String target = !quest.types.isEmpty() ? quest.types.toString() : (quest.mobCategory != null ? quest.mobCategory.name() : "Any");
      String questName = quest.name != null && !quest.name.isBlank() ? quest.name : quest.id.toString();
      return Component.empty()
         .append(CommandText.value(questName))
         .append(Component.literal(" "))
         .append(CommandText.tag(state, color))
         .append(Component.literal(" "))
         .append(CommandText.label(quest.creator.name().toLowerCase()))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.value(target))
         .append(Component.literal(" ").withStyle(ChatFormatting.DARK_GRAY))
         .append(Component.literal("(").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.resource(quest.id))
         .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
   }

   private static int markComplete(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation raceId) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      if (store.hasCompletedRace(raceId)) {
         CommandSupport.info(ctx, raceLine("race already complete", player, raceId), false);
         return 1;
      }

      store.addCompletedRace(raceId);
      STPlayerEvents.fireRacePrestigeCompleted(player, raceId);
      if (store.isDirty()) {
         STExtrasQuestSync.sendTo(player, store.toNet());
         store.clearDirty();
      }

      CommandSupport.success(ctx, raceLine("race marked complete", player, raceId), true);
      return 1;
   }

   private static int unmarkComplete(CommandContext<CommandSourceStack> ctx, ServerPlayer player, ResourceLocation raceId) {
      STExtrasQuestStorage store = ((ISTExtrasQuests)player).stextras$getQuests();
      if (!store.hasCompletedRace(raceId)) {
         CommandSupport.info(ctx, raceLine("race not complete", player, raceId), false);
         return 1;
      }

      store.removeCompletedRace(raceId);
      if (store.isDirty()) {
         STExtrasQuestSync.sendTo(player, store.toNet());
         store.clearDirty();
      }

      CommandSupport.success(ctx, raceLine("race completion removed", player, raceId), true);
      return 1;
   }

   private static Component raceLine(String label, ServerPlayer player, ResourceLocation raceId) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(player))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.namedResource(raceId));
   }

   private static Optional<ResourceLocation> currentRaceId(ServerPlayer player) {
      Optional<ManasRaceInstance> opt = RaceAPI.getRaceFrom(player).getRace();
      if (opt.isEmpty()) {
         return Optional.empty();
      }

      Registrar<ManasRace> reg = RaceAPI.getRaceRegistry();
      return Optional.ofNullable(reg.getId(opt.get().getRace()));
   }

   private RacePrestigeCommands() {
   }
}
