package team.lodestar.lodestone.systems.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import team.lodestar.lodestone.helpers.block.BlockStateHelper;

@Deprecated(forRemoval = true, since = "1.7.2")
public class LodestoneBlockFiller extends ArrayList<LodestoneBlockFiller.LodestoneBlockFillerLayer> {
   public static final LodestoneBlockFiller.LodestoneLayerToken MAIN = new LodestoneBlockFiller.LodestoneLayerToken();
   protected final LodestoneBlockFiller.LodestoneBlockFillerLayer mainLayer = new LodestoneBlockFiller.LodestoneBlockFillerLayer(MAIN);

   public LodestoneBlockFiller() {
   }

   public LodestoneBlockFiller(LodestoneBlockFiller.LodestoneBlockFillerLayer... layers) {
      this(new ArrayList<>(List.of(layers)));
   }

   public LodestoneBlockFiller(Collection<LodestoneBlockFiller.LodestoneBlockFillerLayer> layers) {
      this();
      this.addAll(layers);
   }

   public LodestoneBlockFiller addLayers(LodestoneBlockFiller.LodestoneLayerToken... tokens) {
      for (LodestoneBlockFiller.LodestoneLayerToken token : tokens) {
         this.addLayers(new LodestoneBlockFiller.LodestoneBlockFillerLayer(token));
      }

      return this;
   }

   public LodestoneBlockFiller addLayers(LodestoneBlockFiller.LodestoneBlockFillerLayer... layers) {
      this.addAll(List.of(layers));
      return this;
   }

   public LodestoneBlockFiller.LodestoneBlockFillerLayer getLayer(@NotNull LodestoneBlockFiller.LodestoneLayerToken layerToken) {
      return layerToken.equals(MAIN) ? this.mainLayer : this.stream().filter(l -> l.layerToken.equals(layerToken)).findFirst().orElseThrow();
   }

   public LodestoneBlockFiller.LodestoneBlockFillerLayer getMainLayer() {
      return this.mainLayer;
   }

   public LodestoneBlockFiller.LodestoneBlockFillerLayer fill(LevelAccessor level) {
      LodestoneBlockFiller.LodestoneBlockFillerLayer mainLayer = this.getMainLayer();
      mainLayer.clear();

      for (int i = 0; i < this.size(); i++) {
         this.mergeLayers(mainLayer, this.get(i));
      }

      ArrayList<LodestoneBlockFiller.BlockStateEntry> discarded = mainLayer.entrySet()
         .stream()
         .filter(entry -> entry.getValue().tryDiscard(level, entry.getKey()))
         .map(Entry::getValue)
         .collect(Collectors.toCollection(ArrayList::new));
      mainLayer.forEach((pos, blockStateEntry) -> {
         if (!discarded.contains(blockStateEntry) && blockStateEntry.canPlace(level, pos)) {
            blockStateEntry.place(level, pos);
         }
      });
      return mainLayer;
   }

   protected void mergeLayers(LodestoneBlockFiller.LodestoneBlockFillerLayer toLayer, LodestoneBlockFiller.LodestoneBlockFillerLayer fromLayer) {
      fromLayer.forEach(toLayer::putIfAbsent);
   }

   public static LodestoneBlockFiller.BlockStateEntryBuilder create(BlockState state) {
      return new LodestoneBlockFiller.BlockStateEntryBuilder(state);
   }

   public static class BlockStateEntry {
      private final BlockState state;
      private final LodestoneBlockFiller.EntryDiscardPredicate discardPredicate;
      private final LodestoneBlockFiller.EntryPlacementPredicate placementPredicate;
      private final boolean forcePlace;

      private BlockStateEntry(
         BlockState state,
         LodestoneBlockFiller.EntryDiscardPredicate discardPredicate,
         LodestoneBlockFiller.EntryPlacementPredicate placementPredicate,
         boolean forcePlace
      ) {
         this.state = state;
         this.discardPredicate = discardPredicate;
         this.placementPredicate = placementPredicate;
         this.forcePlace = forcePlace;
      }

