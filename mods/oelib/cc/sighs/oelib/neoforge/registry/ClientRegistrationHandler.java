package cc.sighs.oelib.neoforge.registry;

import cc.sighs.oelib.OELib;
import cc.sighs.oelib.registry.action.ClientTooltipComponentAction;
import cc.sighs.oelib.registry.action.ColorBlockAction;
import cc.sighs.oelib.registry.action.ColorItemAction;
import cc.sighs.oelib.registry.action.CommandRegisterAction;
import cc.sighs.oelib.registry.action.EntityModelLayerAction;
import cc.sighs.oelib.registry.action.EntityRendererAction;
import cc.sighs.oelib.registry.action.KeyMappingAction;
import cc.sighs.oelib.registry.action.MenuAction;
import cc.sighs.oelib.registry.action.ParticleProviderAction;
import cc.sighs.oelib.registry.action.RegistrationAction;
import cc.sighs.oelib.registry.action.RenderTypeBlocksAction;
import cc.sighs.oelib.registry.action.RenderTypeFluidsAction;
import cc.sighs.oelib.registry.action.ShaderRegisterAction;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
import java.util.function.Supplier;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.commands.Commands.CommandSelection;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;

public final class ClientRegistrationHandler {
   private static final Queue<KeyMapping> KEY_MAPPINGS = new ConcurrentLinkedQueue<>();
   private static final Map<Class<?>, Function<?, ? extends ClientTooltipComponent>> TOOLTIP_FACTORIES = new ConcurrentHashMap<>();
   private static final List<ColorItemAction> ITEM_COLORS = new CopyOnWriteArrayList<>();
   private static final List<ColorBlockAction> BLOCK_COLORS = new CopyOnWriteArrayList<>();
   private static final List<EntityRendererAction<?>> ENTITY_RENDERERS = new CopyOnWriteArrayList<>();
   private static final List<EntityModelLayerAction> MODEL_LAYERS = new CopyOnWriteArrayList<>();
   private static final List<ParticleProviderAction<?>> PARTICLE_PROVIDERS = new CopyOnWriteArrayList<>();
   private static final List<RenderTypeBlocksAction> RENDER_BLOCKS = new CopyOnWriteArrayList<>();
   private static final List<RenderTypeFluidsAction> RENDER_FLUIDS = new CopyOnWriteArrayList<>();
   private static final List<CommandRegisterAction> CLIENT_COMMANDS = new CopyOnWriteArrayList<>();
   private static final List<ShaderRegisterAction> SHADERS = new CopyOnWriteArrayList<>();
   private static final List<MenuAction<?, ?>> MENU_SCREENS = new CopyOnWriteArrayList<>();

   public void perform(RegistrationAction action) {
      if (action instanceof KeyMappingAction(KeyMapping mapping)) {
         KEY_MAPPINGS.add(mapping);
      } else if (action instanceof ShaderRegisterAction shader) {
         SHADERS.add(shader);
      } else if (action instanceof ClientTooltipComponentAction<?> tooltipRaw) {
         ClientTooltipComponentAction<TooltipComponent> tooltip = (ClientTooltipComponentAction<TooltipComponent>)tooltipRaw;
         TOOLTIP_FACTORIES.put(tooltip.clazz(), tooltip.factory());
      } else if (action instanceof ColorItemAction cia) {
         ITEM_COLORS.add(cia);
      } else if (action instanceof ColorBlockAction cba) {
         BLOCK_COLORS.add(cba);
      } else if (action instanceof RenderTypeBlocksAction rtba) {
         RENDER_BLOCKS.add(rtba);
      } else if (action instanceof RenderTypeFluidsAction rtfa) {
         RENDER_FLUIDS.add(rtfa);
      } else if (action instanceof EntityRendererAction<?> era) {
         ENTITY_RENDERERS.add(era);
      } else if (action instanceof EntityModelLayerAction emla) {
         MODEL_LAYERS.add(emla);
      } else if (action instanceof MenuAction<?, ?> ma) {
         MENU_SCREENS.add(ma);
      } else if (action instanceof ParticleProviderAction<?> ppa) {
         PARTICLE_PROVIDERS.add(ppa);
      } else {
         if (action instanceof CommandRegisterAction cra && cra.clientOnly()) {
            CLIENT_COMMANDS.add(cra);
         }
      }
   }

   @EventBusSubscriber(modid = "oelib", value = Dist.CLIENT)
   public static class ClientEventHooks {
      @SubscribeEvent
      public static void onRegisterKeys(RegisterKeyMappingsEvent event) {
         KeyMapping km;
         while ((km = ClientRegistrationHandler.KEY_MAPPINGS.poll()) != null) {
            event.register(km);
         }
      }

