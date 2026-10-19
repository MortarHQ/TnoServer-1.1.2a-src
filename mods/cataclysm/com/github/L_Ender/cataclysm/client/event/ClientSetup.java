package com.github.L_Ender.cataclysm.client.event;

import com.github.L_Ender.cataclysm.Cataclysm;
import com.github.L_Ender.cataclysm.blocks.Cataclysm_Skull_Block;
import com.github.L_Ender.cataclysm.client.gui.GUIWeponfusion;
import com.github.L_Ender.cataclysm.client.model.CMModelLayers;
import com.github.L_Ender.cataclysm.client.model.block.AptrgangrHeadModel;
import com.github.L_Ender.cataclysm.client.model.block.DraugrHeadModel;
import com.github.L_Ender.cataclysm.client.model.block.KobolediatorHeadModel;
import com.github.L_Ender.cataclysm.client.particle.AfterImageParticle;
import com.github.L_Ender.cataclysm.client.particle.Amethyst_Crush_Particle;
import com.github.L_Ender.cataclysm.client.particle.CircleLightningParticle;
import com.github.L_Ender.cataclysm.client.particle.Circle_Particle;
import com.github.L_Ender.cataclysm.client.particle.CursedAlgizParticle;
import com.github.L_Ender.cataclysm.client.particle.CursedFlameParticle;
import com.github.L_Ender.cataclysm.client.particle.CursedMarkParticle;
import com.github.L_Ender.cataclysm.client.particle.CustomExplodeParticle;
import com.github.L_Ender.cataclysm.client.particle.Custom_Poof_Particle;
import com.github.L_Ender.cataclysm.client.particle.Desert_Glyph_Particle;
import com.github.L_Ender.cataclysm.client.particle.Dust_Blast_Particle;
import com.github.L_Ender.cataclysm.client.particle.EM_PulseParticle;
import com.github.L_Ender.cataclysm.client.particle.FlameJetParticle;
import com.github.L_Ender.cataclysm.client.particle.Gathering_Water_Particle;
import com.github.L_Ender.cataclysm.client.particle.Ignis_Soul_Swing_Particle;
import com.github.L_Ender.cataclysm.client.particle.Ignis_Swing_Particle;
import com.github.L_Ender.cataclysm.client.particle.LightTrailParticle;
import com.github.L_Ender.cataclysm.client.particle.LightningExplodeParticle;
import com.github.L_Ender.cataclysm.client.particle.LightningParticle;
import com.github.L_Ender.cataclysm.client.particle.LightningStormParticle;
import com.github.L_Ender.cataclysm.client.particle.Lightning_Zap_Particle;
import com.github.L_Ender.cataclysm.client.particle.Not_Spin_TrailParticle;
import com.github.L_Ender.cataclysm.client.particle.ParryParticle;
import com.github.L_Ender.cataclysm.client.particle.Phantom_Emitter_Particle;
import com.github.L_Ender.cataclysm.client.particle.Phantom_Wing_FlameParticle;
import com.github.L_Ender.cataclysm.client.particle.RainCloudParticle;
import com.github.L_Ender.cataclysm.client.particle.Rain_Fog_Particle;
import com.github.L_Ender.cataclysm.client.particle.RingParticle;
import com.github.L_Ender.cataclysm.client.particle.Rising_Trail_Particle;
import com.github.L_Ender.cataclysm.client.particle.RoarParticle;
import com.github.L_Ender.cataclysm.client.particle.Scylla_Swing_Particle;
import com.github.L_Ender.cataclysm.client.particle.Shock_WaveParticle;
import com.github.L_Ender.cataclysm.client.particle.SoulLavaParticle;
import com.github.L_Ender.cataclysm.client.particle.SparkParticle;
import com.github.L_Ender.cataclysm.client.particle.SparkTrailParticle;
import com.github.L_Ender.cataclysm.client.particle.SpinTrailParticle;
import com.github.L_Ender.cataclysm.client.particle.StormParticle;
import com.github.L_Ender.cataclysm.client.particle.TrackLightningParticle;
import com.github.L_Ender.cataclysm.client.particle.TrapFlameParticle;
import com.github.L_Ender.cataclysm.client.render.CMItemstackRenderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Boss_Respawn_Spawn_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Cursed_Tombstone_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Door_Of_Seal_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.Goddess_Statue_Renderer;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAbyssal_Egg;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Abyss;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Amethyst;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Fire;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererAltar_of_Void;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererEMP;
import com.github.L_Ender.cataclysm.client.render.blockentity.RendererMechanical_fusion_anvil;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Blast_Portal_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Mark_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Mine_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Orb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Abyss_Portal_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Accretion_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Amethyst_Cluster_Projectile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Amethyst_Crab_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Desert_Stele_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ancient_Remnant_Rework_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Aptrgangr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Axe_Blade_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Blazing_Bone_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Boltstrike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Brontes_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Cindaria_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Clawdian_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Cm_Falling_Block_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Coral_Golem_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Coralssus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Cursed_Sandstorm_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Death_Laser_beam_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Angler_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Brute_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Priest_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Deepling_Warlock_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Dimensional_Rift_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Drowned_Host_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Elite_Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Golem_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Guardian_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ender_Guardian_bullet_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Endermaptera_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Eye_Of_Dungeon_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Flame_Strike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Flare_Bomb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Hippocamtus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Abyss_Fireball_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Fireball_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignis_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignited_Berserker_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Ignited_Revenant_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Kobolediator_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Koboleton_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Laser_Beam_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lava_Bomb_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lightning_Spear_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lionfish_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Lionfish_Spike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Maledictus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Mini_Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Modern_Remnant_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Netherite_Ministrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.New_Netherite_Monstrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Octo_Ink_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Phantom_Arrow_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Phantom_Halberd_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Player_Ceraunus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Poison_Dart_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Portal_Abyss_Blast_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.RendererNull;
import com.github.L_Ender.cataclysm.client.render.entity.Royal_Draugr_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Sandstorm_Projectile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Sandstorm_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Scylla_Ceraunus_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Scylla_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Storm_Serpent_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Symbiocto_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Baby_Leviathan_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Harbinger_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Leviathan_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Prowler_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.The_Watcher_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Thrown_Coral_Bardiche_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Thrown_Coral_Spear_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Tidal_Hook_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Tidal_Tentacle_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Urchin_Spike_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Urchinkin_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Howitzer_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Rune_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Scatter_Arrow_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Void_Vortex_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wadjet_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Water_Spear_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wave_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Homing_Missile_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Howitzer_Renderer;
import com.github.L_Ender.cataclysm.client.render.entity.Wither_Missile_Renderer;
import com.github.L_Ender.cataclysm.client.render.etc.CurioHeadRenderer;
import com.github.L_Ender.cataclysm.client.render.item.CustomArmorRenderProperties;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Belt_Of_Beginner_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Belt_Of_Monstrosity_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Berserker_Soul_Amulet_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Blazing_Grips_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Chitin_Claw_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Sticky_Gloves_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Sturdy_Boots_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Unbreakable_Skull_Renderer;
import com.github.L_Ender.cataclysm.client.render.item.CuriosRenderer.Vitality_Ankh_Renderer;
import com.github.L_Ender.cataclysm.init.ModDataComponents;
import com.github.L_Ender.cataclysm.init.ModEntities;
import com.github.L_Ender.cataclysm.init.ModItems;
import com.github.L_Ender.cataclysm.init.ModKeybind;
import com.github.L_Ender.cataclysm.init.ModMenu;
import com.github.L_Ender.cataclysm.init.ModParticle;
import com.github.L_Ender.cataclysm.init.ModTileentites;
import net.minecraft.client.renderer.blockentity.SkullBlockRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.CreateSkullModels;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

