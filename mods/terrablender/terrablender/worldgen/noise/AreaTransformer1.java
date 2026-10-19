package terrablender.worldgen.noise;

public interface AreaTransformer1 {
   default AreaFactory run(AreaContext context, AreaFactory areaFactory) {
      return () -> {
         Area area = areaFactory.make();
         return context.createResult((x, y) -> {
            context.initRandom(x, y);
            return this.apply(context, area, x, y);
         }, area);
      };
   }

   int apply(AreaContext var1, Area var2, int var3, int var4);
}
