package org.crypticdev.stextras.command.system;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import dev.architectury.platform.Platform;
import dev.architectury.registry.menu.MenuRegistry;
import io.github.manasmods.tensura.block.SmithingBenchBlock;
import io.github.manasmods.tensura.menu.SmithingBenchMenu;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.inventory.ContainerLevelAccess;
import org.crypticdev.stextras.command.support.CommandSupport;
import org.crypticdev.stextras.command.support.CommandText;
import org.crypticdev.stextras.registary.effects.STExtrasMobEffects;
import org.crypticdev.stextras.storage.STExtarsStorage;

public final class DebugCommands {
   public static LiteralArgumentBuilder<CommandSourceStack> build() {
      return (LiteralArgumentBuilder<CommandSourceStack>)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal(
                        "debug"
                     )
                     .requires(src -> src.hasPermission(2)))
                  .then(
                     ((LiteralArgumentBuilder)Commands.literal("open-bench").executes(ctx -> openBench(ctx, CommandSupport.self(ctx))))
                        .then(Commands.argument("player", EntityArgument.player()).executes(ctx -> openBench(ctx, EntityArgument.getPlayer(ctx, "player"))))
                  ))
               .then(
                  ((LiteralArgumentBuilder)Commands.literal("check-effects").executes(ctx -> checkEffects(ctx, CommandSupport.self(ctx))))
                     .then(Commands.argument("player", EntityArgument.player()).executes(ctx -> checkEffects(ctx, EntityArgument.getPlayer(ctx, "player"))))
               ))
            .then(Commands.literal("soulgrade-levels").executes(DebugCommands::showSoulGradeLevels)))
         .then(Commands.literal("check-lootr").executes(DebugCommands::checkLootr));
   }

   private static int openBench(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      MenuRegistry.openExtendedMenu(
         target, new SimpleMenuProvider((id, inv, player) -> new SmithingBenchMenu(id, inv, ContainerLevelAccess.NULL), SmithingBenchBlock.TITLE), buf -> {}
      );
      CommandSupport.success(ctx, CommandText.keyValue("opened smithing bench for", CommandText.player(target)), true);
      return 1;
   }

   private static int checkEffects(CommandContext<CommandSourceStack> ctx, ServerPlayer target) {
      boolean hasWisp = target.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)STExtrasMobEffects.WISP_MODE.get()));
      boolean hasEarthSense = target.hasEffect(BuiltInRegistries.MOB_EFFECT.wrapAsHolder((MobEffect)STExtrasMobEffects.EARTHEN_SENSE.get()));
      CommandSupport.panel(
         ctx,
         "debug effects",
         List.of(
            CommandText.keyValue("player", CommandText.player(target)),
            CommandText.keyValue("wisp mode", CommandText.bool(hasWisp)),
            CommandText.keyValue("earthen sense", CommandText.bool(hasEarthSense)),
            CommandText.keyValue("active effects", CommandText.value(target.getActiveEffects()))
         )
      );
      return 1;
   }

   private static int checkLootr(CommandContext<CommandSourceStack> ctx) {
      CommandSupport.info(ctx, CommandText.keyValue("lootr loaded", CommandText.bool(Platform.isModLoaded("lootr"))), false);
      return 1;
   }

   private static int showSoulGradeLevels(CommandContext<CommandSourceStack> ctx) {
      List<Integer> thresholds = STExtarsStorage.Player.getSoulGradeLevelThresholds();
      Set<ResourceLocation> disabledRaceIds = STExtarsStorage.Quests.disabledRacePrestigeRaces();
      List<Component> lines = new ArrayList<>();
      lines.add(CommandText.keyValue("override enabled", CommandText.bool(STExtarsStorage.Player.isSoulGradeLevelOverrideEnabled())));
      lines.add(CommandText.keyValue("auto bonus", CommandText.number(STExtarsStorage.Player.getAdditionalSoulGradeForAutoLevels())));
      lines.add(CommandText.keyValue("configured total", CommandText.number(STExtarsStorage.Player.getTotalConfiguredSoulGrade())));
      lines.add(CommandText.keyValue("disabled races", CommandText.number(disabledRaceIds.size())));
      if (!disabledRaceIds.isEmpty()) {
         lines.add(CommandText.keyValue("disabled ids", CommandText.value(disabledRaceIds)));
      }

      for (int i = 0; i < thresholds.size(); i++) {
         lines.add(CommandText.keyValue("level " + (i + 1), CommandText.number(thresholds.get(i))));
      }

      CommandSupport.panel(ctx, "soul grade levels", lines);
      return thresholds.size();
   }

   private DebugCommands() {
   }
}
