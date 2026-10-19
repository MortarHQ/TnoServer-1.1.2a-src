package de.ellpeck.naturesaura.chunk.effect;

import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.api.misc.WeightedOre;
import de.ellpeck.naturesaura.reg.ModRegistry;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.TagKey;
import net.minecraft.util.Mth;
import net.minecraft.util.Tuple;
import net.minecraft.util.random.WeightedRandom;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.common.util.FakePlayer;
import net.neoforged.neoforge.common.util.FakePlayerFactory;
import org.apache.commons.lang3.tuple.Pair;

public class OreSpawnEffect implements IDrainSpotEffect {
   public static final Set<BlockState> SPAWN_EXCEPTIONS = new HashSet<>();
   public static final ResourceLocation NAME = ResourceLocation.fromNamespaceAndPath("naturesaura", "ore_spawn");
   private int amount;
   private int dist;

   private boolean calcValues(Level level, BlockPos pos, Integer spot) {
      if (spot <= 0) {
         return false;
      }

      Pair<Integer, Integer> auraAndSpots = IAuraChunk.getAuraAndSpotAmountInArea(level, pos, 30);
      int aura = (Integer)auraAndSpots.getLeft();
      if (aura <= 2000000) {
         return false;
      }

      this.amount = Math.min(20, Mth.ceil(Math.abs(aura) / 300000.0F / ((Integer)auraAndSpots.getRight()).intValue()));
      if (this.amount <= 0) {
         return false;
      }

      this.dist = Mth.clamp(Math.abs(aura) / 150000, 5, 20);
      return true;
   }

   @Override
   public IDrainSpotEffect.ActiveType isActiveHere(Player player, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot) {
      if (!this.calcValues(player.level(), pos, spot)) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else if (player.distanceToSqr(pos.getX(), pos.getY(), pos.getZ()) > this.dist * this.dist) {
         return IDrainSpotEffect.ActiveType.INACTIVE;
      } else {
         return !NaturesAuraAPI.instance().isEffectPowderActive(player.level(), player.blockPosition(), NAME)
            ? IDrainSpotEffect.ActiveType.INHIBITED
            : IDrainSpotEffect.ActiveType.ACTIVE;
      }
   }

   @Override
   public ItemStack getDisplayIcon() {
      return new ItemStack(Blocks.DIAMOND_ORE);
   }

   @Override
   public void update(Level level, LevelChunk chunk, IAuraChunk auraChunk, BlockPos pos, Integer spot, IAuraChunk.DrainSpot actualSpot) {
      if (level.getGameTime() % 40L == 0L) {
         if (this.calcValues(level, pos, spot)) {
            IAuraType type = auraChunk.getType();
            Block requiredBlock;
            List<WeightedOre> ores;
            if (type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD)) {
               requiredBlock = Blocks.STONE;
               ores = NaturesAuraAPI.OVERWORLD_ORES;
            } else {
               requiredBlock = Blocks.NETHERRACK;
               ores = NaturesAuraAPI.NETHER_ORES;
            }

            int totalWeight = WeightedRandom.getTotalWeight(ores);
            List<Tuple<Vec3, Integer>> powders = NaturesAuraAPI.instance().getActiveEffectPowders(level, new AABB(pos).inflate(this.dist), NAME);
            if (!powders.isEmpty()) {
               label79:
               for (int i = 0; i < this.amount; i++) {
                  Tuple<Vec3, Integer> powder = powders.get(i % powders.size());
                  Vec3 powderPos = (Vec3)powder.getA();
                  int range = (Integer)powder.getB();
                  int x = Mth.floor(powderPos.x + level.random.nextGaussian() * range);
                  int y = Mth.floor(powderPos.y + level.random.nextGaussian() * range);
                  int z = Mth.floor(powderPos.z + level.random.nextGaussian() * range);
                  BlockPos orePos = new BlockPos(x, y, z);
                  if (orePos.distToCenterSqr(powderPos.x, powderPos.y, powderPos.z) <= range * range
                     && orePos.distSqr(pos) <= this.dist * this.dist
                     && level.isLoaded(orePos)) {
                     BlockState state = level.getBlockState(orePos);
                     if (state.getBlock() == requiredBlock) {
                        while (true) {
                           WeightedOre ore = (WeightedOre)WeightedRandom.getRandomItem(level.random, ores, totalWeight).orElse(null);
                           if (ore != null) {
                              TagKey<Block> tag = TagKey.create(Registries.BLOCK, ore.tag);
                              if (tag != null) {
                                 Optional<Named<Block>> values = BuiltInRegistries.BLOCK.getTag(tag);
                                 if (!values.isEmpty()) {
                                    for (Holder<Block> toPlaceHolder : values.get()) {
                                       if (toPlaceHolder != null) {
                                          Block toPlace = (Block)toPlaceHolder.value();
                                          if (toPlace != Blocks.AIR) {
                                             FakePlayer player = FakePlayerFactory.get((ServerLevel)level, ModRegistry.FAKE_PLAYER);
                                             player.setItemInHand(InteractionHand.MAIN_HAND, ItemStack.EMPTY);
                                             BlockHitResult ray = new BlockHitResult(Vec3.atCenterOf(pos), Direction.UP, pos, false);
                                             BlockPlaceContext context = new BlockPlaceContext(new UseOnContext(player, InteractionHand.MAIN_HAND, ray));
                                             BlockState stateToPlace = toPlace.getStateForPlacement(context);
                                             if (!SPAWN_EXCEPTIONS.contains(stateToPlace)) {
                                                level.setBlockAndUpdate(orePos, stateToPlace);
                                                level.levelEvent(2001, orePos, Block.getId(stateToPlace));
                                                int toDrain = (20000 - ore.getWeight().asInt() * 2) * 2;
                                                BlockPos highestSpot = IAuraChunk.getHighestSpot(level, orePos, 30, pos);
                                                IAuraChunk.getAuraChunk(level, highestSpot).drainAura(highestSpot, toDrain);
                                                continue label79;
                                             }
                                          }
                                       }
                                    }
                                 }
                              }
                              continue;
                           }
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
      return (Boolean)ModConfig.instance.oreEffect.get() && (type.isSimilar(NaturesAuraAPI.TYPE_OVERWORLD) || type.isSimilar(NaturesAuraAPI.TYPE_NETHER));
   }

   @Override
   public ResourceLocation getName() {
      return NAME;
   }
}
