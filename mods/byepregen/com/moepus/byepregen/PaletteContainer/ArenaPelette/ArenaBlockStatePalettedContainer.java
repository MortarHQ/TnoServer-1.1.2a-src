package com.moepus.byepregen.PaletteContainer.ArenaPelette;

import com.moepus.byepregen.UnsafeIntArrayAccess;
import com.moepus.byepregen.PaletteContainer.BlockStateRawIdAccess;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.NetworkWriter;
import com.moepus.byepregen.PaletteContainer.ArenaPelette.Codecs.StateImporter;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.core.IdMap;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.ZeroBitStorage;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.Palette;
import net.minecraft.world.level.chunk.PalettedContainer;
import net.minecraft.world.level.chunk.PalettedContainerRO;
import net.minecraft.world.level.chunk.SingleValuePalette;
import net.minecraft.world.level.chunk.PalettedContainer.Configuration;
import net.minecraft.world.level.chunk.PalettedContainer.CountConsumer;
import net.minecraft.world.level.chunk.PalettedContainer.Data;
import net.minecraft.world.level.chunk.PalettedContainer.Strategy;
import net.minecraft.world.level.chunk.PalettedContainerRO.PackedData;
import org.jetbrains.annotations.NotNull;

public final class ArenaBlockStatePalettedContainer extends PalettedContainer<BlockState> implements BlockStateRawIdAccess {
   private static final BlockState AIR = Blocks.AIR.defaultBlockState();
   private static final Configuration<BlockState> DUMMY_PARENT_CONFIGURATION = Strategy.SECTION_STATES.getConfiguration(Block.BLOCK_STATE_REGISTRY, 0);
   private static final Data<BlockState> DUMMY_PARENT_DATA = new Data(DUMMY_PARENT_CONFIGURATION, new ZeroBitStorage(4096), createDummyParentPalette());
   public static final int AIR_RAW_ID = Block.BLOCK_STATE_REGISTRY.getId(AIR);
   private int uniformRawId = AIR_RAW_ID;
   private BlockState uniformState = AIR;
   private int[] arena;
   private int[] denseIds;
   private Int2IntOpenHashMap denseRawIdCounts;

   public ArenaBlockStatePalettedContainer() {
      super(Block.BLOCK_STATE_REGISTRY, Strategy.SECTION_STATES, DUMMY_PARENT_DATA);
   }

   private static Palette<BlockState> createDummyParentPalette() {
      return new SingleValuePalette(Block.BLOCK_STATE_REGISTRY, null, List.of(AIR));
   }

   public void releaseRawIds() {
      this.uniformRawId = AIR_RAW_ID;
      this.uniformState = AIR;
      this.arena = null;
      this.denseIds = null;
      this.denseRawIdCounts = null;
   }

   public void importPackedPalette(BlockState[] paletteStates, long[] packedStorage) {
      StateImporter.importPackedPalette(this, paletteStates, packedStorage);
   }

   public boolean importVanillaPackedRawIds(int[] paletteRawIds, long[] packedStorage) {
      return StateImporter.importVanillaPackedRawIds(this, paletteRawIds, packedStorage);
   }

   public void read(@NotNull FriendlyByteBuf buffer) {
      StateImporter.importNetworkData(this, buffer);
   }

   @NotNull
   public BlockState get(int x, int y, int z) {
      int[] dense = this.denseIds;
      if (dense != null) {
         return Block.stateById(UnsafeIntArrayAccess.get(dense, Layout.localIndex(x, y, z)));
      }

      int[] arena = this.arena;
      if (arena == null) {
         return this.uniformState;
      }

      int page = y >>> 2;
      int local = (y & 3) << 8 | z << 4 | x;
      int base = Layout.pageBase(page);
      int paletteIndex = Layout.localPaletteIndex(arena, base, local);
      int rawId = rawIdForPaletteIndex(arena, base, paletteIndex);
      return Block.stateById(rawId);
   }

   @NotNull
   protected BlockState get(int index) {
      int[] dense = this.denseIds;
      if (dense != null) {
         return Block.stateById(UnsafeIntArrayAccess.get(dense, index));
      }

      int[] arena = this.arena;
      if (arena == null) {
         return this.uniformState;
      }

      int page = Layout.pageIndexFromSectionIndex(index);
      int local = Layout.pageLocalIndexFromSectionIndex(index);
      int base = Layout.pageBase(page);
      int paletteIndex = Layout.localPaletteIndex(arena, base, local);
      return Block.stateById(rawIdForPaletteIndex(arena, base, paletteIndex));
   }

