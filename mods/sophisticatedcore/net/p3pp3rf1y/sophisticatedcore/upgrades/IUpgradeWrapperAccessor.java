package net.p3pp3rf1y.sophisticatedcore.upgrades;

import java.util.List;

public interface IUpgradeWrapperAccessor {
   <T> List<T> getWrappersThatImplement(Class<T> var1);

   <T> List<T> getWrappersThatImplementFromMainStorage(Class<T> var1);

   void clearCache();

   default void onBeforeDeconstruct() {
   }
}
