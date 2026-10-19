package net.unusual.block_factorys_bosses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.unusual.block_factorys_bosses.block.CoinPileBlock;

public class BlockFactorysBossesModTabs {
   public static final DeferredRegister<CreativeModeTab> REGISTRY = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "block_factorys_bosses");
   public static final DeferredHolder<CreativeModeTab, CreativeModeTab> BLOCKFACTORYSBOSSESTAB = REGISTRY.register(
      "blockfactorysbossestab",
      () -> CreativeModeTab.builder()
         .title(Component.translatable("item_group.block_factorys_bosses.blockfactorysbossestab"))
         .icon(() -> new ItemStack((ItemLike)BossesRiseBlocks.DRAGON_SKULL.get()))
         .displayItems((parameters, tabData) -> {
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_VARIATION.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_STRAIGHT.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_CRACKED.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_BROKEN.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_WET.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_WET_VARIATION.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_WET_STRAIGHT.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_WET_CRACKED.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_OAK_PLANKS_WET_BROKEN.get());
            tabData.accept((ItemLike)BossesRiseItems.GUARDRAIL.get());
            tabData.accept((ItemLike)BossesRiseItems.GUARDRAIL_DIAGONAL.get());
            tabData.accept((ItemLike)BossesRiseItems.PLANK.get());
            tabData.accept((ItemLike)BossesRiseItems.CRATE.get());
            tabData.accept((ItemLike)BossesRiseItems.ANCHOR_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.SHIP_LANTERN.get());
            tabData.accept(((Block)BossesRiseBlocks.DRAGON_BANNER.get()).asItem());
            tabData.accept(((CoinPileBlock)BossesRiseBlocks.COIN_PILE.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.ROPE_ROLL.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.SHIP_STEERING_WHEEL.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.CANNONBALL.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.NET.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.VASE.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.TALL_VASE.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.WALL_TORCH.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.UNDERWOLD_WALL_TORCH.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.CANDLES.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.TALL_CANDLES.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.TALL_CANDLES_CROSS.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.UNDERWOLD_CANDLES.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.UNDERWORLD_TALL_CANDLES.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.UNDER_WORLD_TALL_CANDLE_CROSS.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.BONE_REMAINS.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.BONE_REMAINS_LEGS.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.BONE_REMAINS_RIB_CAGE.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.CORPSE.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.BIG_CHAIN.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.PRISON_DOOR.get()).asItem());
            tabData.accept(((Block)BossesRiseBlocks.RUSTY_PRISON_DOOR.get()).asItem());
            tabData.accept((ItemLike)BossesRiseItems.CAGE_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.CAGE_SKELLY_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_CAGE_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.BIG_CAGE_SKELLY_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.UNDERWORLD_ARENA_DOOR.get());
            tabData.accept((ItemLike)BossesRiseItems.KNIGHT_HELMET.get());
            tabData.accept((ItemLike)BossesRiseItems.KNIGHT_CHESTPLATE.get());
            tabData.accept((ItemLike)BossesRiseItems.KNIGHT_LEGGINGS.get());
            tabData.accept((ItemLike)BossesRiseItems.KNIGHT_BOOTS.get());
            tabData.accept((ItemLike)BossesRiseItems.ENHANCED_SHIELD.get());
            tabData.accept((ItemLike)BossesRiseItems.LARGE_SWORD.get());
            tabData.accept((ItemLike)BossesRiseItems.WARRIOR_SWORD.get());
            tabData.accept((ItemLike)BossesRiseItems.DAGGER.get());
            tabData.accept((ItemLike)BossesRiseItems.KNIGHT_SWORD.get());
            tabData.accept(BossesRiseItems.PIRATE_SABER);
            tabData.accept((ItemLike)BossesRiseItems.KRAKEN_TOOTH.get());
            tabData.accept((ItemLike)BossesRiseItems.KRAKEN_TRIDENT.get());
            tabData.accept((ItemLike)BossesRiseItems.UNDYING_TENTACLE.get());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_GUARD_SHIELD.get());
            tabData.accept((ItemLike)BossesRiseItems.SANDWORM_GAUNTLET.get());
            tabData.accept((ItemLike)BossesRiseItems.ICE_GAUNTLET.get());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_BONE.get());
            tabData.accept(((Block)BossesRiseBlocks.DRAGON_SKULL.get()).asItem());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_BONES_CHESTPLATE.get());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_BONES_LEGGINGS.get());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_BONES_BOOTS.get());
            tabData.accept((ItemLike)BossesRiseItems.UNDERWORLD_ARENA_KEY.get());
            tabData.accept((ItemLike)BossesRiseItems.ANCIENT_TRIAL_KEY.get());
            tabData.accept((ItemLike)BossesRiseItems.KRAKEN_CANNON_ITEM.get());
            tabData.accept((ItemLike)BossesRiseItems.INFERNAL_DRAGON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.SANDWORM_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.YETI_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.UNDERWORLD_KNIGHT_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.KRAKEN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.DRAGON_GUARD_SWORD_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.FLAMING_SKELETON_GUARD_SWORD_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.FLAMING_SKELETON_GUARD_FIREBALL_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.SOUL_SKELETON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.SOUL_KNIGHT_WITHER_SKELETON_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.PIRATE_CAPTAIN_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.PIRATE_ROOK_SPAWN_EGG.get());
            tabData.accept((ItemLike)BossesRiseItems.CROSSBOW_PIRATE_SPAWN_EGG.get());
            if (parameters.hasPermissions()) {
               tabData.accept((ItemLike)BossesRiseItems.KRAKEN_SPAWNER.get());
            }
         })
         .build()
   );
}
