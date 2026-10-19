package org.anti_ad.mc.ipnext.profiles.config;

import java.util.List;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import org.anti_ad.mc.common.annotation.MayThrow;
import org.anti_ad.mc.common.gen.ProfilesParser;
import org.anti_ad.mc.common.gen.ProfilesParser$ScriptContext;
import org.anti_ad.mc.ipnext.Log;
import org.anti_ad.mc.ipnext.parser.AntlrExtensionsKt;
import org.jetbrains.annotations.NotNull;

@SourceDebugExtension(
   "SMAP\nProfilesConfigParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfilesConfig\n+ 2 common.kt\norg/anti_ad/mc/common/extensions/CommonKt\n+ 3 _Collections.kt\nkotlin/collections/CollectionsKt___CollectionsKt\n*L\n1#1,292:1\n42#2,12:293\n1869#3,2:305\n*S KotlinDebug\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfilesConfig\n*L\n41#1:293,12\n55#1:305,2\n*E\n"
)
public final class ProfilesConfig {
   @NotNull
   public static final ProfilesConfig INSTANCE = new ProfilesConfig();

   private ProfilesConfig() {
   }

   @MayThrow
   public final void parseRuleDefinition(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Log var2 = Log.INSTANCE;

      try {
         ProfilesConfigParserKt.dump(INSTANCE.getProfiles(var1));
      } catch (Throwable var3) {
         var1 = var3.toString();
         var2.warn(var1);
      }
   }

   @MayThrow
   @NotNull
   public final List getProfiles(@NotNull String var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      ProfilesParser$ScriptContext var10000 = ((ProfilesParser)AntlrExtensionsKt.parseBy(
            var1, ProfilesConfig$getProfiles$1.INSTANCE, ProfilesConfig$getProfiles$2.INSTANCE, 0
         ))
         .script();
      Intrinsics.checkNotNullExpressionValue(var10000, "");
      return ProfilesConfigParserKt.access$toProfileList(var10000);
   }

   @NotNull
   public final String asString(@NotNull List var1) {
      Intrinsics.checkNotNullParameter(var1, "");
      Object var2 = "";

      for (ProfileData var3 : var1) {
         var2 = var2 + var3 + "\n";
      }

      return (String)var2;
   }
}
