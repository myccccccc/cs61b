public class Body {
    public double xxPos, yyPos, xxVel, yyVel, mass;
    public String imgFileName;
    private static double G = 6.67 * 1e-11;

    public Body(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Body(Body body) {
        this.xxPos = body.xxPos;
        this.yyPos = body.yyPos;
        this.xxVel = body.xxVel;
        this.yyVel = body.yyVel;
        this.mass = body.mass;
        this.imgFileName = body.imgFileName;
    }

    public double calcDistance(Body body){
        double xsquare = (this.xxPos-body.xxPos)*(this.xxPos-body.xxPos);
        double ysquare = (this.yyPos-body.yyPos)*(this.yyPos-body.yyPos);
        return Math.sqrt(xsquare+ysquare);
    }

    public double calcForceExertedBy(Body body) {
        if (this.equals(body)) return 0;
        double f = (this.mass * body.mass * G / (this.calcDistance(body) * this.calcDistance(body)));
        return f;
    }

    public double calcNetForceExertedByX(Body[] allBodies){
        double ans = 0;
        for (int i = 0; i < allBodies.length; i++){
            ans+=this.calcForceExertedByX(allBodies[i]);
        }
        return ans;
    }
    public double calcNetForceExertedByY(Body[] allBodies){
        double ans = 0;
        for (int i = 0; i < allBodies.length; i++){
            ans+=this.calcForceExertedByY(allBodies[i]);
        }
        return ans;
    }

    public double calcForceExertedByX(Body body){
        if (this.equals(body)) return 0;
        double dx = body.xxPos - this.xxPos;
        double xf = (this.calcForceExertedBy(body) * dx / this.calcDistance(body));
        return xf; 
    }

    public double calcForceExertedByY(Body body){
        if (this.equals(body)) return 0;
        double dy = body.yyPos - this.yyPos;
        double yf = (this.calcForceExertedBy(body) * dy / this.calcDistance(body));
        return yf;
    }
    public void update(double dt, double fX, double fY){
        double ax = fX/this.mass;
        double ay = fY/this.mass;
        this.xxVel = this.xxVel + dt * ax;
        this.yyVel = this.yyVel + dt * ay;
        this.xxPos = this.xxPos + dt * this.xxVel;
        this.yyPos = this.yyPos + dt * this.yyVel;
    }
    public void draw(){
        StdDraw.picture(xxPos, yyPos, "images/"+imgFileName);
    }
}

