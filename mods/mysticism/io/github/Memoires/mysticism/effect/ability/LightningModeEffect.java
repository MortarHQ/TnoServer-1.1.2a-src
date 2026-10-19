package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.tensura.effect.template.ITransformation;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.particle.TensuraParticleTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.ep.IExistence;
import java.awt.Color;
import java.util.Map.Entry;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffect.AttributeTemplate;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;

public class LightningModeEffect extends TensuraMobEffect implements ITransformation {
   public static final ResourceLocation LIGHTNING_MODE = ResourceLocation.fromNamespaceAndPath("mysticism", "lightning_mode");

   public LightningModeEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 181, 0, 255).getRGB());
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, LIGHTNING_MODE, 12.0, Operation.ADD_VALUE);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, LIGHTNING_MODE, 0.2F, Operation.ADD_VALUE);
   }

   public void addAttributeModifiers(AttributeMap attributeMap, int i) {
      for (Entry<Holder<Attribute>, AttributeTemplate> entry : this.attributeModifiers.entrySet()) {
         AttributeInstance attributeInstance = attributeMap.getInstance(entry.getKey());
         if (attributeInstance != null) {
            attributeInstance.removeModifier(entry.getValue().id());
            int level = entry.getValue().id().equals(ResourceLocation.fromNamespaceAndPath("mysticism", "lightning_mode")) ? 0 : i;
            attributeInstance.addOrReplacePermanentModifier(entry.getValue().create(level));
         }
      }
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      if (this.failedToActivate(entity, TensuraMobEffects.getReference(MysticismMobEffects.LIGHTNING_MODE))) {
         return false;
      }

      TensuraParticleHelper.addParticlesAroundSelf(entity, (ParticleOptions)TensuraParticleTypes.YELLOW_LIGHTNING_SPARK.get());
      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int amplifier) {
      return pDuration % 10 == 0;
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      IExistence existence = TensuraStorages.getExistenceFrom(entity);
      existence.setMagicule(existence.getMagicule() / 2.0);
      existence.setAura(existence.getAura() / 2.0);
      this.applyDebuff(entity);
   }
}
