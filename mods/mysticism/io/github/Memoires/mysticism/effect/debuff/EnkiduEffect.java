package io.github.Memoires.mysticism.effect.debuff;

import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.entity.template.subclass.IFlying;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class EnkiduEffect extends TensuraMobEffect {
   protected static final ResourceLocation ENKIDU = ResourceLocation.fromNamespaceAndPath("mysticism", "enkidu");

   public EnkiduEffect() {
      super(MobEffectCategory.NEUTRAL, new Color(112, 5, 5).getRGB());
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.FLYING_SPEED, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.JUMP_STRENGTH, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.BLOCK_INTERACTION_RANGE, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.JUMP_STRENGTH, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.FOLLOW_RANGE, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.SWIM_SPEED_MULTIPLIER, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.LAVA_SPEED_MULTIPLIER, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(ManasCoreAttributes.GLIDE_SPEED_MULTIPLIER, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.AUTO_MELEE_DODGE_CHANCE, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.AUTO_PROJECTILE_DODGE_CHANCE, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.AURA_REGENERATION_MULTIPLIER, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.MAGICULE_REGENERATION_MULTIPLIER, ENKIDU, -1.0, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public boolean applyEffectTick(LivingEntity entity, int pAmplifier) {
      Level var4 = entity.level();
      if (var4 instanceof ServerLevel) {
         MobEffectInstance instance = entity.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.ENKIDU));
         if (instance == null) {
            return true;
         }

         if (entity instanceof Player player && player.getAbilities().flying) {
            player.getAbilities().flying = false;
            player.onUpdateAbilities();
            return true;
         }

         if (entity instanceof IFlying flying && flying.isFlying()) {
            flying.setFlying(false);
         }
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 10 == 0;
   }
}
