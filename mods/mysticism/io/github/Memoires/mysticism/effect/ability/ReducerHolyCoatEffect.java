package io.github.Memoires.mysticism.effect.ability;

import io.github.Memoires.mysticism.ability.skill.unique.ReducerSkill;
import io.github.Memoires.mysticism.registry.effects.MysticismMobEffects;
import io.github.Memoires.mysticism.registry.skill.UniqueMysticismSkills;
import io.github.manasmods.manascore.attribute.api.ManasCoreAttributeUtils;
import io.github.manasmods.manascore.skill.api.ManasSkill;
import io.github.manasmods.manascore.skill.api.ManasSkillInstance;
import io.github.manasmods.manascore.skill.api.SkillAPI;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.damage.TensuraDamageHelper;
import io.github.manasmods.tensura.damage.TensuraDamageTypes;
import io.github.manasmods.tensura.effect.template.DamageAction;
import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.particle.TensuraParticleHelper;
import io.github.manasmods.tensura.particle.TensuraParticleUtils;
import io.github.manasmods.tensura.registry.attribute.TensuraAttributes;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.registry.sound.TensuraSoundEvents;
import io.github.manasmods.tensura.util.EnergyHelper;
import java.awt.Color;
import java.util.Optional;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class ReducerHolyCoatEffect extends TensuraMobEffect implements DamageAction {
   private static final ResourceLocation HOLY_COAT = ResourceLocation.fromNamespaceAndPath("mysticism", "holy_coat");

   public ReducerHolyCoatEffect() {
      super(MobEffectCategory.BENEFICIAL, new Color(255, 255, 255).getRGB());
      this.addAttributeModifier(TensuraAttributes.PHYSICAL_RESIST_DEGRADATION, HOLY_COAT, 1.0, Operation.ADD_VALUE);
   }

   public boolean onPlayerAttack(Player attacker, Entity entity) {
      MobEffectInstance holyCoat = attacker.getEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_HOLY_COAT));
      if (holyCoat != null) {
         DamageSource source = TensuraDamageHelper.getAbilityDamageSource(TensuraDamageTypes.HOLY_DAMAGE, attacker, holyCoat.tensura$getSourceAbility());
         source = source.tensura$setElement(Element.HOLY);
         float amount = ManasCoreAttributeUtils.getAttackDamage(attacker);
         if (attacker.level() instanceof ServerLevel serverLevel) {
            amount = EnchantmentHelper.modifyDamage(serverLevel, attacker.getOffhandItem(), entity, source, amount);
         }

         entity.hurt(source, amount * ReducerSkill.CONFIG.emancipationMultiplier);
         entity.invulnerableTime = 0;
      }

      return true;
   }

   public boolean shouldApplyEffectTickThisTick(int pDuration, int pAmplifier) {
      return pDuration % 20 == 0;
   }

   public boolean applyEffectTick(LivingEntity livingEntity, int amplifier) {
      if (livingEntity.level().isClientSide) {
         return true;
      }

      Optional<ManasSkillInstance> opt = SkillAPI.getSkillsFrom(livingEntity).getSkill((ManasSkill)UniqueMysticismSkills.REDUCER.get());
      if (opt.isEmpty()) {
         return true;
      }

      if (EnergyHelper.isOutOfEnergy(livingEntity, 0.0, ReducerSkill.CONFIG.emancipationMaintainCost)) {
         if (livingEntity instanceof Player player) {
            player.displayClientMessage(
               Component.translatable("tensura.skill.lack_magicule.toggled_off", new Object[]{opt.get().getSkill().getName()})
                  .setStyle(Style.EMPTY.withColor(ChatFormatting.RED)),
               false
            );
            player.playNotifySound((SoundEvent)TensuraSoundEvents.BUFF_DEACTIVATE.get(), SoundSource.PLAYERS, 1.0F, 1.0F);
         }

         TensuraParticleHelper.spawnServerParticles(
            livingEntity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, livingEntity.getBbWidth() * 3.0F),
            livingEntity.getX(),
            livingEntity.getY() + livingEntity.getBbHeight() * 0.33,
            livingEntity.getZ()
         );
         TensuraParticleHelper.spawnServerParticles(
            livingEntity.level(),
            TensuraParticleUtils.getColorlessReversedWave(0.9F, livingEntity.getBbWidth() * 3.0F),
            livingEntity.getX(),
            livingEntity.getY() + livingEntity.getBbHeight() * 0.66,
            livingEntity.getZ()
         );
         livingEntity.removeEffect(TensuraMobEffects.getReference(MysticismMobEffects.REDUCER_HOLY_COAT));
      }

      return true;
   }
}
