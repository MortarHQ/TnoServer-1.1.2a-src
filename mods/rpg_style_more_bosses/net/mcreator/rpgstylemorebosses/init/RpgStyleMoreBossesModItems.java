package net.mcreator.rpgstylemorebosses.init;

import net.mcreator.rpgstylemorebosses.item.ExoframePartItem;
import net.mcreator.rpgstylemorebosses.item.PizdabobraItem;
import net.mcreator.rpgstylemorebosses.item.TheExoframeKeyItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.common.DeferredSpawnEggItem;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;

public class RpgStyleMoreBossesModItems {
   public static final Items REGISTRY = DeferredRegister.createItems("rpg_style_more_bosses");
   public static final DeferredItem<Item> DUNGEON_BRICKS_CUBES = block(RpgStyleMoreBossesModBlocks.DUNGEON_BRICKS_CUBES);
   public static final DeferredItem<Item> BEACON_SPAWN_EGG = REGISTRY.register(
      "beacon_spawn_egg", () -> new DeferredSpawnEggItem(RpgStyleMoreBossesModEntities.BEACON, -13395457, -6710887, new Properties())
   );
   public static final DeferredItem<Item> EXOFRAME_PART = REGISTRY.register("exoframe_part", ExoframePartItem::new);
   public static final DeferredItem<Item> THE_EXOFRAME_KEY = REGISTRY.register("the_exoframe_key", TheExoframeKeyItem::new);
   public static final DeferredItem<Item> PIZDABOBRA = REGISTRY.register("pizdabobra", PizdabobraItem::new);

   private static DeferredItem<Item> block(DeferredHolder<Block, Block> block) {
      return REGISTRY.register(block.getId().getPath(), () -> new BlockItem((Block)block.get(), new Properties()));
   }
}
