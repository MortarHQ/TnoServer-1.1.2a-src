package dev.ftb.mods.ftbfiltersystem.filter;

import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.ftb.mods.ftbfiltersystem.FTBFilterSystem;
import dev.ftb.mods.ftbfiltersystem.api.FTBFilterSystemAPI;
import dev.ftb.mods.ftbfiltersystem.api.FilterException;
import dev.ftb.mods.ftbfiltersystem.api.filter.AbstractSmartFilter;
import dev.ftb.mods.ftbfiltersystem.api.filter.SmartFilter;
import dev.ftb.mods.ftbfiltersystem.util.PlatformUtil;
import net.minecraft.core.HolderLookup.Provider;
import net.minecraft.core.component.DataComponentMap;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.nbt.TagParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ComponentFilter extends AbstractSmartFilter {
   public static final ResourceLocation ID = FTBFilterSystemAPI.rl("component");
   protected final DataComponentMap map;
   private final boolean fuzzyMatch;

   public ComponentFilter(@Nullable SmartFilter.Compound parent) {
      this(parent, true, DataComponentMap.EMPTY);
   }

   public ComponentFilter(SmartFilter.Compound parent, boolean fuzzyMatch, DataComponentMap map) {
      super(parent);
      this.fuzzyMatch = fuzzyMatch;
      this.map = map;
   }

   @NotNull
   public static String getPrefixStr(boolean fuzzy) {
      return fuzzy ? "fuzzy:" : "strict:";
   }

   public String getStringArgWithoutPrefix(Provider registryAccess) {
      return this.getStringArg(registryAccess).replaceAll("(fuzzy|strict):", "");
   }

   @Override
   public ResourceLocation getId() {
      return ID;
   }

   public boolean test(ItemStack stack) {
      return PlatformUtil.hasComponentPatch(stack)
         ? (this.fuzzyMatch ? this.fuzzyMatch(stack.getComponents()) : stack.getComponents().equals(this.map))
         : this.map.isEmpty();
   }

   private boolean fuzzyMatch(DataComponentMap toMatch) {
      return this.map.stream().allMatch(tc -> toMatch.has(tc.type()) && toMatch.get(tc.type()).equals(tc.value()));
   }

   @Override
   public String getStringArg(Provider registryAccess) {
      try {
         Tag tag = (Tag)DataComponentMap.CODEC.encodeStart(registryAccess.createSerializationContext(NbtOps.INSTANCE), this.map).getOrThrow();
         return getPrefixStr(this.fuzzyMatch) + tag.toString();
      } catch (IllegalStateException e) {
         FTBFilterSystem.LOGGER.error("can't encode component filter: {}", e.getMessage());
         return "";
      }
   }

   public DataComponentMap getComponentMap() {
      return this.map;
   }

   public boolean isFuzzyMatch() {
      return this.fuzzyMatch;
   }

   public static ComponentFilter fromString(SmartFilter.Compound parent, String str, Provider registryAccess) {
      try {
         boolean fuzzy = true;
         if (str.startsWith("strict:") || str.startsWith("fuzzy:")) {
            fuzzy = str.startsWith("fuzzy:");
            str = str.substring(str.indexOf(58) + 1);
         }

         DataComponentMap map = (DataComponentMap)DataComponentMap.CODEC
            .parse(registryAccess.createSerializationContext(NbtOps.INSTANCE), parseNBT(str))
            .getOrThrow(FilterException::new);
         return new ComponentFilter(parent, fuzzy, map);
      } catch (CommandSyntaxException e) {
         throw new FilterException("invalid NBT tag: " + str, e);
      }
   }

   private static CompoundTag parseNBT(String str) throws CommandSyntaxException {
      if (!str.startsWith("{") && !str.endsWith("}")) {
         str = "{" + str + "}";
      }

      return TagParser.parseTag(str);
   }
}
