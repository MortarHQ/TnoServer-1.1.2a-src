package com.yungnickyoung.minecraft.yungscavebiomes.module;

import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegister;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterEntityType;
import com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterEntityType.Builder;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.RareIceBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.block.entity.SuspiciousAncientSandBlockEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.IcicleProjectileEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.ice_cube.IceCubeEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.entity.sand_snapper.SandSnapperEntity;
import com.yungnickyoung.minecraft.yungscavebiomes.mixin.accessor.SpawnPlacementsAccessor;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.Heightmap.Types;

@AutoRegister("yungscavebiomes")
public class EntityTypeModule {
   @AutoRegister("ice_cube")
   public static AutoRegisterEntityType<IceCubeEntity> ICE_CUBE = AutoRegisterEntityType.of(
         () -> Builder.of(IceCubeEntity::new, MobCategory.MONSTER).sized(1.6F, 1.5F).eyeHeight(0.15F).clientTrackingRange(10).build()
      )
      .attributes(IceCubeEntity::createAttributes);
   @AutoRegister("sand_snapper")
   public static AutoRegisterEntityType<SandSnapperEntity> SAND_SNAPPER = AutoRegisterEntityType.of(
         () -> Builder.of(SandSnapperEntity::new, MobCategory.MONSTER).sized(0.9F, 0.5F).clientTrackingRange(10).build()
      )
      .attributes(SandSnapperEntity::createAttributes);
   @AutoRegister("icicle")
   public static final AutoRegisterEntityType<IcicleProjectileEntity> ICICLE = AutoRegisterEntityType.of(
      () -> Builder.of(IcicleProjectileEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20).build()
   );
   @AutoRegister("rare_ice")
   public static final AutoRegisterBlockEntityType<RareIceBlockEntity> RARE_ICE = AutoRegisterBlockEntityType.of(
      () -> com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType.Builder.of(
            RareIceBlockEntity::new, new Block[]{(Block)BlockModule.RARE_ICE.get()}
         )
         .build(null)
   );
   @AutoRegister("suspicious_ancient_sand")
   public static final AutoRegisterBlockEntityType<SuspiciousAncientSandBlockEntity> SUSPICIOUS_ANCIENT_SAND = AutoRegisterBlockEntityType.of(
      () -> com.yungnickyoung.minecraft.yungsapi.api.autoregister.AutoRegisterBlockEntityType.Builder.of(
            SuspiciousAncientSandBlockEntity::new, new Block[]{(Block)BlockModule.SUSPICIOUS_ANCIENT_SAND.get()}
         )
         .build()
   );

   @AutoRegister("init")
   private static void init() {
      SpawnPlacementsAccessor.callRegister(
         (EntityType)ICE_CUBE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules
      );
      SpawnPlacementsAccessor.callRegister(
         (EntityType)SAND_SNAPPER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules
      );
   }
}
