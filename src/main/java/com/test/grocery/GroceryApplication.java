package com.test.grocery;

import com.test.grocery.service.GroceryAppService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.System.exit;

@SpringBootApplication
public class GroceryApplication implements CommandLineRunner {

    @Autowired
    private GroceryAppService groceryAppService;

    public static void main(String[] args) {
        SpringApplication.run(GroceryApplication.class, args);
    }

    private static int randomIndex(int min, int max){
        if (min >= max) {
            throw new IllegalArgumentException("max must be greater than min");
        }

        Random rand = new Random();
        return rand.nextInt((max - min) + 1) + min;
    }

    @Override
    public void run(String...args)throws Exception {
        System.out.println("\n\n\n\n\n\n\n---------------------------------------------");
        System.out.println(groceryAppService.appTitle());
        System.out.println("---------------------------------------------");

        String print = "f";
        List<Integer> subTotal = new ArrayList<>();
        List<String> receiptItems = new ArrayList<>();

        do {
            String[] item = groceryAppService.getAllItems().get(randomIndex(0, groceryAppService.getAllItems().size() - 1)).split(",");
            String item_name = item[0];
            String item_price = item[3];
            String item_on_sale = item[4];

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Scanned Item: " + item_name);
            System.out.println("Quantity: ");
            try {
                int quantity = Integer.parseInt(br.readLine());
                int price = Integer.parseInt(item_price.trim());
                int amount = price * quantity;
                String isPromo = item_on_sale.trim();

                subTotal.add(amount);
                System.out.println("Amount: " + amount);

                int totalAmount = 0;
                for(int sTotal : subTotal) {
                    totalAmount += sTotal;
                }

                receiptItems.add(item_name + "," + quantity + "," + amount + "," + isPromo);

                System.out.println("Total: " + totalAmount + "\n");
                System.out.println("Press any key to add more | Press T for payment");
                try{
                    String ans = br.readLine();
                    if(ans.equalsIgnoreCase("t")){
                        System.out.println("\n\n\n---------------------------------------------");
                        System.out.println(groceryAppService.receiptHeader());
                        System.out.println("---------------------------------------------");

                        String promoMsg = "";
                        for(String receiptItem : receiptItems){
                            String[] printItems = receiptItem.split(",");
                            if(printItems[3].equalsIgnoreCase("1")){
                                int freeItems = Integer.parseInt(printItems[1]) * 2;
                                promoMsg = (Integer.parseInt(printItems[1]) + freeItems) + " [Buy 1 Take 2] " + " @ " + printItems[2];
                            } else if(printItems[3].equalsIgnoreCase("2")){
                                int freeItems = Integer.parseInt(printItems[1]) / 2;
                                promoMsg = (Integer.parseInt(printItems[1]) + freeItems) + " [Buy 2 Take 1] " + " @ " + printItems[2];
                            } else {
                                promoMsg = printItems[1] + " @ " + printItems[2];
                            }
                            System.out.println(printItems[0] + " " + promoMsg);
                        }

                        System.out.println("---------------------------------------------");
                        System.out.println("Total: " + totalAmount);
                        System.out.println("---------------------------------------------\n\n");
                        exit(0);
                    } else {
                        System.out.println("\n");
                    }
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }catch (NumberFormatException nfe){
                System.err.println("Invalid format");
            }
        } while(print.toUpperCase().equalsIgnoreCase("f"));

        System.out.println("---------------------------------------------\n\n\n\n\n\n\n");
    }
}
