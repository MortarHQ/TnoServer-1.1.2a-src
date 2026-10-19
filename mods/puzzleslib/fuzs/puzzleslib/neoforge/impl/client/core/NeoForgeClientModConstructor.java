package fuzs.puzzleslib.neoforge.impl.client.core;

import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.core.v1.ContentRegistrationFlags;
import fuzs.puzzleslib.api.core.v1.resources.ForwardingReloadListenerHelper;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import fuzs.puzzleslib.impl.client.core.context.BlockRenderTypesContextImpl;
import fuzs.puzzleslib.impl.client.core.context.FluidRenderTypesContextImpl;
import fuzs.puzzleslib.neoforge.api.core.v1.NeoForgeModContainerHelper;
import fuzs.puzzleslib.neoforge.impl.client.core.context.AdditionalModelsContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.BlockColorProvidersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.BlockEntityRenderersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.BuiltinModelItemRendererContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ClientTooltipComponentsContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.CoreShadersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.EntityRenderersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.EntitySpectatorShaderContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ItemColorProvidersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ItemDecorationContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ItemModelPropertiesContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.KeyMappingsContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.LayerDefinitionsContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.LivingEntityRenderLayersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.MenuScreensContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ParticleProvidersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.RenderBuffersContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.ResourcePackSourcesContextNeoForgeImpl;
import fuzs.puzzleslib.neoforge.impl.client.core.context.SkullRenderersContextNeoForgeImpl;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;

public final class NeoForgeClientModConstructor {
   private NeoForgeClientModConstructor() {
   }

   public static void construct(
      ClientModConstructor constructor, String modId, Set<ContentRegistrationFlags> availableFlags, Set<ContentRegistrationFlags> flagsToHandle
   ) {
      NeoForgeModContainerHelper.getOptionalModEventBus(modId).ifPresent(eventBus -> {
         registerLoadingHandlers(constructor, modId, eventBus, availableFlags, flagsToHandle);
         constructor.onConstructMod();
      });
   }

   private static void registerLoadingHandlers(
      ClientModConstructor constructor,
      String modId,
      IEventBus eventBus,
      Set<ContentRegistrationFlags> availableFlags,
      Set<ContentRegistrationFlags> flagsToHandle
   ) {
      List<ResourceManagerReloadListener> dynamicRenderers = new ArrayList<>();
      eventBus.addListener(evt -> evt.enqueueWork(() -> {
         constructor.onClientSetup();
         constructor.onRegisterItemModelProperties(new ItemModelPropertiesContextNeoForgeImpl());
         constructor.onRegisterBlockRenderTypes(new BlockRenderTypesContextImpl());
         constructor.onRegisterFluidRenderTypes(new FluidRenderTypesContextImpl());
      }));
      eventBus.addListener(evt -> constructor.onRegisterMenuScreens(new MenuScreensContextNeoForgeImpl(evt)));
      eventBus.addListener(evt -> {
         constructor.onRegisterEntityRenderers(new EntityRenderersContextNeoForgeImpl(evt::registerEntityRenderer));
         constructor.onRegisterBlockEntityRenderers(new BlockEntityRenderersContextNeoForgeImpl(evt::registerBlockEntityRenderer));
      });
      eventBus.addListener(evt -> constructor.onRegisterClientTooltipComponents(new ClientTooltipComponentsContextNeoForgeImpl(evt::register)));
      eventBus.addListener(evt -> constructor.onRegisterParticleProviders(new ParticleProvidersContextNeoForgeImpl(evt)));
      eventBus.addListener(evt -> constructor.onRegisterLayerDefinitions(new LayerDefinitionsContextNeoForgeImpl(evt::registerLayerDefinition)));
      eventBus.addListener(evt -> constructor.onRegisterAdditionalModels(new AdditionalModelsContextNeoForgeImpl(evt::register)));
      eventBus.addListener(evt -> constructor.onRegisterItemDecorations(new ItemDecorationContextNeoForgeImpl(evt::register)));
      eventBus.addListener(evt -> constructor.onRegisterEntitySpectatorShaders(new EntitySpectatorShaderContextNeoForgeImpl(evt::register)));
      eventBus.addListener(evt -> constructor.onRegisterSkullRenderers(new SkullRenderersContextNeoForgeImpl(evt.getEntityModelSet(), evt::registerSkullModel)));
      eventBus.addListener(
         evt -> {
            if (availableFlags.contains(ContentRegistrationFlags.DYNAMIC_RENDERERS)) {
               evt.registerReloadListener(
                  ForwardingReloadListenerHelper.fromResourceManagerReloadListeners(
                     ResourceLocationHelper.fromNamespaceAndPath(modId, "built_in_model_item_renderers"), dynamicRenderers
                  )
               );
            }
         }
      );
      eventBus.addListener(evt -> constructor.onRegisterLivingEntityRenderLayers(new LivingEntityRenderLayersContextNeoForgeImpl(evt)));
      eventBus.addListener(evt -> constructor.onRegisterKeyMappings(new KeyMappingsContextNeoForgeImpl(evt::register)));
      eventBus.addListener(
         evt -> constructor.onRegisterBlockColorProviders(
            new BlockColorProvidersContextNeoForgeImpl((x$0, xva$1) -> evt.register(x$0, new Block[]{xva$1}), evt.getBlockColors())
         )
      );
      eventBus.addListener(
         evt -> constructor.onRegisterItemColorProviders(
            new ItemColorProvidersContextNeoForgeImpl((x$0, xva$1) -> evt.register(x$0, new ItemLike[]{xva$1}), evt.getItemColors())
         )
      );
      eventBus.addListener(evt -> {
         if (evt.getPackType() == PackType.CLIENT_RESOURCES) {
            constructor.onAddResourcePackFinders(new ResourcePackSourcesContextNeoForgeImpl(evt));
         }
      });
      eventBus.addListener(evt -> constructor.onRegisterCoreShaders(new CoreShadersContextNeoForgeImpl(evt::registerShader, evt.getResourceProvider())));
      eventBus.addListener(evt -> constructor.onRegisterRenderBuffers(new RenderBuffersContextNeoForgeImpl(evt::registerRenderBuffer)));
      eventBus.addListener(
         evt -> constructor.onRegisterBuiltinModelItemRenderers(
            new BuiltinModelItemRendererContextNeoForgeImpl((x$0, xva$1) -> evt.registerItem(x$0, new Item[]{xva$1}), modId, dynamicRenderers)
         )
      );
   }
}
