package com.tensura_tno.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.tensura_tno.TensuraTNOMod;
import com.tensura_tno.util.AdminPrestigeBypass;
import com.tensura_tno.util.PonderEditorAccess;
import com.tensura_tno.util.PondererPaperFeatureAccess;
import java.lang.reflect.Method;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class TNOAdminCommands {
   public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
      FoodEPCommand.register(dispatcher);
      SummonEPCommand.register(dispatcher);
      dispatcher.register(
         (LiteralArgumentBuilder)Commands.literal("tno")
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("admin").requires(src -> src.hasPermission(2)))
                        .then(
                           ((LiteralArgumentBuilder)Commands.literal("prestige")
                                 .executes(
                                    ctx -> forcePrestige((CommandSourceStack)ctx.getSource(), ((CommandSourceStack)ctx.getSource()).getPlayerOrException())
                                 ))
                              .then(
                                 Commands.argument("player", EntityArgument.player())
                                    .executes(ctx -> forcePrestige((CommandSourceStack)ctx.getSource(), EntityArgument.getPlayer(ctx, "player")))
                              )
                        ))
                     .then(
                        ((LiteralArgumentBuilder)Commands.literal("ponder_editor").executes(ctx -> showPonderEditorStatus((CommandSourceStack)ctx.getSource())))
                           .then(
                              Commands.argument("enabled", BoolArgumentType.bool())
                                 .executes(ctx -> setPonderEditor((CommandSourceStack)ctx.getSource(), BoolArgumentType.getBool(ctx, "enabled")))
                           )
                     ))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("ponderer_paper").executes(ctx -> showPondererPaperStatus((CommandSourceStack)ctx.getSource())))
                        .then(
                           Commands.argument("enabled", BoolArgumentType.bool())
                              .executes(ctx -> setPondererPaper((CommandSourceStack)ctx.getSource(), BoolArgumentType.getBool(ctx, "enabled")))
                        )
                  )
            )
      );
   }

   private static int showPonderEditorStatus(CommandSourceStack source) {
      source.sendSuccess(
         () -> Component.literal(
            "[TNO] ponder_editor (思索 PonderUI 内的【写本书 / 编辑】按钮) 当前: "
               + (PonderEditorAccess.isEnabled() ? "开启" : "隐藏")
               + "。注: 想控制【拿纸右键圈结构】请使用 /tno admin ponderer_paper"
         ),
         false
      );
      return 1;
   }

   private static int setPonderEditor(CommandSourceStack source, boolean enabled) {
      PonderEditorAccess.setEnabled(enabled);
      source.sendSuccess(
         () -> Component.literal(
            "[TNO] ponder_editor (思索 PonderUI 内的【写本书 / 编辑】按钮) 已" + (enabled ? "开启" : "隐藏") + "，重开思索界面后生效。注: 想控制【拿纸右键圈结构】请使用 /tno admin ponderer_paper"
         ),
         true
      );
      return 1;
   }

   private static int showPondererPaperStatus(CommandSourceStack source) {
      source.sendSuccess(
         () -> Component.literal(
            "[TNO] ponderer_paper (思索者把【纸张】等普通物品当成圈结构 / 思索教程载体) 当前: "
               + (PondererPaperFeatureAccess.isEnabled() ? "开启" : "关闭")
               + "。注: 想控制【思索 PonderUI 内的写本书按钮】请使用 /tno admin ponder_editor"
         ),
         false
      );
      return 1;
   }

   private static int setPondererPaper(CommandSourceStack source, boolean enabled) {
      PondererPaperFeatureAccess.setEnabled(enabled);
      source.sendSuccess(
         () -> Component.literal("[TNO] ponderer_paper (纸张圈结构 / 纸张思索教程) 已" + (enabled ? "开启" : "关闭") + "。圈结构(右键纸张) 立即生效；思索基础教程需打开思索者功能页面点【重载】才会刷新"), true
      );
      return 1;
   }

   private static int forcePrestige(CommandSourceStack source, ServerPlayer target) {
      AdminPrestigeBypass.enable(target.getUUID());

      try {
         Class<?> cls = Class.forName("org.crypticdev.stextras.utils.PrestigeUtils");
         Method doPrestige = cls.getMethod("doPrestige", ServerPlayer.class);
         doPrestige.invoke(null, target);
         source.sendSuccess(() -> Component.literal("[TNO] 已强制为 " + target.getScoreboardName() + " 执行声望"), true);
      } catch (Exception e) {
         TensuraTNOMod.LOGGER.error("[TNO] 强制声望失败（STExtras 未加载？）", e);
         source.sendFailure(Component.literal("[TNO] 执行失败，请检查 STExtras 是否已加载"));
         return 0;
      } finally {
         AdminPrestigeBypass.disable(target.getUUID());
      }

      return 1;
   }
}
