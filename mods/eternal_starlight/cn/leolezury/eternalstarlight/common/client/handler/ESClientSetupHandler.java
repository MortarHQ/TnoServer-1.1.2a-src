package cn.leolezury.eternalstarlight.common.client.handler;

import cn.leolezury.eternalstarlight.common.EternalStarlight;
import cn.leolezury.eternalstarlight.common.block.AccumulatorBlock;
import cn.leolezury.eternalstarlight.common.block.ESSkullType;
import cn.leolezury.eternalstarlight.common.client.ESRenderType;
import cn.leolezury.eternalstarlight.common.client.gui.screen.AlloyFurnaceScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrateScreen;
import cn.leolezury.eternalstarlight.common.client.gui.screen.CrystalbornCatalystScreen;
import cn.leolezury.eternalstarlight.common.client.model.animation.PlayerAnimator;
import cn.leolezury.eternalstarlight.common.client.model.animation.definition.PlayerAnimation;
import cn.leolezury.eternalstarlight.common.client.model.armor.AlchemistArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.StarlitDiamondArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.ThermalSpringStoneArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.armor.UnrealiumArmorModel;
import cn.leolezury.eternalstarlight.common.client.model.block.TangledHeadModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentGolemModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.AethersentMeteorModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.AstralGolemModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.AuroraDeerModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.CandlashModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ChainOfSoulsModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ColdsnapModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.CreteorModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.CrystallizedMothModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.EntModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.FreezeModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.FrozenTubeModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.GleechModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.GrimstoneGolemModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LonestarSkeletonModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LuminarisModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LuminoFishModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarMonstrosityModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarSporeModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.LunarThornModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.NightfallSpiderModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.OrbModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.PermafrostSpitModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.RatlinModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.RookfishModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ScarletBoatModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ScarletChestBoatModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.SeekerModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ShadowSnailModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ShimmerLacewingModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.SolarCreeperModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarfireBirdModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.StarlightGolemModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.StranghoulModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TangledSkullModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TentacleSpikeModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TheGatekeeperModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.ThirstWalkerModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TinyCreteorModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.TwilightGazeModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.YetiModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.BoarwarfModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfBlacksmithModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfChefModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfDruidModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfDyerModel;
import cn.leolezury.eternalstarlight.common.client.model.entity.boarwarf.profession.BoarwarfSilversmithModel;
import cn.leolezury.eternalstarlight.common.client.model.item.CrescentSpearModel;
import cn.leolezury.eternalstarlight.common.client.model.item.FlowglazeShieldModel;
import cn.leolezury.eternalstarlight.common.client.model.item.GlaciteShieldModel;
import cn.leolezury.eternalstarlight.common.client.model.item.MalariteSpearModel;
import cn.leolezury.eternalstarlight.common.client.model.item.PungencyFruitSpearModel;
import cn.leolezury.eternalstarlight.common.client.particle.advanced.AdvancedParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.AethersentExplosionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.AethersentSmokeParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ESExplosionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ESGlowParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ESSmokeParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ElectricSparkParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.EnergyParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.ExplosionShockParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.GatheringTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.GeyserBaseParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.GeyserEruptionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.GeyserPlumeParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.LunarSlashParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.OrbitalAshenSnowParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.OrbitalTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.PungencyFruitSmokeParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.RingExplosionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.RingParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.SmokeTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.SonarParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.SoulTrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.StarfireExplosionParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.StarfireParticle;
import cn.leolezury.eternalstarlight.common.client.particle.effect.TrailParticle;
import cn.leolezury.eternalstarlight.common.client.particle.environment.AshenSnowParticle;
import cn.leolezury.eternalstarlight.common.client.particle.environment.FallingLeavesParticle;
import cn.leolezury.eternalstarlight.common.client.particle.environment.FireflyParticle;
import cn.leolezury.eternalstarlight.common.client.particle.environment.MeteorParticle;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.AlloyFurnaceRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.DryingRackRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.DuskLightRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.ESPortalRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.EclipseCoreRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.EnergyTransmitterRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.FlareSpawnerRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.LootChestRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.LunarVineRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.MechanicalSpawnerRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.SolarEggRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.StarfireBirdNestRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.blockentity.StellarRackRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AethersentArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AethersentGolemRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AethersentMeteorRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AetherstrikeRocketRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AirSacArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AmaramberArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AstralGolemRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.AuroraDeerRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.BallLightningRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.BoarwarfRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.CandlashRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ChainOfSoulsRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ColdsnapRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.CreteorRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.CrystalClusterRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.CrystallizedMothRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ESBoatRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ESFallingBlockRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.EmptyRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.EnergySparkRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.EntRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.FreezeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.FrozenTubeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.GatekeeperFireballRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.GlaciteArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.GleechRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.GrimstoneGolemRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LonestarSkeletonRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LuminarisRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LuminoFishRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LunarMonstrosityRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LunarSporeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.LunarThornRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.MalariteArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.NightfallSpiderRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.PermafrostRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.PermafrostSpitRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.RatlinRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.RookfishRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.SeekerRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ShadowSnailRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ShimmerLacewingRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.SolarCreeperRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StarfireBirdRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StarlightGolemBeamRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StarlightGolemRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.StranghoulRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TangledHuskRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TangledRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TangledSkullRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TearBombMinecartRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TearBombRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TentacleSpikeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TheGatekeeperRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThioquartzArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThioquartzShardRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThirstWalkerRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThrownBoomerangRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThrownMalariteSpearRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThrownPungencyFruitSpearRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.ThrownShatteredBladeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TinyCreteorRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.TwilightGazeRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.VoraciousArrowRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.WiltedPetalRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.entity.YetiRenderer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory.ArmorLikeAccessoryLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory.ButterflyWingsAmuletLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory.CrescentPendantLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory.FungusAmuletLayer;
import cn.leolezury.eternalstarlight.common.client.renderer.layer.accessory.PearlNecklaceLayer;
import cn.leolezury.eternalstarlight.common.client.shader.ESShaders;
import cn.leolezury.eternalstarlight.common.client.visual.TrailVisualEffect;
import cn.leolezury.eternalstarlight.common.client.visual.WorldVisualEffect;
import cn.leolezury.eternalstarlight.common.entity.attack.Candlash;
import cn.leolezury.eternalstarlight.common.entity.attack.Coldsnap;
import cn.leolezury.eternalstarlight.common.entity.attack.TentacleSpike;
import cn.leolezury.eternalstarlight.common.entity.misc.ESBoat;
import cn.leolezury.eternalstarlight.common.entity.projectile.ChainOfSouls;
import cn.leolezury.eternalstarlight.common.item.combat.ShatteredSwordItem;
import cn.leolezury.eternalstarlight.common.item.misc.GalacticQuiverItem;
import cn.leolezury.eternalstarlight.common.platform.ESClientPlatform;
import cn.leolezury.eternalstarlight.common.platform.ESPlatform;
import cn.leolezury.eternalstarlight.common.registry.ESBlockEntities;
import cn.leolezury.eternalstarlight.common.registry.ESBlocks;
import cn.leolezury.eternalstarlight.common.registry.ESDataAttachments;
import cn.leolezury.eternalstarlight.common.registry.ESDataComponents;
import cn.leolezury.eternalstarlight.common.registry.ESEntities;
import cn.leolezury.eternalstarlight.common.registry.ESItems;
import cn.leolezury.eternalstarlight.common.registry.ESMenuTypes;
import cn.leolezury.eternalstarlight.common.registry.ESParticles;
import cn.leolezury.eternalstarlight.common.registry.ESSpells;
import com.mojang.blaze3d.platform.InputConstants.Type;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.VertexFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;
import net.minecraft.Util;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.color.block.BlockColor;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.MenuScreens.ScreenConstructor;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.ChestRaftModel;
import net.minecraft.client.model.HumanoidArmorModel;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.MinecartModel;
import net.minecraft.client.model.RaftModel;
import net.minecraft.client.model.SkullModelBase;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.DripParticle;
import net.minecraft.client.particle.GlowParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SquidInkParticle;
import net.minecraft.client.particle.DripParticle.DripHangParticle;
import net.minecraft.client.particle.DripParticle.DripLandParticle;
import net.minecraft.client.particle.DripParticle.FallAndLandParticle;
import net.minecraft.client.particle.EndRodParticle.Provider;
import net.minecraft.client.particle.ParticleEngine.SpriteParticleRegistration;
import net.minecraft.client.particle.SuspendedTownParticle.HappyVillagerProvider;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.client.renderer.ShaderInstance;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.blockentity.BrushableBlockRenderer;
import net.minecraft.client.renderer.blockentity.CampfireRenderer;
import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.LivingEntityRenderer;
import net.minecraft.client.renderer.entity.PaintingRenderer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider.Context;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ColorParticleOption;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.util.Mth;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.component.ChargedProjectiles;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.SimplexNoise;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.NotNull;

