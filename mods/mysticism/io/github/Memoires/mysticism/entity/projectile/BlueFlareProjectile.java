package io.github.Memoires.mysticism.entity.projectile;

import io.github.Memoires.mysticism.entity.magic.field.BlueFlare;
import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.manascore.skill.api.EntityEvents.ProjectileHitResult;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.projectile.TensuraFlyingProjectile;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import java.util.Optional;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.SynchedEntityData.Builder;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;

public class BlueFlareProjectile extends TensuraFlyingProjectile {
   protected static final ResourceLocation[] TEXTURES = new ResourceLocation[]{
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_0.png"),
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_1.png"),
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_2.png"),
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_3.png"),
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_4.png"),
      ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/projectiles/blue_flare_ball/blue_flare_ball_5.png")
   };
   protected float areaRadius = 0.0F;
   protected int areaLife = 0;
   protected boolean attract = false;
   protected boolean hasZekrom = false;

   public BlueFlareProjectile(EntityType<? extends BlueFlareProjectile> entityType, Level level) {
      super(entityType, level);
      this.setElement(Element.FLAME);
   }

   public BlueFlareProjectile(Level levelIn, Entity shooter) {
      super((EntityType)MysticismProjectileEntityTypes.BLUE_FLARE_PROJECTILE.get(), levelIn);
      this.setOwner(shooter);
      this.setElement(Element.FLAME);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.FIRE_ELEMENTAL;
   }

   public ResourceLocation[] getTextureLocation() {
      return TEXTURES;
   }

   public boolean shouldDiscardInWater() {
      return false;
   }

   public boolean shouldDiscardInLava() {
      return false;
   }

   protected void defineSynchedData(Builder builder) {
      super.defineSynchedData(builder);
   }

   protected void readAdditionalSaveData(CompoundTag compound) {
      super.readAdditionalSaveData(compound);
      this.areaLife = compound.getInt("AreaLife");
      this.areaRadius = compound.getFloat("AreaLife");
   }

   protected void addAdditionalSaveData(CompoundTag compound) {
      super.addAdditionalSaveData(compound);
      compound.putInt("AreaLife", this.areaLife);
      compound.putFloat("AreaLife", this.areaRadius);
   }

   public void setPosAndShoot(LivingEntity entity) {
      this.setPos(entity.position().add(0.0, entity.getEyeHeight(), 0.0));
      this.shootFromRot(entity.getLookAngle());
   }

   protected void onHitBlock(BlockHitResult pResult) {
      if (!this.level().isClientSide()) {
         this.summonBlueFlare(pResult.getLocation());
         this.discard();
      }
   }

   protected void onHitEntity(@NotNull EntityHitResult result, ProjectileHitResult customResult) {
      if (!this.level().isClientSide()) {
         this.summonBlueFlare(result.getEntity().position());
         this.discard();
      }
   }

   protected boolean dealDamage(Entity target, float damage, float costMultiplier) {
      if (damage <= 0.0F) {
         return false;
      }

      DamageSource source = this.getDamageSource(costMultiplier);
      return TensuraDamageHelper.hurtSplitElemental(target, source, 0.9F, damage);
   }

   private void summonBlueFlare(Vec3 position) {
      BlueFlare flare = new BlueFlare(this.level(), this.getOwner());
      flare.setDamage(this.getDamage());
      flare.setTickEachHit(10);
      flare.setMpCost(this.getMpCost());
      flare.setSkill(this.getSkill());
      flare.setLife(this.getAreaLife());
      flare.setSize(this.getAreaRadius());
      flare.setVisualSize(flare.getSize());
      flare.setAttract(this.isAttract());
      flare.setHasZekrom(this.hasZekrom());
      flare.setPos(position.add(0.0, flare.getBbHeight() * -0.4F, 0.0));
      this.level().addFreshEntity(flare);
      this.level()
         .playSound(
            null, this.getX(), this.getY(), this.getZ(), SoundEvents.GENERIC_EXPLODE, SoundSource.NEUTRAL, 3.0F, 0.9F + this.level().random.nextFloat() * 0.2F
         );
   }

   public Optional<SoundEvent> hitSound() {
      return Optional.of((SoundEvent)SoundEvents.GENERIC_EXPLODE.value());
   }

   public void flyingParticles() {
      Vec3 vec3 = this.getDeltaMovement();
      double d0 = this.getX() - vec3.x;
      double d1 = this.getY() - vec3.y;
      double d2 = this.getZ() - vec3.z;

      for (int i = 0; i < 8; i++) {
         Vec3 motion = this.getRandomVec3().scale(0.1F).subtract(this.getDeltaMovement().scale(0.1F));
         Vec3 pos = this.getRandomVec3().scale(0.3F);
         this.level()
            .addParticle((ParticleOptions)TensuraParticleTypes.BLACK_FIRE.get(), d0 + pos.x, d1 + 0.5 + pos.y, d2 + pos.z, motion.x, motion.y, motion.z);
      }
   }

   public float getAreaRadius() {
      return this.areaRadius;
   }

   public void setAreaRadius(float areaRadius) {
      this.areaRadius = areaRadius;
   }

   public int getAreaLife() {
      return this.areaLife;
   }

   public void setAreaLife(int areaLife) {
      this.areaLife = areaLife;
   }

   public boolean isAttract() {
      return this.attract;
   }

   public void setAttract(boolean attract) {
      this.attract = attract;
   }

   public boolean hasZekrom() {
      return this.hasZekrom;
   }

   public void setHasZekrom(boolean hasZekrom) {
      this.hasZekrom = hasZekrom;
   }
}
