package dev.ftb.mods.ftbquests.util;

import dev.ftb.mods.ftbquests.net.ClearRepeatCooldownMessage;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.TeamData;
import java.util.Date;
import java.util.UUID;
import net.minecraft.core.UUIDUtil;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

public class ProgressChange {
   public static StreamCodec<FriendlyByteBuf, ProgressChange> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_LONG,
      ProgressChange::getOriginId,
      ByteBufCodecs.BOOL,
      ProgressChange::shouldReset,
      UUIDUtil.STREAM_CODEC,
      ProgressChange::getPlayerId,
      ByteBufCodecs.BOOL,
      ProgressChange::shouldNotify,
      ProgressChange::createServerSide
   );
   private final Date date;
   private final QuestObjectBase origin;
   private final UUID playerId;
   private boolean reset;
   private boolean notifications;

   public ProgressChange(QuestObjectBase origin, UUID playerId) {
      this.origin = origin;
      this.playerId = playerId;
      this.date = new Date();
      this.reset = true;
      this.notifications = false;
   }

   public static ProgressChange createServerSide(long origin, boolean reset, UUID playerId, boolean notifications) {
      ProgressChange pc = new ProgressChange(ServerQuestFile.INSTANCE.getBase(origin), playerId);
      pc.reset = reset;
      pc.notifications = notifications;
      return pc;
   }

   public void maybeForceProgress(UUID teamId) {
      if (this.origin != null) {
         TeamData data = ServerQuestFile.INSTANCE.getOrCreateTeamData(teamId);
         this.origin.forceProgressRaw(data, this);
         if (this.origin instanceof Quest quest && this.reset) {
            data.clearRepeatCooldown(quest);
            ClearRepeatCooldownMessage.sendToTeam(quest, data.getTeamId());
         }
      }
   }

   public UUID getPlayerId() {
      return this.playerId;
   }

   public Date getDate() {
      return this.date;
   }

   public ProgressChange setReset(boolean reset) {
      this.reset = reset;
      return this;
   }

   public boolean shouldReset() {
      return this.reset;
   }

   public ProgressChange withNotifications() {
      this.notifications = true;
      return this;
   }

   public boolean shouldNotify() {
      return this.notifications;
   }

   private long getOriginId() {
      return this.origin == null ? 0L : this.origin.id;
   }
}
