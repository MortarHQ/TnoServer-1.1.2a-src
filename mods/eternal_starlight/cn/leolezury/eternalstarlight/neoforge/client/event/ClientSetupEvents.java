package cn.leolezury.eternalstarlight.neoforge.client.event;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.client.gui.tooltip.ClientGalacticQuiverTooltip;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientHandler;
import cn.leolezury.eternalstarlight.common.client.handler.ESClientSetupHandler;
import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.StarlitDiamondArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.UnrealiumArmorModel;
import cn.leolezury.eternalstarlight.common.item.tooltip.GalacticQuiverTooltipComponent;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.neoforge.client.renderer.ESNeoItemStackRenderer;
import cn.leolezury.eternalstarlight.neoforge.registry.ESFluidTypes;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.FogRenderer.FogMode;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.resources.PlayerSkin.Model;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.FluidState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterClientReloadListenersEvent;
import net.neoforged.neoforge.client.event.RegisterClientTooltipComponentFactoriesEvent;
import net.neoforged.neoforge.client.event.RegisterDimensionSpecialEffectsEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.RegisterShadersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.AddLayers;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.CreateSkullModels;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterLayerDefinitions;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.event.ModelEvent.ModifyBakingResult;
import net.neoforged.neoforge.client.event.ModelEvent.RegisterAdditional;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Block;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent.Item;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.NotNull;
import org.joml.Vector3f;

@OnlyIn(Dist.CLIENT)
@EventBusSubscriber(modid = "eternal_starlight", value = Dist.CLIENT)
public class ClientSetupEvents {
   @SubscribeEvent
   private static void clientSetup(FMLClientSetupEvent event) {
      event.enqueueWork(ESClientSetupHandler::clientSetup);
   }

   @SubscribeEvent
   private static void onRegisterDimEffects(RegisterDimensionSpecialEffectsEvent event) {
      event.register(EternalStarlight.id("special_effect"), ESClientPlatform.INSTANCE.getDimEffect());
   }

   @SubscribeEvent
   private static void onRegisterBlockColor(Block event) {
      ESClientSetupHandler.registerBlockColors(event::register);
   }

   @SubscribeEvent
   private static void onRegisterItemColor(Item event) {
      ESClientSetupHandler.registerItemColors(event::register);
   }

