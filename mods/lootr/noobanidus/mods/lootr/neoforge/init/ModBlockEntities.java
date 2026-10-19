package noobanidus.mods.lootr.neoforge.init;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import noobanidus.mods.lootr.common.api.LootrConstants;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.block.entity.LootrBarrelBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrBrushableBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrChestBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrDecoratedPotBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrInventoryBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrShulkerBlockEntity;
import noobanidus.mods.lootr.common.block.entity.LootrTrappedChestBlockEntity;

public class ModBlockEntities {
   private static final DeferredRegister<BlockEntityType<?>> REGISTER = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "lootr");
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrChestBlockEntity>> LOOTR_CHEST = REGISTER.register(
      LootrConstants.LOOTR_CHEST.getPath(), () -> Builder.of(LootrChestBlockEntity::new, new Block[]{LootrRegistry.getChestBlock()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrTrappedChestBlockEntity>> LOOTR_TRAPPED_CHEST = REGISTER.register(
      LootrConstants.LOOTR_TRAPPED_CHEST.getPath(),
      () -> Builder.of(LootrTrappedChestBlockEntity::new, new Block[]{LootrRegistry.getTrappedChestBlock()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrBarrelBlockEntity>> LOOTR_BARREL = REGISTER.register(
      LootrConstants.LOOTR_BARREL.getPath(), () -> Builder.of(LootrBarrelBlockEntity::new, new Block[]{LootrRegistry.getBarrelBlock()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrInventoryBlockEntity>> LOOTR_INVENTORY = REGISTER.register(
      LootrConstants.LOOTR_INVENTORY.getPath(), () -> Builder.of(LootrInventoryBlockEntity::new, new Block[]{LootrRegistry.getInventoryBlock()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrShulkerBlockEntity>> LOOTR_SHULKER = REGISTER.register(
      LootrConstants.LOOTR_SHULKER.getPath(), () -> Builder.of(LootrShulkerBlockEntity::new, new Block[]{LootrRegistry.getShulkerBlock()}).build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrBrushableBlockEntity>> LOOTR_BRUSHABLE_BLOCK = REGISTER.register(
      LootrConstants.BRUSHABLE_BLOCK.getPath(),
      () -> Builder.of(LootrBrushableBlockEntity::new, new Block[]{(Block)ModBlocks.SUSPICIOUS_GRAVEL.get(), (Block)ModBlocks.SUSPICIOUS_SAND.get()})
         .build(null)
   );
   public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<LootrDecoratedPotBlockEntity>> LOOTR_DECORATED_POT = REGISTER.register(
      LootrConstants.DECORATED_POT.getPath(),
      () -> Builder.of(LootrDecoratedPotBlockEntity::new, new Block[]{(Block)ModBlocks.DECORATED_POT.get()}).build(null)
   );

   public static void register(IEventBus bus) {
      REGISTER.register(bus);
   }
}
