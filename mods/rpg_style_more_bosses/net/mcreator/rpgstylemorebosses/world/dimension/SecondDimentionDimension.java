package net.mcreator.rpgstylemorebosses.world.dimension;

import net.mcreator.rpgstylemorebosses.procedures.SecondDimentionPlayerEntersDimensionProcedure;
import net.minecraft.client.renderer.DimensionSpecialEffects;
import net.minecraft.client.renderer.DimensionSpecialEffects.SkyType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;

@EventBusSubscriber
public class SecondDimentionDimension {
   @SubscribeEvent
   public static void onPlayerChangedDimensionEvent(PlayerChangedDimensionEvent event) {
      Entity entity = event.getEntity();
      Level world = entity.level();
      double x = entity.getX();
      double y = entity.getY();
      double z = entity.getZ();
      if (event.getTo() == ResourceKey.create(Registries.DIMENSION, ResourceLocation.parse("rpg_style_more_bosses:second_dimention"))) {
         SecondDimentionPlayerEntersDimensionProcedure.execute(world, entity);
      }
   }

   @EventBusSubscriber(bus = Bus.MOD, value = Dist.CLIENT)
   public static class SecondDimentionSpecialEffectsHandler {
      @SubscribeEvent
      public static void registerDimensionSpecialEffects(RegisterDimensionSpecialEffectsEvent event) {
         DimensionSpecialEffects customEffect = new DimensionSpecialEffects(0.0F, true, SkyType.END, false, false) {
            public Vec3 getBrightnessDependentFogColor(Vec3 color, float sunHeight) {
               return color;
            }

            public boolean isFoggyAt(int x, int y) {
               return false;
            }
         };
         event.register(ResourceLocation.parse("rpg_style_more_bosses:second_dimention"), customEffect);
      }
   }
}
