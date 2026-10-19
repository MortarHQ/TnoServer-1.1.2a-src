package me.wesley1808.servercore.mixin.optimizations.ticking.chunk.random;

import java.util.function.Supplier;
import me.wesley1808.servercore.common.interfaces.chunk.ILevelChunk;
import me.wesley1808.servercore.common.interfaces.chunk.IServerLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = ServerLevel.class, priority = 900)
public abstract class ServerLevelMixin extends Level implements IServerLevel {
   @Unique
   private int servercore$currentIceAndSnowTick = 0;

   private ServerLevelMixin(
      WritableLevelData writableLevelData,
      ResourceKey<Level> resourceKey,
      RegistryAccess registryAccess,
      Holder<DimensionType> holder,
      Supplier<ProfilerFiller> supplier,
      boolean bl,
      boolean bl2,
      long l,
      int i
   ) {
      super(writableLevelData, resourceKey, registryAccess, holder, supplier, bl, bl2, l, i);
   }

   @Redirect(
      method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V",
      require = 0,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I", ordinal = 0)
   )
   private int servercore$replaceLightningCheck(RandomSource randomSource, int thunderChance, LevelChunk chunk, int randomTickSpeed) {
      return ((ILevelChunk)chunk).servercore$shouldDoLightning(randomSource, thunderChance);
   }

   @Redirect(
      method = "tickChunk(Lnet/minecraft/world/level/chunk/LevelChunk;I)V",
      require = 0,
      at = @At(value = "INVOKE", target = "Lnet/minecraft/util/RandomSource;nextInt(I)I", ordinal = 1)
   )
   private int servercore$replaceIceAndSnowCheck(RandomSource randomSource, int i) {
      return this.servercore$currentIceAndSnowTick++ & 15;
   }

   @Override
   public void servercore$resetIceAndSnowTick() {
      this.servercore$currentIceAndSnowTick = this.random.nextInt(16);
   }
}
