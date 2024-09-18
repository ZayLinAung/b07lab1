public class Polynomial{

    double[] coefficients;

    public Polynomial(){
        coefficients = new double[1];
    }

    public Polynomial(double[] coefficients){
        this.coefficients = new double[coefficients.length];

        for(int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }
    }

    public Polynomial add(Polynomial p){
        int longLength = Math.max(this.coefficients.length, p.coefficients.length);
        int shortLength = Math.min(this.coefficients.length, p.coefficients.length);
        double newCoefficients[] = new double[longLength];
        int i;
        for(i = 0; i < shortLength; i++){
            newCoefficients[i] = coefficients[i] + p.coefficients[i];
        }

        if(this.coefficients.length == longLength){
            for(int j = i; j < longLength; j++){
                newCoefficients[j] = this.coefficients[j];
            }
        }
        else{
            for(int j = i; j < longLength; j++){
                newCoefficients[j] = p.coefficients[j];
            }
        }

        return new Polynomial(newCoefficients);
    }

    public double evaluate(double x){
        double result = 0.0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i] * Math.pow(x, i);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }
}