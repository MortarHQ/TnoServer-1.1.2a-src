package net.unusual.block_factorys_bosses;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload.Type;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.util.thread.SidedThreadGroups;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.data.loading.DatagenModLoader;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent.Post;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.handling.IPayloadHandler;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.client.particle.bedrock.BedrockParticleEffectCache;
import net.unusual.block_factorys_bosses.geckolib.util.BossesRiseAnimationCache;
import net.unusual.block_factorys_bosses.geckolib.util.CustomBakedModelFactory;
import net.unusual.block_factorys_bosses.init.BlockFactorysBossesModTabs;
import net.unusual.block_factorys_bosses.init.BossesRiseArmorMaterials;
import net.unusual.block_factorys_bosses.init.BossesRiseAttributes;
import net.unusual.block_factorys_bosses.init.BossesRiseBlockEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseBlocks;
import net.unusual.block_factorys_bosses.init.BossesRiseDataAttachments;
import net.unusual.block_factorys_bosses.init.BossesRiseDataComponents;
import net.unusual.block_factorys_bosses.init.BossesRiseEntities;
import net.unusual.block_factorys_bosses.init.BossesRiseEntityDataSerializers;
import net.unusual.block_factorys_bosses.init.BossesRiseItems;
import net.unusual.block_factorys_bosses.init.BossesRisePOI;
import net.unusual.block_factorys_bosses.init.BossesRiseParticleTypes;
import net.unusual.block_factorys_bosses.init.BossesRiseSounds;
import net.unusual.block_factorys_bosses.structures.DragonTowerStructure;
import net.unusual.block_factorys_bosses.structures.KrakenShipStructure;
import net.unusual.block_factorys_bosses.util.BossesRiseRemapper;
import net.unusual.block_factorys_bosses.util.BurnUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import software.bernie.geckolib.cache.GeckoLibCache;
import software.bernie.geckolib.util.GeckoLibUtil;

@Mod("block_factorys_bosses")
public class BossesRise {
   public static final Logger LOGGER = LogManager.getLogger(BossesRise.class);
   public static final String MODID = "block_factorys_bosses";
   public static final DeferredRegister<StructureType<?>> STRUCTURE_TYPES = DeferredRegister.create(Registries.STRUCTURE_TYPE, "block_factorys_bosses");
   public static final DeferredHolder<StructureType<?>, StructureType<DragonTowerStructure>> DRAGON_TOWER = STRUCTURE_TYPES.register(
      "dragon_tower", () -> () -> DragonTowerStructure.CODEC
   );
   public static final DeferredHolder<StructureType<?>, StructureType<KrakenShipStructure>> KRAKEN_SHIP = STRUCTURE_TYPES.register(
      "kraken_ship", () -> () -> KrakenShipStructure.CODEC
   );
   private static boolean networkingRegistered = false;
   private static final Map<Type<?>, BossesRise.NetworkMessage<?>> MESSAGES = new HashMap<>();
   private static final Collection<Tuple<Runnable, Integer>> workQueue = new ConcurrentLinkedQueue<>();

   public BossesRise(IEventBus modEventBus) {
      NeoForge.EVENT_BUS.register(this);
      modEventBus.addListener(this::registerNetworking);
      modEventBus.addListener(this::init);
      BossesRiseSounds.SOUND_EVENTS.register(modEventBus);
      BossesRiseBlocks.REGISTRY.register(modEventBus);
      BossesRiseBlockEntities.REGISTRY.register(modEventBus);
      BossesRiseArmorMaterials.REGISTRY.register(modEventBus);
      BossesRiseItems.REGISTRY.register(modEventBus);
      BossesRiseEntities.REGISTRY.register(modEventBus);
      BossesRiseEntityDataSerializers.REGISTRY.register(modEventBus);
      BlockFactorysBossesModTabs.REGISTRY.register(modEventBus);
      BossesRiseDataAttachments.REGISTRY.register(modEventBus);
      BossesRiseDataComponents.REGISTRAR.register(modEventBus);
      BossesRiseParticleTypes.REGISTRY.register(modEventBus);
      BossesRiseAttributes.REGISTRY.register(modEventBus);
      BossesRisePOI.REGISTRY.register(modEventBus);
      STRUCTURE_TYPES.register(modEventBus);
      BossesRiseRemapper.addRegistryAliases();
      GeckoLibUtil.addCustomBakedModelFactory("block_factorys_bosses", new CustomBakedModelFactory());
      NeoForge.EVENT_BUS.addListener(BossesRise::registerGeckoLibReloadListener);
      if (!DatagenModLoader.isRunningDataGen()) {
         if (FMLEnvironment.dist == Dist.CLIENT) {
            BedrockParticleEffectCache.registerReloadListener();
         }

         if (FMLEnvironment.dist != Dist.DEDICATED_SERVER) {
            BossesRiseAnimationCache.registerReloadListener();
         }
      }
   }

   public static void registerGeckoLibReloadListener(AddReloadListenerEvent event) {
      if (FMLEnvironment.dist == Dist.DEDICATED_SERVER) {
         event.addListener(GeckoLibCache::reload);
         event.addListener(BossesRiseAnimationCache::reload);
      }
   }

   public static <T extends CustomPacketPayload> void addNetworkMessage(
      Type<T> id, StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler
   ) {
      if (networkingRegistered) {
         throw new IllegalStateException("Cannot register new network messages after networking has been registered");
      }

      MESSAGES.put(id, new BossesRise.NetworkMessage(reader, handler));
   }

   private void registerNetworking(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("block_factorys_bosses");
      MESSAGES.forEach((id, networkMessage) -> registrar.playBidirectional(id, networkMessage.reader(), networkMessage.handler()));
      networkingRegistered = true;
   }

   public void init(FMLCommonSetupEvent evt) {
      evt.enqueueWork(() -> BurnUtil.regItemBurn((FireBlock)Blocks.FIRE));
   }

   public static void queueServerWork(int tick, Runnable action) {
      if (Thread.currentThread().getThreadGroup() == SidedThreadGroups.SERVER) {
         workQueue.add(new Tuple(action, tick));
      }
   }

   public static ResourceLocation prefix(String path) {
      return ResourceLocation.fromNamespaceAndPath("block_factorys_bosses", path);
   }

   @SubscribeEvent
   public void tick(Post event) {
      List<Tuple<Runnable, Integer>> actions = new ArrayList<>();
      workQueue.forEach(work -> {
         work.setB((Integer)work.getB() - 1);
         if ((Integer)work.getB() == 0) {
            actions.add((Tuple<Runnable, Integer>)work);
         }
      });
      actions.forEach(e -> ((Runnable)e.getA()).run());
      workQueue.removeAll(actions);
   }

   private record NetworkMessage<T extends CustomPacketPayload>(StreamCodec<? extends FriendlyByteBuf, T> reader, IPayloadHandler<T> handler) {
   }
}
