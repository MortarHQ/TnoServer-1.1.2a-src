package com.blorb.morerelics;

import com.blorb.morerelics.dataComponents.AttachmentHandler;
import com.blorb.morerelics.dataComponents.DataComponentHandler;
import com.blorb.morerelics.effects.Accelerating;
import com.blorb.morerelics.effects.Charred;
import com.blorb.morerelics.effects.Corrosion;
import com.blorb.morerelics.effects.Cyberpsychosis;
import com.blorb.morerelics.effects.Dazed;
import com.blorb.morerelics.effects.Nullification;
import com.blorb.morerelics.effects.Overload;
import com.blorb.morerelics.effects.Static;
import com.blorb.morerelics.effects.Vulnerability;
import com.blorb.morerelics.network.SilkCounterPayload;
import com.blorb.morerelics.network.SilkCounterPayloadHandler;
import com.blorb.morerelics.network.SoarVelocityPayload;
import com.blorb.morerelics.network.SoarVelocityPayloadHandler;
import com.blorb.morerelics.particles.OrbitingParticle;
import com.blorb.morerelics.particles.VaryingAnimationParticle;
import com.blorb.morerelics.particles.VaryingSizedAnimationParticle;
import com.blorb.morerelics.relics.AxolotlCream;
import com.blorb.morerelics.relics.BioJoint;
import com.blorb.morerelics.relics.BionicEye;
import com.blorb.morerelics.relics.ConvergingOrb;
import com.blorb.morerelics.relics.CrownOfTheLegend;
import com.blorb.morerelics.relics.DepletedSpool;
import com.blorb.morerelics.relics.EjectButton;
import com.blorb.morerelics.relics.EpochApple;
import com.blorb.morerelics.relics.GravitumGlove;
import com.blorb.morerelics.relics.GravitumStrider;
import com.blorb.morerelics.relics.GutsOrb;
import com.blorb.morerelics.relics.KingCrimson;
import com.blorb.morerelics.relics.MadeInHeaven;
import com.blorb.morerelics.relics.MassGauntlet;
import com.blorb.morerelics.relics.MoodWorm;
import com.blorb.morerelics.relics.OpalNecklace;
import com.blorb.morerelics.relics.SentientRust;
import com.blorb.morerelics.relics.ShieldWeaveCape;
import com.blorb.morerelics.relics.SlumberingAmulet;
import com.blorb.morerelics.relics.ThermoseismicHeart;
import com.blorb.morerelics.relics.TyrantMask;
import com.blorb.morerelics.relics.VertebraX;
import com.blorb.morerelics.relics.WeaversSpool;
import com.blorb.morerelics.relics.WhimsOfFate;
import com.blorb.morerelics.relics.WhisperingAmulet;
import com.blorb.morerelics.relics.WonderOfU;
import com.mojang.logging.LogUtils;
import java.util.function.Supplier;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.attributes.AttributeModifier.Operation;
import net.minecraft.world.item.Item;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.DirectionalPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredRegister.Items;
import org.slf4j.Logger;

