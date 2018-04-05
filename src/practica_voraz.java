import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JOptionPane;



public class practica_voraz {
	
	List<actividad> actividades = null;
	Scanner scan = new Scanner(System.in);
	
	
	// Generación de las actividades
	public List<actividad> generar_conjunto(int num_actividades){
		
		actividades = new ArrayList<actividad>(num_actividades);		
		
		// Generar mediante entrada por teclado
		for (int i = 0; i < num_actividades; i++){
			System.out.println("Tiempo de inicio");
			int inicio = scan.nextInt();
			System.out.println("Tiempo de fin");
			int fin = scan.nextInt();
			//Añadimos el índice a la lista, que corda con la id de la actividad
			actividades.add(i, new actividad(inicio, fin, i));
		}
		
		
		/*System.out.println(actividades.size());
		for (int i = 0; i < actividades.size(); i++){
			System.out.println(actividades.get(i).getId() + ", inicio: " + actividades.get(i).getTiempo_inicio() + ", fin: " + actividades.get(i).getTiempo_fin());
		}*/
		
		return actividades;
	}
	

	
	// Métodos de genración de la solución
	public List<actividad> esSolucion(List<actividad> solucion){
		
		List<actividad> conjunto_actividades = new ArrayList<actividad>();

		//Añadimos el primero
		conjunto_actividades.add(solucion.get(0));
		
		for (int i = 0; i < solucion.size() - 1; i++){
			int an = conjunto_actividades.get(conjunto_actividades.size()-1).getTiempo_fin();
			int an2 = solucion.get(i+1).getTiempo_inicio();
			
			if (an < an2){
				conjunto_actividades.add(solucion.get(i+1));
			} 
			
		}

		return conjunto_actividades;		
	}
	
	
	
	
	public List<actividad> s_finish(List<actividad> actividades, int iteracion){
		
		if (iteracion > 1){
			
			for (int index = 0; index < actividades.size() - 1; index++){
				int an = actividades.get(index).getTiempo_fin();			
				int an2 = actividades.get(index + 1).getTiempo_fin();
				if (an > an2){
					actividad aux = actividades.get(index);
					//System.out.print(aux.getId() + "   " + aux.getTiempo_fin() + "   " + aux.getTiempo_inicio());
					actividades.set(index, actividades.get(index + 1));
					actividades.set(index + 1, aux);
					//System.out.print(actividades.get(index + 1).getId() + "   " + actividades.get(index + 1).getTiempo_fin() + "   " + actividades.get(index + 1).getTiempo_inicio());
				}
			}
			s_finish(actividades, --iteracion);
		}
		
		return actividades;
		
	}
	
	
	
	
	private void exec() {	
		
		int num_actividades = Integer.parseInt( JOptionPane.showInputDialog("Introduce el número de actividades deseadas"));
		
		List<actividad> lista_actividades = generar_conjunto(num_actividades);
		//System.out.println(lista_actividades.get(lista_actividades.size()-1).getId());
		List<actividad> shorted_actividades = s_finish(lista_actividades, lista_actividades.size());
		for (int i = 0; i < shorted_actividades.size(); i++){
			System.out.println(shorted_actividades.get(i).getId() + ", inicio: " + shorted_actividades.get(i).getTiempo_inicio() + ", fin: " + shorted_actividades.get(i).getTiempo_fin());
		}
		System.out.println(esSolucion(shorted_actividades).size());
		
		//the_voracious_one(el conjunto);
		//en este se supone que hay que llamar a esSolucion. o luego, eso se vera despues
	}
	
	public static void main(String[] args){
		new practica_voraz().exec();		
	}
	

}
