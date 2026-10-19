package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.Helper;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.packet.PacketHandler;
import de.ellpeck.naturesaura.packet.PacketParticles;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.TallGrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.tuple.Pair;

public class PlantBoostEffect implements IDrainSpotEffect {
   public static final Set<Block> EXCEPTIONS = new HashSet<>(
      List.of(
         Blocks.GRASS_BLOCK,
         Blocks.MOSS_BLOCK,
         Blocks.GLOW_LICHEN,
         Blocks.SMALL_DRIPLEAF,
         Blocks.BIG_DRIPLEAF,
         Blocks.BIG_DRIPLEAF_STEM,
         Blocks.PINK_PETALS,
         Blocks.AZALEA,
         Blocks.FLOWERING_AZALEA
      )
   );
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "plant_boost");
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

      this.amount = Math.min(75, Mth.ceil(Math.abs(aura) / 50000.0F / ((Integer)auraAndSpots.getRight()).intValue()));
      if (this.amount <= 1) {
         return false;
      }

      this.dist = Mth.clamp(Math.abs(aura) / 150000, 5, 35);
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
      return new ItemStack(Items.WHEAT_SEEDS);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (this.calcValues(level, pos, spot)) {
         for (int i = this.amount / 2 + level.random.nextInt(this.amount / 2); i >= 0; i--) {
            int x = Mth.floor(pos.getX() + (2.0F * level.random.nextFloat() - 1.0F) * this.dist);
            int y = Mth.floor(pos.getY() + (2.0F * level.random.nextFloat() - 1.0F) * this.dist / 2.0F);
            int z = Mth.floor(pos.getZ() + (2.0F * level.random.nextFloat() - 1.0F) * this.dist);
            BlockPos plantPos = Helper.getClosestAirAboveGround(level, new BlockPos(x, y, z), this.dist / 2).below();
            if (plantPos.distSqr(pos) <= this.dist * this.dist
               && level.isLoaded(plantPos)
               && !NaturesAuraAPI.instance().isEffectPowderActive(level, plantPos, NAME)) {
               BlockState state = level.getBlockState(plantPos);
               Block block = state.getBlock();
               if (block instanceof BonemealableBlock growable
                  && !EXCEPTIONS.contains(block)
                  && !(block instanceof DoublePlantBlock)
                  && !(block instanceof TallGrassBlock)
                  && growable.isValidBonemealTarget(level, plantPos, state)) {
                  try {
                     growable.performBonemeal((ServerLevel)level, level.random, plantPos, state);
                  } catch (Exception var16) {
                  }

                  BlockPos closestSpot = IAuraChunk.getHighestSpot(level, plantPos, 25, pos);
                  IAuraChunk.getAuraChunk(level, closestSpot).drainAura(closestSpot, 3500);
                  PacketHandler.sendToAllAround(
                     level, plantPos, 32, new PacketParticles(plantPos.getX(), plantPos.getY(), plantPos.getZ(), PacketParticles.Type.PLANT_BOOST)
                  );
               }
            }
         }
      }
   }

   @Override
   public boolean appliesHere(LevelChunk chunk, IAuraChunk auraChunk, IAuraType type) {
      return (Boolean)ModConfig.instance.plantBoostEffect.get() && type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD);
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
