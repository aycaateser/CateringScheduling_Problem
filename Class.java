package mypack;
import java.util.Random;
/*

 * Instantiate a thread by using Thread class.
 */

public class MyClass {
	
	public static void main(String[] args) {
		
		System.out.println("Thread main started");
		AllServe allServe = new AllServe(30,15,30);  //all ingredients the waiter has
		serve trayb = new serve(0); //our borek tary
		serve trayc = new serve(0); //our cake try
		serve trayd = new serve(0); //our drink tray
		
		Customer c1 = new Customer(trayb,trayc,trayd,1);	
		Customer c2 = new Customer(trayb,trayc,trayd,2);
		Customer c3 = new Customer(trayb,trayc,trayd,3);
		Customer c4 = new Customer(trayb,trayc,trayd,4);
		Customer c5 = new Customer(trayb,trayc,trayd,5);
		Customer c6 = new Customer(trayb,trayc,trayd,6);
		Customer c7 = new Customer(trayb,trayc,trayd,7);
		Customer c8 = new Customer(trayb,trayc,trayd,8);
		Customer c9 = new Customer(trayb,trayc,trayd,9);
		Customer c10 = new Customer(trayb,trayc,trayd,10);
		Waiter w = new Waiter(allServe,trayb,trayc,trayd);
		// our customer tray, all gets the same tray so they will wait trays for the available time.
		Thread customer1 = new Thread(c1);
		Thread customer2 = new Thread(c2);
		Thread customer3 = new Thread(c3);
		Thread customer4 = new Thread(c4);
		Thread customer5 = new Thread(c5);
		Thread customer6 = new Thread(c6);
		Thread customer7 = new Thread(c7);
		Thread customer8 = new Thread(c8);
		Thread customer9 = new Thread(c9);
		Thread customer10 = new Thread(c10);
		Thread waiter = new Thread(w);

		//threads are starting
		customer1.start();
		customer2.start();
		customer3.start();
		customer4.start();
		customer5.start();
		customer6.start();
		customer7.start();
		customer8.start();
		customer9.start();
		customer10.start();
		waiter.start();
		
		System.out.println("Thread main finished");
	}
}


// customer thread
class Customer implements Runnable {
	public serve[] tray = new serve[3];
	public int turn; //take something to dring or eat; 0 for eat, 1 for drink


	public AllServe eaten;
	public int customerID;
	
