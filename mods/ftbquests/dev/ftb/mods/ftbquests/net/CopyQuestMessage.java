package dev.ftb.mods.ftbquests.net;

import dev.architectury.networking.NetworkManager.PacketContext;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import dev.ftb.mods.ftbquests.util.NetUtils;
import java.util.Objects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.server.MinecraftServer;

public record CopyQuestMessage(long id, long chapterId, double qx, double qy, boolean copyDeps) implements CustomPacketPayload {
   public static final Type<CopyQuestMessage> TYPE = new Type(FTBQuestsAPI.rl("copy_quest_message"));
   public static final StreamCodec<FriendlyByteBuf, CopyQuestMessage> STREAM_CODEC = StreamCodec.composite(
      ByteBufCodecs.VAR_LONG,
      CopyQuestMessage::id,
      ByteBufCodecs.VAR_LONG,
      CopyQuestMessage::chapterId,
      ByteBufCodecs.DOUBLE,
      CopyQuestMessage::qx,
      ByteBufCodecs.DOUBLE,
      CopyQuestMessage::qy,
      ByteBufCodecs.BOOL,
      CopyQuestMessage::copyDeps,
      CopyQuestMessage::new
   );

   public Type<CopyQuestMessage> type() {
      return TYPE;
   }

   public static void handle(CopyQuestMessage message, PacketContext context) {
      context.queue(() -> {
         BaseQuestFile file = ServerQuestFile.INSTANCE;
         if (file.get(message.id) instanceof Quest toCopy && file.get(message.chapterId) instanceof Chapter chapter && NetUtils.canEdit(context)) {
            Quest newQuest = Objects.requireNonNull(QuestObjectBase.copy(toCopy, () -> new Quest(file.newID(), chapter)));
            if (!message.copyDeps) {
               newQuest.clearDependencies();
            }

            newQuest.setX(message.qx);
            newQuest.setY(message.qy);
            newQuest.onCreated();
            toCopy.getTasks().forEach(task -> {
               Task newTask = QuestObjectBase.copy(task, () -> TaskType.createTask(file.newID(), newQuest, task.getType().getTypeForNBT()));
               if (newTask != null) {
                  newTask.onCreated();
               }
            });

            for (Reward reward : toCopy.getRewards()) {
               Reward newReward = QuestObjectBase.copy(reward, () -> RewardType.createReward(file.newID(), newQuest, reward.getType().getTypeForNBT()));
               if (newReward != null) {
                  newReward.onCreated();
               }
            }

            MinecraftServer server = context.getPlayer().getServer();
            NetworkHelper.sendToAll(server, CreateObjectResponseMessage.create(newQuest, null));
            newQuest.getTasks().forEach(task -> {
               CompoundTag extra = new CompoundTag();
               extra.putString("type", task.getType().getTypeForNBT());
               NetworkHelper.sendToAll(server, CreateObjectResponseMessage.create(task, extra));
            });
            newQuest.getRewards().forEach(rewardx -> {
               CompoundTag extra = new CompoundTag();
               extra.putString("type", rewardx.getType().getTypeForNBT());
               NetworkHelper.sendToAll(server, CreateObjectResponseMessage.create(rewardx, extra));
            });
            ServerQuestFile.INSTANCE.refreshIDMap();
            ServerQuestFile.INSTANCE.clearCachedData();
            ServerQuestFile.INSTANCE.markDirty();
         }
      });
   }
}
