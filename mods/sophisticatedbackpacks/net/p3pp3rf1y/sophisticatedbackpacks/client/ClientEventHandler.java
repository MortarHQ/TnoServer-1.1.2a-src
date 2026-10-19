package net.p3pp3rf1y.sophisticatedbackpacks.client;

import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.ItemEntityRenderer;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.core.BlockPos;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.InputEvent.InteractionKeyMappingTriggered;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterGeometryLoaders;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.network.PacketDistributor;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackShapes;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.backpack.BackpackTemplateStorage;
import net.p3pp3rf1y.sophisticatedbackpacks.client.init.ModBlockColors;
import net.p3pp3rf1y.sophisticatedbackpacks.client.init.ModItemColors;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackBlockEntityRenderer;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackDynamicModel;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackItemStackRenderer;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.BackpackLayerRenderer;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.ClientBackpackContentsTooltip;
import net.p3pp3rf1y.sophisticatedbackpacks.client.render.ClientBackpackShapeProvider;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModBlocks;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;
import net.p3pp3rf1y.sophisticatedbackpacks.network.BlockPickPayload;
import net.p3pp3rf1y.sophisticatedbackpacks.network.RequestPlayerSettingsPayload;

public class ClientEventHandler {
   private static final String BACKPACK_REG_NAME = "backpack";
   public static final ModelLayerLocation BACKPACK_LAYER = new ModelLayerLocation(
      ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "backpack"), "main"
   );

   private ClientEventHandler() {
   }

   public static void registerHandlers(IEventBus modBus) {
      modBus.addListener(ClientEventHandler::onModelRegistry);
      modBus.addListener(ClientEventHandler::registerEntityRenderers);
      modBus.addListener(ClientEventHandler::registerReloadListener);
      modBus.addListener(ModItemColors::registerItemColorHandlers);
      modBus.addListener(ModBlockColors::registerBlockColorHandlers);
      modBus.addListener(ClientEventHandler::registerBackpackClientExtension);
      BackpackShapes.setShapeProvider(ClientBackpackShapeProvider.INSTANCE);
      IEventBus eventBus = NeoForge.EVENT_BUS;
      eventBus.addListener(ClientBackpackContentsTooltip::onWorldLoad);
      eventBus.addListener(ClientEventHandler::handleBlockPick);
      eventBus.addListener(ClientEventHandler::onPlayerLoggingIn);
      eventBus.addListener(BackpackStorage::onClientWorldLoad);
      eventBus.addListener(BackpackTemplateStorage::onClientWorldLoad);
   }

   private static void onPlayerLoggingIn(LoggingIn event) {
      PacketDistributor.sendToServer(new RequestPlayerSettingsPayload(), new CustomPacketPayload[0]);
   }

   private static void onModelRegistry(RegisterGeometryLoaders event) {
      event.register(ResourceLocation.fromNamespaceAndPath("sophisticatedbackpacks", "backpack"), BackpackDynamicModel.Loader.INSTANCE);
   }

   public static void registerReloadListener(RegisterClientReloadListenersEvent event) {
      event.registerReloadListener((ResourceManagerReloadListener)resourceManager -> {
         registerBackpackLayer();
         BackpackShapes.reloadDefaultShapeProvider(resourceManager);
         ClientBackpackShapeProvider.INSTANCE.rebuildShapes();
         BackpackShapes.setShapeProvider(ClientBackpackShapeProvider.INSTANCE);
      });
   }

   private static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer(ModItems.EVERLASTING_BACKPACK_ITEM_ENTITY.get(), ItemEntityRenderer::new);
      event.registerBlockEntityRenderer(ModBlocks.BACKPACK_TILE_TYPE.get(), context -> new BackpackBlockEntityRenderer());
   }

   private static void registerBackpackLayer() {
      EntityRenderDispatcher renderManager = Minecraft.getInstance().getEntityRenderDispatcher();
      Map<Model, EntityRenderer<? extends Player>> skinMap = renderManager.getSkinMap();

      for (EntityRenderer<? extends Player> renderer : skinMap.values()) {
         if (renderer instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
            livingEntityRenderer.addLayer(new BackpackLayerRenderer(livingEntityRenderer));
         }
      }

      renderManager.renderers.forEach((e, r) -> {
         if (r instanceof LivingEntityRenderer<?, ?> livingEntityRendererx) {
            livingEntityRendererx.addLayer(new BackpackLayerRenderer(livingEntityRendererx));
         }
      });
   }

   public static void handleBlockPick(InteractionKeyMappingTriggered event) {
      Minecraft mc = Minecraft.getInstance();
      LocalPlayer player = mc.player;
      if (player != null && !player.isCreative() && event.isPickBlock() && mc.hitResult != null && mc.hitResult.getType() == Type.BLOCK) {
         HitResult target = mc.hitResult;
         Level level = player.level();
         BlockPos pos = ((BlockHitResult)target).getBlockPos();
         BlockState state = level.getBlockState(pos);
         if (!state.isAir()) {
            ItemStack result = state.getCloneItemStack(target, level, pos, player);
            if (!result.isEmpty() && player.getInventory().findSlotMatchingItem(result) <= -1) {
               PacketDistributor.sendToServer(new BlockPickPayload(result), new CustomPacketPayload[0]);
            }
         }
      }
   }

   private static void registerBackpackClientExtension(RegisterClientExtensionsEvent event) {
      event.registerItem(
         new IClientItemExtensions() {
            private final Lazy<BlockEntityWithoutLevelRenderer> ister = Lazy.of(
               () -> new BackpackItemStackRenderer(Minecraft.getInstance().getBlockEntityRenderDispatcher(), Minecraft.getInstance().getEntityModels())
            );

            public BlockEntityWithoutLevelRenderer getCustomRenderer() {
               return (BlockEntityWithoutLevelRenderer)this.ister.get();
            }
         },
         new Item[]{
            (Item)ModItems.BACKPACK.get(),
            (Item)ModItems.COPPER_BACKPACK.get(),
            (Item)ModItems.IRON_BACKPACK.get(),
            (Item)ModItems.GOLD_BACKPACK.get(),
            (Item)ModItems.DIAMOND_BACKPACK.get(),
            (Item)ModItems.NETHERITE_BACKPACK.get()
         }
      );
   }
}
