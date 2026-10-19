package com.github.b4ndithelps.tennogamenolife.item;

import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.beam.SolarBeamProjectile;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.List;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Block;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.HitResult.Type;

public class ExMachinasWeaponItem extends Item {
   private static final String MODE_TAG = "WeaponMode";
   private static final float LASER_SIZE = 0.3F;
   private static final float LASER_RANGE = 64.0F;
   private static final int LASER_LIFE = 5;
   private static final double ENTITY_HITBOX_INFLATE = 0.75;
   private static final int POINT_COOLDOWN_TICKS = 60;
   private static final int EXPLOSIVE_COOLDOWN_TICKS = 60;
   private static final int BURST_INTERVAL_TICKS = 4;
   private static final double BURST_COST_PER_SHOT = 40.0;
   private static final double EXPLOSIVE_RADIUS = 3.0;

   public ExMachinasWeaponItem() {
      super(new Properties().stacksTo(1).rarity(Rarity.EPIC).fireResistant());
   }

   public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
      ItemStack itemStack = player.getItemInHand(hand);
      ExMachinasWeaponItem.FireMode mode = this.getMode(itemStack);
      if (player.isShiftKeyDown()) {
         if (!level.isClientSide) {
            ExMachinasWeaponItem.FireMode nextMode = mode.next();
            this.setMode(itemStack, nextMode);
            player.displayClientMessage(
               Component.translatable("item.tennogamenolife.ex_machinas_weapon.mode_switched", new Object[]{Component.translatable(nextMode.translationKey)})
                  .withStyle(ChatFormatting.AQUA),
               true
            );
         }

         return InteractionResultHolder.consume(itemStack);
      } else {
         if (player.getCooldowns().isOnCooldown(itemStack.getItem())) {
            return InteractionResultHolder.fail(itemStack);
         }

         if (mode == ExMachinasWeaponItem.FireMode.BURST) {
            if (!level.isClientSide) {
               if (!this.consumeMagicule(player, 40.0)) {
                  return InteractionResultHolder.fail(itemStack);
               }

               this.fireLaserBeam(level, player, mode);
            }

            player.startUsingItem(hand);
            return InteractionResultHolder.consume(itemStack);
         } else {
            if (!level.isClientSide) {
               if (!this.consumeMagicule(player, mode.magiculeCost)) {
                  return InteractionResultHolder.fail(itemStack);
               }

               this.fireLaserBeam(level, player, mode);
               if (mode.cooldownTicks > 0) {
                  player.getCooldowns().addCooldown(itemStack.getItem(), mode.cooldownTicks);
               }
            }

            return InteractionResultHolder.consume(itemStack);
         }
      }
   }

   public int getUseDuration(ItemStack stack, LivingEntity entity) {
      return Integer.MAX_VALUE;
   }

   public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingUseDuration) {
      if (livingEntity instanceof Player player) {
         if (this.getMode(stack) == ExMachinasWeaponItem.FireMode.BURST) {
            int elapsedTicks = this.getUseDuration(stack, livingEntity) - remainingUseDuration;
            if (elapsedTicks > 0 && elapsedTicks % 4 == 0) {
               if (!level.isClientSide) {
                  if (!this.consumeMagicule(player, 40.0)) {
                     player.stopUsingItem();
                     return;
                  }

                  this.fireLaserBeam(level, player, ExMachinasWeaponItem.FireMode.BURST);
               }
            }
         }
      }
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.NONE;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      ExMachinasWeaponItem.FireMode currentMode = this.getMode(stack);
      tooltip.add(
         Component.translatable("item.tennogamenolife.ex_machinas_weapon.current_mode", new Object[]{Component.translatable(currentMode.translationKey)})
            .withStyle(ChatFormatting.AQUA)
      );
      tooltip.add(Component.translatable("item.tennogamenolife.ex_machinas_weapon.mode_switch").withStyle(ChatFormatting.GRAY));
   }

   private void fireLaserBeam(Level level, Player player, ExMachinasWeaponItem.FireMode mode) {
      Vec3 startPos = player.getEyePosition(1.0F);
      ExMachinasWeaponItem.LaserHitResult hitResult = this.findLaserTarget(level, player, startPos);
      Vec3 endPos = hitResult.hitPos();

      try {
         SolarBeamProjectile laserBeam = new SolarBeamProjectile(level, player);
         laserBeam.setPos(startPos.x, startPos.y, startPos.z);
         laserBeam.setOwner(player);
         laserBeam.setFollowingOwner(false);
         laserBeam.setLife(5);
         laserBeam.setDamage(0.0F);
         laserBeam.setSecondaryDamage(0.0F);
         laserBeam.setSize(mode == ExMachinasWeaponItem.FireMode.EXPLOSIVE ? 0.48000002F : 0.3F);
         laserBeam.setRange((float)startPos.distanceTo(endPos));
         laserBeam.setExplosionRadius(0.0F);
         laserBeam.setTargetPos(endPos.x, endPos.y, endPos.z);
         laserBeam.updateAngle();
         level.addFreshEntity(laserBeam);
         this.applyModeDamage(level, player, hitResult, mode);
         level.playSound(
            null,
            startPos.x,
            startPos.y,
            startPos.z,
            SoundEvents.BEACON_POWER_SELECT,
            SoundSource.PLAYERS,
            mode == ExMachinasWeaponItem.FireMode.BURST ? 0.2F : 0.3F,
            mode == ExMachinasWeaponItem.FireMode.EXPLOSIVE ? 1.2F : 2.0F
         );
         if (mode == ExMachinasWeaponItem.FireMode.EXPLOSIVE) {
            level.playSound(null, endPos.x, endPos.y, endPos.z, SoundEvents.GENERIC_EXPLODE, SoundSource.PLAYERS, 0.8F, 1.2F);
         }
      } catch (Exception e) {
         System.err.println("Failed to create laser beam: " + e.getMessage());
      }
   }

   private void applyModeDamage(Level level, Player player, ExMachinasWeaponItem.LaserHitResult hitResult, ExMachinasWeaponItem.FireMode mode) {
      DamageSource damageSource = TensuraDamageTypes.getEntityDamageSource(level, TensuraDamageTypes.MAGIC_GENERIC, player);
      if (mode != ExMachinasWeaponItem.FireMode.EXPLOSIVE) {
         if (hitResult.target() != null) {
            hitResult.target().hurt(damageSource, mode.damage);
         }
      } else {
         AABB explosionBox = new AABB(hitResult.hitPos(), hitResult.hitPos()).inflate(3.0);

         for (LivingEntity target : level.getEntitiesOfClass(
            LivingEntity.class, explosionBox, living -> living.isAlive() && living != player && !living.isAlliedTo(player)
         )) {
            target.hurt(damageSource, mode.damage);
         }
      }
   }

   private boolean consumeMagicule(Player player, double amount) {
      if (player.hasInfiniteMaterials()) {
         return true;
      }

      IExistence existence = TensuraStorages.getExistenceFrom(player);
      return existence == null ? false : EnergyHelper.isOutOfMagiculeConsuming(player, amount) <= 0.0;
   }

   private ExMachinasWeaponItem.FireMode getMode(ItemStack stack) {
      CompoundTag tag = this.getWeaponDataTag(stack);
      return ExMachinasWeaponItem.FireMode.fromId(tag.getInt("WeaponMode"));
   }

   private void setMode(ItemStack stack, ExMachinasWeaponItem.FireMode mode) {
      CompoundTag tag = this.getWeaponDataTag(stack);
      tag.putInt("WeaponMode", mode.id);
      this.setWeaponDataTag(stack, tag);
   }

   private CompoundTag getWeaponDataTag(ItemStack stack) {
      return ((CustomData)stack.getOrDefault(DataComponents.CUSTOM_DATA, CustomData.EMPTY)).copyTag();
   }

   private void setWeaponDataTag(ItemStack stack, CompoundTag tag) {
      if (tag.isEmpty()) {
         stack.remove(DataComponents.CUSTOM_DATA);
      } else {
         stack.set(DataComponents.CUSTOM_DATA, CustomData.of(tag));
      }
   }

   private ExMachinasWeaponItem.LaserHitResult findLaserTarget(Level level, Player player, Vec3 startPos) {
      Vec3 lookDirection = player.getViewVector(1.0F);
      Vec3 maxEndPos = startPos.add(lookDirection.scale(64.0));
      BlockHitResult blockHitResult = level.clip(new ClipContext(startPos, maxEndPos, Block.COLLIDER, Fluid.NONE, player));
      Vec3 blockHitPos = blockHitResult.getType() == Type.MISS ? maxEndPos : blockHitResult.getLocation();
      double maxDistance = startPos.distanceTo(blockHitPos);
      AABB searchBox = player.getBoundingBox().expandTowards(lookDirection.scale(64.0)).inflate(0.75);
      List<Entity> possibleTargets = level.getEntities(
         player,
         searchBox,
         entityx -> entityx instanceof LivingEntity livingx
            && livingx.isAlive()
            && !entityx.isSpectator()
            && entityx.isPickable()
            && entityx != player
            && !entityx.isAlliedTo(player)
      );
      LivingEntity nearestTarget = null;
      Vec3 nearestHitPos = null;
      double nearestDistance = maxDistance;

      for (Entity entity : possibleTargets) {
         AABB entityBox = entity.getBoundingBox().inflate(0.75);
         Optional<Vec3> hitPos = entityBox.clip(startPos, blockHitPos);
         if (!hitPos.isEmpty()) {
            double distance = startPos.distanceTo(hitPos.get());
            if (distance < nearestDistance && entity instanceof LivingEntity living) {
               nearestDistance = distance;
               nearestTarget = living;
               nearestHitPos = hitPos.get();
            }
         }
      }

      return nearestTarget != null && nearestHitPos != null
         ? new ExMachinasWeaponItem.LaserHitResult(nearestTarget, nearestHitPos)
         : new ExMachinasWeaponItem.LaserHitResult(null, blockHitPos);
   }

   private enum FireMode {
      SEMI_AUTO(1, 15.0F, 200.0, 60, "item.tennogamenolife.ex_machinas_weapon.mode.semi_auto"),
      BURST(2, 4.0F, 40.0, 0, "item.tennogamenolife.ex_machinas_weapon.mode.burst"),
      EXPLOSIVE(3, 30.0F, 1000.0, 60, "item.tennogamenolife.ex_machinas_weapon.mode.explosive");

      private final int id;
      private final float damage;
      private final double magiculeCost;
      private final int cooldownTicks;
      private final String translationKey;

      FireMode(int id, float damage, double magiculeCost, int cooldownTicks, String translationKey) {
         this.id = id;
         this.damage = damage;
         this.magiculeCost = magiculeCost;
         this.cooldownTicks = cooldownTicks;
         this.translationKey = translationKey;
      }

      private ExMachinasWeaponItem.FireMode next() {
         return switch (this) {
            case SEMI_AUTO -> BURST;
            case BURST -> EXPLOSIVE;
            case EXPLOSIVE -> SEMI_AUTO;
         };
      }

      private static ExMachinasWeaponItem.FireMode fromId(int id) {
         for (ExMachinasWeaponItem.FireMode mode : values()) {
            if (mode.id == id) {
               return mode;
            }
         }

         return SEMI_AUTO;
      }
   }

   private record LaserHitResult(LivingEntity target, Vec3 hitPos) {
   }
}
