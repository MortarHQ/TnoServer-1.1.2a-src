package com.tensurabossstructure.mixin;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(targets = "net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement$Placer")
public class JigsawPlacementPlacerMixin {
   private static final ResourceKey<StructureTemplatePool> PLAINS_HOUSES = poolKey("minecraft", "village/plains/houses");
   private static final ResourceKey<StructureTemplatePool> TAIGA_HOUSES = poolKey("minecraft", "village/taiga/houses");
   private static final ResourceKey<StructureTemplatePool> PLAINS_OTHERWORLDER = poolKey("tensura_boss_structure", "village/plains/otherworlder");
   private static final ResourceKey<StructureTemplatePool> TAIGA_OTHERWORLDER = poolKey("tensura_boss_structure", "village/taiga/otherworlder");
   @Final
   @Shadow
   private Registry<StructureTemplatePool> pools;

   @ModifyArg(
      method = "tryPlacingChildren(Lnet/minecraft/world/level/levelgen/structure/PoolElementStructurePiece;Lorg/apache/commons/lang3/mutable/MutableObject;IZLnet/minecraft/world/level/LevelHeightAccessor;Lnet/minecraft/world/level/levelgen/RandomState;Lnet/minecraft/world/level/levelgen/structure/pools/alias/PoolAliasLookup;Lnet/minecraft/world/level/levelgen/structure/templatesystem/LiquidSettings;)V",
      at = @At(value = "INVOKE", target = "Lnet/minecraft/core/Registry;getHolder(Lnet/minecraft/resources/ResourceKey;)Ljava/util/Optional;")
   )
   private ResourceKey<StructureTemplatePool> forceOtherworlderPool(ResourceKey<StructureTemplatePool> resourceKey) {
      ResourceKey<StructureTemplatePool> replacement = null;
      if (resourceKey.equals(PLAINS_HOUSES)) {
         replacement = PLAINS_OTHERWORLDER;
      } else if (resourceKey.equals(TAIGA_HOUSES)) {
         replacement = TAIGA_OTHERWORLDER;
      }

      return replacement != null && this.pools.getHolder(replacement).isPresent() ? replacement : resourceKey;
   }

   private static ResourceKey<StructureTemplatePool> poolKey(String namespace, String path) {
      return ResourceKey.create(Registries.TEMPLATE_POOL, ResourceLocation.fromNamespaceAndPath(namespace, path));
   }
}
