package com.blamejared.crafttweaker.api.bracket;

import com.blamejared.crafttweaker.api.annotation.BracketResolver;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.bracket.custom.RecipeTypeBracketHandler;
import com.blamejared.crafttweaker.api.fluid.IFluidStack;
import com.blamejared.crafttweaker.api.item.IItemStack;
import com.blamejared.crafttweaker.api.logging.CommonLoggers;
import com.blamejared.crafttweaker.api.recipe.manager.base.IRecipeManager;
import com.blamejared.crafttweaker.api.recipe.replacement.ITargetingStrategy;
import com.blamejared.crafttweaker.api.util.GenericUtil;
import com.blamejared.crafttweaker.natives.block.ExpandBlockState;
import com.blamejared.crafttweaker.platform.Services;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import java.util.Locale;
import java.util.Optional;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.valueproviders.FloatProviderType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.entity.npc.VillagerType;
import net.minecraft.world.item.Instrument;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.armortrim.TrimMaterial;
import net.minecraft.world.item.armortrim.TrimPattern;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraft.world.level.block.entity.DecoratedPotPattern;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@ZenCodeType.Name("crafttweaker.api.bracket.BracketHandlers")
@Document("vanilla/api/BracketHandlers")
public class BracketHandlers {
   public static <T> T getRegistry(String tokens, ResourceKey<Registry<T>> registry) {
      return getRegistry(tokens, registry, false);
   }

   public static <T> T getRegistry(String tokens, ResourceKey<Registry<T>> registry, boolean includeTypeNamespace) {
      String type = includeTypeNamespace ? registry.location().toString() : registry.location().getPath();
      if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
         CommonLoggers.zenCode().warn("{} bracket <{}:{}> is not lowercase!", type, type, tokens);
      }

      String[] split = tokens.split(":");
      if (split.length != 2) {
         throw new IllegalArgumentException("Could not get " + type + " with name: <" + type + ":" + tokens + ">! Syntax is <" + type + ":modid:name>");
      }

