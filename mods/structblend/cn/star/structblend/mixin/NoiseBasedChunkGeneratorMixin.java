package cn.star.structblend.mixin;

import cn.star.structblend.terrain.ChunkHeightBatch;
import cn.star.structblend.terrain.SurfaceBlendPlan;
import cn.star.structblend.terrain.TerrainBeardifier;
import cn.star.structblend.terrain.VillageSupportShape;
import cn.star.structblend.terrain.VillageUnderpinning;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.status.ChunkStatus;
import net.minecraft.world.level.levelgen.Beardifier;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseChunk;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.Aquifer.FluidPicker;
import net.minecraft.world.level.levelgen.DensityFunctions.BeardifierOrMarker;
import net.minecraft.world.level.levelgen.blending.Blender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NoiseBasedChunkGenerator.class)
public abstract class NoiseBasedChunkGeneratorMixin {
   @Shadow
   @Final
   private Holder<NoiseGeneratorSettings> settings;
   @Shadow
   @Final
   private Supplier<FluidPicker> globalFluidPicker;

   @Inject(
      method = "createNoiseChunk(Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/levelgen/blending/Blender;Lnet/minecraft/world/level/levelgen/RandomState;)Lnet/minecraft/world/level/levelgen/NoiseChunk;",
      at = @At("HEAD"),
      cancellable = true
   )
   private void structblend$createNoiseChunk(
      ChunkAccess chunk, StructureManager manager, Blender blender, RandomState randomState, CallbackInfoReturnable<NoiseChunk> callback
   ) {
      if (!chunk.getPersistedStatus().isOrAfter(ChunkStatus.NOISE)) {
         NoiseBasedChunkGenerator generator = (NoiseBasedChunkGenerator)this;
         ChunkHeightBatch heights = ChunkHeightBatch.create(generator, chunk, randomState);
         SurfaceBlendPlan plan = SurfaceBlendPlan.prepare(chunk, manager, generator, randomState, heights);
         VillageSupportShape village = VillageUnderpinning.prepare(chunk, manager, generator, randomState, heights);
         if (plan != null || village != null) {
            Beardifier vanilla = Beardifier.forStructuresInChunk(manager, chunk.getPos());
            BeardifierOrMarker density = (BeardifierOrMarker)(plan == null ? vanilla : new TerrainBeardifier(vanilla, plan));
            if (village != null) {
               density = new VillageUnderpinning(density, village);
            }

            callback.setReturnValue(
               NoiseChunk.forChunk(chunk, randomState, density, (NoiseGeneratorSettings)this.settings.value(), this.globalFluidPicker.get(), blender)
            );
         }
      }
   }
}
