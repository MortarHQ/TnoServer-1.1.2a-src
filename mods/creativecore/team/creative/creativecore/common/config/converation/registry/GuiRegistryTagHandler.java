package team.creative.creativecore.common.config.converation.registry;

import com.mojang.datafixers.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderSet.Named;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.PlainTextContents.LiteralContents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import team.creative.creativecore.common.gui.GuiParent;
import team.creative.creativecore.common.gui.control.collection.GuiComboBox;
import team.creative.creativecore.common.gui.control.collection.GuiStackSelector;
import team.creative.creativecore.common.util.registry.FilteredHandlerRegistry;
import team.creative.creativecore.common.util.text.TextMapBuilder;
import team.creative.creativecore.common.util.type.map.HashMapList;

@OnlyIn(Dist.CLIENT)
@Environment(EnvType.CLIENT)
public abstract class GuiRegistryTagHandler {
   public static final FilteredHandlerRegistry<Registry, GuiRegistryTagHandler> REGISTRY = new FilteredHandlerRegistry<>(
      new GuiRegistryTagHandler() {
         @Override
         public void createControls(GuiParent parent, Registry registry) {
            parent.add(
               new GuiComboBox<>(
                  "elements",
                  new TextMapBuilder<ResourceLocation>()
                     .addComponent(
                        registry.getTagNames().iterator(),
                        x -> x.getNamespace().equals("minecraft") ? Component.literal(x.getPath()) : Component.literal(x.toString())
                     )
               )
            );
         }

         @Override
         public void loadValue(GuiParent parent, Registry registry, TagKey tag) {
            GuiComboBox<ResourceLocation> box = parent.get("elements");
            box.select(tag.location());
         }

         @Override
         public TagKey saveValue(GuiParent parent, Registry registry) {
            GuiComboBox<ResourceLocation> box = parent.get("elements");
            return TagKey.create(registry.key(), box.selected());
         }
      }
   );

   public abstract void createControls(GuiParent var1, Registry var2);

   public abstract void loadValue(GuiParent var1, Registry var2, TagKey var3);

   public abstract TagKey saveValue(GuiParent var1, Registry var2);

   static {
      REGISTRY.register(BuiltInRegistries.BLOCK, new GuiRegistryTagHandler() {
         private static final RandomSource RANDOM = RandomSource.create();

         @Override
         public void createControls(GuiParent parent, final Registry registry) {
            parent.add(new GuiStackSelector("content", null, new GuiStackSelector.StackCollector(new GuiStackSelector.GuiBlockSelector()) {
               @Override
               public HashMapList<String, ItemStack> collect(Player player) {
                  HashMapList<String, ItemStack> map = new HashMapList<>();
                  Iterator<Pair<TagKey, Named>> iterator = registry.getTags().iterator();

                  while (iterator.hasNext()) {
                     Pair<TagKey, Named> pair = iterator.next();
                     Optional o = ((Named)pair.getSecond()).getRandomElement(<unrepresentable>.RANDOM);
                     if (!o.isEmpty()) {
                        ItemStack stack = new ItemStack((ItemLike)((Holder)o.get()).value());
                        if (stack.isEmpty()) {
                           stack = new ItemStack(Blocks.BARRIER);
                        }

                        stack.set(DataComponents.ITEM_NAME, Component.literal(((TagKey)pair.getFirst()).location().toString()));
                        map.add("tags", stack);
                     }
                  }

                  return map;
               }
            }));
         }

         private String getName(ItemStack stack) {
            MutableComponent comp = (MutableComponent)stack.get(DataComponents.ITEM_NAME);
            return ((LiteralContents)comp.getContents()).text();
         }

         @Override
         public void loadValue(GuiParent parent, Registry registry, TagKey tag) {
            GuiStackSelector selector = parent.get("content");
            String text = tag.location().toString();

            for (ArrayList<ItemStack> stacks : selector.getStacks().values()) {
               for (ItemStack stack : stacks) {
                  if (this.getName(stack).equals(text)) {
                     selector.setSelectedForce(stack);
                     return;
                  }
               }
            }

            Optional o = registry.getRandomElementOf(tag, RANDOM);
            if (!o.isEmpty()) {
               ItemStack stack = new ItemStack((ItemLike)((Holder)o.get()).value());
               stack.set(DataComponents.ITEM_NAME, Component.literal(tag.location().toString()));
               selector.setSelectedForce(stack);
            }
         }

         @Override
         public TagKey saveValue(GuiParent parent, Registry registry) {
            GuiStackSelector selector = parent.get("content");
            return TagKey.create(registry.key(), ResourceLocation.parse(this.getName(selector.getSelected())));
         }
      });
   }
}
