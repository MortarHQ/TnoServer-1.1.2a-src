package mezz.jei.library.plugins.debug;

public class ErrorRecipe {
   private final ErrorRecipe.CrashType type;

   public ErrorRecipe(ErrorRecipe.CrashType type) {
      this.type = type;
   }

   public ErrorRecipe.CrashType getType() {
      return this.type;
   }

   public enum CrashType {
      Draw,
      SetRecipe,
      CreateRecipeExtras,
      OnDisplayedIngredientsUpdate,
      GetTooltip;
   }
}
