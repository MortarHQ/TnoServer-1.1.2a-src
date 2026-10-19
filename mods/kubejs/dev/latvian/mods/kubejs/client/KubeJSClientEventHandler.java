package dev.latvian.mods.kubejs.client;

import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.serialization.DynamicOps;
import dev.latvian.mods.kubejs.CommonProperties;
import dev.latvian.mods.kubejs.KubeJS;
import dev.latvian.mods.kubejs.block.BlockBuilder;
import dev.latvian.mods.kubejs.client.highlight.HighlightRenderer;
import dev.latvian.mods.kubejs.command.KubeJSClientCommands;
import dev.latvian.mods.kubejs.fluid.FluidBlockBuilder;
import dev.latvian.mods.kubejs.fluid.FluidBuilder;
import dev.latvian.mods.kubejs.fluid.FluidTypeBuilder;
import dev.latvian.mods.kubejs.gui.KubeJSMenus;
import dev.latvian.mods.kubejs.gui.KubeJSScreen;
import dev.latvian.mods.kubejs.item.DynamicItemTooltipsKubeEvent;
import dev.latvian.mods.kubejs.item.ItemBuilder;
import dev.latvian.mods.kubejs.item.ItemModelPropertiesKubeEvent;
import dev.latvian.mods.kubejs.item.ModifyItemTooltipsKubeEvent;
import dev.latvian.mods.kubejs.plugin.builtin.event.ClientEvents;
import dev.latvian.mods.kubejs.plugin.builtin.event.ItemEvents;
import dev.latvian.mods.kubejs.plugin.builtin.event.KeyBindEvents;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.TextIcons;
import dev.latvian.mods.kubejs.registry.BuilderBase;
import dev.latvian.mods.kubejs.registry.RegistryObjectStorage;
import dev.latvian.mods.kubejs.script.ConsoleJS;
import dev.latvian.mods.kubejs.script.PlatformWrapper;
import dev.latvian.mods.kubejs.script.ScriptType;
import dev.latvian.mods.kubejs.stages.Stages;
import dev.latvian.mods.kubejs.text.action.DynamicTextAction;
import dev.latvian.mods.kubejs.text.action.TextAction;
import dev.latvian.mods.kubejs.text.tooltip.ItemTooltipData;
import dev.latvian.mods.kubejs.text.tooltip.TooltipRequirements;
import dev.latvian.mods.kubejs.util.ID;
import dev.latvian.mods.kubejs.util.StackTraceCollector;
import dev.latvian.mods.kubejs.util.Tristate;
import dev.latvian.mods.kubejs.web.LocalWebServer;
import dev.latvian.mods.kubejs.web.WebServerProperties;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.recipebook.RecipeUpdateListener;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.component.TypedDataComponent;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SpawnEggItem;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingIn;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent.LoggingOut;
import net.neoforged.neoforge.client.event.ClientTickEvent.Pre;
import net.neoforged.neoforge.client.event.CustomizeGuiOverlayEvent.DebugText;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.RenderGuiEvent.Post;
import net.neoforged.neoforge.client.event.RenderLevelStageEvent.Stage;
import net.neoforged.neoforge.client.event.ScreenEvent.Opening;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.settings.KeyConflictContext;
import net.neoforged.neoforge.client.settings.KeyModifier;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent;
import net.neoforged.neoforge.event.TagsUpdatedEvent.UpdateCause;
import net.neoforged.neoforge.event.entity.player.ItemTooltipEvent;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;

@EventBusSubscriber(modid = "kubejs", value = Dist.CLIENT)
public class KubeJSClientEventHandler {
   public static final Pattern COMPONENT_ERROR = ConsoleJS.methodPattern(KubeJSClientEventHandler.class, "onItemTooltip");
   private static List<String> lastComponentError = List.of();

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void setupClient(FMLClientSetupEvent event) {
      KubeJS.PROXY = new KubeJSClient();
      event.enqueueWork(KubeJSClientEventHandler::setupClient0);
   }

   @SubscribeEvent
   public static void addClientPacks(AddPackFindersEvent event) {
      if (event.getPackType() == PackType.CLIENT_RESOURCES) {
         event.addRepositorySource(new KubeJSResourcePackFinder());
      }
   }

