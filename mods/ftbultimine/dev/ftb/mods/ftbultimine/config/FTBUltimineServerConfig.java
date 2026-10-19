package dev.ftb.mods.ftbultimine.config;

import dev.architectury.utils.GameInstance;
import dev.ftb.mods.ftblibrary.snbt.config.BooleanValue;
import dev.ftb.mods.ftblibrary.snbt.config.DoubleValue;
import dev.ftb.mods.ftblibrary.snbt.config.IntValue;
import dev.ftb.mods.ftblibrary.snbt.config.LongValue;
import dev.ftb.mods.ftblibrary.snbt.config.SNBTConfig;
import dev.ftb.mods.ftblibrary.snbt.config.StringListValue;
import dev.ftb.mods.ftblibrary.util.NetworkHelper;
import dev.ftb.mods.ftbultimine.FTBUltimine;
import dev.ftb.mods.ftbultimine.integration.IntegrationHandler;
import dev.ftb.mods.ftbultimine.integration.ranks.FTBRanksIntegration;
import dev.ftb.mods.ftbultimine.net.SyncUltimineTimePacket;
import dev.ftb.mods.ftbultimine.registry.ModAttributes;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;

public interface FTBUltimineServerConfig {
   String KEY = "ftbultimine-server";
   SNBTConfig CONFIG = (SNBTConfig)SNBTConfig.create("ftbultimine-server")
      .comment(
         new String[]{
            "Server-specific configuration for FTB Ultimine",
            "Modpack defaults should be defined in <instance>/config/ftbultimine-server.snbt",
            "  (may be overwritten on modpack update)",
            "Server admins may locally override this by copying into <instance>/world/serverconfig/ftbultimine-server.snbt",
            "  (will NOT be overwritten on modpack update)"
         }
      );
   SNBTConfig FEATURES = CONFIG.addGroup("features");
   BooleanValue RIGHT_CLICK_AXE = (BooleanValue)FEATURES.addBoolean("right_click_axe", true)
      .comment(new String[]{"Right-click with an axe with the Ultimine key held to strip multiple logs and scrape/unwax copper blocks"});
   BooleanValue RIGHT_CLICK_SHOVEL = (BooleanValue)FEATURES.addBoolean("right_click_shovel", true)
      .comment(new String[]{"Right-click with a shovel with the Ultimine key held to flatten multiple grass/dirt blocks into dirt paths"});
   BooleanValue RIGHT_CLICK_HOE = (BooleanValue)FEATURES.addBoolean("right_click_hoe", true)
      .comment(new String[]{"Right-click with a hoe with the Ultimine key held to till multiple grass/dirt blocks into farmland"});
   BooleanValue RIGHT_CLICK_HARVESTING = (BooleanValue)FEATURES.addBoolean("right_click_harvesting", true)
      .comment(new String[]{"Right-click crops with the Ultimine key held to harvest multiple crop blocks"});
   BooleanValue RIGHT_CLICK_CRYSTALS = (BooleanValue)FEATURES.addBoolean("right_click_crystals", true)
      .comment(
         new String[]{
            "Right-click budding crystals (e.g. amethyst, AE2 certus) with the Ultimine key held to harvest multiple crystals",
            "FTB EZ Crystals must also be installed"
         }
      );
   BooleanValue SINGLE_CROP_HARVESTING = (BooleanValue)FEATURES.addBoolean("single_crop_harvesting", true)
      .comment(new String[]{"When true, right-clicking a crop block without the Ultimine key held harvests it"});
   SNBTConfig COSTS_LIMITS = CONFIG.addGroup("costs_limits");
   IntValue MAX_BLOCKS = (IntValue)COSTS_LIMITS.addInt("max_blocks", 64)
      .range(32768)
      .comment(
         new String[]{
            "Max amount of blocks that can be ultimined at once",
            "If FTB Ranks is installed, the 'ftbultimine.max_blocks' ranks node can override this",
            "This value can also be modified with the 'ftbultimine:max_blocks_modifier' entity attribute"
         }
      );
   DoubleValue EXHAUSTION_PER_BLOCK = (DoubleValue)COSTS_LIMITS.addDouble("exhaustion_per_block", 20.0)
      .range(10000.0)
      .comment(new String[]{"Hunger multiplier for each block ultimined (fractional values allowed)"});
   DoubleValue EXPERIENCE_PER_BLOCK = (DoubleValue)COSTS_LIMITS.addDouble("experience_per_block", 0.0)
      .range(20000.0)
      .comment(new String[]{"Amount of experience taken per block ultimined (fractional values allowed)"});
   BooleanValue REQUIRE_TOOL = (BooleanValue)COSTS_LIMITS.addBoolean("require_tool", false)
      .comment(new String[]{"Require a damageable tool, or an item in the 'ftbultimine:tools' tag, to ultimine."});
   BooleanValue REQUIRE_VALID_TOOL_FOR_BLOCK = (BooleanValue)COSTS_LIMITS.addBoolean("require_valid_tool_for_block", false)
      .comment(new String[]{"Require a valid tool for the block being ultimined, i.e. one that can properly harvest the block"});
   LongValue ULTIMINE_COOLDOWN = (LongValue)COSTS_LIMITS.addLong("ultimine_cooldown", 0L, 0L, Long.MAX_VALUE)
      .comment(
         new String[]{
            "Cooldown in ticks between successive uses of the ultimine feature",
            "If FTB Ranks is installed, the 'ftbultimine.ultimine_cooldown' ranks node can override this",
            "This value can also be modified with the 'ftbultimine:cooldown_modifier' entity attribute"
         }
      );
   SNBTConfig MISC = CONFIG.addGroup("misc");
   FTBUltimineServerConfig.BlockTagsConfig MERGE_TAGS_SHAPELESS = new FTBUltimineServerConfig.BlockTagsConfig(
      MISC,
      "merge_tags",
      new ArrayList<>(List.of("minecraft:base_stone_overworld", "c:ores/*", "forge:ores/*")),
      "These tags will be considered the same block when checking for blocks to Ultimine in shapeless mining mode"
   );
   FTBUltimineServerConfig.BlockTagsConfig MERGE_TAGS_SHAPED = new FTBUltimineServerConfig.BlockTagsConfig(
      MISC,
      "merge_tags_shaped",
      new ArrayList<>(List.of("*")),
      "These tags will be considered the same block when checking for blocks to Ultimine in shaped mining modes"
   );
   IntValue PREVENT_TOOL_BREAK = (IntValue)MISC.addInt("prevent_tool_break", 0, 0, 100)
      .comment(
         new String[]{
            "This will stop mining if tool reaches X durability. It's possible this won't work with some modded tools if they use non-standard durability handling."
         }
      );
   BooleanValue CANCEL_ON_BLOCK_BREAK_FAIL = (BooleanValue)MISC.addBoolean("cancel_on_block_break_fail", false)
      .comment(new String[]{"If a block couldn't be broken (even though it should be), stop ultimining immediately instead of skipping to the next block."});

