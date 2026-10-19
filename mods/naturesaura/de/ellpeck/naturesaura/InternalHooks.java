package de.ellpeck.naturesaura;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.container.IAuraContainer;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import de.ellpeck.naturesaura.blocks.multi.Multiblock;
import de.ellpeck.naturesaura.chunk.AuraChunk;
import de.ellpeck.naturesaura.misc.LevelData;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.mutable.MutableFloat;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.tuple.Pair;

public class InternalHooks implements NaturesAuraAPI.IInternalHooks {
   @Override
   public boolean extractAuraFromPlayer(Player player, int amount, boolean simulate) {
      return this.auraPlayerInteraction(player, amount, true, simulate);
   }

   @Override
   public boolean insertAuraIntoPlayer(Player player, int amount, boolean simulate) {
      return this.auraPlayerInteraction(player, amount, false, simulate);
   }

   private boolean auraPlayerInteraction(Player player, int amount, boolean extract, boolean simulate) {
      if (extract && player.isCreative()) {
         return true;
      } else {
         ItemStack stack = Helper.getEquippedItem(s -> s.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY) != null, player, false);
         if (!stack.isEmpty()) {
            IAuraContainer container = (IAuraContainer)stack.getCapability(NaturesAuraAPI.AURA_CONTAINER_ITEM_CAPABILITY);
            return extract ? container.drainAura(amount, simulate) > 0 : container.storeAura(amount, simulate) > 0;
         } else {
            return false;
         }
      }
   }

   @Override
   public void spawnMagicParticle(
      double posX,
      double posY,
      double posZ,
      double motionX,
      double motionY,
      double motionZ,
      int color,
      float scale,
      int maxAge,
      float gravity,
      boolean collision,
      boolean fade
   ) {
      NaturesAura.proxy.spawnMagicParticle(posX, posY, posZ, motionX, motionY, motionZ, color, scale, maxAge, gravity, collision, fade);
   }

   @Override
   public void spawnParticleStream(float startX, float startY, float startZ, float endX, float endY, float endZ, float speed, int color, float scale) {
      Vec3 dir = new Vec3(endX - startX, endY - startY, endZ - startZ);
      double length = dir.length();
      if (length > 0.0) {
         dir = dir.normalize();
         this.spawnMagicParticle(startX, startY, startZ, dir.x * speed, dir.y * speed, dir.z * speed, color, scale, (int)(length / speed), 0.0F, false, false);
      }
   }

   @Override
   public void setParticleDepth(boolean depth) {
      NaturesAura.proxy.setParticleDepth(depth);
   }

   @Override
   public void setParticleSpawnRange(int range) {
      NaturesAura.proxy.setParticleSpawnRange(range);
   }

   @Override
   public void setParticleCulling(boolean cull) {
      NaturesAura.proxy.setParticleCulling(cull);
   }

   @Override
   public IMultiblock createMultiblock(ResourceLocation name, String[][] pattern, Object... rawMatchers) {
      return new Multiblock(name, pattern, rawMatchers);
   }

   @Override
   public List<Tuple<Vec3, Integer>> getActiveEffectPowders(Level level, AABB area, ResourceLocation name) {
      List<Tuple<Vec3, Integer>> found = new ArrayList<>();

      for (Tuple<Vec3, Integer> powder : ((LevelData)ILevelData.getLevelData(level)).effectPowders.get(name)) {
         if (area.contains((Vec3)powder.getA())) {
            found.add(powder);
         }
      }

      return found;
   }

   @Override
   public boolean isEffectPowderActive(Level level, BlockPos pos, ResourceLocation name) {
      Vec3 posVec = new Vec3(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5);

      for (Tuple<Vec3, Integer> powder : this.getActiveEffectPowders(level, new AABB(pos).inflate(64.0), name)) {
         AABB bounds = Helper.aabb((Vec3)powder.getA()).inflate(((Integer)powder.getB()).intValue());
         if (bounds.contains(posVec)) {
            return true;
         }
      }

      return false;
   }

   @Override
   public void getAuraSpotsInArea(Level level, BlockPos pos, int radius, BiConsumer<BlockPos, Integer> consumer) {
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, chunk -> chunk.getSpots(pos, radius, consumer));
   }

   @Override
   public int getSpotAmountInArea(Level level, BlockPos pos, int radius) {
      MutableInt result = new MutableInt();
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, chunk -> result.add((Number)chunk.getAuraAndSpotAmount(pos, radius).getRight()));
      return result.intValue();
   }

   @Override
   public int getAuraInArea(Level level, BlockPos pos, int radius) {
      MutableInt result = new MutableInt(1000000);
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, chunk -> result.add((Number)chunk.getAuraAndSpotAmount(pos, radius).getLeft()));
      return result.intValue();
   }

   @Override
   public Pair<Integer, Integer> getAuraAndSpotAmountInArea(Level level, BlockPos pos, int radius) {
      MutableInt spots = new MutableInt();
      MutableInt aura = new MutableInt(1000000);
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, chunk -> {
         Pair<Integer, Integer> auraAndSpots = chunk.getAuraAndSpotAmount(pos, radius);
         aura.add((Number)auraAndSpots.getLeft());
         spots.add((Number)auraAndSpots.getRight());
      });
      return Pair.of(aura.intValue(), spots.intValue());
   }

   @Override
   public int triangulateAuraInArea(Level level, BlockPos pos, int radius) {
      MutableFloat result = new MutableFloat(1000000.0F);
      IAuraChunk.getSpotsInArea(level, pos, radius, (blockPos, spot) -> {
         float percentage = 1.0F - (float)Math.sqrt(pos.distSqr(blockPos)) / radius;
         result.add(spot.intValue() * percentage);
      });
      return result.intValue();
   }

   @Override
   public BlockPos getLowestAuraDrainSpot(Level level, BlockPos pos, int radius, BlockPos defaultSpot) {
      MutableInt lowestAmount = new MutableInt(Integer.MAX_VALUE);
      MutableObject<BlockPos> lowestSpot = new MutableObject();
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, c -> {
         Pair<BlockPos, Integer> spot = c.getLowestAndHighestSpots(pos, radius)[0];
         if ((Integer)spot.getRight() < lowestAmount.intValue()) {
            lowestAmount.setValue((Number)spot.getRight());
            lowestSpot.setValue((BlockPos)spot.getLeft());
         }
      });
      BlockPos lowest = (BlockPos)lowestSpot.getValue();
      if (lowest == null || lowestAmount.intValue() >= 0) {
         lowest = defaultSpot;
      }

      return lowest;
   }

   @Override
   public BlockPos getHighestAuraDrainSpot(Level level, BlockPos pos, int radius, BlockPos defaultSpot) {
      MutableInt highestAmount = new MutableInt(Integer.MIN_VALUE);
      MutableObject<BlockPos> highestSpot = new MutableObject();
      Helper.getAuraChunksWithSpotsInArea(level, pos, radius, c -> {
         Pair<BlockPos, Integer> spot = c.getLowestAndHighestSpots(pos, radius)[1];
         if ((Integer)spot.getRight() > highestAmount.intValue()) {
            highestAmount.setValue((Number)spot.getRight());
            highestSpot.setValue((BlockPos)spot.getLeft());
         }
      });
      BlockPos highest = (BlockPos)highestSpot.getValue();
      if (highest == null || highestAmount.intValue() <= 0) {
         highest = defaultSpot;
      }

      return highest;
   }

   @Override
   public ILevelData getLevelData(Level level) {
      if (level instanceof ServerLevel server) {
         LevelData ret = (LevelData)server.getDataStorage().computeIfAbsent(LevelData.FACTORY, "level_data");
         if (ret.level == null) {
            ret.level = level;
         }

         return ret;
      } else {
         if (LevelData.client == null || LevelData.client.level != level) {
            LevelData.client = new LevelData();
            LevelData.client.level = level;
         }

         return LevelData.client;
      }
   }

   @Override
   public IAuraChunk createAuraChunk(LevelChunk chunk) {
      return new AuraChunk(chunk);
   }
}
