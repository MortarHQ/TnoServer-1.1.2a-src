package com.yungnickyoung.minecraft.yungsapi.mixin;

import com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier.EnhancedBeardifierData;
import com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier.EnhancedBeardifierHelper;
import com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier.EnhancedBeardifierRigid;
import com.yungnickyoung.minecraft.yungsapi.world.structure.terrainadaptation.beardifier.EnhancedJigsawJunction;
import it.unimi.dsi.fastutil.objects.ObjectListIterator;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.DensityFunction.FunctionContext;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Beardifier.class)
public class BeardifierMixin implements EnhancedBeardifierData {
   @Unique
   private ObjectListIterator<EnhancedJigsawJunction> enhancedJunctionIterator;
   @Unique
   private ObjectListIterator<EnhancedBeardifierRigid> enhancedPieceIterator;
   @Unique
   private NoiseChunk noiseChunk;

   @Inject(method = "forStructuresInChunk", at = @At("RETURN"), cancellable = true)
   private static void yungsapi_supportCustomTerrainAdaptations(StructureManager structureManager, ChunkPos chunkPos, CallbackInfoReturnable<Beardifier> cir) {
      Beardifier enhancedBeardifier = EnhancedBeardifierHelper.forStructuresInChunk(structureManager, chunkPos, (Beardifier)cir.getReturnValue());
      cir.setReturnValue(enhancedBeardifier);
   }

   @Inject(method = "compute", at = @At("RETURN"), cancellable = true)
   public void yungsapi_calculateDensity(FunctionContext ctx, CallbackInfoReturnable<Double> cir) {
      double density = (Double)cir.getReturnValue();
      double newDensity = EnhancedBeardifierHelper.computeDensity(ctx, density, this);
      cir.setReturnValue(newDensity);
   }

   @Unique
   @Override
   public ObjectListIterator<EnhancedBeardifierRigid> getEnhancedPieceIterator() {
      return this.enhancedPieceIterator;
   }

   @Unique
   @Override
   public void setEnhancedPieceIterator(ObjectListIterator<EnhancedBeardifierRigid> enhancedPieceIterator) {
      this.enhancedPieceIterator = enhancedPieceIterator;
   }

   @Unique
   @Override
   public ObjectListIterator<EnhancedJigsawJunction> getEnhancedJunctionIterator() {
      return this.enhancedJunctionIterator;
   }

   @Unique
   @Override
   public void setEnhancedJunctionIterator(ObjectListIterator<EnhancedJigsawJunction> enhancedJunctionIterator) {
      this.enhancedJunctionIterator = enhancedJunctionIterator;
   }

   @Unique
   @Override
   public NoiseChunk getNoiseChunk() {
      return this.noiseChunk;
   }

   @Unique
   @Override
   public void setNoiseChunk(NoiseChunk noiseChunk) {
      this.noiseChunk = noiseChunk;
   }
}
