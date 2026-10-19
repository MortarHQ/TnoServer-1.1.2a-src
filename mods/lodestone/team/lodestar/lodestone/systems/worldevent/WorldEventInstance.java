package team.lodestar.lodestone.systems.worldevent;

import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;
import team.lodestar.lodestone.network.worldevent.SyncWorldEventPayload;
import team.lodestar.lodestone.registry.common.LodestoneWorldEventTypes;

public abstract class WorldEventInstance {
   public UUID uuid;
   public WorldEventType type;
   public Level level;
   public boolean discarded;
   public boolean dirty;
   public boolean frozen;

   public WorldEventInstance(WorldEventType type) {
      if (type == null) {
         throw new IllegalArgumentException("World event type cannot be null");
      }

      this.uuid = UUID.randomUUID();
      this.type = type;
   }

   public void start(Level level) {
      this.level = level;
   }

   public abstract void tick(Level var1);

   protected abstract void addAdditionalSaveData(CompoundTag var1);

   protected abstract void readAdditionalSaveData(CompoundTag var1);

   public void end(Level level) {
      this.discarded = true;
   }

   public void setDirty() {
      this.dirty = true;
   }

   public boolean isFrozen() {
      return this.frozen;
   }

   public Level getLevel() {
      return this.level;
   }

   @Internal
   public final CompoundTag serializeNBT() {
      CompoundTag tag = new CompoundTag();
      tag.putUUID("uuid", this.uuid);
      tag.putString("type", this.type.id.toString());
      tag.putBoolean("discarded", this.discarded);
      tag.putBoolean("frozen", this.frozen);
      this.addAdditionalSaveData(tag);
      return tag;
   }

   @Internal
   public final WorldEventInstance deserializeNBT(CompoundTag tag) {
      this.uuid = tag.getUUID("uuid");
      this.type = (WorldEventType)LodestoneWorldEventTypes.WORLD_EVENT_TYPE_REGISTRY.get(ResourceLocation.parse(tag.getString("type")));
      this.discarded = tag.getBoolean("discarded");
      this.frozen = tag.getBoolean("frozen");
      this.readAdditionalSaveData(tag);
      return this;
   }

   @Internal
   public void sync(Level level) {
      if (!level.isClientSide && this.type.isClientSynced()) {
         sync(this);
      }
   }

   @Internal
   public CompoundTag synchronizeNBT() {
      return this.serializeNBT();
   }

   @Internal
   public static <T extends WorldEventInstance> void sync(T instance) {
      sync(instance, null);
   }

   @Internal
   public static <T extends WorldEventInstance> void sync(T instance, @Nullable ServerPlayer player) {
      if (player != null) {
         PacketDistributor.sendToPlayer(player, new SyncWorldEventPayload(instance, false), new CustomPacketPayload[0]);
      } else {
         PacketDistributor.sendToAllPlayers(new SyncWorldEventPayload(instance, false), new CustomPacketPayload[0]);
      }
   }
}
