package org.anti_ad.mc.ipnext.profiles.config;

import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.SourceDebugExtension;
import kotlin.text.StringsKt;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@SourceDebugExtension(
   "SMAP\nProfilesConfigParser.kt\nKotlin\n*S Kotlin\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfileComponentData\n+ 2 VanillaAccessors.kt\norg/anti_ad/mc/ipnext/ingame/VanillaAccessorsKt\n*L\n1#1,292:1\n168#2:293\n*S KotlinDebug\n*F\n+ 1 ProfilesConfigParser.kt\norg/anti_ad/mc/ipnext/profiles/config/ProfileComponentData\n*L\n164#1:293\n*E\n"
)
public final class ProfileComponentData {
   @NotNull
   private final ResourceLocation id;
   @NotNull
   private final Tag componentNbt;

   public ProfileComponentData(@NotNull ResourceLocation var1, @NotNull Tag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      super();
      this.id = var1;
      this.componentNbt = var2;
   }

   @NotNull
   public final ResourceLocation getId() {
      return this.id;
   }

   @NotNull
   public final Tag getComponentNbt() {
      return this.componentNbt;
   }

   @NotNull
   @Override
   public final String toString() {
      ResourceLocation var10000 = this.id;
      String var10001 = this.componentNbt.getAsString();
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      return "\""
         + var10000
         + "\"(\""
         + StringsKt.replace$default(StringsKt.replace$default(var10001, "\\", "\\\\", false, 4, null), "\"", "\\\"", false, 4, null)
         + "\")";
   }

   @NotNull
   public final ResourceLocation component1() {
      return this.id;
   }

   @NotNull
   public final Tag component2() {
      return this.componentNbt;
   }

   @NotNull
   public final ProfileComponentData copy(@NotNull ResourceLocation var1, @NotNull Tag var2) {
      Intrinsics.checkNotNullParameter(var1, "");
      Intrinsics.checkNotNullParameter(var2, "");
      return new ProfileComponentData(var1, var2);
   }

   @Override
   public final int hashCode() {
      return this.id.hashCode() * 31 + this.componentNbt.hashCode();
   }

   @Override
   public final boolean equals(@Nullable Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof ProfileComponentData var2)) {
         return false;
      } else {
         return !Intrinsics.areEqual(this.id, var2.id) ? false : Intrinsics.areEqual(this.componentNbt, var2.componentNbt);
      }
   }
}
