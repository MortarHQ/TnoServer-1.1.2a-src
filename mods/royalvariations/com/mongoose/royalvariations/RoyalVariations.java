package com.mongoose.royalvariations;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.MapCodec;
import com.mongoose.royalvariations.client.ClientProxy;
import com.mongoose.royalvariations.client.particles.RVParticleTypes;
import com.mongoose.royalvariations.common.CommonProxy;
import com.mongoose.royalvariations.common.attachments.RVAttachments;
import com.mongoose.royalvariations.common.attachments.RVCapUpdatePacket;
import com.mongoose.royalvariations.common.effects.RVEffects;
import com.mongoose.royalvariations.common.entities.RVEntityType;
import com.mongoose.royalvariations.common.entities.hostile.RoyalCreeper;
import com.mongoose.royalvariations.common.entities.hostile.RoyalSkeleton;
import com.mongoose.royalvariations.common.entities.hostile.RoyalZombie;
import com.mongoose.royalvariations.common.entities.neutral.RoyalEnderman;
import com.mongoose.royalvariations.common.items.RVItems;
import com.mongoose.royalvariations.common.items.armor.RVArmorMaterials;
import com.mongoose.royalvariations.common.network.client.CSetDeltaMovement;
import com.mongoose.royalvariations.common.network.server.SInstaLookPacket;
import com.mongoose.royalvariations.common.network.server.SRemoveEffectPacket;
import com.mongoose.royalvariations.common.network.server.SRepositionPacket;
import com.mongoose.royalvariations.common.world.RVMobSpawnBiomeModifier;
import com.mongoose.royalvariations.config.RVConfig;
import com.mongoose.royalvariations.init.ClientSideInit;
import com.mongoose.royalvariations.init.RVCreativeTab;
import com.mongoose.royalvariations.init.RVDataComponents;
import com.mongoose.royalvariations.init.RVProxy;
import com.mongoose.royalvariations.init.RVSounds;
import com.mongoose.royalvariations.init.SidedInit;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.InterModComms;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries.Keys;
import org.slf4j.Logger;
import top.theillusivec4.curios.api.SlotTypePreset;

@Mod("royalvariations")
public class RoyalVariations {
   public static final String MOD_ID = "royalvariations";
   public static final Logger LOGGER = LogUtils.getLogger();
   public static RVProxy PROXY;
   public static SidedInit SIDED_INIT;

   public static ResourceLocation location(String path) {
      return ResourceLocation.fromNamespaceAndPath("royalvariations", path);
   }

   public RoyalVariations(IEventBus modEventBus, Dist dist, ModContainer modContainer) {
      modEventBus.addListener(this::setupPackets);
      RVAttachments.ATTACHMENT_TYPES.register(modEventBus);
      RVEntityType.ENTITY_TYPE.register(modEventBus);
      RVParticleTypes.PARTICLE_TYPES.register(modEventBus);
      RVCreativeTab.CREATIVE_MODE_TABS.register(modEventBus);
      RVDataComponents.DATA_COMPONENT_TYPES.register(modEventBus);
      PROXY = FMLLoader.getDist().isClient() ? new ClientProxy() : new CommonProxy();
      SIDED_INIT = FMLLoader.getDist().isClient() ? new ClientSideInit() : new SidedInit();
      modEventBus.addListener(this::setupEntityAttributeCreation);
      modEventBus.addListener(this::SpawnPlacementEvent);
      modContainer.registerConfig(Type.COMMON, RVConfig.SPEC, "royalvariations.toml");
      RVConfig.loadConfig(RVConfig.SPEC, FMLPaths.CONFIGDIR.get().resolve("royalvariations.toml").toString());
      DeferredRegister<MapCodec<? extends BiomeModifier>> biomeModifiers = DeferredRegister.create(Keys.BIOME_MODIFIER_SERIALIZERS, "royalvariations");
      biomeModifiers.register(modEventBus);
      biomeModifiers.register("mob_spawns", RVMobSpawnBiomeModifier::makeCodec);
      RVItems.init(modEventBus);
      RVArmorMaterials.init(modEventBus);
      RVEffects.init(modEventBus);
      RVSounds.init(modEventBus);
      SIDED_INIT.init(modEventBus);
   }

   private void setupEntityAttributeCreation(EntityAttributeCreationEvent event) {
      event.put((EntityType)RVEntityType.ROYAL_BOMB.get(), Mob.createMobAttributes().build());
      event.put((EntityType)RVEntityType.ROYAL_ZOMBIE.get(), RoyalZombie.createAttributes().build());
      event.put((EntityType)RVEntityType.ROYAL_SKELETON.get(), RoyalSkeleton.createAttributes().build());
      event.put((EntityType)RVEntityType.ROYAL_CREEPER.get(), RoyalCreeper.createAttributes().build());
      event.put((EntityType)RVEntityType.ROYAL_ENDERMAN.get(), RoyalEnderman.createAttributes().build());
   }

   private void SpawnPlacementEvent(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)RVEntityType.ROYAL_ZOMBIE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.AND
      );
      event.register(
         (EntityType)RVEntityType.ROYAL_SKELETON.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.AND
      );
      event.register(
         (EntityType)RVEntityType.ROYAL_CREEPER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.AND
      );
      event.register(
         (EntityType)RVEntityType.ROYAL_ENDERMAN.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.AND
      );
   }

   private void enqueueIMC(InterModEnqueueEvent event) {
      InterModComms.sendTo("curios", "register_type", () -> SlotTypePreset.HANDS.getMessageBuilder().build());
   }

   public void setupPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("royalvariations").versioned("1.0.0").optional();
      registrar.playToServer(CSetDeltaMovement.TYPE, CSetDeltaMovement.STREAM_CODEC, CSetDeltaMovement::consume);
      registrar.playToClient(SInstaLookPacket.TYPE, SInstaLookPacket.STREAM_CODEC, SInstaLookPacket::consume);
      registrar.playToClient(SRemoveEffectPacket.TYPE, SRemoveEffectPacket.STREAM_CODEC, SRemoveEffectPacket::consume);
      registrar.playToClient(SRepositionPacket.TYPE, SRepositionPacket.STREAM_CODEC, SRepositionPacket::consume);
      registrar.playToClient(RVCapUpdatePacket.TYPE, RVCapUpdatePacket.STREAM_CODEC, RVCapUpdatePacket::consume);
   }
}
