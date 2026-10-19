package com.mortal_cultivation.item;

import io.github.manasmods.tensura.entity.template.TensuraTamableEntity;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;
import net.minecraft.ChatFormatting;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;

public class PuppetTalismanItem extends Item {
   public static final double MAGICULE_COST = 1000.0;

   public PuppetTalismanItem(Properties properties) {
      super(properties);
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(this.getDescriptionId(stack)).withStyle(ChatFormatting.LIGHT_PURPLE);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      super.appendHoverText(stack, context, tooltip, flag);
      tooltip.add(Component.translatable("item.mortal_cultivation.puppet_talisman.desc").withStyle(ChatFormatting.GRAY));
   }

   public InteractionResult interactLivingEntity(ItemStack stack, Player player, LivingEntity target, InteractionHand hand) {
      if (player.level().isClientSide) {
         return InteractionResult.SUCCESS;
      }

      if (!(target instanceof Mob)) {
         player.displayClientMessage(Component.translatable("item.mortal_cultivation.puppet_talisman.invalid_target").withStyle(ChatFormatting.RED), true);
         return InteractionResult.FAIL;
      }

      double playerEP = TensuraStorages.getExistenceFrom(player).getEP();
      double targetEP = TensuraStorages.getExistenceFrom(target).getEP();
      if (playerEP <= targetEP) {
         player.displayClientMessage(Component.translatable("item.mortal_cultivation.puppet_talisman.too_strong").withStyle(ChatFormatting.RED), true);
         return InteractionResult.FAIL;
      }

      if (!this.hasEnoughMagicule(player)) {
         player.displayClientMessage(Component.translatable("item.mortal_cultivation.puppet_talisman.no_magicule").withStyle(ChatFormatting.RED), true);
         return InteractionResult.FAIL;
      }

      if (this.isEntityTamedByPlayer(target, player)) {
         player.displayClientMessage(Component.translatable("item.mortal_cultivation.puppet_talisman.already_tamed").withStyle(ChatFormatting.YELLOW), true);
         return InteractionResult.FAIL;
      }

      this.consumeMagicule(player);
      boolean tamed = this.tameMob(target, player);
      if (tamed) {
         player.level().playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.8F, 1.0F);
         if (player.level() instanceof ServerLevel serverLevel) {
            serverLevel.sendParticles(ParticleTypes.ENCHANT, target.getX(), target.getY() + target.getBbHeight() / 2.0F, target.getZ(), 30, 0.5, 0.5, 0.5, 0.1);
         }

         if (!player.getAbilities().instabuild) {
            stack.shrink(1);
         }

         player.displayClientMessage(Component.translatable("item.mortal_cultivation.puppet_talisman.success").withStyle(ChatFormatting.GREEN), true);
         return InteractionResult.CONSUME;
      } else {
         return InteractionResult.PASS;
      }
   }

   private boolean hasEnoughMagicule(Player player) {
      return player.getAbilities().instabuild ? true : EnergyHelper.isOutOfMagiculeConsuming(player, 0.0, 1000.0) <= 0.0;
   }

   private void consumeMagicule(Player player) {
      if (!player.getAbilities().instabuild) {
         EnergyHelper.isOutOfMagiculeConsuming(player, 1000.0);
      }
   }

   private boolean isEntityTamedByPlayer(LivingEntity entity, Player player) {
      UUID playerUUID = player.getUUID();
      if (entity instanceof TamableAnimal tamable) {
         return tamable.isOwnedBy(player);
      } else if (entity instanceof TensuraTamableEntity tamable) {
         return tamable.isOwnedBy(player);
      } else {
         UUID reflectiveOwner = this.tryGetOwnerUuid(entity);
         if (reflectiveOwner != null) {
            return reflectiveOwner.equals(playerUUID);
         }

         IExistence existence = TensuraStorages.getExistenceFrom(entity);
         UUID permanentOwner = existence.getPermanentOwner();
         UUID temporaryOwner = existence.getTemporaryOwner();
         return permanentOwner != null && permanentOwner.equals(playerUUID) || temporaryOwner != null && temporaryOwner.equals(playerUUID);
      }
   }

   private boolean tameMob(LivingEntity entity, Player player) {
      if (entity instanceof TensuraTamableEntity tamable) {
         tamable.resetOwner(player.getUUID());
         entity.getPersistentData().putBoolean("PuppetTalismanTamed", true);
         return true;
      } else if (this.tryTameHorseLikeEntity(entity, player)) {
         entity.getPersistentData().putBoolean("PuppetTalismanTamed", true);
         return true;
      } else if (entity instanceof TamableAnimal tamable) {
         tamable.setOwnerUUID(player.getUUID());
         tamable.setTame(true, true);
         entity.getPersistentData().putBoolean("PuppetTalismanTamed", true);
         return true;
      } else if (entity instanceof Mob mob) {
         IExistence existence = TensuraStorages.getExistenceFrom(mob);
         existence.setPermanentOwner(player.getUUID());
         existence.markDirty();
         entity.getPersistentData().putBoolean("PuppetTalismanTamed", true);
         mob.setTarget(null);
         if (mob.getTarget() != player) {
            mob.getNavigation().moveTo(player, 1.0);
         }

         return true;
      } else {
         return false;
      }
   }

   private UUID tryGetOwnerUuid(LivingEntity entity) {
      try {
         Method getOwnerUuid = entity.getClass().getMethod("getOwnerUUID");
         if (getOwnerUuid.invoke(entity) instanceof UUID uuid) {
            return uuid;
         }
      } catch (ReflectiveOperationException var5) {
      }

      return null;
   }

   private boolean tryTameHorseLikeEntity(LivingEntity entity, Player player) {
      if (!entity.getClass().getSimpleName().equals("TensuraHorseEntity")) {
         return false;
      }

      try {
         Method setTamed = entity.getClass().getMethod("setTamed", boolean.class);
         Method setOwnerUuid = entity.getClass().getMethod("setOwnerUUID", UUID.class);
         setTamed.invoke(entity, true);
         setOwnerUuid.invoke(entity, player.getUUID());
         return true;
      } catch (ReflectiveOperationException ignored) {
         return false;
      }
   }
}