   static void onConfigChanged(boolean isServerSide) {
      if (isServerSide) {
         MERGE_TAGS_SHAPELESS.tags = null;
         MERGE_TAGS_SHAPED.tags = null;
         if ((Integer)MAX_BLOCKS.get() > 8192) {
            FTBUltimine.LOGGER.warn("'max_blocks' server config setting is set to more than 8192 blocks; this may cause performance issues!");
         }

         if (GameInstance.getServer() != null) {
            GameInstance.getServer()
               .getPlayerList()
               .getPlayers()
               .forEach(sp -> NetworkHelper.sendTo(sp, new SyncUltimineTimePacket(getUltimineCooldown(sp), SyncUltimineTimePacket.TimeType.COOLDOWN)));
         }
      }
   }

   static int getMaxBlocks(ServerPlayer player) {
      int max = IntegrationHandler.ranksMod ? FTBRanksIntegration.getMaxBlocks(player, (Integer)MAX_BLOCKS.get()) : (Integer)MAX_BLOCKS.get();
      return Math.max(0, max + (int)Math.round(getAttrSafe(player, (Holder<Attribute>)ModAttributes.FixedHolder.MAX_BLOCKS_MODIFIER.get())));
   }

   static long getUltimineCooldown(ServerPlayer player) {
      long cooldown = IntegrationHandler.ranksMod
         ? FTBRanksIntegration.getUltimineCooldown(player, (Long)ULTIMINE_COOLDOWN.get())
         : (Long)ULTIMINE_COOLDOWN.get();
      return Math.max(0L, cooldown + Math.round(getAttrSafe(player, (Holder<Attribute>)ModAttributes.FixedHolder.COOLDOWN_MODIFIER.get())));
   }

