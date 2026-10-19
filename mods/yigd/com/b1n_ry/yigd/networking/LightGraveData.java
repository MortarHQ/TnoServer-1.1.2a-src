package com.b1n_ry.yigd.networking;

import com.b1n_ry.yigd.data.GraveStatus;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

public record LightGraveData(int itemCount, BlockPos pos, int xpPoints, ResourceKey<Level> registryKey, Component deathMessage, UUID id, GraveStatus status) {
   public static LightGraveData fromNbt(CompoundTag nbt, Provider registryLookup) {
      if (nbt == null) {
         return new LightGraveData(0, BlockPos.ZERO, 0, Level.OVERWORLD, Component.empty(), UUID.randomUUID(), GraveStatus.CLAIMED);
      }

      int itemCount = nbt.getInt("itemCount");
      BlockPos pos = NbtUtils.readBlockPos(nbt, "pos").orElse(BlockPos.ZERO);
      int xpPoints = nbt.getInt("xpPoints");
      ResourceKey<Level> registryKey = getRegistryKeyFromNbt(nbt.getCompound("worldKey"));
      Component deathMessage = Serializer.fromJson(nbt.getString("deathMessage"), registryLookup);
      UUID id = nbt.getUUID("id");
      GraveStatus status = GraveStatus.valueOf(nbt.getString("status"));
      return new LightGraveData(itemCount, pos, xpPoints, registryKey, deathMessage, id, status);
   }

   public CompoundTag toNbt(Provider registryLookup) {
      CompoundTag nbt = new CompoundTag();
      nbt.putInt("itemCount", this.itemCount);
      nbt.put("pos", NbtUtils.writeBlockPos(this.pos));
      nbt.putInt("xpPoints", this.xpPoints);
      nbt.put("worldKey", this.getWorldRegistryKeyNbt(this.registryKey));
      nbt.putString("deathMessage", Serializer.toJson(this.deathMessage, registryLookup));
      nbt.putUUID("id", this.id);
      nbt.putString("status", this.status.toString());
      return nbt;
   }

   private CompoundTag getWorldRegistryKeyNbt(ResourceKey<?> key) {
      CompoundTag nbt = new CompoundTag();
      nbt.putString("registry", key.registry().toString());
      nbt.putString("value", key.location().toString());
      return nbt;
   }

   private static ResourceKey<Level> getRegistryKeyFromNbt(CompoundTag nbt) {
      String registry = nbt.getString("registry");
      String value = nbt.getString("value");
      ResourceKey<Registry<Level>> r = ResourceKey.createRegistryKey(ResourceLocation.parse(registry));
      return ResourceKey.create(r, ResourceLocation.parse(value));
   }
}
