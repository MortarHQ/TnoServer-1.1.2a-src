package de.ellpeck.naturesaura.api.aura.chunk;

import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import java.util.function.BiConsumer;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.tuple.Pair;

public interface IAuraChunk extends INBTSerializable<CompoundTag> {
   int DEFAULT_AURA = 1000000;

   static IAuraChunk getAuraChunk(Level level, BlockPos pos) {
      LevelChunk chunk = (LevelChunk)level.getChunk(pos);
      return (IAuraChunk)chunk.getData(NaturesAuraAPI.AURA_CHUNK_ATTACHMENT);
   }

   static void getSpotsInArea(Level level, BlockPos pos, int radius, BiConsumer<BlockPos, Integer> consumer) {
      NaturesAuraAPI.instance().getAuraSpotsInArea(level, pos, radius, consumer);
   }

   static int getSpotAmountInArea(Level level, BlockPos pos, int radius) {
      return NaturesAuraAPI.instance().getSpotAmountInArea(level, pos, radius);
   }

   static int getAuraInArea(Level level, BlockPos pos, int radius) {
      return NaturesAuraAPI.instance().getAuraInArea(level, pos, radius);
   }

   static Pair<Integer, Integer> getAuraAndSpotAmountInArea(Level level, BlockPos pos, int radius) {
      return NaturesAuraAPI.instance().getAuraAndSpotAmountInArea(level, pos, radius);
   }

   static int triangulateAuraInArea(Level level, BlockPos pos, int radius) {
      return NaturesAuraAPI.instance().triangulateAuraInArea(level, pos, radius);
   }

   static BlockPos getLowestSpot(Level level, BlockPos pos, int radius, BlockPos defaultSpot) {
      return NaturesAuraAPI.instance().getLowestAuraDrainSpot(level, pos, radius, defaultSpot);
   }

   static BlockPos getHighestSpot(Level level, BlockPos pos, int radius, BlockPos defaultSpot) {
      return NaturesAuraAPI.instance().getHighestAuraDrainSpot(level, pos, radius, defaultSpot);
   }

   int drainAura(BlockPos var1, int var2, boolean var3, boolean var4);

   int drainAura(BlockPos var1, int var2);

   int storeAura(BlockPos var1, int var2, boolean var3, boolean var4);

   int storeAura(BlockPos var1, int var2);

   IAuraChunk.DrainSpot getActualDrainSpot(BlockPos var1, boolean var2);

   int getDrainSpot(BlockPos var1);

   IAuraType getType();

   void markDirty();

   class DrainSpot extends MutableInt {
      public final BlockPos pos;
      public BlockPos originalSpreadPos;

      public DrainSpot(BlockPos pos, int value) {
         super(value);
         this.pos = pos;
      }

      public DrainSpot(CompoundTag tag) {
         this(BlockPos.of(tag.getLong("pos")), tag.getInt("amount"));
         if (tag.contains("original_spread_pos")) {
            this.originalSpreadPos = BlockPos.of(tag.getLong("original_spread_pos"));
         }
      }

      public CompoundTag serializeNBT() {
         CompoundTag ret = new CompoundTag();
         ret.putLong("pos", this.pos.asLong());
         ret.putInt("amount", this.intValue());
         if (this.originalSpreadPos != null) {
            ret.putLong("original_spread_pos", this.originalSpreadPos.asLong());
         }

         return ret;
      }
   }
}
