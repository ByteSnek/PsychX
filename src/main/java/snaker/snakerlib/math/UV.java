package snaker.snakerlib.math;

import org.joml.Matrix2d;
import org.joml.Matrix2f;
import org.joml.Vector2d;
import org.joml.Vector2f;

/**
 * Created by SnakerBone on 22/06/2023
 **/
public class UV
{
    public double u;
    public double v;

    public UV(double u, double v)
    {
        this.u = u;
        this.v = v;
    }

    public UV()
    {
        this.u = 0;
        this.v = 0;
    }

    public UV(UV uv)
    {
        this.u = uv.u;
        this.v = uv.v;
    }

    public UV(double[] uv)
    {
        this.u = uv[0];
        this.v = uv[1];
    }

    public UV set(double u, double v)
    {
        this.u = u;
        this.v = v;
        return this;
    }

    public UV set(UV uv)
    {
        this.u = uv.u;
        this.v = uv.v;
        return this;
    }

    public UV set(double[] uv)
    {
        this.u = uv[0];
        this.v = uv[1];
        return this;
    }

    public UV add(double u, double v)
    {
        this.u += u;
        this.v += v;
        return this;
    }

    public UV add(UV uv)
    {
        this.u += uv.u;
        this.v += uv.v;
        return this;
    }

    public UV add(double[] uv)
    {
        this.u += uv[0];
        this.v += uv[1];
        return this;
    }

    public UV sub(double u, double v)
    {
        this.u -= u;
        this.v -= v;
        return this;
    }

    public UV sub(UV uv)
    {
        this.u -= uv.u;
        this.v -= uv.v;
        return this;
    }

    public UV sub(double[] uv)
    {
        this.u -= uv[0];
        this.v -= uv[1];
        return this;
    }

    public UV mul(double u, double v)
    {
        this.u *= u;
        this.v *= v;
        return this;
    }

    public UV mul(UV uv)
    {
        this.u *= uv.u;
        this.v *= uv.v;
        return this;
    }

    public UV mul(double[] uv)
    {
        this.u *= uv[0];
        this.v *= uv[1];
        return this;
    }

    public UV div(double u, double v)
    {
        this.u /= u;
        this.v /= v;
        return this;
    }

    public UV div(UV uv)
    {
        this.u /= uv.u;
        this.v /= uv.v;
        return this;
    }

    public UV div(double[] uv)
    {
        this.u /= uv[0];
        this.v /= uv[1];
        return this;
    }

    public UV scale(double scale)
    {
        this.u *= scale;
        this.v *= scale;
        return this;
    }

    public UV scale(double scaleU, double scaleV)
    {
        this.u *= scaleU;
        this.v *= scaleV;
        return this;
    }

    public UV scale(UV scale)
    {
        this.u *= scale.u;
        this.v *= scale.v;
        return this;
    }

    public UV scale(double[] scale)
    {
        this.u *= scale[0];
        this.v *= scale[1];
        return this;
    }

    public UV scale(double scale, double scaleU, double scaleV)
    {
        this.u *= scale * scaleU;
        this.v *= scale * scaleV;
        return this;
    }

    public UV scale(double scale, UV scaleUV)
    {
        this.u *= scale * scaleUV.u;
        this.v *= scale * scaleUV.v;
        return this;
    }

    public UV scale(double scale, double[] scaleUV)
    {
        this.u *= scale * scaleUV[0];
        this.v *= scale * scaleUV[1];
        return this;
    }

    public UV scale(UV scale, double scaleU, double scaleV)
    {
        this.u *= scale.u * scaleU;
        this.v *= scale.v * scaleV;
        return this;
    }

    public UV scale(UV scale, UV scaleUV)
    {
        this.u *= scale.u * scaleUV.u;
        this.v *= scale.v * scaleUV.v;
        return this;
    }

    public UV scale(UV scale, double[] scaleUV)
    {
        this.u *= scale.u * scaleUV[0];
        this.v *= scale.v * scaleUV[1];
        return this;
    }

    public UV scale(double[] scale, double scaleU, double scaleV)
    {
        this.u *= scale[0] * scaleU;
        this.v *= scale[1] * scaleV;
        return this;
    }

