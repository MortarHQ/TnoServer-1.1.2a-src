package com.github.ysbbbbbb.kaleidoscopecookery.item;

import com.github.ysbbbbbb.kaleidoscopecookery.api.item.IHasContainer;
import com.github.ysbbbbbb.kaleidoscopecookery.block.food.FoodBiteBlock;
import com.github.ysbbbbbb.kaleidoscopecookery.config.ClientConfig;
import com.github.ysbbbbbb.kaleidoscopecookery.init.registry.CompatRegistry;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.Quality;
import com.github.ysbbbbbb.kaleidoscopecookery.item.quality.QualityUtils;
import com.google.common.collect.Lists;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.ChatFormatting;
import net.minecraft.Util;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.BuiltInLootTables;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootParams.Builder;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.items.ItemHandlerHelper;

public class BowlFoodBlockItem extends BlockItem implements IHasContainer {
   private final List<MobEffectInstance> effectInstances = Lists.newArrayList();
   private final Function<Quality, List<MobEffectInstance>> effectCache = Util.memoize(quality -> QualityUtils.modifyEffects(this.effectInstances, quality));
   private final BiFunction<Quality, FoodProperties, FoodProperties> foodPropertiesCache = Util.memoize(
      (quality, raw) -> QualityUtils.modifyFoodProperties(raw, quality)
   );
   private final Optional<ItemStack> usingConvertsTo;

   public BowlFoodBlockItem(Block block, FoodProperties properties, @Nullable ItemLike usingConvertsTo) {
      super(
         block,
         new Properties()
            .stacksTo(16)
            .food(
               new FoodProperties(
                  properties.nutrition(),
                  properties.saturation(),
                  properties.canAlwaysEat(),
                  properties.eatSeconds(),
                  usingConvertsTo == null ? Optional.empty() : Optional.of(new ItemStack(usingConvertsTo)),
                  properties.effects()
               )
            )
      );
      this.usingConvertsTo = usingConvertsTo == null ? Optional.empty() : Optional.of(new ItemStack(usingConvertsTo));
      properties.effects().forEach(effect -> {
         if (effect.probability() >= 1.0F) {
            this.effectInstances.add(effect.effect());
         }
      });
   }

   @Nullable
   public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
      FoodProperties raw = super.getFoodProperties(stack, entity);
      if (QualityUtils.hasQuality(stack) && raw != null) {
         Quality quality = QualityUtils.getQuality(stack);
         return this.foodPropertiesCache.apply(quality, raw);
      } else {
         return raw;
      }
   }

   public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
      if (level instanceof ServerLevel serverLevel && this.getBlock() instanceof FoodBiteBlock foodBiteBlock) {
         Builder builder = new Builder(serverLevel)
            .withParameter(LootContextParams.ORIGIN, Vec3.atCenterOf(entity.blockPosition()))
            .withParameter(LootContextParams.TOOL, ItemStack.EMPTY)
            .withOptionalParameter(LootContextParams.THIS_ENTITY, entity)
            .withOptionalParameter(LootContextParams.BLOCK_ENTITY, null);
         BlockState state = (BlockState)foodBiteBlock.defaultBlockState().setValue(foodBiteBlock.getBites(), foodBiteBlock.getMaxBites());
         List<ItemStack> drops = this.getDrops(state, builder);
         drops.forEach(itemStack -> {
            if (!itemStack.isEmpty()) {
               if (!this.usingConvertsTo.isPresent() || !ItemStack.isSameItem(itemStack, this.usingConvertsTo.get())) {
                  if (entity instanceof Player player) {
                     ItemHandlerHelper.giveItemToPlayer(player, itemStack);
                  } else {
                     ItemEntity itemEntity = new ItemEntity(level, entity.getX(), entity.getY(), entity.getZ(), itemStack);
                     level.addFreshEntity(itemEntity);
                  }
               }
            }
         });
      }

      return super.finishUsingItem(stack, level, entity);
   }

   private List<ItemStack> getDrops(BlockState state, Builder params) {
      ResourceKey<LootTable> resourcekey = state.getBlock().getLootTable();
      if (resourcekey == BuiltInLootTables.EMPTY) {
         return Collections.emptyList();
      }

      LootParams lootParams = params.withParameter(LootContextParams.BLOCK_STATE, state).create(LootContextParamSets.BLOCK);
      ServerLevel serverLevel = lootParams.getLevel();
      LootTable lootTable = serverLevel.getServer().reloadableRegistries().getLootTable(resourcekey);
      return lootTable.getRandomItems(lootParams);
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      ResourceLocation id = BuiltInRegistries.ITEM.getKey(stack.getItem());
      if (id != null) {
         String key = "tooltip.%s.%s.maxim".formatted(id.getNamespace(), id.getPath());
         MutableComponent full = Component.translatable(key).withStyle(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC});
         String text = full.getString();

         for (String line : text.split("\n")) {
            if (!line.isEmpty()) {
               tooltip.add(Component.literal(line).withStyle(new ChatFormatting[]{ChatFormatting.DARK_GRAY, ChatFormatting.ITALIC}));
            } else {
               tooltip.add(CommonComponents.EMPTY);
            }
         }
      }

      boolean showEffect = !this.effectInstances.isEmpty()
         && CompatRegistry.SHOW_POTION_EFFECT_TOOLTIPS
         && (Boolean)ClientConfig.SHOW_FOOD_EFFECT_TOOLTIPS.get();
      if (QualityUtils.hasQuality(stack)) {
         Quality quality = QualityUtils.getQuality(stack);
         tooltip.add(quality.getTooltip());
         if (showEffect) {
            tooltip.add(CommonComponents.space());
            PotionContents.addPotionTooltip(this.effectCache.apply(quality), tooltip::add, 1.0F, context.tickRate());
         }
      } else {
         tooltip.add(CommonComponents.space());
         PotionContents.addPotionTooltip(this.effectInstances, tooltip::add, 1.0F, context.tickRate());
      }
   }

   @Override
   public Item getContainerItem() {
      return Items.BOWL;
   }
}