   @SubscribeEvent
   private static void onRegisterClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.GLACITE_SHIELD.get()});
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.FLOWGLAZE_SHIELD.get()});
      IClientItemExtensions alchemistArmor = new IClientItemExtensions() {
         private AlchemistArmorModel<LivingEntity> model;

         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            if (this.model == null) {
               this.model = new AlchemistArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(AlchemistArmorModel.LAYER_LOCATION));
            }

            return this.model;
         }
      };
      event.registerItem(alchemistArmor, new net.minecraft.world.item.Item[]{ESItems.ALCHEMIST_MASK.get()});
      event.registerItem(alchemistArmor, new net.minecraft.world.item.Item[]{ESItems.ALCHEMIST_ROBE.get()});
      IClientItemExtensions thermalSpringstoneArmor = new IClientItemExtensions() {
         private ThermalSpringStoneArmorModel<LivingEntity> innerModel;
         private ThermalSpringStoneArmorModel<LivingEntity> outerModel;

         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            if (this.innerModel == null || this.outerModel == null) {
               this.innerModel = new ThermalSpringStoneArmorModel<>(
                  Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.INNER_LOCATION)
               );
               this.outerModel = new ThermalSpringStoneArmorModel<>(
                  Minecraft.getInstance().getEntityModels().bakeLayer(ThermalSpringStoneArmorModel.OUTER_LOCATION)
               );
            }

            if (itemStack.is(ESItems.THERMAL_SPRINGSTONE_HELMET.get())
               || itemStack.is(ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get())
               || itemStack.is(ESItems.THERMAL_SPRINGSTONE_BOOTS.get())) {
               return this.outerModel;
            } else {
               return (HumanoidModel<?>)(itemStack.is(ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get())
                  ? this.innerModel
                  : super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original));
            }
         }
      };
      event.registerItem(thermalSpringstoneArmor, new net.minecraft.world.item.Item[]{ESItems.THERMAL_SPRINGSTONE_HELMET.get()});
      event.registerItem(thermalSpringstoneArmor, new net.minecraft.world.item.Item[]{ESItems.THERMAL_SPRINGSTONE_CHESTPLATE.get()});
      event.registerItem(thermalSpringstoneArmor, new net.minecraft.world.item.Item[]{ESItems.THERMAL_SPRINGSTONE_LEGGINGS.get()});
      event.registerItem(thermalSpringstoneArmor, new net.minecraft.world.item.Item[]{ESItems.THERMAL_SPRINGSTONE_BOOTS.get()});
      IClientItemExtensions starlitDiamondArmor = new IClientItemExtensions() {
         private StarlitDiamondArmorModel<LivingEntity> innerModel;
         private StarlitDiamondArmorModel<LivingEntity> outerModel;

         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            if (this.innerModel == null || this.outerModel == null) {
               this.innerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.INNER_LOCATION));
               this.outerModel = new StarlitDiamondArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(StarlitDiamondArmorModel.OUTER_LOCATION));
            }

            if (itemStack.is(ESItems.STARLIT_DIAMOND_HELMET.get())
               || itemStack.is(ESItems.STARLIT_DIAMOND_CHESTPLATE.get())
               || itemStack.is(ESItems.STARLIT_DIAMOND_BOOTS.get())) {
               return this.outerModel;
            } else {
               return (HumanoidModel<?>)(itemStack.is(ESItems.STARLIT_DIAMOND_LEGGINGS.get())
                  ? this.innerModel
                  : super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original));
            }
         }
      };
      event.registerItem(starlitDiamondArmor, new net.minecraft.world.item.Item[]{ESItems.STARLIT_DIAMOND_HELMET.get()});
      event.registerItem(starlitDiamondArmor, new net.minecraft.world.item.Item[]{ESItems.STARLIT_DIAMOND_CHESTPLATE.get()});
      event.registerItem(starlitDiamondArmor, new net.minecraft.world.item.Item[]{ESItems.STARLIT_DIAMOND_LEGGINGS.get()});
      event.registerItem(starlitDiamondArmor, new net.minecraft.world.item.Item[]{ESItems.STARLIT_DIAMOND_BOOTS.get()});
      IClientItemExtensions unrealiumArmor = new IClientItemExtensions() {
         private UnrealiumArmorModel<LivingEntity> innerModel;
         private UnrealiumArmorModel<LivingEntity> outerModel;

         @NotNull
         public HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
            if (this.innerModel == null || this.outerModel == null) {
               this.innerModel = new UnrealiumArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(UnrealiumArmorModel.INNER_LOCATION));
               this.outerModel = new UnrealiumArmorModel<>(Minecraft.getInstance().getEntityModels().bakeLayer(UnrealiumArmorModel.OUTER_LOCATION));
            }

            if (itemStack.is(ESItems.UNREALIUM_HELMET.get()) || itemStack.is(ESItems.UNREALIUM_CHESTPLATE.get()) || itemStack.is(ESItems.UNREALIUM_BOOTS.get())
               )
             {
               return this.outerModel;
            } else {
               return (HumanoidModel<?>)(itemStack.is(ESItems.UNREALIUM_LEGGINGS.get())
                  ? this.innerModel
                  : super.getHumanoidArmorModel(livingEntity, itemStack, equipmentSlot, original));
            }
         }
      };
      event.registerItem(unrealiumArmor, new net.minecraft.world.item.Item[]{ESItems.UNREALIUM_HELMET.get()});
      event.registerItem(unrealiumArmor, new net.minecraft.world.item.Item[]{ESItems.UNREALIUM_CHESTPLATE.get()});
      event.registerItem(unrealiumArmor, new net.minecraft.world.item.Item[]{ESItems.UNREALIUM_LEGGINGS.get()});
      event.registerItem(unrealiumArmor, new net.minecraft.world.item.Item[]{ESItems.UNREALIUM_BOOTS.get()});
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.MALARITE_SPEAR.get()});
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.PUNGENCY_FRUIT_SPEAR.get()});
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.CRESCENT_SPEAR.get()});
      event.registerItem(ESNeoItemStackRenderer.CLIENT_ITEM_EXTENSION, new net.minecraft.world.item.Item[]{ESItems.LOOT_CHEST.get()});
      event.registerFluidType(
         new IClientFluidTypeExtensions() {
            public ResourceLocation getStillTexture() {
               return EternalStarlight.id("block/ether");
            }

            public ResourceLocation getFlowingTexture() {
               return EternalStarlight.id("block/ether_flow");
            }

            public int getTintColor(FluidState state, BlockAndTintGetter getter, BlockPos pos) {
               return ESClientHandler.getEtherTint(getter, pos);
            }

            @NotNull
            public Vector3f modifyFogColor(
               Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor
            ) {
               return new Vector3f(0.9098039F, 1.0F, 0.87058824F);
            }

            public void modifyFogRender(
               Camera camera, FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape
            ) {
               RenderSystem.setShaderFogStart(0.0F);
               RenderSystem.setShaderFogEnd(3.0F);
            }
         },
         new FluidType[]{ESFluidTypes.ETHER.get()}
      );
   }

   @SubscribeEvent
   private static void onBakingCompleted(ModifyBakingResult event) {
      Map<ModelResourceLocation, BakedModel> models = event.getModels();
      ESClientSetupHandler.modifiedBakedModels = false;
      ESClientSetupHandler.modifyBakingResult(models);
   }

   @SubscribeEvent
   private static void onRegisterExtraModels(RegisterAdditional event) {
      ESClientSetupHandler.registerExtraBakedModels(l -> {
         ModelResourceLocation forged = ModelResourceLocation.standalone(l.id().withPrefix("item/"));
         event.register(forged);
      });
   }

   @SubscribeEvent
   private static void onRegisterParticleProviders(RegisterParticleProvidersEvent event) {
      ESClientSetupHandler.registerParticleProviders(event::registerSpriteSet);
   }

   @SubscribeEvent
   private static void onRegisterEntityRenderers(RegisterRenderers event) {
      ESClientSetupHandler.registerEntityRenderers(event::registerEntityRenderer);
      ESClientSetupHandler.registerBlockEntityRenderers(event::registerBlockEntityRenderer);
   }

   @SubscribeEvent
   private static void onRegisterSkullModels(CreateSkullModels event) {
      ESClientSetupHandler.registerSkullModels(event::registerSkullModel, event.getEntityModelSet());
   }

   @SubscribeEvent
   private static void onRegisterLayers(RegisterLayerDefinitions event) {
      ESClientSetupHandler.registerLayers(event::registerLayerDefinition);
   }

   @SubscribeEvent
   private static void onRegisterMenuScreens(RegisterMenuScreensEvent event) {
      ESClientSetupHandler.registerMenuScreens(event::register);
   }

   @SubscribeEvent
   private static void onRegisterMenuScreens(AddLayers event) {
      for (EntityType<? extends Entity> entityType : event.getEntityTypes()) {
         if (event.getRenderer(entityType) instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
            ESClientSetupHandler.onRenderLayerAttachment(entityType, livingEntityRenderer, event.getContext());
         }
      }

      for (Model model : event.getSkins()) {
         if (event.getSkin(model) instanceof LivingEntityRenderer<?, ?> livingEntityRenderer) {
            ESClientSetupHandler.onRenderLayerAttachment(EntityType.PLAYER, livingEntityRenderer, event.getContext());
         }
      }
   }

   @SubscribeEvent
   private static void onRegisterShader(RegisterShadersEvent event) {
      ESClientSetupHandler.registerShaders((location, format, loaded) -> {
         try {
            event.registerShader(new ShaderInstance(event.getResourceProvider(), location, format), loaded);
         } catch (IOException e) {
            EternalStarlight.LOGGER.error("Cannot register shader: {}", location);
         }
      });
   }

   @SubscribeEvent
   private static void onRegisterGuiLayers(RegisterGuiLayersEvent event) {
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS,
         EternalStarlight.id("offhand_attack_indicator"),
         (graphics, partialTicks) -> ESClientHandler.renderOffhandAttackIndicator(graphics)
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS,
         EternalStarlight.id("spell_crosshair"),
         (graphics, partialTicks) -> ESClientHandler.renderSpellCrosshair(graphics, graphics.guiWidth(), graphics.guiHeight())
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("ether_erosion"), (graphics, partialTicks) -> ESClientHandler.renderEtherErosion(graphics)
      );
      event.registerAbove(VanillaGuiLayers.ARMOR_LEVEL, EternalStarlight.id("ether_armor"), (graphics, partialTicks) -> {
         if (Minecraft.getInstance().gameMode != null && Minecraft.getInstance().gameMode.canHurtPlayer()) {
            ESClientHandler.renderEtherArmor(graphics, graphics.guiWidth(), graphics.guiHeight());
         }
      });
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS,
         EternalStarlight.id("orb_of_prophecy_use"),
         (graphics, partialTicks) -> ESClientHandler.renderOrbOfProphecyUse(graphics)
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("dream_catcher"), (graphics, partialTicks) -> ESClientHandler.renderDreamCatcher(graphics)
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("current_crest"), (graphics, partialTicks) -> ESClientHandler.renderCurrentCrest(graphics)
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS,
         EternalStarlight.id("carved_lunaris_cactus_fruit_blur"),
         (graphics, partialTicks) -> ESClientHandler.renderCarvedLunarisCactusFruitBlur(graphics)
      );
      event.registerAbove(
         VanillaGuiLayers.CAMERA_OVERLAYS, EternalStarlight.id("portal"), (graphics, partialTicks) -> ESClientHandler.renderPortalOverlay(graphics)
      );
   }

   @SubscribeEvent
   private static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
      for (Entry<ResourceLocation, KeyMapping> mapping : ESClientSetupHandler.KEY_MAPPINGS.entrySet()) {
         event.register(mapping.getValue());
      }
   }

   @SubscribeEvent
   private static void onAddReloadListener(RegisterClientReloadListenersEvent event) {
      ESClientSetupHandler.addClientReloadListeners(event::registerReloadListener);
   }

   @SubscribeEvent
   private static void onRegisterClientTooltipComponentFactories(RegisterClientTooltipComponentFactoriesEvent event) {
      event.register(GalacticQuiverTooltipComponent.class, component -> new ClientGalacticQuiverTooltip(component.contents()));
   }
}
