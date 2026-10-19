package net.p3pp3rf1y.sophisticatedbackpacks.command;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.core.Registry;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.HolderLookup.RegistryLookup;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.p3pp3rf1y.sophisticatedbackpacks.init.ModItems;

public class BackpackUpgradeItemArgumentType extends ItemArgument {
   private static final Collection<String> EXAMPLES = Arrays.asList("stack_upgrade_tier_1", "sophisticatedbackpacks:stack_upgrade_tier_1");

   static CommandBuildContext upgradeItemContext(final Provider provider) {
      return new CommandBuildContext() {
         public Stream<ResourceKey<? extends Registry<?>>> listRegistries() {
            return provider.listRegistries();
         }

         public <T> Optional<RegistryLookup<T>> lookup(ResourceKey<? extends Registry<? extends T>> resourceKey) {
            return provider.lookup(resourceKey).map(lookup -> lookup.filterElements(item -> new ItemStack((ItemLike)item).is(ModItems.BACKPACK_UPGRADE_TAG)));
         }
      };
   }

   public BackpackUpgradeItemArgumentType(CommandBuildContext context) {
      super(upgradeItemContext(context));
   }

   public static BackpackUpgradeItemArgumentType item(CommandBuildContext context) {
      return new BackpackUpgradeItemArgumentType(context);
   }

   public Collection<String> getExamples() {
      return EXAMPLES;
   }
}
