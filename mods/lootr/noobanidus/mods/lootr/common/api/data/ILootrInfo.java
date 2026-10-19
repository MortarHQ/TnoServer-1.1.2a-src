package noobanidus.mods.lootr.common.api.data;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Component.Serializer;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.phys.Vec3;
import noobanidus.mods.lootr.common.api.BuiltInLootrTypes;
import noobanidus.mods.lootr.common.api.ILootrType;
import noobanidus.mods.lootr.common.api.LootrAPI;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.ApiStatus.Internal;

@Internal
public interface ILootrInfo {
   Set<Class<?>> WARNED_CLASSES = new HashSet<>();

   @Deprecated
   @Nullable
   LootrBlockType getInfoBlockType();

   @Deprecated
   @Nullable
   ILootrInfo.LootrInfoType getInfoType();

   @Nullable
   ILootrType getInfoNewType();

   default LootFiller getDefaultFiller() {
      ILootrType type = this.getInfoNewType();
      return type == null ? DefaultLootFiller.getInstance() : type.getDefaultFiller();
   }

   default boolean canRefresh() {
      ILootrType type = this.getInfoNewType();
      return type == null ? true : type.canRefresh();
   }

   default boolean canDecay() {
      ILootrType type = this.getInfoNewType();
      return type == null ? true : type.canDecay();
   }

   default boolean canBeMarkedUnopened() {
      ILootrType type = this.getInfoNewType();
      return type == null ? true : type.canBeMarkedUnopened();
   }

   default boolean canDropContentsWhenBroken() {
      ILootrType type = this.getInfoNewType();
      return type == null ? true : type.canDropContentsWhenBroken();
   }

   @Deprecated
   @Nullable
   default Block getReplacementBlock() {
      ILootrType type = this.getInfoNewType();
      return type == null ? null : type.getReplacementBlock();
   }

   @Deprecated
   @Nullable
   default EntityType<?> getReplacementEntity() {
      ILootrType type = this.getInfoNewType();
      return type == null ? null : type.getReplacementEntity();
   }

   @Deprecated
   default boolean isEntity() {
      ILootrType type = this.getInfoNewType();
      return type == null ? false : type.isEntity();
   }

   @NotNull
   default Vec3 getInfoVec() {
      return Vec3.atCenterOf(this.getInfoPos());
   }

   @NotNull
   UUID getInfoUUID();

   String getInfoKey();

   static String generateInfoKey(UUID id) {
      String idString = id.toString();
      return "lootr/" + idString.charAt(0) + "/" + idString.substring(0, 2) + "/" + idString;
   }

   boolean hasBeenOpened();

   boolean isPhysicallyOpen();

   @NotNull
   BlockPos getInfoPos();

   @Nullable
   Component getInfoDisplayName();

   @NotNull
   ResourceKey<Level> getInfoDimension();

   int getInfoContainerSize();

   @Nullable
   NonNullList<ItemStack> getInfoReferenceInventory();

   default void setInfoReferenceInventory(NonNullList<ItemStack> reference) {
      throw new NotImplementedException(this + " does not declare setInfoReferenceInventory.");
   }

   default boolean canPlayerOpen(ServerPlayer player) {
      return true;
   }

   default void informPlayerCannotOpen(ServerPlayer player) {
   }

   boolean isInfoReferenceInventory();

   default boolean isInfoReferenceInventoryInternal(boolean isReferenceInventory) {
      if (isReferenceInventory && this.getInfoLootTable() != null) {
         LootrAPI.LOG
            .error(
               "Lootr container {} at {} in {} has both a loot table and a custom inventory. This is not supported and may cause issues.",
               this,
               this.getInfoPos(),
               this.getInfoDimension()
            );
      }

      return isReferenceInventory;
   }

   @Nullable
   ResourceKey<LootTable> getInfoLootTable();

   long getInfoLootSeed();

   default Level getInfoLevel() {
      if (!WARNED_CLASSES.contains(this.getClass())) {
         LootrAPI.LOG.error("Class {} does not implement `getInfoLevel`! Falling back on `getDefaultLevel`.", this.getClass().getName());
         WARNED_CLASSES.add(this.getClass());
      }

      return this.getDefaultLevel();
   }

   @Nullable
   default Level getDefaultLevel() {
      MinecraftServer server = LootrAPI.getServer();
      return server == null ? null : server.getLevel(this.getInfoDimension());
   }

