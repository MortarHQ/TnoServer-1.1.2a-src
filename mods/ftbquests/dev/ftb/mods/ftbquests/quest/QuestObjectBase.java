package dev.ftb.mods.ftbquests.quest;

import dev.architectury.networking.NetworkManager;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.config.StringConfig;
import dev.ftb.mods.ftblibrary.config.Tristate;
import dev.ftb.mods.ftblibrary.config.ui.EditConfigScreen;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.math.Bits;
import dev.ftb.mods.ftblibrary.snbt.SNBTCompoundTag;
import dev.ftb.mods.ftbquests.client.ClientQuestFile;
import dev.ftb.mods.ftbquests.client.ConfigIconItemStack;
import dev.ftb.mods.ftbquests.integration.RecipeModHelper;
import dev.ftb.mods.ftbquests.item.CustomIconItem;
import dev.ftb.mods.ftbquests.net.EditObjectMessage;
import dev.ftb.mods.ftbquests.net.SyncTranslationMessageToServer;
import dev.ftb.mods.ftbquests.quest.theme.property.ThemeProperties;
import dev.ftb.mods.ftbquests.quest.translation.TranslationKey;
import dev.ftb.mods.ftbquests.registry.ModDataComponents;
import dev.ftb.mods.ftbquests.registry.ModItems;
import dev.ftb.mods.ftbquests.util.NetUtils;
import dev.ftb.mods.ftbquests.util.ProgressChange;
import dev.ftb.mods.ftbquests.util.TextUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class QuestObjectBase implements Comparable<QuestObjectBase> {
   private static final Pattern TAG_PATTERN = Pattern.compile("^[a-z0-9_]*$");
   private static Tristate sendNotifications = Tristate.DEFAULT;
   public final long id;
   protected boolean invalid = false;
   private ItemStack rawIcon = ItemStack.EMPTY;
   private List<String> tags = new ArrayList<>(0);
   private Icon cachedIcon = null;
   private Component cachedTitle = null;
   private Set<String> cachedTags = null;
   protected EnumMap<TranslationKey, String> protoTranslations = new EnumMap<>(TranslationKey.class);

   public QuestObjectBase(long id) {
      this.id = id;
   }

   public long getId() {
      return this.id;
   }

   public static boolean isNull(@Nullable QuestObjectBase object) {
      return object == null || object.invalid;
   }

   public static long getID(@Nullable QuestObjectBase object) {
      return isNull(object) ? 0L : object.id;
   }

   public static String getCodeString(long id) {
      return String.format("%016X", id);
   }

   public static String getCodeString(@Nullable QuestObjectBase object) {
      return getCodeString(getID(object));
   }

   public static boolean shouldSendNotifications() {
      return sendNotifications.get(true);
   }

   public static ItemStack itemOrMissingFromNBT(Tag tag, Provider provider) {
      CompoundTag compoundTag = processItemTagData(tag);
      return compoundTag.isEmpty() ? ItemStack.EMPTY : ItemStack.parse(provider, compoundTag).orElse(createMissing(compoundTag));
   }

   public static ItemStack singleItemOrMissingFromNBT(Tag tag, Provider provider) {
      CompoundTag compoundTag = processItemTagData(tag);
      return compoundTag.isEmpty()
         ? ItemStack.EMPTY
         : ItemStack.SINGLE_ITEM_CODEC.parse(provider.createSerializationContext(NbtOps.INSTANCE), compoundTag).result().orElse(createMissing(compoundTag));
   }

   private static CompoundTag processItemTagData(Tag tag) {
      if (tag instanceof StringTag s) {
         return (CompoundTag)Util.make(new CompoundTag(), t -> t.putString("id", s.getAsString()));
      } else if (tag instanceof CompoundTag c) {
         return !c.contains("Count") && !c.contains("tag") ? c : (CompoundTag)Util.make(new CompoundTag(), t -> {
            t.putString("id", c.getString("id"));
            int count = c.getInt("Count");
            if (count != 0) {
               t.putInt("count", count);
            }
         });
      } else {
         return new CompoundTag();
      }
   }

   private static ItemStack createMissing(CompoundTag tag) {
      String id = tag.getString("id");
      int count = Math.max(1, tag.getInt("count"));
      String text = count == 1 ? id : count + "x " + id;
      return (ItemStack)Util.make(
         new ItemStack((ItemLike)ModItems.MISSING_ITEM.get()), stack -> stack.set((DataComponentType)ModDataComponents.MISSING_ITEM_DESC.get(), text)
      );
   }

   public final boolean isValid() {
      return !this.invalid;
   }

   public final void setRawIcon(ItemStack rawIcon) {
      this.rawIcon = rawIcon;
   }

   public String getRawTitle() {
      return !this.getQuestFile().isServerSide() && this.protoTranslations.containsKey(TranslationKey.TITLE)
         ? this.protoTranslations.get(TranslationKey.TITLE)
         : this.getQuestFile().getTranslationManager().getStringTranslation(this, this.getQuestFile().getLocale(), TranslationKey.TITLE).orElse("");
   }

   public void setRawTitle(String rawTitle) {
      this.setTranslatableValue(TranslationKey.TITLE, rawTitle);
      this.cachedTitle = null;
   }

   protected final void setTranslatableValue(TranslationKey translationKey, String value) {
      if (this.id != 0L) {
         String locale = this.getQuestFile().getLocale();
         this.getQuestFile().getTranslationManager().addTranslation(this, locale, translationKey, value);
         if (!this.getQuestFile().isServerSide()) {
            NetworkManager.sendToServer(SyncTranslationMessageToServer.create(this, locale, translationKey, value));
         }
      } else if (!this.getQuestFile().isServerSide()) {
         this.protoTranslations.put(translationKey, value);
      }
   }

   protected final void setTranslatableValue(TranslationKey translationKey, List<String> value) {
      if (this.id != 0L) {
         String locale = this.getQuestFile().getLocale();
         this.getQuestFile().getTranslationManager().addTranslation(this, locale, translationKey, value);
         if (!this.getQuestFile().isServerSide()) {
            NetworkManager.sendToServer(SyncTranslationMessageToServer.create(this, locale, translationKey, value));
         }
      }
   }

   public final String getProtoTranslation(TranslationKey key) {
      return this.protoTranslations.getOrDefault(key, "");
   }

   public final void modifyTranslatableListValue(TranslationKey translationKey, Consumer<List<String>> setter) {
      if (translationKey.isListVal()) {
         List<String> mutable = this.getQuestFile()
            .getTranslationManager()
            .getStringListTranslation(this, this.getQuestFile().getLocale(), translationKey)
            .map(ArrayList::new)
            .orElse(new ArrayList<>());
         setter.accept(mutable);
         this.setTranslatableValue(translationKey, List.copyOf(mutable));
      }
   }

   public static long parseCodeString(String id) {
      if (!id.isEmpty() && !id.equals("-")) {
         try {
            return Long.parseLong(id.charAt(0) == '#' ? id.substring(1) : id, 16);
         } catch (Exception ex) {
            return 0L;
         }
      } else {
         return 0L;
      }
   }

   public static Optional<Long> parseHexId(String id) {
      try {
         return Optional.of(Long.parseLong(id, 16));
      } catch (NumberFormatException e) {
         return Optional.empty();
      }
   }

   public static Optional<String> titleToID(String s) {
      s = s.replace(' ', '_').replaceAll("\\W", "").toLowerCase().trim();

      while (s.startsWith("_")) {
         s = s.substring(1);
      }

      while (s.endsWith("_")) {
         s = s.substring(0, s.length() - 1);
      }

      return s.isEmpty() ? Optional.empty() : Optional.of(s);
   }

   public final String getCodeString() {
      return getCodeString(this.id);
   }

   @Override
   public final String toString() {
      return this.getCodeString();
   }

   @Override
   public final boolean equals(Object object) {
      return object == this;
   }

   @Override
   public final int hashCode() {
      return Long.hashCode(this.id);
   }

   public abstract QuestObjectType getObjectType();

   public abstract BaseQuestFile getQuestFile();

   public Set<String> getTags() {
      if (this.tags.isEmpty()) {
         return Collections.emptySet();
      }

      if (this.cachedTags == null) {
         this.cachedTags = new LinkedHashSet<>(this.tags);
      }

      return this.cachedTags;
   }

   public boolean hasTag(String tag) {
      return !this.tags.isEmpty() && this.getTags().contains(tag);
   }

   public void forceProgress(TeamData teamData, ProgressChange progressChange) {
   }

   public final void forceProgressRaw(TeamData teamData, ProgressChange progressChange) {
      if (!teamData.isLocked()) {
         teamData.clearCachedProgress();
         sendNotifications = progressChange.shouldNotify() ? Tristate.TRUE : Tristate.FALSE;
         this.forceProgress(teamData, progressChange);
         sendNotifications = Tristate.DEFAULT;
         teamData.clearCachedProgress();
         teamData.markDirty();
      }
   }

   @Nullable
   public Chapter getQuestChapter() {
      return null;
   }

   public long getParentID() {
      return 1L;
   }

   public void writeData(CompoundTag nbt, Provider provider) {
      if (!this.rawIcon.isEmpty()) {
         ItemStack.SINGLE_ITEM_CODEC.encodeStart(provider.createSerializationContext(NbtOps.INSTANCE), this.rawIcon).ifSuccess(t -> nbt.put("icon", t));
      }

      if (!this.tags.isEmpty()) {
         nbt.put("tags", (Tag)Util.make(new ListTag(), l -> {
            for (String s : this.tags) {
               l.add(StringTag.valueOf(s));
            }
         }));
      }
   }

   public void readData(CompoundTag nbt, Provider provider) {
      this.rawIcon = singleItemOrMissingFromNBT(nbt.getCompound("icon"), provider);
      ListTag tagsList = nbt.getList("tags", 8);
      this.tags = new ArrayList<>(tagsList.size());

      for (int i = 0; i < tagsList.size(); i++) {
         this.tags.add(tagsList.getString(i));
      }

      if (nbt.contains("custom_id")) {
         this.tags.add(nbt.getString("custom_id"));
      }
   }

   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      int flags = 0;
      flags = Bits.setFlag(flags, 2, !this.rawIcon.isEmpty());
      flags = Bits.setFlag(flags, 4, !this.tags.isEmpty());
      buffer.writeVarInt(flags);
      if (!this.rawIcon.isEmpty()) {
         ItemStack.OPTIONAL_STREAM_CODEC.encode(buffer, this.rawIcon);
      }

      if (!this.tags.isEmpty()) {
         NetUtils.writeStrings(buffer, this.tags);
      }
   }

   public void readNetData(RegistryFriendlyByteBuf buffer) {
      int flags = buffer.readVarInt();
      this.rawIcon = Bits.getFlag(flags, 2) ? (ItemStack)ItemStack.OPTIONAL_STREAM_CODEC.decode(buffer) : ItemStack.EMPTY;
      this.tags = new ArrayList<>(0);
      if (Bits.getFlag(flags, 4)) {
         NetUtils.readStrings(buffer, this.tags);
      }
   }

   protected boolean hasTitleConfig() {
      return true;
   }

   protected boolean hasIconConfig() {
      return true;
   }

   @OnlyIn(Dist.CLIENT)
   public void fillConfigGroup(ConfigGroup config) {
      if (this.hasTitleConfig()) {
         config.addString("title", this.getRawTitle(), this::setRawTitle, "").setNameKey("ftbquests.title").setOrder(-127);
      }

      if (this.hasIconConfig()) {
         ((ConfigIconItemStack)config.add("icon", new ConfigIconItemStack(), this.rawIcon, v -> this.rawIcon = v, ItemStack.EMPTY))
            .setNameKey("ftbquests.icon")
            .setOrder(-126);
      }

      config.addList("tags", this.tags, new StringConfig(TAG_PATTERN), "").setNameKey("ftbquests.tags").setOrder(-125);
   }

   @OnlyIn(Dist.CLIENT)
   public abstract Component getAltTitle();

   @OnlyIn(Dist.CLIENT)
   public abstract Icon getAltIcon();

   @OnlyIn(Dist.CLIENT)
   public final Component getTitle() {
      if (this.cachedTitle != null) {
         return this.cachedTitle.copy();
      }

      if (!this.getRawTitle().isEmpty()) {
         this.cachedTitle = TextUtils.parseRawText(this.getRawTitle(), this.holderLookup());
      } else {
         this.cachedTitle = this.getAltTitle();
      }

      return this.cachedTitle.copy();
   }

   @OnlyIn(Dist.CLIENT)
   public final MutableComponent getMutableTitle() {
      return this.getTitle().copy();
   }

   @OnlyIn(Dist.CLIENT)
   public final Icon getIcon() {
      if (this.cachedIcon == null) {
         if (!this.rawIcon.isEmpty()) {
            this.cachedIcon = CustomIconItem.getIcon(this.rawIcon);
         }

         if (this.cachedIcon == null || this.cachedIcon.isEmpty()) {
            this.cachedIcon = ThemeProperties.ICON.get(this);
         }

         if (this.cachedIcon.isEmpty()) {
            this.cachedIcon = this.getAltIcon();
         }
      }

      return this.cachedIcon;
   }

   public void deleteSelf() {
      this.getQuestFile().remove(this.id);
   }

   public void deleteChildren() {
   }

   @OnlyIn(Dist.CLIENT)
   public void editedFromGUI() {
      ClientQuestFile.INSTANCE.refreshGui();
   }

   public void editedFromGUIOnServer() {
   }

   public void onCreated() {
   }

   public Optional<String> getPath() {
      return Optional.empty();
   }

   public void clearCachedData() {
      this.cachedIcon = null;
      this.cachedTitle = null;
      this.cachedTags = null;
   }

   public ConfigGroup createSubGroup(ConfigGroup group) {
      return group.getOrCreateSubgroup(this.getObjectType().getId());
   }

   @OnlyIn(Dist.CLIENT)
   public void onEditButtonClicked(Runnable gui) {
      final ConfigGroup group = new ConfigGroup("ftbquests", accepted -> {
         gui.run();
         if (accepted && this.validateEditedConfig()) {
            NetworkManager.sendToServer(EditObjectMessage.forQuestObject(this));
         }
      }) {
         public Component getName() {
            MutableComponent type = Component.literal(" [")
               .append(Component.translatable("ftbquests." + QuestObjectBase.this.getObjectType().getId()))
               .append("]")
               .withStyle(QuestObjectBase.this.getObjectType().getColor());
            return Component.empty().append(QuestObjectBase.this.getTitle().copy().withStyle(ChatFormatting.UNDERLINE)).append(type);
         }
      };
      this.fillConfigGroup(this.createSubGroup(group));
      (new EditConfigScreen(group) {
         public Component getTitle() {
            return group.getName();
         }
      }).openGui();
   }

   protected boolean validateEditedConfig() {
      return true;
   }

   public Set<RecipeModHelper.Components> componentsToRefresh() {
      return EnumSet.noneOf(RecipeModHelper.Components.class);
   }

   public static <T extends QuestObjectBase> T copy(T orig, Supplier<T> factory) {
      T copied = (T)factory.get();
      if (copied == null) {
         return null;
      }

      CompoundTag tag = new CompoundTag();
      orig.writeData(tag, orig.holderLookup());
      copied.readData(tag, orig.holderLookup());
      return copied;
   }

   public int compareTo(@NotNull QuestObjectBase other) {
      int typeCmp = Integer.compare(this.getObjectType().ordinal(), other.getObjectType().ordinal());
      return typeCmp == 0 ? this.getTitle().getString().toLowerCase().compareTo(other.getTitle().getString().toLowerCase()) : typeCmp;
   }

   public Provider holderLookup() {
      return this.getQuestFile().holderLookup();
   }

   protected CompoundTag saveItemSingleLine(ItemStack stack) {
      return (CompoundTag)(stack.isEmpty()
         ? new SNBTCompoundTag()
         : (CompoundTag)Util.make(SNBTCompoundTag.of(stack.save(this.holderLookup())), SNBTCompoundTag::singleLine));
   }
}
