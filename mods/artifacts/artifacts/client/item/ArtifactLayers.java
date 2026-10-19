package artifacts.client.item;

import artifacts.Artifacts;
import artifacts.client.item.model.ArmsModel;
import artifacts.client.item.model.BeltModel;
import artifacts.client.item.model.HeadModel;
import artifacts.client.item.model.LegsModel;
import artifacts.client.item.model.NecklaceModel;
import artifacts.client.item.model.ScarfModel;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;

public class ArtifactLayers {
   public static final ModelLayerLocation DRINKING_HAT = createLayerLocation("drinking_hat");
   public static final ModelLayerLocation SNORKEL = createLayerLocation("snorkel");
   public static final ModelLayerLocation NIGHT_VISION_GOGGLES = createLayerLocation("night_vision_goggles");
   public static final ModelLayerLocation SUPERSTITIOUS_HAT = createLayerLocation("superstitious_hat");
   public static final ModelLayerLocation BRIMMED_HAT = createLayerLocation("brimmed_hat");
   public static final ModelLayerLocation COWBOY_HAT = createLayerLocation("cowboy_hat");
   public static final ModelLayerLocation ANGLERS_HAT = createLayerLocation("anglers_hat");
   public static final ModelLayerLocation SCARF = createLayerLocation("scarf");
   public static final ModelLayerLocation CROSS_NECKLACE = createLayerLocation("cross_necklace");
   public static final ModelLayerLocation PANIC_NECKLACE = createLayerLocation("panic_necklace");
   public static final ModelLayerLocation PENDANT = createLayerLocation("pendant");
   public static final ModelLayerLocation CHARM_OF_SINKING = createLayerLocation("charm_of_sinking");
   public static final ModelLayerLocation CHARM_OF_SHRINKING = createLayerLocation("charm_of_shrinking");
   public static final ModelLayerLocation CLOUD_IN_A_BOTTLE = createLayerLocation("cloud_in_a_bottle");
   public static final ModelLayerLocation OBSIDIAN_SKULL = createLayerLocation("obsidian_skull");
   public static final ModelLayerLocation ANTIDOTE_VESSEL = createLayerLocation("antidote_vessel");
   public static final ModelLayerLocation UNIVERSAL_ATTRACTOR = createLayerLocation("universal_attractor");
   public static final ModelLayerLocation CRYSTAL_HEART = createLayerLocation("crystal_heart");
   public static final ModelLayerLocation HELIUM_FLAMINGO = createLayerLocation("helium_flamingo");
   public static final ModelLayerLocation CHORUS_TOTEM = createLayerLocation("chorus_totem");
   public static final ModelLayerLocation WARP_DRIVE = createLayerLocation("warp_drive");
   public static final ModelLayerLocation CLAWS_WIDE = createLayerLocation("claws_wide");
   public static final ModelLayerLocation CLAWS_SLIM = createLayerLocation("claws_slim");
   public static final ModelLayerLocation GLOVE_WIDE = createLayerLocation("glove_wide");
   public static final ModelLayerLocation GLOVE_SLIM = createLayerLocation("glove_slim");
   public static final ModelLayerLocation GOLDEN_HOOK_WIDE = createLayerLocation("golden_hook_wide");
   public static final ModelLayerLocation GOLDEN_HOOK_SLIM = createLayerLocation("golden_hook_slim");
   public static final ModelLayerLocation POCKET_PISTON_WIDE = createLayerLocation("pocket_piston_wide");
   public static final ModelLayerLocation POCKET_PISTON_SLIM = createLayerLocation("pocket_piston_slim");
   public static final ModelLayerLocation ONION_RING_WIDE = createLayerLocation("onion_ring_wide");
   public static final ModelLayerLocation ONION_RING_SLIM = createLayerLocation("onion_ring_slim");
   public static final ModelLayerLocation PICKAXE_HEATER_WIDE = createLayerLocation("pickaxe_heater_wide");
   public static final ModelLayerLocation PICKAXE_HEATER_SLIM = createLayerLocation("pickaxe_heater_slim");
   public static final ModelLayerLocation WITHERED_BRACELET_WIDE = createLayerLocation("withered_bracelet_wide");
   public static final ModelLayerLocation WITHERED_BRACELET_SLIM = createLayerLocation("withered_bracelet_slim");
   public static final ModelLayerLocation AQUA_DASHERS_SMALL = createLayerLocation("aqua_dashers_small");
   public static final ModelLayerLocation AQUA_DASHERS_LARGE = createLayerLocation("aqua_dashers_large");
   public static final ModelLayerLocation BUNNY_HOPPERS = createLayerLocation("bunny_hoppers");
   public static final ModelLayerLocation KITTY_SLIPPERS = createLayerLocation("kitty_slippers");
   public static final ModelLayerLocation BOOTS_SMALL = createLayerLocation("boots_small");
   public static final ModelLayerLocation BOOTS_LARGE = createLayerLocation("boots_large");
   public static final ModelLayerLocation SNOWSHOES = createLayerLocation("snowshoes");
   public static final ModelLayerLocation STEADFAST_SPIKES = createLayerLocation("steadfast_spikes");
   public static final ModelLayerLocation FLIPPERS = createLayerLocation("flippers");
   public static final ModelLayerLocation WHOOPEE_CUSHION = createLayerLocation("whoopee_cushion");

