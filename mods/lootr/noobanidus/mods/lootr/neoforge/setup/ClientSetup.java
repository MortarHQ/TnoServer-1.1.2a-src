package noobanidus.mods.lootr.neoforge.setup;

import net.minecraft.client.model.geom.ModelLayers;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import noobanidus.mods.lootr.common.api.LootrAPI;
import noobanidus.mods.lootr.common.api.registry.LootrRegistry;
import noobanidus.mods.lootr.common.client.block.LootrBrushableBlockRenderer;
import noobanidus.mods.lootr.common.client.block.LootrDecoratedPotRenderer;
import noobanidus.mods.lootr.common.client.entity.LootrChestCartRenderer;
import noobanidus.mods.lootr.common.client.entity.LootrItemFrameRenderer;
import noobanidus.mods.lootr.common.client.item.LootrChestItemRenderer;
import noobanidus.mods.lootr.common.client.item.LootrDecoratedPotItemRenderer;
import noobanidus.mods.lootr.common.client.item.LootrShulkerItemRenderer;
import noobanidus.mods.lootr.common.client.item.LootrTrappedChestItemRenderer;
import noobanidus.mods.lootr.common.client.particle.UnopenedParticle;
import noobanidus.mods.lootr.neoforge.client.block.BarrelModel;
import noobanidus.mods.lootr.neoforge.client.block.BrushableModel;
import noobanidus.mods.lootr.neoforge.client.block.CustomModel;
import noobanidus.mods.lootr.neoforge.client.block.LootrChestBlockRenderer;
import noobanidus.mods.lootr.neoforge.client.block.LootrShulkerBlockRenderer;
import noobanidus.mods.lootr.neoforge.init.ModParticles;

@EventBusSubscriber(modid = "lootr", value = Dist.CLIENT)
public class ClientSetup {
   @SubscribeEvent
   public static void modelAdditional(RegisterAdditional event) {
      event.register(LootrItemFrameRenderer.FRAME_LOCATION);
      event.register(LootrItemFrameRenderer.FRAME_OPEN_LOCATION);
   }

   @SubscribeEvent
   public static void modelRegister(RegisterGeometryLoaders event) {
      event.register(LootrAPI.rl("custom_barrel"), CustomModel.Loader.BARREL_INSTANCE);
      event.register(LootrAPI.rl("custom_model"), CustomModel.Loader.INSTANCE);
      event.register(LootrAPI.rl("barrel"), BarrelModel.Loader.INSTANCE);
      event.register(LootrAPI.rl("brushable"), BrushableModel.Loader.INSTANCE);
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer(LootrRegistry.getChestBlockEntity(), LootrChestBlockRenderer::new);
      event.registerBlockEntityRenderer(LootrRegistry.getTrappedChestBlockEntity(), LootrChestBlockRenderer::new);
      event.registerBlockEntityRenderer(LootrRegistry.getChestBlockEntity(), LootrChestBlockRenderer::new);
      event.registerBlockEntityRenderer(LootrRegistry.getShulkerBlockEntity(), LootrShulkerBlockRenderer::new);
      event.registerBlockEntityRenderer(LootrRegistry.getInventoryBlockEntity(), LootrChestBlockRenderer::new);
      event.registerEntityRenderer(LootrRegistry.getMinecart(), e -> new LootrChestCartRenderer(e, ModelLayers.CHEST_MINECART));
      event.registerBlockEntityRenderer(LootrRegistry.getBrushableBlockEntity(), LootrBrushableBlockRenderer::new);
      event.registerBlockEntityRenderer(LootrRegistry.getDecoratedPotBlockEntity(), LootrDecoratedPotRenderer::new);
      event.registerEntityRenderer(LootrRegistry.getItemFrame(), LootrItemFrameRenderer::new);
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      IClientItemExtensions chest = new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return LootrChestItemRenderer.getInstance();
         }
      };
      event.registerItem(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return LootrShulkerItemRenderer.getInstance();
         }
      }, new Item[]{LootrRegistry.getShulkerItem()});
      event.registerItem(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return LootrTrappedChestItemRenderer.getInstance();
         }
      }, new Item[]{LootrRegistry.getTrappedChestItem()});
      event.registerItem(new IClientItemExtensions() {
         public BlockEntityWithoutLevelRenderer getCustomRenderer() {
            return LootrDecoratedPotItemRenderer.getInstance();
         }
      }, new Item[]{LootrRegistry.getDecoratedPotItem()});
      event.registerItem(chest, new Item[]{LootrRegistry.getChestItem()});
      event.registerItem(chest, new Item[]{LootrRegistry.getInventoryItem()});
   }

   @SubscribeEvent
   public static void registerLayersEvent(RegisterLayerDefinitions event) {
      event.registerLayerDefinition(LootrDecoratedPotRenderer.OPEN_POT_LAYER, LootrDecoratedPotRenderer::createBodyLayer);
   }

   @SubscribeEvent
   public static void registerParticles(RegisterParticleProvidersEvent event) {
      event.registerSpriteSet((ParticleType)ModParticles.UNOPENED_PARTICLE.get(), UnopenedParticle.Provider::new);
   }
}
