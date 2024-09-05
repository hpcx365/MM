public class Vector2 {
    public double x, y;
    
    public Vector2(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    // 点积
    public double dot(Vector2 other) {
        return this.x * other.x + this.y * other.y;
    }
    
    // 叉积
    public double cross(Vector2 other) {
        return this.x * other.y - this.y * other.x;
    }
    
    // 向量长度
    public double length() {
        return (double) Math.sqrt(this.x * this.x + this.y * this.y);
    }
    
    // 向量归一化
    public Vector2 normalize() {
        double len = length();
        return new Vector2(this.x / len, this.y / len);
    }
}
