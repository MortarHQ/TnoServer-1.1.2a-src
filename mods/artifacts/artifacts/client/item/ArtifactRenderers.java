package artifacts.client.item;

import artifacts.client.item.model.ArmsModel;
import artifacts.client.item.model.BeltModel;
import artifacts.client.item.model.HeadModel;
import artifacts.client.item.model.LegsModel;
import artifacts.client.item.model.NecklaceModel;
import artifacts.client.item.model.ScarfModel;
import artifacts.client.item.renderer.ArtifactRenderer;
import artifacts.client.item.renderer.BeltArtifactRenderer;
import artifacts.client.item.renderer.BootArtifactRenderer;
import artifacts.client.item.renderer.GenericArtifactRenderer;
import artifacts.client.item.renderer.GloveArtifactRenderer;
import artifacts.client.item.renderer.GlowingArtifactRenderer;
import artifacts.client.item.renderer.GlowingGloveArtifactRenderer;
import artifacts.client.item.renderer.WarpDriveRenderer;
import artifacts.equipment.client.EquipmentRenderingManager;
import artifacts.registry.ModItems;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.item.Item;

public class ArtifactRenderers {
   public static void register() {
      register(
         (Item)ModItems.PLASTIC_DRINKING_HAT.value(),
         () -> new GenericArtifactRenderer("plastic_drinking_hat", new HeadModel(bakeLayer(ArtifactLayers.DRINKING_HAT)))
      );
      register(
         (Item)ModItems.NOVELTY_DRINKING_HAT.value(),
         () -> new GenericArtifactRenderer("novelty_drinking_hat", new HeadModel(bakeLayer(ArtifactLayers.DRINKING_HAT)))
      );
      register(
         (Item)ModItems.SNORKEL.value(),
         () -> new GenericArtifactRenderer("snorkel", new HeadModel(bakeLayer(ArtifactLayers.SNORKEL), RenderType::entityTranslucent))
      );
      register(
         (Item)ModItems.NIGHT_VISION_GOGGLES.value(),
         () -> new GlowingArtifactRenderer("night_vision_goggles", new HeadModel(bakeLayer(ArtifactLayers.NIGHT_VISION_GOGGLES)))
      );
      register(
         (Item)ModItems.SUPERSTITIOUS_HAT.value(),
         () -> new GenericArtifactRenderer("superstitious_hat", new HeadModel(bakeLayer(ArtifactLayers.SUPERSTITIOUS_HAT), RenderType::entityCutoutNoCull))
      );
      register((Item)ModItems.VILLAGER_HAT.value(), () -> new GenericArtifactRenderer("villager_hat", new HeadModel(bakeLayer(ArtifactLayers.BRIMMED_HAT))));
      register((Item)ModItems.COWBOY_HAT.value(), () -> new GenericArtifactRenderer("cowboy_hat", new HeadModel(bakeLayer(ArtifactLayers.COWBOY_HAT))));
      register((Item)ModItems.ANGLERS_HAT.value(), () -> new GenericArtifactRenderer("anglers_hat", new HeadModel(bakeLayer(ArtifactLayers.ANGLERS_HAT))));
      register(
         (Item)ModItems.LUCKY_SCARF.value(),
         () -> new GenericArtifactRenderer("lucky_scarf", new ScarfModel(bakeLayer(ArtifactLayers.SCARF), RenderType::entityCutoutNoCull))
      );
      register(
         (Item)ModItems.SCARF_OF_INVISIBILITY.value(),
         () -> new GenericArtifactRenderer("scarf_of_invisibility", new ScarfModel(bakeLayer(ArtifactLayers.SCARF), RenderType::entityTranslucent))
      );
      register(
         (Item)ModItems.CROSS_NECKLACE.value(),
         () -> new GenericArtifactRenderer("cross_necklace", new NecklaceModel(bakeLayer(ArtifactLayers.CROSS_NECKLACE)))
      );
      register(
         (Item)ModItems.PANIC_NECKLACE.value(),
         () -> new GenericArtifactRenderer("panic_necklace", new NecklaceModel(bakeLayer(ArtifactLayers.PANIC_NECKLACE)))
      );
      register((Item)ModItems.SHOCK_PENDANT.value(), () -> new GenericArtifactRenderer("shock_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
      register((Item)ModItems.FLAME_PENDANT.value(), () -> new GenericArtifactRenderer("flame_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
      register((Item)ModItems.THORN_PENDANT.value(), () -> new GenericArtifactRenderer("thorn_pendant", new NecklaceModel(bakeLayer(ArtifactLayers.PENDANT))));
      register(
         (Item)ModItems.CHARM_OF_SINKING.value(),
         () -> new GenericArtifactRenderer("charm_of_sinking", new NecklaceModel(bakeLayer(ArtifactLayers.CHARM_OF_SINKING)))
      );
      register(
         (Item)ModItems.CHARM_OF_SHRINKING.value(),
         () -> new GenericArtifactRenderer("charm_of_shrinking", new NecklaceModel(bakeLayer(ArtifactLayers.CHARM_OF_SHRINKING)))
      );
      register((Item)ModItems.CLOUD_IN_A_BOTTLE.value(), () -> new BeltArtifactRenderer("cloud_in_a_bottle", BeltModel.createCloudInABottleModel()));
      register((Item)ModItems.OBSIDIAN_SKULL.value(), () -> new BeltArtifactRenderer("obsidian_skull", BeltModel.createObsidianSkullModel()));
      register((Item)ModItems.ANTIDOTE_VESSEL.value(), () -> new BeltArtifactRenderer("antidote_vessel", BeltModel.createAntidoteVesselModel()));
      register((Item)ModItems.UNIVERSAL_ATTRACTOR.value(), () -> new BeltArtifactRenderer("universal_attractor", BeltModel.createUniversalAttractorModel()));
      register((Item)ModItems.CRYSTAL_HEART.value(), () -> new BeltArtifactRenderer("crystal_heart", BeltModel.createCrystalHeartModel()));
      register((Item)ModItems.HELIUM_FLAMINGO.value(), () -> new GenericArtifactRenderer("helium_flamingo", BeltModel.createHeliumFlamingoModel()));
      register((Item)ModItems.CHORUS_TOTEM.value(), () -> new BeltArtifactRenderer("chorus_totem", BeltModel.createChorusTotemModel()));
      register((Item)ModItems.WARP_DRIVE.value(), () -> new WarpDriveRenderer("warp_drive", BeltModel.createWarpDriveModel()));
      register((Item)ModItems.DIGGING_CLAWS.value(), () -> new GloveArtifactRenderer("digging_claws", "digging_claws", ArmsModel::createClawsModel));
      register((Item)ModItems.FERAL_CLAWS.value(), () -> new GloveArtifactRenderer("feral_claws", "feral_claws", ArmsModel::createClawsModel));
      register((Item)ModItems.POWER_GLOVE.value(), () -> new GloveArtifactRenderer("power_glove", ArmsModel::createGloveModel));
      register((Item)ModItems.FIRE_GAUNTLET.value(), () -> new GlowingGloveArtifactRenderer("fire_gauntlet", ArmsModel::createGloveModel));
      register((Item)ModItems.POCKET_PISTON.value(), () -> new GloveArtifactRenderer("pocket_piston", ArmsModel::createPocketPistonModel));
      register((Item)ModItems.VAMPIRIC_GLOVE.value(), () -> new GloveArtifactRenderer("vampiric_glove", ArmsModel::createGloveModel));
      register((Item)ModItems.GOLDEN_HOOK.value(), () -> new GloveArtifactRenderer("golden_hook", ArmsModel::createGoldenHookModel));
      register((Item)ModItems.ONION_RING.value(), () -> new GloveArtifactRenderer("onion_ring", ArmsModel::createOnionRingModel));
      register((Item)ModItems.PICKAXE_HEATER.value(), () -> new GlowingGloveArtifactRenderer("pickaxe_heater", ArmsModel::createPickaxeHeaterModel));
      register((Item)ModItems.WITHERED_BRACELET.value(), () -> new GloveArtifactRenderer("withered_bracelet", ArmsModel::createWitheredBraceletModel));
      register(
         (Item)ModItems.AQUA_DASHERS.value(),
         () -> new BootArtifactRenderer(
            "aqua_dashers", hasArmor -> new LegsModel(bakeLayer(hasArmor ? ArtifactLayers.AQUA_DASHERS_LARGE : ArtifactLayers.AQUA_DASHERS_SMALL))
         )
      );
      register((Item)ModItems.BUNNY_HOPPERS.value(), () -> new GenericArtifactRenderer("bunny_hoppers", new LegsModel(bakeLayer(ArtifactLayers.BUNNY_HOPPERS))));
      register(
         (Item)ModItems.KITTY_SLIPPERS.value(), () -> new GenericArtifactRenderer("kitty_slippers", new LegsModel(bakeLayer(ArtifactLayers.KITTY_SLIPPERS)))
      );
      register(
         (Item)ModItems.RUNNING_SHOES.value(),
         () -> new BootArtifactRenderer(
            "running_shoes", hasArmor -> new LegsModel(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL))
         )
      );
      register((Item)ModItems.SNOWSHOES.value(), () -> new GenericArtifactRenderer("snowshoes", new LegsModel(bakeLayer(ArtifactLayers.SNOWSHOES))));
      register(
         (Item)ModItems.STEADFAST_SPIKES.value(),
         () -> new GenericArtifactRenderer("steadfast_spikes", new LegsModel(bakeLayer(ArtifactLayers.STEADFAST_SPIKES)))
      );
      register((Item)ModItems.FLIPPERS.value(), () -> new GenericArtifactRenderer("flippers", new LegsModel(bakeLayer(ArtifactLayers.FLIPPERS))));
      register(
         (Item)ModItems.ROOTED_BOOTS.value(),
         () -> new BootArtifactRenderer(
            "rooted_boots", hasArmor -> new LegsModel(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL))
         )
      );
      register(
         (Item)ModItems.STRIDER_SHOES.value(),
         () -> new BootArtifactRenderer(
            "strider_shoes", hasArmor -> new LegsModel(bakeLayer(hasArmor ? ArtifactLayers.BOOTS_LARGE : ArtifactLayers.BOOTS_SMALL))
         )
      );
      register(
         (Item)ModItems.WHOOPEE_CUSHION.value(), () -> new GenericArtifactRenderer("whoopee_cushion", new HeadModel(bakeLayer(ArtifactLayers.WHOOPEE_CUSHION)))
      );
   }

   public static ModelPart bakeLayer(ModelLayerLocation layerLocation) {
      return Minecraft.getInstance().getEntityModels().bakeLayer(layerLocation);
   }

   public static void register(Item item, Supplier<ArtifactRenderer> rendererFactory) {
      EquipmentRenderingManager.registerArtifactRenderer(item, rendererFactory);
   }
}