public class ESClientSetupHandler {
   public static final List<Supplier<? extends Block>> BLOCKS_CUTOUT_MIPPED = List.of(
      ESBlocks.LUNAR_LEAVES,
      ESBlocks.NORTHLAND_LEAVES,
      ESBlocks.BANYIN_LEAVES,
      ESBlocks.BANYIN_ROOTS,
      ESBlocks.MUDDY_BANYIN_ROOTS,
      ESBlocks.SCARLET_LEAVES,
      ESBlocks.SCARLET_LEAVES_PILE,
      ESBlocks.TORREYA_LEAVES,
      ESBlocks.HANGING_ALGALEAVES,
      ESBlocks.HANGING_ALGALEAVES_PLANT,
      ESBlocks.ALGALEAVES,
      ESBlocks.NIGHTFALL_GRASS_BLOCK,
      ESBlocks.DEEPSILVER_BARS,
      ESBlocks.UNREALIUM_BARS,
      ESBlocks.GOLEM_STEEL_BARS,
      ESBlocks.WAXED_GOLEM_STEEL_BARS,
      ESBlocks.OXIDIZED_GOLEM_STEEL_BARS,
      ESBlocks.ACCUMULATOR
   );
   public static final List<Supplier<? extends Block>> BLOCKS_CUTOUT = List.of(
      ESBlocks.LUNAR_SAPLING,
      ESBlocks.POTTED_LUNAR_SAPLING,
      ESBlocks.LUNAR_TRAPDOOR,
      ESBlocks.LUNAR_DOOR,
      ESBlocks.NORTHLAND_SAPLING,
      ESBlocks.POTTED_NORTHLAND_SAPLING,
      ESBlocks.NORTHLAND_TRAPDOOR,
      ESBlocks.NORTHLAND_DOOR,
      ESBlocks.BANYIN_SAPLING,
      ESBlocks.POTTED_BANYIN_SAPLING,
      ESBlocks.BANYIN_TRAPDOOR,
      ESBlocks.BANYIN_DOOR,
      ESBlocks.SCARLET_SAPLING,
      ESBlocks.POTTED_SCARLET_SAPLING,
      ESBlocks.SCARLET_TRAPDOOR,
      ESBlocks.SCARLET_DOOR,
      ESBlocks.TORREYA_SAPLING,
      ESBlocks.POTTED_TORREYA_SAPLING,
      ESBlocks.TORREYA_TRAPDOOR,
      ESBlocks.TORREYA_DOOR,
      ESBlocks.TORREYA_VINES,
      ESBlocks.TORREYA_VINES_PLANT,
      ESBlocks.TORREYA_CAMPFIRE,
      ESBlocks.JINGLESTEM_SAPLING,
      ESBlocks.POTTED_JINGLESTEM_SAPLING,
      ESBlocks.JINGLESTEM_TRAPDOOR,
      ESBlocks.JINGLESTEM_DOOR,
      ESBlocks.CRADLEWOOD_SAPLING,
      ESBlocks.POTTED_CRADLEWOOD_SAPLING,
      ESBlocks.CRADLEWOOD_TRAPDOOR,
      ESBlocks.CRADLEWOOD_DOOR,
      ESBlocks.ETERNAL_ICE_LANTERN,
      ESBlocks.HAZE_ICE_LANTERN,
      ESBlocks.ICICLE,
      ESBlocks.ABYSSAL_FIRE,
      ESBlocks.STARLIGHT_FLOWER,
      ESBlocks.POTTED_STARLIGHT_FLOWER,
      ESBlocks.AUREATE_FLOWER,
      ESBlocks.POTTED_AUREATE_FLOWER,
      ESBlocks.CONEBLOOM,
      ESBlocks.POTTED_CONEBLOOM,
      ESBlocks.NIGHTFAN,
      ESBlocks.POTTED_NIGHTFAN,
      ESBlocks.PINK_ROSE,
      ESBlocks.POTTED_PINK_ROSE,
      ESBlocks.STARLIGHT_TORCHFLOWER,
      ESBlocks.POTTED_STARLIGHT_TORCHFLOWER,
      ESBlocks.NIGHTFAN_BUSH,
      ESBlocks.PINK_ROSE_BUSH,
      ESBlocks.NIGHT_SPROUTS,
      ESBlocks.SMALL_NIGHT_SPROUTS,
      ESBlocks.GLOWING_NIGHT_SPROUTS,
      ESBlocks.SMALL_GLOWING_NIGHT_SPROUTS,
      ESBlocks.LUNAR_GRASS,
      ESBlocks.GLOWING_LUNAR_GRASS,
      ESBlocks.CRESCENT_GRASS,
      ESBlocks.POTTED_CRESCENT_GRASS,
      ESBlocks.GLOWING_CRESCENT_GRASS,
      ESBlocks.POTTED_GLOWING_CRESCENT_GRASS,
      ESBlocks.PARASOL_GRASS,
      ESBlocks.POTTED_PARASOL_GRASS,
      ESBlocks.GLOWING_PARASOL_GRASS,
      ESBlocks.POTTED_GLOWING_PARASOL_GRASS,
      ESBlocks.LUNAR_BUSH,
      ESBlocks.GLOWING_LUNAR_BUSH,
      ESBlocks.TALL_CRESCENT_GRASS,
      ESBlocks.TALL_GLOWING_CRESCENT_GRASS,
      ESBlocks.LUNAR_REED,
      ESBlocks.WHISPERBLOOM,
      ESBlocks.POTTED_WHISPERBLOOM,
      ESBlocks.GLADESPIKE,
      ESBlocks.POTTED_GLADESPIKE,
      ESBlocks.VIVIDSTALK,
      ESBlocks.POTTED_VIVIDSTALK,
      ESBlocks.TALL_GLADESPIKE,
      ESBlocks.MOONLIGHT_BUSH,
      ESBlocks.GLINTGRASS,
      ESBlocks.GLOWING_MUSHROOM,
      ESBlocks.POTTED_GLOWING_MUSHROOM,
      ESBlocks.BERRIES_VINES,
      ESBlocks.BERRIES_VINES_PLANT,
      ESBlocks.CAVE_MOSS,
      ESBlocks.CAVE_MOSS_PLANT,
      ESBlocks.CAVE_MOSS_VEIN,
      ESBlocks.CAVE_MOSS_BLOCK,
      ESBlocks.CAVE_MOSS_CARPET,
      ESBlocks.BOULDERSHROOM,
      ESBlocks.POTTED_BOULDERSHROOM,
      ESBlocks.BOULDERSHROOM_ROOTS,
      ESBlocks.BOULDERSHROOM_ROOTS_PLANT,
      ESBlocks.SHINING_MUSHROOM,
      ESBlocks.POTTED_SHINING_MUSHROOM,
      ESBlocks.SWAMP_ROSE,
      ESBlocks.POTTED_SWAMP_ROSE,
      ESBlocks.FANTABUD,
      ESBlocks.GREEN_FANTABUD,
      ESBlocks.FANTAFERN,
      ESBlocks.POTTED_FANTAFERN,
      ESBlocks.GREEN_FANTAFERN,
      ESBlocks.POTTED_GREEN_FANTAFERN,
      ESBlocks.FANTAGRASS,
      ESBlocks.GREEN_FANTAGRASS,
      ESBlocks.HANGING_FANTAGRASS,
      ESBlocks.HANGING_FANTAGRASS_PLANT,
      ESBlocks.ORANGE_SCARLET_BUD,
      ESBlocks.PURPLE_SCARLET_BUD,
      ESBlocks.RED_SCARLET_BUD,
      ESBlocks.SCARLET_GRASS,
      ESBlocks.MAUVE_FERN,
      ESBlocks.WITHERED_STARLIGHT_FLOWER,
      ESBlocks.POTTED_WITHERED_STARLIGHT_FLOWER,
      ESBlocks.AMARAMBER_GRASS,
      ESBlocks.POTTED_AMARAMBER_GRASS,
      ESBlocks.AMARAMBER_GRASS_BUSH,
      ESBlocks.GLOOMCANDLE_ROOT,
      ESBlocks.DEAD_LUNAR_BUSH,
      ESBlocks.POTTED_DEAD_LUNAR_BUSH,
      ESBlocks.DESERT_AMETHYSIA,
      ESBlocks.POTTED_DESERT_AMETHYSIA,
      ESBlocks.WITHERED_DESERT_AMETHYSIA,
      ESBlocks.POTTED_WITHERED_DESERT_AMETHYSIA,
      ESBlocks.SUNSET_THORNBLOOM,
      ESBlocks.POTTED_SUNSET_THORNBLOOM,
      ESBlocks.AMETHYSIA_GRASS,
      ESBlocks.LUNARIS_CACTUS,
      ESBlocks.RED_STARLIGHT_CRYSTAL_CLUSTER,
      ESBlocks.BLUE_STARLIGHT_CRYSTAL_CLUSTER,
      ESBlocks.BLOOMING_RED_STARLIGHT_CRYSTAL_CLUSTER,
      ESBlocks.BLOOMING_BLUE_STARLIGHT_CRYSTAL_CLUSTER,
      ESBlocks.RED_CRYSTALFLEUR,
      ESBlocks.POTTED_RED_CRYSTALFLEUR,
      ESBlocks.BLUE_CRYSTALFLEUR,
      ESBlocks.POTTED_BLUE_CRYSTALFLEUR,
      ESBlocks.RED_CRYSTALFLEUR_VINE,
      ESBlocks.BLUE_CRYSTALFLEUR_VINE,
      ESBlocks.FIRE_ORCHID,
      ESBlocks.POTTED_FIRE_ORCHID,
      ESBlocks.BLAZEBANK_GRASS,
      ESBlocks.MOONLIGHT_LILY_PAD,
      ESBlocks.STARLIT_LILY_PAD,
      ESBlocks.MOONLIGHT_DUCKWEED,
      ESBlocks.ABYSSAL_KELP,
      ESBlocks.ABYSSAL_KELP_PLANT,
      ESBlocks.ORBFLORA,
      ESBlocks.ORBFLORA_PLANT,
      ESBlocks.SPIRAL_KELP,
      ESBlocks.SPIRAL_KELP_PLANT,
      ESBlocks.SEA_ROSA,
      ESBlocks.WICK_GRASS,
      ESBlocks.LUMENSTEM,
      ESBlocks.LUMENSTEM_PLANT,
      ESBlocks.MARIMOLD,
      ESBlocks.CIRCULUSH,
      ESBlocks.STONETT,
      ESBlocks.LUMINIS,
      ESBlocks.GLOWLIS,
      ESBlocks.GLOREED,
      ESBlocks.STARLIGHT_SEAGRASS,
      ESBlocks.JINGLING_PICKLE,
      ESBlocks.DEAD_TENTACLES_CORAL,
      ESBlocks.TENTACLES_CORAL,
      ESBlocks.DEAD_TENTACLES_CORAL_FAN,
      ESBlocks.TENTACLES_CORAL_FAN,
      ESBlocks.DEAD_TENTACLES_CORAL_WALL_FAN,
      ESBlocks.TENTACLES_CORAL_WALL_FAN,
      ESBlocks.DEAD_GOLDEN_CORAL,
      ESBlocks.GOLDEN_CORAL,
      ESBlocks.DEAD_GOLDEN_CORAL_FAN,
      ESBlocks.GOLDEN_CORAL_FAN,
      ESBlocks.DEAD_GOLDEN_CORAL_WALL_FAN,
      ESBlocks.GOLDEN_CORAL_WALL_FAN,
      ESBlocks.DEAD_CRYSTALLUM_CORAL,
      ESBlocks.CRYSTALLUM_CORAL,
      ESBlocks.DEAD_CRYSTALLUM_CORAL_FAN,
      ESBlocks.CRYSTALLUM_CORAL_FAN,
      ESBlocks.DEAD_CRYSTALLUM_CORAL_WALL_FAN,
      ESBlocks.CRYSTALLUM_CORAL_WALL_FAN,
      ESBlocks.VELVETUMOSS_VILLI,
      ESBlocks.RED_VELVETUMOSS_VILLI,
      ESBlocks.RED_VELVETUMOSS_FLOWER,
      ESBlocks.POTTED_RED_VELVETUMOSS_FLOWER,
      ESBlocks.CRYSTALLIZED_LUNAR_GRASS,
      ESBlocks.RED_CRYSTAL_ROOTS,
      ESBlocks.BLUE_CRYSTAL_ROOTS,
      ESBlocks.TWILVEWRYM_HERB,
      ESBlocks.STELLAFLY_BUSH,
      ESBlocks.GLIMMERFLY_BUSH,
      ESBlocks.GOLDEN_GRASS,
      ESBlocks.TALL_GOLDEN_GRASS,
      ESBlocks.CRINOA,
      ESBlocks.NOCTURNAL_MILLET_PANICLE,
      ESBlocks.NOCTURNAL_MILLET_STALK,
      ESBlocks.DEEPSILVER_GRATE,
      ESBlocks.PUNGENCY_FRUIT_VINES,
      ESBlocks.STARFIRE_BIRD_NEST,
      ESBlocks.OAK_STARFIRE_BIRD_AVIARY,
      ESBlocks.SPRUCE_STARFIRE_BIRD_AVIARY,
      ESBlocks.BIRCH_STARFIRE_BIRD_AVIARY,
      ESBlocks.ACACIA_STARFIRE_BIRD_AVIARY,
      ESBlocks.CHERRY_STARFIRE_BIRD_AVIARY,
      ESBlocks.JUNGLE_STARFIRE_BIRD_AVIARY,
      ESBlocks.DARK_OAK_STARFIRE_BIRD_AVIARY,
      ESBlocks.CRIMSON_STARFIRE_BIRD_AVIARY,
      ESBlocks.WARPED_STARFIRE_BIRD_AVIARY,
      ESBlocks.MANGROVE_STARFIRE_BIRD_AVIARY,
      ESBlocks.BAMBOO_STARFIRE_BIRD_AVIARY,
      ESBlocks.LUNAR_STARFIRE_BIRD_AVIARY,
      ESBlocks.NORTHLAND_STARFIRE_BIRD_AVIARY,
      ESBlocks.BANYIN_STARFIRE_BIRD_AVIARY,
      ESBlocks.SCARLET_STARFIRE_BIRD_AVIARY,
      ESBlocks.TORREYA_STARFIRE_BIRD_AVIARY,
      ESBlocks.JINGLESTEM_STARFIRE_BIRD_AVIARY,
      ESBlocks.CRADLEWOOD_STARFIRE_BIRD_AVIARY,
      ESBlocks.AMARAMBER_LANTERN,
      ESBlocks.AMARAMBER_FIRE,
      ESBlocks.THIOQUARTZ_CLUSTER,
      ESBlocks.THE_GATEKEEPER_SPAWNER,
      ESBlocks.STARLIGHT_GOLEM_SPAWNER,
      ESBlocks.PERMAFROST_SPAWNER,
      ESBlocks.LUNAR_MONSTROSITY_SPAWNER,
      ESBlocks.GOLEM_STEEL_GRATE,
      ESBlocks.WAXED_GOLEM_STEEL_GRATE,
      ESBlocks.OXIDIZED_GOLEM_STEEL_GRATE,
      ESBlocks.MECHANICAL_SPAWNER,
      ESBlocks.SHADEGRIEVE,
      ESBlocks.BLOOMING_SHADEGRIEVE,
      ESBlocks.FLARE_SPAWNER,
      ESBlocks.DOOMED_TORCH,
      ESBlocks.WALL_DOOMED_TORCH,
      ESBlocks.DOOMED_REDSTONE_TORCH,
      ESBlocks.WALL_DOOMED_REDSTONE_TORCH
   );
   public static final List<Supplier<? extends Block>> BLOCKS_TRANSLUCENT = List.of(
      ESBlocks.THIN_ETERNAL_ICE,
      ESBlocks.REINFORCED_ICE,
      ESBlocks.REINFORCED_ICE_PANE,
      ESBlocks.LUNARIS_CACTUS_GEL_BLOCK,
      ESBlocks.MARIMOLD_BLOCK,
      ESBlocks.FLOWGLAZE,
      ESBlocks.FLOWGLAZE_PANE,
      ESBlocks.DUSK_GLASS,
      ESBlocks.STARLIGHT_PORTAL
   );
   public static final List<ESClientSetupHandler.WorldVisualEffectSpawnFunction> VISUAL_EFFECT_SPAWN_FUNCTIONS = List.of(TrailVisualEffect::clientTick);
   public static final Map<ModelResourceLocation, Map<ItemDisplayContext, ModelResourceLocation>> ITEMS_WITH_SPECIAL_MODEL = new HashMap<>();
   public static boolean modifiedBakedModels = false;
   public static final String KEY_CATEGORY_ETERNAL_STARLIGHT = "key.categories.eternal_starlight";
   public static final Map<ResourceLocation, KeyMapping> KEY_MAPPINGS = Map.of(
      EternalStarlight.id("switch_crest"),
      new KeyMapping(Util.makeDescriptionId("key", EternalStarlight.id("switch_crest")), Type.KEYSYM, 72, "key.categories.eternal_starlight")
   );
   public static final SimplexNoise COLOR_NOISE = new SimplexNoise(new WorldgenRandom(new LegacyRandomSource(1225L)));
   private static final CubeDeformation INNER_ARMOR_DEFORMATION = new CubeDeformation(0.5F);
   private static final CubeDeformation OUTER_ARMOR_DEFORMATION = new CubeDeformation(1.0F);

