import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;

import java.io.*;



public class practica_voraz {
	
	List<actividad> actividades = null;
	Scanner scan = new Scanner(System.in);
	
	// Generaci�n de las actividades
	public List<actividad> generar_conjunto(int num_actividades){
		
		actividades = new ArrayList<actividad>(num_actividades);		
        System.out.println(actividades.size());

		// Generar mediante entrada por teclado
		for (int i = 0; i < num_actividades; i++){
			System.out.println("Tiempo de inicio");
			int inicio = scan.nextInt();
			System.out.println("Tiempo de fin");
			int fin = scan.nextInt();
			//A�adimos el �ndice a la lista, que corda con la id de la actividad
			actividades.add(i, new actividad(inicio, fin, i));
		}
			
		return actividades;
	}
	

	
	// M�todos de generaci�n de la soluci�n
	public List<actividad> esSolucion(List<actividad> solucion){
		
		List<actividad> conjunto_actividades = new ArrayList<actividad>();

		//A�adimos el primero
		conjunto_actividades.add(solucion.get(0));
		// Se compara el tiempo_fin de la actividad N con el tiempo_inicio de la actividad N+1
		for (int i = 0; i < solucion.size() - 1; i++){
			int an = conjunto_actividades.get(conjunto_actividades.size()-1).getTiempo_fin();
			int an2 = solucion.get(i+1).getTiempo_inicio();
			// Si la actividad N+1 empieza cuando acaba N o m�s tarde, se acepta
			if (an <= an2){
				conjunto_actividades.add(solucion.get(i+1));
			} 
			
		}

		return conjunto_actividades;		
	}
	
	
	
	
	public List<actividad> s_finish(List<actividad> actividades, int iteracion){
		
		if (iteracion > 1){
			// Se ordena la lista de actividades de menor a mayor en funci�n de su tiempo_fin
			for (int index = 0; index < actividades.size() - 1; index++){
				int an = actividades.get(index).getTiempo_fin();			
				int an2 = actividades.get(index + 1).getTiempo_fin();
				if (an > an2){
					actividad aux = actividades.get(index);
					actividades.set(index, actividades.get(index + 1));
					actividades.set(index + 1, aux);
				}
			}
			s_finish(actividades, --iteracion);
		}
		// Devolvemos la lista ya ordenada
		return actividades;
		
	}
	
	
	
	
	private void exec() {
		
		// Bool para que se repita la petici�n de la entrada de datos hasta elegir por teclado o por fichero
		boolean aux = true;
		
		//Variable para la lectura del fichero
		File archivo = null;
	    FileReader fr = null;
	    BufferedReader br = null;
	    
	    // Generamos la lista final y la lista soluci�n
	    List<actividad> shorted_actividades = null;
	    List<actividad> sol_actividades = null;
	    
	    // Variables para controlar los tiempos de ejecuci�n
	    double time_start, time_end;	    
	    
		int opcion = Integer.parseInt( JOptionPane.showInputDialog("Escoja modo de entrada: 1(por teclado) 2(fichero de texto)"));
		
		
		while(aux) {
			
			switch(opcion) {
			
			case 1:
					aux = false;
					int num_actividades = Integer.parseInt( JOptionPane.showInputDialog("Introduce el n�mero de actividades deseadas"));
					// Generamos la lista de actividades por teclado
					List<actividad> lista_actividades = generar_conjunto(num_actividades);					
					// Iniciamos el tiempo antes de las llamadas al algoritmo
				    time_start = System.currentTimeMillis();

					shorted_actividades = s_finish(lista_actividades, lista_actividades.size());
					sol_actividades = esSolucion(shorted_actividades);
					
					// Tiempo final 
					time_end = System.currentTimeMillis();
					System.out.println("Tiempo de ejecuci�n " + ( time_end - time_start ) + " milisegundos");
					
					
					/*for (int i = 0; i < shorted_actividades.size(); i++){
						System.out.println(shorted_actividades.get(i).getId() + ", inicio: " + shorted_actividades.get(i).getTiempo_inicio() + ", fin: " + shorted_actividades.get(i).getTiempo_fin());
					}
					System.out.println(esSolucion(shorted_actividades).size());*/	
					
				break;
				
			case 2:
				
					aux = false;
					
					//Abrimos el archivo y cargamos las actividades:
					try {
						
						 // Apertura del fichero y creacion de BufferedReader para poder hacer una lectura comoda (disponer del metodo readLine())
						 archivo = new File ("ArchivoTexto.txt");
				         fr = new FileReader (archivo);
				         br = new BufferedReader(fr);
	
				         // Lectura del fichero: se lee la primera l�nea y se detecta el cuarto elemento como el n�mero en s� de actividades
				         String linea = br.readLine();
				         String palabra[] = linea.split(" ");
				         int num_Actividades = Integer.parseInt(palabra[3]);
				         
				         // Inicializamos la lista en la que se almacenan las actividades
				         actividades = new ArrayList<actividad>(num_Actividades);
				         
				         // A continuaci�n se leen las siguientes l�neas del fichero y se genera la lista de actividades
				         for (int i = 0; i < num_Actividades; i++) {			        	 
				        	 linea = br.readLine();
				        	 String palabras[] = linea.split(" ");
				        	 // A�adimos a la lista la correspondiente actividad
				        	 actividades.add(i, new actividad(Integer.parseInt(palabras[0]), Integer.parseInt(palabras[1]), i));				        	
				         }
				         
				         // Iniciamos el tiempo antes de las llamadas al algoritmo
						 time_start = System.currentTimeMillis();
				         
				         // Ordenamos de menor a mayor seg�n sus tiempo_final
				         shorted_actividades = s_finish(actividades, actividades.size());
				         // Generamos la lista con las actividades soluci�n
				         sol_actividades = esSolucion(shorted_actividades);
				         
				         // Tiempo final 
						 time_end = System.currentTimeMillis();
						 System.out.println("Tiempo de ejecuci�n " + ( time_end - time_start ) + " milisegundos");
				         
						
					}catch(Exception e) {
						 e.printStackTrace();
					}
					
				break;
				
			default:
					opcion = Integer.parseInt( JOptionPane.showInputDialog("Dato no v�lido, introducir 1 � 2"));
					
				break;		
					
			}		
		}

		
	}
	
	
	
	public static void main(String[] args){
		new practica_voraz().exec();		
	}
	

}
