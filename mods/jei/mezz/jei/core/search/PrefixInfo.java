package mezz.jei.core.search;

import java.util.Collection;
import java.util.function.Supplier;
import org.jetbrains.annotations.Unmodifiable;

public class PrefixInfo<T, I> {
   private final char prefix;
   private final PrefixInfo.IModeGetter modeGetter;
   private final PrefixInfo.IStringsGetter<T> stringsGetter;
   private final Supplier<ISearchStorage<I>> storageSupplier;

   public PrefixInfo(char prefix, PrefixInfo.IModeGetter modeGetter, PrefixInfo.IStringsGetter<T> stringsGetter, Supplier<ISearchStorage<I>> storageSupplier) {
      this.prefix = prefix;
      this.modeGetter = modeGetter;
      this.stringsGetter = stringsGetter;
      this.storageSupplier = storageSupplier;
   }

   public char getPrefix() {
      return this.prefix;
   }

   public SearchMode getMode() {
      return this.modeGetter.getMode();
   }

   public ISearchStorage<I> createStorage() {
      return this.storageSupplier.get();
   }

   public @Unmodifiable Collection<String> getStrings(T element) {
      return this.stringsGetter.getStrings(element);
   }

   @Override
   public String toString() {
      return "PrefixInfo{" + this.prefix + "}";
   }

   @FunctionalInterface
   public interface IModeGetter {
      SearchMode getMode();
   }

   @FunctionalInterface
   public interface IStringsGetter<T> {
      @Unmodifiable Collection<String> getStrings(T var1);
   }
}
