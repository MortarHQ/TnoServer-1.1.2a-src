package io.github.Memoires.mysticism.command.edit;

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
import io.github.manasmods.manascore.command.api.parameter.primitive.BooleanArg;
import io.github.manasmods.manascore.command.api.parameter.primitive.DoubleArg;
import io.github.manasmods.manascore.command.api.parameter.primitive.LiteralArg;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.selector.EntitySelector;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;

@Command("stat")
@Permission(value = "mystic.command.edit_stat", permissionLevel = PermissionLevel.GAMEMASTER)
public class MysticEditStatCommand {
   @Execute
   public boolean setSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("current") String current,
      @LiteralArg("set") String set,
      @DoubleArg(value = "amount", min = -Double.MAX_VALUE) double amount
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
            soulEnergy.setSoulEnergy(amount);
            soulEnergy.markDirty();
            MysticismCommands.sendSuccess(
               stack,
               entity,
               Component.translatable("mysticism.command.soul_energy.set", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(amount)})
            );
         }
      }

      return true;
   }

   @Execute
   public boolean addSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("current") String current,
      @LiteralArg("add") String set,
      @DoubleArg(value = "amount", min = -Double.MAX_VALUE) double amount
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
            soulEnergy.setSoulEnergy(soulEnergy.getSoulEnergy() + amount);
            soulEnergy.markDirty();
            MysticismCommands.sendSuccess(
               stack,
               entity,
               Component.translatable(
                  "mysticism.command.soul_energy.set", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(soulEnergy.getSoulEnergy())}
               )
            );
         }
      }

      return true;
   }

   @Execute
   public boolean setMaxSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("max") String current,
      @LiteralArg("set") String set,
      @DoubleArg("amount") double amount
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            AttributeInstance instance = entity.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
            if (instance != null) {
               instance.setBaseValue(amount);
               MysticismCommands.sendSuccess(
                  stack,
                  entity,
                  Component.translatable("mysticism.command.soul_energy.set_max", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(amount)})
               );
            }
         }
      }

      return true;
   }

   @Execute
   public boolean setMaxSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("max") String current,
      @LiteralArg("set") String set,
      @DoubleArg("amount") double amount,
      @BooleanArg("resetCurrent") boolean resetCurrent
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            AttributeInstance instance = entity.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
            if (instance != null) {
               instance.setBaseValue(amount);
               if (resetCurrent) {
                  ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
                  soulEnergy.setSoulEnergy(instance.getValue());
                  soulEnergy.markDirty();
               }

               MysticismCommands.sendSuccess(
                  stack,
                  entity,
                  Component.translatable("mysticism.command.soul_energy.set_max", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(amount)})
               );
            }
         }
      }

      return true;
   }

   @Execute
   public boolean addMaxSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("max") String current,
      @LiteralArg("add") String set,
      @DoubleArg("amount") double amount
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            AttributeInstance instance = entity.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
            if (instance != null) {
               instance.setBaseValue(instance.getBaseValue() + amount);
               MysticismCommands.sendSuccess(
                  stack,
                  entity,
                  Component.translatable(
                     "mysticism.command.soul_energy.set_max", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(instance.getBaseValue())}
                  )
               );
            }
         }
      }

      return true;
   }

   @Execute
   public boolean addMaxSoulEnergy(
      @SenderArg CommandSourceStack stack,
      @EntityArg(Type.ENTITIES) EntitySelector selector,
      @LiteralArg("soulEnergy") String l,
      @LiteralArg("max") String current,
      @LiteralArg("add") String set,
      @DoubleArg("amount") double amount,
      @BooleanArg("resetCurrent") boolean resetCurrent
   ) throws CommandSyntaxException {
      for (Entity target : selector.findEntities(stack)) {
         if (target instanceof LivingEntity entity) {
            AttributeInstance instance = entity.getAttribute(MysticismAttributes.MAX_SOUL_ENERGY);
            if (instance != null) {
               instance.setBaseValue(instance.getBaseValue() + amount);
               if (resetCurrent) {
                  ISoulEnergy soulEnergy = MysticismStorages.getSoulEnergyFrom(entity);
                  soulEnergy.setSoulEnergy(instance.getValue());
                  soulEnergy.markDirty();
               }

               MysticismCommands.sendSuccess(
                  stack,
                  entity,
                  Component.translatable(
                     "mysticism.command.soul_energy.set_max", new Object[]{entity.getName(), MysticismCommands.getShortenedNumber(instance.getBaseValue())}
                  )
               );
            }
         }
      }

      return true;
   }
}
