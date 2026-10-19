package net.darkhax.bookshelf.common.impl.command;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import net.darkhax.bookshelf.common.api.commands.PermissionLevel;
import net.darkhax.bookshelf.common.api.commands.args.TagArgument;
import net.darkhax.bookshelf.common.api.util.TextHelper;
import net.darkhax.bookshelf.common.impl.Constants;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class BlockTagToItemTagCommand {
   public static LiteralArgumentBuilder<CommandSourceStack> build(CommandBuildContext context) {
      LiteralArgumentBuilder<CommandSourceStack> root = (LiteralArgumentBuilder<CommandSourceStack>)Commands.literal("block_to_item_tag")
         .requires(PermissionLevel.GAMEMASTER);
      root.then(
         Commands.argument("block_tag", TagArgument.arg(context, Registries.BLOCK))
            .executes(
               ctx -> {
                  TagKey<Block> result = TagArgument.get("block_tag", ctx, Registries.BLOCK);
                  Named<Block> tag = (Named<Block>)BuiltInRegistries.BLOCK.getTag(result).orElseThrow();
                  Set<BlockItem> items = new HashSet<>();

                  for (Item item : BuiltInRegistries.ITEM) {
                     if (item instanceof BlockItem blockItem && tag.contains(blockItem.getBlock().builtInRegistryHolder())) {
                        items.add(blockItem);
                     }
                  }

                  ((CommandSourceStack)ctx.getSource())
                     .sendSuccess(() -> TextHelper.copyText(Constants.GSON_PRETTY.toJson(tagJson(items, BuiltInRegistries.ITEM::getKey))), false);
                  return 0;
               }
            )
      );
      return root;
   }

   private static <T> JsonObject tagJson(Collection<T> entries, Function<T, ResourceLocation> idFunc) {
      JsonArray array = new JsonArray();
      entries.stream().map(entry -> idFunc.apply((T)entry).toString()).sorted().forEach(array::add);
      JsonObject obj = new JsonObject();
      obj.add("values", array);
      return obj;
   }
}
