#include <iostream>
#include <cstring>
#include <cmath>
#include <fstream>
#include <iomanip>
double const pi=3.14159265358979323846;
double const d=0.55;
double const tolerance=1e-9;
double len[300];

struct point{
    double x;
    double y;
    double theta;
}p[305],res[305][305];
struct velocity{
    double theta;
    double size;
}v[305],res2[305][305];
// 计算两点之间的直线角度
double cal_LineAngle(double x1, double y1, double x2, double y2) {
    // 计算斜率
    double dx = x2 - x1;
    double dy = y2 - y1;
    
    // 防止除以零的情况
    if (dx == 0) {
        if (dy > 0) {
            return 90.0;
        } else if (dy < 0) {
            return -90.0;
        } else {
            return 0.0;
        }
    }
    
    double slope = dy / dx;
    double radians = atan(slope);
    
    return radians;
}
void cal_xy(int num){ //求一个点的xy坐标
    double r=d/(2*pi)*p[num].theta;
    p[num].x=r*cos(p[num].theta);
    p[num].y=r*sin(p[num].theta);
}
double cal_Chord(double x,double y){
    
    if(x>y){
        return d/(2*pi)*sqrt(y*y + x*x - 2*y*x*cos(x-y));
    }
    else{
        return -1 * d/(2*pi)*sqrt(y*y + x*x - 2*y*x*cos(x-y));
    }
}

double d_cal_Chord(double x,double y) { // 根据角度求弦长函数的导数 关于x
    if(x>y){
        return d/(4*pi) * (1/sqrt(y*y + x*x - 2*y*x*cos(x-y))) * (2*x - 2*y*cos(x-y) + 2*x*y*sin(x-y) );
    }
    else{
        return -1 * d/(4*pi) * (1/sqrt(y*y + x*x - 2*y*x*cos(x-y))) * (2*x - 2*y*cos(x-y) + 2*x*y*sin(x-y) );
    }
}

double f(double x,int num) {
    return cal_Chord(x,p[num-1].theta) - len[num]; // 根据角度求弦长
}
double df(double x,int num) {
    return d_cal_Chord(x,p[num-1].theta);
}
double newtonMethod(double initial_guess, double tolerance, int max_iterations,int num) {
    double x = initial_guess;
    int iteration = 0;
   
    while (iteration < max_iterations) {
        double fx = f(x,num);
        double dfx = df(x,num);
      
        if (dfx == 0) {
            std::cerr << "Derivative is zero at x = " << x << std::endl;
            return x;
        }

        double next_x = x - fx / dfx;

        if (std::abs(next_x - x) < tolerance) {
            return next_x;
        }

        x = next_x;
        iteration++;
    }

    std::cerr << "Maximum number of iterations reached." << std::endl;
    return x;
}
int main(){
    //init
    len[1]=2.86;
    for(int i=2;i<=223;i++){
        len[i]=1.65;
    }
    // 打开文件
    std::ofstream outputFile("points.csv");
    std::ofstream outputFile2("velocity.csv");

    // 设置输出精度
    outputFile << std::fixed << std::setprecision(6);
    outputFile2 << std::fixed << std::setprecision(6);
    if (!outputFile.is_open()) {
        std::cerr << "无法打开文件 points.csv" << std::endl;
        return 1;
    }
    if (!outputFile2.is_open()) {
        std::cerr << "无法打开文件 velocity.csv" << std::endl;
        return 1;
    }
    //cal
    for(int t=0;t<=0;t++){
        memset(p,0,sizeof p);
        memset(v,0,sizeof v);
        p[0].theta=sqrt( ( d/(4*pi) * (32*pi)*(32*pi) - t ) *4*pi/d );
        v[0].size=1;
        v[0].theta=p[0].theta+pi/2-atan(1/p[0].theta);
        
        cal_xy(0);
        for(int i=1;i<=223;i++){
            double initial_guess = p[i-1].theta+len[i]/( d/(2*pi)*p[i-1].theta ) * 1.1 ;
            int max_iterations = 10000;
            double root = newtonMethod(initial_guess, tolerance, max_iterations,i);
            p[i].theta=root;
            cal_xy(i);
            double slop=cal_LineAngle(p[i].x,p[i].y,p[i-1].x,p[i-1].y);
            v[i].theta=p[i].theta+pi/2-atan(1/p[i].theta);
            v[i].size=v[i-1].size*cos(v[i-1].theta-slop)/cos(v[i].theta-slop);
        }
        
        for(int i=0;i<=223;i++){
            res[t][i].x=p[i].x;
            res[t][i].y=p[i].y;
            res2[t][i].size=v[i].size;
        }
    }
    for (int i = 0; i <= 223; i++)
    {   
        if(i%50!=1&&i!=0&&i!=223) continue;
        for(int t=0;t<=300;t++)
        {
            if(t%60!=0) continue;
            outputFile <<res[t][i].x<<",";
            outputFile2 <<res2[t][i].size<<",";
        }
        outputFile << std::endl;
        outputFile2 << std::endl;
        for(int t=0;t<=300;t++)
        {
            if(t%60!=0) continue;
            outputFile <<res[t][i].y<<",";
        }
        outputFile << std::endl;
    }
    // 关闭文件
    outputFile.close();
    outputFile2.close();

    std::cout << "点信息已写入文件 points.csv" << std::endl;
    std::cout << "速度信息已写入文件 velocity.csv" << std::endl;
    return 0;
}
