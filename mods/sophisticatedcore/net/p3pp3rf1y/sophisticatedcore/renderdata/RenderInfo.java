package net.p3pp3rf1y.sophisticatedcore.renderdata;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.FloatTag;
import net.minecraft.nbt.IntTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.fluids.FluidStack;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedBatteryUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IRenderedTankUpgrade;
import net.p3pp3rf1y.sophisticatedcore.upgrades.cooking.CookingUpgradeRenderData;
import net.p3pp3rf1y.sophisticatedcore.upgrades.jukebox.JukeboxUpgradeRenderData;
import net.p3pp3rf1y.sophisticatedcore.util.NBTHelper;
import net.p3pp3rf1y.sophisticatedcore.util.RegistryHelper;

public abstract class RenderInfo {
   private static final String TANKS_TAG = "tanks";
   private static final String BATTERY_TAG = "battery";
   private static final String TANK_POSITION_TAG = "position";
   private static final String TANK_INFO_TAG = "info";
   private static final String ITEM_DISPLAY_TAG = "itemDisplay";
   private static final String UPGRADES_TAG = "upgrades";
   private static final String UPGRADE_ITEMS_TAG = "upgradeItems";
   private static final Map<String, UpgradeRenderDataType<?>> RENDER_DATA_TYPES = Map.of(
      CookingUpgradeRenderData.TYPE.getName(), CookingUpgradeRenderData.TYPE, JukeboxUpgradeRenderData.TYPE.getName(), JukeboxUpgradeRenderData.TYPE
   );
   private RenderInfo.ItemDisplayRenderInfo itemDisplayRenderInfo;
   private final Supplier<Runnable> getSaveHandler;
   private final boolean showsCountsAndFillRatios;
   private final List<ItemStack> upgradeItems = new ArrayList<>();
   private final Map<UpgradeRenderDataType<?>, IUpgradeRenderData> upgradeData = new HashMap<>();
   private final Map<TankPosition, IRenderedTankUpgrade.TankRenderInfo> tankRenderInfos = new LinkedHashMap<>();
   @Nullable
   private IRenderedBatteryUpgrade.BatteryRenderInfo batteryRenderInfo = null;
   private Consumer<RenderInfo> renderUpdateChangeListener = ri -> {};

   protected RenderInfo(Supplier<Runnable> getSaveHandler) {
      this(getSaveHandler, false);
   }

   protected RenderInfo(Supplier<Runnable> getSaveHandler, boolean showsCountsAndFillRatios) {
      this.getSaveHandler = getSaveHandler;
      this.showsCountsAndFillRatios = showsCountsAndFillRatios;
      this.itemDisplayRenderInfo = new RenderInfo.ItemDisplayRenderInfo();
   }

   public RenderInfo.ItemDisplayRenderInfo getItemDisplayRenderInfo() {
      return this.itemDisplayRenderInfo;
   }

   public void setUpgradeItems(List<ItemStack> upgradeItems) {
      this.upgradeItems.clear();
      this.upgradeItems.addAll(upgradeItems);
      this.serializeUpgradeItems();
      this.save();
   }

   private void serializeUpgradeItems() {
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      ListTag upgradeItemsTag = new ListTag();

      for (ItemStack upgradeItem : this.upgradeItems) {
         upgradeItemsTag.add((Tag)RegistryHelper.getRegistryAccess().<CompoundTag>map(upgradeItem::saveOptional).orElse(new CompoundTag()));
      }

      renderInfo.put("upgradeItems", upgradeItemsTag);
      this.serializeRenderInfo(renderInfo);
   }

   public <T extends IUpgradeRenderData> void setUpgradeRenderData(UpgradeRenderDataType<T> upgradeRenderDataType, T renderData) {
      this.upgradeData.put(upgradeRenderDataType, renderData);
      this.serializeUpgradeData(upgrades -> upgrades.put(upgradeRenderDataType.getName(), renderData.serializeNBT()));
      this.save();
   }

   public <T extends IUpgradeRenderData> Optional<T> getUpgradeRenderData(UpgradeRenderDataType<T> upgradeRenderDataType) {
      return !this.upgradeData.containsKey(upgradeRenderDataType) ? Optional.empty() : upgradeRenderDataType.cast(this.upgradeData.get(upgradeRenderDataType));
   }

   private void serializeUpgradeData(Consumer<CompoundTag> modifyUpgradesTag) {
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      CompoundTag upgrades = renderInfo.getCompound("upgrades");
      modifyUpgradesTag.accept(upgrades);
      renderInfo.put("upgrades", upgrades);
      this.serializeRenderInfo(renderInfo);
   }

