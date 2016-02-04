import java.util.ArrayList;

public class Table {

	private ArrayList<Ingredient> ingredients;
	private int numberOfSandwichesMade;

	public Table() {
		ingredients = new ArrayList<>();
		numberOfSandwichesMade = 0;
	}

	public synchronized void put(ArrayList<Ingredient> ingredients) {
		while (!this.ingredients.isEmpty()) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return;
			}
		}
		this.ingredients.addAll(ingredients);
		printIngredients();
		notifyAll();
	}

	public synchronized ArrayList<Ingredient> get(ArrayList<Ingredient> ingredients) {
		while (!this.ingredients.containsAll(ingredients) && numberOfSandwichesMade < 20) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
				return null;
			}
		}
		if(numberOfSandwichesMade >= 20)
			return null;
					
		this.ingredients.removeAll(ingredients);
		incrementNumberOfSandwichesMade();
		printIngredients();
		notifyAll();
		return ingredients;
	}

	/**
	 * Increments the number of sandwiches that have been made by one.
	 */
	public synchronized void incrementNumberOfSandwichesMade() {
		numberOfSandwichesMade++;
	}
	
	public synchronized int getNumberOfSandwichesMade(){
		return numberOfSandwichesMade;
	}
	
	/**
	 * Prints the contents of the class variable ingredients. If ingredients
	 * is empty, then that is printed.
	 */
	private synchronized void printIngredients(){
		String s = "Table contains";
		for(Ingredient ingredient : ingredients)
			s += " " + ingredient;
		if(ingredients.isEmpty())
			s = "Table is empty";
		System.out.println(s);
	}

}
