package dev.ftb.mods.ftblibrary.config.ui.resource;

import dev.architectury.fluid.FluidStack;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;

public record FluidStackResource(FluidStack resource) implements SelectableResource<FluidStack> {
   @Override
   public long getCount() {
      return this.resource().getAmount();
   }

   @Override
   public void setCount(int count) {
      this.resource.setAmount(count);
   }

   @Override
   public Component getName() {
      return this.resource.getName();
   }

   @Override
   public Icon getIcon() {
      return Icon.getIcon(ClientUtils.getStillTexture(this.resource)).withTint(Color4I.rgb(ClientUtils.getFluidColor(this.resource)));
   }

   @Override
   public SelectableResource<FluidStack> copyWithCount(long count) {
      return SelectableResource.fluid(this.resource.copyWithAmount(count));
   }

   @Override
   public CompoundTag getComponentsTag() {
      Tag tag = (Tag)DataComponentMap.CODEC.encodeStart(NbtOps.INSTANCE, this.resource.getComponents()).result().orElse(new CompoundTag());
      return tag instanceof CompoundTag t ? t : null;
   }

   @Override
   public void applyComponentsTag(CompoundTag tag) {
      DataComponentMap.CODEC.parse(NbtOps.INSTANCE, tag).result().ifPresent(this.resource::applyComponents);
   }
}
