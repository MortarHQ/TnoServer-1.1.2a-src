package net.blay09.mods.waystones.mixin;

import net.blay09.mods.waystones.config.WaystonesConfig;
import net.blay09.mods.waystones.config.WaystonesConfigData;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer")
public class JigsawPlacementPlacerMixin {
   @Final
   @Shadow
   private Registry<StructureTemplatePool> pools;
   @Unique
   private boolean waystones$hasWaystone;

   @Unique
   private boolean waystones$shouldForceWaystone() {
      return WaystonesConfig.getActive().worldGen.spawnInVillages == WaystonesConfigData.VillageWaystoneGeneration.FREQUENT;
   }

   @ModifyArg(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;")
   )
   private ResourceKey<StructureTemplatePool> forceWaystonePool(ResourceKey<StructureTemplatePool> resourceKey) {
      if (this.waystones$shouldForceWaystone() && !this.waystones$hasWaystone) {
         String poolPath = resourceKey.location().getPath();
         if (poolPath.endsWith("/houses")) {
            ResourceLocation waystonePoolName = ResourceLocation.fromNamespaceAndPath("waystones", poolPath.replace("/houses", "/waystones"));
            ResourceKey<StructureTemplatePool> waystonePoolKey = ResourceKey.create(Registries.TEMPLATE_POOL, waystonePoolName);
            if (this.pools.getHolder(waystonePoolKey).isPresent()) {
               this.waystones$hasWaystone = true;
               return waystonePoolKey;
            }
         }

         return resourceKey;
      } else {
         return resourceKey;
      }
   }
}
