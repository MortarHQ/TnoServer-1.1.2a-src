package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicAttributeModifier.Modifier;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourceData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingSourcesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemColor;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.GemShape;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.style.BeamsData;
import it.hurts.sskirillss.relics.items.relics.base.data.style.StyleData;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;
import top.theillusivec4.curios.api.SlotContext;

public class Parasite116Item extends Parasite115Item {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("infectious_bloom")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.15)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.4)
                           .formatValue(MathButCool::percentageAndRoundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("max_attacks").initialValue(1.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 0.5).formatValue(Math::round).build())
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.75, 1.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.54)
                           .formatValue(MathButCool::roundSingleDigit)
                           .build()
                     )
                     .stat(StatData.builder("drops").initialValue(2.0, 5.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
                     .maxLevel(10)
                     .build()
               )
               .ability(
                  AbilityData.builder("rage_consumption")
                     .stat(
                        StatData.builder("amount_restored")
                           .initialValue(50.0, 100.0)
                           .upgradeModifier(UpgradeOperation.ADD, 20.0)
                           .formatValue(value -> MathUtils.round(value / 2.0, 1))
                           .build()
                     )
                     .stat(
                        StatData.builder("multiplier")
                           .initialValue(0.4, 0.75)
                           .upgradeModifier(UpgradeOperation.ADD, 0.25)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .maxLevel(5)
                     .requiredLevel(5)
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("infectious_bloom").gem(GemShape.SQUARE, GemColor.YELLOW).build())
                     .source(LevelingSourceData.abilityBuilder("rage_consumption").gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .maxLevel(10)
               .build()
         )
         .style(StyleData.builder().beams((player, stack) -> {
            float ticks = player.tickCount + Minecraft.getInstance().getTimer().getGameTimeDeltaPartialTick(true);
            float lerp = (float)(Math.sin(ticks / 5.0F) / 2.0 + 0.5);
            float r = 1.0F;
            float g = Mth.lerp(lerp, 127.0F, 233.0F) / 255.0F;
            float b = Mth.lerp(lerp, 39.0F, 0.0F) / 255.0F;
            return BeamsData.builder().startColor(new Color(r, g, b, 1.0F).getRGB()).endColor(new Color(r, g, b, 0.0F).getRGB()).build();
         }).build())
         .build();
   }

   @Nullable
   public RelicAttributeModifier getRelicAttributeModifiers(ItemStack stack) {
      float percentage = ((Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0)).intValue() / 200.0F;
      float multiplier = (float)this.getStatValue(stack, "rage_consumption", "multiplier") * percentage;
      return RelicAttributeModifier.builder()
         .attribute(new Modifier(Attributes.ATTACK_DAMAGE, multiplier, Operation.ADD_MULTIPLIED_TOTAL))
         .attribute(new Modifier(Attributes.ATTACK_SPEED, multiplier, Operation.ADD_MULTIPLIED_TOTAL))
         .attribute(new Modifier(Attributes.MOVEMENT_SPEED, multiplier, Operation.ADD_MULTIPLIED_TOTAL))
         .attribute(new Modifier(Attributes.STEP_HEIGHT, percentage * 1.5F, Operation.ADD_VALUE))
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      int time = (Integer)stack.getOrDefault(DataComponentRegistry.TIME, 0);
      if (!slotContext.entity().level().isClientSide && time > 0) {
         stack.set(DataComponentRegistry.TIME, --time);
      }
   }

   @Override
   public void evolve(String identifier, int index, ItemStack stack, LivingEntity entity) {
      super.evolve(identifier, index, stack, entity);
   }

   @Override
   public boolean isEvolved() {
      return true;
   }
}
