import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;

public class Polynomial{

    double[] coefficients;
    int[] exponents;

    public Polynomial(){
        coefficients = new double[1];
        exponents = new int[1];
    }

    public Polynomial(double[] coefficients, int[] exponents){
        this.coefficients = new double[coefficients.length];
        this.exponents = new int[exponents.length];

        for(int i = 0; i < coefficients.length; i++){
            this.coefficients[i] = coefficients[i];
        }

        for(int i = 0; i < exponents.length; i++){
            this.exponents[i] = exponents[i];
        }
    }


    public Polynomial(File f) throws FileNotFoundException{
        Scanner sc = new Scanner(f);
        String p = sc.nextLine();

        String[] terms = p.split("(?=[-])|\\+");
        this.coefficients = new double[terms.length];
        this.exponents = new int[terms.length];

        for(int i = 0; i < terms.length; i++){
            if(!hasX(terms[i])){
                this.coefficients[i] = Double.parseDouble(terms[i]);
                this.exponents[i] = 0;
            }
            else{
                String[] nums = terms[i].split("x");
                if(nums.length == 1){
                    if(nums[0].equals("-")) {
                        this.coefficients[i] = -1;
                        this.exponents[i] = 1;
                    }
                    else{
                        this.coefficients[i] = Double.parseDouble(nums[0]);
                        this.exponents[i] = 1;
                    }
                }
                else{
                    if(nums[0].equals("-")) nums[0] = "-1";
                    this.coefficients[i] = nums[0] == "" ? 1 : Double.parseDouble(nums[0]);
                    this.exponents[i] = Integer.parseInt(nums[1]);
                }
            }
        }
    }

    public void saveToFile(String s) throws IOException{
        FileWriter file = new FileWriter(s);
        String sign = "";
        String coefficient = "";
        String exponent = "";
        for(int i = 0; i < this.coefficients.length; i++){
            sign = this.coefficients[i] < 0 || i == 0? "" : "+";
            coefficient = this.coefficients[i] == 1? "" : this.coefficients[i] == -1? "-" : String.valueOf(this.coefficients[i]);
            exponent = this.exponents[i] == 1? "" : String.valueOf(this.exponents[i]);
            if(this.coefficients[i] != 0)
                if(this.exponents[i] == 0)
                    file.write(String.valueOf(sign + coefficient));
                else
                    file.write(String.valueOf(sign + coefficient + "x" + exponent));
        }
        file.close();
    }


    public boolean hasX(String s){
        for(int i = 0; i < s.length(); i++){
            if (s.charAt(i) == 'x') return true;
        }

        return false;
    }

    public Polynomial add(Polynomial p){
        int highest = Math.max(findHighest(this.exponents), findHighest(p.exponents)) + 1;

        double[] newCoefficients = new double[highest];
        int[] newExponents = new int[highest];

        for(int i = 0; i < newExponents.length; i++){
            newExponents[i] = i;
        }

        for(int i = 0; i < this.coefficients.length; i++){
            int expo = this.exponents[i];
            newCoefficients[expo] += this.coefficients[i];
        }

        for(int i = 0; i < p.coefficients.length; i++){
            int expo = p.exponents[i];
            newCoefficients[expo] += p.coefficients[i];
        }

        int count = 0; 

        for(int i = 0; i < highest; i++){
            if(newCoefficients[i] != 0) count++;
        }

        double[] finalCoefficients = new double[count];
        int[] finalExponents = new int[count];

        int j = 0;
        for(int i = 0; i < highest; i++){
            if(newCoefficients[i] != 0){
                finalCoefficients[j] = newCoefficients[i];
                finalExponents[j] = newExponents[i];
                j++;
            }
        }

        return new Polynomial(finalCoefficients, finalExponents);
    }


    public int findHighest(int[] array1){
        int expo = 0;
        for(int i = 0; i < array1.length; i++){
            expo = Math.max(expo, array1[i]);
        }

        return expo;
    }

    public Polynomial multiply(Polynomial p){
       Polynomial result = new Polynomial();
       double[] subCoefficients = new double[p.coefficients.length];
       int[] subExponents = new int[p.coefficients.length];
       Polynomial sub = new Polynomial(subCoefficients, subExponents);

       for(int i = 0; i < this.coefficients.length; i++){
        for(int j = 0; j < p.coefficients.length; j++){
               sub.coefficients[j] = this.coefficients[i] * p.coefficients[j];
               sub.exponents[j] = this.exponents[i] + p.exponents[j];
        }
        result = result.add(sub);
       }

       return result;
    }   


    public double evaluate(double x){
        double result = 0.0;
        for (int i = 0; i < coefficients.length; i++){
            result += coefficients[i] * Math.pow(x, exponents[i]);
        }
        return result;
    }

    public boolean hasRoot(double x){
        return evaluate(x) == 0;
    }
}