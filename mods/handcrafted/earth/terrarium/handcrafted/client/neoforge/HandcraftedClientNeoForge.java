package earth.terrarium.handcrafted.client.neoforge;

import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import earth.terrarium.handcrafted.client.HandcraftedClient;
import earth.terrarium.handcrafted.client.utils.ClientPlatformUtils;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;

@EventBusSubscriber(value = Dist.CLIENT, bus = Bus.MOD)
public class HandcraftedClientNeoForge {
   public static void init() {
   }

   @SubscribeEvent
   public static void onClientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(HandcraftedClient::init);
   }

   @SubscribeEvent
   public static void onRegisterRenderers(RegisterRenderers event) {
      HandcraftedClient.onRegisterBlockRenderers(new ClientPlatformUtils.BlockRendererRegistry() {
         @Override
         public <T extends BlockEntity> void register(RegistryEntry<? extends BlockEntityType<? extends T>> type, BlockEntityRendererProvider<T> factory) {
            event.registerBlockEntityRenderer((BlockEntityType)type.get(), factory);
         }
      });
   }

   @SubscribeEvent
   public static void onRegisterLayerDefinitions(RegisterLayerDefinitions event) {
      HandcraftedClient.onRegisterEntityLayers(event::registerLayerDefinition);
   }
}
