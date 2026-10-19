package bigchadguys.sellingbin.init;

import bigchadguys.sellingbin.block.BinMaterial;
import bigchadguys.sellingbin.block.SellingBinBlock;
import bigchadguys.sellingbin.block.entity.SellingBinBlockEntity;
import bigchadguys.sellingbin.block.entity.renderer.SellingBinBlockEntityRenderer;
import bigchadguys.sellingbin.item.SellingBinItem;
import bigchadguys.sellingbin.mixin.ProxyModelPredicateProviderRegistry;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.client.ClientLifecycleEvent.ClientState;
import java.util.Map;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModRenderers {
   public static <T extends BlockEntity> BlockEntityRendererProvider<T> register(
      Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> registry, BlockEntityType<? extends T> type, BlockEntityRendererProvider<T> renderer
   ) {
      registry.put(type, renderer);
      return renderer;
   }

   public static class BlockEntities extends ModRenderers {
      public static BlockEntityRendererProvider<SellingBinBlockEntity> SELLING_BIN;

      public static void register(Map<BlockEntityType<?>, BlockEntityRendererProvider<?>> registry) {
         try {
            ProxyModelPredicateProviderRegistry.register(
               ((SellingBinBlock)ModBlocks.SELLING_BIN.get()).asItem(),
               ResourceLocation.parse("material"),
               (stack, world, entity, seed) -> SellingBinItem.getMaterial(stack).orElse(BinMaterial.WOOD).ordinal() / 10.0F
            );
            SELLING_BIN = register(
               registry, (BlockEntityType<? extends SellingBinBlockEntity>)ModBlocks.Entities.SELLING_BIN.get(), SellingBinBlockEntityRenderer::new
            );
         } catch (Exception e) {
            ClientLifecycleEvent.CLIENT_SETUP
               .register(
                  (ClientState)minecraft -> {
                     ProxyModelPredicateProviderRegistry.register(
                        ((SellingBinBlock)ModBlocks.SELLING_BIN.get()).asItem(),
                        ResourceLocation.parse("material"),
                        (stack, world, entity, seed) -> SellingBinItem.getMaterial(stack).orElse(BinMaterial.WOOD).ordinal() / 10.0F
                     );
                     SELLING_BIN = register(
                        registry, (BlockEntityType<? extends SellingBinBlockEntity>)ModBlocks.Entities.SELLING_BIN.get(), SellingBinBlockEntityRenderer::new
                     );
                  }
               );
         }
      }
   }
}
