package it.hurts.sskirillss.relics.entities;

import it.hurts.sskirillss.relics.init.RegistryRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.cast.containers.base.RelicContainer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MoverType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.Entity.MovementEmission;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.fluids.FluidType;

public class RelicExperienceOrbEntity extends Entity {
   private static final EntityDataAccessor<Integer> EXPERIENCE = SynchedEntityData.defineId(RelicExperienceOrbEntity.class, EntityDataSerializers.INT);

   public RelicExperienceOrbEntity(EntityType<? extends RelicExperienceOrbEntity> type, Level level) {
      super(type, level);
   }

   public static int getMaxExperience() {
      return 10;
   }

   public int getExperience() {
      return (Integer)this.getEntityData().get(EXPERIENCE);
   }

   public void setExperience(int experience) {
      this.getEntityData().set(EXPERIENCE, experience);
   }

   public int getStage() {
      int stages = 5;
      return Mth.clamp(this.getExperience() <= 1 ? 1 : Math.min(this.getExperience() / (getMaxExperience() / Math.max(1, stages - 1)) + 1, stages), 1, stages);
   }

   private List<ItemStack> getUpgradeableRelics(Player player) {
      List<ItemStack> relics = new ArrayList<>();

      for (RelicContainer source : RegistryRegistry.RELIC_CONTAINER_REGISTRY.entrySet().stream().map(Entry::getValue).toList()) {
         relics.addAll(source.gatherRelics().apply(player).stream().filter(entry -> !((IRelicItem)entry.getItem()).isRelicMaxLevel(entry)).toList());
      }

      return relics;
   }

   public void tick() {
      super.tick();
      if (!this.isNoGravity()) {
         this.setDeltaMovement(this.getDeltaMovement().add(0.0, -0.03, 0.0));
      }

      if (!this.level().noCollision(this.getBoundingBox())) {
         this.moveTowardsClosestSpace(this.getX(), (this.getBoundingBox().minY + this.getBoundingBox().maxY) / 2.0, this.getZ());
      }

      if (this.tickCount >= 15) {
         if (this.getExperience() < getMaxExperience()) {
            for (RelicExperienceOrbEntity orb : this.level().getEntitiesOfClass(RelicExperienceOrbEntity.class, this.getBoundingBox())) {
               if (!orb.getUUID().equals(this.getUUID()) && !orb.isRemoved() && orb.getExperience() < getMaxExperience()) {
                  int diff = getMaxExperience() - this.getExperience();
                  if (orb.getExperience() < diff) {
                     this.setExperience(this.getExperience() + orb.getExperience());
                     orb.discard();
                  } else {
                     orb.setExperience(orb.getExperience() - diff);
                     this.setExperience(getMaxExperience());
                  }
               }
            }
         }

         double maxDistance = 16.0;
         Player player = this.level().getNearestPlayer(this.getX(), this.getY(), this.getZ(), maxDistance, entity -> {
            Player entry = (Player)entity;
            return !entry.isSpectator() && !this.getUpgradeableRelics(entry).isEmpty();
         });
         if (player != null) {
            this.setDeltaMovement(
               this.getDeltaMovement()
                  .add(
                     player.position()
                        .add(0.0, player.getBbHeight() / 2.0F, 0.0)
                        .subtract(this.position())
                        .normalize()
                        .scale((maxDistance - this.position().distanceTo(player.position())) / (maxDistance * 10.0))
                  )
            );
            if (this.position().distanceTo(player.position()) <= player.getBbWidth() * 1.25F) {
               List<ItemStack> upgradeable = this.getUpgradeableRelics(player);
               if (!upgradeable.isEmpty()) {
                  ItemStack stack = upgradeable.get(this.random.nextInt(upgradeable.size()));
                  ((IRelicItem)stack.getItem()).spreadRelicExperience(player, stack, this.getExperience());
                  this.discard();
                  this.level()
                     .playSound(
                        null,
                        this.blockPosition(),
                        SoundEvents.EXPERIENCE_ORB_PICKUP,
                        SoundSource.MASTER,
                        0.5F,
                        1.25F + this.level().getRandom().nextFloat() * 0.75F
                     );
               }
            }
         }
      }

      this.move(MoverType.SELF, this.getDeltaMovement());
      float friction = 0.98F;
      if (this.onGround()) {
         BlockPos pos = this.getBlockPosBelowThatAffectsMyMovement();
         friction = this.level().getBlockState(pos).getFriction(this.level(), pos, this) * 0.98F;
      }

      this.setDeltaMovement(this.getDeltaMovement().multiply(friction, 0.98, friction));
      if (this.onGround()) {
         this.setDeltaMovement(this.getDeltaMovement().multiply(1.0, -0.9, 1.0));
      }

      if (this.tickCount >= 1000) {
         this.discard();
      }
   }

   protected void readAdditionalSaveData(CompoundTag tag) {
      this.setExperience(tag.getInt("experience"));
   }

   protected void addAdditionalSaveData(CompoundTag tag) {
      tag.putInt("experience", this.getExperience());
   }

   protected void defineSynchedData(Builder builder) {
      builder.define(EXPERIENCE, 0);
   }

   public BlockPos getBlockPosBelowThatAffectsMyMovement() {
      return this.getOnPos(0.999F);
   }

   protected MovementEmission getMovementEmission() {
      return MovementEmission.NONE;
   }

   public boolean isAttackable() {
      return false;
   }

   public SoundSource getSoundSource() {
      return SoundSource.AMBIENT;
   }

   public boolean isPushedByFluid(FluidType type) {
      return false;
   }

   public void onSyncedDataUpdated(EntityDataAccessor<?> pKey) {
      if (EXPERIENCE.equals(pKey)) {
         this.refreshDimensions();
      }

      super.onSyncedDataUpdated(pKey);
   }

   public EntityDimensions getDimensions(Pose pPose) {
      float scale = 0.15F + this.getStage() * 0.05F;
      return EntityDimensions.scalable(scale, scale);
   }

   public boolean shouldRenderAtSqrDistance(double pDistance) {
      double d0 = this.getBoundingBox().inflate(0.2F).getSize();
      if (Double.isNaN(d0)) {
         d0 = 1.0;
      }

      d0 *= 64.0;
      return pDistance < d0 * d0;
   }
}
