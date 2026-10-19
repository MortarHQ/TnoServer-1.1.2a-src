package twilightforest.compat.jei;

import com.mojang.serialization.Codec;
import net.minecraft.world.item.ItemStack;

public record FakeItemEntity(ItemStack stack) {
   public static final Codec<FakeItemEntity> CODEC = ItemStack.STRICT_SINGLE_ITEM_CODEC.xmap(FakeItemEntity::new, FakeItemEntity::stack);
}
