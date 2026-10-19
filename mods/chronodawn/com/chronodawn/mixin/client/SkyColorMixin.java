package com.chronodawn.mixin.client;

import com.chronodawn.compat.CompatResourceLocation;
import com.chronodawn.registry.ModDimensions;
import java.util.Map;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientAdvancements;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Biome.class)
public class SkyColorMixin {
   @Unique
   private static final int DEFAULT_SKY_COLOR = 9474192;
   @Unique
   private static final int BRIGHT_SKY_COLOR = 5605597;
   @Unique
   private static final ResourceLocation TIME_TYRANT_DEFEATED_ADVANCEMENT = CompatResourceLocation.create("chronodawn", "story/us3/time_tyrant_defeat");

   @Inject(method = "getSkyColor()I", at = @At("RETURN"), cancellable = true)
   private void modifyChronoDawnSkyColor(CallbackInfoReturnable<Integer> cir) {
      Minecraft minecraft = Minecraft.getInstance();
      if (minecraft.level != null) {
         if (minecraft.level.dimension().equals(ModDimensions.CHRONO_DAWN_DIMENSION)) {
            int originalColor = (Integer)cir.getReturnValue();
            if (originalColor == 9474192) {
               boolean timeTyrantDefeated = hasTimeTyrantBeenDefeated();
               if (timeTyrantDefeated) {
                  cir.setReturnValue(5605597);
               }
            }
         }
      }
   }

   @Unique
   private static boolean hasTimeTyrantBeenDefeated() {
      Minecraft minecraft = Minecraft.getInstance();
      LocalPlayer player = minecraft.player;
      if (player == null) {
         return false;
      }

      ClientPacketListener connection = minecraft.getConnection();
      if (connection == null) {
         return false;
      }

      ClientAdvancements advancementManager = connection.getAdvancements();
      AdvancementHolder holder = advancementManager.get(TIME_TYRANT_DEFEATED_ADVANCEMENT);
      if (holder == null) {
         return false;
      }

      ClientAdvancementsAccessor progressAccessor = (ClientAdvancementsAccessor)advancementManager;
      Map<AdvancementHolder, AdvancementProgress> progressMap = progressAccessor.chronodawn$getProgress();
      if (progressMap == null) {
         return false;
      }

      AdvancementProgress progress = progressMap.get(holder);
      return progress == null ? false : progress.isDone();
   }
}
