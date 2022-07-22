package com.mystic.embers.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import noobanidus.libs.particleslib.client.particle.Particles;
import noobanidus.libs.particleslib.init.ModParticles;

public class ParticleUtil {

    public static void spawnSpark(Level world, BlockPos loc) {
        for (int i = 0; i < 10; i++) {
            double xLoc = loc.getX() + 0.5;
            double yLoc = loc.getY() + 1.0;
            double zLoc = loc.getZ() + .5;
            Particles.create(ModParticles.GLOW_PARTICLE.get())
                    .addVelocity((world.random.nextFloat() * 1 - 0.5) / 5, (world.random.nextFloat() * 1 - 0.5) / 5, (world.random.nextFloat() * 1 - 0.5) / 5)
                    .setAlpha(0.5f, 0.2f)
                    .setScale(world.random.nextFloat())
                    .setColor(230 / 255.0f, 55 / 255.0f, 16 / 255.0f, 230 / 255.0f, 83 / 255.0f, 16 / 255.0f)
                    .setLifetime(80)
                    .disableGravity()
                    .setSpin(0)
                    .spawn(world, xLoc, yLoc, zLoc);
        }
    }

}
