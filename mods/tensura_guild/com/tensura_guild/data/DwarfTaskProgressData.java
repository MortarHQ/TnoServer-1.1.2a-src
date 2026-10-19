package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record DwarfTaskProgressData(int completedTasks, Optional<UUID> pendingGuildMapDwarfId, boolean guildMapClaimed) {
   private static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
   public static final DwarfTaskProgressData EMPTY = new DwarfTaskProgressData(0, Optional.empty(), false);
   public static final Codec<DwarfTaskProgressData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.optionalFieldOf("completed_tasks", 0).forGetter(DwarfTaskProgressData::completedTasks),
            UUID_CODEC.optionalFieldOf("pending_guild_map_dwarf_id").forGetter(DwarfTaskProgressData::pendingGuildMapDwarfId),
            Codec.BOOL.optionalFieldOf("guild_map_claimed", false).forGetter(DwarfTaskProgressData::guildMapClaimed)
         )
         .apply(instance, DwarfTaskProgressData::new)
   );
   public static final StreamCodec<ByteBuf, DwarfTaskProgressData> STREAM_CODEC = new StreamCodec<ByteBuf, DwarfTaskProgressData>() {
      public DwarfTaskProgressData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null
            ? DwarfTaskProgressData.EMPTY
            : DwarfTaskProgressData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(DwarfTaskProgressData.EMPTY);
      }

      public void encode(ByteBuf buffer, DwarfTaskProgressData value) {
         CompoundTag tag = (CompoundTag)DwarfTaskProgressData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         new FriendlyByteBuf(buffer).writeNbt(tag);
      }
   };

   public DwarfTaskProgressData {
      completedTasks = Math.max(0, completedTasks);
      pendingGuildMapDwarfId = pendingGuildMapDwarfId == null ? Optional.empty() : pendingGuildMapDwarfId.filter(Objects::nonNull);
      if (guildMapClaimed) {
         pendingGuildMapDwarfId = Optional.empty();
      }
   }

   public boolean hasState() {
      return this.completedTasks > 0 || this.pendingGuildMapDwarfId.isPresent() || this.guildMapClaimed;
   }

   public boolean hasPendingGuildMapRewardFor(UUID dwarfId) {
      return this.pendingGuildMapDwarfId.filter(dwarfId::equals).isPresent();
   }

   public DwarfTaskProgressData recordCompletedTask(UUID dwarfId, int requiredCompletions) {
      int nextCompletedTasks = this.completedTasks + 1;
      Optional<UUID> nextPendingDwarfId = this.pendingGuildMapDwarfId;
      if (!this.guildMapClaimed && nextPendingDwarfId.isEmpty() && requiredCompletions > 0 && nextCompletedTasks >= requiredCompletions) {
         nextPendingDwarfId = Optional.ofNullable(dwarfId);
      }

      return new DwarfTaskProgressData(nextCompletedTasks, nextPendingDwarfId, this.guildMapClaimed);
   }

   public DwarfTaskProgressData claimGuildMapReward() {
      return new DwarfTaskProgressData(this.completedTasks, Optional.empty(), true);
   }
}
