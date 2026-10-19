package com.moepus.byepregen.mixin.yalight;

import com.moepus.byepregen.yalight.YABlockLightEngine;
import com.moepus.byepregen.yalight.YALightEngine;
import com.moepus.byepregen.yalight.YALightEngineHolder;
import com.moepus.byepregen.yalight.YASkyLightEngine;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LightLayer;
import net.minecraft.world.level.chunk.DataLayer;
import net.minecraft.world.level.chunk.LightChunkGetter;
import net.minecraft.world.level.lighting.BlockLightEngine;
import net.minecraft.world.level.lighting.LayerLightEventListener;
import net.minecraft.world.level.lighting.LevelLightEngine;
import net.minecraft.world.level.lighting.LightEngine;
import net.minecraft.world.level.lighting.SkyLightEngine;
import net.minecraft.world.level.lighting.LayerLightSectionStorage.SectionType;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLightEngine.class)
public abstract class LevelLightEngineYAMixin implements YALightEngineHolder {
   @Shadow
   @Final
   private LightEngine<?, ?> blockEngine;
   @Shadow
   @Final
   private LightEngine<?, ?> skyEngine;
   @Unique
   private YALightEngine byepregen$yaLightEngine;

   @Override
   public YALightEngine byepregen$getYALightEngine() {
      return this.byepregen$yaLightEngine;
   }

   @Redirect(
      method = "<init>",
      at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/chunk/LightChunkGetter;)Lnet/minecraft/world/level/lighting/BlockLightEngine;")
   )
   private BlockLightEngine byepregen$createYABlockLightEngine(LightChunkGetter chunkGetter) {
      return new YABlockLightEngine(chunkGetter);
   }

   @Redirect(
      method = "<init>",
      at = @At(value = "NEW", target = "(Lnet/minecraft/world/level/chunk/LightChunkGetter;)Lnet/minecraft/world/level/lighting/SkyLightEngine;")
   )
   private SkyLightEngine byepregen$createYASkyLightEngine(LightChunkGetter chunkGetter) {
      return new YASkyLightEngine(chunkGetter);
   }

   @Inject(method = "<init>", at = @At("TAIL"))
   private void byepregen$initYALightEngine(LightChunkGetter chunkGetter, boolean blockLight, boolean skyLight, CallbackInfo ci) {
      this.byepregen$yaLightEngine = new YALightEngine(chunkGetter, (YABlockLightEngine)this.blockEngine, (YASkyLightEngine)this.skyEngine);
   }

   @Overwrite
   public void checkBlock(BlockPos pos) {
      this.byepregen$yaLightEngine.checkBlock(pos);
   }

   @Overwrite
   public boolean hasLightWork() {
      return this.byepregen$yaLightEngine.hasLightWork();
   }

   @Overwrite
   public int runLightUpdates() {
      return this.byepregen$yaLightEngine.runLightUpdates();
   }

   @Overwrite
   public void updateSectionStatus(SectionPos pos, boolean isEmpty) {
      this.byepregen$yaLightEngine.updateSectionStatus(pos, isEmpty);
   }

   @Overwrite
   public void setLightEnabled(ChunkPos pos, boolean lightEnabled) {
      this.byepregen$yaLightEngine.setLightEnabled(pos, lightEnabled);
   }

   @Overwrite
   public void propagateLightSources(ChunkPos pos) {
      this.byepregen$yaLightEngine.propagateLightSources(pos);
   }

   @Overwrite
   public LayerLightEventListener getLayerListener(LightLayer layer) {
      return this.byepregen$yaLightEngine.getLayerListener(layer);
   }

   @Overwrite
   public String getDebugData(LightLayer layer, SectionPos pos) {
      return this.byepregen$yaLightEngine.getDebugData(layer, pos);
   }

   @Overwrite
   public SectionType getDebugSectionType(LightLayer layer, SectionPos pos) {
      return this.byepregen$yaLightEngine.getDebugSectionType(layer, pos);
   }

   @Overwrite
   public void queueSectionData(LightLayer layer, SectionPos pos, DataLayer dataLayer) {
      this.byepregen$yaLightEngine.queueSectionData(layer, pos, dataLayer);
   }

   @Overwrite
   public void retainData(ChunkPos pos, boolean retainData) {
      this.byepregen$yaLightEngine.retainData(pos, retainData);
   }

   @Overwrite
   public int getRawBrightness(BlockPos pos, int ambientDarkness) {
      return this.byepregen$yaLightEngine.getRawBrightness(pos, ambientDarkness);
   }

   @Overwrite
   public boolean lightOnInSection(SectionPos pos) {
      return this.byepregen$yaLightEngine.lightOnInSection(pos);
   }
}
