package terrablender.worldgen.noise;

public interface AreaTransformer0 {
   default AreaFactory run(AreaContext context) {
      return () -> context.createResult((x, y) -> {
         context.initRandom(x, y);
         return this.apply(context, x, y);
      });
   }

   int apply(AreaContext var1, int var2, int var3);
}
