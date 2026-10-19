package de.ellpeck.naturesaura.chunk;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;
import de.ellpeck.naturesaura.ModConfig;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.chunk.IDrainSpotEffect;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.api.misc.ILevelData;
import de.ellpeck.naturesaura.misc.LevelData;
import de.ellpeck.naturesaura.packet.PacketAuraChunk;
import de.ellpeck.naturesaura.packet.PacketHandler;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import org.apache.commons.lang3.mutable.MutableInt;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.tuple.Pair;

public class AuraChunk implements IAuraChunk {
   private final Map<BlockPos, IAuraChunk.DrainSpot> drainSpots = new ConcurrentHashMap<>();
   private final Table<BlockPos, Integer, Pair<Integer, Integer>> auraAndSpotAmountCache = HashBasedTable.create();
   private final Table<BlockPos, Integer, Pair<BlockPos, Integer>[]> limitSpotCache = HashBasedTable.create();
   private final List<IDrainSpotEffect> effects = new ArrayList<>();
   private final LevelChunk chunk;
   private final IAuraType type;
   private boolean needsSync;

   public AuraChunk(LevelChunk chunk) {
      this.chunk = chunk;
      this.type = IAuraType.forLevel(chunk.getLevel());

      for (Supplier<IDrainSpotEffect> supplier : NaturesAuraAPI.DRAIN_SPOT_EFFECTS.values()) {
         IDrainSpotEffect effect = supplier.get();
         if (effect.appliesHere(this.chunk, this, this.type)) {
            this.effects.add(effect);
         }
      }
   }

   @Override
   public int drainAura(BlockPos pos, int amount, boolean aimForZero, boolean simulate) {
      if (amount <= 0) {
         return 0;
      }

      IAuraChunk.DrainSpot spot = this.getActualDrainSpot(pos, !simulate);
      int curr = spot != null ? spot.intValue() : 0;
      if (curr < 0 && curr - amount > 0) {
         return this.drainAura(pos.above(), amount, aimForZero, simulate);
      }

      if (aimForZero && curr > 0 && curr - amount < 0) {
         amount = curr;
      }

      if (!simulate) {
         spot.subtract(amount);
         if (spot.intValue() == 0) {
            this.drainSpots.remove(pos);
         }

         this.markDirty();
      }

      return amount;
   }

   @Override
   public int drainAura(BlockPos pos, int amount) {
      return this.drainAura(pos, amount, false, false);
   }

   @Override
   public int storeAura(BlockPos pos, int amount, boolean aimForZero, boolean simulate) {
      if (amount <= 0) {
         return 0;
      }

      IAuraChunk.DrainSpot spot = this.getActualDrainSpot(pos, !simulate);
      int curr = spot != null ? spot.intValue() : 0;
      if (curr > 0 && curr + amount < 0) {
         return this.storeAura(pos.above(), amount, aimForZero, simulate);
      }

      if (aimForZero && curr < 0 && curr + amount > 0) {
         amount = -curr;
      }

      if (!simulate) {
         spot.add(amount);
         if (spot.intValue() == 0) {
            this.drainSpots.remove(pos);
         }

         this.markDirty();
      }

      return amount;
   }

   @Override
   public int storeAura(BlockPos pos, int amount) {
      return this.storeAura(pos, amount, true, false);
   }

   @Override
   public IAuraChunk.DrainSpot getActualDrainSpot(BlockPos pos, boolean make) {
      IAuraChunk.DrainSpot spot = this.drainSpots.get(pos);
      if (spot == null && make) {
         spot = new IAuraChunk.DrainSpot(pos, 0);
         this.addDrainSpot(spot);
      }

      return spot;
   }

   @Override
   public int getDrainSpot(BlockPos pos) {
      IAuraChunk.DrainSpot spot = this.getActualDrainSpot(pos, false);
      return spot == null ? 0 : spot.intValue();
   }

   private void addDrainSpot(IAuraChunk.DrainSpot spot) {
      int expX = spot.pos.getX() >> 4;
      int expZ = spot.pos.getZ() >> 4;
      ChunkPos myPos = this.chunk.getPos();
      if (expX == myPos.x && expZ == myPos.z) {
         this.drainSpots.put(spot.pos, spot);
      } else {
         throw new IllegalArgumentException(
            "Tried to add drain spot "
               + spot.pos
               + " to chunk at "
               + myPos.x
               + ", "
               + myPos.z
               + " when it should've been added to chunk at "
               + expX
               + ", "
               + expZ
         );
      }
   }

   public void setSpots(Collection<CompoundTag> spots) {
      this.drainSpots.clear();

      for (CompoundTag spot : spots) {
         this.addDrainSpot(new IAuraChunk.DrainSpot(spot));
      }

      this.addOrRemoveAsActive();
   }

   @Override
   public IAuraType getType() {
      return this.type;
   }

   @Override
   public void markDirty() {
      this.chunk.setUnsaved(true);
      this.needsSync = true;
      this.auraAndSpotAmountCache.clear();
      this.limitSpotCache.clear();
      this.addOrRemoveAsActive();
   }