   public static ModelLayerLocation claws(boolean hasSlimArms) {
      return hasSlimArms ? CLAWS_SLIM : CLAWS_WIDE;
   }

   public static ModelLayerLocation glove(boolean hasSlimArms) {
      return hasSlimArms ? GLOVE_SLIM : GLOVE_WIDE;
   }

   public static ModelLayerLocation goldenHook(boolean hasSlimArms) {
      return hasSlimArms ? GOLDEN_HOOK_SLIM : GOLDEN_HOOK_WIDE;
   }

   public static ModelLayerLocation pocketPiston(boolean hasSlimArms) {
      return hasSlimArms ? POCKET_PISTON_SLIM : POCKET_PISTON_WIDE;
   }

   public static ModelLayerLocation onionRing(boolean hasSlimArms) {
      return hasSlimArms ? ONION_RING_SLIM : ONION_RING_WIDE;
   }

   public static ModelLayerLocation pickaxeHeater(boolean hasSlimArms) {
      return hasSlimArms ? PICKAXE_HEATER_SLIM : PICKAXE_HEATER_WIDE;
   }

   public static ModelLayerLocation witheredBracelet(boolean hasSlimArms) {
      return hasSlimArms ? WITHERED_BRACELET_SLIM : WITHERED_BRACELET_WIDE;
   }

   public static ModelLayerLocation createLayerLocation(String name) {
      return new ModelLayerLocation(Artifacts.id(name), name);
   }

   private static Supplier<LayerDefinition> layer(Supplier<MeshDefinition> mesh, int textureWidth, int textureHeight) {
      return () -> LayerDefinition.create(mesh.get(), textureWidth, textureHeight);
   }