      public BlockState getState() {
         return this.state;
      }

      public boolean tryDiscard(LevelAccessor level, BlockPos pos) {
         return this.discardPredicate != null && this.discardPredicate.shouldDiscard(level, pos, this.state);
      }

      public boolean canPlace(LevelAccessor level, BlockPos pos) {
         if (level.isOutsideBuildHeight(pos)) {
            return false;
         } else {
            BlockState state = level.getBlockState(pos);
            if (this.placementPredicate != null && !this.placementPredicate.canPlace(level, pos, state)) {
               return false;
            } else {
               return this.forcePlace ? true : level.isEmptyBlock(pos) || state.canBeReplaced();
            }
         }
      }

      public void place(LevelAccessor level, BlockPos pos) {
         level.setBlock(pos, this.getState(), 19);
         if (level instanceof Level realLevel) {
            BlockStateHelper.updateState(realLevel, pos);
         }
      }
   }

   public static class BlockStateEntryBuilder {
      private final BlockState state;
      private LodestoneBlockFiller.EntryDiscardPredicate discardPredicate;
      private LodestoneBlockFiller.EntryPlacementPredicate placementPredicate;
      private boolean forcePlace;

      public BlockStateEntryBuilder(BlockState state) {
         this.state = state;
      }

      public LodestoneBlockFiller.BlockStateEntryBuilder setDiscardPredicate(LodestoneBlockFiller.EntryDiscardPredicate discardPredicate) {
         this.discardPredicate = discardPredicate;
         return this;
      }

      public LodestoneBlockFiller.BlockStateEntryBuilder setPlacementPredicate(LodestoneBlockFiller.EntryPlacementPredicate placementPredicate) {
         this.placementPredicate = placementPredicate;
         return this;
      }

      public LodestoneBlockFiller.BlockStateEntryBuilder setForcePlace() {
         return this.setForcePlace(true);
      }

      public LodestoneBlockFiller.BlockStateEntryBuilder setForcePlace(boolean forcePlace) {
         this.forcePlace = forcePlace;
         return this;
      }

      public LodestoneBlockFiller.BlockStateEntry build() {
         return new LodestoneBlockFiller.BlockStateEntry(this.state, this.discardPredicate, this.placementPredicate, this.forcePlace);
      }
   }

   public interface EntryDiscardPredicate {
      boolean shouldDiscard(LevelAccessor var1, BlockPos var2, BlockState var3);
   }

   public interface EntryPlacementPredicate {
      boolean canPlace(LevelAccessor var1, BlockPos var2, BlockState var3);
   }

   public static class LodestoneBlockFillerLayer extends HashMap<BlockPos, LodestoneBlockFiller.BlockStateEntry> {
      public final LodestoneBlockFiller.LodestoneLayerToken layerToken;

      public LodestoneBlockFillerLayer(LodestoneBlockFiller.LodestoneLayerToken layerToken) {
         this.layerToken = layerToken;
      }

      public void fill(LevelAccessor level) {
         this.forEach((pos, entry) -> {
            if (entry.canPlace(level, pos)) {
               entry.place(level, pos);
            }
         });
      }

      public void replace(BlockPos pos, Function<LodestoneBlockFiller.BlockStateEntry, LodestoneBlockFiller.BlockStateEntry> entryFunction) {
         this.replace(pos, entryFunction.apply(this.get(pos)));
      }

      public LodestoneBlockFiller.BlockStateEntry put(BlockPos key, LodestoneBlockFiller.BlockStateEntryBuilder value) {
         return super.put(key, value.build());
      }

      public LodestoneBlockFiller.BlockStateEntry putIfAbsent(BlockPos key, LodestoneBlockFiller.BlockStateEntryBuilder value) {
         return super.putIfAbsent(key, value.build());
      }
   }

   public static class LodestoneLayerToken {
      public final UUID index;

      public LodestoneLayerToken(UUID index) {
         this.index = index;
      }

      public LodestoneLayerToken() {
         this(UUID.randomUUID());
      }
   }
}
