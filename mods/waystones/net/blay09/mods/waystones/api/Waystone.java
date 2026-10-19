package net.blay09.mods.waystones.api;

import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public interface Waystone {
   UUID getWaystoneUid();

   Component getName();

   ResourceKey<Level> getDimension();

   default boolean wasGenerated() {
      return this.getOrigin() == WaystoneOrigin.VILLAGE || this.getOrigin() == WaystoneOrigin.WILDERNESS || this.getOrigin() == WaystoneOrigin.DUNGEON;
   }

   WaystoneOrigin getOrigin();

   boolean isOwner(Player var1);

   BlockPos getPos();

   boolean isValid();

   @Nullable
   UUID getOwnerUid();

   ResourceLocation getWaystoneType();

   default boolean hasName() {
      return !this.getName().getString().isEmpty();
   }

   default boolean hasOwner() {
      return this.getOwnerUid() != null;
   }

   default boolean isValidInLevel(ServerLevel level) {
      return false;
   }

   boolean isTransient();

   WaystoneVisibility getVisibility();
}
