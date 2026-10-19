package gardensofthedead.registry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import gardensofthedead.block.entity.HangingSignBlockEntity;
import gardensofthedead.block.entity.SignBlockEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;

public class ModBlockEntityTypes {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create("gardens_of_the_dead", Registries.BLOCK_ENTITY_TYPE);
   public static final RegistrySupplier<BlockEntityType<SignBlockEntity>> SIGN = BLOCK_ENTITY_TYPES.register(
      "sign",
      () -> Builder.of(
            SignBlockEntity::new,
            new Block[]{
               (Block)ModBlocks.SOULBLIGHT_SIGN.get(),
               (Block)ModBlocks.SOULBLIGHT_WALL_SIGN.get(),
               (Block)ModBlocks.WHISTLECANE_SIGN.get(),
               (Block)ModBlocks.WHISTLECANE_WALL_SIGN.get()
            }
         )
         .build(null)
   );
   public static final RegistrySupplier<BlockEntityType<HangingSignBlockEntity>> HANGING_SIGN = BLOCK_ENTITY_TYPES.register(
      "hanging_sign",
      () -> Builder.of(
            HangingSignBlockEntity::new,
            new Block[]{
               (Block)ModBlocks.SOULBLIGHT_HANGING_SIGN.get(),
               (Block)ModBlocks.SOULBLIGHT_WALL_HANGING_SIGN.get(),
               (Block)ModBlocks.WHISTLECANE_HANGING_SIGN.get(),
               (Block)ModBlocks.WHISTLECANE_WALL_HANGING_SIGN.get()
            }
         )
         .build(null)
   );
}
