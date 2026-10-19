package net.p3pp3rf1y.sophisticatedcore.client.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.p3pp3rf1y.sophisticatedcore.client.gui.utils.Position;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerBase;
import net.p3pp3rf1y.sophisticatedcore.common.gui.UpgradeContainerType;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;

public class UpgradeGuiManager {
   private static final Map<UpgradeContainerType<?, ?>, UpgradeGuiManager.IUpgradeSettingsFactory<?, ?>> UPGRADE_TABS = new HashMap<>();
   private static final Map<UpgradeContainerType<?, ?>, UpgradeGuiManager.IUpgradeInventoryPartFactory<?, ?>> UPGRADE_INVENTORY_PARTS = new HashMap<>();

   private UpgradeGuiManager() {
   }

   public static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>, S extends UpgradeSettingsTab<C>> void registerTab(
      UpgradeContainerType<W, C> containerType, UpgradeGuiManager.IUpgradeSettingsFactory<C, S> upgradeSettingsFactory
   ) {
      UPGRADE_TABS.put(containerType, upgradeSettingsFactory);
   }

   public static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>, I extends UpgradeInventoryPartBase<C>> void registerInventoryPart(
      UpgradeContainerType<W, C> containerType, UpgradeGuiManager.IUpgradeInventoryPartFactory<C, I> factory
   ) {
      UPGRADE_INVENTORY_PARTS.put(containerType, factory);
   }

   public static <C extends UpgradeContainerBase<?, ?>> UpgradeSettingsTab<C> getTab(C container, Position position, StorageScreenBase<?> screen) {
      return getTabFactory(container).create(container, position, screen);
   }

   public static <C extends UpgradeContainerBase<?, ?>> Optional<UpgradeInventoryPartBase<C>> getInventoryPart(
      int upgradeSlot, C container, Position position, int height, StorageScreenBase<?> screen
   ) {
      return getInventoryPartFactory(container).map(f -> f.create(upgradeSlot, container, position, height, screen));
   }

   private static <C extends UpgradeContainerBase<?, ?>, S extends UpgradeSettingsTab<C>> UpgradeGuiManager.IUpgradeSettingsFactory<C, S> getTabFactory(
      C container
   ) {
      return (UpgradeGuiManager.IUpgradeSettingsFactory<C, S>)getTabFactory(container.getType());
   }

   private static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>, S extends UpgradeSettingsTab<C>> UpgradeGuiManager.IUpgradeSettingsFactory<C, S> getTabFactory(
      UpgradeContainerType<W, C> containerType
   ) {
      return (UpgradeGuiManager.IUpgradeSettingsFactory<C, S>)UPGRADE_TABS.get(containerType);
   }

   private static <C extends UpgradeContainerBase<?, ?>, I extends UpgradeInventoryPartBase<C>> Optional<UpgradeGuiManager.IUpgradeInventoryPartFactory<C, I>> getInventoryPartFactory(
      C container
   ) {
      return !UPGRADE_INVENTORY_PARTS.containsKey(container.getType())
         ? Optional.empty()
         : Optional.of((UpgradeGuiManager.IUpgradeInventoryPartFactory<C, I>)getInventoryPartFactory(container.getType()));
   }

   private static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>, I extends UpgradeInventoryPartBase<C>> UpgradeGuiManager.IUpgradeInventoryPartFactory<C, I> getInventoryPartFactory(
      UpgradeContainerType<W, C> containerType
   ) {
      return (UpgradeGuiManager.IUpgradeInventoryPartFactory<C, I>)UPGRADE_INVENTORY_PARTS.get(containerType);
   }

   public interface IUpgradeInventoryPartFactory<C extends UpgradeContainerBase<?, ?>, I extends UpgradeInventoryPartBase<C>> {
      I create(int var1, C var2, Position var3, int var4, StorageScreenBase<?> var5);
   }

   public interface IUpgradeSettingsFactory<C extends UpgradeContainerBase<?, ?>, S extends UpgradeSettingsTab<C>> {
      S create(C var1, Position var2, StorageScreenBase<?> var3);
   }
}
