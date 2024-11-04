package math;

import client.game.Block;
import client.game.BlockFace;
import client.game.InteractionManager;

public class MutableVec3i extends Vec3i {
    public MutableVec3i(int x, int y, int z) {
        super(x, y, z);
    }

    public MutableVec3i(Vec3i vec3i) {
        super(vec3i);
    }

    public MutableVec3i(MutableVec3i mutableVec3i) {
        super(mutableVec3i.toVec3i());
    }

    public MutableVec3i addY(int offset) {
        y += offset;
        return this;
    }
    public Vec3i addX(int offset) {
        x += offset;
        return this;
    }
    public Vec3i addZ(int offset) {
        z += offset;
        return this;
    }

    public Vec3i withX(int x) {
        this.x = x;
        return this;
    }
    public Vec3i withY(int y) {
        this.y = y;
        return this;
    }
    public Vec3i withZ(int z) {
       this.z = z;
       return this;
    }



    public Vec3i toVec3i() {
        return new Vec3i(this.getX(), this.getY(), this.getZ());
    }


}
