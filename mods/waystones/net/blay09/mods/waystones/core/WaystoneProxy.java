package net.blay09.mods.waystones.core;

import java.util.UUID;
import net.blay09.mods.waystones.api.MutableWaystone;
import net.blay09.mods.waystones.api.Waystone;
import net.blay09.mods.waystones.api.WaystoneOrigin;
import net.blay09.mods.waystones.api.WaystoneVisibility;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public class WaystoneProxy implements Waystone, MutableWaystone {
   private final MinecraftServer server;
   private final UUID waystoneUid;
   private Waystone backingWaystone;

   public WaystoneProxy(@Nullable MinecraftServer server, UUID waystoneUid) {
      this.server = server;
      this.waystoneUid = waystoneUid;
   }

   @Override
   public boolean isValidInLevel(ServerLevel level) {
      return this.getBackingWaystone().isValidInLevel(level);
   }

   @Override
   public boolean isTransient() {
      return this.getBackingWaystone().isTransient();
   }

   @Override
   public boolean isValid() {
      return WaystoneManagerImpl.get(this.server).getWaystoneById(this.waystoneUid).isPresent();
   }

   public Waystone getBackingWaystone() {
      if (this.backingWaystone == null) {
         this.backingWaystone = WaystoneManagerImpl.get(this.server).getWaystoneById(this.waystoneUid).orElse(InvalidWaystone.INSTANCE);
      }

      return this.backingWaystone;
   }

   @Override
   public UUID getOwnerUid() {
      return this.getBackingWaystone().getOwnerUid();
   }

   @Override
   public UUID getWaystoneUid() {
      return this.waystoneUid;
   }

   @Override
   public Component getName() {
      return this.getBackingWaystone().getName();
   }

   @Override
   public ResourceKey<Level> getDimension() {
      return this.getBackingWaystone().getDimension();
   }

   @Override
   public boolean wasGenerated() {
      return this.getBackingWaystone().wasGenerated();
   }

   @Override
   public WaystoneOrigin getOrigin() {
      return this.getBackingWaystone().getOrigin();
   }

   @Override
   public boolean isOwner(Player player) {
      return this.getBackingWaystone().isOwner(player);
   }

   @Override
   public BlockPos getPos() {
      return this.getBackingWaystone().getPos();
   }

   @Override
   public ResourceLocation getWaystoneType() {
      return this.getBackingWaystone().getWaystoneType();
   }

   @Override
   public void setName(Component name) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setName(name);
      }
   }

   @Override
   public void setDimension(ResourceKey<Level> dimension) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setDimension(dimension);
      }
   }

   @Override
   public void setPos(BlockPos pos) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setPos(pos);
      }
   }

   @Override
   public void setOwnerUid(UUID ownerUid) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setOwnerUid(ownerUid);
      }
   }

   @Override
   public void setTransient(boolean isTransient) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setTransient(isTransient);
      }
   }

   @Override
   public WaystoneVisibility getVisibility() {
      return this.getBackingWaystone().getVisibility();
   }

   @Override
   public void setVisibility(WaystoneVisibility visibility) {
      Waystone backingWaystone = this.getBackingWaystone();
      if (backingWaystone instanceof MutableWaystone) {
         ((MutableWaystone)backingWaystone).setVisibility(visibility);
      }
   }
}
