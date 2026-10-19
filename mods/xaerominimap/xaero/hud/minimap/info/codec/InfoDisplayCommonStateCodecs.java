package xaero.hud.minimap.info.codec;

@Deprecated
public class InfoDisplayCommonStateCodecs {
   @Deprecated
   public static final InfoDisplayStateCodec<Boolean> BOOLEAN = new InfoDisplayStateCodec<>(s -> s.equals("true"), Object::toString, 5);
   @Deprecated
   public static final InfoDisplayStateCodec<Integer> INTEGER = new InfoDisplayStateCodec<>(s -> Integer.parseInt(s), Object::toString, "-2147483648".length());
}
