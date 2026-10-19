package toni.doesittick;

import com.google.common.base.Predicates;
import com.google.common.base.Supplier;
import com.google.common.base.Suppliers;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.FallingBlockEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.entity.monster.Slime;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.raid.Raider;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig.Type;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.loading.FMLLoader;
import net.neoforged.neoforge.common.ModConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec.BooleanValue;
import net.neoforged.neoforge.common.ModConfigSpec.Builder;
import net.neoforged.neoforge.common.ModConfigSpec.ConfigValue;
import net.neoforged.neoforge.common.ModConfigSpec.IntValue;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import toni.chunkactivitytracker.ChunkActivityTracker;
import toni.doesittick.api.Tickable;
import toni.doesittick.integration.FTBChunkClaimProvider;
import toni.doesittick.integration.IChunkClaimProvider;
import toni.doesittick.integration.OPACChunkClaimProvider;

@Mod("does_it_tick")
public class DoesItTick {
   public static final String MOD_ID = "does_it_tick";
   private static final boolean IS_FTB_CHUNKS_PRESENT = FMLLoader.getLoadingModList().getModFileById("ftbchunks") != null;
   private static final boolean IS_OPAC_PRESENT = FMLLoader.getLoadingModList().getModFileById("openpartiesandclaims") != null;
   @Nullable
   public static final IChunkClaimProvider FTB_CLAIM_PROVIDER = IS_FTB_CHUNKS_PRESENT ? new FTBChunkClaimProvider() : null;
   @Nullable
   public static final IChunkClaimProvider OPAC_CLAIM_PROVIDER = IS_OPAC_PRESENT ? new OPACChunkClaimProvider() : null;
   public static final ModConfigSpec COMMON_CONFIG;
   public static final IntValue LIVING_HORIZONTAL_TICK_DIST;
   public static final IntValue LIVING_VERTICAL_TICK_DIST;
   public static final BooleanValue DISABLE_ON_CLIENT;
   public static final BooleanValue DISABLE_IN_ACTIVE_CHUNKS;
   public static final BooleanValue OPTIMIZE_ITEM_MOVEMENT;
   public static final BooleanValue IGNORE_DEAD_ENTITIES;
   public static final BooleanValue IGNORE_HOSTILE_ENTITIES;
   public static final BooleanValue IGNORE_PASSIVE_ENTITIES;
   public static final BooleanValue TICKING_RAIDER_ENTITIES_IN_RAID;
   public static final BooleanValue OPTIMIZE_ENTITIES_TICKING;
   public static final BooleanValue SEND_MESSAGE;
   public static final ConfigValue<List<? extends String>> ENTITIES_WHITELIST;
   public static final ConfigValue<List<? extends String>> ITEMS_WHITELIST;
   public static final ConfigValue<List<? extends String>> ENTITIES_MOD_ID_WHITELIST;
   public static final ConfigValue<List<? extends String>> RAID_ENTITIES_WHITELIST;
   public static final ConfigValue<List<? extends String>> RAID_ENTITIES_MOD_ID_LIST;
   public static final ConfigValue<List<? extends String>> DIMENSION_WHITELIST;
   private static final Supplier<Set<Item>> ITEMS = Suppliers.memoize(
      () -> ((List)ITEMS_WHITELIST.get()).stream().map(s -> (Item)BuiltInRegistries.ITEM.get(resource(s))).collect(Collectors.toSet())
   );
   private static final Supplier<Boolean> ALL_DIMS_ALLOWED = Suppliers.memoize(() -> ((List)DIMENSION_WHITELIST.get()).isEmpty());
   private static final Supplier<Integer> MAX_HEIGHT;
   private static final Supplier<Integer> MAX_DIST_SQUARED = Suppliers.memoize(
      () -> (Integer)LIVING_HORIZONTAL_TICK_DIST.get() * (Integer)LIVING_HORIZONTAL_TICK_DIST.get()
   );

   public static ResourceLocation resource(String path) {
      return ResourceLocation.parse(path);
   }

