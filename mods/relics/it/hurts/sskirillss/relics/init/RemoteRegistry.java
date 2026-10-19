package it.hurts.sskirillss.relics.init;

import it.hurts.octostudios.octolib.module.particle.trail.EntityTrailRegistry;
import it.hurts.sskirillss.relics.client.gui.layers.ActiveAbilitiesLayer;
import it.hurts.sskirillss.relics.client.gui.layers.InfoTileLayer;
import it.hurts.sskirillss.relics.client.gui.layers.LeafyRingHideLayer;
import it.hurts.sskirillss.relics.client.gui.layers.PhantomBootBridgeLayer;
import it.hurts.sskirillss.relics.client.models.items.CurioModel;
import it.hurts.sskirillss.relics.client.models.layers.WingsLayer;
import it.hurts.sskirillss.relics.client.renderer.entities.BlockSimulationRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.DissectionRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.NullRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.RelicExperienceOrbRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.ShadowGlaiveRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.SolidSnowballRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.SporeRenderer;
import it.hurts.sskirillss.relics.client.renderer.entities.StalactiteRenderer;
import it.hurts.sskirillss.relics.client.renderer.items.items.CurioRenderer;
import it.hurts.sskirillss.relics.client.renderer.tiles.ResearchingTableRenderer;
import it.hurts.sskirillss.relics.entities.DeathEssenceEntity;
import it.hurts.sskirillss.relics.entities.LifeEssenceEntity;
import it.hurts.sskirillss.relics.entities.ShadowGlaiveEntity;
import it.hurts.sskirillss.relics.entities.SporeEntity;
import it.hurts.sskirillss.relics.items.relics.InfiniteHamItem;
import it.hurts.sskirillss.relics.items.relics.base.IRelicItem;
import it.hurts.sskirillss.relics.items.relics.base.IRenderableCurio;
import it.hurts.sskirillss.relics.items.relics.necklace.HolyLocketItem;
import it.hurts.sskirillss.relics.utils.EntityUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@EventBusSubscriber(modid = "relics", bus = Bus.MOD, value = Dist.CLIENT)
public class RemoteRegistry {
   @SubscribeEvent
   public static void setupClient(FMLClientSetupEvent event) {
      ItemBlockRenderTypes.setRenderLayer((Block)BlockRegistry.RESEARCHING_TABLE.get(), RenderType.cutout());
      event.enqueueWork(
         () -> {
            ItemProperties.register(
               (Item)ItemRegistry.INFINITY_HAM.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "pieces"),
               (stack, world, entity, id) -> ((InfiniteHamItem)stack.getItem()).getPieces(stack)
            );
            ItemProperties.register(
               (Item)ItemRegistry.SHADOW_GLAIVE.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "charges"),
               (stack, world, entity, id) -> Math.min(8, (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0))
            );
            ItemProperties.register(
               (Item)ItemRegistry.MAGIC_MIRROR.get(), ResourceLocation.fromNamespaceAndPath("relics", "world"), (stack, world, entity, id) -> {
                  Entity e = Minecraft.getInstance().getCameraEntity();
                  if (e == null) {
                     return 0.0F;
                  }

                  return switch (e.getCommandSenderWorld().dimension().location().getPath()) {
                     case "overworld" -> 1.0F;
                     case "the_nether" -> 2.0F;
                     case "the_end" -> 3.0F;
                     default -> 0.0F;
                  };
               }
            );
            ItemProperties.register(
               (Item)ItemRegistry.SHADOW_GLAIVE.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "charges"),
               (stack, world, entity, id) -> Math.min(8, (Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0))
            );
            ItemProperties.register(
               (Item)ItemRegistry.MAGMA_WALKER.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "heat"),
               (stack, world, entity, id) -> ((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue()
                     >= ((IRelicItem)stack.getItem()).getStatValue(stack, "pace", "time")
                  ? 1.0F
                  : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.AQUA_WALKER.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "drench"),
               (stack, world, entity, id) -> ((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue()
                     >= ((IRelicItem)stack.getItem()).getStatValue(stack, "walking", "time")
                  ? 1.0F
                  : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.ELYTRA_BOOSTER.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "fuel"),
               (stack, world, entity, id) -> stack.getOrDefault(DataComponentRegistry.CHARGE, 0) > 0 ? 1.0F : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.SOLID_SNOWBALL.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "snow"),
               (stack, world, entity, id) -> {
                  ItemStack relic = EntityUtils.findEquippedCurio(entity, (Item)ItemRegistry.WOOL_MITTEN.get());
                  return relic.isEmpty()
                     ? 3.0F
                     : (int)Math.floor(
                        ((Integer)stack.getOrDefault(DataComponentRegistry.CHARGE, 0)).intValue()
                           / (((IRelicItem)relic.getItem()).getStatValue(relic, "mold", "size") / 3.0)
                     );
               }
            );
            ItemProperties.register(
               (Item)ItemRegistry.ROLLER_SKATES.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "active"),
               (stack, world, entity, id) -> stack.getOrDefault(DataComponentRegistry.CHARGE, 0) > 0 ? 1.0F : 0.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.BLAZING_FLASK.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "active"),
               (stack, world, entity, id) -> stack.get(DataComponentRegistry.WORLD_POSITION) == null ? 0.0F : 1.0F
            );
            ItemProperties.register(
               (Item)ItemRegistry.HOLY_LOCKET.get(),
               ResourceLocation.fromNamespaceAndPath("relics", "mode"),
               (stack, world, entity, id) -> ((HolyLocketItem)stack.getItem()).getMode(stack).getIndex()
            );
         }
      );

      for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
         if (item instanceof IRenderableCurio) {
            CuriosRendererRegistry.register(item, CurioRenderer::new);
         }
      }

      EntityTrailRegistry.registerProvider((EntityType)EntityRegistry.DEATH_ESSENCE.get(), DeathEssenceEntity.TrailProvider::new);
      EntityTrailRegistry.registerProvider((EntityType)EntityRegistry.LIFE_ESSENCE.get(), LifeEssenceEntity.TrailProvider::new);
      EntityTrailRegistry.registerProvider((EntityType)EntityRegistry.SHADOW_GLAIVE.get(), ShadowGlaiveEntity.TrailProvider::new);
      EntityTrailRegistry.registerProvider((EntityType)EntityRegistry.SPORE.get(), SporeEntity.TrailProvider::new);
   }

   @SubscribeEvent
   public static void registerLayers(RegisterLayerDefinitions event) {
      for (Item item : BuiltInRegistries.ITEM.stream().toList()) {
         if (item instanceof IRenderableCurio renderable) {
            event.registerLayerDefinition(CurioModel.getLayerLocation(item), renderable::constructLayerDefinition);
         }
      }
   }

   @SubscribeEvent
   public static void onPlayerRendererRegister(AddLayers event) {
      for (Model skinType : event.getSkins()) {
         if (event.getSkin(skinType) instanceof PlayerRenderer playerRenderer) {
            playerRenderer.addLayer(new WingsLayer(playerRenderer));
         }
      }
   }

   @SubscribeEvent
   public static void entityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)EntityRegistry.SHADOW_GLAIVE.get(), ShadowGlaiveRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.BLOCK_SIMULATION.get(), BlockSimulationRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SHOCKWAVE.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.LIFE_ESSENCE.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DEATH_ESSENCE.get(), NullRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.STALACTITE.get(), StalactiteRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.DISSECTION.get(), DissectionRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SPORE.get(), SporeRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.SOLID_SNOWBALL.get(), SolidSnowballRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.RELIC_EXPERIENCE_ORB.get(), RelicExperienceOrbRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.THROWN_RELIC_EXPERIENCE_BOTTLE.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)EntityRegistry.CHAIR.get(), NullRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)TileRegistry.RESEARCHING_TABLE.get(), ResearchingTableRenderer::new);
   }

   @SubscribeEvent
   public static void onTooltipRegistry(RegisterClientTooltipComponentFactoriesEvent event) {
      event.register(InfiniteHamItem.InfiniteHamTooltip.class, InfiniteHamItem.ClientInfiniteHamTooltip::new);
   }

   @SubscribeEvent
   public static void onOverlayRegistry(RegisterGuiLayersEvent event) {
      event.registerBelowAll(ResourceLocation.fromNamespaceAndPath("relics", "info_tile"), new InfoTileLayer());
      event.registerBelowAll(ResourceLocation.fromNamespaceAndPath("relics", "active_abilities"), new ActiveAbilitiesLayer());
      event.registerBelowAll(ResourceLocation.fromNamespaceAndPath("relics", "leafy_ring_hide"), new LeafyRingHideLayer());
      event.registerBelowAll(ResourceLocation.fromNamespaceAndPath("relics", "phantom_boot_bridge"), new PhantomBootBridgeLayer());
   }
}
