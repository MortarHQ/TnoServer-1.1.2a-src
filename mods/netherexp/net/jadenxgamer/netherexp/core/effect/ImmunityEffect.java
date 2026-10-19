package net.jadenxgamer.netherexp.core.effect;

import java.util.Optional;
import net.jadenxgamer.elysium_api.api.util.LookupRegistryHelper;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.registry.JNEParticleTypes;
import net.jadenxgamer.netherexp.registry.JNESoundEvents;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImmunityEffect extends IncurableEffect {
   protected static final int DEFAULT_COLOR = 8421504;
   protected final ResourceLocation immunityOf;
   protected Holder<MobEffect> cachedImmunityHolder;
   protected Holder<MobEffect> cachedSelfHolder;

   public ImmunityEffect(MobEffectCategory category, ResourceLocation immunityOf) {
      super(category, 0);
      this.immunityOf = immunityOf;
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return this.getImmunityHolder() != null;
   }

   public boolean applyEffectTick(LivingEntity entity, int amplifier) {
      Holder<MobEffect> immuneTo = this.getImmunityHolder();
      if (immuneTo == null) {
         return true;
      }

      Holder<MobEffect> self = this.getSelfHolder();
      if (self != null && entity.hasEffect(immuneTo)) {
         MobEffectInstance selfEffect = entity.getEffect(self);
         if (selfEffect == null) {
            return true;
         }

         int otherAmplifier = entity.getEffect(immuneTo).getAmplifier() + 1;
         int newDuration = selfEffect.getDuration()
            - (Integer)JNEConfigs.IMMUNITY_CONSUMPTION.get() * (JNEConfigs.AMPLIFIER_SCALES_IMMUNITY_CONSUMPTION.get() ? otherAmplifier : 1);
         entity.level()
            .playSound(
               null,
               entity.blockPosition(),
               newDuration <= 0 ? JNESoundEvents.ANTIDOTE_EXPIRED.get() : JNESoundEvents.ANTIDOTE_NEGATE.get(),
               SoundSource.PLAYERS,
               1.0F,
               1.0F
            );
         entity.removeEffect(immuneTo);
         entity.removeEffect(self);
         entity.addEffect(new MobEffectInstance(self, newDuration, amplifier));
      }

      return true;
   }

   public int getColor() {
      Holder<MobEffect> immuneTo = this.getImmunityHolder();
      return immuneTo != null ? ((MobEffect)immuneTo.value()).getColor() : 8421504;
   }

   @NotNull
   public ParticleOptions createParticleOptions(MobEffectInstance effect) {
      int alpha = effect.isAmbient() ? Mth.floor(38.25F) : 255;
      return ColorParticleOption.create(JNEParticleTypes.IMMUNITY_EFFECT.get(), ARGB32.color(alpha, this.getColor()));
   }

   @Nullable
   protected Holder<MobEffect> getImmunityHolder() {
      if (this.cachedImmunityHolder == null) {
         MobEffect effect = LookupRegistryHelper.getMobEffect(this.immunityOf);
         if (effect != null) {
            this.cachedImmunityHolder = this.getAsHolder(effect);
         }
      }

      return this.cachedImmunityHolder;
   }

   @Nullable
   protected Holder<MobEffect> getSelfHolder() {
      if (this.cachedSelfHolder == null) {
         this.cachedSelfHolder = this.getAsHolder(this);
      }

      return this.cachedSelfHolder;
   }

   @Nullable
   protected Holder<MobEffect> getAsHolder(MobEffect effect) {
      if (effect == null) {
         return null;
      }

      Optional<ResourceKey<MobEffect>> key = BuiltInRegistries.MOB_EFFECT.getResourceKey(effect);
      return key.<Holder<MobEffect>>map(BuiltInRegistries.MOB_EFFECT::getHolderOrThrow).orElse(null);
   }
}
