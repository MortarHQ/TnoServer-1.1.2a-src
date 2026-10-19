package twilightforest.events;

import com.google.common.collect.Maps;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.Sheep;
import net.minecraft.world.entity.monster.AbstractSkeleton;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Spider;
import net.minecraft.world.entity.monster.Zombie;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FireBlock;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import net.minecraft.world.level.levelgen.Heightmap.Types;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.InterModEnqueueEvent;
import net.neoforged.neoforge.capabilities.IBlockCapabilityProvider;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.AddReloadListenerEvent;
import net.neoforged.neoforge.event.BlockEntityTypeAddBlocksEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent;
import net.neoforged.neoforge.event.entity.RegisterSpawnPlacementsEvent.Operation;
import net.neoforged.neoforge.items.IItemHandler;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent.NewRegistry;
import net.neoforged.neoforge.registries.datamaps.RegisterDataMapTypesEvent;
import twilightforest.TFRegistries;
import twilightforest.TwilightForestMod;
import twilightforest.beanification.Autowired;
import twilightforest.beanification.Component;
import twilightforest.beanification.PostConstruct;
import twilightforest.block.entity.DryingRackBlockEntity;
import twilightforest.block.entity.JarBlockEntity;
import twilightforest.command.TFCommand;
import twilightforest.components.block.ChiseledCanopyBookshelfWrapper;
import twilightforest.config.ConfigSetup;
import twilightforest.data.custom.stalactites.entry.StalactiteReloadListener;
import twilightforest.dispenser.TFDispenserBehaviors;
import twilightforest.entity.MagicPaintingVariant;
import twilightforest.entity.RovingCube;
import twilightforest.entity.boss.AlphaYeti;
import twilightforest.entity.boss.Hydra;
import twilightforest.entity.boss.KnightPhantom;
import twilightforest.entity.boss.Lich;
import twilightforest.entity.boss.Minoshroom;
import twilightforest.entity.boss.Naga;
import twilightforest.entity.boss.PlateauBoss;
import twilightforest.entity.boss.SnowQueen;
import twilightforest.entity.boss.UrGhast;
import twilightforest.entity.monster.Adherent;
import twilightforest.entity.monster.BlockChainGoblin;
import twilightforest.entity.monster.CarminiteGhastguard;
import twilightforest.entity.monster.CarminiteGhastling;
import twilightforest.entity.monster.CarminiteGolem;
import twilightforest.entity.monster.DeathTome;
import twilightforest.entity.monster.FireBeetle;
import twilightforest.entity.monster.GiantMiner;
import twilightforest.entity.monster.HarbingerCube;
import twilightforest.entity.monster.HedgeSpider;
import twilightforest.entity.monster.HelmetCrab;
import twilightforest.entity.monster.HostileWolf;
import twilightforest.entity.monster.IceCrystal;
import twilightforest.entity.monster.KingSpider;
import twilightforest.entity.monster.Kobold;
import twilightforest.entity.monster.LowerGoblinKnight;
import twilightforest.entity.monster.LoyalZombie;
import twilightforest.entity.monster.MazeSlime;
import twilightforest.entity.monster.Minotaur;
import twilightforest.entity.monster.MistWolf;
import twilightforest.entity.monster.MosquitoSwarm;
import twilightforest.entity.monster.PinchBeetle;
import twilightforest.entity.monster.Redcap;
import twilightforest.entity.monster.RedcapSapper;
import twilightforest.entity.monster.SkeletonDruid;
import twilightforest.entity.monster.SlimeBeetle;
import twilightforest.entity.monster.SnowGuardian;
import twilightforest.entity.monster.StableIceCore;
import twilightforest.entity.monster.SwarmSpider;
import twilightforest.entity.monster.TowerBroodling;
import twilightforest.entity.monster.TowerwoodBorer;
import twilightforest.entity.monster.Troll;
import twilightforest.entity.monster.UnstableIceCore;
import twilightforest.entity.monster.UpperGoblinKnight;
import twilightforest.entity.monster.WinterWolf;
import twilightforest.entity.monster.Wraith;
import twilightforest.entity.monster.Yeti;
import twilightforest.entity.passive.Boar;
import twilightforest.entity.passive.Deer;
import twilightforest.entity.passive.DwarfRabbit;
import twilightforest.entity.passive.DwarfRabbitVariant;
import twilightforest.entity.passive.Penguin;
import twilightforest.entity.passive.QuestRam;
import twilightforest.entity.passive.Raven;
import twilightforest.entity.passive.Squirrel;
import twilightforest.entity.passive.TinyBird;
import twilightforest.entity.passive.TinyBirdVariant;
import twilightforest.entity.passive.quest.QuestReloadListener;
import twilightforest.init.TFBlockEntities;
import twilightforest.init.TFBlocks;
import twilightforest.init.TFCreativeTabs;
import twilightforest.init.TFDataMaps;
import twilightforest.init.TFEntities;
import twilightforest.init.TFItems;
import twilightforest.init.TFStats;
import twilightforest.init.custom.BiomeLayerStack;
import twilightforest.init.custom.ChunkBlanketProcessors;
import twilightforest.init.custom.TemplateMarkerHandlers;
import twilightforest.init.custom.TravellersModifiersManager;
import twilightforest.item.travellers_gear.modifiers.TravellersModifier;
import twilightforest.loot.modifiers.GiantToolGroupingModifier;
import twilightforest.network.AreaProtectionPacket;
import twilightforest.network.CreateMovingCicadaSoundPacket;
import twilightforest.network.CycleMapSlotPacket;
import twilightforest.network.EnforceProgressionStatusPacket;
import twilightforest.network.GogglesZoomPacket;
import twilightforest.network.GradualGlidePacket;
import twilightforest.network.LifedrainParticlePacket;
import twilightforest.network.MagicMapPacket;
import twilightforest.network.MazeMapPacket;
import twilightforest.network.MissingAdvancementToastPacket;
import twilightforest.network.MovePlayerPacket;
import twilightforest.network.ParticlePacket;
import twilightforest.network.PerformDoubleJumpPacket;
import twilightforest.network.PerformSidestepPacket;
import twilightforest.network.SetMasonJarItemPacket;
import twilightforest.network.SpawnCharmPacket;
import twilightforest.network.SpawnFallenLeafFromPacket;
import twilightforest.network.StructureProtectionPacket;
import twilightforest.network.SwapHotbarPacket;
import twilightforest.network.SyncQuestsPacket;
import twilightforest.network.SyncUncraftingTableConfigPacket;
import twilightforest.network.TFBossBarPacket;
import twilightforest.network.TravellersWingsStatePacket;
import twilightforest.network.UncraftingGuiPacket;
import twilightforest.network.UpdateDeathTimePacket;
import twilightforest.network.UpdateTFMultipartPacket;
import twilightforest.network.UpdateThrownPacket;
import twilightforest.network.WipeOreMeterPacket;
import twilightforest.util.HolidayEvent;
import twilightforest.util.Restriction;
import twilightforest.util.woods.WoodPalette;
import twilightforest.world.components.biomesources.TFBiomeProvider;
import twilightforest.world.components.layer.BiomeDensitySource;
import twilightforest.world.components.structures.StructureSpeleothemConfig;
import twilightforest.world.components.structures.util.StructureTemplateDefinitions;
import twilightforest.world.components.structures.util.TemplateMarkerHandlerList;

