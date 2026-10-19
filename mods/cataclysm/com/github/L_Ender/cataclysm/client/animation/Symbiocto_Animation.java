package com.github.L_Ender.cataclysm.client.animation;

import net.minecraft.client.animation.AnimationDefinition;

public class Symbiocto_Animation {
   public static final AnimationDefinition IDLE;
   public static final AnimationDefinition WALK;
   public static final AnimationDefinition INK;
   public static final AnimationDefinition ATTACK;

   static {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      //
      // Bytecode:
      // 0000: ldc 1.5
      // 0002: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0005: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0008: ldc "octo_head"
      // 000a: new net/minecraft/client/animation/AnimationChannel
      // 000d: dup
      // 000e: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0011: bipush 4
      // 0012: anewarray 28
      // 0015: dup
      // 0016: bipush 0
      // 0017: new net/minecraft/client/animation/Keyframe
      // 001a: dup
      // 001b: fconst_0
      // 001c: fconst_0
      // 001d: fconst_0
      // 001e: fconst_0
      // 001f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0022: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0025: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0028: aastore
      // 0029: dup
      // 002a: bipush 1
      // 002b: new net/minecraft/client/animation/Keyframe
      // 002e: dup
      // 002f: ldc 0.625
      // 0031: fconst_0
      // 0032: fconst_0
      // 0033: fconst_0
      // 0034: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0037: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 003a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 003d: aastore
      // 003e: dup
      // 003f: bipush 2
      // 0040: new net/minecraft/client/animation/Keyframe
      // 0043: dup
      // 0044: ldc 1.125
      // 0046: fconst_0
      // 0047: ldc -0.7
      // 0049: fconst_0
      // 004a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 004d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0050: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0053: aastore
      // 0054: dup
      // 0055: bipush 3
      // 0056: new net/minecraft/client/animation/Keyframe
      // 0059: dup
      // 005a: ldc 1.5
      // 005c: fconst_0
      // 005d: fconst_0
      // 005e: fconst_0
      // 005f: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0062: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0065: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0068: aastore
      // 0069: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 006c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 006f: ldc "octo_head"
      // 0071: new net/minecraft/client/animation/AnimationChannel
      // 0074: dup
      // 0075: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0078: bipush 4
      // 0079: anewarray 28
      // 007c: dup
      // 007d: bipush 0
      // 007e: new net/minecraft/client/animation/Keyframe
      // 0081: dup
      // 0082: fconst_0
      // 0083: dconst_1
      // 0084: dconst_1
      // 0085: dconst_1
      // 0086: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0089: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 008c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 008f: aastore
      // 0090: dup
      // 0091: bipush 1
      // 0092: new net/minecraft/client/animation/Keyframe
      // 0095: dup
      // 0096: ldc 0.5
      // 0098: ldc2_w 1.100000023841858
      // 009b: ldc2_w 1.2000000476837158
      // 009e: dconst_1
      // 009f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 00a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00a8: aastore
      // 00a9: dup
      // 00aa: bipush 2
      // 00ab: new net/minecraft/client/animation/Keyframe
      // 00ae: dup
      // 00af: fconst_1
      // 00b0: ldc2_w 1.0155999660491943
      // 00b3: ldc2_w 0.8313000202178955
      // 00b6: dconst_1
      // 00b7: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 00ba: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00bd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00c0: aastore
      // 00c1: dup
      // 00c2: bipush 3
      // 00c3: new net/minecraft/client/animation/Keyframe
      // 00c6: dup
      // 00c7: ldc 1.5
      // 00c9: dconst_1
      // 00ca: dconst_1
      // 00cb: dconst_1
      // 00cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 00cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00d5: aastore
      // 00d6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 00d9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 00dc: ldc "back_tentacle1"
      // 00de: new net/minecraft/client/animation/AnimationChannel
      // 00e1: dup
      // 00e2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 00e5: bipush 3
      // 00e6: anewarray 28
      // 00e9: dup
      // 00ea: bipush 0
      // 00eb: new net/minecraft/client/animation/Keyframe
      // 00ee: dup
      // 00ef: fconst_0
      // 00f0: fconst_0
      // 00f1: fconst_0
      // 00f2: fconst_0
      // 00f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 00f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 00f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 00fc: aastore
      // 00fd: dup
      // 00fe: bipush 1
      // 00ff: new net/minecraft/client/animation/Keyframe
      // 0102: dup
      // 0103: ldc 0.75
      // 0105: ldc 30.0
      // 0107: ldc 35.0
      // 0109: fconst_0
      // 010a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 010d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0110: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0113: aastore
      // 0114: dup
      // 0115: bipush 2
      // 0116: new net/minecraft/client/animation/Keyframe
      // 0119: dup
      // 011a: ldc 1.5
      // 011c: fconst_0
      // 011d: fconst_0
      // 011e: fconst_0
      // 011f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0122: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0125: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0128: aastore
      // 0129: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 012c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 012f: ldc "face_tentacle2"
      // 0131: new net/minecraft/client/animation/AnimationChannel
      // 0134: dup
      // 0135: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0138: bipush 3
      // 0139: anewarray 28
      // 013c: dup
      // 013d: bipush 0
      // 013e: new net/minecraft/client/animation/Keyframe
      // 0141: dup
      // 0142: fconst_0
      // 0143: fconst_0
      // 0144: fconst_0
      // 0145: fconst_0
      // 0146: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0149: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 014c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 014f: aastore
      // 0150: dup
      // 0151: bipush 1
      // 0152: new net/minecraft/client/animation/Keyframe
      // 0155: dup
      // 0156: ldc 0.9583
      // 0158: ldc -32.5
      // 015a: fconst_0
      // 015b: fconst_0
      // 015c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 015f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0162: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0165: aastore
      // 0166: dup
      // 0167: bipush 2
      // 0168: new net/minecraft/client/animation/Keyframe
      // 016b: dup
      // 016c: ldc 1.5
      // 016e: fconst_0
      // 016f: fconst_0
      // 0170: fconst_0
      // 0171: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0174: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0177: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 017a: aastore
      // 017b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 017e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0181: ldc "face_tentacle1"
      // 0183: new net/minecraft/client/animation/AnimationChannel
      // 0186: dup
      // 0187: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 018a: bipush 3
      // 018b: anewarray 28
      // 018e: dup
      // 018f: bipush 0
      // 0190: new net/minecraft/client/animation/Keyframe
      // 0193: dup
      // 0194: fconst_0
      // 0195: fconst_0
      // 0196: fconst_0
      // 0197: fconst_0
      // 0198: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 019b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 019e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01a1: aastore
      // 01a2: dup
      // 01a3: bipush 1
      // 01a4: new net/minecraft/client/animation/Keyframe
      // 01a7: dup
      // 01a8: ldc 0.75
      // 01aa: ldc -32.0021
      // 01ac: ldc -19.3546
      // 01ae: ldc 11.7009
      // 01b0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01b3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01b6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01b9: aastore
      // 01ba: dup
      // 01bb: bipush 2
      // 01bc: new net/minecraft/client/animation/Keyframe
      // 01bf: dup
      // 01c0: ldc 1.5
      // 01c2: fconst_0
      // 01c3: fconst_0
      // 01c4: fconst_0
      // 01c5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01c8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01cb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01ce: aastore
      // 01cf: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 01d2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 01d5: ldc "face_tentacle3"
      // 01d7: new net/minecraft/client/animation/AnimationChannel
      // 01da: dup
      // 01db: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 01de: bipush 3
      // 01df: anewarray 28
      // 01e2: dup
      // 01e3: bipush 0
      // 01e4: new net/minecraft/client/animation/Keyframe
      // 01e7: dup
      // 01e8: fconst_0
      // 01e9: fconst_0
      // 01ea: fconst_0
      // 01eb: fconst_0
      // 01ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 01ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 01f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 01f5: aastore
      // 01f6: dup
      // 01f7: bipush 1
      // 01f8: new net/minecraft/client/animation/Keyframe
      // 01fb: dup
      // 01fc: ldc 0.5833
      // 01fe: ldc -26.3922
      // 0200: ldc 18.0578
      // 0202: ldc -8.7447
      // 0204: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0207: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 020a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 020d: aastore
      // 020e: dup
      // 020f: bipush 2
      // 0210: new net/minecraft/client/animation/Keyframe
      // 0213: dup
      // 0214: ldc 1.5
      // 0216: fconst_0
      // 0217: fconst_0
      // 0218: fconst_0
      // 0219: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 021c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 021f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0222: aastore
      // 0223: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0226: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0229: ldc "left_tentacle1"
      // 022b: new net/minecraft/client/animation/AnimationChannel
      // 022e: dup
      // 022f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0232: bipush 3
      // 0233: anewarray 28
      // 0236: dup
      // 0237: bipush 0
      // 0238: new net/minecraft/client/animation/Keyframe
      // 023b: dup
      // 023c: fconst_0
      // 023d: fconst_0
      // 023e: fconst_0
      // 023f: fconst_0
      // 0240: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0243: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0246: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0249: aastore
      // 024a: dup
      // 024b: bipush 1
      // 024c: new net/minecraft/client/animation/Keyframe
      // 024f: dup
      // 0250: ldc 0.9167
      // 0252: ldc -15.0
      // 0254: ldc 45.0
      // 0256: ldc -25.0
      // 0258: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 025b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 025e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0261: aastore
      // 0262: dup
      // 0263: bipush 2
      // 0264: new net/minecraft/client/animation/Keyframe
      // 0267: dup
      // 0268: ldc 1.5
      // 026a: fconst_0
      // 026b: fconst_0
      // 026c: fconst_0
      // 026d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0270: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0273: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0276: aastore
      // 0277: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 027a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 027d: ldc "right_tentacle1"
      // 027f: new net/minecraft/client/animation/AnimationChannel
      // 0282: dup
      // 0283: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0286: bipush 3
      // 0287: anewarray 28
      // 028a: dup
      // 028b: bipush 0
      // 028c: new net/minecraft/client/animation/Keyframe
      // 028f: dup
      // 0290: fconst_0
      // 0291: fconst_0
      // 0292: fconst_0
      // 0293: fconst_0
      // 0294: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0297: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 029a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 029d: aastore
      // 029e: dup
      // 029f: bipush 1
      // 02a0: new net/minecraft/client/animation/Keyframe
      // 02a3: dup
      // 02a4: ldc 0.75
      // 02a6: ldc -15.0
      // 02a8: ldc -45.0
      // 02aa: ldc 25.0
      // 02ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02b5: aastore
      // 02b6: dup
      // 02b7: bipush 2
      // 02b8: new net/minecraft/client/animation/Keyframe
      // 02bb: dup
      // 02bc: ldc 1.5
      // 02be: fconst_0
      // 02bf: fconst_0
      // 02c0: fconst_0
      // 02c1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02c4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02c7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02ca: aastore
      // 02cb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 02ce: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 02d1: ldc "left_tentacle2"
      // 02d3: new net/minecraft/client/animation/AnimationChannel
      // 02d6: dup
      // 02d7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 02da: bipush 3
      // 02db: anewarray 28
      // 02de: dup
      // 02df: bipush 0
      // 02e0: new net/minecraft/client/animation/Keyframe
      // 02e3: dup
      // 02e4: fconst_0
      // 02e5: fconst_0
      // 02e6: fconst_0
      // 02e7: fconst_0
      // 02e8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 02eb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 02ee: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 02f1: aastore
      // 02f2: dup
      // 02f3: bipush 1
      // 02f4: new net/minecraft/client/animation/Keyframe
      // 02f7: dup
      // 02f8: ldc 0.75
      // 02fa: fconst_0
      // 02fb: fconst_0
      // 02fc: ldc -15.0
      // 02fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0301: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0304: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0307: aastore
      // 0308: dup
      // 0309: bipush 2
      // 030a: new net/minecraft/client/animation/Keyframe
      // 030d: dup
      // 030e: ldc 1.5
      // 0310: fconst_0
      // 0311: fconst_0
      // 0312: fconst_0
      // 0313: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0316: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0319: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 031c: aastore
      // 031d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0320: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0323: ldc "right_tentacle2"
      // 0325: new net/minecraft/client/animation/AnimationChannel
      // 0328: dup
      // 0329: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 032c: bipush 3
      // 032d: anewarray 28
      // 0330: dup
      // 0331: bipush 0
      // 0332: new net/minecraft/client/animation/Keyframe
      // 0335: dup
      // 0336: fconst_0
      // 0337: fconst_0
      // 0338: fconst_0
      // 0339: fconst_0
      // 033a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 033d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0340: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0343: aastore
      // 0344: dup
      // 0345: bipush 1
      // 0346: new net/minecraft/client/animation/Keyframe
      // 0349: dup
      // 034a: ldc 0.9167
      // 034c: fconst_0
      // 034d: fconst_0
      // 034e: ldc 32.5
      // 0350: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0353: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0356: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0359: aastore
      // 035a: dup
      // 035b: bipush 2
      // 035c: new net/minecraft/client/animation/Keyframe
      // 035f: dup
      // 0360: ldc 1.5
      // 0362: fconst_0
      // 0363: fconst_0
      // 0364: fconst_0
      // 0365: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0368: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 036b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 036e: aastore
      // 036f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0372: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0375: ldc "left_tentacle3"
      // 0377: new net/minecraft/client/animation/AnimationChannel
      // 037a: dup
      // 037b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 037e: bipush 3
      // 037f: anewarray 28
      // 0382: dup
      // 0383: bipush 0
      // 0384: new net/minecraft/client/animation/Keyframe
      // 0387: dup
      // 0388: fconst_0
      // 0389: fconst_0
      // 038a: fconst_0
      // 038b: fconst_0
      // 038c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 038f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0392: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0395: aastore
      // 0396: dup
      // 0397: bipush 1
      // 0398: new net/minecraft/client/animation/Keyframe
      // 039b: dup
      // 039c: ldc 0.625
      // 039e: ldc 15.0
      // 03a0: ldc -40.0
      // 03a2: ldc -25.0
      // 03a4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03a7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03aa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03ad: aastore
      // 03ae: dup
      // 03af: bipush 2
      // 03b0: new net/minecraft/client/animation/Keyframe
      // 03b3: dup
      // 03b4: ldc 1.5
      // 03b6: fconst_0
      // 03b7: fconst_0
      // 03b8: fconst_0
      // 03b9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03bc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03bf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03c2: aastore
      // 03c3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 03c6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 03c9: ldc "right_tentacle3"
      // 03cb: new net/minecraft/client/animation/AnimationChannel
      // 03ce: dup
      // 03cf: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 03d2: bipush 3
      // 03d3: anewarray 28
      // 03d6: dup
      // 03d7: bipush 0
      // 03d8: new net/minecraft/client/animation/Keyframe
      // 03db: dup
      // 03dc: fconst_0
      // 03dd: fconst_0
      // 03de: fconst_0
      // 03df: fconst_0
      // 03e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 03e9: aastore
      // 03ea: dup
      // 03eb: bipush 1
      // 03ec: new net/minecraft/client/animation/Keyframe
      // 03ef: dup
      // 03f0: ldc 0.75
      // 03f2: ldc 15.0
      // 03f4: ldc 40.0
      // 03f6: ldc 25.0
      // 03f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 03fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 03fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0401: aastore
      // 0402: dup
      // 0403: bipush 2
      // 0404: new net/minecraft/client/animation/Keyframe
      // 0407: dup
      // 0408: ldc 1.5
      // 040a: fconst_0
      // 040b: fconst_0
      // 040c: fconst_0
      // 040d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0410: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0413: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0416: aastore
      // 0417: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 041a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 041d: ldc "back_tentacle2"
      // 041f: new net/minecraft/client/animation/AnimationChannel
      // 0422: dup
      // 0423: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0426: bipush 3
      // 0427: anewarray 28
      // 042a: dup
      // 042b: bipush 0
      // 042c: new net/minecraft/client/animation/Keyframe
      // 042f: dup
      // 0430: fconst_0
      // 0431: fconst_0
      // 0432: fconst_0
      // 0433: fconst_0
      // 0434: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0437: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 043a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 043d: aastore
      // 043e: dup
      // 043f: bipush 1
      // 0440: new net/minecraft/client/animation/Keyframe
      // 0443: dup
      // 0444: ldc 0.6667
      // 0446: ldc 15.0
      // 0448: fconst_0
      // 0449: fconst_0
      // 044a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 044d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0450: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0453: aastore
      // 0454: dup
      // 0455: bipush 2
      // 0456: new net/minecraft/client/animation/Keyframe
      // 0459: dup
      // 045a: ldc 1.5
      // 045c: fconst_0
      // 045d: fconst_0
      // 045e: fconst_0
      // 045f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0462: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0465: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0468: aastore
      // 0469: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 046c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 046f: ldc "back_tentacle3"
      // 0471: new net/minecraft/client/animation/AnimationChannel
      // 0474: dup
      // 0475: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0478: bipush 3
      // 0479: anewarray 28
      // 047c: dup
      // 047d: bipush 0
      // 047e: new net/minecraft/client/animation/Keyframe
      // 0481: dup
      // 0482: fconst_0
      // 0483: fconst_0
      // 0484: fconst_0
      // 0485: fconst_0
      // 0486: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0489: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 048c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 048f: aastore
      // 0490: dup
      // 0491: bipush 1
      // 0492: new net/minecraft/client/animation/Keyframe
      // 0495: dup
      // 0496: ldc 0.875
      // 0498: ldc 20.0
      // 049a: ldc -30.0
      // 049c: fconst_0
      // 049d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04a0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04a3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04a6: aastore
      // 04a7: dup
      // 04a8: bipush 2
      // 04a9: new net/minecraft/client/animation/Keyframe
      // 04ac: dup
      // 04ad: ldc 1.5
      // 04af: fconst_0
      // 04b0: fconst_0
      // 04b1: fconst_0
      // 04b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04bb: aastore
      // 04bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 04bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 04c2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 04c5: putstatic com/github/L_Ender/cataclysm/client/animation/Symbiocto_Animation.IDLE Lnet/minecraft/client/animation/AnimationDefinition;
      // 04c8: ldc 1.5
      // 04ca: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 04cd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.looping ()Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 04d0: ldc "everything"
      // 04d2: new net/minecraft/client/animation/AnimationChannel
      // 04d5: dup
      // 04d6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 04d9: bipush 4
      // 04da: anewarray 28
      // 04dd: dup
      // 04de: bipush 0
      // 04df: new net/minecraft/client/animation/Keyframe
      // 04e2: dup
      // 04e3: fconst_0
      // 04e4: fconst_0
      // 04e5: fconst_0
      // 04e6: fconst_0
      // 04e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 04ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 04ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 04f0: aastore
      // 04f1: dup
      // 04f2: bipush 1
      // 04f3: new net/minecraft/client/animation/Keyframe
      // 04f6: dup
      // 04f7: ldc 0.5417
      // 04f9: fconst_0
      // 04fa: ldc 7.5
      // 04fc: fconst_0
      // 04fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0500: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0503: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0506: aastore
      // 0507: dup
      // 0508: bipush 2
      // 0509: new net/minecraft/client/animation/Keyframe
      // 050c: dup
      // 050d: fconst_1
      // 050e: fconst_0
      // 050f: ldc -10.0
      // 0511: fconst_0
      // 0512: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0515: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0518: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 051b: aastore
      // 051c: dup
      // 051d: bipush 3
      // 051e: new net/minecraft/client/animation/Keyframe
      // 0521: dup
      // 0522: ldc 1.5
      // 0524: fconst_0
      // 0525: fconst_0
      // 0526: fconst_0
      // 0527: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 052a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 052d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0530: aastore
      // 0531: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0534: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0537: ldc "everything"
      // 0539: new net/minecraft/client/animation/AnimationChannel
      // 053c: dup
      // 053d: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0540: bipush 4
      // 0541: anewarray 28
      // 0544: dup
      // 0545: bipush 0
      // 0546: new net/minecraft/client/animation/Keyframe
      // 0549: dup
      // 054a: fconst_0
      // 054b: fconst_0
      // 054c: fconst_0
      // 054d: fconst_0
      // 054e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0551: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0554: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0557: aastore
      // 0558: dup
      // 0559: bipush 1
      // 055a: new net/minecraft/client/animation/Keyframe
      // 055d: dup
      // 055e: ldc 0.5
      // 0560: fconst_0
      // 0561: ldc -0.8
      // 0563: fconst_0
      // 0564: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0567: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 056a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 056d: aastore
      // 056e: dup
      // 056f: bipush 2
      // 0570: new net/minecraft/client/animation/Keyframe
      // 0573: dup
      // 0574: fconst_1
      // 0575: fconst_0
      // 0576: fconst_0
      // 0577: fconst_0
      // 0578: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 057b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 057e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0581: aastore
      // 0582: dup
      // 0583: bipush 3
      // 0584: new net/minecraft/client/animation/Keyframe
      // 0587: dup
      // 0588: ldc 1.5
      // 058a: fconst_0
      // 058b: fconst_0
      // 058c: fconst_0
      // 058d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0590: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0593: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0596: aastore
      // 0597: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 059a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 059d: ldc "octo_head"
      // 059f: new net/minecraft/client/animation/AnimationChannel
      // 05a2: dup
      // 05a3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 05a6: bipush 7
      // 05a8: anewarray 28
      // 05ab: dup
      // 05ac: bipush 0
      // 05ad: new net/minecraft/client/animation/Keyframe
      // 05b0: dup
      // 05b1: fconst_0
      // 05b2: dconst_1
      // 05b3: dconst_1
      // 05b4: dconst_1
      // 05b5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 05b8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05bb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05be: aastore
      // 05bf: dup
      // 05c0: bipush 1
      // 05c1: new net/minecraft/client/animation/Keyframe
      // 05c4: dup
      // 05c5: ldc 0.25
      // 05c7: ldc2_w 1.100000023841858
      // 05ca: ldc2_w 1.2000000476837158
      // 05cd: dconst_1
      // 05ce: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 05d1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05d4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05d7: aastore
      // 05d8: dup
      // 05d9: bipush 2
      // 05da: new net/minecraft/client/animation/Keyframe
      // 05dd: dup
      // 05de: ldc 0.5
      // 05e0: dconst_1
      // 05e1: ldc2_w 0.800000011920929
      // 05e4: dconst_1
      // 05e5: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 05e8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 05eb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 05ee: aastore
      // 05ef: dup
      // 05f0: bipush 3
      // 05f1: new net/minecraft/client/animation/Keyframe
      // 05f4: dup
      // 05f5: ldc 0.75
      // 05f7: dconst_1
      // 05f8: dconst_1
      // 05f9: dconst_1
      // 05fa: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 05fd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0600: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0603: aastore
      // 0604: dup
      // 0605: bipush 4
      // 0606: new net/minecraft/client/animation/Keyframe
      // 0609: dup
      // 060a: fconst_1
      // 060b: ldc2_w 1.100000023841858
      // 060e: ldc2_w 1.2000000476837158
      // 0611: dconst_1
      // 0612: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0615: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0618: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 061b: aastore
      // 061c: dup
      // 061d: bipush 5
      // 061e: new net/minecraft/client/animation/Keyframe
      // 0621: dup
      // 0622: ldc 1.25
      // 0624: dconst_1
      // 0625: dconst_1
      // 0626: dconst_1
      // 0627: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 062a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 062d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0630: aastore
      // 0631: dup
      // 0632: bipush 6
      // 0634: new net/minecraft/client/animation/Keyframe
      // 0637: dup
      // 0638: ldc 1.5
      // 063a: dconst_1
      // 063b: dconst_1
      // 063c: dconst_1
      // 063d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0640: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0643: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0646: aastore
      // 0647: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 064a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 064d: ldc "back_tentacle1"
      // 064f: new net/minecraft/client/animation/AnimationChannel
      // 0652: dup
      // 0653: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0656: bipush 3
      // 0657: anewarray 28
      // 065a: dup
      // 065b: bipush 0
      // 065c: new net/minecraft/client/animation/Keyframe
      // 065f: dup
      // 0660: fconst_0
      // 0661: fconst_0
      // 0662: fconst_0
      // 0663: fconst_0
      // 0664: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0667: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 066a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 066d: aastore
      // 066e: dup
      // 066f: bipush 1
      // 0670: new net/minecraft/client/animation/Keyframe
      // 0673: dup
      // 0674: ldc 0.75
      // 0676: fconst_0
      // 0677: ldc 27.5
      // 0679: fconst_0
      // 067a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 067d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0680: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0683: aastore
      // 0684: dup
      // 0685: bipush 2
      // 0686: new net/minecraft/client/animation/Keyframe
      // 0689: dup
      // 068a: ldc 1.5
      // 068c: fconst_0
      // 068d: fconst_0
      // 068e: fconst_0
      // 068f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0692: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0695: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0698: aastore
      // 0699: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 069c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 069f: ldc "face_tentacle2"
      // 06a1: new net/minecraft/client/animation/AnimationChannel
      // 06a4: dup
      // 06a5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 06a8: bipush 3
      // 06a9: anewarray 28
      // 06ac: dup
      // 06ad: bipush 0
      // 06ae: new net/minecraft/client/animation/Keyframe
      // 06b1: dup
      // 06b2: fconst_0
      // 06b3: fconst_0
      // 06b4: fconst_0
      // 06b5: fconst_0
      // 06b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06bf: aastore
      // 06c0: dup
      // 06c1: bipush 1
      // 06c2: new net/minecraft/client/animation/Keyframe
      // 06c5: dup
      // 06c6: ldc 0.75
      // 06c8: fconst_0
      // 06c9: ldc 52.5
      // 06cb: fconst_0
      // 06cc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06cf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06d2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06d5: aastore
      // 06d6: dup
      // 06d7: bipush 2
      // 06d8: new net/minecraft/client/animation/Keyframe
      // 06db: dup
      // 06dc: ldc 1.5
      // 06de: fconst_0
      // 06df: fconst_0
      // 06e0: fconst_0
      // 06e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 06e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 06e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 06ea: aastore
      // 06eb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 06ee: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 06f1: ldc "face_tentacle1"
      // 06f3: new net/minecraft/client/animation/AnimationChannel
      // 06f6: dup
      // 06f7: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 06fa: bipush 3
      // 06fb: anewarray 28
      // 06fe: dup
      // 06ff: bipush 0
      // 0700: new net/minecraft/client/animation/Keyframe
      // 0703: dup
      // 0704: fconst_0
      // 0705: fconst_0
      // 0706: fconst_0
      // 0707: fconst_0
      // 0708: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 070b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 070e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0711: aastore
      // 0712: dup
      // 0713: bipush 1
      // 0714: new net/minecraft/client/animation/Keyframe
      // 0717: dup
      // 0718: ldc 0.75
      // 071a: fconst_0
      // 071b: ldc -85.0
      // 071d: fconst_0
      // 071e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0721: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0724: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0727: aastore
      // 0728: dup
      // 0729: bipush 2
      // 072a: new net/minecraft/client/animation/Keyframe
      // 072d: dup
      // 072e: ldc 1.5
      // 0730: fconst_0
      // 0731: fconst_0
      // 0732: fconst_0
      // 0733: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0736: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0739: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 073c: aastore
      // 073d: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0740: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0743: ldc "face_tentacle3"
      // 0745: new net/minecraft/client/animation/AnimationChannel
      // 0748: dup
      // 0749: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 074c: bipush 3
      // 074d: anewarray 28
      // 0750: dup
      // 0751: bipush 0
      // 0752: new net/minecraft/client/animation/Keyframe
      // 0755: dup
      // 0756: fconst_0
      // 0757: fconst_0
      // 0758: fconst_0
      // 0759: fconst_0
      // 075a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 075d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0760: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0763: aastore
      // 0764: dup
      // 0765: bipush 1
      // 0766: new net/minecraft/client/animation/Keyframe
      // 0769: dup
      // 076a: ldc 0.75
      // 076c: fconst_0
      // 076d: ldc 85.0
      // 076f: fconst_0
      // 0770: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0773: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0776: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0779: aastore
      // 077a: dup
      // 077b: bipush 2
      // 077c: new net/minecraft/client/animation/Keyframe
      // 077f: dup
      // 0780: ldc 1.5
      // 0782: fconst_0
      // 0783: fconst_0
      // 0784: fconst_0
      // 0785: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0788: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 078b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 078e: aastore
      // 078f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0792: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0795: ldc "left_tentacle1"
      // 0797: new net/minecraft/client/animation/AnimationChannel
      // 079a: dup
      // 079b: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 079e: bipush 3
      // 079f: anewarray 28
      // 07a2: dup
      // 07a3: bipush 0
      // 07a4: new net/minecraft/client/animation/Keyframe
      // 07a7: dup
      // 07a8: fconst_0
      // 07a9: fconst_0
      // 07aa: fconst_0
      // 07ab: fconst_0
      // 07ac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07af: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07b2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07b5: aastore
      // 07b6: dup
      // 07b7: bipush 1
      // 07b8: new net/minecraft/client/animation/Keyframe
      // 07bb: dup
      // 07bc: ldc 0.75
      // 07be: fconst_0
      // 07bf: ldc -30.0
      // 07c1: fconst_0
      // 07c2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07c5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07c8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07cb: aastore
      // 07cc: dup
      // 07cd: bipush 2
      // 07ce: new net/minecraft/client/animation/Keyframe
      // 07d1: dup
      // 07d2: ldc 1.5
      // 07d4: fconst_0
      // 07d5: fconst_0
      // 07d6: fconst_0
      // 07d7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 07da: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 07dd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 07e0: aastore
      // 07e1: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 07e4: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 07e7: ldc "right_tentacle1"
      // 07e9: new net/minecraft/client/animation/AnimationChannel
      // 07ec: dup
      // 07ed: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 07f0: bipush 3
      // 07f1: anewarray 28
      // 07f4: dup
      // 07f5: bipush 0
      // 07f6: new net/minecraft/client/animation/Keyframe
      // 07f9: dup
      // 07fa: fconst_0
      // 07fb: fconst_0
      // 07fc: fconst_0
      // 07fd: fconst_0
      // 07fe: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0801: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0804: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0807: aastore
      // 0808: dup
      // 0809: bipush 1
      // 080a: new net/minecraft/client/animation/Keyframe
      // 080d: dup
      // 080e: ldc 0.75
      // 0810: fconst_0
      // 0811: ldc 30.0
      // 0813: fconst_0
      // 0814: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0817: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 081a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 081d: aastore
      // 081e: dup
      // 081f: bipush 2
      // 0820: new net/minecraft/client/animation/Keyframe
      // 0823: dup
      // 0824: ldc 1.5
      // 0826: fconst_0
      // 0827: fconst_0
      // 0828: fconst_0
      // 0829: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 082c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 082f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0832: aastore
      // 0833: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0836: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0839: ldc "left_tentacle2"
      // 083b: new net/minecraft/client/animation/AnimationChannel
      // 083e: dup
      // 083f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0842: bipush 3
      // 0843: anewarray 28
      // 0846: dup
      // 0847: bipush 0
      // 0848: new net/minecraft/client/animation/Keyframe
      // 084b: dup
      // 084c: fconst_0
      // 084d: fconst_0
      // 084e: fconst_0
      // 084f: fconst_0
      // 0850: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0853: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0856: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0859: aastore
      // 085a: dup
      // 085b: bipush 1
      // 085c: new net/minecraft/client/animation/Keyframe
      // 085f: dup
      // 0860: ldc 0.8333
      // 0862: fconst_0
      // 0863: ldc -45.0
      // 0865: fconst_0
      // 0866: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0869: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 086c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 086f: aastore
      // 0870: dup
      // 0871: bipush 2
      // 0872: new net/minecraft/client/animation/Keyframe
      // 0875: dup
      // 0876: ldc 1.5
      // 0878: fconst_0
      // 0879: fconst_0
      // 087a: fconst_0
      // 087b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 087e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0881: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0884: aastore
      // 0885: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0888: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 088b: ldc "right_tentacle2"
      // 088d: new net/minecraft/client/animation/AnimationChannel
      // 0890: dup
      // 0891: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0894: bipush 3
      // 0895: anewarray 28
      // 0898: dup
      // 0899: bipush 0
      // 089a: new net/minecraft/client/animation/Keyframe
      // 089d: dup
      // 089e: fconst_0
      // 089f: fconst_0
      // 08a0: fconst_0
      // 08a1: fconst_0
      // 08a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08ab: aastore
      // 08ac: dup
      // 08ad: bipush 1
      // 08ae: new net/minecraft/client/animation/Keyframe
      // 08b1: dup
      // 08b2: ldc 0.8333
      // 08b4: fconst_0
      // 08b5: ldc 45.0
      // 08b7: fconst_0
      // 08b8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08bb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08be: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08c1: aastore
      // 08c2: dup
      // 08c3: bipush 2
      // 08c4: new net/minecraft/client/animation/Keyframe
      // 08c7: dup
      // 08c8: ldc 1.5
      // 08ca: fconst_0
      // 08cb: fconst_0
      // 08cc: fconst_0
      // 08cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08d6: aastore
      // 08d7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 08da: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 08dd: ldc "left_tentacle3"
      // 08df: new net/minecraft/client/animation/AnimationChannel
      // 08e2: dup
      // 08e3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 08e6: bipush 3
      // 08e7: anewarray 28
      // 08ea: dup
      // 08eb: bipush 0
      // 08ec: new net/minecraft/client/animation/Keyframe
      // 08ef: dup
      // 08f0: fconst_0
      // 08f1: fconst_0
      // 08f2: fconst_0
      // 08f3: fconst_0
      // 08f4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 08f7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 08fa: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 08fd: aastore
      // 08fe: dup
      // 08ff: bipush 1
      // 0900: new net/minecraft/client/animation/Keyframe
      // 0903: dup
      // 0904: ldc 0.625
      // 0906: fconst_0
      // 0907: ldc -57.5
      // 0909: fconst_0
      // 090a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 090d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0910: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0913: aastore
      // 0914: dup
      // 0915: bipush 2
      // 0916: new net/minecraft/client/animation/Keyframe
      // 0919: dup
      // 091a: ldc 1.5
      // 091c: fconst_0
      // 091d: fconst_0
      // 091e: fconst_0
      // 091f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0922: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0925: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0928: aastore
      // 0929: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 092c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 092f: ldc "right_tentacle3"
      // 0931: new net/minecraft/client/animation/AnimationChannel
      // 0934: dup
      // 0935: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0938: bipush 3
      // 0939: anewarray 28
      // 093c: dup
      // 093d: bipush 0
      // 093e: new net/minecraft/client/animation/Keyframe
      // 0941: dup
      // 0942: fconst_0
      // 0943: fconst_0
      // 0944: fconst_0
      // 0945: fconst_0
      // 0946: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0949: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 094c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 094f: aastore
      // 0950: dup
      // 0951: bipush 1
      // 0952: new net/minecraft/client/animation/Keyframe
      // 0955: dup
      // 0956: ldc 0.625
      // 0958: fconst_0
      // 0959: ldc 57.5
      // 095b: fconst_0
      // 095c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 095f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0962: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0965: aastore
      // 0966: dup
      // 0967: bipush 2
      // 0968: new net/minecraft/client/animation/Keyframe
      // 096b: dup
      // 096c: ldc 1.5
      // 096e: fconst_0
      // 096f: fconst_0
      // 0970: fconst_0
      // 0971: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 097a: aastore
      // 097b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 097e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0981: ldc "back_tentacle2"
      // 0983: new net/minecraft/client/animation/AnimationChannel
      // 0986: dup
      // 0987: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 098a: bipush 3
      // 098b: anewarray 28
      // 098e: dup
      // 098f: bipush 0
      // 0990: new net/minecraft/client/animation/Keyframe
      // 0993: dup
      // 0994: fconst_0
      // 0995: fconst_0
      // 0996: fconst_0
      // 0997: fconst_0
      // 0998: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 099b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 099e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09a1: aastore
      // 09a2: dup
      // 09a3: bipush 1
      // 09a4: new net/minecraft/client/animation/Keyframe
      // 09a7: dup
      // 09a8: ldc 0.8333
      // 09aa: fconst_0
      // 09ab: ldc 20.0
      // 09ad: fconst_0
      // 09ae: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09b1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09b4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09b7: aastore
      // 09b8: dup
      // 09b9: bipush 2
      // 09ba: new net/minecraft/client/animation/Keyframe
      // 09bd: dup
      // 09be: ldc 1.5
      // 09c0: fconst_0
      // 09c1: fconst_0
      // 09c2: fconst_0
      // 09c3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09c6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09c9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09cc: aastore
      // 09cd: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 09d0: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 09d3: ldc "back_tentacle3"
      // 09d5: new net/minecraft/client/animation/AnimationChannel
      // 09d8: dup
      // 09d9: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 09dc: bipush 3
      // 09dd: anewarray 28
      // 09e0: dup
      // 09e1: bipush 0
      // 09e2: new net/minecraft/client/animation/Keyframe
      // 09e5: dup
      // 09e6: fconst_0
      // 09e7: fconst_0
      // 09e8: fconst_0
      // 09e9: fconst_0
      // 09ea: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 09ed: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 09f0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 09f3: aastore
      // 09f4: dup
      // 09f5: bipush 1
      // 09f6: new net/minecraft/client/animation/Keyframe
      // 09f9: dup
      // 09fa: ldc 0.75
      // 09fc: fconst_0
      // 09fd: ldc -17.5
      // 09ff: fconst_0
      // 0a00: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a03: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a06: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a09: aastore
      // 0a0a: dup
      // 0a0b: bipush 2
      // 0a0c: new net/minecraft/client/animation/Keyframe
      // 0a0f: dup
      // 0a10: ldc 1.5
      // 0a12: fconst_0
      // 0a13: fconst_0
      // 0a14: fconst_0
      // 0a15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0a18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a1e: aastore
      // 0a1f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0a22: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a25: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 0a28: putstatic com/github/L_Ender/cataclysm/client/animation/Symbiocto_Animation.WALK Lnet/minecraft/client/animation/AnimationDefinition;
      // 0a2b: ldc 1.6667
      // 0a2d: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0a30: ldc "everything"
      // 0a32: new net/minecraft/client/animation/AnimationChannel
      // 0a35: dup
      // 0a36: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0a39: bipush 5
      // 0a3a: anewarray 28
      // 0a3d: dup
      // 0a3e: bipush 0
      // 0a3f: new net/minecraft/client/animation/Keyframe
      // 0a42: dup
      // 0a43: fconst_0
      // 0a44: fconst_0
      // 0a45: fconst_0
      // 0a46: fconst_0
      // 0a47: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a4a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a4d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a50: aastore
      // 0a51: dup
      // 0a52: bipush 1
      // 0a53: new net/minecraft/client/animation/Keyframe
      // 0a56: dup
      // 0a57: ldc 0.8333
      // 0a59: fconst_0
      // 0a5a: fconst_0
      // 0a5b: fconst_0
      // 0a5c: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a5f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a62: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a65: aastore
      // 0a66: dup
      // 0a67: bipush 2
      // 0a68: new net/minecraft/client/animation/Keyframe
      // 0a6b: dup
      // 0a6c: ldc 1.25
      // 0a6e: fconst_0
      // 0a6f: fconst_0
      // 0a70: fconst_0
      // 0a71: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a7a: aastore
      // 0a7b: dup
      // 0a7c: bipush 3
      // 0a7d: new net/minecraft/client/animation/Keyframe
      // 0a80: dup
      // 0a81: ldc 1.4583
      // 0a83: fconst_0
      // 0a84: fconst_0
      // 0a85: fconst_0
      // 0a86: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0a8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0a8f: aastore
      // 0a90: dup
      // 0a91: bipush 4
      // 0a92: new net/minecraft/client/animation/Keyframe
      // 0a95: dup
      // 0a96: ldc 1.6667
      // 0a98: fconst_0
      // 0a99: fconst_0
      // 0a9a: fconst_0
      // 0a9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0a9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0aa1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0aa4: aastore
      // 0aa5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0aa8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0aab: ldc "octo_head"
      // 0aad: new net/minecraft/client/animation/AnimationChannel
      // 0ab0: dup
      // 0ab1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ab4: bipush 7
      // 0ab6: anewarray 28
      // 0ab9: dup
      // 0aba: bipush 0
      // 0abb: new net/minecraft/client/animation/Keyframe
      // 0abe: dup
      // 0abf: fconst_0
      // 0ac0: fconst_0
      // 0ac1: fconst_0
      // 0ac2: fconst_0
      // 0ac3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ac6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ac9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0acc: aastore
      // 0acd: dup
      // 0ace: bipush 1
      // 0acf: new net/minecraft/client/animation/Keyframe
      // 0ad2: dup
      // 0ad3: ldc 0.5
      // 0ad5: ldc -37.5
      // 0ad7: fconst_0
      // 0ad8: fconst_0
      // 0ad9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0adc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0adf: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ae2: aastore
      // 0ae3: dup
      // 0ae4: bipush 2
      // 0ae5: new net/minecraft/client/animation/Keyframe
      // 0ae8: dup
      // 0ae9: ldc 0.7083
      // 0aeb: ldc -25.0
      // 0aed: fconst_0
      // 0aee: fconst_0
      // 0aef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0af2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0af5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0af8: aastore
      // 0af9: dup
      // 0afa: bipush 3
      // 0afb: new net/minecraft/client/animation/Keyframe
      // 0afe: dup
      // 0aff: ldc 0.875
      // 0b01: ldc -57.5
      // 0b03: fconst_0
      // 0b04: fconst_0
      // 0b05: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b08: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b0b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b0e: aastore
      // 0b0f: dup
      // 0b10: bipush 4
      // 0b11: new net/minecraft/client/animation/Keyframe
      // 0b14: dup
      // 0b15: ldc 1.125
      // 0b17: ldc -57.5
      // 0b19: fconst_0
      // 0b1a: fconst_0
      // 0b1b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b1e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b21: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b24: aastore
      // 0b25: dup
      // 0b26: bipush 5
      // 0b27: new net/minecraft/client/animation/Keyframe
      // 0b2a: dup
      // 0b2b: ldc 1.4167
      // 0b2d: fconst_0
      // 0b2e: fconst_0
      // 0b2f: fconst_0
      // 0b30: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b33: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b36: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b39: aastore
      // 0b3a: dup
      // 0b3b: bipush 6
      // 0b3d: new net/minecraft/client/animation/Keyframe
      // 0b40: dup
      // 0b41: ldc 1.6667
      // 0b43: fconst_0
      // 0b44: fconst_0
      // 0b45: fconst_0
      // 0b46: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0b49: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b4c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b4f: aastore
      // 0b50: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0b53: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0b56: ldc "octo_head"
      // 0b58: new net/minecraft/client/animation/AnimationChannel
      // 0b5b: dup
      // 0b5c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0b5f: bipush 7
      // 0b61: anewarray 28
      // 0b64: dup
      // 0b65: bipush 0
      // 0b66: new net/minecraft/client/animation/Keyframe
      // 0b69: dup
      // 0b6a: fconst_0
      // 0b6b: fconst_0
      // 0b6c: fconst_0
      // 0b6d: fconst_0
      // 0b6e: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b71: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b74: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b77: aastore
      // 0b78: dup
      // 0b79: bipush 1
      // 0b7a: new net/minecraft/client/animation/Keyframe
      // 0b7d: dup
      // 0b7e: ldc 0.5
      // 0b80: fconst_0
      // 0b81: ldc 6.0
      // 0b83: fconst_0
      // 0b84: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b87: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0b8a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0b8d: aastore
      // 0b8e: dup
      // 0b8f: bipush 2
      // 0b90: new net/minecraft/client/animation/Keyframe
      // 0b93: dup
      // 0b94: ldc 0.75
      // 0b96: fconst_0
      // 0b97: ldc 6.0
      // 0b99: fconst_0
      // 0b9a: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0b9d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ba0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ba3: aastore
      // 0ba4: dup
      // 0ba5: bipush 3
      // 0ba6: new net/minecraft/client/animation/Keyframe
      // 0ba9: dup
      // 0baa: ldc 0.875
      // 0bac: fconst_0
      // 0bad: ldc 9.0
      // 0baf: fconst_0
      // 0bb0: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bb3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bb6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bb9: aastore
      // 0bba: dup
      // 0bbb: bipush 4
      // 0bbc: new net/minecraft/client/animation/Keyframe
      // 0bbf: dup
      // 0bc0: ldc 1.125
      // 0bc2: fconst_0
      // 0bc3: ldc 9.0
      // 0bc5: fconst_0
      // 0bc6: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bc9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bcc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bcf: aastore
      // 0bd0: dup
      // 0bd1: bipush 5
      // 0bd2: new net/minecraft/client/animation/Keyframe
      // 0bd5: dup
      // 0bd6: ldc 1.4167
      // 0bd8: fconst_0
      // 0bd9: fconst_0
      // 0bda: fconst_0
      // 0bdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0be1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0be4: aastore
      // 0be5: dup
      // 0be6: bipush 6
      // 0be8: new net/minecraft/client/animation/Keyframe
      // 0beb: dup
      // 0bec: ldc 1.6667
      // 0bee: fconst_0
      // 0bef: fconst_0
      // 0bf0: fconst_0
      // 0bf1: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 0bf4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0bf7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0bfa: aastore
      // 0bfb: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0bfe: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c01: ldc "octo_head"
      // 0c03: new net/minecraft/client/animation/AnimationChannel
      // 0c06: dup
      // 0c07: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0c0a: bipush 6
      // 0c0c: anewarray 28
      // 0c0f: dup
      // 0c10: bipush 0
      // 0c11: new net/minecraft/client/animation/Keyframe
      // 0c14: dup
      // 0c15: fconst_0
      // 0c16: dconst_1
      // 0c17: dconst_1
      // 0c18: dconst_1
      // 0c19: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c1c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c1f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c22: aastore
      // 0c23: dup
      // 0c24: bipush 1
      // 0c25: new net/minecraft/client/animation/Keyframe
      // 0c28: dup
      // 0c29: ldc 0.875
      // 0c2b: dconst_1
      // 0c2c: dconst_1
      // 0c2d: dconst_1
      // 0c2e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c31: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c34: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c37: aastore
      // 0c38: dup
      // 0c39: bipush 2
      // 0c3a: new net/minecraft/client/animation/Keyframe
      // 0c3d: dup
      // 0c3e: fconst_1
      // 0c3f: ldc2_w 1.2000000476837158
      // 0c42: ldc2_w 1.2000000476837158
      // 0c45: ldc2_w 1.2000000476837158
      // 0c48: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c4b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c4e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c51: aastore
      // 0c52: dup
      // 0c53: bipush 3
      // 0c54: new net/minecraft/client/animation/Keyframe
      // 0c57: dup
      // 0c58: ldc 1.1667
      // 0c5a: dconst_1
      // 0c5b: dconst_1
      // 0c5c: dconst_1
      // 0c5d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c60: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c63: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c66: aastore
      // 0c67: dup
      // 0c68: bipush 4
      // 0c69: new net/minecraft/client/animation/Keyframe
      // 0c6c: dup
      // 0c6d: ldc 1.4583
      // 0c6f: ldc2_w 1.2000000476837158
      // 0c72: ldc2_w 0.800000011920929
      // 0c75: ldc2_w 1.2000000476837158
      // 0c78: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c7b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c7e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c81: aastore
      // 0c82: dup
      // 0c83: bipush 5
      // 0c84: new net/minecraft/client/animation/Keyframe
      // 0c87: dup
      // 0c88: ldc 1.6667
      // 0c8a: dconst_1
      // 0c8b: dconst_1
      // 0c8c: dconst_1
      // 0c8d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 0c90: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0c93: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0c96: aastore
      // 0c97: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0c9a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0c9d: ldc "back_tentacle1"
      // 0c9f: new net/minecraft/client/animation/AnimationChannel
      // 0ca2: dup
      // 0ca3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ca6: bipush 7
      // 0ca8: anewarray 28
      // 0cab: dup
      // 0cac: bipush 0
      // 0cad: new net/minecraft/client/animation/Keyframe
      // 0cb0: dup
      // 0cb1: fconst_0
      // 0cb2: fconst_0
      // 0cb3: fconst_0
      // 0cb4: fconst_0
      // 0cb5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cb8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cbb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cbe: aastore
      // 0cbf: dup
      // 0cc0: bipush 1
      // 0cc1: new net/minecraft/client/animation/Keyframe
      // 0cc4: dup
      // 0cc5: ldc 0.5
      // 0cc7: ldc 5.4161
      // 0cc9: ldc 16.6658
      // 0ccb: ldc 18.2939
      // 0ccd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0cd0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0cd3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cd6: aastore
      // 0cd7: dup
      // 0cd8: bipush 2
      // 0cd9: new net/minecraft/client/animation/Keyframe
      // 0cdc: dup
      // 0cdd: ldc 0.7083
      // 0cdf: ldc -12.0839
      // 0ce1: ldc 16.6658
      // 0ce3: ldc 18.2939
      // 0ce5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ce8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ceb: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0cee: aastore
      // 0cef: dup
      // 0cf0: bipush 3
      // 0cf1: new net/minecraft/client/animation/Keyframe
      // 0cf4: dup
      // 0cf5: ldc 0.875
      // 0cf7: ldc -29.5839
      // 0cf9: ldc 16.6658
      // 0cfb: ldc 18.2939
      // 0cfd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d00: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d03: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d06: aastore
      // 0d07: dup
      // 0d08: bipush 4
      // 0d09: new net/minecraft/client/animation/Keyframe
      // 0d0c: dup
      // 0d0d: ldc 1.1667
      // 0d0f: ldc 23.125
      // 0d11: ldc 7.5326
      // 0d13: ldc 8.2685
      // 0d15: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d18: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d1b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d1e: aastore
      // 0d1f: dup
      // 0d20: bipush 5
      // 0d21: new net/minecraft/client/animation/Keyframe
      // 0d24: dup
      // 0d25: ldc 1.4167
      // 0d27: ldc 30.0
      // 0d29: ldc 22.5
      // 0d2b: fconst_0
      // 0d2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d35: aastore
      // 0d36: dup
      // 0d37: bipush 6
      // 0d39: new net/minecraft/client/animation/Keyframe
      // 0d3c: dup
      // 0d3d: ldc 1.6667
      // 0d3f: fconst_0
      // 0d40: fconst_0
      // 0d41: fconst_0
      // 0d42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d4b: aastore
      // 0d4c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0d4f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0d52: ldc "face_tentacle2"
      // 0d54: new net/minecraft/client/animation/AnimationChannel
      // 0d57: dup
      // 0d58: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0d5b: bipush 7
      // 0d5d: anewarray 28
      // 0d60: dup
      // 0d61: bipush 0
      // 0d62: new net/minecraft/client/animation/Keyframe
      // 0d65: dup
      // 0d66: fconst_0
      // 0d67: fconst_0
      // 0d68: fconst_0
      // 0d69: fconst_0
      // 0d6a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d6d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d70: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d73: aastore
      // 0d74: dup
      // 0d75: bipush 1
      // 0d76: new net/minecraft/client/animation/Keyframe
      // 0d79: dup
      // 0d7a: ldc 0.5
      // 0d7c: ldc 107.5
      // 0d7e: fconst_0
      // 0d7f: fconst_0
      // 0d80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d89: aastore
      // 0d8a: dup
      // 0d8b: bipush 2
      // 0d8c: new net/minecraft/client/animation/Keyframe
      // 0d8f: dup
      // 0d90: ldc 0.7917
      // 0d92: ldc 125.0
      // 0d94: fconst_0
      // 0d95: fconst_0
      // 0d96: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0d99: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0d9c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0d9f: aastore
      // 0da0: dup
      // 0da1: bipush 3
      // 0da2: new net/minecraft/client/animation/Keyframe
      // 0da5: dup
      // 0da6: ldc 0.9583
      // 0da8: ldc 12.5
      // 0daa: fconst_0
      // 0dab: fconst_0
      // 0dac: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0daf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0db2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0db5: aastore
      // 0db6: dup
      // 0db7: bipush 4
      // 0db8: new net/minecraft/client/animation/Keyframe
      // 0dbb: dup
      // 0dbc: ldc 1.25
      // 0dbe: ldc 12.5
      // 0dc0: fconst_0
      // 0dc1: fconst_0
      // 0dc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0dc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0dcb: aastore
      // 0dcc: dup
      // 0dcd: bipush 5
      // 0dce: new net/minecraft/client/animation/Keyframe
      // 0dd1: dup
      // 0dd2: ldc 1.4167
      // 0dd4: ldc -30.0
      // 0dd6: fconst_0
      // 0dd7: fconst_0
      // 0dd8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ddb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0dde: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0de1: aastore
      // 0de2: dup
      // 0de3: bipush 6
      // 0de5: new net/minecraft/client/animation/Keyframe
      // 0de8: dup
      // 0de9: ldc 1.6667
      // 0deb: fconst_0
      // 0dec: fconst_0
      // 0ded: fconst_0
      // 0dee: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0df1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0df4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0df7: aastore
      // 0df8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0dfb: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0dfe: ldc "face_tentacle1"
      // 0e00: new net/minecraft/client/animation/AnimationChannel
      // 0e03: dup
      // 0e04: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0e07: bipush 7
      // 0e09: anewarray 28
      // 0e0c: dup
      // 0e0d: bipush 0
      // 0e0e: new net/minecraft/client/animation/Keyframe
      // 0e11: dup
      // 0e12: fconst_0
      // 0e13: fconst_0
      // 0e14: fconst_0
      // 0e15: fconst_0
      // 0e16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e1f: aastore
      // 0e20: dup
      // 0e21: bipush 1
      // 0e22: new net/minecraft/client/animation/Keyframe
      // 0e25: dup
      // 0e26: ldc 0.5
      // 0e28: ldc 110.0
      // 0e2a: fconst_0
      // 0e2b: fconst_0
      // 0e2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e35: aastore
      // 0e36: dup
      // 0e37: bipush 2
      // 0e38: new net/minecraft/client/animation/Keyframe
      // 0e3b: dup
      // 0e3c: ldc 0.7917
      // 0e3e: ldc 127.5
      // 0e40: fconst_0
      // 0e41: fconst_0
      // 0e42: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e45: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e48: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e4b: aastore
      // 0e4c: dup
      // 0e4d: bipush 3
      // 0e4e: new net/minecraft/client/animation/Keyframe
      // 0e51: dup
      // 0e52: ldc 1.0417
      // 0e54: ldc -1.7254
      // 0e56: ldc -19.5064
      // 0e58: ldc -4.5043
      // 0e5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e63: aastore
      // 0e64: dup
      // 0e65: bipush 4
      // 0e66: new net/minecraft/client/animation/Keyframe
      // 0e69: dup
      // 0e6a: ldc 1.25
      // 0e6c: ldc 13.2746
      // 0e6e: ldc -19.5064
      // 0e70: ldc -4.5043
      // 0e72: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e7b: aastore
      // 0e7c: dup
      // 0e7d: bipush 5
      // 0e7e: new net/minecraft/client/animation/Keyframe
      // 0e81: dup
      // 0e82: ldc 1.4167
      // 0e84: ldc -30.0
      // 0e86: fconst_0
      // 0e87: fconst_0
      // 0e88: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0e8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0e8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0e91: aastore
      // 0e92: dup
      // 0e93: bipush 6
      // 0e95: new net/minecraft/client/animation/Keyframe
      // 0e98: dup
      // 0e99: ldc 1.6667
      // 0e9b: fconst_0
      // 0e9c: fconst_0
      // 0e9d: fconst_0
      // 0e9e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ea1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ea4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ea7: aastore
      // 0ea8: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0eab: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0eae: ldc "face_tentacle3"
      // 0eb0: new net/minecraft/client/animation/AnimationChannel
      // 0eb3: dup
      // 0eb4: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0eb7: bipush 7
      // 0eb9: anewarray 28
      // 0ebc: dup
      // 0ebd: bipush 0
      // 0ebe: new net/minecraft/client/animation/Keyframe
      // 0ec1: dup
      // 0ec2: fconst_0
      // 0ec3: fconst_0
      // 0ec4: fconst_0
      // 0ec5: fconst_0
      // 0ec6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ec9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ecc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ecf: aastore
      // 0ed0: dup
      // 0ed1: bipush 1
      // 0ed2: new net/minecraft/client/animation/Keyframe
      // 0ed5: dup
      // 0ed6: ldc 0.5
      // 0ed8: ldc 102.5
      // 0eda: fconst_0
      // 0edb: fconst_0
      // 0edc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0edf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ee2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0ee5: aastore
      // 0ee6: dup
      // 0ee7: bipush 2
      // 0ee8: new net/minecraft/client/animation/Keyframe
      // 0eeb: dup
      // 0eec: ldc 0.7917
      // 0eee: ldc 120.0
      // 0ef0: fconst_0
      // 0ef1: fconst_0
      // 0ef2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0ef5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0ef8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0efb: aastore
      // 0efc: dup
      // 0efd: bipush 3
      // 0efe: new net/minecraft/client/animation/Keyframe
      // 0f01: dup
      // 0f02: ldc 1.0417
      // 0f04: ldc -1.7254
      // 0f06: ldc 19.5064
      // 0f08: ldc 4.5043
      // 0f0a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f0d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f10: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f13: aastore
      // 0f14: dup
      // 0f15: bipush 4
      // 0f16: new net/minecraft/client/animation/Keyframe
      // 0f19: dup
      // 0f1a: ldc 1.25
      // 0f1c: ldc 13.2746
      // 0f1e: ldc 19.5064
      // 0f20: ldc 4.5043
      // 0f22: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f25: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f28: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f2b: aastore
      // 0f2c: dup
      // 0f2d: bipush 5
      // 0f2e: new net/minecraft/client/animation/Keyframe
      // 0f31: dup
      // 0f32: ldc 1.4167
      // 0f34: ldc -30.0
      // 0f36: fconst_0
      // 0f37: fconst_0
      // 0f38: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f3b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f3e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f41: aastore
      // 0f42: dup
      // 0f43: bipush 6
      // 0f45: new net/minecraft/client/animation/Keyframe
      // 0f48: dup
      // 0f49: ldc 1.6667
      // 0f4b: fconst_0
      // 0f4c: fconst_0
      // 0f4d: fconst_0
      // 0f4e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f51: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f54: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f57: aastore
      // 0f58: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0f5b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0f5e: ldc "teeth2"
      // 0f60: new net/minecraft/client/animation/AnimationChannel
      // 0f63: dup
      // 0f64: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0f67: bipush 6
      // 0f69: anewarray 28
      // 0f6c: dup
      // 0f6d: bipush 0
      // 0f6e: new net/minecraft/client/animation/Keyframe
      // 0f71: dup
      // 0f72: fconst_0
      // 0f73: fconst_0
      // 0f74: fconst_0
      // 0f75: fconst_0
      // 0f76: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f79: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f7c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f7f: aastore
      // 0f80: dup
      // 0f81: bipush 1
      // 0f82: new net/minecraft/client/animation/Keyframe
      // 0f85: dup
      // 0f86: ldc 0.8333
      // 0f88: fconst_0
      // 0f89: fconst_0
      // 0f8a: fconst_0
      // 0f8b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0f8e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0f91: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0f94: aastore
      // 0f95: dup
      // 0f96: bipush 2
      // 0f97: new net/minecraft/client/animation/Keyframe
      // 0f9a: dup
      // 0f9b: fconst_1
      // 0f9c: fconst_0
      // 0f9d: fconst_0
      // 0f9e: ldc 77.5
      // 0fa0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fa3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fa6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fa9: aastore
      // 0faa: dup
      // 0fab: bipush 3
      // 0fac: new net/minecraft/client/animation/Keyframe
      // 0faf: dup
      // 0fb0: ldc 1.25
      // 0fb2: fconst_0
      // 0fb3: fconst_0
      // 0fb4: ldc 77.5
      // 0fb6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fb9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fbc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fbf: aastore
      // 0fc0: dup
      // 0fc1: bipush 4
      // 0fc2: new net/minecraft/client/animation/Keyframe
      // 0fc5: dup
      // 0fc6: ldc 1.4167
      // 0fc8: fconst_0
      // 0fc9: fconst_0
      // 0fca: fconst_0
      // 0fcb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fce: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fd1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fd4: aastore
      // 0fd5: dup
      // 0fd6: bipush 5
      // 0fd7: new net/minecraft/client/animation/Keyframe
      // 0fda: dup
      // 0fdb: ldc 1.6667
      // 0fdd: fconst_0
      // 0fde: fconst_0
      // 0fdf: fconst_0
      // 0fe0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 0fe3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 0fe6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 0fe9: aastore
      // 0fea: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 0fed: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 0ff0: ldc "teeth"
      // 0ff2: new net/minecraft/client/animation/AnimationChannel
      // 0ff5: dup
      // 0ff6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 0ff9: bipush 6
      // 0ffb: anewarray 28
      // 0ffe: dup
      // 0fff: bipush 0
      // 1000: new net/minecraft/client/animation/Keyframe
      // 1003: dup
      // 1004: fconst_0
      // 1005: fconst_0
      // 1006: fconst_0
      // 1007: fconst_0
      // 1008: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 100b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 100e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1011: aastore
      // 1012: dup
      // 1013: bipush 1
      // 1014: new net/minecraft/client/animation/Keyframe
      // 1017: dup
      // 1018: ldc 0.8333
      // 101a: fconst_0
      // 101b: fconst_0
      // 101c: fconst_0
      // 101d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1020: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1023: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1026: aastore
      // 1027: dup
      // 1028: bipush 2
      // 1029: new net/minecraft/client/animation/Keyframe
      // 102c: dup
      // 102d: fconst_1
      // 102e: fconst_0
      // 102f: fconst_0
      // 1030: ldc -77.5
      // 1032: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1035: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1038: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 103b: aastore
      // 103c: dup
      // 103d: bipush 3
      // 103e: new net/minecraft/client/animation/Keyframe
      // 1041: dup
      // 1042: ldc 1.25
      // 1044: fconst_0
      // 1045: fconst_0
      // 1046: ldc -77.5
      // 1048: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 104b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 104e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1051: aastore
      // 1052: dup
      // 1053: bipush 4
      // 1054: new net/minecraft/client/animation/Keyframe
      // 1057: dup
      // 1058: ldc 1.4167
      // 105a: fconst_0
      // 105b: fconst_0
      // 105c: fconst_0
      // 105d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1060: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1063: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1066: aastore
      // 1067: dup
      // 1068: bipush 5
      // 1069: new net/minecraft/client/animation/Keyframe
      // 106c: dup
      // 106d: ldc 1.6667
      // 106f: fconst_0
      // 1070: fconst_0
      // 1071: fconst_0
      // 1072: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1075: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1078: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 107b: aastore
      // 107c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 107f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1082: ldc "teeth3"
      // 1084: new net/minecraft/client/animation/AnimationChannel
      // 1087: dup
      // 1088: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 108b: bipush 6
      // 108d: anewarray 28
      // 1090: dup
      // 1091: bipush 0
      // 1092: new net/minecraft/client/animation/Keyframe
      // 1095: dup
      // 1096: fconst_0
      // 1097: fconst_0
      // 1098: fconst_0
      // 1099: fconst_0
      // 109a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 109d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10a0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10a3: aastore
      // 10a4: dup
      // 10a5: bipush 1
      // 10a6: new net/minecraft/client/animation/Keyframe
      // 10a9: dup
      // 10aa: ldc 0.8333
      // 10ac: fconst_0
      // 10ad: fconst_0
      // 10ae: fconst_0
      // 10af: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10b2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10b5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10b8: aastore
      // 10b9: dup
      // 10ba: bipush 2
      // 10bb: new net/minecraft/client/animation/Keyframe
      // 10be: dup
      // 10bf: fconst_1
      // 10c0: ldc -77.5
      // 10c2: fconst_0
      // 10c3: fconst_0
      // 10c4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10c7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10ca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10cd: aastore
      // 10ce: dup
      // 10cf: bipush 3
      // 10d0: new net/minecraft/client/animation/Keyframe
      // 10d3: dup
      // 10d4: ldc 1.25
      // 10d6: ldc -77.5
      // 10d8: fconst_0
      // 10d9: fconst_0
      // 10da: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10dd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10e0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10e3: aastore
      // 10e4: dup
      // 10e5: bipush 4
      // 10e6: new net/minecraft/client/animation/Keyframe
      // 10e9: dup
      // 10ea: ldc 1.4167
      // 10ec: fconst_0
      // 10ed: fconst_0
      // 10ee: fconst_0
      // 10ef: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 10f2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 10f5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 10f8: aastore
      // 10f9: dup
      // 10fa: bipush 5
      // 10fb: new net/minecraft/client/animation/Keyframe
      // 10fe: dup
      // 10ff: ldc 1.6667
      // 1101: fconst_0
      // 1102: fconst_0
      // 1103: fconst_0
      // 1104: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1107: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 110a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 110d: aastore
      // 110e: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1111: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1114: ldc "teeth4"
      // 1116: new net/minecraft/client/animation/AnimationChannel
      // 1119: dup
      // 111a: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 111d: bipush 6
      // 111f: anewarray 28
      // 1122: dup
      // 1123: bipush 0
      // 1124: new net/minecraft/client/animation/Keyframe
      // 1127: dup
      // 1128: fconst_0
      // 1129: fconst_0
      // 112a: fconst_0
      // 112b: fconst_0
      // 112c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 112f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1132: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1135: aastore
      // 1136: dup
      // 1137: bipush 1
      // 1138: new net/minecraft/client/animation/Keyframe
      // 113b: dup
      // 113c: ldc 0.8333
      // 113e: fconst_0
      // 113f: fconst_0
      // 1140: fconst_0
      // 1141: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1144: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1147: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 114a: aastore
      // 114b: dup
      // 114c: bipush 2
      // 114d: new net/minecraft/client/animation/Keyframe
      // 1150: dup
      // 1151: fconst_1
      // 1152: ldc 80.0
      // 1154: fconst_0
      // 1155: fconst_0
      // 1156: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1159: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 115c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 115f: aastore
      // 1160: dup
      // 1161: bipush 3
      // 1162: new net/minecraft/client/animation/Keyframe
      // 1165: dup
      // 1166: ldc 1.25
      // 1168: ldc 80.0
      // 116a: fconst_0
      // 116b: fconst_0
      // 116c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 116f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1172: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1175: aastore
      // 1176: dup
      // 1177: bipush 4
      // 1178: new net/minecraft/client/animation/Keyframe
      // 117b: dup
      // 117c: ldc 1.4167
      // 117e: fconst_0
      // 117f: fconst_0
      // 1180: fconst_0
      // 1181: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1184: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1187: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 118a: aastore
      // 118b: dup
      // 118c: bipush 5
      // 118d: new net/minecraft/client/animation/Keyframe
      // 1190: dup
      // 1191: ldc 1.6667
      // 1193: fconst_0
      // 1194: fconst_0
      // 1195: fconst_0
      // 1196: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1199: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 119c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 119f: aastore
      // 11a0: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 11a3: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 11a6: ldc "left_tentacle1"
      // 11a8: new net/minecraft/client/animation/AnimationChannel
      // 11ab: dup
      // 11ac: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 11af: bipush 7
      // 11b1: anewarray 28
      // 11b4: dup
      // 11b5: bipush 0
      // 11b6: new net/minecraft/client/animation/Keyframe
      // 11b9: dup
      // 11ba: fconst_0
      // 11bb: fconst_0
      // 11bc: fconst_0
      // 11bd: fconst_0
      // 11be: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11c1: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11c4: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11c7: aastore
      // 11c8: dup
      // 11c9: bipush 1
      // 11ca: new net/minecraft/client/animation/Keyframe
      // 11cd: dup
      // 11ce: ldc 0.5
      // 11d0: fconst_0
      // 11d1: ldc -30.0
      // 11d3: ldc 75.0
      // 11d5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11d8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11db: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11de: aastore
      // 11df: dup
      // 11e0: bipush 2
      // 11e1: new net/minecraft/client/animation/Keyframe
      // 11e4: dup
      // 11e5: ldc 0.7917
      // 11e7: fconst_0
      // 11e8: ldc -40.0
      // 11ea: ldc 75.0
      // 11ec: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 11ef: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 11f2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 11f5: aastore
      // 11f6: dup
      // 11f7: bipush 3
      // 11f8: new net/minecraft/client/animation/Keyframe
      // 11fb: dup
      // 11fc: ldc 0.9583
      // 11fe: fconst_0
      // 11ff: ldc 17.5
      // 1201: fconst_0
      // 1202: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1205: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1208: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 120b: aastore
      // 120c: dup
      // 120d: bipush 4
      // 120e: new net/minecraft/client/animation/Keyframe
      // 1211: dup
      // 1212: ldc 1.25
      // 1214: fconst_0
      // 1215: ldc 17.5
      // 1217: fconst_0
      // 1218: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 121b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 121e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1221: aastore
      // 1222: dup
      // 1223: bipush 5
      // 1224: new net/minecraft/client/animation/Keyframe
      // 1227: dup
      // 1228: ldc 1.4167
      // 122a: ldc -14.5894
      // 122c: ldc 23.5711
      // 122e: ldc -33.0599
      // 1230: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1233: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1236: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1239: aastore
      // 123a: dup
      // 123b: bipush 6
      // 123d: new net/minecraft/client/animation/Keyframe
      // 1240: dup
      // 1241: ldc 1.6667
      // 1243: fconst_0
      // 1244: fconst_0
      // 1245: fconst_0
      // 1246: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1249: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 124c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 124f: aastore
      // 1250: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1253: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1256: ldc "right_tentacle1"
      // 1258: new net/minecraft/client/animation/AnimationChannel
      // 125b: dup
      // 125c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 125f: bipush 7
      // 1261: anewarray 28
      // 1264: dup
      // 1265: bipush 0
      // 1266: new net/minecraft/client/animation/Keyframe
      // 1269: dup
      // 126a: fconst_0
      // 126b: fconst_0
      // 126c: fconst_0
      // 126d: fconst_0
      // 126e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1271: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1274: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1277: aastore
      // 1278: dup
      // 1279: bipush 1
      // 127a: new net/minecraft/client/animation/Keyframe
      // 127d: dup
      // 127e: ldc 0.5
      // 1280: fconst_0
      // 1281: ldc 30.0
      // 1283: ldc -75.0
      // 1285: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1288: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 128b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 128e: aastore
      // 128f: dup
      // 1290: bipush 2
      // 1291: new net/minecraft/client/animation/Keyframe
      // 1294: dup
      // 1295: ldc 0.7917
      // 1297: fconst_0
      // 1298: ldc 40.0
      // 129a: ldc -75.0
      // 129c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 129f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12a5: aastore
      // 12a6: dup
      // 12a7: bipush 3
      // 12a8: new net/minecraft/client/animation/Keyframe
      // 12ab: dup
      // 12ac: ldc 0.9583
      // 12ae: fconst_0
      // 12af: ldc -17.5
      // 12b1: fconst_0
      // 12b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12bb: aastore
      // 12bc: dup
      // 12bd: bipush 4
      // 12be: new net/minecraft/client/animation/Keyframe
      // 12c1: dup
      // 12c2: ldc 1.25
      // 12c4: fconst_0
      // 12c5: ldc -17.5
      // 12c7: fconst_0
      // 12c8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12cb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12ce: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12d1: aastore
      // 12d2: dup
      // 12d3: bipush 5
      // 12d4: new net/minecraft/client/animation/Keyframe
      // 12d7: dup
      // 12d8: ldc 1.4167
      // 12da: ldc -14.5894
      // 12dc: ldc -23.5711
      // 12de: ldc 33.0599
      // 12e0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12e3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12e6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12e9: aastore
      // 12ea: dup
      // 12eb: bipush 6
      // 12ed: new net/minecraft/client/animation/Keyframe
      // 12f0: dup
      // 12f1: ldc 1.6667
      // 12f3: fconst_0
      // 12f4: fconst_0
      // 12f5: fconst_0
      // 12f6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 12f9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 12fc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 12ff: aastore
      // 1300: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1303: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1306: ldc "left_tentacle2"
      // 1308: new net/minecraft/client/animation/AnimationChannel
      // 130b: dup
      // 130c: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 130f: bipush 7
      // 1311: anewarray 28
      // 1314: dup
      // 1315: bipush 0
      // 1316: new net/minecraft/client/animation/Keyframe
      // 1319: dup
      // 131a: fconst_0
      // 131b: fconst_0
      // 131c: fconst_0
      // 131d: fconst_0
      // 131e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1321: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1324: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1327: aastore
      // 1328: dup
      // 1329: bipush 1
      // 132a: new net/minecraft/client/animation/Keyframe
      // 132d: dup
      // 132e: ldc 0.5
      // 1330: fconst_0
      // 1331: ldc -32.5
      // 1333: ldc 85.0
      // 1335: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1338: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 133b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 133e: aastore
      // 133f: dup
      // 1340: bipush 2
      // 1341: new net/minecraft/client/animation/Keyframe
      // 1344: dup
      // 1345: ldc 0.7917
      // 1347: fconst_0
      // 1348: ldc -40.0
      // 134a: ldc 85.0
      // 134c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 134f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1352: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1355: aastore
      // 1356: dup
      // 1357: bipush 3
      // 1358: new net/minecraft/client/animation/Keyframe
      // 135b: dup
      // 135c: ldc 0.9583
      // 135e: fconst_0
      // 135f: fconst_0
      // 1360: fconst_0
      // 1361: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1364: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1367: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 136a: aastore
      // 136b: dup
      // 136c: bipush 4
      // 136d: new net/minecraft/client/animation/Keyframe
      // 1370: dup
      // 1371: ldc 1.25
      // 1373: fconst_0
      // 1374: fconst_0
      // 1375: fconst_0
      // 1376: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1379: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 137c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 137f: aastore
      // 1380: dup
      // 1381: bipush 5
      // 1382: new net/minecraft/client/animation/Keyframe
      // 1385: dup
      // 1386: ldc 1.4167
      // 1388: fconst_0
      // 1389: fconst_0
      // 138a: ldc -30.0
      // 138c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 138f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1392: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1395: aastore
      // 1396: dup
      // 1397: bipush 6
      // 1399: new net/minecraft/client/animation/Keyframe
      // 139c: dup
      // 139d: ldc 1.6667
      // 139f: fconst_0
      // 13a0: fconst_0
      // 13a1: fconst_0
      // 13a2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13a5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13a8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ab: aastore
      // 13ac: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 13af: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 13b2: ldc "right_tentacle2"
      // 13b4: new net/minecraft/client/animation/AnimationChannel
      // 13b7: dup
      // 13b8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 13bb: bipush 7
      // 13bd: anewarray 28
      // 13c0: dup
      // 13c1: bipush 0
      // 13c2: new net/minecraft/client/animation/Keyframe
      // 13c5: dup
      // 13c6: fconst_0
      // 13c7: fconst_0
      // 13c8: fconst_0
      // 13c9: fconst_0
      // 13ca: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13cd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13d0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13d3: aastore
      // 13d4: dup
      // 13d5: bipush 1
      // 13d6: new net/minecraft/client/animation/Keyframe
      // 13d9: dup
      // 13da: ldc 0.5
      // 13dc: fconst_0
      // 13dd: ldc 32.5
      // 13df: ldc -85.0
      // 13e1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13e4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13e7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 13ea: aastore
      // 13eb: dup
      // 13ec: bipush 2
      // 13ed: new net/minecraft/client/animation/Keyframe
      // 13f0: dup
      // 13f1: ldc 0.7917
      // 13f3: fconst_0
      // 13f4: ldc 40.0
      // 13f6: ldc -85.0
      // 13f8: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 13fb: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 13fe: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1401: aastore
      // 1402: dup
      // 1403: bipush 3
      // 1404: new net/minecraft/client/animation/Keyframe
      // 1407: dup
      // 1408: ldc 0.9583
      // 140a: fconst_0
      // 140b: fconst_0
      // 140c: fconst_0
      // 140d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1410: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1413: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1416: aastore
      // 1417: dup
      // 1418: bipush 4
      // 1419: new net/minecraft/client/animation/Keyframe
      // 141c: dup
      // 141d: ldc 1.25
      // 141f: fconst_0
      // 1420: fconst_0
      // 1421: fconst_0
      // 1422: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1425: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1428: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 142b: aastore
      // 142c: dup
      // 142d: bipush 5
      // 142e: new net/minecraft/client/animation/Keyframe
      // 1431: dup
      // 1432: ldc 1.4167
      // 1434: fconst_0
      // 1435: fconst_0
      // 1436: ldc 30.0
      // 1438: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 143b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 143e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1441: aastore
      // 1442: dup
      // 1443: bipush 6
      // 1445: new net/minecraft/client/animation/Keyframe
      // 1448: dup
      // 1449: ldc 1.6667
      // 144b: fconst_0
      // 144c: fconst_0
      // 144d: fconst_0
      // 144e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1451: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1454: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1457: aastore
      // 1458: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 145b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 145e: ldc "left_tentacle3"
      // 1460: new net/minecraft/client/animation/AnimationChannel
      // 1463: dup
      // 1464: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1467: bipush 5
      // 1468: anewarray 28
      // 146b: dup
      // 146c: bipush 0
      // 146d: new net/minecraft/client/animation/Keyframe
      // 1470: dup
      // 1471: fconst_0
      // 1472: fconst_0
      // 1473: fconst_0
      // 1474: fconst_0
      // 1475: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1478: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 147b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 147e: aastore
      // 147f: dup
      // 1480: bipush 1
      // 1481: new net/minecraft/client/animation/Keyframe
      // 1484: dup
      // 1485: ldc 0.5
      // 1487: fconst_0
      // 1488: ldc -50.0
      // 148a: ldc 62.5
      // 148c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 148f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1492: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1495: aastore
      // 1496: dup
      // 1497: bipush 2
      // 1498: new net/minecraft/client/animation/Keyframe
      // 149b: dup
      // 149c: ldc 1.2083
      // 149e: fconst_0
      // 149f: ldc -50.0
      // 14a1: ldc 62.5
      // 14a3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14a6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14a9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14ac: aastore
      // 14ad: dup
      // 14ae: bipush 3
      // 14af: new net/minecraft/client/animation/Keyframe
      // 14b2: dup
      // 14b3: ldc 1.4167
      // 14b5: ldc 14.5894
      // 14b7: ldc -23.5711
      // 14b9: ldc -33.0599
      // 14bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14c4: aastore
      // 14c5: dup
      // 14c6: bipush 4
      // 14c7: new net/minecraft/client/animation/Keyframe
      // 14ca: dup
      // 14cb: ldc 1.6667
      // 14cd: fconst_0
      // 14ce: fconst_0
      // 14cf: fconst_0
      // 14d0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14d3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14d6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 14d9: aastore
      // 14da: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 14dd: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 14e0: ldc "right_tentacle3"
      // 14e2: new net/minecraft/client/animation/AnimationChannel
      // 14e5: dup
      // 14e6: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 14e9: bipush 5
      // 14ea: anewarray 28
      // 14ed: dup
      // 14ee: bipush 0
      // 14ef: new net/minecraft/client/animation/Keyframe
      // 14f2: dup
      // 14f3: fconst_0
      // 14f4: fconst_0
      // 14f5: fconst_0
      // 14f6: fconst_0
      // 14f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 14fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 14fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1500: aastore
      // 1501: dup
      // 1502: bipush 1
      // 1503: new net/minecraft/client/animation/Keyframe
      // 1506: dup
      // 1507: ldc 0.5
      // 1509: fconst_0
      // 150a: ldc 50.0
      // 150c: ldc -62.5
      // 150e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1511: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1514: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1517: aastore
      // 1518: dup
      // 1519: bipush 2
      // 151a: new net/minecraft/client/animation/Keyframe
      // 151d: dup
      // 151e: ldc 1.2083
      // 1520: fconst_0
      // 1521: ldc 50.0
      // 1523: ldc -62.5
      // 1525: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1528: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 152b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 152e: aastore
      // 152f: dup
      // 1530: bipush 3
      // 1531: new net/minecraft/client/animation/Keyframe
      // 1534: dup
      // 1535: ldc 1.4167
      // 1537: ldc 14.5894
      // 1539: ldc 23.5711
      // 153b: ldc 33.0599
      // 153d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1540: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1543: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1546: aastore
      // 1547: dup
      // 1548: bipush 4
      // 1549: new net/minecraft/client/animation/Keyframe
      // 154c: dup
      // 154d: ldc 1.6667
      // 154f: fconst_0
      // 1550: fconst_0
      // 1551: fconst_0
      // 1552: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1555: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1558: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 155b: aastore
      // 155c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 155f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1562: ldc "back_tentacle2"
      // 1564: new net/minecraft/client/animation/AnimationChannel
      // 1567: dup
      // 1568: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 156b: bipush 7
      // 156d: anewarray 28
      // 1570: dup
      // 1571: bipush 0
      // 1572: new net/minecraft/client/animation/Keyframe
      // 1575: dup
      // 1576: fconst_0
      // 1577: fconst_0
      // 1578: fconst_0
      // 1579: fconst_0
      // 157a: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 157d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1580: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1583: aastore
      // 1584: dup
      // 1585: bipush 1
      // 1586: new net/minecraft/client/animation/Keyframe
      // 1589: dup
      // 158a: ldc 0.5
      // 158c: fconst_0
      // 158d: fconst_0
      // 158e: fconst_0
      // 158f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1592: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1595: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1598: aastore
      // 1599: dup
      // 159a: bipush 2
      // 159b: new net/minecraft/client/animation/Keyframe
      // 159e: dup
      // 159f: ldc 0.7083
      // 15a1: ldc -17.5
      // 15a3: fconst_0
      // 15a4: fconst_0
      // 15a5: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15a8: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ab: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15ae: aastore
      // 15af: dup
      // 15b0: bipush 3
      // 15b1: new net/minecraft/client/animation/Keyframe
      // 15b4: dup
      // 15b5: ldc 0.875
      // 15b7: ldc -35.0
      // 15b9: fconst_0
      // 15ba: fconst_0
      // 15bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15c4: aastore
      // 15c5: dup
      // 15c6: bipush 4
      // 15c7: new net/minecraft/client/animation/Keyframe
      // 15ca: dup
      // 15cb: ldc 1.1667
      // 15cd: ldc 10.68
      // 15cf: fconst_0
      // 15d0: fconst_0
      // 15d1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15d4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15d7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15da: aastore
      // 15db: dup
      // 15dc: bipush 5
      // 15dd: new net/minecraft/client/animation/Keyframe
      // 15e0: dup
      // 15e1: ldc 1.4167
      // 15e3: ldc 30.0
      // 15e5: fconst_0
      // 15e6: fconst_0
      // 15e7: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 15ea: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 15ed: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 15f0: aastore
      // 15f1: dup
      // 15f2: bipush 6
      // 15f4: new net/minecraft/client/animation/Keyframe
      // 15f7: dup
      // 15f8: ldc 1.6667
      // 15fa: fconst_0
      // 15fb: fconst_0
      // 15fc: fconst_0
      // 15fd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1600: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1603: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1606: aastore
      // 1607: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 160a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 160d: ldc "back_tentacle3"
      // 160f: new net/minecraft/client/animation/AnimationChannel
      // 1612: dup
      // 1613: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1616: bipush 7
      // 1618: anewarray 28
      // 161b: dup
      // 161c: bipush 0
      // 161d: new net/minecraft/client/animation/Keyframe
      // 1620: dup
      // 1621: fconst_0
      // 1622: fconst_0
      // 1623: fconst_0
      // 1624: fconst_0
      // 1625: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1628: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 162b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 162e: aastore
      // 162f: dup
      // 1630: bipush 1
      // 1631: new net/minecraft/client/animation/Keyframe
      // 1634: dup
      // 1635: ldc 0.5
      // 1637: ldc 5.4161
      // 1639: ldc -16.6658
      // 163b: ldc -18.2939
      // 163d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1640: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1643: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1646: aastore
      // 1647: dup
      // 1648: bipush 2
      // 1649: new net/minecraft/client/animation/Keyframe
      // 164c: dup
      // 164d: ldc 0.7083
      // 164f: ldc -12.0839
      // 1651: ldc -16.6658
      // 1653: ldc -18.2939
      // 1655: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1658: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 165b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 165e: aastore
      // 165f: dup
      // 1660: bipush 3
      // 1661: new net/minecraft/client/animation/Keyframe
      // 1664: dup
      // 1665: ldc 0.875
      // 1667: ldc -29.5839
      // 1669: ldc -16.6658
      // 166b: ldc -18.2939
      // 166d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1670: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1673: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1676: aastore
      // 1677: dup
      // 1678: bipush 4
      // 1679: new net/minecraft/client/animation/Keyframe
      // 167c: dup
      // 167d: ldc 1.1667
      // 167f: ldc 23.125
      // 1681: ldc -7.5326
      // 1683: ldc -8.2685
      // 1685: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1688: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 168b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 168e: aastore
      // 168f: dup
      // 1690: bipush 5
      // 1691: new net/minecraft/client/animation/Keyframe
      // 1694: dup
      // 1695: ldc 1.4167
      // 1697: ldc 30.0
      // 1699: ldc -22.5
      // 169b: fconst_0
      // 169c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 169f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16a2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16a5: aastore
      // 16a6: dup
      // 16a7: bipush 6
      // 16a9: new net/minecraft/client/animation/Keyframe
      // 16ac: dup
      // 16ad: ldc 1.6667
      // 16af: fconst_0
      // 16b0: fconst_0
      // 16b1: fconst_0
      // 16b2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 16b5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16b8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16bb: aastore
      // 16bc: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 16bf: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 16c2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 16c5: putstatic com/github/L_Ender/cataclysm/client/animation/Symbiocto_Animation.INK Lnet/minecraft/client/animation/AnimationDefinition;
      // 16c8: ldc 1.5
      // 16ca: invokestatic net/minecraft/client/animation/AnimationDefinition$Builder.withLength (F)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 16cd: ldc "everything"
      // 16cf: new net/minecraft/client/animation/AnimationChannel
      // 16d2: dup
      // 16d3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 16d6: bipush 5
      // 16d7: anewarray 28
      // 16da: dup
      // 16db: bipush 0
      // 16dc: new net/minecraft/client/animation/Keyframe
      // 16df: dup
      // 16e0: fconst_0
      // 16e1: dconst_1
      // 16e2: dconst_1
      // 16e3: dconst_1
      // 16e4: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16e7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ea: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 16ed: aastore
      // 16ee: dup
      // 16ef: bipush 1
      // 16f0: new net/minecraft/client/animation/Keyframe
      // 16f3: dup
      // 16f4: ldc 0.3333
      // 16f6: dconst_1
      // 16f7: dconst_1
      // 16f8: dconst_1
      // 16f9: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 16fc: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 16ff: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1702: aastore
      // 1703: dup
      // 1704: bipush 2
      // 1705: new net/minecraft/client/animation/Keyframe
      // 1708: dup
      // 1709: ldc 0.5
      // 170b: ldc2_w 1.2000000476837158
      // 170e: ldc2_w 0.800000011920929
      // 1711: ldc2_w 1.2000000476837158
      // 1714: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1717: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 171a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 171d: aastore
      // 171e: dup
      // 171f: bipush 3
      // 1720: new net/minecraft/client/animation/Keyframe
      // 1723: dup
      // 1724: ldc 0.875
      // 1726: dconst_1
      // 1727: dconst_1
      // 1728: dconst_1
      // 1729: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 172c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 172f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1732: aastore
      // 1733: dup
      // 1734: bipush 4
      // 1735: new net/minecraft/client/animation/Keyframe
      // 1738: dup
      // 1739: ldc 1.5
      // 173b: dconst_1
      // 173c: dconst_1
      // 173d: dconst_1
      // 173e: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1741: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1744: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1747: aastore
      // 1748: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 174b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 174e: ldc "octo_head"
      // 1750: new net/minecraft/client/animation/AnimationChannel
      // 1753: dup
      // 1754: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1757: bipush 5
      // 1758: anewarray 28
      // 175b: dup
      // 175c: bipush 0
      // 175d: new net/minecraft/client/animation/Keyframe
      // 1760: dup
      // 1761: fconst_0
      // 1762: fconst_0
      // 1763: fconst_0
      // 1764: fconst_0
      // 1765: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1768: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 176b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 176e: aastore
      // 176f: dup
      // 1770: bipush 1
      // 1771: new net/minecraft/client/animation/Keyframe
      // 1774: dup
      // 1775: ldc 0.375
      // 1777: ldc -7.5
      // 1779: fconst_0
      // 177a: fconst_0
      // 177b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 177e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1781: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1784: aastore
      // 1785: dup
      // 1786: bipush 2
      // 1787: new net/minecraft/client/animation/Keyframe
      // 178a: dup
      // 178b: ldc 0.5
      // 178d: fconst_0
      // 178e: fconst_0
      // 178f: fconst_0
      // 1790: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1793: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1796: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1799: aastore
      // 179a: dup
      // 179b: bipush 3
      // 179c: new net/minecraft/client/animation/Keyframe
      // 179f: dup
      // 17a0: ldc 0.7917
      // 17a2: ldc -7.5
      // 17a4: fconst_0
      // 17a5: fconst_0
      // 17a6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17a9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17ac: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17af: aastore
      // 17b0: dup
      // 17b1: bipush 4
      // 17b2: new net/minecraft/client/animation/Keyframe
      // 17b5: dup
      // 17b6: ldc 1.5
      // 17b8: fconst_0
      // 17b9: fconst_0
      // 17ba: fconst_0
      // 17bb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 17be: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17c1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17c4: aastore
      // 17c5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 17c8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 17cb: ldc "octo_head"
      // 17cd: new net/minecraft/client/animation/AnimationChannel
      // 17d0: dup
      // 17d1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.POSITION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 17d4: bipush 4
      // 17d5: anewarray 28
      // 17d8: dup
      // 17d9: bipush 0
      // 17da: new net/minecraft/client/animation/Keyframe
      // 17dd: dup
      // 17de: fconst_0
      // 17df: fconst_0
      // 17e0: fconst_0
      // 17e1: fconst_0
      // 17e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 17eb: aastore
      // 17ec: dup
      // 17ed: bipush 1
      // 17ee: new net/minecraft/client/animation/Keyframe
      // 17f1: dup
      // 17f2: ldc 0.625
      // 17f4: fconst_0
      // 17f5: fconst_0
      // 17f6: fconst_0
      // 17f7: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 17fa: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 17fd: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1800: aastore
      // 1801: dup
      // 1802: bipush 2
      // 1803: new net/minecraft/client/animation/Keyframe
      // 1806: dup
      // 1807: ldc 1.125
      // 1809: fconst_0
      // 180a: ldc -0.7
      // 180c: fconst_0
      // 180d: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1810: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1813: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1816: aastore
      // 1817: dup
      // 1818: bipush 3
      // 1819: new net/minecraft/client/animation/Keyframe
      // 181c: dup
      // 181d: ldc 1.5
      // 181f: fconst_0
      // 1820: fconst_0
      // 1821: fconst_0
      // 1822: invokestatic net/minecraft/client/animation/KeyframeAnimations.posVec (FFF)Lorg/joml/Vector3f;
      // 1825: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1828: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 182b: aastore
      // 182c: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 182f: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1832: ldc "octo_head"
      // 1834: new net/minecraft/client/animation/AnimationChannel
      // 1837: dup
      // 1838: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 183b: bipush 4
      // 183c: anewarray 28
      // 183f: dup
      // 1840: bipush 0
      // 1841: new net/minecraft/client/animation/Keyframe
      // 1844: dup
      // 1845: fconst_0
      // 1846: dconst_1
      // 1847: dconst_1
      // 1848: dconst_1
      // 1849: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 184c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 184f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1852: aastore
      // 1853: dup
      // 1854: bipush 1
      // 1855: new net/minecraft/client/animation/Keyframe
      // 1858: dup
      // 1859: ldc 0.5
      // 185b: ldc2_w 1.100000023841858
      // 185e: ldc2_w 1.2000000476837158
      // 1861: dconst_1
      // 1862: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1865: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1868: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 186b: aastore
      // 186c: dup
      // 186d: bipush 2
      // 186e: new net/minecraft/client/animation/Keyframe
      // 1871: dup
      // 1872: fconst_1
      // 1873: ldc2_w 1.0155999660491943
      // 1876: ldc2_w 0.8313000202178955
      // 1879: dconst_1
      // 187a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 187d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1880: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1883: aastore
      // 1884: dup
      // 1885: bipush 3
      // 1886: new net/minecraft/client/animation/Keyframe
      // 1889: dup
      // 188a: ldc 1.5
      // 188c: dconst_1
      // 188d: dconst_1
      // 188e: dconst_1
      // 188f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1892: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1895: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1898: aastore
      // 1899: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 189c: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 189f: ldc "back_tentacle1"
      // 18a1: new net/minecraft/client/animation/AnimationChannel
      // 18a4: dup
      // 18a5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 18a8: bipush 3
      // 18a9: anewarray 28
      // 18ac: dup
      // 18ad: bipush 0
      // 18ae: new net/minecraft/client/animation/Keyframe
      // 18b1: dup
      // 18b2: fconst_0
      // 18b3: fconst_0
      // 18b4: fconst_0
      // 18b5: fconst_0
      // 18b6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18b9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18bc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18bf: aastore
      // 18c0: dup
      // 18c1: bipush 1
      // 18c2: new net/minecraft/client/animation/Keyframe
      // 18c5: dup
      // 18c6: ldc 0.75
      // 18c8: ldc 30.0
      // 18ca: ldc 35.0
      // 18cc: fconst_0
      // 18cd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18d0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18d3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18d6: aastore
      // 18d7: dup
      // 18d8: bipush 2
      // 18d9: new net/minecraft/client/animation/Keyframe
      // 18dc: dup
      // 18dd: ldc 1.5
      // 18df: fconst_0
      // 18e0: fconst_0
      // 18e1: fconst_0
      // 18e2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 18e5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 18e8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 18eb: aastore
      // 18ec: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 18ef: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 18f2: ldc "face_tentacle2"
      // 18f4: new net/minecraft/client/animation/AnimationChannel
      // 18f7: dup
      // 18f8: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 18fb: bipush 4
      // 18fc: anewarray 28
      // 18ff: dup
      // 1900: bipush 0
      // 1901: new net/minecraft/client/animation/Keyframe
      // 1904: dup
      // 1905: fconst_0
      // 1906: fconst_0
      // 1907: fconst_0
      // 1908: fconst_0
      // 1909: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 190c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 190f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1912: aastore
      // 1913: dup
      // 1914: bipush 1
      // 1915: new net/minecraft/client/animation/Keyframe
      // 1918: dup
      // 1919: ldc 0.2917
      // 191b: ldc -69.7
      // 191d: fconst_0
      // 191e: fconst_0
      // 191f: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1922: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1925: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1928: aastore
      // 1929: dup
      // 192a: bipush 2
      // 192b: new net/minecraft/client/animation/Keyframe
      // 192e: dup
      // 192f: ldc 0.4583
      // 1931: fconst_0
      // 1932: fconst_0
      // 1933: fconst_0
      // 1934: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1937: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 193a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 193d: aastore
      // 193e: dup
      // 193f: bipush 3
      // 1940: new net/minecraft/client/animation/Keyframe
      // 1943: dup
      // 1944: ldc 1.5
      // 1946: fconst_0
      // 1947: fconst_0
      // 1948: fconst_0
      // 1949: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 194c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 194f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1952: aastore
      // 1953: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1956: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1959: ldc "face_tentacle2"
      // 195b: new net/minecraft/client/animation/AnimationChannel
      // 195e: dup
      // 195f: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1962: bipush 3
      // 1963: anewarray 28
      // 1966: dup
      // 1967: bipush 0
      // 1968: new net/minecraft/client/animation/Keyframe
      // 196b: dup
      // 196c: ldc 0.25
      // 196e: dconst_1
      // 196f: dconst_1
      // 1970: dconst_1
      // 1971: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1974: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1977: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 197a: aastore
      // 197b: dup
      // 197c: bipush 1
      // 197d: new net/minecraft/client/animation/Keyframe
      // 1980: dup
      // 1981: ldc 0.375
      // 1983: dconst_1
      // 1984: ldc2_w 1.600000023841858
      // 1987: ldc2_w 1.399999976158142
      // 198a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 198d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1990: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1993: aastore
      // 1994: dup
      // 1995: bipush 2
      // 1996: new net/minecraft/client/animation/Keyframe
      // 1999: dup
      // 199a: ldc 0.5
      // 199c: dconst_1
      // 199d: dconst_1
      // 199e: dconst_1
      // 199f: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 19a2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19a5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19a8: aastore
      // 19a9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 19ac: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 19af: ldc "face_tentacle1"
      // 19b1: new net/minecraft/client/animation/AnimationChannel
      // 19b4: dup
      // 19b5: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 19b8: bipush 5
      // 19b9: anewarray 28
      // 19bc: dup
      // 19bd: bipush 0
      // 19be: new net/minecraft/client/animation/Keyframe
      // 19c1: dup
      // 19c2: fconst_0
      // 19c3: fconst_0
      // 19c4: fconst_0
      // 19c5: fconst_0
      // 19c6: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19c9: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19cc: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19cf: aastore
      // 19d0: dup
      // 19d1: bipush 1
      // 19d2: new net/minecraft/client/animation/Keyframe
      // 19d5: dup
      // 19d6: ldc 0.2917
      // 19d8: ldc -62.5
      // 19da: ldc -27.5
      // 19dc: fconst_0
      // 19dd: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19e0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19e3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19e6: aastore
      // 19e7: dup
      // 19e8: bipush 2
      // 19e9: new net/minecraft/client/animation/Keyframe
      // 19ec: dup
      // 19ed: ldc 0.4583
      // 19ef: fconst_0
      // 19f0: ldc -27.5
      // 19f2: fconst_0
      // 19f3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 19f6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 19f9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 19fc: aastore
      // 19fd: dup
      // 19fe: bipush 3
      // 19ff: new net/minecraft/client/animation/Keyframe
      // 1a02: dup
      // 1a03: ldc 0.6667
      // 1a05: fconst_0
      // 1a06: ldc -27.5
      // 1a08: fconst_0
      // 1a09: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a12: aastore
      // 1a13: dup
      // 1a14: bipush 4
      // 1a15: new net/minecraft/client/animation/Keyframe
      // 1a18: dup
      // 1a19: ldc 1.5
      // 1a1b: fconst_0
      // 1a1c: fconst_0
      // 1a1d: fconst_0
      // 1a1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1a21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a27: aastore
      // 1a28: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1a2b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1a2e: ldc "face_tentacle1"
      // 1a30: new net/minecraft/client/animation/AnimationChannel
      // 1a33: dup
      // 1a34: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1a37: bipush 5
      // 1a38: anewarray 28
      // 1a3b: dup
      // 1a3c: bipush 0
      // 1a3d: new net/minecraft/client/animation/Keyframe
      // 1a40: dup
      // 1a41: fconst_0
      // 1a42: dconst_1
      // 1a43: dconst_1
      // 1a44: dconst_1
      // 1a45: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a4e: aastore
      // 1a4f: dup
      // 1a50: bipush 1
      // 1a51: new net/minecraft/client/animation/Keyframe
      // 1a54: dup
      // 1a55: ldc 0.25
      // 1a57: dconst_1
      // 1a58: dconst_1
      // 1a59: dconst_1
      // 1a5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a63: aastore
      // 1a64: dup
      // 1a65: bipush 2
      // 1a66: new net/minecraft/client/animation/Keyframe
      // 1a69: dup
      // 1a6a: ldc 0.375
      // 1a6c: dconst_1
      // 1a6d: ldc2_w 1.600000023841858
      // 1a70: ldc2_w 1.399999976158142
      // 1a73: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a7c: aastore
      // 1a7d: dup
      // 1a7e: bipush 3
      // 1a7f: new net/minecraft/client/animation/Keyframe
      // 1a82: dup
      // 1a83: ldc 0.5
      // 1a85: dconst_1
      // 1a86: dconst_1
      // 1a87: dconst_1
      // 1a88: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1a8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1a8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1a91: aastore
      // 1a92: dup
      // 1a93: bipush 4
      // 1a94: new net/minecraft/client/animation/Keyframe
      // 1a97: dup
      // 1a98: ldc 1.5
      // 1a9a: dconst_1
      // 1a9b: dconst_1
      // 1a9c: dconst_1
      // 1a9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1aa0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aa3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1aa6: aastore
      // 1aa7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1aaa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1aad: ldc "face_tentacle3"
      // 1aaf: new net/minecraft/client/animation/AnimationChannel
      // 1ab2: dup
      // 1ab3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1ab6: bipush 5
      // 1ab7: anewarray 28
      // 1aba: dup
      // 1abb: bipush 0
      // 1abc: new net/minecraft/client/animation/Keyframe
      // 1abf: dup
      // 1ac0: fconst_0
      // 1ac1: fconst_0
      // 1ac2: fconst_0
      // 1ac3: fconst_0
      // 1ac4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ac7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1aca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1acd: aastore
      // 1ace: dup
      // 1acf: bipush 1
      // 1ad0: new net/minecraft/client/animation/Keyframe
      // 1ad3: dup
      // 1ad4: ldc 0.2917
      // 1ad6: ldc -62.5
      // 1ad8: ldc 27.5
      // 1ada: fconst_0
      // 1adb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ade: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ae1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ae4: aastore
      // 1ae5: dup
      // 1ae6: bipush 2
      // 1ae7: new net/minecraft/client/animation/Keyframe
      // 1aea: dup
      // 1aeb: ldc 0.4583
      // 1aed: fconst_0
      // 1aee: ldc 27.5
      // 1af0: fconst_0
      // 1af1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1af4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1af7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1afa: aastore
      // 1afb: dup
      // 1afc: bipush 3
      // 1afd: new net/minecraft/client/animation/Keyframe
      // 1b00: dup
      // 1b01: ldc 0.6667
      // 1b03: fconst_0
      // 1b04: ldc 27.5
      // 1b06: fconst_0
      // 1b07: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b0a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b0d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b10: aastore
      // 1b11: dup
      // 1b12: bipush 4
      // 1b13: new net/minecraft/client/animation/Keyframe
      // 1b16: dup
      // 1b17: ldc 1.5
      // 1b19: fconst_0
      // 1b1a: fconst_0
      // 1b1b: fconst_0
      // 1b1c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1b1f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b22: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b25: aastore
      // 1b26: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1b29: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1b2c: ldc "face_tentacle3"
      // 1b2e: new net/minecraft/client/animation/AnimationChannel
      // 1b31: dup
      // 1b32: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1b35: bipush 5
      // 1b36: anewarray 28
      // 1b39: dup
      // 1b3a: bipush 0
      // 1b3b: new net/minecraft/client/animation/Keyframe
      // 1b3e: dup
      // 1b3f: fconst_0
      // 1b40: dconst_1
      // 1b41: dconst_1
      // 1b42: dconst_1
      // 1b43: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b46: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b49: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b4c: aastore
      // 1b4d: dup
      // 1b4e: bipush 1
      // 1b4f: new net/minecraft/client/animation/Keyframe
      // 1b52: dup
      // 1b53: ldc 0.25
      // 1b55: dconst_1
      // 1b56: dconst_1
      // 1b57: dconst_1
      // 1b58: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b5b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b5e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b61: aastore
      // 1b62: dup
      // 1b63: bipush 2
      // 1b64: new net/minecraft/client/animation/Keyframe
      // 1b67: dup
      // 1b68: ldc 0.375
      // 1b6a: dconst_1
      // 1b6b: ldc2_w 1.600000023841858
      // 1b6e: ldc2_w 1.399999976158142
      // 1b71: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b74: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b77: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b7a: aastore
      // 1b7b: dup
      // 1b7c: bipush 3
      // 1b7d: new net/minecraft/client/animation/Keyframe
      // 1b80: dup
      // 1b81: ldc 0.5
      // 1b83: dconst_1
      // 1b84: dconst_1
      // 1b85: dconst_1
      // 1b86: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b89: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1b8c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1b8f: aastore
      // 1b90: dup
      // 1b91: bipush 4
      // 1b92: new net/minecraft/client/animation/Keyframe
      // 1b95: dup
      // 1b96: ldc 1.5
      // 1b98: dconst_1
      // 1b99: dconst_1
      // 1b9a: dconst_1
      // 1b9b: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1b9e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ba1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ba4: aastore
      // 1ba5: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ba8: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1bab: ldc "left_tentacle1"
      // 1bad: new net/minecraft/client/animation/AnimationChannel
      // 1bb0: dup
      // 1bb1: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1bb4: bipush 5
      // 1bb5: anewarray 28
      // 1bb8: dup
      // 1bb9: bipush 0
      // 1bba: new net/minecraft/client/animation/Keyframe
      // 1bbd: dup
      // 1bbe: fconst_0
      // 1bbf: fconst_0
      // 1bc0: fconst_0
      // 1bc1: fconst_0
      // 1bc2: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bc5: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bc8: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bcb: aastore
      // 1bcc: dup
      // 1bcd: bipush 1
      // 1bce: new net/minecraft/client/animation/Keyframe
      // 1bd1: dup
      // 1bd2: ldc 0.2917
      // 1bd4: ldc -10.3634
      // 1bd6: ldc 4.6391
      // 1bd8: ldc -53.5649
      // 1bda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bdd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1be0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1be3: aastore
      // 1be4: dup
      // 1be5: bipush 2
      // 1be6: new net/minecraft/client/animation/Keyframe
      // 1be9: dup
      // 1bea: ldc 0.4583
      // 1bec: fconst_0
      // 1bed: ldc 42.5
      // 1bef: fconst_0
      // 1bf0: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1bf3: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1bf6: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1bf9: aastore
      // 1bfa: dup
      // 1bfb: bipush 3
      // 1bfc: new net/minecraft/client/animation/Keyframe
      // 1bff: dup
      // 1c00: ldc 0.9167
      // 1c02: ldc -15.0
      // 1c04: ldc 45.0
      // 1c06: ldc -25.0
      // 1c08: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c0b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c0e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c11: aastore
      // 1c12: dup
      // 1c13: bipush 4
      // 1c14: new net/minecraft/client/animation/Keyframe
      // 1c17: dup
      // 1c18: ldc 1.5
      // 1c1a: fconst_0
      // 1c1b: fconst_0
      // 1c1c: fconst_0
      // 1c1d: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1c20: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c23: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c26: aastore
      // 1c27: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1c2a: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1c2d: ldc "left_tentacle1"
      // 1c2f: new net/minecraft/client/animation/AnimationChannel
      // 1c32: dup
      // 1c33: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1c36: bipush 5
      // 1c37: anewarray 28
      // 1c3a: dup
      // 1c3b: bipush 0
      // 1c3c: new net/minecraft/client/animation/Keyframe
      // 1c3f: dup
      // 1c40: fconst_0
      // 1c41: dconst_1
      // 1c42: dconst_1
      // 1c43: dconst_1
      // 1c44: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1c47: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c4a: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c4d: aastore
      // 1c4e: dup
      // 1c4f: bipush 1
      // 1c50: new net/minecraft/client/animation/Keyframe
      // 1c53: dup
      // 1c54: ldc 0.25
      // 1c56: dconst_1
      // 1c57: dconst_1
      // 1c58: dconst_1
      // 1c59: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1c5c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c5f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c62: aastore
      // 1c63: dup
      // 1c64: bipush 2
      // 1c65: new net/minecraft/client/animation/Keyframe
      // 1c68: dup
      // 1c69: ldc 0.375
      // 1c6b: dconst_1
      // 1c6c: ldc2_w 1.600000023841858
      // 1c6f: ldc2_w 1.399999976158142
      // 1c72: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1c75: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c78: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c7b: aastore
      // 1c7c: dup
      // 1c7d: bipush 3
      // 1c7e: new net/minecraft/client/animation/Keyframe
      // 1c81: dup
      // 1c82: ldc 0.5
      // 1c84: dconst_1
      // 1c85: dconst_1
      // 1c86: dconst_1
      // 1c87: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1c8a: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1c8d: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1c90: aastore
      // 1c91: dup
      // 1c92: bipush 4
      // 1c93: new net/minecraft/client/animation/Keyframe
      // 1c96: dup
      // 1c97: ldc 1.5
      // 1c99: dconst_1
      // 1c9a: dconst_1
      // 1c9b: dconst_1
      // 1c9c: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1c9f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ca2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ca5: aastore
      // 1ca6: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ca9: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1cac: ldc "right_tentacle1"
      // 1cae: new net/minecraft/client/animation/AnimationChannel
      // 1cb1: dup
      // 1cb2: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1cb5: bipush 5
      // 1cb6: anewarray 28
      // 1cb9: dup
      // 1cba: bipush 0
      // 1cbb: new net/minecraft/client/animation/Keyframe
      // 1cbe: dup
      // 1cbf: fconst_0
      // 1cc0: fconst_0
      // 1cc1: fconst_0
      // 1cc2: fconst_0
      // 1cc3: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cc6: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cc9: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ccc: aastore
      // 1ccd: dup
      // 1cce: bipush 1
      // 1ccf: new net/minecraft/client/animation/Keyframe
      // 1cd2: dup
      // 1cd3: ldc 0.2917
      // 1cd5: ldc -14.5541
      // 1cd7: ldc -6.5298
      // 1cd9: ldc 56.067
      // 1cdb: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cde: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ce1: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ce4: aastore
      // 1ce5: dup
      // 1ce6: bipush 2
      // 1ce7: new net/minecraft/client/animation/Keyframe
      // 1cea: dup
      // 1ceb: ldc 0.4583
      // 1ced: fconst_0
      // 1cee: ldc -42.5
      // 1cf0: fconst_0
      // 1cf1: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1cf4: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1cf7: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1cfa: aastore
      // 1cfb: dup
      // 1cfc: bipush 3
      // 1cfd: new net/minecraft/client/animation/Keyframe
      // 1d00: dup
      // 1d01: ldc 0.75
      // 1d03: ldc -15.0
      // 1d05: ldc -45.0
      // 1d07: ldc 25.0
      // 1d09: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d0c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d0f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d12: aastore
      // 1d13: dup
      // 1d14: bipush 4
      // 1d15: new net/minecraft/client/animation/Keyframe
      // 1d18: dup
      // 1d19: ldc 1.5
      // 1d1b: fconst_0
      // 1d1c: fconst_0
      // 1d1d: fconst_0
      // 1d1e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1d21: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d24: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d27: aastore
      // 1d28: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1d2b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1d2e: ldc "right_tentacle1"
      // 1d30: new net/minecraft/client/animation/AnimationChannel
      // 1d33: dup
      // 1d34: getstatic net/minecraft/client/animation/AnimationChannel$Targets.SCALE Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1d37: bipush 5
      // 1d38: anewarray 28
      // 1d3b: dup
      // 1d3c: bipush 0
      // 1d3d: new net/minecraft/client/animation/Keyframe
      // 1d40: dup
      // 1d41: fconst_0
      // 1d42: dconst_1
      // 1d43: dconst_1
      // 1d44: dconst_1
      // 1d45: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1d48: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d4b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d4e: aastore
      // 1d4f: dup
      // 1d50: bipush 1
      // 1d51: new net/minecraft/client/animation/Keyframe
      // 1d54: dup
      // 1d55: ldc 0.25
      // 1d57: dconst_1
      // 1d58: dconst_1
      // 1d59: dconst_1
      // 1d5a: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1d5d: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d60: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d63: aastore
      // 1d64: dup
      // 1d65: bipush 2
      // 1d66: new net/minecraft/client/animation/Keyframe
      // 1d69: dup
      // 1d6a: ldc 0.375
      // 1d6c: dconst_1
      // 1d6d: ldc2_w 1.600000023841858
      // 1d70: ldc2_w 1.399999976158142
      // 1d73: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1d76: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d79: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d7c: aastore
      // 1d7d: dup
      // 1d7e: bipush 3
      // 1d7f: new net/minecraft/client/animation/Keyframe
      // 1d82: dup
      // 1d83: ldc 0.5
      // 1d85: dconst_1
      // 1d86: dconst_1
      // 1d87: dconst_1
      // 1d88: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1d8b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1d8e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1d91: aastore
      // 1d92: dup
      // 1d93: bipush 4
      // 1d94: new net/minecraft/client/animation/Keyframe
      // 1d97: dup
      // 1d98: ldc 1.5
      // 1d9a: dconst_1
      // 1d9b: dconst_1
      // 1d9c: dconst_1
      // 1d9d: invokestatic net/minecraft/client/animation/KeyframeAnimations.scaleVec (DDD)Lorg/joml/Vector3f;
      // 1da0: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1da3: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1da6: aastore
      // 1da7: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1daa: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1dad: ldc "left_tentacle2"
      // 1daf: new net/minecraft/client/animation/AnimationChannel
      // 1db2: dup
      // 1db3: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1db6: bipush 3
      // 1db7: anewarray 28
      // 1dba: dup
      // 1dbb: bipush 0
      // 1dbc: new net/minecraft/client/animation/Keyframe
      // 1dbf: dup
      // 1dc0: fconst_0
      // 1dc1: fconst_0
      // 1dc2: fconst_0
      // 1dc3: fconst_0
      // 1dc4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1dc7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1dca: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1dcd: aastore
      // 1dce: dup
      // 1dcf: bipush 1
      // 1dd0: new net/minecraft/client/animation/Keyframe
      // 1dd3: dup
      // 1dd4: ldc 0.75
      // 1dd6: fconst_0
      // 1dd7: fconst_0
      // 1dd8: ldc -15.0
      // 1dda: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ddd: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1de0: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1de3: aastore
      // 1de4: dup
      // 1de5: bipush 2
      // 1de6: new net/minecraft/client/animation/Keyframe
      // 1de9: dup
      // 1dea: ldc 1.5
      // 1dec: fconst_0
      // 1ded: fconst_0
      // 1dee: fconst_0
      // 1def: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1df2: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1df5: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1df8: aastore
      // 1df9: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1dfc: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1dff: ldc "right_tentacle2"
      // 1e01: new net/minecraft/client/animation/AnimationChannel
      // 1e04: dup
      // 1e05: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1e08: bipush 3
      // 1e09: anewarray 28
      // 1e0c: dup
      // 1e0d: bipush 0
      // 1e0e: new net/minecraft/client/animation/Keyframe
      // 1e11: dup
      // 1e12: fconst_0
      // 1e13: fconst_0
      // 1e14: fconst_0
      // 1e15: fconst_0
      // 1e16: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e19: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e1c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e1f: aastore
      // 1e20: dup
      // 1e21: bipush 1
      // 1e22: new net/minecraft/client/animation/Keyframe
      // 1e25: dup
      // 1e26: ldc 0.9167
      // 1e28: fconst_0
      // 1e29: fconst_0
      // 1e2a: ldc 32.5
      // 1e2c: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e2f: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e32: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e35: aastore
      // 1e36: dup
      // 1e37: bipush 2
      // 1e38: new net/minecraft/client/animation/Keyframe
      // 1e3b: dup
      // 1e3c: ldc 1.5
      // 1e3e: fconst_0
      // 1e3f: fconst_0
      // 1e40: fconst_0
      // 1e41: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e44: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e47: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e4a: aastore
      // 1e4b: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1e4e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1e51: ldc "left_tentacle3"
      // 1e53: new net/minecraft/client/animation/AnimationChannel
      // 1e56: dup
      // 1e57: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1e5a: bipush 3
      // 1e5b: anewarray 28
      // 1e5e: dup
      // 1e5f: bipush 0
      // 1e60: new net/minecraft/client/animation/Keyframe
      // 1e63: dup
      // 1e64: fconst_0
      // 1e65: fconst_0
      // 1e66: fconst_0
      // 1e67: fconst_0
      // 1e68: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e6b: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e6e: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e71: aastore
      // 1e72: dup
      // 1e73: bipush 1
      // 1e74: new net/minecraft/client/animation/Keyframe
      // 1e77: dup
      // 1e78: ldc 0.625
      // 1e7a: ldc 15.0
      // 1e7c: ldc -40.0
      // 1e7e: ldc -25.0
      // 1e80: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e83: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e86: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e89: aastore
      // 1e8a: dup
      // 1e8b: bipush 2
      // 1e8c: new net/minecraft/client/animation/Keyframe
      // 1e8f: dup
      // 1e90: ldc 1.5
      // 1e92: fconst_0
      // 1e93: fconst_0
      // 1e94: fconst_0
      // 1e95: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1e98: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1e9b: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1e9e: aastore
      // 1e9f: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ea2: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ea5: ldc "right_tentacle3"
      // 1ea7: new net/minecraft/client/animation/AnimationChannel
      // 1eaa: dup
      // 1eab: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1eae: bipush 3
      // 1eaf: anewarray 28
      // 1eb2: dup
      // 1eb3: bipush 0
      // 1eb4: new net/minecraft/client/animation/Keyframe
      // 1eb7: dup
      // 1eb8: fconst_0
      // 1eb9: fconst_0
      // 1eba: fconst_0
      // 1ebb: fconst_0
      // 1ebc: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ebf: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1ec2: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ec5: aastore
      // 1ec6: dup
      // 1ec7: bipush 1
      // 1ec8: new net/minecraft/client/animation/Keyframe
      // 1ecb: dup
      // 1ecc: ldc 0.75
      // 1ece: ldc 15.0
      // 1ed0: ldc 40.0
      // 1ed2: ldc 25.0
      // 1ed4: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1ed7: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eda: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1edd: aastore
      // 1ede: dup
      // 1edf: bipush 2
      // 1ee0: new net/minecraft/client/animation/Keyframe
      // 1ee3: dup
      // 1ee4: ldc 1.5
      // 1ee6: fconst_0
      // 1ee7: fconst_0
      // 1ee8: fconst_0
      // 1ee9: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1eec: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1eef: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1ef2: aastore
      // 1ef3: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1ef6: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1ef9: ldc "back_tentacle2"
      // 1efb: new net/minecraft/client/animation/AnimationChannel
      // 1efe: dup
      // 1eff: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f02: bipush 3
      // 1f03: anewarray 28
      // 1f06: dup
      // 1f07: bipush 0
      // 1f08: new net/minecraft/client/animation/Keyframe
      // 1f0b: dup
      // 1f0c: fconst_0
      // 1f0d: fconst_0
      // 1f0e: fconst_0
      // 1f0f: fconst_0
      // 1f10: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f13: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f16: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f19: aastore
      // 1f1a: dup
      // 1f1b: bipush 1
      // 1f1c: new net/minecraft/client/animation/Keyframe
      // 1f1f: dup
      // 1f20: ldc 0.6667
      // 1f22: ldc 15.0
      // 1f24: fconst_0
      // 1f25: fconst_0
      // 1f26: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f29: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f2c: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f2f: aastore
      // 1f30: dup
      // 1f31: bipush 2
      // 1f32: new net/minecraft/client/animation/Keyframe
      // 1f35: dup
      // 1f36: ldc 1.5
      // 1f38: fconst_0
      // 1f39: fconst_0
      // 1f3a: fconst_0
      // 1f3b: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f3e: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f41: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f44: aastore
      // 1f45: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1f48: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1f4b: ldc "back_tentacle3"
      // 1f4d: new net/minecraft/client/animation/AnimationChannel
      // 1f50: dup
      // 1f51: getstatic net/minecraft/client/animation/AnimationChannel$Targets.ROTATION Lnet/minecraft/client/animation/AnimationChannel$Target;
      // 1f54: bipush 3
      // 1f55: anewarray 28
      // 1f58: dup
      // 1f59: bipush 0
      // 1f5a: new net/minecraft/client/animation/Keyframe
      // 1f5d: dup
      // 1f5e: fconst_0
      // 1f5f: fconst_0
      // 1f60: fconst_0
      // 1f61: fconst_0
      // 1f62: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f65: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f68: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f6b: aastore
      // 1f6c: dup
      // 1f6d: bipush 1
      // 1f6e: new net/minecraft/client/animation/Keyframe
      // 1f71: dup
      // 1f72: ldc 0.875
      // 1f74: ldc 20.0
      // 1f76: ldc -30.0
      // 1f78: fconst_0
      // 1f79: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f7c: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f7f: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f82: aastore
      // 1f83: dup
      // 1f84: bipush 2
      // 1f85: new net/minecraft/client/animation/Keyframe
      // 1f88: dup
      // 1f89: ldc 1.5
      // 1f8b: fconst_0
      // 1f8c: fconst_0
      // 1f8d: fconst_0
      // 1f8e: invokestatic net/minecraft/client/animation/KeyframeAnimations.degreeVec (FFF)Lorg/joml/Vector3f;
      // 1f91: getstatic net/minecraft/client/animation/AnimationChannel$Interpolations.CATMULLROM Lnet/minecraft/client/animation/AnimationChannel$Interpolation;
      // 1f94: invokespecial net/minecraft/client/animation/Keyframe.<init> (FLorg/joml/Vector3f;Lnet/minecraft/client/animation/AnimationChannel$Interpolation;)V
      // 1f97: aastore
      // 1f98: invokespecial net/minecraft/client/animation/AnimationChannel.<init> (Lnet/minecraft/client/animation/AnimationChannel$Target;[Lnet/minecraft/client/animation/Keyframe;)V
      // 1f9b: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.addAnimation (Ljava/lang/String;Lnet/minecraft/client/animation/AnimationChannel;)Lnet/minecraft/client/animation/AnimationDefinition$Builder;
      // 1f9e: invokevirtual net/minecraft/client/animation/AnimationDefinition$Builder.build ()Lnet/minecraft/client/animation/AnimationDefinition;
      // 1fa1: putstatic com/github/L_Ender/cataclysm/client/animation/Symbiocto_Animation.ATTACK Lnet/minecraft/client/animation/AnimationDefinition;
      // 1fa4: return
   }
}
