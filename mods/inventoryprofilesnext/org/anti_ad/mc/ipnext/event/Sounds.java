package org.anti_ad.mc.ipnext.event;

import kotlin.enums.EnumEntries;
import kotlin.jvm.internal.Intrinsics;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import org.anti_ad.mc.alias.client.ClientExKt;
import org.anti_ad.mc.alias.util.UtilExKt;
import org.anti_ad.mc.common.vanilla.VanillaSound;
import org.jetbrains.annotations.NotNull;

public enum Sounds {
   REFILL_STEP_NOTIFY("tool_refill_step_ping", 0.0F, 2, null);

   @NotNull
   public static final Sounds$Companion Companion = new Sounds$Companion(null);
   @NotNull
   private final String loc;
   private final float defaultPitch;
   @NotNull
   private final SoundEvent soundEvent;

   Sounds(String var3, float var4) {
      this.loc = var3;

      try {
         this.defaultPitch = var4;
      } catch (Throwable var5) {
         var5.printStackTrace();
         throw new ExceptionInInitializerError(var5);
      }

      this.soundEvent = VanillaSound.INSTANCE.createSoundEvent(UtilExKt.IdentifierOf("inventoryprofilesnext", this.loc));
   }

   private final void register() {
   }

   public final void play() {
      VanillaSound var10000 = VanillaSound.INSTANCE;
      SimpleSoundInstance var10001 = ClientExKt.PositionedSoundInstance_Master(this.soundEvent, this.defaultPitch, 0.75F);
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.play((SoundInstance)var10001);
   }

   public final void play(float var1) {
      VanillaSound var10000 = VanillaSound.INSTANCE;
      SimpleSoundInstance var10001 = ClientExKt.PositionedSoundInstance_Master(this.soundEvent, var1, 0.75F);
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.play((SoundInstance)var10001);
   }

   public final void play(float var1, int var2) {
      VanillaSound var10000 = VanillaSound.INSTANCE;
      SimpleSoundInstance var10001 = ClientExKt.PositionedSoundInstance_Master(this.soundEvent, var1, 0.75F);
      Intrinsics.checkNotNullExpressionValue(var10001, "");
      var10000.play((SoundInstance)var10001, var2);
   }

   @NotNull
   public static EnumEntries getEntries() {
      return $ENTRIES;
   }
}
