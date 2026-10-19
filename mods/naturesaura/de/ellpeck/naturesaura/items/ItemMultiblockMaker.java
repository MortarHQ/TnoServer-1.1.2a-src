package de.ellpeck.naturesaura.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.multiblock.IMultiblock;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class ItemMultiblockMaker extends ItemImpl {
   private static List<IMultiblock> multiblocks;

   public ItemMultiblockMaker() {
      super("multiblock_maker", new Properties().component(ItemMultiblockMaker.Data.TYPE, new ItemMultiblockMaker.Data(0)));
   }

   public InteractionResultHolder<ItemStack> use(Level levelIn, Player playerIn, InteractionHand handIn) {
      ItemStack stack = playerIn.getItemInHand(handIn);
      if (!levelIn.isClientSide && playerIn.isCreative()) {
         int curr = getMultiblockId(stack);
         int next = (curr + 1) % multiblocks().size();
         stack.set(ItemMultiblockMaker.Data.TYPE, new ItemMultiblockMaker.Data(next));
      }

      return new InteractionResultHolder(InteractionResult.SUCCESS, stack);
   }

   public InteractionResult useOn(UseOnContext context) {
      Player player = context.getPlayer();
      if (player.isCreative()) {
         IMultiblock multi = getMultiblock(player.getItemInHand(context.getHand()));
         if (multi == null) {
            return InteractionResult.PASS;
         }

         if (!context.getLevel().isClientSide) {
            multi.forEach(context.getClickedPos().above(), '\u0000', (blockPos, matcher) -> {
               context.getLevel().setBlockAndUpdate(blockPos, matcher.defaultState());
               return true;
            });
         }

         return InteractionResult.SUCCESS;
      } else {
         return InteractionResult.PASS;
      }
   }

   public Component getName(ItemStack stack) {
      MutableComponent name = (MutableComponent)super.getName(stack);
      IMultiblock multi = getMultiblock(stack);
      return multi == null ? name : name.append(" (" + multi.getName() + ")");
   }

   private static List<IMultiblock> multiblocks() {
      if (multiblocks == null) {
         if (NaturesAuraAPI.MULTIBLOCKS.isEmpty()) {
            return null;
         }

         multiblocks = new ArrayList<>();
         multiblocks.addAll(NaturesAuraAPI.MULTIBLOCKS.values());
      }

      return multiblocks;
   }

   private static int getMultiblockId(ItemStack stack) {
      return ((ItemMultiblockMaker.Data)stack.get(ItemMultiblockMaker.Data.TYPE)).multiblockId();
   }

   private static IMultiblock getMultiblock(ItemStack stack) {
      List<IMultiblock> multiblocks = multiblocks();
      if (multiblocks == null) {
         return null;
      }

      int id = getMultiblockId(stack);
      return id >= 0 && id < multiblocks.size() ? multiblocks.get(id) : null;
   }

   public record Data(int multiblockId) {
      public static final Codec<ItemMultiblockMaker.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.INT.fieldOf("multiblock_id").forGetter(d -> d.multiblockId)).apply(i, ItemMultiblockMaker.Data::new)
      );
      public static final DataComponentType<ItemMultiblockMaker.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
