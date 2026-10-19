package team.lodestar.lodestone.registry.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Supplier;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.core.DefaultedRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.BlockEntityType.Builder;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.registries.DeferredRegister;
import team.lodestar.lodestone.systems.block.sign.LodestoneStandingSignBlock;
import team.lodestar.lodestone.systems.block.sign.LodestoneWallSignBlock;
import team.lodestar.lodestone.systems.blockentity.LodestoneSignBlockEntity;
import team.lodestar.lodestone.systems.multiblock.ILodestoneMultiblockComponent;
import team.lodestar.lodestone.systems.multiblock.MultiBlockComponentEntity;

public class LodestoneBlockEntities {
   public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, "lodestone");
   public static final Supplier<BlockEntityType<MultiBlockComponentEntity>> MULTIBLOCK_COMPONENT = BLOCK_ENTITY_TYPES.register(
      "multiblock_component", () -> Builder.of(MultiBlockComponentEntity::new, getBlocks(ILodestoneMultiblockComponent.class)).build(null)
   );
   public static final Supplier<BlockEntityType<LodestoneSignBlockEntity>> SIGN = BLOCK_ENTITY_TYPES.register(
      "sign", () -> Builder.of(LodestoneSignBlockEntity::new, getBlocks(LodestoneStandingSignBlock.class, LodestoneWallSignBlock.class)).build(null)
   );

   public static Block[] getBlocks(Class<?>... blockClasses) {
      DefaultedRegistry<Block> blocks = BuiltInRegistries.BLOCK;
      ArrayList<Block> matchingBlocks = new ArrayList<>();

      for (Block block : blocks) {
         if (Arrays.stream(blockClasses).anyMatch(b -> b.isInstance(block))) {
            matchingBlocks.add(block);
         }
      }

      return matchingBlocks.toArray(new Block[0]);
   }

   public static Block[] getBlocksExact(Class<?> clazz) {
      DefaultedRegistry<Block> blocks = BuiltInRegistries.BLOCK;
      ArrayList<Block> matchingBlocks = new ArrayList<>();

      for (Block block : blocks) {
         if (clazz.equals(block.getClass())) {
            matchingBlocks.add(block);
         }
      }

      return matchingBlocks.toArray(new Block[0]);
   }

   @EventBusSubscriber(modid = "lodestone", value = Dist.CLIENT, bus = Bus.MOD)
   public static class ClientOnly {
      @SubscribeEvent
      public static void registerRenderer(RegisterRenderers event) {
         event.registerBlockEntityRenderer(LodestoneBlockEntities.SIGN.get(), SignRenderer::new);
      }
   }
}
