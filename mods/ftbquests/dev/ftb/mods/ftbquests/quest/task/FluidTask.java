package dev.ftb.mods.ftbquests.quest.task;

import dev.architectury.fluid.FluidStack;
import dev.ftb.mods.ftblibrary.config.ConfigGroup;
import dev.ftb.mods.ftblibrary.icon.Color4I;
import dev.ftb.mods.ftblibrary.icon.Icon;
import dev.ftb.mods.ftblibrary.ui.Widget;
import dev.ftb.mods.ftblibrary.util.StringUtils;
import dev.ftb.mods.ftblibrary.util.client.ClientUtils;
import dev.ftb.mods.ftblibrary.util.client.PositionedIngredient;
import dev.ftb.mods.ftbquests.api.FTBQuestsAPI;
import dev.ftb.mods.ftbquests.quest.Quest;
import dev.ftb.mods.ftbquests.quest.TeamData;
import java.util.Optional;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.core.component.DataComponentPatch;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;

public class FluidTask extends Task {
   public static final ResourceLocation TANK_TEXTURE = FTBQuestsAPI.rl("textures/tasks/tank.png");
   private static final FluidStack WATER = FluidStack.create(Fluids.WATER, FluidStack.bucketAmount());
   private FluidStack fluidStack = FluidStack.create(Fluids.WATER, FluidStack.bucketAmount());

   public FluidTask(long id, Quest quest) {
      super(id, quest);
   }

   public Fluid getFluid() {
      return this.fluidStack.getFluid();
   }

   public FluidTask setFluid(FluidStack fluidStack) {
      this.fluidStack = fluidStack;
      return this;
   }

   public DataComponentMap getFluidDataComponents() {
      return this.fluidStack.getComponents();
   }

   public DataComponentPatch getFluidDataComponentPatch() {
      return this.fluidStack.getComponents().asPatch();
   }

   @Override
   public TaskType getType() {
      return TaskTypes.FLUID;
   }

   @Override
   public long getMaxProgress() {
      return this.fluidStack.getAmount();
   }

   @Override
   public String formatMaxProgress() {
      return getVolumeString(this.fluidStack.getAmount());
   }

   @Override
   public String formatProgress(TeamData teamData, long progress) {
      return getVolumeString((int)Math.min(2147483647L, progress));
   }

   @Override
   public boolean consumesResources() {
      return true;
   }

   @Override
   public void writeData(CompoundTag nbt, Provider provider) {
      super.writeData(nbt, provider);
      nbt.put("fluid", this.fluidStack.write(provider, new CompoundTag()));
   }

   @Override
   public void readData(CompoundTag nbt, Provider provider) {
      super.readData(nbt, provider);
      if (nbt.contains("fluid", 8)) {
         ResourceLocation id = ResourceLocation.tryParse(nbt.getString("fluid"));
         if (id == null) {
            this.fluidStack = FluidStack.create(Fluids.WATER, 1000L);
         } else {
            this.fluidStack = FluidStack.create((Fluid)BuiltInRegistries.FLUID.get(id), nbt.getLong("amount"));
         }
      } else {
         this.fluidStack = FluidStack.read(provider, nbt.getCompound("fluid")).orElse(FluidStack.empty());
      }
   }

   @Override
   public void writeNetData(RegistryFriendlyByteBuf buffer) {
      super.writeNetData(buffer);
      this.fluidStack.write(buffer);
   }

   @Override
   public void readNetData(RegistryFriendlyByteBuf buffer) {
      super.readNetData(buffer);
      this.fluidStack = FluidStack.read(buffer);
   }

   public static String getVolumeString(long a) {
      StringBuilder builder = new StringBuilder();
      if (a >= FluidStack.bucketAmount()) {
         if (a % FluidStack.bucketAmount() != 0L) {
            builder.append(StringUtils.formatDouble((double)a / FluidStack.bucketAmount()));
         } else {
            builder.append(a / FluidStack.bucketAmount());
         }

         builder.append(" B");
      } else {
         builder.append(a).append(" mB");
      }

      return builder.toString();
   }

   public MutableComponent getAltTitle() {
      return Component.literal(getVolumeString(this.fluidStack.getAmount()) + " of ").append(this.fluidStack.getName());
   }

   @Override
   public Icon getAltIcon() {
      return Icon.getIcon(ClientUtils.getStillTexture(this.fluidStack)).withTint(Color4I.rgb(ClientUtils.getFluidColor(this.fluidStack)));
   }

   @Override
   public void fillConfigGroup(ConfigGroup config) {
      super.fillConfigGroup(config);
      config.addFluidStack("fluid", this.fluidStack, v -> this.fluidStack = v, WATER, false);
   }

   @Override
   public boolean canInsertItem() {
      return true;
   }

   @Nullable
   @Override
   public Optional<PositionedIngredient> getIngredient(Widget widget) {
      return PositionedIngredient.of(this.fluidStack, widget);
   }
}