   @NotNull
   public BlockState getAndSet(int x, int y, int z, @NotNull BlockState state) {
      return this.getAndSetUnchecked(x, y, z, state);
   }

   @NotNull
   public BlockState getAndSetUnchecked(int x, int y, int z, @NotNull BlockState state) {
      int index = Layout.localIndex(x, y, z);
      BlockState oldState = this.get(index);
      this.setRawId(index, rawId(state));
      return oldState;
   }

   public void set(int x, int y, int z, @NotNull BlockState state) {
      this.setRawId(Layout.localIndex(x, y, z), rawId(state));
   }

   public void getAll(@NotNull Consumer<BlockState> consumer) {
      ArenaBlockStateQueries.getAll(this, consumer);
   }

   public boolean maybeHas(@NotNull Predicate<BlockState> predicate) {
      return ArenaBlockStateQueries.maybeHas(this, predicate);
   }

   public void count(@NotNull CountConsumer<BlockState> consumer) {
      ArenaBlockStateQueries.count(this, consumer);
   }

   public void write(@NotNull FriendlyByteBuf buffer) {
      NetworkWriter.write(buffer, this);
   }

   public int getSerializedSize() {
      return NetworkWriter.serializedSize(this);
   }

   public @NotNull PackedData<BlockState> pack(@NotNull IdMap<BlockState> idMap, @NotNull Strategy strategy) {
      throw new UnsupportedOperationException();
   }

   @NotNull
   public PalettedContainer<BlockState> copy() {
      ArenaBlockStatePalettedContainer copy = new ArenaBlockStatePalettedContainer();
      copy.uniformRawId = this.uniformRawId;
      copy.uniformState = this.uniformState;
      copy.arena = this.arena == null ? null : (int[])this.arena.clone();
      copy.denseIds = this.denseIds == null ? null : (int[])this.denseIds.clone();
      copy.denseRawIdCounts = this.denseRawIdCounts == null ? null : new Int2IntOpenHashMap(this.denseRawIdCounts);
      return copy;
   }

   public PalettedContainerRO<BlockState> sodium$copy() {
      return this.copy();
   }

   public void sodium$unpack(Object[] values) {
      int[] dense = this.denseIds;
      if (dense != null) {
         unpackDense(values, dense);
      } else {
         int[] arena = this.arena;
         if (arena == null) {
            Arrays.fill(values, this.uniformState);
         } else {
            unpackArena(values, arena);
         }
      }
   }

   public void sodium$unpack(Object[] values, int minX, int minY, int minZ, int maxX, int maxY, int maxZ) {
      for (int y = minY; y <= maxY; y++) {
         for (int z = minZ; z <= maxZ; z++) {
            for (int x = minX; x <= maxX; x++) {
               int index = Layout.localIndex(x, y, z);
               values[index] = Block.stateById(this.rawIdAt(index));
            }
         }
      }
   }

   @NotNull
   public PalettedContainer<BlockState> recreate() {
      return new ArenaBlockStatePalettedContainer();
   }

   public int rawIdAt(int index) {
      int[] dense = this.denseIds;
      if (dense != null) {
         return UnsafeIntArrayAccess.get(dense, index);
      }

      int[] arena = this.arena;
      if (arena == null) {
         return this.uniformRawId;
      }

      int page = Layout.pageIndexFromSectionIndex(index);
      int local = Layout.pageLocalIndexFromSectionIndex(index);
      int base = Layout.pageBase(page);
      int paletteIndex = Layout.localPaletteIndex(arena, base, local);
      return rawIdForPaletteIndex(arena, base, paletteIndex);
   }

   @Override
   public int getRawId(int x, int y, int z) {
      return this.rawIdAt(Layout.localIndex(x, y, z));
   }

   public boolean isUniform() {
      return this.arena == null && this.denseIds == null;
   }

   public int uniformRawId() {
      return this.uniformRawId;
   }

   public boolean hasPagePalettes() {
      return this.arena != null && this.denseIds == null;
   }

