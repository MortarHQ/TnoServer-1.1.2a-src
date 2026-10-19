package io.github.manasmods.tensura_ftb.rewards;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
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

public class SpiritReward extends Reward {
   public static RewardType SPIRIT;
   private Element element = Element.FLAME;
   private SpiritLevel level = SpiritLevel.LESSER;

   public SpiritReward(long id, Quest quest) {
      super(id, quest);
   }

   public RewardType getType() {
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

   public void claim(ServerPlayer player, boolean notify) {
      ISpiritWielder spirit = TensuraStorages.getSpiritFrom(player);
      spirit.setSpiritLevel(this.element, this.level);
      spirit.markDirty();
   }
}
