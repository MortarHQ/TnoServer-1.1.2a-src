package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
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
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import top.theillusivec4.curios.api.SlotContext;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFParticleType;

public class FireflyQueenItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("glowkeeper")
                     .stat(
                        StatData.builder("cooldown")
                           .initialValue(200.0, 140.0)
                           .upgradeModifier(UpgradeOperation.ADD, -20.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("max_charges").initialValue(1.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
                     .maxLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("glowkeeper").gem(GemShape.SQUARE, GemColor.YELLOW).build()).build()
               )
               .maxLevel(5)
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.TWILIGHT}).build())
         .style(StyleData.builder().beams(BeamsData.builder().startColor(-5775360).endColor(85803).build()).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      Level level = entity.level();
      if (stack.getItem() instanceof IRelicItem relic) {
         if (level.isClientSide) {
            if (slotContext.visible() && entity.tickCount % 10 == 0 && entity.getRandom().nextFloat() < 0.6F) {
               level.addParticle(
                  (ParticleOptions)TFParticleType.FIREFLY.get(), entity.getRandomX(0.5), entity.getRandomY(), entity.getRandomZ(0.5), 0.0, 0.0, 0.0
               );
            }
         } else {
            int maxTime = (int)Math.round(relic.getStatValue(stack, "glowkeeper", "cooldown"));
            int maxCharges = (int)Math.round(relic.getStatValue(stack, "glowkeeper", "max_charges"));
            int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, maxTime);
            int charge = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
            BlockPos pos = entity.blockPosition();
            BlockState state = ((Block)TFBlocks.FIREFLY.get()).defaultBlockState();
            BlockState posState = level.getBlockState(pos);
            if (charge > 0
               && entity.onGround()
               && !entity.isInLiquid()
               && (!level.isDay() || level.getBrightness(LightLayer.SKY, pos) == 0)
               && level.getBrightness(LightLayer.BLOCK, pos) == 0
               && state.canSurvive(level, pos)
               && posState.canBeReplaced()) {
               level.setBlock(pos, state, 3);
               relic.spreadRelicExperience(entity, stack, 1);
               charge--;
            }

            if (time <= 0) {
               if (charge < maxCharges) {
                  charge++;
                  time = maxTime;
               }
            } else {
               time--;
            }

            stack.set(DataComponentRegistry.TIME, time);
            stack.set(DataComponentRegistry.CHARGE, charge);
         }
      }
   }

   public String getConfigRoute() {
      return "reliquified_twilight_forest";
   }
}
