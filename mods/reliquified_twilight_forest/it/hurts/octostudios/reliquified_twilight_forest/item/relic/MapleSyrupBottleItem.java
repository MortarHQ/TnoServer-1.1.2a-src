package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.DataComponentRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
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
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.living.LivingHealEvent;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.neoforged.neoforge.event.entity.living.LivingEntityUseItemEvent.Start;
import top.theillusivec4.curios.api.SlotContext;
import twilightforest.init.TFItems;

@EventBusSubscriber
public class MapleSyrupBottleItem extends RelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("sugar_rush")
                     .stat(
                        StatData.builder("chance")
                           .initialValue(0.1, 0.2)
                           .upgradeModifier(UpgradeOperation.ADD, 0.055)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .stat(
                        StatData.builder("regen_multiplier")
                           .initialValue(0.3, 0.5)
                           .upgradeModifier(UpgradeOperation.ADD, 0.15)
                           .formatValue(MathButCool::percentage)
                           .build()
                     )
                     .stat(
                        StatData.builder("regen_time")
                           .initialValue(140.0, 200.0)
                           .upgradeModifier(UpgradeOperation.ADD, 20.0)
                           .formatValue(MathButCool::ticksToSecondsAndRoundSingleDigit)
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder().source(LevelingSourceData.abilityBuilder("sugar_rush").gem(GemShape.SQUARE, GemColor.ORANGE).build()).build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.LABYRINTH}).entry(new LootEntry[]{LootEntries.STRONGHOLD}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      if (!slotContext.entity().level().isClientSide) {
         int regenerationTicks = (Integer)stack.getOrDefault(DataComponentRegistry.REGENERATION_TICKS, 0);
         if (regenerationTicks > 0) {
            regenerationTicks--;
         }

         stack.set(DataComponentRegistry.REGENERATION_TICKS, regenerationTicks);
      }
   }

   @SubscribeEvent
   public static void eat(Start e) {
      ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.MAPLE_SYRUP_BOTTLE.get());
      if (!e.getEntity().level().isClientSide && stack.getItem() instanceof MapleSyrupBottleItem relic && isAcceptable(e.getItem())) {
         if (e.getEntity().getRandom().nextDouble() > relic.getStatValue(stack, "sugar_rush", "chance")) {
            e.getItem().remove(DataComponentRegistry.DONT_EAT);
         } else {
            e.getItem().set(DataComponentRegistry.DONT_EAT, true);
         }
      }
   }

   @SubscribeEvent
   public static void eat(Finish e) {
      ItemStack original = e.getItem();
      ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.MAPLE_SYRUP_BOTTLE.get());
      if (!stack.isEmpty() && stack.getItem() instanceof MapleSyrupBottleItem relic) {
         if (!e.getEntity().level().isClientSide && isAcceptable(e.getItem())) {
            int regenerationTicks = (Integer)stack.getOrDefault(DataComponentRegistry.REGENERATION_TICKS, 0);
            int toAdd = (int)Math.round(relic.getStatValue(stack, "sugar_rush", "regen_time"));
            stack.set(DataComponentRegistry.REGENERATION_TICKS, regenerationTicks + toAdd);
            relic.spreadRelicExperience(e.getEntity(), stack, 1);
         }

         if (original.has(DataComponentRegistry.DONT_EAT)) {
            e.setResultStack(original);
         }
      }
   }

   @SubscribeEvent
   public static void onHeal(LivingHealEvent e) {
      ItemStack stack = EntityUtils.findEquippedCurio(e.getEntity(), (Item)ItemRegistry.MAPLE_SYRUP_BOTTLE.get());
      if (!e.getEntity().level().isClientSide
         && stack.getItem() instanceof MapleSyrupBottleItem relic
         && (Integer)stack.getOrDefault(DataComponentRegistry.REGENERATION_TICKS, 0) > 0) {
         e.setAmount(e.getAmount() * (float)(1.0 + relic.getStatValue(stack, "sugar_rush", "regen_multiplier")));
      }
   }

   public static boolean isAcceptable(ItemStack stack) {
      return stack.getItem() == TFItems.MAZE_WAFER.asItem();
   }
}