   public void refreshItemDisplayRenderInfo(
      List<RenderInfo.DisplayItem> displayItems,
      List<Integer> inaccessibleSlots,
      List<Integer> infiniteSlots,
      List<Integer> slotCounts,
      List<Float> slotFillRatios
   ) {
      this.itemDisplayRenderInfo = new RenderInfo.ItemDisplayRenderInfo(displayItems, inaccessibleSlots, infiniteSlots, slotCounts, slotFillRatios);
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      renderInfo.put("itemDisplay", this.itemDisplayRenderInfo.serialize());
      this.serializeRenderInfo(renderInfo);
      this.save();
      this.renderUpdateChangeListener.accept(this);
   }

   public void refreshDisplayItemsAndInaccessibleSlots(List<RenderInfo.DisplayItem> displayItems, List<Integer> inaccessibleSlots) {
      this.itemDisplayRenderInfo = new RenderInfo.ItemDisplayRenderInfo(
         displayItems,
         inaccessibleSlots,
         this.itemDisplayRenderInfo.getInfiniteSlots(),
         this.itemDisplayRenderInfo.getSlotCounts(),
         this.itemDisplayRenderInfo.getSlotFillRatios()
      );
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      renderInfo.put("itemDisplay", this.itemDisplayRenderInfo.serialize());
      this.serializeRenderInfo(renderInfo);
      this.save();
      this.renderUpdateChangeListener.accept(this);
   }

   public void refreshSlotCountsFillRatiosAndInfiniteSlots(List<Integer> slotCounts, List<Float> slotFillRatios, List<Integer> infiniteSlots) {
      this.itemDisplayRenderInfo = new RenderInfo.ItemDisplayRenderInfo(
         this.itemDisplayRenderInfo.getDisplayItems(), this.itemDisplayRenderInfo.getInaccessibleSlots(), infiniteSlots, slotCounts, slotFillRatios
      );
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      renderInfo.put("itemDisplay", this.itemDisplayRenderInfo.serialize());
      this.serializeRenderInfo(renderInfo);
      this.save();
   }

   @Deprecated
   public void setDisplayItemsChangeListener(Consumer<RenderInfo> renderUpdateChangeListener) {
      this.renderUpdateChangeListener = renderUpdateChangeListener;
   }

   public void setRenderUpdateChangeListener(Consumer<RenderInfo> renderUpdateChangeListener) {
      this.renderUpdateChangeListener = renderUpdateChangeListener;
   }

   protected void save(boolean triggerRenderUpdateListener) {
      this.getSaveHandler.get().run();
      if (triggerRenderUpdateListener) {
         this.renderUpdateChangeListener.accept(this);
      }
   }

   protected void save() {
      this.save(false);
   }

   protected abstract void serializeRenderInfo(CompoundTag var1);

   protected void deserialize() {
      this.getRenderInfoTag().ifPresent(renderInfoTag -> {
         this.deserializeItemDisplay(renderInfoTag);
         this.deserializeUpgradeItems(renderInfoTag);
         this.deserializeUpgradeData(renderInfoTag);
         this.deserializeTanks(renderInfoTag);
         this.deserializeBattery(renderInfoTag);
      });
   }

   private void deserializeUpgradeItems(CompoundTag renderInfoTag) {
      ListTag upgradeItemsTag = renderInfoTag.getList("upgradeItems", 10);
      this.upgradeItems.clear();
      RegistryHelper.getRegistryAccess().ifPresent(registryAccess -> {
         for (int i = 0; i < upgradeItemsTag.size(); i++) {
            this.upgradeItems.add(ItemStack.parseOptional(registryAccess, upgradeItemsTag.getCompound(i)));
         }
      });
   }

   private void deserializeItemDisplay(CompoundTag renderInfoTag) {
      this.itemDisplayRenderInfo = RenderInfo.ItemDisplayRenderInfo.deserialize(renderInfoTag.getCompound("itemDisplay"));
   }

   protected abstract Optional<CompoundTag> getRenderInfoTag();

   public Map<UpgradeRenderDataType<?>, IUpgradeRenderData> getUpgradeRenderData() {
      return this.upgradeData;
   }

   public void removeAllUpgradeRenderData() {
      this.upgradeData.clear();
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      renderInfo.remove("upgrades");
      this.serializeRenderInfo(renderInfo);
      this.save();
   }

