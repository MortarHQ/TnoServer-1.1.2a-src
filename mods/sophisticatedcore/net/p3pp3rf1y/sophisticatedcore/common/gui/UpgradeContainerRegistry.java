package net.p3pp3rf1y.sophisticatedcore.common.gui;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeItem;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapper;

public class UpgradeContainerRegistry {
   private static final Map<ResourceLocation, UpgradeContainerType<? extends IUpgradeWrapper, ? extends UpgradeContainerBase<?, ?>>> UPGRADE_CONTAINERS = new HashMap<>();

   private UpgradeContainerRegistry() {
   }

   public static void register(
      ResourceLocation upgradeName, UpgradeContainerType<? extends IUpgradeWrapper, ? extends UpgradeContainerBase<?, ?>> containerFactory
   ) {
      UPGRADE_CONTAINERS.put(upgradeName, containerFactory);
   }

   public static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>> Optional<UpgradeContainerBase<W, C>> instantiateContainer(
      Player player, int containerId, W wrapper
   ) {
      ResourceLocation upgradeName = BuiltInRegistries.ITEM.getKey(wrapper.getUpgradeStack().getItem());
      return wrapper.getUpgradeStack().getItem() instanceof IUpgradeItem && !wrapper.hideSettingsTab() && UPGRADE_CONTAINERS.containsKey(upgradeName)
         ? Optional.of(getContainerType(upgradeName).create(player, containerId, wrapper))
         : Optional.empty();
   }

   private static <W extends IUpgradeWrapper, C extends UpgradeContainerBase<W, C>> UpgradeContainerType<W, C> getContainerType(ResourceLocation upgradeName) {
      return (UpgradeContainerType<W, C>)UPGRADE_CONTAINERS.get(upgradeName);
   }
}
