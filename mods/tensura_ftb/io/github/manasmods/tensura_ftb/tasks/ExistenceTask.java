package io.github.manasmods.tensura_ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.ISingleLongValueTask;
import dev.ftb.mods.ftbquests.quest.task.Task;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.tensura.registry.entity.HumanEntityTypes;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.util.EnergyHelper;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ExistenceTask extends Task implements ISingleLongValueTask {
   public static TaskType EXISTENCE;
   private long value = 1L;
   private ExistenceTask.ExistenceType existence = ExistenceTask.ExistenceType.MAX_EP;

   public ExistenceTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return EXISTENCE;
   }

   public long getMaxProgress() {
      return this.value;
   }

   public String formatMaxProgress() {
      return Long.toUnsignedString(this.value);
   }

   public String formatProgress(TeamData teamData, long progress) {
      return Long.toUnsignedString(progress);
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putLong("value", this.value);
      nbt.putString("existence", this.existence.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.value = nbt.getLong("value");
      this.existence = ExistenceTask.ExistenceType.valueOf(nbt.getString("existence"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeVarLong(this.value);
      buffer.writeEnum(this.existence);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.value = buffer.readVarLong();
      this.existence = (ExistenceTask.ExistenceType)buffer.readEnum(ExistenceTask.ExistenceType.class);
   }

   public void setValue(long v) {
      this.value = v;
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addLong("value", this.value, v -> this.value = v, 1L, 1L, Long.MAX_VALUE).setNameKey("tensura_ftb.task.existence_value.value");
      config.addString("existence", this.existence.toString(), v -> this.existence = ExistenceTask.ExistenceType.valueOf(v), "MAX_EP")
         .setNameKey("tensura_ftb.task.existence_value.type");
   }

   public int autoSubmitOnPlayerTick() {
      return 20;
   }

   public double getExistenceValue(ServerPlayer player) {
      return switch (this.existence) {
         case EP -> TensuraStorages.getExistenceFrom(player).getEP();
         case MAX_EP -> EnergyHelper.getMaxEP(player);
         case AURA -> TensuraStorages.getExistenceFrom(player).getAura();
         case MAX_AURA -> EnergyHelper.getMaxAura(player);
         case MAGICULE -> TensuraStorages.getExistenceFrom(player).getMagicule();
         case MAX_MAGICULE -> EnergyHelper.getMaxMagicule(player);
         case SOUL -> TensuraStorages.getExistenceFrom(player).getSoulPoints() / 10000.0;
         case HUMAN_KILL -> TensuraStorages.getExistenceFrom(player).getHumanKill();
         case RESET_COUNTER -> TensuraStorages.getPlayerDataFrom(player).getResetCounter();
         case REPUTATION -> TensuraStorages.getPlayerDataFrom(player).getReputation(HumanEntityTypes.DWARF.getId());
         case BONUS_SKILL_LOCK -> TensuraStorages.getPlayerDataFrom(player).getBonusSkillLock();
      };
   }

   public void submitTask(TeamData teamData, ServerPlayer player, ItemStack craftedItem) {
      if (this.checkTaskSequence(teamData)) {
         teamData.setProgress(this, (long)this.getExistenceValue(player));
      }
   }

   public enum ExistenceType {
      EP,
      MAX_EP,
      AURA,
      MAX_AURA,
      MAGICULE,
      MAX_MAGICULE,
      SOUL,
      HUMAN_KILL,
      RESET_COUNTER,
      REPUTATION,
      BONUS_SKILL_LOCK;
   }
}
