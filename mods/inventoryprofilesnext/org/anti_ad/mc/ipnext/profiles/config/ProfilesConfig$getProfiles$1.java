package org.anti_ad.mc.ipnext.profiles.config;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.FunctionReferenceImpl;
import org.anti_ad.a.b.a.a.a.g;
import org.anti_ad.mc.common.gen.ProfilesLexer;

// $VF: synthetic class
final class ProfilesConfig$getProfiles$1 extends FunctionReferenceImpl implements Function1 {
   public static final ProfilesConfig$getProfiles$1 INSTANCE = new ProfilesConfig$getProfiles$1();

   ProfilesConfig$getProfiles$1() {
      super(1, ProfilesLexer.class, "<init>", "<init>(Lorg/antlr/v4/runtime/CharStream;)V", 0);
   }

   public final ProfilesLexer invoke(g var1) {
      return new ProfilesLexer(var1);
   }
}
