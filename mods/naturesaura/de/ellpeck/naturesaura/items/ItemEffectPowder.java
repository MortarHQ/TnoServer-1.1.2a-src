package de.ellpeck.naturesaura.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.entities.EntityEffectInhibitor;
import de.ellpeck.naturesaura.reg.IColorProvidingItem;
import de.ellpeck.naturesaura.reg.ICustomCreativeTab;
import java.util.List;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

public class ItemEffectPowder extends ItemImpl implements IColorProvidingItem, ICustomCreativeTab {
   public ItemEffectPowder() {
      super("effect_powder");
   }

   public static ResourceLocation getEffect(ItemStack stack) {
      if (!stack.has(ItemEffectPowder.Data.TYPE)) {
         return null;
      }

      String effect = ((ItemEffectPowder.Data)stack.get(ItemEffectPowder.Data.TYPE)).effect;
      return effect.isEmpty() ? null : ResourceLocation.parse(effect);
   }

   public static ItemStack setEffect(ItemStack stack, ResourceLocation effect) {
      stack.set(ItemEffectPowder.Data.TYPE, new ItemEffectPowder.Data(effect != null ? effect.toString() : ""));
      return stack;
   }

   public InteractionResult useOn(UseOnContext context) {
      Level level = context.getLevel();
      if (!level.isClientSide) {
         Vec3 hit = context.getClickLocation();
         ItemStack stack = context.getPlayer().getItemInHand(context.getHand());
         EntityEffectInhibitor.place(level, stack, hit.x, hit.y + 1.0, hit.z);
         stack.setCount(0);
      }

      return InteractionResult.SUCCESS;
   }

   @Override
   public List<ItemStack> getCreativeTabItems() {
      return NaturesAuraAPI.EFFECT_POWDERS.keySet().stream().map(e -> setEffect(new ItemStack(this), e)).toList();
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(this.getDescriptionId(stack) + "." + getEffect(stack));
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ItemColor getItemColor() {
      return (stack, tintIndex) -> ARGB32.opaque(NaturesAuraAPI.EFFECT_POWDERS.getOrDefault(getEffect(stack), 16777215));
   }

   public record Data(String effect) {
      public static final Codec<ItemEffectPowder.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.STRING.fieldOf("effect").forGetter(d -> d.effect)).apply(i, ItemEffectPowder.Data::new)
      );
      public static final DataComponentType<ItemEffectPowder.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }
}
