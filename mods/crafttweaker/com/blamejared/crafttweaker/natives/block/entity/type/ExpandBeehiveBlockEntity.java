package com.blamejared.crafttweaker.natives.block.entity.type;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BeehiveBlockEntity.Occupant;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/entity/type/BeehiveBlockEntity")
@NativeTypeRegistration(value = BeehiveBlockEntity.class, zenCodeName = "crafttweaker.api.block.entity.type.BeehiveBlockEntity")
public class ExpandBeehiveBlockEntity {
   @ZenRegister
   @Document("vanilla/api/block/entity/type/BeehiveBlockEntityOccupant")
   @NativeTypeRegistration(value = Occupant.class, zenCodeName = "crafttweaker.api.block.entity.type.BeehiveBlockEntityOccupant")
   public static class ExpandBeehiveBlockEntityOccupant {
      @ZenCodeType.StaticExpansionMethod
      public static Occupant of(Entity entity) {
         return Occupant.of(entity);
      }

      @ZenCodeType.StaticExpansionMethod
      public static Occupant create(int ticksInHive) {
         return Occupant.create(ticksInHive);
      }

      @ZenCodeType.Method
      public static @ZenCodeType.Nullable Entity createEntity(Occupant internal, Level level, BlockPos pos) {
         return internal.createEntity(level, pos);
      }

      @ZenCodeType.Getter("entityData")
      public static CustomData entityData(Occupant internal) {
         return internal.entityData();
      }

      @ZenCodeType.Getter("ticksInHive")
      public static int ticksInHive(Occupant internal) {
         return internal.ticksInHive();
      }

      @ZenCodeType.Getter("minTicksInHive")
      public static int minTicksInHive(Occupant internal) {
         return internal.minTicksInHive();
      }
   }
}
