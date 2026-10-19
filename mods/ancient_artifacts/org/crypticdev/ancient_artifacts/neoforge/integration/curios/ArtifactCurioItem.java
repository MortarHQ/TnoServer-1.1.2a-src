package org.crypticdev.ancient_artifacts.neoforge.integration.curios;

import com.google.common.collect.Multimap;
import io.github.manasmods.tensura.registry.item.misc.TensuraDataComponents;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.Item.Properties;
import net.minecraft.world.item.Item.TooltipContext;
import net.minecraft.world.level.Level;
import org.crypticdev.ancient_artifacts.AncientArtifacts;
import org.crypticdev.ancient_artifacts.artifact.ArtifactDefinition;
import org.crypticdev.ancient_artifacts.artifact.ArtifactItem;
import org.crypticdev.ancient_artifacts.artifact.ArtifactText;
import org.jetbrains.annotations.NotNull;
import top.theillusivec4.curios.api.SlotContext;
import top.theillusivec4.curios.api.type.capability.ICurioItem;

public class ArtifactCurioItem extends Item implements ICurioItem, ArtifactItem {
   private final ArtifactDefinition definition;

   public ArtifactCurioItem(ArtifactDefinition definition, Properties properties) {
      super(properties);
      this.definition = definition;
   }

   public void inventoryTick(ItemStack stack, Level level, Entity entity, int slot, boolean selected) {
      if (!stack.has((DataComponentType)TensuraDataComponents.EP.get())) {
         stack.set(
            (DataComponentType)TensuraDataComponents.EP.get(), AncientArtifacts.CONFIG.getGradeConfig(this.definition.defaultRefinement().grade()).StartingEp
         );
      }

      if (!stack.has((DataComponentType)TensuraDataComponents.EP_DURABILITY.get())) {
         stack.set(
            (DataComponentType)TensuraDataComponents.EP_DURABILITY.get(),
            AncientArtifacts.CONFIG.getGradeConfig(this.definition.defaultRefinement().grade()).StartingEp
         );
      }

      if (!stack.has((DataComponentType)TensuraDataComponents.EP_GAIN.get())) {
         stack.set(
            (DataComponentType)TensuraDataComponents.EP_GAIN.get(), AncientArtifacts.CONFIG.getGradeConfig(this.definition.defaultRefinement().grade()).EPGain
         );
      }

      if (!stack.has((DataComponentType)TensuraDataComponents.MAX_EP.get())) {
         stack.set(
            (DataComponentType)TensuraDataComponents.MAX_EP.get(),
            AncientArtifacts.CONFIG.getGradeConfig(this.definition.defaultRefinement().grade()).EffectUnlockEp.getLast()
         );
      }

      super.inventoryTick(stack, level, entity, slot, selected);
   }

   @NotNull
   public Component getName(ItemStack stack) {
      return ArtifactText.name(this, stack);
   }

   @Override
   public ArtifactDefinition getArtifact() {
      return this.definition;
   }

   public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
      ArtifactText.appendTooltip(stack, tooltip);
      super.appendHoverText(stack, context, tooltip, flag);
   }

   public Multimap<Holder<Attribute>, AttributeModifier> getAttributeModifiers(SlotContext slotContext, ResourceLocation id, ItemStack stack) {
      return super.getAttributeModifiers(slotContext, id, stack);
   }
}
