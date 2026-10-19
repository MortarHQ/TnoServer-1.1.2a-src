package dev.ftb.mods.ftbquests.block.entity;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;
import dev.ftb.mods.ftbquests.registry.ModBlockEntityTypes;
import dev.ftb.mods.ftbquests.util.ConfigQuestObject;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class QuestBarrierBlockEntity extends BaseBarrierBlockEntity {
   private Quest cachedQuest = null;

   public QuestBarrierBlockEntity(BlockPos blockPos, BlockState blockState) {
      super((BlockEntityType<?>)ModBlockEntityTypes.BARRIER.get(), blockPos, blockState);
   }

   @Override
   protected void applySavedData(BaseBarrierBlockEntity.BarrierSavedData data) {
      super.applySavedData(data);
      this.cachedQuest = null;
   }

   @Override
   public void updateFromString(String objStr) {
      super.updateFromString(objStr);
      this.cachedQuest = null;
      this.setChanged();
   }

   @Override
   protected boolean checkIfOpen(Player player) {
      Quest quest = this.getQuest();
      return quest != null && quest.getQuestFile().getTeamData(player).map(d -> d.isCompleted(quest)).orElse(false);
   }

   @Override
   protected void addConfigEntries(ConfigGroup cg) {
      ((ConfigQuestObject)cg.add("quest", new ConfigQuestObject(QuestObjectType.QUEST), this.getQuest(), this::setQuest, null)).setNameKey("ftbquests.quest");
   }

   public Quest getQuest() {
      if (this.cachedQuest == null && !this.objStr.isEmpty() || this.cachedQuest != null && !this.cachedQuest.getCodeString().equals(this.objStr)) {
         long objId = BaseQuestFile.parseCodeString(this.objStr);
         this.cachedQuest = FTBQuestsAPI.api().getQuestFile(this.level.isClientSide).getQuest(objId);
      }

      return this.cachedQuest;
   }

   public void setQuest(Quest quest) {
      this.cachedQuest = quest;
      this.objStr = quest == null ? "" : quest.getCodeString();
      this.setChanged();
   }
}