   public void removeUpgradeRenderData(UpgradeRenderDataType<?> type) {
      this.upgradeData.remove(type);
      this.serializeUpgradeData(upgrades -> upgrades.remove(type.getName()));
      this.save();
   }

   private void deserializeUpgradeData(CompoundTag renderInfoTag) {
      CompoundTag upgrades = renderInfoTag.getCompound("upgrades");
      upgrades.getAllKeys().forEach(key -> {
         if (RENDER_DATA_TYPES.containsKey(key)) {
            UpgradeRenderDataType<?> upgradeRenderDataType = RENDER_DATA_TYPES.get(key);
            this.upgradeData.put(upgradeRenderDataType, upgradeRenderDataType.deserialize(upgrades.getCompound(key)));
         }
      });
   }

   public CompoundTag getNbt() {
      return this.getRenderInfoTag().orElse(new CompoundTag());
   }

   public void deserializeFrom(CompoundTag renderInfoNbt) {
      this.resetUpgradeInfo(false);
      this.upgradeData.clear();
      this.serializeRenderInfo(renderInfoNbt);
      this.deserialize();
   }

   public void resetUpgradeInfo(boolean triggerChangeListener) {
      this.tankRenderInfos.clear();
      this.batteryRenderInfo = null;
      this.getRenderInfoTag().ifPresent(renderInfoTag -> {
         renderInfoTag.remove("tanks");
         renderInfoTag.remove("battery");
         this.serializeRenderInfo(renderInfoTag);
      });
      this.save(triggerChangeListener);
   }

   public void setTankRenderInfo(TankPosition tankPosition, IRenderedTankUpgrade.TankRenderInfo tankRenderInfo) {
      boolean updateRender = this.isDifferentFluidOrFillStep(this.tankRenderInfos.get(tankPosition), tankRenderInfo);
      this.tankRenderInfos.put(tankPosition, tankRenderInfo);
      this.serializeTank(tankPosition, tankRenderInfo);
      this.save(updateRender);
   }

   private boolean isDifferentFluidOrFillStep(@Nullable IRenderedTankUpgrade.TankRenderInfo existingInfo, @Nullable IRenderedTankUpgrade.TankRenderInfo newInfo) {
      if ((existingInfo != null || newInfo == null) && (existingInfo == null || newInfo != null)) {
         if (existingInfo == null) {
            return false;
         }

         if (existingInfo.getFluid().isPresent() != newInfo.getFluid().isPresent()) {
            return true;
         }

         if (existingInfo.getFluid().isEmpty()) {
            return false;
         }

         if (!FluidStack.isSameFluidSameComponents(existingInfo.getFluid().get(), newInfo.getFluid().get())) {
            return true;
         }

         int fillSteps = 20;
         return (int)(existingInfo.getFillRatio() * fillSteps) != (int)(newInfo.getFillRatio() * fillSteps);
      } else {
         return true;
      }
   }

   private void deserializeTanks(CompoundTag renderInfoTag) {
      ListTag tanks = renderInfoTag.getList("tanks", 10);

      for (int i = 0; i < tanks.size(); i++) {
         CompoundTag tank = tanks.getCompound(i);
         this.tankRenderInfos
            .put(
               TankPosition.valueOf(tank.getString("position").toUpperCase(Locale.ENGLISH)),
               IRenderedTankUpgrade.TankRenderInfo.deserialize(tank.getCompound("info"))
            );
      }
   }

   private void deserializeBattery(CompoundTag renderInfoTag) {
      this.batteryRenderInfo = NBTHelper.getCompound(renderInfoTag, "battery").map(IRenderedBatteryUpgrade.BatteryRenderInfo::deserialize).orElse(null);
   }

   private void serializeTank(TankPosition tankPosition, IRenderedTankUpgrade.TankRenderInfo tankRenderInfo) {
      CompoundTag tankInfo = tankRenderInfo.serialize();
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      ListTag tanks = renderInfo.getList("tanks", 10);
      boolean infoSet = false;

      for (int i = 0; i < tanks.size(); i++) {
         CompoundTag tank = tanks.getCompound(i);
         if (tank.getString("position").equals(tankPosition.getSerializedName())) {
            tank.put("info", tankInfo);
            infoSet = true;
         }
      }

      if (!infoSet) {
         CompoundTag tankPositionInfo = new CompoundTag();
         tankPositionInfo.putString("position", tankPosition.getSerializedName());
         tankPositionInfo.put("info", tankInfo);
         tanks.add(tankPositionInfo);
         renderInfo.put("tanks", tanks);
      }

      this.serializeRenderInfo(renderInfo);
   }

