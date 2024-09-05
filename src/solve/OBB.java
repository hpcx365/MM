package solve;

public class OBB {
    
    public Vector center; // 中心点
    public double width, height; // 宽度和高度
    public double angle; // 旋转角度
    public Vector[] points; // 顶点，按逆时针方向给出
    
    public OBB(Vector center, double width, double height, double angle) {
        this.center = center;
        this.width = width;
        this.height = height;
        this.angle = angle;
        double hw = 0.5 * width;
        double hh = 0.5 * height;
        this.points = new Vector[] {
                new Vector(center.x - hw, center.y - hh),
                new Vector(center.x + hw, center.y - hh),
                new Vector(center.x + hw, center.y + hh),
                new Vector(center.x - hw, center.y + hh),
        };
    }
}
