package xaero.hud.category.ui.node.options.text;

import java.util.List;
import javax.annotation.Nonnull;
import xaero.common.misc.ListFactory;
import xaero.hud.category.ui.node.options.EditorOptionNode;

public final class TextFieldSuggestionsResolver {
   private ListFactory listFactory;

   private TextFieldSuggestionsResolver(@Nonnull ListFactory listFactory) {
      this.listFactory = listFactory;
   }

   public List<EditorOptionNode<String>> getSuggestions(String input, List<EditorOptionNode<String>> allOptions) {
      if (input.isEmpty()) {
         return this.listFactory.get();
      }

      String lowerCaseInput = input.toLowerCase();
      return allOptions.stream().filter(o -> o.getValue() != null && o.getValue().toString().toLowerCase().contains(lowerCaseInput)).sorted((o1, o2) -> {
         boolean firstStarts = o1.getValue().toString().toLowerCase().startsWith(lowerCaseInput);
         boolean secondStarts = o2.getValue().toString().toLowerCase().startsWith(lowerCaseInput);
         return firstStarts == secondStarts ? 0 : (firstStarts ? -1 : 1);
      }).limit(100L).collect(this.listFactory::get, List::add, List::addAll);
   }

   public static final class Builder {
      private final ListFactory listFactory;

      private Builder(ListFactory listFactory) {
         this.listFactory = listFactory;
      }

      public TextFieldSuggestionsResolver.Builder setDefault() {
         return this;
      }

      public TextFieldSuggestionsResolver build() {
         if (this.listFactory == null) {
            throw new IllegalStateException("required fields not set!");
         } else {
            return new TextFieldSuggestionsResolver(this.listFactory);
         }
      }

      public static TextFieldSuggestionsResolver.Builder begin(ListFactory listFactory) {
         return new TextFieldSuggestionsResolver.Builder(listFactory).setDefault();
      }
   }
}