   public Map<TankPosition, IRenderedTankUpgrade.TankRenderInfo> getTankRenderInfos() {
      return this.tankRenderInfos;
   }

   public Optional<IRenderedBatteryUpgrade.BatteryRenderInfo> getBatteryRenderInfo() {
      return Optional.ofNullable(this.batteryRenderInfo);
   }

   public void setBatteryRenderInfo(IRenderedBatteryUpgrade.BatteryRenderInfo batteryRenderInfo) {
      boolean updateRender = this.isDifferentChargeStep(this.batteryRenderInfo, batteryRenderInfo);
      this.batteryRenderInfo = batteryRenderInfo;
      CompoundTag batteryInfo = batteryRenderInfo.serialize();
      CompoundTag renderInfo = this.getRenderInfoTag().orElse(new CompoundTag());
      renderInfo.put("battery", batteryInfo);
      this.serializeRenderInfo(renderInfo);
      this.save(updateRender);
   }

   private boolean isDifferentChargeStep(
      @Nullable IRenderedBatteryUpgrade.BatteryRenderInfo existingInfo, @Nullable IRenderedBatteryUpgrade.BatteryRenderInfo newInfo
   ) {
      if ((existingInfo != null || newInfo == null) && (existingInfo == null || newInfo != null)) {
         if (existingInfo == null) {
            return false;
         }

         int chargeSteps = 8;
         return (int)(existingInfo.getChargeRatio() * chargeSteps) != (int)(newInfo.getChargeRatio() * chargeSteps);
      } else {
         return true;
      }
   }

   public List<ItemStack> getUpgradeItems() {
      return this.upgradeItems;
   }

   public boolean showsCountsAndFillRatios() {
      return this.showsCountsAndFillRatios;
   }

   public static class DisplayItem {
      private static final String ITEM_TAG = "item";
      private static final String ROTATION_TAG = "rotation";
      private static final String SLOT_INDEX_TAG = "slotIndex";
      private static final String DISPLAY_SIDE_TAG = "displaySide";
      private final ItemStack item;
      private final int rotation;
      private final int slotIndex;
      private final DisplaySide displaySide;

      public DisplayItem(ItemStack item, int rotation, int slotIndex, DisplaySide displaySide) {
         this.item = item;
         this.rotation = rotation;
         this.slotIndex = slotIndex;
         this.displaySide = displaySide;
      }

      private CompoundTag serialize(CompoundTag tag) {
         tag.put("item", RegistryHelper.getRegistryAccess().<Tag>map(this.item::saveOptional).orElse(new CompoundTag()));
         tag.putInt("rotation", this.rotation);
         tag.putInt("slotIndex", this.slotIndex);
         tag.putString("displaySide", this.displaySide.getSerializedName());
         return tag;
      }

      private static RenderInfo.DisplayItem deserialize(CompoundTag tag) {
         return new RenderInfo.DisplayItem(
            RegistryHelper.getRegistryAccess().map(registryAccess -> ItemStack.parseOptional(registryAccess, tag.getCompound("item"))).orElse(ItemStack.EMPTY),
            tag.getInt("rotation"),
            tag.getInt("slotIndex"),
            DisplaySide.fromName(tag.getString("displaySide"))
         );
      }

      public ItemStack getItem() {
         return this.item;
      }

      public int getRotation() {
         return this.rotation;
      }

      public int getSlotIndex() {
         return this.slotIndex;
      }

      public DisplaySide getDisplaySide() {
         return this.displaySide;
      }
   }

   public static class ItemDisplayRenderInfo {
      private static final String ITEMS_TAG = "items";
      private static final String INACCESSIBLE_SLOTS_TAG = "inaccessibleSlots";
      private static final String INFINITE_SLOTS_TAG = "infiniteSlots";
      public static final String SLOT_COUNTS_TAG = "slotCounts";
      public static final String SLOT_FILL_RATIOS_TAG = "slotFillRatios";
      private final List<RenderInfo.DisplayItem> displayItems;
      private final List<Integer> inaccessibleSlots;
      private final List<Integer> infiniteSlots;
      private final List<Integer> slotCounts;
      private final List<Float> slotFillRatios;

      private ItemDisplayRenderInfo(
         RenderInfo.DisplayItem displayItem, List<Integer> inaccessibleSlots, List<Integer> infiniteSlots, List<Integer> slotCounts, List<Float> slotFillRatios
      ) {
         this(List.of(displayItem), inaccessibleSlots, infiniteSlots, slotCounts, slotFillRatios);
      }

