package mysticmods.embers.particles.behaviour;

import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Camera;
import net.minecraft.world.phys.Vec3;
import team.lodestar.lodestone.systems.particle.world.LodestoneWorldParticle;
import team.lodestar.lodestone.systems.particle.world.behaviors.LodestoneParticleBehavior;

public class MoveToPointBehaviour implements LodestoneParticleBehavior {

    private Vec3 endpoint;
    private final double SPEED = 0.1;

    public MoveToPointBehaviour(){

    }

    public MoveToPointBehaviour setPoint(Vec3 endpoint){
        this.endpoint = endpoint;
        return this;
    }

    @Override
    public void tick(LodestoneWorldParticle particle) {
        if (endpoint == null) return;

        // Target is center of the block
        double targetX = endpoint.x;
        double targetY = endpoint.y;
        double targetZ = endpoint.z;

        // Direction vector
        double dx = targetX - particle.getX();
        double dy = targetY - particle.getY();
        double dz = targetZ - particle.getZ();

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);

        // Speed per tick
        final double SPEED = 0.1;

        if (distance <= SPEED) {
            // Snap to endpoint and stop moving
            particle.setPos(targetX, targetY, targetZ);
            return;
        }

        // Normalize direction and scale by speed
        double moveX = dx / distance * SPEED;
        double moveY = dy / distance * SPEED;
        double moveZ = dz / distance * SPEED;

        // Move particle manually
        particle.setParticleSpeed(moveX, moveY, moveZ);
    }

    @Override
    public void render(LodestoneWorldParticle particle, VertexConsumer consumer, Camera camera, float partialTicks) {

    }
}
