package org.crypticdev.stextras.command.player;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.manascore.skill.impl.SkillStorage;
import io.github.manasmods.tensura.ability.SkillHelper;
import io.github.manasmods.tensura.ability.TensuraSkillInstance;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.storage.STExtarsStorage;
import org.crypticdev.stextras.storage.player.ISTExtrasPlayer;

public final class PlayerCommands {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                           "player"
                        )
                        .requires(src -> src.hasPermission(2)))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("summary").executes(ctx -> handleSummary(ctx, CommandSupport.self(ctx))))
                           .then(
                              Commands.argument("player", EntityArgument.player()).executes(ctx -> handleSummary(ctx, EntityArgument.getPlayer(ctx, "player")))
                           )
                     ))
                  .then(buildEssenceCommands()))
               .then(buildSoulGradeCommands()))
            .then(buildLockedSkillsCommands()))
         .then(buildAdminLockedSkillsCommands());
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildEssenceCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("essence")
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("get").executes(ctx -> handleEssenceGet(ctx, CommandSupport.self(ctx))))
                     .then(Commands.argument("player", EntityArgument.player()).executes(ctx -> handleEssenceGet(ctx, EntityArgument.getPlayer(ctx, "player"))))
               ))
            .then(
               Commands.literal("set")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer(0))
                           .executes(ctx -> handleEssenceSet(ctx, CommandSupport.self(ctx), IntegerArgumentType.getInteger(ctx, "amount"))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(ctx -> handleEssenceSet(ctx, EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount")))
                        )
                  )
            ))
         .then(
            Commands.literal("add")
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(ctx -> handleEssenceAdd(ctx, CommandSupport.self(ctx), IntegerArgumentType.getInteger(ctx, "amount"))))
                     .then(
                        Commands.argument("player", EntityArgument.player())
                           .executes(ctx -> handleEssenceAdd(ctx, EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount")))
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildSoulGradeCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("soulgrade")
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("get").executes(ctx -> handleSoulGradeGet(ctx, CommandSupport.self(ctx))))
                     .then(
                        Commands.argument("player", EntityArgument.player()).executes(ctx -> handleSoulGradeGet(ctx, EntityArgument.getPlayer(ctx, "player")))
                     )
               ))
            .then(
               Commands.literal("set")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("value", IntegerArgumentType.integer(0))
                           .executes(ctx -> handleSoulGradeSet(ctx, CommandSupport.self(ctx), IntegerArgumentType.getInteger(ctx, "value"))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(ctx -> handleSoulGradeSet(ctx, EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "value")))
                        )
                  )
            ))
         .then(
            Commands.literal("add")
               .then(
                  ((RequiredArgumentBuilder)Commands.argument("amount", IntegerArgumentType.integer())
                        .executes(ctx -> handleSoulGradeAdd(ctx, CommandSupport.self(ctx), IntegerArgumentType.getInteger(ctx, "amount"))))
                     .then(
                        Commands.argument("player", EntityArgument.player())
                           .executes(ctx -> handleSoulGradeAdd(ctx, EntityArgument.getPlayer(ctx, "player"), IntegerArgumentType.getInteger(ctx, "amount")))
                     )
               )
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildLockedSkillsCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                     "locked-skills"
                  )
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("list").executes(ctx -> handleLockedSkillList(ctx, CommandSupport.self(ctx))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(ctx -> handleLockedSkillList(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                  ))
               .then(
                  Commands.literal("lock")
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.id())
                              .suggests(PlayerCommands::suggestAllSkillIds)
                              .executes(ctx -> handleLockedSkillLock(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id"))))
                           .then(
                              Commands.argument("player", EntityArgument.player())
                                 .executes(
                                    ctx -> handleLockedSkillLock(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "id"))
                                 )
                           )
                     )
               ))
            .then(
               Commands.literal("unlock")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.id())
                           .suggests(PlayerCommands::suggestAllSkillIds)
                           .executes(ctx -> handleLockedSkillUnlock(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id"))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(ctx -> handleLockedSkillUnlock(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "id")))
                        )
                  )
            ))
         .then(
            ((LiteralArgumentBuilder)Commands.literal("clear").executes(ctx -> handleLockedSkillClear(ctx, CommandSupport.self(ctx))))
               .then(Commands.argument("player", EntityArgument.player()).executes(ctx -> handleLockedSkillClear(ctx, EntityArgument.getPlayer(ctx, "player"))))
         );
   }

   private static LiteralArgumentBuilder<CommandSourceStack> buildAdminLockedSkillsCommands() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                     "admin-locked-skills"
                  )
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("list").executes(ctx -> handleAdminLockedSkillList(ctx, CommandSupport.self(ctx))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(ctx -> handleAdminLockedSkillList(ctx, EntityArgument.getPlayer(ctx, "player")))
                        )
                  ))
               .then(
                  Commands.literal("lock")
                     .then(
                        ((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.id())
                              .suggests(PlayerCommands::suggestAllSkillIds)
                              .executes(ctx -> handleAdminLockedSkillLock(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id"))))
                           .then(
                              Commands.argument("player", EntityArgument.player())
                                 .executes(
                                    ctx -> handleAdminLockedSkillLock(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "id"))
                                 )
                           )
                     )
               ))
            .then(
               Commands.literal("unlock")
                  .then(
                     ((RequiredArgumentBuilder)Commands.argument("id", ResourceLocationArgument.id())
                           .suggests(PlayerCommands::suggestAllSkillIds)
                           .executes(ctx -> handleAdminLockedSkillUnlock(ctx, CommandSupport.self(ctx), ResourceLocationArgument.getId(ctx, "id"))))
                        .then(
                           Commands.argument("player", EntityArgument.player())
                              .executes(
                                 ctx -> handleAdminLockedSkillUnlock(ctx, EntityArgument.getPlayer(ctx, "player"), ResourceLocationArgument.getId(ctx, "id"))
                              )
                        )
                  )
            ))
         .then(
            ((LiteralArgumentBuilder)Commands.literal("clear").executes(ctx -> handleAdminLockedSkillClear(ctx, CommandSupport.self(ctx))))
               .then(
                  Commands.argument("player", EntityArgument.player())
                     .executes(ctx -> handleAdminLockedSkillClear(ctx, EntityArgument.getPlayer(ctx, "player")))
               )
         );
   }

   private static int handleSummary(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      CommandSupport.panel(
         ctx,
         "player summary",
         List.of(
            CommandText.keyValue("player", CommandText.player(target)),
            CommandText.keyValue("essence", CommandText.number(data.getReincarnationEssence())),
            CommandText.keyValue("soul grade", CommandText.number(data.getSoulGrade())),
            CommandText.keyValue("locked skills", CommandText.number(data.getLockedSkills().size())),
            CommandText.keyValue("admin locked skills", CommandText.number(data.getAdminLockedSkills().size()))
         )
      );
      return 1;
   }

   private static int handleEssenceSet(CommandContext<CommandSourceStack> ctx, ServerPlayer target, int amount) {
      STExtarsStorage.Player.setReincarnationEssence(target, amount);
      CommandSupport.success(ctx, statLine("essence set", target, CommandText.number(amount)), true);
      return 1;
   }

   private static int handleEssenceAdd(CommandContext<CommandSourceStack> ctx, ServerPlayer target, int delta) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      int after = Math.max(0, data.getReincarnationEssence() + delta);
      STExtarsStorage.Player.setReincarnationEssence(target, after);
      CommandSupport.success(ctx, adjustedStatLine("essence updated", target, delta, after), true);
      return 1;
   }

   private static int handleEssenceGet(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      int value = ((ISTExtrasPlayer)target).getReincarnationEssence();
      CommandSupport.info(ctx, statLine("essence", target, CommandText.number(value)), false);
      return value;
   }

   private static int handleSoulGradeSet(CommandContext<CommandSourceStack> ctx, ServerPlayer target, int value) {
      STExtarsStorage.Player.setSoulGrade(target, value);
      CommandSupport.success(ctx, statLine("soul grade set", target, CommandText.number(value)), true);
      return 1;
   }

   private static int handleSoulGradeAdd(CommandContext<CommandSourceStack> ctx, ServerPlayer target, int delta) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      int after = Math.max(0, data.getSoulGrade() + delta);
      STExtarsStorage.Player.setSoulGrade(target, after);
      CommandSupport.success(ctx, adjustedStatLine("soul grade updated", target, delta, after), true);
      return 1;
   }

   private static int handleSoulGradeGet(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      int value = ((ISTExtrasPlayer)target).getSoulGrade();
      CommandSupport.info(ctx, statLine("soul grade", target, CommandText.number(value)), false);
      return value;
   }

   private static PlayerCommands.SkillEnsureResult ensureSkillExistsAndOwned(ServerPlayer target, ResourceLocation id) {
      if (id == null) {
         return new PlayerCommands.SkillEnsureResult(false, false, CommandText.label("invalid skill id"));
      }

      if (SkillAPI.getSkillRegistry().get(id) instanceof ManasSkill skill) {
         SkillStorage storage = SkillAPI.getSkillsFrom(target);
         if (storage == null) {
            return new PlayerCommands.SkillEnsureResult(
               false,
               false,
               Component.empty().append(CommandText.label("could not access skills for")).append(Component.literal(" ")).append(CommandText.player(target))
            );
         }

         if (storage.getSkill(id).isPresent()) {
            return new PlayerCommands.SkillEnsureResult(true, false, Component.empty());
         }

         TensuraSkillInstance instance = new TensuraSkillInstance(skill);
         boolean learned = SkillHelper.learnSkill(target, instance, -1);
         if (!learned && storage.getSkill(id).isEmpty()) {
            return new PlayerCommands.SkillEnsureResult(false, false, skillLine("failed to grant skill", target, id));
         }

         storage.markDirty();
         return new PlayerCommands.SkillEnsureResult(true, true, Component.empty());
      } else {
         return new PlayerCommands.SkillEnsureResult(false, false, skillLine("unknown skill", target, id));
      }
   }

   private static int handleLockedSkillLock(CommandContext<CommandSourceStack> ctx, ServerPlayer target, ResourceLocation id) {
      PlayerCommands.SkillEnsureResult ensure = ensureSkillExistsAndOwned(target, id);
      if (!ensure.ok()) {
         CommandSupport.failure(ctx, ensure.message());
         return 0;
      } else if (!STExtarsStorage.Player.lockSkill(target, id)) {
         CommandSupport.failure(ctx, skillLine("skill already locked", target, id));
         return 0;
      } else {
         String label = ensure.granted() ? "skill granted and locked" : "skill locked";
         CommandSupport.success(ctx, skillLine(label, target, id), true);
         return 1;
      }
   }

   private static int handleLockedSkillUnlock(CommandContext<CommandSourceStack> ctx, ServerPlayer target, ResourceLocation id) {
      if (!STExtarsStorage.Player.unlockSkill(target, id)) {
         CommandSupport.failure(ctx, skillLine("skill was not locked", target, id));
         return 0;
      } else {
         CommandSupport.success(ctx, skillLine("skill unlocked", target, id), true);
         return 1;
      }
   }

   private static int handleLockedSkillClear(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      int count = data.getLockedSkills().size();
      data.clearLockedSkills();
      CommandSupport.success(ctx, clearLine("locked skills cleared", target, count), true);
      return 1;
   }

   private static int handleLockedSkillList(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      List<ResourceLocation> list = CommandSupport.sortIds(((ISTExtrasPlayer)target).getLockedSkills());
      CommandSupport.panel(ctx, "locked skills", buildSkillPanelLines(target, list, "no skills currently locked"));
      return list.size();
   }

   private static int handleAdminLockedSkillLock(CommandContext<CommandSourceStack> ctx, ServerPlayer target, ResourceLocation id) {
      PlayerCommands.SkillEnsureResult ensure = ensureSkillExistsAndOwned(target, id);
      if (!ensure.ok()) {
         CommandSupport.failure(ctx, ensure.message());
         return 0;
      } else {
         ISTExtrasPlayer data = (ISTExtrasPlayer)target;
         if (!data.addAdminLockedSkill(id)) {
            CommandSupport.failure(ctx, skillLine("skill already admin locked", target, id));
            return 0;
         } else {
            String label = ensure.granted() ? "skill granted and admin locked" : "skill admin locked";
            CommandSupport.success(ctx, skillLine(label, target, id), true);
            return 1;
         }
      }
   }

   private static int handleAdminLockedSkillUnlock(CommandContext<CommandSourceStack> ctx, ServerPlayer target, ResourceLocation id) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      if (!data.removeAdminLockedSkill(id)) {
         CommandSupport.failure(ctx, skillLine("skill was not admin locked", target, id));
         return 0;
      } else {
         CommandSupport.success(ctx, skillLine("admin lock removed", target, id), true);
         return 1;
      }
   }

   private static int handleAdminLockedSkillClear(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      ISTExtrasPlayer data = (ISTExtrasPlayer)target;
      int count = data.getAdminLockedSkills().size();
      data.clearAdminLockedSkills();
      CommandSupport.success(ctx, clearLine("admin locked skills cleared", target, count), true);
      return 1;
   }

   private static int handleAdminLockedSkillList(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      List<ResourceLocation> list = CommandSupport.sortIds(((ISTExtrasPlayer)target).getAdminLockedSkills());
      CommandSupport.panel(ctx, "admin locked skills", buildSkillPanelLines(target, list, "no skills currently admin locked"));
      return list.size();
   }

   private static Component statLine(String label, ServerPlayer target, Component value) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(target))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(value.copy());
   }

   private static Component adjustedStatLine(String label, ServerPlayer target, int delta, int after) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(target))
         .append(Component.literal(" (").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.signedNumber(delta))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.number(after))
         .append(Component.literal(")").withStyle(ChatFormatting.DARK_GRAY));
   }

   private static Component skillLine(String label, ServerPlayer target, ResourceLocation id) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(target))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.namedResource(id));
   }

   private static Component clearLine(String label, ServerPlayer target, int count) {
      return Component.empty()
         .append(CommandText.label(label))
         .append(Component.literal(" "))
         .append(CommandText.player(target))
         .append(Component.literal(" -> ").withStyle(ChatFormatting.DARK_GRAY))
         .append(CommandText.number(count));
   }

   private static List<Component> buildSkillPanelLines(ServerPlayer target, List<ResourceLocation> skills, String emptyMessage) {
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("player", CommandText.player(target)));
      lines.add(CommandText.keyValue("count", CommandText.number(skills.size())));
      if (skills.isEmpty()) {
         lines.add(CommandText.label(emptyMessage));
         return lines;
      } else {
         skills.stream().map(CommandText::namedResource).forEach(lines::add);
         return lines;
      }
   }

   private static CompletableFuture<Suggestions> suggestAllSkillIds(CommandContext<CommandSourceStack> ctx, SuggestionsBuilder builder) {
      return CommandSupport.suggestSkillIds(builder);
   }

   private PlayerCommands() {
   }

   private record SkillEnsureResult(boolean ok, boolean granted, Component message) {
   }
}
