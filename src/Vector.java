public class Vector {
    public double x, y;
    
    public Vector(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    // 点积
    public double dot(Vector other) {
        return this.x * other.x + this.y * other.y;
    }
    
    // 叉积
    public double cross(Vector other) {
        return this.x * other.y - this.y * other.x;
    }
    
    // 向量长度
    public double length() {
        return (double) Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    // 向量归一化
    public Vector normalize() {
        double len = length();
        return new Vector(this.x / len, this.y / len);
    }
}
