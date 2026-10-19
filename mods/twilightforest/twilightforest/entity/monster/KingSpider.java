package twilightforest.entity.monster;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier.Builder;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;
import twilightforest.init.TFEntities;
import twilightforest.init.TFSounds;

public class KingSpider extends Spider {
   public KingSpider(EntityType<? extends KingSpider> type, Level world) {
      super(type, world);
   }

   public static Builder registerAttributes() {
      return Spider.createAttributes().add(Attributes.MAX_HEALTH, 30.0).add(Attributes.MOVEMENT_SPEED, 0.35).add(Attributes.ATTACK_DAMAGE, 6.0);
   }

   protected SoundEvent getAmbientSound() {
      return (SoundEvent)TFSounds.KING_SPIDER_AMBIENT.get();
   }

   protected SoundEvent getHurtSound(DamageSource source) {
      return (SoundEvent)TFSounds.KING_SPIDER_HURT.get();
   }

   protected SoundEvent getDeathSound() {
      return (SoundEvent)TFSounds.KING_SPIDER_DEATH.get();
   }

   protected void playStepSound(BlockPos pos, BlockState blockIn) {
      this.playSound((SoundEvent)TFSounds.KING_SPIDER_STEP.get(), 0.15F, 1.0F);
   }

   public boolean onClimbable() {
      return false;
   }

   @Nullable
   public SpawnGroupData finalizeSpawn(ServerLevelAccessor accessor, DifficultyInstance difficulty, MobSpawnType reason, @Nullable SpawnGroupData data) {
      data = super.finalizeSpawn(accessor, difficulty, reason, data);
      SkeletonDruid druid = (SkeletonDruid)((EntityType)TFEntities.SKELETON_DRUID.get()).create(this.level());
      if (druid != null) {
         druid.moveTo(this.getX(), this.getY(), this.getZ(), this.getYRot(), 0.0F);
         druid.finalizeSpawn(accessor, difficulty, MobSpawnType.JOCKEY, null);
         Entity lastRider = this;

         while (!lastRider.getPassengers().isEmpty()) {
            lastRider = (Entity)lastRider.getPassengers().get(0);
         }

         druid.startRiding(lastRider);
      }

      return data;
   }

   protected Vec3 getPassengerAttachmentPoint(Entity entity, EntityDimensions dimensions, float yRot) {
      return new Vec3(0.0, dimensions.height() * 0.85F, 0.0);
   }
}
