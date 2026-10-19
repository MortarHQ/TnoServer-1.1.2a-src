package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.chunk.ISpotDrainable;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;

public class ReplenishingEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "replenishing");

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (spot < 0) {
         List<ISpotDrainable> tiles = new ArrayList<>();
         Helper.getBlockEntitiesInArea(
            level,
            pos,
            25,
            tilex -> {
               IAuraContainer container = (IAuraContainer)level.getCapability(
                  NaturesAuraAPI.AURA_CONTAINER_BLOCK_CAPABILITY, tilex.getBlockPos(), tilex.getBlockState(), tilex, null
               );
               if (container instanceof ISpotDrainable) {
                  tiles.add((ISpotDrainable)container);
               }

               return false;
            }
         );
         if (!tiles.isEmpty()) {
            IAuraType type = IAuraType.forLevel(level);

            for (int i = level.random.nextInt(6); i >= 0; i--) {
               ISpotDrainable tile = tiles.get(level.random.nextInt(tiles.size()));
               if (tile.isAcceptableType(type)) {
                  int drained = tile.drainAuraPassively(-spot, false);
                  if (drained > 0) {
                     auraChunk.storeAura(pos, drained);
                     spot = spot + drained;
                     if (spot >= drained) {
                        break;
                     }
                  }
               }
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
