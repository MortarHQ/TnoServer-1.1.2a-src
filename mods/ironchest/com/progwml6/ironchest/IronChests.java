package com.progwml6.ironchest;

import com.progwml6.ironchest.common.block.IronChestsBlocks;
import com.progwml6.ironchest.common.block.entity.IronChestsBlockEntityTypes;
import com.progwml6.ironchest.common.block.regular.AbstractIronChestBlock;
import com.progwml6.ironchest.common.creativetabs.IronChestsCreativeTabs;
import com.progwml6.ironchest.common.data.IronChestsBlockTags;
import com.progwml6.ironchest.common.data.IronChestsLanguageProvider;
import com.progwml6.ironchest.common.data.IronChestsRecipeProvider;
import com.progwml6.ironchest.common.data.IronChestsSpriteSourceProvider;
import com.progwml6.ironchest.common.data.loot.IronChestsLootTableProvider;
import com.progwml6.ironchest.common.datacomponents.IronChestsDataComponents;
import com.progwml6.ironchest.common.inventory.IronChestsMenuTypes;
import com.progwml6.ironchest.common.item.IronChestsItems;
import com.progwml6.ironchest.common.network.TopStacksSyncPacket;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import net.neoforged.neoforge.capabilities.Capabilities.ItemHandler;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.items.wrapper.InvWrapper;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;

@Mod("ironchest")
public class IronChests {
   public static final String MODID = "ironchest";

   public IronChests(IEventBus modEventBus) {
      modEventBus.addListener(this::gatherData);
      modEventBus.addListener(this::setupPackets);
      modEventBus.addListener(this::registerCapabilities);
      IronChestsBlocks.BLOCKS.register(modEventBus);
      IronChestsItems.ITEMS.register(modEventBus);
      IronChestsBlockEntityTypes.BLOCK_ENTITIES.register(modEventBus);
      IronChestsMenuTypes.CONTAINERS.register(modEventBus);
      IronChestsCreativeTabs.CREATIVE_MODE_TABS.register(modEventBus);
      IronChestsDataComponents.COMPONENTS.register(modEventBus);
   }

   public void gatherData(GatherDataEvent event) {
      ExistingFileHelper ext = event.getExistingFileHelper();
      DataGenerator gen = event.getGenerator();
      PackOutput packOutput = gen.getPackOutput();
      CompletableFuture<Provider> lookupProvider = event.getLookupProvider();
      gen.addProvider(event.includeServer(), new IronChestsLootTableProvider(packOutput, lookupProvider));
      gen.addProvider(event.includeClient(), new IronChestsRecipeProvider(packOutput, lookupProvider));
      gen.addProvider(event.includeClient(), new IronChestsBlockTags(packOutput, lookupProvider, ext));
      gen.addProvider(event.includeClient(), new IronChestsSpriteSourceProvider(packOutput, ext, lookupProvider));
      gen.addProvider(event.includeClient(), new IronChestsLanguageProvider(packOutput, "en_us"));
   }

   public void setupPackets(RegisterPayloadHandlersEvent event) {
      PayloadRegistrar registrar = event.registrar("ironchest").versioned("1.0.0").optional();
      registrar.playBidirectional(TopStacksSyncPacket.TYPE, TopStacksSyncPacket.STREAM_CODEC, TopStacksSyncPacket::handle);
   }

   public void registerCapabilities(RegisterCapabilitiesEvent event) {
      event.registerBlock(
         ItemHandler.BLOCK,
         (level, pos, state, blockEntity, context) -> new InvWrapper(
            AbstractIronChestBlock.getContainer((AbstractIronChestBlock)state.getBlock(), state, level, pos, true)
         ),
         new Block[]{
            (Block)IronChestsBlocks.IRON_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_IRON_CHEST.get(),
            (Block)IronChestsBlocks.GOLD_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_GOLD_CHEST.get(),
            (Block)IronChestsBlocks.DIAMOND_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_DIAMOND_CHEST.get(),
            (Block)IronChestsBlocks.COPPER_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_COPPER_CHEST.get(),
            (Block)IronChestsBlocks.CRYSTAL_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_CRYSTAL_CHEST.get(),
            (Block)IronChestsBlocks.OBSIDIAN_CHEST.get(),
            (Block)IronChestsBlocks.TRAPPED_OBSIDIAN_CHEST.get(),
            (Block)IronChestsBlocks.DIRT_CHEST.get()
         }
      );
   }
}
