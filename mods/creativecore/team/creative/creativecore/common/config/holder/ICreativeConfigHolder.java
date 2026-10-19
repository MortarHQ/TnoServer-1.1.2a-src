package team.creative.creativecore.common.config.holder;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Collection;
import net.minecraft.core.HolderLookup.Provider;
import team.creative.creativecore.Side;
import team.creative.creativecore.common.config.api.IConfigObject;
import team.creative.creativecore.common.config.core.ICreativeRegistry;
import team.creative.creativecore.common.config.key.ConfigKey;
import team.creative.creativecore.common.config.sync.ConfigSynchronization;

public interface ICreativeConfigHolder extends IConfigObject {
   static void read(Provider provider, ICreativeConfigHolder holder, boolean loadDefault, boolean ignoreRestart, JsonElement element, Side side) {
      if (element.isJsonObject()) {
         holder.load(provider, loadDefault, ignoreRestart, (JsonObject)element, side);
      } else {
         holder.restoreDefault(side, ignoreRestart);
      }
   }

   static JsonElement write(Provider provider, ICreativeConfigHolder holder, boolean saveDefault, boolean ignoreRestart, Side side) {
      return holder.save(provider, saveDefault, ignoreRestart, side);
   }

   ICreativeConfigHolder parent();

   default String getName() {
      return this.path()[this.path().length - 1];
   }

   String[] path();

   Collection<? extends ConfigKey> fields();

   Collection<String> names();

   Object get(String var1);

   ConfigKey getField(String var1);

   void load(Provider var1, boolean var2, boolean var3, JsonObject var4, Side var5);

   JsonObject save(Provider var1, boolean var2, boolean var3, Side var4);

   boolean isEmpty(Side var1);

   boolean isEmptyWithoutForce(Side var1);

   ConfigSynchronization synchronization();

   ICreativeRegistry getRegistry();
}
