package net.jadenxgamer.netherexp.event;

import net.jadenxgamer.elysium_api.impl.client.assetdriven.lightmap_settings.LightmapSettingsManager;
import net.jadenxgamer.netherexp.NetherExp;
import net.jadenxgamer.netherexp.NetherExpClient;
import net.jadenxgamer.netherexp.client.JNEFogRenderer;
import net.jadenxgamer.netherexp.client.gui.BetaPopupWarning;
import net.jadenxgamer.netherexp.client.gui.hud.StampedeHungerHud;
import net.jadenxgamer.netherexp.client.rendering.JNEShaders;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEFluidExtensions;
import net.jadenxgamer.netherexp.client.rendering.extensions.JNEItemExtensions;
import net.jadenxgamer.netherexp.client.rendering.layer.BurningFilterLayer;
import net.jadenxgamer.netherexp.client.shader.NetherHeatDistortionPostprocessor;
import net.jadenxgamer.netherexp.client.shader.SoulGlassPostProcessor;
import net.jadenxgamer.netherexp.config.JNEConfigImpl;
import net.jadenxgamer.netherexp.config.JNEConfigs;
import net.jadenxgamer.netherexp.core.block.MagmaCreamBlock;
import net.jadenxgamer.netherexp.core.item.components.AntidoteContents;
import net.jadenxgamer.netherexp.registry.JNEBlocks;
import net.jadenxgamer.netherexp.registry.JNEFluids;
import net.jadenxgamer.netherexp.registry.JNEItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.MovementInputUpdateEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientTickEvent.Post;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.ViewportEvent.ComputeFogColor;
import net.neoforged.neoforge.client.event.ViewportEvent.RenderFog;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

@EventBusSubscriber(modid = "netherexp", value = Dist.CLIENT)
public class JNEClientEvents {
   @SubscribeEvent
   public static void onClientTickPre(Pre event) {
      Minecraft client = Minecraft.getInstance();
      NetherExpClient.SubmergedStates.tick();
      NetherExpClient.HandlePostShaders.tick(client);
   }

   @SubscribeEvent
   public static void onClientTickPost(Post event) {
      if (JNEConfigImpl.COMMON.isLoaded() && JNEConfigImpl.STARTUP.isLoaded()) {
         if (NetherExpClient.shouldShowBetaPopup) {
            Minecraft client = Minecraft.getInstance();
            if (client.player != null && client.screen == null) {
               client.setScreen(new BetaPopupWarning());
               NetherExpClient.shouldShowBetaPopup = false;
            }
         }

         if ((Boolean)JNEConfigs.ENABLE_NETHER_BIOME_LIGHTMAPS.get()) {
            LightmapSettingsManager.disableEventFlag(NetherExp.netherexpPath("disable_jne_lightmaps"));
         } else {
            LightmapSettingsManager.enableEventFlag(NetherExp.netherexpPath("disable_jne_lightmaps"));
         }
      }
   }

   @SubscribeEvent
   public static void onAddLayers(AddLayers event) {
      for (EntityType<?> entityType : event.getEntityTypes()) {
         if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            livingRenderer.addLayer(new BurningFilterLayer(livingRenderer));
         }
      }

      for (Model skinModel : event.getSkins()) {
         if (event.getSkin(skinModel) instanceof LivingEntityRenderer<?, ?> livingRenderer) {
            livingRenderer.addLayer(new BurningFilterLayer(livingRenderer));
         }
      }
   }

   @SubscribeEvent
   public static void registerOverlays(RegisterGuiLayersEvent event) {
      event.registerAboveAll(NetherExp.netherexpPath("stampede_hunger"), StampedeHungerHud.OVERLAY);
   }

   @SubscribeEvent
   public static void onPlayerLogin(LoggingIn event) {
   }

   @SubscribeEvent(priority = EventPriority.HIGH)
   public static void fogRender(RenderFog event) {
      JNEFogRenderer.fogRender(event);
   }

   @SubscribeEvent
   public static void fogColor(ComputeFogColor event) {
      JNEFogRenderer.fogColor(event);
   }

   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      NetherExpClient.registerRenderers();
      PostProcessHandler.addInstance(SoulGlassPostProcessor.INSTANCE);
      PostProcessHandler.addInstance(NetherHeatDistortionPostprocessor.INSTANCE);
      event.enqueueWork(NetherExpClient::registerProperties);
   }

   @SubscribeEvent
   public static void renderParticles(RegisterParticleProvidersEvent event) {
      NetherExpClient.registerParticles(event);
   }

   @SubscribeEvent
   public static void registerLayerDefinitions(RegisterLayerDefinitions event) {
      NetherExpClient.registerLayerDefinitions(event);
   }

   @SubscribeEvent
   public static void registerShaders(RegisterShadersEvent event) {
      JNEShaders.SHADERS.init(event);
   }

   @SubscribeEvent
   private static void clientExtensions(RegisterClientExtensionsEvent event) {
      event.registerFluidType(JNEFluidExtensions.ectoplasmExt, new FluidType[]{JNEFluids.ECTOPLASM_TYPE.get()});
      event.registerItem(JNEItemExtensions.itemExt, new Item[]{JNEItems.WILL_O_WISP.get()});
      event.registerItem(JNEItemExtensions.itemExt, new Item[]{JNEItems.SHOTGUN_FIST.get()});
      event.registerItem(JNEItemExtensions.itemExt, new Item[]{JNEItems.PUMP_CHARGE_SHOTGUN.get()});
   }

   @SubscribeEvent
   public static void itemTints(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item event) {
      event.register((stack, tint) -> tint > 0 ? -1 : AntidoteContents.getColor(stack), new ItemLike[]{(ItemLike)JNEItems.ANTIDOTE.get()});
      event.register((stack, tint) -> tint > 0 ? -1 : AntidoteContents.getColor(stack), new ItemLike[]{(ItemLike)JNEItems.GRENADE_ANTIDOTE.get()});
   }

   @SubscribeEvent
   public static void onMovementInput(MovementInputUpdateEvent event) {
      Player player = event.getEntity();
      BlockState state = player.getBlockStateOn();
      if (state.is(JNEBlocks.MAGMA_CREAM_BLOCK.get()) && (Boolean)state.getValue(MagmaCreamBlock.UP)) {
         Input input = event.getInput();
         input.forwardImpulse = 0.0F;
         input.leftImpulse = 0.0F;
         input.up = false;
         input.down = false;
         input.left = false;
         input.right = false;
      }
   }
}
