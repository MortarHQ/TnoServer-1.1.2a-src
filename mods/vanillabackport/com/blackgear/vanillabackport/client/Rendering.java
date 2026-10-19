package com.blackgear.vanillabackport.client;

import com.blackgear.platform.client.GameRendering.BlockColorEvent;
import com.blackgear.platform.client.GameRendering.BlockRendererEvent;
import com.blackgear.platform.client.GameRendering.EntityRendererEvent;
import com.blackgear.platform.client.GameRendering.ItemColorEvent;
import com.blackgear.platform.client.GameRendering.ModelLayerEvent;
import com.blackgear.platform.client.GameRendering.ParticleFactoryEvent;
import com.blackgear.platform.client.GameRendering.SpecialModelEvent;
import com.blackgear.platform.client.v2.render.DynamicItemRenderer;
import com.blackgear.platform.client.v2.render.ItemRendererRegistry;
import com.blackgear.vanillabackport.client.api.color.LeafColors;
import com.blackgear.vanillabackport.client.level.entities.model.CreakingModel;
import com.blackgear.vanillabackport.client.level.entities.model.HappyGhastHarnessModel;
import com.blackgear.vanillabackport.client.level.entities.model.HappyGhastModel;
import com.blackgear.vanillabackport.client.level.entities.model.SulfurCubeModel;
import com.blackgear.vanillabackport.client.level.entities.model.chicken.ColdChickenModel;
import com.blackgear.vanillabackport.client.level.entities.model.cow.ColdCowModel;
import com.blackgear.vanillabackport.client.level.entities.model.cow.WarmCowModel;
import com.blackgear.vanillabackport.client.level.entities.model.pig.ColdPigModel;
import com.blackgear.vanillabackport.client.level.entities.renderer.CreakingRenderer;
import com.blackgear.vanillabackport.client.level.entities.renderer.HappyGhastRenderer;
import com.blackgear.vanillabackport.client.level.entities.renderer.PaleOakBoatRenderer;
import com.blackgear.vanillabackport.client.level.entities.renderer.SulfurCubeRenderer;
import com.blackgear.vanillabackport.client.level.item.BundleRenderer;
import com.blackgear.vanillabackport.client.level.item.SpawnEggRenderer;
import com.blackgear.vanillabackport.client.level.particles.FallingLeavesParticle;
import com.blackgear.vanillabackport.client.level.particles.FireflyParticle;
import com.blackgear.vanillabackport.client.level.particles.GeyserBaseParticle;
import com.blackgear.vanillabackport.client.level.particles.GeyserEruptionParticle;
import com.blackgear.vanillabackport.client.level.particles.GeyserPlumeParticle;
import com.blackgear.vanillabackport.client.level.particles.NoxiousGasCloudParticle;
import com.blackgear.vanillabackport.client.level.particles.NoxiousGasParticle;
import com.blackgear.vanillabackport.client.level.particles.SulfurBubbleParticle;
import com.blackgear.vanillabackport.client.level.particles.SulfurCubeParticle;
import com.blackgear.vanillabackport.client.level.particles.TrailParticle;
import com.blackgear.vanillabackport.client.registries.ModModelLayers;
import com.blackgear.vanillabackport.client.registries.ModParticles;
import com.blackgear.vanillabackport.common.registries.ModBlocks;
import com.blackgear.vanillabackport.common.registries.ModEntities;
import com.blackgear.vanillabackport.common.registries.ModEntityTypes;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class Rendering {
   public static void particleFactories(ParticleFactoryEvent event) {
      event.register(ModParticles.PALE_OAK_LEAVES, FallingLeavesParticle.PaleOakProvider::new);
      event.register(ModParticles.TRAIL, TrailParticle.Provider::new);
      event.register(ModParticles.FIREFLY, FireflyParticle.Provider::new);
      event.register(ModParticles.TINTED_LEAVES, FallingLeavesParticle.TintedLeavesProvider::new);
      event.register(ModParticles.TINTED_NEEDLES, FallingLeavesParticle.TintedLeavesProvider::new);
      event.register(ModParticles.SULFUR_BUBBLES, SulfurBubbleParticle.Provider::new);
      event.register(ModParticles.NOXIOUS_GAS, NoxiousGasParticle.Provider::new);
      event.register(ModParticles.NOXIOUS_GAS_CLOUD, new NoxiousGasCloudParticle.Provider());
      event.register(ModParticles.SULFUR_CUBE_GOO, SulfurCubeParticle.Provider::new);
      event.register(ModParticles.GEYSER, GeyserEruptionParticle.Provider::new);
      event.register(ModParticles.GEYSER_BASE, GeyserBaseParticle.Provider::new);
      event.register(ModParticles.GEYSER_POOF, GeyserBaseParticle.Provider::new);
      event.register(ModParticles.GEYSER_PLUME, GeyserPlumeParticle.Provider::new);
   }

   public static void modelLayers(ModelLayerEvent event) {
      event.register(ModModelLayers.CREAKING, CreakingModel::createBodyLayer);
      event.register(ModModelLayers.PALE_OAK_BOAT, BoatModel::createBodyModel);
      event.register(ModModelLayers.PALE_OAK_CHEST_BOAT, ChestBoatModel::createBodyModel);
      event.register(ModModelLayers.HAPPY_GHAST, () -> HappyGhastModel.createBodyLayer(CubeDeformation.NONE));
      event.register(ModModelLayers.HAPPY_GHAST_HARNESS, HappyGhastHarnessModel::createHarnessLayer);
      event.register(ModModelLayers.HAPPY_GHAST_ROPES, () -> HappyGhastModel.createBodyLayer(new CubeDeformation(0.2F)));
      event.register(ModModelLayers.COLD_PIG, ColdPigModel::createBodyLayer);
      event.register(ModModelLayers.COLD_CHICKEN, ColdChickenModel::createBodyLayer);
      event.register(ModModelLayers.COLD_COW, ColdCowModel::createBodyLayer);
      event.register(ModModelLayers.WARM_COW, WarmCowModel::createBodyLayer);
      event.register(ModModelLayers.SULFUR_CUBE, SulfurCubeModel::createOuterBodyLayer);
      event.register(ModModelLayers.SULFUR_CUBE_INNER, SulfurCubeModel::createInnerBodyLayer);
   }

   public static void specialModels(SpecialModelEvent event) {
      BundleRenderer.BUNDLES.forEach(item -> ((ItemRendererRegistry)ItemRendererRegistry.INSTANCE.get()).register(item, new BundleRenderer()));
      SpawnEggRenderer.SPAWN_EGGS.forEach(item -> ((DynamicItemRenderer)DynamicItemRenderer.INSTANCE.get()).register(item, new SpawnEggRenderer()));
   }

   public static void entityRendering(EntityRendererEvent event) {
      event.register(ModEntities.CREAKING.get(), CreakingRenderer::new);
      event.register(ModEntities.HAPPY_GHAST.get(), HappyGhastRenderer::new);
      event.register(ModEntities.PALE_OAK_BOAT.get(), context -> new PaleOakBoatRenderer(context, false));
      event.register(ModEntities.PALE_OAK_CHEST_BOAT.get(), context -> new PaleOakBoatRenderer(context, true));
      event.register(ModEntityTypes.SULFUR_CUBE, SulfurCubeRenderer::new);
   }

   public static void blockRendering(BlockRendererEvent event) {
      event.register(RenderType.cutoutMipped(), new Block[]{ModBlocks.PALE_OAK_LEAVES.get()});
      event.register(
         RenderType.cutout(),
         new Block[]{
            ModBlocks.PALE_MOSS_CARPET.get(),
            ModBlocks.PALE_HANGING_MOSS.get(),
            ModBlocks.OPEN_EYEBLOSSOM.get(),
            ModBlocks.CLOSED_EYEBLOSSOM.get(),
            ModBlocks.POTTED_OPEN_EYEBLOSSOM.get(),
            ModBlocks.POTTED_CLOSED_EYEBLOSSOM.get(),
            ModBlocks.PALE_OAK_SAPLING.get(),
            ModBlocks.POTTED_PALE_OAK_SAPLING.get(),
            ModBlocks.RESIN_CLUMP.get(),
            ModBlocks.BUSH.get(),
            ModBlocks.FIREFLY_BUSH.get(),
            ModBlocks.WILDFLOWERS.get(),
            ModBlocks.LEAF_LITTER.get(),
            ModBlocks.CACTUS_FLOWER.get(),
            ModBlocks.SHORT_DRY_GRASS.get(),
            ModBlocks.TALL_DRY_GRASS.get(),
            ModBlocks.PALE_OAK_DOOR.get(),
            ModBlocks.PALE_OAK_TRAPDOOR.get(),
            ModBlocks.SULFUR_SPIKE.get()
         }
      );
   }

   public static void blockColors(BlockColorEvent event) {
      event.register(
         (state, level, pos, tint) -> level != null && pos != null ? LeafColors.getAverageDryFoliageColor(pos) : -10732494,
         new Block[]{ModBlocks.LEAF_LITTER.get()}
      );
      event.register(
         (state, level, pos, tint) -> level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor(),
         new Block[]{ModBlocks.BUSH.get()}
      );
      event.register((state, level, pos, tint) -> {
         if (tint == 0) {
            return -1;
         } else {
            return level != null && pos != null ? BiomeColors.getAverageGrassColor(level, pos) : GrassColor.getDefaultColor();
         }
      }, new Block[]{ModBlocks.WILDFLOWERS.get()});
   }

   public static void itemColors(ItemColorEvent event) {
      event.register(event::getColor, new ItemLike[]{(ItemLike)ModBlocks.BUSH.get()});
   }
}
