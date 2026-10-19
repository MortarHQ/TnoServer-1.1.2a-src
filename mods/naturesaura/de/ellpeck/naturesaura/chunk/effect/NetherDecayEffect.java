package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.tuple.Pair;

public class NetherDecayEffect implements IDrainSpotEffect {
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "nether_decay");
   private int amount;
   private int dist;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot >= 0) {
         return false;
      }

      Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 50);
      int aura = (Integer)auraAndSpots.getLeft();
      if (aura >= 0) {
         return false;
      }

      this.amount = Math.min(300, Mth.ceil(Math.abs(aura) / 50000.0F / ((Integer)auraAndSpots.getRight()).intValue()));
      if (this.amount <= 1) {
         return false;
      }

      this.dist = Mth.clamp(Math.abs(aura) / 50000, 5, 75);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist
            ? IDrainSpotEffect.ActiveType.INACTIVE
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(Items.SOUL_SAND);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (this.calcValues(level, pos, spot)) {
         for (int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; i--) {
            BlockPos offset = BlockPos.containing(
               pos.getX() + level.random.nextGaussian() * this.dist,
               pos.getY() + level.random.nextGaussian() * this.dist,
               pos.getZ() + level.random.nextGaussian() * this.dist
            );
            if (!(offset.distSqr(pos) > this.dist * this.dist) && level.isLoaded(offset)) {
               Block degraded = null;
               BlockState state = level.getBlockState(offset);
               if (state.getBlock() == Blocks.GLOWSTONE) {
                  degraded = Blocks.NETHERRACK;
               } else if (state.is(BlockTags.NYLIUM) || state.getBlock() == Blocks.NETHERRACK) {
                  degraded = Blocks.SOUL_SOIL;
               } else if (state.getBlock() == Blocks.SOUL_SOIL) {
                  degraded = Blocks.SOUL_SAND;
               }

               if (degraded != null) {
                  level.levelEvent(2001, offset, Block.getId(state));
                  level.setBlockAndUpdate(offset, degraded.defaultBlockState());
               }

               if (BaseFireBlock.canBePlacedAt(level, offset, Direction.NORTH)) {
                  BlockState fire = BaseFireBlock.getState(level, offset);
                  level.setBlockAndUpdate(offset, fire);
                  level.levelEvent(1009, offset, 0);
               }
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.netherDecayEffect.get() && type.isSimilar(NaturesAuraAPI.TYPE_NETHER);
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
