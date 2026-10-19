package dev.ftb.mods.ftbultimine.utils;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;

public final class ShapeMerger {
   private static final Long2ObjectMap<Direction> BY_NORMAL = Arrays.stream(Direction.values())
      .collect(Collectors.toMap(dir -> new BlockPos(dir.getNormal()).asLong(), dir -> (Direction)dir, (dir, dir2) -> {
         throw new IllegalArgumentException("Duplicate keys");
      }, Long2ObjectOpenHashMap::new));
   private double regionBuildingAxis = Double.NEGATIVE_INFINITY;
   private double faceBuildingAxis = Double.NEGATIVE_INFINITY;
   private Vec3 lastCenterPoint = null;
   private AABB currentBox;
   private final Map<Vec3, AABB> boxAssignments = Maps.newHashMap();
   private final Multimap<AABB, Vec3> mergerAssignments = HashMultimap.create();

   public double getRegionBuildingAxisValue() {
      return this.regionBuildingAxis;
   }

   public void setRegionBuildingAxisValue(double regionBuildingAxis) {
      this.regionBuildingAxis = regionBuildingAxis;
   }

   public double getFaceBuildingAxisValue() {
      return this.faceBuildingAxis;
   }

   public void setFaceBuildingAxisValue(double faceBuildingAxis) {
      this.faceBuildingAxis = faceBuildingAxis;
   }

   public AABB getCurrentBox() {
      return this.currentBox;
   }

   public void setCurrentBox(AABB currentBox, Vec3 centerPoint) {
      this.currentBox = currentBox;
      if (currentBox != null) {
         this.boxAssignments.put(centerPoint, currentBox);
         this.mergerAssignments.put(currentBox, centerPoint);
      }
   }

   public Optional<AABB> getBoxFor(Vec3 target) {
      return Optional.ofNullable(this.boxAssignments.get(target));
   }

   public Optional<Vec3> getLastCenter() {
      return Optional.ofNullable(this.lastCenterPoint);
   }

   public void onNextEntry(Vec3 lastCenterPoint) {
      this.lastCenterPoint = lastCenterPoint;
   }

   public void expandCurrentBoxTowards(AABB target, Vec3 center) {
      AABB current = this.getCurrentBox();
      if (current == null) {
         throw new IllegalStateException("Can not expand current box, if current is not set.");
      }

      AABB expanded = current.minmax(target);
      Collection<Vec3> currentlyAssignedToCurrent = this.mergerAssignments.removeAll(current);
      currentlyAssignedToCurrent.forEach(v -> this.boxAssignments.put(v, expanded));
      this.mergerAssignments.putAll(expanded, currentlyAssignedToCurrent);
      this.boxAssignments.put(center, expanded);
      this.mergerAssignments.put(expanded, center);
      this.currentBox = expanded;
   }

   public Collection<AABB> getBoxes() {
      return this.mergerAssignments.keySet();
   }

   public void expandBoxAt(Vec3 neighborCenter, AABB entryData, Vec3 centerPoint) {
      AABB current = this.boxAssignments.get(neighborCenter);
      if (current == null) {
         throw new IllegalStateException(String.format("Can not expand box at: %s, if current is not set.", neighborCenter));
      }

      AABB expanded = current.minmax(entryData);
      Collection<Vec3> currentlyAssignedToCurrent = this.mergerAssignments.removeAll(current);
      currentlyAssignedToCurrent.forEach(v -> this.boxAssignments.put(v, expanded));
      this.mergerAssignments.putAll(expanded, currentlyAssignedToCurrent);
      this.boxAssignments.put(centerPoint, expanded);
      this.mergerAssignments.put(expanded, centerPoint);
   }

   public boolean mergeNeighbors(Vec3 centerPoint, AABB aabb) {
      for (Direction offsetDirection : Direction.values()) {
         Vec3 neighborCenter = centerPoint.add(Vec3.atLowerCornerOf(offsetDirection.getNormal()));
         Optional<AABB> potentialNeighborBox = this.getBoxFor(neighborCenter);
         if (potentialNeighborBox.isPresent()) {
            AABB neighborBox = potentialNeighborBox.get();
            if (areBoxesNeighbors(aabb, neighborBox, offsetDirection)) {
               this.expandBoxAt(neighborCenter, aabb, centerPoint);
               return true;
            }
         }
      }

      return false;
   }

   public static boolean areBoxesNeighbors(AABB l, AABB r, Direction direction) {
      double endOfL = getDirectionalValue(l, direction);
      double startOfR = getDirectionalValue(r, direction.getOpposite());
      if (endOfL != startOfR) {
         return false;
      }

      for (Direction d : Direction.values()) {
         if (d.getAxis() != direction.getAxis() && getDirectionalValue(l, d) != getDirectionalValue(r, d)) {
            return false;
         }
      }

      return true;
   }

   public static double getDirectionalValue(AABB bb, Direction direction) {
      return direction.getAxisDirection() == AxisDirection.POSITIVE ? bb.max(direction.getAxis()) : bb.min(direction.getAxis());
   }

   public static Collection<AABB> merge(Collection<BlockPos> positions, BlockPos origin) {
      ShapeMerger merger = new ShapeMerger();
      positions.stream().map(pos -> pos.subtract(origin)).sorted().<AABB>map(AABB::new).forEachOrdered(aabb -> {
         if (merger.getRegionBuildingAxisValue() != aabb.minX) {
            merger.setCurrentBox(null, null);
         }

         merger.setRegionBuildingAxisValue(aabb.minX);
         if (merger.getFaceBuildingAxisValue() != aabb.minY) {
            merger.setCurrentBox(null, null);
         }

         merger.setFaceBuildingAxisValue(aabb.minY);
         Optional<Vec3> previousCenterPoint = merger.getLastCenter();
         Vec3 centerPoint = aabb.getCenter();
         merger.onNextEntry(centerPoint);
         Optional<Direction> moveNext = previousCenterPoint.map(v -> {
            Vec3 w = centerPoint.subtract(v);
            BlockPos onPos = BlockPos.containing(w);
            return dirFromNormal(onPos.getX(), onPos.getY(), onPos.getZ());
         });
         if (merger.getCurrentBox() != null && moveNext.map(dir -> areBoxesNeighbors(merger.getCurrentBox(), aabb, dir)).orElse(false)) {
            merger.expandCurrentBoxTowards(aabb, centerPoint);
            if (!merger.mergeNeighbors(centerPoint, merger.getCurrentBox())) {
               ;
            }
         } else if (!merger.mergeNeighbors(centerPoint, aabb)) {
            merger.setCurrentBox(aabb, centerPoint);
         }
      });
      return merger.getBoxes();
   }

   private static Direction dirFromNormal(int pX, int pY, int pZ) {
      return (Direction)BY_NORMAL.get(BlockPos.asLong(pX, pY, pZ));
   }
}
