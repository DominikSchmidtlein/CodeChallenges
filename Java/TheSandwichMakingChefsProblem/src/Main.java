import java.util.ArrayList;

public class Main {
	
	public static final int SANDWICH_LIMIT = 20;
	
	public static void main(String[] args) {
		Table table = new Table(SANDWICH_LIMIT);
		
		ArrayList<Thread> threads = new ArrayList<>(Ingredient.values().length + 1);
		threads.add(new Thread(new Agent(SANDWICH_LIMIT, table)));
		
		for(Ingredient ingredient : Ingredient.values())
			threads.add(new Thread(new ChefCurry(table, ingredient, 20)));
		
		for(Thread thread : threads)
			thread.start();
		
		for(Thread thread : threads){
			try {
				thread.join();
			} catch (Exception e) {

			}
		}
		
		System.out.println("\nDone");
	}
}
