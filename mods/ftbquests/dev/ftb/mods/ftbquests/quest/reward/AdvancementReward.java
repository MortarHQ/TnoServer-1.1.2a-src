package dev.ftb.mods.ftbquests.quest.reward;

import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.NameMap;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries;
import dev.ftb.mods.ftblibrary.util.KnownServerRegistries.AdvancementInfo;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.Map;
import net.minecraft.ChatFormatting;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class AdvancementReward extends Reward {
   private ResourceLocation advancement = ResourceLocation.withDefaultNamespace("story/root");
   private String criterion = "";

   public AdvancementReward(long id, Quest quest) {
      super(id, quest);
   }

   @Override
   public RewardType getType() {
      return RewardTypes.ADVANCEMENT;
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
         config.addEnum(
               "advancement",
               this.advancement,
               v -> this.advancement = v,
               NameMap.of(advancements.keySet().iterator().next(), (ResourceLocation[])advancements.keySet().toArray(new ResourceLocation[0]))
                  .icon(resourceLocation -> ItemIcon.getItemIcon(advancements.get(resourceLocation).icon()))
                  .name(resourceLocation -> advancements.get(resourceLocation).name())
                  .create()
            )
            .setNameKey("ftbquests.reward.ftbquests.advancement");
      } else {
         config.addString("advancement", this.advancement.toString(), v -> this.advancement = ResourceLocation.tryParse(v), "minecraft:story/root")
            .setNameKey("ftbquests.reward.ftbquests.advancement");
      }

      config.addString("criterion", this.criterion, v -> this.criterion = v, "");
   }

   @Override
   public void claim(ServerPlayer player, boolean notify) {
      AdvancementHolder advancementHolder = player.server.getAdvancements().get(this.advancement);
      if (advancementHolder != null) {
         if (this.criterion.isEmpty()) {
            for (String s : advancementHolder.value().criteria().keySet()) {
               player.getAdvancements().award(advancementHolder, s);
            }
         } else {
            player.getAdvancements().award(advancementHolder, this.criterion);
         }
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Component getAltTitle() {
      AdvancementInfo info = KnownServerRegistries.client == null ? null : (AdvancementInfo)KnownServerRegistries.client.advancements().get(this.advancement);
      return (Component)(info != null && !TextUtils.isComponentEmpty(info.name())
         ? Component.translatable("ftbquests.reward.ftbquests.advancement").append(": ").append(info.name().copy().withStyle(ChatFormatting.YELLOW))
         : super.getAltTitle());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      AdvancementInfo info = KnownServerRegistries.client == null ? null : (AdvancementInfo)KnownServerRegistries.client.advancements().get(this.advancement);
      return info != null && !info.icon().isEmpty() ? ItemIcon.getItemIcon(info.icon()) : super.getAltIcon();
   }
}
