package net.jadenxgamer.elysium_api.impl.registry;

import java.util.function.Supplier;
import net.jadenxgamer.elysium_api.impl.core.block.GlassStructureVoidBlock;
import net.jadenxgamer.elysium_api.impl.core.block.MobBarrierBlock;
import net.jadenxgamer.elysium_api.impl.core.block.SolidStructureVoidBlock;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.GameMasterBlockItem;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ElysiumBlocks {
   public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(BuiltInRegistries.BLOCK, "elysium_api");
   public static final Supplier<Block> STRUCTURE_STAMP_ANCHOR = registerGameMasterBlock(
      "structure_stamp_anchor",
      () -> new Block(new Properties().mapColor(MapColor.COLOR_LIGHT_GRAY).requiresCorrectToolForDrops().strength(-1.0F, 3600000.0F).noLootTable()),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.EPIC)
   );
   public static final Supplier<Block> MOB_BARRIER = registerGameMasterBlock(
      "mob_barrier",
      () -> new MobBarrierBlock(new Properties().replaceable().strength(-1.0F, 3600000.8F).mapColor(MapColor.NONE).noLootTable().noOcclusion()),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.EPIC)
   );
   public static final Supplier<Block> SOLID_STRUCTURE_VOID = registerGameMasterBlock(
      "solid_structure_void",
      () -> new SolidStructureVoidBlock(new Properties().strength(-1.0F, 3600000.8F).mapColor(MapColor.NONE).noLootTable()),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.EPIC)
   );
   public static final Supplier<Block> GLASS_STRUCTURE_VOID = registerGameMasterBlock(
      "glass_structure_void",
      () -> new GlassStructureVoidBlock(new Properties().noOcclusion().strength(-1.0F, 3600000.8F).mapColor(MapColor.NONE).noLootTable()),
      new net.minecraft.world.item.Item.Properties().rarity(Rarity.EPIC)
   );

   public static void init(IEventBus eventBus) {
      BLOCKS.register(eventBus);
   }

   public static <T extends Block> Supplier<T> registerGameMasterBlock(String name, Supplier<T> block, net.minecraft.world.item.Item.Properties properties) {
      Supplier<T> toReturn = BLOCKS.register(name, block);
      ElysiumItems.ITEMS.register(name, () -> new GameMasterBlockItem(toReturn.get(), properties));
      return toReturn;
   }
}