   public static void register(BiConsumer<ModelLayerLocation, Supplier<LayerDefinition>> registration) {
      registration.accept(DRINKING_HAT, layer(HeadModel::createDrinkingHat, 64, 32));
      registration.accept(SNORKEL, layer(HeadModel::createSnorkel, 64, 32));
      registration.accept(NIGHT_VISION_GOGGLES, layer(HeadModel::createNightVisionGoggles, 32, 32));
      registration.accept(SUPERSTITIOUS_HAT, layer(HeadModel::createSuperstitiousHat, 64, 32));
      registration.accept(BRIMMED_HAT, layer(() -> HeadModel.createBrimmedHat(CubeListBuilder.create()), 32, 32));
      registration.accept(COWBOY_HAT, layer(HeadModel::createCowboyHat, 32, 32));
      registration.accept(ANGLERS_HAT, layer(HeadModel::createAnglersHat, 32, 32));
      registration.accept(SCARF, layer(ScarfModel::createScarf, 64, 32));
      registration.accept(CROSS_NECKLACE, layer(NecklaceModel::createCrossNecklace, 64, 48));
      registration.accept(PANIC_NECKLACE, layer(NecklaceModel::createPanicNecklace, 64, 48));
      registration.accept(PENDANT, layer(NecklaceModel::createPendant, 64, 48));
      registration.accept(CHARM_OF_SINKING, layer(NecklaceModel::createCharmOfSinking, 64, 48));
      registration.accept(CHARM_OF_SHRINKING, layer(NecklaceModel::createCharmOfShrinking, 64, 48));
      registration.accept(CLOUD_IN_A_BOTTLE, layer(BeltModel::createCloudInABottle, 32, 32));
      registration.accept(OBSIDIAN_SKULL, layer(BeltModel::createObsidianSkull, 32, 32));
      registration.accept(ANTIDOTE_VESSEL, layer(BeltModel::createAntidoteVessel, 32, 32));
      registration.accept(UNIVERSAL_ATTRACTOR, layer(BeltModel::createUniversalAttractor, 32, 32));
      registration.accept(CRYSTAL_HEART, layer(BeltModel::createCrystalHeart, 32, 32));
      registration.accept(HELIUM_FLAMINGO, layer(BeltModel::createHeliumFlamingo, 64, 64));
      registration.accept(CHORUS_TOTEM, layer(BeltModel::createChorusTotem, 32, 32));
      registration.accept(WARP_DRIVE, layer(BeltModel::createWarpDrive, 32, 32));
      registration.accept(CLAWS_WIDE, layer(() -> ArmsModel.createClaws(false), 32, 16));
      registration.accept(CLAWS_SLIM, layer(() -> ArmsModel.createClaws(true), 32, 16));
      registration.accept(GLOVE_WIDE, layer(() -> ArmsModel.createSleevedArms(false), 32, 32));
      registration.accept(GLOVE_SLIM, layer(() -> ArmsModel.createSleevedArms(true), 32, 32));
      registration.accept(GOLDEN_HOOK_WIDE, layer(() -> ArmsModel.createGoldenHook(false), 64, 32));
      registration.accept(GOLDEN_HOOK_SLIM, layer(() -> ArmsModel.createGoldenHook(true), 64, 32));
      registration.accept(POCKET_PISTON_WIDE, layer(() -> ArmsModel.createPocketPiston(false), 32, 16));
      registration.accept(POCKET_PISTON_SLIM, layer(() -> ArmsModel.createPocketPiston(true), 32, 16));
      registration.accept(ONION_RING_WIDE, layer(() -> ArmsModel.createBracelet(false, 4, -4), 32, 32));
      registration.accept(ONION_RING_SLIM, layer(() -> ArmsModel.createBracelet(true, 4, -4), 32, 32));
      registration.accept(PICKAXE_HEATER_WIDE, layer(() -> ArmsModel.createPickaxeHeater(false), 64, 32));
      registration.accept(PICKAXE_HEATER_SLIM, layer(() -> ArmsModel.createPickaxeHeater(true), 64, 32));
      registration.accept(WITHERED_BRACELET_WIDE, layer(() -> ArmsModel.createBracelet(false, 3, -5), 32, 32));
      registration.accept(WITHERED_BRACELET_SLIM, layer(() -> ArmsModel.createBracelet(true, 3, -5), 32, 32));
      registration.accept(AQUA_DASHERS_SMALL, layer(() -> LegsModel.createAquaDashers(0.5F), 32, 32));
      registration.accept(AQUA_DASHERS_LARGE, layer(() -> LegsModel.createAquaDashers(1.25F), 32, 32));
      registration.accept(BUNNY_HOPPERS, layer(LegsModel::createBunnyHoppers, 64, 32));
      registration.accept(KITTY_SLIPPERS, layer(LegsModel::createKittySlippers, 64, 32));
      registration.accept(BOOTS_SMALL, layer(() -> LegsModel.createBoots(0.5F), 32, 32));
      registration.accept(BOOTS_LARGE, layer(() -> LegsModel.createBoots(1.25F), 32, 32));
      registration.accept(SNOWSHOES, layer(LegsModel::createSnowshoes, 64, 64));
      registration.accept(STEADFAST_SPIKES, layer(LegsModel::createSteadfastSpikes, 64, 32));
      registration.accept(FLIPPERS, layer(LegsModel::createFlippers, 64, 64));
      registration.accept(WHOOPEE_CUSHION, layer(HeadModel::createWhoopeeCushion, 32, 16));
   }
}