   static double getExhaustionPerBlock(ServerPlayer player) {
      double ex = IntegrationHandler.ranksMod ? FTBRanksIntegration.getExhaustionPerBlock(player) : (Double)EXHAUSTION_PER_BLOCK.get();
      return Math.max(0.0, ex + getAttrSafe(player, (Holder<Attribute>)ModAttributes.FixedHolder.EXHAUSTION_MODIFIER.get()));
   }

   static double getExperiencePerBlock(ServerPlayer player) {
      double ex = IntegrationHandler.ranksMod ? FTBRanksIntegration.getExperiencePerBlock(player) : (Double)EXPERIENCE_PER_BLOCK.get();
      return Math.max(0.0, ex + getAttrSafe(player, (Holder<Attribute>)ModAttributes.FixedHolder.EXPERIENCE_MODIFIER.get()));
   }

   private static double getAttrSafe(ServerPlayer player, Holder<Attribute> attr) {
      return player.getAttributes().hasAttribute(attr) ? player.getAttributeValue(attr) : 0.0;
   }

   class BlockTagsConfig {
      private final StringListValue value;
      private Set<TagKey<Block>> tags = null;
      private boolean matchAny = false;

      public BlockTagsConfig(SNBTConfig parent, String name, List<String> defaults, String... comment) {
         this.value = (StringListValue)parent.addStringList(name, defaults).comment(comment);
      }

      public boolean match(BlockState original, BlockState toTest) {
         Collection<TagKey<Block>> tags = this.getTags();
         return this.matchAny && !toTest.isAir() && !(toTest.getBlock() instanceof LiquidBlock) && !(original.getBlock() instanceof EntityBlock)
            || tags.stream().filter(original::is).anyMatch(toTest::is);
      }

      public Collection<TagKey<Block>> getTags() {
         if (this.tags == null) {
            if (((List)this.value.get()).contains("*")) {
               this.matchAny = true;
               this.tags = Collections.emptySet();
            } else {
               this.tags = new HashSet<>();
               ((List)this.value.get()).forEach(s -> {
                  ResourceLocation rl = ResourceLocation.tryParse(s);
                  if (rl != null) {
                     this.tags.add(TagKey.create(Registries.BLOCK, rl));
                  } else {
                     Pattern pattern = regexFromGlobString(s);
                     BuiltInRegistries.BLOCK.getTags().forEach(tag -> {
                        if (pattern.asPredicate().test(((TagKey)tag.getFirst()).location().toString())) {
                           this.tags.add((TagKey<Block>)tag.getFirst());
                        }
                     });
                  }
               });
            }
         }

         return this.tags;
      }

      private static Pattern regexFromGlobString(String glob) {
         StringBuilder sb = new StringBuilder();
         sb.append("^");

         for (int i = 0; i < glob.length(); i++) {
            char c = glob.charAt(i);
            if (c == '*') {
               sb.append(".*");
            } else if (c == '?') {
               sb.append(".");
            } else if (c == '.') {
               sb.append("\\.");
            } else if (c == '\\') {
               sb.append("\\\\");
            } else {
               sb.append(c);
            }
         }

         sb.append("$");
         return Pattern.compile(sb.toString());
      }
   }
}
