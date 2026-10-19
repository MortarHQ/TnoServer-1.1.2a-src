package cc.sighs.oelib.data.spi;

import cc.sighs.oelib.data.api.DataValidator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;

public interface IDataManager {
   <T> void register(Class<T> var1);

   <T> void registerNamespace(Class<T> var1, String var2);

   <T> void registerNamespaceValidator(Class<T> var1, String var2, Class<? extends DataValidator<?>> var3);

   <T> Map<ResourceLocation, T> getAllData(Class<T> var1);

   <T> T getData(Class<T> var1, ResourceLocation var2);

   <T> List<T> getDataList(Class<T> var1);

   <T> Set<T> getCachedData(Class<T> var1, String var2);

   <T> void addToCache(Class<T> var1, String var2, T var3);

   <T> void clearCache(Class<T> var1);

   <T> void updateClientData(Class<T> var1, Map<ResourceLocation, T> var2);

   void updateClientDataRaw(Class<?> var1, Map<ResourceLocation, ?> var2);

   boolean isModLoaded(String var1);

   MinecraftServer getServer();
}
