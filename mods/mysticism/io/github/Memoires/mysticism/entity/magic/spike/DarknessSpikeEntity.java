package io.github.Memoires.mysticism.entity.magic.spike;

import io.github.Memoires.mysticism.registry.entity.MysticismProjectileEntityTypes;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.entity.magic.spike.MagicSpikeEntity;
import io.github.manasmods.tensura.entity.template.subclass.IElementalSpirit;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import net.minecraft.core.particles.BlockParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;

public class DarknessSpikeEntity extends MagicSpikeEntity {
   public DarknessSpikeEntity(EntityType<? extends DarknessSpikeEntity> pEntityType, Level pLevel) {
      super(pEntityType, pLevel);
   }

   public DarknessSpikeEntity(Level pLevel, LivingEntity pOwner) {
      this((EntityType<? extends DarknessSpikeEntity>)MysticismProjectileEntityTypes.DARKNESS_SPIKE.get(), pLevel);
      this.setOwner(pOwner);
   }

   public ResourceLocation getTexture() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/entity/darkness_spike.png");
   }

   public ResourceKey<DamageType> getDamageType() {
      return TensuraDamageTypes.DARKNESS_ELEMENTAL;
   }

   public boolean canCollideWith(@NotNull Entity entity) {
      return !(entity instanceof IElementalSpirit) && super.canCollideWith(entity);
   }

   public void applyEffect(LivingEntity target) {
      DamageSource damageSource = this.getDamageSource(this.getDamageType(), 0.0F);
      if (TensuraDamageHelper.hurtDouble(target, damageSource, this.damage, damageSource, 0.0F)) {
         target.setDeltaMovement(target.getDeltaMovement().add(0.0, 1.0, 0.0));
      }
   }

   public void onBreak() {
      ParticleOptions particle = new BlockParticleOption(ParticleTypes.BLOCK, Blocks.DRIPSTONE_BLOCK.defaultBlockState());
      TensuraParticleHelper.addServerParticlesAroundSelf(this, particle);
      TensuraParticleHelper.addServerParticlesAroundSelf(this, particle);
      this.level().playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.DRIPSTONE_BLOCK_BREAK, SoundSource.PLAYERS, 1.0F, 1.0F);
      this.discard();
   }
}
