package com.blamejared.crafttweaker.natives.item.component;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import com.blamejared.crafttweaker.api.tag.type.KnownTag;
import com.blamejared.crafttweaker_annotations.annotations.Document;
import com.blamejared.crafttweaker_annotations.annotations.NativeTypeRegistration;
import java.util.List;
import net.minecraft.core.Holder;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.component.Tool.Rule;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.openzen.zencode.java.ZenCodeType;

@ZenRegister
@Document("vanilla/api/item/component/Tool")
@NativeTypeRegistration(value = Tool.class, zenCodeName = "crafttweaker.api.item.component.Tool")
public class ExpandTool {
   @ZenCodeType.StaticExpansionMethod
   public static Tool of(List<Rule> rules, float defaultMiningSpeed, int damagePerBlock) {
      return new Tool(rules, defaultMiningSpeed, damagePerBlock);
   }

   @ZenCodeType.Method
   public static float getMiningSpeed(Tool internal, BlockState state) {
      return internal.getMiningSpeed(state);
   }

   @ZenCodeType.Getter("rules")
   public static List<Rule> rules(Tool internal) {
      return internal.rules();
   }

   @ZenCodeType.Method
   public static boolean isCorrectForDrops(Tool internal, BlockState state) {
      return internal.isCorrectForDrops(state);
   }

   @ZenCodeType.Getter("damagePerBlock")
   public static int damagePerBlock(Tool internal) {
      return internal.damagePerBlock();
   }

   @ZenCodeType.Getter("defaultMiningSpeed")
   public static float defaultMiningSpeed(Tool internal) {
      return internal.defaultMiningSpeed();
   }

   @ZenRegister
   @Document("vanilla/api/item/component/ToolRule")
   @NativeTypeRegistration(value = Rule.class, zenCodeName = "crafttweaker.api.item.component.ToolRule")
   public static class ExpandToolRule {
      @ZenCodeType.Getter("hasCorrectToolForDrops")
      public static boolean hasCorrectToolForDrops(Rule internal) {
         return internal.correctForDrops().isPresent();
      }

      @ZenCodeType.Getter("correctToolForDrops")
      public static boolean correctForDrops(Rule internal) {
         return (Boolean)internal.correctForDrops()
            .orElseThrow(
               () -> new IllegalStateException("Rule does not have a value for 'correctToolForDrops'! Be sure to check 'hasCorrectToolForDrops' first!")
            );
      }

      @ZenCodeType.Getter("speed")
      public static @ZenCodeType.Nullable Float speed(Rule internal) {
         return (Float)internal.speed().orElse(null);
      }

      @ZenCodeType.Getter("blocks")
      public static List<Block> blocks(Rule internal) {
         return internal.blocks().stream().<Block>map(Holder::value).toList();
      }

      @ZenCodeType.StaticExpansionMethod
      public static Rule overrideSpeed(List<Block> blocks, float speed) {
         return Rule.overrideSpeed(blocks, speed);
      }

      @ZenCodeType.StaticExpansionMethod
      public static Rule overrideSpeed(KnownTag<Block> tag, float speed) {
         return Rule.overrideSpeed(tag.getTagKey(), speed);
      }

      @ZenCodeType.StaticExpansionMethod
      public static Rule deniesDrops(KnownTag<Block> tag) {
         return Rule.deniesDrops(tag.getTagKey());
      }

      @ZenCodeType.StaticExpansionMethod
      public static Rule minesAndDrops(KnownTag<Block> tag, float speed) {
         return Rule.minesAndDrops(tag.getTagKey(), speed);
      }

      @ZenCodeType.StaticExpansionMethod
      public static Rule minesAndDrops(List<Block> blocks, float speed) {
         return Rule.minesAndDrops(blocks, speed);
      }
   }
}
