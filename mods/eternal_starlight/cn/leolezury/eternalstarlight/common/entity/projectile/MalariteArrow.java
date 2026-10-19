package cn.leolezury.eternalstarlight.common.entity.projectile;

import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import net.minecraft.core.particles.DustColorTransitionOptions;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

public class MalariteArrow extends AbstractArrow {
   private static final String TAG_DURATION = "duration";
   private int duration = 200;

   public MalariteArrow(EntityType<? extends MalariteArrow> entityType, Level level) {
      super(entityType, level);
   }

   public MalariteArrow(Level level, LivingEntity livingEntity, ItemStack itemStack, @Nullable ItemStack itemStack2) {
      super(ESEntities.MALARITE_ARROW.get(), livingEntity, level, itemStack, itemStack2);
   }

   public MalariteArrow(Level level, double d, double e, double f, ItemStack itemStack, @Nullable ItemStack itemStack2) {
      super(ESEntities.MALARITE_ARROW.get(), d, e, f, level, itemStack, itemStack2);
   }

   public void tick() {
      super.tick();
      if (this.level().isClientSide && !this.inGround) {
         this.level()
            .addParticle(
               new DustColorTransitionOptions(new Vector3f(0.6666667F, 0.31764707F, 0.61960787F), new Vector3f(0.16470589F, 0.23921569F, 0.21960784F), 1.0F),
               this.getX(),
               this.getY(),
               this.getZ(),
               0.0,
               0.0,
               0.0
            );
      }
   }

   protected void doPostHurtEffects(LivingEntity livingEntity) {
      livingEntity.addEffect(new MobEffectInstance(MobEffects.POISON, this.duration, 0), this.getEffectSource());
   }

   public void readAdditionalSaveData(CompoundTag compoundTag) {
      super.readAdditionalSaveData(compoundTag);
      if (compoundTag.contains("duration")) {
         this.duration = compoundTag.getInt("duration");
      }
   }

   public void addAdditionalSaveData(CompoundTag compoundTag) {
      super.addAdditionalSaveData(compoundTag);
      compoundTag.putInt("duration", this.duration);
   }

   protected ItemStack getDefaultPickupItem() {
      return ESItems.MALARITE_ARROW.get().getDefaultInstance();
   }
}
