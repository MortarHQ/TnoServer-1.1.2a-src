package com.iafenvoy.iceandfire.entity;

import com.iafenvoy.iceandfire.entity.util.IDreadMob;
import com.iafenvoy.iceandfire.entity.util.IHumanoid;
import com.iafenvoy.iceandfire.registry.IafEntities;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.server.players.OldUsersConverter;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;

public class DreadMobEntity extends Monster implements IDreadMob {
   protected static final EntityDataAccessor<Optional<UUID>> COMMANDER_UNIQUE_ID = SynchedEntityData.defineId(
      DreadMobEntity.class, EntityDataSerializers.OPTIONAL_UUID
   );

   public DreadMobEntity(EntityType<? extends Monster> t, Level worldIn) {
      super(t, worldIn);
   }

   public static Entity necromancyEntity(LivingEntity entity) {
      if (entity.getType().is(EntityTypeTags.ARTHROPOD)) {
         DreadScuttlerEntity lichSummoned = new DreadScuttlerEntity((EntityType<? extends DreadScuttlerEntity>)IafEntities.DREAD_SCUTTLER.get(), entity.level());
         float readInScale = entity.getBbWidth() / 1.5F;
         if (entity.level() instanceof ServerLevelAccessor serverWorldAccess) {
            lichSummoned.finalizeSpawn(serverWorldAccess, entity.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
         }

         lichSummoned.setSize(readInScale);
         return lichSummoned;
      } else if (!(entity instanceof Zombie) && !(entity instanceof IHumanoid)) {
         if (entity.getType().is(EntityTypeTags.UNDEAD) || entity instanceof AbstractSkeleton || entity instanceof Player) {
            DreadThrallEntity lichSummoned = new DreadThrallEntity((EntityType<? extends DreadThrallEntity>)IafEntities.DREAD_THRALL.get(), entity.level());
            if (entity.level() instanceof ServerLevelAccessor serverWorldAccess) {
               lichSummoned.finalizeSpawn(serverWorldAccess, entity.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
            }

            lichSummoned.setCustomArmorHead(false);
            lichSummoned.setCustomArmorChest(false);
            lichSummoned.setCustomArmorLegs(false);
            lichSummoned.setCustomArmorFeet(false);

            for (EquipmentSlot slot : EquipmentSlot.values()) {
               lichSummoned.setItemSlot(slot, entity.getItemBySlot(slot));
            }

            return lichSummoned;
         } else {
            if (entity instanceof AbstractHorse) {
               return new DreadHorseEntity((EntityType<? extends DreadHorseEntity>)IafEntities.DREAD_HORSE.get(), entity.level());
            }

            if (entity instanceof Animal) {
               DreadBeastEntity lichSummoned = new DreadBeastEntity((EntityType<? extends DreadBeastEntity>)IafEntities.DREAD_BEAST.get(), entity.level());
               float readInScale = entity.getBbWidth() / 1.2F;
               if (entity.level() instanceof ServerLevelAccessor serverWorldAccess) {
                  lichSummoned.finalizeSpawn(serverWorldAccess, entity.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
               }

               lichSummoned.setSize(readInScale);
               return lichSummoned;
            } else {
               return null;
            }
         }
      } else {
         DreadGhoulEntity lichSummoned = new DreadGhoulEntity((EntityType<? extends DreadGhoulEntity>)IafEntities.DREAD_GHOUL.get(), entity.level());
         float readInScale = entity.getBbWidth() / 0.6F;
         if (entity.level() instanceof ServerLevelAccessor serverWorldAccess) {
            lichSummoned.finalizeSpawn(serverWorldAccess, entity.level().getCurrentDifficultyAt(entity.blockPosition()), MobSpawnType.MOB_SUMMONED, null);
         }

         lichSummoned.setSize(readInScale);
         return lichSummoned;
      }
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
      builder.define(COMMANDER_UNIQUE_ID, Optional.empty());
   }

   public void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      if (this.getCommanderId() != null) {
         compound.putUUID("CommanderUUID", this.getCommanderId());
      }
   }

   public void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      UUID uuid;
      if (compound.hasUUID("CommanderUUID")) {
         uuid = compound.getUUID("CommanderUUID");
      } else {
         String s = compound.getString("CommanderUUID");
         uuid = OldUsersConverter.convertMobOwnerIfNecessary(this.getServer(), s);
      }

      if (uuid != null) {
         try {
            this.setCommanderId(uuid);
         } catch (Throwable var4) {
         }
      }
   }

   public boolean isAlliedTo(Entity entityIn) {
      return entityIn instanceof IDreadMob || super.isAlliedTo(entityIn);
   }

   public UUID getCommanderId() {
      return (UUID)((Optional)this.entityData.get(COMMANDER_UNIQUE_ID)).orElse(null);
   }

   public void setCommanderId(UUID uuid) {
      this.entityData.set(COMMANDER_UNIQUE_ID, Optional.ofNullable(uuid));
   }

   public void aiStep() {
      super.aiStep();
      if (!this.level().isClientSide && this.getCommander() instanceof DreadLichEntity lich && lich.getTarget() != null && lich.getTarget().isAlive()) {
         this.setTarget(lich.getTarget());
      }
   }

   @Override
   public Entity getCommander() {
      try {
         UUID uuid = this.getCommanderId();
         LivingEntity player = uuid == null ? null : this.level().getPlayerByUUID(uuid);
         if (player != null) {
            return player;
         }

         if (!this.level().isClientSide) {
            Entity entity = this.level().getServer().getLevel(this.level().dimension()).getEntity(uuid);
            if (entity instanceof LivingEntity) {
               return entity;
            }
         }

         return null;
      } catch (IllegalArgumentException var2) {
         return null;
      }
   }

   public void onKillEntity(LivingEntity LivingEntityIn) {
      Entity commander = (Entity)(this instanceof DreadLichEntity ? this : this.getCommander());
      if (commander != null && !(LivingEntityIn instanceof DragonBaseEntity)) {
         Entity summoned = necromancyEntity(LivingEntityIn);
         if (summoned != null) {
            summoned.copyPosition(LivingEntityIn);
            if (!this.level().isClientSide) {
               this.level().addFreshEntity(summoned);
            }

            if (commander instanceof DreadLichEntity lich) {
               lich.setMinionCount(lich.getMinionCount() + 1);
            }

            if (summoned instanceof DreadMobEntity mob) {
               mob.setCommanderId(commander.getUUID());
            }
         }
      }
   }

   public void remove(RemovalReason reason) {
      if (!this.isRemoved() && this.getCommander() != null && this.getCommander() instanceof DreadLichEntity lich) {
         lich.setMinionCount(lich.getMinionCount() - 1);
      }

      super.remove(reason);
   }
}