public class ClientSetup {
   public static void ClientSetupevent(IEventBus bus) {
      bus.addListener(ClientSetup::registerScreens);
      bus.addListener(ClientSetup::registerEntityRenderers);
      bus.addListener(ClientSetup::registerParticleFactories);
      bus.addListener(ClientSetup::registerClientExtensions);
      bus.addListener(ClientSetup::doClientStuff);
      bus.addListener(ClientSetup::createSkullModels);
      bus.addListener(ClientSetup::registerKeybinds);
      bus.addListener(ClientSetup::registerGuiLayers);
   }

   private static void registerScreens(RegisterMenuScreensEvent event) {
      event.register((MenuType)ModMenu.WEAPON_FUSION.get(), GUIWeponfusion::new);
   }

   private static void registerKeybinds(RegisterKeyMappingsEvent event) {
      event.register(ModKeybind.KEY_ABILITY);
      event.register(ModKeybind.HELMET_KEY_ABILITY);
      event.register(ModKeybind.CHESTPLATE_KEY_ABILITY);
      event.register(ModKeybind.BOOTS_KEY_ABILITY);
   }

   private static void registerGuiLayers(RegisterGuiLayersEvent event) {
   }

   private static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)ModEntities.ENDER_GOLEM.get(), Ender_Golem_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.NETHERITE_MONSTROSITY.get(), New_Netherite_Monstrosity_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.NETHERITE_MINISTROSITY.get(), Netherite_Ministrosity_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LAVA_BOMB.get(), Lava_Bomb_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.FLARE_BOMB.get(), Flare_Bomb_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.FLAME_JET.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIGHTNING_STORM.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.IGNIS.get(), Ignis_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENDER_GUARDIAN.get(), Ender_Guardian_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENDER_GUARDIAN_BULLET.get(), Ender_Guardian_bullet_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.VOID_RUNE.get(), Void_Rune_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ENDERMAPTERA.get(), Endermaptera_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.IGNITED_REVENANT.get(), Ignited_Revenant_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.IGNITED_BERSERKER.get(), Ignited_Berserker_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_HARBINGER.get(), The_Harbinger_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.VOID_SCATTER_ARROW.get(), Void_Scatter_Arrow_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.POISON_DART.get(), Poison_Dart_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PHANTOM_ARROW.get(), Phantom_Arrow_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SCREEN_SHAKE.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.SKY_COLOR.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.WITHER_SMOKE_EFFECT.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIGHTNING_AREA_EFFECT.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.ASHEN_BREATH.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.WALL_WATCHER.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.FLAME_STRIKE.get(), Flame_Strike_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BOLT_STRIKE.get(), Boltstrike_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CM_FALLING_BLOCK.get(), Cm_Falling_Block_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.IGNIS_FIREBALL.get(), Ignis_Fireball_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.IGNIS_ABYSS_FIREBALL.get(), Ignis_Abyss_Fireball_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEATH_LASER_BEAM.get(), Death_Laser_beam_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_BLAST.get(), Abyss_Blast_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.MINI_ABYSS_BLAST.get(), Mini_Abyss_Blast_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LASER_BEAM.get(), Laser_Beam_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WITHER_MISSILE.get(), Wither_Missile_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WITHER_HOMING_MISSILE.get(), Wither_Homing_Missile_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WITHER_HOWITZER.get(), Wither_Howitzer_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.VOID_HOWITZER.get(), Void_Howitzer_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.VOID_VORTEX.get(), Void_Vortex_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_LEVIATHAN.get(), The_Leviathan_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_BABY_LEVIATHAN.get(), The_Baby_Leviathan_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_PORTAL.get(), Abyss_Portal_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_ORB.get(), Abyss_Orb_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_BLAST_PORTAL.get(), Abyss_Blast_Portal_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PORTAL_ABYSS_BLAST.get(), Portal_Abyss_Blast_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEEPLING.get(), Deepling_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_MINE.get(), Abyss_Mine_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CORAL_SPEAR.get(), Thrown_Coral_Spear_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CORAL_BARDICHE.get(), Thrown_Coral_Bardiche_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEEPLING_BRUTE.get(), Deepling_Brute_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEEPLING_PRIEST.get(), Deepling_Priest_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DIMENSIONAL_RIFT.get(), Dimensional_Rift_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEEPLING_ANGLER.get(), Deepling_Angler_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DEEPLING_WARLOCK.get(), Deepling_Warlock_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ABYSS_MARK.get(), Abyss_Mark_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CORAL_GOLEM.get(), Coral_Golem_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CORALSSUS.get(), Coralssus_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIONFISH.get(), Lionfish_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIDAL_HOOK.get(), Tidal_Hook_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.AMETHYST_CRAB.get(), Amethyst_Crab_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ANCIENT_REMNANT.get(), Ancient_Remnant_Rework_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.MODERN_REMNANT.get(), Modern_Remnant_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SANDSTORM.get(), Sandstorm_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SANDSTORM_PROJECTILE.get(), Sandstorm_Projectile_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CURSED_SANDSTORM.get(), Cursed_Sandstorm_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_WATCHER.get(), The_Watcher_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_PROWLER.get(), The_Prowler_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.KOBOLETON.get(), Koboleton_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.KOBOLEDIATOR.get(), Kobolediator_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WADJET.get(), Wadjet_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.MALEDICTUS.get(), Maledictus_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DRAUGR.get(), Draugr_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ROYAL_DRAUGR.get(), Royal_Draugr_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ELITE_DRAUGR.get(), Elite_Draugr_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.APTRGANGR.get(), Aptrgangr_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.AXE_BLADE.get(), Axe_Blade_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PHANTOM_HALBERD.get(), Phantom_Halberd_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.EARTHQUAKE.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.ACCRETION.get(), Accretion_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.ANCIENT_DESERT_STELE.get(), Ancient_Desert_Stele_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.AMETHYST_CLUSTER_PROJECTILE.get(), Amethyst_Cluster_Projectile_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.THE_LEVIATHAN_TONGUE.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.VOID_SHARD.get(), render -> new ThrownItemRenderer(render, 0.75F, true));
      event.registerEntityRenderer((EntityType)ModEntities.EYE_OF_DUNGEON.get(), Eye_Of_Dungeon_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BLAZING_BONE.get(), Blazing_Bone_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIONFISH_SPIKE.get(), Lionfish_Spike_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.TIDAL_TENTACLE.get(), Tidal_Tentacle_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.HIPPOCAMTUS.get(), Hippocamtus_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SCYLLA.get(), Scylla_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CLAWDIAN.get(), Clawdian_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.DROWNED_HOST.get(), Drowned_Host_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SYMBIOCTO.get(), Symbiocto_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.OCTO_INK.get(), Octo_Ink_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.URCHINKIN.get(), Urchinkin_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.WATER_SPEAR.get(), Water_Spear_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.LIGHTNING_SPEAR.get(), Lightning_Spear_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.CINDARIA.get(), Cindaria_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SCYLLA_CERAUNUS.get(), Scylla_Ceraunus_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.PLAYER_CERAUNUS.get(), Player_Ceraunus_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.BRONTES.get(), Brontes_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.URCHIN_SPIKE.get(), Urchin_Spike_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.SPARK.get(), RendererNull::new);
      event.registerEntityRenderer((EntityType)ModEntities.WAVE.get(), Wave_Renderer::new);
      event.registerEntityRenderer((EntityType)ModEntities.STORM_SERPENT.get(), Storm_Serpent_Renderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.ALTAR_OF_FIRE.get(), RendererAltar_of_Fire::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.ALTAR_OF_VOID.get(), RendererAltar_of_Void::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.DOOR_OF_SEAL.get(), Door_Of_Seal_Renderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.CURSED_TOMBSTONE.get(), Cursed_Tombstone_Renderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.EMP.get(), RendererEMP::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.MECHANICAL_FUSION_ANVIL.get(), RendererMechanical_fusion_anvil::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.ALTAR_OF_AMETHYST.get(), RendererAltar_of_Amethyst::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.CATACLYSM_SKULL.get(), SkullBlockRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.ALTAR_OF_ABYSS.get(), RendererAltar_of_Abyss::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.ABYSSAL_EGG.get(), RendererAbyssal_Egg::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.BOSS_RESPAWNER.get(), Boss_Respawn_Spawn_Renderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)ModTileentites.GODDESS_STATUE.get(), Goddess_Statue_Renderer::new);
   }

   public static void createSkullModels(CreateSkullModels event) {
      event.registerSkullModel(
         Cataclysm_Skull_Block.Types.KOBOLEDIATOR, new KobolediatorHeadModel(event.getEntityModelSet().bakeLayer(CMModelLayers.KOBOLEDIATOR_HEAD_MODEL))
      );
      event.registerSkullModel(
         Cataclysm_Skull_Block.Types.APTRGANGR, new AptrgangrHeadModel(event.getEntityModelSet().bakeLayer(CMModelLayers.APTRGANGR_HEAD_MODEL))
      );
      event.registerSkullModel(Cataclysm_Skull_Block.Types.DRAUGR, new DraugrHeadModel(event.getEntityModelSet().bakeLayer(CMModelLayers.DRAUGR_HEAD_MODEL)));
   }

   private static void registerParticleFactories(RegisterParticleProvidersEvent registry) {
      registry.registerSpriteSet((ParticleType)ModParticle.SPARK.get(), SparkParticle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.SOUL_LAVA.get(), SoulLavaParticle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.CURSED_FLAME.get(), CursedFlameParticle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.SMALL_CURSED_FLAME.get(), CursedFlameParticle.SmallFlameProvider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.PHANTOM_WING_FLAME.get(), Phantom_Wing_FlameParticle.EmissiveProvider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.CUSTOM_POOF.get(), Custom_Poof_Particle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.LIGHTNING_ZAP.get(), Lightning_Zap_Particle.Provider::new);
      registry.registerSpecial((ParticleType)ModParticle.EM_PULSE.get(), new EM_PulseParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.SHOCK_WAVE.get(), new Shock_WaveParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.LIGHTNING.get(), new LightningParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.SPARK_TRAIL.get(), new SparkTrailParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.TRACK_LIGHTNING.get(), new TrackLightningParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.SPIN_TRAIL_PARTICLE.get(), new SpinTrailParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.CIRCLE_LIGHTNING.get(), new CircleLightningParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.GATHERING_WATER.get(), new Gathering_Water_Particle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.STORM.get(), new StormParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.RISING_TRAIL.get(), new Rising_Trail_Particle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.AFTER_IMAGE.get(), new AfterImageParticle.Factory());
      registry.registerSpriteSet((ParticleType)ModParticle.RING.get(), RingParticle.RingFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.ROAR.get(), RoarParticle.RoarFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.PARRY.get(), ParryParticle.ParryFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.SCYLLA_SWING.get(), Scylla_Swing_Particle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.IGNIS_SWING.get(), Ignis_Swing_Particle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.IGNIS_SOUL_SWING.get(), Ignis_Soul_Swing_Particle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.RAIN_CLOUD.get(), RainCloudParticle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.TRAP_FLAME.get(), TrapFlameParticle.Factory::new);
      registry.registerSpecial((ParticleType)ModParticle.LIGHT_TRAIL.get(), new LightTrailParticle.Factory());
      registry.registerSpecial((ParticleType)ModParticle.NOT_SPIN_PARTICLE.get(), new Not_Spin_TrailParticle.Factory());
      registry.registerSpriteSet((ParticleType)ModParticle.FLAME_JET.get(), FlameJetParticle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.LIGHTNING_STORM.get(), LightningStormParticle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.RAIN_FOG.get(), Rain_Fog_Particle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.FLARE_EXPLODE.get(), CustomExplodeParticle.FlareFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.LIGHTNING_EXPLODE.get(), LightningExplodeParticle.FlareFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.IGNIS_EXPLODE.get(), CustomExplodeParticle.IgnisFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.IGNIS_ABYSS_EXPLODE.get(), CustomExplodeParticle.IgnisFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.IGNIS_SOUL_EXPLODE.get(), CustomExplodeParticle.IgnisFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.DESERT_GLYPH.get(), Desert_Glyph_Particle.GlyphFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.DUST_BLAST.get(), Dust_Blast_Particle.Factory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.PHANTOM_EMITTER.get(), Phantom_Emitter_Particle.Provider::new);
      registry.registerSpriteSet((ParticleType)ModParticle.CIRCLE.get(), Circle_Particle.CircleFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.AMETHYST_CRASH.get(), Amethyst_Crush_Particle.CrashFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.CURSED_MARK.get(), CursedMarkParticle.MarkFactory::new);
      registry.registerSpriteSet((ParticleType)ModParticle.CURSED_ALGIZ.get(), CursedAlgizParticle.AlgizFactory::new);
   }

   private static void doClientStuff(FMLClientSetupEvent event) {
      try {
         ItemProperties.register(
            (Item)ModItems.BULWARK_OF_THE_FLAME.get(),
            ResourceLocation.parse("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.SOUL_RENDER.get(),
            ResourceLocation.parse("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_SPEAR.get(),
            ResourceLocation.parse("throwing"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_BARDICHE.get(),
            ResourceLocation.parse("throwing"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.MEAT_SHREDDER.get(),
            ResourceLocation.parse("using"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CORAL_CHUNK.get(),
            ResourceLocation.parse("chunk"),
            (stack, level, living, j) -> stack.getCount() % 3 == 0 ? 0.0F : (stack.getCount() % 3 == 1 ? 0.5F : 1.0F)
         );
         ItemProperties.register(
            (Item)ModItems.BLACK_STEEL_TARGE.get(),
            ResourceLocation.parse("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.AZURE_SEA_SHIELD.get(),
            ResourceLocation.parse("blocking"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.ASTRAPE.get(),
            ResourceLocation.parse("throwing"),
            (stack, p_239421_1_, p_239421_2_, j) -> p_239421_2_ != null && p_239421_2_.isUsingItem() && p_239421_2_.getUseItem() == stack ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.CERAUNUS.get(),
            ResourceLocation.parse("throwing"),
            (stack, level, entity, idk) -> stack.get(ModDataComponents.THROWN_ANCHOR) != null ? 1.0F : 0.0F
         );
         ItemProperties.register(
            (Item)ModItems.BRONTES.get(),
            ResourceLocation.parse("throwing"),
            (stack, level, entity, idk) -> stack.get(ModDataComponents.THROWN_HAMMER) != null ? 1.0F : 0.0F
         );
      } catch (Exception e) {
         Cataclysm.LOGGER.warn("Could not load item models for weapons");
      }

      CuriosRendererRegistry.register((Item)ModItems.STICKY_GLOVES.get(), Sticky_Gloves_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.BLAZING_GRIPS.get(), Blazing_Grips_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.KOBOLEDIATOR_SKULL.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.APTRGANGR_HEAD.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.DRAUGR_HEAD.get(), CurioHeadRenderer::new);
      CuriosRendererRegistry.register((Item)ModItems.CHITIN_CLAW.get(), Chitin_Claw_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.VITALITY_ANKH.get(), Vitality_Ankh_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.BELT_OF_BEGINNER.get(), Belt_Of_Beginner_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.BELT_OF_MONSTROSITY.get(), Belt_Of_Monstrosity_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.UNBREAKABLE_SKULL.get(), Unbreakable_Skull_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.BERSERKER_SOUL_AMULET.get(), Berserker_Soul_Amulet_Renderer::new);
      CuriosRendererRegistry.register((Item)ModItems.STURDY_BOOTS.get(), Sturdy_Boots_Renderer::new);
      SkullBlockRenderer.SKIN_BY_TYPE
         .put(Cataclysm_Skull_Block.Types.KOBOLEDIATOR, ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/koboleton/kobolediator.png"));
      SkullBlockRenderer.SKIN_BY_TYPE
         .put(Cataclysm_Skull_Block.Types.APTRGANGR, ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/draugar/aptrgangr.png"));
      SkullBlockRenderer.SKIN_BY_TYPE
         .put(Cataclysm_Skull_Block.Types.DRAUGR, ResourceLocation.fromNamespaceAndPath("cataclysm", "textures/entity/draugar/draugr.png"));
   }

   private static void registerClientExtensions(RegisterClientExtensionsEvent event) {
      event.registerItem(
         CustomArmorRenderProperties.INSTANCE.get(),
         new Item[]{
            (Item)ModItems.IGNITIUM_HELMET.get(),
            (Item)ModItems.IGNITIUM_CHESTPLATE.get(),
            (Item)ModItems.IGNITIUM_LEGGINGS.get(),
            (Item)ModItems.IGNITIUM_BOOTS.get()
         }
      );
      event.registerItem(
         CustomArmorRenderProperties.INSTANCE.get(),
         new Item[]{
            (Item)ModItems.CURSIUM_HELMET.get(),
            (Item)ModItems.CURSIUM_CHESTPLATE.get(),
            (Item)ModItems.CURSIUM_LEGGINGS.get(),
            (Item)ModItems.CURSIUM_BOOTS.get()
         }
      );
      event.registerItem(
         CustomArmorRenderProperties.INSTANCE.get(), new Item[]{(Item)ModItems.BONE_REPTILE_HELMET.get(), (Item)ModItems.BONE_REPTILE_CHESTPLATE.get()}
      );
      event.registerItem(CustomArmorRenderProperties.INSTANCE.get(), new Item[]{(Item)ModItems.MONSTROUS_HELM.get()});
      event.registerItem(CustomArmorRenderProperties.INSTANCE.get(), new Item[]{(Item)ModItems.IGNITIUM_ELYTRA_CHESTPLATE.get()});
      event.registerItem(CustomArmorRenderProperties.INSTANCE.get(), new Item[]{(Item)ModItems.BLOOM_STONE_PAULDRONS.get()});
      event.registerItem(
         CMItemstackRenderer.CLIENT_ITEM_EXTENSION,
         new Item[]{
            (Item)ModItems.BULWARK_OF_THE_FLAME.get(),
            (Item)ModItems.BLACK_STEEL_TARGE.get(),
            (Item)ModItems.GAUNTLET_OF_GUARD.get(),
            (Item)ModItems.GAUNTLET_OF_BULWARK.get(),
            (Item)ModItems.GAUNTLET_OF_MAELSTROM.get(),
            (Item)ModItems.THE_INCINERATOR.get(),
            (Item)ModItems.WITHER_ASSULT_SHOULDER_WEAPON.get(),
            (Item)ModItems.VOID_ASSULT_SHOULDER_WEAPON.get(),
            (Item)ModItems.CORAL_SPEAR.get(),
            (Item)ModItems.CORAL_BARDICHE.get(),
            (Item)ModItems.VOID_FORGE.get(),
            (Item)ModItems.INFERNAL_FORGE.get(),
            (Item)ModItems.TIDAL_CLAWS.get(),
            (Item)ModItems.MEAT_SHREDDER.get(),
            (Item)ModItems.LASER_GATLING.get(),
            (Item)ModItems.ANCIENT_SPEAR.get(),
            (Item)ModItems.CURSED_BOW.get(),
            (Item)ModItems.WRATH_OF_THE_DESERT.get(),
            (Item)ModItems.SOUL_RENDER.get(),
            (Item)ModItems.THE_ANNIHILATOR.get(),
            (Item)ModItems.THE_IMMOLATOR.get(),
            (Item)ModItems.ALTAR_OF_FIRE.get(),
            (Item)ModItems.ALTAR_OF_VOID.get(),
            (Item)ModItems.AZURE_SEA_SHIELD.get(),
            (Item)ModItems.ASTRAPE.get(),
            (Item)ModItems.CERAUNUS.get(),
            (Item)ModItems.BRONTES.get(),
            (Item)ModItems.ALTAR_OF_AMETHYST.get(),
            (Item)ModItems.ALTAR_OF_ABYSS.get(),
            (Item)ModItems.EMP.get(),
            (Item)ModItems.MECHANICAL_FUSION_ANVIL.get(),
            (Item)ModItems.ABYSSAL_EGG.get(),
            (Item)ModItems.KOBOLEDIATOR_SKULL.get(),
            (Item)ModItems.APTRGANGR_HEAD.get(),
            (Item)ModItems.DRAUGR_HEAD.get(),
            (Item)ModItems.GODDESS_STATUE.get(),
            (Item)ModItems.BOSS_RESPAWNER.get()
         }
      );
   }
}
