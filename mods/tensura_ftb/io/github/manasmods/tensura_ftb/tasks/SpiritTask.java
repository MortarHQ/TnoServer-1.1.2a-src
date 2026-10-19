package io.github.manasmods.tensura_ftb.tasks;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.quest.task.AbstractBooleanTask;
import dev.ftb.mods.ftbquests.quest.task.TaskType;
import io.github.manasmods.tensura.ability.magic.Element;
import io.github.manasmods.tensura.ability.magic.spiritual.SpiritualMagic.SpiritLevel;
import io.github.manasmods.tensura.storage.TensuraStorages;
import io.github.manasmods.tensura.storage.spirit.ISpiritWielder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class SpiritTask extends AbstractBooleanTask {
   public static TaskType SPIRIT;
   private Element element = Element.UNIDENTIFIED;
   private SpiritLevel level = SpiritLevel.LESSER;

   public SpiritTask(long id, Quest quest) {
      super(id, quest);
   }

   public TaskType getType() {
      return SPIRIT;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("element", this.element.toString());
      nbt.putString("level", this.level.toString());
   }

   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.element = Element.valueOf(nbt.getString("element"));
      this.level = SpiritLevel.valueOf(nbt.getString("level"));
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeEnum(this.element);
      buffer.writeEnum(this.level);
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.element = (Element)buffer.readEnum(Element.class);
      this.level = (SpiritLevel)buffer.readEnum(SpiritLevel.class);
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addString("element", this.element.toString(), v -> this.element = Element.valueOf(v), "UNIDENTIFIED")
         .setNameKey("tensura_ftb.task.spirit.element");
      config.addString("level", this.level.toString(), v -> this.level = SpiritLevel.valueOf(v), "LESSER").setNameKey("tensura_ftb.task.spirit.level");
   }

   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      ISpiritWielder spirit = TensuraStorages.getSpiritFrom(player);
      if (this.element == Element.UNIDENTIFIED) {
         for (Element each : Element.values()) {
            if (spirit.getSpiritLevelId(each) >= this.level.getId()) {
               return true;
            }
         }

         return false;
      } else {
         return spirit.getSpiritLevelId(this.element) >= this.level.getId();
      }
   }
}
