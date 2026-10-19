package com.blamejared.crafttweaker.impl.plugin.crafttweaker;

import com.blamejared.crafttweaker.api.CraftTweakerConstants;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeComponentBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagBracketHandler;
import com.blamejared.crafttweaker.api.bracket.custom.TagManagerBracketHandler;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.plugin.CraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.IBracketParserRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICommandRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.ICraftTweakerPlugin;
import com.blamejared.crafttweaker.api.plugin.ILoaderRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IRecipeComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IReplacerComponentRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptLoadSourceRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IScriptRunModuleConfiguratorRegistrationHandler;
import com.blamejared.crafttweaker.api.plugin.IVillagerTradeRegistrationHandler;
import com.blamejared.crafttweaker.api.recipe.component.BuiltinRecipeComponents;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.villager.CTTradeObject;
import com.blamejared.crafttweaker.api.zencode.scriptrun.IScriptRunModuleConfigurator;
import com.blamejared.crafttweaker.impl.command.type.DumpCommands;
import com.blamejared.crafttweaker.impl.command.type.HandCommands;
import com.blamejared.crafttweaker.impl.command.type.InventoryCommands;
import com.blamejared.crafttweaker.impl.command.type.MiscCommands;
import com.blamejared.crafttweaker.impl.command.type.ModCommands;
import com.blamejared.crafttweaker.impl.command.type.RecipeCommands;
import com.blamejared.crafttweaker.impl.command.type.conflict.ConflictCommand;
import com.blamejared.crafttweaker.impl.command.type.script.ScriptCommands;
import com.blamejared.crafttweaker.impl.recipe.replacement.DefaultTargetingFilters;
import com.blamejared.crafttweaker.impl.recipe.replacement.DefaultTargetingStrategies;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessDyedArmorForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEmeraldForItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessEnchantedItemForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsAndEmeraldsToItems;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessItemsForEmeralds;
import com.blamejared.crafttweaker.mixin.common.access.villager.AccessTippedArrowForItemsAndEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.DyedArmorForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldForItems;
import net.minecraft.world.entity.npc.VillagerTrades.EmeraldsForVillagerTypeItem;
import net.minecraft.world.entity.npc.VillagerTrades.EnchantBookForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.EnchantedItemForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsAndEmeraldsToItems;
import net.minecraft.world.entity.npc.VillagerTrades.ItemsForEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.SuspiciousStewForEmerald;
import net.minecraft.world.entity.npc.VillagerTrades.TippedArrowForItemsAndEmeralds;
import net.minecraft.world.entity.npc.VillagerTrades.TreasureMapForEmeralds;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

@CraftTweakerPlugin("crafttweaker:common")
public final class CommonCraftTweakerPlugin implements ICraftTweakerPlugin {
   @Override
   public void registerLoaders(ILoaderRegistrationHandler handler) {
      handler.registerLoader("initialize");
      handler.registerLoader("crafttweaker");
      handler.registerLoader("tags");
   }

   @Override
   public void registerLoadSource(IScriptLoadSourceRegistrationHandler handler) {
      handler.registerLoadSource(CraftTweakerConstants.RELOAD_LISTENER_SOURCE_ID);
      handler.registerLoadSource(CraftTweakerConstants.CLIENT_RECIPES_UPDATED_SOURCE_ID);
   }

   @Override
   public void registerModuleConfigurators(IScriptRunModuleConfiguratorRegistrationHandler handler) {
      IScriptRunModuleConfigurator defaultConfig = IScriptRunModuleConfigurator.createDefault("crafttweaker");
      handler.registerConfigurator("crafttweaker", defaultConfig);
      handler.registerConfigurator("initialize", defaultConfig);
      handler.registerConfigurator("tags", defaultConfig);
   }

   @Override
   public void registerBracketParsers(IBracketParserRegistrationHandler handler) {
      handler.registerParserFor(
         "crafttweaker",
         "recipecomponent",
         new RecipeComponentBracketHandler(),
         new IBracketParserRegistrationHandler.DumperData("recipecomponent", RecipeComponentBracketHandler.getDumperData())
      );
      handler.registerParserFor(
         "crafttweaker",
         "recipetype",
         new RecipeTypeBracketHandler(),
         new IBracketParserRegistrationHandler.DumperData("recipetype", RecipeTypeBracketHandler.getDumperData())
      );
      handler.registerParserFor(
         "crafttweaker", "tag", new TagBracketHandler(), new IBracketParserRegistrationHandler.DumperData("tag", TagBracketHandler.getDumperData())
      );
      handler.registerParserFor(
         "crafttweaker",
         "tagmanager",
         new TagManagerBracketHandler(),
         new IBracketParserRegistrationHandler.DumperData("tagmanager", TagManagerBracketHandler.getDumperData())
      );
      handler.registerParserFor("tags", "tag", new TagBracketHandler());
      handler.registerParserFor("tags", "tagmanager", new TagManagerBracketHandler());
   }