   public DoesItTick(IEventBus modEventBus, ModContainer modContainer) {
      modContainer.registerConfig(Type.COMMON, COMMON_CONFIG);
   }

   public void onInitialize() {
   }

   public void commonSetup(FMLCommonSetupEvent event) {
      this.onInitialize();
   }

   public static boolean isTickable(@NotNull Entity entity) {
      if (entity instanceof Player) {
         return true;
      }

      if (!(Boolean)OPTIMIZE_ENTITIES_TICKING.get()) {
         return true;
      }

      Level level = entity.level();
      if (!isOptimizableDim(level)) {
         return true;
      }

      if ((Boolean)DISABLE_ON_CLIENT.get() && level.isClientSide) {
         return true;
      }

      if (entity instanceof FallingBlockEntity) {
         return true;
      }

      if ((Boolean)DISABLE_IN_ACTIVE_CHUNKS.get()) {
         ChunkPos chunkPos = entity.chunkPosition();

         for (int x = -2; x < 2; x++) {
            for (int z = -2; z < 2; z++) {
               long secondsInChunk = ChunkActivityTracker.getTotalTimeInChunk(level, new ChunkPos(chunkPos.x + x, chunkPos.z + z));
               if (secondsInChunk > 15L) {
                  return true;
               }
            }
         }
      }

      if (entity instanceof LivingEntity) {
         if (!(Boolean)IGNORE_DEAD_ENTITIES.get() && ((LivingEntity)entity).isDeadOrDying()) {
            return true;
         }

         boolean isMonster = entity instanceof Monster || entity instanceof Slime;
         if ((Boolean)IGNORE_HOSTILE_ENTITIES.get() && isMonster) {
            return true;
         }

         if ((Boolean)IGNORE_PASSIVE_ENTITIES.get() && !isMonster) {
            return true;
         }
      }

      if (!isOptimizableItemEntity(entity)) {
         BlockPos entityPos = entity.blockPosition();
         if (isInClaimedChunk(level, entityPos)) {
            return true;
         } else {
            EntityType<?> entityType = entity.getType();
            if (((Tickable.EntityType)entityType).doespotatotick$shouldAlwaysTick()) {
               return true;
            } else {
               return shouldTickInRaid(level, entityPos, entityType, entity) ? true : isNearPlayer(level, entityPos);
            }
         }
      } else {
         return ThreadLocalRandom.current().nextBoolean() || ThreadLocalRandom.current().nextBoolean();
      }
   }

   private static boolean shouldTickInRaid(Level level, BlockPos blockPos, EntityType<?> entityType, Entity entity) {
      if (!(level instanceof ServerLevel) || !((ServerLevel)level).isRaided(blockPos)) {
         return false;
      } else {
         return entity instanceof Raider
            ? (Boolean)TICKING_RAIDER_ENTITIES_IN_RAID.get()
            : ((Tickable.EntityType)entityType).doespotatotick$shouldAlwaysTickInRaid();
      }
   }

   private static boolean isOptimizableItemEntity(Entity entity) {
      if (!(Boolean)OPTIMIZE_ITEM_MOVEMENT.get()) {
         return false;
      } else {
         return entity instanceof ItemEntity ? !((Set)ITEMS.get()).contains(((ItemEntity)entity).getItem().getItem()) : false;
      }
   }

   private static boolean isInClaimedChunk(Level level, BlockPos pos) {
      boolean flag = false;
      if (FTB_CLAIM_PROVIDER != null) {
         flag = FTB_CLAIM_PROVIDER.isInClaimedChunk(level, pos);
      }

      if (OPAC_CLAIM_PROVIDER != null) {
         flag = flag || OPAC_CLAIM_PROVIDER.isInClaimedChunk(level, pos);
      }

      return flag;
   }

   private static boolean isOptimizableDim(Level level) {
      return ALL_DIMS_ALLOWED.get() ? true : ((Tickable.Level)level).doespotatotick$isInOptimizableDimension();
   }

