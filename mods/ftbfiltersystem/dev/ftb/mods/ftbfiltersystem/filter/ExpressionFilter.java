package dev.ftb.mods.ftbfiltersystem.filter;

import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractSmartFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.filter.compound.RootFilter;
import dev.ftb.mods.ftbfiltersystem.util.FilterParser;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

public class ExpressionFilter extends AbstractSmartFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("expression");
   private final String expression;
   private final String customName;
   private final SmartFilter parsedExpression;

   public ExpressionFilter(SmartFilter.Compound parent) throws FilterException {
      this(parent, "", null);
   }

   public ExpressionFilter(SmartFilter.Compound parent, String expression, Provider registryAccess) throws FilterException {
      super(parent);
      String[] parts = expression.split("/", 2);
      this.expression = parts[0];
      this.customName = parts.length > 1 ? parts[1] : "";
      this.parsedExpression = this.expression.isEmpty() ? new RootFilter() : FilterParser.parse(this.expression, registryAccess);
   }

   public String getExpression() {
      return this.expression;
   }

   public String getCustomName() {
      return this.customName;
   }

   public boolean test(ItemStack stack) {
      return this.parsedExpression.test(stack);
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      return this.customName.isEmpty() ? this.expression : this.expression + "/" + this.customName;
   }

   @Override
   public Component getDisplayArg(Provider registryAccess) {
      return (Component)(this.customName.isEmpty() ? super.getDisplayArg(registryAccess) : Component.literal(this.customName));
   }

   public static ExpressionFilter fromString(SmartFilter.Compound parent, String str, Provider registryAccess) throws FilterException {
      return new ExpressionFilter(parent, str, registryAccess);
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }
}
