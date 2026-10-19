package twilightforest.network;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.net.URI;
import java.util.Optional;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import twilightforest.beanification.Component;

@Component
public class ModUpdateURLInterceptor {
   private final Logger logger = LogManager.getLogger();
   @Nullable
   private MethodHandle ModInfo_updateJSONURL = null;

   public ModUpdateURLInterceptor() {
      ModList.get()
         .getModFileById("twilightforest")
         .getMods()
         .forEach(
            info -> {
               if (info instanceof ModInfo mod) {
                  if (this.ModInfo_updateJSONURL == null) {
                     try {
                        Field field = ModInfo.class.getDeclaredField("updateJSONURL");
                        field.trySetAccessible();
                        this.ModInfo_updateJSONURL = MethodHandles.lookup().unreflectSetter(field);
                     } catch (Exception ex) {
                        this.logger.error("Error", ex);
                     }
                  }

                  if (this.ModInfo_updateJSONURL != null) {
                     try {
                        this.ModInfo_updateJSONURL
                           .invokeExact(
                              (ModInfo)mod,
                              (Optional)Optional.of(
                                 URI.create(
                                       "https://gh.tamaized.com/TeamTwilight/twilightforest/update.json?m=%s&l=%s&v=%s"
                                          .formatted(
                                             ModList.get().getModFileById("minecraft").getFile().getModFileInfo().versionString(),
                                             "NeoForge",
                                             ModList.get().getModFileById("neoforge").getFile().getModFileInfo().versionString()
                                          )
                                    )
                                    .toURL()
                              )
                           );
                     } catch (Throwable ex) {
                        this.logger.error("Error", ex);
                     }
                  }
               }
            }
         );
   }
}