@Component
public class RegistrationEvents {
   @Autowired
   private TFCommand tfCommand;
   @Autowired
   private HolidayEvent holidayEvent;
   @Autowired
   private StructureTemplateDefinitions structureTemplateDefinitions;

   @PostConstruct
   private void setup(IEventBus bus) {
      bus.addListener(this::init);
      bus.addListener(this::sendIMCs);
      bus.addListener(this::setupPackets);
      bus.addListener(this::createDataMaps);
      bus.addListener(this::registerExtraStuff);
      bus.addListener(this::createNewRegistries);
      bus.addListener(this::addBlockEntityTypes);
      bus.addListener(this::setRegistriesForDatapack);
      bus.addListener(this::registerGenericItemHandlers);
      bus.addListener(this::addEntityAttributes);
      bus.addListener(this::registerSpawnPlacements);
      bus.addListener(TFCreativeTabs::addToTabs);
      bus.addListener(ConfigSetup::loadConfigs);
      bus.addListener(ConfigSetup::reloadConfigs);
      NeoForge.EVENT_BUS.addListener(this::registerCommands);
      NeoForge.EVENT_BUS.addListener(AddReloadListenerEvent.class, event -> event.addListener(new QuestReloadListener()));
      NeoForge.EVENT_BUS
         .addListener(AddReloadListenerEvent.class, event -> event.addListener(TravellersModifiersManager.CacheInvalidationReloadListener.INSTANCE));
      NeoForge.EVENT_BUS.addListener(StalactiteReloadListener.INSTANCE::registerListener);
      NeoForge.EVENT_BUS.addListener(this.structureTemplateDefinitions::registerListener);
      NeoForge.EVENT_BUS.addListener(ConfigSetup::syncUncraftingConfig);
   }

