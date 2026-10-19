package com.tno_tensura_extras.item;

import io.github.manasmods.tensura.effect.template.TensuraMobEffect;
import io.github.manasmods.tensura.registry.effect.TensuraMobEffects;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import io.github.manasmods.tensura.util.EnergyHelper.GainType;
import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.Level;

public class HighQualityHoneyItem extends Item {
   private static final double ENERGY_RESTORE_MULTIPLIER = 0.05;
   private static final int REGENERATION_DURATION = 120;
   private static final int REGENERATION_AMPLIFIER = 2;

   public HighQualityHoneyItem(Properties properties) {
      super(properties);
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
      ItemStack result = super.finishUsingItem(stack, level, livingEntity);
      if (!level.isClientSide()) {
         TensuraMobEffect.removePredicateEffect(livingEntity, HighQualityHoneyItem::isNegativeEffect);
         TensuraStorages.resetEffect(livingEntity);
         EnergyHelper.gainMagicule(livingEntity, EnergyHelper.getMaxMagicule(livingEntity) * 0.05, GainType.NORMAL);
         EnergyHelper.gainAura(livingEntity, EnergyHelper.getMaxAura(livingEntity) * 0.05, GainType.NORMAL);
         livingEntity.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 120, 2));
      }

      return result;
   }

   private static boolean isNegativeEffect(Holder<MobEffect> effect) {
      return ((MobEffect)effect.value()).getCategory() == MobEffectCategory.HARMFUL
         || effect.is(MobEffects.BAD_OMEN)
         || effect.is(MobEffects.TRIAL_OMEN)
         || effect.is(MobEffects.RAID_OMEN)
         || effect.is(io.github.manasmods.tensura.data.TensuraTags.MobEffects.SKILL_DEBUFF)
         || effect.is(TensuraMobEffects.MAGICULE_POISON)
         || effect.is(TensuraMobEffects.HOLY_DAMAGE)
         || effect.is(TensuraMobEffects.RAMPAGE)
         || effect.is(TensuraMobEffects.LUST_EMBRACEMENT);
   }

   public UseAnim getUseAnimation(ItemStack stack) {
      return UseAnim.DRINK;
   }
}