	public Customer(serve t1,serve t2, serve t3, int id) {
		this.tray[0] = t1; //borek tray
		this.tray[1] = t2; //cake tray
		this.tray[2] = t3; //drink tray
		customerID = id; //id of customer
		turn = 0; //the turn for customer to eat or drink
		eaten = new AllServe(0,0,0); //the ingredients the customer already ate
	}



	
	public void run() {
		//System.out.println("step0");
		while(!tray[0].isDone | !tray[1].isDone | !tray[2].isDone){ //continue untils all the trays is empty
			int rand0 = (int)(Math.random() * 10 + 1); //random for demand of custemer to eat or drink something
			//System.out.println("step0 rand0: "+rand0);
			if(rand0 > 3){  //if the custemer want to eat or drink something
				//System.out.println("step1 turn: "+turn);
				if(turn == 0){
					int rand2 = (int)(Math.random() * 3 + 1); //random for choosing cake or borek
					//System.out.println("step2 rand2: "+rand2 +"id: "+customerID+" eatan borek: "+eaten.borek);
					if(rand2 != 1 & eaten.borek<5){  //if customer didnt eat borek too much and chose to eat borek
						//System.out.println("step3,1  flag: "+ tray[0].flag);
						if(tray[0].flag){ //if borek tray is available
							tray[0].flag = false; //make the borek tray busy
								if(tray[0].quantity > 0){ //if it is not empty then eat borek
									tray[0].quantity--;
									eaten.borek++;
									System.out.println("Customer"+customerID+" is eating borek");
									turn = 1; //now turn change to drink turn
								}
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								tray[0].flag = true; //now again borek tray is available for other people
							}
						else{  // if tray is busy
							System.out.println("Customer"+customerID+" is waiting for something to eat");
						}
						
						
					}
					else if(rand2 ==  1 & eaten.cake < 2){  //if custemer didnt eat cake too much and chose to eat cake
						//System.out.println("step3,2");
						
						if(tray[1].flag){ //if cake tray is available
							tray[1].flag = false; //make it busy for other people
						
							if(tray[1].quantity > 0){ //if the cake tray is not empty eat one cake
								tray[1].quantity--;
								eaten.cake++;
								System.out.println("Customer"+customerID+" is eating cake");
								try {
									Thread.sleep(1000);
								} catch (InterruptedException e) {
									e.printStackTrace();
								}
								turn = 1; //change to turn to drink for next time
							}
							tray[1].flag = true; //now cake tray available for other customers
						}
						else{  //if tray is busy
							System.out.println("Customer"+customerID+" is waiting for something to eat");
						}
						
					}
				}
				else{
					if(eaten.drink < 5 & tray[2].quantity>0){ //if the custemer didnt drink too much and the drink tray isnt empty
						
						if(tray[2].flag ){  //if drink tray is available
							tray[2].flag = false; //make it busy
							
							tray[2].quantity--; //aet one cake
							eaten.drink++;
							System.out.println("Customer"+customerID+" is drinking");
							try {
								Thread.sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
							tray[2].flag = true; //now drink tray is available for other customers
							turn = 0; //next time turn to eat
						}
						else{ //if tray busy
							System.out.println("Customer"+customerID+" is waiting for drink");
						}
						
						
					}

				}
			}
			try {//wait for a while after eating or drinking or doing nothing
				int waitRand = (int)(Math.random() * 100 + 1);
				waitRand += 2000;
				Thread.sleep(waitRand);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(true){ // after all the job is done, prints the food everyone ate
			if(tray[0].printFlag){ // to provent to mixing of printing, firstly one customer will print then the others
				tray[0].printFlag = false;
				System.out.println("Customer"+customerID+" ate");
				eaten.Print();
				System.out.println("-----------------");
				tray[0].printFlag = true;
				break;
			}
		}
	}
}


//waiter thread
class Waiter implements Runnable {
	AllServe allServe;
	public serve[] tray = new serve[3];  //0 for borek, 1 for cake, 2 drink
	int count = 0;

	public Waiter(AllServe all,serve t1,serve t2, serve t3){
		this.allServe = all;  //all ingredients he has
		tray[0] = t1;  //borek tray
		tray[1] = t2;  // cake tray
		tray[2] = t3;  // drink tray
	}
	public void run(){
		while(!allServe.Empty()){ // loop until the waiter has nothing
			if((tray[0].quantity == 1 | tray[0].quantity == 0) & allServe.borek != 0 ){ //if there is 1 or zero borek left in borek tray and the waiter has borek to refill
				synchronized(tray[0]){
					if(tray[0].flag){ //if borek tray is available
						tray[0].flag = false;  //make it busy
						System.out.println("------------------------------");
						System.out.println("Waiter refills the boreks");
						allServe.borek += tray[0].quantity; 
						if(allServe.borek > 4){ //refill the borek tray
							tray[0].quantity = 5;
							allServe.borek -= 5;
						}
						else{  //if less then 5 in the waiter then put them all
							tray[0].quantity = allServe.borek;
							allServe.borek = 0;
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						tray[0].flag = true; //now borek tray is available
					}
					
				}
			}
			if ((tray[1].quantity == 1 | tray[1].quantity == 0) & allServe.cake != 0 ){ //if there is 1 or 0 cake left in cake tray, and also the waiter has cake to refill
				synchronized(tray[1]){
					if(tray[1].flag){  // if cake tray is available
						System.out.println("------------------------------");
						System.out.println("Waiter refills the cakes");
						tray[1].flag = false;  //then make it busy
						allServe.cake += tray[1].quantity;
						if(allServe.cake > 4){  //refsill cake tray
							tray[1].quantity = 5;
							allServe.cake -= 5;
						}
						else{ //if the waiter has less than five cake, put them all to tray
							tray[1].quantity = allServe.cake;
							allServe.cake = 0;
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						tray[1].flag = true; // now cake tray is available
					}
				}
			}

			if((tray[2].quantity == 1 | tray[2].quantity == 0) & allServe.drink != 0 ){ //if there is 1 or 0 drink left in drink tray, and also the waiter has drink to refill
				synchronized(tray[2]){
					if(tray[2].flag){ //if drink tray is available
						System.out.println("------------------------------");
						System.out.println("Waiter refills the drinks");
						//System.out.println("Flag is true");
						tray[2].flag = false;  //then make it busy
						allServe.drink += tray[2].quantity;
						if(allServe.drink > 4){  //refill the drink tray
							tray[2].quantity = 5;
							allServe.drink -= 5;
						}
						else{//if the waiter has less than five drink, put them all to tray
							tray[2].quantity = allServe.drink;
							allServe.drink = 0;
						}
						try {
							Thread.sleep(2000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
						tray[2].flag = true;  //now drink tray is available
					}
					
				}
			}

			if(tray[0].quantity+tray[1].quantity+allServe.borek+allServe.cake <6){ //if all food less than 6 then all customer eat enough
				if(allServe.drink == 0 & tray[2].quantity == 0){ // we can say all trays are done
					tray[0].isDone = true;
					tray[1].isDone = true;
					tray[2].isDone = true;
					break;
				}
			}
			if(tray[0].quantity+tray[1].quantity+allServe.borek+allServe.cake ==6){ //if one more food remains because of maximum eaten food
				count += 1;
				if(count == 8 & allServe.drink == 0 & tray[2].quantity == 0){ //after a while end the program
					tray[0].isDone = true;
					tray[1].isDone = true;
					tray[2].isDone = true;
					break;
				}
			}

			System.out.println("------------------------------------------------");
			System.out.println("All borek: "+allServe.borek+" cake: "+allServe.cake+" drink: "+allServe.drink);
			System.out.println("borek tray: "+tray[0].quantity+ " cake tray: "+tray[1].quantity+" drink tray: "+tray[2].quantity);
			System.out.println("------------------------------------------------");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		while(!tray[0].isDone | !tray[1].isDone | !tray[2].isDone){ // if one them is not done wait until the trays empty
			if(tray[0].quantity == 0){
				tray[0].isDone = true;
			}
			if(tray[1].quantity == 0){
				tray[1].isDone = true;
			}
			if(tray[2].quantity == 0){
				tray[2].isDone = true;
			}
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}	
}


// and object to keep all ingradients to serve to people
//also used for store eaten foods and drinks of each customer
class AllServe{
	int borek;
	int cake;
	int drink;
	public AllServe(int b, int c, int d){
		borek = b;
		cake = c;
		drink = d;
	}
	public boolean Empty(){
		if (borek+cake+drink == 0){
			return true;
		}
		return false;
	}

	public void Print(){
		System.out.println("Borek: "+ borek);
		System.out.println("Cake: "+ cake);
		System.out.println("Drink: "+ drink);
	}
}

//an object for performs the trays
class serve{
	int quantity;  //number of ingredients in tray
	boolean flag;  //flag for understand the tray is busy or available
	public boolean isDone; //when the whole serve is finished it will be true
	public boolean printFlag;  //to prevent mixing print data 
	public serve(int q){  //constructer
		quantity = q;
		flag = true;
		printFlag = true;
		isDone = false;
	}

	public boolean Empty(){
		if (quantity == 0){
			return true;
		}
		return false;
	}
}
