package net.p3pp3rf1y.sophisticatedcore.compat.recipeviewers.rei;

import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessorRegistry;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessorRegistry.Serializer;
import me.shedaniel.rei.forge.REIPluginCommon;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;

@REIPluginCommon
public class CoreReiCommonPlugin implements REIServerPlugin {
   public void registerSlotAccessors(SlotAccessorRegistry registry) {
      registry.register(
         ResourceLocation.fromNamespaceAndPath("sophisticatedcore", "storage"), slotAccessor -> slotAccessor instanceof ReiSlotAccessor, new Serializer() {
            public SlotAccessor read(AbstractContainerMenu menu, Player player, CompoundTag tag) {
               int slot = tag.getInt("Slot");
               return new ReiSlotAccessor(menu.getSlot(slot));
            }

            public CompoundTag save(AbstractContainerMenu menu, Player player, SlotAccessor accessor) {
               if (accessor instanceof ReiSlotAccessor reiSlotAccessor) {
                  CompoundTag tag = new CompoundTag();
                  tag.putInt("Slot", reiSlotAccessor.getIndex());
                  return tag;
               } else {
                  throw new IllegalArgumentException("Cannot save non-sophisticated slot accessor!");
               }
            }
         }
      );
   }
}
