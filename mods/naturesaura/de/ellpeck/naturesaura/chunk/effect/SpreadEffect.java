package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class SpreadEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "spread");

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (Math.abs(spot) >= 500000 && Math.abs(IAuraChunk.getAuraInArea(level, pos, 25) - 1000000) >= 1000000) {
         boolean drain = spot > 0;
         int toMove = Mth.ceil(Math.abs(spot) * 0.72F);
         int perSide = toMove / 6;

         while (toMove > 0) {
            BlockPos bestOffset = null;
            int bestAmount = drain ? Integer.MAX_VALUE : Integer.MIN_VALUE;

            for (Direction facing : Direction.values()) {
               BlockPos offset = pos.relative(facing, 15);
               if (level.isLoaded(offset) && offset.getY() >= level.getMinBuildHeight() && offset.getY() <= level.getMaxBuildHeight()) {
                  int amount = IAuraChunk.getAuraInArea(level, offset, 14);
                  if (drain ? amount < bestAmount : amount > bestAmount) {
                     bestAmount = amount;
                     bestOffset = offset;
                  }
               }
            }

            if (bestOffset == null) {
               break;
            }

            BlockPos bestPos = drain
               ? IAuraChunk.getLowestSpot(level, bestOffset, 14, bestOffset)
               : IAuraChunk.getHighestSpot(level, bestOffset, 14, bestOffset);
            IAuraChunk bestChunk = IAuraChunk.getAuraChunk(level, bestPos);
            int moved;
            if (drain) {
               moved = bestChunk.storeAura(bestPos, perSide);
               auraChunk.drainAura(pos, moved);
            } else {
               moved = bestChunk.drainAura(bestPos, perSide);
               auraChunk.storeAura(pos, moved);
            }

            if (moved != 0) {
               IAuraChunk.DrainSpot bestSpot = bestChunk.getActualDrainSpot(bestPos, false);
               if (bestSpot != null && bestSpot.originalSpreadPos == null) {
                  bestSpot.originalSpreadPos = actualSpot.originalSpreadPos;
                  if (bestSpot.originalSpreadPos == null) {
                     bestSpot.originalSpreadPos = pos;
                  }

                  bestChunk.markDirty();
               }

               toMove -= moved;
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return true;
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
