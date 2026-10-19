package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import java.util.UUID;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;

public record DwarfTaskData(Optional<ResourceLocation> taskId, Optional<UUID> dwarfId, long startedAt, int requiredCount) {
   private static final Codec<UUID> UUID_CODEC = Codec.STRING.xmap(UUID::fromString, UUID::toString);
   public static final DwarfTaskData EMPTY = new DwarfTaskData(Optional.empty(), Optional.empty(), -1L, -1);
   public static final Codec<DwarfTaskData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("task_id").forGetter(DwarfTaskData::taskId),
            UUID_CODEC.optionalFieldOf("dwarf_id").forGetter(DwarfTaskData::dwarfId),
            Codec.LONG.optionalFieldOf("started_at", -1L).forGetter(DwarfTaskData::startedAt),
            Codec.INT.optionalFieldOf("required_count", -1).forGetter(DwarfTaskData::requiredCount)
         )
         .apply(instance, DwarfTaskData::new)
   );
   public static final StreamCodec<ByteBuf, DwarfTaskData> STREAM_CODEC = new StreamCodec<ByteBuf, DwarfTaskData>() {
      public DwarfTaskData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null ? DwarfTaskData.EMPTY : DwarfTaskData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(DwarfTaskData.EMPTY);
      }

      public void encode(ByteBuf buffer, DwarfTaskData value) {
         CompoundTag tag = (CompoundTag)DwarfTaskData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         new FriendlyByteBuf(buffer).writeNbt(tag);
      }
   };

   public DwarfTaskData {
      taskId = taskId == null ? Optional.empty() : taskId;
      dwarfId = dwarfId == null ? Optional.empty() : dwarfId;
      startedAt = hasActiveTask(taskId, dwarfId) ? Math.max(0L, startedAt) : -1L;
      requiredCount = hasActiveTask(taskId, dwarfId) && requiredCount > 0 ? requiredCount : -1;
   }

   public static DwarfTaskData active(ResourceLocation taskId, UUID dwarfId, long startedAt) {
      return active(taskId, dwarfId, startedAt, -1);
   }

   public static DwarfTaskData active(ResourceLocation taskId, UUID dwarfId, long startedAt, int requiredCount) {
      return new DwarfTaskData(Optional.of(taskId), Optional.of(dwarfId), Math.max(0L, startedAt), requiredCount);
   }

   public boolean hasActiveTask() {
      return hasActiveTask(this.taskId, this.dwarfId);
   }

   public boolean isExpired(long currentGameTime, int timeLimitTicks) {
      return this.hasActiveTask() && timeLimitTicks > 0 && currentGameTime - this.startedAt > timeLimitTicks;
   }

   public long remainingTicks(long currentGameTime, int timeLimitTicks) {
      return this.hasActiveTask() ? GuildTaskTime.remainingTicks(currentGameTime, this.startedAt, timeLimitTicks) : 0L;
   }

   public int requiredCountOrDefault(int fallback) {
      return this.requiredCount > 0 ? this.requiredCount : fallback;
   }

   private static boolean hasActiveTask(Optional<ResourceLocation> taskId, Optional<UUID> dwarfId) {
      return taskId != null && taskId.isPresent() && dwarfId != null && dwarfId.isPresent();
   }
}
