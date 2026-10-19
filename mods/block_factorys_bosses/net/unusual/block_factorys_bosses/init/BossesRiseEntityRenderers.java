package net.unusual.block_factorys_bosses.init;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.NoopRenderer;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent.RegisterRenderers;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.unusual.block_factorys_bosses.BossesRise;
import net.unusual.block_factorys_bosses.client.model.BigCageModel;
import net.unusual.block_factorys_bosses.client.model.BigSkellyCageModel;
import net.unusual.block_factorys_bosses.client.model.CageModel;
import net.unusual.block_factorys_bosses.client.model.SkellyCageModel;
import net.unusual.block_factorys_bosses.client.renderer.BigRiftProjectileRenderer;
import net.unusual.block_factorys_bosses.client.renderer.CageEntityRenderer;
import net.unusual.block_factorys_bosses.client.renderer.CrateEntityRenderer;
import net.unusual.block_factorys_bosses.client.renderer.PileOfBonesRenderer;
import net.unusual.block_factorys_bosses.client.renderer.RiftProjectileRenderer;
import net.unusual.block_factorys_bosses.client.renderer.SoulKnightWitherSkeletonRenderer;
import net.unusual.block_factorys_bosses.client.renderer.SoulShockwaveRenderer;
import net.unusual.block_factorys_bosses.client.renderer.SoulSkeletonRenderer;
import net.unusual.block_factorys_bosses.client.renderer.SwordWaveRenderer;
import net.unusual.block_factorys_bosses.client.renderer.ThrownKrakenTridentRenderer;
import net.unusual.block_factorys_bosses.client.renderer.block.DragonBannerBlockRenderer;
import net.unusual.block_factorys_bosses.client.renderer.block.HugeDoorBlockEntityRenderer;
import net.unusual.block_factorys_bosses.client.renderer.block.RopeRollBlockEntityRenderer;
import net.unusual.block_factorys_bosses.geckolib.AnchorRenderer;
import net.unusual.block_factorys_bosses.geckolib.CannonballRenderer;
import net.unusual.block_factorys_bosses.geckolib.FrozenSkeletonRenderer;
import net.unusual.block_factorys_bosses.geckolib.GlacialShoveRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeClusterRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeProjectileRenderer;
import net.unusual.block_factorys_bosses.geckolib.IceSpikeRenderer;
import net.unusual.block_factorys_bosses.geckolib.PlankBlockEntityRenderer;
import net.unusual.block_factorys_bosses.geckolib.ThrownCrateRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.dragon.boss.InfernalDragonRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians.DragonGuardSwordRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians.FlamingSkeletonGuardFireballRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.dragon.guardians.FlamingSkeletonGuardSwordRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.knight.UnderworldKnightRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenCannonRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenCinematicRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.kraken.KrakenTentacleRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandColumnRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.worm.SandwormRenderer;
import net.unusual.block_factorys_bosses.geckolib.boss.yeti.YetiRenderer;
import net.unusual.block_factorys_bosses.geckolib.util.CustomEntityGeoModel;
import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.cache.object.GeoBone;
import software.bernie.geckolib.model.GeoModel;
import software.bernie.geckolib.renderer.GeoEntityRenderer;
import software.bernie.geckolib.renderer.GeoRenderer;
import software.bernie.geckolib.renderer.layer.BlockAndItemGeoLayer;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
@EventBusSubscriber(Dist.CLIENT)
class BossesRiseEntityRenderers {
   @SubscribeEvent
   public static void registerEntityRenderers(RegisterRenderers event) {
      event.registerEntityRenderer((EntityType)BossesRiseEntities.PILE_OF_BONES.get(), PileOfBonesRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.YETI.get(), YetiRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.ICE_SPIKE_PR.get(), IceSpikeProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.ICE_SPIKE.get(), IceSpikeRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.ICE_SPIKE_CLUSTER.get(), IceSpikeClusterRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.FROZEN_SKELETON.get(), FrozenSkeletonRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.GLACIAL_SHOVE.get(), GlacialShoveRenderer::new);
      event.registerEntityRenderer(
         (EntityType)BossesRiseEntities.CAGE.get(),
         context -> new CageEntityRenderer(context, new CageModel(context.bakeLayer(CageModel.LAYER_LOCATION)), BossesRise.prefix("textures/entities/cage.png"))
      );
      event.registerEntityRenderer(
         (EntityType)BossesRiseEntities.CAGE_SKELLY.get(),
         context -> new CageEntityRenderer(
            context, new SkellyCageModel(context.bakeLayer(SkellyCageModel.LAYER_LOCATION)), BossesRise.prefix("textures/entities/skellycage.png")
         )
      );
      event.registerEntityRenderer(
         (EntityType)BossesRiseEntities.BIG_CAGE.get(),
         context -> new CageEntityRenderer(
            context, new BigCageModel(context.bakeLayer(BigCageModel.LAYER_LOCATION)), BossesRise.prefix("textures/entities/underworldcage.png")
         )
      );
      event.registerEntityRenderer(
         (EntityType)BossesRiseEntities.BIG_CAGE_SKELLY.get(),
         context -> new CageEntityRenderer(
            context, new BigSkellyCageModel(context.bakeLayer(BigSkellyCageModel.LAYER_LOCATION)), BossesRise.prefix("textures/entities/skellycagebig.png")
         )
      );
      event.registerEntityRenderer((EntityType)BossesRiseEntities.KRAKEN_CANNON.get(), KrakenCannonRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.CANNONBALL.get(), CannonballRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.THROWN_CRATE.get(), ThrownCrateRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.INFERNAL_DRAGON.get(), InfernalDragonRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.DRAGON_GUARD_SWORD.get(), DragonGuardSwordRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_FIREBALL.get(), FlamingSkeletonGuardFireballRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.FLAMING_SKELETON_GUARD_SWORD.get(), FlamingSkeletonGuardSwordRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.BLAZING_FIRE_BALL.get(), ThrownItemRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.FIRE_AREA.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.UNDERWORLD_KNIGHT.get(), UnderworldKnightRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SOUL_SKELETON.get(), SoulSkeletonRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SOUL_KNIGHT_WITHER_SKELETON.get(), SoulKnightWitherSkeletonRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SOUL_SHOCKWAVE.get(), SoulShockwaveRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SAND_COLUMN.get(), SandColumnRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SANDWORM.get(), SandwormRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.POISON_AREA.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.POISON_SPIT_PR.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.SWORD_WAVE.get(), SwordWaveRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.RIFT.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.RIFT_PROJECTILE.get(), RiftProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.BIG_RIFT_PROJECTILE.get(), BigRiftProjectileRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.KNIGHT_MARK.get(), NoopRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.KRAKEN.get(), KrakenRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.CINEMATIC_KRAKEN.get(), KrakenCinematicRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.KRAKEN_TRIDENT.get(), ThrownKrakenTridentRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.KRAKEN_TENTACLE.get(), KrakenTentacleRenderer::new);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.CRATE_PILE.get(), CrateEntityRenderer::new);
      createGeoRenderer(event, BossesRiseEntities.GHOST_TENTACLE, RenderType.ENTITY_TRANSLUCENT_EMISSIVE);
      createGeoRenderer(event, BossesRiseEntities.PIRATE_CAPTAIN, null, BossesRiseEntityRenderers::createHeldItemLayer);
      createGeoRenderer(event, BossesRiseEntities.PIRATE_ROOK, null, BossesRiseEntityRenderers::createHeldItemLayer);
      createGeoRenderer(event, BossesRiseEntities.CROSSBOW_PIRATE, null, BossesRiseEntityRenderers::createHeldItemLayer);
      event.registerEntityRenderer((EntityType)BossesRiseEntities.ANCHOR.get(), AnchorRenderer::new);
      registerBlockEntityRenderers(event);
   }

   private static <T extends Entity & GeoEntity> void createGeoRenderer(RegisterRenderers event, DeferredHolder<EntityType<?>, EntityType<T>> holder) {
      createGeoRenderer(event, holder, null, null);
   }

   private static <T extends Entity & GeoEntity> void createGeoRenderer(
      RegisterRenderers event, DeferredHolder<EntityType<?>, EntityType<T>> holder, @Nullable BiFunction<ResourceLocation, Boolean, RenderType> type
   ) {
      createGeoRenderer(event, holder, type, null);
   }

   private static <T extends Entity & GeoEntity> void createGeoRenderer(
      RegisterRenderers event,
      DeferredHolder<EntityType<?>, EntityType<T>> holder,
      @Nullable BiFunction<ResourceLocation, Boolean, RenderType> type,
      @Nullable Consumer<GeoEntityRenderer<T>> consumer
   ) {
      event.registerEntityRenderer((EntityType)holder.get(), context -> {
         GeoEntityRenderer<T> renderer = new GeoEntityRenderer<T>(context, new CustomEntityGeoModel(holder.getId(), type)) {
            protected float getDeathMaxRotation(T animatable) {
               return 0.0F;
            }
         };
         if (consumer != null) {
            consumer.accept(renderer);
         }

         return renderer;
      });
   }

   private static <T extends LivingEntity & GeoEntity> void createHeldItemLayer(GeoEntityRenderer<T> renderer) {
      renderer.addRenderLayer(new BlockAndItemGeoLayer<T>(renderer, (bone, animatable) -> {
         if (bone.getName().equals("right_item")) {
            return animatable.getMainHandItem();
         } else {
            return bone.getName().equals("left_item") ? animatable.getOffhandItem() : null;
         }
      }, (bone, animatable) -> null) {
         protected ItemDisplayContext getTransformTypeForStack(GeoBone bone, ItemStack stack, T animatable) {
            if (bone.getName().equals("right_item")) {
               return ItemDisplayContext.THIRD_PERSON_RIGHT_HAND;
            } else {
               return bone.getName().equals("left_item") ? ItemDisplayContext.THIRD_PERSON_LEFT_HAND : ItemDisplayContext.NONE;
            }
         }
      });
   }

   private static void registerBlockEntityRenderers(RegisterRenderers event) {
      event.registerBlockEntityRenderer(
         (BlockEntityType)BossesRiseBlockEntities.UNDERWORLD_ARENA_DOOR.get(),
         context -> new HugeDoorBlockEntityRenderer(context, BossesRiseBlocks.UNDERWORLD_ARENA_DOOR.getId(), true)
      );
      event.registerBlockEntityRenderer((BlockEntityType)BossesRiseBlockEntities.DRAGON_BANNER.get(), DragonBannerBlockRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BossesRiseBlockEntities.ROPE_ROLL.get(), RopeRollBlockEntityRenderer::new);
      event.registerBlockEntityRenderer((BlockEntityType)BossesRiseBlockEntities.PLANK.get(), PlankBlockEntityRenderer::new);
   }
}
