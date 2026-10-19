package org.anti_ad.mc.ipnext.profiles.config;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.a.b.a.a.a.R;
import org.anti_ad.mc.common.gen.ProfilesParser;

// $VF: synthetic class
final class ProfilesConfig$getProfiles$2 extends FunctionReferenceImpl implements Function1 {
   public static final ProfilesConfig$getProfiles$2 INSTANCE = new ProfilesConfig$getProfiles$2();

   ProfilesConfig$getProfiles$2() {
      super(1, ProfilesParser.class, "<init>", "<init>(Lorg/antlr/v4/runtime/TokenStream;)V", 0);
   }

   public final ProfilesParser invoke(R var1) {
      return new ProfilesParser(var1);
   }
}
