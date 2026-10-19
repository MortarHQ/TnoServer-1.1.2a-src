package com.mortal_cultivation.mixin;

import com.mortal_cultivation.client.vfx.QilinClientEffects;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.chunk.RenderRegionCache;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher.RenderSection;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public abstract class QilinTerrainRefreshMixin {
   @Shadow(remap = false)
   @Final
   private ObjectArrayList<RenderSection> visibleSections;
   @Shadow(remap = false)
   private SectionRenderDispatcher sectionRenderDispatcher;

   @Inject(method = "compileSections", at = @At("HEAD"), remap = false)
   private void mortalCultivation$refreshDisplacedTerrain(Camera camera, CallbackInfo callback) {
      if (QilinClientEffects.hasSectionRefreshes() && this.sectionRenderDispatcher != null) {
         RenderRegionCache cache = new RenderRegionCache();
         ObjectListIterator var4 = this.visibleSections.iterator();

         while (var4.hasNext()) {
            RenderSection section = (RenderSection)var4.next();
            if (QilinClientEffects.consumeSectionRefresh(section.getOrigin())) {
               this.sectionRenderDispatcher.rebuildSectionSync(section, cache);
               section.setNotDirty();
            }
         }

         this.sectionRenderDispatcher.uploadAllPendingUploads();
      }
   }
}
