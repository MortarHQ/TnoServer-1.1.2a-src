package com.yungnickyoung.minecraft.yungscavebiomes.mixin.lost_caves;

import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.ISandstormServerDataProvider;
import com.yungnickyoung.minecraft.yungscavebiomes.sandstorm.SandstormServerData;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import net.minecraft.core.Holder;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.progress.ChunkProgressListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.RandomSequences;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.dimension.DimensionType;
import net.minecraft.world.level.dimension.LevelStem;
import net.minecraft.world.level.storage.DimensionDataStorage;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraft.world.level.storage.WritableLevelData;
import net.minecraft.world.level.storage.LevelStorageSource.LevelStorageAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin extends Level implements ISandstormServerDataProvider {
   @Unique
   private static final String SANDSTORMS_DATA_NAME = "sandstorms";
   @Unique
   private SandstormServerData sandstormServerData;

   @Shadow
   public abstract DimensionDataStorage getDataStorage();

   protected ServerLevelMixin(
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

   @Unique
   @Override
   public SandstormServerData getSandstormServerData() {
      return this.sandstormServerData;
   }

   @Inject(method = "<init>", at = @At("TAIL"))
   private void yungscavebiomes_initSandstorm(
      MinecraftServer $$0,
      Executor $$1,
      LevelStorageAccess $$2,
      ServerLevelData $$3,
      ResourceKey $$4,
      LevelStem $$5,
      ChunkProgressListener $$6,
      boolean $$7,
      long $$8,
      List $$9,
      boolean $$10,
      RandomSequences $$11,
      CallbackInfo ci
   ) {
      this.sandstormServerData = (SandstormServerData)this.getDataStorage().computeIfAbsent(SandstormServerData.factory(this._this()), "sandstorms");
   }

   @Inject(method = "tick", at = @At("HEAD"))
   private void yungscavebiomes_tickSandstorm(BooleanSupplier $$0, CallbackInfo ci) {
      this.sandstormServerData.tick();
   }

   @Unique
   private ServerLevel _this() {
      return (ServerLevel)this;
   }
}
