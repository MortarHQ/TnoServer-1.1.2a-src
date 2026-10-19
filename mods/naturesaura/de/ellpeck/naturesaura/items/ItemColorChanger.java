package de.ellpeck.naturesaura.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.misc.ColoredBlockHelper;
import de.ellpeck.naturesaura.reg.IColorProvidingItem;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import java.util.List;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemColorChanger extends ItemImpl implements IColorProvidingItem, ICustomItemModel {
   public ItemColorChanger() {
      super("color_changer", new Properties().stacksTo(1));
   }

   private static boolean changeOrCopyColor(Player player, ItemStack stack, Level level, BlockPos pos, DyeColor firstColor) {
      Block block = level.getBlockState(pos).getBlock();
      List<Block> blocks = ColoredBlockHelper.getBlocksContaining(block);
      if (blocks == null) {
         return false;
      }

      DyeColor color = DyeColor.byId(blocks.indexOf(block));
      if (firstColor == null || color == firstColor) {
         DyeColor stored = getStoredColor(stack);
         if (player.isShiftKeyDown()) {
            if (stored != color) {
               level.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BUCKET_FILL, SoundSource.PLAYERS, 0.65F, 1.0F);
               if (!level.isClientSide) {
                  storeColor(stack, color);
               }

               return true;
            }
         } else if (stored != null && stored != color && NaturesAuraAPI.instance().extractAuraFromPlayer(player, 1000, level.isClientSide)) {
            if (firstColor == null) {
               level.playSound(player, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.BUCKET_EMPTY, SoundSource.PLAYERS, 0.65F, 1.0F);
            }

            if (!level.isClientSide) {
               level.setBlockAndUpdate(pos, blocks.get(stored.getId()).defaultBlockState());
               if (isFillMode(stack)) {
                  for (Direction off : Direction.values()) {
                     changeOrCopyColor(player, stack, level, pos.relative(off), color);
                  }
               }
            }

            return true;
         }
      }

      return false;
   }

   public static DyeColor getStoredColor(ItemStack stack) {
      return stack.has(ItemColorChanger.Data.TYPE) ? DyeColor.byId(((ItemColorChanger.Data)stack.get(ItemColorChanger.Data.TYPE)).color) : null;
   }

   private static void storeColor(ItemStack stack, DyeColor color) {
      ItemColorChanger.Data previous = (ItemColorChanger.Data)stack.getOrDefault(ItemColorChanger.Data.TYPE, new ItemColorChanger.Data(0, false));
      stack.set(ItemColorChanger.Data.TYPE, new ItemColorChanger.Data(color.getId(), previous.fill));
   }

   public static boolean isFillMode(ItemStack stack) {
      return stack.has(ItemColorChanger.Data.TYPE) && ((ItemColorChanger.Data)stack.get(ItemColorChanger.Data.TYPE)).fill;
   }

   private static void setFillMode(ItemStack stack, boolean fill) {
      ItemColorChanger.Data previous = (ItemColorChanger.Data)stack.getOrDefault(ItemColorChanger.Data.TYPE, new ItemColorChanger.Data(0, false));
      stack.set(ItemColorChanger.Data.TYPE, new ItemColorChanger.Data(previous.color, fill));
   }

   public InteractionResult useOn(UseOnContext context) {
      ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
      return changeOrCopyColor(context.getPlayer(), stack, context.getLevel(), context.getClickedPos(), null)
         ? InteractionResult.SUCCESS
         : InteractionResult.PASS;
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      if (playerIn.isShiftKeyDown() && getStoredColor(stack) != null) {
         levelIn.playSound(playerIn, playerIn.getX(), playerIn.getY(), playerIn.getZ(), SoundEvents.BUCKET_FILL_LAVA, SoundSource.PLAYERS, 0.65F, 1.0F);
         if (!levelIn.isClientSide) {
            setFillMode(stack, !isFillMode(stack));
         }

         return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
      } else {
         return new InteractionResultHolder(InteractionResult.PASS, stack);
      }
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ItemColor getItemColor() {
      return (stack, tintIndex) -> {
         if (tintIndex > 0) {
            DyeColor color = getStoredColor(stack);
            if (color != null) {
               return ARGB32.opaque(color.getFireworkColor());
            }
         }

         return -1;
      };
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
   }

   public record Data(int color, boolean fill) {
      public static final Codec<ItemColorChanger.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.INT.fieldOf("color").forGetter(d -> d.color), Codec.BOOL.fieldOf("fill").forGetter(d -> d.fill))
            .apply(i, ItemColorChanger.Data::new)
      );
      public static final DataComponentType<ItemColorChanger.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
