package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.EffectRegistry;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootEntry;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import top.theillusivec4.curios.api.SlotContext;

public class InvisibilityCloakItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(AbilityData.builder("cosmetic_armor").maxLevel(0).build())
               .ability(
                  AbilityData.builder("invisibility")
                     .stat(
                        StatData.builder("duration")
                           .initialValue(160.0, 120.0)
                           .upgradeModifier(UpgradeOperation.ADD, -10.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .thresholdValue(1.0, 9999.0)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("invisibility").gem(GemShape.SQUARE, GemColor.CYAN).build()).build()
               )
               .build()
         )
         .style(StyleData.builder().beams((player, stack) -> {
            float ticks = player.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
            return BeamsData.builder().startColor(new Color(0.5F, 0.5F, 0.5F, Mth.sin(ticks / 10.0F) / 2.0F + 0.5F).getRGB()).endColor(4473924).build();
         }).build())
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.HEDGE}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (!slotContext.entity().level().isClientSide) {
         LivingEntity entity = slotContext.entity();
         int idleTicks = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
         int maxIdleTicks = this.getMaxIdleTicks(slotContext, stack);
         double lengthSqr = slotContext.entity().getKnownMovement().lengthSqr();
         double movementThreshold = 0.005;
         if (!(lengthSqr < movementThreshold) && !entity.isCrouching()) {
            idleTicks = 0;
            entity.removeEffect(EffectRegistry.VANISHING);
         } else if (idleTicks < maxIdleTicks) {
            idleTicks++;
         } else {
            if (!entity.hasEffect(EffectRegistry.VANISHING)) {
               this.spreadRelicExperience(entity, stack, 1);
            }

            entity.addEffect(new MobEffectInstance(EffectRegistry.VANISHING, 24, 0, true, false));
         }

         stack.set(DataComponentRegistry.TIME, idleTicks);
      }
   }

   public int getMaxIdleTicks(SlotContext slotContext, ItemStack stack) {
      return (int)Math.round(this.getStatValue(stack, "invisibility", "duration"));
   }
}