    public UV scale(double[] scale, UV scaleUV)
    {
        this.u *= scale[0] * scaleUV.u;
        this.v *= scale[1] * scaleUV.v;
        return this;
    }

    public UV scale(double[] scale, double[] scaleUV)
    {
        this.u *= scale[0] * scaleUV[0];
        this.v *= scale[1] * scaleUV[1];
        return this;
    }

    public UV scale(double scale, double scaleU, double scaleV, double scaleUV)
    {
        this.u *= scale * scaleU * scaleUV;
        this.v *= scale * scaleV * scaleUV;
        return this;
    }

    public UV scale(double scale, double scaleU, double scaleV, double[] scaleUV)
    {
        this.u *= scale * scaleU * scaleUV[0];
        this.v *= scale * scaleV * scaleUV[1];
        return this;
    }

    public UV scale(double scale, double scaleU, UV scaleUV)
    {
        this.u *= scale * scaleU * scaleUV.u;
        this.v *= scale * scaleU * scaleUV.v;
        return this;
    }

    public UV scale(double scale, double scaleU, double[] scaleUV)
    {
        this.u *= scale * scaleU * scaleUV[0];
        this.v *= scale * scaleU * scaleUV[1];
        return this;
    }

    public UV scale(double scale, UV scaleU, double scaleV)
    {
        this.u *= scale * scaleU.u * scaleV;
        this.v *= scale * scaleU.v * scaleV;
        return this;
    }

    public UV scale(double scale, UV scaleU, double[] scaleV)
    {
        this.u *= scale * scaleU.u * scaleV[0];
        this.v *= scale * scaleU.v * scaleV[1];
        return this;
    }

    public UV scale(double scale, UV scaleU, UV scaleV)
    {
        this.u *= scale * scaleU.u * scaleV.u;
        this.v *= scale * scaleU.v * scaleV.v;
        return this;
    }

    public UV scale(double scale, double[] scaleU, double scaleV)
    {
        this.u *= scale * scaleU[0] * scaleV;
        this.v *= scale * scaleU[1] * scaleV;
        return this;
    }

    public UV scale(double scale, double[] scaleU, double[] scaleV)
    {
        this.u *= scale * scaleU[0] * scaleV[0];
        this.v *= scale * scaleU[1] * scaleV[1];
        return this;
    }

    public UV scale(double scale, double[] scaleU, UV scaleV)
    {
        this.u *= scale * scaleU[0] * scaleV.u;
        this.v *= scale * scaleU[1] * scaleV.v;
        return this;
    }

    public UV copy()
    {
        return new UV(this);
    }

    public Vector2f v2f()
    {
        return new Vector2f((float) u, (float) v);
    }

    public Vector2d v2d()
    {
        return new Vector2d(u, v);
    }

    public Matrix2f m2f()
    {
        return new Matrix2f((float) u, (float) v, (float) v, (float) u);
    }

    public Matrix2d m2d()
    {
        return new Matrix2d(u, v, v, u);
    }

    public double magnitude()
    {
        return Maths.sqrt(u * u + v * v);
    }

    public double magnitudeSquared()
    {
        return u * u + v * v;
    }

    public UV negate()
    {
        this.u = -u;
        this.v = -v;
        return this;
    }

    public UV normalize()
    {
        double mag = magnitude();
        this.u /= mag;
        this.v /= mag;
        return this;
    }

    public UV normalize(double mag)
    {
        this.u /= mag;
        this.v /= mag;
        return this;
    }

    public double dot(UV uv)
    {
        return u * uv.u + v * uv.v;
    }

    public double scalarProjection(UV uv)
    {
        return dot(uv) / magnitude();
    }

    public boolean isZero()
    {
        return u == 0 && v == 0;
    }

    @Override
    public boolean equals(Object object)
    {
        if (super.equals(object)) {
            return true;
        }

        if (!(object instanceof UV uv)) {
            return false;
        }

        return u == uv.u && v == uv.v;
    }

    @Override
    public String toString()
    {
        return "u: " + u + ", v: " + v;
    }
}
