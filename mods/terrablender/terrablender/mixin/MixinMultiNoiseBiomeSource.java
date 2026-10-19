package terrablender.mixin;

import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.QuartPos;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MultiNoiseBiomeSource;
import net.minecraft.world.level.biome.Climate.ParameterList;
import net.minecraft.world.level.biome.Climate.Sampler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import terrablender.worldgen.IExtendedMultiNoiseBiomeSource;
import terrablender.worldgen.IExtendedParameterList;

@Mixin(MultiNoiseBiomeSource.class)
public abstract class MixinMultiNoiseBiomeSource implements IExtendedMultiNoiseBiomeSource {
   @Shadow
   public abstract ParameterList<Holder<Biome>> parameters();

   @Inject(method = "getNoiseBiome(IIILnet/minecraft/world/level/biome/Climate$Sampler;)Lnet/minecraft/core/Holder;", at = @At("HEAD"), cancellable = true)
   public void getNoiseBiome(int x, int y, int z, Sampler sampler, CallbackInfoReturnable<Holder<Biome>> cir) {
      cir.setReturnValue((Holder)((IExtendedParameterList)this.parameters()).findValuePositional(sampler.sample(x, y, z), x, y, z));
   }

   @Inject(method = "addDebugInfo", at = @At("TAIL"))
   public void addDebugInfo(List<String> debugLines, BlockPos pos, Sampler sampler, CallbackInfo ci) {
      int qx = QuartPos.fromBlock(pos.getX());
      int qz = QuartPos.fromBlock(pos.getZ());
      IExtendedParameterList<Holder<Biome>> extension = (IExtendedParameterList<Holder<Biome>>)this.parameters();
      if (extension.isInitialized()) {
         debugLines.add("Region: " + extension.getRegion(extension.getUniqueness(qx, 0, qz)).getName().toString());
      }
   }

   @Override
   public MultiNoiseBiomeSource clone() {
      try {
         return (MultiNoiseBiomeSource)super.clone();
      } catch (CloneNotSupportedException e) {
         throw new AssertionError();
      }
   }
}