   public boolean hasDenseIds() {
      return this.denseIds != null;
   }

   public Int2IntOpenHashMap denseRawIdCounts() {
      Int2IntOpenHashMap counts = this.denseRawIdCounts;
      if (counts != null) {
         return counts;
      }

      int[] dense = this.denseIds;
      if (dense == null) {
         return null;
      }

      counts = new Int2IntOpenHashMap();

      for (int i = 0; i < 4096; i++) {
         counts.addTo(UnsafeIntArrayAccess.get(dense, i), 1);
      }

      this.denseRawIdCounts = counts;
      return counts;
   }

   public int arenaPageBase(int page) {
      return Layout.pageBase(page);
   }

   public int arenaLivePaletteMask(int base) {
      return Layout.pageLivePaletteMask(this.arena, base);
   }

   public int arenaPaletteRawId(int base, int paletteIndex) {
      return rawIdForPaletteIndex(this.arena, base, paletteIndex);
   }

   public int arenaPaletteWord(int base, int wordIndex) {
      return this.arena[base + wordIndex];
   }

   public void forEachRawId(ArenaBlockStateQueries.RawIdConsumer consumer) {
      ArenaBlockStateQueries.forEachRawId(this, consumer);
   }

   public void setRawId(int index, int rawId) {
      if (rawId < 0) {
         rawId = AIR_RAW_ID;
      }

      int[] dense = this.denseIds;
      if (dense != null) {
         int oldRawId = UnsafeIntArrayAccess.get(dense, index);
         if (oldRawId != rawId) {
            UnsafeIntArrayAccess.set(dense, index, rawId);
            this.updateDenseRawIdCounts(oldRawId, rawId);
         }
      } else {
         int[] arena = this.arena;
         if (arena == null) {
            if (rawId == this.uniformRawId) {
               return;
            }

            arena = this.ensureArena();
         }

         int page = Layout.pageIndexFromSectionIndex(index);
         int local = Layout.pageLocalIndexFromSectionIndex(index);
         int base = Layout.pageBase(page);
         int oldPaletteIndex = Layout.localPaletteIndex(arena, base, local);
         int oldRawId = rawIdForPaletteIndex(arena, base, oldPaletteIndex);
         if (oldRawId != rawId) {
            int paletteIndex = this.findPaletteIndex(base, rawId);
            if (paletteIndex < 0) {
               paletteIndex = this.appendPaletteIndex(base, rawId);
            }

            if (paletteIndex < 0) {
               if (!this.tryReuseDeadPaletteSlotForWrite(base, local, oldPaletteIndex, rawId)) {
                  this.promoteToDense();
                  UnsafeIntArrayAccess.set(this.denseIds, index, rawId);
               }
            } else {
               Layout.setLocalPaletteIndex(arena, base, local, paletteIndex);
            }
         }
      }
   }

   public void c2me$setUnsafe(int x, int y, int z, Object state) {
      this.setRawId(Layout.localIndex(x, y, z), rawId((BlockState)state));
   }

   public int[] ensureArena() {
      int[] arena = this.arena;
      if (arena != null) {
         return arena;
      }

      arena = new int[576];

      for (int page = 0; page < 4; page++) {
         arena[Layout.pageBase(page) + 128] = this.uniformRawId;
      }

      this.arena = arena;
      return arena;
   }

   public void promoteToDense() {
      if (this.denseIds == null) {
         int[] dense = new int[4096];
         int[] arena = this.arena;
         if (arena == null) {
            Arrays.fill(dense, this.uniformRawId);
         } else {
            for (int page = 0; page < 4; page++) {
               int base = Layout.pageBase(page);

               for (int local = 0; local < 1024; local++) {
                  int paletteIndex = Layout.localPaletteIndex(arena, base, local);
                  dense[Layout.sectionIndex(page, local)] = rawIdForPaletteIndex(arena, base, paletteIndex);
               }
            }
         }

         this.denseIds = dense;
         this.arena = null;
         this.denseRawIdCounts = null;
      }
   }