@Mod("morerelics")
public class MoreRelics {
   public static final String MODID = "morerelics";
   public static final Logger LOGGER = LogUtils.getLogger();
   public static final Items ITEMS = DeferredRegister.createItems("morerelics");
   public static final DeferredItem<Item> GUTS_ORB = ITEMS.registerItem("guts_orb", GutsOrb::new);
   public static final DeferredItem<Item> SLUMBERING_AMULET = ITEMS.registerItem("slumbering_amulet", SlumberingAmulet::new);
   public static final DeferredItem<Item> WHISPERING_AMULET = ITEMS.registerItem("whispering_amulet", WhisperingAmulet::new);
   public static final DeferredItem<Item> MADE_IN_HEAVEN = ITEMS.registerItem("made_in_heaven", MadeInHeaven::new);
   public static final DeferredItem<Item> MASS_GAUNTLET = ITEMS.registerItem("mass_gauntlet", MassGauntlet::new);
   public static final DeferredItem<Item> EJECT_BUTTON = ITEMS.registerItem("eject_button", EjectButton::new);
   public static final DeferredItem<Item> SHIELDWEAVE_CAPE = ITEMS.registerItem("shieldweave_cape", ShieldWeaveCape::new);
   public static final DeferredItem<Item> AXOLOTL_CREAM = ITEMS.registerItem("axolotl_cream", AxolotlCream::new);
   public static final DeferredItem<Item> SENTIENT_RUST = ITEMS.registerItem("sentient_rust", SentientRust::new);
   public static final DeferredItem<Item> OPAL_NECKLACE = ITEMS.registerItem("opal_necklace", OpalNecklace::new);
   public static final DeferredItem<Item> TYRANT_MASK = ITEMS.registerItem("tyrant_mask", TyrantMask::new);
   public static final DeferredItem<Item> KING_CRIMSON = ITEMS.registerItem("king_crimson", KingCrimson::new);
   public static final DeferredItem<Item> CROWN_OF_THE_LEGEND = ITEMS.registerItem("crown_of_the_legend", CrownOfTheLegend::new);
   public static final DeferredItem<Item> BIONIC_EYE = ITEMS.registerItem("bionic_eye", BionicEye::new);
   public static final DeferredItem<Item> THERMOSEISMIC_HEART = ITEMS.registerItem("thermoseismic_heart", ThermoseismicHeart::new);
   public static final DeferredItem<Item> BIOJOINT = ITEMS.registerItem("biojoint", BioJoint::new);
   public static final DeferredItem<Item> WHIMS_OF_FATE = ITEMS.registerItem("whims_of_fate", WhimsOfFate::new);
   public static final DeferredItem<Item> DEPLETED_SPOOL = ITEMS.registerItem("depleted_spool", DepletedSpool::new);
   public static final DeferredItem<Item> WEAVERS_SPOOL = ITEMS.registerItem("weavers_spool", WeaversSpool::new);
   public static final DeferredItem<Item> MOODWORM = ITEMS.registerItem("moodworm", MoodWorm::new);
   public static final DeferredItem<Item> CONVERGING_ORB = ITEMS.registerItem("converging_orb", ConvergingOrb::new);
   public static final DeferredItem<Item> WONDER_OF_U = ITEMS.registerItem("wonder_of_u", WonderOfU::new);
   public static final DeferredItem<Item> GRAVITON_GLOVE = ITEMS.registerItem("gravitum_glove", GravitumGlove::new);
   public static final DeferredItem<Item> VERTEBRAX = ITEMS.registerItem("vertebrax", VertebraX::new);
   public static final DeferredItem<Item> EPOCH_APPLE = ITEMS.registerItem("epoch_apple", EpochApple::new);
   public static final DeferredItem<Item> GRAVITUM_STRIDER = ITEMS.registerItem("gravitum_strider", GravitumStrider::new);
   public static final DeferredRegister<MobEffect> EFFECTS = DeferredRegister.create(Registries.MOB_EFFECT, "morerelics");
   public static final Holder<MobEffect> CORROSION = EFFECTS.register(
      "corrosion",
      () -> new Corrosion()
         .addAttributeModifier(Attributes.ARMOR, ResourceLocation.fromNamespaceAndPath("morerelics", "armor_corrosion"), -0.05, Operation.ADD_MULTIPLIED_TOTAL)
   );
   public static final Holder<MobEffect> STATIC = EFFECTS.register("static", Static::new);
   public static final Holder<MobEffect> NULLIFICATION = EFFECTS.register("nullification", Nullification::new);
   public static final Holder<MobEffect> VULNERABILITY = EFFECTS.register("vulnerability", Vulnerability::new);
   public static final Holder<MobEffect> CHARRED = EFFECTS.register("charred", Charred::new);
   public static final Holder<MobEffect> DAZED = EFFECTS.register("dazed", Dazed::new);
   public static final Holder<MobEffect> ACCELERATING = EFFECTS.register("accelerating", Accelerating::new);
   public static final Holder<MobEffect> CYBERPSYCHOSIS = EFFECTS.register(
      "cyberpsychosis",
      () -> new Cyberpsychosis()
         .addAttributeModifier(
            Attributes.ATTACK_DAMAGE, ResourceLocation.fromNamespaceAndPath("morerelics", "attack_cyberpsychosis"), 0.2, Operation.ADD_MULTIPLIED_TOTAL
         )
         .addAttributeModifier(
            Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("morerelics", "speed_cyberpsychosis"), 0.2, Operation.ADD_MULTIPLIED_TOTAL
         )
   );
   public static final Holder<MobEffect> OVERLOAD = EFFECTS.register(
      "overload",
      () -> new Overload()
         .addAttributeModifier(
            Attributes.MOVEMENT_SPEED, ResourceLocation.fromNamespaceAndPath("morerelics", "speed_overload"), 0.05, Operation.ADD_MULTIPLIED_TOTAL
         )
   );
   public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, "morerelics");
   public static final Holder<SoundEvent> HEAL = SOUND_EVENTS.register(
      "heal", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "heal"))
   );
   public static final Holder<SoundEvent> JUDGEMENT = SOUND_EVENTS.register(
      "judgement", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "judgement"))
   );
   public static final Holder<SoundEvent> EXECUTE = SOUND_EVENTS.register(
      "execute", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "execute"))
   );
   public static final Holder<SoundEvent> WONDER = SOUND_EVENTS.register(
      "wonder_explode", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "wonder_explode"))
   );
   public static final Holder<SoundEvent> NULL_END = SOUND_EVENTS.register(
      "null_end", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "null_end"))
   );
   public static final Holder<SoundEvent> BONK = SOUND_EVENTS.register(
      "bonk", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "bonk"))
   );
   public static final Holder<SoundEvent> MISS = SOUND_EVENTS.register(
      "miss", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "miss"))
   );
   public static final Holder<SoundEvent> JUDGEMENT_BELL = SOUND_EVENTS.register(
      "judgement_bell", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "judgement_bell"))
   );
   public static final Holder<SoundEvent> JUDGEMENT_STORM = SOUND_EVENTS.register(
      "judgement_storm", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "judgement_storm"))
   );
   public static final Holder<SoundEvent> G_STRIDER_READY = SOUND_EVENTS.register(
      "g_strider_ready", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "g_strider_ready"))
   );
   public static final Holder<SoundEvent> G_STRIDER_LEAP = SOUND_EVENTS.register(
      "g_strider_leap", () -> SoundEvent.createVariableRangeEvent(ResourceLocation.fromNamespaceAndPath("morerelics", "g_strider_leap"))
   );
   public static final DeferredRegister<ParticleType<?>> PARTICLES = DeferredRegister.create(BuiltInRegistries.PARTICLE_TYPE, "morerelics");
   public static final Supplier<SimpleParticleType> HOLY_FIRE = PARTICLES.register("holy_fire", () -> new SimpleParticleType(true));
   public static final Supplier<SimpleParticleType> ORBITING_HOLY_FIRE = PARTICLES.register("orbit_holy_fire", () -> new SimpleParticleType(true));
   public static final Supplier<SimpleParticleType> GLITCHED_SOUL = PARTICLES.register("glitched_soul", () -> new SimpleParticleType(true));
   public static final Supplier<SimpleParticleType> WONDER_EXPLOSION = PARTICLES.register("wonder_explosion", () -> new SimpleParticleType(true));
   public static final ResourceKey<DamageType> CRIMSON_EXECUTE = ResourceKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("morerelics", "crimson_execution")
   );
   public static final ResourceKey<DamageType> JUDGEMENT_BURN = ResourceKey.create(
      Registries.DAMAGE_TYPE, ResourceLocation.fromNamespaceAndPath("morerelics", "judgement_burn")
   );

   public MoreRelics(IEventBus modEventBus, ModContainer modContainer) {
      modEventBus.addListener(this::commonSetup);
      ITEMS.register(modEventBus);
      EFFECTS.register(modEventBus);
      SOUND_EVENTS.register(modEventBus);
      DataComponentHandler.REGISTRAR.register(modEventBus);
      AttachmentHandler.ATTACHMENT_TYPES.register(modEventBus);
      PARTICLES.register(modEventBus);
      NeoForge.EVENT_BUS.register(this);
      modEventBus.addListener(this::addCreative);
      modContainer.registerConfig(Type.CLIENT, Config.CLIENT_SPEC);
      modContainer.registerConfig(Type.COMMON, Config.COMMON_SPEC);
   }

   private void commonSetup(FMLCommonSetupEvent event) {
   }

   private void addCreative(BuildCreativeModeTabContentsEvent event) {
   }

   @SubscribeEvent
   public void onServerStarting(ServerStartingEvent event) {
   }

   @EventBusSubscriber(modid = "morerelics", value = Dist.CLIENT)
   public static class ClientModEvents {
      @SubscribeEvent
      public static void onClientSetup(FMLClientSetupEvent event) {
         event.enqueueWork(() -> {
            makeIntoWorm((Item)MoreRelics.MOODWORM.get());
            makeIntoConvergingOrb((Item)MoreRelics.CONVERGING_ORB.get());
            makeIntoEpochApple((Item)MoreRelics.EPOCH_APPLE.get());
         });
      }

      @SubscribeEvent
      public static void registerParticleFactory(RegisterParticleProvidersEvent event) {
         event.registerSpriteSet((ParticleType)MoreRelics.HOLY_FIRE.get(), spriteSet -> new VaryingAnimationParticle.Provider(spriteSet, 15));
         event.registerSpriteSet((ParticleType)MoreRelics.ORBITING_HOLY_FIRE.get(), spriteSet -> new OrbitingParticle.Provider(spriteSet, 10));
         event.registerSpriteSet((ParticleType)MoreRelics.GLITCHED_SOUL.get(), spriteSet -> new VaryingAnimationParticle.Provider(spriteSet, 20));
         event.registerSpriteSet((ParticleType)MoreRelics.WONDER_EXPLOSION.get(), spriteSet -> new VaryingSizedAnimationParticle.Provider(spriteSet, 10));
      }

      public static void makeIntoWorm(Item item) {
         ItemProperties.register(item, ResourceLocation.parse("mood"), (stack, a, b, c) -> DataComponentHandler.getAltIntegerCounter(stack).current());
      }

      public static void makeIntoConvergingOrb(Item item) {
         ItemProperties.register(
            item, ResourceLocation.parse("morerelics_density"), (stack, a, b, c) -> DataComponentHandler.getAltIntegerCounter(stack).current()
         );
      }

      public static void makeIntoEpochApple(Item item) {
         ItemProperties.register(
            item, ResourceLocation.parse("morerelics_evermore_state"), (stack, a, b, c) -> DataComponentHandler.getAltIntegerCounter(stack).current()
         );
      }
   }

   @EventBusSubscriber(modid = "morerelics")
   public static class CommonModEvents {
      @SubscribeEvent
      public static void onRegisterPayload(RegisterPayloadHandlersEvent event) {
         PayloadRegistrar registrar = event.registrar("1");
         registrar.playBidirectional(
            SilkCounterPayload.TYPE,
            SilkCounterPayload.STREAM_CODEC,
            new DirectionalPayloadHandler(SilkCounterPayloadHandler.CLIENT_HANDLER, SilkCounterPayloadHandler.SERVER_HANDLER)
         );
         registrar.playBidirectional(
            SoarVelocityPayload.TYPE,
            SoarVelocityPayload.STREAM_CODEC,
            new DirectionalPayloadHandler(SoarVelocityPayloadHandler.CLIENT_HANDLER, SoarVelocityPayloadHandler.SERVER_HANDLER)
         );
      }
   }
}
