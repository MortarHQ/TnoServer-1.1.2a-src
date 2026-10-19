package xaero.map.region;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import xaero.map.MapProcessor;

public class OverlayBuilder {
   private static final int MAX_OVERLAYS = 10;
   private Overlay[] overlayBuildingSet;
   private int currentOverlayIndex;
   private OverlayManager overlayManager;
   private TextureAtlasSprite prevIcon;
   private ResourceKey<Biome> overlayBiome;

   public OverlayBuilder(OverlayManager overlayManager) {
      this.overlayManager = overlayManager;
      this.overlayBuildingSet = new Overlay[10];

      for (int i = 0; i < this.overlayBuildingSet.length; i++) {
         this.overlayBuildingSet[i] = new Overlay(Blocks.AIR.defaultBlockState(), (byte)0, false);
      }

      this.currentOverlayIndex = -1;
   }

   public void startBuilding() {
      this.currentOverlayIndex = -1;
      this.setOverlayBiome(null);
   }

   public void build(BlockState state, int opacity, byte light, MapProcessor mapProcessor, ResourceKey<Biome> biomeId) {
      Overlay currentOverlay = this.getCurrentOverlay();
      Overlay nextOverlay = null;
      if (this.currentOverlayIndex < this.overlayBuildingSet.length - 1) {
         nextOverlay = this.overlayBuildingSet[this.currentOverlayIndex + 1];
      }

      TextureAtlasSprite icon = null;
      boolean changed = false;
      if (currentOverlay == null || currentOverlay.getState() != state) {
         icon = Minecraft.getInstance().getBlockRenderer().getBlockModelShaper().getParticleIcon(state);
         changed = icon != this.prevIcon;
      }

      if (nextOverlay != null && (currentOverlay == null || changed)) {
         boolean glowing = false;

         try {
            glowing = mapProcessor.getMapWriter().isGlowing(state);
         } catch (Exception var12) {
         }

         if (this.getOverlayBiome() == null) {
            this.setOverlayBiome(biomeId);
         }

         nextOverlay.write(state, light, glowing);
         currentOverlay = nextOverlay;
         this.currentOverlayIndex++;
      }

      currentOverlay.increaseOpacity(opacity);
      if (changed) {
         this.prevIcon = icon;
      }
   }

   public boolean isEmpty() {
      return this.currentOverlayIndex < 0;
   }

   public Overlay getCurrentOverlay() {
      Overlay currentOverlay = null;
      if (this.currentOverlayIndex >= 0) {
         currentOverlay = this.overlayBuildingSet[this.currentOverlayIndex];
      }

      return currentOverlay;
   }

   public void finishBuilding(MapBlock block) {
      for (int i = 0; i <= this.currentOverlayIndex; i++) {
         Overlay o = this.overlayBuildingSet[i];
         Overlay original = this.overlayManager.getOriginal(o);
         if (o == original) {
            this.overlayBuildingSet[i] = new Overlay(Blocks.AIR.defaultBlockState(), (byte)0, false);
         }

         block.addOverlay(original);
      }
   }

   public ResourceKey<Biome> getOverlayBiome() {
      return this.overlayBiome;
   }

   public void setOverlayBiome(ResourceKey<Biome> overlayBiome) {
      this.overlayBiome = overlayBiome;
   }
}
