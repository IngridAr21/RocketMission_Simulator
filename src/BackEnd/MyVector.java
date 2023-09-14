package BackEnd;

import java.math.BigDecimal;

public class MyVector {

  /** The x component of the vector. */
  public double x;

  /** The y component of the vector. */
  public double y;

  /** The z component of the vector. */
  public double z;

  /** Array so that this.copy() can be temporarily used in an array context */
  protected double[] array;

  /**
   * Constructor for an empty vector: x, y, and z are set to 0.
   */
  BigDecimal x2, y2, z2;

  public MyVector() {
  }

  public MyVector(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public MyVector(double x, double y) {
    this.x = x;
    this.y = y;
    this.z = 0;
  }

  public MyVector(double[] array) {

    this.x = array[0];
    this.y = array[1];
    this.z = array[2];

  }

  public MyVector(MyVector vector) {
    this.x = vector.x;
    this.y = vector.y;
    this.z = vector.z;
  }

  // public MyVector(BigDecimal x2, BigDecimal y2, BigDecimal z2) {

  // this.x2 = x2;
  // this.y2 = y2;
  // this.z2 = z2;
  // }

  public void set(double x, double y, double z) {
    this.x = x;
    this.y = y;
    this.z = z;
  }

  public void set(MyVector v) {
    x = v.x;
    y = v.y;
    z = v.z;
  }

  /**
   * Get a copy of this vector.
   */
  public MyVector get() {
    return new MyVector(x, y, z);
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public double getZ() {
    return z;
  }

  /**
   * Calculate the magnitude (length) of the vector
   * 
   * the magnitude of the vector
   */
  public double mag() {
    return (double) Math.sqrt(x * x + y * y + z * z);
  }

  /**
   * Add a vector to this vector
   * 
   * the vector to be added
   */
  public void add(MyVector v) {

    x += v.x;
    y += v.y;
    z += v.z;

  }

  public void add(double x, double y, double z) {
    this.x += x;
    this.y += y;
    this.z += z;

  }

  /**
   * Add two vectors
   * 
   * return a new vector that is the sum of v1 and v2
   */
  public static MyVector add(MyVector v1, MyVector v2) {
    return new MyVector(v1.x + v2.x, v1.y + v2.y, v1.z + v2.z);
  }

  public static MyVector add(MyVector v1, MyVector v2, MyVector v3, MyVector v4) {
    return new MyVector(v1.x + v2.x + v3.x + v4.x, v1.y + v2.y + v3.y + v4.y, v1.z + v2.z + v3.z + v4.z);
  }

  /**
   * Subtract a vector from this vector
   * 
   * v the vector to be subtracted
   */
  public void sub(MyVector v) {
    x -= v.x;
    y -= v.y;
    z -= v.z;

  }

  public void sub(double x, double y, double z) {
    this.x -= x;
    this.y -= y;
    this.z -= z;

  }

  public static MyVector absError(MyVector ex, MyVector act) {
    double x = Math.abs((ex.x - act.x));
    double y = Math.abs((ex.y - act.y));
    double z = Math.abs((ex.z - act.z));
    return new MyVector(x, y, z);
  }

  /**
   * Subtract one vector from another
   * 
   * return a new vector that is v1 - v2
   */
  public static MyVector sub(MyVector v1, MyVector v2) {
    return new MyVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
  }

  public static MyVector sub(MyVector v1, MyVector v2, MyVector target) {
    if (target == null) {
      target = new MyVector(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    } else {
      // target.set(v1.x - v2.x, v1.y - v2.y, v1.z - v2.z);
    }
    return target;
  }

  /**
   * Multiply this vector by a scalar
   * 
   */
  public void mult(double n) {
    x *= n;
    y *= n;
    z *= n;

  }

  /**
   * Multiply a vector by a scalar
   * 
   * return a new vector that is v1 * n
   */
  static public MyVector mult(MyVector v, double n) {
    return new MyVector(v.x * n, v.y * n, v.z * n);
  }

  /**
   * Multiply this vector with v.
   * 
   */
  public void mult(MyVector v) {
    x *= v.x;
    y *= v.y;
    z *= v.z;

  }

  static public MyVector mult(MyVector v1, MyVector v2) {
    return new MyVector(v1.x * v2.x, v1.y * v2.y, v1.z * v2.z);
  }

  /**
   * Divide this vector by a scalar
   */
  public void div(double n) {
    x /= n;
    y /= n;
    z /= n;

  }

  public static MyVector div1(MyVector v, double n) {
    v.x /= n;
    v.y /= n;
    v.z /= n;
    return v;
  }

  /**
   * Divide each element of one vector by the elements of another vector.
   */
  public void div(MyVector v) {
    x = (Math.abs(x / v.x) <= 1E-319) ? 0 : (x /= v.x);
    y = (Math.abs(y / v.y) <= 1E-319) ? 0 : (y /= v.y);
    z = (Math.abs(z / v.z) <= 1E-319) ? 0 : (z /= v.z);

  }

  /**
   * Calculate the Euclidean distance between two points (considering a point as a
   * vector object)
   * the Euclidean distance between
   */
  public double dist(MyVector v) {
    double dx = x - v.x;
    double dy = y - v.y;
    double dz = z - v.z;
    return (double) Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  /**
   * Calculate the Euclidean distance between two points (considering a point as a
   * vector object)
   * return the Euclidean distance between v1 and v2
   */
  static public double dist(MyVector v1, MyVector v2) {
    double dx = v1.x - v2.x;
    double dy = v1.y - v2.y;
    double dz = v1.z - v2.z;
    return (double) Math.sqrt(dx * dx + dy * dy + dz * dz);
  }

  /**
   * Calculate the dot product with another vector
   * the dot product
   */
  public double dot(MyVector v) {
    return x * v.x + y * v.y + z * v.z;
  }

  public double dot(double x, double y, double z) {
    return this.x * x + this.y * y + this.z * z;
  }

  static public double dot(MyVector v1, MyVector v2) {
    return v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
  }

  /**
   * Return a vector composed of the cross product between this and another.
   */
  public MyVector cross(MyVector v) {
    double crossX = y * v.z - v.y * z;
    double crossY = z * v.x - v.z * x;
    double crossZ = x * v.y - v.x * y;

    return new MyVector(crossX, crossY, crossZ);
  }

  /**
   * Normalize the vector to length 1 (make it a unit vector)
   */
  public void normalize() {
    double m = mag();
    if (m != 0 && m != 1) {
      div(m);
    }

  }

  /**
   * Normalize this vector, storing the result in another vector.
   * return a new vector (if target was null), or target
   */
  public MyVector normalize(MyVector target) {
    if (target == null) {
      target = new MyVector();
    }
    double m = mag();
    if (m > 0) {
      // target.set(x / m, y / m, z / m);
    } else {
      // target.set(x, y, z);
    }
    return target;
  }

  /**
   * Limit the magnitude of this vector
   */
  public void limit(double max) {
    if (mag() > max) {
      normalize();
      mult(max);
    }

  }

  public static MyVector[] add(MyVector[] a, MyVector[] b) {
    MyVector[] result = new MyVector[a.length];
    for (int i = 0; i < a.length; i++) {
      a[i].add(b[i]);
      result[i] = a[i];
    }
    return result;
  }

  public void abs() {
    this.x = Math.abs(this.x);
    this.y = Math.abs(this.y);
    this.z = Math.abs(this.z);

  }

  public static MyVector abs(MyVector v) {
    return new MyVector(Math.abs(v.x), Math.abs(v.y), Math.abs(v.z));
  }

  public String toString() {
    return "[ " + x + ", " + y + ", " + z + " ]";
  }

  public void cos() {
    this.x = Math.cos(this.x);
    this.y = Math.cos(this.y);
    this.z = Math.cos(this.z);

  }

  public static MyVector cos(MyVector v) {
    return new MyVector(Math.cos(v.x), Math.cos(v.y), Math.cos(v.z));
  }

  public void log() {
    this.x = Math.log(this.x);
    this.y = Math.log(this.y);
    this.z = Math.log(this.z);

  }

  public static MyVector log(MyVector v) {
    return new MyVector(Math.log(v.x), Math.log(v.y), Math.log(v.z));
  }

  public void sin() {
    this.x = Math.sin(this.x);
    this.y = Math.sin(this.y);
    this.z = Math.sin(this.z);

  }

  public static MyVector sin(MyVector v) {
    return new MyVector(Math.sin(v.x), Math.sin(v.y), Math.sin(v.z));
  }

  public void tan() {
    this.x = Math.tan(this.x);
    this.y = Math.tan(this.y);
    this.z = Math.tan(this.z);

  }

  public static MyVector tan(MyVector v) {
    return new MyVector(Math.tan(v.x), Math.tan(v.y), Math.tan(v.z));
  }

  public void pow(MyVector v) {
    this.x = Math.pow(v.x, this.x);
    this.y = Math.pow(v.y, this.y);
    this.z = Math.pow(v.z, this.z);

  }

  public static MyVector pow(MyVector v, MyVector a) {
    return new MyVector(Math.pow(v.x, a.x), Math.pow(v.y, a.y),
        Math.pow(v.z, a.z));
  }

  public static MyVector div(MyVector v, MyVector a) {
    double x = (Math.abs(v.x / a.x) <= 1E-319) ? 0 : (v.x / a.x);
    double y = (Math.abs(v.y / a.y) <= 1E-319) ? 0 : (v.y / a.y);
    double z = (Math.abs(v.z / a.z) <= 1E-319) ? 0 : (v.z / a.z);
    return new MyVector(x, y, z);
  }

  public MyVector copy() {
    MyVector cloned = new MyVector(x, y, z);
    return cloned;
  }

  public double angle(MyVector v) {

    return dot(v) / (mag() * v.mag());

  }
  
  //rocket stuff
  public MyVector rotateZ(double angle) {
    double radAngle = Math.toRadians(angle);
    double newX = x * Math.cos(radAngle) - y * Math.sin(radAngle);
    double newY = x * Math.sin(radAngle) + y * Math.cos(radAngle);
    return new MyVector(newX, newY, z);
  }

  public MyVector addR(MyVector other) {
    return new MyVector(x + other.x, y + other.y, z + other.z);
  }

  public MyVector subt(MyVector other) {
    return new MyVector(x - other.x, y - other.y, z - other.z);
  }

  public MyVector norm() {
    double magnitude = Math.sqrt(x * x + y * y + z * z);
    return new MyVector(x / magnitude, y / magnitude, z / magnitude);
  }
  
  public MyVector multr(double n) {
    x *= n;
    y *= n;
    z *= n;
    return new MyVector(x, y, z);
  }

  public MyVector rotate(double angleDegrees) {
    double angleRadians = Math.toRadians(angleDegrees);
    double cosTheta = Math.cos(angleRadians);
    double sinTheta = Math.sin(angleRadians);

    double newX = x * cosTheta - y * sinTheta;
    double newY = x * sinTheta + y * cosTheta;

    return new MyVector(newX, newY);
}

}
