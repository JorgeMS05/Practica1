import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.io.*;



public class practica_voraz {
	
	
	List<actividad> actividades = null;
	Scanner scan = new Scanner(System.in);
	
	// Bool para que se repita la petición de la entrada de datos hasta elegir por teclado o por fichero
	boolean aux = true;
	
	//Variable para la lectura del fichero
	File archivo = null;
    FileReader fr = null;
    BufferedReader br = null;
    
    // Generamos la lista final y la lista solución
    List<actividad> shorted_actividades = null;
    List<actividad> sol_actividades = null;
    
    // Variables para controlar los tiempos de ejecución
    double time_start, time_end;	

	
	///////////////////////////
	///   Algoritmo Voraz   ///
	///////////////////////////

	// Métodos de generación de la solución
	public List<actividad> esSolucion(List<actividad> solucion){
		
		List<actividad> conjunto_actividades = new ArrayList<actividad>();
		
		//Añadimos el primero
		conjunto_actividades.add(solucion.get(0));
		// Se compara el tiempo_fin de la actividad N con el tiempo_inicio de la actividad N+1
		for (int i = 0; i < solucion.size() - 1; i++){
			int an = conjunto_actividades.get(conjunto_actividades.size()-1).getTiempo_fin();
			int an2 = solucion.get(i+1).getTiempo_inicio();
			// Si la actividad N+1 empieza cuando acaba N o más tarde, se acepta
			if (an <= an2){
				conjunto_actividades.add(solucion.get(i+1));
			} 
			
		}

		return conjunto_actividades;		
	}
		
	
		
	public List<actividad> s_finish(List<actividad> actividades, int iteracion){
			
		if (iteracion > 1){
			// Se ordena la lista de actividades de menor a mayor en función de su tiempo_fin
			for (int index = 0; index < actividades.size() - 1; index++){
				int an = actividades.get(index).getTiempo_fin();			
				int an2 = actividades.get(index + 1).getTiempo_fin();
				if (an > an2){
					actividad aux = actividades.get(index);
					actividades.set(index, actividades.get(index + 1));
					actividades.set(index + 1, aux);
				}
			}		
			// stackOverFLow a partir de cierto valor (12000??)
			s_finish(actividades, --iteracion);
		}
		// Devolvemos la lista ya ordenada
		return actividades;
			
	}
	
	
	
	////////////////////////////////////////////
	//    Introducción de datos por teclado   //
	////////////////////////////////////////////
	
