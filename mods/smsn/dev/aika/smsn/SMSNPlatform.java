package dev.aika.smsn;

import dev.aika.smsn.neoforge.SMSNPlatformImpl;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.injectables.annotations.ExpectPlatform.Transformed;
import java.nio.file.Path;
import java.util.Map;

public class SMSNPlatform {
   @ExpectPlatform
   @Transformed
   public static boolean isModLoaded(String modId) {
      return SMSNPlatformImpl.isModLoaded(modId);
   }

   @ExpectPlatform
   @Transformed
   public static Path getConfigDir() {
      return SMSNPlatformImpl.getConfigDir();
   }

   @ExpectPlatform
   @Transformed
   public static Map<String, String> getMixinClassNames() {
      return SMSNPlatformImpl.getMixinClassNames();
   }
}