   private void registerGenericItemHandlers(RegisterCapabilitiesEvent event) {
      IBlockCapabilityProvider<IItemHandler, Direction> itemHandlerProvider = (level, pos, state, blockEntity, side) -> level.getBlockEntity(pos) instanceof ChestBlockEntity tfChestBlock
         ? new InvWrapper(tfChestBlock)
         : null;
      event.registerBlock(
         ItemHandler.BLOCK,
         itemHandlerProvider,
         new Block[]{
            (Block)TFBlocks.TWILIGHT_OAK_CHEST.get(),
            (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
            (Block)TFBlocks.CANOPY_CHEST.get(),
            (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
            (Block)TFBlocks.MANGROVE_CHEST.get(),
            (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
            (Block)TFBlocks.DARK_CHEST.get(),
            (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
            (Block)TFBlocks.TIME_CHEST.get(),
            (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
            (Block)TFBlocks.TRANSFORMATION_CHEST.get(),
            (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
            (Block)TFBlocks.MINING_CHEST.get(),
            (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
            (Block)TFBlocks.SORTING_CHEST.get(),
            (Block)TFBlocks.SORTING_TRAPPED_CHEST.get()
         }
      );
      event.registerBlockEntity(
         ItemHandler.BLOCK,
         (BlockEntityType)TFBlockEntities.MASON_JAR.get(),
         (masonJarBlock, side) -> side == Direction.UP ? masonJarBlock.getItemHandler() : null
      );
      event.registerBlockEntity(
         ItemHandler.BLOCK, (BlockEntityType)TFBlockEntities.DRYING_RACK.get(), (entity, side) -> new DryingRackBlockEntity.DryingRackHandler(entity)
      );
      event.registerBlockEntity(
         ItemHandler.BLOCK, (BlockEntityType)TFBlockEntities.CHISELED_CANOPY_BOOKSHELF.get(), (entity, side) -> new ChiseledCanopyBookshelfWrapper(entity)
      );
   }

   public void addBlockEntityTypes(BlockEntityTypeAddBlocksEvent event) {
      event.modify(
         BlockEntityType.HANGING_SIGN,
         new Block[]{
            (Block)TFBlocks.TWILIGHT_OAK_HANGING_SIGN.get(),
            (Block)TFBlocks.TWILIGHT_OAK_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.CANOPY_HANGING_SIGN.get(),
            (Block)TFBlocks.CANOPY_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.MANGROVE_HANGING_SIGN.get(),
            (Block)TFBlocks.MANGROVE_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.DARK_HANGING_SIGN.get(),
            (Block)TFBlocks.DARK_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.TIME_HANGING_SIGN.get(),
            (Block)TFBlocks.TIME_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.TRANSFORMATION_HANGING_SIGN.get(),
            (Block)TFBlocks.TRANSFORMATION_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.MINING_HANGING_SIGN.get(),
            (Block)TFBlocks.MINING_WALL_HANGING_SIGN.get(),
            (Block)TFBlocks.SORTING_HANGING_SIGN.get(),
            (Block)TFBlocks.SORTING_WALL_HANGING_SIGN.get()
         }
      );
      event.modify(
         BlockEntityType.SIGN,
         new Block[]{
            (Block)TFBlocks.TWILIGHT_OAK_SIGN.get(),
            (Block)TFBlocks.TWILIGHT_WALL_SIGN.get(),
            (Block)TFBlocks.CANOPY_SIGN.get(),
            (Block)TFBlocks.CANOPY_WALL_SIGN.get(),
            (Block)TFBlocks.MANGROVE_SIGN.get(),
            (Block)TFBlocks.MANGROVE_WALL_SIGN.get(),
            (Block)TFBlocks.DARK_SIGN.get(),
            (Block)TFBlocks.DARK_WALL_SIGN.get(),
            (Block)TFBlocks.TIME_SIGN.get(),
            (Block)TFBlocks.TIME_WALL_SIGN.get(),
            (Block)TFBlocks.TRANSFORMATION_SIGN.get(),
            (Block)TFBlocks.TRANSFORMATION_WALL_SIGN.get(),
            (Block)TFBlocks.MINING_SIGN.get(),
            (Block)TFBlocks.MINING_WALL_SIGN.get(),
            (Block)TFBlocks.SORTING_SIGN.get(),
            (Block)TFBlocks.SORTING_WALL_SIGN.get()
         }
      );
      event.modify(
         BlockEntityType.CHEST,
         new Block[]{
            (Block)TFBlocks.TWILIGHT_OAK_CHEST.get(),
            (Block)TFBlocks.CANOPY_CHEST.get(),
            (Block)TFBlocks.MANGROVE_CHEST.get(),
            (Block)TFBlocks.DARK_CHEST.get(),
            (Block)TFBlocks.TIME_CHEST.get(),
            (Block)TFBlocks.TRANSFORMATION_CHEST.get(),
            (Block)TFBlocks.MINING_CHEST.get(),
            (Block)TFBlocks.SORTING_CHEST.get()
         }
      );
      event.modify(
         BlockEntityType.TRAPPED_CHEST,
         new Block[]{
            (Block)TFBlocks.TWILIGHT_OAK_TRAPPED_CHEST.get(),
            (Block)TFBlocks.CANOPY_TRAPPED_CHEST.get(),
            (Block)TFBlocks.MANGROVE_TRAPPED_CHEST.get(),
            (Block)TFBlocks.DARK_TRAPPED_CHEST.get(),
            (Block)TFBlocks.TIME_TRAPPED_CHEST.get(),
            (Block)TFBlocks.TRANSFORMATION_TRAPPED_CHEST.get(),
            (Block)TFBlocks.MINING_TRAPPED_CHEST.get(),
            (Block)TFBlocks.SORTING_TRAPPED_CHEST.get()
         }
      );
   }

   public void createNewRegistries(NewRegistryEvent event) {
      event.register(TFRegistries.BIOME_LAYER_TYPE);
      event.register(TFRegistries.ENFORCEMENT);
      event.register(TFRegistries.CHUNK_BLANKET_TYPES);
      event.register(TFRegistries.TEMPLATE_MARKER_HANDLER_TYPES);
      event.register(TFRegistries.ITEM_DISPLAY_TYPE);
      event.register(TFRegistries.TRAVELLERS_MODIFIER_TYPE);
   }

   public void setRegistriesForDatapack(NewRegistry event) {
      event.dataPackRegistry(TFRegistries.Keys.WOOD_PALETTES, WoodPalette.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.BIOME_STACK, BiomeLayerStack.DISPATCH_CODEC);
      event.dataPackRegistry(TFRegistries.Keys.BIOME_TERRAIN_DATA, BiomeDensitySource.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.RESTRICTIONS, Restriction.CODEC, Restriction.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.MAGIC_PAINTINGS, MagicPaintingVariant.CODEC, MagicPaintingVariant.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.STRUCTURE_SPELEOTHEM_SETTINGS, StructureSpeleothemConfig.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.CHUNK_BLANKET_PROCESSORS, ChunkBlanketProcessors.DISPATCH_CODEC);
      event.dataPackRegistry(TFRegistries.Keys.TEMPLATE_MARKER_HANDLER, TemplateMarkerHandlers.DISPATCH_CODEC);
      event.dataPackRegistry(TFRegistries.Keys.TEMPLATE_MARKER_HANDLER_LIST, TemplateMarkerHandlerList.CODEC);
      event.dataPackRegistry(TFRegistries.Keys.DWARF_RABBIT_VARIANT, DwarfRabbitVariant.DIRECT_CODEC, DwarfRabbitVariant.DIRECT_CODEC);
      event.dataPackRegistry(TFRegistries.Keys.TINY_BIRD_VARIANT, TinyBirdVariant.DIRECT_CODEC, TinyBirdVariant.DIRECT_CODEC);
      event.dataPackRegistry(TFRegistries.Keys.TRAVELLERS_MODIFIERS, TravellersModifier.CODEC, TravellersModifier.CODEC);
   }

   public void registerExtraStuff(RegisterEvent evt) {
      if (evt.getRegistryKey().equals(Registries.BIOME_SOURCE)) {
         Registry.register(BuiltInRegistries.BIOME_SOURCE, TwilightForestMod.prefix("twilight_biomes"), TFBiomeProvider.TF_CODEC);
      }
   }

   public void createDataMaps(RegisterDataMapTypesEvent event) {
      event.register(TFDataMaps.CRUMBLE_HORN);
      event.register(TFDataMaps.TRANSFORMATION_POWDER);
      event.register(TFDataMaps.OMINOUS_FIRE);
      event.register(TFDataMaps.MAGIC_MAP_BIOME_COLOR);
      event.register(TFDataMaps.ORE_MAP_ORE_COLOR);
   }

   public void sendIMCs(InterModEnqueueEvent event) {
   }

   public void setupPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("twilightforest").versioned("1.0.0").optional();
      registrar.playToClient(AreaProtectionPacket.TYPE, AreaProtectionPacket.STREAM_CODEC, AreaProtectionPacket::handle);
      registrar.playToClient(CreateMovingCicadaSoundPacket.TYPE, CreateMovingCicadaSoundPacket.STREAM_CODEC, CreateMovingCicadaSoundPacket::handle);
      registrar.playToClient(EnforceProgressionStatusPacket.TYPE, EnforceProgressionStatusPacket.STREAM_CODEC, EnforceProgressionStatusPacket::handle);
      registrar.playToClient(MagicMapPacket.TYPE, MagicMapPacket.STREAM_CODEC, MagicMapPacket::handle);
      registrar.playToClient(MazeMapPacket.TYPE, MazeMapPacket.STREAM_CODEC, MazeMapPacket::handle);
      registrar.playToClient(MissingAdvancementToastPacket.TYPE, MissingAdvancementToastPacket.STREAM_CODEC, MissingAdvancementToastPacket::handle);
      registrar.playToClient(MovePlayerPacket.TYPE, MovePlayerPacket.STREAM_CODEC, MovePlayerPacket::handle);
      registrar.playToClient(ParticlePacket.TYPE, ParticlePacket.STREAM_CODEC, ParticlePacket::handle);
      registrar.playBidirectional(GogglesZoomPacket.TYPE, GogglesZoomPacket.STREAM_CODEC, GogglesZoomPacket::handle);
      registrar.playBidirectional(GradualGlidePacket.TYPE, GradualGlidePacket.STREAM_CODEC, GradualGlidePacket::handle);
      registrar.playToServer(PerformDoubleJumpPacket.TYPE, PerformDoubleJumpPacket.STREAM_CODEC, PerformDoubleJumpPacket::handle);
      registrar.playToServer(SwapHotbarPacket.TYPE, SwapHotbarPacket.STREAM_CODEC, SwapHotbarPacket::handle);
      registrar.playToServer(PerformSidestepPacket.TYPE, PerformSidestepPacket.STREAM_CODEC, PerformSidestepPacket::handle);
      registrar.playToServer(CycleMapSlotPacket.TYPE, CycleMapSlotPacket.STREAM_CODEC, CycleMapSlotPacket::handle);
      registrar.playToClient(SpawnCharmPacket.TYPE, SpawnCharmPacket.STREAM_CODEC, SpawnCharmPacket::handle);
      registrar.playToClient(SpawnFallenLeafFromPacket.TYPE, SpawnFallenLeafFromPacket.STREAM_CODEC, SpawnFallenLeafFromPacket::handle);
      registrar.playToClient(StructureProtectionPacket.TYPE, StructureProtectionPacket.STREAM_CODEC, StructureProtectionPacket::handle);
      registrar.playToClient(SyncUncraftingTableConfigPacket.TYPE, SyncUncraftingTableConfigPacket.STREAM_CODEC, SyncUncraftingTableConfigPacket::handle);
      registrar.playToServer(UncraftingGuiPacket.TYPE, UncraftingGuiPacket.STREAM_CODEC, UncraftingGuiPacket::handle);
      registrar.playToClient(UpdateTFMultipartPacket.TYPE, UpdateTFMultipartPacket.STREAM_CODEC, UpdateTFMultipartPacket::handle);
      registrar.playToClient(UpdateThrownPacket.TYPE, UpdateThrownPacket.STREAM_CODEC, UpdateThrownPacket::handle);
      registrar.playToServer(WipeOreMeterPacket.TYPE, WipeOreMeterPacket.STREAM_CODEC, WipeOreMeterPacket::handle);
      registrar.playToClient(LifedrainParticlePacket.TYPE, LifedrainParticlePacket.STREAM_CODEC, LifedrainParticlePacket::handle);
      registrar.playToClient(UpdateDeathTimePacket.TYPE, UpdateDeathTimePacket.STREAM_CODEC, UpdateDeathTimePacket::handle);
      registrar.playToClient(
         TFBossBarPacket.AddTFBossBarPacket.TYPE, TFBossBarPacket.AddTFBossBarPacket.STREAM_CODEC, TFBossBarPacket.AddTFBossBarPacket::handle
      );
      registrar.playToClient(
         TFBossBarPacket.UpdateTFBossBarStylePacket.TYPE,
         TFBossBarPacket.UpdateTFBossBarStylePacket.STREAM_CODEC,
         TFBossBarPacket.UpdateTFBossBarStylePacket::handle
      );
      registrar.playToClient(SetMasonJarItemPacket.TYPE, SetMasonJarItemPacket.STREAM_CODEC, SetMasonJarItemPacket::handle);
      registrar.playToClient(SyncQuestsPacket.TYPE, SyncQuestsPacket.STREAM_CODEC, SyncQuestsPacket::handle);
      registrar.playToClient(TravellersWingsStatePacket.TYPE, TravellersWingsStatePacket.STREAM_CODEC, TravellersWingsStatePacket::handle);
   }

   public void init(FMLCommonSetupEvent evt) {
      evt.enqueueWork(() -> {
         TFDispenserBehaviors.init();
         TFStats.init();
         CauldronInteraction.WATER.map().put((Item)TFItems.ARCTIC_HELMET.get(), CauldronInteraction.DYED_ITEM);
         CauldronInteraction.WATER.map().put((Item)TFItems.ARCTIC_CHESTPLATE.get(), CauldronInteraction.DYED_ITEM);
         CauldronInteraction.WATER.map().put((Item)TFItems.ARCTIC_LEGGINGS.get(), CauldronInteraction.DYED_ITEM);
         CauldronInteraction.WATER.map().put((Item)TFItems.ARCTIC_BOOTS.get(), CauldronInteraction.DYED_ITEM);
         AxeItem.STRIPPABLES = Maps.newHashMap(AxeItem.STRIPPABLES);
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TWILIGHT_OAK_LOG.get(), (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.CANOPY_LOG.get(), (Block)TFBlocks.STRIPPED_CANOPY_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.MANGROVE_LOG.get(), (Block)TFBlocks.STRIPPED_MANGROVE_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.DARK_LOG.get(), (Block)TFBlocks.STRIPPED_DARK_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TIME_LOG.get(), (Block)TFBlocks.STRIPPED_TIME_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TRANSFORMATION_LOG.get(), (Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.MINING_LOG.get(), (Block)TFBlocks.STRIPPED_MINING_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.SORTING_LOG.get(), (Block)TFBlocks.STRIPPED_SORTING_LOG.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TWILIGHT_OAK_WOOD.get(), (Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.CANOPY_WOOD.get(), (Block)TFBlocks.STRIPPED_CANOPY_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.MANGROVE_WOOD.get(), (Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.DARK_WOOD.get(), (Block)TFBlocks.STRIPPED_DARK_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TIME_WOOD.get(), (Block)TFBlocks.STRIPPED_TIME_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.TRANSFORMATION_WOOD.get(), (Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.MINING_WOOD.get(), (Block)TFBlocks.STRIPPED_MINING_WOOD.get());
         AxeItem.STRIPPABLES.put((Block)TFBlocks.SORTING_WOOD.get(), (Block)TFBlocks.STRIPPED_SORTING_WOOD.get());
         FlowerPotBlock pot = (FlowerPotBlock)Blocks.FLOWER_POT;
         pot.addPlant(TFBlocks.TWILIGHT_OAK_SAPLING.getId(), TFBlocks.POTTED_TWILIGHT_OAK_SAPLING);
         pot.addPlant(TFBlocks.CANOPY_SAPLING.getId(), TFBlocks.POTTED_CANOPY_SAPLING);
         pot.addPlant(TFBlocks.MANGROVE_SAPLING.getId(), TFBlocks.POTTED_MANGROVE_SAPLING);
         pot.addPlant(TFBlocks.DARKWOOD_SAPLING.getId(), TFBlocks.POTTED_DARKWOOD_SAPLING);
         pot.addPlant(TFBlocks.HOLLOW_OAK_SAPLING.getId(), TFBlocks.POTTED_HOLLOW_OAK_SAPLING);
         pot.addPlant(TFBlocks.RAINBOW_OAK_SAPLING.getId(), TFBlocks.POTTED_RAINBOW_OAK_SAPLING);
         pot.addPlant(TFBlocks.TIME_SAPLING.getId(), TFBlocks.POTTED_TIME_SAPLING);
         pot.addPlant(TFBlocks.TRANSFORMATION_SAPLING.getId(), TFBlocks.POTTED_TRANSFORMATION_SAPLING);
         pot.addPlant(TFBlocks.MINING_SAPLING.getId(), TFBlocks.POTTED_MINING_SAPLING);
         pot.addPlant(TFBlocks.SORTING_SAPLING.getId(), TFBlocks.POTTED_SORTING_SAPLING);
         pot.addPlant(TFBlocks.MAYAPPLE.getId(), TFBlocks.POTTED_MAYAPPLE);
         pot.addPlant(TFBlocks.FIDDLEHEAD.getId(), TFBlocks.POTTED_FIDDLEHEAD);
         pot.addPlant(TFBlocks.MUSHGLOOM.getId(), TFBlocks.POTTED_MUSHGLOOM);
         pot.addPlant(TFBlocks.BROWN_THORNS.getId(), TFBlocks.POTTED_THORN);
         pot.addPlant(TFBlocks.GREEN_THORNS.getId(), TFBlocks.POTTED_GREEN_THORN);
         pot.addPlant(TFBlocks.BURNT_THORNS.getId(), TFBlocks.POTTED_DEAD_THORN);
         FireBlock fireblock = (FireBlock)Blocks.FIRE;
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TWILIGHT_OAK_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TWILIGHT_OAK_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_CANOPY_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_CANOPY_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CANOPY_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CANOPY_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CANOPY_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_BOOKSHELF.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_MANGROVE_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_MANGROVE_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MANGROVE_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MANGROVE_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MANGROVE_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_ROOT.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.DARK_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_DARK_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_DARK_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.DARK_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.DARK_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TIME_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TIME_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TIME_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TIME_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TIME_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TIME_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TIME_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TIME_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TRANSFORMATION_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_TRANSFORMATION_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_TRANSFORMATION_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.MINING_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_MINING_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_MINING_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MINING_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MINING_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_MINING_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.MINING_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.MINING_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.SORTING_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.SORTING_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_SORTING_LOG.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.STRIPPED_SORTING_WOOD.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SORTING_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SORTING_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SORTING_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.SORTING_BANISTER.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.SORTING_PLANKS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.SORTING_SLAB.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.SORTING_STAIRS.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.SORTING_FENCE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.SORTING_GATE.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.RASPBERRY_BUSH.get(), 4, 25);
         fireblock.setFlammable((Block)TFBlocks.BLUEBERRY_BUSH.get(), 4, 25);
         fireblock.setFlammable((Block)TFBlocks.BLACKBERRY_BUSH.get(), 4, 25);
         fireblock.setFlammable((Block)TFBlocks.MALOBERRY_BUSH.get(), 4, 25);
         fireblock.setFlammable((Block)TFBlocks.CLOVER_PATCH.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.FALLEN_LEAVES.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.FIDDLEHEAD.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.MAYAPPLE.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.MOSS_PATCH.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.ROOT_STRAND.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.TORCHBERRY_PLANT.get(), 60, 100);
         fireblock.setFlammable((Block)TFBlocks.ROOT_BLOCK.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.ARCTIC_FUR_BLOCK.get(), 20, 20);
         fireblock.setFlammable((Block)TFBlocks.LIVEROOT_BLOCK.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.CHISELED_CANOPY_BOOKSHELF.get(), 30, 20);
         fireblock.setFlammable((Block)TFBlocks.HUGE_STALK.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.TOWERWOOD.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.CRACKED_TOWERWOOD.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.MOSSY_TOWERWOOD.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.ENCASED_TOWERWOOD.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.INFESTED_TOWERWOOD.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.TWILIGHT_OAK_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.CANOPY_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.MANGROVE_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.DARK_LEAVES.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.HARDENED_DARK_LEAVES.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.TIME_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.TRANSFORMATION_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.MINING_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.SORTING_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.BEANSTALK_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.THORN_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.RAINBOW_OAK_LEAVES.get(), 30, 60);
         fireblock.setFlammable((Block)TFBlocks.HARDENED_DARK_LEAVES.get(), 0, 1);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_OAK_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_OAK_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_OAK_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.OAK_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SPRUCE_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SPRUCE_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_SPRUCE_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.SPRUCE_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_BIRCH_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_BIRCH_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_BIRCH_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.BIRCH_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_JUNGLE_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_JUNGLE_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_JUNGLE_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.JUNGLE_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_ACACIA_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_ACACIA_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_ACACIA_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.ACACIA_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_DARK_OAK_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.DARK_OAK_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CRIMSON_STEM_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CRIMSON_STEM_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CRIMSON_STEM_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.CRIMSON_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_WARPED_STEM_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_WARPED_STEM_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_WARPED_STEM_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.WARPED_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_VANGROVE_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_VANGROVE_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_VANGROVE_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.VANGROVE_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CHERRY_LOG_HORIZONTAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CHERRY_LOG_VERTICAL.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.HOLLOW_CHERRY_LOG_CLIMBABLE.get(), 5, 5);
         fireblock.setFlammable((Block)TFBlocks.CHERRY_BANISTER.get(), 5, 20);
         fireblock.setFlammable((Block)TFBlocks.BAMBOO_BANISTER.get(), 5, 20);
         GiantToolGroupingModifier.CONVERSIONS.put(Blocks.COBBLESTONE, ((Block)TFBlocks.GIANT_COBBLESTONE.get()).asItem());
         GiantToolGroupingModifier.CONVERSIONS.put(Blocks.OAK_LOG, ((Block)TFBlocks.GIANT_LOG.get()).asItem());
         GiantToolGroupingModifier.CONVERSIONS.put(Blocks.OAK_LEAVES, ((Block)TFBlocks.GIANT_LEAVES.get()).asItem());
         GiantToolGroupingModifier.CONVERSIONS.put(Blocks.OBSIDIAN, ((Block)TFBlocks.GIANT_OBSIDIAN.get()).asItem());
         JarBlockEntity.addLid(TFBlocks.MANGROVE_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.CANOPY_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.DARK_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.MINING_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.SORTING_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.TIME_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.TRANSFORMATION_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.TWILIGHT_OAK_LOG.asItem());
         JarBlockEntity.addLid(Items.ACACIA_LOG);
         JarBlockEntity.addLid(Items.BIRCH_LOG);
         JarBlockEntity.addLid(Items.CHERRY_LOG);
         JarBlockEntity.addLid(Items.DARK_OAK_LOG);
         JarBlockEntity.addLid(Items.JUNGLE_LOG);
         JarBlockEntity.addLid(Items.MANGROVE_LOG);
         JarBlockEntity.addLid(Items.OAK_LOG);
         JarBlockEntity.addLid(Items.SPRUCE_LOG);
         JarBlockEntity.addLid(Items.CRIMSON_STEM);
         JarBlockEntity.addLid(Items.WARPED_STEM);
         JarBlockEntity.addLid(TFBlocks.STRIPPED_MANGROVE_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_CANOPY_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_DARK_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_MINING_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_SORTING_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_TIME_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_TRANSFORMATION_LOG.asItem());
         JarBlockEntity.addLid(TFBlocks.STRIPPED_TWILIGHT_OAK_LOG.asItem());
         JarBlockEntity.addLid(Items.STRIPPED_ACACIA_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_BIRCH_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_CHERRY_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_DARK_OAK_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_JUNGLE_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_MANGROVE_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_OAK_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_SPRUCE_LOG);
         JarBlockEntity.addLid(Items.STRIPPED_CRIMSON_STEM);
         JarBlockEntity.addLid(Items.STRIPPED_WARPED_STEM);
         JarBlockEntity.addLid(TFBlocks.CINDER_LOG.asItem());
         JarBlockEntity.addLid(Items.BAMBOO_BLOCK);
         JarBlockEntity.addLid(Items.STRIPPED_BAMBOO_BLOCK);
         JarBlockEntity.addLid(Items.PUMPKIN, () -> this.holidayEvent.isHalloweenWeek());
      });
   }

   private void registerCommands(RegisterCommandsEvent event) {
      this.tfCommand.register(event.getDispatcher(), event.getBuildContext());
   }

   private void registerSpawnPlacements(RegisterSpawnPlacementsEvent event) {
      event.register(
         (EntityType)TFEntities.BOAR.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.BIGHORN_SHEEP.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Animal::checkAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.DEER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.REDCAP.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SKELETON_DRUID.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         SkeletonDruid::checkDruidSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.WRAITH.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Wraith::checkMonsterSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.HOSTILE_WOLF.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         HostileWolf::checkWolfSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.HYDRA.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.LICH.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.PENGUIN.get(), SpawnPlacementTypes.NO_RESTRICTIONS, Types.MOTION_BLOCKING_NO_LEAVES, Penguin::canSpawn, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.LICH_MINION.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.LOYAL_ZOMBIE.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Mob::checkMobSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.TINY_BIRD.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Animal::checkAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SQUIRREL.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Animal::checkAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.DWARF_RABBIT.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Animal::checkAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.RAVEN.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Animal::checkAnimalSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.QUEST_RAM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Animal::checkAnimalSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.KOBOLD.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.MOSQUITO_SWARM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.DEATH_TOME.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.MINOTAUR.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.MINOSHROOM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.FIRE_BEETLE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SLIME_BEETLE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.PINCH_BEETLE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.MIST_WOLF.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.CARMINITE_GHASTLING.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         CarminiteGhastling::canSpawnHere,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.CARMINITE_GOLEM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.TOWERWOOD_BORER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.CARMINITE_GHASTGUARD.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         CarminiteGhastguard::ghastSpawnHandler,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.UR_GHAST.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.UPPER_GOBLIN_KNIGHT.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.HELMET_CRAB.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.KNIGHT_PHANTOM.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Mob::checkMobSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.NAGA.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SWARM_SPIDER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         SwarmSpider::getCanSpawnHere,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.KING_SPIDER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.CARMINITE_BROODLING.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.HEDGE_SPIDER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, HedgeSpider::canSpawn, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.REDCAP_SAPPER.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.MAZE_SLIME.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, MazeSlime::getCanSpawnHere, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.YETI.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Yeti::yetiSnowyForestSpawnHandler,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.ALPHA_YETI.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.WINTER_WOLF.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, WinterWolf::canSpawnHere, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SNOW_GUARDIAN.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.STABLE_ICE_CORE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.UNSTABLE_ICE_CORE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.SNOW_QUEEN.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.TROLL.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, Monster::checkMonsterSpawnRules, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.GIANT_MINER.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GiantMiner::canSpawn, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.ARMORED_GIANT.get(), SpawnPlacementTypes.ON_GROUND, Types.MOTION_BLOCKING_NO_LEAVES, GiantMiner::canSpawn, Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.ICE_CRYSTAL.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.HARBINGER_CUBE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.ADHERENT.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.ROVING_CUBE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
      event.register(
         (EntityType)TFEntities.RISING_ZOMBIE.get(),
         SpawnPlacementTypes.ON_GROUND,
         Types.MOTION_BLOCKING_NO_LEAVES,
         Monster::checkMonsterSpawnRules,
         Operation.REPLACE
      );
   }

   private void addEntityAttributes(EntityAttributeCreationEvent event) {
      event.put((EntityType)TFEntities.BOAR.get(), Boar.registerAttributes().build());
      event.put((EntityType)TFEntities.BIGHORN_SHEEP.get(), Sheep.createAttributes().build());
      event.put((EntityType)TFEntities.DEER.get(), Deer.registerAttributes().build());
      event.put((EntityType)TFEntities.REDCAP.get(), Redcap.registerAttributes().build());
      event.put((EntityType)TFEntities.SWARM_SPIDER.get(), SwarmSpider.registerAttributes().build());
      event.put((EntityType)TFEntities.NAGA.get(), Naga.registerAttributes().build());
      event.put((EntityType)TFEntities.SKELETON_DRUID.get(), AbstractSkeleton.createAttributes().build());
      event.put((EntityType)TFEntities.HOSTILE_WOLF.get(), HostileWolf.registerAttributes().build());
      event.put((EntityType)TFEntities.WRAITH.get(), Wraith.registerAttributes().build());
      event.put((EntityType)TFEntities.HEDGE_SPIDER.get(), Spider.createAttributes().build());
      event.put((EntityType)TFEntities.HYDRA.get(), Hydra.registerAttributes().build());
      event.put((EntityType)TFEntities.LICH.get(), Lich.registerAttributes().build());
      event.put((EntityType)TFEntities.PENGUIN.get(), Penguin.registerAttributes().build());
      event.put((EntityType)TFEntities.LICH_MINION.get(), Zombie.createAttributes().build());
      event.put((EntityType)TFEntities.LOYAL_ZOMBIE.get(), LoyalZombie.registerAttributes().build());
      event.put((EntityType)TFEntities.TINY_BIRD.get(), TinyBird.registerAttributes().build());
      event.put((EntityType)TFEntities.SQUIRREL.get(), Squirrel.registerAttributes().build());
      event.put((EntityType)TFEntities.DWARF_RABBIT.get(), DwarfRabbit.registerAttributes().build());
      event.put((EntityType)TFEntities.RAVEN.get(), Raven.registerAttributes().build());
      event.put((EntityType)TFEntities.QUEST_RAM.get(), QuestRam.registerAttributes().build());
      event.put((EntityType)TFEntities.KOBOLD.get(), Kobold.registerAttributes().build());
      event.put((EntityType)TFEntities.MOSQUITO_SWARM.get(), MosquitoSwarm.registerAttributes().build());
      event.put((EntityType)TFEntities.DEATH_TOME.get(), DeathTome.registerAttributes().build());
      event.put((EntityType)TFEntities.MINOTAUR.get(), Minotaur.registerAttributes().build());
      event.put((EntityType)TFEntities.MINOSHROOM.get(), Minoshroom.registerAttributes().build());
      event.put((EntityType)TFEntities.FIRE_BEETLE.get(), FireBeetle.registerAttributes().build());
      event.put((EntityType)TFEntities.SLIME_BEETLE.get(), SlimeBeetle.registerAttributes().build());
      event.put((EntityType)TFEntities.PINCH_BEETLE.get(), PinchBeetle.registerAttributes().build());
      event.put((EntityType)TFEntities.MAZE_SLIME.get(), Monster.createMonsterAttributes().build());
      event.put((EntityType)TFEntities.REDCAP_SAPPER.get(), RedcapSapper.registerAttributes().build());
      event.put((EntityType)TFEntities.MIST_WOLF.get(), MistWolf.registerAttributes().build());
      event.put((EntityType)TFEntities.KING_SPIDER.get(), KingSpider.registerAttributes().build());
      event.put((EntityType)TFEntities.CARMINITE_GHASTLING.get(), CarminiteGhastling.registerAttributes().build());
      event.put((EntityType)TFEntities.CARMINITE_GHASTGUARD.get(), CarminiteGhastguard.registerAttributes().build());
      event.put((EntityType)TFEntities.CARMINITE_GOLEM.get(), CarminiteGolem.registerAttributes().build());
      event.put((EntityType)TFEntities.TOWERWOOD_BORER.get(), TowerwoodBorer.registerAttributes().build());
      event.put((EntityType)TFEntities.CARMINITE_BROODLING.get(), TowerBroodling.registerAttributes().build());
      event.put((EntityType)TFEntities.UR_GHAST.get(), UrGhast.registerAttributes().build());
      event.put((EntityType)TFEntities.BLOCKCHAIN_GOBLIN.get(), BlockChainGoblin.registerAttributes().build());
      event.put((EntityType)TFEntities.UPPER_GOBLIN_KNIGHT.get(), UpperGoblinKnight.registerAttributes().build());
      event.put((EntityType)TFEntities.LOWER_GOBLIN_KNIGHT.get(), LowerGoblinKnight.registerAttributes().build());
      event.put((EntityType)TFEntities.HELMET_CRAB.get(), HelmetCrab.registerAttributes().build());
      event.put((EntityType)TFEntities.KNIGHT_PHANTOM.get(), KnightPhantom.registerAttributes().build());
      event.put((EntityType)TFEntities.YETI.get(), Yeti.registerAttributes().build());
      event.put((EntityType)TFEntities.ALPHA_YETI.get(), AlphaYeti.registerAttributes().build());
      event.put((EntityType)TFEntities.WINTER_WOLF.get(), WinterWolf.registerAttributes().build());
      event.put((EntityType)TFEntities.SNOW_GUARDIAN.get(), SnowGuardian.registerAttributes().build());
      event.put((EntityType)TFEntities.STABLE_ICE_CORE.get(), StableIceCore.registerAttributes().build());
      event.put((EntityType)TFEntities.UNSTABLE_ICE_CORE.get(), UnstableIceCore.registerAttributes().build());
      event.put((EntityType)TFEntities.SNOW_QUEEN.get(), SnowQueen.registerAttributes().build());
      event.put((EntityType)TFEntities.TROLL.get(), Troll.registerAttributes().build());
      event.put((EntityType)TFEntities.GIANT_MINER.get(), GiantMiner.registerAttributes().build());
      event.put((EntityType)TFEntities.ARMORED_GIANT.get(), GiantMiner.registerAttributes().build());
      event.put((EntityType)TFEntities.ICE_CRYSTAL.get(), IceCrystal.registerAttributes().build());
      event.put((EntityType)TFEntities.HARBINGER_CUBE.get(), HarbingerCube.registerAttributes().build());
      event.put((EntityType)TFEntities.ADHERENT.get(), Adherent.registerAttributes().build());
      event.put((EntityType)TFEntities.ROVING_CUBE.get(), RovingCube.registerAttributes().build());
      event.put((EntityType)TFEntities.PLATEAU_BOSS.get(), PlateauBoss.registerAttributes().build());
      event.put((EntityType)TFEntities.RISING_ZOMBIE.get(), Zombie.createAttributes().build());
   }
}