   public static ModelResourceLocation getSpecialModel(ModelResourceLocation item, ItemDisplayContext context) {
      return ITEMS_WITH_SPECIAL_MODEL.containsKey(item) && ITEMS_WITH_SPECIAL_MODEL.get(item).containsKey(context)
         ? new ModelResourceLocation(
            ITEMS_WITH_SPECIAL_MODEL.get(item).get(context).id().withPrefix("item/"),
            ESPlatform.INSTANCE.getLoader() == ESPlatform.Loader.FABRIC ? "fabric_resource" : "standalone"
         )
         : item;
   }

   private static void registerSimpleSpecialModel(String id) {
      ITEMS_WITH_SPECIAL_MODEL.put(
         ModelResourceLocation.inventory(EternalStarlight.id(id)),
         Map.of(
            ItemDisplayContext.HEAD,
            ModelResourceLocation.inventory(EternalStarlight.id(id + "_inventory")),
            ItemDisplayContext.GUI,
            ModelResourceLocation.inventory(EternalStarlight.id(id + "_inventory")),
            ItemDisplayContext.GROUND,
            ModelResourceLocation.inventory(EternalStarlight.id(id + "_inventory")),
            ItemDisplayContext.FIXED,
            ModelResourceLocation.inventory(EternalStarlight.id(id + "_inventory"))
         )
      );
   }

