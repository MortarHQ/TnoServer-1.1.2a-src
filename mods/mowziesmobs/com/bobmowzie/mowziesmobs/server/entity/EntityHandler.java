package com.bobmowzie.mowziesmobs.server.entity;

import com.bobmowzie.mowziesmobs.MMCommon;
import com.bobmowzie.mowziesmobs.server.entity.bluff.EntityBluff;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityAxeAttack;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityBlockSwapper;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityCameraShake;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityFallingBlock;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityIceBreath;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntityPoisonBall;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySolarBeam;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySunstrike;
import com.bobmowzie.mowziesmobs.server.entity.effects.EntitySuperNova;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderProjectile;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityBoulderSculptor;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityEarthSpike;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityFissure;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityFissurePiece;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillar;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityPillarPiece;
import com.bobmowzie.mowziesmobs.server.entity.effects.geomancy.EntityRockSling;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosa;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosaFollowerToHowler;
import com.bobmowzie.mowziesmobs.server.entity.elokosa.EntityElokosaHowler;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityBabyFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.foliaath.EntityFoliaath;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrostmaw;
import com.bobmowzie.mowziesmobs.server.entity.frostmaw.EntityFrozenController;
import com.bobmowzie.mowziesmobs.server.entity.grottol.EntityGrottol;
import com.bobmowzie.mowziesmobs.server.entity.lantern.EntityLantern;
import com.bobmowzie.mowziesmobs.server.entity.naga.EntityNaga;
import com.bobmowzie.mowziesmobs.server.entity.sculptor.EntitySculptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthana;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCrane;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaCraneToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToPlayer;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaFollowerToRaptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaMinion;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthanaRaptor;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.EntityUmvuthi;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.MaskType;
import com.bobmowzie.mowziesmobs.server.entity.umvuthana.trade.Trade;
import com.bobmowzie.mowziesmobs.server.entity.wroughtnaut.EntityWroughtnaut;
import java.util.Optional;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType.Builder;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.EventBusSubscriber.Bus;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

