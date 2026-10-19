package com.github.dominickwd04.traddon.effect;

import com.github.dominickwd04.traddon.ability.skill.unique.DevastatorSkill;
import com.github.dominickwd04.traddon.config.ability.skill.TrAddonSkillConfig;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.race.RaceUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import java.awt.Color;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class DespairEffect extends TensuraMobEffect {
   protected static final ResourceLocation DESPAIR = ResourceLocation.fromNamespaceAndPath("traddon", "despair");

   public DespairEffect() {
      super(MobEffectCategory.HARMFUL, new Color(92, 31, 184).getRGB());
      TrAddonSkillConfig.Devastator CONFIG = DevastatorSkill.CONFIG;
      this.addAttributeModifier(Attributes.ATTACK_DAMAGE, DESPAIR, CONFIG.attack, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.ATTACK_SPEED, DESPAIR, CONFIG.attackSpeed, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(Attributes.MOVEMENT_SPEED, DESPAIR, CONFIG.movementSpeed, Operation.ADD_MULTIPLIED_TOTAL);
      this.addAttributeModifier(TensuraAttributes.CHANT_SPEED, DESPAIR, CONFIG.chantSpeed, Operation.ADD_MULTIPLIED_TOTAL);
   }

   public boolean applyEffectTick(LivingEntity pLivingEntity, int pAmplifier) {
      TensuraDamageHelper.directSpiritualHurt(
         pLivingEntity,
         null,
         TensuraDamageTypes.getDamageSource(pLivingEntity.level(), TensuraDamageTypes.FEAR),
         (float)Math.max(5.0, pLivingEntity.getAttributeValue(TensuraAttributes.MAX_SPIRITUAL_HEALTH) * (0.01F * (pAmplifier + 1)))
      );
      if (pLivingEntity instanceof Player player) {
         if (!player.getAbilities().mayfly) {
            return true;
         }

         if (RaceUtils.canStillFly(player, true, true, true)) {
            return true;
         }

         player.getAbilities().mayfly = false;
         player.getAbilities().flying = false;
         player.onUpdateAbilities();
         return true;
      } else {
         return true;
      }
   }

   public boolean shouldApplyEffectTickThisTick(int duration, int amplifier) {
      return duration % 40 == 0;
   }
}
