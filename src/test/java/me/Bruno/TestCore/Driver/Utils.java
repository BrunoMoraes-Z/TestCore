package me.Bruno.TestCore.Driver;

import java.util.Random;

public class Utils {

    private static Random r = new Random();

    public static String geraCPF() {

        int digito1 = 0, digito2 = 0, resto = 0;
        String  nDigResult;
        String numerosContatenados;
        String numeroGerado;

        int n1 = r.nextInt(10);
        int n2 = r.nextInt(10);
        int n3 = r.nextInt(10);
        int n4 = r.nextInt(10);
        int n5 = r.nextInt(10);
        int n6 = r.nextInt(10);
        int n7 = r.nextInt(10);
        int n8 = r.nextInt(10);
        int n9 = r.nextInt(10);

        int soma = n9*2 + n8*3 + n7*4 + n6*5 + n5*6 + n4*7 + n3*8 + n2*9 + n1*10;
        int valor = (soma / 11)*11;
        digito1 = soma-valor;
        resto = (digito1 % 11);

        if(digito1 < 2){
            digito1 = 0;
        }
        else {
            digito1 = 11-resto;
        }

        int soma2 = digito1 * 2 + n9*3 + n8*4 + n7*5 + n6*6 + n5*7 + n4*8 + n3*9 + n2*10 + n1*11;
        int valor2 = (soma2 / 11)*11;
        digito2 = soma2-valor2;
        resto = (digito2 % 11);

        if(digito2 < 2){
            digito2 = 0;
        }
        else {
            digito2 = 11-resto;
        }

        numerosContatenados = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3) +"." + String.valueOf(n4) +
                String.valueOf(n5) + String.valueOf(n6) +"."+ String.valueOf(n7) +String.valueOf(n8)  +
                String.valueOf(n9)+"-";

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        numeroGerado = numerosContatenados+nDigResult;

        return numeroGerado;
    }

    public static String gerarRg() {

        String numerosContatenados;
        String numeroGerado;

        int n1 = r.nextInt(10);
        int n2 = r.nextInt(10);
        int n3 = r.nextInt(10);
        int n4 = r.nextInt(10);
        int n5 = r.nextInt(10);
        int n6 = r.nextInt(10);
        int n7 = r.nextInt(10);
        int n8 = r.nextInt(10);
        int n9 = r.nextInt(10);

        numerosContatenados = String.valueOf(n1) + String.valueOf(n2) + String.valueOf(n3)  + String.valueOf(n4) +
                String.valueOf(n5) + String.valueOf(n6) + String.valueOf(n7) +String.valueOf(n8)  +
                String.valueOf(n9);

        numeroGerado = numerosContatenados;

        return numeroGerado;
    }

    public static String geraCNPJ() {

        int digito1 = 0, digito2 = 0, resto = 0;
        String  nDigResult;
        String numerosContatenados;
        String numeroGerado;

        int n1 = r.nextInt(10);
        int n2 = r.nextInt(10);
        int n3 = r.nextInt(10);
        int n4 = r.nextInt(10);
        int n5 = r.nextInt(10);
        int n6 = r.nextInt(10);
        int n7 = r.nextInt(10);
        int n8 = r.nextInt(10);
        int n9 = r.nextInt(10);
        int n10 = r.nextInt(10);
        int n11 = r.nextInt(10);
        int n12 = r.nextInt(10);

        int soma = n12*2 + n11*3 + n10*4 + n9*5 + n8*6 + n7*7 + n6*8 + n5*9 + n4*2 + n3*3 + n2*4 + n1*5;
        int valor = (soma / 11)*11;
        digito1 = soma-valor;
        resto = (digito1 % 11);

        if(digito1 < 2){
            digito1 = 0;
        }
        else {
            digito1 = 11-resto;
        }

        int soma2 =  digito1*2 + n12*3 + n11*4 + n10*5 + n9*6 + n8*7 + n7*8 + n6*9 + n5*2 + n4*3 + n3*4 + n2*5 + n1*6 ;
        int valor2 = (soma2 / 11)*11;
        digito2 = soma2-valor2;
        resto = (digito2 % 11);

        if(digito2 < 2){
            digito2 = 0;
        }
        else {
            digito2 = 11-resto;
        }

        numerosContatenados = String.valueOf(n1) + String.valueOf(n2) +"."+ String.valueOf(n3) + String.valueOf(n4) +
                String.valueOf(n5) +"."+ String.valueOf(n6) + String.valueOf(n7) +String.valueOf(n8)+"/"+
                String.valueOf(n9) + String.valueOf(n10) + String.valueOf(n11) +
                String.valueOf(n12)+"-";

        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        numeroGerado = numerosContatenados+nDigResult;

        return numeroGerado;
    }

}