      @SubscribeEvent(priority = EventPriority.HIGH)
      public static void onRegisterTooltipFactories(RegisterClientTooltipComponentFactoriesEvent event) {
         for (Entry<Class<?>, Function<?, ? extends ClientTooltipComponent>> e : ClientRegistrationHandler.TOOLTIP_FACTORIES.entrySet()) {
            Class<? extends TooltipComponent> clazz = (Class<? extends TooltipComponent>)e.getKey();
            Function<TooltipComponent, ClientTooltipComponent> fn = (Function<TooltipComponent, ClientTooltipComponent>)e.getValue();
            event.register(clazz, fn);
         }
      }

      @SubscribeEvent
      public static void onRegisterRenderers(RegisterRenderers event) {
         for (EntityRendererAction<?> eraRaw : ClientRegistrationHandler.ENTITY_RENDERERS) {
            EntityRendererAction<Entity> era = (EntityRendererAction<Entity>)eraRaw;
            event.registerEntityRenderer(era.type().get(), era.provider());
         }
      }

      @SubscribeEvent
      public static void onRegisterLayers(RegisterLayerDefinitions event) {
         for (EntityModelLayerAction emla : ClientRegistrationHandler.MODEL_LAYERS) {
            event.registerLayerDefinition(emla.location(), () -> emla.definition().get());
         }
      }

      @SubscribeEvent
      public static void onRegisterParticles(RegisterParticleProvidersEvent event) {
         for (ParticleProviderAction<?> ppaRaw : ClientRegistrationHandler.PARTICLE_PROVIDERS) {
            ParticleProviderAction<ParticleOptions> ppa = (ParticleProviderAction<ParticleOptions>)ppaRaw;
            event.registerSpecial(ppa.type(), ppa.provider());
         }
      }

      @SubscribeEvent
      public static void onRegisterItemColors(Item event) {
         for (ColorItemAction cia : ClientRegistrationHandler.ITEM_COLORS) {
            net.minecraft.world.item.Item[] arr = new net.minecraft.world.item.Item[cia.items().length];

            for (int i = 0; i < cia.items().length; i++) {
               arr[i] = cia.items()[i].get().asItem();
            }

            event.register(cia.color(), arr);
         }
      }

      @SubscribeEvent
      public static void onRegisterBlockColors(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block event) {
         for (ColorBlockAction cba : ClientRegistrationHandler.BLOCK_COLORS) {
            Block[] arr = new Block[cba.blocks().length];

            for (int i = 0; i < cba.blocks().length; i++) {
               arr[i] = cba.blocks()[i].get();
            }

            event.register(cba.color(), arr);
         }
      }

      @SubscribeEvent
      public static void onRegisterShaders(RegisterShadersEvent event) {
         for (ShaderRegisterAction shader : ClientRegistrationHandler.SHADERS) {
            try {
               ShaderInstance instance = new ShaderInstance(event.getResourceProvider(), shader.id(), shader.vertexFormat());
               event.registerShader(instance, shader.loadCallback());
            } catch (Exception e) {
               OELib.LOGGER.error("Failed to register shader {}", shader.id(), e);
            }
         }
      }

      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         event.enqueueWork(() -> {
            for (RenderTypeBlocksAction rt : ClientRegistrationHandler.RENDER_BLOCKS) {
               for (Supplier<? extends Block> b : rt.blocks()) {
                  ItemBlockRenderTypes.setRenderLayer(b.get(), rt.type());
               }
            }

            for (RenderTypeFluidsAction rf : ClientRegistrationHandler.RENDER_FLUIDS) {
               for (Supplier<? extends Fluid> f : rf.fluids()) {
                  ItemBlockRenderTypes.setRenderLayer(f.get(), rf.type());
               }
            }
         });
      }

      @SubscribeEvent
      public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
         for (CommandRegisterAction cra : ClientRegistrationHandler.CLIENT_COMMANDS) {
            cra.registrar().register(event.getDispatcher(), event.getBuildContext(), CommandSelection.INTEGRATED);
         }
      }

      @SubscribeEvent
      public static void onRegisterMenus(RegisterMenuScreensEvent event) {
         for (MenuAction<?, ?> ma : ClientRegistrationHandler.MENU_SCREENS) {
            registerMenuScreen(event, ma);
         }
      }

      private static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerMenuScreen(
         RegisterMenuScreensEvent event, MenuAction<H, S> action
      ) {
         event.register(action.type().get(), action.factory()::create);
      }
   }
}
