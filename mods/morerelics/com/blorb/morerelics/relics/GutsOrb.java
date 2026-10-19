package com.blorb.morerelics.relics;

import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.dataComponents.IntegerCounter;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import top.theillusivec4.curios.api.SlotContext;

public class GutsOrb extends MoreRelicBase {
   public final int experienceTimer = 10;

   public GutsOrb(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("guts")
                     .stat(
                        StatData.builder("damage_perchantage")
                           .initialValue(0.2, 0.4)
                           .upgradeModifier(UpgradeOperation.ADD, 0.05)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredPoints(1)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(5)
               .initialCost(100)
               .step(20)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("guts").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.BASTION, LootEntries.DESERT, LootEntries.NETHER_LIKE}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack itemStack) {
      LivingEntity livingEntity = slotContext.entity();
      if (!livingEntity.level().isClientSide) {
         boolean hasHarmfulEffect = false;

         for (MobEffectInstance effect : livingEntity.getActiveEffects()) {
            if (((MobEffect)effect.getEffect().value()).getCategory() == MobEffectCategory.HARMFUL) {
               hasHarmfulEffect = true;
               break;
            }
         }

         float multiplier;
         if (!livingEntity.isOnFire() && !hasHarmfulEffect) {
            multiplier = 0.0F;
         } else {
            multiplier = (float)this.getStatValue(itemStack, "guts", "damage_perchantage");
            IntegerCounter counter = DataComponentHandler.getIntegerCounter(itemStack);
            int experienceProgress = counter.current();
            if (10 > experienceProgress) {
               experienceProgress++;
            } else {
               this.spreadRelicExperience(livingEntity, itemStack, 1);
               experienceProgress = 0;
            }

            itemStack.set(DataComponentHandler.INT_COUNTER, counter.getUpdatedCounter(experienceProgress));
         }

         if (multiplier != 0.0F) {
            EntityUtils.applyAttribute(livingEntity, itemStack, Attributes.ATTACK_DAMAGE, multiplier, Operation.ADD_MULTIPLIED_TOTAL);
         } else {
            EntityUtils.removeAttribute(livingEntity, itemStack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
         }
      }
   }

   public void onUnequip(SlotContext slotContext, ItemStack newStack, ItemStack stack) {
      LivingEntity livingEntity = slotContext.entity();
      if (stack.getItem() != newStack.getItem()) {
         EntityUtils.removeAttribute(livingEntity, stack, Attributes.ATTACK_DAMAGE, Operation.ADD_MULTIPLIED_TOTAL);
      }
   }
}
