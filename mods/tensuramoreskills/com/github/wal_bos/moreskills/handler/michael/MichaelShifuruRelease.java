package com.github.wal_bos.moreskills.handler.michael;

import com.github.wal_bos.moreskills.configs.MoreSkillsConfigs;
import com.github.wal_bos.moreskills.entity.michael.MichaelMeteorEntity;
import com.github.wal_bos.moreskills.net.msg.MichaelAuraVfxPayload;
import com.github.wal_bos.moreskills.net.msg.MichaelShifuruMeteorVfxPayload;
import com.github.wal_bos.moreskills.registry.TenaddexItems;
import com.github.wal_bos.moreskills.registry.entity.ModEntityTypes;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;

public final class MichaelShifuruRelease implements MichaelAttack {
   private static final String ARMED = "MichaelShifuruArmed";
   private static final Map<UUID, MichaelShifuruRelease.MeteorState> METEORS = new ConcurrentHashMap<>();
   private static final Map<UUID, Integer> LAST_CHARGE_PACKET = new ConcurrentHashMap<>();

   @Override
   public String modeId() {
      return "michael_angel_of_locks.shifuru";
   }

   @Override
   public double magiculeCost(LivingEntity entity, ManasSkillInstance instance) {
      return ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruCost.get()).intValue();
   }

   @Override
   public void activate(ManasSkillInstance instance, LivingEntity entity, int keyNumber, int mode) {
      if (entity.level() instanceof ServerLevel level) {
         if (!EnergyHelper.isOutOfEnergy(entity, instance, mode)) {
            boolean currentlyAwakened = MichaelActions.isHoldingAwakened(entity);
            ItemStack newStack = currentlyAwakened
               ? ((Item)TenaddexItems.KEY_OF_SEALING.get()).getDefaultInstance()
               : ((Item)TenaddexItems.AWAKENED_KEY_OF_SEALING.get()).getDefaultInstance();
            MichaelActions.enchantKey(level, newStack, !currentlyAwakened);
            entity.setItemInHand(InteractionHand.MAIN_HAND, newStack);
            entity.getPersistentData().putBoolean("MichaelShifuruArmed", !currentlyAwakened);
            entity.swing(InteractionHand.MAIN_HAND, true);
            entity.level()
               .playSound(
                  null,
                  entity.getX(),
                  entity.getY(),
                  entity.getZ(),
                  (SoundEvent)TensuraSoundEvents.BUFF_ACTIVATE.get(),
                  SoundSource.PLAYERS,
                  1.2F,
                  currentlyAwakened ? 0.7F : 1.2F
               );
            MichaelAuraVfxPayload.send(entity, 5.5F, 120);
            if (entity instanceof Player player) {
               player.displayClientMessage(
                  Component.literal(currentlyAwakened ? "Michael returns to key form." : "Michael releases its true form.").withStyle(ChatFormatting.GOLD),
                  true
               );
            }

            instance.addMasteryPoint(entity);
            instance.setCoolDown(20, mode);
         }
      }
   }

   @Override
   public boolean onHeld(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int mode) {
      if (entity.level().isClientSide()) {
         return true;
      }

      if (!MichaelActions.isHoldingAwakened(entity)) {
         return false;
      }

      if (entity.level() instanceof ServerLevel level) {
         int var8 = Math.min(10, heldTicks / 20);
         if (heldTicks % 20 == 0 && entity instanceof Player player) {
            player.displayClientMessage(Component.literal("Meteor Shower: " + var8 + "/10").withStyle(ChatFormatting.LIGHT_PURPLE), true);
         }

         if (heldTicks >= 40) {
            level.setDayTime(18000L);
            int last = LAST_CHARGE_PACKET.getOrDefault(entity.getUUID(), -999999);
            if (heldTicks - last >= 16) {
               LAST_CHARGE_PACKET.put(entity.getUUID(), heldTicks);
               MichaelShifuruMeteorVfxPayload.sendWormhole(entity, Math.min(heldTicks - 40, 260), 190);
            }
         }

         int required = (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorHoldTicks.get();
         if (heldTicks >= required && !hasActive(entity)) {
            if (EnergyHelper.isOutOfEnergy(entity, ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorCost.get()).intValue(), 0.0)) {
               return false;
            }

            LAST_CHARGE_PACKET.remove(entity.getUUID());
            startMeteor(level, entity, instance.isMastered(entity));
            entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 4.8F, 0.22F);
            instance.addMasteryPoint(entity);
            instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorCooldownTicks.get(), mode);
            return false;
         } else {
            return true;
         }
      } else {
         return true;
      }
   }

   @Override
   public void onRelease(ManasSkillInstance instance, LivingEntity entity, int heldTicks, int keyNumber, int mode) {
      LAST_CHARGE_PACKET.remove(entity.getUUID());
      if (entity.level() instanceof ServerLevel level) {
         if (heldTicks >= (Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorHoldTicks.get()) {
            if (!hasActive(entity)) {
               if (!EnergyHelper.isOutOfEnergy(entity, ((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorCost.get()).intValue(), 0.0)) {
                  startMeteor(level, entity, instance.isMastered(entity));
                  entity.level().playSound(null, entity.getX(), entity.getY(), entity.getZ(), SoundEvents.END_PORTAL_SPAWN, SoundSource.PLAYERS, 4.8F, 0.22F);
                  instance.addMasteryPoint(entity);
                  instance.setCoolDown((Integer)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruMeteorCooldownTicks.get(), mode);
               }
            }
         }
      }
   }

   public static boolean hasActive(LivingEntity entity) {
      return METEORS.containsKey(entity.getUUID());
   }

   private static void startMeteor(ServerLevel level, LivingEntity owner, boolean mastered) {
      int duration = mastered ? 1500 : 1320;
      level.setDayTime(18000L);
      Vec3 rawLook = owner.getLookAngle();
      Vec3 look = new Vec3(rawLook.x, 0.0, rawLook.z);
      if (look.lengthSqr() < 0.001) {
         look = new Vec3(0.0, 0.0, 1.0);
      } else {
         look = look.normalize();
      }

      Vec3 center = owner.position().add(look.scale(mastered ? 30.0 : 24.0));
      int totalSmall = mastered ? 176 : 142;
      METEORS.put(
         owner.getUUID(),
         new MichaelShifuruRelease.MeteorState(
            owner.getUUID(), level.dimension(), center, look, level.getGameTime() + duration, level.getGameTime(), mastered, totalSmall
         )
      );
      MichaelShifuruMeteorVfxPayload.sendMeteor(level, owner, center, mastered ? 180.0F : 150.0F, duration + 560, mastered);
   }

   private static void spawnSmallMeteor(ServerLevel level, LivingEntity owner, MichaelShifuruRelease.MeteorState state, int index) {
      Vec3 look = state.look;
      Vec3 right = look.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 0.001) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      boolean mastered = state.mastered;
      int columns = mastered ? 19 : 17;
      double row = index % columns - (columns - 1) * 0.5;
      double lane = (double)index / columns;
      double wave = Math.sin(index * 0.47) * (mastered ? 9.0 : 7.0);
      double side = row * (mastered ? 24.0 : 21.0) + wave + (level.random.nextDouble() - 0.5) * 18.0;
      double forward = -40.0 + lane * 9.0 + level.random.nextDouble() * 42.0;
      Vec3 target = state.center
         .add(right.scale(side))
         .add(look.scale(forward))
         .add((level.random.nextDouble() - 0.5) * 5.0, 0.0, (level.random.nextDouble() - 0.5) * 5.0);
      Vec3 start = target.subtract(look.scale(145.0 + level.random.nextDouble() * 165.0))
         .add(right.scale((level.random.nextDouble() - 0.5) * 30.0))
         .add(0.0, 92.0 + level.random.nextDouble() * 36.0, 0.0);
      MichaelMeteorEntity meteor = (MichaelMeteorEntity)((EntityType)ModEntityTypes.MICHAEL_METEOR.get()).create(level);
      if (meteor != null) {
         meteor.setup(owner, start, target, mastered ? 185.0F : 138.0F, mastered ? 92.0F : 68.0F, mastered ? 6.4F : 5.2F, false);
         level.addFreshEntity(meteor);
      }
   }

   private static void spawnBigMeteor(ServerLevel level, LivingEntity owner, MichaelShifuruRelease.MeteorState state) {
      Vec3 look = state.look;
      Vec3 right = look.cross(new Vec3(0.0, 1.0, 0.0));
      if (right.lengthSqr() < 0.001) {
         right = new Vec3(1.0, 0.0, 0.0);
      }

      right = right.normalize();
      Vec3 bigTarget = state.center.add(state.look.scale(state.mastered ? 10.0 : 6.0));
      Vec3 bigStart = bigTarget.subtract(look.scale(state.mastered ? 340.0 : 300.0))
         .add(right.scale(state.mastered ? -14.0 : -10.0))
         .add(0.0, state.mastered ? 156.0 : 138.0, 0.0);
      MichaelMeteorEntity bigMeteor = (MichaelMeteorEntity)((EntityType)ModEntityTypes.MICHAEL_METEOR.get()).create(level);
      if (bigMeteor != null) {
         float damage = ((Double)MoreSkillsConfigs.GRAND.michaelAngelOfLocks.shifuruBigMeteorDamage.get()).floatValue();
         bigMeteor.setup(
            owner,
            bigStart,
            bigTarget,
            state.mastered ? damage : damage * 0.78F,
            state.mastered ? damage * 0.6F : damage * 0.46F,
            state.mastered ? 118.0F : 96.0F,
            true
         );
         level.addFreshEntity(bigMeteor);
      }
   }

   public static void tick(ServerLevel level) {
      Iterator<MichaelShifuruRelease.MeteorState> iterator = METEORS.values().iterator();

      while (iterator.hasNext()) {
         MichaelShifuruRelease.MeteorState state = iterator.next();
         if (state.dimension.equals(level.dimension())) {
            long now = level.getGameTime();
            LivingEntity owner = entity(level, state.owner);
            if (now <= state.endTick && owner != null) {
               long age = now - state.startTick;
               if (age % 10L == 0L && state.spawnedSmall < state.totalSmall) {
                  spawnSmallMeteor(level, owner, state, state.spawnedSmall++);
               }

               if (!state.bigSpawned && age >= (state.mastered ? 980L : 860L)) {
                  state.bigSpawned = true;
                  spawnBigMeteor(level, owner, state);
               }
            } else {
               iterator.remove();
            }
         }
      }
   }

   private static LivingEntity entity(ServerLevel level, UUID uuid) {
      return level.getEntity(uuid) instanceof LivingEntity living ? living : null;
   }

   private static final class MeteorState {
      private final UUID owner;
      private final ResourceKey<Level> dimension;
      private final Vec3 center;
      private final Vec3 look;
      private final long endTick;
      private final long startTick;
      private final boolean mastered;
      private final int totalSmall;
      private int spawnedSmall;
      private boolean bigSpawned;

      private MeteorState(UUID owner, ResourceKey<Level> dimension, Vec3 center, Vec3 look, long endTick, long startTick, boolean mastered, int totalSmall) {
         this.owner = owner;
         this.dimension = dimension;
         this.center = center;
         this.look = look;
         this.endTick = endTick;
         this.startTick = startTick;
         this.mastered = mastered;
         this.totalSmall = totalSmall;
      }
   }
}