   private static void setupClient0() {
      if (!PlatformWrapper.isGeneratingData() && Minecraft.getInstance() != null && WebServerProperties.get().enabled) {
         LocalWebServer.start(Minecraft.getInstance(), true);
      }

      ItemEvents.MODEL_PROPERTIES.post(ScriptType.STARTUP, new ItemModelPropertiesKubeEvent());

      for (BuilderBase<? extends Block> builder : RegistryObjectStorage.BLOCK) {
         if (builder instanceof BlockBuilder b) {
            FluidBlockBuilder fb;
            switch (b instanceof fb ? fb.fluidBuilder.fluidType.renderType : b.renderType) {
               case CUTOUT:
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutout());
                  break;
               case CUTOUT_MIPPED:
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.cutoutMipped());
                  break;
               case TRANSLUCENT:
                  ItemBlockRenderTypes.setRenderLayer(b.get(), RenderType.translucent());
            }
         }
      }

      for (BuilderBase<? extends Fluid> builder : RegistryObjectStorage.FLUID) {
         if (builder instanceof FluidBuilder b) {
            switch (b.fluidType.renderType) {
               case CUTOUT:
                  ItemBlockRenderTypes.setRenderLayer(b.get().getSource(), RenderType.cutout());
                  ItemBlockRenderTypes.setRenderLayer(b.get().getFlowing(), RenderType.cutout());
                  break;
               case CUTOUT_MIPPED:
                  ItemBlockRenderTypes.setRenderLayer(b.get().getSource(), RenderType.cutoutMipped());
                  ItemBlockRenderTypes.setRenderLayer(b.get().getFlowing(), RenderType.cutoutMipped());
                  break;
               case TRANSLUCENT:
                  ItemBlockRenderTypes.setRenderLayer(b.get().getSource(), RenderType.translucent());
                  ItemBlockRenderTypes.setRenderLayer(b.get().getFlowing(), RenderType.translucent());
            }
         }
      }

      ArrayList<ItemTooltipData> list = new ArrayList<>();
      ItemEvents.MODIFY_TOOLTIPS.post(ScriptType.CLIENT, new ModifyItemTooltipsKubeEvent(list::add));
      KubeJSClient.clientItemTooltips = List.copyOf(list);
   }

   @SubscribeEvent
   public static void blockColors(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block event) {
      for (BuilderBase<? extends Block> builder : RegistryObjectStorage.BLOCK) {
         if (builder instanceof BlockBuilder b && b.tint != null) {
            event.register(new BlockTintFunctionWrapper(b.tint), new Block[]{b.get()});
         }
      }
   }

   @SubscribeEvent
   public static void itemColors(net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item event) {
      for (BuilderBase<? extends Item> builder : RegistryObjectStorage.ITEM) {
         if (builder instanceof ItemBuilder b && b.tint != null) {
            event.register(new ItemTintFunctionWrapper(b.tint), new ItemLike[]{(ItemLike)b.get()});
         }
      }
   }

   @SubscribeEvent
   public static void registerMenuScreens(RegisterMenuScreensEvent event) {
      event.register(KubeJSMenus.MENU.get(), KubeJSScreen::new);
      ClientEvents.MENU_SCREEN_REGISTRY.post(ScriptType.STARTUP, new MenuScreenRegistryKubeEvent(event));
   }

   @SubscribeEvent
   public static void registerRenderers(RegisterRenderers event) {
      ClientEvents.ENTITY_RENDERER_REGISTRY.post(ScriptType.STARTUP, new EntityRendererRegistryKubeEvent(event));
      ClientEvents.BLOCK_ENTITY_RENDERER_REGISTRY.post(ScriptType.STARTUP, new BlockEntityRendererRegistryKubeEvent(event));
   }

   @SubscribeEvent
   public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
      event.register(
         HighlightRenderer.keyMapping = new KeyMapping(
            "key.kubejs.kubedex", KeyConflictContext.UNIVERSAL, KeyModifier.NONE, Type.KEYSYM, 75, "key.categories.kubejs"
         )
      );
      KeybindRegistryKubeEvent kubeEvent = new KeybindRegistryKubeEvent();
      KeyBindEvents.REGISTRY.post(kubeEvent);

      for (KubeJSKeybinds.KubeKey bind : kubeEvent.build()) {
         event.register(bind.mapping);
      }

      KubeJSKeybinds.triggerReload();
   }

   @SubscribeEvent
   public static void registerCoreShaders(RegisterShadersEvent event) throws IOException {
      event.registerShader(
         new ShaderInstance(event.getResourceProvider(), ID.mc("kubejs/rendertype_highlight"), DefaultVertexFormat.POSITION_COLOR),
         s -> HighlightRenderer.INSTANCE.highlightShader = s
      );
   }

   @SubscribeEvent
   public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      for (BuilderBase<? extends FluidType> builder : RegistryObjectStorage.FLUID_TYPE) {
         if (builder instanceof FluidTypeBuilder b) {
            event.registerFluidType(new IClientFluidTypeExtensions() {
               public ResourceLocation getStillTexture() {
                  return b.actualStillTexture;
               }

               public ResourceLocation getFlowingTexture() {
                  return b.actualFlowingTexture;
               }

               public ResourceLocation getOverlayTexture() {
                  return b.blockOverlayTexture;
               }

               @Nullable
               public ResourceLocation getRenderOverlayTexture(Minecraft mc) {
                  return b.screenOverlayTexture;
               }
            }, new FluidType[]{b.get()});
         }
      }
   }

   @SubscribeEvent
   public static void registerParticleProviders(RegisterParticleProvidersEvent event) {
      if (ClientEvents.PARTICLE_PROVIDER_REGISTRY.hasListeners()) {
         ClientEvents.PARTICLE_PROVIDER_REGISTRY.post(new ParticleProviderRegistryKubeEvent(event));
      }
   }

   @SubscribeEvent
   public static void onRegisterClientCommands(RegisterClientCommandsEvent event) {
      KubeJSClientCommands.register(event.getDispatcher());
   }

   @SubscribeEvent
   public static void debugInfo(DebugText event) {
      Minecraft mc = Minecraft.getInstance();
      if (mc.player != null) {
         if (ClientEvents.DEBUG_LEFT.hasListeners()) {
            ClientEvents.DEBUG_LEFT.post(new DebugInfoKubeEvent(mc.player, event.getLeft()));
         }

         if (ClientEvents.DEBUG_RIGHT.hasListeners()) {
            ClientEvents.DEBUG_RIGHT.post(new DebugInfoKubeEvent(mc.player, event.getRight()));
         }
      }
   }

   private static <T> List<String> appendComponentValue(DynamicOps<Tag> ops, MutableComponent line, DataComponentType<T> type, T value) {
      if (value == null) {
         line.append(Component.literal("null").kjs$red());
         return List.of();
      }

      if (value instanceof Component c) {
         line.append(Component.empty().kjs$gold().append(c));
      }

      try {
         Tag tag = (Tag)type.codecOrThrow().encodeStart(ops, value).getOrThrow();
         line.append(NbtUtils.toPrettyComponent(tag));
         return List.of();
      } catch (Throwable ex) {
         line.append(Component.literal(String.valueOf(value)).kjs$red());
         ArrayList<String> lines = new ArrayList<>();
         ex.printStackTrace(new StackTraceCollector(lines, COMPONENT_ERROR, ConsoleJS.ERROR_REDUCE));
         return lines;
      }
   }

   public static boolean testRequirements(Minecraft mc, DynamicItemTooltipsKubeEvent event, TooltipRequirements r) {
      if (!r.advanced().test(event.advanced)) {
         return false;
      }

      if (!r.creative().test(event.creative)) {
         return false;
      }

      if (!r.shift().test(event.shift)) {
         return false;
      }

      if (!r.ctrl().test(event.ctrl)) {
         return false;
      }

      if (!r.alt().test(event.alt)) {
         return false;
      }

      if (!r.stages().isEmpty()) {
         Stages stages = mc.player.kjs$getStages();

         for (Entry<String, Tristate> entry : r.stages().entrySet()) {
            if (entry.getValue() != Tristate.DEFAULT && !entry.getValue().test(stages.has(entry.getKey()))) {
               return false;
            }
         }
      }

      return true;
   }

   private static void handleItemTooltips(Minecraft mc, ItemTooltipData tooltip, DynamicItemTooltipsKubeEvent event) {
      if ((tooltip.filter().isEmpty() || tooltip.filter().get().test(event.item))
         && (tooltip.requirements().isEmpty() || testRequirements(mc, event, tooltip.requirements().get()))) {
         for (TextAction action : tooltip.actions()) {
            if (action instanceof DynamicTextAction(String id)) {
               try {
                  ItemEvents.DYNAMIC_TOOLTIPS.post(ScriptType.CLIENT, id, event);
               } catch (Exception ex) {
                  ConsoleJS.CLIENT.error("Item " + event.item.kjs$getId() + " dynamic tooltip error", ex);
               }
            } else {
               action.apply(event.lines);
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void onItemTooltip(ItemTooltipEvent event) {
      ItemStack stack = event.getItemStack();
      if (!stack.isEmpty()) {
         Minecraft mc = Minecraft.getInstance();
         List<Component> lines = event.getToolTip();
         TooltipFlag flags = event.getFlags();
         KubeSessionData sessionData = KubeSessionData.of(mc);
         DynamicItemTooltipsKubeEvent dynamicEvent = new DynamicItemTooltipsKubeEvent(stack, flags, lines, sessionData == null);

         for (ItemTooltipData tooltip : KubeJSClient.clientItemTooltips) {
            handleItemTooltips(mc, tooltip, dynamicEvent);
         }

         if (sessionData != null) {
            for (ItemTooltipData tooltip : sessionData.itemTooltips) {
               handleItemTooltips(mc, tooltip, dynamicEvent);
            }
         }

         boolean advanced = flags.isAdvanced();
         ClientProperties properties = ClientProperties.get();
         if (mc.level != null && advanced && properties.showComponents && dynamicEvent.alt) {
            Registry<DataComponentType<?>> components = BuiltInRegistries.DATA_COMPONENT_TYPE;
            RegistryOps<Tag> ops = mc.level.registryAccess().createSerializationContext(NbtOps.INSTANCE);
            ArrayList<String> errors = new ArrayList<>(0);

            for (Entry<DataComponentType<?>, Optional<?>> entry : stack.getComponentsPatch().entrySet()) {
               ResourceLocation id = components.getKey(entry.getKey());
               if (id != null) {
                  MutableComponent line = Component.empty();
                  line.append(TextIcons.icon(Component.literal("Q.")));
                  if (entry.getValue().isEmpty()) {
                     line.append(Component.literal("!"));
                  }

                  line.append(Component.literal(ID.reduce(id)).kjs$yellow());
                  if (entry.getValue().isPresent()) {
                     line.append(Component.literal("="));
                     List errors0 = appendComponentValue(ops, line, (DataComponentType<Object>)entry.getKey(), entry.getValue().get());
                     if (!errors0.isEmpty()) {
                        lines.add(Component.literal(ID.reduce(id) + " errored, see log").kjs$darkRed());
                        errors.add("Failed to encode value of " + id + ": " + entry.getValue().get());
                        errors.addAll(errors0);
                     }
                  }

                  lines.add(line);
               }
            }

            if (dynamicEvent.shift) {
               for (TypedDataComponent<?> type : stack.getPrototype()) {
                  ResourceLocation id = components.getKey(type.type());
                  if (id != null && stack.getComponentsPatch().get(type.type()) == null) {
                     MutableComponent line = Component.empty();
                     line.append(TextIcons.icon(Component.literal("P.")));
                     line.append(Component.literal(ID.reduce(id)).kjs$gray());
                     line.append(Component.literal("="));
                     List errors0 = appendComponentValue(ops, line, type.type(), type.value());
                     if (!errors0.isEmpty()) {
                        lines.add(Component.literal(ID.reduce(id) + " errored, see log").kjs$darkRed());
                        errors.add("Failed to encode value of " + id + ": " + type.value());
                        errors.addAll(errors0);
                     }

                     lines.add(line);
                  }
               }
            }

            if (!errors.isEmpty() && !lastComponentError.equals(errors)) {
               lastComponentError = errors;
               errors.forEach(ConsoleJS.CLIENT::error);
            }
         } else if (advanced && (properties.showTagNames || properties.showFuelValue) && dynamicEvent.shift) {
            if (properties.showFuelValue) {
               int fuel = stack.getBurnTime(null);
               if (fuel > 0) {
                  MutableComponent line = Component.empty();
                  line.append(TextIcons.icon(Component.literal("R.")));
                  line.append(Component.literal("Fuel: ").kjs$gold());
                  String s = String.valueOf(fuel / 20.0F);
                  line.append(Component.literal(fuel + " t / " + (s.endsWith(".0") ? s.substring(0, s.length() - 2) : s) + " s").kjs$yellow());
                  lines.add(line);
               }
            }

            if (properties.showTagNames) {
               LinkedHashMap<ResourceLocation, TagInstance> tempTagNames = new LinkedHashMap<>();
               TagInstance.Type.ITEM.append(tempTagNames, stack.getItem().builtInRegistryHolder().tags());
               if (stack.getItem() instanceof BlockItem item) {
                  TagInstance.Type.BLOCK.append(tempTagNames, item.getBlock().builtInRegistryHolder().tags());
               }

               if (stack.getItem() instanceof BucketItem bucket) {
                  Fluid fluid = bucket.content;
                  if (fluid != Fluids.EMPTY) {
                     TagInstance.Type.FLUID.append(tempTagNames, fluid.builtInRegistryHolder().tags());
                  }
               }

               if (stack.getItem() instanceof SpawnEggItem item) {
                  EntityType<?> entityType = item.getType(stack);
                  if (entityType != null) {
                     TagInstance.Type.ENTITY.append(tempTagNames, entityType.builtInRegistryHolder().tags());
                  }
               }

               if (!tempTagNames.isEmpty()) {
                  tempTagNames.values().stream().sorted().map(TagInstance::toText).forEach(lines::add);
               }
            }
         }
      }
   }

   @SubscribeEvent
   public static void loggingIn(LoggingIn event) {
      ClientEvents.LOGGED_IN.post(ScriptType.CLIENT, new ClientPlayerKubeEvent(event.getPlayer()));
   }

   @SubscribeEvent
   public static void loggingOut(LoggingOut event) {
      ClientEvents.LOGGED_OUT.post(ScriptType.CLIENT, new ClientPlayerKubeEvent(event.getPlayer()));
   }

   @SubscribeEvent
   public static void hudPostDraw(Post event) {
      Minecraft mc = Minecraft.getInstance();
      HighlightRenderer.INSTANCE.hudPostDraw(mc, event.getGuiGraphics(), event.getPartialTick().getGameTimeDeltaPartialTick(false));
   }

   @SubscribeEvent
   public static void screenPostDraw(net.neoforged.neoforge.client.event.ScreenEvent.Render.Post event) {
      Minecraft mc = Minecraft.getInstance();
      if (event.getScreen() instanceof AbstractContainerScreen<?> screen) {
         HighlightRenderer.INSTANCE.screen(mc, event.getGuiGraphics(), screen, event.getMouseX(), event.getMouseY(), event.getPartialTick());
      }
   }

   @SubscribeEvent
   public static void clientTick(Pre event) {
      Minecraft mc = Minecraft.getInstance();
      HighlightRenderer.INSTANCE.tickPre(mc);
      KubeJSKeybinds.triggerKeyEvents(mc);
   }

   @SubscribeEvent
   public static void worldRender(RenderLevelStageEvent event) {
      Minecraft mc = Minecraft.getInstance();
      if (event.getStage() == Stage.AFTER_SKY) {
         HighlightRenderer.INSTANCE.clearBuffers(mc);
         mc.getMainRenderTarget().bindWrite(true);
      } else if (event.getStage() == Stage.AFTER_ENTITIES) {
         HighlightRenderer.INSTANCE.renderAfterEntities(mc, event);
      } else if (event.getStage() == Stage.AFTER_LEVEL) {
         HighlightRenderer.INSTANCE.renderAfterLevel(mc, event);
      }
   }

   @Nullable
   public static Screen setScreen(Screen screen) {
      if (screen instanceof TitleScreen && !ConsoleJS.STARTUP.errors.isEmpty() && CommonProperties.get().startupErrorGUI) {
         return new KubeJSErrorScreen(screen, ConsoleJS.STARTUP, false);
      } else {
         return screen instanceof TitleScreen && !ConsoleJS.CLIENT.errors.isEmpty() && CommonProperties.get().startupErrorGUI
            ? new KubeJSErrorScreen(screen, ConsoleJS.CLIENT, false)
            : screen;
      }
   }

   @SubscribeEvent
   public static void guiPostInit(net.neoforged.neoforge.client.event.ScreenEvent.Init.Post event) {
      Screen screen = event.getScreen();
      if (ClientProperties.get().disableRecipeBook && screen instanceof RecipeUpdateListener) {
         Iterator<? extends GuiEventListener> iterator = screen.children().iterator();

         while (iterator.hasNext()) {
            GuiEventListener listener = iterator.next();
            if (listener instanceof ImageButton button && button.sprites.enabled().equals(KubeJSClient.RECIPE_BUTTON_TEXTURE)) {
               screen.renderables.remove(listener);
               screen.narratables.remove(listener);
               iterator.remove();
               return;
            }
         }
      }
   }

   @SubscribeEvent(priority = EventPriority.LOW)
   public static void openScreenEvent(Opening event) {
      Screen s = setScreen(event.getScreen());
      if (s != null && event.getScreen() != s) {
         event.setNewScreen(s);
      }
   }

   @SubscribeEvent
   public static void tagsUpdated(TagsUpdatedEvent event) {
      if (event.getUpdateCause() == UpdateCause.CLIENT_PACKET_RECEIVED
         && Minecraft.getInstance().screen instanceof KubeJSErrorScreen screen
         && screen.scriptType == ScriptType.SERVER) {
         Minecraft.getInstance().kjs$runCommand("kubejs errors server");
      }
   }
}
