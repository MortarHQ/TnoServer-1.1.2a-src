package net.unusual.block_factorys_bosses.mixins.client;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.unusual.block_factorys_bosses.client.CinematicEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import software.bernie.geckolib.animatable.GeoAnimatable;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.renderer.GeoRenderer;

@Mixin({Entity.class, BlockEntity.class})
public class CinematicEntitiesMixin implements CinematicEntity {
   private GeoBone cameraBone;

   @Override
   public void setCameraBone(String name) {
      if (name != null) {
         GeoRenderer<? extends GeoAnimatable> renderer = null;
         Object self = this;
         if (self instanceof Entity entity) {
            renderer = (GeoRenderer<? extends GeoAnimatable>)Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(entity);
         } else if (self instanceof BlockEntity blockEntity) {
            renderer = (GeoRenderer<? extends GeoAnimatable>)Minecraft.getInstance().getBlockEntityRenderDispatcher().getRenderer(blockEntity);
         }

         if (renderer != null) {
            this.cameraBone = (GeoBone)renderer.getGeoModel().getBone(name).orElse(null);
            if (this.cameraBone != null && self instanceof Entity) {
               this.cameraBone.setTrackingMatrices(true);
            }
         }
      }
   }

   @Nullable
   @Override
   public GeoBone getCameraBone() {
      return this.cameraBone;
   }
}
