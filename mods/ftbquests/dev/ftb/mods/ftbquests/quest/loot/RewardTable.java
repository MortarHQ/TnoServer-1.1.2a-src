package dev.ftb.mods.ftbquests.quest.loot;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.icon.IconAnimation;
import dev.ftb.mods.ftblibrary.icon.Icons;
import dev.ftb.mods.ftblibrary.icon.ItemIcon;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftblibrary.ui.BaseScreen;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftblibrary.util.TooltipList;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.FTBQuestsClient;
import dev.ftb.mods.ftbquests.client.gui.EditRewardTableScreen;
import dev.ftb.mods.ftbquests.client.gui.RewardTablesScreen;
import dev.ftb.mods.ftbquests.client.gui.quests.QuestScreen;
import dev.ftb.mods.ftbquests.integration.RecipeModHelper;
import dev.ftb.mods.ftbquests.net.CreateObjectResponseMessage;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.net.SyncTranslationMessageToClient;
import dev.ftb.mods.ftbquests.quest.BaseQuestFile;
import dev.ftb.mods.ftbquests.quest.Chapter;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.QuestObjectBase;
import dev.ftb.mods.ftbquests.quest.QuestObjectType;
import dev.ftb.mods.ftbquests.quest.ServerQuestFile;
import dev.ftb.mods.ftbquests.quest.reward.ItemReward;
import dev.ftb.mods.ftbquests.quest.reward.Reward;
import dev.ftb.mods.ftbquests.quest.reward.RewardType;
import dev.ftb.mods.ftbquests.quest.reward.RewardTypes;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RewardTable extends QuestObjectBase {
   private final BaseQuestFile file;
   private final List<WeightedReward> weightedRewards;
   private final Quest fakeQuest;
   private float emptyWeight;
   private int lootSize;
   private boolean hideTooltip;
   private boolean useTitle;
   private LootCrate lootCrate;
   private ResourceLocation lootTableId;
   private String filename;

   public RewardTable(long id, BaseQuestFile file) {
      this(id, file, "");
   }

   public RewardTable(long id, BaseQuestFile file, String filename) {
      super(id);
      this.file = file;
      this.filename = filename;
      this.weightedRewards = new ArrayList<>();
      this.fakeQuest = makeFakeQuest(file);
      this.emptyWeight = 0.0F;
      this.lootSize = 1;
      this.hideTooltip = false;
      this.useTitle = false;
      this.lootCrate = null;
      this.lootTableId = null;
   }

   private static Quest makeFakeQuest(BaseQuestFile file) {
      return new Quest(-1L, new Chapter(-1L, file, file.getDefaultChapterGroup()));
   }

   public static boolean isFakeQuestId(long id) {
      return id == -1L;
   }

   @NotNull
   public static QuestObjectBase createRewardForTable(long id, String type, BaseQuestFile file) {
      Reward reward = RewardType.createReward(id, makeFakeQuest(file), type);
      Validate.isTrue(reward != null, "Unknown reward type!", new Object[0]);
      return reward;
   }

   public Component getTitleOrElse(Component def) {
      return this.useTitle ? this.getTitle() : def;
   }

   public BaseQuestFile getFile() {
      return this.file;
   }

   public List<WeightedReward> getWeightedRewards() {
      return Collections.unmodifiableList(this.weightedRewards);
   }

   @Nullable
   public LootCrate getLootCrate() {
      return this.lootCrate;
   }

   public Quest getFakeQuest() {
      return this.fakeQuest;
   }

   @Override
   public QuestObjectType getObjectType() {
      return QuestObjectType.REWARD_TABLE;
   }

   @Override
   public BaseQuestFile getQuestFile() {
      return this.file;
   }

   public float getTotalWeight(boolean includeEmpty) {
      float initial = includeEmpty ? this.emptyWeight : 0.0F;
      return this.weightedRewards.stream().map(WeightedReward::getWeight).reduce(initial, Float::sum);
   }

   public Collection<WeightedReward> generateWeightedRandomRewards(RandomSource random, int nAttempts, boolean includeEmpty) {
      float total = this.getTotalWeight(includeEmpty);
      if (total <= 0.0F) {
         return List.of();
      }

      List<WeightedReward> res = this.weightedRewards.stream().filter(rewardx -> rewardx.getWeight() == 0.0F).collect(Collectors.toCollection(ArrayList::new));
      nAttempts *= this.lootSize;

      for (int i = 0; i < nAttempts; i++) {
         float threshold = random.nextFloat() * total;
         float currentWeight = includeEmpty ? this.emptyWeight : 0.0F;
         if (currentWeight < threshold) {
            for (WeightedReward reward : this.weightedRewards) {
               currentWeight += reward.getWeight();
               if (currentWeight >= threshold) {
                  res.add(reward);
                  break;
               }
            }
         }
      }

      return res;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      if (this.emptyWeight > 0.0F) {
         nbt.putFloat("empty_weight", this.emptyWeight);
      }

      nbt.putInt("loot_size", this.lootSize);
      if (this.hideTooltip) {
         nbt.putBoolean("hide_tooltip", true);
      }

      if (this.useTitle) {
         nbt.putBoolean("use_title", true);
      }

      ListTag list = new ListTag();

      for (WeightedReward wr : this.weightedRewards) {
         SNBTCompoundTag rewardTag = new SNBTCompoundTag();
         rewardTag.putString("id", wr.getReward().getCodeString());
         wr.getReward().writeData(rewardTag, provider);
         wr.getReward().addAnyProtoTranslations(rewardTag);
         if (wr.getReward().getType() != RewardTypes.ITEM) {
            rewardTag.putString("type", wr.getReward().getType().getTypeForNBT());
         }

         if (wr.getWeight() != 1.0F) {
            rewardTag.putFloat("weight", wr.getWeight());
         }

         if (rewardTag.size() < 3) {
            rewardTag.singleLine();
         }

         list.add(rewardTag);
      }

      nbt.put("rewards", list);
      if (this.lootCrate != null) {
         nbt.put("loot_crate", (Tag)Util.make(new CompoundTag(), tag -> this.lootCrate.writeData(tag)));
      }

      if (this.lootTableId != null) {
         nbt.putString("loot_table_id", this.lootTableId.toString());
      }
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      this.emptyWeight = nbt.getFloat("empty_weight");
      this.lootSize = nbt.getInt("loot_size");
      this.hideTooltip = nbt.getBoolean("hide_tooltip");
      this.useTitle = nbt.getBoolean("use_title");
      Set<Long> prevRewards = this.weightedRewards.stream().map(wr -> wr.getReward().getId()).collect(Collectors.toSet());
      this.weightedRewards.clear();
      boolean refreshIds = false;
      ListTag list = nbt.getList("rewards", 10);

      for (int i = 0; i < list.size(); i++) {
         boolean newReward = false;
         CompoundTag rewardTag = list.getCompound(i);
         if (!rewardTag.contains("id") && this.file.isServerSide()) {
            rewardTag.putString("id", QuestObjectBase.getCodeString(this.file.newID()));
         }

         long rewardId = QuestObjectBase.parseCodeString(rewardTag.getString("id"));
         if (rewardId == 0L && this.file.isServerSide()) {
            rewardId = this.file.newID();
            rewardTag.putString("id", QuestObjectBase.getCodeString(rewardId));
            refreshIds = true;
            newReward = true;
         }

         Reward reward = RewardType.createReward(rewardId, this.fakeQuest, rewardTag.getString("type"));
         if (reward != null) {
            this.getQuestFile().getTranslationManager().processInitialTranslation(rewardTag, reward);
            reward.readData(rewardTag, provider);
            this.weightedRewards.add(new WeightedReward(reward, rewardTag.contains("weight") ? rewardTag.getFloat("weight") : 1.0F));
            prevRewards.remove(rewardId);
            if (newReward && this.getFile() instanceof ServerQuestFile sqf) {
               NetworkHelper.sendToAll(sqf.server, CreateObjectResponseMessage.create(reward, rewardTag));
            }
         }
      }

      if (refreshIds) {
         this.file.refreshRewardTableRewardIDs();
      }

      if (this.getFile().isServerSide()) {
         prevRewards.forEach(id -> this.getFile().deleteObject(id));
      }

      if (nbt.contains("loot_crate")) {
         this.lootCrate = new LootCrate(this, false);
         this.lootCrate.readData(nbt.getCompound("loot_crate"));
      } else {
         this.lootCrate = null;
      }

      this.lootTableId = nbt.contains("loot_table_id") ? ResourceLocation.tryParse(nbt.getString("loot_table_id")) : null;
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      buffer.writeUtf(this.filename, 32767);
      buffer.writeFloat(this.emptyWeight);
      buffer.writeVarInt(this.lootSize);
      int flags = 0;
      flags = Bits.setFlag(flags, 1, this.hideTooltip);
      flags = Bits.setFlag(flags, 2, this.useTitle);
      flags = Bits.setFlag(flags, 4, this.lootCrate != null);
      flags = Bits.setFlag(flags, 8, this.lootTableId != null);
      buffer.writeVarInt(flags);
      buffer.writeVarInt(this.weightedRewards.size());

      for (WeightedReward wr : this.weightedRewards) {
         buffer.writeLong(wr.getReward().getId());
         buffer.writeVarInt(wr.getReward().getType().intId);
         wr.getReward().writeNetData(buffer);
         buffer.writeFloat(wr.getWeight());
      }

      if (this.lootCrate != null) {
         this.lootCrate.writeNetData(buffer);
      }

      if (this.lootTableId != null) {
         buffer.writeResourceLocation(this.lootTableId);
      }
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.filename = buffer.readUtf(32767);
      this.emptyWeight = buffer.readFloat();
      this.lootSize = buffer.readVarInt();
      int flags = buffer.readVarInt();
      this.hideTooltip = Bits.getFlag(flags, 1);
      this.useTitle = Bits.getFlag(flags, 2);
      boolean hasCrate = Bits.getFlag(flags, 4);
      boolean hasLootTableId = Bits.getFlag(flags, 8);
      this.weightedRewards.clear();
      int s = buffer.readVarInt();

      for (int i = 0; i < s; i++) {
         long id = buffer.readLong();
         RewardType type = this.file.getRewardType(buffer.readVarInt());
         Reward reward = type.createReward(id, this.fakeQuest);
         reward.readNetData(buffer);
         float weight = buffer.readFloat();
         this.weightedRewards.add(new WeightedReward(reward, weight));
      }

      this.lootCrate = null;
      if (hasCrate) {
         this.lootCrate = new LootCrate(this, false);
         this.lootCrate.readNetData(buffer);
      }

      this.lootTableId = hasLootTableId ? buffer.readResourceLocation() : null;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addDouble("empty_weight", this.emptyWeight, v -> this.emptyWeight = v.floatValue(), 0.0, 0.0, 2.147483647E9);
      config.addInt("loot_size", this.lootSize, v -> this.lootSize = v, 1, 1, Integer.MAX_VALUE);
      config.addBool("hide_tooltip", this.hideTooltip, v -> this.hideTooltip = v, false);
      config.addBool("use_title", this.useTitle, v -> this.useTitle = v, false);
      if (this.lootCrate != null) {
         this.lootCrate.fillConfigGroup(config.getOrCreateSubgroup("loot_crate").setNameKey("item.ftbquests.lootcrate"));
      }
   }

   @Override
   public void clearCachedData() {
      super.clearCachedData();
      this.weightedRewards.forEach(reward -> reward.getReward().clearCachedData());
   }

   @Override
   public void deleteSelf() {
      this.file.removeRewardTable(this);
      super.deleteSelf();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void editedFromGUI() {
      QuestScreen gui = (QuestScreen)ClientUtils.getCurrentGuiAs(QuestScreen.class);
      if (gui != null && gui.isViewingQuest()) {
         gui.refreshViewQuestPanel();
      } else {
         RewardTablesScreen gui1 = (RewardTablesScreen)ClientUtils.getCurrentGuiAs(RewardTablesScreen.class);
         if (gui1 != null) {
            gui1.refreshWidgets();
         }
      }

      this.file.updateLootCrates();
   }

   @Override
   public void editedFromGUIOnServer() {
      this.file.updateLootCrates();
      MinecraftServer server = ((ServerQuestFile)this.file).server;
      this.weightedRewards
         .forEach(
            wr -> this.file
               .getTranslationManager()
               .getStringTranslation(wr.getReward(), this.file.getLocale(), TranslationKey.TITLE)
               .ifPresent(
                  title -> NetworkHelper.sendToAll(
                     server, SyncTranslationMessageToClient.create(wr.getReward(), this.file.getLocale(), TranslationKey.TITLE, title)
                  )
               )
         );
   }

   @Override
   public void onCreated() {
      this.file.addRewardTable(this);
   }

   public String getFilename() {
      if (this.filename.isEmpty()) {
         this.filename = getCodeString(this);
      }

      return this.filename;
   }

   @Override
   public Optional<String> getPath() {
      return Optional.of("reward_tables/" + this.getFilename() + ".snbt");
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Component getAltTitle() {
      return (Component)(this.weightedRewards.size() == 1
         ? this.weightedRewards.get(0).getReward().getTitle()
         : Component.translatable("ftbquests.reward_table"));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public Icon getAltIcon() {
      if (this.lootCrate != null) {
         return ItemIcon.getItemIcon(this.lootCrate.createStack());
      }

      if (this.weightedRewards.isEmpty()) {
         return Icons.DICE;
      }

      List<Icon> icons = this.weightedRewards.stream().map(reward -> reward.getReward().getIcon()).collect(Collectors.toList());
      return IconAnimation.fromList(icons, false);
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void onEditButtonClicked(Runnable gui) {
      new EditRewardTableScreen(gui, this, editedReward -> {
         NetworkManager.sendToServer(EditObjectMessage.forQuestObject(editedReward));
         this.clearCachedData();
      }).openGui();
   }

   public void addMouseOverText(TooltipList list, boolean includeWeight, boolean includeEmpty) {
      if (ClientQuestFile.INSTANCE.canEdit() || !this.hideTooltip) {
         float totalWeight = this.getTotalWeight(includeEmpty);
         if (includeWeight && includeEmpty && this.emptyWeight > 0.0F) {
            addItem(list, Component.translatable("ftbquests.reward_table.nothing"), this.emptyWeight, totalWeight);
         }

         List<WeightedReward> sortedRewards = this.weightedRewards.stream().sorted().toList();
         BaseScreen gui = (BaseScreen)ClientUtils.getCurrentGuiAs(BaseScreen.class);
         int maxLines = gui == null ? 12 : (gui.height - 20) / (gui.getTheme().getFontHeight() + 2);
         int nRewards = sortedRewards.size();
         int start = nRewards > maxLines ? (int)(FTBQuestsClient.getClientLevel().getGameTime() / 10L % nRewards) : 0;
         int nLines = Math.min(maxLines, nRewards);

         for (int idx = 0; idx < nLines; idx++) {
            WeightedReward wr = sortedRewards.get((start + idx) % nRewards);
            if (includeWeight) {
               addItem(list, wr.getReward().getTitle(), wr.getWeight(), totalWeight);
            } else {
               list.add(Component.literal("- ").withStyle(ChatFormatting.GRAY).append(wr.getReward().getTitle()));
            }
         }
      }
   }

   private static void addItem(TooltipList list, Component text, float weight, float totalWeight) {
      list.add(
         Component.literal("- ")
            .withStyle(ChatFormatting.GRAY)
            .append(text)
            .append(Component.literal(" [" + WeightedReward.chanceString(weight, totalWeight) + "]").withStyle(ChatFormatting.DARK_GRAY))
      );
   }

   @Override
   public Set<RecipeModHelper.Components> componentsToRefresh() {
      return EnumSet.of(RecipeModHelper.Components.LOOT_CRATES);
   }

   public void addReward(WeightedReward weightedReward) {
      this.weightedRewards.add(weightedReward);
   }

   public void removeReward(WeightedReward weightedReward) {
      this.weightedRewards.remove(weightedReward);
   }

   public WeightedReward makeWeightedItemReward(ItemStack stack, float weight) {
      return new WeightedReward(new ItemReward(0L, this.fakeQuest, stack), weight);
   }

   public LootCrate toggleLootCrate() {
      if (this.lootCrate == null) {
         this.lootCrate = new LootCrate(this, true);
      } else {
         this.lootCrate = null;
      }

      return this.lootCrate;
   }

   public boolean shouldShowTooltip() {
      return !this.hideTooltip;
   }

   public RewardTable copy() {
      RewardTable copy = QuestObjectBase.copy(this, () -> new RewardTable(this.id, this.getFile()));
      if (copy != null) {
         copy.setRawTitle(this.getRawTitle());
         copy.weightedRewards.clear();
         this.getWeightedRewards().forEach(wr -> copy.weightedRewards.add(wr.copy()));
      }

      return copy;
   }
}