      private ItemDisplayRenderInfo(
         List<RenderInfo.DisplayItem> displayItems,
         List<Integer> inaccessibleSlots,
         List<Integer> infiniteSlots,
         List<Integer> slotCounts,
         List<Float> slotFillRatios
      ) {
         this.displayItems = displayItems;
         this.inaccessibleSlots = inaccessibleSlots;
         this.infiniteSlots = infiniteSlots;
         this.slotCounts = slotCounts;
         this.slotFillRatios = slotFillRatios;
      }

      public ItemDisplayRenderInfo() {
         this(new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Collections.emptyList(), Collections.emptyList());
      }

      public CompoundTag serialize() {
         CompoundTag ret = new CompoundTag();
         if (this.displayItems.size() == 1) {
            this.displayItems.get(0).serialize(ret);
         } else if (this.displayItems.size() > 1) {
            NBTHelper.putList(ret, "items", this.displayItems, displayItem -> displayItem.serialize(new CompoundTag()));
         }

         ret.putIntArray("inaccessibleSlots", this.inaccessibleSlots.stream().mapToInt(i -> i).toArray());
         ret.putIntArray("infiniteSlots", this.infiniteSlots.stream().mapToInt(i -> i).toArray());
         ret.putIntArray("slotCounts", this.slotCounts.stream().mapToInt(i -> i).toArray());
         NBTHelper.putList(ret, "slotFillRatios", this.slotFillRatios, FloatTag::valueOf);
         return ret;
      }

      public static RenderInfo.ItemDisplayRenderInfo deserialize(CompoundTag tag) {
         List<Integer> inaccessibleSlots;
         if (tag.getTagType("inaccessibleSlots") == 11) {
            inaccessibleSlots = Arrays.stream(tag.getIntArray("inaccessibleSlots")).boxed().collect(Collectors.toCollection(ArrayList::new));
         } else {
            inaccessibleSlots = (List<Integer>)NBTHelper.<Integer, ArrayList<E>>getCollection(
                  tag, "inaccessibleSlots", (byte)3, t -> Optional.of(((IntTag)t).getAsInt()), ArrayList::new
               )
               .orElseGet(ArrayList::new);
         }

         List<Integer> infiniteSlots = Arrays.stream(tag.getIntArray("infiniteSlots")).boxed().collect(Collectors.toCollection(ArrayList::new));
         List<Integer> slotCounts = Arrays.stream(tag.getIntArray("slotCounts")).boxed().collect(Collectors.toCollection(ArrayList::new));
         List<Float> slotFillRatios = (List<Float>)NBTHelper.<Float, ArrayList<E>>getCollection(
               tag, "slotFillRatios", (byte)5, t -> Optional.of(((FloatTag)t).getAsFloat()), ArrayList::new
            )
            .orElseGet(ArrayList::new);
         if (tag.contains("item")) {
            return new RenderInfo.ItemDisplayRenderInfo(RenderInfo.DisplayItem.deserialize(tag), inaccessibleSlots, infiniteSlots, slotCounts, slotFillRatios);
         } else if (tag.contains("items")) {
            List<RenderInfo.DisplayItem> items = (List<RenderInfo.DisplayItem>)NBTHelper.<RenderInfo.DisplayItem, ArrayList<E>>getCollection(
                  tag, "items", (byte)10, stackTag -> Optional.of(RenderInfo.DisplayItem.deserialize((CompoundTag)stackTag)), ArrayList::new
               )
               .orElseGet(ArrayList::new);
            return new RenderInfo.ItemDisplayRenderInfo(items, inaccessibleSlots, infiniteSlots, slotCounts, slotFillRatios);
         } else {
            return new RenderInfo.ItemDisplayRenderInfo();
         }
      }

      public Optional<RenderInfo.DisplayItem> getDisplayItem() {
         return !this.displayItems.isEmpty() ? Optional.of(this.displayItems.getFirst()) : Optional.empty();
      }

      public List<RenderInfo.DisplayItem> getDisplayItems() {
         return this.displayItems;
      }

      public List<Integer> getInaccessibleSlots() {
         return this.inaccessibleSlots;
      }

      public List<Integer> getSlotCounts() {
         return this.slotCounts;
      }

      public List<Integer> getInfiniteSlots() {
         return this.infiniteSlots;
      }

      public List<Float> getSlotFillRatios() {
         return this.slotFillRatios;
      }
   }
}
