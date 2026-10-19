package com.tensura_guild.compat.ftbquests;

import com.tensura_guild.data.GuildLevelData;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class CompleteGuildTask extends AbstractGuildEventTask {
   private static final String RANK_FILTER_KEY = "rank_filter";
   private static final NameMap<CompleteGuildTask.RankFilter> RANK_FILTERS = NameMap.of(CompleteGuildTask.RankFilter.ANY, CompleteGuildTask.RankFilter.values())
      .id(CompleteGuildTask.RankFilter::serializedName)
      .name(filter -> Component.translatable("ftbquests.task.tensura_guild.complete_guild_task.rank_filter." + filter.serializedName()))
      .create();
   private CompleteGuildTask.RankFilter rankFilter = CompleteGuildTask.RankFilter.ANY;

   public CompleteGuildTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return GuildFtbQuestTaskTypes.COMPLETE_GUILD_TASK;
   }

   public void recordCompletion(TeamData teamData, ServerPlayer player, String completedRank) {
      if (this.rankFilter.matches(completedRank)) {
         super.recordCompletion(teamData, player);
      }
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("rank_filter", this.rankFilter.serializedName());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.rankFilter = CompleteGuildTask.RankFilter.fromSerializedName(nbt.getString("rank_filter"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.rankFilter.serializedName(), 16);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.rankFilter = CompleteGuildTask.RankFilter.fromSerializedName(buffer.readUtf(16));
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addEnum("rank_filter", this.rankFilter, value -> this.rankFilter = value, RANK_FILTERS, CompleteGuildTask.RankFilter.ANY)
         .setNameKey("ftbquests.task.tensura_guild.complete_guild_task.rank_filter");
   }

   public Component getAltTitle() {
      return Component.translatable("ftbquests.task.tensura_guild.complete_guild_task")
         .append(": ")
         .append(Component.translatable("ftbquests.task.tensura_guild.complete_guild_task.rank_filter." + this.rankFilter.serializedName()));
   }

   private enum RankFilter {
      ANY("any"),
      A("a"),
      B("b"),
      C("c"),
      D("d"),
      E("e"),
      F("f");

      private final String serializedName;

      RankFilter(String serializedName) {
         this.serializedName = serializedName;
      }

      private String serializedName() {
         return this.serializedName;
      }

      private boolean matches(String rank) {
         return this == ANY || this.name().equals(GuildLevelData.normalizeRank(rank));
      }

      private static CompleteGuildTask.RankFilter fromSerializedName(String value) {
         for (CompleteGuildTask.RankFilter filter : values()) {
            if (filter.serializedName.equalsIgnoreCase(value) || filter.name().equalsIgnoreCase(value)) {
               return filter;
            }
         }

         return ANY;
      }
   }
}
