package net.p3pp3rf1y.sophisticatedbackpacks.upgrades.inception;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;
import net.p3pp3rf1y.sophisticatedcore.upgrades.IUpgradeWrapperAccessor;

public class InceptionWrapperAccessor implements IUpgradeWrapperAccessor {
   private final Map<Class<?>, List<?>> interfaceWrappers = new HashMap<>();
   private final Map<Class<?>, List<?>> mainBackpackInterfaceWrappers = new HashMap<>();
   private final IStorageWrapper backpackWrapper;
   private final SubBackpacksHandler subBackpacksHandler;

   public InceptionWrapperAccessor(IStorageWrapper backpackWrapper, SubBackpacksHandler subBackpacksHandler) {
      this.backpackWrapper = backpackWrapper;
      this.subBackpacksHandler = subBackpacksHandler;
      this.addRefreshCallbacks(subBackpacksHandler.getSubBackpacks());
      subBackpacksHandler.addBeforeRefreshListener(this::removeCallBacks);
      subBackpacksHandler.addRefreshListener(this::clearCacheAndAddCallBacks);
   }

   private void clearCacheAndAddCallBacks(Collection<IStorageWrapper> subbackpacks) {
      this.clearCache();
      this.addRefreshCallbacks(subbackpacks);
   }

   private void addRefreshCallbacks(Collection<IStorageWrapper> subbackpacks) {
      subbackpacks.forEach(sb -> sb.getUpgradeHandler().setRefreshCallBack(this::clearCache));
   }

   public <T> List<T> getWrappersThatImplement(Class<T> upgradeClass) {
      return (List<T>)this.interfaceWrappers.computeIfAbsent(upgradeClass, this::collectListOfWrappersThatImplement);
   }

   public <T> List<T> getWrappersThatImplementFromMainStorage(Class<T> upgradeClass) {
      return (List<T>)this.mainBackpackInterfaceWrappers
         .computeIfAbsent(upgradeClass, this.backpackWrapper.getUpgradeHandler()::getListOfWrappersThatImplement);
   }

   public void onBeforeDeconstruct() {
      this.removeCallBacks(this.subBackpacksHandler.getSubBackpacks());
   }

   private <T> List<T> collectListOfWrappersThatImplement(Class<T> upgradeClass) {
      List<T> ret = new ArrayList<>(this.backpackWrapper.getUpgradeHandler().getListOfWrappersThatImplement(upgradeClass));
      this.subBackpacksHandler.getSubBackpacks().forEach(sbp -> ret.addAll(sbp.getUpgradeHandler().getWrappersThatImplement(upgradeClass)));
      return ret;
   }

   private void removeCallBacks(Collection<IStorageWrapper> subBackpacksHandler) {
      subBackpacksHandler.forEach(sb -> sb.getUpgradeHandler().removeRefreshCallback());
   }

   public void clearCache() {
      this.interfaceWrappers.clear();
   }
}
