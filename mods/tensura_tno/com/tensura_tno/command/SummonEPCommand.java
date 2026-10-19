package com.tensura_tno.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.DoubleArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.tensura_tno.ability.skill.SpiritSummonSkill;
import com.tensura_tno.race.fox_spirit.SummonMaxEPRequirement;
import io.github.manasmods.manascore.race.api.RaceAPI;
import java.util.List;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;

public final class SummonEPCommand {
   private static final SuggestionProvider<CommandSourceStack> SUGGEST_ENTITY_ID = (ctx, builder) -> {
      ServerPlayer player;
      try {
         player = ((CommandSourceStack)ctx.getSource()).getPlayerOrException();
      } catch (Exception e) {
         return builder.buildFuture();
      }

      List<String> ids = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntities(player);
      return SharedSuggestionProvider.suggest(ids, builder);
   };

   private SummonEPCommand() {
   }

   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)Commands.literal("tno")
            .then(
               ((LiteralArgumentBuilder)Commands.literal("admin").requires(src -> src.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("summonep")
                           .then(
                              ((LiteralArgumentBuilder)Commands.literal("set")
                                    .then(
                                       Commands.literal("all")
                                          .then(
                                             ((RequiredArgumentBuilder)Commands.argument("amount", DoubleArgumentType.doubleArg(0.0))
                                                   .executes(ctx -> executeAll(ctx, SummonEPCommand.Mode.SET, null)))
                                                .then(
                                                   Commands.argument("player", EntityArgument.player())
                                                      .executes(ctx -> executeAll(ctx, SummonEPCommand.Mode.SET, EntityArgument.getPlayer(ctx, "player")))
                                                )
                                          )
                                    ))
                                 .then(
                                    Commands.argument("entity_id", ResourceLocationArgument.id())
                                       .suggests(SUGGEST_ENTITY_ID)
                                       .then(
                                          ((RequiredArgumentBuilder)Commands.argument("amount", DoubleArgumentType.doubleArg(0.0))
                                                .executes(ctx -> executeOne(ctx, SummonEPCommand.Mode.SET, null)))
                                             .then(
                                                Commands.argument("player", EntityArgument.player())
                                                   .executes(ctx -> executeOne(ctx, SummonEPCommand.Mode.SET, EntityArgument.getPlayer(ctx, "player")))
                                             )
                                       )
                                 )
                           ))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("add")
                                 .then(
                                    Commands.literal("all")
                                       .then(
                                          ((RequiredArgumentBuilder)Commands.argument("amount", DoubleArgumentType.doubleArg())
                                                .executes(ctx -> executeAll(ctx, SummonEPCommand.Mode.ADD, null)))
                                             .then(
                                                Commands.argument("player", EntityArgument.player())
                                                   .executes(ctx -> executeAll(ctx, SummonEPCommand.Mode.ADD, EntityArgument.getPlayer(ctx, "player")))
                                             )
                                       )
                                 ))
                              .then(
                                 Commands.argument("entity_id", ResourceLocationArgument.id())
                                    .suggests(SUGGEST_ENTITY_ID)
                                    .then(
                                       ((RequiredArgumentBuilder)Commands.argument("amount", DoubleArgumentType.doubleArg())
                                             .executes(ctx -> executeOne(ctx, SummonEPCommand.Mode.ADD, null)))
                                          .then(
                                             Commands.argument("player", EntityArgument.player())
                                                .executes(ctx -> executeOne(ctx, SummonEPCommand.Mode.ADD, EntityArgument.getPlayer(ctx, "player")))
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int executeAll(CommandContext<CommandSourceStack> ctx, SummonEPCommand.Mode mode, ServerPlayer explicitTarget) {
      return execute(ctx, mode, explicitTarget, "all");
   }

   private static int executeOne(CommandContext<CommandSourceStack> ctx, SummonEPCommand.Mode mode, ServerPlayer explicitTarget) {
      ResourceLocation rl = ResourceLocationArgument.getId(ctx, "entity_id");
      return execute(ctx, mode, explicitTarget, rl.toString());
   }

   private static int execute(CommandContext<CommandSourceStack> ctx, SummonEPCommand.Mode mode, ServerPlayer explicitTarget, String entityId) {
      CommandSourceStack source = (CommandSourceStack)ctx.getSource();

      ServerPlayer target;
      try {
         target = explicitTarget != null ? explicitTarget : source.getPlayerOrException();
      } catch (Exception e) {
         source.sendFailure(Component.literal("[TNO] 需要指定玩家或由玩家执行"));
         return 0;
      }

      double amount = DoubleArgumentType.getDouble(ctx, "amount");
      List<String> absorbed = SpiritSummonSkill.SpiritSummonPockets.getAbsorbedEntities(target);
      if (absorbed.isEmpty()) {
         source.sendFailure(Component.literal("[TNO] 该玩家灵之召唤口袋为空"));
         return 0;
      }

      boolean isAll = entityId.equalsIgnoreCase("all");
      List<String> targets = isAll ? absorbed : List.of(entityId);
      if (!isAll && !absorbed.contains(entityId)) {
         source.sendFailure(Component.literal("[TNO] 口袋中不存在该实体: " + entityId));
         return 0;
      }

      double maxEP = 0.0;

      for (String eid : targets) {
         if (mode == SummonEPCommand.Mode.SET) {
            SpiritSummonSkill.SpiritSummonPockets.setBonusEP(target, eid, amount);
         } else {
            SpiritSummonSkill.SpiritSummonPockets.addBonusEP(target, eid, amount);
         }

         double current = SpiritSummonSkill.SpiritSummonPockets.getBonusEP(target, eid);
         if (current > maxEP) {
            maxEP = current;
         }
      }

      double finalMax = maxEP;
      RaceAPI.getRaceFrom(target).getRace().ifPresent(instance -> SummonMaxEPRequirement.updateMaxSummonBonusEP(instance, finalMax));
      String modeStr = mode == SummonEPCommand.Mode.SET ? "设置" : "增加";
      String targetStr = isAll ? "全部召唤物" : entityId;
      String msg = String.format("[TNO] 已%s %s 的 EP %s %.0f（当前最大: %.0f）", modeStr, targetStr, mode == SummonEPCommand.Mode.SET ? "为" : "", amount, finalMax);
      source.sendSuccess(() -> Component.literal(msg), true);
      return 1;
   }

   private enum Mode {
      SET,
      ADD;
   }
}
