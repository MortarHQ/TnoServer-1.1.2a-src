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

public record DwarfTaskOfferData(
   Optional<ResourceLocation> taskId, Optional<ResourceLocation> cooldownTaskId, long cooldownUntil, long nextRollAt, int amount, Optional<UUID> reservedBy
) {
   public static final int GAME_DAY_TICKS = 28800;
   public static final int TASK_COOLDOWN_TICKS = 86400;
   public static final double DAILY_REOFFER_CHANCE = 0.1;
   public static final DwarfTaskOfferData EMPTY = new DwarfTaskOfferData(Optional.empty(), Optional.empty(), -1L, -1L, -1, Optional.empty());
   public static final Codec<DwarfTaskOfferData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            ResourceLocation.CODEC.optionalFieldOf("task_id").forGetter(DwarfTaskOfferData::taskId),
            ResourceLocation.CODEC.optionalFieldOf("cooldown_task_id").forGetter(DwarfTaskOfferData::cooldownTaskId),
            Codec.LONG.optionalFieldOf("cooldown_until", -1L).forGetter(DwarfTaskOfferData::cooldownUntil),
            Codec.LONG.optionalFieldOf("next_roll_at", -1L).forGetter(DwarfTaskOfferData::nextRollAt),
            Codec.INT.optionalFieldOf("amount", -1).forGetter(DwarfTaskOfferData::amount),
            Codec.STRING.xmap(UUID::fromString, UUID::toString).optionalFieldOf("reserved_by").forGetter(DwarfTaskOfferData::reservedBy)
         )
         .apply(instance, DwarfTaskOfferData::new)
   );
   public static final StreamCodec<ByteBuf, DwarfTaskOfferData> STREAM_CODEC = new StreamCodec<ByteBuf, DwarfTaskOfferData>() {
      public DwarfTaskOfferData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null ? DwarfTaskOfferData.EMPTY : DwarfTaskOfferData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(DwarfTaskOfferData.EMPTY);
      }

      public void encode(ByteBuf buffer, DwarfTaskOfferData value) {
         CompoundTag tag = (CompoundTag)DwarfTaskOfferData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         new FriendlyByteBuf(buffer).writeNbt(tag);
      }
   };

   public DwarfTaskOfferData(
      Optional<ResourceLocation> taskId, Optional<ResourceLocation> cooldownTaskId, long cooldownUntil, long nextRollAt, int amount, Optional<UUID> reservedBy
   ) {
      taskId = taskId == null ? Optional.empty() : taskId;
      cooldownTaskId = cooldownTaskId == null ? Optional.empty() : cooldownTaskId;
      reservedBy = reservedBy == null ? Optional.empty() : reservedBy;
      if (taskId.isPresent()) {
         cooldownTaskId = Optional.empty();
         cooldownUntil = -1L;
         nextRollAt = -1L;
         amount = amount > 0 ? amount : -1;
         reservedBy = reservedBy.filter(uuid -> uuid != null);
      } else if (cooldownTaskId.isPresent()) {
         cooldownUntil = Math.max(0L, cooldownUntil);
         nextRollAt = nextRollAt < 0L ? cooldownUntil : Math.max(cooldownUntil, nextRollAt);
         amount = -1;
         reservedBy = Optional.empty();
      } else {
         cooldownUntil = -1L;
         nextRollAt = -1L;
         amount = -1;
         reservedBy = Optional.empty();
      }

      this.taskId = taskId;
      this.cooldownTaskId = cooldownTaskId;
      this.cooldownUntil = cooldownUntil;
      this.nextRollAt = nextRollAt;
      this.amount = amount;
      this.reservedBy = reservedBy;
   }

   public static DwarfTaskOfferData assigned(ResourceLocation taskId) {
      return assigned(taskId, -1);
   }

   public static DwarfTaskOfferData assigned(ResourceLocation taskId, int amount) {
      return new DwarfTaskOfferData(Optional.of(taskId), Optional.empty(), -1L, -1L, amount, Optional.empty());
   }

   public static DwarfTaskOfferData assigned(ResourceLocation taskId, int amount, UUID reservedBy) {
      return new DwarfTaskOfferData(Optional.of(taskId), Optional.empty(), -1L, -1L, amount, Optional.ofNullable(reservedBy));
   }

   public static DwarfTaskOfferData reserved(ResourceLocation taskId, int amount, UUID reservedBy) {
      return assigned(taskId, amount, reservedBy);
   }

   public static DwarfTaskOfferData cooldown(ResourceLocation taskId, long currentGameTime, long cooldownTicks) {
      long readyAt = Math.max(0L, currentGameTime) + Math.max(0L, cooldownTicks);
      return new DwarfTaskOfferData(Optional.empty(), Optional.of(taskId), readyAt, readyAt, -1, Optional.empty());
   }

   public boolean hasTask() {
      return this.taskId.isPresent();
   }

   public boolean hasState() {
      return this.taskId.isPresent() || this.cooldownTaskId.isPresent() || this.reservedBy.isPresent();
   }

   public boolean suppressesFallbackOffer() {
      return this.taskId.isEmpty() && this.cooldownTaskId.isPresent();
   }

   public boolean isReadyForRoll(long currentGameTime) {
      return this.cooldownTaskId.isPresent() && currentGameTime >= this.cooldownUntil && currentGameTime >= this.nextRollAt;
   }

   public DwarfTaskOfferData rollFailed(long currentGameTime) {
      return this.cooldownTaskId.isEmpty()
         ? EMPTY
         : new DwarfTaskOfferData(
            Optional.empty(), this.cooldownTaskId, this.cooldownUntil, Math.max(currentGameTime, this.nextRollAt) + 28800L, -1, Optional.empty()
         );
   }

   public DwarfTaskOfferData offerAgain() {
      return this.cooldownTaskId.<DwarfTaskOfferData>map(DwarfTaskOfferData::assigned).orElse(EMPTY);
   }

   public int amountOrDefault(int fallback) {
      return this.amount > 0 ? this.amount : fallback;
   }
}
