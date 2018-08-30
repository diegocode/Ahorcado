/*

Un juego de Ahorcado para línea de comandos.

Lee las palabras para adivinar de un archivo 
lista_palabras.txt (una palabra por línea) 

TODO:

- Evitar errores
- Lista de letras usadas
- i18n
- Soporte para frases
- Categorías para palabras?
- Ayuda en línea?
- Especificar nombre de archivo de palabras?

*/

import java.util.Scanner;
import java.util.Random;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Ahorcado {
    /*
        Dibuja monigote segun numero de intentos
        si intentos es menor a cero o seis o mayor, 
        dibuja el monigote completo
    */
    public static void dibujaMonigote(int intentos){
        switch(intentos) {
        case 0:
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                break;
        case 1:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                System.out.println("     ");
                break;
        case 2:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println("     ");
                System.out.println("     ");        
                break;
        case 3:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println(" /   ");
                System.out.println("     ");        
                break;
        case 4:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("  |  ");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");        
                break;
        case 5:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("--|  ");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");        
                break;
        case 6:
        default:
                System.out.println("     ");
                System.out.println("  O  ");
                System.out.println("--|--");
                System.out.println("  |  ");
                System.out.println(" / \\ ");
                System.out.println("     ");        
                break;
        }
    }       

    /*
        Devuelve un ArrayList con las líneas de 
        un archivo de texto como elementos.
    */
    public static ArrayList<String> leer(String nombreArchivo) throws FileNotFoundException, IOException {
        String auxs = "";
                      
        ArrayList<String> palabrasLeidas = new ArrayList<String>();
                      
        FileReader fr =  new FileReader(nombreArchivo);
            
        BufferedReader buff_in = new BufferedReader( fr );
            
        while(( auxs = buff_in.readLine()) != null) {
                if (auxs.length() > 0)
                    palabrasLeidas.add(auxs);
        }	

        buff_in.close();	
        
        return palabrasLeidas;
    }    
    
	public static void main(String[] args) throws IOException {
		String palabraSecreta;
        String palabraAdivinada;
        
        final int intentosMaximos = 6;
        int intentos = 0;
        int resultado = 0;
        
        boolean letraBien = false;
		
		// Crea una lista de palabras a partir de un archivo 
        ArrayList<String> palabras = new ArrayList<String>();
        try {
            palabras = leer("lista_palabras.txt");
        } catch(FileNotFoundException e) {
            System.out.println("El archivo de palabras no existe");
            System.out.println("Imposible continuar");
            return;
        }

        // Elige aleatoriamente una palabra de la lista como 
        // palabra secreta
        Random randGen = new Random();
        palabraSecreta = palabras.get(randGen.nextInt(palabras.size()));
        
        // Genera un String con tantos _ como letras tenga la palabra 
        // a adivinar
        palabraAdivinada = String.format("%0" + palabraSecreta.length() + "d", 0).replace("0", "_");
		
		Scanner consInp = new Scanner(System.in);
		
		// muestra el progreso 
		for (char c : palabraAdivinada.toCharArray()) {
            System.out.print(c + " ");
        }
        System.out.println();

        // mientras que no se adivine o se acaben los intentos...
        while(resultado == 0) {
            System.out.println();
            System.out.println();
            System.out.print("Ingrese una letra: ");
		
            // lee letra
            String letraIngresada = consInp.next();
            letraIngresada = letraIngresada.trim().substring(0, 1);
            
            // verifica que la letra esté en la palabra secreta
            int posicion;
            posicion = palabraSecreta.indexOf(letraIngresada);
            
            letraBien = false;
            while (posicion != -1) {
                // reemplaza el _ por la letra ingresada
                palabraAdivinada = palabraAdivinada.substring(0,posicion) + letraIngresada + palabraAdivinada.substring(posicion + 1);

                // sigue verificando si hay más ocurrencias de la letra ingreada
                posicion = palabraSecreta.indexOf(letraIngresada, posicion + 1);
                
                letraBien = true;
            }
            
            // muestra el progreso
            System.out.println();
            for (char c : palabraAdivinada.toCharArray()) {
                System.out.print(c + " ");
            }
            System.out.println();
            
            // chequea si se adivinó la palabra secreta
            if (!palabraAdivinada.contains("_")) {
                resultado = 1;
            } else {
                // ...y si la letra ingresada no estaba...
                if (!letraBien) { 
                    // ...incrementa el contador de intentos
                    intentos++;
                    // si se acabaron los intentos termina...
                    if (intentos == intentosMaximos) {
                        resultado = -1;
                    }
                }
            }
            
            System.out.println();
            System.out.println("Usaste " + intentos + " intentos de los " + intentosMaximos);
            dibujaMonigote(intentos);
		}
		
		// informa resultado del juego
		if (resultado == 1) {
            System.out.println();
            System.out.println("Ganaste!");
            System.out.println();
		} else {
            System.out.println();
            System.out.println("La palabra es: " + palabraSecreta);
            System.out.println();
		}
	}
}
