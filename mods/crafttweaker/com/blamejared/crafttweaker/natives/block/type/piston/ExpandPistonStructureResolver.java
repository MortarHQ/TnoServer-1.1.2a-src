package com.blamejared.crafttweaker.natives.block.type.piston;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.piston.PistonStructureResolver;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/type/piston/PistonStructureResolver")
@NativeTypeRegistration(value = PistonStructureResolver.class, zenCodeName = "crafttweaker.api.block.type.piston.PistonStructureResolver")
public class ExpandPistonStructureResolver {
   @ZenCodeType.Method
   public static boolean resolve(PistonStructureResolver internal) {
      return internal.resolve();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("pushDirection")
   public static Direction getPushDirection(PistonStructureResolver internal) {
      return internal.getPushDirection();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("toPush")
   public static List<BlockPos> getToPush(PistonStructureResolver internal) {
      return internal.getToPush();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("toDestroy")
   public static List<BlockPos> getToDestroy(PistonStructureResolver internal) {
      return internal.getToDestroy();
   }
}
