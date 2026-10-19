package it.hurts.sskirillss.relics.items.relics.hands;

import it.hurts.sskirillss.relics.init.DataComponentRegistry;
import it.hurts.sskirillss.relics.init.ItemRegistry;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.RelicItem;
import it.hurts.sskirillss.relics.items.relics.base.data.RelicData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilitiesData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.AbilityData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.LevelingData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.StatData;
import it.hurts.sskirillss.relics.items.relics.base.data.leveling.misc.UpgradeOperation;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.LootData;
import it.hurts.sskirillss.relics.items.relics.base.data.loot.misc.LootEntries;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import it.hurts.sskirillss.relics.utils.MathUtils;
import java.util.Comparator;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SnowLayerBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickBlock;

public class WoolMittenItem extends RelicItem {
   @Override
   public RelicData constructDefaultRelicData() {
      return RelicData.builder()
         .abilities(
            AbilitiesData.builder()
               .ability(
                  AbilityData.builder("mold")
                     .stat(
                        StatData.builder("size")
                           .initialValue(12.0, 32.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.2)
                           .formatValue(value -> (int)MathUtils.round(value, 0))
                           .build()
                     )
                     .stat(
                        StatData.builder("damage")
                           .initialValue(0.05, 0.25)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.1)
                           .formatValue(value -> MathUtils.round(value, 2))
                           .build()
                     )
                     .stat(
                        StatData.builder("stun")
                           .initialValue(0.025, 0.05)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.15)
                           .formatValue(value -> MathUtils.round(value, 3))
                           .build()
                     )
                     .stat(
                        StatData.builder("freeze")
                           .initialValue(1.0, 2.0)
                           .upgradeModifier(UpgradeOperation.MULTIPLY_BASE, 0.3)
                           .formatValue(value -> MathUtils.round(value, 1))
                           .build()
                     )
                     .build()
               )
               .build()
         )
         .leveling(new LevelingData(100, 10, 100))
         .loot(LootData.builder().entry(LootEntries.FROST, LootEntries.TAIGA, LootEntries.MOUNTAIN).build())
         .build();
   }

   @EventBusSubscriber
   public static class Events {
      @SubscribeEvent
      public static void onBlockClick(RightClickBlock event) {
         Player player = event.getEntity();
         ItemStack relicStack = EntityUtils.findEquippedCurio(player, (Item)ItemRegistry.WOOL_MITTEN.get());
         if (player.getMainHandItem().isEmpty() && player.getOffhandItem().isEmpty() && relicStack.getItem() instanceof IRelicItem relic) {
            Level var14 = player.level();
            BlockPos pos = event.getPos();
            BlockState state = var14.getBlockState(pos);
            Block block = state.getBlock();
            int layers = block == Blocks.SNOW ? (Integer)state.getValue(SnowLayerBlock.LAYERS) : (block == Blocks.SNOW_BLOCK ? 9 : 0);
            if (layers != 0) {
               Inventory inventory = player.getInventory();
               int size = (int)Math.round(relic.getStatValue(relicStack, "mold", "size"));
               Optional<Integer> slot = EntityUtils.getSlotsWithItem(player, (Item)ItemRegistry.SOLID_SNOWBALL.get())
                  .stream()
                  .filter(id -> (Integer)inventory.getItem(id).getOrDefault(DataComponentRegistry.CHARGE, 0) < size)
                  .max(Comparator.comparingInt(s -> (Integer)((ItemStack)inventory.items.get(s)).getOrDefault(DataComponentRegistry.CHARGE, 0)));
               if (slot.isEmpty()) {
                  if (!inventory.add(new ItemStack((ItemLike)ItemRegistry.SOLID_SNOWBALL.get()))) {
                     return;
                  }

                  slot = EntityUtils.getSlotsWithItem(player, (Item)ItemRegistry.SOLID_SNOWBALL.get()).stream().findFirst();
                  if (slot.isEmpty()) {
                     return;
                  }
               }

               ItemStack stack = inventory.getItem(slot.get());
               var14.destroyBlock(pos, false);
               int snow = (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0);
               stack.set(DataComponentRegistry.CHARGE, Math.min(snow + layers, size));
            }
         }
      }
   }
}
