package fuzs.illagerinvasion.client;

import fuzs.illagerinvasion.IllagerInvasion;
import fuzs.illagerinvasion.client.gui.screens.inventory.ImbuingScreen;
import fuzs.illagerinvasion.client.handler.EnchantmentTooltipHandler;
import fuzs.illagerinvasion.client.init.ClientModRegistry;
import fuzs.illagerinvasion.client.model.ArmoredIllagerEntityModel;
import fuzs.illagerinvasion.client.model.BrimmedHatIllagerEntityModel;
import fuzs.illagerinvasion.client.model.HatIllagerEntityModel;
import fuzs.illagerinvasion.client.model.IllagerWithStaffEntityModel;
import fuzs.illagerinvasion.client.model.InvokerEntityModel;
import fuzs.illagerinvasion.client.model.InvokerFangsModel;
import fuzs.illagerinvasion.client.render.entity.AlchemistRender;
import fuzs.illagerinvasion.client.render.entity.ArchivistRender;
import fuzs.illagerinvasion.client.render.entity.BasherRender;
import fuzs.illagerinvasion.client.render.entity.FirecallerRender;
import fuzs.illagerinvasion.client.render.entity.HatchetRender;
import fuzs.illagerinvasion.client.render.entity.InquisitorRender;
import fuzs.illagerinvasion.client.render.entity.InvokerFangsRenderer;
import fuzs.illagerinvasion.client.render.entity.InvokerRender;
import fuzs.illagerinvasion.client.render.entity.MagmaEntityRender;
import fuzs.illagerinvasion.client.render.entity.MarauderRender;
import fuzs.illagerinvasion.client.render.entity.NecromancerRender;
import fuzs.illagerinvasion.client.render.entity.ProvokerRender;
import fuzs.illagerinvasion.client.render.entity.SkullBoltRender;
import fuzs.illagerinvasion.client.render.entity.SorcererRender;
import fuzs.illagerinvasion.client.render.entity.SurrenderedRender;
import fuzs.illagerinvasion.init.ModEntityTypes;
import fuzs.illagerinvasion.init.ModItems;
import fuzs.illagerinvasion.init.ModRegistry;
import fuzs.puzzleslib.api.client.core.v1.ClientModConstructor;
import fuzs.puzzleslib.api.client.core.v1.context.EntityRenderersContext;
import fuzs.puzzleslib.api.client.core.v1.context.ItemModelPropertiesContext;
import fuzs.puzzleslib.api.client.core.v1.context.LayerDefinitionsContext;
import fuzs.puzzleslib.api.client.core.v1.context.MenuScreensContext;
import fuzs.puzzleslib.api.client.core.v1.context.ParticleProvidersContext;
import fuzs.puzzleslib.api.client.core.v1.context.RenderTypesContext;
import fuzs.puzzleslib.api.client.event.v1.gui.ItemTooltipCallback;
import net.minecraft.client.model.geom.LayerDefinitions;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.particle.FlameParticle.Provider;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

public class IllagerInvasionClient implements ClientModConstructor {
   public void onConstructMod() {
      registerEventHandlers();
   }

   private static void registerEventHandlers() {
      ItemTooltipCallback.EVENT.register(EnchantmentTooltipHandler::onItemTooltip);
   }

   public void onRegisterMenuScreens(MenuScreensContext context) {
      context.registerMenuScreen((MenuType)ModRegistry.IMBUING_MENU_TYPE.value(), ImbuingScreen::new);
   }

   public void onRegisterEntityRenderers(EntityRenderersContext context) {
      context.registerEntityRenderer((EntityType)ModEntityTypes.PROVOKER_ENTITY_TYPE.value(), ProvokerRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.INVOKER_ENTITY_TYPE.value(), InvokerRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.SURRENDERED_ENTITY_TYPE.value(), SurrenderedRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.NECROMANCER_ENTITY_TYPE.value(), NecromancerRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.SKULL_BOLT_ENTITY_TYPE.value(), SkullBoltRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.BASHER_ENTITY_TYPE.value(), BasherRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.SORCERER_ENTITY_TYPE.value(), SorcererRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.ARCHIVIST_ENTITY_TYPE.value(), ArchivistRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.INQUISITOR_ENTITY_TYPE.value(), InquisitorRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.MARAUDER_ENTITY_TYPE.value(), MarauderRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.ALCHEMIST_ENTITY_TYPE.value(), AlchemistRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.FIRECALLER_ENTITY_TYPE.value(), FirecallerRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.INVOKER_FANGS_ENTITY_TYPE.value(), InvokerFangsRenderer::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.HATCHET_ENTITY_TYPE.value(), HatchetRender::new);
      context.registerEntityRenderer((EntityType)ModEntityTypes.FLYING_MAGMA_ENTITY_TYPE.value(), MagmaEntityRender::new);
   }

   public void onRegisterParticleProviders(ParticleProvidersContext context) {
      context.registerParticleProvider((ParticleType)ModRegistry.MAGIC_FLAME_PARTICLE_TYPE.value(), Provider::new);
      context.registerParticleProvider(
         (ParticleType)ModRegistry.NECROMANCER_BUFF_PARTICLE_TYPE.value(), net.minecraft.client.particle.HeartParticle.Provider::new
      );
   }

   public void onRegisterLayerDefinitions(LayerDefinitionsContext context) {
      context.registerLayerDefinition(ClientModRegistry.CAPED_ILLAGER, () -> InvokerEntityModel.getTexturedModelData(CubeDeformation.NONE));
      context.registerLayerDefinition(ClientModRegistry.INVOKER_SHIELD, () -> InvokerEntityModel.getTexturedModelData(LayerDefinitions.INNER_ARMOR_DEFORMATION));
      context.registerLayerDefinition(
         ClientModRegistry.NECROMANCER_SHIELD, () -> ClientModRegistry.createIllagerBodyLayer(LayerDefinitions.INNER_ARMOR_DEFORMATION)
      );
      context.registerLayerDefinition(ClientModRegistry.INVOKER_FANGS, InvokerFangsModel::getTexturedModelData);
      context.registerLayerDefinition(ClientModRegistry.ARMORED_ILLAGER, ArmoredIllagerEntityModel::getTexturedModelData);
      context.registerLayerDefinition(ClientModRegistry.HAT_ILLAGER, HatIllagerEntityModel::getTexturedModelData);
      context.registerLayerDefinition(ClientModRegistry.STAFF_ILLAGER, IllagerWithStaffEntityModel::getTexturedModelData);
      context.registerLayerDefinition(ClientModRegistry.BRIM_HAT_ILLAGER, BrimmedHatIllagerEntityModel::getTexturedModelData);
   }

   public void onRegisterBlockRenderTypes(RenderTypesContext<Block> context) {
      context.registerRenderType(RenderType.cutout(), new Block[]{(Block)ModRegistry.MAGIC_FIRE_BLOCK.value()});
   }

   public void onRegisterItemModelProperties(ItemModelPropertiesContext context) {
      context.registerItemProperty(
         IllagerInvasion.id("tooting"),
         (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F
            : 0.0F,
         new ItemLike[]{(ItemLike)ModItems.HORN_OF_SIGHT_ITEM.value()}
      );
   }
}
