package io.github.Memoires.mysticism.command.get;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import io.github.Memoires.mysticism.command.MysticismCommands;
import io.github.Memoires.mysticism.registry.attributes.MysticismAttributes;
import io.github.Memoires.mysticism.storage.MysticismStorages;
import io.github.Memoires.mysticism.storage.soulEnergy.ISoulEnergy;
import io.github.manasmods.manascore.command.api.Command;
import io.github.manasmods.manascore.command.api.Execute;
import io.github.manasmods.manascore.command.api.Permission;
import io.github.manasmods.manascore.command.api.Permission.PermissionLevel;
import io.github.manasmods.manascore.command.api.parameter.EntityArg;
import io.github.manasmods.manascore.command.api.parameter.SenderArg;
import io.github.manasmods.manascore.command.api.parameter.EntityArg.Type;
import io.github.manasmods.manascore.command.api.parameter.primitive.LiteralArg;
import net.minecraft.ChatFormatting;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;

@Command("stat")
public class MysticGetStatCommand {
   @Execute
   @Permission(value = "mystic.command.get_stat_others", permissionLevel = PermissionLevel.MODERATOR)
   public boolean getSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITY) EntitySelector selector,
      @LiteralArg("soulEnergy") String dl,
      @LiteralArg("current") String current
   ) throws CommandSyntaxException {
      if (selector.findSingleEntity(stack) instanceof LivingEntity entity) {
         ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
         MysticismCommands.sendSuccess(
            stack,
            Component.translatable(
               "mysticism.command.soul_energy.get", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(soulEnergy.getSoulEnergy())}
            ),
            ChatFormatting.AQUA
         );
         return true;
      } else {
         return false;
      }
   }

   @Execute
   @Permission(value = "mystic.command.get_stat_self", permissionLevel = PermissionLevel.PLAYER)
   public boolean getSoulEnergy(@SenderArg CommandSourceStack stack, @LiteralArg("soulEnergy") String dl, @LiteralArg("current") String current) {
      Player player = stack.getPlayer();
      if (player == null) {
         return false;
      }

      ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(player);
      MysticismCommands.sendSuccess(
         stack,
         Component.translatable(
            "mysticism.command.soul_energy.get", new Object[]{player.getName(), MysticismCommands.getShortenedNumber(soulEnergy.getSoulEnergy())}
         ),
         ChatFormatting.AQUA
      );
      return true;
   }

   @Execute
   @Permission(value = "mystic.command.get_stat_others", permissionLevel = PermissionLevel.MODERATOR)
   public boolean getMaxMagicule(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITY) EntitySelector selector,
      @LiteralArg("soulEnergy") String dl,
      @LiteralArg("max") String current
   ) throws CommandSyntaxException {
      if (selector.findSingleEntity(stack) instanceof LivingEntity entity) {
         AttributeInstance soulEnergy = entity.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
         if (soulEnergy == null) {
            return false;
         }

         MysticismCommands.sendSuccess(
            stack,
            Component.translatable(
               "mysticism.command.soul_energy.get_max", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(soulEnergy.getBaseValue())}
            ),
            ChatFormatting.AQUA
         );
         return true;
      } else {
         return false;
      }
   }

   @Execute
   @Permission(value = "mystic.command.get_stat_self", permissionLevel = PermissionLevel.PLAYER)
   public boolean getMaxSoulEnergy(@SenderArg CommandSourceStack stack, @LiteralArg("soulEnergy") String dl, @LiteralArg("max") String current) {
      Player player = stack.getPlayer();
      if (player == null) {
         return false;
      }

      AttributeInstance soulEnergy = player.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
      if (soulEnergy == null) {
         return false;
      }

      MysticismCommands.sendSuccess(
         stack,
         Component.translatable(
            "mysticism.command.soul_energy.get_max", new Object[]{player.getName(), MysticismCommands.getShortenedNumber(soulEnergy.getBaseValue())}
         ),
         ChatFormatting.AQUA
      );
      return true;
   }
}
