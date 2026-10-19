package twilightforest.init;

public abstract class TFKeyBindsCategories {
   public static final TFKeyBindsCategories.Category TRAVELLERS_GEAR = new TFKeyBindsCategories.Category(addCategoryPrefix("travellers_gear"));

   private static String addCategoryPrefix(String s) {
      return "key.twilightforest.categories." + s;
   }

   public record Category(String internalName) {
   }
}
