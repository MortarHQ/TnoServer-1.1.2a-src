package artifacts.component.ability;

import artifacts.config.value.Value;
import artifacts.config.value.ValueTypes;
import artifacts.equipment.EquipmentHelper;
import artifacts.registry.ModDataComponents;
import artifacts.registry.ModTags;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.block.state.BlockState;

public record ToolTierUpgrade(Value<ToolTierUpgrade.Tier> tier) implements EquipmentAbility {
   public static final Codec<ToolTierUpgrade> CODEC = RecordCodecBuilder.create(
      instance -> instance.group(ValueTypes.TOOL_TIER.codec().fieldOf("tier").forGetter(ToolTierUpgrade::tier)).apply(instance, ToolTierUpgrade::new)
   );
   public static final StreamCodec<ByteBuf, ToolTierUpgrade> STREAM_CODEC = StreamCodec.composite(
      ValueTypes.TOOL_TIER.streamCodec(), ToolTierUpgrade::tier, ToolTierUpgrade::new
   );

   public static boolean canHarvestWithTier(LivingEntity entity, BlockState state) {
      if (state.is(ModTags.MINEABLE_WITH_DIGGING_CLAWS)) {
         ToolTierUpgrade.Tier tier = ToolTierUpgrade.Tier.fromLevel(
            EquipmentHelper.maxInt(ModDataComponents.TOOL_TIER_UPGRADE.get(), entity, ability -> ability.tier().get().getLevel(), true)
         );
         return isCorrectTierForDrops(tier, state);
      } else {
         return false;
      }
   }

   public static boolean isCorrectTierForDrops(ToolTierUpgrade.Tier tier, BlockState state) {
      if (!state.requiresCorrectToolForDrops()) {
         return true;
      } else {
         int i = tier.getLevel();
         if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) {
            return i >= 4;
         } else if (state.is(BlockTags.NEEDS_IRON_TOOL)) {
            return i >= 3;
         } else {
            return state.is(BlockTags.NEEDS_STONE_TOOL) ? i >= 2 : i >= 1;
         }
      }
   }

   @Override
   public boolean isNonCosmetic() {
      return this.tier().get() != ToolTierUpgrade.Tier.NONE;
   }

   @Override
   public void addToTooltip(EquipmentAbility.TooltipWriter writer) {
      writer.addDefaultTooltipKey(getTierName(this.tier.get()));
   }

   public static Component getTierName(ToolTierUpgrade.Tier tier) {
      return Component.translatable("%s.tooltip.tool_tier.%s".formatted("artifacts", tier.getSerializedName()));
   }

   public enum Tier implements StringRepresentable {
      NONE(0),
      WOOD(1),
      STONE(2),
      IRON(3),
      DIAMOND(4),
      NETHERITE(5);

      private final int level;

      Tier(int level) {
         this.level = level;
      }

      public static ToolTierUpgrade.Tier fromLevel(int level) {
         return switch (level) {
            case 0 -> NONE;
            case 1 -> WOOD;
            case 2 -> STONE;
            case 3 -> IRON;
            case 4 -> DIAMOND;
            default -> NETHERITE;
         };
      }

      public int getLevel() {
         return this.level;
      }

      public String getSerializedName() {
         return this.toString().toLowerCase();
      }
   }
}
