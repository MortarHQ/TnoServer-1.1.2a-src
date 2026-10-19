package com.iafenvoy.iceandfire.registry;

import com.google.common.collect.ImmutableSet;
import com.iafenvoy.iceandfire.util.trade.TradeOfferHelper;
import com.iafenvoy.iceandfire.util.trade.factory.BuyWithPrice;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.DeferredSupplier;
import dev.architectury.registry.registries.RegistrySupplier;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public final class IafTrades {
   public static final DeferredRegister<PoiType> POI_REGISTRY = DeferredRegister.create("iceandfire", Registries.POINT_OF_INTEREST_TYPE);
   public static final DeferredRegister<VillagerProfession> PROFESSION_REGISTRY = DeferredRegister.create("iceandfire", Registries.VILLAGER_PROFESSION);
   private static final String SCRIBE = "scribe";
   private static final RegistrySupplier<Block> SCRIBE_BLOCK = IafBlocks.LECTERN;
   public static final Function<Block, Set<BlockState>> SCRIBE_WORKSTATION = block -> new HashSet<>(block.getStateDefinition().getPossibleStates());
   public static final DeferredSupplier<PoiType> SCRIBE_POI = POI_REGISTRY.register(
      "scribe", () -> new PoiType(SCRIBE_WORKSTATION.apply((Block)SCRIBE_BLOCK.get()), 1, 1)
   );
   public static final RegistrySupplier<VillagerProfession> SCRIBE_PROFESSION = PROFESSION_REGISTRY.register(
      "scribe",
      () -> new VillagerProfession(
         "scribe", e -> e.is(SCRIBE_POI.getKey()), e -> e.is(SCRIBE_POI.getKey()), ImmutableSet.of(), ImmutableSet.of(), SoundEvents.VILLAGER_WORK_LIBRARIAN
      )
   );

   public static void init() {
      for (BlockState state : SCRIBE_WORKSTATION.apply((Block)SCRIBE_BLOCK.get())) {
         PoiTypes.TYPE_BY_STATE.put(state, BuiltInRegistries.POINT_OF_INTEREST_TYPE.wrapAsHolder((PoiType)SCRIBE_POI.get()));
      }

      VillagerProfession profession = (VillagerProfession)SCRIBE_PROFESSION.get();
      float emeraldForItemsMultiplier = 0.05F;
      float itemForEmeraldMultiplier = 0.05F;
      float rareItemForEmeraldMultiplier = 0.2F;
      TradeOfferHelper.registerVillagerOffers(
         profession,
         1,
         new BuyWithPrice(new ItemStack(Items.EMERALD, 1), new ItemStack((ItemLike)IafItems.MANUSCRIPT.get(), 4), 25, 2, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.MANUSCRIPT.get(), 6), new ItemStack(Items.EMERALD, 1), 10, 5, 0.05F),
         new BuyWithPrice(new ItemStack(Items.BOOKSHELF, 3), new ItemStack(Items.EMERALD, 1), 8, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.PAPER, 15), new ItemStack(Items.EMERALD, 2), 4, 4, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafBlocks.ASH.get(), 10), new ItemStack(Items.EMERALD, 1), 8, 4, 0.05F)
      );
      TradeOfferHelper.registerVillagerOffers(
         profession,
         2,
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.SILVER_INGOT.get(), 5), new ItemStack(Items.EMERALD, 1), 3, 5, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafBlocks.FIRE_LILY.get(), 8), new ItemStack(Items.EMERALD, 1), 3, 5, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafBlocks.LIGHTNING_LILY.get(), 7), new ItemStack(Items.EMERALD, 3), 2, 5, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 3), new ItemStack((ItemLike)IafBlocks.FROST_LILY.get(), 4), 3, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 2), new ItemStack((ItemLike)IafBlocks.DRAGON_ICE_SPIKES.get(), 7), 2, 3, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.SAPPHIRE_GEM.get()), new ItemStack(Items.EMERALD, 2), 30, 3, 0.2F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 2), new ItemStack((ItemLike)IafBlocks.JAR_EMPTY.get(), 1), 3, 4, 0.05F),
         new BuyWithPrice(new ItemStack(Items.AMETHYST_SHARD), new ItemStack(Items.EMERALD, 3), 20, 3, 0.2F)
      );
      TradeOfferHelper.registerVillagerOffers(
         profession,
         3,
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.DRAGON_BONE.get(), 6), new ItemStack(Items.EMERALD, 1), 7, 4, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.CHAIN.get(), 2), new ItemStack(Items.EMERALD, 3), 4, 2, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 6), new ItemStack((ItemLike)IafItems.PIXIE_DUST.get(), 2), 8, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 6), new ItemStack((ItemLike)IafItems.FIRE_DRAGON_FLESH.get(), 2), 8, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 7), new ItemStack((ItemLike)IafItems.ICE_DRAGON_FLESH.get(), 1), 8, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 8), new ItemStack((ItemLike)IafItems.LIGHTNING_DRAGON_FLESH.get(), 1), 8, 3, 0.05F)
      );
      TradeOfferHelper.registerVillagerOffers(
         profession,
         4,
         new BuyWithPrice(new ItemStack(Items.EMERALD, 10), new ItemStack((ItemLike)IafItems.DRAGON_BONE.get(), 2), 20, 5, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 4), new ItemStack((ItemLike)IafItems.SHINY_SCALES.get(), 1), 5, 2, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.DREAD_SHARD.get(), 5), new ItemStack(Items.EMERALD, 1), 10, 4, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 8), new ItemStack((ItemLike)IafItems.STYMPHALIAN_BIRD_FEATHER.get(), 12), 3, 6, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 4), new ItemStack((ItemLike)IafItems.TROLL_TUSK.get(), 12), 7, 3, 0.05F)
      );
      TradeOfferHelper.registerVillagerOffers(
         profession,
         5,
         new BuyWithPrice(new ItemStack(Items.EMERALD, 15), new ItemStack((ItemLike)IafItems.SERPENT_FANG.get(), 3), 20, 3, 0.05F),
         new BuyWithPrice(new ItemStack(Items.EMERALD, 12), new ItemStack((ItemLike)IafItems.HYDRA_FANG.get(), 1), 20, 3, 0.05F),
         new BuyWithPrice(new ItemStack((ItemLike)IafItems.ECTOPLASM.get(), 6), new ItemStack(Items.EMERALD, 1), 7, 3, 0.05F)
      );
   }
}
