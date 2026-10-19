package dev.ftb.mods.ftblibrary.icon;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.architectury.registry.registries.RegistrarManager;
import dev.ftb.mods.ftblibrary.FTBLibrary;
import dev.ftb.mods.ftblibrary.ui.GuiHelper;
import java.util.function.Supplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

public class ItemIcon extends Icon implements IResourceIcon {
   private final ItemStack stack;

   private ItemIcon(ItemStack is) {
      this.stack = is;
   }

   public static Icon getItemIcon(ItemStack stack) {
      if (stack.isEmpty()) {
         return empty();
      } else {
         return stack.getItem() instanceof CustomIconItem ? ((CustomIconItem)stack.getItem()).getCustomIcon(stack) : new ItemIcon(stack);
      }
   }

   public static Icon getItemIcon(Item item) {
      return item == Items.AIR ? empty() : getItemIcon(item.getDefaultInstance());
   }

   public static Icon getItemIcon(String lazyStackString) {
      return lazyStackString.isEmpty()
         ? empty()
         : new LazyIcon(
            () -> {
               String[] s = lazyStackString.split(" ", 4);
               ItemStack stack = ((Item)BuiltInRegistries.ITEM.get(ResourceLocation.parse(s[0]))).getDefaultInstance();
               if (s.length >= 2 && !s[1].equals("1")) {
                  stack.setCount(Integer.parseInt(s[1]));
               }

               if (s.length >= 3 && !s[2].equals("0")) {
                  stack.setDamageValue(Integer.parseInt(s[2]));
               }

               if (s.length >= 4 && !s[3].equals("null")) {
                  try {
                     DataComponentMap.CODEC
                        .parse(NbtOps.INSTANCE, TagParser.parseTag(s[3]))
                        .resultOrPartial(err -> FTBLibrary.LOGGER.error("can't parse data component map for {}: {}", s[3], err))
                        .ifPresent(stack::applyComponents);
                  } catch (CommandSyntaxException ex) {
                     FTBLibrary.LOGGER.error("can't parse data component tag for item icon: {} ({})", lazyStackString, ex.getMessage());
                  }
               }

               if (stack.isEmpty()) {
                  ItemStack fallback = new ItemStack(Items.BARRIER);
                  fallback.set(DataComponents.CUSTOM_NAME, Component.literal(lazyStackString));
                  return getItemIcon(fallback);
               } else {
                  return getItemIcon(stack);
               }
            }
         ) {
            @Override
            public String toString() {
               return "item:" + lazyStackString;
            }
         };
   }

   @OnlyIn(Dist.CLIENT)
   public static void drawItem3D(GuiGraphics graphics, ItemStack stack) {
      Minecraft.getInstance()
         .getItemRenderer()
         .renderStatic(
            stack,
            ItemDisplayContext.FIXED,
            240,
            OverlayTexture.NO_OVERLAY,
            graphics.pose(),
            Minecraft.getInstance().renderBuffers().bufferSource(),
            Minecraft.getInstance().level,
            0
         );
   }

   public ItemStack getStack() {
      return this.stack;
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void draw(GuiGraphics graphics, int x, int y, int w, int h) {
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(x + w / 2.0, y + h / 2.0, 0.0);
      if (w != 16 || h != 16) {
         float s = Math.min(w, h) / 16.0F;
         poseStack.scale(s, s, s);
      }

      GuiHelper.drawItem(graphics, this.getStack(), 0, true, null);
      poseStack.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void drawStatic(GuiGraphics graphics, int x, int y, int w, int h) {
      PoseStack poseStack = graphics.pose();
      poseStack.pushPose();
      poseStack.translate(x + w / 2.0, y + h / 2.0, 0.0);
      if (w != 16 || h != 16) {
         float s = Math.min(w, h) / 16.0F;
         poseStack.scale(s, s, s);
      }

      GuiHelper.drawItem(graphics, this.getStack(), 0, false, null);
      poseStack.popPose();
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public void draw3D(GuiGraphics graphics) {
      drawItem3D(graphics, this.getStack());
   }

   @Override
   public String toString() {
      ItemStack stack = this.getStack();
      StringBuilder builder = new StringBuilder("item:");
      builder.append(RegistrarManager.getId(stack.getItem(), Registries.ITEM));
      int count = stack.getCount();
      int damage = stack.getDamageValue();
      Tag nbt = (Tag)DataComponentMap.CODEC.encodeStart(NbtOps.INSTANCE, stack.getComponents()).result().orElse(null);
      if (count > 1 || damage > 0 || nbt != null) {
         builder.append(' ');
         builder.append(count);
      }

      if (damage > 0 || nbt != null) {
         builder.append(' ');
         builder.append(damage);
      }

      if (nbt != null) {
         builder.append(' ');
         builder.append(nbt);
      }

      return builder.toString();
   }

   @Override
   public int hashCode() {
      return ItemStack.hashItemAndComponents(this.getStack());
   }

   @Override
   public boolean equals(Object o) {
      return o == this || o instanceof ItemIcon && ItemStack.matches(this.getStack(), ((ItemIcon)o).getStack());
   }

   @Nullable
   @Override
   public Object getIngredient() {
      return this.getStack();
   }

   @Override
   public ResourceLocation getResourceLocation() {
      return BuiltInRegistries.ITEM.getKey(this.stack.getItem());
   }
}
