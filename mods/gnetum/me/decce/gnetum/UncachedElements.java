package me.decce.gnetum;

import com.google.common.collect.ImmutableSet;
import java.util.Set;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;

public class UncachedElements {
   public final Set<String> moddedPre;
   public final Set<String> moddedPost;
   public final Set<String> vanilla = ImmutableSet.of(
      VanillaGuiLayers.CROSSHAIR.toString(), VanillaGuiLayers.CAMERA_OVERLAYS.toString(), VanillaGuiLayers.DEMO_OVERLAY.toString(), "apoli:above_overlay"
   );

   public UncachedElements() {
      this.moddedPre = ImmutableSet.of("xaerominimap");
      this.moddedPost = ImmutableSet.of();
   }

   public boolean has(String vanillaOverlay) {
      return this.vanilla.contains(vanillaOverlay);
   }

   public boolean has(String moddedOverlay, ElementType type) {
      return switch (type) {
         case PRE -> this.moddedPre.contains(moddedOverlay);
         case POST -> this.moddedPost.contains(moddedOverlay);
         case VANILLA -> this.has(moddedOverlay);
      };
   }
}
