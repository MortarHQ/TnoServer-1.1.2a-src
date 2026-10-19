package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.blocks.ModBlocks;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.Tags.Blocks;
import org.apache.commons.lang3.tuple.Pair;

public class NetherGrassEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "nether_grass");
   private int amount;
   private int dist;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot <= 0) {
         return false;
      }

      Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 30);
      int aura = (Integer)auraAndSpots.getLeft();
      if (aura < 1500000) {
         return false;
      }

      this.amount = Math.min(20, Mth.ceil(Math.abs(aura) / 100000.0F / ((Integer)auraAndSpots.getRight()).intValue()));
      if (this.amount <= 1) {
         return false;
      }

      this.dist = Mth.clamp(Math.abs(aura) / 100000, 5, 35);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return NaturesAuraAPI.instance().isEffectPowderActive(player.level(), player.blockPosition(), NAME)
            ? IDrainSpotEffect.ActiveType.INHIBITED
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(ModBlocks.NETHER_GRASS);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (level.getGameTime() % 40L == 0L) {
         if (this.calcValues(level, pos, spot)) {
            for (int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; i--) {
               int x = Mth.floor(pos.getX() + level.random.nextGaussian() * this.dist);
               int y = Mth.floor(pos.getY() + level.random.nextGaussian() * this.dist);
               int z = Mth.floor(pos.getZ() + level.random.nextGaussian() * this.dist);

               for (int yOff = -5; yOff <= 5; yOff++) {
                  BlockPos goalPos = new BlockPos(x, y + yOff, z);
                  if (goalPos.distSqr(pos) <= this.dist * this.dist
                     && level.isLoaded(goalPos)
                     && !NaturesAuraAPI.instance().isEffectPowderActive(level, goalPos, NAME)) {
                     BlockPos up = goalPos.above();
                     if (!level.getBlockState(up).isFaceSturdy(level, up, Direction.DOWN)) {
                        BlockState state = level.getBlockState(goalPos);
                        if (state.is(Blocks.NETHERRACKS)) {
                           level.setBlockAndUpdate(goalPos, ModBlocks.NETHER_GRASS.defaultBlockState());
                           BlockPos closestSpot = IAuraChunk.getHighestSpot(level, goalPos, 25, pos);
                           IAuraChunk.getAuraChunk(level, closestSpot).drainAura(closestSpot, 500);
                           PacketHandler.sendToAllAround(
                              level, goalPos, 32, new PacketParticles(goalPos.getX(), goalPos.getY() + 0.5F, goalPos.getZ(), PacketParticles.Type.PLANT_BOOST)
                           );
                           break;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.netherGrassEffect.get() && type.isSimilar(NaturesAuraAPI.TYPE_NETHER);
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
