package io.github.Memoires.mysticism.entity.magic.field;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.Memoires.mysticism.registry.entity.MysticismSkillEntityTypes;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.data.TensuraEntityTags;
import io.github.manasmods.tensura.entity.magic.field.AreaField;
import io.github.manasmods.tensura.entity.magic.lightning.LightningBolt;
import io.github.manasmods.tensura.registry.skill.ExtraSkills;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import java.util.List;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.animatable.instance.AnimatableInstanceCache;
import software.bernie.geckolib.animation.AnimationController;
import software.bernie.geckolib.animation.RawAnimation;
import software.bernie.geckolib.animation.AnimatableManager.ControllerRegistrar;
import software.bernie.geckolib.util.GeckoLibUtil;

public class BlueFlare extends AreaField implements GeoEntity {
   protected static final UniqueMysticismSkillConfig.Reshiram CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(UniqueMysticismSkillConfig.class)).Reshiram;
   private final AnimatableInstanceCache cache;
   protected boolean attract = false;
   protected boolean hasZekrom = false;

   public BlueFlare(EntityType<? extends Projectile> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
      this.cache = GeckoLibUtil.createInstanceCache(this);
      this.setBurnTicks(100);
   }

   public BlueFlare(Level level, Entity entity) {
      this((EntityType<? extends Projectile>)MysticismSkillEntityTypes.BLUE_FLARE.get(), level);
      this.setOwner(entity);
      this.setBurnTicks(100);
   }

   protected boolean canHitEntity(Entity pTarget) {
      if (pTarget == this.getOwner()) {
         return false;
      } else {
         return !super.canHitEntity(pTarget) ? false : !this.isAlly(pTarget);
      }
   }

   public boolean shouldRenderAtSqrDistance(double pDistance) {
      return pDistance < 4096.0 || super.shouldRenderAtSqrDistance(pDistance);
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.BURN;
   }

   private boolean isAlly(Entity target) {
      Entity owner = this.getOwner();
      return owner != null && target.isAlliedTo(owner);
   }

   public boolean isInstant() {
      return true;
   }

   public void tick() {
      super.tick();
      if (this.getAge() >= this.getTickEachHit()) {
         this.level()
            .playSound(null, this.getX(), this.getY(), this.getZ(), (SoundEvent)TensuraSoundEvents.CAST_FIRE.get(), SoundSource.WEATHER, this.getSize(), 1.0F);
         if (this.isAttract()) {
            this.applyPull();
         }
      }
   }

   private void applyPull() {
      List<LivingEntity> livingEntityList = this.level()
         .getEntitiesOfClass(
            LivingEntity.class,
            this.getBoundingBox().inflate(this.getSize()),
            entityData -> this.getOwner() == null || !entityData.isAlliedTo(this.getOwner()) && !entityData.is(this.getOwner())
         );
      if (!livingEntityList.isEmpty()) {
         for (LivingEntity target : livingEntityList) {
            if (!target.getType().is(TensuraEntityTags.FULL_GRAVITY_CONTROL)
               && !SkillUtils.isSkillToggled(target, (ManasSkill)ExtraSkills.GRAVITY_DOMINATION.get())) {
               Vec3 vec3 = new Vec3(this.getX() - target.getX(), this.getY() - target.getY(), this.getZ() - target.getZ());
               target.setDeltaMovement(target.getDeltaMovement().add(vec3.scale(0.3F)));
            }
         }
      }
   }

   protected boolean dealDamage(Entity target) {
      if (this.hasZekrom()) {
         Vec3 pos = target.position();
         LightningBolt bolt = new LightningBolt(this.level(), this.getOwner());
         bolt.setTensuraDamage(CONFIG.fusionFlareZekromBonus);
         bolt.setPos(pos);
         bolt.setRadius(8.0F);
         bolt.setAdditionalVisual(6);
         this.level().addFreshEntity(bolt);
      }

      return super.dealDamage(target);
   }

   public void applyEffect(LivingEntity target, boolean instant) {
      if (instant) {
         super.applyEffect(target, true);
      } else {
         target.setRemainingFireTicks(Math.max(target.getRemainingFireTicks(), 100));
      }
   }

   public void registerControllers(ControllerRegistrar controllers) {
      controllers.add(
         new AnimationController(this, "controller", 0, event -> event.setAndContinue(RawAnimation.begin().thenPlayAndHold("animation.hell_flare.start")))
      );
   }

   public AnimatableInstanceCache getAnimatableInstanceCache() {
      return this.cache;
   }

   public void setAttract(boolean attract) {
      this.attract = attract;
   }

   public boolean isAttract() {
      return this.attract;
   }

   public void setHasZekrom(boolean hasZekrom) {
      this.hasZekrom = hasZekrom;
   }

   public boolean hasZekrom() {
      return this.hasZekrom;
   }
}
