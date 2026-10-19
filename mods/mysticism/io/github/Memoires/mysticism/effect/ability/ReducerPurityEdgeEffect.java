package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.ability.skill.unique.ReducerSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.SkillUtils;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;

public class ReducerPurityEdgeEffect extends TensuraMobEffect implements DamageAction {
   private static final ResourceLocation PURITY_EDGE = ResourceLocation.fromNamespaceAndPath("mysticism", "purity_edge");

   public ReducerPurityEdgeEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 255, 255).getRGB());
      this.addAttributeModifier(TensuraAttributes.RESISTANCE_DEGRADATION, PURITY_EDGE, 1.0, Operation.ADD_VALUE);
   }

   public boolean onPlayerAttack(Player player, Entity entity) {
      MobEffectInstance holyCoat = player.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_PURITY_EDGE));
      if (holyCoat != null) {
         DamageSource source = TensuraDamageHelper.getAbilityDamageSource(TensuraDamageTypes.HOLY_DAMAGE, player, holyCoat.tensura$getSourceAbility());
         source = source.tensura$setElement(Element.HOLY);
         if (entity instanceof LivingEntity le) {
            boolean master = SkillUtils.isSkillMastered(player, (ManasSkill)UniqueMysticismSkills.REDUCER.get());
            le.hurt(
               source, le.getMaxHealth() * (master ? ReducerSkill.CONFIG.purityEdgeDamagePercentageMastered : ReducerSkill.CONFIG.purityEdgeDamagePercentage)
            );
            le.invulnerableTime = 0;
         }
      }

      player.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_PURITY_EDGE));
      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(player).getSkill((ManasSkill)UniqueMysticismSkills.REDUCER.get());
      if (opt.isPresent()) {
         int cooldown = opt.get().isMastered(player) ? ReducerSkill.CONFIG.purityEdgeCooldownMastered : ReducerSkill.CONFIG.purityEdgeCooldown;
         opt.get().setCoolDown(cooldown, 1);
      }

      return true;
   }

   public void onEffectRemoved(LivingEntity entity, MobEffectInstance instance) {
      if (instance.getDuration() <= 0) {
         if (!(entity instanceof Player player && player.isCreative())) {
            DamageSource source = TensuraDamageHelper.getAbilityDamageSource(TensuraDamageTypes.HOLY_DAMAGE, entity, instance.tensura$getSourceAbility());
            source = source.tensura$setElement(Element.HOLY);
            boolean mastered = SkillUtils.isSkillMastered(entity, (ManasSkill)UniqueMysticismSkills.REDUCER.get());
            entity.hurt(
               source,
               mastered ? entity.getMaxHealth() * ReducerSkill.CONFIG.purityEdgeRecoilMastered : entity.getMaxHealth() * ReducerSkill.CONFIG.purityEdgeRecoil
            );
            Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(entity).getSkill((ManasSkill)UniqueMysticismSkills.REDUCER.get());
            if (opt.isPresent()) {
               int cooldown = opt.get().isMastered(entity) ? ReducerSkill.CONFIG.purityEdgeCooldownMastered : ReducerSkill.CONFIG.purityEdgeCooldown;
               opt.get().setCoolDown(cooldown, 1);
            }
         }
      }
   }
}
