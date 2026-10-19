package com.github.wal_bos.moreskills.handler;

import com.github.wal_bos.moreskills.ability.skill.unique.MitamaFaithSkill;
import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.menu.MitamaOfferingMenu;
import com.github.wal_bos.moreskills.registry.skill.AllSkills;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.architectury.event.CompoundEventResult;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import dev.architectury.event.events.common.InteractionEvent.RightClickItem;
import io.github.manasmods.manascore.attribute.api.AttributeEvents;
import io.github.manasmods.manascore.attribute.api.AttributeEvents.CriticalAttackChanceEvent;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.item.misc.ResetScrollItem;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent.Action;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class MitamaFaithHandler {
   private static final Map<UUID, UUID> PENDING_INVITES = new HashMap<>();

   private MitamaFaithHandler() {
   }

   public static void init() {
      NeoForge.EVENT_BUS.addListener(MitamaFaithHandler::registerCommands);
      InteractionEvent.RIGHT_CLICK_ITEM.register((RightClickItem)(player, hand) -> {
         ItemStack stack = player.getItemInHand(hand);
         if (!player.level().isClientSide() && player instanceof ServerPlayer sp && stack.getItem() instanceof ResetScrollItem) {
            clearFollowerFromAllGods(sp);
         }

         return CompoundEventResult.pass();
      });
      AttributeEvents.CRITICAL_ATTACK_CHANCE_EVENT
         .register(
            (CriticalAttackChanceEvent)(attacker, target, originalMultiplier, multiplier, chance) -> MoreSkillsConfigs.GRAND
                     .mitamaFaith
                     .nullCriticalHitImmunity
                     .get()
                  && target instanceof LivingEntity living
                  && SkillUtils.isSkillToggled(living, (ManasSkill)AllSkills.MITAMA_FAITH.get())
               ? EventResult.interruptFalse()
               : EventResult.pass()
         );
   }

   public static void clearGod(UUID god) {
      PENDING_INVITES.entrySet().removeIf(entry -> entry.getValue().equals(god));
   }

   public static void clearFollowerFromAllGods(ServerPlayer follower) {
      PENDING_INVITES.remove(follower.getUUID());

      for (ServerPlayer player : follower.server.getPlayerList().getPlayers()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
         if (optional.isPresent() && MitamaFaithSkill.removeFollower(optional.get(), follower.getUUID())) {
            player.sendSystemMessage(
               Component.literal(follower.getName().getString() + "'s Mitama worship was cleared by reset.").withStyle(ChatFormatting.RED)
            );
         }
      }
   }

   public static void sendInvite(LivingEntity god, ServerPlayer target) {
      if (god instanceof ServerPlayer godPlayer) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(godPlayer).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
         if (!optional.isEmpty()) {
            ManasSkillInstance instance = optional.get();
            if (MitamaFaithSkill.isFollower(instance, target.getUUID())) {
               godPlayer.sendSystemMessage(Component.literal(target.getName().getString() + " is already your supporter.").withStyle(ChatFormatting.YELLOW));
            } else {
               PENDING_INVITES.put(target.getUUID(), godPlayer.getUUID());
               MutableComponent accept = Component.literal("[ACCEPT]")
                  .setStyle(
                     Style.EMPTY
                        .withColor(ChatFormatting.GREEN)
                        .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/mitama accept " + godPlayer.getGameProfile().getName()))
                        .withHoverEvent(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.literal("Accept worship pact")))
                  );
               MutableComponent reject = Component.literal("[REJECT]")
                  .setStyle(
                     Style.EMPTY
                        .withColor(ChatFormatting.RED)
                        .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/mitama reject " + godPlayer.getGameProfile().getName()))
                        .withHoverEvent(new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.literal("Reject worship pact")))
                  );
               target.sendSystemMessage(
                  Component.literal(godPlayer.getName().getString() + " demands worship. ")
                     .withStyle(ChatFormatting.GOLD)
                     .append(accept)
                     .append(Component.literal(" "))
                     .append(reject)
               );
               godPlayer.sendSystemMessage(Component.literal("Worship request sent to " + target.getName().getString() + ".").withStyle(ChatFormatting.GOLD));
            }
         }
      } else {
         god.sendSystemMessage(Component.literal("Only player gods can recruit player supporters.").withStyle(ChatFormatting.RED));
      }
   }

   public static void sendOfferingMenuMessage(ServerPlayer god, ServerPlayer target) {
      MutableComponent open = Component.literal("[OPEN OFFERING MENU]")
         .setStyle(
            Style.EMPTY
               .withColor(ChatFormatting.GOLD)
               .withClickEvent(new ClickEvent(Action.RUN_COMMAND, "/mitama offering " + god.getGameProfile().getName()))
               .withHoverEvent(
                  new HoverEvent(net.minecraft.network.chat.HoverEvent.Action.SHOW_TEXT, Component.literal("Donate EP, MP, Aura, Souls, or Skills"))
               )
         );
      target.sendSystemMessage(Component.literal(god.getName().getString() + " calls for an offering. ").withStyle(ChatFormatting.GOLD).append(open));
   }

   public static void openOwnCreationOrStatus(ServerPlayer player) {
      Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
      if (optional.isEmpty()) {
         player.sendSystemMessage(Component.literal("You do not have Mitama Faith.").withStyle(ChatFormatting.RED));
      } else {
         double worship = MitamaFaithSkill.getWorship(optional.get());
         player.sendSystemMessage(
            Component.literal(
                  "Mitama Faith: "
                     + MitamaFaithSkill.format(worship)
                     + " Worship | Damage x"
                     + MitamaFaithSkill.format(MitamaFaithSkill.getDamageMultiplier(worship))
               )
               .withStyle(ChatFormatting.GOLD)
         );
      }
   }

   public static void openOfferingMenu(ServerPlayer actor, MitamaFaithSkill.MitamaMenuKind kind, UUID targetUuid, String targetName) {
      actor.openMenu(
         new SimpleMenuProvider(
            (containerId, inventory, player) -> MitamaOfferingMenu.server(containerId, inventory, actor, kind, targetUuid, targetName),
            Component.translatable("tensuramoreskills.gui.mitama.title")
         ),
         buf -> MitamaOfferingMenu.writeExtraData(actor, buf, kind, targetUuid, targetName)
      );
   }

   public static ServerPlayer findGodForFollower(ServerPlayer follower) {
      for (ServerPlayer player : follower.server.getPlayerList().getPlayers()) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
         if (optional.isPresent() && MitamaFaithSkill.isFollower(optional.get(), follower.getUUID())) {
            return player;
         }
      }

      return null;
   }

   private static void registerCommands(RegisterCommandsEvent event) {
      event.getDispatcher()
         .register(
            (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)Commands.literal("mitama")
                        .then(
                           Commands.literal("accept")
                              .then(
                                 Commands.argument("god", StringArgumentType.word())
                                    .executes(
                                       ctx -> accept(((CommandSourceStack)ctx.getSource()).getPlayerOrException(), StringArgumentType.getString(ctx, "god"))
                                    )
                              )
                        ))
                     .then(
                        Commands.literal("reject")
                           .then(
                              Commands.argument("god", StringArgumentType.word())
                                 .executes(
                                    ctx -> reject(((CommandSourceStack)ctx.getSource()).getPlayerOrException(), StringArgumentType.getString(ctx, "god"))
                                 )
                           )
                     ))
                  .then(Commands.literal("status").executes(ctx -> status(((CommandSourceStack)ctx.getSource()).getPlayerOrException()))))
               .then(
                  Commands.literal("offering")
                     .then(
                        Commands.argument("god", StringArgumentType.word())
                           .executes(ctx -> offering(((CommandSourceStack)ctx.getSource()).getPlayerOrException(), StringArgumentType.getString(ctx, "god")))
                     )
               )
         );
   }

   private static int accept(ServerPlayer player, String godName) {
      ServerPlayer god = player.server.getPlayerList().getPlayerByName(godName);
      if (god != null && PENDING_INVITES.containsKey(player.getUUID()) && PENDING_INVITES.get(player.getUUID()).equals(god.getUUID())) {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
         if (optional.isEmpty()) {
            player.sendSystemMessage(Component.literal("That god no longer has Mitama Faith.").withStyle(ChatFormatting.RED));
            return 0;
         } else {
            double points = Math.max(
               (Double)MoreSkillsConfigs.GRAND.mitamaFaith.playerBaseWorship.get(),
               EnergyHelper.getMaxEP(player) / Math.max(1.0, (Double)MoreSkillsConfigs.GRAND.mitamaFaith.playerEpWorshipDivisor.get())
            );
            MitamaFaithSkill.addFollower(optional.get(), player.getUUID(), player.getName().getString(), true, points);
            PENDING_INVITES.remove(player.getUUID());
            player.sendSystemMessage(
               Component.literal("You now worship " + god.getName().getString() + ". Worship value: +" + MitamaFaithSkill.format(points))
                  .withStyle(ChatFormatting.GOLD)
            );
            god.sendSystemMessage(
               Component.literal(player.getName().getString() + " accepted worship. +" + MitamaFaithSkill.format(points) + " Worship.")
                  .withStyle(ChatFormatting.GOLD)
            );
            sendOfferingMenuMessage(god, player);
            return 1;
         }
      } else {
         player.sendSystemMessage(Component.literal("No valid Mitama worship invite from that player.").withStyle(ChatFormatting.RED));
         return 0;
      }
   }

   private static int reject(ServerPlayer player, String godName) {
      PENDING_INVITES.remove(player.getUUID());
      player.sendSystemMessage(Component.literal("Worship rejected.").withStyle(ChatFormatting.RED));
      ServerPlayer god = player.server.getPlayerList().getPlayerByName(godName);
      if (god != null) {
         god.sendSystemMessage(Component.literal(player.getName().getString() + " rejected worship.").withStyle(ChatFormatting.RED));
      }

      return 1;
   }

   private static int status(ServerPlayer player) {
      openOwnCreationOrStatus(player);
      return 1;
   }

   private static int offering(ServerPlayer player, String godName) {
      ServerPlayer god = player.server.getPlayerList().getPlayerByName(godName);
      if (god == null) {
         player.sendSystemMessage(Component.literal("That Mitama user is not online.").withStyle(ChatFormatting.RED));
         return 0;
      } else {
         Optional<ManasSkillInstance> optional = SkillAPI.getSkillsFrom(god).getSkill((ManasSkill)AllSkills.MITAMA_FAITH.get());
         if (!optional.isEmpty() && MitamaFaithSkill.isFollower(optional.get(), player.getUUID())) {
            openOfferingMenu(player, MitamaFaithSkill.MitamaMenuKind.DONATE, god.getUUID(), god.getName().getString());
            return 1;
         } else {
            player.sendSystemMessage(Component.literal("You are not that player's supporter.").withStyle(ChatFormatting.RED));
            return 0;
         }
      }
   }
}