   private boolean tryReuseDeadPaletteSlotForWrite(int base, int local, int oldPaletteIndex, int newRawId) {
      int[] arena = this.arena;
      int seen = 0;
      int twice = 0;

      for (int wordIndex = 0; wordIndex < 128; wordIndex++) {
         int word = arena[base + wordIndex];
         int bit = 1 << (word & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 4 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 8 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 12 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 16 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 20 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 24 & 15);
         twice |= seen & bit;
         seen |= bit;
         bit = 1 << (word >>> 28 & 15);
         twice |= seen & bit;
         seen |= bit;
      }

      int oldBit = 1 << oldPaletteIndex;
      boolean oldDiesAfterWrite = (seen & oldBit) != 0 && (twice & oldBit) == 0;
      int newPaletteIndex;
      if (oldDiesAfterWrite) {
         newPaletteIndex = oldPaletteIndex;
      } else {
         int deadMask = ~seen & 65535;
         int extraDead = deadMask & 65534;
         if (extraDead != 0) {
            newPaletteIndex = Integer.numberOfTrailingZeros(extraDead);
         } else {
            if ((deadMask & 1) == 0) {
               return false;
            }

            newPaletteIndex = 0;
         }
      }

      if (newPaletteIndex == 0) {
         arena[base + 128] = newRawId;
      } else {
         arena[base + 129 + newPaletteIndex - 1] = newRawId + 1;
      }

      Layout.setLocalPaletteIndex(arena, base, local, newPaletteIndex);
      return true;
   }

   private static int rawIdForPaletteIndex(int[] arena, int base, int paletteIndex) {
      return arena[base + 128 + paletteIndex] - (paletteIndex + 16 - 1 >>> 4);
   }

   private static void unpackDense(Object[] values, int[] dense) {
      for (int i = 0; i < 4096; i++) {
         values[i] = Block.stateById(UnsafeIntArrayAccess.get(dense, i));
      }
   }

   private static void unpackArena(Object[] values, int[] arena) {
      for (int page = 0; page < 4; page++) {
         int base = Layout.pageBase(page);

         for (int local = 0; local < 1024; local++) {
            int paletteIndex = Layout.localPaletteIndex(arena, base, local);
            int rawId = rawIdForPaletteIndex(arena, base, paletteIndex);
            values[Layout.sectionIndex(page, local)] = Block.stateById(rawId);
         }
      }
   }

   private int findPaletteIndex(int base, int rawId) {
      if (rawId == this.arena[base + 128]) {
         return 0;
      }

      int stored = rawId + 1;

      for (int i = 0; i < 15; i++) {
         if (this.arena[base + 129 + i] == stored) {
            return i + 1;
         }
      }

      return -1;
   }

   private int appendPaletteIndex(int base, int rawId) {
      int stored = rawId + 1;

      for (int i = 0; i < 15; i++) {
         if (this.arena[base + 129 + i] == 0) {
            this.arena[base + 129 + i] = stored;
            return i + 1;
         }
      }

      return -1;
   }

   public void tryPromoteFullUniformSection() {
      int[] arena = this.arena;
      if (arena != null && this.denseIds == null) {
         int rawId = arena[128];

         for (int page = 0; page < 4; page++) {
            int base = Layout.pageBase(page);
            if (arena[base + 128] != rawId) {
               return;
            }

            for (int wordIndex = 0; wordIndex < 128; wordIndex++) {
               if (arena[base + wordIndex] != 0) {
                  return;
               }
            }
         }

         this.setUniformSection(rawId);
      }
   }

   public void setUniformSection(int rawId) {
      this.uniformRawId = rawId;
      this.uniformState = Block.stateById(rawId);
      this.arena = null;
      this.denseIds = null;
      this.denseRawIdCounts = null;
   }

   private void updateDenseRawIdCounts(int oldRawId, int newRawId) {
      Int2IntOpenHashMap counts = this.denseRawIdCounts;
      if (counts != null) {
         int oldCount = counts.get(oldRawId);
         if (oldCount <= 1) {
            counts.remove(oldRawId);
         } else {
            counts.put(oldRawId, oldCount - 1);
         }

         counts.addTo(newRawId, 1);
      }
   }

   public boolean isUniformRawId(int rawId) {
      return this.arena == null && rawId == this.uniformRawId;
   }

   public static int rawId(BlockState state) {
      int id = state == null ? AIR_RAW_ID : Block.BLOCK_STATE_REGISTRY.getId(state);
      return id < 0 ? AIR_RAW_ID : id;
   }
}
