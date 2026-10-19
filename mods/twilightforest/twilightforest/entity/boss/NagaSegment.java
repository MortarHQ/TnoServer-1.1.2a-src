package twilightforest.entity.boss;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.level.block.state.BlockState;
import twilightforest.TwilightForestMod;
import twilightforest.entity.TFPart;
import twilightforest.init.TFSounds;

public class NagaSegment extends TFPart<Naga> {
   public static final ResourceLocation RENDERER = TwilightForestMod.prefix("naga_segment");
   private int deathCounter;

   public NagaSegment(Naga naga) {
      super(naga);
      this.setPos(naga.getX(), naga.getY(), naga.getZ());
      this.deactivate();
   }

   protected void defineSynchedData(Builder builder) {
   }

   @Override
   public ResourceLocation renderer() {
      return RENDERER;
   }

   public boolean hurt(DamageSource src, float damage) {
      return !this.isInvisible() && ((Naga)this.getParent()).hurt(src, damage * 2.0F / 3.0F);
   }

   public boolean is(Entity entity) {
      return entity == this || entity == this.getParent();
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
   }

   @Override
   public void tick() {
      super.tick();
      this.tickCount++;
      if (!this.isInvisible()) {
         this.collideWithOthers();
      }

      if (this.deathCounter > 0) {
         this.deathCounter--;
         if (this.deathCounter <= 0) {
            Naga naga = (Naga)this.getParent();
            naga.makePoofAt(this.position());
            naga.playSound((SoundEvent)TFSounds.NAGA_HURT.get(), 0.25F, naga.getVoicePitch() * 0.75F + 0.5F * naga.getRandom().nextFloat());
            naga.deathTime = 0;
            this.deactivate();
         }
      }
   }

   private void collideWithOthers() {
      for (Entity entity : this.level().getEntities(this, this.getBoundingBox())) {
         if (entity.isPushable()) {
            this.collideWithEntity(entity);
         }
      }
   }

   private void collideWithEntity(Entity entity) {
      entity.push(this);
      if (entity instanceof LivingEntity && !(entity instanceof Naga) && !((Naga)this.getParent()).isDazed() && !((Naga)this.getParent()).isDeadOrDying()) {
         int attackStrength = 2;
         if (entity instanceof Animal) {
            attackStrength *= 3;
         }

         entity.hurt(entity.level().damageSources().mobAttack((LivingEntity)this.getParent()), attackStrength);
      }
   }

   public void deactivate() {
      this.setSize(EntityDimensions.scalable(0.0F, 0.0F));
      this.setInvisible(true);
   }

   public void activate() {
      this.setSize(EntityDimensions.scalable(2.0F, 2.0F));
      this.setInvisible(false);
   }

   public void setRot(float yaw, float pitch) {
      super.setRot(yaw, pitch);
   }

   protected void playStepSound(BlockPos pos, BlockState block) {
   }

   public void selfDestruct(int counter) {
      this.deathCounter = counter;
   }

   public boolean canUsePortal(boolean force) {
      return false;
   }
}
