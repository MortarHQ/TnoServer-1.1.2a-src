package team.lodestar.lodestone.systems.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class LodestoneWorldgenBuilderLayer {
   protected final HashMap<BlockPos, LodestoneWorldgenBuilderEntry> entries = new HashMap<>();
   protected final ArrayList<BlockPos> entryOrder = new ArrayList<>();
   protected PlacementCondition defaultPlacementCondition;
   protected AdditionalPlacement defaultAdditionalPlacement;

   public ArrayList<BlockPos> getAffectedArea() {
      return new ArrayList<>(this.entries.keySet());
   }

   public LodestoneWorldgenBuilderLayer addAdditionalPlacement(AdditionalPlacement defaultAdditionalPlacement) {
      this.defaultAdditionalPlacement = defaultAdditionalPlacement;
      return this;
   }

   public LodestoneWorldgenBuilderLayer addPlacementCondition(PlacementCondition defaultPlacementCondition) {
      this.defaultPlacementCondition = defaultPlacementCondition;
      return this;
   }

   public LodestoneWorldgenBuilderLayer merge(LodestoneWorldgenBuilderLayer other) {
      this.entries.putAll(other.entries);
      return this;
   }

   public LodestoneWorldgenBuilderEntry add(BlockPos blockPos, Block block) {
      return this.add(blockPos, block.defaultBlockState());
   }

   public LodestoneWorldgenBuilderEntry add(BlockPos pos, BlockState state) {
      if (pos instanceof MutableBlockPos mutable) {
         pos = mutable.immutable();
      }

      LodestoneWorldgenBuilderEntry entry = new LodestoneWorldgenBuilderEntry(pos, state);
      if (!entry.hasPlacementCondition()) {
         entry.addPlacementCondition(this.defaultPlacementCondition);
      }

      if (!entry.hasAdditionalPlacement()) {
         entry.addAdditionalPlacement(this.defaultAdditionalPlacement);
      }

      this.add(pos, entry);
      return entry;
   }

   public LodestoneWorldgenBuilderLayer add(BlockPos pos, LodestoneWorldgenBuilderEntry entry) {
      this.entries.put(pos, entry);
      this.entryOrder.add(pos);
      return this;
   }

   public LodestoneWorldgenBuilderLayer remove(BlockPos pos) {
      this.entries.remove(pos);
      this.entryOrder.remove(pos);
      return this;
   }

   public LodestoneWorldgenBuilderEntry get(BlockPos pos) {
      return this.entries.get(pos);
   }

   public boolean containsKey(BlockPos pos) {
      return this.entries.containsKey(pos);
   }

   public Collection<LodestoneWorldgenBuilderEntry> getEntries() {
      return this.entries.values();
   }

   public ArrayList<LodestoneWorldgenBuilderEntry> getOrderedEntries() {
      ArrayList<LodestoneWorldgenBuilderEntry> orderedEntries = new ArrayList<>();

      for (BlockPos pos : this.entryOrder) {
         orderedEntries.add(this.entries.get(pos));
      }

      return orderedEntries;
   }

   public ArrayList<LodestoneWorldgenBuilderEntry> getRandomEntries(int amount) {
      ArrayList<LodestoneWorldgenBuilderEntry> randomEntries = new ArrayList<>();
      List<BlockPos> keys = new ArrayList<>(this.entries.keySet());
      Collections.shuffle(keys);

      for (int i = 0; i < Math.min(amount, keys.size()); i++) {
         randomEntries.add(this.entries.get(keys.get(i)));
      }

      return randomEntries;
   }
}
