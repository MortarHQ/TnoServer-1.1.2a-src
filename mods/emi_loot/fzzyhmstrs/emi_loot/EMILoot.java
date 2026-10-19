package fzzyhmstrs.emi_loot;

import com.google.gson.JsonElement;
import fzzyhmstrs.emi_loot.client.ClientLootTables;
import fzzyhmstrs.emi_loot.client.ClientLootTablesReceiver;
import fzzyhmstrs.emi_loot.networking.ArchaeologyLootPayload;
import fzzyhmstrs.emi_loot.networking.BlockLootPayload;
import fzzyhmstrs.emi_loot.networking.ChestLootPayload;
import fzzyhmstrs.emi_loot.networking.ClearPayload;
import fzzyhmstrs.emi_loot.networking.GameplayLootPayload;
import fzzyhmstrs.emi_loot.networking.MobLootPayload;
import fzzyhmstrs.emi_loot.parser.LootTableParser;
import fzzyhmstrs.emi_loot.server.ServerResourceData;
import fzzyhmstrs.emi_loot.util.TextKey;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.function.Supplier;
import me.fzzyhmstrs.fzzy_config.annotations.Action;
import me.fzzyhmstrs.fzzy_config.annotations.ConvertFrom;
import me.fzzyhmstrs.fzzy_config.annotations.IgnoreVisibility;
import me.fzzyhmstrs.fzzy_config.annotations.NonSync;
import me.fzzyhmstrs.fzzy_config.annotations.RequiresAction;
import me.fzzyhmstrs.fzzy_config.annotations.Version;
import me.fzzyhmstrs.fzzy_config.api.ConfigApi;
import me.fzzyhmstrs.fzzy_config.api.ConfigApiJava;
import me.fzzyhmstrs.fzzy_config.api.RegisterType;
import me.fzzyhmstrs.fzzy_config.config.Config;
import me.fzzyhmstrs.fzzy_config.util.FcText;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedList;
import me.fzzyhmstrs.fzzy_config.validation.collection.ValidatedSet;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedAny;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedChoice;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedEnum;
import me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedString;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedInt;
import me.fzzyhmstrs.fzzy_config.validation.number.ValidatedNumber.WidgetType;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Registry;
import net.minecraft.resources.RegistryOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.SingleThreadedRandomSource;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;
import net.minecraft.world.level.storage.loot.predicates.LootItemConditionType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EMILoot {
   public static final String MOD_ID = "emi_loot";
   public static final Logger LOGGER = LoggerFactory.getLogger("emi_loot");
   public static RandomSource emiLootRandom = new SingleThreadedRandomSource(System.currentTimeMillis());
   public static LootTableParser parser = new LootTableParser();
   public static EMILoot.EmiLootConfig config = (EMILoot.EmiLootConfig)ConfigApiJava.registerAndLoadConfig(EMILoot.EmiLootConfig::new, RegisterType.BOTH);
   public static boolean DEBUG = config.debugMode;
   public static Supplier<LootItemConditionType> WITHER_KILL;
   public static Supplier<LootItemConditionType> SPAWNS_WITH;
   public static Supplier<LootItemConditionType> CREEPER;
   public static Supplier<LootItemFunctionType<?>> SET_ANY_DAMAGE;
   public static Supplier<LootItemFunctionType<?>> OMINOUS_BANNER;

   public static ResourceLocation identity(String path) {
      return ResourceLocation.fromNamespaceAndPath("emi_loot", path);
   }

   public static void onInitialize() {
      ConfigApi.INSTANCE.network().registerS2C(ClearPayload.TYPE, ClearPayload.CODEC, (payload, ctx) -> ClientLootTables.INSTANCE.clearLoots());
      ConfigApi.INSTANCE.network().registerS2C(ChestLootPayload.TYPE, ChestLootPayload.CODEC, ClientLootTablesReceiver::receiveChestSender);
      ConfigApi.INSTANCE.network().registerS2C(BlockLootPayload.TYPE, BlockLootPayload.CODEC, ClientLootTablesReceiver::receiveBlockSender);
      ConfigApi.INSTANCE.network().registerS2C(MobLootPayload.TYPE, MobLootPayload.CODEC, ClientLootTablesReceiver::receiveMobSender);
      ConfigApi.INSTANCE.network().registerS2C(GameplayLootPayload.TYPE, GameplayLootPayload.CODEC, ClientLootTablesReceiver::receiveGameplaySender);
      ConfigApi.INSTANCE.network().registerS2C(ArchaeologyLootPayload.TYPE, ArchaeologyLootPayload.CODEC, ClientLootTablesReceiver::receiveArchaeologySender);
   }

   public static void parseTables(ResourceManager resourceManager, Registry<LootTable> lootManager, RegistryOps<JsonElement> ops) {
      LootTableParser.registryOps = ops;
      ServerResourceData.loadDirectTables(resourceManager, ops);
      LootTableParser.parseLootTables(lootManager);
   }

   @IgnoreVisibility
   private static class CompactLoot {
      public boolean block = true;
      public boolean chest = true;
      public boolean mob = true;
      public boolean gameplay = true;
      public boolean archaeology = true;
   }

   @IgnoreVisibility
   private static class DebugMode {
      public boolean block = false;
      public boolean chest = false;
      public boolean mob = false;
      public boolean gameplay = false;
      public boolean archaeology = false;
   }

   @IgnoreVisibility
   private static class DirectDrops {
      @NonSync
      public boolean chestLoot = true;
      @NonSync
      public boolean blockLoot = true;
      @NonSync
      public boolean mobLoot = true;
      @NonSync
      public boolean gameplayLoot = true;
      @NonSync
      public boolean archaeologyLoot = true;
   }

   @Version(version = 1)
   @IgnoreVisibility
   @ConvertFrom(fileName = "EmiLootConfig_v1.json")
   public static class EmiLootConfig extends Config {
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean debugMode = false;
      @RequiresAction(action = Action.RELOAD_DATA)
      private ValidatedAny<EMILoot.DebugMode> debugModes = new ValidatedAny(new EMILoot.DebugMode());
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean parseChestLoot = true;
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean parseBlockLoot = true;
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean parseMobLoot = true;
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean parseGameplayLoot = true;
      @RequiresAction(action = Action.RELOAD_DATA)
      public boolean parseArchaeologyLoot = true;
      @RequiresAction(action = Action.RELOAD_DATA)
      public Set<String> skippedKeys = new ValidatedSet(TextKey.defaultSkips, ValidatedString.fromList(TextKey.keys().stream().toList()));
      @NonSync
      private ValidatedAny<EMILoot.CompactLoot> compactLoot = new ValidatedAny(new EMILoot.CompactLoot());
      @NonSync
      private ValidatedAny<EMILoot.LogUntranslatedTables> logUntranslatedTables = new ValidatedAny(new EMILoot.LogUntranslatedTables());
      @NonSync
      public boolean chestLootAlwaysStackSame = false;
      @NonSync
      @RequiresAction(action = Action.RELOAD_RESOURCES)
      public boolean splitChestLootByTrial = false;
      @NonSync
      private ValidatedAny<EMILoot.DirectDrops> directDrops = new ValidatedAny(new EMILoot.DirectDrops());
      @NonSync
      public ValidatedInt chanceDecimalPlaces = new ValidatedInt(1, 10, 0, WidgetType.SLIDER);
      @NonSync
      private ValidatedChoice<String> conditionStyle = EMILootAgnos.isModLoaded("symbols_n_stuff")
         ? new ValidatedChoice(
            List.of("tooltip", "plain", "default"),
            new ValidatedString(),
            (t, u) -> FcText.INSTANCE.translate(u + "." + t, new Object[0]),
            (t, u) -> FcText.INSTANCE.translate(u + "." + t, new Object[0]),
            me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedChoice.WidgetType.CYCLING
         )
         : new ValidatedChoice(
            List.of("default", "tooltip", "plain"),
            new ValidatedString(),
            (t, u) -> FcText.INSTANCE.translate(u + "." + t + ".sns", new Object[0]),
            (t, u) -> FcText.INSTANCE.translate(u + "." + t + ".sns", new Object[0]),
            me.fzzyhmstrs.fzzy_config.validation.misc.ValidatedChoice.WidgetType.CYCLING
         );
      @NonSync
      private ValidatedList<ChatFormatting> linkFormatting = new ValidatedEnum(ChatFormatting.UNDERLINE).toList(new ChatFormatting[]{ChatFormatting.UNDERLINE});

      EmiLootConfig() {
         super(ResourceLocation.fromNamespaceAndPath("emi_loot", "emi_loot_config"), "", "");
      }

      public boolean isTooltipStyle() {
         return Objects.equals(this.conditionStyle.get(), "tooltip") || Objects.equals(this.conditionStyle.get(), "plain");
      }

      public boolean isNotPlain() {
         return (!Objects.equals(this.conditionStyle.get(), "tooltip") || EMILootAgnos.isModLoaded("symbols_n_stuff"))
            && !Objects.equals(this.conditionStyle.get(), "plain");
      }

      public boolean isCompact(EMILoot.Type type) {
         return type.compactLootSupplier.getAsBoolean();
      }

      public boolean isDebug(EMILoot.Type type) {
         return type.debugModeSupplier.getAsBoolean();
      }

      public boolean isLogI18n(EMILoot.Type type) {
         return type.logUntranslatedTablesSupplier.getAsBoolean();
      }

      public boolean doDirectDrops(EMILoot.Type type) {
         return type.doDirectDrops.getAsBoolean();
      }

      public ChatFormatting[] getLinkFormatting() {
         return (ChatFormatting[])this.linkFormatting.toArray(new ChatFormatting[this.linkFormatting.getSize()]);
      }
   }

   @IgnoreVisibility
   private static class LogUntranslatedTables {
      public boolean chest = EMILootAgnos.isDevelopmentEnvironment();
      public boolean gameplay = EMILootAgnos.isDevelopmentEnvironment();
      public boolean archaeology = EMILootAgnos.isDevelopmentEnvironment();
   }

   public enum Type {
      BLOCK(
         () -> ((EMILoot.CompactLoot)EMILoot.config.compactLoot.get()).block,
         () -> ((EMILoot.DebugMode)EMILoot.config.debugModes.get()).block,
         () -> false,
         () -> ((EMILoot.DirectDrops)EMILoot.config.directDrops.get()).blockLoot
      ),
      CHEST(
         () -> ((EMILoot.CompactLoot)EMILoot.config.compactLoot.get()).chest,
         () -> ((EMILoot.DebugMode)EMILoot.config.debugModes.get()).chest,
         () -> ((EMILoot.LogUntranslatedTables)EMILoot.config.logUntranslatedTables.get()).chest,
         () -> ((EMILoot.DirectDrops)EMILoot.config.directDrops.get()).chestLoot
      ),
      MOB(
         () -> ((EMILoot.CompactLoot)EMILoot.config.compactLoot.get()).mob,
         () -> ((EMILoot.DebugMode)EMILoot.config.debugModes.get()).mob,
         () -> false,
         () -> ((EMILoot.DirectDrops)EMILoot.config.directDrops.get()).mobLoot
      ),
      GAMEPLAY(
         () -> ((EMILoot.CompactLoot)EMILoot.config.compactLoot.get()).gameplay,
         () -> ((EMILoot.DebugMode)EMILoot.config.debugModes.get()).gameplay,
         () -> ((EMILoot.LogUntranslatedTables)EMILoot.config.logUntranslatedTables.get()).gameplay,
         () -> ((EMILoot.DirectDrops)EMILoot.config.directDrops.get()).gameplayLoot
      ),
      ARCHAEOLOGY(
         () -> ((EMILoot.CompactLoot)EMILoot.config.compactLoot.get()).archaeology,
         () -> ((EMILoot.DebugMode)EMILoot.config.debugModes.get()).archaeology,
         () -> ((EMILoot.LogUntranslatedTables)EMILoot.config.logUntranslatedTables.get()).archaeology,
         () -> ((EMILoot.DirectDrops)EMILoot.config.directDrops.get()).archaeologyLoot
      );

      final BooleanSupplier compactLootSupplier;
      final BooleanSupplier debugModeSupplier;
      final BooleanSupplier logUntranslatedTablesSupplier;
      final BooleanSupplier doDirectDrops;

      Type(BooleanSupplier compactLootSupplier, BooleanSupplier debugModeSupplier, BooleanSupplier logUntranslatedTablesSupplier, BooleanSupplier doDirectDrops) {
         this.compactLootSupplier = compactLootSupplier;
         this.debugModeSupplier = debugModeSupplier;
         this.logUntranslatedTablesSupplier = logUntranslatedTablesSupplier;
         this.doDirectDrops = doDirectDrops;
      }
   }
}