   public static void clientSetup() {
      registerSimpleSpecialModel("thermal_springstone_scythe");
      registerSimpleSpecialModel("thermal_springstone_hammer");
      registerSimpleSpecialModel("glacite_scythe");
      registerSimpleSpecialModel("malarite_spear");
      registerSimpleSpecialModel("pungency_fruit_spear");
      registerSimpleSpecialModel("seeds_launcher");
      registerSimpleSpecialModel("starfire_scythe");
      registerSimpleSpecialModel("starfire_hammer");
      registerSimpleSpecialModel("flowglaze_scythe");
      registerSimpleSpecialModel("glistering_greatsword");
      registerSimpleSpecialModel("glistering_greatsword_blocking");
      registerSimpleSpecialModel("glistering_morning_star");
      registerSimpleSpecialModel("golem_steel_greatsword");
      registerSimpleSpecialModel("golem_steel_greatsword_blocking");
      registerSimpleSpecialModel("crystal_greatsword");
      registerSimpleSpecialModel("crystal_greatsword_blocking");
      registerSimpleSpecialModel("moonring_greatsword");
      registerSimpleSpecialModel("moonring_greatsword_blocking");
      registerSimpleSpecialModel("petal_scythe");
      registerSimpleSpecialModel("crescent_spear");
      registerSimpleSpecialModel("bonemore");
      registerSimpleSpecialModel("bonemore_blocking");
      registerSimpleSpecialModel("doomeden_rapier");
      registerSimpleSpecialModel("orb_of_prophecy");
      registerSimpleSpecialModel("orb_of_prophecy_with_crests");
      PlayerAnimator.register(
         new PlayerAnimator.UseItemAnimationTrigger(ESItems.ORB_OF_PROPHECY),
         player -> new PlayerAnimator.PlayerAnimationState(
            PlayerAnimation.ORB_OF_PROPHECY_USE,
            PlayerAnimation.FIRST_PERSON_ORB_OF_PROPHECY_USE,
            List.of(new PlayerAnimator.UseItemHandAnimationTransformer(), new PlayerAnimator.CopyOuterLayerAnimationTransformer()),
            true,
            true,
            true,
            true
         )
      );
      PlayerAnimator.register(
         new PlayerAnimator.CastSpellAnimationTrigger(ESSpells.LASER_BEAM),
         player -> new PlayerAnimator.PlayerAnimationState(
            PlayerAnimation.GATHER_HANDS,
            PlayerAnimation.FIRST_PERSON_GATHER_HANDS,
            List.of(new PlayerAnimator.CastSpellHandAnimationTransformer(), new PlayerAnimator.CopyOuterLayerAnimationTransformer()),
            true,
            true,
            true,
            true
         )
      );
      TrailVisualEffect.registerTrailRenderType(ESEntities.AETHERSENT_METEOR.get(), ESRenderType.entityTranslucentAdditiveGlow(TrailVisualEffect.TRAIL_TEXTURE));
      TrailVisualEffect.registerTrailRenderType(ESEntities.CREST.get(), ESRenderType.entityTranslucentAdditiveGlow(TrailVisualEffect.TRAIL_TEXTURE));
      TrailVisualEffect.registerTrailRenderType(
         ESEntities.GATEKEEPER_FIREBALL.get(), ESRenderType.entityTranslucentAdditiveGlow(TrailVisualEffect.TRAIL_TEXTURE)
      );
      TrailVisualEffect.registerTrailRenderType(ESEntities.ENERGY_SPARK.get(), ESRenderType.entityTranslucentAdditiveGlow(TrailVisualEffect.TRAIL_TEXTURE));
      TrailVisualEffect.registerTrailRenderType(ESEntities.BALL_LIGHTNING.get(), ESRenderType.entityTranslucentAdditiveGlow(TrailVisualEffect.TRAIL_TEXTURE));
      SkullBlockRenderer.SKIN_BY_TYPE.put(ESSkullType.TANGLED, TangledSkullRenderer.ENTITY_TEXTURE);
      ItemProperties.register(ESItems.STARFALL_LONGBOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         ESItems.STARFALL_LONGBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.GALACTIC_QUIVER.get(), EternalStarlight.id("arrows"), (stack, level, entity, i) -> GalacticQuiverItem.hasArrows(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.GLACITE_SHIELD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F
            : 0.0F
      );
      ItemProperties.register(
         ESItems.DEEPSILVER_BRUSH.get(),
         ResourceLocation.withDefaultNamespace("brushing"),
         (stack, level, entity, i) -> entity != null && entity.getUseItem() == stack ? entity.getUseItemRemainingTicks() % 10 / 10.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.UNREALIUM_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (stack, level, entity, i) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(stack)
                  ? 0.0F
                  : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration(stack, entity);
            }
         }
      );
      ItemProperties.register(
         ESItems.UNREALIUM_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.UNREALIUM_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("charged"),
         (stack, level, entity, i) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.UNREALIUM_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
         ChargedProjectiles chargedProjectiles = (ChargedProjectiles)stack.get(DataComponents.CHARGED_PROJECTILES);
         return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
      });
      ItemProperties.register(
         ESItems.MALARITE_SPEAR.get(),
         ResourceLocation.withDefaultNamespace("throwing"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.PUNGENCY_FRUIT_SPEAR.get(),
         ResourceLocation.withDefaultNamespace("throwing"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.STARFIRE_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (stack, level, entity, i) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(stack)
                  ? 0.0F
                  : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration(stack, entity);
            }
         }
      );
      ItemProperties.register(
         ESItems.STARFIRE_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.STARFIRE_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("charged"),
         (stack, level, entity, i) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.STARFIRE_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
         ChargedProjectiles chargedProjectiles = (ChargedProjectiles)stack.get(DataComponents.CHARGED_PROJECTILES);
         return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
      });
      ItemProperties.register(ESItems.FLOWGLAZE_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         ESItems.FLOWGLAZE_BOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.FLOWGLAZE_SHIELD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (itemStack, clientLevel, livingEntity, i) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F
            : 0.0F
      );
      ItemProperties.register(ESItems.CANDLASH.get(), EternalStarlight.id("extended"), (stack, level, entity, i) -> {
         if (entity != null && level != null) {
            return entity.getMainHandItem() == stack && level.getEntity(ESDataAttachments.WHIP.getData(entity)) instanceof Candlash ? 1.0F : 0.0F;
         } else {
            return 0.0F;
         }
      });
      ItemProperties.register(ESItems.TENTACLE_SPIKE.get(), EternalStarlight.id("extended"), (stack, level, entity, i) -> {
         if (entity != null && level != null) {
            return entity.getMainHandItem() == stack && level.getEntity(ESDataAttachments.WHIP.getData(entity)) instanceof TentacleSpike ? 1.0F : 0.0F;
         } else {
            return 0.0F;
         }
      });
      ItemProperties.register(
         ESItems.SHATTERED_SWORD.get(), EternalStarlight.id("no_blade"), (stack, level, entity, i) -> ShatteredSwordItem.hasBlade(stack) ? 0.0F : 1.0F
      );
      ItemProperties.register(
         ESItems.DAGGER_OF_HUNGER.get(),
         EternalStarlight.id("hunger_state"),
         (stack, level, entity, i) -> Math.min(2.0F, ((Float)stack.getOrDefault(ESDataComponents.HUNGER_LEVEL.get(), 0.0F) + 1.0F) * 1.5F) / 2.0F
      );
      ItemProperties.register(
         ESItems.GLISTERING_GREATSWORD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.GLISTERING_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         ESItems.GLISTERING_BOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.COLDSNAP.get(), EternalStarlight.id("extended"), (stack, level, entity, i) -> {
         if (entity != null && level != null) {
            return entity.getMainHandItem() == stack && level.getEntity(ESDataAttachments.WHIP.getData(entity)) instanceof Coldsnap ? 1.0F : 0.0F;
         } else {
            return 0.0F;
         }
      });
      ItemProperties.register(
         ESItems.GOLEM_STEEL_GREATSWORD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.MECHANICAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (stack, level, entity, i) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(stack)
                  ? 0.0F
                  : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration(stack, entity);
            }
         }
      );
      ItemProperties.register(
         ESItems.MECHANICAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.MECHANICAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("charged"),
         (stack, level, entity, i) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.MECHANICAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
         ChargedProjectiles chargedProjectiles = (ChargedProjectiles)stack.get(DataComponents.CHARGED_PROJECTILES);
         return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
      });
      ItemProperties.register(
         ESItems.CRYSTAL_GREATSWORD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.CRYSTAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (stack, level, entity, i) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(stack)
                  ? 0.0F
                  : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration(stack, entity);
            }
         }
      );
      ItemProperties.register(
         ESItems.CRYSTAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.CRYSTAL_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("charged"),
         (stack, level, entity, i) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.CRYSTAL_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
         ChargedProjectiles chargedProjectiles = (ChargedProjectiles)stack.get(DataComponents.CHARGED_PROJECTILES);
         return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
      });
      ItemProperties.register(
         ESItems.WILTED_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (stack, level, entity, i) -> {
            if (entity == null) {
               return 0.0F;
            } else {
               return CrossbowItem.isCharged(stack)
                  ? 0.0F
                  : (float)(stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / CrossbowItem.getChargeDuration(stack, entity);
            }
         }
      );
      ItemProperties.register(
         ESItems.WILTED_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack && !CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.WILTED_CROSSBOW.get(),
         ResourceLocation.withDefaultNamespace("charged"),
         (stack, level, entity, i) -> CrossbowItem.isCharged(stack) ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.WILTED_CROSSBOW.get(), ResourceLocation.withDefaultNamespace("firework"), (stack, level, entity, i) -> {
         ChargedProjectiles chargedProjectiles = (ChargedProjectiles)stack.get(DataComponents.CHARGED_PROJECTILES);
         return chargedProjectiles != null && chargedProjectiles.contains(Items.FIREWORK_ROCKET) ? 1.0F : 0.0F;
      });
      ItemProperties.register(ESItems.MOONRING_BOW.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         ESItems.MOONRING_BOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.MOONRING_GREATSWORD.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.CHAIN_OF_SOULS.get(), EternalStarlight.id("extended"), (stack, level, entity, i) -> {
         if (entity != null && level != null) {
            boolean mainHand = entity.getMainHandItem() == stack;
            boolean offhand = entity.getOffhandItem() == stack;
            if (entity.getMainHandItem().is(ESItems.CHAIN_OF_SOULS.get())) {
               offhand = false;
            }

            return (mainHand || offhand) && level.getEntity(ESDataAttachments.GRAPPLING.getData(entity)) instanceof ChainOfSouls ? 1.0F : 0.0F;
         } else {
            return 0.0F;
         }
      });
      ItemProperties.register(
         ESItems.CRESCENT_SPEAR.get(),
         ResourceLocation.withDefaultNamespace("throwing"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(
         ESItems.BONEMORE.get(),
         ResourceLocation.withDefaultNamespace("blocking"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
      ItemProperties.register(ESItems.BOW_OF_BLOOD.get(), ResourceLocation.withDefaultNamespace("pull"), (stack, level, entity, i) -> {
         if (entity == null) {
            return 0.0F;
         } else {
            return entity.getUseItem() != stack ? 0.0F : (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
         }
      });
      ItemProperties.register(
         ESItems.BOW_OF_BLOOD.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (stack, level, entity, i) -> entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
      );
   }

   public static void registerBlockColors(ESClientSetupHandler.BlockColorRegisterStrategy strategy) {
      BlockColor leavesColor = (state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageFoliageColor(getter, pos) : 5195923;
      BlockColor grassColor = (state, getter, pos, i) -> getter != null && pos != null ? BiomeColors.getAverageGrassColor(getter, pos) : 5195923;
      BlockColor dyeColor = (state, getter, pos, i) -> state.getBlock().defaultMapColor().col;
      strategy.register(grassColor, (Block)ESBlocks.NIGHTFALL_GRASS_BLOCK.get());
      strategy.register(grassColor, (Block)ESBlocks.CAVE_MOSS.get());
      strategy.register(grassColor, (Block)ESBlocks.CAVE_MOSS_PLANT.get());
      strategy.register(grassColor, (Block)ESBlocks.CAVE_MOSS_VEIN.get());
      strategy.register(grassColor, ESBlocks.CAVE_MOSS_BLOCK.get());
      strategy.register(grassColor, (Block)ESBlocks.CAVE_MOSS_CARPET.get());
      strategy.register(grassColor, (Block)ESBlocks.MOONLIGHT_LILY_PAD.get());
      strategy.register(grassColor, (Block)ESBlocks.STARLIT_LILY_PAD.get());
      strategy.register(grassColor, (Block)ESBlocks.MOONLIGHT_DUCKWEED.get());
      strategy.register(leavesColor, (Block)ESBlocks.BANYIN_LEAVES.get());
      strategy.register(leavesColor, (Block)ESBlocks.STARFIRE_BIRD_NEST.get());
      strategy.register(dyeColor, (Block)ESBlocks.WHITE_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.ORANGE_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.MAGENTA_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIGHT_BLUE_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.YELLOW_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIME_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.PINK_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.GRAY_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIGHT_GRAY_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.CYAN_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.PURPLE_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.BLUE_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.BROWN_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.GREEN_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.RED_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.BLACK_YETI_FUR.get());
      strategy.register(dyeColor, (Block)ESBlocks.WHITE_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.ORANGE_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.YELLOW_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIME_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.PINK_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.GRAY_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.CYAN_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.PURPLE_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.BLUE_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.BROWN_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.GREEN_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.RED_YETI_FUR_CARPET.get());
      strategy.register(dyeColor, (Block)ESBlocks.BLACK_YETI_FUR_CARPET.get());
      strategy.register((state, getter, pos, i) -> {
         float progress = state.hasProperty(AccumulatorBlock.POWER) ? ((Integer)state.getValue(AccumulatorBlock.POWER)).intValue() / 15.0F : 0.0F;
         return ARGB32.lerp(progress, -1, -16719873);
      }, (Block)ESBlocks.ACCUMULATOR.get());
      strategy.register(
         (state, getter, pos, i) -> {
            double progress = getter != null && pos != null
               ? (COLOR_NOISE.getValue(pos.getX() / 10.0, pos.getY() / 10.0, pos.getZ() / 10.0) + 1.0) / 2.0
               : (
                     Math.sin(
                           (
                                 ESClientHandler.clientTickCount
                                    + Minecraft.getInstance()
                                       .getTimer()
                                       .getGameTimeDeltaPartialTick(
                                          Minecraft.getInstance().level != null && Minecraft.getInstance().level.tickRateManager().runsNormally()
                                       )
                              )
                              / 30.0
                        )
                        + 1.0
                  )
                  / 2.0;
            return ARGB32.color((int)Mth.lerp(progress, 218.0, 255.0), (int)Mth.lerp(progress, 90.0, 255.0), (int)Mth.lerp(progress, 255.0, 116.0));
         },
         (Block)ESBlocks.DUSK_GLASS.get()
      );
   }

   public static void registerItemColors(ESClientSetupHandler.ItemColorRegisterStrategy strategy) {
      ItemColor toBlock = (stack, packedLight) -> {
         if (stack.getItem() instanceof BlockItem blockItem) {
            BlockState blockstate = blockItem.getBlock().defaultBlockState();
            return Minecraft.getInstance().getBlockColors().getColor(blockstate, null, null, packedLight);
         } else {
            return -1;
         }
      };
      strategy.register(toBlock, (ItemLike)ESBlocks.NIGHTFALL_GRASS_BLOCK.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CAVE_MOSS.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CAVE_MOSS_PLANT.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CAVE_MOSS_VEIN.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CAVE_MOSS_BLOCK.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CAVE_MOSS_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.MOONLIGHT_LILY_PAD.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.STARLIT_LILY_PAD.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.MOONLIGHT_DUCKWEED.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BANYIN_LEAVES.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.STARFIRE_BIRD_NEST.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.WHITE_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.ORANGE_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.MAGENTA_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIGHT_BLUE_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.YELLOW_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIME_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.PINK_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.GRAY_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIGHT_GRAY_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CYAN_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.PURPLE_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BLUE_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BROWN_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.GREEN_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.RED_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BLACK_YETI_FUR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.WHITE_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.ORANGE_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.MAGENTA_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIGHT_BLUE_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.YELLOW_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIME_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.PINK_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.GRAY_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.LIGHT_GRAY_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.CYAN_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.PURPLE_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BLUE_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BROWN_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.GREEN_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.RED_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.BLACK_YETI_FUR_CARPET.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.ACCUMULATOR.get());
      strategy.register(toBlock, (ItemLike)ESBlocks.DUSK_GLASS.get());
   }

   public static void registerShaders(ESClientSetupHandler.ShaderRegisterStrategy strategy) {
      strategy.register(EternalStarlight.id("crest_selection_gui"), DefaultVertexFormat.POSITION_TEX, ESShaders::setCrestSelectionGui);
      strategy.register(EternalStarlight.id("rendertype_starlight_portal"), DefaultVertexFormat.BLOCK, ESShaders::setRenderTypeStarlightPortal);
      strategy.register(EternalStarlight.id("rendertype_eclipse"), DefaultVertexFormat.BLOCK, ESShaders::setRenderTypeEclipse);
      strategy.register(EternalStarlight.id("aurora"), DefaultVertexFormat.POSITION_COLOR, ESShaders::setAurora);
   }

   public static void modifyBakingResult(Map<ModelResourceLocation, BakedModel> models) {
      if (!modifiedBakedModels) {
         for (ModelResourceLocation id : models.keySet()) {
            String path = id.id().getPath();
            if (id.id().getNamespace().equals("eternal_starlight")
               && (
                  path.startsWith("thermal_springstone_")
                     || path.startsWith("starfire_") && !path.startsWith("starfire_bird") && !path.startsWith("starfire_upgrade")
               )) {
               models.put(id, ESClientPlatform.INSTANCE.getGlowingBakedModel(models.get(id)));
            }
         }

         modifiedBakedModels = true;
      }
   }

   public static void registerExtraBakedModels(Consumer<ModelResourceLocation> registration) {
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("thermal_springstone_scythe_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("thermal_springstone_hammer_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("glacite_scythe_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("malarite_spear_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("pungency_fruit_spear_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("seeds_launcher_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("starfire_scythe_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("starfire_hammer_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("flowglaze_scythe_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("glistering_greatsword_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("glistering_morning_star_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("golem_steel_greatsword_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("crystal_greatsword_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("moonring_greatsword_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("petal_scythe_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("crescent_spear_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("bonemore_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("doomeden_rapier_inventory")));
      registration.accept(ModelResourceLocation.inventory(EternalStarlight.id("orb_of_prophecy_inventory")));
   }

   public static void registerParticleProviders(ESClientSetupHandler.ParticleProviderRegisterStrategy strategy) {
      strategy.register((ParticleType)ESParticles.STARLIGHT.get(), Provider::new);
      strategy.register((ParticleType)ESParticles.STARDUST.get(), Provider::new);
      strategy.register((ParticleType)ESParticles.FIREFLY.get(), FireflyParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.SCARLET_LEAVES.get(), FallingLeavesParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.CRADLEWOOD_LEAVES.get(), FallingLeavesParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.SHADEGRIEVE_LEAVES.get(), FallingLeavesParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.SPIRAL_KELP_LEAVES.get(), FallingLeavesParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.FALLING_RED_CRYSTAL_MOSS.get(), FallingLeavesParticle.GlowProvider::new);
      strategy.register((ParticleType)ESParticles.FALLING_BLUE_CRYSTAL_MOSS.get(), FallingLeavesParticle.GlowProvider::new);
      strategy.register((ParticleType)ESParticles.ENERGY.get(), EnergyParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.ELECTRIC_SPARK.get(), ElectricSparkParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.LUNAR_SLASH.get(), LunarSlashParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.CRYSTALLIZED_MOTH_SONAR.get(), SonarParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.AMARAMBER_FLAME.get(), net.minecraft.client.particle.FlameParticle.Provider::new);
      strategy.register(ESParticles.EXPLOSION.get(), ESExplosionParticle.Provider::new);
      strategy.register(ESParticles.BLAST.get(), ESExplosionParticle.Provider::new);
      strategy.register(ESParticles.SMOKE.get(), ESSmokeParticle.Provider::new);
      strategy.register(ESParticles.RING_EXPLOSION.get(), RingExplosionParticle.Provider::new);
      strategy.register(ESParticles.RING.get(), RingParticle.Provider::new);
      strategy.register(ESParticles.ORBITAL_TRAIL.get(), OrbitalTrailParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.METEOR.get(), MeteorParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.PARRY.get(), TrailParticle.Provider::new);
      strategy.register(ESParticles.GATHERING_ENERGY.get(), GatheringTrailParticle.Provider::new);
      strategy.register(ESParticles.GATHERING_SOUL.get(), GatheringTrailParticle.Provider::new);
      strategy.register(ESParticles.GATHERING_FLARE.get(), GatheringTrailParticle.Provider::new);
      strategy.register(ESParticles.GLOW.get(), ESGlowParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.AETHERSENT_SMOKE.get(), AethersentSmokeParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.SMOKE_TRAIL.get(), SmokeTrailParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.AETHERSENT_EXPLOSION.get(), AethersentExplosionParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.ASHEN_SNOW.get(), AshenSnowParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.ORBITAL_ASHEN_SNOW.get(), OrbitalAshenSnowParticle.Provider::new);
      strategy.register(ESParticles.EXPLOSION_SHOCK.get(), ExplosionShockParticle.Provider::new);
      strategy.register(
         ESParticles.COLORED_INK.get(),
         spriteSet -> new ParticleProvider<ColorParticleOption>() {
            @NotNull
            public Particle createParticle(ColorParticleOption option, ClientLevel level, double x, double y, double z, double dx, double dy, double dz) {
               return new SquidInkParticle(
                  level,
                  x,
                  y,
                  z,
                  dx,
                  dy,
                  dz,
                  ARGB32.colorFromFloat(option.getAlpha(), 1.0F - option.getRed(), 1.0F - option.getGreen(), 1.0F - option.getBlue()),
                  spriteSet
               );
            }
         }
      );
      strategy.register((ParticleType)ESParticles.AMARAMBER_WAX_ON.get(), spriteSet -> (type, level, x, y, z, dx, dy, dz) -> {
         GlowParticle particle = new GlowParticle(level, x, y, z, 0.0, 0.0, 0.0, spriteSet);
         particle.setParticleSpeed(dx * 0.01 / 2.0, dy * 0.01, dz * 0.01 / 2.0);
         particle.setLifetime(level.random.nextInt(30) + 10);
         return particle;
      });
      strategy.register((ParticleType)ESParticles.DRIPPING_MUD.get(), spriteSet -> (type, level, x, y, z, dx, dy, dz) -> {
         DripParticle particle = new DripHangParticle(level, x, y, z, Fluids.EMPTY, (ParticleOptions)ESParticles.FALLING_MUD.get());
         particle.setColor(0.19607843F, 0.10980392F, 0.08627451F);
         particle.pickSprite(spriteSet);
         return particle;
      });
      strategy.register((ParticleType)ESParticles.FALLING_MUD.get(), spriteSet -> (type, level, x, y, z, dx, dy, dz) -> {
         DripParticle particle = new FallAndLandParticle(level, x, y, z, Fluids.EMPTY, (ParticleOptions)ESParticles.LANDING_MUD.get());
         particle.setColor(0.26666668F, 0.1764706F, 0.105882354F);
         particle.pickSprite(spriteSet);
         return particle;
      });
      strategy.register((ParticleType)ESParticles.LANDING_MUD.get(), spriteSet -> (type, level, x, y, z, dx, dy, dz) -> {
         DripParticle particle = new DripLandParticle(level, x, y, z, Fluids.EMPTY);
         particle.setColor(0.26666668F, 0.1764706F, 0.105882354F);
         particle.pickSprite(spriteSet);
         return particle;
      });
      strategy.register((ParticleType)ESParticles.ALLIED.get(), HappyVillagerProvider::new);
      strategy.register((ParticleType)ESParticles.PUNGENCY_FRUIT_SMOKE.get(), PungencyFruitSmokeParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.STARFIRE.get(), StarfireParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.STARFIRE_EXPLOSION.get(), StarfireExplosionParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.STARFIRE_EXPLOSION_SMALL.get(), StarfireExplosionParticle.SmallProvider::new);
      strategy.register((ParticleType)ESParticles.SOUL_TRAIL.get(), SoulTrailParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.CANDLASH_TRAIL.get(), StarfireExplosionParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.ETHER_TRAIL.get(), TrailParticle.Provider::new);
      strategy.register(ESParticles.GEYSER.get(), GeyserEruptionParticle.Provider::new);
      strategy.register(ESParticles.GEYSER_BASE.get(), GeyserBaseParticle.Provider::new);
      strategy.register(ESParticles.GEYSER_POOF.get(), GeyserBaseParticle.Provider::new);
      strategy.register(ESParticles.GEYSER_PLUME.get(), GeyserPlumeParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.ADVANCED_GLOW.get(), AdvancedParticle.Provider::new);
      strategy.register((ParticleType)ESParticles.SHINE.get(), AdvancedParticle.Provider::new);
   }

   public static void registerEntityRenderers(ESClientSetupHandler.EntityRendererRegisterStrategy strategy) {
      strategy.register(ESEntities.FALLING_BLOCK.get(), ESFallingBlockRenderer::new);
      strategy.register(ESEntities.PAINTING.get(), PaintingRenderer::new);
      strategy.register(ESEntities.AETHERSENT_METEOR.get(), AethersentMeteorRenderer::new);
      strategy.register(ESEntities.AETHERSTRIKE_ROCKET.get(), AetherstrikeRocketRenderer::new);
      strategy.register(ESEntities.BOAT.get(), context -> new ESBoatRenderer(context, false));
      strategy.register(ESEntities.CHEST_BOAT.get(), context -> new ESBoatRenderer(context, true));
      strategy.register(ESEntities.EYE_OF_SEEKING.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.CREST.get(), EmptyRenderer::new);
      strategy.register(ESEntities.BOARWARF.get(), BoarwarfRenderer::new);
      strategy.register(ESEntities.ASTRAL_GOLEM.get(), AstralGolemRenderer::new);
      strategy.register(ESEntities.GLEECH.get(), GleechRenderer::new);
      strategy.register(ESEntities.GLEECH_EGG.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.LONESTAR_SKELETON.get(), LonestarSkeletonRenderer::new);
      strategy.register(ESEntities.NIGHTFALL_SPIDER.get(), NightfallSpiderRenderer::new);
      strategy.register(ESEntities.SEEKER.get(), SeekerRenderer::new);
      strategy.register(ESEntities.THIRST_WALKER.get(), ThirstWalkerRenderer::new);
      strategy.register(ESEntities.CRETEOR.get(), CreteorRenderer::new);
      strategy.register(ESEntities.TINY_CRETEOR.get(), TinyCreteorRenderer::new);
      strategy.register(ESEntities.STRANGHOUL.get(), StranghoulRenderer::new);
      strategy.register(ESEntities.ENT.get(), EntRenderer::new);
      strategy.register(ESEntities.RATLIN.get(), RatlinRenderer::new);
      strategy.register(ESEntities.ZOMBIFIED_RATLIN.get(), RatlinRenderer::new);
      strategy.register(ESEntities.SHADOW_SNAIL.get(), ShadowSnailRenderer::new);
      strategy.register(ESEntities.YETI.get(), YetiRenderer::new);
      strategy.register(ESEntities.AURORA_DEER.get(), AuroraDeerRenderer::new);
      strategy.register(ESEntities.CRYSTALLIZED_MOTH.get(), CrystallizedMothRenderer::new);
      strategy.register(ESEntities.SHIMMER_LACEWING.get(), ShimmerLacewingRenderer::new);
      strategy.register(ESEntities.STARFIRE_BIRD.get(), StarfireBirdRenderer::new);
      strategy.register(ESEntities.GRIMSTONE_GOLEM.get(), GrimstoneGolemRenderer::new);
      strategy.register(ESEntities.AETHERSENT_GOLEM.get(), AethersentGolemRenderer::new);
      strategy.register(ESEntities.ROOKFISH.get(), RookfishRenderer::new);
      strategy.register(ESEntities.LUMINOFISH.get(), LuminoFishRenderer::new);
      strategy.register(ESEntities.LUMINARIS.get(), LuminarisRenderer::new);
      strategy.register(ESEntities.TWILIGHT_GAZE.get(), TwilightGazeRenderer::new);
      strategy.register(ESEntities.THE_GATEKEEPER.get(), TheGatekeeperRenderer::new);
      strategy.register(ESEntities.GATEKEEPER_FIREBALL.get(), GatekeeperFireballRenderer::new);
      strategy.register(ESEntities.STARLIGHT_GOLEM.get(), StarlightGolemRenderer::new);
      strategy.register(ESEntities.GOLEM_LASER_BEAM.get(), StarlightGolemBeamRenderer::new);
      strategy.register(ESEntities.ENERGIZED_FLAME.get(), EmptyRenderer::new);
      strategy.register(ESEntities.FREEZE.get(), FreezeRenderer::new);
      strategy.register(ESEntities.FROZEN_TUBE.get(), FrozenTubeRenderer::new);
      strategy.register(ESEntities.PERMAFROST.get(), PermafrostRenderer::new);
      strategy.register(ESEntities.PERMAFROST_SPIT.get(), PermafrostSpitRenderer::new);
      strategy.register(ESEntities.PERMAFROST_CLOUD.get(), EmptyRenderer::new);
      strategy.register(ESEntities.COLDSNAP.get(), ColdsnapRenderer::new);
      strategy.register(ESEntities.LUNAR_MONSTROSITY.get(), LunarMonstrosityRenderer::new);
      strategy.register(ESEntities.LUNAR_MONSTROSITY_BREATH.get(), EmptyRenderer::new);
      strategy.register(ESEntities.LUNAR_SPORE.get(), LunarSporeRenderer::new);
      strategy.register(ESEntities.LUNAR_THORN.get(), LunarThornRenderer::new);
      strategy.register(ESEntities.POISONOUS_CLOUD.get(), EmptyRenderer::new);
      strategy.register(ESEntities.TANGLED.get(), TangledRenderer::new);
      strategy.register(ESEntities.TANGLED_SKULL.get(), TangledSkullRenderer::new);
      strategy.register(ESEntities.SOLAR_CREEPER.get(), SolarCreeperRenderer::new);
      strategy.register(ESEntities.TANGLED_HUSK.get(), TangledHuskRenderer::new);
      strategy.register(ESEntities.SHATTERED_BLADE.get(), ThrownShatteredBladeRenderer::new);
      strategy.register(ESEntities.MALARITE_SPEAR.get(), ThrownMalariteSpearRenderer::new);
      strategy.register(ESEntities.PUNGENCY_FRUIT_SPEAR.get(), ThrownPungencyFruitSpearRenderer::new);
      strategy.register(ESEntities.TEAR_BOMB.get(), TearBombRenderer::new);
      strategy.register(ESEntities.TEAR_BOMB_MINECART.get(), TearBombMinecartRenderer::new);
      strategy.register(ESEntities.THIOQUARTZ_ARROW.get(), ThioquartzArrowRenderer::new);
      strategy.register(ESEntities.THIOQUARTZ_SHARD.get(), ThioquartzShardRenderer::new);
      strategy.register(ESEntities.AETHERSENT_ARROW.get(), AethersentArrowRenderer::new);
      strategy.register(ESEntities.GLACITE_ARROW.get(), GlaciteArrowRenderer::new);
      strategy.register(ESEntities.MALARITE_ARROW.get(), MalariteArrowRenderer::new);
      strategy.register(ESEntities.AMARAMBER_ARROW.get(), AmaramberArrowRenderer::new);
      strategy.register(ESEntities.VORACIOUS_ARROW.get(), VoraciousArrowRenderer::new);
      strategy.register(ESEntities.AIR_SAC_ARROW.get(), AirSacArrowRenderer::new);
      strategy.register(ESEntities.SONAR_BOMB.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.ASHEN_SNOWBALL.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.FROZEN_BOMB.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.WILTED_PETAL.get(), WiltedPetalRenderer::new);
      strategy.register(ESEntities.SHOT_SEEDS.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.STARFIRE.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.CANDLASH.get(), CandlashRenderer::new);
      strategy.register(ESEntities.TENTACLE_SPIKE.get(), TentacleSpikeRenderer::new);
      strategy.register(ESEntities.ENERGY_SPARK.get(), EnergySparkRenderer::new);
      strategy.register(ESEntities.BALL_LIGHTNING.get(), BallLightningRenderer::new);
      strategy.register(ESEntities.CRYSTAL_CLUSTER.get(), CrystalClusterRenderer::new);
      strategy.register(ESEntities.ENERGY_BOOMERANG.get(), ThrownBoomerangRenderer::new);
      strategy.register(ESEntities.SOULIT_SPECTATOR.get(), ThrownItemRenderer::new);
      strategy.register(ESEntities.CHAIN_OF_SOULS.get(), ChainOfSoulsRenderer::new);
   }

   public static void registerBlockEntityRenderers(ESClientSetupHandler.BlockEntityRendererRegisterStrategy strategy) {
      strategy.register(ESBlockEntities.SIGN.get(), SignRenderer::new);
      strategy.register(ESBlockEntities.HANGING_SIGN.get(), HangingSignRenderer::new);
      strategy.register(ESBlockEntities.TORREYA_CAMPFIRE.get(), CampfireRenderer::new);
      strategy.register(ESBlockEntities.DRYING_RACK.get(), DryingRackRenderer::new);
      strategy.register(ESBlockEntities.STARFIRE_BIRD_NEST.get(), StarfireBirdNestRenderer::new);
      strategy.register(ESBlockEntities.SKULL.get(), SkullBlockRenderer::new);
      strategy.register(ESBlockEntities.BRUSHABLE_BLOCK.get(), BrushableBlockRenderer::new);
      strategy.register(ESBlockEntities.LOOT_CHEST.get(), LootChestRenderer::new);
      strategy.register(ESBlockEntities.ENERGY_TRANSMITTER.get(), EnergyTransmitterRenderer::new);
      strategy.register(ESBlockEntities.MECHANICAL_SPAWNER.get(), MechanicalSpawnerRenderer::new);
      strategy.register(ESBlockEntities.ALLOY_FURNACE.get(), AlloyFurnaceRenderer::new);
      strategy.register(ESBlockEntities.SOLAR_EGG.get(), SolarEggRenderer::new);
      strategy.register(ESBlockEntities.LUNAR_VINE.get(), LunarVineRenderer::new);
      strategy.register(ESBlockEntities.DUSK_LIGHT.get(), DuskLightRenderer::new);
      strategy.register(ESBlockEntities.DUSK_EMITTER.get(), DuskLightRenderer::new);
      strategy.register(ESBlockEntities.FLARE_SPAWNER.get(), FlareSpawnerRenderer::new);
      strategy.register(ESBlockEntities.ECLIPSE_CORE.get(), EclipseCoreRenderer::new);
      strategy.register(ESBlockEntities.STELLAR_RACK.get(), StellarRackRenderer::new);
      strategy.register(ESBlockEntities.STARLIGHT_PORTAL.get(), ESPortalRenderer::new);
   }

   public static void registerSkullModels(ESClientSetupHandler.SkullRendererRegisterStrategy strategy, EntityModelSet modelSet) {
      strategy.register(ESSkullType.TANGLED, new TangledHeadModel(modelSet.bakeLayer(TangledHeadModel.LAYER_LOCATION)));
   }

   public static void registerLayers(ESClientSetupHandler.ModelLayerRegisterStrategy strategy) {
      strategy.register(
         ThermalSpringStoneArmorModel.INNER_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(ThermalSpringStoneArmorModel.OUTER_LOCATION, () -> ThermalSpringStoneArmorModel.createArmorLayer(OUTER_ARMOR_DEFORMATION));
      strategy.register(AlchemistArmorModel.LAYER_LOCATION, AlchemistArmorModel::createBodyLayer);
      strategy.register(
         StarlitDiamondArmorModel.INNER_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(StarlitDiamondArmorModel.OUTER_LOCATION, () -> StarlitDiamondArmorModel.createArmorLayer(OUTER_ARMOR_DEFORMATION));
      strategy.register(UnrealiumArmorModel.INNER_LOCATION, UnrealiumArmorModel::createInnerArmorLayer);
      strategy.register(UnrealiumArmorModel.OUTER_LOCATION, UnrealiumArmorModel::createOuterArmorLayer);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.LUNAR), BoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.LUNAR), ChestBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.NORTHLAND), BoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.NORTHLAND), ChestBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.BANYIN), BoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.BANYIN), ChestBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.SCARLET), ScarletBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.SCARLET), ScarletChestBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.TORREYA), BoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.TORREYA), ChestBoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.JINGLESTEM), RaftModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.JINGLESTEM), ChestRaftModel::createBodyModel);
      strategy.register(ESBoatRenderer.createBoatModelName(ESBoat.Type.CRADLEWOOD), BoatModel::createBodyModel);
      strategy.register(ESBoatRenderer.createChestBoatModelName(ESBoat.Type.CRADLEWOOD), ChestBoatModel::createBodyModel);
      strategy.register(AethersentMeteorModel.LAYER_LOCATION, AethersentMeteorModel::createBodyLayer);
      strategy.register(BoarwarfModel.LAYER_LOCATION, BoarwarfModel::createBodyLayer);
      strategy.register(BoarwarfBlacksmithModel.LAYER_LOCATION, BoarwarfBlacksmithModel::createBodyLayer);
      strategy.register(BoarwarfChefModel.LAYER_LOCATION, BoarwarfChefModel::createBodyLayer);
      strategy.register(BoarwarfDruidModel.LAYER_LOCATION, BoarwarfDruidModel::createBodyLayer);
      strategy.register(BoarwarfDyerModel.LAYER_LOCATION, BoarwarfDyerModel::createBodyLayer);
      strategy.register(BoarwarfSilversmithModel.LAYER_LOCATION, BoarwarfSilversmithModel::createBodyLayer);
      strategy.register(AstralGolemModel.LAYER_LOCATION, AstralGolemModel::createBodyLayer);
      strategy.register(
         AstralGolemModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(
         AstralGolemModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(GleechModel.LAYER_LOCATION, GleechModel::createBodyLayer);
      strategy.register(LonestarSkeletonRenderer.LONESTAR, LonestarSkeletonModel::createBodyLayer);
      strategy.register(
         LonestarSkeletonRenderer.LONESTAR_INNER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(
         LonestarSkeletonRenderer.LONESTAR_OUTER_ARMOR, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32)
      );
      strategy.register(NightfallSpiderModel.LAYER_LOCATION, NightfallSpiderModel::createBodyLayer);
      strategy.register(SeekerModel.LAYER_LOCATION, SeekerModel::createBodyLayer);
      strategy.register(ThirstWalkerModel.LAYER_LOCATION, ThirstWalkerModel::createBodyLayer);
      strategy.register(CreteorModel.LAYER_LOCATION, () -> CreteorModel.createBodyLayer(CubeDeformation.NONE));
      strategy.register(CreteorModel.ARMOR_LOCATION, () -> CreteorModel.createBodyLayer(new CubeDeformation(2.0F)));
      strategy.register(TinyCreteorModel.LAYER_LOCATION, () -> TinyCreteorModel.createBodyLayer(CubeDeformation.NONE));
      strategy.register(TinyCreteorModel.ARMOR_LOCATION, () -> TinyCreteorModel.createBodyLayer(new CubeDeformation(2.0F)));
      strategy.register(StranghoulModel.LAYER_LOCATION, StranghoulModel::createBodyLayer);
      strategy.register(StranghoulModel.INNER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(INNER_ARMOR_DEFORMATION), 64, 32));
      strategy.register(StranghoulModel.OUTER_ARMOR_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(OUTER_ARMOR_DEFORMATION), 64, 32));
      strategy.register(EntModel.LAYER_LOCATION, EntModel::createBodyLayer);
      strategy.register(RatlinModel.LAYER_LOCATION, RatlinModel::createBodyLayer);
      strategy.register(ShadowSnailModel.LAYER_LOCATION, ShadowSnailModel::createBodyLayer);
      strategy.register(YetiModel.LAYER_LOCATION, YetiModel::createBodyLayer);
      strategy.register(AuroraDeerModel.LAYER_LOCATION, AuroraDeerModel::createBodyLayer);
      strategy.register(CrystallizedMothModel.LAYER_LOCATION, CrystallizedMothModel::createBodyLayer);
      strategy.register(ShimmerLacewingModel.LAYER_LOCATION, ShimmerLacewingModel::createBodyLayer);
      strategy.register(StarfireBirdModel.ADULT_LOCATION, StarfireBirdModel::createBodyLayer);
      strategy.register(StarfireBirdModel.BABY_LOCATION, StarfireBirdModel::createBabyBodyLayer);
      strategy.register(GrimstoneGolemModel.LAYER_LOCATION, GrimstoneGolemModel::createBodyLayer);
      strategy.register(AethersentGolemModel.LAYER_LOCATION, AethersentGolemModel::createBodyLayer);
      strategy.register(RookfishModel.LAYER_LOCATION, RookfishModel::createBodyLayer);
      strategy.register(LuminoFishModel.LAYER_LOCATION, LuminoFishModel::createBodyLayer);
      strategy.register(LuminarisModel.LAYER_LOCATION, LuminarisModel::createBodyLayer);
      strategy.register(TwilightGazeModel.LAYER_LOCATION, TwilightGazeModel::createBodyLayer);
      strategy.register(CandlashModel.LAYER_LOCATION, CandlashModel::createBodyLayer);
      strategy.register(TentacleSpikeModel.LAYER_LOCATION, TentacleSpikeModel::createBodyLayer);
      strategy.register(TheGatekeeperModel.LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false));
      strategy.register(TheGatekeeperModel.SLIM_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true));
      strategy.register(TheGatekeeperModel.OUTER_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(false, 0.5F));
      strategy.register(TheGatekeeperModel.SLIM_OUTER_LAYER_LOCATION, () -> TheGatekeeperModel.createBodyLayer(true, 0.5F));
      strategy.register(StarlightGolemModel.LAYER_LOCATION, StarlightGolemModel::createBodyLayer);
      strategy.register(FreezeModel.LAYER_LOCATION, FreezeModel::createBodyLayer);
      strategy.register(PermafrostModel.LAYER_LOCATION, PermafrostModel::createBodyLayer);
      strategy.register(PermafrostSpitModel.LAYER_LOCATION, PermafrostSpitModel::createBodyLayer);
      strategy.register(PermafrostSpitModel.SMALL_LAYER_LOCATION, PermafrostSpitModel::createSmallLayer);
      strategy.register(FrozenTubeModel.LAYER_LOCATION, FrozenTubeModel::createBodyLayer);
      strategy.register(ColdsnapModel.LAYER_LOCATION, ColdsnapModel::createBodyLayer);
      strategy.register(LunarMonstrosityModel.LAYER_LOCATION, LunarMonstrosityModel::createBodyLayer);
      strategy.register(LunarSporeModel.LAYER_LOCATION, LunarSporeModel::createBodyLayer);
      strategy.register(LunarThornModel.LAYER_LOCATION, LunarThornModel::createBodyLayer);
      strategy.register(TangledModel.LAYER_LOCATION, TangledModel::createBodyLayer);
      strategy.register(TangledSkullModel.LAYER_LOCATION, TangledSkullModel::createBodyLayer);
      strategy.register(TangledHeadModel.LAYER_LOCATION, TangledHeadModel::createBodyLayer);
      strategy.register(SolarCreeperModel.LAYER_LOCATION, SolarCreeperModel::createBodyLayer);
      strategy.register(OrbModel.LAYER_LOCATION, OrbModel::createBodyLayer);
      strategy.register(TearBombMinecartRenderer.LAYER_LOCATION, MinecartModel::createBodyLayer);
      strategy.register(ChainOfSoulsModel.LAYER_LOCATION, ChainOfSoulsModel::createBodyLayer);
      strategy.register(
         ArmorLikeAccessoryLayer.INNER_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.0F)), 64, 32)
      );
      strategy.register(
         ArmorLikeAccessoryLayer.OUTER_LOCATION, () -> LayerDefinition.create(HumanoidArmorModel.createBodyLayer(new CubeDeformation(1.5F)), 64, 32)
      );
      strategy.register(GlaciteShieldModel.LAYER_LOCATION, GlaciteShieldModel::createBodyLayer);
      strategy.register(FlowglazeShieldModel.LAYER_LOCATION, FlowglazeShieldModel::createBodyLayer);
      strategy.register(MalariteSpearModel.LAYER_LOCATION, MalariteSpearModel::createBodyLayer);
      strategy.register(PungencyFruitSpearModel.LAYER_LOCATION, PungencyFruitSpearModel::createBodyLayer);
      strategy.register(CrescentSpearModel.LAYER_LOCATION, CrescentSpearModel::createBodyLayer);
      strategy.register(MechanicalSpawnerRenderer.MechanicalSpawnerModel.LAYER_LOCATION, MechanicalSpawnerRenderer.MechanicalSpawnerModel::createLayer);
      strategy.register(LootChestRenderer.LootChestModel.LAYER_LOCATION, LootChestRenderer.LootChestModel::createLayer);
      strategy.register(AlloyFurnaceRenderer.AlloyFurnaceModel.LAYER_LOCATION, AlloyFurnaceRenderer.AlloyFurnaceModel::createLayer);
      strategy.register(SolarEggRenderer.SolarEggModel.LAYER_LOCATION, SolarEggRenderer.SolarEggModel::createLayer);
      strategy.register(LunarVineRenderer.VineModel.LAYER_LOCATION, LunarVineRenderer.VineModel::createLayer);
      strategy.register(LunarVineRenderer.FlowerModel.LAYER_LOCATION, LunarVineRenderer.FlowerModel::createLayer);
   }

   public static void registerMenuScreens(ESClientSetupHandler.MenuScreenRegisterStrategy strategy) {
      strategy.register(ESMenuTypes.CRATE.get(), CrateScreen::new);
      strategy.register(ESMenuTypes.CRYSTALBORN_CATALYST.get(), CrystalbornCatalystScreen::new);
      strategy.register(ESMenuTypes.ALLOY_FURNACE.get(), AlloyFurnaceScreen::new);
   }

   public static void addClientReloadListeners(Consumer<PreparableReloadListener> strategy) {
      ESClientHandler.books = ESClientPlatform.INSTANCE.createBookLoader();
      strategy.accept(ESClientHandler.books);
   }

   public static void onRenderLayerAttachment(EntityType<?> entityType, LivingEntityRenderer<?, ?> renderer, Context context) {
      try {
         if (renderer.getModel() instanceof HumanoidModel) {
            renderer.addLayer(
               new ButterflyWingsAmuletLayer(
                  (RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>>)renderer,
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.INNER_LOCATION)),
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.OUTER_LOCATION))
               )
            );
            renderer.addLayer(
               new FungusAmuletLayer(
                  (RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>>)renderer,
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.INNER_LOCATION)),
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.OUTER_LOCATION))
               )
            );
            renderer.addLayer(
               new PearlNecklaceLayer(
                  (RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>>)renderer,
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.INNER_LOCATION)),
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.OUTER_LOCATION))
               )
            );
            renderer.addLayer(
               new CrescentPendantLayer(
                  (RenderLayerParent<LivingEntity, HumanoidModel<LivingEntity>>)renderer,
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.INNER_LOCATION)),
                  new HumanoidArmorModel(context.bakeLayer(ArmorLikeAccessoryLayer.OUTER_LOCATION))
               )
            );
         }
      } catch (ClassCastException var4) {
      }
   }

   public interface BlockColorRegisterStrategy {
      void register(BlockColor var1, Block... var2);
   }

   public interface BlockEntityRendererRegisterStrategy {
      <T extends BlockEntity> void register(BlockEntityType<? extends T> var1, BlockEntityRendererProvider<T> var2);
   }

   public interface EntityRendererRegisterStrategy {
      <T extends Entity> void register(EntityType<? extends T> var1, EntityRendererProvider<T> var2);
   }

   public interface ItemColorRegisterStrategy {
      void register(ItemColor var1, ItemLike... var2);
   }

   public interface MenuScreenRegisterStrategy {
      <M extends AbstractContainerMenu, U extends Screen & MenuAccess<M>> void register(MenuType<? extends M> var1, ScreenConstructor<M, U> var2);
   }

   public interface ModelLayerRegisterStrategy {
      void register(ModelLayerLocation var1, Supplier<LayerDefinition> var2);
   }

   public interface ParticleProviderRegisterStrategy {
      <T extends ParticleOptions> void register(ParticleType<T> var1, SpriteParticleRegistration<T> var2);
   }

   public interface ShaderRegisterStrategy {
      void register(ResourceLocation var1, VertexFormat var2, Consumer<ShaderInstance> var3);
   }

   public interface SkullRendererRegisterStrategy {
      void register(net.minecraft.world.level.block.SkullBlock.Type var1, SkullModelBase var2);
   }

   public interface WorldVisualEffectSpawnFunction {
      void clientTick(ClientLevel var1, List<WorldVisualEffect> var2);
   }
}
