package net.p3pp3rf1y.sophisticatedbackpacks.client.render;

import com.google.common.collect.UnmodifiableIterator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.p3pp3rf1y.sophisticatedbackpacks.SophisticatedBackpacks;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackBlock;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapes;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;

public class ClientBackpackShapeProvider implements BackpackShapes.IShapeProvider {
   public static final ClientBackpackShapeProvider INSTANCE = new ClientBackpackShapeProvider();
   private volatile Map<BlockState, VoxelShape> shapeCache = Map.of();
   private final Set<Class<?>> warnedModelTypes = ConcurrentHashMap.newKeySet();
   private volatile boolean warnedRendererMissing = false;

   private ClientBackpackShapeProvider() {
   }

   @Override
   public VoxelShape getShape(BlockState state) {
      return this.shapeCache.getOrDefault(state, BackpackShapes.getDefaultShapeProvider().getShape(state));
   }

   public void rebuildShapes() {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.getBlockRenderer() == null) {
         if (!this.warnedRendererMissing) {
            this.warnedRendererMissing = true;
            SophisticatedBackpacks.LOGGER.warn("Backpack block renderer is not ready yet, using base model-derived shapes until client models are available");
         }

         this.shapeCache = Map.of();
      } else {
         this.warnedRendererMissing = false;
         Map<BlockState, VoxelShape> newShapes = new HashMap<>();

         for (Supplier<? extends Block> backpackBlockSupplier : List.of(
            ModBlocks.BACKPACK,
            ModBlocks.COPPER_BACKPACK,
            ModBlocks.IRON_BACKPACK,
            ModBlocks.GOLD_BACKPACK,
            ModBlocks.DIAMOND_BACKPACK,
            ModBlocks.NETHERITE_BACKPACK
         )) {
            Block block = backpackBlockSupplier.get();
            UnmodifiableIterator var7 = block.getStateDefinition().getPossibleStates().iterator();

            while (var7.hasNext()) {
               BlockState state = (BlockState)var7.next();
               newShapes.put(state, this.computeShapeFromLoadedModel(minecraft, state));
            }
         }

         this.shapeCache = Map.copyOf(newShapes);
      }
   }

   private VoxelShape computeShapeFromLoadedModel(Minecraft minecraft, BlockState state) {
      BakedModel model = minecraft.getBlockRenderer().getBlockModel(state);
      return model instanceof BackpackDynamicModel.BackpackBakedModel backpackModel
         ? backpackModel.getShape(
               (Direction)state.getValue(BackpackBlock.FACING),
               (Boolean)state.getValue(BackpackBlock.LEFT_TANK),
               (Boolean)state.getValue(BackpackBlock.RIGHT_TANK),
               (Boolean)state.getValue(BackpackBlock.BATTERY)
            )
            .orElseGet(() -> this.getDefaultShapeWithWarning(state, model))
         : this.getDefaultShapeWithWarning(state, model);
   }

   private VoxelShape getDefaultShapeWithWarning(BlockState state, BakedModel model) {
      Class<?> modelClass = model.getClass();
      if (this.warnedModelTypes.add(modelClass)) {
         SophisticatedBackpacks.LOGGER
            .warn("Backpack client model {} does not provide shape extraction, using base model-derived shape fallback", modelClass.getName());
      }

      VoxelShape shape = BackpackShapes.getDefaultShapeProvider().getShape(state);
      return shape.isEmpty() ? Shapes.block() : shape;
   }
}