   public void update() {
      Level level = this.chunk.getLevel();

      for (IAuraChunk.DrainSpot spot : this.drainSpots.values()) {
         for (IDrainSpotEffect effect : this.effects) {
            effect.update(level, this.chunk, this, spot.pos, spot.intValue(), spot);
         }

         if (spot.intValue() > 0
            && spot.originalSpreadPos != null
            && !spot.originalSpreadPos.closerThan(spot.pos, ((Integer)ModConfig.instance.maxAuraSpreadRange.get()).intValue())) {
            this.drainAura(spot.pos, spot.intValue());
         }
      }

      if (this.needsSync) {
         ChunkPos pos = this.chunk.getPos();
         PacketHandler.sendToAllLoaded(level, new BlockPos(pos.x * 16, 0, pos.z * 16), this.makePacket());
         this.needsSync = false;
      }
   }

   public PacketAuraChunk makePacket() {
      ChunkPos pos = this.chunk.getPos();
      return new PacketAuraChunk(pos.x, pos.z, this.drainSpots.values().stream().map(IAuraChunk.DrainSpot::serializeNBT).toList());
   }

   public void getSpots(BlockPos pos, int radius, BiConsumer<BlockPos, Integer> consumer) {
      for (Entry<BlockPos, IAuraChunk.DrainSpot> entry : this.drainSpots.entrySet()) {
         BlockPos drainPos = entry.getKey();
         if (drainPos.distSqr(pos) <= radius * radius) {
            consumer.accept(drainPos, entry.getValue().intValue());
         }
      }
   }

   public Pair<Integer, Integer> getAuraAndSpotAmount(BlockPos pos, int radius) {
      Pair<Integer, Integer> ret = (Pair<Integer, Integer>)this.auraAndSpotAmountCache.get(pos, radius);
      if (ret == null) {
         MutableInt aura = new MutableInt();
         MutableInt spots = new MutableInt();
         this.getSpots(pos, radius, (p, i) -> {
            aura.add(i);
            spots.increment();
         });
         ret = Pair.of(aura.intValue(), spots.intValue());
         this.auraAndSpotAmountCache.put(pos, radius, ret);
      }

      return ret;
   }

   public Pair<BlockPos, Integer>[] getLowestAndHighestSpots(BlockPos pos, int radius) {
      Pair<BlockPos, Integer>[] ret = (Pair<BlockPos, Integer>[])this.limitSpotCache.get(pos, radius);
      if (ret == null) {
         MutableObject<BlockPos> lowestSpot = new MutableObject();
         MutableObject<BlockPos> highestSpot = new MutableObject();
         MutableInt lowestAmount = new MutableInt(Integer.MAX_VALUE);
         MutableInt highestAmount = new MutableInt(Integer.MIN_VALUE);
         this.getSpots(pos, radius, (p, i) -> {
            if (i > highestAmount.intValue()) {
               highestAmount.setValue(i);
               highestSpot.setValue(p);
            }

            if (i < lowestAmount.intValue()) {
               lowestAmount.setValue(i);
               lowestSpot.setValue(p);
            }
         });
         ret = new Pair[]{
            Pair.of((BlockPos)lowestSpot.getValue(), lowestAmount.intValue()), Pair.of((BlockPos)highestSpot.getValue(), highestAmount.intValue())
         };
         this.limitSpotCache.put(pos, radius, ret);
      }

      return ret;
   }

   public void getActiveEffectIcons(Player player, Map<ResourceLocation, Tuple<ItemStack, Boolean>> icons) {
      for (IDrainSpotEffect effect : this.effects) {
         Tuple<ItemStack, Boolean> alreadyThere = icons.get(effect.getName());
         if (alreadyThere == null || !(Boolean)alreadyThere.getB()) {
            for (Entry<BlockPos, IAuraChunk.DrainSpot> entry : this.drainSpots.entrySet()) {
               BlockPos pos = entry.getKey();
               IAuraChunk.DrainSpot amount = entry.getValue();
               IDrainSpotEffect.ActiveType state = effect.isActiveHere(player, this.chunk, this, pos, amount.intValue());
               if (state != IDrainSpotEffect.ActiveType.INACTIVE) {
                  ItemStack stack = effect.getDisplayIcon();
                  if (!stack.isEmpty()) {
                     icons.put(effect.getName(), new Tuple(stack, state == IDrainSpotEffect.ActiveType.INHIBITED));
                  }
               }
            }
         }
      }
   }

   public CompoundTag serializeNBT(Provider registries) {
      ListTag list = new ListTag();

      for (IAuraChunk.DrainSpot spot : this.drainSpots.values()) {
         list.add(spot.serializeNBT());
      }

      CompoundTag compound = new CompoundTag();
      compound.put("drain_spots", list);
      return compound;
   }

   public void deserializeNBT(Provider registries, CompoundTag compound) {
      this.drainSpots.clear();

      for (Tag base : compound.getList("drain_spots", 10)) {
         this.addDrainSpot(new IAuraChunk.DrainSpot((CompoundTag)base));
      }

      this.addOrRemoveAsActive();
   }

   private void addOrRemoveAsActive() {
      long chunkPos = this.chunk.getPos().toLong();
      LevelData data = (LevelData)ILevelData.getLevelData(this.chunk.getLevel());
      if (!this.drainSpots.isEmpty()) {
         data.auraChunksWithSpots.put(chunkPos, this);
      } else {
         data.auraChunksWithSpots.remove(chunkPos);
      }
   }
}
