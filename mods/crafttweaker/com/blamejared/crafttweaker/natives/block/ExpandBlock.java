package com.blamejared.crafttweaker.natives.block;

import com.blamejared.crafttweaker.api.CraftTweakerAPI;
import com.blamejared.crafttweaker.api.action.block.ActionSetBlockProperty;
import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.block.CTBlockIngredient;
import com.blamejared.crafttweaker.mixin.common.access.block.AccessBlockBehaviour;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import com.blamejared.crafttweaker_annotations.annotations.TaggableElement;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/block/Block")
@NativeTypeRegistration(value = Block.class, zenCodeName = "crafttweaker.api.block.Block")
@TaggableElement("minecraft:block")
public class ExpandBlock {
   @ZenCodeType.Method
   @ZenCodeType.Getter("registryName")
   public static ResourceLocation getRegistryName(Block internal) {
      return BuiltInRegistries.BLOCK.getKey(internal);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("defaultState")
   public static BlockState getDefaultState(Block internal) {
      return internal.defaultBlockState();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("lootTable")
   public static String getLootTable(Block internal) {
      return internal.getLootTable().toString();
   }

   @ZenCodeType.Method
   public static boolean isPossibleToRespawnInThis(Block internal, BlockState state) {
      return internal.isPossibleToRespawnInThis(state);
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("descriptionId")
   public static String getDescriptionId(Block internal) {
      return internal.getDescriptionId();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("name")
   public static MutableComponent getName(Block internal) {
      return internal.getName();
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster
   @ZenCodeType.Getter("commandString")
   public static String getCommandString(Block internal) {
      return "<block:" + getRegistryName(internal) + ">";
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("possibleStates")
   public static List<BlockState> getPossibleStates(Block internal) {
      return internal.getStateDefinition().getPossibleStates();
   }

   @ZenCodeType.Method
   @ZenCodeType.Operator(ZenCodeType.OperatorType.EQUALS)
   public static boolean matches(Block internal, Block other) {
      return internal == other;
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("friction")
   public static float getFriction(Block internal) {
      return ((AccessBlockBehaviour)internal).crafttweaker$getFriction();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("friction")
   public static void setFriction(Block internal, float friction) {
      CraftTweakerAPI.apply(
         new ActionSetBlockProperty<>(
            internal,
            "Friction",
            friction,
            ((AccessBlockBehaviour)internal).crafttweaker$getFriction(),
            ((AccessBlockBehaviour)internal)::crafttweaker$setFriction
         )
      );
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("speedFactor")
   public static float getSpeedFactor(Block internal) {
      return internal.getSpeedFactor();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("speedFactor")
   public static void setSpeedFactor(Block internal, float speedFactor) {
      CraftTweakerAPI.apply(
         new ActionSetBlockProperty<>(
            internal,
            "Speed Factor",
            speedFactor,
            ((AccessBlockBehaviour)internal).crafttweaker$getSpeedFactor(),
            ((AccessBlockBehaviour)internal)::crafttweaker$setSpeedFactor
         )
      );
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("jumpFactor")
   public static float getJumpFactor(Block internal) {
      return internal.getJumpFactor();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("jumpFactor")
   public static void setJumpFactor(Block internal, float jumpFactor) {
      CraftTweakerAPI.apply(
         new ActionSetBlockProperty<>(
            internal,
            "Jump Factor",
            jumpFactor,
            ((AccessBlockBehaviour)internal).crafttweaker$getJumpFactor(),
            ((AccessBlockBehaviour)internal)::crafttweaker$setJumpFactor
         )
      );
   }

   @ZenCodeType.Method
   @ZenCodeType.Caster
   public static Item asItem(Block internal) {
      return internal.asItem();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("dynamicShape")
   public static boolean hasDynamicShape(Block internal) {
      return internal.hasDynamicShape();
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("hasCollision")
   public static boolean hasCollision(Block internal) {
      return ((AccessBlockBehaviour)internal).crafttweaker$getHasCollision();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("hasCollision")
   public static void setHasCollision(Block internal, boolean canCollide) {
      CraftTweakerAPI.apply(
         new ActionSetBlockProperty<>(
            internal,
            "Has Collision",
            canCollide,
            ((AccessBlockBehaviour)internal).crafttweaker$getHasCollision(),
            ((AccessBlockBehaviour)internal)::crafttweaker$setHasCollision
         )
      );
   }

   @ZenCodeType.Method
   @ZenCodeType.Getter("explosionResistance")
   public static float getExplosionResistance(Block internal) {
      return ((AccessBlockBehaviour)internal).crafttweaker$getExplosionResistance();
   }

   @ZenCodeType.Method
   @ZenCodeType.Setter("explosionResistance")
   public static void setExplosionResistance(Block internal, float resistance) {
      CraftTweakerAPI.apply(
         new ActionSetBlockProperty<>(
            internal,
            "Explosion Resistance",
            resistance,
            ((AccessBlockBehaviour)internal).crafttweaker$getExplosionResistance(),
            ((AccessBlockBehaviour)internal)::crafttweaker$setExplosionResistance
         )
      );
   }

   @ZenCodeType.Caster(implicit = true)
   public static CTBlockIngredient asBlockIngredient(Block internal) {
      return new CTBlockIngredient.BlockIngredient(internal);
   }

   @ZenCodeType.Operator(ZenCodeType.OperatorType.OR)
   public static CTBlockIngredient asList(Block internal, CTBlockIngredient other) {
      List<CTBlockIngredient> elements = new ArrayList<>();
      elements.add(asBlockIngredient(internal));
      elements.add(other);
      return new CTBlockIngredient.CompoundBlockIngredient(elements);
   }
}
