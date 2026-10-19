package com.tom.storagemod.components;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.tom.storagemod.Content;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;

public record ConfiguratorComponent(
   BlockPos bound, boolean isBound, boolean selecting, boolean showInvBox, boolean massSelect, BlockPos boxStart, List<BlockPos> selection, long lastAction
) {
   private static final ConfiguratorComponent EMPTY = new ConfiguratorComponent(
      BlockPos.ZERO, false, false, false, false, BlockPos.ZERO, Collections.emptyList(), 0L
   );
   public static final Codec<ConfiguratorComponent> CODEC = RecordCodecBuilder.mapCodec(
         b -> b.group(
               BlockPos.CODEC.fieldOf("bound").forGetter(ConfiguratorComponent::bound),
               Codec.BOOL.fieldOf("is_bound").forGetter(ConfiguratorComponent::isBound),
               Codec.BOOL.fieldOf("selecting").forGetter(ConfiguratorComponent::selecting),
               Codec.BOOL.fieldOf("show_inv_box").forGetter(ConfiguratorComponent::showInvBox),
               Codec.BOOL.fieldOf("mass_select").forGetter(ConfiguratorComponent::massSelect),
               BlockPos.CODEC.fieldOf("box_start").forGetter(ConfiguratorComponent::boxStart),
               Codec.list(BlockPos.CODEC).fieldOf("selection").forGetter(ConfiguratorComponent::selection),
               Codec.LONG.fieldOf("last_action").forGetter(ConfiguratorComponent::lastAction)
            )
            .apply(b, ConfiguratorComponent::new)
      )
      .codec();

   public static ConfiguratorComponent empty() {
      return EMPTY;
   }

   public ConfiguratorComponent hiddenItem(long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, false, false, this.boxStart, this.selection, actionTime);
   }

   public ConfiguratorComponent showInv(List<BlockPos> sel, long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, true, this.massSelect, this.boxStart, sel, actionTime);
   }

   public ConfiguratorComponent massSelectStart(BlockPos pos, long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, this.showInvBox, true, pos, this.selection, actionTime);
   }

   public ConfiguratorComponent massSelectEnd(long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, this.showInvBox, false, BlockPos.ZERO, this.selection, actionTime);
   }

   public ConfiguratorComponent massSelectEnd(List<BlockPos> sel, long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, this.showInvBox, false, BlockPos.ZERO, sel, actionTime);
   }

   public ConfiguratorComponent setSelection(List<BlockPos> sel, long actionTime) {
      return new ConfiguratorComponent(this.bound, this.isBound, this.selecting, this.showInvBox, this.massSelect, this.boxStart, sel, actionTime);
   }

   public static class Configurator {
      private ItemStack stack;
      private ConfiguratorComponent comp;
      private long actionTime;

      public Configurator(ItemStack stack, long actionTime) {
         this.stack = stack;
         this.comp = (ConfiguratorComponent)stack.get(Content.configuratorComponent.get());
         this.actionTime = actionTime;
      }

      public boolean debounce() {
         return this.actionTime - this.comp.lastAction() < 3L;
      }

      public BlockPos bound() {
         return this.comp.bound();
      }

      public BlockPos boxStart() {
         return this.comp.boxStart();
      }

      public boolean isBound() {
         return this.comp.isBound();
      }

      public boolean massSelect() {
         return this.comp.massSelect();
      }

      public boolean selecting() {
         return this.comp.selecting();
      }

      public List<BlockPos> selection() {
         return this.comp.selection();
      }

      public boolean showInvBox() {
         return this.comp.showInvBox();
      }

      public void massSelectStart(BlockPos pos) {
         this.set(this.comp.massSelectStart(pos, this.actionTime));
      }

      public void massSelectEnd() {
         this.set(this.comp.massSelectEnd(this.actionTime));
      }

      public void clear() {
         this.set(ConfiguratorComponent.EMPTY);
      }

      public void showInvBox(Collection<BlockPos> sel) {
         this.set(this.comp.showInv(new ArrayList<>(sel), this.actionTime));
      }

      public void massSelectEnd(Collection<BlockPos> sel) {
         this.set(this.comp.massSelectEnd(new ArrayList<>(sel), this.actionTime));
      }

      public void setSelection(Collection<BlockPos> sel) {
         this.set(this.comp.setSelection(new ArrayList<>(sel), this.actionTime));
      }

      public void startSelection(BlockPos bind, Collection<BlockPos> sel) {
         this.set(new ConfiguratorComponent(bind, true, true, false, false, BlockPos.ZERO, new ArrayList<>(sel), this.actionTime));
      }

      private void set(ConfiguratorComponent c) {
         this.comp = c;
         this.stack.set(Content.configuratorComponent.get(), c);
      }
   }
}
