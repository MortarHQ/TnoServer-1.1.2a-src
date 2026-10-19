package net.p3pp3rf1y.sophisticatedcore.inventory;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;
import net.minecraft.world.item.ItemStack;
import net.p3pp3rf1y.sophisticatedcore.SophisticatedCore;
import net.p3pp3rf1y.sophisticatedcore.api.IStorageWrapper;

public class StorageWrapperRepository {
   private static final Cache<ItemStack, IStorageWrapper> stackStorageWrappers = CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).build();
   private static final Cache<UUID, IStorageWrapper> uuidStorageWrappers = CacheBuilder.newBuilder().expireAfterAccess(10L, TimeUnit.MINUTES).build();

   public static <T extends IStorageWrapper> Optional<T> getExistingStorageWrapper(ItemStack stack, Class<T> wrapperClass) {
      IStorageWrapper storageWrapper = (IStorageWrapper)stackStorageWrappers.getIfPresent(stack);
      return wrapperClass.isInstance(storageWrapper) ? Optional.of(wrapperClass.cast(storageWrapper)) : Optional.empty();
   }

   public static <T extends IStorageWrapper> T getStorageWrapper(ItemStack stack, Class<T> wrapperClass, Function<ItemStack, T> factory) {
      IStorageWrapper storageWrapper = (IStorageWrapper)stackStorageWrappers.getIfPresent(stack);
      if (storageWrapper == null) {
         storageWrapper = instantiateWrapper(stack, factory);
         stackStorageWrappers.put(stack, storageWrapper);
      } else if (!wrapperClass.isInstance(storageWrapper)) {
         SophisticatedCore.LOGGER
            .error("StorageWrapperRepository: Wrapper with ItemStack {} is not an instance of {}. Replacing with new instance...", stack, wrapperClass);
         stackStorageWrappers.invalidate(stack);
         storageWrapper = instantiateWrapper(stack, factory);
         stackStorageWrappers.put(stack, storageWrapper);
      }

      return wrapperClass.cast(storageWrapper);
   }

   private static <T extends IStorageWrapper> T instantiateWrapper(ItemStack stack, Function<ItemStack, T> instantiate) {
      return instantiate.apply(stack);
   }

   public static void migrateToUuid(IStorageWrapper storageWrapper, ItemStack stack, UUID storageUuid) {
      stackStorageWrappers.invalidate(stack);
      uuidStorageWrappers.put(storageUuid, storageWrapper);
   }

   public static void clearCache() {
      stackStorageWrappers.invalidateAll();
      uuidStorageWrappers.invalidateAll();
   }
}