	public List<actividad> generar_conjunto(int num_actividades){
		
		actividades = new ArrayList<actividad>(num_actividades);		

		// Generar mediante entrada por teclado
		for (int i = 0; i < num_actividades; i++){
			System.out.println("Introduce el tiempo de inicio de la tarea " + (i + 1) + ":");
			int inicio = scan.nextInt();
			System.out.println("Introduce el tiempo final de la tarea "+  (i + 1)  +":");
			int fin = scan.nextInt();
			//Añadimos el índice a la lista, que corda con la id de la actividad
			actividades.add(i, new actividad(inicio, fin, i));
		}
			
		return actividades;
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////
	///  Generar fichero de entrada de tamaño variable con datos aleatorios  ///
	////////////////////////////////////////////////////////////////////////////
	
	// Método para generar un fichero de entrada válido aleatorio
	public void generarFicheroPruebas () {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try
        {
            fichero = new FileWriter("fichero_usuario.txt");
            pw = new PrintWriter(fichero);
            int num_actividades = Integer.parseInt( JOptionPane.showInputDialog("Introduce el número de actividades deseadas"));
            pw.println("Número de actividades: " + num_actividades );
            int t_ini, t_fin;
            for (int i = 0; i < num_actividades; i++) {
                t_ini = (int) (Math.random() * 10) + 1;
                t_fin = (int) (t_ini + (Math.random() * 10) + 1);
               
                pw.println(t_ini + " " +  t_fin);
            }
 
        } catch (Exception e) {
          
        	e.printStackTrace();           
        } finally {
        	
           try {
           if (null != fichero)
              fichero.close();
           } catch (Exception e2) {
              e2.printStackTrace();
           }
        }
    }
	
	
	
	///////////////////////////////////////////////////
	///  Generar fichero de salida con la solución  ///
	///////////////////////////////////////////////////

	public void generarTxt (List<actividad> actividades, String path) {		
		
		FileWriter fichero = null;
		PrintWriter pw = null;
		try
		{
		    fichero = new FileWriter(path);
		    pw = new PrintWriter(fichero);
		
		    for (int i = 0; i < actividades.size(); i++)
		        pw.println("Id de la actividad: " + actividades.get(i).getId());
		
		} catch (Exception e) {
		    e.printStackTrace();
		} finally {
		   try {
		   if (null != fichero)
		      fichero.close();
		   } catch (Exception e2) {
		      e2.printStackTrace();
		   }
		}		
	}
	
	
	////////////////////////////////
	//  Gestión de la aplicación  //
	////////////////////////////////
	
	private void exec1() {
		aux = false;
		System.out.println("Introduce el número de actividades deseadas");
		int num_actividades = scan.nextInt();
		// Generamos la lista de actividades por teclado
		List<actividad> lista_actividades = generar_conjunto(num_actividades);					
		// Iniciamos el tiempo antes de las llamadas al algoritmo
	    time_start = System.currentTimeMillis();

		shorted_actividades = s_finish(lista_actividades, lista_actividades.size());
		sol_actividades = esSolucion(shorted_actividades);
		
		
		
		String solucion = "";
		for (int i = 0; i < sol_actividades.size(); i++) {
			solucion += " " +  sol_actividades.get(i).toString();
		}
		System.out.println("El conjunto de actividades que forman la solución son/es: " + solucion + ". \n");
		for (int i = 0; i < sol_actividades.size(); i++){
			System.out.println("\n Actividad número: " + shorted_actividades.get(i).getId() + "\n Tiempo de inicio: "
							+ sol_actividades.get(i).getTiempo_inicio() + "\n Tiempo final: " + sol_actividades.get(i).getTiempo_fin());
		}
		
		
		// Tiempo final 
		time_end = System.currentTimeMillis();
		System.out.println("\n El tiempo de ejecución de la aplicación ha sido de " + (time_end - time_start) + " milisegundos");
	}
	
	
	private void exec2(String entrada) {
		//Abrimos el archivo y cargamos las actividades:
		try {
			
			 // Apertura del fichero y creacion de BufferedReader para poder hacer una lectura cómoda (disponer del metodo readLine())
			 archivo = new File (entrada);
	         fr = new FileReader (archivo);
	         br = new BufferedReader(fr);

	         // Lectura del fichero: se lee la primera línea y se detecta el cuarto elemento como el número en sí de actividades
	         String linea = br.readLine();
	         String palabra[] = linea.split(" ");
	         int num_Actividades = Integer.parseInt(palabra[3]);
	         
	         // Inicializamos la lista en la que se almacenan las actividades
	         actividades = new ArrayList<actividad>(num_Actividades);
	         
	         // A continuación se leen las siguientes líneas del fichero y se genera la lista de actividades
	         for (int i = 0; i < num_Actividades; i++) {			        	 
	        	 linea = br.readLine();
	        	 String palabras[] = linea.split(" ");
	        	 // Añadimos a la lista la correspondiente actividad
	        	 actividades.add(i, new actividad(Integer.parseInt(palabras[0]), Integer.parseInt(palabras[1]), i));				        	
	         }
	         
	         // Iniciamos el tiempo antes de las llamadas al algoritmo
			 time_start = System.currentTimeMillis();
	         
	         // Ordenamos de menor a mayor según sus tiempo_final
	         shorted_actividades = s_finish(actividades, actividades.size());
	         // Generamos la lista con las actividades solución
	         sol_actividades = esSolucion(shorted_actividades);
	         
	        String solucion = "";
	  		for (int i = 0; i < sol_actividades.size(); i++) {
	  			solucion += " " +  sol_actividades.get(i).toString();
	  		}
	 		System.out.println("El conjunto de actividades que forman la solución son/es: " + solucion + ". \n");
	 		for (int i = 0; i < sol_actividades.size(); i++){
	 			System.out.println("Actividad número: " + sol_actividades.get(i).getId() + "\n Tiempo de inicio: "
	 							+ sol_actividades.get(i).getTiempo_inicio() + "\n Tiempo final: " + sol_actividades.get(i).getTiempo_fin());
	 		}
	 		
	 		
	 		// Tiempo final 
	 		time_end = System.currentTimeMillis();
	 		System.out.println("\n El tiempo de ejecución de la aplicación ha sido de " + (time_end - time_start) + " milisegundos");
			 
		}catch(Exception e) {
			 e.printStackTrace();
		}
		
	}
	
	private void exec3(String entrada, String salida) {
		aux = false;
        
        //Abrimos el archivo y cargamos las actividades:
        try {
     	   	 // La llamada a este método genera un fichero con x actividades aleatorias 
              //generarFicheroPruebas();

              archivo = new File (entrada);
              fr = new FileReader (archivo);
              br = new BufferedReader(fr);

              String linea = br.readLine();
              String palabra[] = linea.split(" ");
              int num_Actividades = Integer.parseInt(palabra[3]);
              
              actividades = new ArrayList<actividad>(num_Actividades);
              
              for (int i = 0; i < num_Actividades; i++) {                         
                  linea = br.readLine();
                  String palabras[] = linea.split(" ");
                  actividades.add(i, new actividad(Integer.parseInt(palabras[0]), Integer.parseInt(palabras[1]), i));                           
              }

              time_start = System.currentTimeMillis();

              shorted_actividades = s_finish(actividades, actividades.size());	          
              sol_actividades = esSolucion(shorted_actividades);
              
              time_end = System.currentTimeMillis();
              System.out.println("Tiempo de ejecución " + ( time_end - time_start ) + " milisegundos");
              
              System.out.println(salida);
              if (!new File(salida).exists()) {
            	  generarTxt(sol_actividades, salida);
              }else {   
            	  
            	final JPanel panel = new JPanel();
      			JOptionPane.showMessageDialog(panel, "Existe un fichero con ese mismo nombre, bórrelo antes de continuar", "Dialog", JOptionPane.ERROR_MESSAGE);
              }
              
         }catch(Exception e) {	           
              e.printStackTrace();
         }		
	}
	
	public static void main(String[] args){
		int aux = args.length;
		String f_e, f_s;	
		switch (aux) {
		case 0:
			new practica_voraz().exec1();
			break;
		case 1:
			f_e = args[0];
			System.out.println(f_e);
			new practica_voraz().exec2(f_e);
			break;
		case 2:
			f_e = args[0];
			f_s = args[1];
			new practica_voraz().exec3(f_e, f_s);
			
			break;
		default:
			break;
		
		}
	}
	

}
