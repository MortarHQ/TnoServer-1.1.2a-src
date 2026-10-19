package com.mongoose.royalvariations.init;

import com.mongoose.royalvariations.client.render.CuriosRenderer;
import com.mongoose.royalvariations.client.render.NetRenderer;
import com.mongoose.royalvariations.client.render.RVModelLayer;
import com.mongoose.royalvariations.client.render.RoyalArrowRenderer;
import com.mongoose.royalvariations.client.render.RoyalBombProjectileRenderer;
import com.mongoose.royalvariations.client.render.RoyalBombRenderer;
import com.mongoose.royalvariations.client.render.RoyalCreeperRenderer;
import com.mongoose.royalvariations.client.render.RoyalEndermanRenderer;
import com.mongoose.royalvariations.client.render.RoyalSkeletonRenderer;
import com.mongoose.royalvariations.client.render.RoyalZombieRenderer;
import com.mongoose.royalvariations.client.render.SpectralAnvilRenderer;
import com.mongoose.royalvariations.client.render.item.ArmorClientExtension;
import com.mongoose.royalvariations.client.render.model.GloveModel;
import com.mongoose.royalvariations.client.render.model.NetModel;
import com.mongoose.royalvariations.client.render.model.RoyalBombModel;
import com.mongoose.royalvariations.client.render.model.RoyalBombProjectileModel;
import com.mongoose.royalvariations.client.render.model.RoyalCreeperModel;
import com.mongoose.royalvariations.client.render.model.RoyalEndermanModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightBootsModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightCuirassModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightHelmetModel;
import com.mongoose.royalvariations.client.render.model.RoyalKnightLeggingsModel;
import com.mongoose.royalvariations.client.render.model.RoyalSkeletonModel;
import com.mongoose.royalvariations.client.render.model.RoyalZombieModel;
import com.mongoose.royalvariations.client.render.model.SpectralAnvilModel;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.common.items.RoyalTelelocator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;

@EventBusSubscriber(modid = "royalvariations", bus = Bus.MOD, value = Dist.CLIENT)
public class ClientInitEvents {
   @SubscribeEvent
   public static void clientInit(FMLClientSetupEvent event) {
      CuriosRenderer.register();
      event.enqueueWork(
         () -> {
            ItemProperties.register((Item)RVItems.ROYAL_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, world, living, seed) -> {
               if (living == null) {
                  return 0.0F;
               } else {
                  return living.getUseItem() != stack ? 0.0F : (stack.getUseDuration(living) - living.getUseItemRemainingTicks()) / 20.0F;
               }
            });
            ItemProperties.register(
               (Item)RVItems.ROYAL_BOW.get(),
               ResourceLocation.withDefaultNamespace("pulling"),
               (stack, world, living, seed) -> living != null && living.isUsingItem() && living.getUseItem() == stack ? 1.0F : 0.0F
            );
            ItemProperties.register(
               (Item)RVItems.ROYAL_TELELOCATOR.get(),
               ResourceLocation.withDefaultNamespace("active"),
               (stack, world, living, seed) -> RoyalTelelocator.hasBlock(stack) ? 1.0F : 0.0F
            );
         }
      );
   }

   @SubscribeEvent
   public static void onRegisterLayers(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(RVModelLayer.ROYAL_BOMB_PROJECTILE, RoyalBombProjectileModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_BOMB, RoyalBombModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.NET, NetModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.SPECTRAL_ANVIL, SpectralAnvilModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_ZOMBIE, RoyalZombieModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_SKELETON, RoyalSkeletonModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_CREEPER, () -> RoyalCreeperModel.createBodyLayer(CubeDeformation.NONE));
      event.registerLayerDefinition(RVModelLayer.ROYAL_CREEPER_ARMOR, () -> RoyalCreeperModel.createBodyLayer(new CubeDeformation(0.5F)));
      event.registerLayerDefinition(RVModelLayer.ROYAL_ENDERMAN, RoyalEndermanModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.GLOVE, GloveModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_KNIGHT_HELMET, RoyalKnightHelmetModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_KNIGHT_CUIRASS, RoyalKnightCuirassModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_KNIGHT_LEGGINGS, RoyalKnightLeggingsModel::createBodyLayer);
      event.registerLayerDefinition(RVModelLayer.ROYAL_KNIGHT_BOOTS, RoyalKnightBootsModel::createBodyLayer);
   }

   @SubscribeEvent
   public static void onRegisterRenders(RegisterRenderers event) {
      ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_ARROW.get(), RoyalArrowRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_BOMB_PROJECTILE.get(), RoyalBombProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_BOMB.get(), RoyalBombRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_ENDER_PEARL.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.NET.get(), NetRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.SPECTRAL_ANVIL.get(), SpectralAnvilRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_ZOMBIE.get(), RoyalZombieRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_SKELETON.get(), RoyalSkeletonRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_CREEPER.get(), RoyalCreeperRenderer::new);
      event.registerEntityRenderer((EntityType)RVEntityType.ROYAL_ENDERMAN.get(), RoyalEndermanRenderer::new);
   }

   @SubscribeEvent
   public static void clientExtensionsEvent(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new ArmorClientExtension(),
         new Item[]{
            (Item)RVItems.ROYAL_KNIGHT_HELMET.get(),
            (Item)RVItems.ROYAL_KNIGHT_CUIRASS.get(),
            (Item)RVItems.ROYAL_KNIGHT_LEGGINGS.get(),
            (Item)RVItems.ROYAL_KNIGHT_BOOTS.get()
         }
      );
   }
}
