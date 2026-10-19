package io.github.Memoires.mysticism.ability.skill.unique;

import io.github.Memoires.mysticism.config.ability.skill.UniqueMysticismSkillConfig;
import io.github.manasmods.manascore.config.ConfigRegistry;
import io.github.manasmods.manascore.network.api.util.Changeable;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.tensura.ability.skill.Skill;
import io.github.manasmods.tensura.ability.skill.Skill.SkillType;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import org.jetbrains.annotations.Nullable;

public class SchrodingerSkill extends Skill {
   protected static final UniqueMysticismSkillConfig.Schrodinger CONFIG = ((UniqueMysticismSkillConfig)ConfigRegistry.getConfig(
         UniqueMysticismSkillConfig.class
      ))
      .Schrodinger;
   private static final ResourceLocation SCHRODINGER = ResourceLocation.fromNamespaceAndPath("mysticism", "schrodinger");

   public SchrodingerSkill() {
      super(SkillType.UNIQUE);
   }

   @Nullable
   public ResourceLocation getSkillIcon() {
      return ResourceLocation.fromNamespaceAndPath("mysticism", "textures/skill/unique/schrodinger.png");
   }

   public void onLearnSkill(ManasSkillInstance instance, LivingEntity entity) {
      instance.getOrCreateTag().putInt("Lives", 9);
      instance.markDirty();
   }

   public boolean canBeToggled(ManasSkillInstance instance, LivingEntity entity) {
      return instance.getMastery() >= 0.0;
   }

   public void onToggleOn(ManasSkillInstance instance, LivingEntity entity) {
      AttributeInstance safeFallDistance = entity.getAttribute(Attributes.SAFE_FALL_DISTANCE);
      if (safeFallDistance != null) {
         safeFallDistance.addOrReplacePermanentModifier(new AttributeModifier(SCHRODINGER, 32.0, Operation.ADD_VALUE));
      }
   }

   public boolean onDeath(ManasSkillInstance instance, LivingEntity owner, DamageSource source) {
      MinecraftServer server = owner.getServer();
      if (server != null && server.isHardcore()) {
         instance.getOrCreateTag().putInt("Lives", instance.getOrCreateTag().getInt("Lives") - 1);
      }

      return super.onDeath(instance, owner, source);
   }

   public boolean onTouchEntity(ManasSkillInstance instance, LivingEntity owner, LivingEntity target, DamageSource source, Changeable<Float> amount) {
      if (!this.isInSlot(owner)) {
         return true;
      }

      boolean mastered = instance.isMastered(owner);
      Holder<MobEffect> effect = mastered ? TensuraMobEffects.getReference(TensuraMobEffects.FATAL_POISON) : MobEffects.POISON;
      TensuraMobEffect.addEffect(target, new MobEffectInstance(effect, CONFIG.poisonEffectDuration, 1, false, false, false), owner, this);
      return true;
   }
}
