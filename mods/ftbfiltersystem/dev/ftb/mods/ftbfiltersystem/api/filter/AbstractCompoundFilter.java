package dev.ftb.mods.ftbfiltersystem.api.filter;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;

public abstract class AbstractCompoundFilter extends AbstractSmartFilter implements SmartFilter.Compound {
   private final List<SmartFilter> children;

   protected AbstractCompoundFilter(SmartFilter.Compound parent, List<SmartFilter> children) {
      super(parent);
      if (children.size() > this.maxChildren()) {
         throw new FilterException("Too many children! " + children.size() + " > " + this.maxChildren());
      }

      this.children = children;
   }

   public AbstractCompoundFilter(SmartFilter.Compound parent) {
      super(parent);
      this.children = new ArrayList<>();
   }

   @Override
   public List<SmartFilter> getChildren() {
      return this.children;
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      return String.join("", this.children.stream().map(sf -> sf.asString(registryAccess)).toList());
   }

   @Override
   public boolean isConfigurable() {
      return false;
   }

   @Override
   public Component getDisplayName() {
      return super.getDisplayName().copy().withStyle(ChatFormatting.ITALIC).append(" ▶");
   }

   public static <F extends AbstractCompoundFilter> F createCompoundFilter(
      BiFunction<SmartFilter.Compound, List<SmartFilter>, F> creator, SmartFilter.Compound parent, String str, Provider registryAccess
   ) {
      F filter = (F)creator.apply(parent, new ArrayList<>());
      List<SmartFilter> children = FTBFilterSystemAPI.api().parseFilterList(filter, str, registryAccess);
      if (children.size() > filter.maxChildren()) {
         throw new FilterException("Too many children for " + filter.getId() + " - expected " + filter.maxChildren() + ", got " + children.size());
      }

      filter.getChildren().addAll(children);
      return filter;
   }
}
