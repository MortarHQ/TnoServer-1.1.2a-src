package twilightforest.loot.modifiers;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;
import twilightforest.components.entity.GiantPickaxeMiningAttachment;
import twilightforest.init.TFDataAttachments;

public class GiantToolGroupingModifier extends LootModifier {
   public static Map<Block, Item> CONVERSIONS = new HashMap<>();
   public static final MapCodec<GiantToolGroupingModifier> CODEC = RecordCodecBuilder.mapCodec(
      inst -> LootModifier.codecStart(inst).apply(inst, GiantToolGroupingModifier::new)
   );

   public GiantToolGroupingModifier(LootItemCondition[] conditions) {
      super(conditions);
   }

   @NotNull
   protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
      if (context.getParam(LootContextParams.THIS_ENTITY) instanceof Player player
         && !generatedLoot.isEmpty()
         && ((ItemStack)generatedLoot.getFirst()).getItem() instanceof BlockItem block
         && CONVERSIONS.containsKey(block.getBlock())) {
         GiantPickaxeMiningAttachment attachment = (GiantPickaxeMiningAttachment)player.getData(TFDataAttachments.GIANT_PICKAXE_MINING);
         int blockConversion = attachment.getGiantBlockConversion();
         attachment.setGiantBlockConversion(blockConversion - 1);
         return blockConversion == 64 ? ObjectArrayList.of(new ItemStack[]{new ItemStack((ItemLike)CONVERSIONS.get(block.getBlock()))}) : new ObjectArrayList();
      } else {
         return generatedLoot;
      }
   }

   public MapCodec<? extends IGlobalLootModifier> codec() {
      return CODEC;
   }
}
