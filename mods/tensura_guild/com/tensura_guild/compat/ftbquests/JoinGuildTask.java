package com.tensura_guild.compat.ftbquests;

import com.tensura_guild.data.GuildMemberData;
import com.tensura_guild.registry.GuildAttachments;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import net.minecraft.server.level.ServerPlayer;

public class JoinGuildTask extends AbstractBooleanTask {
   public JoinGuildTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return GuildFtbQuestTaskTypes.JOIN_GUILD;
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      return ((GuildMemberData)player.getData(GuildAttachments.GUILD_MEMBER)).hasJoinedGuild();
   }

   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   public boolean checkOnLogin() {
      return true;
   }
}