   private static boolean isNearPlayer(@NotNull Level level, @NotNull BlockPos pos) {
      int posX = pos.getX();
      int posY = pos.getY();
      int posZ = pos.getZ();
      int maxHeight = (Integer)MAX_HEIGHT.get();
      int maxDistSquared = (Integer)MAX_DIST_SQUARED.get();

      for (Player player : level.players()) {
         if (Math.abs(player.getY() - posY) < maxHeight) {
            double x = player.getX() - posX;
            double z = player.getZ() - posZ;
            if (x * x + z * z < maxDistSquared) {
               return true;
            }
         }
      }

      return false;
   }

   static {
      List<? extends String> itemList = ObjectArrayList.wrap(new String[]{"minecraft:cobblestone"});
      List<? extends String> entityModIdList = ObjectArrayList.wrap(new String[]{"create", "witherstormmod"});
      List<? extends String> entityWhiteList = ObjectArrayList.wrap(
         new String[]{
            "minecraft:ender_dragon",
            "minecraft:ghast",
            "minecraft:wither",
            "minecraft:player",
            "alexsmobs:void_worm",
            "alexsmobs:void_worm_part",
            "alexsmobs:spectre",
            "twilightforest:naga",
            "twilightforest:lich",
            "twilightforest:yeti",
            "twilightforest:snow_queen",
            "twilightforest:minoshroom",
            "twilightforest:hydra",
            "twilightforest:knight_phantom",
            "twilightforest:ur_ghast",
            "atum:pharaoh",
            "mowziesmobs:barako",
            "mowziesmobs:ferrous_wroughtnaut",
            "mowziesmobs:frostmaw",
            "mowziesmobs:naga",
            "aoa3:skeletron",
            "aoa3:smash",
            "aoa3:baroness",
            "aoa3:clunkhead",
            "aoa3:corallus",
            "aoa3:cotton_candor",
            "aoa3:craexxeus",
            "aoa3:xxeus",
            "aoa3:creep",
            "aoa3:crystocore",
            "aoa3:dracyon",
            "aoa3:graw",
            "aoa3:gyro",
            "aoa3:hive_king",
            "aoa3:kajaros",
            "aoa3:miskel",
            "aoa3:harkos",
            "aoa3:raxxan",
            "aoa3:okazor",
            "aoa3:king_bambambam",
            "aoa3:king_shroomus",
            "aoa3:kror",
            "aoa3:mechbot",
            "aoa3:nethengeic_wither",
            "aoa3:red_guardian",
            "aoa3:blue_guardian",
            "aoa3:green_guardian",
            "aoa3:yellow_guardian",
            "aoa3:rock_rider",
            "aoa3:shadowlord",
            "aoa3:tyrosaur",
            "aoa3:vinecorne",
            "aoa3:visualent",
            "aoa3:voxxulon",
            "aoa3:bane",
            "aoa3:elusive",
            "gaiadimension:malachite_drone",
            "gaiadimension:malachite_guard",
            "blue_skies:alchemist",
            "blue_skies:arachnarch",
            "blue_skies:starlit_crusher",
            "blue_skies:summoner",
            "stalwart_dungeons:awful_ghast",
            "stalwart_dungeons:nether_keeper",
            "stalwart_dungeons:shelterer_without_armor",
            "dungeonsmod:extrapart",
            "dungeonsmod:king",
            "dungeonsmod:deserted",
            "dungeonsmod:crawler",
            "dungeonsmod:ironslime",
            "dungeonsmod:kraken",
            "dungeonsmod:voidmaster",
            "dungeonsmod:lordskeleton",
            "dungeonsmod:winterhunter",
            "dungeonsmod:sun",
            "forestcraft:beequeen",
            "forestcraft:iguana_king",
            "forestcraft:cosmic_fiend",
            "forestcraft:nether_scourge",
            "cataclysm:ender_golem",
            "cataclysm:ender_guardian",
            "cataclysm:ignis",
            "cataclysm:ignited_revenant",
            "cataclysm:netherite_monstrosity",
            "iceandfire:fire_dragon",
            "iceandfire:ice_dragon",
            "iceandfire:lightning_dragon",
            "iceandfire:dragon_multipart"
         }
      );
      Builder builder = new Builder();
      builder.comment("Does It Tick?").push("Living Entities Tick Settings");
      OPTIMIZE_ENTITIES_TICKING = builder.comment(
            "If you disable this, entities will not stop ticking when they'are far from you, this mod may be useless for you too"
         )
         .define("OptimizeEntitiesTicking", true);
      LIVING_HORIZONTAL_TICK_DIST = builder.defineInRange("LivingEntitiesMaxHorizontalTickDistance", 64, 1, Integer.MAX_VALUE);
      LIVING_VERTICAL_TICK_DIST = builder.defineInRange("LivingEntitiesMaxVerticalTickDistance", 32, 1, Integer.MAX_VALUE);
      ENTITIES_WHITELIST = builder.comment("If you don't want an entity to be affected by the optimization, you can write its registry name down here.")
         .defineList("EntitiesWhitelist", entityWhiteList, Predicates.alwaysTrue());
      ENTITIES_MOD_ID_WHITELIST = builder.comment("If you don't want entities of a mod to be affected by the optimization, you can write its modid down here")
         .defineList("EntitiesModIDWhiteList", entityModIdList, Predicates.alwaysTrue());
      TICKING_RAIDER_ENTITIES_IN_RAID = builder.comment(
            "With this turned on, all the raider won't stop ticking in raid chunks even if they are far from players (well this is not perfect as the raiders may walk out of the raid range)"
         )
         .define("TickRaidersInRaid", true);
      RAID_ENTITIES_WHITELIST = builder.comment("Similar with entity whitelist, but only take effect in raid.")
         .defineList("RaidEntitiesWhiteList", ObjectArrayList.wrap(new String[]{"minecraft:witch", "minecraft:vex"}), Predicates.alwaysTrue());
      RAID_ENTITIES_MOD_ID_LIST = builder.comment("Similar with entity modID whitelist, but only take effect in raid")
         .defineList("RaidEntitiesModIDWhiteList", new ObjectArrayList(), Predicates.alwaysTrue());
      DIMENSION_WHITELIST = builder.comment(
            new String[]{"Leave this empty for applying to all the dimensions", "Entities in these dimensions will be affected by the optimization"}
         )
         .defineList("DimensionWhitelist", new ObjectArrayList(), Predicates.alwaysTrue());
      IGNORE_DEAD_ENTITIES = builder.comment("If this is enabled, tickable check will run a lot faster, but the entity will not die out of range")
         .define("IgnoreDeadEntities", false);
      IGNORE_HOSTILE_ENTITIES = builder.comment("If this is enabled, this mod will only work on passive entities.").define("IgnoreHostileEntities", false);
      IGNORE_PASSIVE_ENTITIES = builder.comment("If this is enabled, this mod will only work on hostile entities.").define("IgnorePassiveEntities", false);
      builder.pop();
      builder.push("Item Entities Tick Settings");
      OPTIMIZE_ITEM_MOVEMENT = builder.comment("Slow down item entities' ticking speed by 1/4").define("OptimizeItemMovement", false);
      ITEMS_WHITELIST = builder.comment(
            new String[]{
               "If you don't want to let a specific item entity in the world to be effected by the optimization, you can write its registry name down here.",
               "Require 'OptimizeItemMovement' to be true"
            }
         )
         .defineList("ItemWhiteList", itemList, Predicates.alwaysTrue());
      builder.pop();
      builder.push("Misc");
      DISABLE_ON_CLIENT = builder.define("DisableOnClient", true);
      SEND_MESSAGE = builder.define("SendWarningMessageWhenPlayerLogIn", true);
      DISABLE_IN_ACTIVE_CHUNKS = builder.comment("If you disable this, entities near player bases may be affected.").define("DisableInActiveChunks", true);
      builder.pop();
      COMMON_CONFIG = builder.build();
      MAX_HEIGHT = Suppliers.memoize(LIVING_VERTICAL_TICK_DIST::get);
   }
}
