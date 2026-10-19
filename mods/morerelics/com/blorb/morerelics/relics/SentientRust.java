package com.blorb.morerelics.relics;

import com.blorb.morerelics.MoreRelics;
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
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingIncomingDamageEvent;

@EventBusSubscriber
public class SentientRust extends MoreRelicBase {
   public SentientRust(Properties properties) {
   }

   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("corrosive")
                     .stat(
                        StatData.builder("seconds")
                           .initialValue(20.0, 30.0)
                           .upgradeModifier(UpgradeOperation.ADD, 5.0)
                           .formatValue(value -> (float)Math.round(value * 100.0) / 100.0F)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(0)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .maxLevel(5)
               .initialCost(150)
               .step(30)
               .sources(LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("corrosive").build()).build())
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.MINESHAFT, LootEntries.MOUNTAIN, LootEntries.SWAMP}).build())
         .build();
   }

   @SubscribeEvent
   public static void onPlayerAttack(LivingIncomingDamageEvent event) {
      if (event.getSource().getEntity() instanceof LivingEntity livingEntity && !livingEntity.level().isClientSide) {
         if (!(event.getEntity().invulnerableTime > 10.0F)
            || event.getSource().is(DamageTypeTags.BYPASSES_COOLDOWN)
            || !(event.getAmount() <= event.getEntity().lastHurt)) {
            double seconds = EntityUtils.findEquippedCurios(livingEntity, (Item)MoreRelics.SENTIENT_RUST.get()).stream().mapToDouble(stack -> {
               SentientRust item = (SentientRust)stack.getItem();
               if (!item.isAbilityUnlocked(stack, "corrosive")) {
                  return 0.0;
               }

               double time = item.getStatValue(stack, "corrosive", "seconds");
               if (time > 0.0) {
                  item.spreadRelicExperience(livingEntity, stack, 1);
               }

               return time;
            }).sum();
            if (seconds > 0.0) {
               LivingEntity victim = event.getEntity();
               MobEffectInstance current = event.getEntity().getEffect(MoreRelics.CORROSION.getDelegate());
               if (current == null) {
                  victim.addEffect(new MobEffectInstance(MoreRelics.CORROSION.getDelegate(), (int)Math.round(seconds * 20.0), 0));
               } else {
                  int currentAmplifier = current.getAmplifier();
                  if (currentAmplifier < 9) {
                     victim.addEffect(new MobEffectInstance(MoreRelics.CORROSION.getDelegate(), (int)Math.round(seconds * 20.0), currentAmplifier + 1));
                  } else {
                     victim.addEffect(new MobEffectInstance(MoreRelics.CORROSION.getDelegate(), (int)Math.round(seconds * 20.0), 9));
                  }
               }
            }
         }
      }
   }
}
