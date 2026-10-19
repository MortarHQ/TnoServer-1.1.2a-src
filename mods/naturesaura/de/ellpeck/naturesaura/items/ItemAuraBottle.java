package de.ellpeck.naturesaura.items;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import de.ellpeck.naturesaura.api.NaturesAuraAPI;
import de.ellpeck.naturesaura.api.aura.chunk.IAuraChunk;
import de.ellpeck.naturesaura.api.aura.type.IAuraType;
import de.ellpeck.naturesaura.data.ItemModelGenerator;
import de.ellpeck.naturesaura.reg.IColorProvidingItem;
import de.ellpeck.naturesaura.reg.ICustomCreativeTab;
import de.ellpeck.naturesaura.reg.ICustomItemModel;
import java.util.List;
import net.minecraft.client.color.item.ItemColor;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.FastColor.ARGB32;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ClipContext.Fluid;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.HitResult.Type;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.entity.player.PlayerInteractEvent.RightClickItem;

public class ItemAuraBottle extends ItemImpl implements IColorProvidingItem, ICustomItemModel, ICustomCreativeTab {
   public ItemAuraBottle(Item emptyBottle) {
      super("aura_bottle");
      NeoForge.EVENT_BUS.register(new ItemAuraBottle.EventHandler());
      DispenserBlock.registerBehavior(emptyBottle, (source, stack) -> {
         Level level = source.level();
         BlockState state = source.state();
         Direction facing = (Direction)state.getValue(DispenserBlock.FACING);
         BlockPos offset = source.pos().relative(facing);
         BlockState offsetState = level.getBlockState(offset);
         ItemStack dispense = stack.split(1);
         if (offsetState.isAir()) {
            ItemStack bottle = create(level, offset);
            if (!bottle.isEmpty()) {
               dispense = bottle;
            }
         }

         DefaultDispenseItemBehavior.spawnItem(level, dispense, 6, facing, DispenserBlock.getDispensePosition(source));
         return stack;
      });
   }

   @Override
   public List<ItemStack> getCreativeTabItems() {
      return NaturesAuraAPI.AURA_TYPES.values().stream().map(t -> setType(new ItemStack(this), t)).toList();
   }

   public Component getName(ItemStack stack) {
      return Component.translatable(stack.getDescriptionId() + "." + getType(stack).getName());
   }

   @OnlyIn(Dist.CLIENT)
   @Override
   public ItemColor getItemColor() {
      return (stack, tintIndex) -> tintIndex > 0 ? ARGB32.opaque(getType(stack).getColor()) : -1;
   }

   @Override
   public void generateCustomItemModel(ItemModelGenerator generator) {
      ((ItemModelBuilder)((ItemModelBuilder)generator.withExistingParent(this.getBaseName(), "item/generated")).texture("layer0", "item/" + this.getBaseName()))
         .texture("layer1", "item/" + this.getBaseName() + "_overlay");
   }

   public static IAuraType getType(ItemStack stack) {
      if (!stack.has(ItemAuraBottle.Data.TYPE)) {
         return NaturesAuraAPI.TYPE_OTHER;
      }

      String type = ((ItemAuraBottle.Data)stack.get(ItemAuraBottle.Data.TYPE)).auraType;
      return type.isEmpty() ? NaturesAuraAPI.TYPE_OTHER : NaturesAuraAPI.AURA_TYPES.get(ResourceLocation.parse(type));
   }

   public static ItemStack setType(ItemStack stack, IAuraType type) {
      stack.set(ItemAuraBottle.Data.TYPE, new ItemAuraBottle.Data(type.getName().toString()));
      return stack;
   }

   private static ItemStack create(Level level, BlockPos pos) {
      int aura = IAuraChunk.getAuraInArea(level, pos, 30);
      if (aura <= -100000) {
         return new ItemStack(ModItems.VACUUM_BOTTLE);
      } else if (aura >= 100000) {
         BlockPos spot = IAuraChunk.getHighestSpot(level, pos, 30, pos);
         IAuraChunk.getAuraChunk(level, spot).drainAura(spot, 20000);
         return setType(new ItemStack(ModItems.AURA_BOTTLE), IAuraType.forLevel(level));
      } else {
         return ItemStack.EMPTY;
      }
   }

   public record Data(String auraType) {
      public static final Codec<ItemAuraBottle.Data> CODEC = RecordCodecBuilder.create(
         i -> i.group(Codec.STRING.fieldOf("aura_type").forGetter(d -> d.auraType)).apply(i, ItemAuraBottle.Data::new)
      );
      public static final DataComponentType<ItemAuraBottle.Data> TYPE = DataComponentType.builder().persistent(CODEC).cacheEncoding().build();
   }

   private static class EventHandler {
      @SubscribeEvent
      public void onRightClick(RightClickItem event) {
         ItemStack held = event.getItemStack();
         if (!held.isEmpty() && held.getItem() == ModItems.BOTTLE_TWO_THE_REBOTTLING) {
            Player player = event.getEntity();
            HitResult ray = Item.getPlayerPOVHitResult(player.level(), player, Fluid.NONE);
            if (ray.getType() != Type.BLOCK) {
               ItemStack bottle = ItemAuraBottle.create(player.level(), player.blockPosition());
               if (!bottle.isEmpty()) {
                  if (!player.level().isClientSide) {
                     held.shrink(1);
                     if (!player.addItem(bottle)) {
                        player.level().addFreshEntity(new ItemEntity(player.level(), player.getX(), player.getY(), player.getZ(), bottle));
                     }

                     player.level()
                        .playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.PLAYERS, 1.0F, 1.0F);
                  }

                  player.swing(event.getHand());
               }
            }
         }
      }
   }
}
