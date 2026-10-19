package com.iafenvoy.iceandfire.registry;

import com.iafenvoy.iceandfire.data.DragonColor;
import com.iafenvoy.iceandfire.data.SeaSerpentType;
import com.iafenvoy.iceandfire.data.TrollType;
import com.iafenvoy.iceandfire.impl.ParticleProviderHolder;
import com.iafenvoy.iceandfire.item.DragonHornItem;
import com.iafenvoy.iceandfire.item.SummoningCrystalItem;
import com.iafenvoy.iceandfire.particle.BloodParticle;
import com.iafenvoy.iceandfire.particle.DragonFlameParticle;
import com.iafenvoy.iceandfire.particle.DragonFrostParticle;
import com.iafenvoy.iceandfire.particle.DreadPortalParticle;
import com.iafenvoy.iceandfire.particle.DreadTorchParticle;
import com.iafenvoy.iceandfire.particle.GhostAppearanceParticle;
import com.iafenvoy.iceandfire.particle.HydraBreathParticle;
import com.iafenvoy.iceandfire.particle.PixieDustParticle;
import com.iafenvoy.iceandfire.particle.SerpentBubbleParticle;
import com.iafenvoy.iceandfire.particle.SirenMusicParticle;
import com.iafenvoy.iceandfire.render.block.DreadPortalBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.DreadSpawnerBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.EggInIceBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.JarBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.LecternBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.PixieHouseBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.block.PodiumBlockEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.AmphithereArrowEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.AmphithereEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.ChainTieEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.CockatriceEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.CyclopsEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DeathWormEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DragonArrowEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DragonBaseEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DragonChargeEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DragonEggEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DragonSkullEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadBeastEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadGhoulEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadHorseEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadKnightEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadLichEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadLichSkullEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadScuttlerEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.DreadThrallEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.GhostEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.GhostSwordEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.GorgonEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.HippocampusEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.HippogryphEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.HydraArrowEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.HydraEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.LightningDragonChargeEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.LightningDragonEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.MobSkullEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.NothingEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.PixieEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.SeaSerpentArrowEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.SeaSerpentEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.SirenEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.StoneStatueEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.StymphalianArrowEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.StymphalianBirdEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.StymphalianFeatherEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.TideTridentEntityRenderer;
import com.iafenvoy.iceandfire.render.entity.TrollEntityRenderer;
import com.iafenvoy.iceandfire.render.item.DeathwormGauntletRenderer;
import com.iafenvoy.iceandfire.render.item.GorgonHeadRenderer;
import com.iafenvoy.iceandfire.render.item.MiscItemRenderer;
import com.iafenvoy.iceandfire.render.item.TideTridentItemRenderer;
import com.iafenvoy.iceandfire.render.item.TrollWeaponRenderer;
import com.iafenvoy.iceandfire.render.item.armor.BasicArmorRenderer;
import com.iafenvoy.iceandfire.render.item.armor.ScaleArmorRenderer;
import com.iafenvoy.iceandfire.render.model.animator.FireDragonTabulaModelAnimator;
import com.iafenvoy.iceandfire.render.model.animator.IceDragonTabulaModelAnimator;
import com.iafenvoy.iceandfire.render.model.animator.LightningTabulaDragonAnimator;
import com.iafenvoy.iceandfire.render.model.armor.CopperArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.DeathWormArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.DragonSteelFireArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.DragonSteelIceArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.DragonSteelLightningArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.SeaSerpentArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.SilverArmorModel;
import com.iafenvoy.iceandfire.render.model.armor.TrollArmorModel;
import com.iafenvoy.uranus.client.model.util.TabulaModelHandlerHelper;
import com.iafenvoy.uranus.client.render.DynamicItemRenderer;
import com.iafenvoy.uranus.client.render.armor.IArmorRendererBase;
import com.iafenvoy.uranus.util.function.MemorizeSupplier;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import dev.architectury.registry.client.rendering.RenderTypeRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import java.util.function.Consumer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.ChestRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public final class IafRenderers {
   public static final ResourceLocation FIRE_DRAGON = ResourceLocation.fromNamespaceAndPath("iceandfire", "firedragon/firedragon_ground");
   public static final ResourceLocation ICE_DRAGON = ResourceLocation.fromNamespaceAndPath("iceandfire", "icedragon/icedragon_ground");
   public static final ResourceLocation LIGHTNING_DRAGON = ResourceLocation.fromNamespaceAndPath("iceandfire", "lightningdragon/lightningdragon_ground");
   public static final ResourceLocation SEA_SERPENT = ResourceLocation.fromNamespaceAndPath("iceandfire", "seaserpent/seaserpent_base");

   public static void registerEntityRenderers() {
      EntityRendererRegistry.register(
         IafEntities.FIRE_DRAGON,
         x -> new DragonBaseEntityRenderer(x, TabulaModelHandlerHelper.getModel(FIRE_DRAGON, new MemorizeSupplier(FireDragonTabulaModelAnimator::new)))
      );
      EntityRendererRegistry.register(
         IafEntities.ICE_DRAGON,
         manager -> new DragonBaseEntityRenderer(
            manager, TabulaModelHandlerHelper.getModel(ICE_DRAGON, new MemorizeSupplier(IceDragonTabulaModelAnimator::new))
         )
      );
      EntityRendererRegistry.register(
         IafEntities.LIGHTNING_DRAGON,
         manager -> new LightningDragonEntityRenderer(
            manager, TabulaModelHandlerHelper.getModel(LIGHTNING_DRAGON, new MemorizeSupplier(LightningTabulaDragonAnimator::new))
         )
      );
      EntityRendererRegistry.register(IafEntities.DRAGON_EGG, DragonEggEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DRAGON_ARROW, DragonArrowEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DRAGON_SKULL, DragonSkullEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.FIRE_DRAGON_CHARGE, manager -> new DragonChargeEntityRenderer(manager, true));
      EntityRendererRegistry.register(IafEntities.ICE_DRAGON_CHARGE, manager -> new DragonChargeEntityRenderer(manager, false));
      EntityRendererRegistry.register(IafEntities.LIGHTNING_DRAGON_CHARGE, LightningDragonChargeEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HIPPOGRYPH_EGG, ThrownItemRenderer::new);
      EntityRendererRegistry.register(IafEntities.HIPPOGRYPH, HippogryphEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.STONE_STATUE, StoneStatueEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.GORGON, GorgonEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.PIXIE, PixieEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.CYCLOPS, CyclopsEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.SIREN, SirenEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HIPPOCAMPUS, HippocampusEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DEATH_WORM, DeathWormEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DEATH_WORM_EGG, ThrownItemRenderer::new);
      EntityRendererRegistry.register(IafEntities.COCKATRICE, CockatriceEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.COCKATRICE_EGG, ThrownItemRenderer::new);
      EntityRendererRegistry.register(IafEntities.STYMPHALIAN_BIRD, StymphalianBirdEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.STYMPHALIAN_FEATHER, StymphalianFeatherEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.STYMPHALIAN_ARROW, StymphalianArrowEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.TROLL, TrollEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.AMPHITHERE, AmphithereEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.AMPHITHERE_ARROW, AmphithereArrowEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.SEA_SERPENT, SeaSerpentEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.SEA_SERPENT_BUBBLES, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.SEA_SERPENT_ARROW, SeaSerpentArrowEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.CHAIN_TIE, ChainTieEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.PIXIE_CHARGE, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.TIDE_TRIDENT, TideTridentEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.MOB_SKULL, MobSkullEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_SCUTTLER, DreadScuttlerEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_GHOUL, DreadGhoulEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_BEAST, DreadBeastEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_SCUTTLER, DreadScuttlerEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_THRALL, DreadThrallEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_LICH, DreadLichEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_LICH_SKULL, DreadLichSkullEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_KNIGHT, DreadKnightEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DREAD_HORSE, DreadHorseEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HYDRA, HydraEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HYDRA_BREATH, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HYDRA_ARROW, HydraArrowEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.SLOW_MULTIPART, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.DRAGON_MULTIPART, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.CYCLOPS_MULTIPART, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.HYDRA_MULTIPART, NothingEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.GHOST, GhostEntityRenderer::new);
      EntityRendererRegistry.register(IafEntities.GHOST_SWORD, GhostSwordEntityRenderer::new);
   }

   public static void registerParticleRenderers(Consumer<ParticleProviderHolder<?>> consumer) {
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.BLOOD.get(), BloodParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.DRAGON_FLAME.get(), DragonFlameParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.DRAGON_FROST.get(), DragonFrostParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.DREAD_PORTAL.get(), DreadPortalParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.DREAD_TORCH.get(), DreadTorchParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.GHOST_APPEARANCE.get(), GhostAppearanceParticle.factory()));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.HYDRA_BREATH.get(), HydraBreathParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.PIXIE_DUST.get(), PixieDustParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.SERPENT_BUBBLE.get(), SerpentBubbleParticle::factory));
      consumer.accept(new ParticleProviderHolder((ParticleType)IafParticles.SIREN_MUSIC.get(), SirenMusicParticle::factory));
   }

   public static void registerBlockEntityRenderers() {
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.PODIUM.get(), PodiumBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.IAF_LECTERN.get(), LecternBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.EGG_IN_ICE.get(), EggInIceBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.PIXIE_HOUSE.get(), PixieHouseBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.PIXIE_JAR.get(), JarBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.DREAD_PORTAL.get(), DreadPortalBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.DREAD_SPAWNER.get(), DreadSpawnerBlockEntityRenderer::new);
      BlockEntityRendererRegistry.register((BlockEntityType)IafBlockEntities.GHOST_CHEST.get(), ChestRenderer::new);
   }

   public static void registerArmorRenderers() {
      IArmorRendererBase.register(
         new BasicArmorRenderer(CopperArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.COPPER_HELMET.get(),
            (ItemLike)IafItems.COPPER_CHESTPLATE.get(),
            (ItemLike)IafItems.COPPER_LEGGINGS.get(),
            (ItemLike)IafItems.COPPER_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DeathWormArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DEATHWORM_WHITE_HELMET.get(),
            (ItemLike)IafItems.DEATHWORM_WHITE_CHESTPLATE.get(),
            (ItemLike)IafItems.DEATHWORM_WHITE_LEGGINGS.get(),
            (ItemLike)IafItems.DEATHWORM_WHITE_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DeathWormArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DEATHWORM_YELLOW_HELMET.get(),
            (ItemLike)IafItems.DEATHWORM_YELLOW_CHESTPLATE.get(),
            (ItemLike)IafItems.DEATHWORM_YELLOW_LEGGINGS.get(),
            (ItemLike)IafItems.DEATHWORM_YELLOW_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DeathWormArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DEATHWORM_RED_HELMET.get(),
            (ItemLike)IafItems.DEATHWORM_RED_CHESTPLATE.get(),
            (ItemLike)IafItems.DEATHWORM_RED_LEGGINGS.get(),
            (ItemLike)IafItems.DEATHWORM_RED_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DragonSteelFireArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DRAGONSTEEL_FIRE_HELMET.get(),
            (ItemLike)IafItems.DRAGONSTEEL_FIRE_CHESTPLATE.get(),
            (ItemLike)IafItems.DRAGONSTEEL_FIRE_LEGGINGS.get(),
            (ItemLike)IafItems.DRAGONSTEEL_FIRE_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DragonSteelIceArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DRAGONSTEEL_ICE_HELMET.get(),
            (ItemLike)IafItems.DRAGONSTEEL_ICE_CHESTPLATE.get(),
            (ItemLike)IafItems.DRAGONSTEEL_ICE_LEGGINGS.get(),
            (ItemLike)IafItems.DRAGONSTEEL_ICE_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(DragonSteelLightningArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.DRAGONSTEEL_LIGHTNING_HELMET.get(),
            (ItemLike)IafItems.DRAGONSTEEL_LIGHTNING_CHESTPLATE.get(),
            (ItemLike)IafItems.DRAGONSTEEL_LIGHTNING_LEGGINGS.get(),
            (ItemLike)IafItems.DRAGONSTEEL_LIGHTNING_BOOTS.get()
         }
      );
      IArmorRendererBase.register(
         new BasicArmorRenderer(SilverArmorModel::new),
         new ItemLike[]{
            (ItemLike)IafItems.SILVER_HELMET.get(),
            (ItemLike)IafItems.SILVER_CHESTPLATE.get(),
            (ItemLike)IafItems.SILVER_LEGGINGS.get(),
            (ItemLike)IafItems.SILVER_BOOTS.get()
         }
      );

      for (DragonColor armor : IafRegistries.DRAGON_COLOR) {
         IArmorRendererBase.register(
            new ScaleArmorRenderer(),
            new ItemLike[]{(ItemLike)armor.helmet.get(), (ItemLike)armor.chestplate.get(), (ItemLike)armor.leggings.get(), (ItemLike)armor.boots.get()}
         );
      }

      for (SeaSerpentType seaSerpent : IafRegistries.SEA_SERPENT_TYPE) {
         IArmorRendererBase.register(
            new BasicArmorRenderer(SeaSerpentArmorModel::new),
            new ItemLike[]{
               (ItemLike)seaSerpent.helmet.get(), (ItemLike)seaSerpent.chestplate.get(), (ItemLike)seaSerpent.leggings.get(), (ItemLike)seaSerpent.boots.get()
            }
         );
      }

      for (TrollType troll : IafRegistries.TROLL_TYPE) {
         IArmorRendererBase.register(
            new BasicArmorRenderer(TrollArmorModel::new),
            new ItemLike[]{(ItemLike)troll.helmet.get(), (ItemLike)troll.chestplate.get(), (ItemLike)troll.leggings.get(), (ItemLike)troll.boots.get()}
         );
      }
   }

   public static void registerItemRenderers() {
      DynamicItemRenderer.RENDERERS.put((Item)IafItems.DEATHWORM_GAUNTLET_RED.get(), new DeathwormGauntletRenderer());
      DynamicItemRenderer.RENDERERS.put((Item)IafItems.DEATHWORM_GAUNTLET_YELLOW.get(), new DeathwormGauntletRenderer());
      DynamicItemRenderer.RENDERERS.put((Item)IafItems.DEATHWORM_GAUNTLET_WHITE.get(), new DeathwormGauntletRenderer());
      DynamicItemRenderer.RENDERERS.put((Item)IafItems.GORGON_HEAD.get(), new GorgonHeadRenderer());
      DynamicItemRenderer.RENDERERS.put((Item)IafItems.TIDE_TRIDENT.get(), new TideTridentItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_BIRCH.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_OAK.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_DARK_OAK.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_SPRUCE.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_RED.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_BROWN.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.DREAD_PORTAL.get()).asItem(), new MiscItemRenderer());
      DynamicItemRenderer.RENDERERS.put(((Block)IafBlocks.GHOST_CHEST.get()).asItem(), new MiscItemRenderer());

      for (TrollType.BuiltinWeapon weapon : TrollType.BuiltinWeapon.values()) {
         DynamicItemRenderer.RENDERERS.put(weapon.getItem(), new TrollWeaponRenderer());
      }
   }

   public static void registerRenderLayers() {
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.GOLD_PILE.get(), (Block)IafBlocks.SILVER_PILE.get()});
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.LECTERN.get()});
      RenderTypeRegistry.register(
         RenderType.cutout(),
         new Block[]{
            (Block)IafBlocks.PODIUM_OAK.get(),
            (Block)IafBlocks.PODIUM_BIRCH.get(),
            (Block)IafBlocks.PODIUM_SPRUCE.get(),
            (Block)IafBlocks.PODIUM_JUNGLE.get(),
            (Block)IafBlocks.PODIUM_ACACIA.get(),
            (Block)IafBlocks.PODIUM_DARK_OAK.get()
         }
      );
      RenderTypeRegistry.register(
         RenderType.cutout(), new Block[]{(Block)IafBlocks.FIRE_LILY.get(), (Block)IafBlocks.FROST_LILY.get(), (Block)IafBlocks.LIGHTNING_LILY.get()}
      );
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DRAGON_ICE_SPIKES.get()});
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DREAD_STONE_FACE.get()});
      RenderTypeRegistry.register(RenderType.translucent(), new Block[]{(Block)IafBlocks.EGG_IN_ICE.get()});
      RenderTypeRegistry.register(
         RenderType.cutout(),
         new Block[]{
            (Block)IafBlocks.JAR_EMPTY.get(),
            (Block)IafBlocks.JAR_PIXIE_0.get(),
            (Block)IafBlocks.JAR_PIXIE_1.get(),
            (Block)IafBlocks.JAR_PIXIE_2.get(),
            (Block)IafBlocks.JAR_PIXIE_3.get(),
            (Block)IafBlocks.JAR_PIXIE_4.get()
         }
      );
      RenderTypeRegistry.register(
         RenderType.cutout(),
         new Block[]{
            (Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_BROWN.get(),
            (Block)IafBlocks.PIXIE_HOUSE_MUSHROOM_RED.get(),
            (Block)IafBlocks.PIXIE_HOUSE_OAK.get(),
            (Block)IafBlocks.PIXIE_HOUSE_BIRCH.get(),
            (Block)IafBlocks.PIXIE_HOUSE_SPRUCE.get(),
            (Block)IafBlocks.PIXIE_HOUSE_DARK_OAK.get()
         }
      );
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DREAD_SPAWNER.get()});
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DREAD_TORCH.get(), (Block)IafBlocks.BURNT_TORCH.get()});
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DREAD_TORCH_WALL.get(), (Block)IafBlocks.BURNT_TORCH_WALL.get()});
      RenderTypeRegistry.register(RenderType.cutout(), new Block[]{(Block)IafBlocks.DREADWOOD_LEAVES.get(), (Block)IafBlocks.DREADWOOD_SAPLING.get()});
   }

   public static void registerModelPredicates() {
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.DRAGON_BOW.get(),
         ResourceLocation.withDefaultNamespace("pulling"),
         (itemStack, clientWorld, livingEntity, seed) -> livingEntity != null && livingEntity.isUsingItem() && livingEntity.getUseItem() == itemStack
            ? 1.0F
            : 0.0F
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.DRAGON_BOW.get(),
         ResourceLocation.withDefaultNamespace("pull"),
         (itemStack, clientWorld, livingEntity, seed) -> livingEntity == null
            ? 0.0F
            : (livingEntity.getUseItem() != itemStack ? 0.0F : (itemStack.getUseDuration(livingEntity) - livingEntity.getUseItemRemainingTicks()) / 20.0F)
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.DRAGON_HORN.get(),
         ResourceLocation.withDefaultNamespace("iceorfire"),
         (stack, level, entity, p) -> DragonHornItem.getDragonType(stack) * 0.25F
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.SUMMONING_CRYSTAL_FIRE.get(),
         ResourceLocation.withDefaultNamespace("has_dragon"),
         (stack, level, entity, p) -> SummoningCrystalItem.hasDragon(stack) ? 1.0F : 0.0F
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.SUMMONING_CRYSTAL_ICE.get(),
         ResourceLocation.withDefaultNamespace("has_dragon"),
         (stack, level, entity, p) -> SummoningCrystalItem.hasDragon(stack) ? 1.0F : 0.0F
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.SUMMONING_CRYSTAL_LIGHTNING.get(),
         ResourceLocation.withDefaultNamespace("has_dragon"),
         (stack, level, entity, p) -> SummoningCrystalItem.hasDragon(stack) ? 1.0F : 0.0F
      );
      ItemPropertiesRegistry.register(
         (ItemLike)IafItems.TIDE_TRIDENT.get(),
         ResourceLocation.withDefaultNamespace("throwing"),
         (stack, level, entity, p) -> entity != null && entity.isUsingItem() && entity.getMainHandItem() == stack ? 1.0F : 0.0F
      );
   }
}
