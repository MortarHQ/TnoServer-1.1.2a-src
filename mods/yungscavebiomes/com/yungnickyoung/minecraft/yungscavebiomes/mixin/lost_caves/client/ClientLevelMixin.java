package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves.client;

import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.ISandstormClientDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.client.render.sandstorm.SandstormClientData;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.BlockPos.MutableBlockPos;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.storage.WritableLevelData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientLevel.class)
public abstract class ClientLevelMixin extends Level implements ISandstormClientDataProvider {
   @Unique
   private final SandstormClientData sandstormClientData = new SandstormClientData();

   @Unique
   @Override
   public SandstormClientData getSandstormClientData() {
      return this.sandstormClientData;
   }

   @Shadow
   public abstract void addParticle(ParticleOptions var1, double var2, double var4, double var6, double var8, double var10, double var12);

   protected ClientLevelMixin(
      WritableLevelData $$0,
      ResourceKey<Level> $$1,
      RegistryAccess $$2,
      Holder<DimensionType> $$3,
      Supplier<ProfilerFiller> $$4,
      boolean $$5,
      boolean $$6,
      long $$7,
      int $$8
   ) {
      super($$0, $$1, $$2, $$3, $$4, $$5, $$6, $$7, $$8);
   }

   @Inject(method = "tick", at = @At("TAIL"))
   private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
      this.sandstormClientData.tick();
   }

   @Inject(method = "doAnimateTick", at = @At("TAIL"))
   private void yungscavebiomes_addSandstormParticles(
      int x, int y, int z, int maxDistance, RandomSource random, Block markerTarget, MutableBlockPos pos, CallbackInfo ci
   ) {
      this.sandstormClientData.addSandstormParticles((ClientLevel)this, x, y, z, maxDistance, random, pos);
   }

   @Inject(method = "animateTick", at = @At("TAIL"))
   private void yungscavebiomes_addExtraSandstormParticles(int x, int y, int z, CallbackInfo ci) {
      this.sandstormClientData.addExtraSandstormParticles((ClientLevel)this, x, y, z);
   }
}
