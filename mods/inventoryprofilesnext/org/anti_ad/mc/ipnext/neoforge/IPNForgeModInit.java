package org.anti_ad.mc.ipnext.neoforge;

import kotlin.Unit;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.loading.FMLEnvironment;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.ModInfo;
import org.anti_ad.mc.ipnext.event.ClientInitHandler;
import org.jetbrains.annotations.NotNull;

@Mod("inventoryprofilesnext")
public final class IPNForgeModInit {
   @NotNull
   private final Runnable toInit = FMLEnvironment.dist == Dist.CLIENT ? new KotlinClientInit() : new KotlinServerInit();

   public IPNForgeModInit() {
      try {
         this.toInit.run();
         Log.INSTANCE.info("Inventory Profiles Next - init in Kotlin");
      } catch (Throwable var1) {
         var1.printStackTrace();
      }

      ClientInitHandler.INSTANCE.register(IPNForgeModInit::_init_$lambda$0);
   }

   private static final Unit _init_$lambda$0() {
      ModInfo.INSTANCE.setMOD_VERSION(ModInfo.INSTANCE.getModVersion());
      return Unit.INSTANCE;
   }
}
