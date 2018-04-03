import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;



public class practica_voraz {
	
	List<actividad> actividades = null;
	Scanner scan = new Scanner(System.in);
	
	
	// Generación de las actividades
	public List<actividad> generar_conjunto(int num_actividades){
		
		actividades = new ArrayList<actividad>(num_actividades);
		
		for (int i = 0; i < num_actividades; i++){
			System.out.println("Tiempo de inicio");
			int inicio = scan.nextInt();
			System.out.println("Tiempo de fin");
			int fin = scan.nextInt();
			//Añadimos el índice a la lista, que corda con la id de la actividad
			actividades.add(i, new actividad(inicio, fin, i));
		}
		
		return actividades;
	}
	

	
	// Métodos de comprobación de la solución
	public boolean esSolucion(actividad[] solucion){
		
		boolean mismuertos = false;
		// Quizas ordenar por tiempo de inicio y despues comparar si puede ser con el timpo de fin? 
		// plan si tiempo_inicio(n) >= tiempo_fin(n-1) entonces es factible 
		// else () remove tal cosa y volvemos a comparar
		// AUNQUE:
		// puta mierda que eso solo serviria para una posible solucion pero no la optima
		
		
		
		
		return mismuertos;		
	}
	
	
	private void exec() {	
		generar_conjunto(12);	
		//the_voracious_one(el conjunto);
		//en este se supone que hay que llamar a esSolucion. o luego, eso se vera despues
	}
	
	public static void main(String[] args){
		new practica_voraz().exec();		
	}
	

}