      ResourceLocation key = ResourceLocation.fromNamespaceAndPath(split[0], split[1]);
      return (T)Services.REGISTRY
         .registryOrThrow(registry)
         .getOptional(key)
         .orElseThrow(() -> new IllegalArgumentException("Could not get " + type + " with name: <" + type + ":" + tokens + ">! " + type + " does not exist!"));
   }

   @ZenCodeType.Method
   @BracketResolver("attribute")
   public static Attribute getAttribute(String tokens) {
      return getRegistry(tokens, Registries.ATTRIBUTE);
   }

   @ZenCodeType.Method
   @BracketResolver("block")
   public static Block getBlock(String tokens) {
      return getRegistry(tokens, Registries.BLOCK);
   }

   @ZenCodeType.Method
   @BracketResolver("fluid")
   public static IFluidStack getFluidStack(String tokens) {
      return IFluidStack.of(getRegistry(tokens, Registries.FLUID), 1L);
   }

   @ZenCodeType.Method
   @BracketResolver("blockstate")
   public static BlockState getBlockState(String tokens) {
      if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
         CommonLoggers.zenCode().warn("BlockState bracket <blockstate:{}> is not lower-cased!", tokens);
      }

      String[] split = tokens.split(":", 4);
      if (split.length > 1) {
         String blockName = split[0] + ":" + split[1];
         String properties = split.length > 2 ? split[2] : "";
         Optional<Block> found = BuiltInRegistries.BLOCK.getOptional(ResourceLocation.parse(blockName));
         if (!found.isEmpty()) {
            return getBlockState(found.get(), properties);
         }

         Throwable t = new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">! The block does not exist!");
         CommonLoggers.zenCode().error("Error creating BlockState!", t);
      }

      CommonLoggers.zenCode().error("Error creating BlockState!", new IllegalArgumentException("Could not get BlockState from: <blockstate:" + tokens + ">!"));
      return null;
   }

   public static BlockState getBlockState(String name, String properties) {
      return getBlockState((Block)BuiltInRegistries.BLOCK.get(ResourceLocation.parse(name)), properties);
   }

   public static BlockState getBlockState(Block block, String properties) {
      BlockState blockState = block.defaultBlockState();
      if (properties != null && !properties.isEmpty()) {
         for (String propertyPair : properties.split(",")) {
            String[] splitPair = propertyPair.split("=");
            if (splitPair.length != 2) {
               CommonLoggers.zenCode().warn("Invalid blockstate property format '{}'. Using default property value.", propertyPair);
            } else {
               blockState = ExpandBlockState.withProperty(blockState, splitPair[0], splitPair[1]);
            }
         }
      }

      return blockState;
   }

   @BracketResolver("mobeffect")
   @ZenCodeType.Method
   public static MobEffect getMobEffect(String tokens) {
      return getRegistry(tokens, Registries.MOB_EFFECT);
   }

   @ZenCodeType.Method
   @BracketResolver("enchantment")
   public static Enchantment getEnchantment(String tokens) {
      return getRegistry(tokens, Registries.ENCHANTMENT);
   }

   @ZenCodeType.Method
   @BracketResolver("entitytype")
   public static EntityType<Entity> getEntityType(String tokens) {
      return GenericUtil.uncheck(getRegistry(tokens, Registries.ENTITY_TYPE));
   }

   @BracketResolver("item")
   @ZenCodeType.Method
   public static IItemStack getItem(String tokens) {
      return IItemStack.of(new ItemStack(getRegistry(tokens, Registries.ITEM)));
   }

   @BracketResolver("potion")
   @ZenCodeType.Method
   public static Potion getPotion(String tokens) {
      return getRegistry(tokens, Registries.POTION);
   }

   @ZenCodeType.Method
   public static IRecipeManager<?> getRecipeManager(String tokens) {
      if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
         CommonLoggers.zenCode().warn("RecipeType bracket <recipetype:{}> is not lower-cased!", tokens);
      }

      if (tokens.equalsIgnoreCase("crafttweaker:scripts")) {
         throw new IllegalArgumentException("Nice try, but there's no reason you need to access the <recipetype:crafttweaker:scripts> recipe manager!");
      } else {
         ResourceLocation key = ResourceLocation.parse(tokens);
         IRecipeManager<?> result = RecipeTypeBracketHandler.getOrDefault(key);
         if (result != null) {
            return result;
         } else {
            throw new IllegalArgumentException("Could not get RecipeType with name: <recipetype:" + tokens + ">! RecipeType does not exist!");
         }
      }
   }

   @ZenCodeType.Method
   @BracketResolver("profession")
   public static VillagerProfession getProfession(String tokens) {
      return getRegistry(tokens, Registries.VILLAGER_PROFESSION);
   }

   @ZenCodeType.Method
   @BracketResolver("soundevent")
   public static SoundEvent getSoundEvent(String tokens) {
      return getRegistry(tokens, Registries.SOUND_EVENT);
   }

   @ZenCodeType.Method
   @BracketResolver("targetingstrategy")
   public static ITargetingStrategy getTargetingStrategy(String tokens) {
      if (!tokens.toLowerCase(Locale.ENGLISH).equals(tokens)) {
         CommonLoggers.zenCode().warn("Targeting strategy bracket <targetingstrategy:{}> is not lower-cased!", tokens);
      }

      String[] split = tokens.split(":");
      if (split.length != 2) {
         throw new IllegalArgumentException(
            "Could not get targeting strategy with <targetingstrategy:" + tokens + ">: syntax is <targetingstrategy:modid:name>"
         );
      }

      ResourceLocation key = ResourceLocation.fromNamespaceAndPath(split[0], split[1]);
      return ITargetingStrategy.find(key);
   }

   @ZenCodeType.Method
   @BracketResolver("villagertype")
   public static VillagerType getVillagerType(String tokens) {
      return getRegistry(tokens, Registries.VILLAGER_TYPE);
   }

   @ZenCodeType.Method
   @BracketResolver("componenttype")
   public static DataComponentType getComponentType(String tokens) {
      return getRegistry(tokens, Registries.DATA_COMPONENT_TYPE);
   }

   @ZenCodeType.Method
   @BracketResolver("bannerpattern")
   public static BannerPattern getBannerPattern(String tokens) {
      return getRegistry(tokens, Registries.BANNER_PATTERN);
   }

   @ZenCodeType.Method
   @BracketResolver("instrument")
   public static Instrument getInstrument(String tokens) {
      return getRegistry(tokens, Registries.INSTRUMENT);
   }

   @ZenCodeType.Method
   @BracketResolver("trimpattern")
   public static TrimPattern getTrimPattern(String tokens) {
      return getRegistry(tokens, Registries.TRIM_PATTERN);
   }

   @ZenCodeType.Method
   @BracketResolver("trimmaterial")
   public static TrimMaterial getTrimMaterial(String tokens) {
      FloatProviderType<?> registry = getRegistry(tokens, Registries.FLOAT_PROVIDER_TYPE);
      return getRegistry(tokens, Registries.TRIM_MATERIAL);
   }

   @ZenCodeType.Method
   @BracketResolver("decoratedpotpattern")
   public static DecoratedPotPattern getDecoratedPotPattern(String tokens) {
      return getRegistry(tokens, Registries.DECORATED_POT_PATTERN);
   }
}