@EventBusSubscriber(bus = Bus.MOD)
public class EntityHandler {
   public static final DeferredRegister<EntityDataSerializer<?>> SERIALIZER_REG = DeferredRegister.create(
      NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, "mowziesmobs"
   );
   public static DeferredHolder<EntityDataSerializer<?>, EntityDataSerializer<Optional<Trade>>> OPTIONAL_TRADE = SERIALIZER_REG.register(
      "optional_trade", () -> EntityDataSerializer.forValueType(Trade.STREAM_CODEC.apply(ByteBufCodecs::optional))
   );
   public static final DeferredRegister<EntityType<?>> REG = DeferredRegister.create(Registries.ENTITY_TYPE, "mowziesmobs");
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFoliaath>> FOLIAATH = REG.register(
      "foliaath",
      () -> Builder.of(EntityFoliaath::new, MobCategory.MONSTER).sized(0.5F, 2.5F).clientTrackingRange(8).build(MMCommon.resource("foliaath").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBabyFoliaath>> BABY_FOLIAATH = REG.register(
      "baby_foliaath",
      () -> Builder.of(EntityBabyFoliaath::new, MobCategory.MONSTER)
         .clientTrackingRange(8)
         .sized(0.4F, 0.4F)
         .clientTrackingRange(8)
         .build(MMCommon.resource("baby_foliaath").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityWroughtnaut>> WROUGHTNAUT = REG.register(
      "ferrous_wroughtnaut",
      () -> Builder.of(EntityWroughtnaut::new, MobCategory.MONSTER)
         .clientTrackingRange(8)
         .sized(2.5F, 3.5F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("ferrous_wroughtnaut").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaFollowerToRaptor>> UMVUTHANA_FOLLOWER_TO_RAPTOR = REG.register(
      "umvuthana_follower_raptor",
      () -> umvuthanaFollowerToRaptorBuilder()
         .sized(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana_follower_raptor").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaFollowerToPlayer>> UMVUTHANA_FOLLOWER_TO_PLAYER = REG.register(
      "umvuthana_follower_player",
      () -> umvuthanaFollowerToPlayerBuilder()
         .sized(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana_follower_player").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaCraneToPlayer>> UMVUTHANA_CRANE_TO_PLAYER = REG.register(
      "umvuthana_crane_player",
      () -> umvuthanaCraneToPlayerBuilder()
         .sized(MaskType.FAITH.entityWidth, MaskType.FAITH.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana_crane_player").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaMinion>> UMVUTHANA_MINION = REG.register(
      "umvuthana",
      () -> Builder.of(EntityUmvuthanaMinion::new, MobCategory.MONSTER)
         .sized(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaRaptor>> UMVUTHANA_RAPTOR = REG.register(
      "umvuthana_raptor",
      () -> Builder.of(EntityUmvuthanaRaptor::new, MobCategory.MONSTER)
         .sized(MaskType.FURY.entityWidth, MaskType.FURY.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana_raptor").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthanaCrane>> UMVUTHANA_CRANE = REG.register(
      "umvuthana_crane",
      () -> Builder.of(EntityUmvuthanaCrane::new, MobCategory.MONSTER)
         .sized(MaskType.FEAR.entityWidth, MaskType.FEAR.entityHeight)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthana_crane").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityUmvuthi>> UMVUTHI = REG.register(
      "umvuthi",
      () -> Builder.of(EntityUmvuthi::new, MobCategory.MONSTER)
         .sized(1.5F, 3.2F)
         .clientTrackingRange(10)
         .setUpdateInterval(1)
         .build(MMCommon.resource("umvuthi").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFrostmaw>> FROSTMAW = REG.register(
      "frostmaw",
      () -> Builder.of(EntityFrostmaw::new, MobCategory.MONSTER)
         .sized(4.0F, 4.0F)
         .clientTrackingRange(10)
         .setUpdateInterval(1)
         .build(MMCommon.resource("frostmaw").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityGrottol>> GROTTOL = REG.register(
      "grottol",
      () -> Builder.of(EntityGrottol::new, MobCategory.MONSTER)
         .sized(0.9F, 1.2F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("grottol").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityLantern>> LANTERN = REG.register(
      "lantern",
      () -> Builder.of(EntityLantern::new, MobCategory.AMBIENT)
         .sized(1.0F, 1.0F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("lantern").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityNaga>> NAGA = REG.register(
      "naga",
      () -> Builder.of(EntityNaga::new, MobCategory.MONSTER)
         .sized(3.0F, 1.0F)
         .clientTrackingRange(13)
         .canSpawnFarFromPlayer()
         .setUpdateInterval(1)
         .build(MMCommon.resource("naga").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntitySculptor>> SCULPTOR = REG.register(
      "sculptor",
      () -> Builder.of(EntitySculptor::new, MobCategory.MISC)
         .sized(1.0F, 2.3F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("sculptor").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBluff>> BLUFF = REG.register(
      "bluff",
      () -> Builder.of(EntityBluff::new, MobCategory.MONSTER)
         .sized(0.6F, 1.8F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("bluff").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityElokosaFollowerToHowler>> ELOKOSA_FOLLOWER_TO_HOWLER = REG.register(
      "elokosa_follower_howler",
      () -> elokosaFollowerToHowlerBuilder()
         .sized(0.9F, 1.6F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("elokosa_follower_howler").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityElokosaHowler>> ELOKOSA_HOWLER = REG.register(
      "elokosa_howler",
      () -> Builder.of(EntityElokosaHowler::new, MobCategory.MONSTER)
         .sized(0.95F, 1.7F)
         .clientTrackingRange(8)
         .setUpdateInterval(1)
         .build(MMCommon.resource("elokosa_howler").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntitySunstrike>> SUNSTRIKE = REG.register(
      "sunstrike", () -> sunstrikeBuilder().sized(0.1F, 0.1F).build(MMCommon.resource("sunstrike").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntitySolarBeam>> SOLAR_BEAM = REG.register(
      "solar_beam", () -> solarBeamBuilder().sized(0.1F, 0.1F).setUpdateInterval(1).build(MMCommon.resource("solar_beam").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBoulderProjectile>> BOULDER_PROJECTILE = REG.register(
      "boulder_projectile", () -> boulderProjectileBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("boulder_projectile").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityRockSling>> ROCK_SLING = REG.register(
      "rock_sling", () -> rockSlingBuilder().sized(0.5F, 0.5F).setUpdateInterval(1).build(MMCommon.resource("rock_sling").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBoulderSculptor>> BOULDER_SCULPTOR = REG.register(
      "boulder_platform", () -> boulderPlatformBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("boulder_platform").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBoulderSculptor.EntityBoulderSculptorCrumbling>> BOULDER_SCULPTOR_CRUMBLING = REG.register(
      "boulder_platform_crumbling",
      () -> boulderPlatformCrumblingBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("boulder_platform_crumbling").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityPillar>> PILLAR = REG.register(
      "pillar", () -> pillarBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("pillar").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityPillar.EntityPillarSculptor>> PILLAR_SCULPTOR = REG.register(
      "pillar_sculptor",
      () -> sculptorPillarBuilder().sized(1.0F, 1.0F).clientTrackingRange(8).setUpdateInterval(1).build(MMCommon.resource("pillar_sculptor").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityPillarPiece>> PILLAR_PIECE = REG.register(
      "pillar_piece", () -> pillarPieceBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("pillar_piece").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityAxeAttack>> AXE_ATTACK = REG.register(
      "axe_attack", () -> axeAttackBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("axe_attack").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityIceBreath>> ICE_BREATH = REG.register(
      "ice_breath", () -> iceBreathBuilder().sized(0.0F, 0.0F).setUpdateInterval(1).build(MMCommon.resource("ice_breath").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityIceBall>> ICE_BALL = REG.register(
      "ice_ball", () -> iceBallBuilder().sized(0.5F, 0.5F).setUpdateInterval(20).build(MMCommon.resource("ice_ball").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFrozenController>> FROZEN_CONTROLLER = REG.register(
      "frozen_controller", () -> frozenControllerBuilder().noSummon().sized(0.0F, 0.0F).build(MMCommon.resource("frozen_controller").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityDart>> DART = REG.register(
      "dart", () -> dartBuilder().noSummon().sized(0.5F, 0.5F).setUpdateInterval(20).build(MMCommon.resource("dart").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityPoisonBall>> POISON_BALL = REG.register(
      "poison_ball", () -> poisonBallBuilder().sized(0.5F, 0.5F).setUpdateInterval(20).build(MMCommon.resource("poison_ball").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntitySuperNova>> SUPER_NOVA = REG.register(
      "super_nova", () -> superNovaBuilder().sized(1.0F, 1.0F).setUpdateInterval(Integer.MAX_VALUE).build(MMCommon.resource("super_nova").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFallingBlock>> FALLING_BLOCK = REG.register(
      "falling_block", () -> fallingBlockBuilder().sized(1.0F, 1.0F).build(MMCommon.resource("falling_block").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBlockSwapper>> BLOCK_SWAPPER = REG.register(
      "block_swapper",
      () -> blockSwapperBuilder().noSummon().sized(1.0F, 1.0F).setUpdateInterval(Integer.MAX_VALUE).build(MMCommon.resource("block_swapper").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityBlockSwapper.EntityBlockSwapperTunneling>> BLOCK_SWAPPER_TUNNELING = REG.register(
      "block_swapper_tunneling",
      () -> blockSwapperTunnelingBuilder()
         .noSummon()
         .sized(1.0F, 1.0F)
         .setUpdateInterval(Integer.MAX_VALUE)
         .build(MMCommon.resource("block_swapper_tunneling").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityCameraShake>> CAMERA_SHAKE = REG.register(
      "camera_shake", () -> cameraShakeBuilder().sized(1.0F, 1.0F).setUpdateInterval(Integer.MAX_VALUE).build(MMCommon.resource("camera_shake").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFissure>> FISSURE = REG.register(
      "fissure", () -> fissureBuilder().sized(1.0F, 1.0F).setUpdateInterval(1).build(MMCommon.resource("fissure").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityFissurePiece>> FISSURE_PIECE = REG.register(
      "fissure_piece", () -> fissurePieceBuilder().sized(2.0F, 0.1F).build(MMCommon.resource("fissure_piece").toString())
   );
   public static final DeferredHolder<EntityType<?>, EntityType<EntityEarthSpike>> EARTH_SPIKE = REG.register(
      "earth_spike", () -> earthSpikeBuilder().sized(1.0F, 1.0F).build(MMCommon.resource("earth_spike").toString())
   );

   private static Builder<EntityUmvuthanaFollowerToRaptor> umvuthanaFollowerToRaptorBuilder() {
      return Builder.of(EntityUmvuthanaFollowerToRaptor::new, MobCategory.MONSTER);
   }

   private static Builder<EntityUmvuthanaFollowerToPlayer> umvuthanaFollowerToPlayerBuilder() {
      return Builder.of(EntityUmvuthanaFollowerToPlayer::new, MobCategory.MONSTER);
   }

   private static Builder<EntityUmvuthanaCraneToPlayer> umvuthanaCraneToPlayerBuilder() {
      return Builder.of(EntityUmvuthanaCraneToPlayer::new, MobCategory.MONSTER);
   }

   private static Builder<EntityElokosaFollowerToHowler> elokosaFollowerToHowlerBuilder() {
      return Builder.of(EntityElokosaFollowerToHowler::new, MobCategory.MONSTER);
   }

   private static Builder<EntitySunstrike> sunstrikeBuilder() {
      return Builder.of(EntitySunstrike::new, MobCategory.MISC);
   }

   private static Builder<EntitySolarBeam> solarBeamBuilder() {
      return Builder.of(EntitySolarBeam::new, MobCategory.MISC);
   }

   private static Builder<EntityBoulderProjectile> boulderProjectileBuilder() {
      return Builder.of(EntityBoulderProjectile::new, MobCategory.MISC);
   }

   private static Builder<EntityRockSling> rockSlingBuilder() {
      return Builder.of(EntityRockSling::new, MobCategory.MISC);
   }

   private static Builder<EntityBoulderSculptor> boulderPlatformBuilder() {
      return Builder.of(EntityBoulderSculptor::new, MobCategory.MISC);
   }

   private static Builder<EntityBoulderSculptor.EntityBoulderSculptorCrumbling> boulderPlatformCrumblingBuilder() {
      return Builder.of(EntityBoulderSculptor.EntityBoulderSculptorCrumbling::new, MobCategory.MISC);
   }

   private static Builder<EntityPillar> pillarBuilder() {
      return Builder.of(EntityPillar::new, MobCategory.MISC);
   }

   private static Builder<EntityPillar.EntityPillarSculptor> sculptorPillarBuilder() {
      return Builder.of(EntityPillar.EntityPillarSculptor::new, MobCategory.MISC);
   }

   private static Builder<EntityPillarPiece> pillarPieceBuilder() {
      return Builder.of(EntityPillarPiece::new, MobCategory.MISC);
   }

   private static Builder<EntityAxeAttack> axeAttackBuilder() {
      return Builder.of(EntityAxeAttack::new, MobCategory.MISC);
   }

   private static Builder<EntityIceBreath> iceBreathBuilder() {
      return Builder.of(EntityIceBreath::new, MobCategory.MISC);
   }

   private static Builder<EntityIceBall> iceBallBuilder() {
      return Builder.of(EntityIceBall::new, MobCategory.MISC);
   }

   private static Builder<EntityFrozenController> frozenControllerBuilder() {
      return Builder.of(EntityFrozenController::new, MobCategory.MISC);
   }

   private static Builder<EntityDart> dartBuilder() {
      return Builder.of(EntityDart::new, MobCategory.MISC);
   }

   private static Builder<EntityPoisonBall> poisonBallBuilder() {
      return Builder.of(EntityPoisonBall::new, MobCategory.MISC);
   }

   private static Builder<EntitySuperNova> superNovaBuilder() {
      return Builder.of(EntitySuperNova::new, MobCategory.MISC);
   }

   private static Builder<EntityFallingBlock> fallingBlockBuilder() {
      return Builder.of(EntityFallingBlock::new, MobCategory.MISC);
   }

   private static Builder<EntityBlockSwapper> blockSwapperBuilder() {
      return Builder.of(EntityBlockSwapper::new, MobCategory.MISC);
   }

   private static Builder<EntityBlockSwapper.EntityBlockSwapperTunneling> blockSwapperTunnelingBuilder() {
      return Builder.of(EntityBlockSwapper.EntityBlockSwapperTunneling::new, MobCategory.MISC);
   }

   private static Builder<EntityCameraShake> cameraShakeBuilder() {
      return Builder.of(EntityCameraShake::new, MobCategory.MISC);
   }

   private static Builder<EntityFissure> fissureBuilder() {
      return Builder.of(EntityFissure::new, MobCategory.MISC);
   }

   private static Builder<EntityFissurePiece> fissurePieceBuilder() {
      return Builder.of(EntityFissurePiece::new, MobCategory.MISC);
   }

   private static Builder<EntityEarthSpike> earthSpikeBuilder() {
      return Builder.of(EntityEarthSpike::new, MobCategory.MISC);
   }

   @SubscribeEvent
   public static void onCreateAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)FOLIAATH.get(), EntityFoliaath.createAttributes().build());
      event.put((EntityType)BABY_FOLIAATH.get(), EntityBabyFoliaath.createAttributes().build());
      event.put((EntityType)WROUGHTNAUT.get(), EntityWroughtnaut.createAttributes().build());
      event.put((EntityType)UMVUTHANA_RAPTOR.get(), EntityUmvuthanaRaptor.createAttributes().build());
      event.put((EntityType)UMVUTHANA_MINION.get(), EntityUmvuthana.createAttributes().build());
      event.put((EntityType)UMVUTHANA_FOLLOWER_TO_PLAYER.get(), EntityUmvuthanaFollowerToPlayer.createAttributes().build());
      event.put((EntityType)UMVUTHANA_CRANE_TO_PLAYER.get(), EntityUmvuthanaFollowerToPlayer.createAttributes().build());
      event.put((EntityType)UMVUTHANA_FOLLOWER_TO_RAPTOR.get(), EntityUmvuthana.createAttributes().build());
      event.put((EntityType)UMVUTHANA_CRANE.get(), EntityUmvuthana.createAttributes().build());
      event.put((EntityType)UMVUTHI.get(), EntityUmvuthi.createAttributes().build());
      event.put((EntityType)FROSTMAW.get(), EntityFrostmaw.createAttributes().build());
      event.put((EntityType)NAGA.get(), EntityNaga.createAttributes().build());
      event.put((EntityType)LANTERN.get(), EntityLantern.createAttributes().build());
      event.put((EntityType)GROTTOL.get(), EntityGrottol.createAttributes().build());
      event.put((EntityType)SCULPTOR.get(), EntitySculptor.createAttributes().build());
      event.put((EntityType)BLUFF.get(), EntityBluff.createAttributes().build());
      event.put((EntityType)ELOKOSA_FOLLOWER_TO_HOWLER.get(), EntityElokosa.createAttributes().build());
      event.put((EntityType)ELOKOSA_HOWLER.get(), EntityElokosaHowler.createAttributes().build());
   }
}
