package com.tensura_guild.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import java.util.Optional;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public record GuildMemberData(
   int memberNumber,
   Optional<String> title,
   Optional<String> squad,
   int completedTasks,
   int activeTasks,
   int successfulTasks,
   int subjugationCount,
   int failureCount,
   Optional<String> originBiome,
   int originX,
   int originY,
   int originZ,
   boolean guildPrefixVisible
) {
   public static final GuildMemberData EMPTY = new GuildMemberData(0, Optional.empty(), Optional.empty(), 0, 0, 0, 0, 0, Optional.empty(), 0, 0, 0, true);
   public static final Codec<GuildMemberData> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(
            Codec.INT.optionalFieldOf("member_number", 0).forGetter(GuildMemberData::memberNumber),
            Codec.STRING.optionalFieldOf("title").forGetter(GuildMemberData::title),
            Codec.STRING.optionalFieldOf("squad").forGetter(GuildMemberData::squad),
            Codec.INT.optionalFieldOf("completed_tasks", 0).forGetter(GuildMemberData::completedTasks),
            Codec.INT.optionalFieldOf("active_tasks", 0).forGetter(GuildMemberData::activeTasks),
            Codec.INT.optionalFieldOf("successful_tasks", 0).forGetter(GuildMemberData::successfulTasks),
            Codec.INT.optionalFieldOf("subjugation_count", 0).forGetter(GuildMemberData::subjugationCount),
            Codec.INT.optionalFieldOf("failure_count", 0).forGetter(GuildMemberData::failureCount),
            Codec.STRING.optionalFieldOf("origin_biome").forGetter(GuildMemberData::originBiome),
            Codec.INT.optionalFieldOf("origin_x", 0).forGetter(GuildMemberData::originX),
            Codec.INT.optionalFieldOf("origin_y", 0).forGetter(GuildMemberData::originY),
            Codec.INT.optionalFieldOf("origin_z", 0).forGetter(GuildMemberData::originZ),
            Codec.BOOL.optionalFieldOf("guild_prefix_visible", true).forGetter(GuildMemberData::guildPrefixVisible)
         )
         .apply(instance, GuildMemberData::new)
   );
   public static final StreamCodec<ByteBuf, GuildMemberData> STREAM_CODEC = new StreamCodec<ByteBuf, GuildMemberData>() {
      public GuildMemberData decode(ByteBuf buffer) {
         CompoundTag tag = new FriendlyByteBuf(buffer).readNbt();
         return tag == null ? GuildMemberData.EMPTY : GuildMemberData.CODEC.parse(NbtOps.INSTANCE, tag).result().orElse(GuildMemberData.EMPTY);
      }

      public void encode(ByteBuf buffer, GuildMemberData value) {
         CompoundTag tag = (CompoundTag)GuildMemberData.CODEC.encodeStart(NbtOps.INSTANCE, value).result().orElseGet(CompoundTag::new);
         FriendlyByteBuf friendlyByteBuf = new FriendlyByteBuf(buffer);
         friendlyByteBuf.writeNbt(tag);
      }
   };

   public GuildMemberData {
      memberNumber = Math.max(0, memberNumber);
      title = title == null ? Optional.empty() : title.filter(value -> !value.isBlank()).map(String::trim);
      squad = squad == null ? Optional.empty() : squad.filter(value -> !value.isBlank()).map(String::trim);
      completedTasks = Math.max(0, completedTasks);
      activeTasks = Math.max(0, activeTasks);
      successfulTasks = Math.max(0, successfulTasks);
      subjugationCount = Math.max(0, subjugationCount);
      failureCount = Math.max(0, failureCount);
      originBiome = originBiome == null ? Optional.empty() : originBiome.filter(value -> !value.isBlank()).map(String::trim);
   }

   public boolean hasJoinedGuild() {
      return this.memberNumber > 0;
   }

   public GuildMemberData withMemberNumber(int value) {
      return new GuildMemberData(
         value,
         this.title,
         this.squad,
         this.completedTasks,
         this.activeTasks,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withTitle(String value) {
      return new GuildMemberData(
         this.memberNumber,
         Optional.ofNullable(value),
         this.squad,
         this.completedTasks,
         this.activeTasks,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withSquad(String value) {
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         Optional.ofNullable(value),
         this.completedTasks,
         this.activeTasks,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withOrigin(String biomeId, int x, int y, int z) {
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks,
         this.activeTasks,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         Optional.ofNullable(biomeId),
         x,
         y,
         z,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withGuildPrefixVisible(boolean value) {
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks,
         this.activeTasks,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         value
      );
   }

   public GuildMemberData withAcceptedTask() {
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks,
         this.activeTasks + 1,
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withCompletedTask() {
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks + 1,
         Math.max(0, this.activeTasks - 1),
         this.successfulTasks + 1,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withFailedTasks(int amount) {
      int count = Math.max(0, amount);
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks,
         Math.max(0, this.activeTasks - count),
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount + count,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public GuildMemberData withRemovedActiveTasks(int amount) {
      int count = Math.max(0, amount);
      return new GuildMemberData(
         this.memberNumber,
         this.title,
         this.squad,
         this.completedTasks,
         Math.max(0, this.activeTasks - count),
         this.successfulTasks,
         this.subjugationCount,
         this.failureCount,
         this.originBiome,
         this.originX,
         this.originY,
         this.originZ,
         this.guildPrefixVisible
      );
   }

   public String formattedMemberNumber() {
      return this.hasJoinedGuild() ? String.format("%06d", this.memberNumber) : "------";
   }

   public String successRateText() {
      int resolvedTasks = this.successfulTasks + this.failureCount;
      if (resolvedTasks <= 0) {
         return "--";
      }

      double rate = this.successfulTasks * 100.0 / resolvedTasks;
      return String.format("%.0f%%", rate);
   }
}