   @Override
   public void registerRecipeComponents(IRecipeComponentRegistrationHandler handler) {
      handler.registerRecipeComponent(BuiltinRecipeComponents.Metadata.GROUP);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Metadata.COOKING_BOOK_CATEGORY);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Metadata.CRAFTING_BOOK_CATEGORY);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Metadata.MIRROR_AXIS);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Metadata.SHAPE_SIZE_2D);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Input.INGREDIENTS);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Input.FLUID_INGREDIENTS);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Processing.FUNCTION_0D);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Processing.FUNCTION_1D);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Processing.FUNCTION_2D);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Processing.TIME);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Output.CHANCED_ITEMS);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Output.EXPERIENCE);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Output.FLUIDS);
      handler.registerRecipeComponent(BuiltinRecipeComponents.Output.ITEMS);
   }

   @Override
   public void registerVillagerTradeConverters(IVillagerTradeRegistrationHandler handler) {
      IItemStack emerald = IItemStack.of(new ItemStack(Items.EMERALD));
      IItemStack compass = IItemStack.of(new ItemStack(Items.COMPASS));
      IItemStack book = IItemStack.of(new ItemStack(Items.BOOK));
      IItemStack enchantedBook = IItemStack.of(new ItemStack(Items.ENCHANTED_BOOK));
      IItemStack filledMap = IItemStack.of(new ItemStack(Items.FILLED_MAP));
      IItemStack suspiciousStew = IItemStack.of(new ItemStack(Items.SUSPICIOUS_STEW));
      handler.registerTradeConverter(
         DyedArmorForEmeralds.class,
         trade -> new CTTradeObject(emerald, IItemStack.empty(), IItemStack.of(((AccessDyedArmorForEmeralds)trade).crafttweaker$getItem().getDefaultInstance()))
      );
      handler.registerTradeConverter(
         EmeraldForItems.class,
         trade -> new CTTradeObject(IItemStack.of(((AccessEmeraldForItems)trade).crafttweaker$getItemStack().itemStack()), IItemStack.empty(), emerald)
      );
      handler.registerTradeConverter(TreasureMapForEmeralds.class, trade -> new CTTradeObject(emerald, compass, filledMap));
      handler.registerTradeConverter(EmeraldsForVillagerTypeItem.class, trade -> new CTTradeObject(IItemStack.empty(), IItemStack.empty(), emerald));
      handler.registerTradeConverter(EnchantBookForEmeralds.class, trade -> new CTTradeObject(emerald, book, enchantedBook));
      handler.registerTradeConverter(
         EnchantedItemForEmeralds.class,
         trade -> new CTTradeObject(emerald, IItemStack.empty(), IItemStack.ofMutable(((AccessEnchantedItemForEmeralds)trade).crafttweaker$getItemStack()))
      );
      handler.registerTradeConverter(
         TippedArrowForItemsAndEmeralds.class,
         trade -> new CTTradeObject(
            emerald,
            IItemStack.ofMutable(((AccessTippedArrowForItemsAndEmeralds)trade).crafttweaker$getFromItem().getDefaultInstance()),
            IItemStack.ofMutable(((AccessTippedArrowForItemsAndEmeralds)trade).crafttweaker$getToItem())
         )
      );
      handler.registerTradeConverter(
         ItemsAndEmeraldsToItems.class,
         trade -> new CTTradeObject(
            emerald,
            IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems)trade).crafttweaker$getFromItem().itemStack()),
            IItemStack.ofMutable(((AccessItemsAndEmeraldsToItems)trade).crafttweaker$getToItem())
         )
      );
      handler.registerTradeConverter(
         ItemsForEmeralds.class,
         trade -> new CTTradeObject(emerald, IItemStack.empty(), IItemStack.ofMutable(((AccessItemsForEmeralds)trade).crafttweaker$getItemStack()))
      );
      handler.registerTradeConverter(SuspiciousStewForEmerald.class, trade -> new CTTradeObject(emerald, IItemStack.empty(), suspiciousStew));
   }

   @Override
   public void registerCommands(ICommandRegistrationHandler handler) {
      ConflictCommand.registerCommands(handler);
      DumpCommands.registerCommands(handler);
      InventoryCommands.registerCommands(handler);
      HandCommands.registerCommands(handler);
      ScriptCommands.registerCommands(handler);
      MiscCommands.registerCommands(handler);
      ModCommands.registerCommands(handler);
      RecipeCommands.registerCommands(handler);
      DumpCommands.registerDumpers(handler);
   }

   @Override
   public void registerReplacerComponents(IReplacerComponentRegistrationHandler handler) {
      handler.registerTargetingFilter(DefaultTargetingFilters::scripts);
      handler.registerTargetingFilter(DefaultTargetingFilters::vanillaSpecial);
      handler.registerTargetingStrategy(ITargetingStrategy.DEFAULT_STRATEGY_ID, DefaultTargetingStrategies::shallow);
      handler.registerTargetingStrategy(CraftTweakerConstants.rl("deep"), DefaultTargetingStrategies::deep);
   }
}
