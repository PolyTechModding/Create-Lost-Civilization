package com.polytechmodding.createlostcivilization.mixins;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {
  protected PlayerMixin(EntityType<? extends LivingEntity> entityType,
                        Level level) {
    super(entityType, level);
  }

  @Inject(
      method = "travel(Lnet/minecraft/world/phys/Vec3;)V",
      at = @At(
          value = "INVOKE",
          target =
              "Lnet/minecraft/world/entity/player/Player;getLookAngle()Lnet/minecraft/world/phys/Vec3;")
      )
  public void
  inject(Vec3 vec3, CallbackInfo ci) {
    double d = this.getLookAngle().y;
    double e = d < -0.2 ? 0.085 : 0.06;
    if (this.level()
            .getBlockState(BlockPos.containing(
                this.getX(), this.getY() + 1.0 - 0.1, this.getZ()))
            .is(Blocks.AIR)) {
      Vec3 vec32 = this.getDeltaMovement();
      this.setDeltaMovement(vec32.add(0.0, (d - vec32.y) * e, 0.0));
    }
  }
}
