package it.hurts.octostudios.reliquified_twilight_forest.item.relic;

import it.hurts.octostudios.reliquified_twilight_forest.data.loot.LootEntries;
import it.hurts.octostudios.reliquified_twilight_forest.init.ItemRegistry;
import it.hurts.octostudios.reliquified_twilight_forest.item.BrokenCharmItem;
import it.hurts.octostudios.reliquified_twilight_forest.item.BundleLikeRelicItem;
import it.hurts.octostudios.reliquified_twilight_forest.util.MathButCool;
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
import java.util.List;
import java.util.function.Predicate;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import top.theillusivec4.curios.api.SlotContext;

public class CharmBackpackItem extends BundleLikeRelicItem {
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("charm_storage")
                     .stat(StatData.builder("max_slots").initialValue(1.0, 3.0).upgradeModifier(UpgradeOperation.ADD, 1.0).formatValue(Math::round).build())
                     .stat(
                        StatData.builder("repair_time")
                           .initialValue(30.0, 20.0)
                           .upgradeModifier(UpgradeOperation.ADD, -1.0)
                           .formatValue(value -> MathButCool.roundSingleDigit(300.0 * value / 1200.0))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(
            LevelingData.builder()
               .sources(
                  LevelingSourcesData.builder()
                     .source(LevelingSourceData.abilityBuilder("charm_storage").gem(GemShape.SQUARE, GemColor.ORANGE).build())
                     .build()
               )
               .build()
         )
         .loot(LootData.builder().entry(new LootEntry[]{LootEntries.LABYRINTH}).build())
         .build();
   }

   public void curioTick(SlotContext slotContext, ItemStack stack) {
      LivingEntity entity = slotContext.entity();
      if (entity instanceof Player player) {
         List charms = this.getContents(stack).stream().map(itemStack -> {
            if (itemStack.getItem() instanceof BrokenCharmItem charm) {
               charm.backpackTick(entity, stack, itemStack);
               return itemStack.getDamageValue() <= 0 ? charm.original.getDefaultInstance() : itemStack;
            } else {
               return (ItemStack)itemStack;
            }
         }).toList();
         this.setContents(player, stack, charms);
      }
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slotId, boolean isSelected) {
      super.inventoryTick(stack, level, entity, slotId, isSelected);
      if (stack.getItem() instanceof CharmBackpackItem relic && entity instanceof Player player && !player.level().isClientSide) {
         relic.dropExcessive(player, stack);
      }
   }

   @Override
   public int getMaxSlots(ItemStack stack) {
      return stack.getItem() instanceof CharmBackpackItem relic ? (int)Math.round(relic.getStatValue(stack, "charm_storage", "max_slots")) : 0;
   }

   @Override
   public Predicate<ItemStack> getPredicate() {
      return stack -> stack.getItem() instanceof BrokenCharmItem || ItemRegistry.CHARMS.apply(stack.getItem()) != Items.AIR;
   }
}
