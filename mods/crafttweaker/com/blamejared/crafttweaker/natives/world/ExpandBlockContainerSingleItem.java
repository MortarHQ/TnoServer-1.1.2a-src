package com.blamejared.crafttweaker.natives.world;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.ticks.ContainerSingleItem;
import net.minecraft.world.ticks.ContainerSingleItem.BlockContainerSingleItem;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/world/BlockContainerSingleItem")
@NativeTypeRegistration(value = BlockContainerSingleItem.class, zenCodeName = "crafttweaker.api.world.BlockContainerSingleItem")
public class ExpandBlockContainerSingleItem {
   @ZenCodeType.Method
   public static BlockEntity getContainerBlockEntity(BlockContainerSingleItem internal) {
      return internal.getContainerBlockEntity();
   }

   @ZenCodeType.Method
   public static boolean stillValid(ContainerSingleItem internal, Player player) {
      return internal.stillValid(player);
   }
}
