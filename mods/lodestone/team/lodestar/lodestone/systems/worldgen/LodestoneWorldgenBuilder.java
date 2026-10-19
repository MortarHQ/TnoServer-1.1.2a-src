package team.lodestar.lodestone.systems.worldgen;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;

public class LodestoneWorldgenBuilder {
   protected final ArrayList<LodestoneWorldgenBuilderLayer> layers = new ArrayList<>();
   protected PlacementCondition defaultPlacementCondition = (level, entry) -> true;
   protected AdditionalPlacement defaultAdditionalPlacement = (level, entry) -> {};

   public static LodestoneWorldgenBuilder create() {
      return new LodestoneWorldgenBuilder();
   }

   public LodestoneWorldgenBuilder addAdditionalPlacement(AdditionalPlacement defaultAdditionalPlacement) {
      this.defaultAdditionalPlacement = defaultAdditionalPlacement;
      return this;
   }

   public LodestoneWorldgenBuilder addPlacementCondition(PlacementCondition defaultPlacementCondition) {
      this.defaultPlacementCondition = defaultPlacementCondition;
      return this;
   }

   public LodestoneWorldgenBuilderLayer createLayer() {
      LodestoneWorldgenBuilderLayer layer = new LodestoneWorldgenBuilderLayer();
      layer.addAdditionalPlacement(this.defaultAdditionalPlacement);
      layer.addPlacementCondition(this.defaultPlacementCondition);
      this.layers.add(layer);
      return layer;
   }

   public ArrayList<LodestoneWorldgenBuilderLayer> getLayers() {
      return this.layers;
   }

   public LodestoneWorldgenBuilderLayer getLayer(int index) {
      return this.layers.get(index);
   }

   public ArrayList<BlockPos> getAffectedArea(int layerIndex) {
      return new ArrayList<>(this.getLayer(layerIndex).getAffectedArea());
   }

   public ArrayList<BlockPos> getAffectedArea() {
      ArrayList<BlockPos> affectedArea = new ArrayList<>();

      for (LodestoneWorldgenBuilderLayer layer : this.getLayers()) {
         affectedArea.addAll(layer.getAffectedArea());
      }

      return affectedArea;
   }

   public Collection<LodestoneWorldgenBuilderEntry> getEntries(int layerIndex) {
      return this.getLayer(layerIndex).getEntries();
   }

   public ArrayList<LodestoneWorldgenBuilderEntry> getAllEntries() {
      ArrayList<LodestoneWorldgenBuilderEntry> entries = new ArrayList<>();

      for (LodestoneWorldgenBuilderLayer layer : this.getLayers()) {
         entries.addAll(layer.getEntries());
      }

      return entries;
   }

   public Collection<LodestoneWorldgenBuilderEntry> getOrderedEntries(int layerIndex) {
      return this.getLayer(layerIndex).getOrderedEntries();
   }

   public ArrayList<LodestoneWorldgenBuilderEntry> getOrderedEntries() {
      ArrayList<LodestoneWorldgenBuilderEntry> entries = new ArrayList<>();

      for (LodestoneWorldgenBuilderLayer layer : this.getLayers()) {
         entries.addAll(layer.getOrderedEntries());
      }

      return entries;
   }

   public LodestoneWorldgenBuilder merge(LodestoneWorldgenBuilder other) {
      this.getLayers().addAll(other.getLayers());
      return this;
   }

   public void place(WorldGenLevel level) {
      Set<BlockPos> skippedPositions = new HashSet<>();

      for (LodestoneWorldgenBuilderLayer layer : this.getLayers()) {
         for (LodestoneWorldgenBuilderEntry entry : layer.getOrderedEntries()) {
            if ((entry.isImportant() || !skippedPositions.contains(entry.position())) && entry.tryPlace(level)) {
               skippedPositions.add(entry.position());
            }
         }
      }
   }
}