   @Nullable
   default Container getInfoContainer() {
      if (!(this.getInfoLevel() instanceof ServerLevel level && !level.isClientSide())) {
         return null;
      } else if (this.getInfoNewType() != null) {
         return this.getInfoNewType().getContainer(this, level);
      } else if (level.getBlockEntity(this.getInfoPos()) instanceof Container container) {
         return container;
      } else if (level.getEntity(this.getInfoUUID()) instanceof Container container) {
         return container;
      } else {
         LootrAPI.LOG.warn("Unable to guess container type for LootrInfo with key '{}'", this.getInfoKey());
         return null;
      }
   }

   default NonNullList<ItemStack> buildInitialInventory() {
      return NonNullList.withSize(this.getInfoContainerSize(), ItemStack.EMPTY);
   }

   default void saveInfoToTag(CompoundTag tag, Provider provider) {
      if (this.getInfoType() != null) {
         tag.putInt("type", this.getInfoType().ordinal());
      }

      if (this.getInfoNewType() != null) {
         tag.putString("newType", this.getInfoNewType().getName());
      }

      if (this.getInfoBlockType() != null) {
         tag.putInt("blockType", this.getInfoBlockType().ordinal());
      }

      tag.put("position", NbtUtils.writeBlockPos(this.getInfoPos()));
      tag.putString("key", this.getInfoKey());
      tag.putString("dimension", this.getInfoDimension().location().toString());
      tag.putUUID("uuid", this.getInfoUUID());
      tag.putInt("size", this.getInfoContainerSize());
      if (this.getInfoLootTable() != null) {
         tag.putString("table", this.getInfoLootTable().location().toString());
         tag.putLong("seed", this.getInfoLootSeed());
      }

      if (this.getInfoDisplayName() != null) {
         tag.putString("name", Serializer.toJson(this.getInfoDisplayName(), provider));
      }

      if (this.isInfoReferenceInventory()) {
         if (this.getInfoReferenceInventory() != null) {
            tag.putInt("referenceSize", this.getInfoReferenceInventory().size());
            tag.put("reference", ContainerHelper.saveAllItems(new CompoundTag(), this.getInfoReferenceInventory(), true, provider));
         } else {
            LootrAPI.LOG
               .error(
                  "Info at {} with ID {} is marked as reference inventory but getInfoReferenceInventory() returns null. This may cause issues.",
                  this.getInfoPos(),
                  this.getInfoKey()
               );
         }
      }
   }

   static ILootrInfo loadInfoFromTag(CompoundTag tag, Provider provider) {
      ILootrType type = null;
      if (tag.contains("newType", 8)) {
         type = LootrAPI.getType(tag.getString("newType"));
         if (type == null) {
            LootrAPI.LOG.error("Couldn't find LootrType '{}' when loading LootrInfo from tag: {}", tag.getString("newType"), tag);
            throw new IllegalStateException("Couldn't find LootrType '" + tag.getString("newType") + "' when loading LootrInfo from tag: " + tag);
         }
      }

      if (type == null) {
         if (tag.contains("blockType", 3)) {
            LootrBlockType oldType = LootrBlockType.values()[tag.getInt("blockType")];
            type = BuiltInLootrTypes.fromLegacy(oldType);
         } else if (tag.contains("type", 3)) {
            type = BuiltInLootrTypes.CHEST;
         } else if (tag.contains("entity") && tag.getBoolean("entity")) {
            type = BuiltInLootrTypes.MINECART;
         }
      }

      BlockPos pos = NbtUtils.readBlockPos(tag, "position").orElse(BlockPos.ZERO);
      UUID uuid = tag.getUUID("uuid");
      ResourceKey<Level> dimension = ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse(tag.getString("dimension")));
      int size = tag.getInt("size");
      Component name = null;
      if (tag.contains("name")) {
         name = Serializer.fromJson(tag.getString("name"), provider);
      }

      NonNullList<ItemStack> reference = null;
      if (tag.contains("reference") && tag.contains("referenceSize")) {
         reference = NonNullList.withSize(tag.getInt("referenceSize"), ItemStack.EMPTY);
         ContainerHelper.loadAllItems(tag.getCompound("reference"), reference, provider);
         type = BuiltInLootrTypes.INVENTORY;
      }

      ResourceKey<LootTable> table = null;
      long seed = -1L;
      if (tag.contains("table")) {
         table = ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.parse(tag.getString("table")));
         seed = tag.getLong("seed");
      }

      if (type == null) {
         LootrAPI.LOG.error("Couldn't determine LootrType when loading LootrInfo from tag, guessing chest: {}", tag);
         type = BuiltInLootrTypes.CHEST;
      }

      return new BaseLootrInfo(null, null, type, uuid, generateInfoKey(uuid), pos, name, dimension, size, reference, table, seed);
   }

   @Deprecated
   enum LootrInfoType {
      CONTAINER_BLOCK_ENTITY,
      CONTAINER_ENTITY;
   }
}
