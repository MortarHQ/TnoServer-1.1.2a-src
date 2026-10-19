package dev.ftb.mods.ftbquests.quest.task;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries.AdvancementInfo;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.advancements.CriterionProgress;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AdvancementTask extends AbstractBooleanTask {
   private ResourceLocation advancement = ResourceLocation.parse("minecraft:story/root");
   private String criterion = "";

   public AdvancementTask(long id, Quest quest) {
      super(id, quest);
   }

   @Override
   public TaskType getType() {
      return TaskTypes.ADVANCEMENT;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.putString("advancement", this.advancement.toString());
      nbt.putString("criterion", this.criterion);
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.advancement = ResourceLocation.tryParse(nbt.getString("advancement"));
      this.criterion = nbt.getString("criterion");
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeResourceLocation(this.advancement);
      buffer.writeUtf(this.criterion, 32767);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.advancement = buffer.readResourceLocation();
      this.criterion = buffer.readUtf(32767);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      if (KnownServerRegistries.client != null && !KnownServerRegistries.client.advancements().isEmpty()) {
         Map<ResourceLocation, AdvancementInfo> advancements = KnownServerRegistries.client.advancements();
         AdvancementInfo def = advancements.values().iterator().next();
         config.addEnum(
               "advancement",
               this.advancement,
               v -> this.advancement = v,
               NameMap.of(def.id(), (ResourceLocation[])advancements.keySet().toArray(new ResourceLocation[0]))
                  .icon(id -> ItemIcon.getItemIcon(advancements.getOrDefault(id, def).icon()))
                  .name(id -> advancements.getOrDefault(id, def).name())
                  .create()
            )
            .setNameKey("ftbquests.task.ftbquests.advancement");
      } else {
         config.addString("advancement", this.advancement.toString(), v -> this.advancement = ResourceLocation.tryParse(v), "minecraft:story/root")
            .setNameKey("ftbquests.task.ftbquests.advancement");
      }

      config.addString("criterion", this.criterion, v -> this.criterion = v, "");
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Component getAltTitle() {
      AdvancementInfo info = KnownServerRegistries.client == null ? null : (AdvancementInfo)KnownServerRegistries.client.advancements().get(this.advancement);
      return (Component)(info != null && !TextUtils.isComponentEmpty(info.name())
         ? Component.translatable("ftbquests.task.ftbquests.advancement")
            .append(": ")
            .append(Component.literal("").append(info.name()).withStyle(ChatFormatting.YELLOW))
         : super.getAltTitle());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      AdvancementInfo info = KnownServerRegistries.client == null ? null : (AdvancementInfo)KnownServerRegistries.client.advancements().get(this.advancement);
      return info != null && !info.icon().isEmpty() ? ItemIcon.getItemIcon(info.icon()) : super.getAltIcon();
   }

   @Override
   public int autoSubmitOnPlayerTick() {
      return 5;
   }

   @Override
   public boolean canSubmit(TeamData teamData, ServerPlayer player) {
      AdvancementHolder advancementHolder = player.server.getAdvancements().get(this.advancement);
      if (advancementHolder == null) {
         return false;
      }

      AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancementHolder);
      if (this.criterion.isEmpty()) {
         return progress.isDone();
      }

      CriterionProgress criterionProgress = progress.getCriterion(this.criterion);
      return criterionProgress != null && criterionProgress.isDone();
   }
}
